/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.vijay.bank;

import org.vijay.petty.*;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.proteanit.sql.DbUtils;
import org.vijay.common.AnarTrading;
import org.vijay.common.CurrentWorkingDirectory;
import org.vijay.common.DateCellRenderer;
import org.vijay.common.NumberRenderer;
import org.vijay.common.connection;
import org.vijay.employee.LabourDetails;
import org.vijay.invoice.InvoiceEntry;
import org.vijay.report.ReportView;

/**
 *
 * @author MAC
 */
public class AnarCBQsearch extends javax.swing.JInternalFrame {

    /**
     * Creates new form pettySearch
     */
    public AnarCBQsearch() {
        initComponents();
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        jDateFrom.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("date")) {
                    Fromdate = new SimpleDateFormat("yyyy-MM-dd").format(jDateFrom.getDate());
                }
            }  
        });
        jDateTo.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("date")) {
                    Todate = new SimpleDateFormat("yyyy-MM-dd").format(jDateTo.getDate());
                }
            }  
        });
        jTable1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
            int rowNo=jTable1.getSelectedRow();
            aCbqId=Integer.parseInt(jTable1.getValueAt(rowNo,0).toString());
            }
        });
        truncateTempDbTable();
        insertIntoTempDbTable();
        CurrentWorkingDirectory CWD=new CurrentWorkingDirectory();
        path=CWD.getpath();
    }
    public void ViewAnarCbqSearchForm()
    {
        AnarCBQsearch  AcbqSearch=new AnarCBQsearch();
        AnarTrading.desktopPane1.add(AcbqSearch);
        AcbqSearch.setVisible(true);
        AcbqSearch.show();
    }
    
    public void Search()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT X.AcbqId,X.transDate,X.description, X.debit, X.credit, SUM(Y.bal) balance FROM( SELECT *,credit-debit bal FROM tbl_temp_anarcbq) X JOIN( SELECT *,credit-debit bal FROM tbl_temp_anarcbq) Y ON Y.id <= X.id  WHERE X.transDate BETWEEN '"+Fromdate+"' AND '"+Todate+"' GROUP BY X.id");
            
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
            jTable1.setEnabled(true);

        }
        catch(Exception e)
        {

        }
    }
    public void insertIntoTempDbTable()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            PreparedStatement ps=con.prepareStatement("INSERT INTO tbl_temp_anarcbq (AcbqId,transDate,debit,credit,description) SELECT * FROM tbl_anarcbq ORDER BY transDate,AcbqId");
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
            PreparedStatement ps=con.prepareStatement("TRUNCATE TABLE tbl_temp_anarcbq");
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
        jDateFrom = new com.toedter.calendar.JDateChooser();
        jDateTo = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        btnSearch = new javax.swing.JButton();
        btnPrint = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setClosable(true);
        setTitle("Date Wise-Anar CBQ");

        jTable1.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable1.setEnabled(false);
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

        jDateFrom.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jDateTo.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel1.setText("From Date");

        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel2.setText("To Date");

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnSearch.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnSearch.setText("Search");
        btnSearch.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });
        jToolBar1.add(btnSearch);

        btnPrint.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnPrint.setText("Print");
        btnPrint.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnPrint.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });
        jToolBar1.add(btnPrint);

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 827, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(137, 137, 137)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDateFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDateTo, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jDateFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed

        Search();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
  //       TODO add your handling code here:
            HashMap para=new HashMap();
            para.put("fromDate",Fromdate);
            para.put("toDate",Todate);
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Bank\\AnarCBQ.jasper"),para);
            re.setVisible(true);

    }//GEN-LAST:event_btnPrintActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // TODO add your handling code here:
        dispose();
        ViewAnarCbqSearchForm();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelActionPerformed

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
        // TODO add your handling code here:
         int keyCode = evt.getKeyCode();
        if(keyCode == KeyEvent.VK_ENTER)
        {
            AnarCBQ AcbqEntry=new AnarCBQ(aCbqId);
            AnarTrading.desktopPane1.add(AcbqEntry);
            AcbqEntry.setVisible(true);
        }
        if(keyCode == KeyEvent.VK_DELETE)
        {
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
                    int i=stmt1.executeUpdate("delete from tbl_anarcbq  where AcbqId="+aCbqId);
                    if(i!=0)
                    {
                        dispose();
                        AnarCBQsearch  ACS=new AnarCBQsearch();
                        AnarTrading.desktopPane1.add(ACS);
                        ACS.setVisible(true);
                        ACS.show();
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
        }
    }//GEN-LAST:event_jTable1KeyPressed

    private void jTable1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTable1FocusGained
        // TODO add your handling code here:
        Search();
    }//GEN-LAST:event_jTable1FocusGained


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSearch;
    private com.toedter.calendar.JDateChooser jDateFrom;
    private com.toedter.calendar.JDateChooser jDateTo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
    String dateString = "",Fromdate,Todate,path;
    public int aCbqId;
}
