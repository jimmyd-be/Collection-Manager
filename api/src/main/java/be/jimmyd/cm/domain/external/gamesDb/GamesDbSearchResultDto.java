package be.jimmyd.cm.domain.external.gamesDb;

import lombok.Data;

@Data
public class GamesDbSearchResultDto {

    private long id;
    private String game_title;
    private String release_date;
    private int platform;
}
