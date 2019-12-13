package se.emiweb.search.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import se.emiweb.search.model.Larsson_pop;

@Repository()
public interface Larsson_popRepository extends ElasticsearchRepository<Larsson_pop, String>{}
