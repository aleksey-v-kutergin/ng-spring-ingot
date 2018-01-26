package ru.rusquant.ngingot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.rusquant.ngingot.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByLogin(String login);

    @Modifying
    @Query("update User u set u.password = ?2 where u.id = ?1")
    void updatePassword(Long id, String password);

}
