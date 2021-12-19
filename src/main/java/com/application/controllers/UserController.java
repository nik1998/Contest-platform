package com.application.controllers;

import com.application.dto.EditUserDto;
import com.application.exception.ForbiddenException;
import com.application.model.Contest;
import com.application.model.User;
import com.application.service.ContestService;
import com.application.service.UserService;
import com.application.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ContestService contestService;

    @GetMapping
    public String user(@RequestParam(value = "id", required = false, defaultValue = "-1") long userId,
                       Principal principal, Model model) {
        if (principal != null) {
            User user = userService.findByEmail(principal.getName());
            if (userId == user.getId() || userId == -1) {
                model.addAttribute("root", 1);
                model.addAttribute("cur_user", user);
                model.addAttribute("image", DataUtils.encodeImg(user.getPicByte()));
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
            model.addAttribute("image", DataUtils.encodeImg(curUser.get().getPicByte()));
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
            model.addAttribute("image", DataUtils.encodeImg(user.getPicByte()));
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
        model.addAttribute("image", DataUtils.encodeImg(user.getPicByte()));
        return "user";
    }

    @PostMapping("/picture")
    public String picture(@RequestParam("imagefile") MultipartFile file, Principal principal) throws IOException {
        if(file.getSize()>100000000){
            throw new ForbiddenException();
        }
        User user = userService.findByEmail(principal.getName());
        byte[] bfile = DataUtils.compressContestPicture(file.getBytes());
        userService.updateImage(user, bfile);
        return "redirect:/user?success";
    }

    @GetMapping("/follow")
    public String register(@RequestParam(value = "id") long conId, Principal principal, Model model) {

        User user = userService.findByEmail(principal.getName());
        Optional<Contest> contest = contestService.findById(conId);
        if (contest.isPresent()) {
            if (contest.get().getJury().contains(user)) {
                throw new ForbiddenException();
            }
            if (user.getContests().contains(contest.get())) {
                throw new ForbiddenException();
            }
            userService.follow(contest.get(), user);
            model.addAttribute("root", 0);
            model.addAttribute("follow", true);
            model.addAttribute("cur_contest", contest.get());
            model.addAttribute("image", DataUtils.encodeImg(contest.get().getPicByte()));
            return "contest";
        } else {
            model.addAttribute("myerror", "Соревнование не найдено");
            return "index";
        }
    }
}
