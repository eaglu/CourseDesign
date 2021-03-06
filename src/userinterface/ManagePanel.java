package userinterface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

//各管理界面父类
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

    //在表格中添加新行
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

    //调用删除功能
    public void delete(){
        delete.addActionListener(e->{
            deleteLine();
            updateUI();
        });
    }

    public abstract void deleteLine();

    //调用保存功能
    public void save(){
        save.addActionListener(e->{
                    saveData();
                    rowLength = model.getRowCount();
                    updateUI();

          }
        );
    }

    //调用修改功能呢
    public void update(){
        update.addActionListener(e-> updateData());
    }

    public abstract void updateData();

    //保存新加的行
    public abstract void saveData();

    //获取数据库设计到表格中
    public abstract void getTable();

    //调用搜素功能
    public void search(){
        search.addActionListener(e->{
            bottomPanel.setVisible(false);
            addSearchPanel();
        });
    }

    public abstract void searchByRule();
}
