package me.eladmin.modules.system.service.impl;

import lombok.RequiredArgsConstructor;
import me.eladmin.modules.system.domain.User;
import me.eladmin.modules.system.repository.UserRepository;
import me.eladmin.modules.system.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> list() {
        List<User> users = userRepository.findAll();
        return users;
    }
}
