import javax.management.MBeanRegistrationException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

import static javax.swing.SwingConstants.CENTER;

public class Server extends JFrame {
    Random random = new Random();
    fonts font = new fonts();
    private JButton startButton = new JButton("Start Server");
    private JButton endButton = new JButton("Terminate Server");
    private JPanel panel = new JPanel();
    private JLabel label = new JLabel("Server");
    ServerSocket socket = null;
    int[] accountbalance = new int[10];
    Server(){
        generateAccountbalance();
        displayAccountBalance();
        setFont();
        label.setHorizontalAlignment(CENTER);
        startButton.setBounds(50,50,100,50);
        panel.setLayout(new GridLayout(3,1));
        panel.add(label);
        panel.add(startButton);
        panel.add(endButton);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    System.out.println("Server Started");
                    socket = new ServerSocket(9806);
                    Socket soc = socket.accept();
                    DataInputStream in = new DataInputStream(soc.getInputStream());
                    DataOutputStream out = new DataOutputStream(soc.getOutputStream());
                    int accountNumber = in.readInt();
                    out.writeInt(validateAccount(accountNumber));
                    while(true) {
                        switch (in.readInt()) {
                            case 1:
                                out.writeInt(checkBalance(accountNumber));
                                break;
                            case 2:
                                int withdrawalAmount = in.readInt();
                                if (withdrawalAmount<=accountbalance[accountNumber]) {
                                    withdrawal(withdrawalAmount, accountNumber);
                                    System.out.println("Updated balances");
                                    displayAccountBalance();
                                }
                                break;
                            case 3:
                                int depositAmount = in.readInt();
                                deposit(depositAmount,accountNumber);
                                System.out.println("Updated balances");
                                displayAccountBalance();
                        }
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

    private void generateAccountbalance() {
        for (int i = 0; i <accountbalance.length;i++)
        {
            accountbalance[i] = random.nextInt(50000);
        }
    }
    private void displayAccountBalance(){
        for (int i = 0; i <accountbalance.length;i++)
        {
            System.out.println("Account Number "+i+" Balance : "+accountbalance[i]);
        }
    }

    public static void main(String[] args) {
        new Server();
    }

    void setFont()
    {
        label.setFont(font.largeLabelFont());
        startButton.setFont(font.buttonFont());
        endButton.setFont(font.buttonFont());
    }

    int checkBalance(int number)
    {
        return accountbalance[number];
    }
    int validateAccount(int number)
    {
        System.out.println("This is called");
        if (number<accountbalance.length)
            return 1;
        else
            return 0;
    }
    void withdrawal(int amount,int accoutnNumber)
    {
        if (accountbalance[accoutnNumber]>=amount)
            accountbalance[accoutnNumber]-=amount;
    }

    void deposit(int amount,int accountNumber)
    {
        accountbalance[accountNumber]+=amount;
    }
}
