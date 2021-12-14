package com.application.repository;

import com.application.model.Contest;
import com.application.model.Report;
import com.application.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    Optional<Report> findReportByContestAndSender(Contest contest, User user);
    List<Report> findReportByContest(Contest contest);

    @Override
    Optional<Report> findById(Long id);
}
