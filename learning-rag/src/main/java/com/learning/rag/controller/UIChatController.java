package com.learning.rag.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UIChatController {
    @GetMapping("/chat")
    public String chat() {
        return "forward:/index.html";
    }
}
