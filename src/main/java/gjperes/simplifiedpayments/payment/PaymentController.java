package gjperes.simplifiedpayments.payment;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(PaymentController.URL)
@RequiredArgsConstructor
public class PaymentController {
    public static final String URL = "/api/v1/payment";
    private final PaymentService service;

    @PostMapping
    public ResponseEntity<PaymentResponse> transfer(@NonNull @Valid @RequestBody PaymentPayload payload) {
        log.debug("[request] transfer {}", payload);
        return ResponseEntity.ok(service.transfer(payload));
    }
}
