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

import static org.mockito.Mockito.when;

public class ProductServiceTest {
    @Mock
    private ProductDao productDao;
    @InjectMocks
    private ProductService productService;

    @Before
    public void setUp() throws NoSuchEntityInDBException {
        MockitoAnnotations.initMocks(this);
        Product existProduct = new Product();
        existProduct.setName("exist");
        Product oldProduct = new Product();
        oldProduct.setName("oldName");
        when(productDao.getByName("exist")).thenReturn(existProduct);
        when(productDao.getByName("oldName")).thenReturn(oldProduct);
        when(productDao.getByName("newName")).thenThrow(new NoSuchEntityInDBException());
    }

    @Test
    public void testIsValidNameEmptyName(){
        Product product = new Product();
        product.setName("");
        Assert.assertFalse(productService.isValidName(product));
    }
    @Test
    public void testIsValidNameExistingName(){
        Product product = new Product();
        product.setName("exist");
        Assert.assertFalse(productService.isValidName(product));
    }
    @Test
    public void testIsValidNameValidName(){
        Product product = new Product();
        product.setName("newName");
        Assert.assertTrue(productService.isValidName(product));
    }
    @Test
    public void testIsValidNameCurrentProductModificationEmptyName(){
        Product product = new Product();
        product.setName("");
        Assert.assertFalse(productService.isValidNameCurrentProductModification(product, "oldName"));
    }
    @Test
    public void testIsValidNameCurrentProductModificationExistingName(){
        Product product = new Product();
        product.setName("exist");
        Assert.assertFalse(productService.isValidNameCurrentProductModification(product, "oldName"));
    }
    @Test
    public void testIsValidNameCurrentProductModificationValidName(){
        Product product = new Product();
        product.setName("newName");
        Assert.assertTrue(productService.isValidNameCurrentProductModification(product, "oldName"));
    }
    @Test
    public void testIsValidNameCurrentProductModificationCurrentName(){
        Product product = new Product();
        product.setName("oldName");
        Assert.assertTrue(productService.isValidNameCurrentProductModification(product, "oldName"));
    }
    @Test
    public void testIsValidPricePositivePrice(){
        Product product = new Product();
        product.setPrice(10);
        Assert.assertTrue(productService.isValidPrice(product));
    }
    @Test
    public void testIsValidPricNegativePrice(){
        Product product = new Product();
        product.setPrice(-1);
        Assert.assertFalse(productService.isValidPrice(product));
    }
    @Test
    public void testIsValidPriceZeroPrice(){
        Product product = new Product();
        product.setPrice(0);
        Assert.assertTrue(productService.isValidPrice(product));
    }
    @Test
    public void testIsValidCountPositiveCount(){
        Product product = new Product();
        product.setCount(10);
        Assert.assertTrue(productService.isValidCount(product));
    }
    @Test
    public void testIsValidCountNegativeCount(){
        Product product = new Product();
        product.setCount(-1);
        Assert.assertFalse(productService.isValidCount(product));
    }
    @Test
    public void testIsValidCountZeroCount(){
        Product product = new Product();
        product.setCount(0);
        Assert.assertTrue(productService.isValidCount(product));
    }
    @Test
    public void testValidateValidProduct(){
        Product product = new Product("newName", 22, 10);
        Assert.assertTrue(productService.validate(product));
    }
    @Test
    public void testValidateAllFieldsInvalidProduct(){
        Product product = new Product("", -22, -10);
        Assert.assertFalse(productService.validate(product));
    }
}
