package uz.banking.finance.constants;

public interface Constants {
    String REQUEST_ID = "request_id";
    String INTERNAL_REQUEST_ID = "request.id";

    String AUTHORITIES_KEY = "roles";
    String AUTHORIZATION_HEADER = "Authorization";
    String ALGORITHM_SECRET = "TEMPLATE_$ECR1T";

    interface ROLE {
        String ADMIN = "ROLE_ADMIN";
        String USER = "ROLE_USER";
    }
}
