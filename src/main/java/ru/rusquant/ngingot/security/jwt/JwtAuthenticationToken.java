package ru.rusquant.ngingot.security.jwt;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.util.Collection;


/**
 *    По аналогии с классом UsernamePasswordAuthenticationToken, создаем
 *    имлементацию интерфейса Authentication для jwt-аунтификации.
 *    Это не токен сам по себе, а данные, которые должны быть переданы
 *    в AuthenticationProvider для проведения процесса аунтификации.
 **/
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private String token;
    private Object principal;
    private Object credentials;

    /**
     *   Этот конструктор должен использоваться кодом, который хочет создать
     *   <code>JwtAuthenticationToken</code> для передачи в <code>AuthenticationManager</code>
     *   для выполнения процесса аунтификации. Такой токен не будет доверенным, так как
     *   (@link #isAuthenticated()) вернет <code>false</code>.
     **/
    public JwtAuthenticationToken(String token) {
        super(null);
        this.token = token;
        super.setAuthenticated(false);
    }

    /**
     *   Конструктор должен использоваться только в реализациях
     *   <code>AuthenticationManager</code> или <code>AuthenticationProvider</code>
     *   для создания доверенного токена (i.e. {@link #isAuthenticated()} = <code>true</code>)
     *   как результатта успешной аутектификации.
     **/
    public JwtAuthenticationToken(Object principal, Object credentials,
                                  Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true);
    }

    /** Возвращаем password из токена **/
    @Override
    public Object getCredentials() {
        return this.principal;
    }

    /** Возвращаем username из токена **/
    @Override
    public Object getPrincipal() {
        return this.credentials;
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.credentials = null;
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        // Создать доверенный токен можно только через соответствующий
        // конструктор
        if(authenticated) {
           String msg = "Cannot set this token to trusted - use constructor, ";
           msg += "which takes a GrantedAuthority list instead.";
           throw new IllegalArgumentException(msg);
        }
        super.setAuthenticated(false);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
