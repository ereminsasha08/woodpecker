package com.woodpecker.woodpecker.web.user;

import com.woodpecker.woodpecker.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/u")
@AllArgsConstructor
public class UserUI {

    private final UserRepository repository;

    @GetMapping
    public String get(Model model){
        model.addAttribute("users", repository.findAll());
        return "users";
    }
}
