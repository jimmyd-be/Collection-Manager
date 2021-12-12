package be.jimmyd.cm.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "cm_invalid_token")
public class InvalidToken {

    @Id
    @Column(name = "token")
    private String token;

    @Column(name = "invalid_from")
    private LocalDateTime invalidFrom;

    private InvalidToken() {}

    private InvalidToken(Builder builder) {
        token = builder.token;
        invalidFrom = builder.invalidFrom;
    }

    public String getToken() {
        return token;
    }

    public LocalDateTime getInvalidFrom() {
        return invalidFrom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvalidToken that = (InvalidToken) o;
        return token.equals(that.token) && Objects.equals(invalidFrom, that.invalidFrom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, invalidFrom);
    }


    public static final class Builder {
        private String token;
        private LocalDateTime invalidFrom;

        public Builder() {
        }

        public Builder withToken(String val) {
            token = val;
            return this;
        }

        public Builder withInvalidFrom(LocalDateTime val) {
            invalidFrom = val;
            return this;
        }

        public InvalidToken build() {
            return new InvalidToken(this);
        }
    }
}
