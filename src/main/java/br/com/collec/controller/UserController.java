package br.com.collec.controller;

import br.com.collec.controller.payload.CreateUserDTO;
import br.com.collec.controller.payload.ResponseUserDTO;
import br.com.collec.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/user")
public record UserController(UserService userService) {

    @PostMapping("/create")
    public ResponseUserDTO createUser(@RequestBody @Valid CreateUserDTO user){
        return userService.saveUser(user);
    }

    @RequestMapping
    public ResponseEntity<List<ResponseUserDTO>> getAllUsers(){
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

}
