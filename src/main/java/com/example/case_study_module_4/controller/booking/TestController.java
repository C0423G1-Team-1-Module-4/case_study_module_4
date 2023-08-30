package com.example.case_study_module_4.controller.booking;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admins")
public class TestController {
    @GetMapping("")
    public String showIndex() {
        return "admin/index";
    }
}
