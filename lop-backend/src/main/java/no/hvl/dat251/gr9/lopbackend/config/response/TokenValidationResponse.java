package no.hvl.dat251.gr9.lopbackend.config.response;

import java.util.Date;

public class TokenValidationResponse {
    private String token;
    private boolean valid;
    private Date tokenExpirationDate;

    public TokenValidationResponse(String token, boolean valid, Date tokenExpirationDate) {
        this.token = token;
        this.valid = valid;
        this.tokenExpirationDate = tokenExpirationDate;
    }

    public void setToken(String token) { this.token = token;}
    public void setValid(boolean valid) { this.valid = valid;}
    public void setTokenExpirationDate(Date tokenExpirationDate) {
        this.tokenExpirationDate = tokenExpirationDate;
    }

    public String getToken() { return token; }
    public boolean getValid() { return valid; }
    public Date getTokenExpirationDate() { return tokenExpirationDate; }
}
