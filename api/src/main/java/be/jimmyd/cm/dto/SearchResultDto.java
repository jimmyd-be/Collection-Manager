package be.jimmyd.cm.dto;

import lombok.Data;

import java.util.List;

@Data
public class SearchResultDto {

    private long collectionId;
    private String collectionName;
    private List<ItemDto> items;
}
