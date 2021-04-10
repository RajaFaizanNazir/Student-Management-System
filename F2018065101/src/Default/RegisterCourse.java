package Default;

import Database.Query;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
public class RegisterCourse extends javax.swing.JPanel implements ActionListener {

    /**
     * Creates new form RegisterCourse
     */
    private volatile static RegisterCourse uniqueInstance = null;
    private volatile static JFrame uniqueJframe = null;

    public RegisterCourse() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        initComponents();
        exit.addActionListener(this);
        back.addActionListener(this);
        searchstudent.addActionListener(this);
        addrequest.addActionListener(this);
        check.addActionListener(this);
        addstudent.addActionListener(this);
        done.addActionListener(this);
        droprequest.addActionListener(this);
        courses.removeAllItems();
        ResultSet rs = Query.executeQuery("Select id,name from course;");
        while (rs.next()) {
            courses.addItem(rs.getString("id") + "," + rs.getString("name"));
        }
    }

    public static RegisterCourse getInstance() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        if (uniqueInstance == null) { //Single Checked
            synchronized (RegisterCourse.class) {
                if (uniqueInstance == null) { //Double Checked
                    uniqueInstance = new RegisterCourse();
                }
            }
        }
        return uniqueInstance;
    }

    public static void showGUI() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        if (uniqueJframe == null) { //Single Checked
            synchronized (RegisterCourse.class) {
                if (uniqueJframe == null) { //Double Checked
                    uniqueJframe = new JFrame();
                    uniqueJframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    uniqueJframe.add(getInstance());
                    uniqueJframe.pack();
                    uniqueJframe.setLocationRelativeTo(null);
                    uniqueJframe.setTitle("Register Course");
                }
            }
        }
        uniqueJframe.setVisible(true);
    }

    public static void hideGUI() {
        uniqueJframe.setVisible(false);
    }

    private void writeStatus() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, URISyntaxException, IOException {
        ArrayList<String> listl = new ArrayList<String>();
        listl.add("ID:");
        listl.add("Name:");
        listl.add("Email:");
        listl.add("MAX CR:");
        listl.add("Admission Time:");
        ArrayList<String> listr = new ArrayList<String>();
        ResultSet rs = Query.executeQuery("SELECT * from student where id=" + id.getText() + ";");
        if (rs.next()) {
            listr.add(rs.getString("id"));
            listr.add(rs.getString("name"));
            listr.add(rs.getString("email"));
            listr.add(rs.getString("maxcr"));
            listr.add(rs.getString("time"));
        }
        for (int i = 0; i < 9; i++) {
            listl.add("");
            listr.add("");
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        listl.add(dtf.format(now));
        dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        now = LocalDateTime.now();
        listr.add(dtf.format(now));
        for (int i = 0; i < 5; i++) {
            listl.add("");
            listr.add("");
        }
        for (int i = 0; i < table.getRowCount(); i++) {
            listl.add((String.valueOf(table.getValueAt(i, 1))));
            listr.add((String.valueOf(table.getValueAt(i, 2))));
        }
        listl.add("---------------------------------------------------------------------------------------------------------------------------");
        listr.add("");
        listl.add("Courses #");
        listr.add(String.valueOf(noofcourse.getText()));
        listl.add("CR #");
        listr.add(String.valueOf(totalcr.getText()));
        listl.add("CR ALLOWED #");
        listr.add(String.valueOf(crallowed.getText()));
        listl.add("Approved Course #");
        listr.add(String.valueOf(aprrovedcourse.getText()));
        listl.add("Pending Course #");
        listr.add(String.valueOf(pendingcourse.getText()));
        PDF.PDFModify.write(listl, listr, 200, 680);
        File file = null;
        //first check if Desktop is supported by Platform or not
        if (!Desktop.isDesktopSupported()) {
            System.out.println("Desktop is not supported");
            return;
        }
        Desktop desktop = Desktop.getDesktop();
        file = new File("Status.pdf");
        if (file.exists()) {
            desktop.open(file);
        }
        System.out.println("Opened?");
    }

    private int gettotalcr(String courseidint) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        int numofcrallowedint = 0;
        ResultSet rs = Query.executeQuery("select cr from course where id=" + courseidint);
        if (rs.next()) {
            numofcrallowedint = Integer.valueOf(rs.getString("cr"));
        }
        return numofcrallowedint;
    }

    private String updatestudentmaxcr(String studentidint) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        String maxcrallowedint = "";
        ResultSet rs = Query.executeQuery("select maxcr from student where id=" + studentidint);
        if (rs.next()) {
            maxcrallowedint = rs.getString("maxcr");
        }
        return maxcrallowedint;
    }

    private void updatependingnapproved() {
        int pending = 0;
        int approved = 0;
        for (int i = 0; i < table.getRowCount(); i++) {
            if (table.getValueAt(i, 2).equals("Pending")) {
                pending++;
            } else if (table.getValueAt(i, 2).equals("Approved")) {
                approved++;
            }
        }
        aprrovedcourse.setText(String.valueOf(approved));
        pendingcourse.setText(String.valueOf(pending));
    }

    public void updatefield() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        int numofcoursesint = 0;
        int numofcrallowedint = 0;
        noofcourse.setText(String.valueOf(table.getRowCount()));
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        ResultSet rs = Query.executeQuery("select * from request where studentid=" + id.getText() + ";");
        crallowed.setText(updatestudentmaxcr(id.getText()));
        model.setRowCount(0);
        if (rs != null) {
            while (rs.next()) {
                model.addRow(new Object[]{rs.getString("id"), rs.getString("courseid"), rs.getString("status"), rs.getString("time")});
                numofcoursesint++;
                numofcrallowedint += gettotalcr(rs.getString("courseid"));
            }
        }
        noofcourse.setText(String.valueOf(numofcoursesint));
        totalcr.setText(String.valueOf(numofcrallowedint));
        updatependingnapproved();
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
        jLabel2 = new javax.swing.JLabel();
        back = new javax.swing.JButton();
        exit = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        addrequest = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        totalcr = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        noofcourse = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        crallowed = new javax.swing.JTextField();
        aprrovedcourse = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        pendingcourse = new javax.swing.JTextField();
        courses = new javax.swing.JComboBox<>();
        droprequest = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        id = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        name = new javax.swing.JTextField();
        check = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        searchstudent = new javax.swing.JButton();
        addstudent = new javax.swing.JButton();
        printcheckbox = new javax.swing.JCheckBox();
        done = new javax.swing.JButton();

        setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/logo_black.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Register Course");
        jLabel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        back.setText("BACK");

        exit.setText("EXIT");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Courses"));

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Request ID", "Course ID", "Status", "Request Time"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(table);

        addrequest.setText("Add Request");

        jLabel5.setText("Course");

        jLabel8.setText("Total CR");

        totalcr.setEditable(false);

        jLabel9.setText("No of Courses");

        noofcourse.setEditable(false);

        jLabel10.setText("Total CR Allowed");

        jLabel11.setText("Approved Course");

        crallowed.setEditable(false);

        aprrovedcourse.setEditable(false);

        jLabel12.setText("Pending Courses");

        pendingcourse.setEditable(false);

        droprequest.setText("Drop Request");

        jLabel6.setText("Currect Requests");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 723, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(courses, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(addrequest, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(18, 18, 18)
                                .addComponent(noofcourse, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel8)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel11))))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(aprrovedcourse)
                            .addComponent(totalcr, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(crallowed, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                            .addComponent(pendingcourse)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(droprequest, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addGap(329, 329, 329))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addrequest)
                    .addComponent(courses, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(droprequest)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(totalcr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(noofcourse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(crallowed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(aprrovedcourse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(pendingcourse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Customer"));

        jLabel3.setText("ID");

        jLabel4.setText("Name");

        name.setEditable(false);

        check.setText("Check");

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Need Help?"));
        jPanel3.setToolTipText("");

        searchstudent.setText("Search Course");

        addstudent.setText("Add Student");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(addstudent, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(searchstudent)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchstudent)
                    .addComponent(addstudent))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(59, 59, 59)
                .addComponent(id, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jLabel4)
                .addGap(31, 31, 31)
                .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(check)
                .addGap(45, 45, 45)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(check))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        printcheckbox.setSelected(true);
        printcheckbox.setText("Print");

        done.setText("Done");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(back)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(printcheckbox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(done)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(exit))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(223, 223, 223)
                                .addComponent(jLabel1))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(308, 308, 308)
                                .addComponent(jLabel2))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 754, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(back)
                    .addComponent(exit)
                    .addComponent(printcheckbox)
                    .addComponent(done))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addrequest;
    private javax.swing.JButton addstudent;
    private javax.swing.JTextField aprrovedcourse;
    private javax.swing.JButton back;
    private javax.swing.JButton check;
    private javax.swing.JComboBox<String> courses;
    private javax.swing.JTextField crallowed;
    private javax.swing.JButton done;
    private javax.swing.JButton droprequest;
    private javax.swing.JButton exit;
    public static javax.swing.JTextField id;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTextField name;
    private javax.swing.JTextField noofcourse;
    private javax.swing.JTextField pendingcourse;
    private javax.swing.JCheckBox printcheckbox;
    private javax.swing.JButton searchstudent;
    private javax.swing.JTable table;
    private javax.swing.JTextField totalcr;
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
                MAIN.showGUI();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MAIN.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (e.getSource() == searchstudent) {
            try {
                SearchStudentRegister.showGUI();
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
                Logger.getLogger(RegisterCourse.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (e.getSource() == addrequest) {
            try {
                Query.executeUpdate("INSERT INTO request(studentid,courseid) VALUES(" + id.getText() + "," + courses.getSelectedItem().toString().split(",")[0] + ");");
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(RegisterCourse.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (e.getSource() == check) {
            try {
                ResultSet rs = Query.executeQuery("SELECT name from student where id=" + id.getText() + ";");
                if (rs.next()) {
                    name.setText(rs.getString("name"));
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
                JOptionPane.showMessageDialog(null, "CUSTOMER NOT FOIND! with this ID, kindly try searching customer or add new customer\n" + ex.toString());
            }
        } else if (e.getSource() == addstudent) {
            try {
                AddStudentinRegister.showGUI();
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
                Logger.getLogger(RegisterCourse.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (e.getSource() == done) {
            if (printcheckbox.isSelected()) {
                try {
                    try {
                        writeStatus();
                    } catch (URISyntaxException | IOException ex) {
                        Logger.getLogger(RegisterCourse.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
                    Logger.getLogger(RegisterCourse.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            id.setText("");
            name.setText("");
            noofcourse.setText("");
            totalcr.setText("");
            crallowed.setText("");
            aprrovedcourse.setText("");
            pendingcourse.setText("");
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
        } else if (e.getSource() == droprequest) {
            int reqid = Integer.valueOf(table.getValueAt(table.getSelectedRow(), 0).toString());
            try {
                Query.executeUpdate("DELETE from request where id=" + reqid + ";");
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(RegisterCourse.class.getName()).log(Level.SEVERE, null, ex);
            }
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.removeRow(table.getSelectedRow());
        }
        try {
            updatefield();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
            Logger.getLogger(RegisterCourse.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
