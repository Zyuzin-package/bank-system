package bank.system.rest.dao.api.service.api;

import java.util.List;

public interface StorageDAO<T,I> {
    T save(T t);
    List<T> getAll();
    T findById(I id);
    T update(T t);
    boolean remove(T t);
    boolean removeById(I id);
}
