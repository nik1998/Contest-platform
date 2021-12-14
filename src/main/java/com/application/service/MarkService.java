package com.application.service;

import com.application.model.Mark;
import com.application.model.Report;
import com.application.model.User;

import java.util.Optional;

public interface MarkService {
    Mark evaluate(User evaluator, User gainer, Report report);

    Optional<Mark> findMarkByEvaluatorAndReport(User evaluator, Report report);
}
