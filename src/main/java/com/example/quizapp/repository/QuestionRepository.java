package com.example.quizapp.repository;

import com.example.quizapp.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    List<Question> findByCategory(String category);

    @Query(value = "SELECT * from question q WHERE q.category=:category ORDER BY RANDOM() LIMIT :numQ" , nativeQuery = true)
    List<Question> findRandomQuestionsByCategory(String category , int numQ);
}
