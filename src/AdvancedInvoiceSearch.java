
import java.awt.Point;
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
    }
    public void queryGenerator()
    {
        String query="";
        String payment=cmbPayment.getSelectedItem().toString();
        String from=cmbFrom.getSelectedItem().toString();
        String to=cmbTo.getSelectedItem().toString();
        String month=cmbMonth.getSelectedItem().toString();
        String year=cmbYear.getSelectedItem().toString();
        
        
        if(chkPayment.isSelected()==false && chkFrom.isSelected()==false && chkTo.isSelected()==false && chkMonth.isSelected()==false && chkYear.isSelected()==false)
        {
            query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',InvoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoiceDetails,(SELECT @i := 0) temp where status=0";
            search(query);
        }
        if(chkPayment.isSelected()==false && chkFrom.isSelected()==false && chkTo.isSelected()==false && chkMonth.isSelected()==false && chkYear.isSelected()==true)
        {
            query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',InvoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoiceDetails,(SELECT @i := 0) temp where invoiceYear=? and status=0";
            search(query,year);
        }
        if(chkPayment.isSelected()==false && chkFrom.isSelected()==false && chkTo.isSelected()==false && chkMonth.isSelected()==true && chkYear.isSelected()==false)
        {
            query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',InvoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoiceDetails,(SELECT @i := 0) temp where InvoiceMonth=? and status=0";
            search(query,month);
        }
        if(chkPayment.isSelected()==false && chkFrom.isSelected()==false && chkTo.isSelected()==false && chkMonth.isSelected()==true && chkYear.isSelected()==true)
        {
            query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',InvoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoiceDetails,(SELECT @i := 0) temp where InvoiceMonth=? and invoiceYear=? and status=0";
            search(query,month,year);
        }
        if(chkPayment.isSelected()==false && chkFrom.isSelected()==false && chkTo.isSelected()==true && chkMonth.isSelected()==false && chkYear.isSelected()==false)
        {
            query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',InvoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoiceDetails,(SELECT @i := 0) temp where toAdd=? and status=0";
            search(query,to);
        }
        if(chkPayment.isSelected()==false && chkFrom.isSelected()==false && chkTo.isSelected()==true && chkMonth.isSelected()==false && chkYear.isSelected()==true)
        {
            query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',InvoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoiceDetails,(SELECT @i := 0) temp where toAdd=? and invoiceYear=? and status=0";
            search(query,to,year);
        }
        if(chkPayment.isSelected()==false && chkFrom.isSelected()==false && chkTo.isSelected()==true && chkMonth.isSelected()==true && chkYear.isSelected()==false)
        {
            query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',InvoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoiceDetails,(SELECT @i := 0) temp where toAdd=? and InvoiceMonth=? and status=0";
            search(query,to,month);
        }
        if(chkPayment.isSelected()==false && chkFrom.isSelected()==false && chkTo.isSelected()==true && chkMonth.isSelected()==true && chkYear.isSelected()==true)
        {
            query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',InvoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoiceDetails,(SELECT @i := 0) temp where toAdd=? and InvoiceMonth=? and invoiceYear=? and status=0";
            search(query,to,month,year);
        }
        
        if(chkPayment.isSelected()==false && chkFrom.isSelected()==true && chkTo.isSelected()==false && chkMonth.isSelected()==false && chkYear.isSelected()==false)
        {
            query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',InvoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoiceDetails,(SELECT @i := 0) temp where fromAdd=? and status=0";
            search(query,from);
        }
        if(chkPayment.isSelected()==false && chkFrom.isSelected()==true && chkTo.isSelected()==false && chkMonth.isSelected()==false && chkYear.isSelected()==true)
        {
            query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',InvoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoiceDetails,(SELECT @i := 0) temp where fromAdd=? and invoiceYear=? and status=0";
            search(query,from,year);
        }
        if(chkPayment.isSelected()==false && chkFrom.isSelected()==true && chkTo.isSelected()==false && chkMonth.isSelected()==true && chkYear.isSelected()==false)
        {
            query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',InvoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoiceDetails,(SELECT @i := 0) temp where fromAdd=? and InvoiceMonth=? and status=0";
            search(query,from,month);
        }
        if(chkPayment.isSelected()==false && chkFrom.isSelected()==true && chkTo.isSelected()==false && chkMonth.isSelected()==true && chkYear.isSelected()==true)
        {
            query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',InvoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoiceDetails,(SELECT @i := 0) temp where fromAdd=? and InvoiceMonth=? and invoiceYear=? and status=0";
            search(query,from,month,year);
        }
        if(chkPayment.isSelected()==false && chkFrom.isSelected()==true && chkTo.isSelected()==true && chkMonth.isSelected()==false && chkYear.isSelected()==false)
        {
            query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',InvoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoiceDetails,(SELECT @i := 0) temp where fromAdd=? and toAdd=? and status=0";
            search(query,from,to);
        }
        if(chkPayment.isSelected()==false && chkFrom.isSelected()==true && chkTo.isSelected()==true && chkMonth.isSelected()==false && chkYear.isSelected()==true)
        {
            query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',InvoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoiceDetails,(SELECT @i := 0) temp where fromAdd=? and toAdd=? and invoiceYear=? and status=0";
            search(query,from,to,year);
        }
        if(chkPayment.isSelected()==false && chkFrom.isSelected()==true && chkTo.isSelected()==true && chkMonth.isSelected()==true && chkYear.isSelected()==false)
        {
            query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',InvoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoiceDetails,(SELECT @i := 0) temp where fromAdd=? and toAdd=? and InvoiceMonth=? and status=0";
            search(query,from,to,month);
        }
        if(chkPayment.isSelected()==false && chkFrom.isSelected()==true && chkTo.isSelected()==true && chkMonth.isSelected()==true && chkYear.isSelected()==true)
        {
            query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',InvoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoiceDetails,(SELECT @i := 0) temp where fromAdd=? and toAdd=? and InvoiceMonth=? and invoiceYear=? and status=0";
            search(query,from,to,month,year);
        }
        
        
        if(chkPayment.isSelected()==true && chkFrom.isSelected()==false && chkTo.isSelected()==false && chkMonth.isSelected()==false && chkYear.isSelected()==false)
        {
            query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',InvoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoiceDetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and status=0";
            search(query,payment);
        }
        if(chkPayment.isSelected()==true && chkFrom.isSelected()==false && chkTo.isSelected()==false && chkMonth.isSelected()==false && chkYear.isSelected()==true)
        {
            query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',InvoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoiceDetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and invoiceYear=? and status=0";
            search(query,payment,year);
        }
        if(chkPayment.isSelected()==true && chkFrom.isSelected()==false && chkTo.isSelected()==false && chkMonth.isSelected()==true && chkYear.isSelected()==false)
        {
            query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',InvoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoiceDetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and InvoiceMonth=? and status=0";
            search(query,payment,month);
        }
        if(chkPayment.isSelected()==true && chkFrom.isSelected()==false && chkTo.isSelected()==false && chkMonth.isSelected()==true && chkYear.isSelected()==true)
        {
            query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',InvoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoiceDetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and InvoiceMonth=? and invoiceYear=? and status=0";
            search(query,payment,month,year);
        }
        if(chkPayment.isSelected()==true && chkFrom.isSelected()==false && chkTo.isSelected()==true && chkMonth.isSelected()==false && chkYear.isSelected()==false)
        {
            query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',InvoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoiceDetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and toAdd=? and status=0";
            search(query,payment,to);
        }
        if(chkPayment.isSelected()==true && chkFrom.isSelected()==false && chkTo.isSelected()==true && chkMonth.isSelected()==false && chkYear.isSelected()==true)
        {
            query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',InvoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoiceDetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and toAdd=? and invoiceYear=? and status=0";
            search(query,payment,to,year);
        }
        if(chkPayment.isSelected()==true && chkFrom.isSelected()==false && chkTo.isSelected()==true && chkMonth.isSelected()==true && chkYear.isSelected()==false)
        {
            query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',InvoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoiceDetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and toAdd=? and InvoiceMonth=? and status=0";
            search(query,payment,to,month);
        }
        if(chkPayment.isSelected()==true && chkFrom.isSelected()==false && chkTo.isSelected()==true && chkMonth.isSelected()==true && chkYear.isSelected()==true)
        {
            query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',InvoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoiceDetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and toAdd=? and InvoiceMonth=? and invoiceYear=? and status=0";
            search(query,payment,to,month,year);
        }
        
        if(chkPayment.isSelected()==true && chkFrom.isSelected()==true && chkTo.isSelected()==false && chkMonth.isSelected()==false && chkYear.isSelected()==false)
        {
            query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',InvoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoiceDetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and fromAdd=? and status=0";
            search(query,payment,from);
        }
        if(chkPayment.isSelected()==true && chkFrom.isSelected()==true && chkTo.isSelected()==false && chkMonth.isSelected()==false && chkYear.isSelected()==true)
        {
            query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',InvoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoiceDetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and fromAdd=? and invoiceYear=? and status=0";
            search(query,payment,from,year);
        }
        if(chkPayment.isSelected()==true && chkFrom.isSelected()==true && chkTo.isSelected()==false && chkMonth.isSelected()==true && chkYear.isSelected()==false)
        {
            query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',InvoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoiceDetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and fromAdd=? and InvoiceMonth=? and status=0";
            search(query,payment,from,month);
        }
        if(chkPayment.isSelected()==true && chkFrom.isSelected()==true && chkTo.isSelected()==false && chkMonth.isSelected()==true && chkYear.isSelected()==true)
        {
            query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',InvoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoiceDetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and fromAdd=? and InvoiceMonth=? and invoiceYear=? and status=0";
            search(query,payment,from,month,year);
        }
        if(chkPayment.isSelected()==true && chkFrom.isSelected()==true && chkTo.isSelected()==true && chkMonth.isSelected()==false && chkYear.isSelected()==false)
        {
            query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',InvoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoiceDetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and fromAdd=? and toAdd=? and status=0";
            search(query,payment,from,to);
        }
        if(chkPayment.isSelected()==true && chkFrom.isSelected()==true && chkTo.isSelected()==true && chkMonth.isSelected()==false && chkYear.isSelected()==true)
        {
            query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',InvoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoiceDetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and fromAdd=? and toAdd=? and invoiceYear=? and status=0";
            search(query,payment,from,to,year);
        }
        if(chkPayment.isSelected()==true && chkFrom.isSelected()==true && chkTo.isSelected()==true && chkMonth.isSelected()==true && chkYear.isSelected()==false)
        {
            query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',InvoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoiceDetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and fromAdd=? and toAdd=? and InvoiceMonth=? and status=0";
            search(query,payment,from,to,month);
        }
        if(chkPayment.isSelected()==true && chkFrom.isSelected()==true && chkTo.isSelected()==true && chkMonth.isSelected()==true && chkYear.isSelected()==true)
        {
            query="select @i := @i + 1 '"+"SL.NO"+"',invoiceId '"+"ID"+"',fromAdd'"+"FROM"+"',toAdd '"+"TO"+"',invoiceNumber'"+"INVOICE#"+"',invoiceDate '"+"INVOICE DATE"+"',amount'"+"AMOUNT"+"',InvoiceMonth'"+"MONTH"+"',invoiceYear '"+"YEAR"+"',terms'"+"TERMS"+"',paymentDate'"+"PAY DATE"+"',remark '"+"REMARK"+"',balance '"+"BALANCE"+"' from tbl_invoiceDetails,(SELECT @i := 0) temp where DATE_FORMAT(paymentDate,'%M %Y')=? and fromAdd=? and toAdd=? and InvoiceMonth=? and invoiceYear=? and status=0";
            search(query,payment,from,to,month,year);
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
        menuItemDocument = new javax.swing.JMenuItem();
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        menuItemDocument.setText("Add Document");
        menuItemDocument.setToolTipText("");
        menuItemDocument.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemDocumentActionPerformed(evt);
            }
        });
        jtablePopUp.add(menuItemDocument);

        setTitle("Invoice Search");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search Critiria", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Gabriola", 0, 18))); // NOI18N

        chkFrom.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        chkFrom.setText("From");
        chkFrom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkFromActionPerformed(evt);
            }
        });

        cmbFrom.addItem("--select name--");
        cmbFrom.setSelectedItem("--select name--");
        cmbFrom.setEnabled(false);
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

        chkTo.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        chkTo.setText("To");
        chkTo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkToActionPerformed(evt);
            }
        });

        cmbTo.addItem("--select name--");
        cmbTo.setSelectedItem("--select name--");
        cmbTo.setEnabled(false);
        cmbTo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbToActionPerformed(evt);
            }
        });

        chkMonth.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        chkMonth.setText("Month");
        chkMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMonthActionPerformed(evt);
            }
        });

        chkYear.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        chkYear.setText("Year");
        chkYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkYearActionPerformed(evt);
            }
        });

        cmbYear.addItem("--select year--");
        cmbYear.setSelectedItem("--select year--");
        cmbYear.setEnabled(false);
        cmbYear.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", " " }));
        cmbYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbYearActionPerformed(evt);
            }
        });

        cmbMonth.addItem("--select month--");
        cmbMonth.setSelectedItem("--select month--");
        cmbMonth.setEnabled(false);
        cmbMonth.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "January", "February", "March", "april", "May", "June", "July", "August", "September", "October", "November", "December", " " }));
        cmbMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbMonthActionPerformed(evt);
            }
        });

        chkPayment.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        chkPayment.setText("Payment");
        chkPayment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkPaymentActionPerformed(evt);
            }
        });

        cmbPayment.addItem("--select month & year--");
        cmbPayment.setSelectedItem("--select month & year--");
        cmbPayment.setEnabled(false);
        cmbPayment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPaymentActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                        .addComponent(cmbYear, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cmbFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chkPayment)
                    .addComponent(chkTo))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cmbTo, 0, 325, Short.MAX_VALUE)
                    .addComponent(cmbPayment, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkFrom)
                    .addComponent(chkTo)
                    .addComponent(cmbTo, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkMonth)
                    .addComponent(chkYear)
                    .addComponent(cmbYear, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkPayment)
                    .addComponent(cmbPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 20, Short.MAX_VALUE))
        );

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
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setText("*Click here to close*");
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 10, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 642, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1))
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

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        // TODO add your handling code here:
        dispose();
        AnarTrading.menuItemSearchEmployee.setEnabled(true);
    }//GEN-LAST:event_jLabel1MouseClicked

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

    private void menuItemDocumentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemDocumentActionPerformed
         // TODO add your handling code here:
        InvoiceDocument ID = new InvoiceDocument(invoiceId);
        AnarTrading.desktopPane.add(ID);
        ID.setVisible(true);
        ID.show();
    }//GEN-LAST:event_menuItemDocumentActionPerformed

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


    // Variables declaration - do not modify//GEN-BEGIN:variables
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JPopupMenu jtablePopUp;
    private javax.swing.JMenuItem menuItemDocument;
    // End of variables declaration//GEN-END:variables
    Point middle = new Point(100,0);
    int invoiceId;
}
