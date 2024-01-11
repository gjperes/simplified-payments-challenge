package gjperes.simplifiedpayments.payment;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.ZonedDateTime;

public record PaymentPayload(@NotNull @Positive Long amount,
                             @NotNull Long payer,
                             @NotNull Long payee,
                             @NotNull ZonedDateTime expiresAt
) {
}
