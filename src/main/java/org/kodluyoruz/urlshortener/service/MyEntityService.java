package org.kodluyoruz.urlshortener.service;

import lombok.Getter;
import org.kodluyoruz.urlshortener.model.MyEntity;
import org.kodluyoruz.urlshortener.repository.MyEntityRepository;

import java.io.Serializable;
import java.util.List;

public abstract class MyEntityService<
        ID extends Serializable,
        E extends MyEntity<ID>,
        R extends MyEntityRepository<ID,E>
        >
{
    @Getter
    private final R repository;


    protected MyEntityService(R repository)
    {
        this.repository = repository;
    }

    public long count()
    {
        return getRepository().count();
    }

    public List<E> findAll()
    {
        return getRepository().findAll();
    }

    public E findById(ID id)
    {
        return getRepository().getOne(id);
    }

    public boolean existsById(ID id)
    {
        return getRepository().existsById(id);
    }

    public E add(E entity)
    {
        return getRepository().saveAndFlush(entity);
    }

    public E update(E entity)
    {
        if(entity.getId() == null || !existsById(entity.getId()))
            throw new IllegalArgumentException("This entity doesn't exist "+entity);
        return add(entity);
    }
}
