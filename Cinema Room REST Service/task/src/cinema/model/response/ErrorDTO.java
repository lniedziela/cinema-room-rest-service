package cinema.model.response;

public class ErrorDTO {
    private final String error;

    public ErrorDTO(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
