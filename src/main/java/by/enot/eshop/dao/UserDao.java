package by.enot.eshop.dao;

import by.enot.eshop.entity.User;
import by.enot.eshop.exception.NoSuchEntityInDBException;

import java.util.List;

public interface UserDao {

    public List<User> getAll();

    public User getByName(String name) throws NoSuchEntityInDBException;

    public User getByEmail(String email) throws NoSuchEntityInDBException;

    public User login(String name, String password) throws NoSuchEntityInDBException;

    public void delete(User user);

    public void update(User user);

    public void add(User user);
}
