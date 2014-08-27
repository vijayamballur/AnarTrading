package org.vijay.payment;


import org.vijay.payment.AdvancedSalaryDeductionSearch;
import org.vijay.common.AnarTrading;
import org.vijay.common.connection;
import org.vijay.common.AutoCompleteDecorator;
import java.awt.Point;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
public final class SalaryDeductions extends javax.swing.JInternalFrame {

    /**
     * Creates new form AdvancePayment
     */
    public SalaryDeductions() {
        initComponents();
        setLocation(middle);
        cmbEmployeeName.setEditable(true);
        cmbPaidBy.setEditable(true);
        txtDate.setEditable(false);
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
        txtDate.setText("1111-11-11");
        cmbPaidBy.addItem("--select name--");
        AutoCompleteDecorator.decorate(cmbEmployeeName);
        AutoCompleteDecorator.decorate(cmbPaidBy);
        
        cmbEmployeeNameFill();
        cmbPaidByFill();
        viewDbEmployeeDetails();
        btnDate.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

                @Override
                public void propertyChange(java.beans.PropertyChangeEvent evt) {
                    if (evt.getNewValue() instanceof Date) {
                        paymentDate((Date) evt.getNewValue());
                    }
                }
            });
        jTable1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
            int rowNo=jTable1.getSelectedRow();
            btnSave.setEnabled(false);
            btnUpdate.setEnabled(true);
            btnDelete.setEnabled(true);

            
            advancePaymentId=Integer.parseInt(jTable1.getValueAt(rowNo, 0).toString());
            cmbEmployeeName.setSelectedItem(jTable1.getValueAt(rowNo,1).toString());
            cmbMonth.setSelectedItem(jTable1.getValueAt(rowNo,2).toString());
            cmbYear.setSelectedItem(jTable1.getValueAt(rowNo,3).toString());
            txtAmount.setText(jTable1.getValueAt(rowNo,4).toString());
            txtComment.setText(jTable1.getValueAt(rowNo,5).toString());
            cmbPaidBy.setSelectedItem(jTable1.getValueAt(rowNo,6).toString());
            txtDate.setText(jTable1.getValueAt(rowNo,7).toString());
            }
        });
    }
    public void viewDbEmployeeDetails()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select advancePaymentId '"+"ID"+"',empName'"+"NAME"+"',pMonth '"+"MONTH"+"',pYear '"+"YEAR"+"',amount '"+"AMOUNT"+"',comments'"+"COMMENTS"+"',paidBy'"+"PAID BY"+"',paidDate '"+"DATE"+"' from tbl_advancepayment order by advancePaymentId desc");
            jTable1.setModel(DbUtils.resultSetToTableModel(rs));
            con.close();
            jTable1.getColumnModel().getColumn(0).setMinWidth(0);
            jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
            jTable1.getColumnModel().getColumn(0).setWidth(0);
            jTable1.setShowHorizontalLines( true );
            jTable1.setRowSelectionAllowed( true );

        }
        catch(Exception e)
        {

        }
    }
    public void paymentDate(String dateString)
    {
		Date date = null;
		try
                {
                    if ((dateString != null) && (dateString.length() > 0))
                        date = dateFormat.parse(dateString);
		}
                catch (Exception e)
                {
                    date = null;
		}
                this.paymentDate(date);
    }
     public void paymentDate(Date date)
     {
        if (date != null)
        dateString = dateFormat.format(date);
        txtDate.setText(dateString);
        btnDate.setTargetDate(date);
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
    public void cmbPaidByFill() {
        try {
            connection c = new connection();
            Connection con = c.conn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT paidBy FROM tbl_advancepayment ORDER BY paidBy asc");
            while (rs.next()) {
                cmbPaidBy.addItem(rs.getString(1));
            }
            con.close();
        } catch (SQLException ex) {
            
        }
    }
    public void ViewAdvancedPaymentForm()
    {
        SalaryDeductions  advance=new SalaryDeductions();
        AnarTrading.desktopPane1.add(advance);
        advance.setVisible(true);
        advance.show();
    }
    public void insertIntoDb()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            PreparedStatement ps=con.prepareStatement("INSERT INTO tbl_advancepayment(empId,empName,pMonth,pYear,amount,comments,paidBy,paidDate) VALUES(?,upper(?),?,?,?,upper(?),upper(?),?)");           
            ps.setInt(1, empId);
            ps.setString(2, cmbEmployeeName.getSelectedItem().toString());
            ps.setString(3, cmbMonth.getSelectedItem().toString());
            ps.setString(4, cmbYear.getSelectedItem().toString());
            ps.setDouble(5, Double.parseDouble(txtAmount.getText()));
            ps.setString(6, txtComment.getText());
            ps.setString(7, cmbPaidBy.getSelectedItem().toString());
            ps.setString(8, txtDate.getText());
            int i=ps.executeUpdate();
            if(i!=0)
            {
                    dispose();
                    ViewAdvancedPaymentForm();
            }
            con.close();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(rootPane,e+"Error SA001");
        }
    }
    public void updateDb()
    {
        
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            PreparedStatement ps=con.prepareStatement("UPDATE tbl_advancepayment SET empId=?,empName=upper(?),pMonth=?,pYear=?,amount=?,comments=upper(?),paidBy=upper(?),paidDate=? where advancePaymentId=?");
            ps.setInt(1,empId);
            ps.setString(2,cmbEmployeeName.getSelectedItem().toString());
            ps.setString(3,cmbMonth.getSelectedItem().toString());
            ps.setString(4,cmbYear.getSelectedItem().toString());
            ps.setString(5,txtAmount.getText());
            ps.setString(6,txtComment.getText());
            ps.setString(7,cmbPaidBy.getSelectedItem().toString());
            ps.setString(8,txtDate.getText());
            ps.setInt(9, advancePaymentId);
            int i=ps.executeUpdate();
            if(i!=0)
            {  
                    dispose();
                    ViewAdvancedPaymentForm();
            }
            con.close();  
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(rootPane, e);
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

        jPanel3 = new javax.swing.JPanel();
        btnSave = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        lblEmployeeName = new javax.swing.JLabel();
        cmbEmployeeName = new javax.swing.JComboBox();
        jLabel33 = new javax.swing.JLabel();
        cmbMonth = new javax.swing.JComboBox();
        jLabel34 = new javax.swing.JLabel();
        cmbYear = new javax.swing.JComboBox();
        jLabel35 = new javax.swing.JLabel();
        txtAmount = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        txtDate = new javax.swing.JTextField();
        btnDate = new net.sourceforge.jcalendarbutton.JCalendarButton();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        cmbPaidBy = new javax.swing.JComboBox();
        txtComment = new javax.swing.JTextField();
        lblSearch = new javax.swing.JLabel();

        setTitle("Salary Deductions");
        setPreferredSize(new java.awt.Dimension(947, 582));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Controls", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Verdana", 0, 10))); // NOI18N

        btnSave.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        btnSave.setText("Save");
        btnSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnUpdate.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnCancel.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnRefresh.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
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
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Controls", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Verdana", 0, 10))); // NOI18N

        jTable1.setFont(new java.awt.Font("Verdana", 0, 9)); // NOI18N
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

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        lblEmployeeName.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        lblEmployeeName.setText("Employee Name");

        cmbEmployeeName.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        cmbEmployeeName.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmbEmployeeName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbEmployeeNameActionPerformed(evt);
            }
        });

        jLabel33.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel33.setText("Month");

        cmbMonth.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        cmbMonth.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));
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

        jLabel34.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel34.setText("Year");

        cmbYear.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        cmbYear.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2014", "2015", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2021", "2022" }));
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

        jLabel35.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel35.setText("Amount");

        txtAmount.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        jLabel38.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel38.setText("Date");

        txtDate.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        btnDate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/calendar-icon.png"))); // NOI18N

        jLabel36.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel36.setText("Comments");

        jLabel37.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel37.setText("Paid By");

        cmbPaidBy.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        cmbPaidBy.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmbPaidBy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPaidByActionPerformed(evt);
            }
        });

        txtComment.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        lblSearch.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        lblSearch.setForeground(new java.awt.Color(255, 51, 255));
        lblSearch.setText("Search>>");
        lblSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSearchMouseClicked(evt);
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
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblEmployeeName)
                            .addComponent(jLabel33)
                            .addComponent(jLabel35)
                            .addComponent(jLabel36))
                        .addGap(50, 50, 50)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbEmployeeName, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtAmount)
                                    .addComponent(cmbMonth, 0, 364, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel34, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel38, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cmbYear, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnDate, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(txtComment)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel37)
                        .addGap(98, 98, 98)
                        .addComponent(cmbPaidBy, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblSearch)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbEmployeeName, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEmployeeName, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33)
                    .addComponent(jLabel34)
                    .addComponent(cmbYear, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35)
                    .addComponent(jLabel38)
                    .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDate, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtComment, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbPaidBy, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37))
                .addGap(18, 18, 18)
                .addComponent(lblSearch)
                .addGap(25, 25, 25))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbMonthItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbMonthItemStateChanged

    }//GEN-LAST:event_cmbMonthItemStateChanged

    private void cmbMonthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbMonthActionPerformed
        // TODO add your handling code here:
        //SetFriday();
        //fillEntireTextBox();
    }//GEN-LAST:event_cmbMonthActionPerformed

    private void cmbYearItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbYearItemStateChanged
        // TODO add your handling code here:

    }//GEN-LAST:event_cmbYearItemStateChanged

    private void cmbYearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbYearActionPerformed
        // TODO add your handling code here:
        //SetFriday();
        //fillEntireTextBox();
    }//GEN-LAST:event_cmbYearActionPerformed

    private void cmbEmployeeNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEmployeeNameActionPerformed
        // TODO add your handling code here:
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            PreparedStatement ps=con.prepareStatement("SELECT empId FROM tbl_labourdetails WHERE empName=?");
            ps.setString(1, cmbEmployeeName.getSelectedItem().toString());
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                 empId=rs.getInt(1);   
            }
        }
        catch(Exception e)
        {

        }

    }//GEN-LAST:event_cmbEmployeeNameActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
         
        insertIntoDb();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
    updateDb();

    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
 

    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        dispose();
        AnarTrading.menuItemAdvancePayment.setEnabled(true);

    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // TODO add your handling code here:
        dispose();
        ViewAdvancedPaymentForm();

    }//GEN-LAST:event_btnRefreshActionPerformed

    private void cmbPaidByActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPaidByActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbPaidByActionPerformed

    private void lblSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSearchMouseClicked
        // TODO add your handling code here:
        AdvancedSalaryDeductionSearch advPaySearch = new AdvancedSalaryDeductionSearch();
        AnarTrading.desktopPane1.add(advPaySearch);
        advPaySearch.setVisible(true);
        advPaySearch.show();  
    }//GEN-LAST:event_lblSearchMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private net.sourceforge.jcalendarbutton.JCalendarButton btnDate;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox cmbEmployeeName;
    private javax.swing.JComboBox cmbMonth;
    private javax.swing.JComboBox cmbPaidBy;
    private javax.swing.JComboBox cmbYear;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblEmployeeName;
    private javax.swing.JLabel lblSearch;
    private javax.swing.JTextField txtAmount;
    private javax.swing.JTextField txtComment;
    private javax.swing.JTextField txtDate;
    // End of variables declaration//GEN-END:variables
    Point middle = new Point(100,0);
    public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat defaultDate = new SimpleDateFormat("yyyy-MM-dd");
    String dateString = "";
    int empId,advancePaymentId;
}
