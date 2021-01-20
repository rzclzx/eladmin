package me.eladmin.modules.system.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.eladmin.modules.system.domain.SysUserEntity;
import me.eladmin.modules.system.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "系统：用户管理")
@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ApiOperation("用户列表")
    @GetMapping("list")
    public List<SysUserEntity> list() {
        List<SysUserEntity> users = userService.list();
        return users;
    }
}
