insert into t_user(first_name, username, password, created_by, created_at) values
       ('Admin', 'admin', '$2a$10$GA8sES31GgwdquNf6PGLSOVTJ7XGXM8JznpozB54EsNASfwBeaP5q', 'system', now()),
       ('User', 'user', '$2a$10$W2SRxvtOp.MT958REkOLLO9JVViQU3RFRvD5dlz60pWFXb7rVlscy', 'system', now());

insert into user_roles(user_id, role) values
                                            ((select id from t_user where username = 'admin' limit 1), 'ROLE_ADMIN'),
                                            ((select id from t_user where username = 'user' limit 1), 'ROLE_USER');
