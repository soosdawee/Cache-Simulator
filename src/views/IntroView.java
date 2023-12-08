package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class IntroView extends JFrame {
    private JButton startButton;
    private JTextField setNumber;
    private JTextField cacheSize;
    private JTextField blockSize;
    private JComboBox<String> chooseWrite;
    private JComboBox<String> chooseReplacement;
    String[] comboOptions = {"Write-through", "Write-back"};
    String[] comboReplacement = {"FIFO", "LRU"};
    private JTextField inputField;

    public IntroView() {
        this.setBounds(100, 100, 500, 550);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().setLayout(null);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

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
        startButton.setBounds(175, 450, 150, 21);
        this.getContentPane().add(startButton);

        setNumber = new JTextField();
        setNumber.setBounds(300, 201, 40, 20);
        this.getContentPane().add(setNumber);

        JLabel numberLabel = new JLabel("Number of sets:");
        numberLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        numberLabel.setBounds(180, 200, 150, 20);
        this.getContentPane().add(numberLabel);

        cacheSize = new JTextField();
        cacheSize.setBounds(300, 126, 40, 20);
        this.getContentPane().add(cacheSize);

        JLabel sizeLabel = new JLabel("Size of the cache:");
        sizeLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        sizeLabel.setBounds(180, 125, 150, 20);
        this.getContentPane().add(sizeLabel);

        blockSize = new JTextField();
        blockSize.setBounds(300, 163, 40, 20);
        this.getContentPane().add(blockSize);

        JLabel blockLabel = new JLabel("Size of blocks:");
        blockLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        blockLabel.setBounds(180, 163, 150, 20);
        this.getContentPane().add(blockLabel);

        JLabel mechanismLabel = new JLabel("Mechanism for write operation:");
        mechanismLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        mechanismLabel.setBounds(170, 230, 200, 40);
        this.getContentPane().add(mechanismLabel);

        chooseWrite = new JComboBox<>(comboOptions);
        chooseWrite.setBounds(190, 270, 125, 25);
        this.getContentPane().add(chooseWrite);

        JLabel replacementLabel = new JLabel("Mechanism for replacement:");
        replacementLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        replacementLabel.setBounds(170, 305, 200, 40);
        this.getContentPane().add(replacementLabel);

        chooseReplacement = new JComboBox<>(comboReplacement);
        chooseReplacement.setBounds(190, 345, 125, 25);
        this.getContentPane().add(chooseReplacement);

        JLabel inputLabel = new JLabel("Place to get input from");
        inputLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        inputLabel.setBounds(190, 370, 200, 40);
        this.getContentPane().add(inputLabel);

        inputField = new JTextField();
        inputField.setBounds(173, 415, 150, 21);
        this.getContentPane().add(inputField);

        this.setVisible(true);
    }

    public void addStartListener(ActionListener action) {
        startButton.addActionListener(action);
    }

    public Integer getSetNumber() {
        return Integer.parseInt(setNumber.getText());
    }

    public Integer getCacheSize() {
        return Integer.parseInt(cacheSize.getText());
    }

    public Integer getBlockSize() {
        return Integer.parseInt(blockSize.getText());
    }

    public String getChooseWrite() {
        return chooseWrite.getSelectedItem().toString();
    }

    public String getChooseReplacement() {
        return chooseReplacement.getSelectedItem().toString();
    }

    public String getInputField() {
        return inputField.getText();
    }

    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "ALERT", JOptionPane.ERROR_MESSAGE);
    }
}
