package by.enot.eshop.controller;

import by.enot.eshop.dao.UserDao;
import by.enot.eshop.entity.User;
import by.enot.eshop.exception.NoSuchEntityInDBException;
import by.enot.eshop.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

// user login/logout/register operations
@Controller
public class UserController {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserService userService;
    private static final Logger log = Logger.getLogger(UserController.class);


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView preLogin(){
        ModelAndView model = new ModelAndView("login");
        model.addObject("user", new User());
        return model;
    }
    //ckeck input data and make decision about login
    @RequestMapping(value = "/loginGo", method = RequestMethod.POST)
    public ModelAndView login(@ModelAttribute("user") User user, HttpServletRequest request){
        ModelAndView model = null;
        //if login and password are correct add user as session atr "User" and redirect to main page
        try{
            User currUser = userDao.login(user.getName(), user.getPassword());
            model = new ModelAndView("redirect:/");
            request.getSession().setAttribute("User", currUser);
            log.info("User loged in with login: " +user.getName() + " and password: " + user.getPassword() + " , ip: " + request.getRemoteAddr());
        }catch (NoSuchEntityInDBException e){
            //not valid login or pasword. add error message to login page
            model = new ModelAndView("login");
            model.addObject("invalidLogin", "Invalid login or password");
            log.info("Failed login, ip:  " + request.getRemoteAddr(), e);
        }
        return model;
    }
    //logout by removing session atr "User"
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, @SessionAttribute("User") User user){
        request.getSession().removeAttribute("User");
        log.info("User '" + user.getName() + "' loged out.");
        return "redirect:/";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView preRegister(){
        ModelAndView model = new ModelAndView("register");
        model.addObject("user", new User());
        return model;
    }

    //make dicision about user registration
    @Transactional
    @RequestMapping(value = "/registerGo", method = RequestMethod.POST)
    public ModelAndView register(@ModelAttribute("user") User user, HttpServletRequest request){
        ModelAndView model = null;
        //chek if user is valid
        if (userService.isValid(user)){
            userDao.add(user);
            //login user after regisration and redirect to main page
            model = new ModelAndView("redirect:/");
            request.getSession().setAttribute("User", user);
            log.info("User: " + user.getName() + " loged in. ip " + request.getRemoteAddr());
        }else{
            //if user is not valid add error message to view
            model = new ModelAndView("register");
            String errMessage = "Error occurred:";
            //if login is already in use
            if (!userService.isValidLogin()){
                errMessage += " login is already in use";
                user.setName(null);
            }
            //if email is already in use
            if (!userService.isValidEmail()){
                errMessage += " email is already in use";
                user.setEmail(null);
            }
            errMessage += ".";
            model.addObject("regError", errMessage);
            //add not valid user for fitting valid registartion forms on view
            model.addObject("baduser", user);
            log.info("Bad attempt to login by user: " + user.getName() + ", ip " + request.getRemoteAddr());
        }
        return model;
    }
}
