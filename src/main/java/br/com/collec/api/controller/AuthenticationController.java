package br.com.collec.api.controller;

import br.com.collec.api.payload.AllResponseDTO;
import br.com.collec.api.payload.authentication.AuthenticationDTO;
import br.com.collec.api.payload.user.OnlyUserResponseDTO;
import br.com.collec.api.payload.user.UserDataDTO;
import br.com.collec.domain.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login (@RequestBody @Valid AuthenticationDTO data){

        return new ResponseEntity<>(userService.authenticate(data), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<OnlyUserResponseDTO> createUser(@RequestBody @Valid UserDataDTO user){
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.OK);
    }
}
