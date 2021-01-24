package be.jimmyd.cm.domain.external;

import be.jimmyd.cm.domain.external.CollectionTypeEnum;
import be.jimmyd.cm.domain.external.ExternalApi;
import be.jimmyd.cm.dto.ItemSearchDto;
import be.jimmyd.cm.entities.Field;
import be.jimmyd.cm.repositories.SettingRepository;
import io.github.jimmydbe.TheGamesDbClient;
import io.github.jimmydbe.entities.Game;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GamesDbApi implements ExternalApi {

    private final SettingRepository settingRepository;
    private final RestTemplate restTemplate;

    public GamesDbApi(final SettingRepository settingRepository, final RestTemplate restTemplate) {
        this.settingRepository = settingRepository;
        this.restTemplate = restTemplate;
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
        return settingRepository.getById(getUniqueKey() + "." + "apiKey") != null;
    }

    @Override
    public List<ItemSearchDto> searchItems(String search) throws Throwable {

       TheGamesDbClient client = new TheGamesDbClient(settingRepository.getById(getUniqueKey() + "apiKey").getValue());

        return client.getGameByName(search).parallelStream()
                .map(game -> {
                 ItemSearchDto dto = new ItemSearchDto();

                 dto.setName(game.getGame_title());
                 dto.setUrl("https://thegamesdb.net/game.php?id=" + game.getId());
                 //dto.setImage();
                 dto.setExternalId(String.valueOf(game.getId()));
                 dto.setSource(getUniqueKey());
                 dto.setReleaseDate(game.getRelease_date().getYear());

                 return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, String> getById(String id, List<Field> basicFields) throws Throwable {

        //TODO implement this
        return null;
    }
}
