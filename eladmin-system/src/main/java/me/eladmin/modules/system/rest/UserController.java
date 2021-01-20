package me.eladmin.modules.system.rest;

import lombok.RequiredArgsConstructor;
import me.eladmin.modules.system.domain.User;
import me.eladmin.modules.system.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("list")
    public List<User> list() {
        List<User> users = userService.list();
        return users;
    }
}
