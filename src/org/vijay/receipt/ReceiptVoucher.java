package org.vijay.receipt;


import common.CurrentWorkingDirectory;
import org.vijay.report.ReportView;
import org.vijay.common.EnglishNumberToWords;
import org.vijay.common.AnarTrading;
import org.vijay.common.connection;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JasperPrint;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author MAC
 */
public class ReceiptVoucher extends javax.swing.JInternalFrame {

    /**
     * Creates new form ReceiptVoucher
     */
    public ReceiptVoucher() {
        initComponents();
        viewDbEmployeeDetails();
        setLocation(middle);
        txtDate.setEditable(false);
        txtDated.setEditable(false);
        btnPrint.setEnabled(false);
        btnUpdate.setEnabled(false);
        txtTheSumOfQR.setEditable(false);
        txtDate.setText("1111-11-11");
        txtDated.setText("1111-11-11");
        btnDate.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

                public void propertyChange(java.beans.PropertyChangeEvent evt) {
                    if (evt.getNewValue() instanceof Date) {
                        todayDate((Date) evt.getNewValue());
                    }
                }
            });
        btnDated.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

                public void propertyChange(java.beans.PropertyChangeEvent evt) {
                    if (evt.getNewValue() instanceof Date) {
                        todayDated((Date) evt.getNewValue());
                    }
                }
            });
        jTable1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
            int rowNo=jTable1.getSelectedRow();
            btnSave.setEnabled(false);
            btnPrint.setEnabled(true);
            btnUpdate.setEnabled(true);
            paymentId=Integer.parseInt(jTable1.getValueAt(rowNo, 0).toString());
            txtDate.setText(jTable1.getValueAt(rowNo,1).toString());
            txtreceivedFrom.setText(jTable1.getValueAt(rowNo,2).toString());
            txtByCashOrCheckNo.setText(jTable1.getValueAt(rowNo,3).toString());
            txtDated.setText(jTable1.getValueAt(rowNo,4).toString());
            txtBank.setText(jTable1.getValueAt(rowNo,5).toString());
            txtBeing.setText(jTable1.getValueAt(rowNo,6).toString());
            txtAmount.setText(jTable1.getValueAt(rowNo,7).toString()); 
            txtTheSumOfQR.setText(jTable1.getValueAt(rowNo,8).toString()); 
            }
        });
        CurrentWorkingDirectory CWD=new CurrentWorkingDirectory();
        path=CWD.getpath();
    }
    public void todayDate(String dateString)
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
                this.todayDate(date);
    }
     public void todayDate(Date date)
     {
        if (date != null)
        dateString = dateFormat.format(date);
        txtDate.setText(dateString);
        btnDate.setTargetDate(date);
     }
     public void todayDated(String dateString)
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
                this.todayDated(date);
    }
     public void todayDated(Date date)
     {
        if (date != null)
        dateString = dateFormat.format(date);
        txtDated.setText(dateString);
        btnDated.setTargetDate(date);
     }
     public void getPaymentId()
     {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT MAX(receiptId) FROM tbl_receipt;");
            while(rs.next())
            {
                ipaymentId=rs.getInt(1);
            }
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
            PreparedStatement ps=con.prepareStatement("INSERT INTO tbl_receipt(voucherDate,receivedFrom,ByCashOrCheckNo,dated,bank,being,amount,theSumOf) VALUES(?,upper(?),upper(?),?,upper(?),upper(?),?,upper(?))");
            ps.setString(1, txtDate.getText());
            ps.setString(2, txtreceivedFrom.getText());
            ps.setString(3, txtByCashOrCheckNo.getText());
            ps.setString(4, txtDated.getText());
            ps.setString(5, txtBank.getText());
            ps.setString(6, txtBeing.getText());
            ps.setString(7, txtAmount.getText());
            ps.setString(8, txtTheSumOfQR.getText());
            int i=ps.executeUpdate();
            if(i!=0)
            {
//                getPaymentId();
//                HashMap para=new HashMap();
//                para.put("receiptId",ipaymentId);
//                ReportView re=new ReportView("D:\\Vijay\\reports\\Anar\\ReceiptVoucher.jasper",para);
//                re.setVisible(true);
            }
            con.close();
            dispose();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(rootPane,e+"Error SA001");
        }
    }
     public void ViewPaymentForm()
    {
        ReceiptVoucher RV = new ReceiptVoucher();
        AnarTrading.desktopPane.add(RV);
        RV.setVisible(true);
        AnarTrading.btnReceiptVoucher.setEnabled(false);
        AnarTrading.menuItemReceiptVoucher.setEnabled(false);
    }
     public void viewDbEmployeeDetails()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            int i=0;
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT receiptId '"+"Payment Id"+"',voucherDate'"+"Date"+"',receivedFrom '"+"Received From"+"',ByCashOrCheckNo '"+"By Check Or Check#"+"',dated'"+"Dated"+"',bank'"+"Bank"+"',being'"+"Being"+"',amount'"+"Amount"+"',theSumOf'"+"The Sum Of"+"' FROM tbl_receipt order by receiptId desc ");
            jTable1.setModel(DbUtils.resultSetToTableModel(rs));
            con.close();
            jTable1.getColumnModel().getColumn(0).setMinWidth(100);
            jTable1.getColumnModel().getColumn(0).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(0).setWidth(50);
            jTable1.setShowHorizontalLines( false );
            jTable1.setRowSelectionAllowed( true );

        }
        catch(Exception e)
        {

        }
    }
     public void updateDb()
    {
        
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            PreparedStatement ps=con.prepareStatement("UPDATE tbl_receipt SET voucherDate=?,receivedFrom=upper(?),ByCashOrCheckNo=upper(?),dated=?,bank=upper(?),being=upper(?),amount=?,theSumOf=upper(?) where receiptId=?");
            ps.setString(1,txtDate.getText());
            ps.setString(2,txtreceivedFrom.getText());
            ps.setString(3,txtByCashOrCheckNo.getText());
            ps.setString(4,txtDated.getText());
            ps.setString(5,txtBank.getText());
            ps.setString(6,txtBeing.getText());
            ps.setString(7,txtAmount.getText());
            ps.setString(8,txtTheSumOfQR.getText());
            ps.setInt(9, paymentId);
            ps.executeUpdate();
            con.close();
            dispose();
            
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

        jTablePopUp = new javax.swing.JPopupMenu();
        jPopUpPrint = new javax.swing.JMenuItem();
        jPopUpPrintSealSign = new javax.swing.JMenuItem();
        jPopUpPrintDuplicate = new javax.swing.JMenuItem();
        jPopUpPrintDuplicateWithSealSign = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtAmount = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtDate = new javax.swing.JTextField();
        btnDate = new net.sourceforge.jcalendarbutton.JCalendarButton();
        jLabel1 = new javax.swing.JLabel();
        txtreceivedFrom = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtTheSumOfQR = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtByCashOrCheckNo = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtDated = new javax.swing.JTextField();
        btnDated = new net.sourceforge.jcalendarbutton.JCalendarButton();
        jLabel5 = new javax.swing.JLabel();
        txtBank = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtBeing = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        btnPrint = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        jPopUpPrint.setText("Print");
        jPopUpPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPopUpPrintActionPerformed(evt);
            }
        });
        jTablePopUp.add(jPopUpPrint);

        jPopUpPrintSealSign.setText("Print With Seal and Sign");
        jPopUpPrintSealSign.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPopUpPrintSealSignActionPerformed(evt);
            }
        });
        jTablePopUp.add(jPopUpPrintSealSign);

        jPopUpPrintDuplicate.setText("Print Duplicate");
        jPopUpPrintDuplicate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPopUpPrintDuplicateActionPerformed(evt);
            }
        });
        jTablePopUp.add(jPopUpPrintDuplicate);

        jPopUpPrintDuplicateWithSealSign.setText("Print Duplicate With Seal and Sign");
        jPopUpPrintDuplicateWithSealSign.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPopUpPrintDuplicateWithSealSignActionPerformed(evt);
            }
        });
        jTablePopUp.add(jPopUpPrintDuplicateWithSealSign);

        setMaximizable(true);
        setTitle("RECEIPT VOUCHER");
        setToolTipText("");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Receipt Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Gabriola", 0, 18))); // NOI18N

        jLabel9.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        jLabel9.setText("QR");

        txtAmount.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtAmountFocusLost(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        jLabel7.setText("Date");

        btnDate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/calendar-icon.png"))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        jLabel1.setText("Received from Mr./M/s");

        jLabel2.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        jLabel2.setText("The sum of QR.");

        jLabel3.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        jLabel3.setText("By Cash/Cheque No.");

        jLabel4.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        jLabel4.setText("Dated");

        btnDated.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/calendar-icon.png"))); // NOI18N

        jLabel5.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        jLabel5.setText("Bank");

        jLabel6.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        jLabel6.setText("Being");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel1)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(txtByCashOrCheckNo, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDated, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDated, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtTheSumOfQR)
                    .addComponent(txtreceivedFrom)
                    .addComponent(txtBank)
                    .addComponent(txtBeing))
                .addGap(21, 21, 21))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7)
                        .addComponent(jLabel9)
                        .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtreceivedFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtTheSumOfQR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtByCashOrCheckNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtDated, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDated, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtBank, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtBeing, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26))
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

        btnPrint.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/MODIFY.PNG"))); // NOI18N
        btnPrint.setText("Print");
        btnPrint.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });

        btnUpdate.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/MODIFY.PNG"))); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnRefresh, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
            .addComponent(btnSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnPrint, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(btnSave)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnUpdate)
                .addGap(12, 12, 12)
                .addComponent(btnPrint)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnRefresh)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
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
            txtTheSumOfQR.setText(s);
        }
        else
        {
            Float num = new Float( phrase ) ;
            int riyal = (int)Math.floor( num ) ;
            int dirham = Integer.parseInt(phrase.substring(phrase.lastIndexOf(".")+1));
            //txtTheSumOfQR.setText(+phrase.lastIndexOf(".")+" ... "+phrase.substring(phrase.lastIndexOf(".")+1));
            String s = EnglishNumberToWords.convert( riyal ) + " Only";
            txtTheSumOfQR.setText(s);
        }
    }//GEN-LAST:event_txtAmountFocusLost

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed

        if(txtAmount.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "Please Enter The Amount", "Warning!!", JOptionPane.WARNING_MESSAGE);
        }
        else if(txtDate.getText().equals("1111-11-11"))
        {
            JOptionPane.showMessageDialog(null, "Please Enter The Date", "Warning!!", JOptionPane.WARNING_MESSAGE);
        }
        else if(txtreceivedFrom.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "Please Enter Paid To", "Warning!!", JOptionPane.WARNING_MESSAGE);
        }
        else
        {
            insertIntoDb();
            ViewPaymentForm();
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        dispose();
        AnarTrading.btnReceiptVoucher.setEnabled(true);
        AnarTrading.menuItemReceiptVoucher.setEnabled(true);
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // TODO add your handling code here:
        dispose();
        ViewPaymentForm();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        // TODO add your handling code here:
//        HashMap para=new HashMap();
//        para.put("receiptId",paymentId);
//        ReportView re=new ReportView("D:\\Vijay\\reports\\Anar\\ReceiptVoucherDuplicate.jasper",para);
//        re.setVisible(true);
    }//GEN-LAST:event_btnPrintActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        if(txtAmount.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "Please Enter The Amount", "Warning!!", JOptionPane.WARNING_MESSAGE);
        }
        else if(txtDate.getText().equals("1111-11-11"))
        {
            JOptionPane.showMessageDialog(null, "Please Enter The Date", "Warning!!", JOptionPane.WARNING_MESSAGE);
        }
        else if(txtreceivedFrom.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "Please Enter Paid To", "Warning!!", JOptionPane.WARNING_MESSAGE);
        }
        else
        {
            updateDb();
            ViewPaymentForm();
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

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

    private void jPopUpPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPopUpPrintActionPerformed
        // TODO add your handling code here:
        HashMap para=new HashMap();
        para.put("receiptId",paymentId);
        ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\ReceiptVoucher\\ReceiptVoucher.jasper"),para);
        re.setVisible(true);
    }//GEN-LAST:event_jPopUpPrintActionPerformed

    private void jPopUpPrintSealSignActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPopUpPrintSealSignActionPerformed
        // TODO add your handling code here:
        HashMap para=new HashMap();
        para.put("receiptId",paymentId);
        ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\ReceiptVoucher\\ReceiptVoucher-SealAndSign.jasper"),para);
        re.setVisible(true);
    }//GEN-LAST:event_jPopUpPrintSealSignActionPerformed

    private void jPopUpPrintDuplicateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPopUpPrintDuplicateActionPerformed
        // TODO add your handling code here:
        HashMap para=new HashMap();
        para.put("receiptId",paymentId);
        ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\ReceiptVoucher\\ReceiptVoucherDuplicate.jasper"),para);
        re.setVisible(true);
    }//GEN-LAST:event_jPopUpPrintDuplicateActionPerformed

    private void jPopUpPrintDuplicateWithSealSignActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPopUpPrintDuplicateWithSealSignActionPerformed
        // TODO add your handling code here:
        HashMap para=new HashMap();
        para.put("receiptId",paymentId);
        ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\ReceiptVoucher\\ReceiptVoucherDuplicate-SealAndSign.jasper"),para);
        re.setVisible(true);
    }//GEN-LAST:event_jPopUpPrintDuplicateWithSealSignActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private net.sourceforge.jcalendarbutton.JCalendarButton btnDate;
    private net.sourceforge.jcalendarbutton.JCalendarButton btnDated;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JMenuItem jPopUpPrint;
    private javax.swing.JMenuItem jPopUpPrintDuplicate;
    private javax.swing.JMenuItem jPopUpPrintDuplicateWithSealSign;
    private javax.swing.JMenuItem jPopUpPrintSealSign;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JPopupMenu jTablePopUp;
    private javax.swing.JTextField txtAmount;
    private javax.swing.JTextField txtBank;
    private javax.swing.JTextField txtBeing;
    private javax.swing.JTextField txtByCashOrCheckNo;
    private javax.swing.JTextField txtDate;
    private javax.swing.JTextField txtDated;
    private javax.swing.JTextField txtTheSumOfQR;
    private javax.swing.JTextField txtreceivedFrom;
    // End of variables declaration//GEN-END:variables
    Point middle = new Point(100,0);
    public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat defaultDate = new SimpleDateFormat("yyyy-MM-dd");
    String dateString = "",path;
    int paymentId,ipaymentId;
    connection c=new connection();
    Connection con=c.conn();
    JasperPrint jasperPrint;
}
