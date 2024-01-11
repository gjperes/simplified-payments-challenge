package gjperes.simplifiedpayments.infra.exceptions;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record ApiErrorDetails(String pointer,
                              String reason
) implements Serializable {
}
