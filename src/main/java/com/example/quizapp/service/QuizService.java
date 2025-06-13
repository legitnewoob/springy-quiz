package com.example.quizapp.service;

import com.example.quizapp.entity.Question;
import com.example.quizapp.entity.QuestionWrapper;
import com.example.quizapp.entity.Quiz;
import com.example.quizapp.entity.Response;
import com.example.quizapp.repository.QuestionRepository;
import com.example.quizapp.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    QuestionRepository questionRepository;

    public ResponseEntity<String> createQuiz(String category , int numQ , String title) {
        Quiz quiz = new Quiz();

        List<Question> questions = questionRepository.findRandomQuestionsByCategory(category , numQ);
        quiz.setTitle(title);
        quiz.setQuestions(questions);

        quizRepository.save(quiz);

        return new ResponseEntity<>("Success" , HttpStatus.CREATED);

    }

    public ResponseEntity<List<QuestionWrapper>> getQuiz(Integer id) {
        Optional<Quiz> quiz = quizRepository.findById(id);
        List<Question> questionsFromDB = quiz.get().getQuestions();

        List<QuestionWrapper> questionForUser = new ArrayList<>();

        for(Question q : questionsFromDB) {
            QuestionWrapper qw = new QuestionWrapper(q.getId() , q.getQuestionTitle() , q.getOption1() , q.getOption2() , q.getOption3() , q.getOption4());
            questionForUser.add(qw);
        }
        return new ResponseEntity<>(questionForUser, HttpStatus.OK);
    }

    public ResponseEntity<String> calculateResult(Integer id, List<Response> responses) {
        Quiz quiz = quizRepository.findById(id).get();

        List<Question> questions = quiz.getQuestions();

        int score = 0;
        int idx = 0;

        for(Response response : responses) {
            if(response.getResponse().equals(questions.get(idx).getRightAnswer())) {
                score++;
            }

            idx++;
        }

        return new ResponseEntity<>("Score: " + score, HttpStatus.OK);
    }
}
