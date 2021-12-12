package be.jimmyd.cm.domain.service;

import be.jimmyd.cm.domain.external.ExternalApi;
import be.jimmyd.cm.dto.SettingDto;
import be.jimmyd.cm.entities.Setting;
import be.jimmyd.cm.repositories.SettingRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SettingService {

    private final SettingRepository settingRepository;
    private final List<ExternalApi> externalApiList;

    public SettingService(SettingRepository settingRepository, List<ExternalApi> externalApiList) {
        this.settingRepository = settingRepository;
        this.externalApiList = externalApiList;
    }

    public List<SettingDto> getAllSettings() {

        List<String> properties = getAllApiProperties();
        List<Setting> settings = settingRepository.findAll();

        return properties.stream()
                .map(property -> {
                    SettingDto.Builder dto = new SettingDto.Builder();
                    dto.withKey(property);

                    settings.stream()
                            .filter(n -> n.getId().equals(property))
                            .map(Setting::getValue)
                            .findFirst()
                            .ifPresent(dto::withValue);

                    return dto.build();
                })
                .collect(Collectors.toList());
    }

    private List<String> getAllApiProperties() {

        List<String> properties = new ArrayList<>();

        for (ExternalApi api : externalApiList) {
            api.getProperties().stream()
                    .map(property -> api.getUniqueKey() + "." + property)
                    .forEach(properties::add);
        }

        return properties;
    }

    public void saveSettings(List<SettingDto> settings) {

        final List<Setting> previousSettings = settingRepository.findAll();
        final List<String> allSettings = getAllApiProperties();

        settings.parallelStream()
                .map(setting ->
                    editOrCreateSetting(previousSettings, allSettings, setting)
                )
                .filter(Objects::nonNull)
                .forEach(settingRepository::save);
    }

    private Setting editOrCreateSetting(List<Setting> previousSettings, List<String> allSettings, SettingDto setting) {
        final Optional<Setting> settingOptional = previousSettings.stream().filter(n -> n.getId().equals(setting.getKey())).findFirst();

        Setting newSetting = null;
        if (settingOptional.isPresent()) {
            newSetting = settingOptional.get();
            newSetting.setValue(setting.getValue());
        } else if (allSettings.contains(setting.getKey())) {
            newSetting = new Setting.Builder()
                    .withId(setting.getKey())
                    .withValue(setting.getValue())
                    .build();
        }

        return newSetting;
    }
}
