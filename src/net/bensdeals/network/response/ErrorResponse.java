package net.bensdeals.network.response;

public class ErrorResponse<T> extends Response<T> {
    public ErrorResponse() {
        super(500, null, null, null);
    }
}
