package by.enot.eshop.filter;

import by.enot.eshop.service.CartManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

//restore cart from cookie to session atr
public class CartExistInterceptor extends AbstractInterceptor {

    @Autowired
    private CartManager manager;
    private static final Logger log = Logger.getLogger(CartExistInterceptor.class);

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        if (request.getSession().getAttribute("Cart") == null){
            Cookie[] cookie = request.getCookies();
            //convert stored in cookie cart into hashmap
            Map<String, Integer> cart = manager.loadCart(cookie);
            request.getSession().setAttribute("Cart", cart);
            log.debug("restoring cart form cookie for ip adress: " + request.getRemoteAddr());
            return true;
        }else {
            //if session already has atr "Cart" do nothing
            return true;
        }
    }
}
