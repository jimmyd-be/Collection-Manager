package be.jimmyd.cm.domain.external;

import be.jimmyd.cm.dto.ItemSearchDto;
import be.jimmyd.cm.entities.Field;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExternalSystem {

    private final List<ExternalApi> externalApis;

    public ExternalSystem(List<ExternalApi> externalApis) {
        this.externalApis = externalApis;
    }

    public List<ItemSearchDto> searchItemsByType(final String type, final String search) {
        return externalApis.parallelStream()
                .filter(n -> n.getType().getType().equalsIgnoreCase(type))
                .map(n -> {
                    try {
                        return n.searchItems(search);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                    return null;
                })
                .filter( n-> n != null)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public Map<String, String> getItemById(final String systemUniqueKey, final String itemId, List<Field> basicFields) {
        final Optional<ExternalApi> api = externalApis.stream()
                .filter(n -> n.getUniqueKey().equals(systemUniqueKey)).findFirst();

        if(api.isPresent()) {
            try {
                return api.get().getById(itemId, basicFields);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        return null;
    }

}
