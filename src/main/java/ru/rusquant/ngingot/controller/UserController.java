package ru.rusquant.ngingot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.rusquant.ngingot.domain.User;
import ru.rusquant.ngingot.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController // Добавляет аннтоцию @ResponseBody ко всем методам констроллера и др.
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        return userService.getAll();
    }

    @RequestMapping(value = "/{userId}")
    public User getUser(@PathVariable(value = "userId") Long userId) {
        return userService.getUser(userId);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public User create(HttpServletRequest request, @RequestBody User user) {
        return userService.create(user);
    }

}
