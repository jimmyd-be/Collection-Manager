package be.jimmyd.cm.domain.external.gamesDb;

import be.jimmyd.cm.domain.external.CollectionTypeEnum;
import be.jimmyd.cm.domain.external.ExternalApi;
import be.jimmyd.cm.dto.ItemSearchDto;
import be.jimmyd.cm.entities.Field;
import be.jimmyd.cm.repositories.SettingRepository;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class GamesDbApi implements ExternalApi {

    private final String baseUrl = "api.thegamesdb.net";
    private final String searchUrl = "/v1.1/Games/ByGameName";
    private final String getUrl = "/v1/Games/ByGameID";
    private final String getPlatformUrl = "/v1/Platforms";

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
        return false; //TODO create check for property here
    }

    @Override
    public List<ItemSearchDto> searchItems(String search) throws Throwable {

        final URI url = getFullUrl(searchUrl).queryParam("name", search).build().toUri();

        final GamesDbResultDto<GamesDbSearchResultDto> body = restTemplate.exchange(url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<GamesDbResultDto<GamesDbSearchResultDto>>() {
                }).getBody();

        //TODO finalize this
        return null;
    }

    @Override
    public Map<String, String> getById(String id, List<Field> basicFields) throws Throwable {

        //TODO implement this
        return null;
    }

    private UriComponentsBuilder getFullUrl(final String url) {
        return UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(baseUrl)
                .path(url)
                .queryParam("apikey", this.settingRepository.getById("apiKey"))
                .queryParam("include", "boxart,platform");
    }
}
