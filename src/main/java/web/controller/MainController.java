package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import web.config.security.AdapterUserService;
import web.model.AuthGroup;
import web.model.User;
import web.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class MainController {

    @Autowired
    private UserService userService;
    @Autowired
    private AdapterUserService adapterUserService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping(value = "/registration")
    public ModelAndView registration(ModelAndView modelAndView) {
        modelAndView.addObject("userForm", new User());
        modelAndView.setViewName("/registration");
        return modelAndView;
    }

    @PostMapping(value = "/registration")
    public ModelAndView registrationPost(@ModelAttribute("userForm") User user, @ModelAttribute("role") String role, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        String encode = bCryptPasswordEncoder.encode(user.getPassword());
        Set<GrantedAuthority> grantedAuthoritySet = new HashSet<>();
        List<AuthGroup> authGroupList = new LinkedList<>();
        AuthGroup authGroupUser = new AuthGroup();
        authGroupUser.setName(user.getName());
        authGroupUser.setAuthgroup("USER");
        authGroupList.add(authGroupUser);
        grantedAuthoritySet.add(new SimpleGrantedAuthority(authGroupUser.getAuthgroup()));
        if (role.equals("admin")) {
            AuthGroup authGroupAdmin = new AuthGroup();
            authGroupAdmin.setName(user.getName());
            authGroupAdmin.setAuthgroup("ADMIN");
            authGroupList.add(authGroupAdmin);
            grantedAuthoritySet.add(new SimpleGrantedAuthority(authGroupAdmin.getAuthgroup()));
        }

        user.setAuthGroupList(authGroupList);
        user.setPassword(encode);
        userService.createUser(user);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword(), Collections.singleton(new SimpleGrantedAuthority("USER")));
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetails(request));
        Authentication authenticatedUser = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);

        modelAndView.addObject("user", user);
        modelAndView.setViewName("user");

        return modelAndView;
    }

    @GetMapping(value = "/")
    public ModelAndView login(ModelAndView modelAndView) {
        modelAndView.setViewName("redirect:/admin/");
        return modelAndView;
    }

    @GetMapping(value = "/index")
    public ModelAndView indexPage(ModelAndView modelAndView) {
        modelAndView.setViewName("index");
        return modelAndView;
    }




}