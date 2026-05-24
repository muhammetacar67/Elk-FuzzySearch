package com.example.blogsearch.repository.es;

import com.example.blogsearch.model.document.BlogDocument;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BlogEsRepository extends ElasticsearchRepository<BlogDocument, String> {

    List<BlogDocument> findByTitle(String title);

    List<BlogDocument> findByCategory(String category);

    @Query("{\"fuzzy\": {\"title\": {\"value\": \"?0\", \"fuzziness\": \"AUTO\"}}}")
    List<BlogDocument> findByTitleFuzzy(String title);
}