package com.application.controllers;

import com.application.exception.ForbiddenException;
import com.application.model.Contest;
import com.application.model.Mark;
import com.application.model.Report;
import com.application.model.User;
import com.application.service.ContestService;
import com.application.service.MarkService;
import com.application.service.ReportService;
import com.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Date;
import java.util.Optional;

@Controller
public class ReportController {
    @Autowired
    private ContestService contestService;

    @Autowired
    private UserService userService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private MarkService markService;

    @GetMapping("/reports")
    public String reports(@RequestParam(value = "conid") long conId, Principal principal, Model model) {
        User user = userService.findByEmail(principal.getName());
        Optional<Contest> con = contestService.findById(conId);
        if (con.isPresent()) {
            Contest contest = con.get();
            boolean humanVoting = contest.getPopularVoting() && contest.getDeadline().before(new Date());
            if (contest.getJury().contains(user) || humanVoting) {
                model.addAttribute("reports", reportService.findReportsByContest(contest));
                return "report";
            } else {
                throw new ForbiddenException();
            }
        } else {
            model.addAttribute("myerror", "Соревнование не найдено");
            return "index";
        }
    }

    @GetMapping("/reports/vote")
    public String voteReport(@RequestParam(value = "id") long id, Principal principal, Model model) {

        Optional<Report> rep = reportService.findById(id);
        if (rep.isPresent()) {
            Report report = rep.get();
            User user = userService.findByEmail(principal.getName());
            Contest contest = report.getContest();
            boolean humanVoting = contest.getPopularVoting() && contest.getDeadline().before(new Date());
            if (contest.getJury().contains(user) || humanVoting) {
                Optional<Mark> mark = markService.findMarkByEvaluatorAndReport(user, report);
                if (mark.isPresent()) {
                    throw new ForbiddenException();
                }
                markService.evaluate(user, report.getSender(), report);
                return "redirect:/reports?conid=" + contest.getId();
            } else {
                throw new ForbiddenException();
            }
        } else {
            model.addAttribute("myerror", "Отчёт не найден");
            return "index";
        }
    }

    @GetMapping("/reports/files")
    public ResponseEntity<byte[]> getFile(@RequestParam(value = "id") long id, Principal principal, Model model) {
        Optional<Report> rep = reportService.findById(id);
        if (rep.isPresent()) {
            Report report = rep.get();
            User user = userService.findByEmail(principal.getName());
            Contest contest = report.getContest();
            boolean humanVoting = contest.getPopularVoting() && contest.getDeadline().before(new Date());
            if (contest.getJury().contains(user) || humanVoting) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + report.getFileName() + "\"")
                        .body(report.getFileReport());
            } else {
                throw new ForbiddenException();
            }
        } else {
            model.addAttribute("myerror", "Отчёт не найден");
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "/index");
            return new ResponseEntity<byte[]>(headers, HttpStatus.FOUND);
        }
    }
}
