package ru.rusquant.ngingot.security.encryption.impl;

import org.springframework.stereotype.Service;
import ru.rusquant.ngingot.security.encryption.RestEncryptionService;

import java.util.HashMap;
import java.util.Map;

/** Реазиация логики шифрования \ дешифрования паролей в приложении. **/
@Service
public class RestEncryptionServiceImpl implements RestEncryptionService {

    /**
     *    В чем прикол.
     *    Процесс антификации состоит из двух этапов:
     *    1. На первом этапе мы генерируем соль и отдаем ее на клиент
     *    2. Мы должны при помощи сгенерированной соли дешифровать пароль
     *
     *    Между этими этапами сгенерированную соль нужно где-то хранить,
     *    чтобы не потерять ключ шифрования.
     **/
    private Map<String, String> saltShaker = new HashMap<>();

    @Override
    public String getSaltHash(String login) {
        final String salt = this.generateSalt();
        this.saltShaker.put(login, salt);
        // Должна быть реализована стратегия вычисления хэшей
        return salt;
    }

    @Override
    public String getPasswordHash(String login, String passwordAndSalt) {
        final String salt = this.saltShaker.get(login);
        int saltStartIndex = passwordAndSalt.indexOf(salt);
        this.saltShaker.remove(login);
        return passwordAndSalt.substring(0, saltStartIndex);
    }

    @Override
    public Boolean isSaltGenerated(String login) {
        return this.saltShaker.containsKey(login);
    }

    /** Алгоритм генерирования соли **/
    private String generateSalt() {
        return "salt_hash";
    }
}
