INSERT INTO "user"(username, password, deleted)
VALUES ('admin@example.com', '$2a$10$0cA2xAj9Y/r1IFxwdIpZZuvXVHIZXXK/YIZaTE37Tpb42FFHpzaWa', false),
       ('user@example.com', '$2a$10$0cA2xAj9Y/r1IFxwdIpZZuvXVHIZXXK/YIZaTE37Tpb42FFHpzaWa', false);

INSERT INTO role(role_name) VALUES ('ROLE_ADMIN');
INSERT INTO role(role_name) VALUES ('ROLE_USER');

INSERT INTO user_role(user_id, role_id) VALUES (1, 1);
INSERT INTO user_role(user_id, role_id) VALUES (2, 2);

INSERT INTO customer(name, surname,created_by_user_id, deleted)
VALUES ('Link', 'Floyd', 1, false);