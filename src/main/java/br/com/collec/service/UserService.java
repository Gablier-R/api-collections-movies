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

import java.util.Optional;

@Service
public record UserService(UserRepository userRepository, PasswordEncoder encoder) {

    public UserResponseDTO saveUser(UserCreateDTO userDTO){

        if (userRepository.existsByEmail(userDTO.getEmail())){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "user already exists");
        }

        return mapToResponseUser(Optional.of(userRepository.save(createNewUser(userDTO))));
    }

    public UserResponseDTO getUserById(String id){
        Optional<User> user = Optional.ofNullable(userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not exists")));

        return mapToResponseUser(user);
    }

    public void deleteById(String id){
        userRepository.deleteById(id);
    }

    public ResponseDTO queryUsers(int pageNo, int pageSize) {
        return mapToPageableUsers(PageRequest.of(pageNo, pageSize));
    }

    private ResponseDTO mapToPageableUsers(Pageable pageable){
        Page<User> posts = userRepository.findAll(pageable);
        return mapToResponseDTO(posts);
    }

    private User createNewUser(UserCreateDTO user){
        return new User(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                encoder.encode(user.getPassword())
        );
    }

    private UserResponseDTO mapToResponseUser(Optional<User> user){
        return new UserResponseDTO(
                user.get().getFirstName(),
                user.get().getLastName(),
                user.get().getLastName(),
                user.get().getEmail()
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

