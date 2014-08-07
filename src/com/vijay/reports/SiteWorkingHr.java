/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vijay.reports;

import java.awt.Point;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import org.vijay.common.AnarTrading;
import org.vijay.common.AutoCompleteDecorator;
import org.vijay.common.connection;

/**
 *
 * @author MAC
 */
public class SiteWorkingHr extends javax.swing.JInternalFrame {

    /**
     * Creates new form employeeWiseGained
     */
    public SiteWorkingHr() {
        initComponents();
        cmbEmployeeNameFill();
        fieldFill();
        HrsFill();
        viewDbDetails();
        setLocation(middle);
    }
    public void ViewForm()
    {
        SiteWorkingHr SWH=new SiteWorkingHr();
        AnarTrading.desktopPane1.add(SWH);
        SWH.setVisible(true); 
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
    
    public void fieldFill()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            PreparedStatement ps=con.prepareStatement("SELECT * FROM tbl_labourdetails WHERE empName=?");
            ps.setString(1, cmbEmployeeName.getSelectedItem().toString());
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                 empId=rs.getInt(1);
                 txtSiteName.setText(rs.getString(11));
                 txtConCompany.setText(rs.getString(14));
                 txtBasic.setText(rs.getString(15));
                 
                 
            }
        }
        catch(Exception e)
        {

        }
    }
    public void clearFileds()
    {
        txtNhr.setText("");
        txtOThr.setText("");
        txtFOThr.setText("");
        txtTotalHr.setText("");
        txtWorkingHr.setText("");
        txtRate.setText("");
        txtInvoiceAmtPerPerson.setText("");
        txtGrossNormal.setText(""); 
        txtGrossOT.setText(""); 
        txtGrossFOT.setText(""); 
        txtGrossAmount.setText(""); 
    }
    public void HrsFill()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            PreparedStatement ps=con.prepareStatement("SELECT tbl_timesheet.empId,tbl_timesheet.totalNormal, tbl_timesheet.toalOT, tbl_timesheet.totalHOT,grossAmountNormal,grossAmountOT,grossAmountHOT,grossAmount FROM anar.tbl_timesheet INNER JOIN anar.tbl_labourdetails  ON (tbl_timesheet.empId = tbl_labourdetails.empId) WHERE tbl_timesheet.timeSheetMonth=? AND tbl_timesheet.timeSheetYear=? AND tbl_labourdetails.empName=?");
            ps.setString(1, cmbMonth.getSelectedItem().toString());
            ps.setString(2, cmbYear.getSelectedItem().toString());
            ps.setString(3, cmbEmployeeName.getSelectedItem().toString());
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                 empId=rs.getInt(1);
                 txtNhr.setText(rs.getString(2));
                 txtOThr.setText(rs.getString(3));
                 txtFOThr.setText(rs.getString(4));
                 txtGrossNormal.setText(rs.getString(5));
                 txtGrossOT.setText(rs.getString(6));
                 txtGrossFOT.setText(rs.getString(7));
                 txtGrossAmount.setText(rs.getString(8));
                 
                 
            }
            double normalHr=Double.parseDouble(txtNhr.getText().toString());
            double OThr=Double.parseDouble(txtOThr.getText().toString());
            double fOThr=Double.parseDouble(txtFOThr.getText().toString());
            txtTotalHr.setText(Double.toString(normalHr+OThr+fOThr));
            
        }
        catch(Exception e)
        {

        }
    }
    public void insertIntoDb()
    {
         connection c=new connection();
        Connection con=c.conn();
        try
        {
            PreparedStatement ps=con.prepareStatement("INSERT INTO tbl_siteworkinghr(empId,workingHr,ratePerHr,invoiceAmtPerPerson,month,year,actualAmt) VALUES(?,?,?,?,?,?,?)");           
            ps.setInt(1, empId);
            ps.setString(2, txtWorkingHr.getText());
            ps.setString(3, txtRate.getText());
            ps.setString(4, txtInvoiceAmtPerPerson.getText());
            ps.setString(5, cmbMonth.getSelectedItem().toString());
            ps.setString(6, cmbYear.getSelectedItem().toString());
            ps.setString(7, txtGrossAmount.getText());
            int i=ps.executeUpdate();
            if(i!=0)
            {
                    dispose();
                    ViewForm();
            }
            con.close();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(rootPane,e+"Error SA001");
        }
    }
    public void viewDbDetails()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT @i := @i + 1 '"+"SL.NO"+"',\n" +
                                            "	tbl_siteworkinghr.id as ID\n" +
                                            "    ,tbl_labourdetails.empName as Name\n" +
                                            "    , tbl_siteworkinghr.empId as Emp_Id\n" +
                                            "    , tbl_siteworkinghr.actualAmt as Amt\n" +
                                            "    , tbl_siteworkinghr.workingHr as Wk_Hr\n" +
                                            "    , tbl_siteworkinghr.ratePerHr as Rate_Hr\n" +
                                            "    , tbl_siteworkinghr.invoiceAmtPerPerson\n" +
                                            "    , tbl_siteworkinghr.month\n" +
                                            "    , tbl_siteworkinghr.year\n" +
                                            "FROM\n" +
                                            "    anar.tbl_siteworkinghr\n" +
                                            "    INNER JOIN anar.tbl_labourdetails \n" +
                                            "        ON (tbl_siteworkinghr.empId = tbl_labourdetails.empId),(SELECT @i := 0) temp ORDER BY STR_TO_DATE(tbl_siteworkinghr.year,'%Y')DESC,STR_TO_DATE(tbl_siteworkinghr.month,'%M')DESC,tbl_labourdetails.empName;");
            jTable1.setModel(DbUtils.resultSetToTableModel(rs));
            con.close();
            jTable1.getColumnModel().getColumn(1).setMinWidth(0);
            jTable1.getColumnModel().getColumn(1).setMaxWidth(0);
            jTable1.getColumnModel().getColumn(1).setWidth(0);
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtNhr = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtOThr = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtFOThr = new javax.swing.JTextField();
        txtRate = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtWorkingHr = new javax.swing.JTextField();
        txtTotalHr = new javax.swing.JTextField();
        txtInvoiceAmtPerPerson = new javax.swing.JTextField();
        cmbEmployeeName = new javax.swing.JComboBox();
        cmbMonth = new javax.swing.JComboBox();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtConCompany = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtSiteName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtBasic = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        cmbYear = new javax.swing.JComboBox();
        jLabel16 = new javax.swing.JLabel();
        txtGrossNormal = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtGrossOT = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtGrossFOT = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtGrossAmount = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        btnSave = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel1.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel1.setText("Employee Name");

        jLabel4.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel4.setText("Working Hr Based On Site:");

        jLabel5.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel5.setText("Working Hr Based On Sheet:");

        jLabel6.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel6.setText("Rate");

        jLabel8.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel8.setText("Normal Hr.");

        txtNhr.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel9.setText("OT Hr");

        txtOThr.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel10.setText("FOT Hr");

        txtFOThr.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        txtRate.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtRate.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtRateFocusLost(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel11.setText("Wrk Hr.");

        txtWorkingHr.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtWorkingHr.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtWorkingHrFocusLost(evt);
            }
        });

        txtTotalHr.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        txtInvoiceAmtPerPerson.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        cmbEmployeeName.setEditable(true);
        AutoCompleteDecorator.decorate(cmbEmployeeName);
        cmbEmployeeName.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        cmbEmployeeName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbEmployeeNameActionPerformed(evt);
            }
        });

        cmbMonth.setEditable(true);
        AutoCompleteDecorator.decorate(cmbMonth);
        cmbMonth.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        cmbMonth.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "January", "February", "March", "april", "May", "June", "July", "August", "September", "October", "November", "December" }));
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

        jLabel14.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel14.setText("Year");

        jLabel15.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel15.setText("Basic");

        jLabel12.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel12.setText("Total Hr.");

        txtConCompany.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel7.setText("Month");

        jLabel3.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel3.setText("Site Name");

        txtSiteName.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel2.setText("Con.Comapny");

        txtBasic.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel13.setText("In.Amt/Person");

        AutoCompleteDecorator.decorate(cmbYear);
        cmbYear.setEditable(true);
        cmbYear.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        cmbYear.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021" }));
        cmbYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbYearActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel16.setText("Rate Based On Sheet:");

        txtGrossNormal.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        jLabel17.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel17.setText("OT Rt.");

        txtGrossOT.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        jLabel18.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel18.setText("FOT Rt.");

        txtGrossFOT.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        jLabel19.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel19.setText("Total Rt.");

        txtGrossAmount.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        jLabel20.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel20.setText("Normal Rt.");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel2))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(cmbMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbYear, 0, 117, Short.MAX_VALUE))
                            .addComponent(txtConCompany)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel16))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel11))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(4, 4, 4)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtWorkingHr, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
                            .addComponent(txtNhr)
                            .addComponent(txtGrossNormal))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel6)
                            .addComponent(jLabel17))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtGrossOT, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                            .addComponent(txtOThr)
                            .addComponent(txtRate))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(jLabel10)
                    .addComponent(jLabel13)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtFOThr, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTotalHr))
                    .addComponent(txtInvoiceAmtPerPerson, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtGrossFOT, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtGrossAmount))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtSiteName, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBasic, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE))
                    .addComponent(cmbEmployeeName, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel1)
                    .addComponent(cmbEmployeeName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(cmbYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtConCompany, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(txtSiteName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(txtBasic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel8)
                    .addComponent(txtNhr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtOThr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txtFOThr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(txtTotalHr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtGrossNormal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(txtGrossOT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(txtGrossFOT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(txtGrossAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel11)
                    .addComponent(txtWorkingHr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(txtInvoiceAmtPerPerson, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTable1.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
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

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Controls", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Verdana", 0, 10))); // NOI18N

        btnSave.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/SAVE.PNG"))); // NOI18N
        btnSave.setText("Save");
        btnSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnUpdate.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/MODIFY.PNG"))); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/DELETE.PNG"))); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnCancel.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/CANCEL.PNG"))); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnRefresh.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        btnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/clear.png"))); // NOI18N
        btnRefresh.setText("Refresh");
        btnRefresh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRefresh, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE))
                .addGap(29, 29, 29))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed

        insertIntoDb();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        

    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        dispose();
        AnarTrading.menuItemSiteWorkingHr.setEnabled(true);
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // TODO add your handling code here:
        dispose();
        ViewForm();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void cmbEmployeeNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEmployeeNameActionPerformed
        // TODO add your handling code here:
        clearFileds();
        fieldFill();
        HrsFill();
    }//GEN-LAST:event_cmbEmployeeNameActionPerformed

    private void cmbMonthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbMonthActionPerformed
        // TODO add your handling code here:
        clearFileds();
        HrsFill();
    }//GEN-LAST:event_cmbMonthActionPerformed

    private void cmbMonthItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbMonthItemStateChanged
        // TODO add your handling code here:
        
    }//GEN-LAST:event_cmbMonthItemStateChanged

    private void cmbYearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbYearActionPerformed
        // TODO add your handling code here:
        clearFileds();
        HrsFill();
    }//GEN-LAST:event_cmbYearActionPerformed

    private void txtRateFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtRateFocusLost
        // TODO add your handling code here:
        double rate=Double.parseDouble(txtRate.getText());
        double workingHr=Double.parseDouble(txtWorkingHr.getText());
        txtInvoiceAmtPerPerson.setText(Double.toString(rate*workingHr));
    }//GEN-LAST:event_txtRateFocusLost

    private void txtWorkingHrFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtWorkingHrFocusLost
        // TODO add your handling code here:
        double rate=Double.parseDouble(txtRate.getText());
        double workingHr=Double.parseDouble(txtWorkingHr.getText());
        txtInvoiceAmtPerPerson.setText(Double.toString(rate*workingHr));
    }//GEN-LAST:event_txtWorkingHrFocusLost


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox cmbEmployeeName;
    private javax.swing.JComboBox cmbMonth;
    private javax.swing.JComboBox cmbYear;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtBasic;
    private javax.swing.JTextField txtConCompany;
    private javax.swing.JTextField txtFOThr;
    private javax.swing.JTextField txtGrossAmount;
    private javax.swing.JTextField txtGrossFOT;
    private javax.swing.JTextField txtGrossNormal;
    private javax.swing.JTextField txtGrossOT;
    private javax.swing.JTextField txtInvoiceAmtPerPerson;
    private javax.swing.JTextField txtNhr;
    private javax.swing.JTextField txtOThr;
    private javax.swing.JTextField txtRate;
    private javax.swing.JTextField txtSiteName;
    private javax.swing.JTextField txtTotalHr;
    private javax.swing.JTextField txtWorkingHr;
    // End of variables declaration//GEN-END:variables
    Point middle = new Point(100,0);
    int empId,NoDays,year,month;
}
