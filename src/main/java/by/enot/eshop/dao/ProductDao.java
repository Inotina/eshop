package by.enot.eshop.dao;

import by.enot.eshop.entity.Product;
import by.enot.eshop.exception.NoSuchEntityInDBException;

import java.util.List;

public interface ProductDao {

    public List<Product> getAll();

    public Product getById(long id) throws NoSuchEntityInDBException;

    public Product getByName(String name) throws NoSuchEntityInDBException;

    public void add(Product product);

    public void update(Product product);

    public void delete(Product product);
}
