package com.application.service;

import com.application.dto.ReportDto;
import com.application.model.Contest;
import com.application.model.Report;
import com.application.model.User;
import com.application.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportRepository reportRepository;

    public Report create(ReportDto report, User user, Contest contest) {
        Report r = new Report();
        r.setContest(contest);
        r.setSender(user);
        r.setText(report.getText());
        r.setFileName(report.getFileReport().getOriginalFilename());
        try {
            r.setFileReport(report.getFileReport().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reportRepository.save(r);
    }

    public Optional<Report> findReportByContestAndSender(Contest contest, User user) {
        return reportRepository.findReportByContestAndSender(contest, user);
    }

    public List<Report> findReportsByContest(Contest contest) {
        return reportRepository.findReportByContest(contest);
    }

    public Optional<Report> findById(Long id) {
        return reportRepository.findById(id);
    }
}
