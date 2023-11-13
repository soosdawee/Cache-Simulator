package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class IntroView extends JFrame {
    private JButton startButton;
    private JTextField setNumber;
    private JComboBox<String> chooseWrite;
    String[] comboOptions = {"Write-through", "Write-back"};

    public IntroView() {
        this.setBounds(100, 100, 500, 400);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().setLayout(null);

        JLabel titleLabel = new JLabel("Cache simulator");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        titleLabel.setBounds(170, 15, 200, 40);
        this.getContentPane().add(titleLabel);

        JLabel messageLabel = new JLabel("Please select the data you want to use during the simulation!");
        messageLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        messageLabel.setBounds(35, 60, 500, 40);
        this.getContentPane().add(messageLabel);

        startButton = new JButton("Start simulation");
        startButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
        startButton.setBounds(175, 300, 150, 21);
        this.getContentPane().add(startButton);

        setNumber = new JTextField();
        setNumber.setBounds(300, 126, 20, 20);
        this.getContentPane().add(setNumber);

        JLabel numberLabel = new JLabel("Number of sets:");
        numberLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        numberLabel.setBounds(180, 125, 150, 20);
        this.getContentPane().add(numberLabel);

        JLabel mechanismLabel = new JLabel("Mechanism for write operation:");
        mechanismLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        mechanismLabel.setBounds(170, 175, 200, 40);
        this.getContentPane().add(mechanismLabel);

        chooseWrite = new JComboBox<>(comboOptions);
        chooseWrite.setBounds(190, 230, 125, 25);
        this.getContentPane().add(chooseWrite);

        this.setVisible(true);
    }

    public void addStartListener(ActionListener action) {
        startButton.addActionListener(action);
    }
}
