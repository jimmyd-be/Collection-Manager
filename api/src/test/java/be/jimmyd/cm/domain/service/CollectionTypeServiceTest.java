package be.jimmyd.cm.domain.service;

import be.jimmyd.cm.repositories.CollectionTypeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static be.jimmyd.cm.constants.CollectionTestConstants.COLLECTION_TYPE_NAME;
import static be.jimmyd.cm.constants.CollectionTestConstants.collectionType;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CollectionTypeServiceTest {

    @Mock
    private CollectionTypeRepository collectionTypeRepository;
    @InjectMocks
    private CollectionTypeService collectionTypeService;

    @Test
    public void getAllTypes() {

        when(collectionTypeRepository.getAllTypes()).thenReturn(List.of(collectionType()));

        List<String> result = collectionTypeService.getAllTypes();

        Assertions.assertThat(result).containsExactly(COLLECTION_TYPE_NAME);

    }

}