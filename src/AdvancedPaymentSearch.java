
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
public final class AdvancedPaymentSearch extends javax.swing.JInternalFrame {

    /**
     * Creates new form AdvancedPaymentSearch
     */
    public AdvancedPaymentSearch() {
        initComponents();
        setLocation(middle);
        cmbEmployeeName.setEnabled(false);
        cmbEmployeeName.addItem("--Select Name--");
        cmbEmployeeName.setSelectedItem("--Select Name--");
        cmbMonth.setEnabled(false);
        cmbYear.setEnabled(false);
        cmbEmployeeNameFill();
        queryGenerator();
    }
    public void cmbEmployeeNameFill() {
        try {
            connection c = new connection();
            Connection con = c.conn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT empName FROM tbl_labourdetails order by empName asc");
            while (rs.next()) {
                cmbEmployeeName.addItem(rs.getString(1));
            }
            con.close();
        } catch (SQLException ex) {
            
        }
    }
    public void queryGenerator()
    {
        String query="";
        String empName=cmbEmployeeName.getSelectedItem().toString();
        String month=cmbMonth.getSelectedItem().toString();
        String year=cmbYear.getSelectedItem().toString();
        
        
        if(chkEmployeeName.isSelected()==true && chkMonth.isSelected()==false && chkYear.isSelected()==false )
        {
            query="select advancePaymentId '"+"ID"+"',empName'"+"NAME"+"',pMonth '"+"MONTH"+"',pYear '"+"YEAR"+"',amount '"+"AMOUNT"+"',comments'"+"COMMENTS"+"',paidBy'"+"PAID BY"+"',paidDate '"+"DATE"+"' from tbl_advancepayment where empName=?";
            search(query,empName);
        }
        if(chkEmployeeName.isSelected()==false && chkMonth.isSelected()==true && chkYear.isSelected()==false )
        {
            query="select advancePaymentId '"+"ID"+"',empName'"+"NAME"+"',pMonth '"+"MONTH"+"',pYear '"+"YEAR"+"',amount '"+"AMOUNT"+"',comments'"+"COMMENTS"+"',paidBy'"+"PAID BY"+"',paidDate '"+"DATE"+"' from tbl_advancepayment where pMonth=?";
            search(query,month);
        }
        if(chkEmployeeName.isSelected()==false && chkMonth.isSelected()==false && chkYear.isSelected()==true )
        {
            query="select advancePaymentId '"+"ID"+"',empName'"+"NAME"+"',pMonth '"+"MONTH"+"',pYear '"+"YEAR"+"',amount '"+"AMOUNT"+"',comments'"+"COMMENTS"+"',paidBy'"+"PAID BY"+"',paidDate '"+"DATE"+"' from tbl_advancepayment where pYear=?";
            search(query,year);
        }
        if(chkEmployeeName.isSelected()==true && chkMonth.isSelected()==true && chkYear.isSelected()==false )
        {
            query="select advancePaymentId '"+"ID"+"',empName'"+"NAME"+"',pMonth '"+"MONTH"+"',pYear '"+"YEAR"+"',amount '"+"AMOUNT"+"',comments'"+"COMMENTS"+"',paidBy'"+"PAID BY"+"',paidDate '"+"DATE"+"' from tbl_advancepayment where empName=? and pMonth=?";
            search(query,empName,month);
        }
        if(chkEmployeeName.isSelected()==true && chkMonth.isSelected()==false && chkYear.isSelected()==true )
        {
            query="select advancePaymentId '"+"ID"+"',empName'"+"NAME"+"',pMonth '"+"MONTH"+"',pYear '"+"YEAR"+"',amount '"+"AMOUNT"+"',comments'"+"COMMENTS"+"',paidBy'"+"PAID BY"+"',paidDate '"+"DATE"+"' from tbl_advancepayment where empName=? and pYear=?";
            search(query,empName,year);
        }
        if(chkEmployeeName.isSelected()==false && chkMonth.isSelected()==true && chkYear.isSelected()==true )
        {
            query="select advancePaymentId '"+"ID"+"',empName'"+"NAME"+"',pMonth '"+"MONTH"+"',pYear '"+"YEAR"+"',amount '"+"AMOUNT"+"',comments'"+"COMMENTS"+"',paidBy'"+"PAID BY"+"',paidDate '"+"DATE"+"' from tbl_advancepayment where pMonth=? and pYear=?";
            search(query,month,year);
        }
        if(chkEmployeeName.isSelected()==true && chkMonth.isSelected()==true && chkYear.isSelected()==true )
        {
            query="select advancePaymentId '"+"ID"+"',empName'"+"NAME"+"',pMonth '"+"MONTH"+"',pYear '"+"YEAR"+"',amount '"+"AMOUNT"+"',comments'"+"COMMENTS"+"',paidBy'"+"PAID BY"+"',paidDate '"+"DATE"+"' from tbl_advancepayment where empName=? and pMonth=? and pYear=?";
            search(query,empName,month,year);
        }
        if(chkEmployeeName.isSelected()==false && chkMonth.isSelected()==false && chkYear.isSelected()==false )
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
        chkEmployeeName = new javax.swing.JCheckBox();
        chkMonth = new javax.swing.JCheckBox();
        chkYear = new javax.swing.JCheckBox();
        cmbEmployeeName = new javax.swing.JComboBox();
        cmbMonth = new javax.swing.JComboBox();
        cmbYear = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setClosable(true);
        setTitle("Advance Payment Search");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search Critiria", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Gabriola", 0, 18))); // NOI18N

        chkEmployeeName.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        chkEmployeeName.setText("Employee Name");
        chkEmployeeName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkEmployeeNameActionPerformed(evt);
            }
        });

        chkMonth.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        chkMonth.setText("Month");
        chkMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMonthActionPerformed(evt);
            }
        });

        chkYear.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        chkYear.setText("Year");
        chkYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkYearActionPerformed(evt);
            }
        });

        cmbEmployeeName.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbEmployeeNameItemStateChanged(evt);
            }
        });
        cmbEmployeeName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbEmployeeNameActionPerformed(evt);
            }
        });

        cmbMonth.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        cmbMonth.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "--Select Month--", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));
        cmbMonth.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmbMonth.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbMonthItemStateChanged(evt);
            }
        });
        cmbMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbMonthActionPerformed(evt);
            }
        });

        cmbYear.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "--Select Year--", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2021", "2022" }));
        cmbYear.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmbYear.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbYearItemStateChanged(evt);
            }
        });
        cmbYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbYearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chkEmployeeName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbEmployeeName, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(chkMonth)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(chkYear)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmbYear, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkEmployeeName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbEmployeeName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbYear, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkMonth)
                    .addComponent(chkYear))
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
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbMonthItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbMonthItemStateChanged

    }//GEN-LAST:event_cmbMonthItemStateChanged

    private void cmbMonthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbMonthActionPerformed
        // TODO add your handling code here:
        queryGenerator();
    }//GEN-LAST:event_cmbMonthActionPerformed

    private void cmbYearItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbYearItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbYearItemStateChanged

    private void cmbYearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbYearActionPerformed
        queryGenerator();
    }//GEN-LAST:event_cmbYearActionPerformed

    private void chkEmployeeNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkEmployeeNameActionPerformed
        // TODO add your handling code here:
        if(chkEmployeeName.isSelected()==false)
        {
            cmbEmployeeName.setSelectedItem("--Select Name--");
            cmbEmployeeName.setEnabled(false);
        }
        else
        {
            cmbEmployeeName.setEnabled(true);
            cmbEmployeeName.setEditable(true);
            AutoCompleteDecorator.decorate(cmbEmployeeName);
        }
       
    }//GEN-LAST:event_chkEmployeeNameActionPerformed

    private void chkMonthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMonthActionPerformed
        // TODO add your handling code here:
        if(chkMonth.isSelected()==false)
        {
            cmbMonth.setSelectedItem("--Select Month--");
            cmbMonth.setEnabled(false);
        }
        else
        {
            cmbMonth.setEnabled(true);
            cmbMonth.setEditable(true);
            AutoCompleteDecorator.decorate(cmbMonth);
        }
        
    }//GEN-LAST:event_chkMonthActionPerformed

    private void chkYearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkYearActionPerformed
        // TODO add your handling code here:
        if(chkYear.isSelected()==false)
        {
            cmbYear.setSelectedItem("--Select Year--");
            cmbYear.setEnabled(false);
        }
        else
        {
            cmbYear.setEnabled(true);
            cmbYear.setEditable(true);
            AutoCompleteDecorator.decorate(cmbYear);
        }
    }//GEN-LAST:event_chkYearActionPerformed

    private void cmbEmployeeNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEmployeeNameActionPerformed
        // TODO add your handling code here:
       queryGenerator();
    }//GEN-LAST:event_cmbEmployeeNameActionPerformed

    private void cmbEmployeeNameItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbEmployeeNameItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbEmployeeNameItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox chkEmployeeName;
    private javax.swing.JCheckBox chkMonth;
    private javax.swing.JCheckBox chkYear;
    private javax.swing.JComboBox cmbEmployeeName;
    private javax.swing.JComboBox cmbMonth;
    private javax.swing.JComboBox cmbYear;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
    Point middle = new Point(100,0);
}
