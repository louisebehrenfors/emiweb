package se.emiweb.search.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import se.emiweb.search.model.Usmgbg;


@Repository("ElasticsearchRepository")
public interface UsmgbgRepository extends ElasticsearchRepository<Usmgbg, String>{
	List<Usmgbg> findAll();
}
