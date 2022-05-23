package be.jimmyd.cm.domain.external;

import be.jimmyd.cm.dto.ItemSearchDto;
import be.jimmyd.cm.entities.Field;
import be.jimmyd.cm.repositories.SettingRepository;
import com.omertron.omdbapi.OmdbApi;
import com.omertron.omdbapi.model.OmdbVideoFull;
import com.omertron.omdbapi.model.SearchResults;
import com.omertron.omdbapi.tools.OmdbParameters;
import com.omertron.omdbapi.tools.Param;
import io.github.jimmydbe.imdb.domain.MovieDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OmdbMovieApi implements ExternalApi{

    private final SettingRepository settingRepository;

    public OmdbMovieApi(SettingRepository settingRepository) {
        this.settingRepository = settingRepository;
    }

    @Override
    public CollectionTypeEnum getType() {
        return CollectionTypeEnum.MOVIES;
    }

    @Override
    public String getUniqueKey() {
        return "omdb";
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
    public List<ItemSearchDto> searchItems(String search) throws Throwable {

        OmdbApi omdb = new OmdbApi(settingRepository.getById(getUniqueKey() + ".apiKey").getValue());

        SearchResults results = omdb.search(search);

        return results.getResults()
                .parallelStream()
                .map(result -> new ItemSearchDto.Builder()
                        .withExternalId(result.getImdbID())
                        .withName(result.getTitle())
                        .withImage(result.getPoster())
                        .withSource(getUniqueKey())
                        .withReleaseDate(Integer.parseInt(result.getYear()))
                        .withUrl("https://www.imdb.com/title/" + result.getImdbID())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, String> getById(String id, List<Field> basicFields) throws Throwable {
        OmdbApi omdb = new OmdbApi(settingRepository.getById(getUniqueKey() + ".apiKey").getValue());
        Map<String, String> itemData = new HashMap<>();

        OmdbParameters omdbParameters = new OmdbParameters();
        omdbParameters.add(Param.IMDB, id);

        OmdbVideoFull info = omdb.getInfo(omdbParameters);

        basicFields.parallelStream().forEach(field -> {

            switch (field.getName()) {
                case "title":
                        itemData.put(field.getId() + "_0", info.getTitle());
                    break;
                case "genre":

                    String[] genres = info.getGenre().split(", ");
                    for (int i = 0; i < genres.length; i++) {
                        itemData.put(field.getId() + "_" + i, genres[i]);
                    }

                    break;
                case "runtime":
                    itemData.put(field.getId() + "_0", String.valueOf(info.getRuntime()));
                    break;
                case "storyline":
                    itemData.put(field.getId() + "_0", info.getPlot());
                    break;
                case "director":

                    String[] directors = info.getDirector().split(", ");

                    for (int i = 0; i < directors.length; i++) {
                        itemData.put(field.getId() + "_" + i, directors[i]);
                    }
                    break;
                case "writer":

                    String[] writers = info.getWriter().split(", ");

                    for (int i = 0; i < writers.length; i++) {
                        itemData.put(field.getId() + "_" + i, writers[i]);
                    }
                    break;
                case "cast":

                    String[] actors = info.getActors().split(", ");

                    for (int i = 0; i < actors.length; i++) {
                        itemData.put(field.getId() + "_" + i, actors[i]);
                    }
                    break;
                case "cover":
                    itemData.put(field.getId() + "_0", info.getPoster());
                    break;
                case "rating":
                    itemData.put(field.getId() + "_0", String.valueOf(info.getImdbRating()));
                    break;
            }
        });

        return itemData;
    }
}
