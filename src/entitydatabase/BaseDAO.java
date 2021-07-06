package entitydatabase;

import java.awt.*;
import java.util.List;

//规定数据库基本操作
public interface BaseDAO<T> {
    public List<T> getList();//获得一个实例
    public void saveList(List<T> list);//按队列保存
    public void deleteList(List<T> list);//按队列删除
    public void updateList(List<T> list);//按队列修改
}