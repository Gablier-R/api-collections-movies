package br.com.collec.api.controller;

import br.com.collec.domain.entity.User;
import br.com.collec.infra.security.TokenService;
import br.com.collec.api.payload.authentication.AuthenticationDTO;
import br.com.collec.api.payload.authentication.LoginResponseDTO;
import br.com.collec.api.payload.user.OnlyUserResponseDTO;
import br.com.collec.api.payload.user.UserDataDTO;
import br.com.collec.domain.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    UserService userService;

    @Autowired
    TokenService tokenService;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login (@RequestBody @Valid AuthenticationDTO data){
        var userName = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = authenticationManager.authenticate(userName);
        var token = tokenService.generateToken( (User) auth.getPrincipal());


        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<OnlyUserResponseDTO> createUser(@RequestBody @Valid UserDataDTO user){
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.OK);
    }
}
