package br.com.collec.service;

import br.com.collec.payload.collectionsMovies.CollectionsResponseDTO;
import br.com.collec.payload.user.UserResponsePage;
import br.com.collec.payload.user.UserDataDTO;
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
public record UserService(UserRepository userRepository, PasswordEncoder encoder, CollectionsMoviesService collectionsMoviesService) {

    public UserResponseDTO saveUser(UserDataDTO userDTO){

        if (userRepository.existsByEmail(userDTO.getEmail())){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "E-mail already registered");
        }

        return mapToResponseUser(userRepository.save(createNewUser(userDTO)));
    }

    public UserResponseDTO getUserById(String id){

        var user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        return mapToResponseUser(user);
    }

    public void deleteById(String id){

        userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        userRepository.deleteById(id);
    }

    public UserResponsePage queryUsers(int pageNo, int pageSize) {
        return mapToPageableUsers(PageRequest.of(pageNo, pageSize));
    }

    public UserResponseDTO updateUser (String id, UserDataDTO userUpdateDTO){

        User user = userRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        return mapToResponseUser(userRepository.save(update(userUpdateDTO, user)));
    }

    private User update(UserDataDTO userUpdateDTO, User user) {
        user.setFirstName(userUpdateDTO.getFirstName());
        user.setLastName(userUpdateDTO.getLastName());
        user.setEmail(userUpdateDTO.getEmail());
        user.setPassword(encoder.encode(userUpdateDTO.getPassword()) );

        return user;
    }

    private User createNewUser(UserDataDTO user){
        return new User(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                encoder.encode(user.getPassword())
        );
    }

    private UserResponsePage mapToPageableUsers(Pageable pageable){
        Page<User> users = userRepository.findAll(pageable);

        List<User> listOfUser = users.getContent();

        List<UserResponseDTO> content = listOfUser.stream().map(this::mapToResponseUser).collect(Collectors.toList());

        return mapToResponse (content, users);

    }

    private UserResponsePage mapToResponse(List<UserResponseDTO> content, Page<User> users) {

        UserResponsePage responseDTO = new UserResponsePage();
        responseDTO.setContent(content);
        responseDTO.setPageNo(users.getNumber());
        responseDTO.setPageSize(users.getSize());
        responseDTO.setTotalElements(users.getTotalElements());
        responseDTO.setTotalPages(users.getTotalPages());
        responseDTO.setLast(users.isLast());

        return responseDTO;
    }

    public UserResponseDTO mapToResponseUser(User user) {
        List<CollectionsResponseDTO> collectionsMoviesDTOs = user.getCollectionsMovies().stream()
                .map(collectionsMoviesService::mapToResponseCollectionsMovies)
                .collect(Collectors.toList());

        return new UserResponseDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                collectionsMoviesDTOs
        );
    }


}

