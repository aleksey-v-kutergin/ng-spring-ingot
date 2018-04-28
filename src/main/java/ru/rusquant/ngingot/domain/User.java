package ru.rusquant.ngingot.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;
import ru.rusquant.ngingot.utils.DateDeserializer;
import ru.rusquant.ngingot.utils.DateSerializer;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static ru.rusquant.ngingot.utils.DateSerializer.DATE_PATTERN;

@Entity
@Table(name = "ingot_users")
public class User {
    @Id
    @SequenceGenerator(name = "ingot_id_gen", sequenceName = "ingot_id_generator", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ingot_id_gen")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "registration_date")
    @DateTimeFormat(pattern = DATE_PATTERN)
    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
    private Date registrationDate;

    @Column(name = "account_lifetime")
    private Long accountLifetime;

    @Column(name = "last_password_reset_date")
    @DateTimeFormat(pattern = DATE_PATTERN)
    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
    private Date lastPasswordResetDate;

    @Column(name = "password_life_time")
    private Long passwordLifetime;

    @Column(name = "locked")
    private Boolean locked;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_authorities",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Authority> authorities;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
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

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
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

    public Boolean isLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Long getAccountLifetime() {
        return accountLifetime;
    }

    public void setAccountLifetime(Long accountLifetime) {
        this.accountLifetime = accountLifetime;
    }

    public Boolean getLocked() {
        return locked;
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    public Boolean isPasswordExpired() {
        final Date currentDate = Calendar.getInstance().getTime();
        long diff = currentDate.getTime() - this.registrationDate.getTime();
        return diff >= this.passwordLifetime;
    }

    public Boolean isAccountExpired() {
        final Date currentDate = Calendar.getInstance().getTime();
        long diff = currentDate.getTime() - this.registrationDate.getTime();
        return diff >= this.accountLifetime;
    }
}
