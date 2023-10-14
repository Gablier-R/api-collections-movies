package br.com.collec.controller;

import br.com.collec.payload.user.ResponseDTO;
import br.com.collec.payload.user.UserDTO;
import br.com.collec.payload.user.UserResponseDTO;
import br.com.collec.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static br.com.collec.Utils.Constants.DEFAULT_PAGE_NUMBER;
import static br.com.collec.Utils.Constants.DEFAULT_PAGE_SIZE;

@RestController
@RequestMapping("api/user")
public record UserController(UserService userService) {

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid UserDTO user){
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.OK);
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

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUserById(@PathVariable String id, @Valid @RequestBody UserDTO userUpdateDTO){
        return new ResponseEntity<>(userService.updateUser(id, userUpdateDTO), HttpStatus.OK);
    }

}
