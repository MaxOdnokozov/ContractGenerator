--liquibase formatted sql

--changeset Maksym_Odnokozov:initial_tables
-- Sequence for generating primary keys
CREATE SEQUENCE app_user_sequence START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE organization_sequence START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE template_sequence START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE placeholder_sequence START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE placeholder_value_sequence START WITH 1 INCREMENT BY 1;

-- Table for organization
CREATE TABLE organization (
    id BIGINT PRIMARY KEY DEFAULT nextval('organization_sequence'),
    name VARCHAR(256) NOT NULL UNIQUE,
    description VARCHAR(256)
);

-- Table for app_user with a foreign key to organization
CREATE TABLE app_user (
    id BIGINT PRIMARY KEY DEFAULT nextval('app_user_sequence'),
    email VARCHAR(256) NOT NULL UNIQUE,
    password VARCHAR(128) NOT NULL,
    role VARCHAR(32),
    organization_id BIGINT,
    FOREIGN KEY (organization_id) REFERENCES organization(id)
);

-- Table for template with a foreign key to organization and app_user (as owner)
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

-- Table for placeholder with a foreign key to template
CREATE TABLE placeholder (
    id BIGINT PRIMARY KEY DEFAULT nextval('placeholder_sequence'),
    name VARCHAR(128) NOT NULL,
    description VARCHAR(128),
    type VARCHAR(32) DEFAULT 'TEXT',
    template_id BIGINT,
    FOREIGN KEY (template_id) REFERENCES template(id)
);

-- Table for placeholder_value with a foreign key to placeholder
CREATE TABLE placeholder_value (
    id BIGINT PRIMARY KEY DEFAULT nextval('placeholder_value_sequence'),
    value TEXT NOT NULL,
    sequence_number INTEGER,
    placeholder_id BIGINT,
    FOREIGN KEY (placeholder_id) REFERENCES placeholder(id)
);

-- Indices for app_user, organization, template, and placeholder
CREATE INDEX idx_app_user_email ON app_user(email);
CREATE INDEX idx_organization_name ON organization(name);
CREATE INDEX idx_template_name ON template(name);
CREATE INDEX idx_placeholder_name ON placeholder(name);
CREATE INDEX idx_placeholder_value_value ON placeholder_value(value);