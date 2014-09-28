/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.vijay.contractEmployee;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.proteanit.sql.DbUtils;
import org.vijay.common.AnarTrading;
import org.vijay.common.AutoCompleteDecorator;
import org.vijay.common.DateCellRenderer;
import org.vijay.common.connection;

/**
 *
 * @author MAC
 */
public class ConEmployeesSearch extends javax.swing.JInternalFrame {

    /**
     * Creates new form SearchConEmployees
     */
    public ConEmployeesSearch() {
        initComponents();
        btnDemoblization.setEnabled(false);
        btnChangeSite.setEnabled(false);
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        cmbEmpNameFill();
        viewDbEmployeeDetails();
         jTable1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
            int rowNo=jTable1.getSelectedRow();
            empId=Integer.parseInt(jTable1.getValueAt(rowNo, 1).toString());
            btnDemoblization.setEnabled(true);
            btnChangeSite.setEnabled(true);
            }
        });
    }
    public void deleteAction()
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
                    int i=stmt1.executeUpdate("delete from tbl_conemployee  where empId="+empId);
                    if(i!=0)
                    {
                        dispose();
                        ViewConLabourSearchForm();
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
    public void ViewConLabourSearchForm()
    {
        ConEmployeesSearch  CES=new ConEmployeesSearch();
        AnarTrading.desktopPane1.add(CES);
        CES.setVisible(true);
        CES.show();
    }
    public void viewDbEmployeeDetails()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select @i := @i + 1 '"+"SL.NO"+"',empId '"+"ID"+"',empName'"+"NAME"+"',passportNumber '"+"PASSPORT#"+"',passportExpiry'"+"P.EXPIRY"+"',idNumber'"+"ID #"+"',idExpiry '"+"ID.EXIPRY"+"',joinDate'"+"JOIN.DATE"+"',site '"+"SITE"+"',contractingCompany '"+"CONT.COMAPNY"+"' from tbl_conemployee,(SELECT @i := 0) temp where status=0 order by empName");
            jTable1.setModel(DbUtils.resultSetToTableModel(rs));
            jTable1.setAutoCreateRowSorter(true);
            con.close();
            
            jTable1.getColumnModel().getColumn(0).setMaxWidth(50);
            jTable1.getColumnModel().getColumn(1).setMaxWidth(50);
            jTable1.getColumnModel().getColumn(3).setMaxWidth(200);
            jTable1.getColumnModel().getColumn(4).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(5).setMaxWidth(200);
            jTable1.getColumnModel().getColumn(6).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(7).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(8).setMaxWidth(100);
            
            jTable1.getColumnModel().getColumn(4).setCellRenderer(new DateCellRenderer());
            jTable1.getColumnModel().getColumn(6).setCellRenderer(new DateCellRenderer());
            jTable1.getColumnModel().getColumn(7).setCellRenderer(new DateCellRenderer());

            
            jTable1.setShowHorizontalLines( true );
            jTable1.setRowSelectionAllowed( true );

        }
        catch(Exception e)
        {

        }
    }
    public void viewEmployeeDetailsUsingName()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select @i := @i + 1 '"+"SL.NO"+"',empId '"+"ID"+"',empName'"+"NAME"+"',passportNumber '"+"PASSPORT#"+"',passportExpiry'"+"P.EXPIRY"+"',idNumber'"+"ID #"+"',idExpiry '"+"ID.EXIPRY"+"',joinDate'"+"JOIN.DATE"+"',site '"+"SITE"+"',contractingCompany '"+"CONT.COMAPNY"+"' from tbl_conemployee,(SELECT @i := 0) temp where empname='"+cmbEmployeeName.getSelectedItem()+"'and status=0");
            jTable1.setModel(DbUtils.resultSetToTableModel(rs));
            jTable1.setAutoCreateRowSorter(true);
            con.close();
            

            
            
            jTable1.getColumnModel().getColumn(0).setMaxWidth(50);
            jTable1.getColumnModel().getColumn(1).setMaxWidth(50);
            jTable1.getColumnModel().getColumn(3).setMaxWidth(200);
            jTable1.getColumnModel().getColumn(4).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(5).setMaxWidth(200);
            jTable1.getColumnModel().getColumn(6).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(7).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(8).setMaxWidth(100);
            
            jTable1.getColumnModel().getColumn(4).setCellRenderer(new DateCellRenderer());
            jTable1.getColumnModel().getColumn(6).setCellRenderer(new DateCellRenderer());
            jTable1.getColumnModel().getColumn(7).setCellRenderer(new DateCellRenderer());

            
            jTable1.setShowHorizontalLines( true );
            jTable1.setRowSelectionAllowed( true );

        }
        catch(Exception e)
        {

        }
    }
    public void cmbEmpNameFill() {
        try {
            connection c = new connection();
            Connection con = c.conn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT empName FROM tbl_conemployee WHERE STATUS=0 ORDER BY empName");
            while (rs.next()) {
                cmbEmployeeName.addItem(rs.getString(1));
            }
            con.close();
        } catch (SQLException ex) {
            
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

        cmbEmployeeName = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnRefresh = new javax.swing.JButton();
        jToolBar2 = new javax.swing.JToolBar();
        btnDemoblization = new javax.swing.JButton();
        btnChangeSite = new javax.swing.JButton();
        jToolBar3 = new javax.swing.JToolBar();
        btnView = new javax.swing.JButton();
        btnViewPP = new javax.swing.JButton();
        btnViewRP = new javax.swing.JButton();
        btnViewID = new javax.swing.JButton();

        setClosable(true);
        setTitle("Search Contract Employee");

        cmbEmployeeName.setEditable(true);
        AutoCompleteDecorator.decorate(cmbEmployeeName);
        cmbEmployeeName.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cmbEmployeeName.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "--select name--" }));
        cmbEmployeeName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbEmployeeNameActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel10.setText("Employee Name");

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

        btnRefresh.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnRefresh.setMnemonic('r');
        btnRefresh.setText("Refresh");
        btnRefresh.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnRefresh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        jToolBar2.setFloatable(false);
        jToolBar2.setRollover(true);

        btnDemoblization.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnDemoblization.setText("Demobilize");
        btnDemoblization.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnDemoblization.setFocusable(false);
        btnDemoblization.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnDemoblization.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDemoblization.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDemoblizationActionPerformed(evt);
            }
        });
        jToolBar2.add(btnDemoblization);

        btnChangeSite.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnChangeSite.setText("Change Site");
        btnChangeSite.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnChangeSite.setFocusable(false);
        btnChangeSite.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnChangeSite.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnChangeSite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangeSiteActionPerformed(evt);
            }
        });
        jToolBar2.add(btnChangeSite);

        jToolBar3.setFloatable(false);
        jToolBar3.setRollover(true);

        btnView.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnView.setMnemonic('v');
        btnView.setText("View");
        btnView.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnView.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewActionPerformed(evt);
            }
        });
        jToolBar3.add(btnView);

        btnViewPP.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnViewPP.setMnemonic('P');
        btnViewPP.setText("View PP");
        btnViewPP.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnViewPP.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnViewPP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewPPActionPerformed(evt);
            }
        });
        jToolBar3.add(btnViewPP);

        btnViewRP.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnViewRP.setMnemonic('R');
        btnViewRP.setText("View RP");
        btnViewRP.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnViewRP.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnViewRP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewRPActionPerformed(evt);
            }
        });
        jToolBar3.add(btnViewRP);

        btnViewID.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnViewID.setMnemonic('I');
        btnViewID.setText("View ID");
        btnViewID.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnViewID.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnViewID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewIDActionPerformed(evt);
            }
        });
        jToolBar3.add(btnViewID);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(cmbEmployeeName, 0, 306, Short.MAX_VALUE)
                .addGap(295, 295, 295)
                .addComponent(btnRefresh)
                .addContainerGap())
            .addComponent(jScrollPane1)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jToolBar3, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbEmployeeName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRefresh))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToolBar3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbEmployeeNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEmployeeNameActionPerformed
        // TODO add your handling code here:
        viewEmployeeDetailsUsingName();
        
    }//GEN-LAST:event_cmbEmployeeNameActionPerformed

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
        // TODO add your handling code here:
        int keyCode = evt.getKeyCode();
        if(keyCode == KeyEvent.VK_ENTER)
        {
            ContractLabourDetails  CLD=new ContractLabourDetails(empId);
            AnarTrading.desktopPane1.add(CLD);
            CLD.setVisible(true);
            CLD.show();
        }
        if(keyCode == KeyEvent.VK_DELETE)
        {
            deleteAction();
        }
    }//GEN-LAST:event_jTable1KeyPressed

    private void jTable1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTable1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1FocusGained

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // TODO add your handling code here:
        dispose();
        ViewConLabourSearchForm();

    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnDemoblizationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDemoblizationActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        DemobilizationContractEmployee demob=new DemobilizationContractEmployee(null, closable,empId);
        demob.setVisible(true);
    }//GEN-LAST:event_btnDemoblizationActionPerformed

    private void btnChangeSiteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangeSiteActionPerformed
        // TODO add your handling code here:
        ChangeSiteContractEmployee changeSite=new ChangeSiteContractEmployee(null, closable,empId);
        changeSite.setVisible(true);
    }//GEN-LAST:event_btnChangeSiteActionPerformed

    private void btnViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewActionPerformed
        // TODO add your handling code here:
        ConEmployeesSearch CES = new ConEmployeesSearch();
        AnarTrading.desktopPane1.add(CES);
        CES.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnViewActionPerformed

    private void btnViewPPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewPPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnViewPPActionPerformed

    private void btnViewRPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewRPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnViewRPActionPerformed

    private void btnViewIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnViewIDActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChangeSite;
    private javax.swing.JButton btnDemoblization;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnView;
    private javax.swing.JButton btnViewID;
    private javax.swing.JButton btnViewPP;
    private javax.swing.JButton btnViewRP;
    private javax.swing.JComboBox cmbEmployeeName;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JToolBar jToolBar3;
    // End of variables declaration//GEN-END:variables
    int empId;
}
