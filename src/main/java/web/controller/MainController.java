package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import web.config.security.AdapterUserService;
import web.model.AuthGroup;
import web.model.User;
import web.service.AuthGroupService;
import web.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    UserService userServise;
    @Autowired
    AuthGroupService authGroupService;

    @Autowired
    AdapterUserService adapterUserService;

    @Autowired
    // @Lazy
    AuthenticationManager authenticationManager;


    @GetMapping(value = "/registration")
    public ModelAndView registration(ModelAndView modelAndView, Principal principal) {
        if (principal != null) {
            modelAndView.setViewName("index");
            modelAndView.addObject("message", "Сначало разлогинтесь чтоб заново зарегистрироваться");
            return modelAndView;
        }
        modelAndView.addObject("userForm", new User());
        modelAndView.setViewName("/registration");
        return modelAndView;
    }

    @PostMapping(value = "/registration")
    public ModelAndView registrationPost(@ModelAttribute("userForm") User user, HttpServletRequest request, Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        if (principal != null) {
            modelAndView.setViewName("index");
            modelAndView.addObject("message", "Сначало разлогинтесь чтоб зарегистрироваться");
            return modelAndView;
        }
        User oldUser = userServise.redUserByNameAndLastName(user.getName(), user.getLastName());
        if (oldUser != null) {
            modelAndView.setViewName("registration");
            if (oldUser.getName().equals(user.getName())) {
                modelAndView.addObject("message", "Пользователь с таким именем существует");
            } else {
                modelAndView.addObject("message", "Пользователь с такой фамилией уже существует");
            }
            return modelAndView;
        }
        if (user.getPassword().length() < 1 || user.getPassword() == null) {
            modelAndView.setViewName("registration");
            modelAndView.addObject("message", "Пароль должен быть больше 5 и меньше 20 символов");
            return modelAndView;
        }
        String password = user.getPassword();
        String encode = new BCryptPasswordEncoder(11).encode(password);
        user.setPassword(encode);

        userServise.createUser(user);
        AuthGroup authGroup = new AuthGroup();
        authGroup.setName(user.getName());
        authGroup.setAuthgroup("USER");
        authGroupService.createAuthGroup(authGroup);

       /* AuthGroup authGroup2 = new AuthGroup();
        authGroup2.setName(user.getName());
        authGroup2.setAuthgroup("ADMIN");
        authGroupService.createAuthGroup(authGroup2);*/

        modelAndView.addObject("messageNameCustomer", user.getName());

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(user.getName(), password, Collections.singleton(new SimpleGrantedAuthority("USER")));
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetails(request));
        Authentication authenticatedUser = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);

        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping(value = "/login")
    public ModelAndView login(ModelAndView modelAndView, Principal principal) {
        if (principal != null) {
            modelAndView.setViewName("index");
            modelAndView.addObject("message", "Сначало разлогинтесь чтоб войти под другим именем");
            return modelAndView;
        }
        modelAndView.setViewName("/login");
        return modelAndView;
    }
    @GetMapping(value = "/")
    public ModelAndView indexPage(ModelAndView modelAndView){
        modelAndView.setViewName("/index");
        return modelAndView;
    }
    @GetMapping(value = "/error")
    public ModelAndView errorPage(ModelAndView modelAndView){
        modelAndView.setViewName("/error");
        return modelAndView;
    }


}