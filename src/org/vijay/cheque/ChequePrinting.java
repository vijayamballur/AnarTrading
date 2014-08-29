/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.vijay.cheque;

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
            else
            {
                ps.setString(1,radioDoha.getText().toString());
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
            JOptionPane.showMessageDialog(rootPane,e+"Error SA001");
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
            else
            {
                ps.setString(1,radioDoha.getText().toString());
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
        btnDelete = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        btnView = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

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
        jScrollPane2.setViewportView(jTable1);

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
                        .addComponent(radioDoha))
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
                        .addComponent(jToolBar1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(273, Short.MAX_VALUE))
            .addComponent(jScrollPane2)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(radioDoha)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(radioCommercial)
                        .addComponent(jLabel8)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE)
                .addContainerGap())
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

        insertIntoDb();
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
            HashMap para=new HashMap();
            para.put("ChequeId",chequeId);
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\ChequePrinting\\Commercial\\Commercial.jasper"),para);
            re.setVisible(true);
    }//GEN-LAST:event_menuItemPrintCommercialActionPerformed

    private void menuItemPrintDohaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemPrintDohaActionPerformed
        // TODO add your handling code here:
            HashMap para=new HashMap();
            para.put("ChequeId",chequeId);
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\ChequePrinting\\Doha\\Doha.jasper"),para);
            re.setVisible(true);
    }//GEN-LAST:event_menuItemPrintDohaActionPerformed

    private void btnViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewActionPerformed
        // TODO add your handling code here:
        AdvancedChequeSearch ACS=new AdvancedChequeSearch();
        AnarTrading.desktopPane1.add(ACS);
        ACS.setVisible(true);
        ACS.show();
    }//GEN-LAST:event_btnViewActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnView;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cmbBearer;
    private javax.swing.JComboBox cmbFromName;
    private com.toedter.calendar.JDateChooser jDateCheque;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JPopupMenu jTablePopUp;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JMenuItem menuItemPrintCommercial;
    private javax.swing.JMenuItem menuItemPrintDoha;
    private javax.swing.JRadioButton radioCommercial;
    private javax.swing.JRadioButton radioDoha;
    private javax.swing.JTextField txtAmount;
    private javax.swing.JTextField txtAmountInWords;
    private javax.swing.JTextField txtChequeNo;
    private javax.swing.JTextArea txtRemarks;
    // End of variables declaration//GEN-END:variables
public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
   DateFormat defaultDate = new SimpleDateFormat("yyyy-MM-dd");
   String dateString = "",chequeDate,bankName,path,inputValue;
   int chequeId;
}
