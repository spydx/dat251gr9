package no.hvl.dat251.gr9.lopbackend.config.security;

public class JwtAuthenticationResponse {
    private String token;
    private String tokenType = "Bearer";
    private String profile;

    public JwtAuthenticationResponse() {}

    public JwtAuthenticationResponse(String token, String profile) {
        this.token = token;
        this.profile = profile;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
