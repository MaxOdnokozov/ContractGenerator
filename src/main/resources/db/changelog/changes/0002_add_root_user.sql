--liquibase formatted sql

--changeset Maksym_Odnokozov:add_root_user
INSERT INTO app_user (email, password, role) VALUES ('${rootEmail}', '${rootPasswordHash}', 'ADMIN');
--rollback DELETE FROM app_user WHERE email = '${myapp.root.email}';