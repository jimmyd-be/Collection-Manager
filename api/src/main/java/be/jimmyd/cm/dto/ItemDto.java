package be.jimmyd.cm.dto;

import lombok.Data;

import java.util.List;

@Data
public class ItemDto {

    private long id;
    private String name;
    private String image;
    private List<ItemDataDto> data;
}
