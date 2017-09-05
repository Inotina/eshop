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
    public void test_isValidName_empty_name(){
        Product product = new Product();
        product.setName("");
        Assert.assertFalse(productService.isValidName(product));
    }
    @Test
    public void test_isValidName_existing_name(){
        Product product = new Product();
        product.setName("exist");
        Assert.assertFalse(productService.isValidName(product));
    }
    @Test
    public void test_isValidName_valid_name(){
        Product product = new Product();
        product.setName("newName");
        Assert.assertTrue(productService.isValidName(product));
    }
    @Test
    public void test_isValidNameCurrentProductModification_empty_name(){
        Product product = new Product();
        product.setName("");
        Assert.assertFalse(productService.isValidNameCurrentProductModification(product, "oldName"));
    }
    @Test
    public void test_isValidNameCurrentProductModification_existing_name(){
        Product product = new Product();
        product.setName("exist");
        Assert.assertFalse(productService.isValidNameCurrentProductModification(product, "oldName"));
    }
    @Test
    public void test_isValidNameCurrentProductModification_valid_name(){
        Product product = new Product();
        product.setName("newName");
        Assert.assertTrue(productService.isValidNameCurrentProductModification(product, "oldName"));
    }
    @Test
    public void test_isValidNameCurrentProductModification_current_name(){
        Product product = new Product();
        product.setName("oldName");
        Assert.assertTrue(productService.isValidNameCurrentProductModification(product, "oldName"));
    }
    @Test
    public void test_isValidPrice_positive_price(){
        Product product = new Product();
        product.setPrice(10);
        Assert.assertTrue(productService.isValidPrice(product));
    }
    @Test
    public void test_isValidPrice_negative_price(){
        Product product = new Product();
        product.setPrice(-1);
        Assert.assertFalse(productService.isValidPrice(product));
    }
    @Test
    public void test_isValidPrice_zero_price(){
        Product product = new Product();
        product.setPrice(0);
        Assert.assertTrue(productService.isValidPrice(product));
    }
    @Test
    public void test_isValidCount_positive_count(){
        Product product = new Product();
        product.setCount(10);
        Assert.assertTrue(productService.isValidCount(product));
    }
    @Test
    public void test_isValidCount_negative_count(){
        Product product = new Product();
        product.setCount(-1);
        Assert.assertFalse(productService.isValidCount(product));
    }
    @Test
    public void test_isValidCount_zero_count(){
        Product product = new Product();
        product.setCount(0);
        Assert.assertTrue(productService.isValidCount(product));
    }
    @Test
    public void test_validate_valid_product(){
        Product product = new Product("newName", 22, 10);
        Assert.assertTrue(productService.validate(product));
    }
    @Test
    public void test_validate_all_fields_invalid_product(){
        Product product = new Product("", -22, -10);
        Assert.assertFalse(productService.validate(product));
    }
}
