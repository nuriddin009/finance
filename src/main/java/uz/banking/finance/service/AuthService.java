package uz.banking.finance.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.banking.finance.constants.ResponseStatus;
import uz.banking.finance.controller.vm.ChangePasswordRequest;
import uz.banking.finance.dto.DynamicDto;
import uz.banking.finance.exception.ApiException;
import uz.banking.finance.exception.ExceptionHandler;
import uz.banking.finance.model.MUser;
import uz.banking.finance.repository.UserRepository;
import uz.banking.finance.utils.SecurityContextUtils;

@Service
@RequiredArgsConstructor
public class AuthService implements ExceptionHandler {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DynamicDto changePassword(ChangePasswordRequest changePasswordRequest, HttpServletRequest request) {

        MUser mUser = userRepository.findByUsername(SecurityContextUtils.getUsername())
                .orElseThrow(() -> new ApiException("User not found", ResponseStatus.NOT_FOUND));

        mUser.setPassword(passwordEncoder.encode(changePasswordRequest.getPassword()));
        userRepository.save(mUser);

        DynamicDto result = new DynamicDto();
        result.addProperty("message", "Password has been changed!");
        return result;
    }
}
