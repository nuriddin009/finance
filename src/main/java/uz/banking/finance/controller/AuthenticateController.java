package uz.banking.finance.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.banking.finance.config.security.jwt.JWTToken;
import uz.banking.finance.config.security.jwt.TokenProvider;
import uz.banking.finance.constants.ResponseStatus;
import uz.banking.finance.controller.vm.LoginVM;
import uz.banking.finance.dto.Response;
import uz.banking.finance.exception.ApiException;
import uz.banking.finance.exception.ExceptionHandler;
import uz.banking.finance.service.AuthService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AuthenticateController implements ExceptionHandler {

    private final TokenProvider tokenProvider;
    private final AuthService authService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping("/authenticate")
    public Response<JWTToken> authorize(@RequestBody LoginVM loginVM, HttpServletRequest request) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = tokenProvider.createToken(authentication, request);
            return Response.<JWTToken>ok().setPayload(new JWTToken(jwt));
        } catch (Exception e) {
            throw new ApiException("Incorrect username or password", ResponseStatus.UNAUTHORIZED);
        }
    }
}
