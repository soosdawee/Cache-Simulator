package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;

public class CacheView extends JFrame {
    private DefaultTableModel requestTableModel;
    private DefaultTableModel cacheTableModel;
    private DefaultTableModel memoryTableModel;
    private JTable requestTable;
    private JTable cacheTable;
    private JTable memoryTable;
    String[] requestColumns = {"Type", "Address", "Tag", "Set", "Offset"};
    String[] cacheColumns = {"LRU", "Dirty", "V", "Tag", "Data"};
    String[] memoryColumns = {"Address", "Data"};
    private JButton iterateButton;

    public CacheView() {
        this.setBounds(25, 25, 1500, 800);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().setLayout(null);

        requestTableModel = new DefaultTableModel(null, requestColumns);
        requestTable = new JTable(requestTableModel);
        JScrollPane scrollPane = new JScrollPane(requestTable);
        scrollPane.setBounds(50, 100, 400, 500);
        this.getContentPane().add(scrollPane);

        cacheTableModel = new DefaultTableModel(null, cacheColumns);
        cacheTable = new JTable(cacheTableModel);
        JScrollPane cacheScrollPane = new JScrollPane(cacheTable);
        cacheScrollPane.setBounds(500, 100, 400, 100);
        this.getContentPane().add(cacheScrollPane);

        memoryTableModel = new DefaultTableModel(null, memoryColumns);
        memoryTable = new JTable(memoryTableModel);
        JScrollPane memoryScrollPane = new JScrollPane(memoryTable);
        memoryScrollPane.setBounds(1000, 100, 200, 100);
        this.getContentPane().add(memoryScrollPane);

        JLabel requestLabel = new JLabel("Calculated data based on input:");
        requestLabel.setFont(new Font("Tahoma",Font.PLAIN, 18));
        requestLabel.setBounds(125, 80, 300, 20);
        this.getContentPane().add(requestLabel);

        JLabel cacheLabel = new JLabel("The cache:");
        cacheLabel.setFont(new Font("Tahoma",Font.PLAIN, 18));
        cacheLabel.setBounds(650, 80, 300, 20);
        this.getContentPane().add(cacheLabel);

        JLabel memoryLabel = new JLabel("The memory:");
        memoryLabel.setFont(new Font("Tahoma",Font.PLAIN, 18));
        memoryLabel.setBounds(1050, 80, 300, 20);
        this.getContentPane().add(memoryLabel);

        iterateButton = new JButton("Iterate");
        iterateButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
        iterateButton.setBounds(700, 35, 100, 21);
        this.getContentPane().add(iterateButton);

        this.setVisible(false);
    }

    public DefaultTableModel getTableModel() {
        return requestTableModel;
    }

    public void setTableModel(DefaultTableModel tableModel) {
        this.requestTableModel = tableModel;
    }
}
