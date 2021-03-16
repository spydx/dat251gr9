package no.hvl.dat251.gr9.lopbackend.config.security;

public class APIResponse {
    private boolean success;
    private String msg;

    public APIResponse(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }
}