package ru.rusquant.ngingot.domain;

import com.sun.istack.internal.NotNull;
import ru.rusquant.ngingot.domain.types.AuthorityType;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "authorities")
public class Authority {

    @Id
    @SequenceGenerator(name = "ingot_id_gen", sequenceName = "ingot_id_generator", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ingot_id_gen")
    @Column(name = "id")
    private Long id;

    @Column(name = "role")
    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthorityType role;

    @ManyToMany(mappedBy = "authorities", fetch = FetchType.EAGER)
    private List<User> users;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AuthorityType getRole() {
        return role;
    }

    public void setRole(AuthorityType role) {
        this.role = role;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

}
