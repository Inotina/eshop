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
    public void test_validClientId_valid_clientid(){
        Assert.assertTrue(purchaseService.validClientid(goodPurchase));
    }

    @Test
    public void test_validPhone_valid_phone(){
        Assert.assertTrue(purchaseService.validPhone(goodPurchase));
    }

    @Test
    public void test_validPhone_invalid_phone(){
        Assert.assertFalse(purchaseService.validPhone(badPurchase));
    }

    @Test
    public void test_validAdress_valid_adress(){
        Assert.assertTrue(purchaseService.validAdress(goodPurchase));
    }
    @Test
    public void test_validAdress_invalid_adress(){
        Assert.assertFalse(purchaseService.validAdress(badPurchase));
    }
    @Test
    public void test_validDate_no_date(){
        Assert.assertTrue(purchaseService.validDate(goodPurchase));
        Assert.assertNotNull(goodPurchase.getDate());
    }
    @Test
    public void test_isValid_valid_purchase_no_cart_arg(){
        Assert.assertTrue(purchaseService.isValid(goodPurchase));
    }
    @Test
    public void test_isValid_invalid_purchase_no_cart_arg(){
        Assert.assertFalse(purchaseService.isValid(badPurchase));
    }
    @Test
    public void test_validCart_empty_cart(){
        Assert.assertFalse(purchaseService.validCart(goodPurchase, new HashMap<String, Integer>()));
    }
    @Test
    public void test_validCart_not_empty_valid_cart(){
        Map<String, Integer> cart = new HashMap<String, Integer>();
        cart.put("product", 1);
        Assert.assertTrue(purchaseService.validCart(goodPurchase, cart));
    }
    @Test
    public void test_validCart_not_empty_invalid_cart_productcart_count_greater_than_in_db(){
        Map<String, Integer> cart = new HashMap<String, Integer>();
        cart.put("product", 2);
        Assert.assertFalse(purchaseService.validCart(goodPurchase, cart));
    }
    @Test
    public void test_validCart_not_empty_invalid_cart_product_in_cart_is_not_in_db(){
        Map<String, Integer> cart = new HashMap<String, Integer>();
        cart.put("noProduct", 1);
        Assert.assertFalse(purchaseService.validCart(goodPurchase, cart));
    }
    @Test
    public void test_isValid_valid_purchase_and_cart_arg(){
        Map<String, Integer> cart = new HashMap<String, Integer>();
        cart.put("product", 1);
        Assert.assertTrue(purchaseService.isValid(goodPurchase, cart));
    }
    @Test
    public void test_isValid_valid_purchase_and_invalid_cart_arg(){
        Map<String, Integer> cart = new HashMap<String, Integer>();
        cart.put("product", 2);
        Assert.assertFalse(purchaseService.isValid(goodPurchase, cart));
    }
    @Test
    public void test_updateProductCount_three_product_in_cart(){
        Map<String, Integer> cart = new HashMap<String, Integer>();
        cart.put("product", 1);
        purchaseService.updateProductCount(cart);
    }

}
