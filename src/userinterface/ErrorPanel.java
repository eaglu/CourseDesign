package userinterface;

import javax.swing.*;

public class ErrorPanel extends JOptionPane {
    public static void ShowMessage(String message){
        showMessageDialog(null,message,"错误", JOptionPane.ERROR_MESSAGE);
    }
}
