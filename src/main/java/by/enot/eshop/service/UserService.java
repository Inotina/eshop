package by.enot.eshop.service;

import by.enot.eshop.dao.UserDao;
import by.enot.eshop.entity.User;
import by.enot.eshop.exception.NoSuchEntityInDBException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private boolean isValidEmail, isValidLogin;
    @Autowired
    private UserDao userDao;
    private static final Logger log = Logger.getLogger(UserService.class);

    //check if user object is valid
    public boolean isValid(User user){
        return validateLogin(user) && validateEmail(user) && validateAdmin(user);
    }
    //check if login is not in use
    private boolean validateLogin(User user){
        boolean valid = false;
        try{
            userDao.getByName(user.getName());
            isValidLogin = false;
            log.debug("Login '" + user.getName() + "' is in use.");
        }catch (NoSuchEntityInDBException e){
            isValidLogin = true;
            valid = true;
            log.debug("Login '" + user.getName() + "' is free to use.", e);
        }
        return valid;
    }
    //check if email is in not in use
    private boolean validateEmail(User user){
        boolean valid = false;
        try{
            userDao.getByEmail(user.getEmail());
            isValidEmail = false;
            log.debug("Email '" + user.getEmail() + "' is in use.");
        }catch (NoSuchEntityInDBException e){
            isValidEmail = true;
            valid = true;
            log.debug("Email '" + user.getEmail() + "' is free to use.", e);
        }
        return valid;
    }
    //currently this method always returns true and set all new user not an admin
    private boolean validateAdmin(User user){
        user.setIsAdmin("N");
        return true;
    }

    public boolean isValidEmail() {
        return isValidEmail;
    }

    public boolean isValidLogin() {
        return isValidLogin;
    }
}
