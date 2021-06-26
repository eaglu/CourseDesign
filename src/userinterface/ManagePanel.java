package userinterface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

public abstract class ManagePanel extends JPanel {
    protected JScrollPane jScrollPane;
    protected JPanel bottomPanel;
    protected JTable jTable;
    protected JButton add,delete,save,update,search;
    protected GridBagLayout gridBagLayout;
    protected GridBagConstraints pos;
    protected DefaultTableModel model;
    protected int rowLength = -1;

    public ManagePanel(){
        getTable();
        jScrollPane = new JScrollPane(jTable);
        add = new JButton("添加");
        delete = new JButton("删除");
        save = new JButton("保存");
        update = new JButton("修改");
        search = new JButton("搜索");

        gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);
        pos = new GridBagConstraints();

        addScrollPanel();

        bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.add(add);
        bottomPanel.add(delete);
        bottomPanel.add(update);
        bottomPanel.add(save);
        bottomPanel.add(search);

        add();
        delete();
        save();
        update();

        add(jScrollPane);
        add(bottomPanel);
        setVisible(true);
    }

    public void addScrollPanel(){
        pos.gridwidth=GridBagConstraints.REMAINDER;
        pos.gridheight=GridBagConstraints.BOTH;
        pos.fill=GridBagConstraints.BOTH;
        gridBagLayout.addLayoutComponent(jScrollPane,pos);
    }

    public void addLine(){
        Vector<String[]> blankRow = new Vector<>();
        ((DefaultTableModel) jTable.getModel()).addRow(blankRow);
        int count = jTable.getRowCount();
        jTable.requestFocus();
        jTable.setRowSelectionInterval(count-1,count-1);
        jTable.editCellAt(count-1,0);
    }

    public void add(){
        add.addActionListener(e-> addLine());
    }

    public void delete(){
        delete.addActionListener(e->deleteLine());
    }

    public abstract void deleteLine();

    public void save(){
        save.addActionListener(e->{
            saveData();
            updateTable();
          }
        );
    }

    public void update(){
        update.addActionListener(e->updateData());
    }

    public abstract void updateData();

    public abstract void saveData();

    public abstract void getTable();

    public void updateTable(){
        getTable();
        updateUI();
    }
}
