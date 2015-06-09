package org.vijay.employee;


import org.vijay.common.AnarTrading;
import org.vijay.common.connection;
import org.vijay.common.AutoCompleteDecorator;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.proteanit.sql.DbUtils;
import org.vijay.common.DateCellRenderer;
import org.vijay.common.NumberRenderer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author MAC
 */
public class AdvancedEmployeeSearch extends javax.swing.JInternalFrame {

    /**
     * Creates new form AdvancedEmployeeSearch
     */
    public AdvancedEmployeeSearch() {
        initComponents();
        setLocation(middle);
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        cmbSponserFill();
        cmbEmployeeTypeFill();
        cmbEmployeeStatus();
        queryGenerator();
        jTable1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
            int rowNo=jTable1.getSelectedRow();
            empId=Integer.parseInt(jTable1.getValueAt(rowNo,1).toString());

            }
        });
    }
    
    public void queryGenerator()
    {
        String query="";
        String sponser=cmbSponserName.getSelectedItem().toString();
        String empType=cmbEmployeeType.getSelectedItem().toString();
        String status=cmbStatus.getSelectedItem().toString();
        
        if(chkSponser.isSelected()==false && chkSponser.isSelected()==false && chkStatus.isSelected()==false)
        {
            query="select @i := @i + 1 '"+"SL.NO"+"',empId '"+"ID"+"',empName'"+"NAME"+"',nationality '"+"NATIONALITY"+"',profession '"+"PROFESSION"+"',passportNumber '"+"PASSPORT#"+"',passportExpiry'"+"P.EXPIRY"+"',visaExpiry'"+"ID.EXPIRY"+"',idNumber '"+"ID#"+"',todayDate'"+"DATE"+"',dob'"+"DOB"+"',firstParty '"+"FIRST PARTY"+"',employeeType '"+"EMPLOYEE TYPE"+"' from tbl_labourdetails,(SELECT @i := 0) temp order by empId desc";
            search(query);
        }
        if(chkSponser.isSelected()==false && chkSponser.isSelected()==false && chkStatus.isSelected()==true)
        {
            query="select @i := @i + 1 '"+"SL.NO"+"',empId '"+"ID"+"',empName'"+"NAME"+"',nationality '"+"NATIONALITY"+"',profession '"+"PROFESSION"+"',passportNumber '"+"PASSPORT#"+"',passportExpiry'"+"P.EXPIRY"+"',visaExpiry'"+"ID.EXPIRY"+"',idNumber '"+"ID#"+"',todayDate'"+"DATE"+"',dob'"+"DOB"+"',firstParty '"+"FIRST PARTY"+"',employeeType '"+"EMPLOYEE TYPE"+"' from tbl_labourdetails,(SELECT @i := 0) temp where Status=? order by empId desc";
            search(query,status);
        }
        if(chkSponser.isSelected()==false && chkEmployeeType.isSelected()==true && chkStatus.isSelected()==false)
        {
            query="select @i := @i + 1 '"+"SL.NO"+"',empId '"+"ID"+"',empName'"+"NAME"+"',nationality '"+"NATIONALITY"+"',profession '"+"PROFESSION"+"',passportNumber '"+"PASSPORT#"+"',passportExpiry'"+"P.EXPIRY"+"',visaExpiry'"+"ID.EXPIRY"+"',idNumber '"+"ID#"+"',todayDate'"+"DATE"+"',dob'"+"DOB"+"',firstParty '"+"FIRST PARTY"+"',employeeType '"+"EMPLOYEE TYPE"+"' from tbl_labourdetails,(SELECT @i := 0) temp where employeeType=? order by empId desc";
            search(query,empType);
        }
        if(chkSponser.isSelected()==false && chkEmployeeType.isSelected()==true && chkStatus.isSelected()==true)
        {
            query="select @i := @i + 1 '"+"SL.NO"+"',empId '"+"ID"+"',empName'"+"NAME"+"',nationality '"+"NATIONALITY"+"',profession '"+"PROFESSION"+"',passportNumber '"+"PASSPORT#"+"',passportExpiry'"+"P.EXPIRY"+"',visaExpiry'"+"ID.EXPIRY"+"',idNumber '"+"ID#"+"',todayDate'"+"DATE"+"',dob'"+"DOB"+"',firstParty '"+"FIRST PARTY"+"',employeeType '"+"EMPLOYEE TYPE"+"' from tbl_labourdetails,(SELECT @i := 0) temp where employeeType=? and Status=? order by empId desc";
            search(query,empType,status);
        }
        if(chkSponser.isSelected()==true && chkEmployeeType.isSelected()==false && chkStatus.isSelected()==false)
        {
            query="select @i := @i + 1 '"+"SL.NO"+"',empId '"+"ID"+"',empName'"+"NAME"+"',nationality '"+"NATIONALITY"+"',profession '"+"PROFESSION"+"',passportNumber '"+"PASSPORT#"+"',passportExpiry'"+"P.EXPIRY"+"',visaExpiry'"+"ID.EXPIRY"+"',idNumber '"+"ID#"+"',todayDate'"+"DATE"+"',dob'"+"DOB"+"',firstParty '"+"FIRST PARTY"+"',employeeType '"+"EMPLOYEE TYPE"+"' from tbl_labourdetails,(SELECT @i := 0) temp where firstParty=? order by empId desc";
            search(query,sponser);
        }
         if(chkSponser.isSelected()==true && chkEmployeeType.isSelected()==false && chkStatus.isSelected()==true)
        {
            query="select @i := @i + 1 '"+"SL.NO"+"',empId '"+"ID"+"',empName'"+"NAME"+"',nationality '"+"NATIONALITY"+"',profession '"+"PROFESSION"+"',passportNumber '"+"PASSPORT#"+"',passportExpiry'"+"P.EXPIRY"+"',visaExpiry'"+"ID.EXPIRY"+"',idNumber '"+"ID#"+"',todayDate'"+"DATE"+"',dob'"+"DOB"+"',firstParty '"+"FIRST PARTY"+"',employeeType '"+"EMPLOYEE TYPE"+"' from tbl_labourdetails,(SELECT @i := 0) temp where firstParty=? and Status=? order by empId desc";
            search(query,sponser,status);
        }
         if(chkSponser.isSelected()==true && chkEmployeeType.isSelected()==true && chkStatus.isSelected()==false)
        {
            query="select @i := @i + 1 '"+"SL.NO"+"',empId '"+"ID"+"',empName'"+"NAME"+"',nationality '"+"NATIONALITY"+"',profession '"+"PROFESSION"+"',passportNumber '"+"PASSPORT#"+"',passportExpiry'"+"P.EXPIRY"+"',visaExpiry'"+"ID.EXPIRY"+"',idNumber '"+"ID#"+"',todayDate'"+"DATE"+"',dob'"+"DOB"+"',firstParty '"+"FIRST PARTY"+"',employeeType '"+"EMPLOYEE TYPE"+"' from tbl_labourdetails,(SELECT @i := 0) temp where firstParty=? and employeeType=? order by empId desc";
            search(query,sponser,empType);
        }
         if(chkSponser.isSelected()==true && chkEmployeeType.isSelected()==true && chkStatus.isSelected()==true)
        {
            query="select @i := @i + 1 '"+"SL.NO"+"',empId '"+"ID"+"',empName'"+"NAME"+"',nationality '"+"NATIONALITY"+"',profession '"+"PROFESSION"+"',passportNumber '"+"PASSPORT#"+"',passportExpiry'"+"P.EXPIRY"+"',visaExpiry'"+"ID.EXPIRY"+"',idNumber '"+"ID#"+"',todayDate'"+"DATE"+"',dob'"+"DOB"+"',firstParty '"+"FIRST PARTY"+"',employeeType '"+"EMPLOYEE TYPE"+"' from tbl_labourdetails,(SELECT @i := 0) temp where firstParty=? and employeeType=? and Status=? order by empId desc";
            search(query,sponser,empType,status);
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

            }
            catch(Exception e)
            {

            }      
    }
    public void search(String query,String field1,String field2,String field3,String field4)
    {
        connection c=new connection();
        Connection con=c.conn();
            try
            {
                PreparedStatement ps=con.prepareStatement(query);
                ps.setString(1, field1);
                ps.setString(2, field2);
                ps.setString(3, field3);
                ps.setString(4, field4);
                ResultSet rs=ps.executeQuery();
                jTable1.setModel(DbUtils.resultSetToTableModel(rs));
                con.close();

            }
            catch(Exception e)
            {

            }      
    }
     public void cmbSponserFill() {
        try {
            connection c = new connection();
            Connection con = c.conn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT firstParty FROM tbl_labourdetails order by firstParty asc");
            while (rs.next()) {
                cmbSponserName.addItem(rs.getString(1));
            }
            con.close();
        } catch (SQLException ex) {
            
        }
    }
     public void cmbEmployeeTypeFill() {
        try {
            connection c = new connection();
            Connection con = c.conn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT employeeType FROM tbl_labourdetails order by employeeType asc");
            while (rs.next()) 
            {
                cmbEmployeeType.addItem(rs.getString(1));
            }
            con.close();
        } catch (SQLException ex) {
            
        }
    }
     public void cmbEmployeeStatus() {
        try {
            connection c = new connection();
            Connection con = c.conn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT Status FROM tbl_labourdetails order by Status asc");
            while (rs.next()) 
            {
                cmbStatus.addItem(rs.getString(1));
            }
            con.close();
        } catch (SQLException ex) {
            
        }
    }
     public void updateJobStatus()
     {
         int response=JOptionPane.showConfirmDialog(null,"Is he Left the job?","Confirm",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
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
                    int i=stmt1.executeUpdate("UPDATE tbl_labourdetails SET Status='LEFT' where empId="+empId);
                    if(i!=0)
                    {
                        dispose();
                        ViewAdvancedEmployeeSearchForm();
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
     public void ViewAdvancedEmployeeSearchForm()
    {
        AdvancedEmployeeSearch  AES=new AdvancedEmployeeSearch();
        AnarTrading.desktopPane1.add(AES);
        AES.setVisible(true);
        AES.show();
    }
    public void viewDbLeftEmployeeDetails()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select @i := @i + 1 '"+"SL.NO"+"',empId '"+"ID"+"',empName'"+"NAME"+"',nationality '"+"NATIONALITY"+"',profession '"+"PROFESSION"+"',passportNumber '"+"PASSPORT#"+"',passportExpiry'"+"P.EXPIRY"+"',visaExpiry'"+"ID.EXPIRY"+"',idNumber '"+"ID#"+"',todayDate'"+"DATE"+"',dob'"+"DOB"+"',currentSite '"+"SITE"+"',firstParty '"+"FIRST PARTY"+"',secondParty '"+"SECOND PARTY"+"',contractingCompany '"+"CONT.COMAPNY"+"',basicSalary'"+"BASIC"+"' from tbl_labourdetails,(SELECT @i := 0) temp where status='LEFT' order by empName");
            jTable1.setModel(DbUtils.resultSetToTableModel(rs));
            jTable1.setAutoCreateRowSorter(true);
            con.close();
            jTable1.getColumnModel().getColumn(1).setMinWidth(0);
            jTable1.getColumnModel().getColumn(1).setMaxWidth(0);
            jTable1.getColumnModel().getColumn(1).setWidth(0);
            jTable1.getColumnModel().getColumn(6).setCellRenderer(NumberRenderer.getDateTimeRenderer());
            jTable1.getColumnModel().getColumn(7).setCellRenderer(NumberRenderer.getDateTimeRenderer());
            jTable1.getColumnModel().getColumn(9).setCellRenderer(NumberRenderer.getDateTimeRenderer());
            jTable1.getColumnModel().getColumn(10).setCellRenderer(NumberRenderer.getDateTimeRenderer());
            jTable1.setShowHorizontalLines( true );
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

        JtablePopUP = new javax.swing.JPopupMenu();
        menuItemViewPP = new javax.swing.JMenuItem();
        menuItemViewRP = new javax.swing.JMenuItem();
        menuItemViewID = new javax.swing.JMenuItem();
        menuItemPrint = new javax.swing.JMenuItem();
        menuItemDelete = new javax.swing.JMenuItem();
        menuItemJobStatus = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        chkSponser = new javax.swing.JCheckBox();
        cmbSponserName = new javax.swing.JComboBox();
        chkEmployeeType = new javax.swing.JCheckBox();
        cmbEmployeeType = new javax.swing.JComboBox();
        chkStatus = new javax.swing.JCheckBox();
        cmbStatus = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        menuItemViewPP.setText("View PP");
        menuItemViewPP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemViewPPActionPerformed(evt);
            }
        });
        JtablePopUP.add(menuItemViewPP);

        menuItemViewRP.setText("View RP");
        menuItemViewRP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemViewRPActionPerformed(evt);
            }
        });
        JtablePopUP.add(menuItemViewRP);

        menuItemViewID.setText("View ID");
        menuItemViewID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemViewIDActionPerformed(evt);
            }
        });
        JtablePopUP.add(menuItemViewID);

        menuItemPrint.setText("Print");
        menuItemPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemPrintActionPerformed(evt);
            }
        });
        JtablePopUP.add(menuItemPrint);

        menuItemDelete.setText("Delete");
        menuItemDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemDeleteActionPerformed(evt);
            }
        });
        JtablePopUP.add(menuItemDelete);

        menuItemJobStatus.setText("Left Job");
        menuItemJobStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemJobStatusActionPerformed(evt);
            }
        });
        JtablePopUP.add(menuItemJobStatus);

        setClosable(true);
        setIconifiable(true);
        setTitle("Employee Search");
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
        });

        chkSponser.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        chkSponser.setText("Sponser Name");
        chkSponser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkSponserActionPerformed(evt);
            }
        });

        cmbSponserName.setEnabled(false);
        cmbSponserName.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "--select name--" }));
        cmbSponserName.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbSponserNameItemStateChanged(evt);
            }
        });
        cmbSponserName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbSponserNameActionPerformed(evt);
            }
        });

        chkEmployeeType.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        chkEmployeeType.setText("Employee Type");
        chkEmployeeType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkEmployeeTypeActionPerformed(evt);
            }
        });

        cmbSponserName.setEnabled(false);
        cmbEmployeeType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "--select type--" }));
        cmbEmployeeType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbEmployeeTypeItemStateChanged(evt);
            }
        });
        cmbEmployeeType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbEmployeeTypeActionPerformed(evt);
            }
        });

        chkStatus.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        chkStatus.setText("Status");
        chkStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkStatusActionPerformed(evt);
            }
        });

        cmbSponserName.setEnabled(false);
        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "--select status--" }));
        cmbStatus.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbStatusItemStateChanged(evt);
            }
        });
        cmbStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbStatusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chkSponser)
                    .addComponent(chkStatus))
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cmbSponserName, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(chkEmployeeType)
                        .addGap(18, 18, 18)
                        .addComponent(cmbEmployeeType, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(294, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbSponserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkSponser)
                    .addComponent(chkEmployeeType)
                    .addComponent(cmbEmployeeType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkStatus)
                    .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jTable1.setFont(new java.awt.Font("Century Gothic", 0, 10)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
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
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 697, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(169, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void chkSponserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkSponserActionPerformed
        // TODO add your handling code here:
        if(chkSponser.isSelected()==false)
        {
            cmbSponserName.setSelectedItem("--select name--");
            cmbSponserName.setEnabled(false);
        }
        else
        {
            cmbSponserName.setEnabled(true);
            cmbSponserName.setEditable(true);
            AutoCompleteDecorator.decorate(cmbSponserName);
        }
    }//GEN-LAST:event_chkSponserActionPerformed

    private void cmbSponserNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSponserNameActionPerformed
        // TODO add your handling code here:
        queryGenerator();

    }//GEN-LAST:event_cmbSponserNameActionPerformed

    private void cmbSponserNameItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbSponserNameItemStateChanged
        // TODO add your handling code here:
        //queryGenerator();
    }//GEN-LAST:event_cmbSponserNameItemStateChanged

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
        // TODO add your handling code here:
        int keyCode = evt.getKeyCode();
        if(keyCode == KeyEvent.VK_ENTER)
        {
            LabourDetails LD = new LabourDetails(empId);
            AnarTrading.desktopPane1.add(LD);
            LD.setVisible(true);
        }
        if(keyCode == KeyEvent.VK_DELETE)
        {
            int response=JOptionPane.showConfirmDialog(null,"Do you want to Delete ?","Confirm",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
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
                    int i=stmt1.executeUpdate("delete from tbl_labourdetails  where empId="+empId);
                    if(i!=0)
                    {
                        JOptionPane.showMessageDialog(null, "Deleted", "SUCCESS!!", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                catch(Exception e)
                {
                    JOptionPane.showMessageDialog(rootPane,e, "ERROR!!", JOptionPane.ERROR_MESSAGE);
                }
            }
            else if(response==JOptionPane.CLOSED_OPTION)
            {

            }
        }
    }//GEN-LAST:event_jTable1KeyPressed

    private void menuItemViewIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemViewIDActionPerformed
        // TODO add your handling code here:
        ID id = new ID(empId);
        AnarTrading.desktopPane1.add(id);
        id.setVisible(true);
    }//GEN-LAST:event_menuItemViewIDActionPerformed

    private void menuItemPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemPrintActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuItemPrintActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        int rowNo=jTable1.getSelectedRow();
        if(rowNo==-1)
        {
            
        }
        else
        {
            if(evt.getButton()==MouseEvent.BUTTON3)
            {
                JtablePopUP.show(evt.getComponent(), evt.getX(),evt.getY());
            }
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void menuItemViewPPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemViewPPActionPerformed
        // TODO add your handling code here:
        PassportDocument PD = new PassportDocument(empId);
        AnarTrading.desktopPane1.add(PD);
        PD.setVisible(true);
    }//GEN-LAST:event_menuItemViewPPActionPerformed

    private void menuItemDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemDeleteActionPerformed
        // TODO add your handling code here:
        int response=JOptionPane.showConfirmDialog(null,"Do you want to Delete ?","Confirm",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
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
                    int i=stmt1.executeUpdate("delete from tbl_labourdetails  where empId="+empId);
                    dispose();
                }
                catch(Exception e)
                {
                    JOptionPane.showMessageDialog(rootPane,e, "ERROR!!", JOptionPane.ERROR_MESSAGE);
                }
            }
            else if(response==JOptionPane.CLOSED_OPTION)
            {

            }
    }//GEN-LAST:event_menuItemDeleteActionPerformed

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_formFocusGained

    private void jTable1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTable1FocusGained
        // TODO add your handling code here:
        queryGenerator();
    }//GEN-LAST:event_jTable1FocusGained

    private void menuItemViewRPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemViewRPActionPerformed
        // TODO add your handling code here:
        RPDocument RD = new RPDocument(empId);
        AnarTrading.desktopPane1.add(RD);
        RD.setVisible(true);
    }//GEN-LAST:event_menuItemViewRPActionPerformed

    private void menuItemJobStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemJobStatusActionPerformed
        // TODO add your handling code here:
        updateJobStatus();
    }//GEN-LAST:event_menuItemJobStatusActionPerformed

    private void chkEmployeeTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkEmployeeTypeActionPerformed
        // TODO add your handling code here:
        
        if(chkEmployeeType.isSelected()==false)
        {
            cmbEmployeeType.setSelectedItem("--select type--");
            cmbEmployeeType.setEnabled(false);
        }
        else
        {
            cmbEmployeeType.setEnabled(true);
            cmbEmployeeType.setEditable(true);
            AutoCompleteDecorator.decorate(cmbEmployeeType);
        }
    }//GEN-LAST:event_chkEmployeeTypeActionPerformed

    private void cmbEmployeeTypeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbEmployeeTypeItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbEmployeeTypeItemStateChanged

    private void cmbEmployeeTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEmployeeTypeActionPerformed
        // TODO add your handling code here:
        queryGenerator();
    }//GEN-LAST:event_cmbEmployeeTypeActionPerformed

    private void chkStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkStatusActionPerformed
        // TODO add your handling code here:
        if(chkStatus.isSelected()==false)
        {
            cmbStatus.setSelectedItem("--select status--");
            cmbStatus.setEnabled(false);
        }
        else
        {
            cmbStatus.setEnabled(true);
            cmbStatus.setEditable(true);
            AutoCompleteDecorator.decorate(cmbStatus);
        }
    }//GEN-LAST:event_chkStatusActionPerformed

    private void cmbStatusItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbStatusItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbStatusItemStateChanged

    private void cmbStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbStatusActionPerformed
        // TODO add your handling code here:
        queryGenerator();
    }//GEN-LAST:event_cmbStatusActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPopupMenu JtablePopUP;
    private javax.swing.JCheckBox chkEmployeeType;
    private javax.swing.JCheckBox chkSponser;
    private javax.swing.JCheckBox chkStatus;
    private javax.swing.JComboBox cmbEmployeeType;
    private javax.swing.JComboBox cmbSponserName;
    private javax.swing.JComboBox cmbStatus;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JMenuItem menuItemDelete;
    private javax.swing.JMenuItem menuItemJobStatus;
    private javax.swing.JMenuItem menuItemPrint;
    private javax.swing.JMenuItem menuItemViewID;
    private javax.swing.JMenuItem menuItemViewPP;
    private javax.swing.JMenuItem menuItemViewRP;
    // End of variables declaration//GEN-END:variables
    Point middle = new Point(0,0);
    String blank=" ";
    int empId;
}
