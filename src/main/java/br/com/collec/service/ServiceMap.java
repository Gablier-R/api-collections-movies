package br.com.collec.service;

import br.com.collec.entity.CollectionsMovies;
import br.com.collec.entity.User;
import br.com.collec.payload.AllResponseDTO;
import br.com.collec.payload.collectionsMovies.CollectionsResponseDTO;
import br.com.collec.payload.user.UserResponseDTO;
import br.com.collec.payload.user.OnlyUserResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceMap {

    public UserResponseDTO mapToResponseUserAndCollections(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getCollectionsMovies()
        );
    }

    public OnlyUserResponseDTO mapToResponseOnlyUser(User user) {
        return new OnlyUserResponseDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        );
    }


    public CollectionsResponseDTO mapToResponseOnlyCollectionsMovies(CollectionsMovies collectionsMovies){
        return new CollectionsResponseDTO(
                collectionsMovies.getId(),
                collectionsMovies.getName(),
                collectionsMovies.getResume(),
                collectionsMovies.getMovies(),
                collectionsMovies.getPublished()
        );
    }

    public static <T, U> AllResponseDTO<T> mapToResponseAll(List<T> content, Page<U> paginatedEntity) {
        return (AllResponseDTO<T>) new AllResponseDTO<>(
                content,
                paginatedEntity.getNumber(),
                paginatedEntity.getSize(),
                paginatedEntity.getTotalPages(),
                paginatedEntity.getTotalElements(),
                paginatedEntity.isLast()
        );
    }


}
