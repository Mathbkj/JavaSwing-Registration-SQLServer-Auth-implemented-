
import Model.Users;
import SQLServer.SQLs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("DB_MANAGER");
        CheckConnectivity();
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        // Add spacing between components
        gbc.insets = new Insets(5, 5, 5, 5);

        // Email label and text field
        JLabel MAIL = new JLabel("Email");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(MAIL, gbc);

        JTextField email = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(email, gbc);

        // Name label and text field
        JLabel NAME = new JLabel("Name");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(NAME, gbc);

        JTextField name = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(name, gbc);

        // Surname label and text field
        JLabel SURNAME = new JLabel("Surname");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(SURNAME, gbc);

        JTextField surName = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(surName, gbc);


        JLabel PHONE = new JLabel("Phone");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(PHONE, gbc);

        JTextField phone = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(phone, gbc);

        JButton register = new JButton("Register");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(register, gbc);


        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] inputs = new String[]{email.getText(),name.getText(),surName.getText()};

                    try{
                        float telIn=Float.parseFloat(phone.getText());
                        for(String input:inputs){
                            if(input.isBlank()){
                                JOptionPane.showMessageDialog(frame,"No Fields can be empty");
                                return;
                            }
                            else CreateUser(inputs,telIn);
                        }
                    }
                    catch(NumberFormatException ex){
                        JOptionPane.showMessageDialog(frame,ex.getMessage());
                    }




            }
        });
        frame.setSize(300, 300);
        frame.setContentPane(panel);
        frame.setLocationRelativeTo(null);
        frame.setUndecorated(true);
        frame.setVisible(true);

    }
    public static void CheckConnectivity(){
        try{
            SQLs.CreateConnection();
        }
        catch(Exception ex){
           System.out.print(STR."\{ex.getMessage()}");
        }
    }
    public static void CreateUser(String[] param,float Phone){
        Users user = new Users(param[0],param[1],param[2],Phone);
        try{
            SQLs.Insert(user);
            Arrays.stream(param).map(el->{
                if(el.isEmpty()){
                    throw new Error("Can't proceed with empty fields");
                }
                return el;
            });
        }
        catch(Exception ex){
           System.out.print(ex.getMessage());
        }
    }
}
