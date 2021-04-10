package Default;

import Database.Query;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author RFaiz
 */
public class SearchStudentRegister extends javax.swing.JPanel implements ActionListener {

    /**
     * Creates new form SearchStudentRegister
     */
    private volatile static SearchStudentRegister uniqueInstance = null;
    private volatile static JFrame uniqueJframe = null;

    public SearchStudentRegister() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        initComponents();
        exit.addActionListener(this);
        back.addActionListener(this);
        gotoregister.addActionListener(this);
        search.addActionListener(this);
        clear.addActionListener(this);
        ResultSet rs = Query.executeQuery("SELECT id,name,email from student;");
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        while (rs.next()) {
            model.addRow(new Object[]{rs.getString("id"), rs.getString("name"), rs.getString("email")});
        }
    }

    public static SearchStudentRegister getInstance() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        if (uniqueInstance == null) { //Single Checked
            synchronized (SearchStudentRegister.class) {
                if (uniqueInstance == null) { //Double Checked
                    uniqueInstance = new SearchStudentRegister();
                }
            }
        }
        return uniqueInstance;
    }

    public static void showGUI() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        if (uniqueJframe == null) { //Single Checked
            synchronized (SearchStudentRegister.class) {
                if (uniqueJframe == null) { //Double Checked
                    uniqueJframe = new JFrame();
                    uniqueJframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    uniqueJframe.add(getInstance());
                    uniqueJframe.pack();
                    uniqueJframe.setLocationRelativeTo(null);
                    uniqueJframe.setTitle("Search Customer");
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

        jLabel1 = new javax.swing.JLabel();
        searchcombobox = new javax.swing.JComboBox<>();
        searchby = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        exit = new javax.swing.JButton();
        back = new javax.swing.JButton();
        gotoregister = new javax.swing.JButton();
        search = new javax.swing.JButton();
        clear = new javax.swing.JButton();

        setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("SEARCH BY");

        searchcombobox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID", "Name", "Email" }));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Customers"));

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Email"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(table);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
        );

        exit.setText("EXIT");

        back.setText("BACK");

        gotoregister.setText("Register Course for selected Student");

        search.setText("Search");

        clear.setText("Clear Filter");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(back)
                        .addGap(52, 52, 52)
                        .addComponent(gotoregister)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                        .addComponent(exit))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(136, 136, 136))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(searchcombobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchby, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(clear)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(search)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(49, 49, 49)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchcombobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchby, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(search)
                    .addComponent(clear))
                .addGap(60, 60, 60)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(exit)
                    .addComponent(back)
                    .addComponent(gotoregister))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton back;
    private javax.swing.JButton clear;
    private javax.swing.JButton exit;
    private javax.swing.JButton gotoregister;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton search;
    private javax.swing.JTextField searchby;
    private javax.swing.JComboBox<String> searchcombobox;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exit) {
            JFrame f = new JFrame();
            int a = JOptionPane.showConfirmDialog(f, "Are you sure?");
            if (a == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        } else if (e.getSource() == back) {
            try {
                hideGUI();
                RegisterCourse.showGUI();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MAIN.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(SearchStudentRegister.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(SearchStudentRegister.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(SearchStudentRegister.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (e.getSource() == gotoregister) {
            RegisterCourse.id.setText(table.getModel().getValueAt(table.getSelectedRow(), 0).toString());
            RegisterCourse.name.setText(table.getModel().getValueAt(table.getSelectedRow(), 1).toString());
            try {
                RegisterCourse.getInstance().updatefield();
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
                Logger.getLogger(SearchStudentRegister.class.getName()).log(Level.SEVERE, null, ex);
            }
            hideGUI();
            try {
                RegisterCourse.showGUI();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(SearchStudentRegister.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(SearchStudentRegister.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(SearchStudentRegister.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(SearchStudentRegister.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (e.getSource() == search) {
            ResultSet rs = null;
            try {
                if (!searchby.getText().equals("")) {
                    rs = Query.executeQuery("SELECT id,name,email from student WHERE " + searchcombobox.getSelectedItem().toString() + "=\'" + searchby.getText() + "\';");
                } else if (searchby.getText().equals("id")) {
                    rs = Query.executeQuery("SELECT id,name,email from student WHERE " + searchcombobox.getSelectedItem().toString() + "=" + searchby.getText() + ";");
                } else {
                    rs = Query.executeQuery("SELECT id,name,email from student;");
                }
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setRowCount(0);
                try {
                    while (rs.next()) {
                        model.addRow(new Object[]{rs.getString("id"), rs.getString("name"), rs.getString("email")});
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(SearchStudentRegister.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(SearchStudentRegister.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (e.getSource() == clear) {
            searchby.setText("");
            ResultSet rs = null;
            try {
                rs = Query.executeQuery("SELECT id,name,email from student;");
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setRowCount(0);
                try {
                    while (rs.next()) {
                        model.addRow(new Object[]{rs.getString("id"), rs.getString("name"), rs.getString("email")});
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(SearchStudentRegister.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(SearchStudentRegister.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
