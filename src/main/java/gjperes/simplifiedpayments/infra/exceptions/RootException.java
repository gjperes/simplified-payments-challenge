package gjperes.simplifiedpayments.infra.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

@Getter
public class RootException extends RuntimeException {
    @Serial private static final long serialVersionUID = 1501142523763890585L;

    private final HttpStatus status;
    private final List<ApiErrorDetails> errors = new ArrayList<>();

    public RootException(HttpStatus status) {
        super();
        this.status = status;
    }

    public RootException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
