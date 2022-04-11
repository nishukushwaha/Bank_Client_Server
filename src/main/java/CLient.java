import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

import static javax.swing.SwingConstants.CENTER;

public class CLient extends JFrame {
    fonts font = new fonts();
    private JLabel label = new JLabel("Client");
    private JLabel accountNumberLabel = new JLabel("Account Number");
    private JTextField accountNumberField = new JTextField();
    private JButton loginButton = new JButton("Login");
    private JPanel panel = new JPanel();
    private JPanel centerPanel = new JPanel();
    private JPanel dashboardPanel = new JPanel();
    private JPanel withdrawPanel = new JPanel();
    private JPanel depositPanel = new JPanel();
    private JPanel balancepanel = new JPanel();
    private JFrame dasboardFrame ;
    int accountNumber;
    CLient()
    {
        setFont();
        label.setHorizontalAlignment(CENTER);
        centerPanel.setSize(500,100);
        centerPanel.setLayout(new GridLayout(3,1));
        centerPanel.add(accountNumberLabel);
        centerPanel.add(accountNumberField);
        centerPanel.add(loginButton);
        panel.setLayout(new BorderLayout());
        panel.add(centerPanel,BorderLayout.CENTER);
        panel.add(label,BorderLayout.NORTH);
            loginButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Login initialed for account number " + accountNumberField.getText());
                    try {
                        Socket soc = new Socket("localhost", 9806);
                        accountNumber = Integer.parseInt(accountNumberField.getText());
                        DataInputStream in = new DataInputStream(soc.getInputStream());
                        DataOutputStream out = new DataOutputStream(soc.getOutputStream());
                        out.writeInt(accountNumber);
                        int accountNumberValidate = in.readInt();
                        if (accountNumberValidate == 1) {
                            System.out.println("Login Success for account Number " + accountNumber);
                            System.out.println("Valid Account ");
                            JLabel balance = new JLabel("Balance");
                            JLabel accountBalance = new JLabel();
                            out.writeInt(1);
                            accountBalance.setText(String.valueOf(in.readInt()));
                            JButton withdrawButton = new JButton("Withdraw");
                            JTextField withdrawAmount = new JTextField();

                            withdrawButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    try {
                                        out.writeInt(2);
                                        out.writeInt(Integer.parseInt(withdrawAmount.getText()));
                                        out.writeInt(1);
                                        int updatedBalance = in.readInt();
                                        accountBalance.setText(String.valueOf(updatedBalance));
                                        System.out.println(Integer.parseInt(withdrawAmount.getText())+" withdrawn");
                                        System.out.println("Updated account balance = "+updatedBalance);
                                    } catch (IOException ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            });
                            JButton depositButton = new JButton("Deposit");
                            JTextField depositAmount = new JTextField();

                            depositButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    try {
                                        out.writeInt(3);
                                        out.writeInt(Integer.parseInt(depositAmount.getText()));
                                        out.writeInt(1);
                                        int updatedBalance = in.readInt();
                                        accountBalance.setText(String.valueOf(updatedBalance));
                                        System.out.println(Integer.parseInt(depositAmount.getText())+" Deposited");
                                        System.out.println("Updated account balance = "+updatedBalance);
                                    } catch (IOException ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            });
                            dashboardPanel.setLayout(new GridLayout(3, 1));
                            balancepanel.setLayout(new GridLayout(1, 2));
                            withdrawPanel.setLayout(new GridLayout(1, 2));
                            depositPanel.setLayout(new GridLayout(1, 2));
                            balancepanel.add(balance);
                            balancepanel.add(accountBalance);
                            dashboardPanel.add(balancepanel);
                            withdrawPanel.add(withdrawAmount);
                            withdrawPanel.add(withdrawButton);
                            dashboardPanel.add(withdrawPanel);
                            depositPanel.add(depositAmount);
                            depositPanel.add(depositButton);
                            dashboardPanel.add(depositPanel);
                            dasboardFrame = new JFrame("Account " + accountNumber);
                            dasboardFrame.setSize(500, 500);
                            dasboardFrame.add(dashboardPanel);
                            dasboardFrame.setVisible(true);
                        } else if (accountNumberValidate == 0) {
                            System.out.println("Invalid Account");
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });


        this.add(panel);
        this.setSize(500,500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new CLient();
    }

    void setFont()
    {
        accountNumberField.setFont(font.smallLabelFont());
        loginButton.setFont(font.buttonFont());
        accountNumberLabel.setFont(font.smallLabelFont());
        label.setFont(font.largeLabelFont());
    }
}
