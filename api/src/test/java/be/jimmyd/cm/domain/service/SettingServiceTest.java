package be.jimmyd.cm.domain.service;

import be.jimmyd.cm.domain.external.ExternalApi;
import be.jimmyd.cm.repositories.SettingRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class SettingServiceTest {

    @Mock
    private SettingRepository settingRepository;
    @Mock
    private List<ExternalApi> externalApiList;
    @InjectMocks
    private SettingService settingService;

    @Disabled
    @Test
    void getAllSettings() {
    }

    @Disabled
    @Test
    void saveSettings() {
    }
}