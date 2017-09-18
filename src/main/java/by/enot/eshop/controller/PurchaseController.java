package by.enot.eshop.controller;

import by.enot.eshop.dao.PurchaseDao;
import by.enot.eshop.dao.UserDao;
import by.enot.eshop.entity.Purchase;
import by.enot.eshop.entity.User;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.Map;

//checking purchase and make  dicision about adding it to db or not
@Controller
public class PurchaseController {
    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private PurchaseDao purchaseDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private CartManager manager;
    private static final Logger log = Logger.getLogger(PurchaseController.class);

    //create new purchase model
    @RequestMapping(value = "/purchase", method = RequestMethod.GET)
    public ModelAndView prePurchase(){
        ModelAndView model = new ModelAndView("purchase");
        model.addObject("purchase", new Purchase());
        return model;
    }
    //check purchase and make decision. return result to view(failed or success)
    @Transactional
    @RequestMapping(value = "/purchase", method = RequestMethod.POST)
    public ModelAndView purchase(@ModelAttribute("purchase") Purchase purchase, @SessionAttribute("Cart") Map<String, Integer> cart,
                                 HttpServletResponse response, HttpServletRequest request) throws Exception {
        ModelAndView model = null;
        //check if user is logged in. if not, add anonymous user to purchase
        Object user = request.getSession().getAttribute("User");
        if (user != null){
            purchase.setClient((User)user);
        }else{
            purchase.setClient(userDao.getByName("anonymous"));
        }
        //check if purchase and cart is valid
        if (purchaseService.isValid(purchase, cart)){
            purchaseDao.add(purchase);
            //update sold products count in db(deduct sold items)
            purchaseService.updateProductCount(cart);
            model = new ModelAndView("confirmed");
            cart.clear();
            //update cookie
            response.addCookie(manager.saveCart(cart));
            log.debug("New purchase, client id: " +purchase.getClient().getId());
        //if purchase is not valid add error message on view
        }else{
            model = new ModelAndView("purchase");
            model.addObject("errMessage", purchaseService.getErrorMessage());
            //if cart has more products that are avaliable fix the cart
            manager.fixCart(cart);
            response.addCookie(manager.saveCart(cart));
            log.debug("Not valid purchase, client id: " + purchase.getClient().getId() + ". Error message: " + purchaseService.getErrorMessage());
            purchaseService.clearErrorMessage();
        }

        return model;
    }
}
