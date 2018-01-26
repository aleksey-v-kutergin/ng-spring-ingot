CREATE SEQUENCE ingot_id_generator INCREMENT BY 1 MINVALUE 1 NO MAXVALUE;

CREATE TABLE ingot_users (
  id                    INTEGER DEFAULT nextval('ingot_id_generator') PRIMARY KEY,
  name                  VARCHAR(1024) NOT NULL,
  login                 VARCHAR(255) NOT NULL,
  password              VARCHAR(255) NOT NULL,
  email                 VARCHAR(255) NOT NULL,
  role_type             INTEGER NOT NULL,
  registration_date     DATE NOT NULL DEFAULT CURRENT_DATE,
  is_password_expired   BOOLEAN NOT NULL DEFAULT FALSE
);