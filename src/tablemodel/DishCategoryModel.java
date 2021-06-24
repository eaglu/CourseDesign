package tablemodel;

import entitydatabase.DishCategoryDAO;

import javax.swing.table.AbstractTableModel;

public class DishCategoryModel extends AbstractTableModel {

    @Override
    public int getRowCount() {
        return DishCategoryDAO.getRowCount();
    }

    @Override
    public int getColumnCount() {
        return DishCategoryDAO.getColumnCount();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return null;
    }
}
