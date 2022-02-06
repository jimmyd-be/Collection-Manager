package be.jimmyd.cm.dto;

public class TokenDto {

    private String token;

    private TokenDto() {
    }

    public TokenDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
