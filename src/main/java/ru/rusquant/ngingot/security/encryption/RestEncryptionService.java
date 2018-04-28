package ru.rusquant.ngingot.security.encryption;

/**
 *   Сервис содержит логику для шифровки\дешифровки паролей при работе приложения.
 **/
public interface RestEncryptionService {
    String getSaltHash(String login);
    String getPasswordHash(String login, String passwordAndSalt);
    Boolean isSaltGenerated(String login);
}
