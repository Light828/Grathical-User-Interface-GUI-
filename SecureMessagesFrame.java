package za.ac.tut.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class SecureMessagesFrame extends JFrame {
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem openFileMenuItem, encryptFileMenuItem, saveEncryptedFileMenuItem, clearFileMenuItem, exitFileMenuItem;
    private JPanel headingPnl, plainTextPnl, encryptedTextPnl, mainPnl;
    private JLabel headingLbl;
    private JTextArea plainMsgTxtArea, encryptedMsgTxtArea;
    private JScrollPane scrollablePlainMsgTxtArea, scrollableEncryptedMsgTxtArea;

    public SecureMessagesFrame() {
        setTitle("Secure Messages");
        setSize(600, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");

        openFileMenuItem = new JMenuItem("Open file...");
        openFileMenuItem.addActionListener(new OpenFileMenuItemListener());

        encryptFileMenuItem = new JMenuItem("Encrypt message...");
        encryptFileMenuItem.addActionListener(new EncryptFileMenuItemListener());

        saveEncryptedFileMenuItem = new JMenuItem("Save encrypted message...");
        saveEncryptedFileMenuItem.addActionListener(new SaveEncryptedFileMenuItemListener());

        clearFileMenuItem = new JMenuItem("Clear");
        clearFileMenuItem.addActionListener(new ClearFileMenuItemListener());

        exitFileMenuItem = new JMenuItem("Exit");
        exitFileMenuItem.addActionListener(new ExitFileMenuItemListener());

        fileMenu.add(openFileMenuItem);
        fileMenu.add(encryptFileMenuItem);
        fileMenu.add(saveEncryptedFileMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(clearFileMenuItem);
        fileMenu.add(exitFileMenuItem);
        menuBar.add(fileMenu);

        headingPnl = new JPanel(new FlowLayout(FlowLayout.CENTER));
        plainTextPnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
        plainTextPnl.setBorder(new TitledBorder(new LineBorder(Color.BLACK, 1), "Plain message"));
        encryptedTextPnl = new JPanel(new FlowLayout(FlowLayout.CENTER));
        encryptedTextPnl.setBorder(new TitledBorder(new LineBorder(Color.BLACK, 1), "Encrypted message"));
        mainPnl = new JPanel(new BorderLayout());

        headingLbl = new JLabel("Message Encryptor");
        headingLbl.setForeground(Color.BLUE);
        headingLbl.setFont(new Font("SERIF", Font.BOLD + Font.ITALIC, 30));
        headingLbl.setBorder(new BevelBorder(BevelBorder.RAISED));

        plainMsgTxtArea = new JTextArea(10, 30);
        plainMsgTxtArea.setEditable(false);
        encryptedMsgTxtArea = new JTextArea(10, 30);
        encryptedMsgTxtArea.setEditable(false);

        scrollablePlainMsgTxtArea = new JScrollPane(plainMsgTxtArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollableEncryptedMsgTxtArea = new JScrollPane(encryptedMsgTxtArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        headingPnl.add(headingLbl);
        plainTextPnl.add(scrollablePlainMsgTxtArea);
        encryptedTextPnl.add(scrollableEncryptedMsgTxtArea);

        mainPnl.add(headingPnl, BorderLayout.NORTH);
        mainPnl.add(plainTextPnl, BorderLayout.WEST);
        mainPnl.add(encryptedTextPnl, BorderLayout.EAST);

        setJMenuBar(menuBar);
        add(mainPnl);
        pack();
        setResizable(false);
        setVisible(true);
    }

    private class OpenFileMenuItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    StringBuilder content = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        content.append(line).append("\n");
                    }
                    plainMsgTxtArea.setText(content.toString());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error reading file.");
                }
            }
        }
    }

    private class EncryptFileMenuItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            String plainText = plainMsgTxtArea.getText().toLowerCase();
            StringBuilder encrypted = new StringBuilder();
            for (char c : plainText.toCharArray()) {
                if (Character.isLetter(c)) {
                    char shifted = (char) (((c - 'a' + 3) % 26) + 'a');
                    encrypted.append(shifted);
                } else {
                    encrypted.append(c);
                }
            }
            encryptedMsgTxtArea.setText(encrypted.toString());
        }
    }

    private class SaveEncryptedFileMenuItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            String encryptedMessage = encryptedMsgTxtArea.getText();
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("encrypted_messages.txt", true))) {
                writer.write("ID: " + System.currentTimeMillis() + "\n");
                writer.write("Timestamp: " + timestamp + "\n");
                writer.write("Message: " + encryptedMessage + "\n\n");
                JOptionPane.showMessageDialog(null, "Encrypted message saved.");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error saving message.");
            }
        }
    }

    private class ClearFileMenuItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            plainMsgTxtArea.setText("");
            encryptedMsgTxtArea.setText("");
        }
    }

    private class ExitFileMenuItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            System.exit(0);
        }
    }

    
}
