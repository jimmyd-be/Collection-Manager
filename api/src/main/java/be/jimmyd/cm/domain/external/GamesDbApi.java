package be.jimmyd.cm.domain.external;

import be.jimmyd.cm.dto.ItemSearchDto;
import be.jimmyd.cm.entities.Field;
import be.jimmyd.cm.repositories.SettingRepository;
import io.github.jimmydbe.TheGamesDbClient;
import io.github.jimmydbe.entities.GameDto;
import io.github.jimmydbe.entities.Genre;
import io.github.jimmydbe.entities.Publisher;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GamesDbApi implements ExternalApi {

    private final SettingRepository settingRepository;

    public GamesDbApi(final SettingRepository settingRepository, final RestTemplate restTemplate) {
        this.settingRepository = settingRepository;
    }

    @Override
    public CollectionTypeEnum getType() {
        return CollectionTypeEnum.GAMES;
    }

    @Override
    public String getUniqueKey() {
        return "theGamesDB";
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

        TheGamesDbClient client = new TheGamesDbClient(settingRepository.getById(getUniqueKey() + ".apiKey").getValue());

        return client.getGameByName(search).parallelStream()
                .map(game ->
                        new ItemSearchDto.Builder()
                                .withName(game.getGame_title())
                                .withUrl("https://thegamesdb.net/game.php?id=" + game.getId())
                                .withImage(game.getImageBaseurl().getThumb() + game.getImages().stream().filter(n -> n.getSide().equals("front") && n.getType().equals("boxart")).findFirst().get().getFilename())
                                .withExternalId(String.valueOf(game.getId()))
                                .withReleaseDate(game.getRelease_date().getYear())
                                .withSource(getUniqueKey())
                                .build()
                )
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, String> getById(String id, List<Field> basicFields) throws Throwable {

        TheGamesDbClient client = new TheGamesDbClient(settingRepository.getById(getUniqueKey() + "apiKey").getValue());

        final List<GameDto> gameById = client.getGameById(Integer.valueOf(id));
        final List<Genre> genres = client.getGenres();
        final List<Publisher> publishers = client.getPublishers();

        Map<String, String> gameMapping = new HashMap<>();

        if (gameById != null && !gameById.isEmpty()) {

            GameDto dto = gameById.get(0);

            basicFields.parallelStream().forEach(field -> {

                switch (field.getName()) {
                    case "title":
                        gameMapping.put(field.getId() + "_0", dto.getGame_title());
                        break;

                    case "genre":
                        for (int i = 0; i < dto.getGenres().size(); i++) {

                            int genreId = dto.getGenres().get(i);

                            final Optional<Genre> genre = genres.stream()
                                    .filter(n -> n.getId() == genreId)
                                    .findFirst();

                            if (genre.isPresent()) {
                                gameMapping.put(field.getId() + "_" + i, genre.get().getName());
                            }
                        }
                        break;
                    case "releaseDate":
                        gameMapping.put(field.getId() + "_0", dto.getRelease_date().toString());
                        break;
                    case "platform":
                        gameMapping.put(field.getId() + "_0", dto.getPlatformObject().getName());
                        break;
                    case "publisher":

                        for (int i = 0; i < dto.getPublishers().size(); i++) {

                            int publisherId = dto.getPublishers().get(i);

                            final Optional<Publisher> publisher = publishers.stream()
                                    .filter(n -> n.getId() == publisherId)
                                    .findFirst();

                            if (publisher.isPresent()) {
                                gameMapping.put(field.getId() + "_" + i, publisher.get().getName());
                            }
                        }

                        break;
                    case "storyline":
                        gameMapping.put(field.getId() + "_0", dto.getOverview());
                        break;
                    case "cover":
                        gameMapping.put(field.getId() + "_0", dto.getImageBaseurl().getOriginal() + dto.getImages().stream().filter(n -> n.getSide().equals("front") && n.getType().equals("boxart")).findFirst().get().getFilename());
                        break;
                    case "rating":
                        gameMapping.put(field.getId() + "_0", dto.getRating());
                        break;
                }
            });
        }

        return gameMapping;
    }
}
