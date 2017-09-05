package by.enot.eshop.controller;

import by.enot.eshop.dao.PurchaseDao;
import by.enot.eshop.entity.Purchase;
import by.enot.eshop.service.CartManager;
import by.enot.eshop.service.PurchaseService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.Map;

@Controller
public class PurchaseController {
    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private PurchaseDao purchaseDao;
    @Autowired
    private CartManager manager;
    private static final Logger log = Logger.getLogger(PurchaseController.class);

    @RequestMapping(value = "/purchase", method = RequestMethod.GET)
    public ModelAndView prePurchase(){
        ModelAndView model = new ModelAndView("purchase");
        model.addObject("purchase", new Purchase());
        return model;
    }
    @Transactional
    @RequestMapping(value = "/purchase", method = RequestMethod.POST)
    public ModelAndView purchase(@ModelAttribute("purchase") Purchase purchase, @SessionAttribute("Cart") Map<String, Integer> cart, HttpServletResponse response) throws Exception {
        ModelAndView model = null;
        if (purchaseService.isValid(purchase, cart)){
            purchaseDao.add(purchase);
            purchaseService.updateProductCount(cart);
            model = new ModelAndView("confirmed");
            cart.clear();
            response.addCookie(manager.saveCart(cart));
            log.debug("New purchase, client id: " +purchase.getClientId());
        }else{
            model = new ModelAndView("purchase");
            model.addObject("errMessage", purchaseService.getErrorMessage());
            manager.fixCart(cart);
            response.addCookie(manager.saveCart(cart));
            log.debug("Not valid purchase, client id: " + purchase.getClientId() + ". Error message: " + purchaseService.getErrorMessage());
            purchaseService.clearErrorMessage();
        }

        return model;
    }
}
