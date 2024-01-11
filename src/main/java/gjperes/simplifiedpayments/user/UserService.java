package gjperes.simplifiedpayments.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    public boolean hasSufficientAmount(Long payerId) {
        double random = Math.random();
        return random > 0.15;
    }

    public boolean userExistsById(Long userId) {
        double random = Math.random();
        return random > 0.01;
    }

    public boolean hasPermissionToPay(Long userId) {
        double random = Math.random();
        return random > 0.01;
    }
}
