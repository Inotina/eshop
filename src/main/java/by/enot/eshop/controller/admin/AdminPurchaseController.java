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

// update/delete purchases, create table with all purchases
@Controller
public class AdminPurchaseController {
    @Autowired
    private PurchaseDao purchaseDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private PurchaseService purchaseService;
    private static final Logger log = Logger.getLogger(AdminPurchaseController.class);

    //create table with all purchases where user can select one for updating/deleting
    @RequestMapping(value = "/admin/purchases", method = RequestMethod.GET)
    public ModelAndView onload(){
        ModelAndView model = new ModelAndView("admin/adminpurchase");
        model.addObject("Purchase", purchaseDao.getAll());
        return model;
    }
    //fit the form for edeting/deleting selected purchase
    @RequestMapping(value = "admin/purchases", method = RequestMethod.POST)
    public ModelAndView selected(@RequestParam("row")long purchaseId){
        //create table with all purchases where user can select one for updating/deleting
        ModelAndView model = onload();
        try {
            model.addObject("target", purchaseDao.getByID(purchaseId));
        } catch (NoSuchEntityInDBException e) {
            e.printStackTrace();
        }
        return model;
    }
    //make decision about updating/deleting purchase and return it to view(done or failed)
    @Transactional
    @RequestMapping(value = "/admin/purchasechange", method = RequestMethod.POST)
    public ModelAndView changePurchases(@ModelAttribute("target") Purchase purchase, @RequestParam("bt") String button,
                                        @RequestParam("id") long id, @RequestParam("clientname") String name) {
        ModelAndView model = new ModelAndView("admin/adminpurchase");
        //code for update
        if (button.equals("Update")) {
            //restore User object from db
            try {
                purchase.setClient(userDao.getByName(name));
            } catch (NoSuchEntityInDBException e) {
                log.debug(e);
            }
            //check if all field are valid
            if (purchaseService.isValid(purchase)) {
                purchase.setId(id);
                //apply changes and restore missing on view fields
                purchase = purchaseService.applyChanges(purchase);
                purchaseDao.update(purchase);
                model.addObject("message", "Successfully updated");
                log.debug("Purchase with id: " + purchase.getId() + " updated.");
            } else {
                //if not valid add message on view what is wrong
                model.addObject("errMessage", purchaseService.getErrorMessage());
                purchaseService.clearErrorMessage();
                log.debug("Error updating purchase with id: " + purchase.getId());
            }
            model.addObject("target", purchase);
        //code for delete
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
