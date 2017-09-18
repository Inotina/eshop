package by.enot.eshop.controller;

import by.enot.eshop.service.CartManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

//manage cart. add/remove product or clear cart.
@Controller
public class CartController {

    @Autowired
    private CartManager manager;
    private static final Logger log = Logger.getLogger(CartController.class);

    //all cart actions
    @RequestMapping("/cart")
    public ModelAndView action(@RequestParam("cart") String action, @SessionAttribute("Cart") Map<String, Integer> cart,
                               HttpServletResponse response, HttpServletRequest request){
        Cookie cookie = null;
        //take product name from request
        String name = request.getParameter("name");
        log.debug("Command: " + action + " product name: " + name);
        //code for add
        if (action.equals("add")){
            manager.add(name, cart);
            //update cookie
            cookie = manager.saveCart(cart);
        //code for remove
        }else if (action.equals("remove")){
            manager.remove(name, cart);
            //update cookie
            cookie = manager.saveCart(cart);
        //code for clear
        }else if (action.equals("clear")){
            cart.clear();
            //update cookie
            cookie = manager.saveCart(cart);
        }
        response.addCookie(cookie);
        //return to the same page
        return new ModelAndView("redirect:" + request.getHeader("Referer"));
    }

}
