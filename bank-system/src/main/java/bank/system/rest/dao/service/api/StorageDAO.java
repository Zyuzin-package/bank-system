package bank.system.rest.dao.service.api;

import bank.system.rest.exception.ServerException;

import java.util.List;

public interface StorageDAO<T,I> {
    T save(T t) throws ServerException;
    List<T> getAll();
    T findById(I id);
    T update(T t) throws ServerException;
    void remove(T t) throws ServerException;
    void removeById(I id) throws ServerException;
}
