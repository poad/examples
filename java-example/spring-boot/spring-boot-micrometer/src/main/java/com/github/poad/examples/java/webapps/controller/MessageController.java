package com.github.poad.examples.java.webapps.controller;

import com.github.poad.examples.java.webapps.domain.Message;
import com.github.poad.examples.java.webapps.exception.NotFoundException;
import com.github.poad.examples.java.webapps.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Immutable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

@Immutable
@Controller
@Validated
@RequestMapping("/")
public class MessageController {

    private final MessageService service;

    public MessageController(@Autowired MessageService service) {
        this.service = service;
    }

    @GetMapping
    public String all(Model model) {
        List<Message> messages = service.findAll();
        model.addAttribute("messages", messages);
        return "index";
    }

    @GetMapping("{id}")
    public String byId(@PathVariable("id") @NotBlank String id, Model model) {
        Message message = service.findById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("message", message);
        return "show";
    }

    @PostMapping
    public String create(String message, Model model) {
        service.create(message);
        return "redirect:/";
    }

    @PostMapping("update")
    @PutMapping("{id}")
    public String update(String id, String message, Model model) {
        service.update(id, message);
        return "redirect:/";
    }

    @PostMapping("delete")
    @DeleteMapping("{id}")
    public String delete(String id, String message, Model model) {
        service.delete(id);
        return "redirect:/";
    }
}
