package com.example.case_study_module_4.controller.booking;

import com.example.case_study_module_4.model.booking.Contract;
import com.example.case_study_module_4.service.booking.IContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admins")
public class TestController {

    @Autowired
    private IContractService contractService;

    @ModelAttribute("alert")
    public List<Contract> alert() {
        List<Contract> contractList = contractService.findContract();
        return contractList;
    }

    @GetMapping("")
    public String showIndex() {
        return "admin/index";
    }
}
