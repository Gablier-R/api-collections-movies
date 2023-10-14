package br.com.collec.repository;

import br.com.collec.entity.CollectionsMovies;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionsMoviesRepository extends MongoRepository<CollectionsMovies, String> {
}
