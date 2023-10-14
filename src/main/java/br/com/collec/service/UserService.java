package br.com.collec.service;

import br.com.collec.payload.user.ResponseDTO;
import br.com.collec.payload.user.UserDTO;
import br.com.collec.payload.user.UserResponseDTO;
import br.com.collec.entity.User;
import br.com.collec.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;


@Service
public record UserService(UserRepository userRepository, PasswordEncoder encoder) {

    public UserResponseDTO saveUser(UserDTO userDTO){

        if (userRepository.existsByEmail(userDTO.getEmail())){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "E-mail already registered");
        }

        return mapToResponseUser(userRepository.save(createNewUser(userDTO)));
    }

    public UserResponseDTO getUserById(String id){

        var user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found 1"));

        return mapToResponseUser(user);
    }

    public void deleteById(String id){

        userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found 2"));

        userRepository.deleteById(id);
    }

    public ResponseDTO queryUsers(int pageNo, int pageSize) {
        return mapToPageableUsers(PageRequest.of(pageNo, pageSize));
    }

    public UserResponseDTO updateUser (String id, UserDTO userUpdateDTO){

        User user = userRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found 3 "));

        return mapToResponseUser(userRepository.save(update(userUpdateDTO, user)));
    }

    private User update(UserDTO userUpdateDTO, User user) {
        user.setFirstName(userUpdateDTO.getFirstName());
        user.setLastName(userUpdateDTO.getLastName());
        user.setEmail(userUpdateDTO.getEmail());
        user.setPassword(encoder.encode(userUpdateDTO.getPassword()) );

        return user;
    }

    private User createNewUser(UserDTO user){
        return new User(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                encoder.encode(user.getPassword())
        );
    }

    private ResponseDTO mapToPageableUsers(Pageable pageable){
        Page<User> users = userRepository.findAll(pageable);

        List<User> listOfUser = users.getContent();

        List<UserResponseDTO> content = listOfUser.stream().map(this::mapToResponseUser).collect(Collectors.toList());

        return mapToResponse (content, users);

    }

    private ResponseDTO mapToResponse(List<UserResponseDTO> content, Page<User> users) {

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setContent(content);
        responseDTO.setPageNo(users.getNumber());
        responseDTO.setPageSize(users.getSize());
        responseDTO.setTotalElements(users.getTotalElements());
        responseDTO.setTotalPages(users.getTotalPages());
        responseDTO.setLast(users.isLast());

        return responseDTO;
    }

    public UserResponseDTO mapToResponseUser(User user){
        return new UserResponseDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getCollectionsMovies()
        );
    }


}

