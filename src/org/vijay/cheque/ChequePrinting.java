/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.vijay.cheque;

import java.awt.Color;
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
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.proteanit.sql.DbUtils;
import org.vijay.common.AnarTrading;
import org.vijay.common.AutoCompleteDecorator;
import org.vijay.common.CurrentWorkingDirectory;
import org.vijay.common.EnglishNumberToWords;
import org.vijay.common.NumberRenderer;
import org.vijay.common.connection;
import org.vijay.employee.LabourDetails;
import static org.vijay.employee.LabourDetails.dateFormat;
import org.vijay.invoice.AdvancedInvoiceSearch;
import org.vijay.invoice.InvoiceEntry;
import org.vijay.report.ReportView;

/**
 *
 * @author MAC
 */
public class ChequePrinting extends javax.swing.JInternalFrame {

    /**
     * Creates new form ChequePrinting
     */
    public ChequePrinting() {
        initComponents();
        setSize(Toolkit.getDefaultToolkit().getScreenSize());   
        cmbFromFill();
        cmbBearerFill();
        jDateCheque.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("date")) {
                    chequeDate = new SimpleDateFormat("yyyy-MM-dd").format(jDateCheque.getDate());
                }
            }  
        });
        viewDbChequeDetails();
        jtableSelection();
        CurrentWorkingDirectory CWD=new CurrentWorkingDirectory();
        path=CWD.getpath();
    }
    public void viewDbChequeDetails()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select @i := @i + 1 '"+"SL.NO"+"',chequeId,bankName '"+"BANK NAME"+"',fromName'"+"FROM"+"',bearer'"+"BEARER"+"',chequeNo'"+"CHEQUE#"+"',chequeDate'"+"CHEQUE DATE"+"',amount'"+"AMOUNT"+"',amountInWords'"+"AMOUNT IN WORDS"+"',remarks'"+"REMARKS"+"' from tbl_cheque,(SELECT @i := 0) temp order by chequeId desc");
            jTable1.setModel(DbUtils.resultSetToTableModel(rs));
            jTable1.setAutoCreateRowSorter(true);
            con.close();
            jTable1.getColumnModel().getColumn(1).setMinWidth(0);
            jTable1.getColumnModel().getColumn(1).setMaxWidth(0);
            jTable1.setShowHorizontalLines( true );
            jTable1.setRowSelectionAllowed( true );

        }
        catch(Exception e)
        {

        }
    }
    public void viewDbChequeDetailsUsingChequeNumber()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select @i := @i + 1 '"+"SL.NO"+"',chequeId,bankName '"+"BANK NAME"+"',fromName'"+"FROM"+"',bearer'"+"BEARER"+"',chequeNo'"+"CHEQUE#"+"',chequeDate'"+"CHEQUE DATE"+"',amount'"+"AMOUNT"+"',amountInWords'"+"AMOUNT IN WORDS"+"',remarks'"+"REMARKS"+"' from tbl_cheque,(SELECT @i := 0) temp where chequeNo='"+txtChequeNumberSearch.getText()+"' order by chequeId desc");
            jTable1.setModel(DbUtils.resultSetToTableModel(rs));
            jTable1.setAutoCreateRowSorter(true);
            con.close();
            jTable1.getColumnModel().getColumn(1).setMinWidth(0);
            jTable1.getColumnModel().getColumn(1).setMaxWidth(0);
            jTable1.setShowHorizontalLines( true );
            jTable1.setRowSelectionAllowed( true );

        }
        catch(Exception e)
        {

        }
    }
    public void cmbFromFill()
    {
        try {
            connection c = new connection();
            Connection con = c.conn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT fromName FROM tbl_cheque order by fromName asc");
            while (rs.next()) {
                cmbFromName.addItem(rs.getString(1));
            }
            con.close();
        } catch (SQLException ex) {
            
        }
    }
    public void cmbBearerFill()
    {
        try {
            connection c = new connection();
            Connection con = c.conn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT bearer FROM tbl_cheque order by bearer asc");
            while (rs.next()) {
                cmbBearer.addItem(rs.getString(1));
            }
            con.close();
        } catch (SQLException ex) {
            
        }
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
            
            chequeId=Integer.parseInt(jTable1.getValueAt(rowNo, 1).toString());
            bankName=jTable1.getValueAt(rowNo,2).toString();
            if(bankName.equals("DOHA BANK"))
                radioDoha.setSelected(true);
            else
                radioCommercial.setSelected(true);
            cmbFromName.setSelectedItem(jTable1.getValueAt(rowNo,3).toString());
            cmbBearer.setSelectedItem(jTable1.getValueAt(rowNo,4).toString());
            txtChequeNo.setText(jTable1.getValueAt(rowNo,5).toString());
            try 
            {
                    jDateCheque.setDate(defaultDate.parse(jTable1.getValueAt(rowNo,6).toString()));
            } 
            catch (ParseException ex) 
            {
                    Logger.getLogger(LabourDetails.class.getName()).log(Level.SEVERE, null, ex);
            } 
            txtAmount.setText(jTable1.getValueAt(rowNo,7).toString());
            txtAmountInWords.setText(jTable1.getValueAt(rowNo,8).toString());
            txtRemarks.setText(jTable1.getValueAt(rowNo,9).toString());     
            }
        });
    }
    public void ViewChequePrintingForm()
    {
        ChequePrinting  CP=new ChequePrinting();
        AnarTrading.desktopPane1.add(CP);
        CP.setVisible(true);
        CP.show();
    }
    
    public void insertIntoDb()
    {   
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            PreparedStatement ps=con.prepareStatement("INSERT INTO tbl_cheque(bankName,fromName,bearer,chequeNo,chequeDate,amount,amountInWords,remarks) VALUES(upper(?),upper(?),upper(?),upper(?),?,?,upper(?),upper(?))");           
            if(radioCommercial.isSelected())
            {
                ps.setString(1,radioCommercial.getText().toString());
            }
            else if(radioDoha.isSelected())
            {
                ps.setString(1,radioDoha.getText().toString());
            }
            else
            {
                ps.setString(1,radioMashreq.getText().toString());
            }
            ps.setString(2, cmbFromName.getSelectedItem().toString());
            ps.setString(3, cmbBearer.getSelectedItem().toString());
            ps.setString(4, txtChequeNo.getText());
            ps.setString(5, chequeDate);
            ps.setString(6, txtAmount.getText());
            ps.setString(7, txtAmountInWords.getText());
            ps.setString(8, txtRemarks.getText());
            int i=ps.executeUpdate();
            if(i!=0)
            {
                    dispose();
                    ViewChequePrintingForm();
            }
            con.close();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(rootPane,e+"-Cheque Db Exception");
        }
    }
    public void updateDb()
    {   
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            PreparedStatement ps=con.prepareStatement("UPDATE tbl_cheque SET bankName=upper(?),fromName=upper(?),bearer=upper(?),chequeNo=?,chequeDate=?,amount=?,amountInWords=upper(?),remarks=upper(?) where chequeId=?");
            if(radioCommercial.isSelected())
            {
                ps.setString(1,radioCommercial.getText().toString());
            }
            else if(radioDoha.isSelected())
            {
                ps.setString(1,radioDoha.getText().toString());
            }
            else
            {
                ps.setString(1,radioMashreq.getText().toString());
            }
            ps.setString(2,cmbFromName.getSelectedItem().toString());
            ps.setString(3,cmbBearer.getSelectedItem().toString());
            ps.setString(4,txtChequeNo.getText());
            ps.setString(5,chequeDate);
            ps.setString(6,txtAmount.getText());
            ps.setString(7,txtAmountInWords.getText());
            ps.setString(8,txtRemarks.getText());
            ps.setInt(9, chequeId);
            int i=ps.executeUpdate();
            if(i!=0)
            {       
                    dispose();
                    ViewChequePrintingForm();
            }
            con.close();  
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(rootPane, e);
        }
    }
   public void insertIntoFixCbqDb()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            PreparedStatement ps=con.prepareStatement("INSERT INTO tbl_fixturecbq(transDate,debit,credit,description) VALUES(?,?,?,?)");           
            ps.setString(1,chequeDate);
            ps.setString(2, txtAmount.getText());
            ps.setString(3, "0.00");
            ps.setString(4, txtRemarks.getText());
            int i=ps.executeUpdate();
            if(i!=0)
            {
               insertIntoDb();
            }
            con.close();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(rootPane,e+"-Fix_Cbq_Db Exception");
            //JOptionPane.showMessageDialog(rootPane,e+"Error SA001");
        }
    }
   public void insertIntoAnarCbqDb()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            PreparedStatement ps=con.prepareStatement("INSERT INTO tbl_anarcbq(transDate,debit,credit,description) VALUES(?,?,?,?)");           
            ps.setString(1,chequeDate);
            ps.setString(2, txtAmount.getText());
            ps.setString(3,"0.00");
            ps.setString(4, txtRemarks.getText());
            int i=ps.executeUpdate();
            if(i!=0)
            {
                insertIntoDb();
            }
            con.close();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(rootPane,e+"-Anar_Cbq_Db Exception");
            //JOptionPane.showMessageDialog(rootPane,e+"Error SA001");
        }
    }
   public void insertIntoFixDohaDb()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            PreparedStatement ps=con.prepareStatement("INSERT INTO tbl_fixdoha(transDate,debit,credit,description) VALUES(?,?,?,?)");           
            ps.setString(1,chequeDate);
            ps.setString(2, txtAmount.getText());
            ps.setString(3,"0.00");
            ps.setString(4, txtRemarks.getText());
            int i=ps.executeUpdate();
            if(i!=0)
            {
                insertIntoDb();
            }
            con.close();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(rootPane, e, "ERROR!!", JOptionPane.ERROR_MESSAGE);
            //JOptionPane.showMessageDialog(rootPane,e+"Error SA001");
        }
    }
   public void insertIntoFixMashreqDb()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            PreparedStatement ps=con.prepareStatement("INSERT INTO tbl_fixturemashreq(transDate,debit,credit,description) VALUES(?,?,?,?)");           
            ps.setString(1,chequeDate);
            ps.setString(2, txtAmount.getText());
            ps.setString(3,"0.00");
            ps.setString(4, txtRemarks.getText());
            int i=ps.executeUpdate();
            if(i!=0)
            {
                insertIntoDb();
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jTablePopUp = new javax.swing.JPopupMenu();
        menuItemPrintCommercial = new javax.swing.JMenuItem();
        menuItemPrintDoha = new javax.swing.JMenuItem();
        menuItemPrintMashreq = new javax.swing.JMenuItem();
        radioCommercial = new javax.swing.JRadioButton();
        radioDoha = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        cmbFromName = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        cmbBearer = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        txtAmount = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtAmountInWords = new javax.swing.JTextField();
        jDateCheque = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        txtChequeNo = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtRemarks = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        btnSave = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        btnView = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtChequeNumberSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        radioMashreq = new javax.swing.JRadioButton();
        checkAccountPay = new javax.swing.JCheckBox();

        menuItemPrintCommercial.setText("Print Commercial");
        menuItemPrintCommercial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemPrintCommercialActionPerformed(evt);
            }
        });
        jTablePopUp.add(menuItemPrintCommercial);

        menuItemPrintDoha.setText("Print Doha");
        menuItemPrintDoha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemPrintDohaActionPerformed(evt);
            }
        });
        jTablePopUp.add(menuItemPrintDoha);

        menuItemPrintMashreq.setText("Print Mashreq");
        menuItemPrintMashreq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemPrintMashreqActionPerformed(evt);
            }
        });
        jTablePopUp.add(menuItemPrintMashreq);

        setClosable(true);
        setIconifiable(true);
        setTitle("Cheque Printing");
        setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        buttonGroup1.add(radioCommercial);
        radioCommercial.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        radioCommercial.setSelected(true);
        radioCommercial.setText("Commercial Bank");

        buttonGroup1.add(radioDoha);
        radioDoha.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        radioDoha.setText("Doha Bank");

        jLabel1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel1.setText("From");

        cmbFromName.setEditable(true);
        AutoCompleteDecorator.decorate(cmbFromName);
        cmbFromName.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel2.setText("Bearer");

        cmbBearer.setEditable(true);
        AutoCompleteDecorator.decorate(cmbBearer);
        cmbBearer.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel3.setText("Amount");

        txtAmount.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtAmount.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtAmountFocusLost(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel4.setText("Amount In Words");

        txtAmountInWords.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        jDateCheque.setDateFormatString("yyyy-MM-dd");
        jDateCheque.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel5.setText("Cheque #");

        txtChequeNo.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel6.setText("Cheque Date");

        jLabel7.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel7.setText("Remarks");

        txtRemarks.setColumns(20);
        txtRemarks.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtRemarks.setRows(5);
        jScrollPane1.setViewportView(txtRemarks);

        jLabel8.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel8.setText("Bank Name");

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnSave.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnSave.setText("Save");
        btnSave.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSaveMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSaveMouseExited(evt);
            }
        });
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

        btnView.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnView.setText("View");
        btnView.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnView.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewActionPerformed(evt);
            }
        });
        jToolBar1.add(btnView);

        btnDelete.setEnabled(false);
        btnDelete.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnDelete.setForeground(new java.awt.Color(204, 0, 0));
        btnDelete.setText("Delete");
        btnDelete.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        jToolBar1.add(btnDelete);

        btnCancel.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnCancel.setForeground(new java.awt.Color(204, 0, 0));
        btnCancel.setText("Cancel");
        btnCancel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnCancel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        jToolBar1.add(btnCancel);

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
        jScrollPane2.setViewportView(jTable1);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 0, 12))); // NOI18N

        jLabel9.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel9.setText("Cheque #");

        txtChequeNumberSearch.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtChequeNumberSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtChequeNumberSearchFocusLost(evt);
            }
        });

        btnSearch.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnSearch.setText("Search");
        btnSearch.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Century Gothic", 0, 10)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 153, 51));
        jLabel10.setText("For accurate results please refresh the form before search");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtChequeNumberSearch)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtChequeNumberSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSearch)
                .addContainerGap(53, Short.MAX_VALUE))
        );

        buttonGroup1.add(radioMashreq);
        radioMashreq.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        radioMashreq.setText("Mashreq Bank");

        checkAccountPay.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        checkAccountPay.setText("Account Payee");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(radioCommercial)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(radioDoha)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(radioMashreq)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(checkAccountPay)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtAmountInWords, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(txtChequeNo, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jDateCheque, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(cmbFromName, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbBearer, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane1)
                            .addComponent(jToolBar1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(jScrollPane2)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(radioDoha)
                        .addComponent(radioMashreq)
                        .addComponent(checkAccountPay))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(radioCommercial)
                        .addComponent(jLabel8)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(cmbFromName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(cmbBearer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel5)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtChequeNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jDateCheque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtAmountInWords, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 543, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtAmountFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAmountFocusLost
        // TODO add your handling code here:
        String phrase = txtAmount.getText() ;
        if(phrase.contains("."))
        {
            Float num = new Float( phrase ) ;
            int riyal = (int)Math.floor( num ) ;
            int dirham = Integer.parseInt(phrase.substring(phrase.lastIndexOf(".")+1));
            //txtTheSumOfQR.setText(+phrase.lastIndexOf(".")+" ... "+phrase.substring(phrase.lastIndexOf(".")+1));
            String s = EnglishNumberToWords.convert( riyal ) + " and " + EnglishNumberToWords.convert( dirham ) + " Dirham Only" ;
            txtAmountInWords.setText(s);
        }
        else
        {
            Float num = new Float( phrase ) ;
            int riyal = (int)Math.floor( num ) ;
            int dirham = Integer.parseInt(phrase.substring(phrase.lastIndexOf(".")+1));
            //txtTheSumOfQR.setText(+phrase.lastIndexOf(".")+" ... "+phrase.substring(phrase.lastIndexOf(".")+1));
            String s = EnglishNumberToWords.convert( riyal ) + " Only";
            txtAmountInWords.setText(s);
        }
    }//GEN-LAST:event_txtAmountFocusLost

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
       if(radioCommercial.isSelected())
       {
           if(cmbFromName.getSelectedItem().equals("FIXTURE INTERNATIONAL W.L.L"))
           {
               insertIntoFixCbqDb();
           }
           else if(cmbFromName.getSelectedItem().equals("ANAR TRADING & CONTRACTING"))
           {
               insertIntoAnarCbqDb();
           }
           else
           {
               
           }
       }
       else if(radioDoha.isSelected())
       {
           if(cmbFromName.getSelectedItem().equals("FIXTURE INTERNATIONAL W.L.L"))
           {
               insertIntoFixDohaDb();
           }
           else if(cmbFromName.getSelectedItem().equals("ANAR TRADING & CONTRACTING"))
           {
               JOptionPane.showMessageDialog(null, "There no DOha-Bank Account Attached with ANAR TRADING & CONTRACTING");
           }
           else
           {
               
           }
       }
       else
       {
           if(cmbFromName.getSelectedItem().equals("FIXTURE INTERNATIONAL W.L.L"))
           {
               insertIntoFixMashreqDb();
           }
           else if(cmbFromName.getSelectedItem().equals("ANAR TRADING & CONTRACTING"))
           {
               JOptionPane.showMessageDialog(null, "There no Mashreq-Bank Account Attached with ANAR TRADING & CONTRACTING");
           }
           else
           {
               
           }
       }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        updateDb();
        
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // TODO add your handling code here:
        dispose();
        ViewChequePrintingForm();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

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
                int i=stmt1.executeUpdate("delete from tbl_cheque where chequeId="+chequeId);
                dispose();
                ViewChequePrintingForm();
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
                jTablePopUp.show(evt.getComponent(), evt.getX(),evt.getY());
            }
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void menuItemPrintCommercialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemPrintCommercialActionPerformed
        // TODO add your handling code here:
        if(checkAccountPay.isSelected())
        {
            HashMap para=new HashMap();
            para.put("ChequeId",chequeId);
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\ChequePrinting\\Commercial\\Commercial - AccountPayee.jasper"),para);
            re.setVisible(true);
        }
        else
        {
            HashMap para=new HashMap();
            para.put("ChequeId",chequeId);
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\ChequePrinting\\Commercial\\Commercial.jasper"),para);
            re.setVisible(true);
        }
            
    }//GEN-LAST:event_menuItemPrintCommercialActionPerformed

    private void menuItemPrintDohaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemPrintDohaActionPerformed
        // TODO add your handling code here:
        if(checkAccountPay.isSelected())
        {
            HashMap para=new HashMap();
            para.put("ChequeId",chequeId);
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\ChequePrinting\\Doha\\Doha - AccountPayee.jasper"),para);
            re.setVisible(true);
        }
        else
        {
            HashMap para=new HashMap();
            para.put("ChequeId",chequeId);
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\ChequePrinting\\Doha\\Doha.jasper"),para);
            re.setVisible(true);
        }
    }//GEN-LAST:event_menuItemPrintDohaActionPerformed

    private void btnViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewActionPerformed
        // TODO add your handling code here:
        AdvancedChequeSearch ACS=new AdvancedChequeSearch();
        AnarTrading.desktopPane1.add(ACS);
        ACS.setVisible(true);
        ACS.show();
    }//GEN-LAST:event_btnViewActionPerformed

    private void txtChequeNumberSearchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtChequeNumberSearchFocusLost
        // TODO add your handling code here:

    }//GEN-LAST:event_txtChequeNumberSearchFocusLost

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        if(txtChequeNumberSearch.getText().equals(""))
        {
            JOptionPane.showMessageDialog(txtChequeNumberSearch, "There is no cheque# to search");
        }
        else
        {
            viewDbChequeDetailsUsingChequeNumber();
        }
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnSaveMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSaveMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSaveMouseEntered

    private void btnSaveMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSaveMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSaveMouseExited

    private void menuItemPrintMashreqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemPrintMashreqActionPerformed
        // TODO add your handling code here:
        if(checkAccountPay.isSelected())
        {
            HashMap para=new HashMap();
            para.put("ChequeId",chequeId);
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\ChequePrinting\\mashreq\\Mashreq -AccountPayee.jasper"),para);
            re.setVisible(true);
        }
        else
        {
            HashMap para=new HashMap();
            para.put("ChequeId",chequeId);
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\ChequePrinting\\mashreq\\Mashreq.jasper"),para);
            re.setVisible(true);
        }
        
    }//GEN-LAST:event_menuItemPrintMashreqActionPerformed
/*
*/
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnView;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox checkAccountPay;
    private javax.swing.JComboBox cmbBearer;
    private javax.swing.JComboBox cmbFromName;
    private com.toedter.calendar.JDateChooser jDateCheque;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JPopupMenu jTablePopUp;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JMenuItem menuItemPrintCommercial;
    private javax.swing.JMenuItem menuItemPrintDoha;
    private javax.swing.JMenuItem menuItemPrintMashreq;
    private javax.swing.JRadioButton radioCommercial;
    private javax.swing.JRadioButton radioDoha;
    private javax.swing.JRadioButton radioMashreq;
    private javax.swing.JTextField txtAmount;
    private javax.swing.JTextField txtAmountInWords;
    private javax.swing.JTextField txtChequeNo;
    private javax.swing.JTextField txtChequeNumberSearch;
    private javax.swing.JTextArea txtRemarks;
    // End of variables declaration//GEN-END:variables
public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
   DateFormat defaultDate = new SimpleDateFormat("yyyy-MM-dd");
   String dateString = "",chequeDate,bankName,path,inputValue;
   int chequeId;
}
