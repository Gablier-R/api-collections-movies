package br.com.collec.api.controller;

import br.com.collec.api.payload.AllResponseDTO;
import br.com.collec.api.payload.authentication.AuthenticationDTO;
import br.com.collec.api.payload.user.UserDataDTO;
import br.com.collec.api.payload.user.OnlyUserResponseDTO;
import br.com.collec.domain.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static br.com.collec.utils.Constants.DEFAULT_PAGE_NUMBER;
import static br.com.collec.utils.Constants.DEFAULT_PAGE_SIZE;

@RestController
@RequestMapping("/user")
public class UserController {

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

    @GetMapping("/{id}")
    public ResponseEntity<OnlyUserResponseDTO>  getUserById(@PathVariable String id){
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<AllResponseDTO> getAllResource(@RequestParam( defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                                         @RequestParam( defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize){
        return new ResponseEntity<>(userService.queryAllUsersPageable(pageNo, pageSize), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OnlyUserResponseDTO> updateUserById(@PathVariable String id, @RequestBody @Valid UserDataDTO userUpdateDTO){
        return new ResponseEntity<>(userService.updateUser(id, userUpdateDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable String id){
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }



}
