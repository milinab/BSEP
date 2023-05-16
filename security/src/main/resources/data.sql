INSERT INTO app_user(first_name, last_name, email, password, app_user_role, locked, enabled) VALUES ('Ana', 'Lakic', 'ana@example.com', '123', 'HR', false, false);

INSERT INTO confirmation_token(expires_at, token, app_user_id, confirmed_at, created_at) VALUES (null, null, 1, null, null);

INSERT INTO project(name) VALUES ('BSEP');

INSERT INTO work(worker_id, project_id) VALUES (1, 1);