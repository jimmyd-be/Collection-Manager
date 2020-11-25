package be.jimmyd.cm.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ItemSearchDto {

    public String externalId;
    public String name;
    public String image;
    public LocalDate releaseDate;
    public String source;
    public String url;
}
