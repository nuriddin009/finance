package uz.banking.finance.service;

import uz.banking.finance.controller.request.UserRequest;
import uz.banking.finance.dto.User;
import uz.banking.finance.model.MUser;

import java.util.Optional;
import java.util.UUID;

public interface UserService {

    User getCurrentUser();


    User getById(UUID id);


    User updateUser(UserRequest request);

    Optional<MUser> findByUsername(String username);
}
