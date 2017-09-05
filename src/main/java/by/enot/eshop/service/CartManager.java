package by.enot.eshop.service;

import by.enot.eshop.dao.ProductDao;
import by.enot.eshop.entity.Product;
import by.enot.eshop.exception.NoSuchEntityInDBException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.util.*;

@Service
public class CartManager {

    @Autowired
    private ProductDao productDao;
    private static final Logger log = Logger.getLogger(CartManager.class);


    public Map<String, Integer> loadCart(Cookie[] cookie) {
        Map<String, Integer> result = null;
        try {
            Cookie targetCookie = findCookie("yourCart", cookie);
            result = stringToMap(targetCookie.getValue());
        } catch (NoSuchElementException e) {
            result = new HashMap<String, Integer>();
            log.debug(e);
        }
        return result;
    }

    private Cookie findCookie(String name, Cookie[] cookie) throws NoSuchElementException {
        Cookie result = null;
        for (int i = 0; i < cookie.length; i++) {
            if (cookie[i].getName().equals(name)) {
                result = cookie[i];
                break;
            }
        }
        if (result == null) {
            throw new NoSuchElementException("Client has no cookie.");
        }
        return result;
    }

    private Map<String, Integer> stringToMap(String str){
        if (str == null){
            return new HashMap<String, Integer>();
        }
        List<String> parcedStr = Arrays.asList(str.split("&"));
        Map<String, Integer> result = new HashMap<String, Integer>();
        for (String currStr : parcedStr){
            String[] tuple = currStr.split(":");
            if (tuple.length < 2){
                break;
            }
            try {
                Product product = productDao.getByName(tuple[0]);
                int productCount = Integer.parseInt(tuple[1]);
                result.put(product.getName(), productCount);
            }catch (NoSuchEntityInDBException e){
                log.debug(e);
            }catch (NumberFormatException e){
                log.debug("corrupted cookie value.", e);
                break;
            }
        }
        return result;
    }

    public Cookie saveCart(Map<String, Integer> cart){
        Cookie result = new Cookie("yourCart", mapToString(cart));
        result.setMaxAge(60 * 60 * 24 * 7);
        return result;
    }

    public String mapToString(Map<String, Integer> cart) {
        StringBuilder cartStr = new StringBuilder();
        for (Map.Entry<String, Integer> entry : cart.entrySet()){
            cartStr.append(entry.getKey());
            cartStr.append(":");
            cartStr.append(entry.getValue().toString());
            cartStr.append("&");
        }
        return cartStr.toString();
    }

    public void add(String name, Map<String, Integer> cart){
        if (cart.containsKey(name)){
            cart.put(name, cart.get(name) + 1);
        }else {
            cart.put(name, 1);
        }
    }

    public void remove(String name, Map<String, Integer> cart){
        if (cart.get(name) > 1){
            cart.put(name, cart.get(name) - 1);
        }else {
            cart.remove(name);
        }
    }

    public Cookie fixCart(Map<String, Integer > cart){
        for (Map.Entry<String, Integer> entry : cart.entrySet()){
            Product currProduct = null;
            try {
                currProduct = productDao.getByName(entry.getKey());
            } catch (NoSuchEntityInDBException e) {
                log.debug(e);
            }
            if (entry.getValue() > currProduct.getCount()){
                cart.put(entry.getKey(), currProduct.getCount());
            }
        }
        return saveCart(cart);
    }


}
