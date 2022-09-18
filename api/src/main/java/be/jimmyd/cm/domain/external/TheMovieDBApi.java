package be.jimmyd.cm.domain.external;

import be.jimmyd.cm.dto.ItemSearchDto;
import be.jimmyd.cm.entities.Field;
import be.jimmyd.cm.repositories.SettingRepository;
import info.movito.themoviedbapi.TmdbApi;
import io.github.jimmydbe.imdb.domain.MovieDetails;
import io.github.jimmydbe.imdb.exceptions.IMDBException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TheMovieDBApi implements ExternalApi {

    private final SettingRepository settingRepository;
    private TmdbApi api;

    public TheMovieDBApi(SettingRepository settingRepository) {
        this.settingRepository = settingRepository;
    }

    @Override
    public CollectionTypeEnum getType() {
        return CollectionTypeEnum.MOVIES;
    }

    @Override
    public String getUniqueKey() {
        return "theMovieDb";
    }

    @Override
    public List<String> getProperties() {
        List<String> properties = new ArrayList<>();
        properties.add("apiKey");
        return properties;
    }

    @Override
    public boolean isReadyToUse() {
        return settingRepository.getById(getUniqueKey() + ".apiKey") != null;
    }

    @Override
    public List<ItemSearchDto> searchItems(final String search) {

        if (api == null) {
            api = new TmdbApi(settingRepository.getById(getUniqueKey() + ".apiKey").getValue());
        }

        return api.getSearch().searchMovie(search, null, "en", false, 0)
                .getResults()
                .stream()
                .map(movie ->
                    new ItemSearchDto.Builder()
                            .withExternalId(String.valueOf(movie.getId()))
                            .withImage("https://image.tmdb.org/t/p/w600" + movie.getPosterPath())
                            .withName(movie.getTitle())
                            .withSource(getUniqueKey())
                            //.withReleaseDate(movie.getReleaseDate() != null && movie.getReleaseDate().length() >= 4 ? Integer.valueOf(movie.getReleaseDate().substring(0, 4)) : null)
                            .withUrl(movie.getHomepage())
                            .build()

                )
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, String> getById(String id, List<Field> basicFields)  {

            if (api == null) {
                api = new TmdbApi(settingRepository.getById(getUniqueKey() + ".apiKey").getValue());
            }

//        Map<String, String> itemData = new HashMap<>();
//
//        final MovieDetails movie = api.getMovieDetails(id);
//
//        basicFields.parallelStream().forEach(field -> {
//
//            switch (field.getName()) {
//                case "title":
//
//                    if (movie.getMovieOriginalName() != null && !movie.getMovieOriginalName().isBlank()) {
//                        itemData.put(field.getId() + "_0", movie.getMovieOriginalName());
//                    } else {
//                        itemData.put(field.getId() + "_0", movie.getMovieName());
//                    }
//                    break;
//                case "genre":
//
//                    for (int i = 0; i < movie.getCategories().size(); i++) {
//                        itemData.put(field.getId() + "_" + i, movie.getCategories().get(i));
//                    }
//
//                    break;
//                case "runtime":
//                    itemData.put(field.getId() + "_0", String.valueOf(movie.getDuration()));
//                    break;
//                case "storyline":
//                    itemData.put(field.getId() + "_0", movie.getDescription());
//                    break;
//                case "director":
//
//                    for (int i = 0; i < movie.getDirectors().size(); i++) {
//                        itemData.put(field.getId() + "_" + i, movie.getDirectors().get(i));
//                    }
//                    break;
//                case "writer":
//                    for (int i = 0; i < movie.getWriters().size(); i++) {
//                        itemData.put(field.getId() + "_" + i, movie.getWriters().get(i));
//                    }
//                    break;
//                case "cast":
//                    for (int i = 0; i < movie.getStars().size(); i++) {
//                        itemData.put(field.getId() + "_" + i, movie.getStars().get(i));
//                    }
//                    break;
//                case "cover":
//                    itemData.put(field.getId() + "_0", movie.getImage());
//                    break;
//                case "rating":
//                    itemData.put(field.getId() + "_0", String.valueOf(movie.getRating()));
//                    break;
//            }
//        });
//
//        return itemData;

        return null;
    }
}
