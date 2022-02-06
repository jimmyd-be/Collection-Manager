package be.jimmyd.cm.domain.mappers;

import be.jimmyd.cm.dto.RoleDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static be.jimmyd.cm.constants.CollectionDtoTestConstants.roleDto;
import static be.jimmyd.cm.constants.CollectionTestConstants.role;

@ExtendWith(MockitoExtension.class)
class RoleMapperTest {

    @InjectMocks
    private RoleMapper mapper;

    @Test
    public void map() {
        RoleDto result = mapper.map(role());

        Assertions.assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(roleDto());
    }

    @Test
    public void mapList() {
        List<RoleDto> result = mapper.map(List.of(role()));

        Assertions.assertThat(result.size()).isEqualTo(1);
        Assertions.assertThat(result.get(0))
                .usingRecursiveComparison()
                .isEqualTo(roleDto());
    }

}