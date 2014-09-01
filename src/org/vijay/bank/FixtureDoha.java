/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.vijay.bank;

import org.vijay.petty.*;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.proteanit.sql.DbUtils;
import org.vijay.common.AnarTrading;
import org.vijay.common.AutoCompleteDecorator;
import org.vijay.common.DateCellRenderer;
import org.vijay.common.NumberRenderer;
import org.vijay.common.connection;
import org.vijay.employee.LabourDetails;
import static org.vijay.invoice.InvoiceEntry.dateFormat;

/**
 *
 * @author MAC
 */
public class FixtureDoha extends javax.swing.JInternalFrame {

    /**
     * Creates new form PettyCashEntry
     */
    public FixtureDoha() {
        initComponents();
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        viewDbFixtureDOHAdetails();
        jDate.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("date")) {
                    todaydate = new SimpleDateFormat("yyyy-MM-dd").format(jDate.getDate());
                }
            }  
        });
        jtableSelection();
    }
    public FixtureDoha(int fDohaId) {
        this.fDohaId=fDohaId;
        initComponents();
        setTitle("Fixture Doha Bank-Updation/Deletion");
        
        btnSave.setEnabled(false);
        btnUpdate.setEnabled(true);
        btnDelete.setEnabled(true);
        
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        viewDbFixtureDOHAdetails();
        jDate.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("date")) {
                    todaydate = new SimpleDateFormat("yyyy-MM-dd").format(jDate.getDate());
                }
            }  
        });
        jtableSelection();
        getDetailsAcbqId();
    }
    public void getDetailsAcbqId()
     {
         try {
            connection c = new connection();
            Connection con = c.conn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT transDate,debit,credit,description FROM tbl_fixdoha where FdohaId="+fDohaId);
            while (rs.next()) {
                try 
                {
                    jDate.setDate(dateFormat.parse(rs.getString("transDate")));
                } 
                catch (ParseException ex) 
                {
                    Logger.getLogger(LabourDetails.class.getName()).log(Level.SEVERE, null, ex);
                }
                txtDebit.setText(rs.getString("debit"));
                txtCredit.setText(rs.getString("credit"));
                txtDescription.setText(rs.getString("description"));
                
            }
            con.close();
        } catch (SQLException ex) {
            
        }
     }
    public void viewDbFixtureDOHAdetails()
    {
        truncateTempDbTable();
        insertIntoTempDbTable();
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT x.FdohaId,x.transDate,x.description, x.debit, x.credit, SUM(y.bal) balance FROM( SELECT *,credit-debit bal FROM tbl_temp_fixdoha) X JOIN( SELECT *,credit-debit bal FROM tbl_temp_fixdoha) Y ON y.id <= x.id WHERE MONTH(x.transDate)=MONTH(CURRENT_DATE) GROUP BY x.id");
            jTable1.setModel(DbUtils.resultSetToTableModel(rs));
            
            jTable1.getColumnModel().getColumn(0).setMinWidth(50);
            jTable1.getColumnModel().getColumn(0).setMaxWidth(50);          
            
            
            jTable1.getColumnModel().getColumn(1).setMinWidth(100);
            jTable1.getColumnModel().getColumn(1).setMaxWidth(100);
            
            jTable1.getColumnModel().getColumn(3).setMinWidth(100);
            jTable1.getColumnModel().getColumn(3).setMaxWidth(100);
            
            jTable1.getColumnModel().getColumn(4).setMinWidth(100);
            jTable1.getColumnModel().getColumn(4).setMaxWidth(100);
            
            jTable1.getColumnModel().getColumn(5).setMinWidth(100);
            jTable1.getColumnModel().getColumn(5).setMaxWidth(100);
            
            jTable1.getColumnModel().getColumn(1).setCellRenderer(new DateCellRenderer());
            jTable1.getColumnModel().getColumn(3).setCellRenderer(NumberRenderer.getIntegerRenderer());
            jTable1.getColumnModel().getColumn(4).setCellRenderer(NumberRenderer.getIntegerRenderer());
            jTable1.getColumnModel().getColumn(5).setCellRenderer(NumberRenderer.getIntegerRenderer());
            jTable1.setAutoCreateRowSorter(true);
            con.close();

        }
        catch(Exception e)
        {

        }
    }
    public void  viewFixtureCbqform()
    {
        FixtureDoha FCBQ=new FixtureDoha();
        AnarTrading.desktopPane1.add(FCBQ);
        FCBQ.setVisible(true);
    }
    
    public void insertIntoDb()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            PreparedStatement ps=con.prepareStatement("INSERT INTO tbl_fixdoha(transDate,debit,credit,description) VALUES(?,?,?,?)");           
            ps.setString(1,todaydate);
            ps.setString(2, txtDebit.getText());
            ps.setString(3, txtCredit.getText());
            ps.setString(4, txtDescription.getText());
            int i=ps.executeUpdate();
            if(i!=0)
            {
                    dispose();
                    viewFixtureCbqform();
                    
            }
            con.close();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(rootPane, e, "ERROR!!", JOptionPane.ERROR_MESSAGE);
            //JOptionPane.showMessageDialog(rootPane,e+"Error SA001");
        }
    }
    public void insertIntoTempDbTable()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            PreparedStatement ps=con.prepareStatement("INSERT INTO tbl_temp_fixdoha (FdohaId,transDate,debit,credit,description) SELECT * FROM tbl_fixdoha ORDER BY transDate,FdohaId");
            int i=ps.executeUpdate();
            if(i!=0)
            {
                    
            }
            con.close();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(rootPane, e, "ERROR!!", JOptionPane.ERROR_MESSAGE);
            //JOptionPane.showMessageDialog(rootPane,e+"Error SA001");
        }
    }
     public void truncateTempDbTable()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            PreparedStatement ps=con.prepareStatement("TRUNCATE TABLE tbl_temp_fixdoha");
            int i=ps.executeUpdate();
            if(i!=0)
            {
                    
            }
            con.close();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(rootPane, e, "ERROR!!", JOptionPane.ERROR_MESSAGE);
            //JOptionPane.showMessageDialog(rootPane,e+"Error SA001");
        }
    }
     public void jtableSelection()
    {
        jTable1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
            int rowNo=jTable1.getSelectedRow();
            
            btnSave.setEnabled(false);
            btnUpdate.setEnabled(true);
            btnDelete.setEnabled(true);
            
            fDohaId=Integer.parseInt(jTable1.getValueAt(rowNo,0).toString());
            try 
            {
                    jDate.setDate(defaultDate.parse(jTable1.getValueAt(rowNo,1).toString()));
            } 
            catch (ParseException ex) 
            {
                    Logger.getLogger(LabourDetails.class.getName()).log(Level.SEVERE, null, ex);
            } 
            txtDescription.setText(jTable1.getValueAt(rowNo,2).toString());
            txtDebit.setText(jTable1.getValueAt(rowNo,3).toString());
            txtCredit.setText(jTable1.getValueAt(rowNo,4).toString());
            }
        });
    }
     
     public void updateDb()
    {
        
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            PreparedStatement ps=con.prepareStatement("UPDATE tbl_fixdoha SET transDate=?,debit=?,credit=?,description=? where FdohaId=?");
            ps.setString(1,todaydate);
            ps.setString(2, txtDebit.getText());
            ps.setString(3, txtCredit.getText());
            ps.setString(4, txtDescription.getText());
            ps.setInt(5, fDohaId);
            int i=ps.executeUpdate();
            if(i!=0)
            {
                    dispose();
                    viewFixtureCbqform();
            }
            con.close();  
            balance=0;
        }
        catch(Exception e)
        {
            balance=0;
            JOptionPane.showMessageDialog(rootPane, e, "ERROR!!", JOptionPane.ERROR_MESSAGE);
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

        jPanel1 = new javax.swing.JPanel();
        jDate = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtDebit = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtCredit = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescription = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        btnSave = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        View = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setClosable(true);
        setTitle("Fixture Doha Bank");

        jDate.setDateFormatString("yyy-MM-dd");

        jLabel1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel1.setText("Date Of Entry");

        jLabel3.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel3.setText("Debit");

        txtDebit.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txtDebit.setText("0.0");

        jLabel4.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel4.setText("Credit");

        txtCredit.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txtCredit.setText("0.0");

        txtDescription.setColumns(20);
        txtDescription.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txtDescription.setRows(5);
        jScrollPane1.setViewportView(txtDescription);

        jLabel6.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel6.setText("Description");

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnSave.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnSave.setText("Save");
        btnSave.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        jToolBar1.add(btnSave);

        btnUpdate.setEnabled(false);
        btnUpdate.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        jToolBar1.add(btnUpdate);

        btnDelete.setEnabled(false);
        btnDelete.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        jToolBar1.add(btnDelete);

        View.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        View.setText("Date Wise Search");
        View.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        View.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        View.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ViewActionPerformed(evt);
            }
        });
        jToolBar1.add(View);

        btnRefresh.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnRefresh.setText("Refresh");
        btnRefresh.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnRefresh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });
        jToolBar1.add(btnRefresh);

        btnCancel.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnCancel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        jToolBar1.add(btnCancel);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 527, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jDate, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                            .addComponent(txtDebit))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(txtCredit, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(242, 242, 242)))
                .addContainerGap(187, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(txtCredit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4))
                    .addComponent(txtDebit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 165, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 624, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
     
        insertIntoDb();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        
        updateDb();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:   
        int response=JOptionPane.showConfirmDialog(null,"Do you want to delete data Permanently?","Confirm",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
            if(response==JOptionPane.NO_OPTION)
            {

            }
            else if(response==JOptionPane.YES_OPTION)
            {
                try
                {
                    connection c=new connection();
                    Connection con=c.conn();
                    Statement stmt1=con.createStatement();
                    int i=stmt1.executeUpdate("delete from tbl_fixdoha  where FdohaId="+fDohaId);
                    if(i!=0)
                    {
                        dispose();
                        viewFixtureCbqform();
                    }
                }
                catch(Exception e)
                {
                    JOptionPane.showMessageDialog(rootPane, e, "ERROR!!", JOptionPane.ERROR_MESSAGE);
                }
            }
            else if(response==JOptionPane.CLOSED_OPTION)
            {

            }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void ViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ViewActionPerformed
        // TODO add your handling code here:
        FixtureDohaSearch  FDohaSearch=new FixtureDohaSearch();
        AnarTrading.desktopPane1.add(FDohaSearch);
        FDohaSearch.setVisible(true);
        FDohaSearch.show();
    }//GEN-LAST:event_ViewActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // TODO add your handling code here:
        dispose();
        viewFixtureCbqform();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnCancelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton View;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUpdate;
    private com.toedter.calendar.JDateChooser jDate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextField txtCredit;
    private javax.swing.JTextField txtDebit;
    private javax.swing.JTextArea txtDescription;
    // End of variables declaration//GEN-END:variables
    String dateString = "",todaydate;
    float debit,credit,balance;
    public int fDohaId;
    DateFormat defaultDate = new SimpleDateFormat("yyyy-MM-dd");

}
