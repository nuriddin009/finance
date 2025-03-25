package uz.aloqabank.template.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.aloqabank.template.controller.request.UserRequest;
import uz.aloqabank.template.dto.User;
import uz.aloqabank.template.exception.ExceptionHandler;
import uz.aloqabank.template.model.MUser;
import uz.aloqabank.template.repository.UserRepository;
import uz.aloqabank.template.utils.SecurityContextUtils;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements ExceptionHandler {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User getCurrentUser() {
        Long userId = SecurityContextUtils.getUserId();

        if (userId == null) {
            return null;
        }
        return getById(userId);
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .map(this::userMapping).orElse(null);
    }

    private User userMapping(MUser mUser) {
        List<SimpleGrantedAuthority> roles = Optional.ofNullable(mUser.getRoles())
                .map(r -> r.stream().map(ur -> new SimpleGrantedAuthority(ur.getCode())).toList()).orElse(List.of());
        User user = new User(mUser.getUsername(), mUser.getPassword(), roles);
        user.setId(mUser.getId());
        user.setFirstName(mUser.getFirstName());
        user.setLastName(mUser.getLastName());
        return user;
    }

    public User updateUser(UserRequest request) {
        return userRepository.findByUsername(request.getUsername())
                .map(user -> {
                    user.setUsername(request.getUsername());
                    if (!StringUtils.isEmpty(request.getPassword())) {
                        user.setPassword(passwordEncoder.encode(request.getPassword()));
                    }
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    user.setDeleted(false);
                    return userRepository.save(user);
                }).map(this::userMapping).orElse(null);
    }

    public Optional<MUser> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
