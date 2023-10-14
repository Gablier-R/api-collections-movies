package br.com.collec.repository;

import br.com.collec.entity.CollectionsMovies;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CollectionsMoviesRepository extends MongoRepository<CollectionsMovies, String> {
}
