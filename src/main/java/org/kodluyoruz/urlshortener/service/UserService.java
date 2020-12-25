package org.kodluyoruz.urlshortener.service;


import org.kodluyoruz.urlshortener.model.User;
import org.kodluyoruz.urlshortener.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends MyEntityService<Integer, User, UserRepository>{

    @Autowired
    public UserService(UserRepository repository) {
        super(repository);
    }

    User findByEmail(String email)
    {
        return getRepository().findByEmail(email);
    }

    public boolean existsByEmail(String email)
    {
        return findByEmail(email) != null;
    }

    public User add(String email,String password)
    {
        if(existsByEmail(email))
            throw new IllegalArgumentException("This email has already registered :"+email);
        User user = new User();
        user.setEmail(email);
        user.encryptAndSetPassword(password);
        return add(user);
    }
}
