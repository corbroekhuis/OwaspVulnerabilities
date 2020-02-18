package com.academy.training.repository;

import com.academy.training.model.Article;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends CrudRepository<Article, Long> {

    @Override
    void deleteById(Long aLong);
}
