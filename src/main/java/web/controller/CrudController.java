package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import web.dao.UserDaoImplHibernate;
import web.model.User;
import web.service.UserService;

import javax.servlet.http.HttpServletRequest;


@Controller
public class CrudController {
    @Autowired
    UserService userService;

    @GetMapping(value = "/")
    public ModelAndView getEditPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", new User());
        modelAndView.setViewName("editUsers");
        modelAndView.addObject("allUsers", userService.getUsers());
        return modelAndView;
    }

    @PostMapping(value = "/delete")
    public ModelAndView getDelete(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/");
        long id = Long.parseLong(request.getParameter("idUser"));
        userService.deleteUser(id);
        return modelAndView;
    }

    @PostMapping(value = "/add")
    public ModelAndView getAdd(@ModelAttribute("user") User theUser) {
        ModelAndView modelAndView = new ModelAndView();
        userService.createUser(theUser);
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }

    @PostMapping(value = "/preupdate")
    public ModelAndView getUpdate(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("update");
        long id = Long.parseLong(request.getParameter("idUser"));
        User user = userService.readUser(id);
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @PostMapping(value = "/update")
    public ModelAndView getUpdatePost(@ModelAttribute("user") User theUser) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/");
        userService.updateUser(theUser.getId(), theUser.getName(), theUser.getLastName(), theUser.getAge());
        return modelAndView;
    }

}