package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import web.dao.UserDaoImplHibernate;
import web.model.User;


@Controller
public class CrudControllerTest {
    @Autowired
    UserDaoImplHibernate userDaoImplHibernate;

    @GetMapping(value = "/add")
    public ModelAndView getAddPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        userDaoImplHibernate.createUser(new User("Pety", "Kolov",  20));
        return modelAndView;
    }

    @GetMapping(value = "/del")
    public ModelAndView getDelPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        userDaoImplHibernate.deleteUser(1L);
        userDaoImplHibernate.createUser(new User("Vasy", "Pupkin", 25));
         return modelAndView;
    }
    @GetMapping(value = "/upd")
    public ModelAndView getUpdPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        userDaoImplHibernate.updateUser(2l,"Vasy", "Pupkin",  20);
        final User user = userDaoImplHibernate.readUser(2L);
        System.out.println(user.getAge());
        return modelAndView;
    }
    @GetMapping(value = "/all")
    public ModelAndView getAllPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.addObject("all",userDaoImplHibernate.getUsers());
        return modelAndView;
    }



}