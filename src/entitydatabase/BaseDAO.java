package entitydatabase;

import java.awt.*;
import java.util.List;

public interface BaseDAO<T> {
    public List<T> getList();
    public void saveList(List<T> list);
    public void deleteList(List<T> list);
    public void updateList(List<T> list);
    public void save(T object);
    public void delete(T object);
    public void update(T object);
}