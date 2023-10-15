package br.com.collec.service;

import br.com.collec.entity.CollectionsMovies;
import br.com.collec.entity.Movies;
import br.com.collec.entity.User;
import br.com.collec.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public record MoviesService(UserRepository userRepository) {

    public void deleteMovie(String userId, String collectionId, String movieId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Impossible to perform this operation: User does not exist"));

        if (user != null) {
            CollectionsMovies collectionWithMovie = user.getCollectionsMovies().stream()
                    .filter(collection -> collection.getId().equals(collectionId))
                    .findFirst()
                    .orElse(null);

            if (collectionWithMovie != null) {
                collectionWithMovie.getMovies().removeIf(movie -> movie.getId().equals(movieId));
                userRepository.save(user);
            }
        }
    }


}
