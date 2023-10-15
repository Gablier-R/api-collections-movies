package br.com.collec.service;

import br.com.collec.entity.CollectionsMovies;
import br.com.collec.entity.User;
import br.com.collec.payload.collectionsMovies.CollectionsResponseDTO;
import br.com.collec.payload.user.UserResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceMap {

    public UserResponseDTO mapToResponseUser(User user) {
        List<CollectionsResponseDTO> collectionsMoviesDTOs = user.getCollectionsMovies().stream()
                .map(this::mapToResponseCollectionsMovies)
                .collect(Collectors.toList());

        return new UserResponseDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                collectionsMoviesDTOs
        );
    }

    public CollectionsResponseDTO mapToResponseCollectionsMovies(CollectionsMovies collectionsMovies){
        return new CollectionsResponseDTO(
                collectionsMovies.getId(),
                collectionsMovies.getName(),
                collectionsMovies.getResume(),
                collectionsMovies.getMovies(),
                collectionsMovies.getPublished()
        );
    }

}
