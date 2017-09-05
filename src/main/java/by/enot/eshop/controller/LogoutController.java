package by.enot.eshop.controller;

import by.enot.eshop.entity.User;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LogoutController {

    private static final Logger log = Logger.getLogger(LogoutController.class);

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, @SessionAttribute("User") User user){
        request.getSession().removeAttribute("User");
        log.info("User '" + user.getName() + "' loged out.");
        return "redirect:/";
    }
}
