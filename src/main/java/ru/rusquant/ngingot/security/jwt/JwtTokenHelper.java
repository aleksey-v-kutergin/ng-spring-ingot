package ru.rusquant.ngingot.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/** Сервис для выполнения операций с JWT-токеном **/
@Component
public class JwtTokenHelper {

    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.token.expiration}")
    private Long tokenLifetimeInMinutes;

    private Clock clock = DefaultClock.INSTANCE;

    /**
     * Кэш результатов разбора токена.
     * Разбор токена - затратная операция, поэтому для оптимизации
     * жулательно кешировать результаты разбора токена хотябы до момента получения
     * нового токена (когда в метод extractClaims() будет передан токен,
     * отличный от текущего токена в кеше). Можно организовать кеширование на
     * время жизни токена.
     **/
    private Map<String, Claims> claimsCache = new HashMap<>();

    public String generateToken(UserDetails userDetails) {
        final Date dateOfCreation = this.clock.now();
        final Date expirationDate = this.calculateExpirationDate(dateOfCreation);
        final JwtUserDetails jwtUserDetails = (JwtUserDetails) userDetails;
        return Jwts.builder()
                .setSubject(jwtUserDetails.getUsername()) // Имя пользователя
                .setClaims(jwtUserDetails.toClaims()) // Содержимое токена. Мапа ключ-значение по-сути
                .setIssuedAt(dateOfCreation) // Дата создания токена (типа когда токен был запрошен)
                .setExpiration(expirationDate) // Дата истечения токена
                .signWith(SignatureAlgorithm.HS512, this.secret) // Алгоритм шифрования токена и подпись
                .compact();
    }


    /** Проверка валидности токена **/
    public Boolean validateToken(String token, UserDetails userDetails) throws UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
        if(this.isTokenExpired(token)) return false;
        JwtUserDetails jwtUserDetails = (JwtUserDetails) userDetails;
        Claims claims = this.extractClaims(token);
        JwtUserDetails detailsFromToken = JwtUserDetails.fromClaims(claims);

        String targetUsername = jwtUserDetails.getUsername();
        String usernameFromToken = detailsFromToken.getUsername();

        return usernameFromToken.equals(targetUsername)
                && !isCreatedBeforeLastPasswordReset(claims.getIssuedAt(), jwtUserDetails.getLastPasswordResetDate());
    }


    /** Обновление токена **/
    public String refreshToken(String token) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
        final Date dateOfCreation = this.clock.now();
        final Date expirationDate = this.calculateExpirationDate(dateOfCreation);

        Claims claims = this.extractClaims(token);
        claims.setIssuedAt(dateOfCreation);
        claims.setExpiration(expirationDate);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, this.secret)
                .compact();
    }


    /** Можно ли обновить токен **/
    public Boolean canTokenBeRefreshed(String token, Date lastPasswordResetDate) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
        final Claims claims = this.extractClaims(token);
        return !isCreatedBeforeLastPasswordReset(claims.getIssuedAt(), lastPasswordResetDate)
                && (!isTokenExpired(token) || ignoreTokenExpiration(token));
    }


    public UserDetails getUserDetails(String token) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
        final Claims claims = this.extractClaims(token);
        return JwtUserDetails.fromClaims(claims);
    }


    /** Достаем содержимое токена **/
    private Claims extractClaims(String token) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
        if (this.claimsCache.containsKey(token)) {
            return this.claimsCache.get(token);
        } else {
            final Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
            this.claimsCache.clear();
            this.claimsCache.put(token, claims);
            return claims;
        }
    }

    /** Истекло ли время жизни токена **/
    public Boolean isTokenExpired(String token)throws UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
        try {
            final Claims claims = this.extractClaims(token);
            return claims.getExpiration().before(this.clock.now());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    /**
     *   Был ли токен создан перед тем как юзер сменил пароль
     *   На самом деле с этой проверкой возникает интересный сабж.
     *   Проверять это на основе даты последней смены пароля из токена нельзя по объективным причинам :)
     *   Можно только сопоставлять с датой последней смены пароля из БД.
     *   Но сервер ресурсов не должен лазить в БД (часть БД), связанную с аунтификацией.
     *
     *   Поэтому чтобы избежать этих заморочек:
     *   1. При выдаче токена проверяется протух\не протух пароль учетки, если протух то выдать токен
     *      мы не имеем права. Пользователь отправляется на форму смены пароля и меняет там пароль.
     *      После чего ему показывается уведомление об успешности операции и предложение перейти на форму логина
     *      для того чтобы зайти в систему
     *
     *   2. Пользователь работает в системе. Действие его пароля подходит к концу, его уведомляют об этом
     *      тем или иным способом. Пользователь переходит на форму смены пароля. Меняет пароль.
     *      Система уведомляет его об успехе \ не успехе операции. В случае успеха предлагает перейти
     *      на форму логина для захода в систему
     *
     *   3. Пользователь проигнорировал уведомление об истечении срока действия пароля. Работает до "упора".
     *      При выолнении очередного http-запроса к защищенным ресурсам токен не проходит проверку и
     *      пользователя выбрасывает на форму смены пароля.
     **/
    public Boolean isCreatedBeforeLastPasswordReset(Date dateOfCreation, Date dateOfLastPasswordReset) {
        return (dateOfLastPasswordReset != null && dateOfCreation.before(dateOfLastPasswordReset));
    }

    /**
     * Игнорирование истечения времени жизни токена при необходимости
     **/
    private Boolean ignoreTokenExpiration(String token) {
        return false;
    }

    /**
     * Вычисление даты, задающей время жизни токена
     **/
    private Date calculateExpirationDate(Date dateOfCreation) {
        return new Date(dateOfCreation.getTime() + this.tokenLifetimeInMinutes * 60 * 1000);
    }

}
