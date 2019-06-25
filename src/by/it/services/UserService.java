package by.it.services;

import by.it.entities.User;

public interface UserService {

    User createUser(String name, String login, String password);

    User authorization(String login, String password);
}
