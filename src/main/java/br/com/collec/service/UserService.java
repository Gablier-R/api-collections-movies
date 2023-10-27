package br.com.collec.service;

import br.com.collec.payload.AllResponseDTO;
import br.com.collec.payload.user.UserDataDTO;
import br.com.collec.payload.user.UserResponseDTO;
import br.com.collec.entity.User;
import br.com.collec.payload.user.OnlyUserResponseDTO;
import br.com.collec.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    final UserRepository userRepository;
    final PasswordEncoder encoder;
    final ServiceMap serviceMap;

    public UserService(UserRepository userRepository, PasswordEncoder encoder, ServiceMap serviceMap) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.serviceMap = serviceMap;
    }

    public OnlyUserResponseDTO saveUser(UserDataDTO userDTO){
        if (userRepository.existsByEmail(userDTO.getEmail())){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "E-mail already registered");
        }

        return serviceMap.mapToResponseOnlyUser(userRepository.save(createNewUser(userDTO)));
    }

    public OnlyUserResponseDTO getUserById(String id){

        return serviceMap.mapToResponseOnlyUser(verifyUserById(id));
    }

    public void deleteById(String id){

        userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        userRepository.deleteById(id);
    }

    public UserResponseDTO updateUser (String id, UserDataDTO userUpdateDTO){

        return serviceMap.mapToResponseUserAndCollections(userRepository.save(update(userUpdateDTO, verifyUserById(id))));
    }

    private User verifyUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
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

    public AllResponseDTO queryAllUsersPageable(int pageNo, int pageSize) {
        Page<User> users = userRepository.findAll(PageRequest.of(pageNo, pageSize));

        List<OnlyUserResponseDTO> content = users.getContent().stream()
                .map(serviceMap::mapToResponseOnlyUser)
                .collect(Collectors.toList());

        return serviceMap.mapToResponseAll(content, users);
    }

    //Mets for fetch all resource
    public AllResponseDTO mapToPageableAllResource(int pageNo, int pageSize){
        Page<User> users = userRepository.findAll(PageRequest.of(pageNo, pageSize));

        List<User> listOfUser = users.getContent();

        List<UserResponseDTO> content = listOfUser.stream()
                .map(serviceMap::mapToResponseUserAndCollections)
                .collect(Collectors.toList());

        return serviceMap.mapToResponseAll(content, users);

    }

}

