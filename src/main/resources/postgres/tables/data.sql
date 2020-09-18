INSERT INTO "user"(username, password, enabled)
VALUES ('admin@example.com', '1', true),
       ('user@example.com', '2', true);

INSERT INTO role(role_name) VALUES ('ROLE_ADMIN');
INSERT INTO role(role_name) VALUES ('ROLE_USER');

INSERT INTO user_role(user_id, role_id) VALUES (1, 1);
INSERT INTO user_role(user_id, role_id) VALUES (2, 2);