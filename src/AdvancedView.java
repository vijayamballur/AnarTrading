
import java.awt.Point;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
public final class AdvancedView extends javax.swing.JInternalFrame {

    /**
     * Creates new form AdavancedView
     */
    public AdvancedView() {
        initComponents();
        setLocation(middle);
        cmbfirstParty.addItem("");
        cmbfirstParty.setSelectedItem("");
        cmbSecondParty.setSelectedItem("");
        viewDbEmployeeDetails();
        cmbFirstPartyFill();
        cmbSecondPartyFill();
        
        cmbfirstParty.setEditable(true);
        cmbSecondParty.setEditable(true);
        
        AutoCompleteDecorator.decorate(cmbfirstParty);
        AutoCompleteDecorator.decorate(cmbSecondParty);
    }
    public void viewDbEmployeeDetails()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select @i := @i + 1 '"+"SL.NO"+"',empId '"+"ID"+"',empName'"+"NAME"+"',nationality '"+"NATIONALITY"+"',profession '"+"PROFESSION"+"',passportNumber '"+"PASSPORT#"+"',CASE passportExpiry  WHEN '1111-11-11' THEN '-' ELSE DATE_FORMAT(passportExpiry,'%d %b %y')END '"+"P.EXPIRY"+"',CASE visaExpiry  WHEN '1111-11-11' THEN '-' ELSE DATE_FORMAT(visaExpiry,'%d %b %y')END'"+"ID.EXPIRY"+"',idNumber '"+"ID#"+"',CASE todayDate  WHEN '1111-11-11' THEN '-' ELSE DATE_FORMAT(todayDate,'%d %b %y')END'"+"DATE"+"',CASE dob  WHEN '1111-11-11' THEN '-' ELSE DATE_FORMAT(dob,'%d %b %y')END'"+"DOB"+"',currentSite '"+"SITE"+"',firstParty '"+"FIRST PARTY"+"',secondParty '"+"SECOND PARTY"+"' from tbl_labourdetails, (SELECT @i := 0) temp order by empName");
            jTable1.setModel(DbUtils.resultSetToTableModel(rs));
            con.close();
            jTable1.getColumnModel().getColumn(1).setMinWidth(0);
            jTable1.getColumnModel().getColumn(1).setMaxWidth(0);
            jTable1.getColumnModel().getColumn(1).setWidth(0);
            jTable1.setShowHorizontalLines( false );
            jTable1.setRowSelectionAllowed( true );

        }
        catch(Exception e)
        {

        }
    }
    public void viewDbEmployeeDetails1()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            PreparedStatement ps=con.prepareStatement("select @i := @i + 1 '"+"SL.NO"+"',empId '"+"ID"+"',empName'"+"NAME"+"',nationality '"+"NATIONALITY"+"',profession '"+"PROFESSION"+"',passportNumber '"+"PASSPORT#"+"',CASE passportExpiry  WHEN '1111-11-11' THEN '-' ELSE DATE_FORMAT(passportExpiry,'%d %b %y')END '"+"P.EXPIRY"+"',CASE visaExpiry  WHEN '1111-11-11' THEN '-' ELSE DATE_FORMAT(visaExpiry,'%d %b %y')END'"+"ID.EXPIRY"+"',idNumber '"+"ID#"+"',CASE todayDate  WHEN '1111-11-11' THEN '-' ELSE DATE_FORMAT(todayDate,'%d %b %y')END'"+"DATE"+"',CASE dob  WHEN '1111-11-11' THEN '-' ELSE DATE_FORMAT(dob,'%d %b %y')END'"+"DOB"+"',currentSite '"+"SITE"+"',firstParty '"+"FIRST PARTY"+"',secondParty '"+"SECOND PARTY"+"' from tbl_labourdetails, (SELECT @i := 0) temp where firstParty=? and secondParty=? order by empName");
            ps.setString(1, cmbfirstParty.getSelectedItem().toString());
            ps.setString(2, cmbSecondParty.getSelectedItem().toString());
            ResultSet rs=ps.executeQuery();
            jTable1.setModel(DbUtils.resultSetToTableModel(rs));
            con.close();
            jTable1.getColumnModel().getColumn(1).setMinWidth(0);
            jTable1.getColumnModel().getColumn(1).setMaxWidth(0);
            jTable1.getColumnModel().getColumn(1).setWidth(0);
            jTable1.setShowHorizontalLines( false );
            jTable1.setRowSelectionAllowed( true );

        }
        catch(Exception e)
        {

        }
    }
    public void firstPartyNull()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            PreparedStatement ps=con.prepareStatement("select @i := @i + 1 '"+"SL.NO"+"',empId '"+"ID"+"',empName'"+"NAME"+"',nationality '"+"NATIONALITY"+"',profession '"+"PROFESSION"+"',passportNumber '"+"PASSPORT#"+"',CASE passportExpiry  WHEN '1111-11-11' THEN '-' ELSE DATE_FORMAT(passportExpiry,'%d %b %y')END '"+"P.EXPIRY"+"',CASE visaExpiry  WHEN '1111-11-11' THEN '-' ELSE DATE_FORMAT(visaExpiry,'%d %b %y')END'"+"ID.EXPIRY"+"',idNumber '"+"ID#"+"',CASE todayDate  WHEN '1111-11-11' THEN '-' ELSE DATE_FORMAT(todayDate,'%d %b %y')END'"+"DATE"+"',CASE dob  WHEN '1111-11-11' THEN '-' ELSE DATE_FORMAT(dob,'%d %b %y')END'"+"DOB"+"',currentSite '"+"SITE"+"',firstParty '"+"FIRST PARTY"+"',secondParty '"+"SECOND PARTY"+"' from tbl_labourdetails, (SELECT @i := 0) temp where secondParty=? order by empName");
            ps.setString(1, cmbSecondParty.getSelectedItem().toString());
            ResultSet rs=ps.executeQuery();
            jTable1.setModel(DbUtils.resultSetToTableModel(rs));
            con.close();
            jTable1.getColumnModel().getColumn(1).setMinWidth(0);
            jTable1.getColumnModel().getColumn(1).setMaxWidth(0);
            jTable1.getColumnModel().getColumn(1).setWidth(0);
            jTable1.setShowHorizontalLines( false );
            jTable1.setRowSelectionAllowed( true );

        }
        catch(Exception e)
        {

        }
    }
    public void secondPartyNull()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            PreparedStatement ps=con.prepareStatement("select @i := @i + 1 '"+"SL.NO"+"',empId '"+"ID"+"',empName'"+"NAME"+"',nationality '"+"NATIONALITY"+"',profession '"+"PROFESSION"+"',passportNumber '"+"PASSPORT#"+"',CASE passportExpiry  WHEN '1111-11-11' THEN '-' ELSE DATE_FORMAT(passportExpiry,'%d %b %y')END '"+"P.EXPIRY"+"',CASE visaExpiry  WHEN '1111-11-11' THEN '-' ELSE DATE_FORMAT(visaExpiry,'%d %b %y')END'"+"ID.EXPIRY"+"',idNumber '"+"ID#"+"',CASE todayDate  WHEN '1111-11-11' THEN '-' ELSE DATE_FORMAT(todayDate,'%d %b %y')END'"+"DATE"+"',CASE dob  WHEN '1111-11-11' THEN '-' ELSE DATE_FORMAT(dob,'%d %b %y')END'"+"DOB"+"',currentSite '"+"SITE"+"',firstParty '"+"FIRST PARTY"+"',secondParty '"+"SECOND PARTY"+"' from tbl_labourdetails, (SELECT @i := 0) temp where firstParty=? order by empName");
            ps.setString(1, cmbfirstParty.getSelectedItem().toString());
            ResultSet rs=ps.executeQuery();
            jTable1.setModel(DbUtils.resultSetToTableModel(rs));
            con.close();
            jTable1.getColumnModel().getColumn(1).setMinWidth(0);
            jTable1.getColumnModel().getColumn(1).setMaxWidth(0);
            jTable1.getColumnModel().getColumn(1).setWidth(0);
            jTable1.setShowHorizontalLines( false );
            jTable1.setRowSelectionAllowed( true );

        }
        catch(Exception e)
        {

        }
    }
    public void ViewAdvancedViewForm()
    {
        AdvancedView  av=new AdvancedView();
        AnarTrading.desktopPane.add(av);
        av.setVisible(true);
        av.show();
    }
    public void cmbFirstPartyFill() {
        try {
            connection c = new connection();
            Connection con = c.conn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT firstParty FROM tbl_labourdetails ORDER BY firstParty ASC");
            while (rs.next()) {
                cmbfirstParty.addItem(rs.getString(1));
            }
            con.close();
        } catch (SQLException ex) {
            
        }
    }
    public void cmbSecondPartyFill() {
        try {
            connection c = new connection();
            Connection con = c.conn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT secondParty FROM tbl_labourdetails order by secondParty asc");
            while (rs.next()) {
                cmbSecondParty.addItem(rs.getString(1));
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cmbfirstParty = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        cmbSecondParty = new javax.swing.JComboBox();
        btnRefresh = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnGenerate = new javax.swing.JButton();

        setTitle("Advanced View");
        setPreferredSize(new java.awt.Dimension(1138, 823));

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

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Filtter Options", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Gabriola", 0, 18))); // NOI18N
        jPanel1.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        jLabel1.setText("First Party");

        jLabel2.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        jLabel2.setText("Second Party");

        btnRefresh.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        btnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/clear.png"))); // NOI18N
        btnRefresh.setText("Refresh");
        btnRefresh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        btnCancel.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/CANCEL.PNG"))); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnGenerate.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        btnGenerate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/MODIFY.PNG"))); // NOI18N
        btnGenerate.setText("Generate");
        btnGenerate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGenerate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(cmbfirstParty, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(cmbSecondParty, javax.swing.GroupLayout.PREFERRED_SIZE, 483, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnGenerate, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cmbfirstParty, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(cmbSecondParty, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancel)
                    .addComponent(btnRefresh)
                    .addComponent(btnGenerate, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // TODO add your handling code here:
        dispose();
        ViewAdvancedViewForm();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        dispose();
        AnarTrading.btnAdvanced.setEnabled(true);
        AnarTrading.menuItemReceiptVoucher.setEnabled(true);
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnGenerateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerateActionPerformed

        if(cmbfirstParty.getSelectedItem().toString().equals("") && !cmbSecondParty.getSelectedItem().toString().equals(""))
        {
            firstPartyNull();
        }
        else if(!cmbfirstParty.getSelectedItem().toString().equals("") && cmbSecondParty.getSelectedItem().toString().equals(""))
        {
            secondPartyNull();
        }
        else if(!cmbfirstParty.getSelectedItem().toString().equals("") && !cmbSecondParty.getSelectedItem().toString().equals(""))
        {
            
        }
        else
        {
            
        }
        //viewDbEmployeeDetails1();
        
    }//GEN-LAST:event_btnGenerateActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnGenerate;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JComboBox cmbSecondParty;
    private javax.swing.JComboBox cmbfirstParty;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
    Point middle = new Point(100,0);
}
