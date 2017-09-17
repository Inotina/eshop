package by.enot.eshop.service;

import by.enot.eshop.dao.ProductDao;
import by.enot.eshop.entity.Product;
import by.enot.eshop.entity.Purchase;
import by.enot.eshop.exception.NoSuchEntityInDBException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;

public class PurchaseServiceTest {

    private Purchase goodPurchase, badPurchase;
    @Mock
    private ProductDao productDao;
    @Mock
    private CartManager manager;
    @InjectMocks
    private PurchaseService purchaseService;

    @Before
    public void setUp() throws NoSuchEntityInDBException {
        MockitoAnnotations.initMocks(this);
        Product product = new Product();
        product.setName("product");
        product.setCount(1);
        when(productDao.getByName("product")).thenReturn(product);
        when(productDao.getByName("noProduct")).thenThrow(new NoSuchEntityInDBException());
        goodPurchase = new Purchase();
        goodPurchase.setProducts("not empty");
        goodPurchase.setAdress("not empty");
        goodPurchase.setPhone("not empty");
        goodPurchase.setId(1);
        goodPurchase.setDate(null);
        badPurchase = new Purchase();
        badPurchase.setProducts("");
        badPurchase.setAdress("");
        badPurchase.setPhone("");
        badPurchase.setId(1);
        badPurchase.setDate(null);
    }

    @Test
    public void testValidClientIdValidClientid(){
        Assert.assertTrue(purchaseService.validClientid(goodPurchase));
    }

    @Test
    public void testValidPhoneValidPhone(){
        Assert.assertTrue(purchaseService.validPhone(goodPurchase));
    }

    @Test
    public void testValidPhoneInvalidPhone(){
        Assert.assertFalse(purchaseService.validPhone(badPurchase));
    }

    @Test
    public void testValidAdressValidAdress(){
        Assert.assertTrue(purchaseService.validAdress(goodPurchase));
    }
    @Test
    public void testValidAdressInvalidAdress(){
        Assert.assertFalse(purchaseService.validAdress(badPurchase));
    }
    @Test
    public void testValidDateNoDate(){
        Assert.assertTrue(purchaseService.validDate(goodPurchase));
        Assert.assertNotNull(goodPurchase.getDate());
    }
    @Test
    public void testIsValidValidPurchaseNoCartArg(){
        Assert.assertTrue(purchaseService.isValid(goodPurchase));
    }
    @Test
    public void testIsValidInvalidPurchaseNoCartArg(){
        Assert.assertFalse(purchaseService.isValid(badPurchase));
    }
    @Test
    public void testValidCartEmptyCart(){
        Assert.assertFalse(purchaseService.validCart(goodPurchase, new HashMap<String, Integer>()));
    }
    @Test
    public void testValidCartNotEmptyValidCart(){
        Map<String, Integer> cart = new HashMap<String, Integer>();
        cart.put("product", 1);
        Assert.assertTrue(purchaseService.validCart(goodPurchase, cart));
    }
    @Test
    public void testValidCartNotEmptyInvalidCartProductcartCountGreaterThanInDb(){
        Map<String, Integer> cart = new HashMap<String, Integer>();
        cart.put("product", 2);
        Assert.assertFalse(purchaseService.validCart(goodPurchase, cart));
    }
    @Test
    public void testValidCartNotEmptyInvalidCartProductInCartIsNotInDb(){
        Map<String, Integer> cart = new HashMap<String, Integer>();
        cart.put("noProduct", 1);
        Assert.assertFalse(purchaseService.validCart(goodPurchase, cart));
    }
    @Test
    public void testIsValidValidPurchaseAndCartArg(){
        Map<String, Integer> cart = new HashMap<String, Integer>();
        cart.put("product", 1);
        Assert.assertTrue(purchaseService.isValid(goodPurchase, cart));
    }
    @Test
    public void testIsValidValidPurchaseAndInvalidCartArg(){
        Map<String, Integer> cart = new HashMap<String, Integer>();
        cart.put("product", 2);
        Assert.assertFalse(purchaseService.isValid(goodPurchase, cart));
    }
    @Test
    public void testUpdateProductCountThreeProductInCart(){
        Map<String, Integer> cart = new HashMap<String, Integer>();
        cart.put("product", 1);
        purchaseService.updateProductCount(cart);
    }

}
