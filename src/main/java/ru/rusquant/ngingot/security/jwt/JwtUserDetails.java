package ru.rusquant.ngingot.security.jwt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.rusquant.ngingot.domain.User;
import ru.rusquant.ngingot.security.utils.SimpleGrantedAuthorityListDeserializer;

import java.util.*;

/** Контент токена - информация, значимая для процесса аунтификации **/
@JsonIgnoreProperties(ignoreUnknown = true)
public class JwtUserDetails implements UserDetails {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    @JsonProperty("email")
    private String email;

    @JsonProperty("locked")
    private Boolean locked;

    @JsonProperty("registrationDate")
    private Date registrationDate;

    @JsonProperty("accountLifetime")
    private Long accountLifetime;

    @JsonProperty("lastPasswordResetDate")
    private Date lastPasswordResetDate;

    @JsonProperty("passwordLifetime")
    private Long passwordLifetime;

    @JsonProperty("authorities")
    @JsonDeserialize(using = SimpleGrantedAuthorityListDeserializer.class)
    private Collection<? extends GrantedAuthority> authorities;

    public JwtUserDetails() {}

    public JwtUserDetails(User user, Collection<? extends GrantedAuthority> authorities) {
        this.id = user.getId();
        this.username = user.getLogin();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.locked = user.isLocked();
        this.registrationDate = user.getRegistrationDate();
        this.accountLifetime = user.getAccountLifetime();
        this.lastPasswordResetDate = user.getLastPasswordResetDate();
        this.passwordLifetime = user.getPasswordLifetime();
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        final Date currentDate = Calendar.getInstance().getTime();
        long  diff = currentDate.getTime() - this.registrationDate.getTime();
        return diff < this.accountLifetime;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        final Date currentDate = Calendar.getInstance().getTime();
        long diff = currentDate.getTime() - this.lastPasswordResetDate.getTime();
        return diff < this.passwordLifetime;
    }

    @Override
    public boolean isEnabled() {
        return this.locked;
    }

    public Map<String, Object> toClaims() {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(this, Map.class);
    }

    public static JwtUserDetails fromClaims(Map<String, Object> claims) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(claims, JwtUserDetails.class);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Long getAccountLifetime() {
        return accountLifetime;
    }

    public void setAccountLifetime(Long accountLifetime) {
        this.accountLifetime = accountLifetime;
    }

    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Date lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    public Long getPasswordLifetime() {
        return passwordLifetime;
    }

    public void setPasswordLifetime(Long passwordLifetime) {
        this.passwordLifetime = passwordLifetime;
    }

}
