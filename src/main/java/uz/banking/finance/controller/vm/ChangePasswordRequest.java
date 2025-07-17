package uz.banking.finance.controller.vm;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequest {

    @NotNull
    private String oldPassword;
    @NotNull
    @Size(min = 4, max = 100)
    private String password;

    private String device;
}
