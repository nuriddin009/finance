package uz.banking.finance.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import uz.banking.finance.constants.ResponseStatus;
import uz.banking.finance.controller.request.UserRequest;
import uz.banking.finance.dto.Response;
import uz.banking.finance.dto.User;
import uz.banking.finance.exception.ExceptionHandler;
import uz.banking.finance.repository.UserRepository;
import uz.banking.finance.service.UserService;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController implements ExceptionHandler {

    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("/account")
    public User getAccount() {
        return userService.getCurrentUser();
    }


    @GetMapping("/users/{userId}")
    public Response<User> getUser(@PathVariable("userId") UUID userId) {
        log.debug("REST request to get User : {}", userId);
        if (userId == null || !userRepository.existsById(userId)) {
            exception("User not found.", ResponseStatus.NOT_FOUND);
        }
        User user = userService.getById(userId);
        return Response.<User>ok().setPayload(user);
    }

    @PutMapping("/users/{userId}")
    public Response<User> updateUser(@PathVariable("userId") UUID userId, UserRequest userRequest) {
        log.debug("REST request to get User : {}", userId);
        if (userId == null || !userRepository.existsById(userId)) {
            exception("Пользователь не найден.", ResponseStatus.NOT_FOUND);
        }
        if (StringUtils.isEmpty(userRequest.getUsername())) {
            exception("Username not found", ResponseStatus.BAD_REQUEST);
        }
        if (StringUtils.isEmpty(userRequest.getFirstName())) {
            exception("First name not found", ResponseStatus.BAD_REQUEST);
        }
        if (StringUtils.isEmpty(userRequest.getLastName())) {
            exception("Last name not found", ResponseStatus.BAD_REQUEST);
        }
        if (!userRepository.existsByIdAndUsername(userId, userRequest.getUsername())) {
            exception("Username already exist", ResponseStatus.BAD_REQUEST);
        }
        User user = userService.updateUser(userRequest);
        return Response.<User>ok().setPayload(user);
    }
}
