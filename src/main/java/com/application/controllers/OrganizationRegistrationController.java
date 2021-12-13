package com.application.controllers;

import com.application.dto.OrganizationDto;
import com.application.model.Organization;
import com.application.service.OrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/registration_organization")
public class OrganizationRegistrationController {

    @Autowired
    private OrgService orgService;

    @ModelAttribute("org")
    public OrganizationDto userRegistrationDto() {
        return new OrganizationDto();
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        return "org_registration";
    }

    @PostMapping
    public String registerOrganization(@ModelAttribute("org") @Valid OrganizationDto orgDto,
                                       BindingResult result) {

        /*Organization existing = orgService.findByEmail(orgDto.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }*/

        Organization existing = orgService.findByCompanyName(orgDto.getCompanyName());
        if (existing != null) {
            result.rejectValue("companyName", null, "There is already an account registered with that company name");
        }

        if (result.hasErrors()) {
            return "org_registration";
        }

        orgService.save(orgDto);
        return "redirect:/login?success";
    }
}
