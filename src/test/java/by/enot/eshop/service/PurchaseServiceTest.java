package by.enot.eshop.service;

import by.enot.eshop.dao.ProductDao;
import by.enot.eshop.dao.PurchaseDao;
import by.enot.eshop.entity.Product;
import by.enot.eshop.entity.Purchase;
import by.enot.eshop.entity.PurchaseItem;
import by.enot.eshop.entity.User;
import by.enot.eshop.exception.NoSuchEntityInDBException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

import java.util.*;

public class PurchaseServiceTest {

    private Purchase goodPurchase, badPurchase;
    @Mock
    private ProductDao productDao;
    @Mock
    private PurchaseDao purchaseDao;
    @Mock
    private User user;
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
        goodPurchase.setClient(user);
        goodPurchase.setAdress("not empty");
        goodPurchase.setPhone("not empty");
        goodPurchase.setId(1);
        goodPurchase.setDate(null);
        goodPurchase.setProducts(new ArrayList<PurchaseItem>(Arrays.asList(new PurchaseItem())));
        when(purchaseDao.getByID(1)).thenReturn(goodPurchase);
        badPurchase = new Purchase();
        badPurchase.setClient(user);
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
    @Test
    public void testCartToPurchaseItemsEmtyCart(){
        Map<String, Integer> cart = new HashMap<String, Integer>();
        Assert.assertEquals(purchaseService.cartToPurchaseItems(cart, goodPurchase).size(), 0);
    }
    @Test
    public void testCartToPurchaseItemsCartWithOneUniqueProduct(){
        Map<String, Integer> cart = new HashMap<String, Integer>();
        cart.put("product", 3);
        Assert.assertEquals(purchaseService.cartToPurchaseItems(cart, goodPurchase).size(), 1);
    }
    @Test
    public void testApplyChangesCountAndPriceChanged(){
        Purchase purchase = new Purchase();
//        purchase.setClient(new User());
        purchase.setId(1);
        purchase.setAdress("newAdress");
        purchase.setPhone("newPhone");
        List<PurchaseItem> list = new ArrayList<PurchaseItem>();
        list.add(new PurchaseItem());
        purchase.setProducts(list);
        purchase = purchaseService.applyChanges(purchase);
        Assert.assertEquals(purchase.getAdress(), "newAdress");
        Assert.assertEquals(purchase.getPhone(), "newPhone");
    }


}
