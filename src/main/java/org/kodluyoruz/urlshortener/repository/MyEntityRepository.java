package org.kodluyoruz.urlshortener.repository;

import org.kodluyoruz.urlshortener.model.MyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface MyEntityRepository<ID extends Serializable, E extends MyEntity<ID>>
        extends JpaRepository<E, ID>
{

}
