package gjperes.simplifiedpayments.external;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionAuthorizationService {
    public boolean isTransactionAuthorized(@NonNull UUID paymentId) {
        double random = Math.random();
        return random > 0.15;
    }
}
