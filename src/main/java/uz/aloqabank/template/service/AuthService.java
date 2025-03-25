package uz.aloqabank.template.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.aloqabank.template.constants.ResponseStatus;
import uz.aloqabank.template.controller.vm.ChangePasswordRequest;
import uz.aloqabank.template.dto.DynamicDto;
import uz.aloqabank.template.exception.ApiException;
import uz.aloqabank.template.exception.ExceptionHandler;
import uz.aloqabank.template.model.MUser;
import uz.aloqabank.template.repository.UserRepository;
import uz.aloqabank.template.utils.SecurityContextUtils;

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
