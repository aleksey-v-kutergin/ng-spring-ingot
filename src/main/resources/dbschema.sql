CREATE SEQUENCE ingot_id_generator INCREMENT BY 1 MINVALUE 1 NO MAXVALUE;

/* Функциональные модули приложения */
CREATE TABLE app_modules (
  id      INTEGER DEFAULT nextval('ingot_id_generator') PRIMARY KEY,
  ident   VARCHAR(255) NOT NULL,
  name    VARCHAR(1024) NOT NULL,
  icon    VARCHAR(255) NOT NULL,
  parent  INTEGER,
  ord     INTEGER
);


/* Пользовательские аккаунты */
CREATE TABLE ingot_users (
  id                            INTEGER DEFAULT nextval('ingot_id_generator') PRIMARY KEY,
  name                          VARCHAR(1024) NOT NULL,
  login                         VARCHAR(255) NOT NULL,
  password                      VARCHAR(255) NOT NULL,
  email                         VARCHAR(255) NOT NULL,
  registration_date             DATE NOT NULL DEFAULT CURRENT_DATE,
  account_lifetime              NUMBER NOT NULL,
  last_password_reset_date      DATE NOT NULL DEFAULT CURRENT_DATE,
  password_life_time            NUMBER NOT NULL,
  locked                        BOOLEAN NOT NULL DEFAULT FALSE
);


/* Доступные роли пользователя а приложении */
CREATE TABLE authorities (
  id    INTEGER DEFAULT nextval('ingot_id_generator') PRIMARY KEY,
  role  VARCHAR(255) NOT NULL
);


/*
    Сопоставление пользователей и ролей.
    Мощность связи - многие ко многим
*/
CREATE TABLE user_authorities (
  id        INTEGER DEFAULT nextval('ingot_id_generator') PRIMARY KEY,
  user_id   INTEGER NOT NULL,
  role_id   INTEGER NOT NULL
);
ALTER TABLE user_authorities ADD CONSTRAINT fk_user_authorities_ingot_users FOREIGN KEY (user_id) REFERENCES ingot_users(id);
ALTER TABLE user_authorities ADD CONSTRAINT fk_user_authorities_authorities FOREIGN KEY (role_id) REFERENCES authorities(id);