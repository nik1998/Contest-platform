package com.application.service;

import com.application.dto.ReportDto;
import com.application.model.Contest;
import com.application.model.Report;
import com.application.model.User;

import java.util.List;
import java.util.Optional;

public interface ReportService {
    Report create(ReportDto report, User user, Contest contest);

    Optional<Report> findReportByContestAndSender(Contest contest, User user);

    List<Report> findReportsByContest(Contest contest);

    Optional<Report> findById(Long id);

    Report update(ReportDto reportDto, Report report);
}
