/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vijay.org.visa;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;
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
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.proteanit.sql.DbUtils;
import org.vijay.common.AnarTrading;
import org.vijay.common.AutoCompleteDecorator;
import org.vijay.common.CurrentWorkingDirectory;
import org.vijay.common.DateCellRenderer;
import org.vijay.common.connection;
import org.vijay.contractEmployee.ConEmployeeTimeSheet;
import org.vijay.employee.LabourDetails;
import org.vijay.report.ReportView;

/**
 *
 * @author MAC
 */
public class BusinessVisa extends javax.swing.JInternalFrame {

    /**
     * Creates new form BusinessVisa
     */
    public BusinessVisa() {
        initComponents();
        cmbNationalityFill();
        cmbProfessionFill();
        cmbQualificationFill();
        cmbProNameFill();
        cmbProIdFill();
        viewDbDetailsEnteredCountry();
        viewDbDetailsVisaApproved();
        viewDbDetailsUnderProcessing();
        
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        jDatePassportExpiry.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("date")) {
                    passportExpiry = new SimpleDateFormat("yyyy-MM-dd").format(jDatePassportExpiry.getDate());
                }
            }  
        });
        jDateDateOfBirth.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("date")) {
                    dateOfBirth = new SimpleDateFormat("yyyy-MM-dd").format(jDateDateOfBirth.getDate());
                }
            }  
        });
        CurrentWorkingDirectory CWD=new CurrentWorkingDirectory();
        path=CWD.getpath();
        jtable1Selection();
        jtable2Selection();
        jtable3Selection();
    }
    public boolean validation()
    {
        if(txtName1.getText().equals(" "))
        {
            JOptionPane.showMessageDialog(txtName1, "Please Enter the Applicant Name");
            return false;
        }
        else if(cmbSex.getSelectedItem().equals("--select sex--"))
        {
            JOptionPane.showMessageDialog(cmbSex, "Please select the Sex");
            return false;
        }
        else if(cmbNationality.getSelectedItem().equals("--select nationality--"))
        {
            JOptionPane.showMessageDialog(cmbNationality, "Please select the Nationality");
            return false;
        }
        else if(txtPassportNumber.getText().equals(" "))
        {
            JOptionPane.showMessageDialog(txtPassportNumber, "Please Enter the Passport Number");
            return false;
        }
        else if(txtPassportNumber.getText().equals(" "))
        {
            JOptionPane.showMessageDialog(txtPassportNumber, "Please Enter the Passport Number");
            return false;
        }
        else if(passportExpiry.equals(""))
        {
            JOptionPane.showMessageDialog(jDatePassportExpiry, "Please Enter the Passport Expiry");
            return false;
        }
        else if(dateOfBirth.equals(""))
        {
            JOptionPane.showMessageDialog(jDateDateOfBirth, "Please Enter the Date Of Birth");
            return false;
        }
        return true;
    }
    public void insertIntoDb()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            PreparedStatement ps=con.prepareStatement("INSERT INTO tbl_business_visa_appli(applicantName1,applicantName2,applicantName3,applicantName4,applicantName5,sex,nationality,passportNumber,passportExpiry,dateOfBirth,profession,eduQualification,companyId,companyName,ProName,proId,status) VALUES(upper(?),upper(?),upper(?),upper(?),upper(?),upper(?),upper(?),upper(?),upper(?),upper(?),upper(?),upper(?),upper(?),upper(?),upper(?),upper(?),'Under Processing')");           
            ps.setString(1, txtName1.getText());
            ps.setString(2, txtName2.getText());
            ps.setString(3, txtName3.getText());
            ps.setString(4, txtName4.getText());
            ps.setString(5, txtName5.getText());
            ps.setString(6, cmbSex.getSelectedItem().toString());
            ps.setString(7, cmbNationality.getSelectedItem().toString());
            ps.setString(8, txtPassportNumber.getText());
            ps.setString(9, passportExpiry);
            ps.setString(10, dateOfBirth);
            ps.setString(11,cmbProfession.getSelectedItem().toString());
            ps.setString(12,cmbQualification.getSelectedItem().toString());
            ps.setString(13,cmbCompanyId.getSelectedItem().toString());
            ps.setString(14,cmbCompanyName.getSelectedItem().toString());
            ps.setString(15,cmbProName.getSelectedItem().toString());
            ps.setString(16,cmbProId.getSelectedItem().toString());
            int i=ps.executeUpdate();
            if(i!=0)
            {
                    dispose();
                    viewBusinessVisaForm();
            }
            con.close();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(rootPane, e, "ERROR!!", JOptionPane.ERROR_MESSAGE);
            //JOptionPane.showMessageDialog(rootPane,e+"Error SA001");
        }
    }
    public void viewBusinessVisaForm()
    {
        BusinessVisa BV=new BusinessVisa();
        AnarTrading.desktopPane1.add(BV);
        BV.setVisible(true);
    }
    public void cmbNationalityFill() {
        try {
            connection c = new connection();
            Connection con = c.conn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT nationality FROM tbl_business_visa_appli order by nationality asc");
            while (rs.next()) {
                cmbNationality.addItem(rs.getString(1));
            }
            con.close();
        } catch (SQLException ex) {
            
        }
    }
    public void cmbProfessionFill() {
        try {
            connection c = new connection();
            Connection con = c.conn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT profession FROM tbl_business_visa_appli order by profession asc");
            while (rs.next()) {
                cmbProfession.addItem(rs.getString(1));
            }
            con.close();
        } catch (SQLException ex) {
            
        }
    }
    public void cmbQualificationFill() {
        try {
            connection c = new connection();
            Connection con = c.conn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT eduQualification FROM tbl_business_visa_appli order by eduQualification asc");
            while (rs.next()) {
                cmbQualification.addItem(rs.getString(1));
            }
            con.close();
        } catch (SQLException ex) {
            
        }
    }
    public void cmbProNameFill() {
        try {
            connection c = new connection();
            Connection con = c.conn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT ProName FROM tbl_business_visa_appli order by ProName asc");
            while (rs.next()) {
                cmbProName.addItem(rs.getString(1));
            }
            con.close();
        } catch (SQLException ex) {
            
        }
    }
    public void cmbProIdFill() {
        try {
            connection c = new connection();
            Connection con = c.conn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT proId FROM tbl_business_visa_appli order by proId asc");
            while (rs.next()) {
                cmbProId.addItem(rs.getString(1));
            }
            con.close();
        } catch (SQLException ex) {
            
        }
    }
     public void viewDbDetailsEnteredCountry()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select @i := @i + 1 '"+"SL.NO"+"',businessVisaId '"+"Id"+"',concat(concat(concat(concat(applicantName1,'"+" "+"'),applicantName2),'"+" "+"'),applicantName3)'"+"Name"+"',sex ,nationality,passportNumber'"+"pp.no"+"',passportExpiry '"+"pp.Exp"+"',dateOfBirth'"+"dob"+"',profession ,eduQualification '"+"Qualification"+"',companyId,companyName,ProName,proId,applicantName1,applicantName2,applicantName3,applicantName4,applicantName5 from tbl_business_visa_appli,(SELECT @i := 0) temp where status='Entered to the Country' order by businessVisaId desc");
            jTable1.setModel(DbUtils.resultSetToTableModel(rs));
            jTable1.setAutoCreateRowSorter(true);
            
            jTable1.getColumnModel().getColumn(14).setMinWidth(0);
            jTable1.getColumnModel().getColumn(14).setMaxWidth(0);
            jTable1.getColumnModel().getColumn(14).setWidth(0);
            
            
            jTable1.getColumnModel().getColumn(15).setMinWidth(0);
            jTable1.getColumnModel().getColumn(15).setMaxWidth(0);
            jTable1.getColumnModel().getColumn(15).setWidth(0);
            
            jTable1.getColumnModel().getColumn(16).setMinWidth(0);
            jTable1.getColumnModel().getColumn(16).setMaxWidth(0);
            jTable1.getColumnModel().getColumn(16).setWidth(0);
            
            jTable1.getColumnModel().getColumn(17).setMinWidth(0);
            jTable1.getColumnModel().getColumn(17).setMaxWidth(0);
            jTable1.getColumnModel().getColumn(17).setWidth(0);
            
            jTable1.getColumnModel().getColumn(18).setMinWidth(0);
            jTable1.getColumnModel().getColumn(18).setMaxWidth(0);
            jTable1.getColumnModel().getColumn(18).setWidth(0);
            
            jTable1.getColumnModel().getColumn(6).setCellRenderer(new DateCellRenderer());
            jTable1.getColumnModel().getColumn(7).setCellRenderer(new DateCellRenderer());
            con.close();


        }
        catch(Exception e)
        {

        }
    }
     public void viewDbDetailsUnderProcessing()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select @i := @i + 1 '"+"SL.NO"+"',businessVisaId '"+"Id"+"',concat(concat(concat(concat(applicantName1,'"+" "+"'),applicantName2),'"+" "+"'),applicantName3)'"+"Name"+"',sex ,nationality,passportNumber'"+"pp.no"+"',passportExpiry '"+"pp.Exp"+"',dateOfBirth'"+"dob"+"',profession ,eduQualification '"+"Qualification"+"',companyId,companyName,ProName,proId,applicantName1,applicantName2,applicantName3,applicantName4,applicantName5 from tbl_business_visa_appli,(SELECT @i := 0) temp where status='Under Processing' order by businessVisaId desc");
            jTable2.setModel(DbUtils.resultSetToTableModel(rs));
            jTable2.setAutoCreateRowSorter(true);
            
            jTable2.getColumnModel().getColumn(14).setMinWidth(0);
            jTable2.getColumnModel().getColumn(14).setMaxWidth(0);
            jTable2.getColumnModel().getColumn(14).setWidth(0);
            
            
            jTable2.getColumnModel().getColumn(15).setMinWidth(0);
            jTable2.getColumnModel().getColumn(15).setMaxWidth(0);
            jTable2.getColumnModel().getColumn(15).setWidth(0);
            
            jTable2.getColumnModel().getColumn(16).setMinWidth(0);
            jTable2.getColumnModel().getColumn(16).setMaxWidth(0);
            jTable2.getColumnModel().getColumn(16).setWidth(0);
            
            jTable2.getColumnModel().getColumn(17).setMinWidth(0);
            jTable2.getColumnModel().getColumn(17).setMaxWidth(0);
            jTable2.getColumnModel().getColumn(17).setWidth(0);
            
            jTable2.getColumnModel().getColumn(18).setMinWidth(0);
            jTable2.getColumnModel().getColumn(18).setMaxWidth(0);
            jTable2.getColumnModel().getColumn(18).setWidth(0);
            
            jTable2.getColumnModel().getColumn(6).setCellRenderer(new DateCellRenderer());
            jTable2.getColumnModel().getColumn(7).setCellRenderer(new DateCellRenderer());
            con.close();


        }
        catch(Exception e)
        {

        }
    }
      public void viewDbDetailsVisaApproved()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select @i := @i + 1 '"+"SL.NO"+"',businessVisaId '"+"Id"+"',concat(concat(concat(concat(applicantName1,'"+" "+"'),applicantName2),'"+" "+"'),applicantName3)'"+"Name"+"',sex ,nationality,passportNumber'"+"pp.no"+"',passportExpiry '"+"pp.Exp"+"',dateOfBirth'"+"dob"+"',profession ,eduQualification '"+"Qualification"+"',companyId,companyName,ProName,proId,applicantName1,applicantName2,applicantName3,applicantName4,applicantName5 from tbl_business_visa_appli,(SELECT @i := 0) temp where status='Visa Approved' order by businessVisaId desc");
            jTable3.setModel(DbUtils.resultSetToTableModel(rs));
            jTable3.setAutoCreateRowSorter(true);
            
            jTable3.getColumnModel().getColumn(14).setMinWidth(0);
            jTable3.getColumnModel().getColumn(14).setMaxWidth(0);
            jTable3.getColumnModel().getColumn(14).setWidth(0);
            
            
            jTable3.getColumnModel().getColumn(15).setMinWidth(0);
            jTable3.getColumnModel().getColumn(15).setMaxWidth(0);
            jTable3.getColumnModel().getColumn(15).setWidth(0);
            
            jTable3.getColumnModel().getColumn(16).setMinWidth(0);
            jTable3.getColumnModel().getColumn(16).setMaxWidth(0);
            jTable3.getColumnModel().getColumn(16).setWidth(0);
            
            jTable3.getColumnModel().getColumn(17).setMinWidth(0);
            jTable3.getColumnModel().getColumn(17).setMaxWidth(0);
            jTable3.getColumnModel().getColumn(17).setWidth(0);
            
            jTable3.getColumnModel().getColumn(18).setMinWidth(0);
            jTable3.getColumnModel().getColumn(18).setMaxWidth(0);
            jTable3.getColumnModel().getColumn(18).setWidth(0);
            
            jTable3.getColumnModel().getColumn(6).setCellRenderer(new DateCellRenderer());
            jTable3.getColumnModel().getColumn(7).setCellRenderer(new DateCellRenderer());
            con.close();


        }
        catch(Exception e)
        {

        }
    }
     public void jtable1Selection()
    {
        jTable1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
            int rowNo=jTable1.getSelectedRow();
            btnSave.setEnabled(false);
            btnUpdate.setEnabled(true);
            btnDelete.setEnabled(true);
            btnPrint.setEnabled(true);
            btnAttachPassport.setEnabled(true);
            btnAttachCheckingReceipt.setEnabled(true);
            btnAgentDetails.setEnabled(true);
            btnVisaDetails.setEnabled(true);
            
            businessVisaId=Integer.parseInt(jTable1.getValueAt(rowNo,1).toString());
            cmbSex.setSelectedItem(jTable1.getValueAt(rowNo,3).toString());
            cmbNationality.setSelectedItem(jTable1.getValueAt(rowNo,4).toString());
            txtPassportNumber.setText(jTable1.getValueAt(rowNo,5).toString());
            try 
            {
                    jDatePassportExpiry.setDate(defaultDate.parse(jTable1.getValueAt(rowNo,6).toString()));
                    jDateDateOfBirth.setDate(defaultDate.parse(jTable1.getValueAt(rowNo,7).toString()));
                    cmbProfession.setSelectedItem(jTable1.getValueAt(rowNo,8).toString());
                    cmbQualification.setSelectedItem(jTable1.getValueAt(rowNo,9).toString());
                    cmbCompanyId.setSelectedItem(jTable1.getValueAt(rowNo,10).toString());
                    cmbCompanyName.setSelectedItem(jTable1.getValueAt(rowNo,11).toString());
                    cmbProName.setSelectedItem(jTable1.getValueAt(rowNo,12).toString());
                    cmbProId.setSelectedItem(jTable1.getValueAt(rowNo,13).toString());
                    txtName1.setText(jTable1.getValueAt(rowNo,14).toString());
                    txtName2.setText(jTable1.getValueAt(rowNo,15).toString());
                    txtName3.setText(jTable1.getValueAt(rowNo,16).toString());
                    txtName4.setText(jTable1.getValueAt(rowNo,17).toString());
                    txtName5.setText(jTable1.getValueAt(rowNo,18).toString());
                    
            } 
            catch (ParseException ex) 
            {
                    Logger.getLogger(LabourDetails.class.getName()).log(Level.SEVERE, null, ex);
            }  
            }
        });
    }
     public void jtable2Selection()
    {
        jTable2.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
            int rowNo=jTable2.getSelectedRow();
            btnSave.setEnabled(false);
            btnUpdate.setEnabled(true);
            btnDelete.setEnabled(true);
            btnPrint.setEnabled(true);
            btnAttachPassport.setEnabled(true);
            btnAttachCheckingReceipt.setEnabled(true);
            btnAgentDetails.setEnabled(true);
            btnVisaDetails.setEnabled(true);
            
            businessVisaId=Integer.parseInt(jTable2.getValueAt(rowNo,1).toString());
            cmbSex.setSelectedItem(jTable2.getValueAt(rowNo,3).toString());
            cmbNationality.setSelectedItem(jTable2.getValueAt(rowNo,4).toString());
            txtPassportNumber.setText(jTable2.getValueAt(rowNo,5).toString());
            try 
            {
                    jDatePassportExpiry.setDate(defaultDate.parse(jTable2.getValueAt(rowNo,6).toString()));
                    jDateDateOfBirth.setDate(defaultDate.parse(jTable2.getValueAt(rowNo,7).toString()));
                    cmbProfession.setSelectedItem(jTable2.getValueAt(rowNo,8).toString());
                    cmbQualification.setSelectedItem(jTable2.getValueAt(rowNo,9).toString());
                    cmbCompanyId.setSelectedItem(jTable2.getValueAt(rowNo,10).toString());
                    cmbCompanyName.setSelectedItem(jTable2.getValueAt(rowNo,11).toString());
                    cmbProName.setSelectedItem(jTable2.getValueAt(rowNo,12).toString());
                    cmbProId.setSelectedItem(jTable2.getValueAt(rowNo,13).toString());
                    txtName1.setText(jTable2.getValueAt(rowNo,14).toString());
                    txtName2.setText(jTable2.getValueAt(rowNo,15).toString());
                    txtName3.setText(jTable2.getValueAt(rowNo,16).toString());
                    txtName4.setText(jTable2.getValueAt(rowNo,17).toString());
                    txtName5.setText(jTable2.getValueAt(rowNo,18).toString());
                    
            } 
            catch (ParseException ex) 
            {
                    Logger.getLogger(LabourDetails.class.getName()).log(Level.SEVERE, null, ex);
            }  
            }
        });
    }
     public void jtable3Selection()
    {
        jTable3.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
            int rowNo=jTable3.getSelectedRow();
            btnSave.setEnabled(false);
            btnUpdate.setEnabled(true);
            btnDelete.setEnabled(true);
            btnPrint.setEnabled(true);
            btnAttachPassport.setEnabled(true);
            btnAttachCheckingReceipt.setEnabled(true);
            btnAgentDetails.setEnabled(true);
            btnVisaDetails.setEnabled(true);
            
            businessVisaId=Integer.parseInt(jTable3.getValueAt(rowNo,1).toString());
            cmbSex.setSelectedItem(jTable3.getValueAt(rowNo,3).toString());
            cmbNationality.setSelectedItem(jTable3.getValueAt(rowNo,4).toString());
            txtPassportNumber.setText(jTable3.getValueAt(rowNo,5).toString());
            try 
            {
                    jDatePassportExpiry.setDate(defaultDate.parse(jTable3.getValueAt(rowNo,6).toString()));
                    jDateDateOfBirth.setDate(defaultDate.parse(jTable3.getValueAt(rowNo,7).toString()));
                    cmbProfession.setSelectedItem(jTable3.getValueAt(rowNo,8).toString());
                    cmbQualification.setSelectedItem(jTable3.getValueAt(rowNo,9).toString());
                    cmbCompanyId.setSelectedItem(jTable3.getValueAt(rowNo,10).toString());
                    cmbCompanyName.setSelectedItem(jTable3.getValueAt(rowNo,11).toString());
                    cmbProName.setSelectedItem(jTable3.getValueAt(rowNo,12).toString());
                    cmbProId.setSelectedItem(jTable3.getValueAt(rowNo,13).toString());
                    txtName1.setText(jTable3.getValueAt(rowNo,14).toString());
                    txtName2.setText(jTable3.getValueAt(rowNo,15).toString());
                    txtName3.setText(jTable3.getValueAt(rowNo,16).toString());
                    txtName4.setText(jTable3.getValueAt(rowNo,17).toString());
                    txtName5.setText(jTable3.getValueAt(rowNo,18).toString());
                    
            } 
            catch (ParseException ex) 
            {
                    Logger.getLogger(LabourDetails.class.getName()).log(Level.SEVERE, null, ex);
            }  
            }
        });
    }
     public void updateDb()
    {
        
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            PreparedStatement ps=con.prepareStatement("UPDATE tbl_business_visa_appli SET applicantName1=upper(?),applicantName2=upper(?),applicantName3=upper(?),applicantName4=upper(?),applicantName5=upper(?),sex=upper(?),nationality=upper(?),passportNumber=upper(?),passportExpiry=upper(?),dateOfBirth=upper(?),profession=upper(?),eduQualification=upper(?),companyId=upper(?),companyName=upper(?),ProName=upper(?),proId=upper(?) where businessVisaId=?");
            ps.setString(1,txtName1.getText());
            ps.setString(2,txtName2.getText());
            ps.setString(3,txtName3.getText());
            ps.setString(4,txtName4.getText());
            ps.setString(5,txtName5.getText());
            ps.setString(6,cmbSex.getSelectedItem().toString());
            ps.setString(7,cmbNationality.getSelectedItem().toString());
            ps.setString(8,txtPassportNumber.getText());
            ps.setString(9,passportExpiry);
            ps.setString(10,dateOfBirth);
            ps.setString(11,cmbProfession.getSelectedItem().toString());
            ps.setString(12,cmbQualification.getSelectedItem().toString());
            ps.setString(13,cmbCompanyId.getSelectedItem().toString());
            ps.setString(14,cmbCompanyName.getSelectedItem().toString());
            ps.setString(15,cmbProName.getSelectedItem().toString());
            ps.setString(16,cmbProId.getSelectedItem().toString());
            ps.setInt(17, businessVisaId);
            int i=ps.executeUpdate();
            if(i!=0)
            {
                    dispose();
                    viewBusinessVisaForm();
            }
            con.close();  
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(rootPane, e, "ERROR!!", JOptionPane.ERROR_MESSAGE);
        }
    }
     public void deleteAction()
     {
         int response=JOptionPane.showConfirmDialog(null,"Do you want to delete data Permanently?","Confirm",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
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
                    int i=stmt1.executeUpdate("delete from tbl_business_visa_appli  where businessVisaId="+businessVisaId);
                    if(i!=0)
                    {
                        dispose();
                        viewBusinessVisaForm();
                    }
                }
                catch(Exception e)
                {
                    JOptionPane.showMessageDialog(rootPane, e, "ERROR!!", JOptionPane.ERROR_MESSAGE);
                }
            }
            else if(response==JOptionPane.CLOSED_OPTION)
            {

            }
     }
     public boolean ispassportCopyAvailable()
    {
        boolean ispassportCopyAvailable=true;
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT * FROM tbl_business_visa_passport WHERE businessVisaId="+businessVisaId);
            if(rs.next()==false)
            {
                 ispassportCopyAvailable=false;
            }
            else
            {
               ispassportCopyAvailable=true;
            }
            con.close();
            
        }
        catch(Exception e)
        {

        }
        return ispassportCopyAvailable;
    }
    public boolean isCheckingReceiptAvailable()
    {
        boolean isCheckingReceiptAvailbale=true;
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT * FROM tbl_business_visa_checking_recepit WHERE businessVisaId="+businessVisaId);
            if(rs.next()==false)
            {
                 isCheckingReceiptAvailbale=false;
            }
            else
            {
               isCheckingReceiptAvailbale=true;
            }
            con.close();
            
        }
        catch(Exception e)
        {

        }
        return isCheckingReceiptAvailbale;
    }
    public boolean isVisaDetailsAvailable()
    {
        boolean isVisaDetailsAvailable=true;
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT * FROM tbl_business_visa_details WHERE businessVisaId="+businessVisaId);
            if(rs.next()==false)
            {
                 isVisaDetailsAvailable=false;
            }
            else
            {
               isVisaDetailsAvailable=true;
            }
            con.close();
            
        }
        catch(Exception e)
        {

        }
        return isVisaDetailsAvailable;
    }
    public boolean isEntryDateAvailable()
    {
        boolean isEntryDateAvailable=true;
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT * FROM tbl_business_visa_entrydate WHERE businessVisaId="+businessVisaId);
            if(rs.next()==false)
            {
                 isEntryDateAvailable=false;
            }
            else
            {
               isEntryDateAvailable=true;
            }
            con.close();
            
        }
        catch(Exception e)
        {

        }
        return isEntryDateAvailable;
    }
    public void addVisaDetails()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT * FROM tbl_business_visa_details WHERE businessVisaId="+businessVisaId);
            if(rs.next()==false)
            {
                 int response=JOptionPane.showConfirmDialog(null,"There Is No VIsa Details Available for this person!!  Do you want to add the same? ","Please add agent details!",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
                if(response==JOptionPane.NO_OPTION)
                {

                }
                else if(response==JOptionPane.YES_OPTION)
                {
                    VisaDetails VD=new VisaDetails(businessVisaId,txtName1.getText()+" "+txtName2.getText()+" "+txtName3.getText()+" "+txtName4.getText()+" "+txtName5.getText(),txtPassportNumber.getText());
                    AnarTrading.desktopPane1.add(VD);
                    VD.setVisible(true);
                    dispose();
                }
                else if(response==JOptionPane.CLOSED_OPTION)
                {

                }
            }
            else
            {
                VisaDetails VD=new VisaDetails(businessVisaId,txtName1.getText()+" "+txtName2.getText()+" "+txtName3.getText()+" "+txtName4.getText()+" "+txtName5.getText(),txtPassportNumber.getText(),0);
                AnarTrading.desktopPane1.add(VD);
                VD.setVisible(true);
                dispose();
            }
            con.close();


        }
        catch(Exception e)
        {

        }
    }
    public boolean isEixtDateAvailable()
    {
        boolean isEixtDateAvailable=true;
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT * FROM tbl_business_visa_exit WHERE businessVisaId="+businessVisaId);
            if(rs.next()==false)
            {
                 isEixtDateAvailable=false;
            }
            else
            {
               isEixtDateAvailable=true;
            }
            con.close();
            
        }
        catch(Exception e)
        {

        }
        return isEixtDateAvailable;
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
        menuItemAttachPassport = new javax.swing.JMenuItem();
        menuItemAttachCheckingReceipt = new javax.swing.JMenuItem();
        menuItemAttachVisaDetails = new javax.swing.JMenuItem();
        menuItemEntryDate = new javax.swing.JMenuItem();
        menuItemExitDate = new javax.swing.JMenuItem();
        menuItemPrint = new javax.swing.JMenuItem();
        menuItemDelete = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtName1 = new javax.swing.JTextField();
        cmbSex = new javax.swing.JComboBox();
        jLabel17 = new javax.swing.JLabel();
        cmbNationality = new javax.swing.JComboBox();
        jLabel19 = new javax.swing.JLabel();
        jDatePassportExpiry = new com.toedter.calendar.JDateChooser();
        jLabel20 = new javax.swing.JLabel();
        jDateDateOfBirth = new com.toedter.calendar.JDateChooser();
        txtName2 = new javax.swing.JTextField();
        txtName3 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtPassportNumber = new javax.swing.JTextField();
        txtName4 = new javax.swing.JTextField();
        txtName5 = new javax.swing.JTextField();
        cmbProfession = new javax.swing.JComboBox();
        jLabel22 = new javax.swing.JLabel();
        cmbQualification = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        cmbCompanyName = new javax.swing.JComboBox();
        cmbCompanyId = new javax.swing.JComboBox();
        jPanel3 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        cmbProName = new javax.swing.JComboBox();
        cmbProId = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jToolBar1 = new javax.swing.JToolBar();
        btnSave = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnPrint = new javax.swing.JButton();
        btnAttachPassport = new javax.swing.JButton();
        btnAttachCheckingReceipt = new javax.swing.JButton();
        btnAgentDetails = new javax.swing.JButton();
        btnVisaDetails = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();

        menuItemAttachPassport.setText("Attach Passport Copy");
        menuItemAttachPassport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemAttachPassportActionPerformed(evt);
            }
        });
        jtablePopUp.add(menuItemAttachPassport);

        menuItemAttachCheckingReceipt.setText("Attach Checking Receipt");
        menuItemAttachCheckingReceipt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemAttachCheckingReceiptActionPerformed(evt);
            }
        });
        jtablePopUp.add(menuItemAttachCheckingReceipt);

        menuItemAttachVisaDetails.setText("Attach Visa Details");
        menuItemAttachVisaDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemAttachVisaDetailsActionPerformed(evt);
            }
        });
        jtablePopUp.add(menuItemAttachVisaDetails);

        menuItemEntryDate.setText("Attach Entry Date");
        menuItemEntryDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemEntryDateActionPerformed(evt);
            }
        });
        jtablePopUp.add(menuItemEntryDate);

        menuItemExitDate.setForeground(new java.awt.Color(255, 0, 51));
        menuItemExitDate.setText("Attach Exit Date");
        menuItemExitDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemExitDateActionPerformed(evt);
            }
        });
        jtablePopUp.add(menuItemExitDate);

        menuItemPrint.setMnemonic('p');
        menuItemPrint.setText("Print Application Form");
        menuItemPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemPrintActionPerformed(evt);
            }
        });
        jtablePopUp.add(menuItemPrint);

        menuItemDelete.setForeground(new java.awt.Color(255, 0, 51));
        menuItemDelete.setText("Delete");
        menuItemDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemDeleteActionPerformed(evt);
            }
        });
        jtablePopUp.add(menuItemDelete);

        setClosable(true);
        setTitle("Business Visa Application");
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Visitors's Particulars(As in passport)", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 0, 12))); // NOI18N

        jLabel15.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel15.setText("Name");

        jLabel16.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel16.setText("Sex");

        txtName1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txtName1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtName1KeyPressed(evt);
            }
        });

        cmbSex.setEditable(true);
        AutoCompleteDecorator.decorate(cmbSex);
        cmbSex.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cmbSex.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "--select sex--", "M", "F" }));

        jLabel17.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel17.setText("Nationality");

        cmbNationality.setEditable(true);
        AutoCompleteDecorator.decorate(cmbNationality);
        cmbNationality.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cmbNationality.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "--select nationality--" }));

        jLabel19.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel19.setText("Passport Expiry");

        jDatePassportExpiry.setDateFormatString("yyyy-MM-dd");
        jDatePassportExpiry.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jLabel20.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel20.setText("DOB");

        jDateDateOfBirth.setDateFormatString("yyyy-MM-dd");
        jDateDateOfBirth.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        txtName2.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txtName2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtName2KeyPressed(evt);
            }
        });

        txtName3.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txtName3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtName3KeyPressed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel18.setText("Passport Number");

        jLabel21.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel21.setText("Profession");

        txtPassportNumber.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txtPassportNumber.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPassportNumberKeyPressed(evt);
            }
        });

        txtName4.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txtName4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtName4KeyPressed(evt);
            }
        });

        txtName5.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txtName5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtName5KeyPressed(evt);
            }
        });

        cmbProfession.setEditable(true);
        AutoCompleteDecorator.decorate(cmbProfession);
        cmbProfession.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cmbProfession.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "--select profession--" }));

        jLabel22.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel22.setText("Qualification");

        cmbQualification.setEditable(true);
        AutoCompleteDecorator.decorate(cmbQualification);
        cmbQualification.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cmbQualification.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "--select qualification--" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(189, 189, 189)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19)
                            .addComponent(jLabel22))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(cmbSex, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jDatePassportExpiry, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtName1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(txtName2, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtName3, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel20)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cmbNationality, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jDateDateOfBirth, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addComponent(cmbQualification, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                    .addComponent(txtName4)
                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cmbProfession, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtPassportNumber)
                    .addComponent(txtName5))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtName1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtName2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtName3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtName4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtName5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbSex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbNationality, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPassportNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jDatePassportExpiry, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbProfession, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addComponent(jDateDateOfBirth, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbQualification, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sponsor's Particulars", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 0, 12))); // NOI18N

        jLabel23.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel23.setText("Company Id");

        jLabel24.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel24.setText("Company Name");

        cmbCompanyName.setEditable(true);
        AutoCompleteDecorator.decorate(cmbCompanyName);
        cmbCompanyName.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cmbCompanyName.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Fixture International W.L.L" }));

        cmbCompanyId.setEditable(true);
        AutoCompleteDecorator.decorate(cmbCompanyId);
        cmbCompanyId.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cmbCompanyId.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "13777500" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel23)
                    .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cmbCompanyName, 0, 367, Short.MAX_VALUE)
                    .addComponent(cmbCompanyId, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(42, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbCompanyName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbCompanyId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Applicant's(PRO) Particulars", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 0, 12))); // NOI18N

        jLabel25.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel25.setText("ID No.");

        jLabel26.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel26.setText("Name");

        cmbProName.setEditable(true);
        AutoCompleteDecorator.decorate(cmbProName);
        cmbProName.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cmbProName.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "--select name--" }));

        cmbProId.setEditable(true);
        AutoCompleteDecorator.decorate(cmbProId);
        cmbProId.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cmbProId.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "--select id number--" }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel25)
                    .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cmbProName, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbProId, 0, 367, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbProName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbProId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Entered To The Country", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 0, 12))); // NOI18N

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
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jTable1MouseEntered(evt);
            }
        });
        jTable1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTable1FocusGained(evt);
            }
        });
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable1KeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jToolBar1.setBorder(null);
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnSave.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnSave.setMnemonic('s');
        btnSave.setText("Save");
        btnSave.setToolTipText("");
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
        btnUpdate.setMnemonic('u');
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
        btnDelete.setMnemonic('u');
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
        btnRefresh.setMnemonic('r');
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
        btnCancel.setMnemonic('c');
        btnCancel.setText("Cancel");
        btnCancel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnCancel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        jToolBar1.add(btnCancel);

        btnPrint.setEnabled(false);
        btnPrint.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnPrint.setText("Print Application Form");
        btnPrint.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnPrint.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPrint.setFocusable(false);
        btnPrint.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPrint.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });

        btnAttachPassport.setEnabled(false);
        btnAttachPassport.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnAttachPassport.setMnemonic('r');
        btnAttachPassport.setText("1.Attach Passport");
        btnAttachPassport.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnAttachPassport.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAttachPassport.setFocusable(false);
        btnAttachPassport.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAttachPassport.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAttachPassport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAttachPassportActionPerformed(evt);
            }
        });

        btnAttachCheckingReceipt.setEnabled(false);
        btnAttachCheckingReceipt.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnAttachCheckingReceipt.setMnemonic('r');
        btnAttachCheckingReceipt.setText("2.Attach Checking Receipt");
        btnAttachCheckingReceipt.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnAttachCheckingReceipt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAttachCheckingReceipt.setFocusable(false);
        btnAttachCheckingReceipt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAttachCheckingReceipt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAttachCheckingReceipt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAttachCheckingReceiptActionPerformed(evt);
            }
        });

        btnAgentDetails.setEnabled(false);
        btnAgentDetails.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnAgentDetails.setMnemonic('r');
        btnAgentDetails.setText("3.Agent Details");
        btnAgentDetails.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnAgentDetails.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAgentDetails.setFocusable(false);
        btnAgentDetails.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAgentDetails.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAgentDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgentDetailsActionPerformed(evt);
            }
        });

        btnVisaDetails.setEnabled(false);
        btnVisaDetails.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnVisaDetails.setMnemonic('r');
        btnVisaDetails.setText("4.Visa Details");
        btnVisaDetails.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnVisaDetails.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVisaDetails.setFocusable(false);
        btnVisaDetails.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnVisaDetails.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnVisaDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVisaDetailsActionPerformed(evt);
            }
        });

        jScrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Under Processing", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 0, 12))); // NOI18N

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jTable2MouseEntered(evt);
            }
        });
        jTable2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTable2FocusGained(evt);
            }
        });
        jTable2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable2KeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jScrollPane3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Visa Approved", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 0, 12))); // NOI18N

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jTable3MouseEntered(evt);
            }
        });
        jTable3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTable3FocusGained(evt);
            }
        });
        jTable3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable3KeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(jTable3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addComponent(jScrollPane1))
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 1267, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnPrint)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnAttachPassport)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAttachCheckingReceipt)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAgentDetails)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnVisaDetails)
                                .addGap(13, 13, 13)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAttachPassport)
                    .addComponent(btnAttachCheckingReceipt)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPrint)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btnVisaDetails, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAgentDetails, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(209, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtName1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtName1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtName1KeyPressed

    private void txtPassportNumberKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPassportNumberKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPassportNumberKeyPressed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed

        insertIntoDb();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        updateDb();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        deleteAction();

    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // TODO add your handling code here:
        dispose();
        viewBusinessVisaForm();


    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

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
                screenX = evt.getXOnScreen();
                screenY = evt.getYOnScreen();
            }
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void menuItemDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemDeleteActionPerformed
        // TODO add your handling code here:
        deleteAction();
    }//GEN-LAST:event_menuItemDeleteActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        // TODO add your handling code here:
            HashMap para=new HashMap();
            para.put("businessVisaId",businessVisaId);
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\BusinessVisa\\Application.jasper"),para);
            re.setVisible(true);
    }//GEN-LAST:event_btnPrintActionPerformed

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
        // TODO add your handling code here:
        int keyCode = evt.getKeyCode();
        if(keyCode == KeyEvent.VK_ENTER)
        {
            
        }
        if(keyCode == KeyEvent.VK_DELETE)
        {
            deleteAction();
        }
    }//GEN-LAST:event_jTable1KeyPressed

    private void menuItemPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemPrintActionPerformed
        // TODO add your handling code here:
        HashMap para=new HashMap();
        para.put("businessVisaId",businessVisaId);
        ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\BusinessVisa\\Application.jasper"),para);
        re.setVisible(true);
    }//GEN-LAST:event_menuItemPrintActionPerformed

    private void txtName2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtName2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtName2KeyPressed

    private void txtName3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtName3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtName3KeyPressed

    private void txtName4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtName4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtName4KeyPressed

    private void txtName5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtName5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtName5KeyPressed

    private void btnAttachPassportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAttachPassportActionPerformed
        // TODO add your handling code here:
            if(ispassportCopyAvailable())
            {
                PassportCopy PC=new PassportCopy(businessVisaId);
                AnarTrading.desktopPane1.add(PC);
                PC.setVisible(true); 
            }
            else
            {
                int response=JOptionPane.showConfirmDialog(null,"There Is No Passport Copy Available for this person!!  Do you want to attach the same? ","Please attach passport copy!",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
                if(response==JOptionPane.NO_OPTION)
                {

                }
                else if(response==JOptionPane.YES_OPTION)
                {
                    PassportCopy PC=new PassportCopy(businessVisaId);
                    AnarTrading.desktopPane1.add(PC);
                    PC.setVisible(true); 
                }
                else if(response==JOptionPane.CLOSED_OPTION)
                {

                }
            }

    }//GEN-LAST:event_btnAttachPassportActionPerformed

    private void menuItemAttachPassportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemAttachPassportActionPerformed
        // TODO add your handling code here:
        PassportCopy PC=new PassportCopy(businessVisaId);
        AnarTrading.desktopPane1.add(PC);
        PC.setVisible(true);
    }//GEN-LAST:event_menuItemAttachPassportActionPerformed

    private void btnAttachCheckingReceiptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAttachCheckingReceiptActionPerformed
        // TODO add your handling code here:
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT * FROM tbl_business_visa_checking_recepit WHERE businessVisaId="+businessVisaId);
            if(rs.next()==false)
            {
                 int response=JOptionPane.showConfirmDialog(null,"There Is No Checking Receipt available for this person!!  Do you want to attach the same? ","Please attach checking receipt!",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
                if(response==JOptionPane.NO_OPTION)
                {

                }
                else if(response==JOptionPane.YES_OPTION)
                {
                    CheckingReceipt CR=new CheckingReceipt(businessVisaId);
                    AnarTrading.desktopPane1.add(CR);
                    CR.setVisible(true);
                }
                else if(response==JOptionPane.CLOSED_OPTION)
                {

                }
            }
            else
            {
                CheckingReceipt CR=new CheckingReceipt(businessVisaId);
                AnarTrading.desktopPane1.add(CR);
                CR.setVisible(true);
            }
            con.close();


        }
        catch(Exception e)
        {

        }
        
    }//GEN-LAST:event_btnAttachCheckingReceiptActionPerformed

    private void menuItemAttachCheckingReceiptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemAttachCheckingReceiptActionPerformed
        // TODO add your handling code here:
        CheckingReceipt CR=new CheckingReceipt(businessVisaId);
        AnarTrading.desktopPane1.add(CR);
        CR.setVisible(true);
    }//GEN-LAST:event_menuItemAttachCheckingReceiptActionPerformed

    private void btnAgentDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgentDetailsActionPerformed
        // TODO add your handling code here:
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT * FROM tbl_business_visa_agent WHERE businessVisaId="+businessVisaId);
            if(rs.next()==false)
            {
                 int response=JOptionPane.showConfirmDialog(null,"There Is No Agent Details Available for this person!!  Do you want to add the same? ","Please add agent details!",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
                if(response==JOptionPane.NO_OPTION)
                {

                }
                else if(response==JOptionPane.YES_OPTION)
                {
                    AgentDetails AD=new AgentDetails(businessVisaId,txtName1.getText()+" "+txtName2.getText()+" "+txtName3.getText()+" "+txtName4.getText()+" "+txtName5.getText(),txtPassportNumber.getText());
                    AnarTrading.desktopPane1.add(AD);
                    AD.setVisible(true);
                }
                else if(response==JOptionPane.CLOSED_OPTION)
                {

                }
            }
            else
            {
                AgentDetails AD=new AgentDetails(businessVisaId,txtName1.getText()+" "+txtName2.getText()+" "+txtName3.getText()+" "+txtName4.getText()+" "+txtName5.getText(),txtPassportNumber.getText());
                AnarTrading.desktopPane1.add(AD);
                AD.setVisible(true);
            }
            con.close();


        }
        catch(Exception e)
        {

        }
//  
    }//GEN-LAST:event_btnAgentDetailsActionPerformed

    private void btnVisaDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVisaDetailsActionPerformed
        // TODO add your handling code here:
        addVisaDetails();
    }//GEN-LAST:event_btnVisaDetailsActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        // TODO add your handling code here:
        int rowNo=jTable2.getSelectedRow();
        if(rowNo==-1)
        {

        }
        else
        {
            if(evt.getButton()==MouseEvent.BUTTON3)
            {
                jtablePopUp.show(evt.getComponent(), evt.getX(),evt.getY());
                screenX = evt.getXOnScreen();
                screenY = evt.getYOnScreen();
            }
        }
    }//GEN-LAST:event_jTable2MouseClicked

    private void jTable2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable2KeyPressed

    private void jTable1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTable1FocusGained
        // TODO add your handling code here:
        jTable1.setRowSelectionAllowed(true);
        jTable2.setRowSelectionAllowed(false);
        jTable3.setRowSelectionAllowed(false);
    }//GEN-LAST:event_jTable1FocusGained

    private void jTable2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTable2FocusGained
        // TODO add your handling code here:
        jTable2.setRowSelectionAllowed(true);
        jTable1.setRowSelectionAllowed(false);
        jTable3.setRowSelectionAllowed(false);
    }//GEN-LAST:event_jTable2FocusGained

    private void jTable3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable3MouseClicked
        // TODO add your handling code here:
        
        int rowNo=jTable3.getSelectedRow();
        if(rowNo==-1)
        {

        }
        else
        {
            if(evt.getButton()==MouseEvent.BUTTON3)
            {
                jtablePopUp.show(evt.getComponent(), evt.getX(),evt.getY());
                    screenX = evt.getXOnScreen();
                    screenY = evt.getYOnScreen();
            }
        }
    }//GEN-LAST:event_jTable3MouseClicked

    private void jTable3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTable3FocusGained
        // TODO add your handling code here:
        jTable3.setRowSelectionAllowed(true);
        jTable2.setRowSelectionAllowed(false);
        jTable1.setRowSelectionAllowed(false);
    }//GEN-LAST:event_jTable3FocusGained

    private void jTable3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable3KeyPressed

    private void jTable1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1MouseEntered

    private void jTable3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable3MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable3MouseEntered

    private void jTable2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable2MouseEntered

    private void menuItemEntryDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemEntryDateActionPerformed
        // TODO add your handling code here:
        if(ispassportCopyAvailable())
        {
            if(isCheckingReceiptAvailable())
            {
                if(isVisaDetailsAvailable())
                {
                    if(isEntryDateAvailable())
                    {
                        EntryDateUpdate EDU=new EntryDateUpdate(null, closable,businessVisaId,screenX,screenY,txtName1.getText()+" "+txtName2.getText()+" "+txtName3.getText()+" "+txtName4.getText()+" "+txtName5.getText(),txtPassportNumber.getText(),0);
                        EDU.setVisible(true);
                    }
                    else
                    {
                        EntryDateUpdate EDU=new EntryDateUpdate(null, closable,businessVisaId,screenX,screenY,txtName1.getText()+" "+txtName2.getText()+" "+txtName3.getText()+" "+txtName4.getText()+" "+txtName5.getText(),txtPassportNumber.getText());
                        EDU.setVisible(true);

                    }
                }
                else
                {
                    int response=JOptionPane.showConfirmDialog(null,"There Is No VIsa Details Available for this person!!  Do you want to add the same? ","Please add agent details first!",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
                    if(response==JOptionPane.NO_OPTION)
                    {

                    }
                    else if(response==JOptionPane.YES_OPTION)
                    {
                        VisaDetails VD=new VisaDetails(businessVisaId,txtName1.getText()+" "+txtName2.getText()+" "+txtName3.getText()+" "+txtName4.getText()+" "+txtName5.getText(),txtPassportNumber.getText());
                        AnarTrading.desktopPane1.add(VD);
                        VD.setVisible(true);
                        dispose();
                    }
                    else if(response==JOptionPane.CLOSED_OPTION)
                    {

                    }
                }
            }
            else
            {
                int response=JOptionPane.showConfirmDialog(null,"There Is No Checking Receipt available for this person!!  Do you want to attach the same? ","Please attach checking receipt first!",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
                if(response==JOptionPane.NO_OPTION)
                {
                    if(isVisaDetailsAvailable())
                    {
                        if(isEntryDateAvailable())
                        {
                            EntryDateUpdate EDU=new EntryDateUpdate(null, closable,businessVisaId,screenX,screenY,txtName1.getText()+" "+txtName2.getText()+" "+txtName3.getText()+" "+txtName4.getText()+" "+txtName5.getText(),txtPassportNumber.getText(),0);
                            EDU.setVisible(true);
                        }
                        else
                        {
                            EntryDateUpdate EDU=new EntryDateUpdate(null, closable,businessVisaId,screenX,screenY,txtName1.getText()+" "+txtName2.getText()+" "+txtName3.getText()+" "+txtName4.getText()+" "+txtName5.getText(),txtPassportNumber.getText());
                            EDU.setVisible(true);

                        }
                    }
                    else
                    {
                        int response1=JOptionPane.showConfirmDialog(null,"There Is No VIsa Details Available for this person!!  Do you want to add the same? ","Please add agent details first!",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
                        if(response1==JOptionPane.NO_OPTION)
                        {

                        }
                        else if(response1==JOptionPane.YES_OPTION)
                        {
                            VisaDetails VD=new VisaDetails(businessVisaId,txtName1.getText()+" "+txtName2.getText()+" "+txtName3.getText()+" "+txtName4.getText()+" "+txtName5.getText(),txtPassportNumber.getText());
                            AnarTrading.desktopPane1.add(VD);
                            VD.setVisible(true);
                            dispose();
                        }
                        else if(response1==JOptionPane.CLOSED_OPTION)
                        {

                        }
                    }
                }
                else if(response==JOptionPane.YES_OPTION)
                {
                    CheckingReceipt CR=new CheckingReceipt(businessVisaId);
                    AnarTrading.desktopPane1.add(CR);
                    CR.setVisible(true);
                }
                else if(response==JOptionPane.CLOSED_OPTION)
                {

                }
            }
        }
        else
        {
            int response=JOptionPane.showConfirmDialog(null,"There Is No Passport Copy Available for this person!!  Do you want to attach the same? ","Please attach passport copy first!",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
            if(response==JOptionPane.NO_OPTION)
            {

            }
            else if(response==JOptionPane.YES_OPTION)
            {
                PassportCopy PC=new PassportCopy(businessVisaId);
                AnarTrading.desktopPane1.add(PC);
                PC.setVisible(true); 
            }
            else if(response==JOptionPane.CLOSED_OPTION)
            {

            }
        }
    }//GEN-LAST:event_menuItemEntryDateActionPerformed

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_formFocusGained

    private void menuItemExitDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemExitDateActionPerformed
        // TODO add your handling code here:
        if(ispassportCopyAvailable())
        {
            if(isCheckingReceiptAvailable())
            {
                if(isVisaDetailsAvailable())
                {
                    if(isEntryDateAvailable())
                    {
                        if(isEixtDateAvailable())
                        {
                            ExitDateUpdate EDU=new ExitDateUpdate(null, closable,businessVisaId,screenX,screenY,txtName1.getText()+" "+txtName2.getText()+" "+txtName3.getText()+" "+txtName4.getText()+" "+txtName5.getText(),txtPassportNumber.getText(),0);
                            EDU.setVisible(true);
                        }
                        else
                        {
                            ExitDateUpdate EDU=new ExitDateUpdate(null, closable,businessVisaId,screenX,screenY,txtName1.getText()+" "+txtName2.getText()+" "+txtName3.getText()+" "+txtName4.getText()+" "+txtName5.getText(),txtPassportNumber.getText());
                            EDU.setVisible(true);
                        }
                    }
                    else
                    {
                        int response=JOptionPane.showConfirmDialog(null,"There Is No Entry Details Available for this person!!  Do you want to add the same? ","Please Attach Entry Date First!",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
                        if(response==JOptionPane.NO_OPTION)
                        {

                        }
                        else if(response==JOptionPane.YES_OPTION)
                        {
                            EntryDateUpdate EDU=new EntryDateUpdate(null, closable,businessVisaId,screenX,screenY,txtName1.getText()+" "+txtName2.getText()+" "+txtName3.getText()+" "+txtName4.getText()+" "+txtName5.getText(),txtPassportNumber.getText());
                            EDU.setVisible(true);
                        }   
                        else if(response==JOptionPane.CLOSED_OPTION)
                        {

                        }
                    }
                }
                else
                {
                    int response=JOptionPane.showConfirmDialog(null,"There Is No VIsa Details Available for this person!!  Do you want to add the same? ","Please add agent details first!",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
                    if(response==JOptionPane.NO_OPTION)
                    {

                    }
                    else if(response==JOptionPane.YES_OPTION)
                    {
                        VisaDetails VD=new VisaDetails(businessVisaId,txtName1.getText()+" "+txtName2.getText()+" "+txtName3.getText()+" "+txtName4.getText()+" "+txtName5.getText(),txtPassportNumber.getText());
                        AnarTrading.desktopPane1.add(VD);
                        VD.setVisible(true);
                        dispose();
                    }
                    else if(response==JOptionPane.CLOSED_OPTION)
                    {

                    }
                }
            }
            else
            {
                int response=JOptionPane.showConfirmDialog(null,"There Is No Checking Receipt available for this person!!  Do you want to attach the same? ","Please attach checking receipt first!",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
                if(response==JOptionPane.NO_OPTION)
                {
                    if(isVisaDetailsAvailable())
                    {
                        if(isEntryDateAvailable())
                        {
                            if(isEixtDateAvailable())
                            {
                                ExitDateUpdate EDU=new ExitDateUpdate(null, closable,businessVisaId,screenX,screenY,txtName1.getText()+" "+txtName2.getText()+" "+txtName3.getText()+" "+txtName4.getText()+" "+txtName5.getText(),txtPassportNumber.getText(),0);
                                EDU.setVisible(true);
                            }
                            else
                            {
                                ExitDateUpdate EDU=new ExitDateUpdate(null, closable,businessVisaId,screenX,screenY,txtName1.getText()+" "+txtName2.getText()+" "+txtName3.getText()+" "+txtName4.getText()+" "+txtName5.getText(),txtPassportNumber.getText());
                                EDU.setVisible(true);
                            }
                        }
                        else
                        {
                            int response1=JOptionPane.showConfirmDialog(null,"There Is No VIsa Details Available for this person!!  Do you want to add the same? ","Please add agent details first!",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
                            if(response1==JOptionPane.NO_OPTION)
                            {

                            }
                            else if(response1==JOptionPane.YES_OPTION)
                            {
                                EntryDateUpdate EDU=new EntryDateUpdate(null, closable,businessVisaId,screenX,screenY,txtName1.getText()+" "+txtName2.getText()+" "+txtName3.getText()+" "+txtName4.getText()+" "+txtName5.getText(),txtPassportNumber.getText());
                                EDU.setVisible(true);
                            }   
                            else if(response1==JOptionPane.CLOSED_OPTION)
                            {

                            }
                        }
                    }   
                    else
                    {
                        int response2=JOptionPane.showConfirmDialog(null,"There Is No VIsa Details Available for this person!!  Do you want to add the same? ","Please add Visa details first!",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
                        if(response2==JOptionPane.NO_OPTION)
                        {

                        }
                        else if(response2==JOptionPane.YES_OPTION)
                        {
                            VisaDetails VD=new VisaDetails(businessVisaId,txtName1.getText()+" "+txtName2.getText()+" "+txtName3.getText()+" "+txtName4.getText()+" "+txtName5.getText(),txtPassportNumber.getText());
                            AnarTrading.desktopPane1.add(VD);
                            VD.setVisible(true);
                            dispose();
                        }
                        else if(response2==JOptionPane.CLOSED_OPTION)
                        {

                        }
                    }
                    
                    
                }
                else if(response==JOptionPane.YES_OPTION)
                {
                    CheckingReceipt CR=new CheckingReceipt(businessVisaId);
                    AnarTrading.desktopPane1.add(CR);
                    CR.setVisible(true);
                }
                else if(response==JOptionPane.CLOSED_OPTION)
                {

                }
            }
        }
        else
        {
            int response=JOptionPane.showConfirmDialog(null,"There Is No Passport Copy Available for this person!!  Do you want to attach the same? ","Please attach passport copy first!",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
            if(response==JOptionPane.NO_OPTION)
            {

            }
            else if(response==JOptionPane.YES_OPTION)
            {
                PassportCopy PC=new PassportCopy(businessVisaId);
                AnarTrading.desktopPane1.add(PC);
                PC.setVisible(true); 
            }
            else if(response==JOptionPane.CLOSED_OPTION)
            {

            }
        }
    }//GEN-LAST:event_menuItemExitDateActionPerformed

    private void menuItemAttachVisaDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemAttachVisaDetailsActionPerformed
        // TODO add your handling code here:
        addVisaDetails();
        
    }//GEN-LAST:event_menuItemAttachVisaDetailsActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgentDetails;
    private javax.swing.JButton btnAttachCheckingReceipt;
    private javax.swing.JButton btnAttachPassport;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnVisaDetails;
    private javax.swing.JComboBox cmbCompanyId;
    private javax.swing.JComboBox cmbCompanyName;
    private javax.swing.JComboBox cmbNationality;
    private javax.swing.JComboBox cmbProId;
    private javax.swing.JComboBox cmbProName;
    private javax.swing.JComboBox cmbProfession;
    private javax.swing.JComboBox cmbQualification;
    private javax.swing.JComboBox cmbSex;
    private com.toedter.calendar.JDateChooser jDateDateOfBirth;
    private com.toedter.calendar.JDateChooser jDatePassportExpiry;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JPopupMenu jtablePopUp;
    private javax.swing.JMenuItem menuItemAttachCheckingReceipt;
    private javax.swing.JMenuItem menuItemAttachPassport;
    private javax.swing.JMenuItem menuItemAttachVisaDetails;
    private javax.swing.JMenuItem menuItemDelete;
    private javax.swing.JMenuItem menuItemEntryDate;
    private javax.swing.JMenuItem menuItemExitDate;
    private javax.swing.JMenuItem menuItemPrint;
    private javax.swing.JTextField txtName1;
    private javax.swing.JTextField txtName2;
    private javax.swing.JTextField txtName3;
    private javax.swing.JTextField txtName4;
    private javax.swing.JTextField txtName5;
    private javax.swing.JTextField txtPassportNumber;
    // End of variables declaration//GEN-END:variables
    public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat defaultDate = new SimpleDateFormat("yyyy-MM-dd");
    String dateString = "",passportExpiry="",dateOfBirth="",path;
    int businessVisaId,screenX,screenY;
}
