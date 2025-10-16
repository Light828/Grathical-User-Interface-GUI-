/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package securemessagesapp;
import javax.swing.SwingUtilities;
import za.ac.tut.ui.SecureMessagesFrame;
/**
 *
 * @author LIGHT MNISI
 */
public class SecureMessagesApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        SwingUtilities.invokeLater(() -> new SecureMessagesFrame());
    }
    
}
