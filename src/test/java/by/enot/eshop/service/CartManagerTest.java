package by.enot.eshop.service;

import by.enot.eshop.dao.ProductDao;
import by.enot.eshop.entity.Product;
import by.enot.eshop.exception.NoSuchEntityInDBException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.Cookie;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;

public class CartManagerTest {
    @Mock
    private ProductDao productDao;
    @InjectMocks
    private CartManager manager;

    @Before
    public void setUp() throws NoSuchEntityInDBException {
        MockitoAnnotations.initMocks(this);
        Product product = new Product();
        product.setName("a");
        product.setCount(2);
        when(productDao.getByName("a")).thenReturn(product);
    }

    @Test
    public void testLoadCartValidCookie(){
        Cookie[] cookie = {new Cookie("yourCart", "a:1&")};
        Assert.assertNotNull(manager.loadCart(cookie));
        Assert.assertEquals(manager.loadCart(cookie).size(), 1);
    }
    @Test
    public void testLoadCartInvalidCookie(){
        Cookie[] cookie = {new Cookie("nothing", "a:1&")};
        Assert.assertNotNull(manager.loadCart(cookie));
        Assert.assertEquals(manager.loadCart(cookie).size(), 0);
    }
    @Test
    public void testSaveCart(){
        Assert.assertNotNull(manager.saveCart(new HashMap<String, Integer>()));
        Assert.assertEquals(manager.saveCart(new HashMap<String, Integer>()).getMaxAge(), 60*60*24*7);
        Assert.assertEquals(manager.saveCart(new HashMap<String, Integer>()).getName(), "yourCart");
    }
    @Test
    public void testMapToString(){
       Map<String, Integer> cart = new HashMap<String, Integer>();
       cart.put("A", 1);
       Assert.assertEquals(manager.mapToString(cart), "A:1&");
    }
    @Test
    public void testAddNewProduct(){
        Map<String, Integer> cart = new HashMap<String, Integer>();
        cart.put("A", 1);
        manager.add("A", cart);
        int aCount = cart.get("A");
        Assert.assertEquals(aCount, 2);
    }
    @Test
    public void testAddExistProduct(){
        Map<String, Integer> cart = new HashMap<String, Integer>();
        cart.put("A", 1);
        manager.add("B", cart);
        int aCount = cart.get("A");
        int bCount = cart.get("B");
        Assert.assertEquals(aCount, 1);
        Assert.assertEquals(bCount, 1);
    }
    @Test
    public void testRemoveNotLastProductInCart(){
        Map<String, Integer> cart = new HashMap<String, Integer>();
        cart.put("A", 2);
        manager.remove("A", cart);
        int aCount = cart.get("A");
        Assert.assertEquals(aCount, 1);
    }
    @Test
    public void testRemoveLastProductInCart(){
        Map<String, Integer> cart = new HashMap<String, Integer>();
        cart.put("A", 1);
        manager.remove("A", cart);
        Assert.assertEquals(cart.size(), 0);
    }
    @Test
    public void testFixCartFromFiveToTwo(){
        Map<String, Integer> cart = new HashMap<String, Integer>();
        cart.put("a", 5);
        manager.fixCart(cart);
        int aCount = cart.get("a");
        Assert.assertEquals(aCount, 2);
    }
    @Test
    public void testFixCartCartDoNotNeedToBeFixed(){
        Map<String, Integer> cart = new HashMap<String, Integer>();
        cart.put("a", 1);
        manager.fixCart(cart);
        int aCount = cart.get("a");
        Assert.assertEquals(aCount, 1);
    }
}
