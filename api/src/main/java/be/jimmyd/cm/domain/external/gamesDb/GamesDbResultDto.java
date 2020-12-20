package be.jimmyd.cm.domain.external.gamesDb;

import lombok.Data;

import java.util.List;

@Data
public class GamesDbResultDto<T> {

    private int code;
    private String status;
    private List<T> data;
}
