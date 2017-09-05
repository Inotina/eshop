package by.enot.eshop.controller;

import by.enot.eshop.dao.UserDao;
import by.enot.eshop.entity.User;
import by.enot.eshop.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserDao userDao;
    private static final Logger log = Logger.getLogger(RegistrationController.class);

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView preRegister(){
        ModelAndView model = new ModelAndView("register");
        model.addObject("user", new User());
        return model;
    }

    @Transactional
    @RequestMapping(value = "/registerGo", method = RequestMethod.POST)
    public ModelAndView register(@ModelAttribute("user") User user, HttpServletRequest request){
        ModelAndView model = null;
        if (userService.isValid(user)){
            userDao.add(user);
            model = new ModelAndView("redirect:/");
            request.getSession().setAttribute("User", user);
            log.info("User: " + user.getName() + " loged in. ip " + request.getRemoteAddr());
        }else{
            model = new ModelAndView("register");
            String errMessage = "Error occurred:";
            if (!userService.isValidLogin()){
                errMessage += " login is already in use";
                user.setName(null);
            }
            if (!userService.isValidEmail()){
                errMessage += " email is already in use";
                user.setEmail(null);
            }
            errMessage += ".";
            model.addObject("regError", errMessage);
            model.addObject("baduser", user);
            log.info("Bad attempt to login by user: " + user.getName() + ", ip " + request.getRemoteAddr());
        }
        return model;
    }
}
