package org.kodluyoruz.urlshortener.service;

import org.kodluyoruz.urlshortener.model.LoginToken;
import org.kodluyoruz.urlshortener.model.User;
import org.kodluyoruz.urlshortener.repository.LoginTokenRepository;
import org.kodluyoruz.urlshortener.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LoginTokenService extends MyEntityService<Integer, LoginToken, LoginTokenRepository>{
    @Autowired
    public LoginTokenService(LoginTokenRepository loginTokenRepository)
    {
        super(loginTokenRepository);
    }

    public LoginToken findByValue(String value)
    {
        return getRepository().findByValue(value);
    }

    public List<LoginToken> findByUser(User user)
    {
        return getRepository().findByUser(user);
    }

    private String generateValue()
    {
        String value;

        do {
            value = UUID
                    .randomUUID()
                    .toString();
        }while (existsByValue(value));
        return value;
    }

    public boolean existsByValue(String value)
    {
        return findByValue(value) != null;
    }

    private LoginToken add(LoginToken.Type type,Long expirationDuration,User user)
    {
        LoginToken loginToken = new LoginToken();
        loginToken.setType(type);
        loginToken.setExpirationDuration(expirationDuration);
        loginToken.setUser(user);
        return add(loginToken);
    }

    public LoginToken addAnonymousLoginToken()
    {
        return add(LoginToken.Type.ANONYMOUS,Constants.ANONYMOUS_LOGIN_TOKEN_EXPIRATION_DURATION,null);
    }

    public LoginToken addUserLoginToken(User user)
    {
        return add(LoginToken.Type.LOGGED_IN,Constants.LOGGED_IN_LOGIN_TOKEN_EXPIRATION_DURATION,user);
    }
}
