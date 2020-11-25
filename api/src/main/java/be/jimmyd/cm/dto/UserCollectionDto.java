package be.jimmyd.cm.dto;

import lombok.Data;

@Data
public class UserCollectionDto {

    public long userId;
    public String userName;
    public long roleId;
    public String roleName;
}
