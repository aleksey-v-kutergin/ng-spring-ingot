package ru.rusquant.ngingot.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;
import ru.rusquant.ngingot.utils.DateDeserializer;
import ru.rusquant.ngingot.utils.DateSerializer;

import javax.persistence.*;
import java.util.Date;

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

    @Column(name = "role_type")
    private Integer roleType;

    @Column(name = "registration_date")
    @DateTimeFormat(pattern = DATE_PATTERN)
    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
    private Date registrationDate;

    @Column(name = "is_password_expired")
    private Boolean isPasswordExpired;

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

    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Boolean getPasswordExpired() {
        return isPasswordExpired;
    }

    public void setPasswordExpired(Boolean passwordExpired) {
        isPasswordExpired = passwordExpired;
    }
}
