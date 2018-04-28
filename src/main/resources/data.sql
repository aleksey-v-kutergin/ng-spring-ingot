/* Populate ingot_users table */
INSERT INTO ingot_users(name, login, password, email, account_lifetime, password_life_time)
VALUES ('Aleksey Kutergin', 'kiba', 'qwerty', 'aleksey.v.kutergin@gmail.com', 160 * 60 * 60 * 1000, 160 * 60 * 60 * 1000);

INSERT INTO ingot_users(name, login, password, email, account_lifetime, password_life_time)
VALUES ('Admin', 'admin', 'admin', 'admin@gmail.com', 160 * 60 * 60 * 1000, 160 * 60 * 60 * 1000);

INSERT INTO ingot_users(name, login, password, email, account_lifetime, password_life_time)
VALUES ('filler', 'filler', 'filler', 'filler@gmail.com', 160 * 60 * 60 * 1000, 160 * 60 * 60 * 1000);

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

/* Populate authorities table */
INSERT INTO authorities(role) VALUES ('ROLE_ADMIN');
INSERT INTO authorities(role) VALUES ('ROLE_GEO_FILLER');
INSERT INTO authorities(role) VALUES ('ROLE_ACCOUNT_CHECKER');

/* Populate user_authorities table */
DROP ALIAS IF EXISTS populate_user_authorities;
CREATE ALIAS populate_user_authorities AS '
  import java.lang.String;
  import java.lang.Long;
  import java.sql.Connection;
  import java.sql.PreparedStatement;
  import java.sql.ResultSet;
  import java.sql.SQLException;
@CODE
  void populateUserAuthorities(final Connection connection) throws SQLException {
      String sql = "SELECT id, email FROM ingot_users";
      PreparedStatement sqlStatement = connection.prepareStatement(sql);

      ResultSet usersCursor = sqlStatement.executeQuery();
      String userEmail;
      Long userId;
      String[] roles;
      while(usersCursor.next()) {
        userEmail = usersCursor.getString(2);
        userId = usersCursor.getLong(1);
        if("aleksey.v.kutergin@gmail.com".equals(userEmail)) {
            sql = "SELECT id FROM authorities where role=''ROLE_ADMIN'' OR role=''ROLE_GEO_FILLER'' OR role=''ROLE_ACCOUNT_CHECKER''";
            sqlStatement = connection.prepareStatement(sql);

            ResultSet roleCursor = sqlStatement.executeQuery();
            Long roleId;
            while(roleCursor.next()) {
                roleId = roleCursor.getLong(1);
                sql = "INSERT INTO user_authorities (user_id, role_id) VALUES (" + userId + ", " + roleId + ")";
                sqlStatement = connection.prepareStatement(sql);
                sqlStatement.executeUpdate();
            }
            roleCursor.close();
        } else if("admin@gmail.com".equals(userEmail)) {
            sql = "SELECT id FROM authorities where role=''ROLE_ADMIN'' OR role=''ROLE_ACCOUNT_CHECKER''";
            sqlStatement = connection.prepareStatement(sql);

            ResultSet roleCursor = sqlStatement.executeQuery();
            Long roleId;
            while(roleCursor.next()) {
                roleId = roleCursor.getLong(1);
                sql = "INSERT INTO user_authorities (user_id, role_id) VALUES (" + userId + ", " + roleId + ")";
                sqlStatement = connection.prepareStatement(sql);
                sqlStatement.executeUpdate();
            }
            roleCursor.close();
        } else if("filler@gmail.com".equals(userEmail)) {
            sql = "SELECT id FROM authorities where role=''ROLE_GEO_FILLER''";
            sqlStatement = connection.prepareStatement(sql);

            ResultSet roleCursor = sqlStatement.executeQuery();
            roleCursor.next();
            Long roleId = roleCursor.getLong(1);
            roleCursor.close();

            sql = "INSERT INTO user_authorities (user_id, role_id) VALUES (" + userId + ", " + roleId + ")";
            sqlStatement = connection.prepareStatement(sql);
            sqlStatement.executeUpdate();
        }
      }
      usersCursor.close();
  }
';

CALL populate_user_authorities();