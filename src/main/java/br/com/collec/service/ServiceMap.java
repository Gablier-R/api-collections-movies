package br.com.collec.service;

import br.com.collec.entity.CollectionsMovies;
import br.com.collec.entity.User;
import br.com.collec.payload.AllResponseDTO;
import br.com.collec.payload.collectionsMovies.CollectionsResponseDTO;
import br.com.collec.payload.user.Custom;
import br.com.collec.payload.user.UserResponseDTO;
import br.com.collec.payload.user.UserResponsePage;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceMap {

    public UserResponseDTO mapToResponseForAll(User user) {
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

    public UserResponsePage mapToResponseUser(User user) {
        return new UserResponsePage(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        );
    }

    public Custom mapToResponseUserPage(List<UserResponsePage> content, Page<User> users) {
        Custom responseDTO = new Custom();
        responseDTO.setContent(content);
        responseDTO.setPageNo(users.getNumber());
        responseDTO.setPageSize(users.getSize());
        responseDTO.setTotalElements(users.getTotalElements());
        responseDTO.setTotalPages(users.getTotalPages());
        responseDTO.setLast(users.isLast());

        return responseDTO;
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

    public AllResponseDTO mapToResponseAllResource(List<UserResponseDTO> content, Page<User> users) {
        AllResponseDTO responseDTO = new AllResponseDTO();
        responseDTO.setContent(content);
        responseDTO.setPageNo(users.getNumber());
        responseDTO.setPageSize(users.getSize());
        responseDTO.setTotalElements(users.getTotalElements());
        responseDTO.setTotalPages(users.getTotalPages());
        responseDTO.setLast(users.isLast());

        return responseDTO;
    }

}
