package org.kodluyoruz.urlshortener.repository;

import org.kodluyoruz.urlshortener.model.User;

public interface UserRepository extends MyEntityRepository<Integer, User>{

    User findByEmail(String email);
}
