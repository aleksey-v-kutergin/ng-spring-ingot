/* Populate ingot_users table */
INSERT INTO ingot_users(name, login, password, email, role_type)
VALUES ('Aleksey Kutergin', 'kiba', 'qwerty', 'aleksey.v.kutergin@gmail.com', 3);

INSERT INTO ingot_users(name, login, password, email, role_type)
VALUES ('Admin', 'admin', 'admin', 'admin@gmail.com', 1);

INSERT INTO ingot_users(name, login, password, email, role_type)
VALUES ('filler', 'filler', 'filler', 'filler@gmail.com', 2);

/* Populate app_modules table */

/* Main modules */
INSERT INTO app_modules(id, ident, name, icon, parent, ord)
VALUES (1, 'ADMIN_MODULE', 'Administration', 'assets/img/admin.svg', 0, 1);

INSERT INTO app_modules(id, ident, name, icon, parent, ord)
VALUES (2, 'GEO_MODULE', 'Google maps tools', 'assets/img/geo.svg', 0, 2);

INSERT INTO app_modules(id, ident, name, icon, parent, ord)
VALUES (3, 'ANALYTICS_MODULE', 'Analytics', 'assets/img/dashboard.svg', 0, 3);

/* Submodules for Admin module */
INSERT INTO app_modules(id, ident, name, icon, parent, ord)
VALUES (4, 'USERS_MODULE', 'Users', 'assets/img/user_accounts.svg', 1, 1);

INSERT INTO app_modules(id, ident, name, icon, parent, ord)
VALUES (5, 'REGISTER_REQUESTS_MODULE', 'Registration Requests', 'assets/img/verified_user.svg', 1, 2);