package by.enot.eshop.controller.admin;

import by.enot.eshop.dao.PurchaseDao;
import by.enot.eshop.dao.UserDao;
import by.enot.eshop.entity.Purchase;
import by.enot.eshop.exception.NoSuchEntityInDBException;
import by.enot.eshop.service.PurchaseService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;

@Controller
public class AdminPurchaseController {
    @Autowired
    private PurchaseDao purchaseDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private PurchaseService purchaseService;
    private static final Logger log = Logger.getLogger(AdminPurchaseController.class);


    @RequestMapping(value = "/admin/purchases", method = RequestMethod.GET)
    public ModelAndView onload(){
        ModelAndView model = new ModelAndView("admin/adminpurchase");
        model.addObject("Purchase", purchaseDao.getAll());
        return model;
    }
    @RequestMapping(value = "admin/purchases", method = RequestMethod.POST)
    public ModelAndView selected(@RequestParam("row")long purchaseId){
        ModelAndView model = onload();
        try {
            model.addObject("target", purchaseDao.getByID(purchaseId));
        } catch (NoSuchEntityInDBException e) {
            e.printStackTrace();
        }
        return model;
    }
    @Transactional
    @RequestMapping(value = "/admin/purchasechange", method = RequestMethod.POST)
    public ModelAndView changePurchases(@ModelAttribute("target") Purchase purchase, @RequestParam("bt") String button,
                                        @RequestParam("id") long id, @RequestParam("clientname") String name) {
        ModelAndView model = new ModelAndView("admin/adminpurchase");
        if (button.equals("Update")) {
            try {
                purchase.setClient(userDao.getByName(name));
            } catch (NoSuchEntityInDBException e) {
                log.debug(e);
            }
//            model.addObject("target", purchase);
            if (purchaseService.isValid(purchase)) {
                purchase.setId(id);
                purchase = purchaseService.applyChanges(purchase);
                purchaseService.applyChanges(purchase);
                purchaseDao.update(purchase);
                model.addObject("message", "Successfully updated");
                log.debug("Purchase with id: " + purchase.getId() + " updated.");
            } else {
                model.addObject("errMessage", purchaseService.getErrorMessage());
                purchaseService.clearErrorMessage();
                log.debug("Error updating purchase with id: " + purchase.getId());
            }
            model.addObject("target", purchase);
        }else if (button.equals("Delete")){
            purchase.setId(id);
            purchaseDao.delete(purchase);
            model.addObject("message", "Successfully deleted");
            log.debug("Purchase with id: " + purchase.getId() + " deleted.");
            //remove info about deleted obj from view
            model.addObject("target", null);
        }
        model.addObject("Purchase", purchaseDao.getAll());
        return model;
    }
}
