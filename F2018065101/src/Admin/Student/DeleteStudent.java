/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin.Student;

import Database.Query;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author RFaiz
 */
public class DeleteStudent extends javax.swing.JPanel implements ActionListener {

    /**
     * Creates new form DeleteStudent
     */
    private volatile static DeleteStudent uniqueInstance = null;
    private volatile static JFrame uniqueJframe = null;
    public DeleteStudent() {
        initComponents();
        exit.addActionListener(this);
        back.addActionListener(this);
        delete.addActionListener(this);
        deletenexit.addActionListener(this);
    }

    public static DeleteStudent getInstance() throws ClassNotFoundException {
        if (uniqueInstance == null) { //Single Checked
            synchronized (DeleteStudent.class) {
                if (uniqueInstance == null) { //Double Checked
                    uniqueInstance = new DeleteStudent();
                }
            }
        }
        
        return uniqueInstance;
    }
    public static void showGUI() throws ClassNotFoundException {
        if (uniqueJframe == null) { //Single Checked
            synchronized (DeleteStudent.class) {
                if (uniqueJframe == null) { //Double Checked
                    uniqueJframe = new JFrame();
                    uniqueJframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    uniqueJframe.add(getInstance());
                    uniqueJframe.pack();
                    uniqueJframe.setLocationRelativeTo(null);
                    uniqueJframe.setTitle("Delete Student");
                }
            }
        }
        uniqueJframe.setVisible(true);
    }

    public static void hideGUI() {
        uniqueJframe.setVisible(false);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        back = new javax.swing.JButton();
        exit = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        id = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        delete = new javax.swing.JButton();
        deletenexit = new javax.swing.JButton();

        back.setText("BACK");

        exit.setText("EXIT");

        jLabel1.setText("ID");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Delete Student");

        delete.setText("Delete");

        deletenexit.setText("Delete and Close");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(back)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(delete)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(deletenexit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(exit)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(id, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(84, 84, 84))
            .addGroup(layout.createSequentialGroup()
                .addGap(133, 133, 133)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(72, 72, 72)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 108, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(exit)
                    .addComponent(back)
                    .addComponent(deletenexit)
                    .addComponent(delete))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton back;
    private javax.swing.JButton delete;
    private javax.swing.JButton deletenexit;
    private javax.swing.JButton exit;
    private javax.swing.JTextField id;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == back) {
            try {
                hideGUI();
                Student.showGUI();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DeleteStudent.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (e.getSource() == exit) {
            JFrame f = new JFrame();
            int a = JOptionPane.showConfirmDialog(f, "Are you sure?");
            if (a == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
        else if (e.getSource() == delete) {
            String option = id.getText();
            try {
                if (Query.executeUpdate("delete from student where id=" + option + ";") > 0) {
                    JFrame f = new JFrame();
                    JOptionPane.showMessageDialog(f, "Deleted Succesfully");
                    hideGUI();
                    showGUI();
                }
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
                Logger.getLogger(DeleteStudent.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (e.getSource() == deletenexit) {
            String option = id.getText();
            try {
                if (Query.executeUpdate("delete from student where id=" + option + ";") > 0) {
                    hideGUI();
                    Student.showGUI();
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(DeleteStudent.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
