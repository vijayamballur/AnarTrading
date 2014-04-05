package org.vijay.invoice;


import org.vijay.invoice.InvoiceEntry;
import org.vijay.common.AnarTrading;
import org.vijay.common.connection;
import org.vijay.common.AutoCompleteDecorator;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class InvoiceReceived extends javax.swing.JInternalFrame {

    /**
     * Creates new form InvoiceEntry
     */
    public InvoiceReceived() {
        initComponents();
        setLocation(middle);
        cmbFromFill();
        cmbToFill();
        viewDbEmployeeDetails();
        btnDate.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

                @Override
                public void propertyChange(java.beans.PropertyChangeEvent evt) {
                    if (evt.getNewValue() instanceof Date) {
                        invoiceDate((Date) evt.getNewValue());
                        try 
                        {
                            addDaysToDate(txtDate.getText(),Integer.parseInt(txtTerms.getText()));
                        } 
                        catch (Exception ex) 
                        {
                            Logger.getLogger(InvoiceEntry.class.getName()).log(Level.SEVERE, null, ex);
                        }
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
            
            receivedId=Integer.parseInt(jTable1.getValueAt(rowNo, 1).toString());
            cmbFrom.setSelectedItem(jTable1.getValueAt(rowNo,2).toString());
            cmbTo.setSelectedItem(jTable1.getValueAt(rowNo,3).toString());
            txtInvoiceNumber.setText(jTable1.getValueAt(rowNo,4).toString());
            txtDate.setText(jTable1.getValueAt(rowNo,5).toString());
            txtAmount.setText(jTable1.getValueAt(rowNo,6).toString());
            cmbMonth.setSelectedItem(jTable1.getValueAt(rowNo,7).toString());
            cmbYear.setSelectedItem(jTable1.getValueAt(rowNo,8).toString());
            txtTerms.setText(jTable1.getValueAt(rowNo,9).toString());
            txtPaymentDate.setText(jTable1.getValueAt(rowNo,10).toString());
            txtBalance.setText(jTable1.getValueAt(rowNo,11).toString());
            txtDeduction.setText(jTable1.getValueAt(rowNo,12).toString());
            txtRemarks.setText(jTable1.getValueAt(rowNo,13).toString());
            status=Integer.parseInt(jTable1.getValueAt(rowNo,14).toString());
            if(status==0)
                radioNotPaid.setSelected(true);
            else
                radioPaid.setSelected(true);
            
            }
        });
    }
    public void invoiceDate(String dateString)
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
                this.invoiceDate(date);
    }
     public void invoiceDate(Date date)
     {
        if (date != null)
        dateString = dateFormat.format(date);
        txtDate.setText(dateString);
        btnDate.setTargetDate(date);
     }
     private void addDaysToDate(String date, int daysToAdd) throws Exception 
     {
         Date parsedDate = dateFormat.parse(date);
         Calendar now = Calendar.getInstance();
         now.setTime(parsedDate); 
         now.add(Calendar.DAY_OF_MONTH, daysToAdd);
         txtPaymentDate.setText(dateFormat.format(now.getTime()));
      }
     public void ViewInvoiceDetailsForm()
    {
        InvoiceReceived  IR=new InvoiceReceived();
        AnarTrading.desktopPane.add(IR);
        IR.setVisible(true);
        IR.show();
    }
     public void insertIntoDb()
    {
        if(radioPaid.isSelected())
        {
            status=1;
        }
        else
        {
            status=0;
        }
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            PreparedStatement ps=con.prepareStatement("INSERT INTO tbl_invoicereceived(fromAdd,toAdd,invoiceNumber,invoiceDate,amount,InvoiceMonth,invoiceYear,terms,paymentDate,remark,balance,deduction,status) VALUES(upper(?),upper(?),upper(?),?,?,?,?,?,?,upper(?),?,?,?)");           
            ps.setString(1, cmbFrom.getSelectedItem().toString());
            ps.setString(2, cmbTo.getSelectedItem().toString());
            ps.setString(3, txtInvoiceNumber.getText());
            ps.setString(4, txtDate.getText());
            ps.setString(5, txtAmount.getText());
            ps.setString(6, cmbMonth.getSelectedItem().toString());
            ps.setString(7, cmbYear.getSelectedItem().toString());
            ps.setString(8, txtTerms.getText());
            ps.setString(9, txtPaymentDate.getText());
            ps.setString(10, txtRemarks.getText());
            ps.setString(11, txtBalance.getText());
            ps.setString(12, txtDeduction.getText());
            ps.setInt(13, status);
            
            int i=ps.executeUpdate();
            if(i!=0)
            {
                    dispose();
                    ViewInvoiceDetailsForm();
            }
            con.close();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(rootPane,e+"Error SA001");
        }
    }
    public void viewDbEmployeeDetails()
    {
        if(radioAll.isSelected()==true)
        {
             query="select @i := @i + 1 '"+"SL.NO"+"',receivedId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',InvoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',balance'"+"Balance"+"',deduction'"+"DEDUCTION"+"',remark '"+"REMARK"+"',status'"+"STATUS"+"' from tbl_invoicereceived,(SELECT @i := 0) temp order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(InvoiceMonth,'%M')Desc";
        }
        if(radioNotPaidView.isSelected()==true)
        {
            query="select @i := @i + 1 '"+"SL.NO"+"',receivedId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',InvoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',balance'"+"Balance"+"',deduction'"+"DEDUCTION"+"',remark '"+"REMARK"+"',status'"+"STATUS"+"' from tbl_invoicereceived,(SELECT @i := 0) temp where status=0 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(InvoiceMonth,'%M')Desc";
        }
        if(radioPaidView.isSelected()==true)
        {
            query="select @i := @i + 1 '"+"SL.NO"+"',receivedId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',InvoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',balance'"+"Balance"+"',deduction'"+"DEDUCTION"+"',remark '"+"REMARK"+"',status'"+"STATUS"+"' from tbl_invoicereceived,(SELECT @i := 0) temp where status=1 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(InvoiceMonth,'%M')Desc";
        }
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery(query);
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
    public void updateDb()
    {
        if(radioPaid.isSelected())
        {
            status=1;
            txtBalance.setText("0.0");
        }
        else
            status=0;
        
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            PreparedStatement ps=con.prepareStatement("UPDATE tbl_invoicereceived SET fromAdd=upper(?),toAdd=upper(?),invoiceNumber=upper(?),invoiceDate=?,amount=?,InvoiceMonth=?,invoiceYear=?,terms=?,paymentDate=?,remark=upper(?),balance=?,deduction=?,status=? where receivedId=?");
            ps.setString(1,cmbFrom.getSelectedItem().toString());
            ps.setString(2,cmbTo.getSelectedItem().toString());
            ps.setString(3,txtInvoiceNumber.getText());
            ps.setString(4,txtDate.getText());
            ps.setString(5,txtAmount.getText());
            ps.setString(6,cmbMonth.getSelectedItem().toString());
            ps.setString(7,cmbYear.getSelectedItem().toString());
            ps.setString(8,txtTerms.getText());
            ps.setString(9,txtPaymentDate.getText());
            ps.setString(10,txtRemarks.getText());
            ps.setString(11,txtBalance.getText());
            ps.setString(12,txtDeduction.getText());
            ps.setInt(13,status);
            ps.setInt(14, receivedId);
            int i=ps.executeUpdate();
            if(i!=0)
            {       
                    dispose();
                    ViewInvoiceDetailsForm();
            }
            con.close();  
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(rootPane, e);
        }
    }
    public void cmbFromFill()
    {
        try {
            connection c = new connection();
            Connection con = c.conn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT fromAdd FROM tbl_invoicereceived order by fromAdd asc");
            while (rs.next()) {
                cmbFrom.addItem(rs.getString(1));
            }
            con.close();
        } catch (SQLException ex) {
            
        }
    }
    public void cmbToFill()
    {
        try {
            connection c = new connection();
            Connection con = c.conn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT toAdd FROM tbl_invoicereceived order by toAdd asc");
            while (rs.next()) {
                cmbTo.addItem(rs.getString(1));
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

        jtablePopUp = new javax.swing.JPopupMenu();
        menuItemDocument = new javax.swing.JMenuItem();
        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        lblEmployeeName = new javax.swing.JLabel();
        cmbFrom = new javax.swing.JComboBox();
        cmbTo = new javax.swing.JComboBox();
        lblEmployeeName2 = new javax.swing.JLabel();
        txtInvoiceNumber = new javax.swing.JTextField();
        txtDate = new javax.swing.JTextField();
        btnDate = new net.sourceforge.jcalendarbutton.JCalendarButton();
        lblEmployeeName3 = new javax.swing.JLabel();
        lblEmployeeName4 = new javax.swing.JLabel();
        lblEmployeeName5 = new javax.swing.JLabel();
        txtAmount = new javax.swing.JTextField();
        cmbMonth = new javax.swing.JComboBox();
        lblEmployeeName6 = new javax.swing.JLabel();
        cmbYear = new javax.swing.JComboBox();
        lblEmployeeName7 = new javax.swing.JLabel();
        lblEmployeeName8 = new javax.swing.JLabel();
        txtPaymentDate = new javax.swing.JTextField();
        lblEmployeeName9 = new javax.swing.JLabel();
        txtTerms = new javax.swing.JTextField();
        lblSearch = new javax.swing.JLabel();
        lblEmployeeName11 = new javax.swing.JLabel();
        txtDeduction = new javax.swing.JTextField();
        radioNotPaid = new javax.swing.JRadioButton();
        radioPaid = new javax.swing.JRadioButton();
        lblEmployeeName10 = new javax.swing.JLabel();
        txtBalance = new javax.swing.JTextField();
        lblEmployeeName13 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtRemarks = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        btnSave = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        radioPaidView = new javax.swing.JRadioButton();
        radioNotPaidView = new javax.swing.JRadioButton();
        radioAll = new javax.swing.JRadioButton();

        menuItemDocument.setText("Add Document");
        menuItemDocument.setToolTipText("");
        menuItemDocument.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemDocumentActionPerformed(evt);
            }
        });
        jtablePopUp.add(menuItemDocument);

        setTitle("Invoice Received Details");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        lblEmployeeName.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        lblEmployeeName.setText("From");

        cmbFrom.setEditable(true);
        AutoCompleteDecorator.decorate(cmbFrom);
        cmbFrom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbFromActionPerformed(evt);
            }
        });

        cmbTo.setEditable(true);
        AutoCompleteDecorator.decorate(cmbTo);
        cmbTo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbToActionPerformed(evt);
            }
        });

        lblEmployeeName2.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        lblEmployeeName2.setText("Invoice#");

        txtInvoiceNumber.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        txtDate.setEditable(false);
        txtDate.setText("1111-11-11");
        txtDate.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        btnDate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/calendar-icon.png"))); // NOI18N

        lblEmployeeName3.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        lblEmployeeName3.setText("Amount");

        lblEmployeeName4.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        lblEmployeeName4.setText("Month of");

        lblEmployeeName5.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        lblEmployeeName5.setText("Date");

        txtAmount.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtAmount.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtAmountFocusLost(evt);
            }
        });

        cmbMonth.setEditable(true);
        AutoCompleteDecorator.decorate(cmbMonth);
        cmbMonth.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        cmbMonth.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));
        cmbMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbMonthActionPerformed(evt);
            }
        });

        lblEmployeeName6.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        lblEmployeeName6.setText("To");

        cmbYear.setEditable(true);
        AutoCompleteDecorator.decorate(cmbYear);
        cmbYear.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023" }));
        cmbYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbYearActionPerformed(evt);
            }
        });

        lblEmployeeName7.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        lblEmployeeName7.setText("Terms");

        lblEmployeeName8.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        lblEmployeeName8.setText("Payment Date");

        txtPaymentDate.setEditable(false);
        txtPaymentDate.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        lblEmployeeName9.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        lblEmployeeName9.setText("Details");

        txtPaymentDate.setEnabled(false);
        txtTerms.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtTerms.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTermsFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTermsFocusLost(evt);
            }
        });

        lblSearch.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        lblSearch.setForeground(new java.awt.Color(255, 51, 255));
        lblSearch.setText("Search>>");
        lblSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSearchMouseClicked(evt);
            }
        });

        lblEmployeeName11.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        lblEmployeeName11.setText("Status");

        txtDeduction.setText("0.0");
        txtDeduction.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        radioNotPaid.setSelected(true);
        buttonGroup1.add(radioNotPaid);
        radioNotPaid.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        radioNotPaid.setText("Not Paid");

        buttonGroup1.add(radioPaid);
        radioPaid.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        radioPaid.setText("Paid");

        lblEmployeeName10.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        lblEmployeeName10.setText("Balance");

        txtBalance.setText("0.0");
        txtBalance.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtBalance.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtBalanceFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtBalanceFocusLost(evt);
            }
        });

        lblEmployeeName13.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        lblEmployeeName13.setText("Dedu.");

        txtRemarks.setColumns(20);
        txtRemarks.setRows(5);
        jScrollPane2.setViewportView(txtRemarks);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblEmployeeName11)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblEmployeeName)
                                        .addComponent(lblEmployeeName2))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(lblEmployeeName3)
                                    .addGap(6, 6, 6)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblEmployeeName7)
                                    .addComponent(lblEmployeeName13))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(radioPaid)
                                .addGap(18, 18, 18)
                                .addComponent(radioNotPaid))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtDeduction)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(txtTerms)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblEmployeeName8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtPaymentDate))
                                    .addComponent(txtAmount, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(cmbFrom, 0, 300, Short.MAX_VALUE)
                                    .addComponent(txtInvoiceNumber))
                                .addGap(57, 57, 57)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblEmployeeName4)
                                        .addComponent(lblEmployeeName5)
                                        .addComponent(lblEmployeeName6)
                                        .addComponent(lblEmployeeName10, javax.swing.GroupLayout.Alignment.TRAILING))
                                    .addComponent(lblEmployeeName9)))))
                    .addComponent(lblSearch))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtBalance)
                    .addComponent(cmbTo, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(txtDate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cmbMonth, 0, 256, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbYear, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmployeeName, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbTo, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEmployeeName6, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtInvoiceNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEmployeeName2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEmployeeName5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2))
                    .addComponent(btnDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(lblEmployeeName4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblEmployeeName3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cmbMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cmbYear, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblEmployeeName7, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblEmployeeName8, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtPaymentDate, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtTerms, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtBalance, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblEmployeeName10, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(lblEmployeeName9, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtDeduction, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblEmployeeName13, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(radioPaid)
                                    .addComponent(radioNotPaid)
                                    .addComponent(lblEmployeeName11, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblSearch)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addGap(51, 51, 51))))))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Controls", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Gabriola", 0, 18))); // NOI18N

        btnSave.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/SAVE.PNG"))); // NOI18N
        btnSave.setText("Save");
        btnSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnUpdate.setEnabled(false);
        btnUpdate.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/MODIFY.PNG"))); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setEnabled(false);
        btnDelete.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/DELETE.PNG"))); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
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

        btnRefresh.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
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
                .addComponent(btnSave)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDelete)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRefresh)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancel)
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
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        buttonGroup2.add(radioPaidView);
        radioPaidView.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        radioPaidView.setText("Paid View");
        radioPaidView.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                radioPaidViewItemStateChanged(evt);
            }
        });
        radioPaidView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioPaidViewActionPerformed(evt);
            }
        });

        buttonGroup2.add(radioNotPaidView);
        radioNotPaidView.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        radioNotPaidView.setText("Not Paid View");
        radioNotPaidView.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                radioNotPaidViewItemStateChanged(evt);
            }
        });
        radioNotPaidView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioNotPaidViewActionPerformed(evt);
            }
        });

        radioAll.setSelected(true);
        buttonGroup2.add(radioAll);
        radioAll.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        radioAll.setText("All");
        radioAll.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                radioAllItemStateChanged(evt);
            }
        });
        radioAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioAllActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(radioPaidView)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(radioNotPaidView)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(radioAll)))
                .addContainerGap(42, Short.MAX_VALUE))
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radioPaidView)
                    .addComponent(radioNotPaidView)
                    .addComponent(radioAll))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbFromActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbFromActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_cmbFromActionPerformed

    private void cmbToActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbToActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbToActionPerformed

    private void cmbMonthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbMonthActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbMonthActionPerformed

    private void cmbYearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbYearActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbYearActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        insertIntoDb();
       
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:

        updateDb();

    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
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
                    int i=stmt1.executeUpdate("delete from tbl_invoicereceived where receivedId="+receivedId);
                    dispose();
                    ViewInvoiceDetailsForm();
                }
                catch(Exception e)
                {
                    JOptionPane.showMessageDialog(null,"Deletion"+e);
                }
            }
            else if(response==JOptionPane.CLOSED_OPTION)
            {

            }   
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        dispose();
        AnarTrading.menuItemInvoice.setEnabled(true);
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // TODO add your handling code here:
        dispose();
        ViewInvoiceDetailsForm();

    }//GEN-LAST:event_btnRefreshActionPerformed

    private void txtTermsFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTermsFocusLost
        try {
            if(txtDate.getText().equals("1111-11-11"))
            {
                
            }
            else
            {
                addDaysToDate(txtDate.getText(),Integer.parseInt(txtTerms.getText()));
            }
        } catch (Exception ex) {
            Logger.getLogger(InvoiceEntry.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_txtTermsFocusLost

    private void txtTermsFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTermsFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTermsFocusGained

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
                jtablePopUp.show(evt.getComponent(), evt.getX(),evt.getY());
            }
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void menuItemDocumentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemDocumentActionPerformed
        // TODO add your handling code here:
        InvoiceReceivedDocument IRD = new InvoiceReceivedDocument(receivedId);
        AnarTrading.desktopPane.add(IRD);
        IRD.setVisible(true);
        IRD.show();
    }//GEN-LAST:event_menuItemDocumentActionPerformed

    private void lblSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSearchMouseClicked
        // TODO add your handling code here:
        AdvancedInvoiceReceivedSearch AIRS = new AdvancedInvoiceReceivedSearch();
        AnarTrading.desktopPane.add(AIRS);
        AIRS.setVisible(true);
        AIRS.show();
    }//GEN-LAST:event_lblSearchMouseClicked

    private void txtBalanceFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBalanceFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBalanceFocusGained

    private void txtBalanceFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBalanceFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBalanceFocusLost

    private void radioPaidViewItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_radioPaidViewItemStateChanged
        // TODO add your handling code here:
        viewDbEmployeeDetails();
    }//GEN-LAST:event_radioPaidViewItemStateChanged

    private void radioPaidViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioPaidViewActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radioPaidViewActionPerformed

    private void radioNotPaidViewItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_radioNotPaidViewItemStateChanged
        // TODO add your handling code here:
        viewDbEmployeeDetails();
    }//GEN-LAST:event_radioNotPaidViewItemStateChanged

    private void radioNotPaidViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioNotPaidViewActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radioNotPaidViewActionPerformed

    private void radioAllItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_radioAllItemStateChanged
        // TODO add your handling code here:
        viewDbEmployeeDetails();
    }//GEN-LAST:event_radioAllItemStateChanged

    private void radioAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioAllActionPerformed

    }//GEN-LAST:event_radioAllActionPerformed

    private void txtAmountFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAmountFocusLost
        // TODO add your handling code here:
        txtBalance.setText(txtAmount.getText());
    }//GEN-LAST:event_txtAmountFocusLost


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private net.sourceforge.jcalendarbutton.JCalendarButton btnDate;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUpdate;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox cmbFrom;
    private javax.swing.JComboBox cmbMonth;
    private javax.swing.JComboBox cmbTo;
    private javax.swing.JComboBox cmbYear;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JPopupMenu jtablePopUp;
    private javax.swing.JLabel lblEmployeeName;
    private javax.swing.JLabel lblEmployeeName10;
    private javax.swing.JLabel lblEmployeeName11;
    private javax.swing.JLabel lblEmployeeName13;
    private javax.swing.JLabel lblEmployeeName2;
    private javax.swing.JLabel lblEmployeeName3;
    private javax.swing.JLabel lblEmployeeName4;
    private javax.swing.JLabel lblEmployeeName5;
    private javax.swing.JLabel lblEmployeeName6;
    private javax.swing.JLabel lblEmployeeName7;
    private javax.swing.JLabel lblEmployeeName8;
    private javax.swing.JLabel lblEmployeeName9;
    private javax.swing.JLabel lblSearch;
    private javax.swing.JMenuItem menuItemDocument;
    private javax.swing.JRadioButton radioAll;
    private javax.swing.JRadioButton radioNotPaid;
    private javax.swing.JRadioButton radioNotPaidView;
    private javax.swing.JRadioButton radioPaid;
    private javax.swing.JRadioButton radioPaidView;
    private javax.swing.JTextField txtAmount;
    private javax.swing.JTextField txtBalance;
    private javax.swing.JTextField txtDate;
    private javax.swing.JTextField txtDeduction;
    private javax.swing.JTextField txtInvoiceNumber;
    private javax.swing.JTextField txtPaymentDate;
    private javax.swing.JTextArea txtRemarks;
    private javax.swing.JTextField txtTerms;
    // End of variables declaration//GEN-END:variables
   Point middle = new Point(100,0);
   public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
   DateFormat defaultDate = new SimpleDateFormat("yyyy-MM-dd");
   String dateString = "",query;
   int receivedId,status;
}
