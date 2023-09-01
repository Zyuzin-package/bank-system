package bank.system.rest.dao.service.api;

import java.util.List;

public interface StorageDAO<T,I> {
    T save(T t);
    List<T> getAll();
    T findById(I id);
    T update(T t);
    void remove(T t);
    void removeById(I id);
}
