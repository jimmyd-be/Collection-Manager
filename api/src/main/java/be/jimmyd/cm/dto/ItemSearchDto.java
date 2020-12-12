package be.jimmyd.cm.dto;

import lombok.Data;

@Data
public class ItemSearchDto {

    private String externalId;
    private String name;
    private String image;
    private int releaseDate;
    private String source;
    private String url;
}
