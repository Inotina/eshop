package by.enot.eshop.service;

import by.enot.eshop.dao.UserDao;
import by.enot.eshop.entity.User;
import by.enot.eshop.exception.NoSuchEntityInDBException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class UserServiceTest {
    @Mock
    private UserDao userDao;
    @InjectMocks
    private UserService userService;

    @Before
    public void setUp() throws NoSuchEntityInDBException {
        MockitoAnnotations.initMocks(this);
        when(userDao.getByName("bad")).thenReturn(new User());
        when(userDao.getByEmail("bad")).thenReturn(new User());
        when(userDao.getByName("good")).thenThrow(new NoSuchEntityInDBException());
        when(userDao.getByEmail("good")).thenThrow(new NoSuchEntityInDBException());
    }
    @Test
    public void testIsValidNameValidEmailValid(){
        User user = new User();
        user.setEmail("good");
        user.setName("good");
        Assert.assertTrue(userService.isValid(user));
    }
    @Test
    public void testIsValidNameValidEmailInvalid(){
        User user = new User();
        user.setEmail("bad");
        user.setName("good");
        Assert.assertFalse(userService.isValid(user));
    }
    @Test
    public void testIsValidNameInvalidEmailValid(){
        User user = new User();
        user.setEmail("good");
        user.setName("bad");
        Assert.assertFalse(userService.isValid(user));
    }
    @Test
    public void testIsValidNameInvalidEmailInvalid(){
        User user = new User();
        user.setEmail("bad");
        user.setName("bad");
        Assert.assertFalse(userService.isValid(user));
    }
}
