//package br.com.collec.service;
//
//
//import br.com.collec.payload.collectionsMovies.CollectionsMoviesDTO;
//import br.com.collec.payload.collectionsMovies.CollectionsMoviesResponseDTO;
//import br.com.collec.payload.user.UserResponseDTO;
//import br.com.collec.entity.CollectionsMovies;
//import br.com.collec.repository.CollectionsMoviesRepository;
//import org.springframework.stereotype.Service;
//
//@Service
//public record CollectionsMoviesService(CollectionsMoviesRepository collectionsMoviesRepository, UserService userService) {
//
//    public CollectionsMoviesResponseDTO saveCollectionsInUser(String id, CollectionsMoviesDTO collectionsMoviesDTO){
//        UserResponseDTO user = userService.getUserById(id);
//
//        var newCollection = collectionsMoviesRepository.save(newCollectionsMovies(collectionsMoviesDTO));
//        return mapToResponseCollectionsMovies(newCollection);
//    }
//
//    public CollectionsMovies newCollectionsMovies(CollectionsMoviesDTO collectionsMoviesDTO){
//        return new CollectionsMovies(
//                collectionsMoviesDTO.getName(),
//                collectionsMoviesDTO.getResume(),
//                collectionsMoviesDTO.getMovies(),
//                collectionsMoviesDTO.getPublished()
//        );
//    }
//
//    public CollectionsMoviesResponseDTO mapToResponseCollectionsMovies(CollectionsMovies collectionsMovies){
//        return new CollectionsMoviesResponseDTO(
//                collectionsMovies.getId(),
//                collectionsMovies.getName(),
//                collectionsMovies.getResume(),
//                collectionsMovies.getMovies(),
//                collectionsMovies.getPublished()
//        );
//    }
//
//}
