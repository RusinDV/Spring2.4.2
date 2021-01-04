package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import web.dao.UserDaoImplHibernate;
import web.model.AuthGroup;
import web.model.User;
import web.service.AuthGroupService;
import web.service.UserService;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/admin")
public class CrudController {
    @Autowired
    UserService userService;
    @Autowired
    AuthGroupService authGroupService;

    @GetMapping(value = "/")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ModelAndView getEditPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", new User());
        modelAndView.setViewName("editUsers");
        modelAndView.addObject("allUsers", userService.getUsers());
        return modelAndView;
    }

    @PostMapping(value = "/delete")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ModelAndView getDelete(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/");
        long id = Long.parseLong(request.getParameter("idUser"));
        authGroupService.deleteAuthGroupByName(userService.readUser(id).getName());
        userService.deleteUser(id);
        return modelAndView;
    }

    @PostMapping(value = "/add")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ModelAndView getAdd(@ModelAttribute("user") User theUser) {
        ModelAndView modelAndView = new ModelAndView();
        String password = theUser.getPassword();
        String encode = new BCryptPasswordEncoder(11).encode(password);
        theUser.setPassword(encode);
        userService.createUser(theUser);

        AuthGroup authGroup = new AuthGroup();
        authGroup.setName(theUser.getName());
        authGroup.setAuthgroup("USER");
        authGroupService.createAuthGroup(authGroup);

        AuthGroup authGroup2 = new AuthGroup();
        authGroup2.setName(theUser.getName());
        authGroup2.setAuthgroup("ADMIN");
        authGroupService.createAuthGroup(authGroup2);

        modelAndView.setViewName("redirect:/admin/");
        return modelAndView;
    }

    @PostMapping(value = "/preupdate")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ModelAndView getUpdate(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("update");
        long id = Long.parseLong(request.getParameter("idUser"));
        User user = userService.readUser(id);
        user.setPassword("");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @PostMapping(value = "/update")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ModelAndView getUpdatePost(@ModelAttribute("user") User theUser) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/");
        String password = theUser.getPassword();
        String encode = new BCryptPasswordEncoder(11).encode(password);
        userService.updateUser(theUser.getId(), theUser.getName(), theUser.getLastName(), theUser.getAge(), encode);

        AuthGroup authGroup = new AuthGroup();
        authGroup.setName(theUser.getName());
        authGroup.setAuthgroup("USER");
        authGroupService.createAuthGroup(authGroup);

        AuthGroup authGroup2 = new AuthGroup();
        authGroup2.setName(theUser.getName());
        authGroup2.setAuthgroup("ADMIN");
        authGroupService.createAuthGroup(authGroup2);

        return modelAndView;
    }

}