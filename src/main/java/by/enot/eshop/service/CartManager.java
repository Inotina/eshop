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

    //convert saved on client cart to hashmap
    public Map<String, Integer> loadCart(Cookie[] cookie) {
        Map<String, Integer> result = null;
        try {
            Cookie targetCookie = findCookie("yourCart", cookie);
            result = stringToMap(targetCookie.getValue());
        } catch (NoSuchElementException e) {
            //if it's first visit create empty cart
            result = new HashMap<String, Integer>();
            log.debug(e);
        }
        return result;
    }
    //find cookie with provided name
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
    //parse string and convert it into hashmap
    private Map<String, Integer> stringToMap(String str){
        if (str == null){
            return new HashMap<String, Integer>();
        }
        //split string into pairs "product name":"count"
        List<String> parcedStr = Arrays.asList(str.split("&"));
        Map<String, Integer> result = new HashMap<String, Integer>();
        for (String currStr : parcedStr){
            //split product name and count
            String[] tuple = currStr.split(":");
            //break if string is not pair(can't be splitted)
            if (tuple.length < 2){
                break;
            }
            try {
                //check if such product is still available
                Product product = productDao.getByName(tuple[0]);
                int productCount = Integer.parseInt(tuple[1]);
                result.put(product.getName(), productCount);
            }catch (NoSuchEntityInDBException e){
                //if product is not available just ignore it
                log.debug(e);
            }catch (NumberFormatException e){
                log.debug("corrupted cookie value.", e);
                break;
            }
        }
        return result;
    }
    //create cookie with cart info
    public Cookie saveCart(Map<String, Integer> cart){
        Cookie result = new Cookie("yourCart", mapToString(cart));
        //max age is 7 days
        result.setMaxAge(60 * 60 * 24 * 7);
        return result;
    }
    //convert hashmap cart to string
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
    //add product name to cart
    public void add(String name, Map<String, Integer> cart){
        if (cart.containsKey(name)){
            cart.put(name, cart.get(name) + 1);
        }else {
            cart.put(name, 1);
        }
    }
    //remove product name from cart
    public void remove(String name, Map<String, Integer> cart){
        if (cart.get(name) > 1){
            cart.put(name, cart.get(name) - 1);
        }else {
            cart.remove(name);
        }
    }
    //fix products count in cart if it's higher than available prodct count(user can't buy more than is available)
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
