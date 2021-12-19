package com.application.controllers;

import com.application.dto.ContestDto;
import com.application.dto.ReportDto;
import com.application.exception.ForbiddenException;
import com.application.model.Contest;
import com.application.model.Report;
import com.application.model.User;
import com.application.service.ContestService;
import com.application.service.ReportService;
import com.application.service.UserService;
import com.application.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

@Controller
@RequestMapping("/contests")
public class ContestController {

    @Autowired
    private ContestService contestService;

    @Autowired
    private UserService userService;

    @Autowired
    private ReportService reportService;

    @ModelAttribute("contest")
    public ContestDto contestDto() {
        return new ContestDto();
    }

    @ModelAttribute("report")
    public ReportDto reportDto() {
        return new ReportDto();
    }

    @GetMapping
    public String list(@RequestParam(value = "page", required = false, defaultValue = "0") int pageNumber,
                       @RequestParam(value = "size", required = false, defaultValue = "10") int size, Model model) {
        Pageable contestPage = PageRequest.of(pageNumber, size);
        Page<Contest> pages = contestService.findAll(contestPage);
        model.addAttribute("contestList", pages);
        return "contests";
    }

    @GetMapping("/create")
    public String getCreatePage(Model model) {
        return "create_contest";
    }

    @PostMapping("/save")
    public String createContest(@ModelAttribute("contest") @Valid ContestDto conDto, BindingResult result, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        if (result.hasErrors()) {
            return "create_contest";
        }
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(conDto.getDeadline())) {
            result.rejectValue("deadline", null, "Дата конца раньше текущей даты");
        }
        if (now.isAfter(conDto.getStartDate())) {
            result.rejectValue("startDate", null, "Дата конца раньше текущей даты");
        }
        if (conDto.getDeadline().isBefore(conDto.getStartDate())) {
            result.rejectValue("deadline", null, "Дата начала позже даты конца");
        }
        if (result.hasErrors()) {
            return "create_contest";
        }
        contestService.save(conDto, user);
        return "redirect:/contests";
    }

    @GetMapping("/contest/{id}")
    public String getContest(@PathVariable("id") long id, Principal principal, Model model) {
        Optional<Contest> contest = contestService.findById(id);
        if (contest.isPresent()) {
            if (principal != null) {
                User user = userService.findByEmail(principal.getName());
                if (contest.get().getJury().contains(user)) {
                    model.addAttribute("root", 1);
                } else {
                    model.addAttribute("root", 0);
                }
                model.addAttribute("follow", user.getContests().contains(contest.get()));
            } else {
                model.addAttribute("root", -1);
            }
            model.addAttribute("image", DataUtils.encodeImg(contest.get().getPicByte()));
            model.addAttribute("cur_contest", contest.get());
        } else {
            model.addAttribute("myerror", "Соревнование не найдено");
            return "index";
        }
        return "contest";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam(value = "id") long conId, Principal principal, Model model) {

        User user = userService.findByEmail(principal.getName());
        Optional<Contest> contest = contestService.findById(conId);
        if (contest.isPresent()) {
            if (!contest.get().getJury().contains(user)) {
                throw new ForbiddenException();
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            model.addAttribute("start", contest.get().getStartDate().format(formatter));
            model.addAttribute("deadline", contest.get().getDeadline().format(formatter));
            model.addAttribute("root", 2);
            model.addAttribute("cur_contest", contest.get());
            model.addAttribute("image", DataUtils.encodeImg(contest.get().getPicByte()));
        } else {
            model.addAttribute("myerror", "Соревнование не найдено");
        }
        return "contest";
    }

    @PostMapping("/update")
    public String updateContest(@ModelAttribute("cur_contest") @Valid ContestDto conDto, BindingResult result, Principal principal, Model model
    ) throws ForbiddenException {
        User user = userService.findByEmail(principal.getName());
        Optional<Contest> contest = contestService.findById(conDto.getId());
        if (contest.isPresent()) {
            if (!contest.get().getJury().contains(user)) {
                throw new ForbiddenException();
            }
            LocalDateTime now = LocalDateTime.now();
            if (now.isAfter(conDto.getDeadline())) {
                result.rejectValue("deadline", null, "Дата конца раньше текущей даты");
            }
            if (now.isAfter(conDto.getStartDate())) {
                result.rejectValue("startDate", null, "Дата конца раньше текущей даты");
            }
            if (conDto.getDeadline().isBefore(conDto.getStartDate())) {
                result.rejectValue("deadline", null, "Дата начала позже даты конца");
            }
        } else {
            model.addAttribute("myerror", "Соревнование не найдено");
            return "index";
        }
        if (result.hasErrors()) {
            conDto.setContestName(contest.get().getContestName());
            conDto.setJury(contest.get().getJury());
            conDto.setOrganization(contest.get().getOrganization());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            model.addAttribute("start", contest.get().getStartDate().format(formatter));
            model.addAttribute("deadline", contest.get().getDeadline().format(formatter));
            model.addAttribute("image", DataUtils.encodeImg(contest.get().getPicByte()));
            model.addAttribute("root", 2);
            return "contest";
        }
        contestService.update(contest.get(), conDto);
        model.addAttribute("cur_contest", contest.get());
        model.addAttribute("image", DataUtils.encodeImg(contest.get().getPicByte()));
        model.addAttribute("root", 1);
        return "contest";
    }

    @PostMapping("update/picture")
    public String picture(@RequestParam("imagefile") MultipartFile file, @RequestParam("id") long id, Principal principal, Model model) throws IOException {
        User user = userService.findByEmail(principal.getName());
        Optional<Contest> contest = contestService.findById(id);
        if (contest.isPresent()) {
            if (!contest.get().getJury().contains(user)) {
                throw new ForbiddenException();
            }
        } else {
            model.addAttribute("myerror", "Соревнование не найдено");
            return "index";
        }
        byte[] bfile = DataUtils.compressContestPicture(file.getBytes());
        contestService.updateImage(contest.get(), bfile);
        model.addAttribute("cur_contest", contest.get());
        model.addAttribute("image", DataUtils.encodeImg(contest.get().getPicByte()));
        model.addAttribute("root", 1);
        return "contest";
    }

    @PostMapping("/report")
    public String report(@ModelAttribute("report") @Valid ReportDto repDto, Principal principal, Model model) {

        User user = userService.findByEmail(principal.getName());
        Optional<Contest> opContest = contestService.findById(repDto.getContestId());
        if (opContest.isPresent()) {
            Contest contest = opContest.get();
            if (contest.getJury().contains(user)) {
                throw new ForbiddenException();
            }
            LocalDateTime now = LocalDateTime.now();
            if (now.isAfter(contest.getDeadline())) {
                throw new ForbiddenException();
            }
            if (now.isBefore(contest.getStartDate())) {
                throw new ForbiddenException();
            }
            Optional<Report> report = reportService.findReportByContestAndSender(contest, user);
            if (report.isPresent()) {
                reportService.update(repDto, report.get());
            } else {
                reportService.create(repDto, user, contest);
            }
            model.addAttribute("root", 0);
            model.addAttribute("follow", user.getContests().contains(contest));
            model.addAttribute("cur_contest", contest);
            model.addAttribute("image", DataUtils.encodeImg(contest.getPicByte()));
            return "contest";
        } else {
            model.addAttribute("myerror", "Соревнование не найдено");
            return "index";
        }
    }
}
