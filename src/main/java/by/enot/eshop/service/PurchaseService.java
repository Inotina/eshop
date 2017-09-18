package by.enot.eshop.service;

import by.enot.eshop.dao.ProductDao;
import by.enot.eshop.dao.PurchaseDao;
import by.enot.eshop.dao.UserDao;
import by.enot.eshop.entity.Product;
import by.enot.eshop.entity.Purchase;
import by.enot.eshop.entity.PurchaseItem;
import by.enot.eshop.entity.User;
import by.enot.eshop.exception.NoSuchEntityInDBException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class PurchaseService {
    private String errorMessage = "Error occurred:";
    @Autowired
    private CartManager manager;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private PurchaseDao purchaseDao;
    private static final Logger log = Logger.getLogger(PurchaseService.class);


    public String getErrorMessage() {
        return errorMessage;
    }

    public void clearErrorMessage() {
        this.errorMessage = "Error occurred:";
    }

    public boolean isValid(Purchase purchase) {
        log.debug("validating purchase: " + purchase.getId());
        return validClientid(purchase) & validAdress(purchase) & validDate(purchase) & validPhone(purchase);
    }

    public boolean isValid(Purchase purchase, Map<String, Integer> cart) {
        return validClientid(purchase) & validAdress(purchase) & validDate(purchase) & validPhone(purchase) & validCart(purchase, cart);
    }


    public boolean validClientid(Purchase purchase) {
        if (purchase.getClient() == null){
            errorMessage += "No such client in db";
            return false;
        }else {
            return true;
        }
    }

    public boolean validPhone(Purchase purchase) {
        if (purchase.getPhone().length() < 1) {
            errorMessage += " empty phone field";
            return false;
        } else {
            return true;
        }
    }

    public boolean validAdress(Purchase purchase) {
        if (purchase.getAdress().length() < 1) {
            errorMessage += " empty adress field";
            return false;
        } else {
            return true;
        }
    }

    public boolean validDate(Purchase purchase) {
        if (purchase.getDate() == null) {
            purchase.setDate(new Date().toString());
        }
        return true;
    }

    public boolean validCart(Purchase purchase, Map<String, Integer> cart) {
        if (cart.isEmpty()) {
            errorMessage += " empty cart";
            return false;
        }
        boolean validCart = true;
        for (Map.Entry<String, Integer> entry : cart.entrySet()) {
            Product currProduct = null;
            try {
                currProduct = productDao.getByName(entry.getKey());
                if (entry.getValue() > currProduct.getCount()) {
                    errorMessage += " Products count in cart was changed!";
                    return false;
                }
            } catch (NoSuchEntityInDBException e) {
                errorMessage += "Product: " + entry.getKey() + " removed from cart!";
                log.info("Product from cart wasn't found in db.", e);
                return false;
            }
        }
        purchase.setProducts(cartToPurchaseItems(cart, purchase));
        return true;
    }
    @Transactional
    public void updateProductCount(Map<String, Integer> cart) {
        for (Map.Entry<String, Integer> entry : cart.entrySet()) {
            Product product = null;
            try {
                product = productDao.getByName(entry.getKey());
            } catch (NoSuchEntityInDBException e) {
                log.info("Product from cart wasn't found in db.", e);
            }
            product.setCount(product.getCount() - entry.getValue());
            productDao.update(product);
        }
    }

    public List<PurchaseItem> cartToPurchaseItems(Map<String, Integer> cart, Purchase purchase){
        List<PurchaseItem> result = new ArrayList<PurchaseItem>();
        for (Map.Entry<String, Integer> entry : cart.entrySet()){
            Product product = null;
            try {
                product = productDao.getByName(entry.getKey());
            } catch (NoSuchEntityInDBException e) {
                log.debug(e);
            }
            result.add(new PurchaseItem(product, entry.getValue().intValue(), product.getPrice(), purchase));
        }
        return result;
    }

    public Purchase applyChanges(Purchase purchase){
        Purchase result = null;
        try {
            result = purchaseDao.getByID(purchase.getId());
        } catch (NoSuchEntityInDBException e) {
            log.debug(e);
        }
        result.setClient(purchase.getClient());
        result.setPhone(purchase.getPhone());
        result.setAdress(purchase.getAdress());
        for (int i = 0; i < purchase.getProducts().size(); i++){
            result.getProducts().get(i).setCount(purchase.getProducts().get(i).getCount());
            result.getProducts().get(i).setPrice(purchase.getProducts().get(i).getPrice());
        }
        return result;
    }
}
