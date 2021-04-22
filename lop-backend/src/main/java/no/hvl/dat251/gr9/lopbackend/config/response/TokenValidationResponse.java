package no.hvl.dat251.gr9.lopbackend.config.response;

import lombok.Data;

import java.util.Date;


@Data
public class TokenValidationResponse {
    private String token;
    private boolean valid;
    private Date tokenExpirationDate;

    public TokenValidationResponse(String token, boolean valid, Date tokenExpirationDate) {
        this.token = token;
        this.valid = valid;
        this.tokenExpirationDate = tokenExpirationDate;
    }
}
