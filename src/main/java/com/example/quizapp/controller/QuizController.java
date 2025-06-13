package com.example.quizapp.controller;


import com.example.quizapp.entity.Question;
import com.example.quizapp.entity.QuestionWrapper;
import com.example.quizapp.entity.Response;
import com.example.quizapp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    QuizService quizService;


    @PostMapping("/create")
    public ResponseEntity<String> createQuiz(
            @RequestParam String category ,
            @RequestParam int numQ ,
            @RequestParam String title) {
        return quizService.createQuiz(category , numQ , title);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuiz(@PathVariable Integer id) {
        return  quizService.getQuiz(id);

    }

    @PostMapping("/submit/{id}")
    public ResponseEntity<String> submitQuiz(@PathVariable Integer id , @RequestBody List<Response> reponses) {
        return quizService.calculateResult(id , reponses);
    }

}
