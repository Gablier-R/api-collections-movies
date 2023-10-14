package br.com.collec.service;


import br.com.collec.entity.User;
import br.com.collec.payload.collectionsMovies.CollectionsMoviesDTO;
import br.com.collec.payload.collectionsMovies.CollectionsMoviesResponseDTO;
import br.com.collec.entity.CollectionsMovies;
import br.com.collec.repository.CollectionsMoviesRepository;
import br.com.collec.repository.UserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public record CollectionsMoviesService(CollectionsMoviesRepository collectionsMoviesRepository, UserRepository userRepository) {

    public User updateCollectionsMovies(String userId, CollectionsMoviesDTO collectionsMoviesPatchDTO) {

        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        user.getCollectionsMovies().add(newCollectionsMovies(collectionsMoviesPatchDTO));

        return userRepository.save(user);
    }

    private static CollectionsMovies newCollectionsMovies(CollectionsMoviesDTO collectionsMoviesPatchDTO) {
        CollectionsMovies collectionsMovies = new CollectionsMovies();
        collectionsMovies.setName(collectionsMoviesPatchDTO.getName());
        collectionsMovies.setResume(collectionsMoviesPatchDTO.getResume());
        collectionsMovies.setMovies(collectionsMoviesPatchDTO.getMovies());
        collectionsMovies.setPublished(collectionsMoviesPatchDTO.getPublished());
        return collectionsMovies;
    }


    public CollectionsMoviesResponseDTO mapToResponseCollectionsMovies(CollectionsMovies collectionsMovies){
        return new CollectionsMoviesResponseDTO(
                collectionsMovies.getId(),
                collectionsMovies.getName(),
                collectionsMovies.getResume(),
                collectionsMovies.getMovies(),
                collectionsMovies.getPublished()
        );
    }

}
