package be.jimmyd.cm.domain.mappers;

import be.jimmyd.cm.dto.UserCollectionDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static be.jimmyd.cm.constants.CollectionDtoTestConstants.userCollectionDto;
import static be.jimmyd.cm.constants.CollectionTestConstants.userCollection;

@ExtendWith(MockitoExtension.class)
class CollectionUserMapperTest {

    @InjectMocks
    private CollectionUserMapper mapper;

    @Test
    public void map() {
        UserCollectionDto result = mapper.map(userCollection());

        Assertions.assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(userCollectionDto());

    }

    @Test
    public void mapList() {
        List<UserCollectionDto> result = mapper.map(List.of(userCollection()));

        Assertions.assertThat(result.get(0))
                .usingRecursiveComparison()
                .isEqualTo(userCollectionDto());
    }

}