package org.vijay.receipt;


import org.vijay.common.CurrentWorkingDirectory;
import org.vijay.report.ReportView;
import org.vijay.common.EnglishNumberToWords;
import org.vijay.common.AnarTrading;
import org.vijay.common.connection;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import org.vijay.common.AutoCompleteDecorator;
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
public class ReceiptVoucher extends javax.swing.JInternalFrame {

    /**
     * Creates new form ReceiptVoucher
     */
    public ReceiptVoucher() {
        initComponents();
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        cmbReceivedFromFill();
        viewDbDetails();
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
            cmbReceivedFrom.setSelectedItem(jTable1.getValueAt(rowNo,2).toString());
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
     public void cmbReceivedFromFill() {
        try {
            connection c = new connection();
            Connection con = c.conn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT toAdd FROM tbl_invoicedetails order by toAdd asc");
            while (rs.next()) {
                cmbReceivedFrom.addItem(rs.getString(1));
            }
            con.close();
        } catch (SQLException ex) {
            
        }
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
            ps.setString(2, cmbReceivedFrom.getSelectedItem().toString());
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
        AnarTrading.menuItemReceiptVoucher.setEnabled(false);
    }
     public void viewDbDetails()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            int i=0;
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT receiptId '"+"Payment Id"+"',voucherDate'"+"Date"+"',receivedFrom '"+"Received From"+"',ByCashOrCheckNo '"+"Cheque#"+"',dated'"+"Dated"+"',bank'"+"Bank"+"',being'"+"Being"+"',amount'"+"Amount"+"',theSumOf'"+"The Sum Of"+"' FROM tbl_receipt order by receiptId desc ");
            jTable1.setModel(DbUtils.resultSetToTableModel(rs));
            jTable1.getColumnModel().getColumn(1).setCellRenderer(NumberRenderer.getDateTimeRenderer());
            jTable1.getColumnModel().getColumn(4).setCellRenderer(NumberRenderer.getDateTimeRenderer());
            jTable1.getColumnModel().getColumn(7).setCellRenderer(NumberRenderer.getIntegerRenderer());
            jTable1.getColumnModel().getColumn(0).setMinWidth(70);
            jTable1.getColumnModel().getColumn(0).setMaxWidth(70);
            jTable1.getColumnModel().getColumn(1).setWidth(70);
            jTable1.getColumnModel().getColumn(1).setMinWidth(90);
            jTable1.getColumnModel().getColumn(1).setMaxWidth(90);
            jTable1.getColumnModel().getColumn(1).setWidth(90);
            jTable1.getColumnModel().getColumn(3).setMinWidth(90);
            jTable1.getColumnModel().getColumn(3).setMaxWidth(90);
            jTable1.getColumnModel().getColumn(3).setWidth(90);
            jTable1.getColumnModel().getColumn(4).setMinWidth(90);
            jTable1.getColumnModel().getColumn(4).setMaxWidth(90);
            jTable1.getColumnModel().getColumn(4).setWidth(90);
            jTable1.getColumnModel().getColumn(5).setMinWidth(120);
            jTable1.getColumnModel().getColumn(5).setMaxWidth(120);
            jTable1.getColumnModel().getColumn(5).setWidth(120);
            jTable1.getColumnModel().getColumn(7).setMinWidth(90);
            jTable1.getColumnModel().getColumn(7).setMaxWidth(90);
            jTable1.getColumnModel().getColumn(7).setWidth(90);
            con.close();


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
            ps.setString(2,cmbReceivedFrom.getSelectedItem().toString());
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
        cmbReceivedFrom = new javax.swing.JComboBox();
        jToolBar1 = new javax.swing.JToolBar();
        btnSave = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnPrint = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jCalendar1 = new com.toedter.calendar.JCalendar();

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

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("RECEIPT VOUCHER");
        setToolTipText("");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Receipt Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Verdana", 0, 10))); // NOI18N

        jLabel9.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel9.setText("QR");

        txtAmount.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtAmount.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtAmountFocusLost(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel7.setText("Date");

        txtDate.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        btnDate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/calendar-icon.png"))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel1.setText("Received from Mr./M/s");

        jLabel2.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel2.setText("The sum of QR.");

        txtTheSumOfQR.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel3.setText("By Cash/Cheque No.");

        txtByCashOrCheckNo.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel4.setText("Dated");

        txtDated.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        btnDated.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/calendar-icon.png"))); // NOI18N

        jLabel5.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel5.setText("Bank");

        txtBank.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel6.setText("Being");

        txtBeing.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        cmbReceivedFrom.setEditable(true);
        AutoCompleteDecorator.decorate(cmbReceivedFrom);
        cmbReceivedFrom.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnSave.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/SAVE.PNG"))); // NOI18N
        btnSave.setText("Save");
        btnSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        jToolBar1.add(btnSave);

        btnUpdate.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/MODIFY.PNG"))); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        jToolBar1.add(btnUpdate);

        btnPrint.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/MODIFY.PNG"))); // NOI18N
        btnPrint.setText("Print");
        btnPrint.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });
        jToolBar1.add(btnPrint);

        btnRefresh.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        btnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/clear.png"))); // NOI18N
        btnRefresh.setText("Refresh");
        btnRefresh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });
        jToolBar1.add(btnRefresh);

        btnCancel.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/CANCEL.PNG"))); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        jToolBar1.add(btnCancel);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel9)
                                .addGap(127, 127, 127)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtAmount, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                                .addGap(159, 159, 159)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnDate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtByCashOrCheckNo, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDated)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnDated, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtTheSumOfQR, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtBank)
                            .addComponent(txtBeing)
                            .addComponent(cmbReceivedFrom, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(21, 21, 21))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7)
                        .addComponent(jLabel9)
                        .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnDate, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cmbReceivedFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(txtTheSumOfQR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtByCashOrCheckNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)
                        .addComponent(txtDated, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnDated, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(txtBank, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(txtBeing, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
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

        jCalendar1.setDecorationBackgroundColor(new java.awt.Color(255, 153, 0));
        jCalendar1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCalendar1, javax.swing.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE))
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jCalendar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE))
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
        else if(cmbReceivedFrom.getSelectedItem().toString().equals(""))
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
        else if(cmbReceivedFrom.getSelectedItem().toString().equals(""))
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
    private javax.swing.JComboBox cmbReceivedFrom;
    private com.toedter.calendar.JCalendar jCalendar1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JMenuItem jPopUpPrint;
    private javax.swing.JMenuItem jPopUpPrintDuplicate;
    private javax.swing.JMenuItem jPopUpPrintDuplicateWithSealSign;
    private javax.swing.JMenuItem jPopUpPrintSealSign;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JPopupMenu jTablePopUp;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextField txtAmount;
    private javax.swing.JTextField txtBank;
    private javax.swing.JTextField txtBeing;
    private javax.swing.JTextField txtByCashOrCheckNo;
    private javax.swing.JTextField txtDate;
    private javax.swing.JTextField txtDated;
    private javax.swing.JTextField txtTheSumOfQR;
    // End of variables declaration//GEN-END:variables
    public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat defaultDate = new SimpleDateFormat("yyyy-MM-dd");
    String dateString = "",path;
    int paymentId,ipaymentId;
    connection c=new connection();
    Connection con=c.conn();
    JasperPrint jasperPrint;
}
