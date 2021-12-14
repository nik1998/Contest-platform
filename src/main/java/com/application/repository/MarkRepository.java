package com.application.repository;

import com.application.model.Mark;
import com.application.model.Report;
import com.application.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MarkRepository extends JpaRepository<Mark, Long> {
    Optional<Mark> findMarkByEvaluatorAndReport(User evaluator, Report report);
}
