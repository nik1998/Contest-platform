package com.application.controllers;

import com.application.dto.ContestDto;
import com.application.model.Contest;
import com.application.model.Organization;
import com.application.service.ContestService;
import com.application.service.OrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/contests")
public class ContestController {

    @Autowired
    private ContestService contestService;

    @Autowired
    private OrgService orgService;

    @ModelAttribute("contest")
    public ContestDto contestDto() {
        return new ContestDto();
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
    public String createContest(@ModelAttribute("contest") @Valid ContestDto conDto,
                                BindingResult result) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Organization org = orgService.findByEmail(authentication.getName());
        if (result.hasErrors()) {
            return "create_contest";
        }
        contestService.save(conDto, org);
        return "redirect:/contests";
    }
}
