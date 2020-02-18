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
public class RegisterAttackController {

    protected static final Logger logger = LogManager.getLogger();

    @GetMapping(value = "/registerattack", produces = "text/html")
    public ResponseEntity<String> registerAttack() {

        logger.info("Http request in !DOCTYPE element in xml is executed!");
       return ResponseEntity.ok("ok") ;
    }

}
