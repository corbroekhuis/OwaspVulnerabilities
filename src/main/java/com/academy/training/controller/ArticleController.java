package com.academy.training.controller;

import com.academy.training.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.academy.training.TrainingApplication;
import com.academy.training.model.Article;

@RestController
@RequestMapping("/api")
public class ArticleController {

  protected static final Logger logger = LogManager.getLogger();

  ArticleRepository articleRepository;

  public ArticleController(@Autowired ArticleRepository articleRepository){
      this.articleRepository = articleRepository;

  }

    @GetMapping(value = "/article/sendIfo", produces = "text/html")
    public ResponseEntity<String> sendInfo( @RequestParam(value = "info", required = true) final String info) {

        return ResponseEntity.ok(info) ;

    }

  @GetMapping(value = "/article/getArticleById", produces = "text/html")
  public ResponseEntity<String> getArticleById( @RequestParam(value = "id", required = true) final long id) {

	  Article article = articleRepository.findById(id).orElse(null);

	  String html =
              "<html>\n" +
              "<body>\n" +
              "Name: " + article.getName() + "\n" +
              "Description: " + article.getDescription() + "\n" +
              "Stock: " + article.getStock() + "\n" +
              "</body>\n" +
              "</html>\n";


      return ResponseEntity.ok(html) ;

  }
  
  @PostMapping(value = "/article/addArticle", consumes = "application/json", produces = "application/json")
  public ResponseEntity<Article> addArticle( @RequestBody final Article articleIn) {

      Article articleOut = articleRepository.save(articleIn);
      return ResponseEntity.ok(articleOut) ;

  }

}
