package be.jimmyd.cm.dto;

import lombok.Data;

@Data
public class UserCollectionDto {

    private long userId;
    private String userName;
    private long roleId;
    private String roleName;
}
