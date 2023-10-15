package br.com.collec.service;

import br.com.collec.entity.CollectionsMovies;
import br.com.collec.entity.Movies;
import br.com.collec.entity.User;
import br.com.collec.payload.collectionsMovies.CollectionsResponseDTO;
import br.com.collec.payload.movies.MoviesDataDTO;
import br.com.collec.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public record MoviesService(UserRepository userRepository, ServiceMap serviceMap) {

    public CollectionsResponseDTO addMovieToCollection(String userId, String collectionId, MoviesDataDTO movieRequest) {

        userAndCollection addMoviesInCollection = verifyUserAndCollection(userId, collectionId);

        addMoviesInCollection.collections.getMovies().add(new Movies(movieRequest.getUrl()));

        userRepository.save(addMoviesInCollection.user());

        return serviceMap.mapToResponseCollectionsMovies(addMoviesInCollection.collections);
    }
    public void deleteMovie(String userId, String collectionId, String movieId) {

        userAndCollection result = verifyUserAndCollection(userId, collectionId);

        if (result.collections != null) {
                result.collections.getMovies().removeIf(movie -> movie.getId().equals(movieId));
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



