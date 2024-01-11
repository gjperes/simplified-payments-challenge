package gjperes.simplifiedpayments.payment;

import gjperes.simplifiedpayments.external.TransactionAuthorizationService;
import gjperes.simplifiedpayments.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.ZonedDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {
    private final UserService userService;
    private final TransactionAuthorizationService transactionAuthorizationService;

    @Transactional
    public PaymentResponse transfer(@NonNull @Valid PaymentPayload payload) {
        log.info("[creating] {}", payload);

        boolean payerExists = userService.userExistsById(payload.payer());
        boolean payeeExists = userService.userExistsById(payload.payee());

        boolean isPayerValid = payerExists && userService.hasPermissionToPay(payload.payer());

        if (!isPayerValid) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "payer '" + payload.payer() + "' not valid");
        }

        if (!payeeExists) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "payee '" + payload.payee() + "' not valid");
        }

        if (!userService.hasSufficientAmount(payload.payer())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "unsufficient fund amount");
        }

        UUID transactionId = UUID.randomUUID();
        ZonedDateTime now = ZonedDateTime.now();

        if (!transactionAuthorizationService.isTransactionAuthorized(transactionId)) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "transaction unauthorized");
        }

        ZonedDateTime paymentAt = ZonedDateTime.now();
        return new PaymentResponse(transactionId, payload.amount(), PaymentStatus.PAID, paymentAt, now, now);
    }
}
