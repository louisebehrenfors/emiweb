package se.emiweb.search.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import se.emiweb.search.model.Saka;

public interface SakaRepository extends ElasticsearchRepository<Saka, String>{

}
