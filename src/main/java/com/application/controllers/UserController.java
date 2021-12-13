package com.application.controllers;

import com.application.dto.EditUserDto;
import com.application.dto.UserRegistrationDto;
import com.application.exception.ForbiddenException;
import com.application.model.User;
import com.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public String user(@RequestParam(value = "id", required = false, defaultValue = "-1") long userId,
                       Principal principal, Model model) {
        if (principal != null) {
            User user = userService.findByEmail(principal.getName());
            if (userId == user.getId() || userId == -1) {
                model.addAttribute("root", 1);
                model.addAttribute("cur_user", user);
                return "user";
            }
        }
        Optional<User> curUser = userService.findById(userId);
        if (curUser.isPresent()) {
            if (principal != null) {
                model.addAttribute("root", 0);
            } else {
                model.addAttribute("root", -1);
            }
            model.addAttribute("cur_user", curUser.get());
        } else {
            model.addAttribute("myerror", "User not found");
            return "index";
        }
        return "user";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("cur_user") @Valid EditUserDto userDto,
                       BindingResult result,
                       Principal principal, Model model) throws ForbiddenException {
        if (!principal.getName().equals(userDto.getEmail())) {
            throw new ForbiddenException();
        }
        if (result.hasErrors()) {
            User user = userService.findByEmail(principal.getName());
            userDto.setContacts(user.getContacts());
            userDto.setContests(user.getContests());
            userDto.setRating(user.getRating());
            model.addAttribute("root", 2);
            return "user";
        }
        userService.update(userDto);
        return "redirect:/user?success";
    }

    @GetMapping("/edit")
    public String edit(Principal principal, Model model) {
        User user = userService.findByEmail(principal.getName());
        model.addAttribute("root", 2);
        model.addAttribute("cur_user", user);
        return "user";
    }
}
