package userinterface;

import javax.swing.*;

public class MainFrame2 {
    private JPanel panel1;
    private JTable table1;
    private JButton add;
    private JButton edit;
    private JButton delete;

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainFrame2");
        frame.setContentPane(new MainFrame2().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
