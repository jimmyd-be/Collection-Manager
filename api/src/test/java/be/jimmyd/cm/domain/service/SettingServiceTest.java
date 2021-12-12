package be.jimmyd.cm.domain.service;

import be.jimmyd.cm.domain.external.ExternalApi;
import be.jimmyd.cm.domain.external.GamesDbApi;
import be.jimmyd.cm.domain.external.ImdbMovieApi;
import be.jimmyd.cm.dto.SettingDto;
import be.jimmyd.cm.repositories.SettingRepository;
import io.github.jimmydbe.imdb.IMDB;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static be.jimmyd.cm.constants.ItemDtoTestConstant.settingDto;
import static be.jimmyd.cm.constants.ItemTestConstant.setting;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SettingServiceTest {

    @Mock
    private SettingRepository settingRepository;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private IMDB imdb;
    @Spy
    private List<ExternalApi> externalApiList = new ArrayList<>();
    @InjectMocks
    private SettingService settingService;

    @Test
    void getAllSettings() {
        when(settingRepository.findAll()).thenReturn(List.of(setting()));

        externalApiList.add(new GamesDbApi(settingRepository, restTemplate));
        externalApiList.add(new ImdbMovieApi(imdb));

        List<SettingDto> allSettings = settingService.getAllSettings();

        Assertions.assertThat(allSettings)
                .containsExactly(settingDto());
    }

    @Test
    void saveSettings() {
        externalApiList.add(new GamesDbApi(settingRepository, restTemplate));
        externalApiList.add(new ImdbMovieApi(imdb));

        settingService.saveSettings(List.of(settingDto()));

        verify(settingRepository, times(1)).save(setting());
    }

    @Test
    void saveSettings_edit() {
        when(settingRepository.findAll()).thenReturn(List.of(setting()));

        externalApiList.add(new GamesDbApi(settingRepository, restTemplate));
        externalApiList.add(new ImdbMovieApi(imdb));

        settingService.saveSettings(List.of(settingDto()));

        verify(settingRepository, times(1)).save(setting());
    }
}