package be.jimmyd.cm.domain.logic;

import be.jimmyd.cm.domain.external.ExternalApi;
import be.jimmyd.cm.dto.SettingDto;
import be.jimmyd.cm.entities.Setting;
import be.jimmyd.cm.repositories.SettingRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SettingLogic {

    private final SettingRepository settingRepository;
    private final List<ExternalApi> externalApiList;

    public SettingLogic(final SettingRepository settingRepository, final List<ExternalApi> externalApiList) {
        this.settingRepository = settingRepository;
        this.externalApiList = externalApiList;
    }

    public List<SettingDto> getAllSettings() {

        List<String> properties = getAllApiProperties();
        List<Setting> settings = settingRepository.findAll();

        return properties.stream()
                .map(property -> {
                    SettingDto dto = new SettingDto();
                    dto.setKey(property);

                    settings.stream()
                            .filter(n -> n.getKey().equals(property))
                            .map(setting -> setting.getValue())
                            .findFirst()
                            .ifPresent(value -> dto.setValue(value));

                    return dto;
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


}
