package be.jimmyd.cm.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ItemSearchDto {

    private String externalId;
    private String name;
    private String image;
    private LocalDate releaseDate;
    private String source;
    private String url;
}
