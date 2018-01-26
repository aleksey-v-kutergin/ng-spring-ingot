package ru.rusquant.ngingot.service;

import ru.rusquant.ngingot.domain.User;

import java.util.List;

public interface UserService {

    User create(User user);

    void delete(User user);

    User update(User user);

    List<User> getAll();

    User getUser(Long id);

    User findByLogin(String login);

    void updatePassword(Long id, String password);

}
