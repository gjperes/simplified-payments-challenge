package gjperes.simplifiedpayments.payment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.ZonedDateTime;
import java.util.UUID;

public record PaymentResponse(@NotNull UUID transactionId,
                              @NotBlank Long amount,
                              @NotNull  PaymentStatus status,
                              @NotNull  ZonedDateTime paidAt,
                              @NotNull  ZonedDateTime createdAt,
                              @NotNull  ZonedDateTime updatedAt) {
}
