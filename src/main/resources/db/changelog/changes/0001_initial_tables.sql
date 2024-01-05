CREATE SEQUENCE app_user_sequence START 1 INCREMENT 1;
CREATE SEQUENCE organization_sequence START 1 INCREMENT 1;
CREATE SEQUENCE template_sequence START 1 INCREMENT 1;
CREATE SEQUENCE placeholder_sequence START 1 INCREMENT 1;
CREATE SEQUENCE placeholder_value_sequence START 1 INCREMENT 1;

CREATE TABLE app_user (
id BIGINT PRIMARY KEY DEFAULT nextval('app_user_sequence'),
email VARCHAR(256) NOT NULL,
password VARCHAR(128) NOT NULL,
role VARCHAR(32)
);

CREATE INDEX idx_app_user_email ON app_user(email);

CREATE TABLE organization (
id BIGINT PRIMARY KEY DEFAULT nextval('organization_sequence'),
name VARCHAR(256) NOT NULL,
description VARCHAR(256)
);

CREATE INDEX idx_organization_name ON organization(name);

CREATE TABLE template (
id BIGINT PRIMARY KEY DEFAULT nextval('template_sequence'),
name VARCHAR(64) NOT NULL,
file_path TEXT NOT NULL,
is_active BOOLEAN DEFAULT FALSE,
language VARCHAR(32),
owner_id BIGINT,
organization_id BIGINT,
FOREIGN KEY (owner_id) REFERENCES app_user(id),
FOREIGN KEY (organization_id) REFERENCES organization(id)
);

CREATE INDEX idx_template_name ON template(name);

CREATE TABLE placeholder (
id BIGINT PRIMARY KEY DEFAULT nextval('placeholder_sequence'),
name VARCHAR(128) NOT NULL,
description VARCHAR(128),
type VARCHAR(32) DEFAULT 'TEXT',
template_id BIGINT,
FOREIGN KEY (template_id) REFERENCES template(id)
);

CREATE INDEX idx_placeholder_name ON placeholder(name);

CREATE TABLE placeholder_value (
id BIGINT PRIMARY KEY DEFAULT nextval('placeholder_value_sequence'),
value TEXT NOT NULL,
sequence_number INTEGER,
placeholder_id BIGINT,
FOREIGN KEY (placeholder_id) REFERENCES placeholder(id)
);

CREATE INDEX idx_placeholder_value_value ON placeholder_value(value);

CREATE TABLE user_organization (
user_id BIGINT,
organization_id BIGINT,
PRIMARY KEY (user_id, organization_id),
FOREIGN KEY (user_id) REFERENCES app_user(id),
FOREIGN KEY (organization_id) REFERENCES organization(id)
);

CREATE TABLE organization_template (
organization_id BIGINT,
template_id BIGINT,
PRIMARY KEY (organization_id, template_id),
FOREIGN KEY (organization_id) REFERENCES organization(id),
FOREIGN KEY (template_id) REFERENCES template(id)
);

CREATE TABLE user_template (
user_id BIGINT,
template_id BIGINT,
PRIMARY KEY (user_id, template_id),
FOREIGN KEY (user_id) REFERENCES app_user(id),
FOREIGN KEY (template_id) REFERENCES template(id)
);
