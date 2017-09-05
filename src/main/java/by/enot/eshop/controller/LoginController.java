package by.enot.eshop.controller;

import by.enot.eshop.dao.UserDao;
import by.enot.eshop.entity.User;
import by.enot.eshop.exception.NoSuchEntityInDBException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    @Autowired
    private UserDao userDao;
    private static final Logger log = Logger.getLogger(LoginController.class);


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView preLogin(){
        ModelAndView model = new ModelAndView("login");
        model.addObject("user", new User());
        return model;
    }

    @RequestMapping(value = "/loginGo", method = RequestMethod.POST)
    public ModelAndView login(@ModelAttribute("user") User user, HttpServletRequest request){
        ModelAndView model = null;
        try{
            User currUser = userDao.login(user.getName(), user.getPassword());
            model = new ModelAndView("redirect:/");
            request.getSession().setAttribute("User", currUser);
            log.info("User loged in with login: " +user.getName() + " and password: " + user.getPassword() + " , ip: " + request.getRemoteAddr());
        }catch (NoSuchEntityInDBException e){
            model = new ModelAndView("login");
            model.addObject("invalidLogin", "Invalid login or password");
            log.info("Failed login, ip:  " + request.getRemoteAddr(), e);
        }
        return model;
    }
}
