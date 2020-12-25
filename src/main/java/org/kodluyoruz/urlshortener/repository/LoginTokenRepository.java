package org.kodluyoruz.urlshortener.repository;

import org.kodluyoruz.urlshortener.model.LoginToken;
import org.kodluyoruz.urlshortener.model.User;

import java.util.List;

public interface LoginTokenRepository extends MyEntityRepository<Integer, LoginToken>{

    LoginToken findByValue(String value);
    List<LoginToken> findByUser(User user);
}
