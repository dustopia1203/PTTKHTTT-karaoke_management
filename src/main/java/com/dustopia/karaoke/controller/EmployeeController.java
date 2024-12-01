package com.dustopia.karaoke.controller;

import com.dustopia.karaoke.model.Employee;
import com.dustopia.karaoke.model.Manager;
import com.dustopia.karaoke.service.EmployeeService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/login")
    public ModelAndView login(
            @RequestParam(required = false) boolean error,
            HttpSession session
    ) {
        ModelAndView view = new ModelAndView("login");
        Employee employee;
        if (error) {
            employee = Employee.builder()
                    .username((String) session.getAttribute("username"))
                    .password((String) session.getAttribute("password"))
                    .build();
            view.addObject("error", true);
        } else {
            employee = new Employee();
        }
        view.addObject("employee", employee);
        return view;
    }

    @PostMapping("/login")
    public ModelAndView doLogin(
            @ModelAttribute Employee employee,
            HttpSession session
    ) {
        Employee checkedEmployee = employeeService.checkLogin(employee);
        if (Objects.isNull(checkedEmployee)) {
            session.setAttribute("username", employee.getUsername());
            session.setAttribute("password", employee.getPassword());
        }
        if (checkedEmployee instanceof Manager manager) {
            session.setAttribute("manager", manager);
            return new ModelAndView("redirect:/manager-home");
        }
        return new ModelAndView("redirect:/login?error=true");
    }

}
