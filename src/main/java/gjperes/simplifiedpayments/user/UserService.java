package gjperes.simplifiedpayments.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    public boolean hasSufficientAmount(User payer) {
        log.debug("[logic] verifiying if user '{}' has sufficient amount", payer.getId());
        double random = Math.random();
        return random > 0.15;
    }

    public Optional<User> findUserById(Long userId) {
        log.debug("[retrieving] find user by id '{}'", userId);
        double random = Math.random();
        if (random > 0.01) {
            return Optional.of(new User());
        }
        return Optional.empty();
    }

    public boolean hasPermissionToPay(User user) {
        log.debug("[logic] verifiying if user '{}' has permission to pay", user.getId());
        double random = Math.random();
        return random > 0.01;
    }
}
