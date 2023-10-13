package br.com.collec.service;

import br.com.collec.entity.User;
import br.com.collec.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public record UserService(UserRepository userRepository) {

    public User create(User user){

        return userRepository.save(new User(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword()
            )
        );
    }

}

