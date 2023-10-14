package br.com.collec.service;

import br.com.collec.controller.payload.user.ResponseDTO;
import br.com.collec.controller.payload.user.UserCreateDTO;
import br.com.collec.controller.payload.user.UserResponseDTO;
import br.com.collec.entity.User;
import br.com.collec.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public record UserService(UserRepository userRepository, PasswordEncoder encoder) {

    public UserResponseDTO saveUser(UserCreateDTO userDTO){

        if (userRepository.existsByEmail(userDTO.getEmail())){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "E-mail already registered");
        }

        return mapToResponseUser(userRepository.save(createNewUser(userDTO)));
    }

    public UserResponseDTO getUserById(String id){

        var user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not exists"));

        return mapToResponseUser(user);
    }

    public void deleteById(String id){

        userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not exists"));

        userRepository.deleteById(id);
    }

    public ResponseDTO queryUsers(int pageNo, int pageSize) {
        return mapToPageableUsers(PageRequest.of(pageNo, pageSize));
    }

    private ResponseDTO mapToPageableUsers(Pageable pageable){
        return mapToResponseDTO(userRepository.findAll(pageable));
    }

    private User createNewUser(UserCreateDTO user){
        return new User(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                encoder.encode(user.getPassword())
        );
    }

    private UserResponseDTO mapToResponseUser(User user){
        return new UserResponseDTO(
                user.getFirstName(),
                user.getLastName(),
                user.getLastName(),
                user.getEmail()
        );
    }

    private ResponseDTO mapToResponseDTO(Page<User> users) {
        return new ResponseDTO(
                users.getContent(),
                users.getNumber(),
                users.getSize(),
                users.getTotalPages(),
                users.getTotalElements(),
                users.isLast()
        );
    }

}

