package by.enot.eshop.dao;

import by.enot.eshop.entity.Purchase;
import by.enot.eshop.exception.NoSuchEntityInDBException;

import java.util.List;

public interface PurchaseDao {

    public List<Purchase> getAll();

    public Purchase getByID(int id) throws NoSuchEntityInDBException;

    public void add(Purchase purchase);

    public void update(Purchase purchase);

    public void delete(Purchase purchase);
}
