package org.vijay.invoice;


import org.vijay.invoice.InvoiceEntry;
import org.vijay.common.AnarTrading;
import org.vijay.common.connection;
import org.vijay.common.AutoCompleteDecorator;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.proteanit.sql.DbUtils;
import org.vijay.common.NumberRenderer;
import org.vijay.employee.LabourDetails;
import static org.vijay.invoice.InvoiceEntry.dateFormat;

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
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setLocation(middle);
        cmbFromFill();
        cmbToFill();
        viewDbEmployeeDetails();
        jDateChooserDate.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("date")) {
                    invoiceDate = new SimpleDateFormat("yyyy-MM-dd").format(jDateChooserDate.getDate());
                }
            }  
        });
        jtableSelection();
    }
    public InvoiceReceived(int invoiceId) {
        this.receivedId=invoiceId;
        initComponents();
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setLocation(middle);
        cmbFromFill();
        cmbToFill();
        viewDbEmployeeDetails();
        btnUpdate.setEnabled(true);
        btnSave.setEnabled(false);
        jDateChooserDate.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("date")) {
                    invoiceDate = new SimpleDateFormat("yyyy-MM-dd").format(jDateChooserDate.getDate());
                }
            }  
        });
        jtableSelection();
        getDetailsUsingInvoiceId();
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
            
            receivedId=Integer.parseInt(jTable1.getValueAt(rowNo, 1).toString());
            cmbFrom.setSelectedItem(jTable1.getValueAt(rowNo,2).toString());
            cmbTo.setSelectedItem(jTable1.getValueAt(rowNo,3).toString());
            txtInvoiceNumber.setText(jTable1.getValueAt(rowNo,4).toString());
            try 
            {
                    jDateChooserDate.setDate(defaultDate.parse(jTable1.getValueAt(rowNo,5).toString()));
            } 
            catch (ParseException ex) 
            {
                    Logger.getLogger(LabourDetails.class.getName()).log(Level.SEVERE, null, ex);
            } 
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
    public void getDetailsUsingInvoiceId()
     {
         try {
            connection c = new connection();
            Connection con = c.conn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT fromAdd, toAdd, invoiceNumber, invoiceDate, amount, invoiceMonth, invoiceYear, terms, paymentDate, remark, balance, deduction, STATUS FROM tbl_invoicereceived where receivedId="+receivedId);
            while (rs.next()) {
                cmbFrom.setSelectedItem(rs.getString("fromAdd"));
                cmbTo.setSelectedItem(rs.getString("toAdd"));
                txtInvoiceNumber.setText(rs.getString("invoiceNumber"));
                try 
                {
                    jDateChooserDate.setDate(dateFormat.parse(rs.getString("invoiceDate")));
                } 
                catch (ParseException ex) 
                {
                    Logger.getLogger(LabourDetails.class.getName()).log(Level.SEVERE, null, ex);
                }
                txtAmount.setText(rs.getString("amount"));
                cmbMonth.setSelectedItem(rs.getString("InvoiceMonth"));
                cmbYear.setSelectedItem(rs.getString("invoiceYear"));
                txtTerms.setText(rs.getString("terms"));
                txtPaymentDate.setText(rs.getString("paymentDate"));
                txtRemarks.setText(rs.getString("remark"));
                txtBalance.setText(rs.getString("balance"));
                txtDeduction.setText(rs.getString("deduction"));
                status=rs.getInt("status");
                if(status==0)
                radioNotPaid.setSelected(true);
                else
                radioPaid.setSelected(true);
                
            }
            con.close();
        } catch (SQLException ex) {
            
        }
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
        AnarTrading.desktopPane1.add(IR);
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
            ps.setString(4, invoiceDate);
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
        query="select @i := @i + 1 '"+"SL.NO"+"',receivedId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',InvoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',balance'"+"Balance"+"',deduction'"+"DEDUCTION"+"',remark '"+"REMARK"+"',status'"+"STATUS"+"' from tbl_invoicereceived,(SELECT @i := 0) temp order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(InvoiceMonth,'%M')Desc";
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
            jTable1.getColumnModel().getColumn(5).setCellRenderer(NumberRenderer.getDateTimeRenderer());
            jTable1.getColumnModel().getColumn(6).setCellRenderer(NumberRenderer.getIntegerRenderer());
            jTable1.getColumnModel().getColumn(10).setCellRenderer(NumberRenderer.getDateTimeRenderer());
            jTable1.getColumnModel().getColumn(11).setCellRenderer(NumberRenderer.getIntegerRenderer());
            jTable1.getColumnModel().getColumn(12).setCellRenderer(NumberRenderer.getIntegerRenderer());

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
            ps.setString(4,invoiceDate);
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
        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jToolBar1 = new javax.swing.JToolBar();
        btnSave = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        View = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        lblEmployeeName = new javax.swing.JLabel();
        cmbFrom = new javax.swing.JComboBox();
        lblEmployeeName6 = new javax.swing.JLabel();
        cmbTo = new javax.swing.JComboBox();
        lblEmployeeName2 = new javax.swing.JLabel();
        txtInvoiceNumber = new javax.swing.JTextField();
        lblEmployeeName5 = new javax.swing.JLabel();
        jDateChooserDate = new com.toedter.calendar.JDateChooser();
        lblEmployeeName3 = new javax.swing.JLabel();
        txtAmount = new javax.swing.JTextField();
        lblEmployeeName4 = new javax.swing.JLabel();
        cmbMonth = new javax.swing.JComboBox();
        cmbYear = new javax.swing.JComboBox();
        lblEmployeeName7 = new javax.swing.JLabel();
        txtTerms = new javax.swing.JTextField();
        lblEmployeeName8 = new javax.swing.JLabel();
        txtPaymentDate = new javax.swing.JTextField();
        lblEmployeeName10 = new javax.swing.JLabel();
        txtBalance = new javax.swing.JTextField();
        lblEmployeeName13 = new javax.swing.JLabel();
        txtDeduction = new javax.swing.JTextField();
        lblEmployeeName9 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtRemarks = new javax.swing.JTextArea();
        lblEmployeeName11 = new javax.swing.JLabel();
        radioPaid = new javax.swing.JRadioButton();
        radioNotPaid = new javax.swing.JRadioButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Invoice Received Details");

        jTable1.setFont(new java.awt.Font("Century Gothic", 0, 10)); // NOI18N
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
        View.setText("View");
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

        lblEmployeeName.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        lblEmployeeName.setText("From");

        cmbFrom.setEditable(true);
        AutoCompleteDecorator.decorate(cmbFrom);
        cmbFrom.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        cmbFrom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbFromActionPerformed(evt);
            }
        });

        lblEmployeeName6.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        lblEmployeeName6.setText("To");

        cmbTo.setEditable(true);
        AutoCompleteDecorator.decorate(cmbTo);
        cmbTo.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        cmbTo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbToActionPerformed(evt);
            }
        });

        lblEmployeeName2.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        lblEmployeeName2.setText("Invoice#");

        txtInvoiceNumber.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        lblEmployeeName5.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        lblEmployeeName5.setText("Date");

        jDateChooserDate.setDateFormatString("yyyy-MM-dd");
        jDateChooserDate.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        lblEmployeeName3.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        lblEmployeeName3.setText("Amount");

        txtAmount.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtAmount.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtAmountFocusLost(evt);
            }
        });

        lblEmployeeName4.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        lblEmployeeName4.setText("Mon.");

        cmbMonth.setEditable(true);
        AutoCompleteDecorator.decorate(cmbMonth);
        cmbMonth.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        cmbMonth.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));
        cmbMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbMonthActionPerformed(evt);
            }
        });

        cmbYear.setEditable(true);
        AutoCompleteDecorator.decorate(cmbYear);
        cmbYear.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        cmbYear.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023" }));
        cmbYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbYearActionPerformed(evt);
            }
        });

        lblEmployeeName7.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        lblEmployeeName7.setText("Terms");

        txtPaymentDate.setEnabled(false);
        txtTerms.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtTerms.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTermsFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTermsFocusLost(evt);
            }
        });

        lblEmployeeName8.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        lblEmployeeName8.setText("P.Date");

        txtPaymentDate.setEditable(false);
        txtPaymentDate.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        lblEmployeeName10.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        lblEmployeeName10.setText("Balance");

        txtBalance.setText("0.0");
        txtBalance.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtBalance.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtBalanceFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtBalanceFocusLost(evt);
            }
        });

        lblEmployeeName13.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        lblEmployeeName13.setText("Dedu.");

        txtDeduction.setText("0.0");
        txtDeduction.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        lblEmployeeName9.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        lblEmployeeName9.setText("Details");

        txtRemarks.setColumns(20);
        txtRemarks.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtRemarks.setRows(5);
        jScrollPane2.setViewportView(txtRemarks);

        lblEmployeeName11.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        lblEmployeeName11.setText("Status");

        buttonGroup1.add(radioPaid);
        radioPaid.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        radioPaid.setText("Paid");

        radioNotPaid.setSelected(true);
        buttonGroup1.add(radioNotPaid);
        radioNotPaid.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        radioNotPaid.setText("unPaid");
        radioNotPaid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioNotPaidActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 644, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(274, 274, 274))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblEmployeeName2)
                                    .addComponent(lblEmployeeName)
                                    .addComponent(lblEmployeeName10)
                                    .addComponent(lblEmployeeName11))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cmbFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtInvoiceNumber, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                                            .addComponent(txtBalance))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addComponent(lblEmployeeName13)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(txtDeduction, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblEmployeeName5)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jDateChooserDate, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblEmployeeName3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtAmount))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(radioPaid)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(radioNotPaid)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblEmployeeName9)
                                        .addGap(18, 18, 18)
                                        .addComponent(jScrollPane2))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblEmployeeName6)
                                            .addComponent(lblEmployeeName4))
                                        .addGap(28, 28, 28)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(cmbMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(cmbYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblEmployeeName7)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtTerms, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblEmployeeName8)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtPaymentDate))
                                            .addComponent(cmbTo, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addGap(349, 349, 349)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmployeeName, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEmployeeName6, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEmployeeName2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtInvoiceNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEmployeeName5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jDateChooserDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEmployeeName3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEmployeeName4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEmployeeName7, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTerms, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEmployeeName8, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPaymentDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblEmployeeName10, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtBalance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblEmployeeName9, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblEmployeeName13, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtDeduction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEmployeeName11, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(radioPaid)
                            .addComponent(radioNotPaid)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 387, Short.MAX_VALUE)
                .addGap(23, 23, 23))
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
            addDaysToDate(invoiceDate,Integer.parseInt(txtTerms.getText()));
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

    private void txtBalanceFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBalanceFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBalanceFocusGained

    private void txtBalanceFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBalanceFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBalanceFocusLost

    private void txtAmountFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAmountFocusLost
        // TODO add your handling code here:
        txtBalance.setText(txtAmount.getText());
    }//GEN-LAST:event_txtAmountFocusLost

    private void ViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ViewActionPerformed
        // TODO add your handling code here:
        AdvancedInvoiceReceivedSearch AIRS=new AdvancedInvoiceReceivedSearch();
        AnarTrading.desktopPane1.add(AIRS);
        AIRS.setVisible(true);
        AIRS.show();
    }//GEN-LAST:event_ViewActionPerformed

    private void radioNotPaidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioNotPaidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radioNotPaidActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton View;
    private javax.swing.JButton btnCancel;
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
    private com.toedter.calendar.JDateChooser jDateChooserDate;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JToolBar jToolBar1;
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
    private javax.swing.JRadioButton radioNotPaid;
    private javax.swing.JRadioButton radioPaid;
    private javax.swing.JTextField txtAmount;
    private javax.swing.JTextField txtBalance;
    private javax.swing.JTextField txtDeduction;
    private javax.swing.JTextField txtInvoiceNumber;
    private javax.swing.JTextField txtPaymentDate;
    private javax.swing.JTextArea txtRemarks;
    private javax.swing.JTextField txtTerms;
    // End of variables declaration//GEN-END:variables
   Point middle = new Point(0,0);
   public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
   DateFormat defaultDate = new SimpleDateFormat("yyyy-MM-dd");
   String dateString = "",query,invoiceDate;
   int receivedId,status;
}
