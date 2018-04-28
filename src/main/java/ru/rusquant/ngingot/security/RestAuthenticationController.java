package ru.rusquant.ngingot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import ru.rusquant.ngingot.domain.User;
import ru.rusquant.ngingot.security.jwt.JwtAuthenticationToken;
import ru.rusquant.ngingot.security.encryption.RestEncryptionService;
import ru.rusquant.ngingot.service.UserService;


/**
 *    Контроллер обечпечивает процесс аутификации в REST-API.
 *    В приложении используется двухступенчатый процесс аунтификации для того, чтобы
 *    исключить передачу паролей в открытом виде.
 **/
@RestController
@RequestMapping("/api/auth")
public class RestAuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private RestEncryptionService encryptionService;

    @Autowired
    private RestAuthenticationProvider restAuthenticationProvider;

    /**
     *    Метод возвращает на клиент шифрованную соль.
     *    Клиент на своей стороне дешифрует соль и солит ей хэш пароля.
     *    Чтобы не выполнять лишних телодвижений логично на этом этапе прокинуть логин,
     *    и отсеять попытки зайти с несуществующим пользователем.
     **/
    @RequestMapping(value = "/salt", method = RequestMethod.GET)
    public ResponseEntity<?> getSecret(@RequestParam(name = "login") String login) {
        final User user = this.userService.findByLogin(login);
        if(user == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("User with login: " + login + " does not exist!");
        }
        return ResponseEntity.ok(this.encryptionService.getSaltHash(login));
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody RestAuthRequest authRequest) {
        try {
            UsernamePasswordAuthenticationToken loginAndPassword
                    = new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());
            JwtAuthenticationToken jwtAuthenticationToken =
                    (JwtAuthenticationToken) this.restAuthenticationProvider.authenticate(loginAndPassword);
            return ResponseEntity.ok(jwtAuthenticationToken.getToken());
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }


    /** Запрос на обновление токена **/
    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    public String refresh() {
        return "refreshed token";
    }
}
