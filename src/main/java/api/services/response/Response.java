package api.services.response;

public class Response {
    ResponseType status;
    String message;
    Object data;

    public Response(ResponseType status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public Response(ResponseType status, Object data) {
        this.status = status;
        this.data = data;
    }

    public Response(ResponseType status, String message) {
        this.status = status;
        this.message = message;
    }
}
