package org.kodluyoruz.urlshortener.repository;

import org.kodluyoruz.urlshortener.model.Link;
import org.kodluyoruz.urlshortener.model.LoginToken;
import org.kodluyoruz.urlshortener.model.User;

import java.util.List;

public interface LinkRepository extends MyEntityRepository<Integer, Link>{

    Link findByShortUrl(String shortUrl);
    List<Link> findByLoginToken(LoginToken loginToken);
    List<Link> findByUser(User user);
}
