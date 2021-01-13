package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import web.model.User;
import web.service.UserService;

import java.security.Principal;

@Controller

public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "/user")
    @PreAuthorize(value = "hasRole('USER')")
    public ModelAndView getEditPage(Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user");
        User user = userService.redUserByName(principal.getName());
        modelAndView.addObject("user", user);
        return modelAndView;
    }
}
