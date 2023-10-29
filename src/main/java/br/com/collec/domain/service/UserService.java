package br.com.collec.domain.service;

import br.com.collec.api.payload.AllResponseDTO;
import br.com.collec.api.payload.user.UserDataDTO;
import br.com.collec.api.payload.user.UserResponseDTO;
import br.com.collec.domain.entity.User;
import br.com.collec.api.payload.user.OnlyUserResponseDTO;
import br.com.collec.infra.emailService.producer.UserProducer;
import br.com.collec.domain.repository.UserRepository;
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
    final UserProducer userProducer;

    public UserService(UserRepository userRepository, PasswordEncoder encoder, ServiceMap serviceMap, UserProducer userProducer) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.serviceMap = serviceMap;
        this.userProducer = userProducer;
    }

    public OnlyUserResponseDTO saveUser(UserDataDTO userDTO){
        if (userRepository.existsByEmail(userDTO.email())){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "E-mail already registered");
        }

        userProducer.publishMessageEmail(userDTO);
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

    public OnlyUserResponseDTO updateUser (String id, UserDataDTO userUpdateDTO){

        return serviceMap.mapToResponseOnlyUser(userRepository.save(update(userUpdateDTO, verifyUserById(id))));
    }

    private User verifyUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    private User update(UserDataDTO userUpdateDTO, User user) {
        user.setFirstName(userUpdateDTO.firstName());
        user.setLastName(userUpdateDTO.lastName());
        user.setEmail(userUpdateDTO.email());
        user.setPassword(encoder.encode(userUpdateDTO.password()) );

        return user;
    }

    private User createNewUser(UserDataDTO user){
        return new User(
                user.firstName(),
                user.lastName(),
                user.email(),
                encoder.encode(user.password())
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

