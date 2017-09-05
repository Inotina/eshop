package by.enot.eshop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminNavigateController {

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView navigate(){
        ModelAndView model = new ModelAndView("admin/admin");
        return model;
    }
}
