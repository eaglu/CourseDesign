package userinterface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

public abstract class ManagePanel extends JPanel{
    protected JScrollPane jScrollPane;
    protected JPanel bottomPanel,searchPanel;
    protected JLabel contentLabel;
    protected JTextField contentFiled;
    protected JTable jTable;
    protected JButton add,delete,save,update,search,confirm;
    protected GridBagLayout gridBagLayout;
    protected GridBagConstraints pos;
    protected DefaultTableModel model;
    protected int rowLength = -1;
    protected String labelContent;

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
        update();
        save();
        search();

        add(jScrollPane);
        add(bottomPanel);
        setVisible(true);

        System.out.println("rowLength = "+rowLength);
    }

    public void addScrollPanel(){
        pos.gridwidth=GridBagConstraints.REMAINDER;
        pos.gridheight=GridBagConstraints.BOTH;
        pos.fill=GridBagConstraints.BOTH;
        gridBagLayout.addLayoutComponent(jScrollPane,pos);
    }

    public void addSearchPanel(){
        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints pos = new GridBagConstraints();

        searchPanel = new JPanel();
        searchPanel.setLayout(gridBagLayout);

        contentLabel = new JLabel("输入要查询的信息");

        contentFiled = new JTextField(20);

        confirm = new JButton("确 定");
        confirm.addActionListener(e->{
            labelContent =  contentFiled.getText();
            System.out.println("OnPanel"+labelContent);
            searchByRule();
        });

        pos.weighty=1;
        gridBagLayout.addLayoutComponent(contentLabel,pos);
        gridBagLayout.addLayoutComponent(contentFiled,pos);

        pos.gridy=2;
        pos.gridx=1;
        gridBagLayout.addLayoutComponent(confirm,pos);

        searchPanel.add(contentLabel);
        searchPanel.add(contentFiled);
        searchPanel.add(confirm);

        setVisible(true);
        repaint();
        add(searchPanel);
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
        delete.addActionListener(e->{
            deleteLine();
            updateUI();
        });
    }

    public abstract void deleteLine();

    public void save(){;
        save.addActionListener(e->{
            if(checkNull()) {
                if(checkConflict()) {
                    saveData();
                    updateUI();
                }
            }
          }
        );
    }

    public void update(){
        update.addActionListener(e->{
            if(checkNull()) {
                if(checkConflict()) {
                    updateData();
                }
            }
        });
    }

    public abstract void updateData();

    public abstract void saveData();

    public abstract void getTable();

    public void search(){
        search.addActionListener(e->{
            bottomPanel.setVisible(false);
            addSearchPanel();
        });
    }

    public abstract void searchByRule();

    protected boolean checkNull(){
        boolean flag = true;
        try{
            int row = jTable.getSelectedRow();
            for(int i= 1;i<model.getColumnCount();i++){
                String value = model.getValueAt(row,i).toString();
                System.out.println("value = " + value);
                if (value.equals("")){
                    flag = false;
                }
            }
        } catch (Exception e) {
            System.out.println("Check Failed");
            flag = false;
        }
        if(!flag){
            JOptionPane.showMessageDialog(this,"信息不完整","错误", JOptionPane.ERROR_MESSAGE);
        }
        System.out.println("Check passed");
        return flag;
    }

    protected abstract boolean checkConflict();

    public void updateTable(){
        getTable();
    }
}
