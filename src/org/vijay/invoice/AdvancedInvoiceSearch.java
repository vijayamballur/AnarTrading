package org.vijay.invoice;


import org.vijay.common.CurrentWorkingDirectory;
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
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.proteanit.sql.DbUtils;
import org.vijay.common.NumberRenderer;
import org.vijay.employee.LabourDetails;
import org.vijay.report.ReportView;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author MAC
 */
public final class AdvancedInvoiceSearch extends javax.swing.JInternalFrame {

    /**
     * Creates new form AdvancedEmployeeSearch
     */
    public AdvancedInvoiceSearch() {
        initComponents();
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setLocation(middle);
        cmbFromFill();
        cmbToFill();
        cmbPaymentFill();
        queryGenerator();
        jTable1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
            int rowNo=jTable1.getSelectedRow();
            invoiceId=Integer.parseInt(jTable1.getValueAt(rowNo, 1).toString());
            }
        });
        CurrentWorkingDirectory CWD=new CurrentWorkingDirectory();
        path=CWD.getpath();
    }
    public void ViewAdvancedInvoiceSearchForm()
    {
        AdvancedInvoiceSearch  AIS=new AdvancedInvoiceSearch();
        AnarTrading.desktopPane1.add(AIS);
        AIS.setVisible(true);
        AIS.show();
    }
    public void queryGenerator()
    {
        String query="";
        String totalQuery="";
        String payment=cmbPayment.getSelectedItem().toString();
        String from=cmbFrom.getSelectedItem().toString();
        String to=cmbTo.getSelectedItem().toString();
        String month=cmbMonth.getSelectedItem().toString();
        String year=cmbYear.getSelectedItem().toString();
        
        
        if(chkPayment.isSelected()==false && chkFrom.isSelected()==false && chkTo.isSelected()==false && chkMonth.isSelected()==false && chkYear.isSelected()==false)
        {   if(radioAll.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails";
                printValue="1";
            }
            if(radioNotPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"',CASE WHEN DATEDIFF(paymentDate,CURDATE())>=0 THEN CONCAT(DATEDIFF(paymentDate,CURDATE()),' days left') ELSE CONCAT(ABS(DATEDIFF(paymentDate,CURDATE())),' days Due')END as STATUS from tbl_invoicedetails,(SELECT @i := 0) temp where status=0 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where status=0";
                printValue="1.0";
            }
            if(radioPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where status=1 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where status=1";
                printValue="1.1";
            }   
            search(query);
            findTotal(totalQuery);
        }
        if(chkPayment.isSelected()==false && chkFrom.isSelected()==false && chkTo.isSelected()==false && chkMonth.isSelected()==false && chkYear.isSelected()==true)
        {
            if(radioAll.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where invoiceYear=? order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where invoiceYear=?";
                printValue="2";
            }
            if(radioNotPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"',CASE WHEN DATEDIFF(paymentDate,CURDATE())>=0 THEN CONCAT(DATEDIFF(paymentDate,CURDATE()),' days left') ELSE CONCAT(ABS(DATEDIFF(paymentDate,CURDATE())),' days Due')END as STATUS from tbl_invoicedetails,(SELECT @i := 0) temp where invoiceYear=? and status=0 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where invoiceYear=? and status=0";
                printValue="2.0";
            }
            if(radioPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where invoiceYear=? and status=1 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where invoiceYear=? and status=1";
                printValue="2.1";
            }   
            search(query,year);
            findTotal(totalQuery,year);
        }
        if(chkPayment.isSelected()==false && chkFrom.isSelected()==false && chkTo.isSelected()==false && chkMonth.isSelected()==true && chkYear.isSelected()==false)
        {
            if(radioAll.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where invoiceMonth=? order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where invoiceMonth=?";
                printValue="3";
            }
            if(radioNotPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"',CASE WHEN DATEDIFF(paymentDate,CURDATE())>=0 THEN CONCAT(DATEDIFF(paymentDate,CURDATE()),' days left') ELSE CONCAT(ABS(DATEDIFF(paymentDate,CURDATE())),' days Due')END as STATUS from tbl_invoicedetails,(SELECT @i := 0) temp where invoiceMonth=? and status=0 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where invoiceMonth=? and status=0";
                printValue="3.0";
            }
            if(radioPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where invoiceMonth=? and status=1 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where invoiceMonth=? and status=1";
                printValue="3.1";
            }   
            search(query,month);
            findTotal(totalQuery,month);
        }
        if(chkPayment.isSelected()==false && chkFrom.isSelected()==false && chkTo.isSelected()==false && chkMonth.isSelected()==true && chkYear.isSelected()==true)
        {
            if(radioAll.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where invoiceMonth=? and invoiceYear=? order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where invoiceMonth=? and invoiceYear=?";
                printValue="4";
            }
            if(radioNotPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"',CASE WHEN DATEDIFF(paymentDate,CURDATE())>=0 THEN CONCAT(DATEDIFF(paymentDate,CURDATE()),' days left') ELSE CONCAT(ABS(DATEDIFF(paymentDate,CURDATE())),' days Due')END as STATUS from tbl_invoicedetails,(SELECT @i := 0) temp where invoiceMonth=? and invoiceYear=? and status=0 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where invoiceMonth=? and invoiceYear=? and status=0";
                printValue="4.0";
            }
            if(radioPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where invoiceMonth=? and invoiceYear=? and status=1 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where invoiceMonth=? and invoiceYear=? and status=1";
                printValue="4.1";
            }   
            search(query,month,year);
            findTotal(totalQuery,month,year);
        }
        if(chkPayment.isSelected()==false && chkFrom.isSelected()==false && chkTo.isSelected()==true && chkMonth.isSelected()==false && chkYear.isSelected()==false)
        {
            if(radioAll.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where toAdd=? order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where toAdd=?";
                printValue="5";
            }
            if(radioNotPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"',CASE WHEN DATEDIFF(paymentDate,CURDATE())>=0 THEN CONCAT(DATEDIFF(paymentDate,CURDATE()),' days left') ELSE CONCAT(ABS(DATEDIFF(paymentDate,CURDATE())),' days Due')END as STATUS from tbl_invoicedetails,(SELECT @i := 0) temp where toAdd=? and status=0 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where toAdd=? and status=0";
                printValue="5.0";
            }
            if(radioPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where toAdd=? and status=1 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where toAdd=? and status=1";
                printValue="5.1";
            }  
            search(query,to);
            findTotal(totalQuery,to);
        }
        if(chkPayment.isSelected()==false && chkFrom.isSelected()==false && chkTo.isSelected()==true && chkMonth.isSelected()==false && chkYear.isSelected()==true)
        {
            if(radioAll.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where toAdd=? and invoiceYear=? order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where toAdd=? and invoiceYear=?";
                printValue="6";
            }
            if(radioNotPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"',CASE WHEN DATEDIFF(paymentDate,CURDATE())>=0 THEN CONCAT(DATEDIFF(paymentDate,CURDATE()),' days left') ELSE CONCAT(ABS(DATEDIFF(paymentDate,CURDATE())),' days Due')END as STATUS from tbl_invoicedetails,(SELECT @i := 0) temp where toAdd=? and invoiceYear=? and status=0 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where toAdd=? and invoiceYear=? and status=0";
                printValue="6.0";
            }
            if(radioPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where toAdd=? and invoiceYear=? and status=1 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where toAdd=? and invoiceYear=? and status=1";
                printValue="6.1";
            }  
            search(query,to,year);
            findTotal(totalQuery,to,year);
        }
        if(chkPayment.isSelected()==false && chkFrom.isSelected()==false && chkTo.isSelected()==true && chkMonth.isSelected()==true && chkYear.isSelected()==false)
        {
            if(radioAll.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where toAdd=? and invoiceMonth=? order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where toAdd=? and invoiceMonth=?";
                printValue="7";
            }
            if(radioNotPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"',CASE WHEN DATEDIFF(paymentDate,CURDATE())>=0 THEN CONCAT(DATEDIFF(paymentDate,CURDATE()),' days left') ELSE CONCAT(ABS(DATEDIFF(paymentDate,CURDATE())),' days Due')END as STATUS from tbl_invoicedetails,(SELECT @i := 0) temp where toAdd=? and invoiceMonth=? and status=0 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where toAdd=? and invoiceMonth=? and status=0";
                printValue="7.0";
                
            }
            if(radioPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where toAdd=? and invoiceMonth=? and status=1 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where toAdd=? and invoiceMonth=? and status=1";
                printValue="7.1";
            }  
            search(query,to,month);
            findTotal(totalQuery,to,month);
        }
        if(chkPayment.isSelected()==false && chkFrom.isSelected()==false && chkTo.isSelected()==true && chkMonth.isSelected()==true && chkYear.isSelected()==true)
        {
            if(radioAll.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where toAdd=? and invoiceMonth=? and invoiceYear=? order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where toAdd=? and invoiceMonth=? and invoiceYear=?";
                printValue="8";
            }
            if(radioNotPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"',CASE WHEN DATEDIFF(paymentDate,CURDATE())>=0 THEN CONCAT(DATEDIFF(paymentDate,CURDATE()),' days left') ELSE CONCAT(ABS(DATEDIFF(paymentDate,CURDATE())),' days Due')END as STATUS from tbl_invoiceDetails,(SELECT @i := 0) temp where toAdd=? and invoiceMonth=? and invoiceYear=? and status=0 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where toAdd=? and invoiceMonth=? and invoiceYear=? and status=0";
                printValue="8.0";
            }
            if(radioPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where toAdd=? and invoiceMonth=? and invoiceYear=? and status=1 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where toAdd=? and invoiceMonth=? and invoiceYear=? and status=1";
                printValue="8.1";
            }   
            search(query,to,month,year);
            findTotal(totalQuery,to,month,year);
        }
        
        if(chkPayment.isSelected()==false && chkFrom.isSelected()==true && chkTo.isSelected()==false && chkMonth.isSelected()==false && chkYear.isSelected()==false)
        {
            if(radioAll.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where fromAdd=? order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where fromAdd=?";
                printValue="9";
            }
            if(radioNotPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"',CASE WHEN DATEDIFF(paymentDate,CURDATE())>=0 THEN CONCAT(DATEDIFF(paymentDate,CURDATE()),' days left') ELSE CONCAT(ABS(DATEDIFF(paymentDate,CURDATE())),' days Due')END as STATUS from tbl_invoicedetails,(SELECT @i := 0) temp where fromAdd=? and status=0 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where fromAdd=? and status=0";
                printValue="9.0";
            }
            if(radioPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where fromAdd=? and status=1 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where fromAdd=? and status=1";
                printValue="9.1";
            }  
            search(query,from);
            findTotal(totalQuery,from);
        }
        if(chkPayment.isSelected()==false && chkFrom.isSelected()==true && chkTo.isSelected()==false && chkMonth.isSelected()==false && chkYear.isSelected()==true)
        {
            if(radioAll.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where fromAdd=? and invoiceYear=? order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where fromAdd=? and invoiceYear=?";
                printValue="10";
            }
            if(radioNotPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"',CASE WHEN DATEDIFF(paymentDate,CURDATE())>=0 THEN CONCAT(DATEDIFF(paymentDate,CURDATE()),' days left') ELSE CONCAT(ABS(DATEDIFF(paymentDate,CURDATE())),' days Due')END as STATUS from tbl_invoicedetails,(SELECT @i := 0) temp where fromAdd=? and invoiceYear=? and status=0 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where fromAdd=? and invoiceYear=? and status=0";
                printValue="10.0";
            }
            if(radioPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where fromAdd=? and invoiceYear=? and status=1 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where fromAdd=? and invoiceYear=? and status=1";
                printValue="10.1";
            }  
            search(query,from,year);
            findTotal(totalQuery,from,year);
        }
        if(chkPayment.isSelected()==false && chkFrom.isSelected()==true && chkTo.isSelected()==false && chkMonth.isSelected()==true && chkYear.isSelected()==false)
        {
            if(radioAll.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where fromAdd=? and invoiceMonth=? order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where fromAdd=? and invoiceMonth=?";
                printValue="11";
            }
            if(radioNotPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"',CASE WHEN DATEDIFF(paymentDate,CURDATE())>=0 THEN CONCAT(DATEDIFF(paymentDate,CURDATE()),' days left') ELSE CONCAT(ABS(DATEDIFF(paymentDate,CURDATE())),' days Due')END as STATUS from tbl_invoicedetails,(SELECT @i := 0) temp where fromAdd=? and invoiceMonth=? and status=0 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where fromAdd=? and invoiceMonth=? and status=0";
                printValue="11.0";
            }
            if(radioPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where fromAdd=? and invoiceMonth=? and status=1 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where fromAdd=? and invoiceMonth=? and status=1";
                printValue="11.1";
            } 
            search(query,from,month);
            findTotal(totalQuery,from,month);
        }
        if(chkPayment.isSelected()==false && chkFrom.isSelected()==true && chkTo.isSelected()==false && chkMonth.isSelected()==true && chkYear.isSelected()==true)
        {
            if(radioAll.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where fromAdd=? and invoiceMonth=? and invoiceYear=? order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where fromAdd=? and invoiceMonth=? and invoiceYear=?";
                printValue="12";
            }
            if(radioNotPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"',CASE WHEN DATEDIFF(paymentDate,CURDATE())>=0 THEN CONCAT(DATEDIFF(paymentDate,CURDATE()),' days left') ELSE CONCAT(ABS(DATEDIFF(paymentDate,CURDATE())),' days Due')END as STATUS from tbl_invoicedetails,(SELECT @i := 0) temp where fromAdd=? and invoiceMonth=? and invoiceYear=? and status=0 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where fromAdd=? and invoiceMonth=? and invoiceYear=? and status=0";
                printValue="12.0";
            }
            if(radioPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where fromAdd=? and invoiceMonth=? and invoiceYear=? and status=1 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where fromAdd=? and invoiceMonth=? and invoiceYear=? and status=1";
                printValue="12.1";
            } 
            search(query,from,month,year);
            findTotal(totalQuery,from,month,year);
        }
        if(chkPayment.isSelected()==false && chkFrom.isSelected()==true && chkTo.isSelected()==true && chkMonth.isSelected()==false && chkYear.isSelected()==false)
        {
            if(radioAll.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where fromAdd=? and toAdd=? order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where fromAdd=? and toAdd=?";
                printValue="13";
            }
            if(radioNotPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"',CASE WHEN DATEDIFF(paymentDate,CURDATE())>=0 THEN CONCAT(DATEDIFF(paymentDate,CURDATE()),' days left') ELSE CONCAT(ABS(DATEDIFF(paymentDate,CURDATE())),' days Due')END as STATUS from tbl_invoicedetails,(SELECT @i := 0) temp where fromAdd=? and toAdd=? and status=0 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where fromAdd=? and toAdd=? and status=0";
                printValue="13.0";
            }
            if(radioPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where fromAdd=? and toAdd=? and status=1 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where fromAdd=? and toAdd=? and status=1";
                printValue="13.1";
            } 
            search(query,from,to);
            findTotal(totalQuery,from,to);
        }
        if(chkPayment.isSelected()==false && chkFrom.isSelected()==true && chkTo.isSelected()==true && chkMonth.isSelected()==false && chkYear.isSelected()==true)
        {
            if(radioAll.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where fromAdd=? and toAdd=? and invoiceYear=? order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where fromAdd=? and toAdd=? and invoiceYear=?";
                printValue="14";
            }
            if(radioNotPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"',CASE WHEN DATEDIFF(paymentDate,CURDATE())>=0 THEN CONCAT(DATEDIFF(paymentDate,CURDATE()),' days left') ELSE CONCAT(ABS(DATEDIFF(paymentDate,CURDATE())),' days Due')END as STATUS from tbl_invoicedetails,(SELECT @i := 0) temp where fromAdd=? and toAdd=? and invoiceYear=? and status=0 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where fromAdd=? and toAdd=? and invoiceYear=? and status=0";
                printValue="14.0";
            }
            if(radioPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where fromAdd=? and toAdd=? and invoiceYear=? and status=1 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where fromAdd=? and toAdd=? and invoiceYear=? and status=1";
                printValue="14.1";
            } 
            search(query,from,to,year);
            findTotal(totalQuery,from,to,year);
        }
        if(chkPayment.isSelected()==false && chkFrom.isSelected()==true && chkTo.isSelected()==true && chkMonth.isSelected()==true && chkYear.isSelected()==false)
        {
            if(radioAll.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where fromAdd=? and toAdd=? and invoiceMonth=? order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where fromAdd=? and toAdd=? and invoiceMonth=?";
                printValue="15";
            }
            if(radioNotPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"',CASE WHEN DATEDIFF(paymentDate,CURDATE())>=0 THEN CONCAT(DATEDIFF(paymentDate,CURDATE()),' days left') ELSE CONCAT(ABS(DATEDIFF(paymentDate,CURDATE())),' days Due')END as STATUS from tbl_invoicedetails,(SELECT @i := 0) temp where fromAdd=? and toAdd=? and invoiceMonth=? and status=0 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where fromAdd=? and toAdd=? and invoiceMonth=? and status=0";
                printValue="15.0";
            }
            if(radioPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where fromAdd=? and toAdd=? and invoiceMonth=? and status=1 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where fromAdd=? and toAdd=? and invoiceMonth=? and status=1";
                printValue="15.1";
            } 
            search(query,from,to,month);
            findTotal(totalQuery,from,to,month);
        }
        if(chkPayment.isSelected()==false && chkFrom.isSelected()==true && chkTo.isSelected()==true && chkMonth.isSelected()==true && chkYear.isSelected()==true)
        {
            if(radioAll.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where fromAdd=? and toAdd=? and invoiceMonth=? and invoiceYear=? order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where fromAdd=? and toAdd=? and invoiceMonth=? and invoiceYear=?";
                printValue="16";
            }
            if(radioNotPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"',CASE WHEN DATEDIFF(paymentDate,CURDATE())>=0 THEN CONCAT(DATEDIFF(paymentDate,CURDATE()),' days left') ELSE CONCAT(ABS(DATEDIFF(paymentDate,CURDATE())),' days Due')END as STATUS from tbl_invoicedetails,(SELECT @i := 0) temp where fromAdd=? and toAdd=? and invoiceMonth=? and invoiceYear=? and status=0 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where fromAdd=? and toAdd=? and invoiceMonth=? and invoiceYear=? and status=0";
                printValue="16.0";
            }
            if(radioPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where fromAdd=? and toAdd=? and invoiceMonth=? and invoiceYear=? and status=1 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where fromAdd=? and toAdd=? and invoiceMonth=? and invoiceYear=? and status=1";
                printValue="16.1";
            } 
            search(query,from,to,month,year);
            findTotal(totalQuery,from,to,month,year);
        }
        
        
        if(chkPayment.isSelected()==true && chkFrom.isSelected()==false && chkTo.isSelected()==false && chkMonth.isSelected()==false && chkYear.isSelected()==false)
        {
            if(radioAll.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where DATE_FORMAT(paymentDate,'%M %Y')=?";
                printValue="17";
            }
            if(radioNotPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"',CASE WHEN DATEDIFF(paymentDate,CURDATE())>=0 THEN CONCAT(DATEDIFF(paymentDate,CURDATE()),' days left') ELSE CONCAT(ABS(DATEDIFF(paymentDate,CURDATE())),' days Due')END as STATUS from tbl_invoicedetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and status=0 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where DATE_FORMAT(paymentDate,'%M %Y')=? and status=0";
                printValue="17.0";
            }
            if(radioPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and status=1 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where DATE_FORMAT(paymentDate,'%M %Y')=? and status=1";
                printValue="17.1";
            } 
            search(query,payment);
            findTotal(totalQuery,payment);
        }
        if(chkPayment.isSelected()==true && chkFrom.isSelected()==false && chkTo.isSelected()==false && chkMonth.isSelected()==false && chkYear.isSelected()==true)
        {
            if(radioAll.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and invoiceYear=? order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where DATE_FORMAT(paymentDate,'%M %Y')=? and invoiceYear=?";
                printValue="18";
            }
            if(radioNotPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"',CASE WHEN DATEDIFF(paymentDate,CURDATE())>=0 THEN CONCAT(DATEDIFF(paymentDate,CURDATE()),' days left') ELSE CONCAT(ABS(DATEDIFF(paymentDate,CURDATE())),' days Due')END as STATUS from tbl_invoicedetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and invoiceYear=? and status=0 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where DATE_FORMAT(paymentDate,'%M %Y')=? and invoiceYear=? and status=0";
                printValue="18.0";
            }
            if(radioPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and invoiceYear=? and status=1 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where DATE_FORMAT(paymentDate,'%M %Y')=? and invoiceYear=? and status=1";
                printValue="18.1";
            } 
            search(query,payment,year);
            findTotal(totalQuery,payment,year);
        }
        if(chkPayment.isSelected()==true && chkFrom.isSelected()==false && chkTo.isSelected()==false && chkMonth.isSelected()==true && chkYear.isSelected()==false)
        {
            if(radioAll.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and invoiceMonth=? order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where DATE_FORMAT(paymentDate,'%M %Y')=? and invoiceMonth=?";
                printValue="19";
            }
            if(radioNotPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"',CASE WHEN DATEDIFF(paymentDate,CURDATE())>=0 THEN CONCAT(DATEDIFF(paymentDate,CURDATE()),' days left') ELSE CONCAT(ABS(DATEDIFF(paymentDate,CURDATE())),' days Due')END as STATUS from tbl_invoicedetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and invoiceMonth=? and status=0 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where DATE_FORMAT(paymentDate,'%M %Y')=? and invoiceMonth=? and status=0";
                printValue="19.0";
            }
            if(radioPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and invoiceMonth=? and status=1 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where DATE_FORMAT(paymentDate,'%M %Y')=? and invoiceMonth=? and status=1";
                printValue="19.1";
            } 
            search(query,payment,month);
            findTotal(totalQuery,payment,month);
        }
        if(chkPayment.isSelected()==true && chkFrom.isSelected()==false && chkTo.isSelected()==false && chkMonth.isSelected()==true && chkYear.isSelected()==true)
        {
            if(radioAll.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and invoiceMonth=? and invoiceYear=? order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where DATE_FORMAT(paymentDate,'%M %Y')=? and invoiceMonth=? and invoiceYear=?";
                printValue="20";
            }
            if(radioNotPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"',CASE WHEN DATEDIFF(paymentDate,CURDATE())>=0 THEN CONCAT(DATEDIFF(paymentDate,CURDATE()),' days left') ELSE CONCAT(ABS(DATEDIFF(paymentDate,CURDATE())),' days Due')END as STATUS from tbl_invoicedetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and invoiceMonth=? and invoiceYear=? and status=0 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where DATE_FORMAT(paymentDate,'%M %Y')=? and invoiceMonth=? and invoiceYear=? and status=0";
                printValue="20.0";
            }
            if(radioPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and invoiceMonth=? and invoiceYear=? and status=1 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where DATE_FORMAT(paymentDate,'%M %Y')=? and invoiceMonth=? and invoiceYear=? and status=1";
                printValue="20.1";
            } 
            search(query,payment,month,year);
            findTotal(totalQuery,payment,month,year);
        }
        if(chkPayment.isSelected()==true && chkFrom.isSelected()==false && chkTo.isSelected()==true && chkMonth.isSelected()==false && chkYear.isSelected()==false)
        {
            if(radioAll.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and toAdd=? order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where DATE_FORMAT(paymentDate,'%M %Y')=? and toAdd=?";
                printValue="21";
            }
            if(radioNotPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"',CASE WHEN DATEDIFF(paymentDate,CURDATE())>=0 THEN CONCAT(DATEDIFF(paymentDate,CURDATE()),' days left') ELSE CONCAT(ABS(DATEDIFF(paymentDate,CURDATE())),' days Due')END as STATUS from tbl_invoicedetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and toAdd=? and status=0 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where DATE_FORMAT(paymentDate,'%M %Y')=? and toAdd=? and status=0";
                printValue="21.0";
            }
            if(radioPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and toAdd=? and status=1 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where DATE_FORMAT(paymentDate,'%M %Y')=? and toAdd=? and status=1";
                printValue="21.1";
            } 
            search(query,payment,to);
            findTotal(totalQuery,payment,to);
        }
        if(chkPayment.isSelected()==true && chkFrom.isSelected()==false && chkTo.isSelected()==true && chkMonth.isSelected()==false && chkYear.isSelected()==true)
        {
            if(radioAll.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and toAdd=? and invoiceYear=? order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where DATE_FORMAT(paymentDate,'%M %Y')=? and toAdd=? and invoiceYear=?";
                printValue="22";
            }
            if(radioNotPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"',CASE WHEN DATEDIFF(paymentDate,CURDATE())>=0 THEN CONCAT(DATEDIFF(paymentDate,CURDATE()),' days left') ELSE CONCAT(ABS(DATEDIFF(paymentDate,CURDATE())),' days Due')END as STATUS from tbl_invoicedetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and toAdd=? and invoiceYear=? and status=0 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where DATE_FORMAT(paymentDate,'%M %Y')=? and toAdd=? and invoiceYear=? and status=0";
                printValue="22.0";
            }
            if(radioPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and toAdd=? and invoiceYear=? and status=1 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where DATE_FORMAT(paymentDate,'%M %Y')=? and toAdd=? and invoiceYear=? and status=1";
                printValue="22.1";
            } 
            search(query,payment,to,year);
            findTotal(totalQuery,payment,to,year);
        }
        if(chkPayment.isSelected()==true && chkFrom.isSelected()==false && chkTo.isSelected()==true && chkMonth.isSelected()==true && chkYear.isSelected()==false)
        {
            if(radioAll.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and toAdd=? and invoiceMonth=?order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where DATE_FORMAT(paymentDate,'%M %Y')=? and toAdd=? and invoiceMonth=?";
                printValue="23";
            }
            if(radioNotPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"',CASE WHEN DATEDIFF(paymentDate,CURDATE())>=0 THEN CONCAT(DATEDIFF(paymentDate,CURDATE()),' days left') ELSE CONCAT(ABS(DATEDIFF(paymentDate,CURDATE())),' days Due')END as STATUS from tbl_invoicedetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and toAdd=? and invoiceMonth=? and status=0 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where DATE_FORMAT(paymentDate,'%M %Y')=? and toAdd=? and invoiceMonth=? and status=0";
                printValue="23.0";
            }
            if(radioPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and toAdd=? and invoiceMonth=? and status=1 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where DATE_FORMAT(paymentDate,'%M %Y')=? and toAdd=? and invoiceMonth=? and status=1";
                printValue="23.1";
            } 
            search(query,payment,to,month);
            findTotal(totalQuery,payment,to,month);
        }
        if(chkPayment.isSelected()==true && chkFrom.isSelected()==false && chkTo.isSelected()==true && chkMonth.isSelected()==true && chkYear.isSelected()==true)
        {
            if(radioAll.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and toAdd=? and invoiceMonth=? and invoiceYear=? order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where DATE_FORMAT(paymentDate,'%M %Y')=? and toAdd=? and invoiceMonth=? and invoiceYear=?";
                printValue="24";
            }
            if(radioNotPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"',CASE WHEN DATEDIFF(paymentDate,CURDATE())>=0 THEN CONCAT(DATEDIFF(paymentDate,CURDATE()),' days left') ELSE CONCAT(ABS(DATEDIFF(paymentDate,CURDATE())),' days Due')END as STATUS from tbl_invoicedetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and toAdd=? and invoiceMonth=? and invoiceYear=? and status=0 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where DATE_FORMAT(paymentDate,'%M %Y')=? and toAdd=? and invoiceMonth=? and invoiceYear=? and status=0";
                printValue="24.0";
            }
            if(radioPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and toAdd=? and invoiceMonth=? and invoiceYear=? and status=1 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where DATE_FORMAT(paymentDate,'%M %Y')=? and toAdd=? and invoiceMonth=? and invoiceYear=? and status=1";
                printValue="24.1";
            } 
            search(query,payment,to,month,year);
            findTotal(totalQuery,payment,to,month,year);
        }
        
        if(chkPayment.isSelected()==true && chkFrom.isSelected()==true && chkTo.isSelected()==false && chkMonth.isSelected()==false && chkYear.isSelected()==false)
        {
            if(radioAll.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and fromAdd=? order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where DATE_FORMAT(paymentDate,'%M %Y')=? and fromAdd=?";
                printValue="25";
            }
            if(radioNotPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"',CASE WHEN DATEDIFF(paymentDate,CURDATE())>=0 THEN CONCAT(DATEDIFF(paymentDate,CURDATE()),' days left') ELSE CONCAT(ABS(DATEDIFF(paymentDate,CURDATE())),' days Due')END as STATUS from tbl_invoicedetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and fromAdd=? and status=0 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where DATE_FORMAT(paymentDate,'%M %Y')=? and fromAdd=? and status=0";
                printValue="25.0";
            }
            if(radioPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and fromAdd=? and status=1 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where DATE_FORMAT(paymentDate,'%M %Y')=? and fromAdd=? and status=1";
                printValue="25.1";
            } 
            search(query,payment,from);
            findTotal(totalQuery,payment,from);
        }
        if(chkPayment.isSelected()==true && chkFrom.isSelected()==true && chkTo.isSelected()==false && chkMonth.isSelected()==false && chkYear.isSelected()==true)
        {
            if(radioAll.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and fromAdd=? and invoiceYear=? order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where DATE_FORMAT(paymentDate,'%M %Y')=? and fromAdd=? and invoiceYear=?";
                printValue="26";
            }
            if(radioNotPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"',CASE WHEN DATEDIFF(paymentDate,CURDATE())>=0 THEN CONCAT(DATEDIFF(paymentDate,CURDATE()),' days left') ELSE CONCAT(ABS(DATEDIFF(paymentDate,CURDATE())),' days Due')END as STATUS from tbl_invoicedetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and fromAdd=? and invoiceYear=? and status=0 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where DATE_FORMAT(paymentDate,'%M %Y')=? and fromAdd=? and invoiceYear=? and status=0";
                printValue="26.0";
            }
            if(radioPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and fromAdd=? and invoiceYear=? and status=1 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where DATE_FORMAT(paymentDate,'%M %Y')=? and fromAdd=? and invoiceYear=? and status=1";
                printValue="26.1";
            } 
            search(query,payment,from,year);
            findTotal(totalQuery,payment,from,year);
        }
        if(chkPayment.isSelected()==true && chkFrom.isSelected()==true && chkTo.isSelected()==false && chkMonth.isSelected()==true && chkYear.isSelected()==false)
        {
            if(radioAll.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and fromAdd=? and invoiceMonth=? order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where DATE_FORMAT(paymentDate,'%M %Y')=? and fromAdd=? and invoiceMonth=?";
                printValue="27";
            }
            if(radioNotPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"',CASE WHEN DATEDIFF(paymentDate,CURDATE())>=0 THEN CONCAT(DATEDIFF(paymentDate,CURDATE()),' days left') ELSE CONCAT(ABS(DATEDIFF(paymentDate,CURDATE())),' days Due')END as STATUS from tbl_invoicedetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and fromAdd=? and invoiceMonth=? and status=0 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where DATE_FORMAT(paymentDate,'%M %Y')=? and fromAdd=? and invoiceMonth=? and status=0";
                printValue="27.0";
            }
            if(radioPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and fromAdd=? and invoiceMonth=? and status=1 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where DATE_FORMAT(paymentDate,'%M %Y')=? and fromAdd=? and invoiceMonth=? and status=1";
                printValue="27.1";
            } 
            search(query,payment,from,month);
            findTotal(totalQuery,payment,from,month);
        }
        if(chkPayment.isSelected()==true && chkFrom.isSelected()==true && chkTo.isSelected()==false && chkMonth.isSelected()==true && chkYear.isSelected()==true)
        {
            if(radioAll.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and fromAdd=? and invoiceMonth=? and invoiceYear=? order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where DATE_FORMAT(paymentDate,'%M %Y')=? and fromAdd=? and invoiceMonth=? and invoiceYear=?";
                printValue="28";
            }
            if(radioNotPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"',CASE WHEN DATEDIFF(paymentDate,CURDATE())>=0 THEN CONCAT(DATEDIFF(paymentDate,CURDATE()),' days left') ELSE CONCAT(ABS(DATEDIFF(paymentDate,CURDATE())),' days Due')END as STATUS from tbl_invoicedetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and fromAdd=? and invoiceMonth=? and invoiceYear=? and status=0 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where DATE_FORMAT(paymentDate,'%M %Y')=? and fromAdd=? and invoiceMonth=? and invoiceYear=? and status=0";
                printValue="28.0";
            }
            if(radioPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and fromAdd=? and invoiceMonth=? and invoiceYear=? and status=1 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where DATE_FORMAT(paymentDate,'%M %Y')=? and fromAdd=? and invoiceMonth=? and invoiceYear=? and status=1";
                printValue="28.1";
            } 
            search(query,payment,from,month,year);
            findTotal(totalQuery,payment,from,month,year);
        }
        if(chkPayment.isSelected()==true && chkFrom.isSelected()==true && chkTo.isSelected()==true && chkMonth.isSelected()==false && chkYear.isSelected()==false)
        {
            if(radioAll.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and fromAdd=? and toAdd=? order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where DATE_FORMAT(paymentDate,'%M %Y')=? and fromAdd=? and toAdd=?";
                printValue="29";
            }
            if(radioNotPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"',CASE WHEN DATEDIFF(paymentDate,CURDATE())>=0 THEN CONCAT(DATEDIFF(paymentDate,CURDATE()),' days left') ELSE CONCAT(ABS(DATEDIFF(paymentDate,CURDATE())),' days Due')END as STATUS from tbl_invoicedetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and fromAdd=? and toAdd=? and status=0 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where DATE_FORMAT(paymentDate,'%M %Y')=? and fromAdd=? and toAdd=? and status=0";
                printValue="29.0";
            }
            if(radioPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and fromAdd=? and toAdd=? and status=1 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where DATE_FORMAT(paymentDate,'%M %Y')=? and fromAdd=? and toAdd=? and status=1";
                printValue="29.1";
            } 
            search(query,payment,from,to);
            findTotal(totalQuery,payment,from,to);            
        }
        if(chkPayment.isSelected()==true && chkFrom.isSelected()==true && chkTo.isSelected()==true && chkMonth.isSelected()==false && chkYear.isSelected()==true)
        {
            if(radioAll.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',InvoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and fromAdd=? and toAdd=? and invoiceYear=? order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where DATE_FORMAT(paymentDate,'%M %Y')=? and fromAdd=? and toAdd=? and invoiceYear=?";
                printValue="30";
            }
            if(radioNotPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"',CASE WHEN DATEDIFF(paymentDate,CURDATE())>=0 THEN CONCAT(DATEDIFF(paymentDate,CURDATE()),' days left') ELSE CONCAT(ABS(DATEDIFF(paymentDate,CURDATE())),' days Due')END as STATUS from tbl_invoicedetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and fromAdd=? and toAdd=? and invoiceYear=? and status=0 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where DATE_FORMAT(paymentDate,'%M %Y')=? and fromAdd=? and toAdd=? and invoiceYear=? and status=0";
                printValue="30.0";
            }
            if(radioPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and fromAdd=? and toAdd=? and invoiceYear=? and status=1 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where DATE_FORMAT(paymentDate,'%M %Y')=? and fromAdd=? and toAdd=? and invoiceYear=? and status=1";
                printValue="30.1";
            } 
            search(query,payment,from,to,year);
            findTotal(totalQuery,payment,from,to,year);
        }
        if(chkPayment.isSelected()==true && chkFrom.isSelected()==true && chkTo.isSelected()==true && chkMonth.isSelected()==true && chkYear.isSelected()==false)
        {
            if(radioAll.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and fromAdd=? and toAdd=? and invoiceMonth=? order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where DATE_FORMAT(paymentDate,'%M %Y')=? and fromAdd=? and toAdd=? and invoiceMonth=?";
                printValue="31";
            }
            if(radioNotPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"',CASE WHEN DATEDIFF(paymentDate,CURDATE())>=0 THEN CONCAT(DATEDIFF(paymentDate,CURDATE()),' days left') ELSE CONCAT(ABS(DATEDIFF(paymentDate,CURDATE())),' days Due')END as STATUS from tbl_invoicedetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and fromAdd=? and toAdd=? and invoiceMonth=? and status=0 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where DATE_FORMAT(paymentDate,'%M %Y')=? and fromAdd=? and toAdd=? and invoiceMonth=? and status=0";
                printValue="31.0";
            }
            if(radioPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and fromAdd=? and toAdd=? and invoiceMonth=? and status=1 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where DATE_FORMAT(paymentDate,'%M %Y')=? and fromAdd=? and toAdd=? and invoiceMonth=? and status=1";
                printValue="31.1";
            } 
            search(query,payment,from,to,month);
            findTotal(totalQuery,payment,from,to,month);
        }
        if(chkPayment.isSelected()==true && chkFrom.isSelected()==true && chkTo.isSelected()==true && chkMonth.isSelected()==true && chkYear.isSelected()==true)
        {
            if(radioAll.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and fromAdd=? and toAdd=? and invoiceMonth=? and invoiceYear=? order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where DATE_FORMAT(paymentDate,'%M %Y')=? and fromAdd=? and toAdd=? and invoiceMonth=? and invoiceYear=?";
                printValue="32";
            }
            if(radioNotPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"',CASE WHEN DATEDIFF(paymentDate,CURDATE())>=0 THEN CONCAT(DATEDIFF(paymentDate,CURDATE()),' days left') ELSE CONCAT(ABS(DATEDIFF(paymentDate,CURDATE())),' days Due')END as STATUS from tbl_invoicedetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and fromAdd=? and toAdd=? and invoiceMonth=? and invoiceYear=? and status=0 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where DATE_FORMAT(paymentDate,'%M %Y')=? and fromAdd=? and toAdd=? and invoiceMonth=? and invoiceYear=? and status=0";
                printValue="32.0";
            }
            if(radioPaidView.isSelected()==true)
            {
                query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',invoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoicedetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and fromAdd=? and toAdd=? and invoiceMonth=? and invoiceYear=? and status=1 order by STR_TO_DATE(invoiceYear,'%Y')Desc,STR_TO_DATE(invoiceMonth,'%M')Desc";
                totalQuery="SELECT SUM(balance) FROM tbl_invoicedetails where DATE_FORMAT(paymentDate,'%M %Y')=? and fromAdd=? and toAdd=? and invoiceMonth=? and invoiceYear=? and status=1";
                printValue="32.1";
            } 
            search(query,payment,from,to,month,year);
            findTotal(totalQuery,payment,from,to,month,year);
        }
        
        
    }
    public void JtableClearSelection()
    {
        int row=jTable1.getSelectedRow();
        
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
//                jTable1.getColumnModel().getColumn(0).setMaxWidth(35);
//                jTable1.getColumnModel().getColumn(1).setMaxWidth(35);
//                jTable1.getColumnModel().getColumn(5).setMaxWidth(70);
//                jTable1.getColumnModel().getColumn(7).setMaxWidth(60);
//                jTable1.getColumnModel().getColumn(8).setMaxWidth(35);
//                jTable1.getColumnModel().getColumn(9).setMaxWidth(30);
//                jTable1.getColumnModel().getColumn(10).setMaxWidth(70);
//                jTable1.setShowHorizontalLines( true );
//                jTable1.setRowSelectionAllowed( true );
                jTable1.getColumnModel().getColumn(6).setCellRenderer(NumberRenderer.getIntegerRenderer());
                jTable1.getColumnModel().getColumn(12).setCellRenderer(NumberRenderer.getIntegerRenderer());            
                jTable1.getColumnModel().getColumn(5).setCellRenderer(NumberRenderer.getDateTimeRenderer());
                jTable1.getColumnModel().getColumn(10).setCellRenderer(NumberRenderer.getDateTimeRenderer());
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
//                jTable1.getColumnModel().getColumn(0).setMaxWidth(35);
//                jTable1.getColumnModel().getColumn(1).setMaxWidth(35);
//                jTable1.getColumnModel().getColumn(5).setMaxWidth(70);
//                jTable1.getColumnModel().getColumn(7).setMaxWidth(60);
//                jTable1.getColumnModel().getColumn(8).setMaxWidth(35);
//                jTable1.getColumnModel().getColumn(9).setMaxWidth(30);
//                jTable1.getColumnModel().getColumn(10).setMaxWidth(70);
//                jTable1.setShowHorizontalLines( true );
//                jTable1.setRowSelectionAllowed( true );
                jTable1.getColumnModel().getColumn(6).setCellRenderer(NumberRenderer.getIntegerRenderer());
                jTable1.getColumnModel().getColumn(12).setCellRenderer(NumberRenderer.getIntegerRenderer());            
                jTable1.getColumnModel().getColumn(5).setCellRenderer(NumberRenderer.getDateTimeRenderer());
                jTable1.getColumnModel().getColumn(10).setCellRenderer(NumberRenderer.getDateTimeRenderer());

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
//                jTable1.getColumnModel().getColumn(0).setMaxWidth(35);
//                jTable1.getColumnModel().getColumn(1).setMaxWidth(35);
//                jTable1.getColumnModel().getColumn(5).setMaxWidth(70);
//                jTable1.getColumnModel().getColumn(7).setMaxWidth(60);
//                jTable1.getColumnModel().getColumn(8).setMaxWidth(35);
//                jTable1.getColumnModel().getColumn(9).setMaxWidth(30);
//                jTable1.getColumnModel().getColumn(10).setMaxWidth(70);
//                jTable1.setShowHorizontalLines( true );
//                jTable1.setRowSelectionAllowed( true );
                jTable1.getColumnModel().getColumn(6).setCellRenderer(NumberRenderer.getIntegerRenderer());
                jTable1.getColumnModel().getColumn(12).setCellRenderer(NumberRenderer.getIntegerRenderer());            
                jTable1.getColumnModel().getColumn(5).setCellRenderer(NumberRenderer.getDateTimeRenderer());
                jTable1.getColumnModel().getColumn(10).setCellRenderer(NumberRenderer.getDateTimeRenderer());

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
//                jTable1.getColumnModel().getColumn(0).setMaxWidth(35);
//                jTable1.getColumnModel().getColumn(1).setMaxWidth(35);
//                jTable1.getColumnModel().getColumn(5).setMaxWidth(70);
//                jTable1.getColumnModel().getColumn(7).setMaxWidth(60);
//                jTable1.getColumnModel().getColumn(8).setMaxWidth(35);
//                jTable1.getColumnModel().getColumn(9).setMaxWidth(30);
//                jTable1.getColumnModel().getColumn(10).setMaxWidth(70);
//                jTable1.setShowHorizontalLines( true );
//                jTable1.setRowSelectionAllowed( true );
                jTable1.getColumnModel().getColumn(6).setCellRenderer(NumberRenderer.getIntegerRenderer());
                jTable1.getColumnModel().getColumn(12).setCellRenderer(NumberRenderer.getIntegerRenderer());            
                jTable1.getColumnModel().getColumn(5).setCellRenderer(NumberRenderer.getDateTimeRenderer());
                jTable1.getColumnModel().getColumn(10).setCellRenderer(NumberRenderer.getDateTimeRenderer());

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
//                jTable1.getColumnModel().getColumn(0).setMaxWidth(35);
//                jTable1.getColumnModel().getColumn(1).setMaxWidth(35);
//                jTable1.getColumnModel().getColumn(5).setMaxWidth(70);
//                jTable1.getColumnModel().getColumn(7).setMaxWidth(60);
//                jTable1.getColumnModel().getColumn(8).setMaxWidth(35);
//                jTable1.getColumnModel().getColumn(9).setMaxWidth(30);
//                jTable1.getColumnModel().getColumn(10).setMaxWidth(70);
//                jTable1.setShowHorizontalLines( true );
//                jTable1.setRowSelectionAllowed( true );
                jTable1.getColumnModel().getColumn(6).setCellRenderer(NumberRenderer.getIntegerRenderer());
                jTable1.getColumnModel().getColumn(12).setCellRenderer(NumberRenderer.getIntegerRenderer());            
                jTable1.getColumnModel().getColumn(5).setCellRenderer(NumberRenderer.getDateTimeRenderer());
                jTable1.getColumnModel().getColumn(10).setCellRenderer(NumberRenderer.getDateTimeRenderer());

            }
            catch(Exception e)
            {

            }      
    }
    public void search(String query,String field1,String field2,String field3,String field4,String field5)
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
                ps.setString(5, field5);
                ResultSet rs=ps.executeQuery();
                jTable1.setModel(DbUtils.resultSetToTableModel(rs));
                con.close();
//                jTable1.getColumnModel().getColumn(0).setMaxWidth(35);
//                jTable1.getColumnModel().getColumn(1).setMaxWidth(35);
//                jTable1.getColumnModel().getColumn(5).setMaxWidth(70);
//                jTable1.getColumnModel().getColumn(7).setMaxWidth(60);
//                jTable1.getColumnModel().getColumn(8).setMaxWidth(35);
//                jTable1.getColumnModel().getColumn(9).setMaxWidth(30);
//                jTable1.getColumnModel().getColumn(10).setMaxWidth(70);
//                jTable1.setShowHorizontalLines( true );
//                jTable1.setRowSelectionAllowed( true );
                jTable1.getColumnModel().getColumn(6).setCellRenderer(NumberRenderer.getIntegerRenderer());
                jTable1.getColumnModel().getColumn(12).setCellRenderer(NumberRenderer.getIntegerRenderer());            
                jTable1.getColumnModel().getColumn(5).setCellRenderer(NumberRenderer.getDateTimeRenderer());
                jTable1.getColumnModel().getColumn(10).setCellRenderer(NumberRenderer.getDateTimeRenderer());

            }
            catch(Exception e)
            {

            }      
    }
    public void findTotal(String query,String field1)
    {
        connection c=new connection();
        Connection con=c.conn();
            try
            {
                PreparedStatement ps=con.prepareStatement(query);
                ps.setString(1, field1);
                ResultSet rs=ps.executeQuery();
                while(rs.next())
                {
                    txtTotal.setText(rs.getString(1));
                }
                con.close();
            }
            catch(Exception e)
            {

            }      
    }
    public void findTotal(String query,String field1,String field2)
    {
        connection c=new connection();
        Connection con=c.conn();
            try
            {
                PreparedStatement ps=con.prepareStatement(query);
                ps.setString(1, field1);
                ps.setString(2, field2);
                ResultSet rs=ps.executeQuery();
                while(rs.next())
                {
                    txtTotal.setText(rs.getString(1));
                }
                con.close();
            }
            catch(Exception e)
            {

            }      
    }
    public void findTotal(String query)
    {
        connection c=new connection();
        Connection con=c.conn();
            try
            {
                PreparedStatement ps=con.prepareStatement(query);
                ResultSet rs=ps.executeQuery();
                while(rs.next())
                {
                    txtTotal.setText(rs.getString(1));
                }
                con.close();
            }
            catch(Exception e)
            {

            }      
    }
    public void findTotal(String query,String field1,String field2,String field3)
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
                while(rs.next())
                {
                    txtTotal.setText(rs.getString(1));
                }
                con.close();
            }
            catch(Exception e)
            {

            }      
    }
    public void findTotal(String query,String field1,String field2,String field3,String field4)
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
                while(rs.next())
                {
                    txtTotal.setText(rs.getString(1));
                }
                con.close();

            }
            catch(Exception e)
            {

            }      
    }
    public void findTotal(String query,String field1,String field2,String field3,String field4,String field5)
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
                ps.setString(5, field5);
                ResultSet rs=ps.executeQuery();
                while(rs.next())
                {
                    txtTotal.setText(rs.getString(1));
                }
                con.close();
                

            }
            catch(Exception e)
            {

            }      
    }
     public void cmbFromFill() {
        try {
            connection c = new connection();
            Connection con = c.conn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT fromAdd FROM tbl_invoicedetails order by fromAdd asc");
            while (rs.next()) {
                cmbFrom.addItem(rs.getString(1));
            }
            con.close();
        } catch (SQLException ex) {
            
        }
    }
     public void cmbToFill() {
        try {
            connection c = new connection();
            Connection con = c.conn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT toAdd FROM tbl_invoicedetails order by toAdd asc");
            while (rs.next()) 
            {
                cmbTo.addItem(rs.getString(1));
            }
            con.close();
        } catch (SQLException ex) {
            
        }
    }
     public void cmbPaymentFill() {
        try {
            connection c = new connection();
            Connection con = c.conn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT DATE_FORMAT(paymentDate,'%M %Y') FROM tbl_invoicedetails order by paymentDate");
            while (rs.next()) {
                cmbPayment.addItem(rs.getString(1));
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
        menuItemEdit = new javax.swing.JMenuItem();
        menuItemDelete = new javax.swing.JMenuItem();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        chkFrom = new javax.swing.JCheckBox();
        cmbFrom = new javax.swing.JComboBox();
        chkTo = new javax.swing.JCheckBox();
        cmbTo = new javax.swing.JComboBox();
        chkMonth = new javax.swing.JCheckBox();
        chkYear = new javax.swing.JCheckBox();
        cmbYear = new javax.swing.JComboBox();
        cmbMonth = new javax.swing.JComboBox();
        chkPayment = new javax.swing.JCheckBox();
        cmbPayment = new javax.swing.JComboBox();
        jToolBar1 = new javax.swing.JToolBar();
        radioAll = new javax.swing.JRadioButton();
        radioPaidView = new javax.swing.JRadioButton();
        radioNotPaidView = new javax.swing.JRadioButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jLabel3 = new javax.swing.JLabel();
        txtInvoiceId = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        btnPrint = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        txtTotal = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menuItemComplete = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        menuItemMonthCompany = new javax.swing.JMenuItem();
        menuItemMonthInvoice = new javax.swing.JMenuItem();

        menuItemEdit.setText("Edit");
        menuItemEdit.setToolTipText("");
        menuItemEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemEditActionPerformed(evt);
            }
        });
        jtablePopUp.add(menuItemEdit);

        menuItemDelete.setText("Delete");
        menuItemDelete.setToolTipText("");
        menuItemDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemDeleteActionPerformed(evt);
            }
        });
        jtablePopUp.add(menuItemDelete);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Invoice Search");

        chkFrom.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        chkFrom.setText("From");
        chkFrom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkFromActionPerformed(evt);
            }
        });

        cmbFrom.addItem("--select name--");
        cmbFrom.setSelectedItem("--select name--");
        cmbFrom.setEnabled(false);
        cmbFrom.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        cmbFrom.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbFromItemStateChanged(evt);
            }
        });
        cmbFrom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbFromActionPerformed(evt);
            }
        });

        chkTo.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        chkTo.setText("To");
        chkTo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkToActionPerformed(evt);
            }
        });

        cmbTo.addItem("--select name--");
        cmbTo.setSelectedItem("--select name--");
        cmbTo.setEnabled(false);
        cmbTo.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        cmbTo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbToActionPerformed(evt);
            }
        });

        chkMonth.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        chkMonth.setText("Month");
        chkMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMonthActionPerformed(evt);
            }
        });

        chkYear.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        chkYear.setText("Year");
        chkYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkYearActionPerformed(evt);
            }
        });

        cmbYear.addItem("--select year--");
        cmbYear.setSelectedItem("--select year--");
        cmbYear.setEnabled(false);
        cmbYear.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        cmbYear.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "--select year--", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "" }));
        cmbYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbYearActionPerformed(evt);
            }
        });

        cmbMonth.setEnabled(false);
        cmbMonth.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        cmbMonth.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "--select month--", "January", "February", "March", "april", "May", "June", "July", "August", "September", "October", "November", "December", "" }));
        cmbMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbMonthActionPerformed(evt);
            }
        });

        chkPayment.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        chkPayment.setText("Payment");
        chkPayment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkPaymentActionPerformed(evt);
            }
        });

        cmbPayment.addItem("--select month & year--");
        cmbPayment.setSelectedItem("--select month & year--");
        cmbPayment.setEnabled(false);
        cmbPayment.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        cmbPayment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPaymentActionPerformed(evt);
            }
        });

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        radioAll.setSelected(true);
        buttonGroup1.add(radioAll);
        radioAll.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        radioAll.setText("All");
        radioAll.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
        jToolBar1.add(radioAll);

        buttonGroup1.add(radioPaidView);
        radioPaidView.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        radioPaidView.setText("Received View");
        radioPaidView.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
        jToolBar1.add(radioPaidView);

        buttonGroup1.add(radioNotPaidView);
        radioNotPaidView.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        radioNotPaidView.setText("Not Received View");
        radioNotPaidView.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
        jToolBar1.add(radioNotPaidView);
        jToolBar1.add(jSeparator1);

        jLabel3.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel3.setText("Invoice Id");
        jToolBar1.add(jLabel3);
        jToolBar1.add(txtInvoiceId);

        jButton1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jButton1.setText("Search");
        jButton1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        btnPrint.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnPrint.setText("Print");
        btnPrint.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnPrint.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });
        jToolBar1.add(btnPrint);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chkFrom)
                            .addComponent(chkMonth))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(cmbMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(chkYear)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbYear, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cmbFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chkPayment)
                            .addComponent(chkTo))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cmbTo, 0, 412, Short.MAX_VALUE)
                            .addComponent(cmbPayment, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 610, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(99, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkFrom)
                    .addComponent(chkTo)
                    .addComponent(cmbTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkMonth)
                    .addComponent(chkYear)
                    .addComponent(cmbYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkPayment)
                    .addComponent(cmbPayment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTable1.setFont(new java.awt.Font("Century Gothic", 0, 10)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jTable1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTable1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTable1FocusLost(evt);
            }
        });
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable1KeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        txtTotal.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N

        jMenu1.setText("Graphical Reports");
        jMenu1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenu1.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N

        menuItemComplete.setText("Invoice Complete");
        menuItemComplete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemCompleteActionPerformed(evt);
            }
        });
        jMenu1.add(menuItemComplete);

        jMenu2.setText("Comparison");

        menuItemMonthCompany.setText("Month/Company");
        menuItemMonthCompany.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemMonthCompanyActionPerformed(evt);
            }
        });
        jMenu2.add(menuItemMonthCompany);

        menuItemMonthInvoice.setText("Month/Invoice");
        menuItemMonthInvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemMonthInvoiceActionPerformed(evt);
            }
        });
        jMenu2.add(menuItemMonthInvoice);

        jMenu1.add(jMenu2);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void chkFromActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkFromActionPerformed
        // TODO add your handling code here:
        if(chkFrom.isSelected()==false)
        {
            cmbFrom.setSelectedItem("--Select Name--");
            cmbFrom.setEnabled(false);
        }
        else
        {
            cmbFrom.setEnabled(true);
            cmbFrom.setEditable(true);
            AutoCompleteDecorator.decorate(cmbFrom);
        }
    }//GEN-LAST:event_chkFromActionPerformed

    private void chkMonthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMonthActionPerformed
        // TODO add your handling code here:
        if(chkMonth.isSelected()==false)
        {
            cmbMonth.setSelectedItem("--Select month--");
            cmbMonth.setEnabled(false);
        }
        else
        {
            cmbMonth.setEnabled(true);
            cmbMonth.setEditable(true);
            AutoCompleteDecorator.decorate(cmbMonth);
        }
    }//GEN-LAST:event_chkMonthActionPerformed

    private void chkToActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkToActionPerformed
        // TODO add your handling code here:
        if(chkTo.isSelected()==false)
        {
            cmbTo.setSelectedItem("--Select Name--");
            cmbTo.setEnabled(false);
        }
        else
        {
            cmbTo.setEnabled(true);
            cmbTo.setEditable(true);
            AutoCompleteDecorator.decorate(cmbTo);
        }
    }//GEN-LAST:event_chkToActionPerformed

    private void chkYearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkYearActionPerformed
        // TODO add your handling code here:
        if(chkYear.isSelected()==false)
        {
            cmbYear.setSelectedItem("--Select year--");
            cmbYear.setEnabled(false);
        }
        else
        {
            cmbYear.setEnabled(true);
            cmbYear.setEditable(true);
            AutoCompleteDecorator.decorate(cmbYear);
        }
    }//GEN-LAST:event_chkYearActionPerformed

    private void cmbFromActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbFromActionPerformed
        // TODO add your handling code here:
        queryGenerator();

    }//GEN-LAST:event_cmbFromActionPerformed

    private void cmbFromItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbFromItemStateChanged
        // TODO add your handling code here:
        queryGenerator();
    }//GEN-LAST:event_cmbFromItemStateChanged

    private void cmbMonthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbMonthActionPerformed
        // TODO add your handling code here:
        queryGenerator();
    }//GEN-LAST:event_cmbMonthActionPerformed

    private void cmbToActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbToActionPerformed
        // TODO add your handling code here:
        queryGenerator();
    }//GEN-LAST:event_cmbToActionPerformed

    private void cmbYearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbYearActionPerformed
        // TODO add your handling code here:
        queryGenerator();
    }//GEN-LAST:event_cmbYearActionPerformed

    private void chkPaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkPaymentActionPerformed
        // TODO add your handling code here:
        if(chkPayment.isSelected()==false)
        {
            cmbPayment.setSelectedItem("--select month & year--");
            cmbPayment.setEnabled(false);
        }
        else
        {
            cmbPayment.setEnabled(true);
            cmbPayment.setEditable(true);
            AutoCompleteDecorator.decorate(cmbPayment);
        }
    }//GEN-LAST:event_chkPaymentActionPerformed

    private void cmbPaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPaymentActionPerformed
        // TODO add your handling code here:
        queryGenerator();
    }//GEN-LAST:event_cmbPaymentActionPerformed

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

    private void radioPaidViewItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_radioPaidViewItemStateChanged
        // TODO add your handling code here:
        queryGenerator();

    }//GEN-LAST:event_radioPaidViewItemStateChanged

    private void radioPaidViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioPaidViewActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radioPaidViewActionPerformed

    private void radioNotPaidViewItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_radioNotPaidViewItemStateChanged
        // TODO add your handling code here:
        queryGenerator();
    }//GEN-LAST:event_radioNotPaidViewItemStateChanged

    private void radioNotPaidViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioNotPaidViewActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radioNotPaidViewActionPerformed

    private void radioAllItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_radioAllItemStateChanged
        // TODO add your handling code here:
        queryGenerator();
    }//GEN-LAST:event_radioAllItemStateChanged

    private void radioAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioAllActionPerformed

    }//GEN-LAST:event_radioAllActionPerformed

    private void menuItemMonthCompanyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemMonthCompanyActionPerformed
        // TODO add your handling code here:
        ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\Graphical\\Comparison(month-company).jasper"));
        re.setVisible(true);
    }//GEN-LAST:event_menuItemMonthCompanyActionPerformed

    private void menuItemCompleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemCompleteActionPerformed
        // TODO add your handling code here:
        ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\Graphical\\All_G.jasper"));
        re.setVisible(true);
    }//GEN-LAST:event_menuItemCompleteActionPerformed

    private void menuItemMonthInvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemMonthInvoiceActionPerformed
        // TODO add your handling code here:
        ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\Graphical\\Comparison(month-Invoice).jasper"));
        re.setVisible(true);
    }//GEN-LAST:event_menuItemMonthInvoiceActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void menuItemEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemEditActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuItemEditActionPerformed

    private void menuItemDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemDeleteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuItemDeleteActionPerformed

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
        // TODO add your handling code here:
         int keyCode = evt.getKeyCode();
        if(keyCode == KeyEvent.VK_ENTER)
        {
            InvoiceEntry  IE=new InvoiceEntry(invoiceId);
            AnarTrading.desktopPane1.add(IE);
            IE.setVisible(true);
            IE.show();
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
                    int i=stmt1.executeUpdate("delete from tbl_invoicedetails  where invoiceId="+invoiceId);
                    if(i!=0)
                    {
                        dispose();
                        ViewAdvancedInvoiceSearchForm();
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

    private void jTable1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTable1FocusGained
        // TODO add your handling code here:
        queryGenerator();
    }//GEN-LAST:event_jTable1FocusGained

    private void jTable1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTable1FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1FocusLost

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        // TODO add your handling code here:
          
        if(printValue.equals("1"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_All.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("2"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("invoiceYear",cmbYear.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_All.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("3"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("invoiceMonth",cmbMonth.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_All.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("4"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("invoiceYear",cmbYear.getSelectedItem().toString());
            para.put("invoiceMonth",cmbMonth.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_All.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("5"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("toAdd",cmbTo.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_All.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("6"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("invoiceYear",cmbYear.getSelectedItem().toString());
            para.put("toAdd",cmbTo.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_All.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("7"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
             para.put("invoiceMonth",cmbMonth.getSelectedItem().toString());
            para.put("toAdd",cmbTo.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_All.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("8"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("invoiceYear",cmbYear.getSelectedItem().toString());
            para.put("invoiceMonth",cmbMonth.getSelectedItem().toString());
            para.put("toAdd",cmbTo.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_All.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("9"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("fromAdd",cmbFrom.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_All.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("10"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("invoiceYear",cmbYear.getSelectedItem().toString());
            para.put("fromAdd",cmbFrom.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_All.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("11"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("invoiceMonth",cmbMonth.getSelectedItem().toString());
            para.put("fromAdd",cmbFrom.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_All.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("12"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("invoiceYear",cmbYear.getSelectedItem().toString());
            para.put("invoiceMonth",cmbMonth.getSelectedItem().toString());
            para.put("fromAdd",cmbFrom.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_All.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("13"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("toAdd",cmbTo.getSelectedItem().toString());
            para.put("fromAdd",cmbFrom.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_All.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("14"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("invoiceYear",cmbYear.getSelectedItem().toString());
            para.put("toAdd",cmbTo.getSelectedItem().toString());
            para.put("fromAdd",cmbFrom.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_All.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("15"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("invoiceMonth",cmbMonth.getSelectedItem().toString());
            para.put("toAdd",cmbTo.getSelectedItem().toString());
            para.put("fromAdd",cmbFrom.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_All.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("16"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("invoiceYear",cmbYear.getSelectedItem().toString());
            para.put("invoiceMonth",cmbMonth.getSelectedItem().toString());
            para.put("toAdd",cmbTo.getSelectedItem().toString());
            para.put("fromAdd",cmbFrom.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_All.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("17"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("pDate",cmbPayment.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_All.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("18"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("invoiceYear",cmbYear.getSelectedItem().toString());
            para.put("pDate",cmbPayment.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_All.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("19"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("invoiceMonth",cmbMonth.getSelectedItem().toString());
            para.put("pDate",cmbPayment.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_All.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("20"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("invoiceYear",cmbYear.getSelectedItem().toString());
            para.put("invoiceMonth",cmbMonth.getSelectedItem().toString());
            para.put("pDate",cmbPayment.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_All.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("21"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("toAdd",cmbTo.getSelectedItem().toString());
            para.put("pDate",cmbPayment.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_All.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("22"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("invoiceYear",cmbYear.getSelectedItem().toString());
            para.put("toAdd",cmbTo.getSelectedItem().toString());
            para.put("pDate",cmbPayment.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_All.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("23"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("invoiceMonth",cmbMonth.getSelectedItem().toString());
            para.put("toAdd",cmbTo.getSelectedItem().toString());
            para.put("pDate",cmbPayment.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_All.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("24"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("invoiceYear",cmbYear.getSelectedItem().toString());
            para.put("invoiceMonth",cmbMonth.getSelectedItem().toString());
            para.put("toAdd",cmbTo.getSelectedItem().toString());
            para.put("pDate",cmbPayment.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_All.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("25"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("fromAdd",cmbFrom.getSelectedItem().toString());
            para.put("pDate",cmbPayment.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_All.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("26"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("invoiceYear",cmbYear.getSelectedItem().toString());
            para.put("fromAdd",cmbFrom.getSelectedItem().toString());
            para.put("pDate",cmbPayment.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_All.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("27"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("invoiceMonth",cmbMonth.getSelectedItem().toString());
            para.put("fromAdd",cmbFrom.getSelectedItem().toString());
            para.put("pDate",cmbPayment.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_All.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("28"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("invoiceYear",cmbYear.getSelectedItem().toString());
            para.put("invoiceMonth",cmbMonth.getSelectedItem().toString());
            para.put("fromAdd",cmbFrom.getSelectedItem().toString());
            para.put("pDate",cmbPayment.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_All.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("29"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("toAdd",cmbTo.getSelectedItem().toString());
            para.put("fromAdd",cmbFrom.getSelectedItem().toString());
            para.put("pDate",cmbPayment.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_All.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("30"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("invoiceYear",cmbYear.getSelectedItem().toString());
            para.put("toAdd",cmbTo.getSelectedItem().toString());
            para.put("fromAdd",cmbFrom.getSelectedItem().toString());
            para.put("pDate",cmbPayment.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_All.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("31"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("invoiceMonth",cmbMonth.getSelectedItem().toString());
            para.put("toAdd",cmbTo.getSelectedItem().toString());
            para.put("fromAdd",cmbFrom.getSelectedItem().toString());
            para.put("pDate",cmbPayment.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_All.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("32"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("invoiceYear",cmbYear.getSelectedItem().toString());
            para.put("invoiceMonth",cmbMonth.getSelectedItem().toString());
            para.put("toAdd",cmbTo.getSelectedItem().toString());
            para.put("fromAdd",cmbFrom.getSelectedItem().toString());
            para.put("pDate",cmbPayment.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_All.jasper"),para);
            re.setVisible(true);
        }
         if(printValue.equals("1.1"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_Received.jasper"),para);
            re.setVisible(true);
        }
         if(printValue.equals("2.1"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("invoiceYear",cmbYear.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_Received.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("3.1"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("invoiceMonth",cmbMonth.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_Received.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("4.1"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("invoiceYear",cmbYear.getSelectedItem().toString());
            para.put("invoiceMonth",cmbMonth.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_Received.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("5.1"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("toAdd",cmbTo.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_Received.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("6.1"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("invoiceYear",cmbYear.getSelectedItem().toString());
            para.put("toAdd",cmbTo.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_Received.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("7.1"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("invoiceMonth",cmbMonth.getSelectedItem().toString());
            para.put("toAdd",cmbTo.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_Received.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("8.1"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("invoiceYear",cmbYear.getSelectedItem().toString());
            para.put("invoiceMonth",cmbMonth.getSelectedItem().toString());
            para.put("toAdd",cmbTo.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_Received.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("9.1"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("fromAdd",cmbFrom.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_Received.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("10.1"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("invoiceYear",cmbYear.getSelectedItem().toString());
            para.put("fromAdd",cmbFrom.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_Received.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("11.1"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("invoiceMonth",cmbMonth.getSelectedItem().toString());
            para.put("fromAdd",cmbFrom.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_Received.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("12.1"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("invoiceYear",cmbYear.getSelectedItem().toString());
            para.put("invoiceMonth",cmbMonth.getSelectedItem().toString());
            para.put("fromAdd",cmbFrom.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_Received.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("13.1"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("toAdd",cmbTo.getSelectedItem().toString());
            para.put("fromAdd",cmbFrom.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_Received.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("14.1"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("invoiceYear",cmbYear.getSelectedItem().toString());
            para.put("toAdd",cmbTo.getSelectedItem().toString());
            para.put("fromAdd",cmbFrom.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_Received.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("15.1"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("invoiceMonth",cmbMonth.getSelectedItem().toString());
            para.put("toAdd",cmbTo.getSelectedItem().toString());
            para.put("fromAdd",cmbFrom.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_Received.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("16.1"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("invoiceYear",cmbYear.getSelectedItem().toString());
            para.put("invoiceMonth",cmbMonth.getSelectedItem().toString());
            para.put("toAdd",cmbTo.getSelectedItem().toString());
            para.put("fromAdd",cmbFrom.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_Received.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("17.1"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("pDate",cmbPayment.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_Received.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("18.1"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("invoiceYear",cmbYear.getSelectedItem().toString());
            para.put("pDate",cmbPayment.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_Received.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("19.1"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("invoiceMonth",cmbMonth.getSelectedItem().toString());
            para.put("pDate",cmbPayment.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_Received.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("20.1"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("invoiceYear",cmbYear.getSelectedItem().toString());
            para.put("invoiceMonth",cmbMonth.getSelectedItem().toString());
            para.put("pDate",cmbPayment.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_Received.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("21.1"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("toAdd",cmbTo.getSelectedItem().toString());
            para.put("pDate",cmbPayment.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_Received.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("22.1"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("invoiceYear",cmbYear.getSelectedItem().toString());
            para.put("toAdd",cmbTo.getSelectedItem().toString());
            para.put("pDate",cmbPayment.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_Received.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("23.1"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("invoiceMonth",cmbMonth.getSelectedItem().toString());
            para.put("toAdd",cmbTo.getSelectedItem().toString());
            para.put("pDate",cmbPayment.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_Received.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("24.1"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("invoiceYear",cmbYear.getSelectedItem().toString());
            para.put("invoiceMonth",cmbMonth.getSelectedItem().toString());
            para.put("toAdd",cmbTo.getSelectedItem().toString());
            para.put("pDate",cmbPayment.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_Received.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("25.1"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("fromAdd",cmbFrom.getSelectedItem().toString());
            para.put("pDate",cmbPayment.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_Received.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("26.1"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("invoiceYear",cmbYear.getSelectedItem().toString());
            para.put("fromAdd",cmbFrom.getSelectedItem().toString());
            para.put("pDate",cmbPayment.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_Received.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("27.1"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("invoiceMonth",cmbMonth.getSelectedItem().toString());
            para.put("fromAdd",cmbFrom.getSelectedItem().toString());
            para.put("pDate",cmbPayment.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_Received.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("28.1"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("invoiceYear",cmbYear.getSelectedItem().toString());
            para.put("invoiceMonth",cmbMonth.getSelectedItem().toString());
            para.put("fromAdd",cmbFrom.getSelectedItem().toString());
            para.put("pDate",cmbPayment.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_Received.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("29.1"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("toAdd",cmbTo.getSelectedItem().toString());
            para.put("fromAdd",cmbFrom.getSelectedItem().toString());
            para.put("pDate",cmbPayment.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_Received.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("30.1"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("invoiceYear",cmbYear.getSelectedItem().toString());
            para.put("toAdd",cmbTo.getSelectedItem().toString());
            para.put("fromAdd",cmbFrom.getSelectedItem().toString());
            para.put("pDate",cmbPayment.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_Received.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("31.1"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("invoiceMonth",cmbMonth.getSelectedItem().toString());
            para.put("toAdd",cmbTo.getSelectedItem().toString());
            para.put("fromAdd",cmbFrom.getSelectedItem().toString());
            para.put("pDate",cmbPayment.getSelectedItem().toString());
           ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_Received.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("32.1"))
        {
            HashMap para=new HashMap();
            para.put("InputValue",printValue);
            para.put("invoiceYear",cmbYear.getSelectedItem().toString());
            para.put("invoiceMonth",cmbMonth.getSelectedItem().toString());
            para.put("toAdd",cmbTo.getSelectedItem().toString());
            para.put("fromAdd",cmbFrom.getSelectedItem().toString());
            para.put("pDate",cmbPayment.getSelectedItem().toString());
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\Invoice\\Invoice_send\\InvoiceSend_Received.jasper"),para);
            re.setVisible(true);
        }
        
    }//GEN-LAST:event_btnPrintActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPrint;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox chkFrom;
    private javax.swing.JCheckBox chkMonth;
    private javax.swing.JCheckBox chkPayment;
    private javax.swing.JCheckBox chkTo;
    private javax.swing.JCheckBox chkYear;
    private javax.swing.JComboBox cmbFrom;
    private javax.swing.JComboBox cmbMonth;
    private javax.swing.JComboBox cmbPayment;
    private javax.swing.JComboBox cmbTo;
    private javax.swing.JComboBox cmbYear;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JPopupMenu jtablePopUp;
    private javax.swing.JMenuItem menuItemComplete;
    private javax.swing.JMenuItem menuItemDelete;
    private javax.swing.JMenuItem menuItemEdit;
    private javax.swing.JMenuItem menuItemMonthCompany;
    private javax.swing.JMenuItem menuItemMonthInvoice;
    private javax.swing.JRadioButton radioAll;
    private javax.swing.JRadioButton radioNotPaidView;
    private javax.swing.JRadioButton radioPaidView;
    private javax.swing.JTextField txtInvoiceId;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
    Point middle = new Point(0,0);
    int invoiceId;
    String printValue,path;
}
