package be.jimmyd.cm.domain.external;

import be.jimmyd.cm.dto.ItemSearchDto;
import be.jimmyd.cm.entities.Field;
import io.github.jimmydbe.imdb.IMDB;
import io.github.jimmydbe.imdb.domain.MovieDetails;
import io.github.jimmydbe.imdb.exceptions.IMDBException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ImdbMovieApi implements ExternalApi {

    private final IMDB imdb;

    public ImdbMovieApi(IMDB imdb) {
        this.imdb = imdb;
    }

    @Override
    public CollectionTypeEnum getType() {
        return CollectionTypeEnum.MOVIES;
    }

    @Override
    public String getUniqueKey() {
        return "imdbMovie";
    }

    @Override
    public List<String> getProperties() {
        return new ArrayList<>();
    }

    @Override
    public boolean isReadyToUse() {
        return false; //Nothing to check here
    }

    @Override
    public List<ItemSearchDto> searchItems(final String search) throws IMDBException {

        return imdb.search(search).parallelStream()
                .filter(n -> n.getType().equalsIgnoreCase("Movie"))
                .map(n ->
                        new ItemSearchDto.Builder()
                                .withSource(getUniqueKey())
                                .withImage(n.getThumbnail())
                                .withName(n.getName())
                                .withExternalId(n.getId())
                                .withReleaseDate(n.getYear())
                                .withUrl("https://www.imdb.com/title/" + n.getId())
                                .build()
                )
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, String> getById(String id, List<Field> basicFields) throws IMDBException {

        Map<String, String> itemData = new HashMap<>();

        final MovieDetails movie = imdb.getMovieDetails(id);

        basicFields.parallelStream().forEach(field -> {

            switch (field.getName()) {
                case "title":

                    if (movie.getMovieOriginalName() != null && !movie.getMovieOriginalName().isBlank()) {
                        itemData.put(field.getId() + "_0", movie.getMovieOriginalName());
                    } else {
                        itemData.put(field.getId() + "_0", movie.getMovieName());
                    }
                    break;
                case "genre":

                    for (int i = 0; i < movie.getCategories().size(); i++) {
                        itemData.put(field.getId() + "_" + i, movie.getCategories().get(i));
                    }

                    break;
                case "runtime":
                    itemData.put(field.getId() + "_0", String.valueOf(movie.getDuration()));
                    break;
                case "storyline":
                    itemData.put(field.getId() + "_0", movie.getDescription());
                    break;
                case "director":

                    for (int i = 0; i < movie.getDirectors().size(); i++) {
                        itemData.put(field.getId() + "_" + i, movie.getDirectors().get(i));
                    }
                    break;
                case "writer":
                    for (int i = 0; i < movie.getWriters().size(); i++) {
                        itemData.put(field.getId() + "_" + i, movie.getWriters().get(i));
                    }
                    break;
                case "cast":
                    for (int i = 0; i < movie.getStars().size(); i++) {
                        itemData.put(field.getId() + "_" + i, movie.getStars().get(i));
                    }
                    break;
                case "cover":
                    itemData.put(field.getId() + "_0", movie.getImage());
                    break;
                case "rating":
                    itemData.put(field.getId() + "_0", String.valueOf(movie.getRating()));
                    break;
            }
        });

        return itemData;
    }
}
