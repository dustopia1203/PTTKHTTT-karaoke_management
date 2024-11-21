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

@Controller
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/login")
    public ModelAndView login(
            @RequestParam(required = false) boolean back,
            HttpSession session
    ) {
        ModelAndView view = new ModelAndView("login");
        Employee employee;
        if (back) {
             employee = Employee.builder()
                    .username((String) session.getAttribute("username"))
                    .password((String) session.getAttribute("password"))
                    .build();
            view.addObject("back", true);
        } else {
            employee = new Employee();
            view.addObject("back", false);
        }
        view.addObject("employee", employee);
        return view;
    }

    @PostMapping("/login")
    public ModelAndView doLogin(
            @ModelAttribute Employee employee,
            HttpSession session
    ) {
        if (employeeService.checkLogin(employee)) {
            Manager manager = Manager.builder()
                    .id(employee.getId())
                    .name(employee.getName())
                    .build();
            session.setAttribute("manager", manager);
            return new ModelAndView("redirect:/manager-home");
        } else {
            session.setAttribute("username", employee.getUsername());
            session.setAttribute("password", employee.getPassword());
            return new ModelAndView("redirect:/login?back=true");
        }
    }

}
