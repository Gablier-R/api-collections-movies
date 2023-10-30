package br.com.collec.domain.service;

import br.com.collec.domain.entity.CollectionsMovies;
import br.com.collec.domain.entity.Movies;
import br.com.collec.domain.entity.User;
import br.com.collec.api.payload.collectionsMovies.CollectionsResponseDTO;
import br.com.collec.api.payload.movies.MoviesDataDTO;
import br.com.collec.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class MoviesService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ServiceMap serviceMap;

    public CollectionsResponseDTO addMovieToCollection(String userId, String collectionId, MoviesDataDTO movieRequest) {

        userAndCollection addMoviesInCollection = verifyUserAndCollection(userId, collectionId);

        addMoviesInCollection.collections.getMovies().add(new Movies(movieRequest.url()));
        addMoviesInCollection.collections.setUpdatedAt(LocalDateTime.now());

        userRepository.save(addMoviesInCollection.user());

        return serviceMap.mapToResponseOnlyCollectionsMovies(addMoviesInCollection.collections);
    }

    public void deleteMovie(String userId, String collectionId, String movieId) {

        userAndCollection result = verifyUserAndCollection(userId, collectionId);

        if (result.collections != null) {
                result.collections.getMovies().removeIf(movie -> movie.getId().equals(movieId));
                result.collections.setUpdatedAt(LocalDateTime.now());
                userRepository.save(result.user());
            }
    }

    private userAndCollection verifyUserAndCollection(String userId, String collectionId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Impossible to perform this operation: User does not exist"));

        CollectionsMovies collectionToUpdate = user.getCollectionsMovies().stream()
                .filter(collection -> collection.getId().equals(collectionId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Impossible to perform this operation: Collection does not exist"));
        return new userAndCollection(user, collectionToUpdate);
    }

    private record userAndCollection(User user, CollectionsMovies collections) {
    }

}



