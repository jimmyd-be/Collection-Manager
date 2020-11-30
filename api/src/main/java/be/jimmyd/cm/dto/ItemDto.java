package be.jimmyd.cm.dto;

import lombok.Data;

import java.util.List;

@Data
public class ItemDto {

    public long id;
    public String name;
    public String image;
    public List<ItemDataDto> data;
}
