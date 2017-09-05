package by.enot.eshop.service;

import by.enot.eshop.dao.ProductDao;
import by.enot.eshop.entity.Product;
import by.enot.eshop.exception.NoSuchEntityInDBException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ProductService {
    @Autowired
    private ProductDao productDao;
    private String errorMessage = "Error occurred:";
    private static final Logger log = Logger.getLogger(ProductService.class);

    public String getErrorMessage() {
        return errorMessage;
    }

    public void clearErrorMessage() {
        errorMessage = "Error occured:";
    }

    @Transactional
    public boolean validate(Product product) {
        log.debug("validating product with name: " + product.getName());
        return isValidCount(product) & isValidName(product) & isValidPrice(product);
    }
    @Transactional
    public boolean validateCurrentModification(Product product, String oldName) {
        log.debug("validating product for update with id: " +product.getId());
        return isValidCount(product) & isValidNameCurrentProductModification(product, oldName) & isValidPrice(product);
    }

    public boolean isValidName(Product product) {
        if (product.getName().length() < 1) {
            errorMessage += " empty name field";
            return false;
        }
        try {
            productDao.getByName(product.getName());
            errorMessage += " product with this name already exists";
            return false;
        } catch (NoSuchEntityInDBException e) {
            return true;
        }
    }

    public boolean isValidNameCurrentProductModification(Product product, String oldName) {
        if (product.getName().length() < 1) {
            errorMessage += " empty name field";
            return false;
        }
        try {
            Product currProduct = productDao.getByName(product.getName());
            if (currProduct.getName().equals(oldName)){
                return true;
            }
            errorMessage += " product with this name already exists";
            return false;
        } catch (NoSuchEntityInDBException e) {
            return true;
        }
    }

    public boolean isValidPrice(Product product) {
        if (product.getPrice() < 0) {
            errorMessage += " negative price";
            return false;
        }
        return true;
    }

    public boolean isValidCount(Product product) {
        if (product.getCount() < 0) {
            errorMessage += " negative count";
            return false;
        }
        return true;
    }
}
