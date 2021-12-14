package com.application.service;

import com.application.model.Mark;
import com.application.model.Report;
import com.application.model.User;
import com.application.repository.MarkRepository;
import com.application.repository.ReportRepository;
import com.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MarkServiceImpl implements MarkService {

    @Autowired
    private MarkRepository markRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private UserRepository userRepository;

    public Mark evaluate(User evaluator, User gainer, Report report) {
        Mark mark = new Mark();
        mark.setValue(1);
        mark.setEvaluator(evaluator);
        mark.setGainer(gainer);
        mark.setReport(report);
        report.incRating(1);
        gainer.incRating(1);
        reportRepository.save(report); //??
        userRepository.save(gainer); //??
        return markRepository.save(mark);
    }

    public Optional<Mark> findMarkByEvaluatorAndReport(User evaluator, Report report) {
        return markRepository.findMarkByEvaluatorAndReport(evaluator, report);
    }
}
