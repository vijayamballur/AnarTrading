
import java.awt.Point;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author MAC
 */
public final class AdvancedEmployeeSearch extends javax.swing.JInternalFrame {

    /**
     * Creates new form AdvancedPaymentSearch
     */
    public AdvancedEmployeeSearch() {
        initComponents();
        setLocation(middle);
        cmbCurrentSite.setEnabled(false);
        cmbContracting.setEnabled(false);
        cmbChild.setEnabled(false);
        cmbParent.setEnabled(false);
//        cmbEmployeeNameFill();
//        queryGenerator();
    }
    public void cmbEmployeeNameFill() {
        try {
            connection c = new connection();
            Connection con = c.conn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT empName FROM tbl_labourdetails order by empName asc");
            while (rs.next()) {
                cmbParent.addItem(rs.getString(1));
            }
            con.close();
        } catch (SQLException ex) {
            
        }
    }
    public void queryGenerator()
    {
        String query="";
        String empName=cmbParent.getSelectedItem().toString();
        String month=cmbCurrentSite.getSelectedItem().toString();
        String year=cmbContracting.getSelectedItem().toString();
        
        
        if(chkParentCompany.isSelected()==true && chkChildCompany.isSelected()==false && chkContractingCompany.isSelected()==false )
        {
            query="select advancePaymentId '"+"ID"+"',empName'"+"NAME"+"',pMonth '"+"MONTH"+"',pYear '"+"YEAR"+"',amount '"+"AMOUNT"+"',comments'"+"COMMENTS"+"',paidBy'"+"PAID BY"+"',paidDate '"+"DATE"+"' from tbl_advancepayment where empName=?";
            search(query,empName);
        }
        if(chkParentCompany.isSelected()==false && chkChildCompany.isSelected()==true && chkContractingCompany.isSelected()==false )
        {
            query="select advancePaymentId '"+"ID"+"',empName'"+"NAME"+"',pMonth '"+"MONTH"+"',pYear '"+"YEAR"+"',amount '"+"AMOUNT"+"',comments'"+"COMMENTS"+"',paidBy'"+"PAID BY"+"',paidDate '"+"DATE"+"' from tbl_advancepayment where pMonth=?";
            search(query,month);
        }
        if(chkParentCompany.isSelected()==false && chkChildCompany.isSelected()==false && chkContractingCompany.isSelected()==true )
        {
            query="select advancePaymentId '"+"ID"+"',empName'"+"NAME"+"',pMonth '"+"MONTH"+"',pYear '"+"YEAR"+"',amount '"+"AMOUNT"+"',comments'"+"COMMENTS"+"',paidBy'"+"PAID BY"+"',paidDate '"+"DATE"+"' from tbl_advancepayment where pYear=?";
            search(query,year);
        }
        if(chkParentCompany.isSelected()==true && chkChildCompany.isSelected()==true && chkContractingCompany.isSelected()==false )
        {
            query="select advancePaymentId '"+"ID"+"',empName'"+"NAME"+"',pMonth '"+"MONTH"+"',pYear '"+"YEAR"+"',amount '"+"AMOUNT"+"',comments'"+"COMMENTS"+"',paidBy'"+"PAID BY"+"',paidDate '"+"DATE"+"' from tbl_advancepayment where empName=? and pMonth=?";
            search(query,empName,month);
        }
        if(chkParentCompany.isSelected()==true && chkChildCompany.isSelected()==false && chkContractingCompany.isSelected()==true )
        {
            query="select advancePaymentId '"+"ID"+"',empName'"+"NAME"+"',pMonth '"+"MONTH"+"',pYear '"+"YEAR"+"',amount '"+"AMOUNT"+"',comments'"+"COMMENTS"+"',paidBy'"+"PAID BY"+"',paidDate '"+"DATE"+"' from tbl_advancepayment where empName=? and pYear=?";
            search(query,empName,year);
        }
        if(chkParentCompany.isSelected()==false && chkChildCompany.isSelected()==true && chkContractingCompany.isSelected()==true )
        {
            query="select advancePaymentId '"+"ID"+"',empName'"+"NAME"+"',pMonth '"+"MONTH"+"',pYear '"+"YEAR"+"',amount '"+"AMOUNT"+"',comments'"+"COMMENTS"+"',paidBy'"+"PAID BY"+"',paidDate '"+"DATE"+"' from tbl_advancepayment where pMonth=? and pYear=?";
            search(query,month,year);
        }
        if(chkParentCompany.isSelected()==true && chkChildCompany.isSelected()==true && chkContractingCompany.isSelected()==true )
        {
            query="select advancePaymentId '"+"ID"+"',empName'"+"NAME"+"',pMonth '"+"MONTH"+"',pYear '"+"YEAR"+"',amount '"+"AMOUNT"+"',comments'"+"COMMENTS"+"',paidBy'"+"PAID BY"+"',paidDate '"+"DATE"+"' from tbl_advancepayment where empName=? and pMonth=? and pYear=?";
            search(query,empName,month,year);
        }
        if(chkParentCompany.isSelected()==false && chkChildCompany.isSelected()==false && chkContractingCompany.isSelected()==false )
        {
            query="select advancePaymentId '"+"ID"+"',empName'"+"NAME"+"',pMonth '"+"MONTH"+"',pYear '"+"YEAR"+"',amount '"+"AMOUNT"+"',comments'"+"COMMENTS"+"',paidBy'"+"PAID BY"+"',paidDate '"+"DATE"+"' from tbl_advancepayment";
            search(query);
        }
    }
    public void search(String query,String field1)
    {
        connection c=new connection();
        Connection con=c.conn();
            try
            {
                PreparedStatement ps=con.prepareStatement(query);
                ps.setString(1, field1);
                ResultSet rs=ps.executeQuery();
                jTable1.setModel(DbUtils.resultSetToTableModel(rs));
                con.close();
                jTable1.getColumnModel().getColumn(0).setMinWidth(0);
                jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
                jTable1.getColumnModel().getColumn(0).setWidth(0);
                jTable1.setShowHorizontalLines( false );
                jTable1.setRowSelectionAllowed( true );

            }
            catch(Exception e)
            {

            }      
    }
    public void search(String query,String field1,String field2)
    {
        connection c=new connection();
        Connection con=c.conn();
            try
            {
                PreparedStatement ps=con.prepareStatement(query);
                ps.setString(1, field1);
                ps.setString(2, field2);
                ResultSet rs=ps.executeQuery();
                jTable1.setModel(DbUtils.resultSetToTableModel(rs));
                con.close();
                jTable1.getColumnModel().getColumn(0).setMinWidth(0);
                jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
                jTable1.getColumnModel().getColumn(0).setWidth(0);
                jTable1.setShowHorizontalLines( false );
                jTable1.setRowSelectionAllowed( true );

            }
            catch(Exception e)
            {

            }      
    }
    public void search(String query,String field1,String field2,String field3)
    {
        connection c=new connection();
        Connection con=c.conn();
            try
            {
                PreparedStatement ps=con.prepareStatement(query);
                ps.setString(1, field1);
                ps.setString(2, field2);
                ps.setString(3, field3);
                ResultSet rs=ps.executeQuery();
                jTable1.setModel(DbUtils.resultSetToTableModel(rs));
                con.close();
                jTable1.getColumnModel().getColumn(0).setMinWidth(0);
                jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
                jTable1.getColumnModel().getColumn(0).setWidth(0);
                jTable1.setShowHorizontalLines( false );
                jTable1.setRowSelectionAllowed( true );

            }
            catch(Exception e)
            {

            }      
    }
    public void search(String query)
    {
        connection c=new connection();
        Connection con=c.conn();
            try
            {
                PreparedStatement ps=con.prepareStatement(query);
                ResultSet rs=ps.executeQuery();
                jTable1.setModel(DbUtils.resultSetToTableModel(rs));
                con.close();
                jTable1.getColumnModel().getColumn(0).setMinWidth(0);
                jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
                jTable1.getColumnModel().getColumn(0).setWidth(0);
                jTable1.setShowHorizontalLines( false );
                jTable1.setRowSelectionAllowed( true );

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

        jPanel1 = new javax.swing.JPanel();
        chkParentCompany = new javax.swing.JCheckBox();
        chkChildCompany = new javax.swing.JCheckBox();
        chkContractingCompany = new javax.swing.JCheckBox();
        cmbParent = new javax.swing.JComboBox();
        cmbCurrentSite = new javax.swing.JComboBox();
        cmbContracting = new javax.swing.JComboBox();
        chkCurrentSite = new javax.swing.JCheckBox();
        cmbChild = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setClosable(true);
        setTitle("Advance Payment Search");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search Critiria", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Gabriola", 0, 18))); // NOI18N

        chkParentCompany.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        chkParentCompany.setText("Parent Company");
        chkParentCompany.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkParentCompanyActionPerformed(evt);
            }
        });

        chkChildCompany.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        chkChildCompany.setText("Child Company");
        chkChildCompany.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkChildCompanyActionPerformed(evt);
            }
        });

        chkContractingCompany.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        chkContractingCompany.setText("Contracting Company");
        chkContractingCompany.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkContractingCompanyActionPerformed(evt);
            }
        });

        cmbParent.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbParentItemStateChanged(evt);
            }
        });
        cmbParent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbParentActionPerformed(evt);
            }
        });

        cmbCurrentSite.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        cmbCurrentSite.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmbCurrentSite.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbCurrentSiteItemStateChanged(evt);
            }
        });
        cmbCurrentSite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbCurrentSiteActionPerformed(evt);
            }
        });

        cmbContracting.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmbContracting.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbContractingItemStateChanged(evt);
            }
        });
        cmbContracting.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbContractingActionPerformed(evt);
            }
        });

        chkCurrentSite.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        chkCurrentSite.setText("Curttent Site");
        chkCurrentSite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkCurrentSiteActionPerformed(evt);
            }
        });

        cmbChild.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        cmbChild.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmbChild.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbChildItemStateChanged(evt);
            }
        });
        cmbChild.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbChildActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(chkContractingCompany)
                        .addGap(18, 18, 18)
                        .addComponent(cmbContracting, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(chkParentCompany)
                        .addGap(48, 48, 48)
                        .addComponent(cmbParent, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(chkChildCompany)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbChild, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(chkCurrentSite)
                        .addGap(18, 18, 18)
                        .addComponent(cmbCurrentSite, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkParentCompany, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbParent, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkChildCompany)
                    .addComponent(cmbChild, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkContractingCompany)
                    .addComponent(cmbContracting, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkCurrentSite)
                    .addComponent(cmbCurrentSite, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
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
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 946, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbCurrentSiteItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbCurrentSiteItemStateChanged

    }//GEN-LAST:event_cmbCurrentSiteItemStateChanged

    private void cmbCurrentSiteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCurrentSiteActionPerformed
        // TODO add your handling code here:
        queryGenerator();
    }//GEN-LAST:event_cmbCurrentSiteActionPerformed

    private void cmbContractingItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbContractingItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbContractingItemStateChanged

    private void cmbContractingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbContractingActionPerformed
        queryGenerator();
    }//GEN-LAST:event_cmbContractingActionPerformed

    private void chkParentCompanyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkParentCompanyActionPerformed
        // TODO add your handling code here:
        if(chkParentCompany.isSelected()==false)
        {
            
            cmbParent.setEnabled(false);
        }
        else
        {
            cmbParent.setEnabled(true);
            cmbParent.setEditable(true);
            cmbParent.setSelectedItem("--Select Company--");
            AutoCompleteDecorator.decorate(cmbParent);
        }
       
    }//GEN-LAST:event_chkParentCompanyActionPerformed

    private void chkChildCompanyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkChildCompanyActionPerformed
        // TODO add your handling code here:
        if(chkChildCompany.isSelected()==false)
        {
            cmbChild.setEnabled(false);
            
        }
        else
        {
            cmbChild.setEnabled(true);
            cmbChild.setEditable(true);
            cmbChild.setSelectedItem("--Select Month--");
            AutoCompleteDecorator.decorate(cmbChild);
        }
        
    }//GEN-LAST:event_chkChildCompanyActionPerformed

    private void chkContractingCompanyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkContractingCompanyActionPerformed
        // TODO add your handling code here:
        if(chkContractingCompany.isSelected()==false)
        {
            
            cmbContracting.setEnabled(false);
        }
        else
        {
            cmbContracting.setEnabled(true);
            cmbContracting.setEditable(true);
            cmbContracting.setSelectedItem("--Select Year--");
            AutoCompleteDecorator.decorate(cmbContracting);
        }
    }//GEN-LAST:event_chkContractingCompanyActionPerformed

    private void cmbParentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbParentActionPerformed
        // TODO add your handling code here:
       queryGenerator();
    }//GEN-LAST:event_cmbParentActionPerformed

    private void cmbParentItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbParentItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbParentItemStateChanged

    private void chkCurrentSiteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkCurrentSiteActionPerformed
        // TODO add your handling code here:
        if(chkCurrentSite.isSelected()==false)
        {
            
            cmbCurrentSite.setEnabled(false);
        }
        else
        {
            cmbCurrentSite.setEnabled(true);
            cmbCurrentSite.setEditable(true);
            cmbCurrentSite.setSelectedItem("--Select Name--");
            AutoCompleteDecorator.decorate(cmbCurrentSite);
        }
    }//GEN-LAST:event_chkCurrentSiteActionPerformed

    private void cmbChildItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbChildItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbChildItemStateChanged

    private void cmbChildActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbChildActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbChildActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox chkChildCompany;
    private javax.swing.JCheckBox chkContractingCompany;
    private javax.swing.JCheckBox chkCurrentSite;
    private javax.swing.JCheckBox chkParentCompany;
    private javax.swing.JComboBox cmbChild;
    private javax.swing.JComboBox cmbContracting;
    private javax.swing.JComboBox cmbCurrentSite;
    private javax.swing.JComboBox cmbParent;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
    Point middle = new Point(100,0);
}
