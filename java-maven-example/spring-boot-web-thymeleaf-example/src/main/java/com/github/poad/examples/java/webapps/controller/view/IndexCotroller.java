package com.github.poad.examples.java.webapps.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexCotroller {

    @GetMapping
    String index(Model model) {
        return "redirect:/artist";
    }
}
