package service.impl;

import dao.UserDAO;
import factory.DAOFactory;
import model.User;
import service.LoginService;

/**
 * Created by raychen on 2017/1/1.
 */
public class LoginSerivceImpl implements LoginService {
    private DAOFactory factory = DAOFactory.getInstance();
    private UserDAO userDAO;

    public LoginSerivceImpl(){
        userDAO = factory.getUserDAO();
    }

    @Override
    public User getUserByUsername(String username) {
        return userDAO.findUserByUsername(username);
    }
}
