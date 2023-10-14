package br.com.collec.controller;

import br.com.collec.controller.payload.user.ResponseDTO;
import br.com.collec.controller.payload.user.UserCreateDTO;
import br.com.collec.controller.payload.user.UserResponseDTO;
import br.com.collec.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static br.com.collec.Utils.Constants.DEFAULT_PAGE_NUMBER;
import static br.com.collec.Utils.Constants.DEFAULT_PAGE_SIZE;

@RestController
@RequestMapping("api/user")
public record UserController(UserService userService) {

    @PostMapping("/create")
    public UserResponseDTO createUser(@RequestBody @Valid UserCreateDTO user){
        return userService.saveUser(user);
    }

    @RequestMapping
    public ResponseEntity<ResponseDTO> getAllUsers(@RequestParam( defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                                   @RequestParam( defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize){
        return new ResponseEntity<>(userService.queryUsers(pageNo, pageSize), HttpStatus.OK);
    }

    @RequestMapping("/{id}")
    public ResponseEntity<UserResponseDTO>  getUserByEmail(@PathVariable String id){
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable String id){
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
