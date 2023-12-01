package views;

import models.MyByte;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

public class CacheView extends JFrame {
    private DefaultTableModel cacheTableModel;
    private DefaultTableModel memoryTableModel;
    private JTable cacheTable;
    private JTable memoryTable;
    private String[] requestColumns = {"Type", "Address", "Tag", "Set", "Offset"};
    private String[] cacheColumns;
    private String[] memoryColumns = {"Address", "0", "1", "2", "3", "4", "5", "6", "7"};
    private JButton iterateButton;
    private JLabel nextLabel;
    private JLabel queueLabel;
    private JTextArea queueArea;

    public CacheView() {
        this.setBounds(25, 25, 1500, 800);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().setLayout(null);

        JLabel cacheLabel = new JLabel("The cache:");
        cacheLabel.setFont(new Font("Tahoma",Font.PLAIN, 18));
        cacheLabel.setBounds(650, 80, 300, 20);
        this.getContentPane().add(cacheLabel);

        nextLabel = new JLabel();
        nextLabel.setFont(new Font("Tahoma",Font.PLAIN, 25));
        nextLabel.setBounds(100, 100, 300, 30);
        this.getContentPane().add(nextLabel);

        queueArea = new JTextArea("Al a");
        queueArea.setFont(new Font("Tahoma",Font.PLAIN, 20));
        queueArea.setBounds(100, 150, 300, 400);
        queueArea.setOpaque(false);
        this.getContentPane().add(queueArea);

        iterateButton = new JButton("Iterate");
        iterateButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
        iterateButton.setBounds(700, 35, 100, 21);
        this.getContentPane().add(iterateButton);

        this.setVisible(false);
    }

    public String getNextLabel() {
        return nextLabel.getText();
    }

    public void setNextLabel(String string) {
        nextLabel.setText(string);
    }

    public String getQueueArea() {
        return queueArea.getText();
    }

    public void setQueueArea(String string) {
        queueArea.setText(string);
    }

    public void setCacheTable(Integer size, String[] cacheColumns) {
        this.cacheColumns = cacheColumns;
        cacheTableModel = new DefaultTableModel(null, cacheColumns);
        for (int i = 0; i < size; i++) {
            Object[] data = {"", "", "", "", "", ""};
            cacheTableModel.addRow(data);
        }
        cacheTable = new JTable(cacheTableModel);
        JScrollPane cacheScrollPane = new JScrollPane(cacheTable);
        cacheScrollPane.setBounds(400, 100, 700, 400);
        cacheScrollPane.setViewportBorder(new EmptyBorder(0, 0, 0, 0));
        cacheScrollPane.setBorder(new LineBorder(new Color(0, 0, 0, 0)));
        this.getContentPane().add(cacheScrollPane);
    }

    public void setMemoryTable (List<MyByte> memory) {
        memoryTableModel = new DefaultTableModel(null, memoryColumns);
        memoryTable = new JTable(memoryTableModel);

        for (int i = 0; i < 512; i = i + 8) {
            memoryTableModel.addRow(new Object[]{memory.get(i).getAddress(), memory.get(i).getContent(), memory.get(i+1).getContent(), memory.get(i+2).getContent(),
                    memory.get(i+3).getContent(), memory.get(i+4).getContent(), memory.get(i+5).getContent(), memory.get(i+6).getContent(),
                    memory.get(i+7).getContent()});
        }

        TableColumnModel tableColumnModel = memoryTable.getColumnModel();
        TableColumn column = tableColumnModel.getColumn(0);
        column.setPreferredWidth(200);
        JScrollPane memoryScrollPane = new JScrollPane(memoryTable);
        memoryScrollPane.setBounds(1200, 100, 300, 600);
        memoryScrollPane.setViewportBorder(new EmptyBorder(0, 0, 0, 0));
        memoryScrollPane.setBorder(new LineBorder(new Color(0, 0, 0, 0)));
        this.getContentPane().add(memoryScrollPane);
    }

    public void updateCacheTable(Object[][] objects, int rows, int columns) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                cacheTableModel.setValueAt(objects[i][j], i, j);
            }
        }
    }

    public void addIterateListener(ActionListener actionListener) {
        iterateButton.addActionListener(actionListener);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "RESULTS", JOptionPane.INFORMATION_MESSAGE);
    }
}
