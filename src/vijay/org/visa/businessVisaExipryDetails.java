/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vijay.org.visa;

import org.vijay.employee.*;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.proteanit.sql.DbUtils;
import org.vijay.common.AnarTrading;
import org.vijay.common.DateCellRenderer;
import org.vijay.common.NumberRenderer;
import org.vijay.common.connection;

/**
 *
 * @author MAC
 */
public class businessVisaExipryDetails extends javax.swing.JInternalFrame {

    /**
     * Creates new form btnRPExpiryList
     */
    public businessVisaExipryDetails() {
        initComponents();
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        viewDbEmployeeDetails();
       
    }
    public void viewDbEmployeeDetails()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT tbl_business_visa_appli.businessVisaId as id,CONCAT(CONCAT(CONCAT(CONCAT(CONCAT(CONCAT(CONCAT(CONCAT(tbl_business_visa_appli.applicantName1,\" \"),tbl_business_visa_appli.applicantName2),\" \"),tbl_business_visa_appli.applicantName3),\" \"),tbl_business_visa_appli.applicantName4),\" \"),tbl_business_visa_appli.applicantName5) as Name,tbl_business_visa_appli.AgentName,tbl_business_visa_appli.passportNumber as PP_Number,tbl_business_visa_entrydate.entryDate as Entry_Date,tbl_business_visa_entrydate.expiryDate As Expiry_Date,DATEDIFF(tbl_business_visa_entrydate.expiryDate,tbl_business_visa_entrydate.entryDate) as Visa_Applied_For,DATEDIFF(tbl_business_visa_entrydate.expiryDate,CURDATE()) as Days_Left FROM anar.tbl_business_visa_entrydate INNER JOIN anar.tbl_business_visa_appli ON (tbl_business_visa_entrydate.businessVisaId = tbl_business_visa_appli.businessVisaId) WHERE tbl_business_visa_appli.status='Entered to the Country' ORDER BY DATEDIFF(tbl_business_visa_entrydate.expiryDate,CURDATE()) ");
            jTable1.setModel(DbUtils.resultSetToTableModel(rs));
//            jTable1.getColumnModel().getColumn(0).setMaxWidth(50);
//            jTable1.getColumnModel().getColumn(1).setMaxWidth(50);
//            jTable1.getColumnModel().getColumn(7).setMaxWidth(100);
//            jTable1.getColumnModel().getColumn(4).setCellRenderer(new DateCellRenderer());
//            jTable1.getColumnModel().getColumn(6).setCellRenderer(new DateCellRenderer());
            jTable1.setAutoCreateRowSorter(true);
            con.close();

        }
        catch(Exception e)
        {

        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setClosable(true);
        setTitle("Business VIsa Expiry Details");

        jTable1.setFont(new java.awt.Font("Century Gothic", 0, 10)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTable1FocusGained(evt);
            }
        });
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable1KeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 837, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 720, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
        // TODO add your handling code here:
        int keyCode = evt.getKeyCode();
        if(keyCode == KeyEvent.VK_ENTER)
        {

        }
    }//GEN-LAST:event_jTable1KeyPressed

    private void jTable1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTable1FocusGained
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jTable1FocusGained


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
    int empId;
}
