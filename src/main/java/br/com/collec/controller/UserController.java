package br.com.collec.controller;

import br.com.collec.entity.User;
import br.com.collec.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
public record UserController(UserService userService) {

    @PostMapping("/post")
    public User create(@RequestBody User user){
        return userService.create(user);
    }

}
