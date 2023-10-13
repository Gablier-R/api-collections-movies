package br.com.collec.service;

import br.com.collec.controller.payload.CreateUserDTO;
import br.com.collec.controller.payload.ResponseUserDTO;
import br.com.collec.entity.User;
import br.com.collec.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public record UserService(UserRepository userRepository) {

    public ResponseUserDTO saveUser(CreateUserDTO userDTO){

        if (userRepository.existsByEmail(userDTO.getEmail())){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "user already exists");
        }

        return mapToResponseUser(userRepository.save(createNewUser(userDTO)));
    }

    public List<ResponseUserDTO> getAll(){
        List<User> users = userRepository.findAll();
        List<ResponseUserDTO> usersNoPassword = new ArrayList<>();

        for (User user : users){
            usersNoPassword.add(mapToResponseUser(user));
        }

        return usersNoPassword;
    }


    private User createNewUser(CreateUserDTO user){
        return new User(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword()
        );
    }

    private ResponseUserDTO mapToResponseUser(User user){
        return new ResponseUserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        );
    }

}

