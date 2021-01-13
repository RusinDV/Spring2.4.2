package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import web.model.AuthGroup;
import web.model.User;
import web.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping(value = "/")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ModelAndView getEditPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("editUsers");
        modelAndView.addObject("allUsers", userService.getUsers());
        return modelAndView;
    }

    @PostMapping(value = "/delete")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ModelAndView getDelete(@RequestParam long idUser) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/");
        userService.deleteUser(idUser);
        return modelAndView;
    }

    @GetMapping(value = "/createUser")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ModelAndView createUser(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("createUser");
        modelAndView.addObject("userAdd", new User());
        return modelAndView;
    }

    @PostMapping(value = "/add")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ModelAndView registrationPost(@ModelAttribute("userForm") User user, @ModelAttribute("role") String role, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        String encode = bCryptPasswordEncoder.encode(user.getPassword());

        List<AuthGroup> authGroupList = new LinkedList<>();
        AuthGroup authGroupUser = new AuthGroup();
        authGroupUser.setName(user.getName());
        authGroupUser.setAuthgroup("USER");
        authGroupList.add(authGroupUser);

        if (role.equals("admin")) {
            AuthGroup authGroupAdmin = new AuthGroup();
            authGroupAdmin.setName(user.getName());
            authGroupAdmin.setAuthgroup("ADMIN");
            authGroupList.add(authGroupAdmin);

        }

        user.setAuthGroupList(authGroupList);
        user.setPassword(encode);
        userService.createUser(user);

        modelAndView.setViewName("redirect:/admin/");
        return modelAndView;
    }

    @PostMapping(value = "/preupdate")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ModelAndView getUpdate(@RequestParam long idUser) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("update");
        User user = userService.readUser(idUser);
        user.setPassword("");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @PostMapping(value = "/update")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ModelAndView getUpdatePost(@ModelAttribute("user") User theUser,@ModelAttribute("role") String role) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/");
        String encode = bCryptPasswordEncoder.encode(theUser.getPassword());

        List<AuthGroup> authGroupList = new LinkedList<>();
        AuthGroup authGroupUser = new AuthGroup();
        authGroupUser.setName(theUser.getName());
        authGroupUser.setAuthgroup("USER");
        authGroupList.add(authGroupUser);

        if (role.equals("admin")) {
            AuthGroup authGroupAdmin = new AuthGroup();
            authGroupAdmin.setName(theUser.getName());
            authGroupAdmin.setAuthgroup("ADMIN");
            authGroupList.add(authGroupAdmin);
        }


        userService.updateUser(theUser.getId(), theUser.getName(), theUser.getLastName(), theUser.getAge(), encode,authGroupList);

        return modelAndView;
    }

}