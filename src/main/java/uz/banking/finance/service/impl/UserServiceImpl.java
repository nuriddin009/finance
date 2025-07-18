package uz.banking.finance.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.banking.finance.controller.request.UserRequest;
import uz.banking.finance.dto.User;
import uz.banking.finance.exception.ExceptionHandler;
import uz.banking.finance.model.MUser;
import uz.banking.finance.repository.UserRepository;
import uz.banking.finance.service.UserService;
import uz.banking.finance.utils.SecurityContextUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, ExceptionHandler {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User getCurrentUser() {
        UUID userId = SecurityContextUtils.getUserId();

        if (userId == null) {
            return null;
        }
        return getById(userId);
    }

    @Override
    public User getById(UUID id) {
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

    @Override
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

    @Override
    public Optional<MUser> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
