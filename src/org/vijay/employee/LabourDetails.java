package org.vijay.employee;


import org.vijay.common.connection;
import org.vijay.common.AutoCompleteDecorator;
import org.vijay.common.AnarTrading;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
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
import javax.swing.RowFilter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import net.proteanit.sql.DbUtils;
import org.vijay.common.CurrentWorkingDirectory;
import org.vijay.common.DateCellRenderer;
import org.vijay.report.ReportView;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user
 */
public final class LabourDetails extends javax.swing.JInternalFrame {

    /**
     * Creates new form Accession
     */
    public LabourDetails() {
        initComponents();
    
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        cmbProfessionFill();
        cmbNationalityFill();

        cmbfirstPartyFill();    
        cmbProfession.setEditable(true);
        cmbNationality.setEditable(true);
        cmbfirstParty.setEditable(true);
        cmbEmployeeType.setEditable(true);
        btnViewRP.setEnabled(false);
        btnViewPP.setEnabled(false);
        btnViewID.setEnabled(false);
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
        btnLeftJob.setEnabled(false);
        
        cmbNationality.addItem("");
        cmbProfession.addItem("");
        cmbfirstParty.addItem("");

        cmbNationality.setSelectedItem("");
        cmbProfession.setSelectedItem("");

        cmbfirstParty.setSelectedItem("");



        
        AutoCompleteDecorator.decorate(cmbNationality);
        AutoCompleteDecorator.decorate(cmbProfession);
        AutoCompleteDecorator.decorate(cmbfirstParty);
        AutoCompleteDecorator.decorate(cmbEmployeeType);
        viewDbEmployeeDetails();
        setLocation(middle);
         jDateChooserDate.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("date")) {
                    todaydate = new SimpleDateFormat("yyyy-MM-dd").format(jDateChooserDate.getDate());
                }
            }  
        });
         jDateChooserDOB.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("date")) {
                    dob = new SimpleDateFormat("yyyy-MM-dd").format(jDateChooserDOB.getDate());
                }
            }  
        });
         jDateChooserPPExpiry.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("date")) {
                    ppExpiry = new SimpleDateFormat("yyyy-MM-dd").format(jDateChooserPPExpiry.getDate());
                }
            }  
        });
         jDateChooserRPExpiry.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("date")) {
                    rpExpiry = new SimpleDateFormat("yyyy-MM-dd").format(jDateChooserRPExpiry.getDate());
                }
            }  
        });
         CurrentWorkingDirectory CWD=new CurrentWorkingDirectory();
         path=CWD.getpath();
         jtableSelection();
    }
    public LabourDetails(int empId1) {
        initComponents();
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.empId=empId1;
        cmbProfessionFill();
        cmbNationalityFill();
        cmbfirstPartyFill();   
        cmbProfession.setEditable(true);
        cmbNationality.setEditable(true);
        cmbfirstParty.setEditable(true);




        btnViewRP.setEnabled(true);
        btnViewPP.setEnabled(true);
        btnViewID.setEnabled(true);
        btnSave.setEnabled(false);
        btnView.setEnabled(false);
        
        cmbNationality.addItem("");
        cmbProfession.addItem("");

        cmbfirstParty.addItem("");


        
        cmbNationality.setSelectedItem("");
        cmbProfession.setSelectedItem("");

        cmbfirstParty.setSelectedItem("");

        AutoCompleteDecorator.decorate(cmbNationality);
        AutoCompleteDecorator.decorate(cmbProfession);
        AutoCompleteDecorator.decorate(cmbfirstParty);
        viewDbEmployeeDetails();
        setLocation(middle);
       
        jDateChooserDate.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("date")) {
                    todaydate = new SimpleDateFormat("yyyy-MM-dd").format(jDateChooserDate.getDate());
                }
            }  
        });
         jDateChooserDOB.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("date")) {
                    dob = new SimpleDateFormat("yyyy-MM-dd").format(jDateChooserDOB.getDate());
                }
            }  
        });
         jDateChooserPPExpiry.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("date")) {
                    ppExpiry = new SimpleDateFormat("yyyy-MM-dd").format(jDateChooserPPExpiry.getDate());
                }
            }  
        });
         jDateChooserRPExpiry.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("date")) {
                    rpExpiry = new SimpleDateFormat("yyyy-MM-dd").format(jDateChooserRPExpiry.getDate());
                }
            }  
        });
         jtableSelection();
         getDetailsUsingEmpId();
         
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
            btnViewPP.setEnabled(true);
            btnViewRP.setEnabled(true);
            btnViewID.setEnabled(true);
            btnLeftJob.setEnabled(true);
            
            empId=Integer.parseInt(jTable1.getValueAt(rowNo,1).toString());
            txtEmpName.setText(jTable1.getValueAt(rowNo,2).toString());
            cmbNationality.setSelectedItem(jTable1.getValueAt(rowNo,3).toString());
            cmbProfession.setSelectedItem(jTable1.getValueAt(rowNo,4).toString());
            txtPassportNumber.setText(jTable1.getValueAt(rowNo,5).toString());
            try 
            {
                    jDateChooserPPExpiry.setDate(defaultDate.parse(jTable1.getValueAt(rowNo,6).toString()));
                    jDateChooserRPExpiry.setDate(defaultDate.parse(jTable1.getValueAt(rowNo,7).toString()));
            } 
            catch (ParseException ex) 
            {
                    Logger.getLogger(LabourDetails.class.getName()).log(Level.SEVERE, null, ex);
            }  
            txtIdNumber.setText(jTable1.getValueAt(rowNo,8).toString());
            try 
            {
                    jDateChooserDate.setDate(defaultDate.parse(jTable1.getValueAt(rowNo,9).toString()));
                    jDateChooserDOB.setDate(defaultDate.parse(jTable1.getValueAt(rowNo,10).toString()));
            } 
            catch (ParseException ex) 
            {
                    Logger.getLogger(LabourDetails.class.getName()).log(Level.SEVERE, null, ex);
            }     

            cmbfirstParty.setSelectedItem(jTable1.getValueAt(rowNo,11).toString()); 
            cmbEmployeeType.setSelectedItem(jTable1.getValueAt(rowNo,12).toString()); 

            }
        });
    }
    public void cmbProfessionFill() {
        try {
            connection c = new connection();
            Connection con = c.conn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT profession FROM tbl_labourdetails order by profession asc");
            while (rs.next()) {
                cmbProfession.addItem(rs.getString(1));
            }
            con.close();
        } catch (SQLException ex) {
            
        }
    }
    public void cmbNationalityFill() {
        try {
            connection c = new connection();
            Connection con = c.conn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT nationality FROM tbl_labourdetails order by nationality asc");
            while (rs.next()) {
                cmbNationality.addItem(rs.getString(1));
            }
            con.close();
        } catch (SQLException ex) {
            
        }
    }
    
    public void cmbfirstPartyFill() {
        try {
            connection c = new connection();
            Connection con = c.conn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT firstParty FROM tbl_labourdetails order by firstParty asc");
            while (rs.next()) {
                cmbfirstParty.addItem(rs.getString(1));
            }
            con.close();
        } catch (SQLException ex) {
            
        }
    }
     public void getDetailsUsingEmpId()
     {
         try {
            connection c = new connection();
            Connection con = c.conn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT empName,nationality,profession,passportNumber,passportExpiry,visaExpiry,idNumber,todayDate,dob,currentSite,firstParty,secondParty,contractingCompany,basicSalary FROM tbl_labourdetails where empId="+empId);
            while (rs.next()) {
                txtEmpName.setText(rs.getString("empName"));
                cmbNationality.setSelectedItem(rs.getString("nationality"));
                cmbProfession.setSelectedItem(rs.getString("profession"));
                txtPassportNumber.setText(rs.getString("passportNumber"));
                try 
                {
                    jDateChooserPPExpiry.setDate(dateFormat.parse(rs.getString("passportExpiry")));
                    jDateChooserRPExpiry.setDate(dateFormat.parse(rs.getString("visaExpiry")));
                } 
                catch (ParseException ex) 
                {
                    Logger.getLogger(LabourDetails.class.getName()).log(Level.SEVERE, null, ex);
                }
                txtIdNumber.setText(rs.getString("idNumber"));
                try 
                {
                    jDateChooserDate.setDate(dateFormat.parse(rs.getString("todayDate")));
                    jDateChooserDOB.setDate(dateFormat.parse(rs.getString("dob")));
                } 
                catch (ParseException ex) 
                {
                    Logger.getLogger(LabourDetails.class.getName()).log(Level.SEVERE, null, ex);
                }
                cmbfirstParty.setSelectedItem(rs.getString("firstParty"));
            }
            con.close();
        } catch (SQLException ex) {
            
        }
     }
     public void viewDbEmployeeDetails()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select @i := @i + 1 '"+"SL.NO"+"',empId '"+"ID"+"',empName'"+"NAME"+"',nationality '"+"NATIONALITY"+"',profession '"+"PROFESSION"+"',passportNumber '"+"PASSPORT#"+"',passportExpiry'"+"P.EXPIRY"+"',visaExpiry'"+"ID.EXPIRY"+"',idNumber '"+"ID#"+"',todayDate'"+"DATE"+"',dob'"+"DOB"+"',firstParty '"+"FIRST PARTY"+"',employeeType '"+"EMPLOYEE TYPE"+"' from tbl_labourdetails,(SELECT @i := 0) temp where Status='ENTERED TO COUNTRY' order by empId desc");
            jTable1.setModel(DbUtils.resultSetToTableModel(rs));
            jTable1.setAutoCreateRowSorter(true);
            con.close();           
            jTable1.getColumnModel().getColumn(0).setMaxWidth(50);
            jTable1.getColumnModel().getColumn(1).setMaxWidth(50);
            jTable1.getColumnModel().getColumn(2).setMaxWidth(400);
            jTable1.getColumnModel().getColumn(3).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(4).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(5).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(6).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(7).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(8).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(9).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(10).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(11).setMaxWidth(200);
            jTable1.getColumnModel().getColumn(12).setMaxWidth(100);
            
            jTable1.getColumnModel().getColumn(6).setCellRenderer(new DateCellRenderer());
            jTable1.getColumnModel().getColumn(7).setCellRenderer(new DateCellRenderer());
            jTable1.getColumnModel().getColumn(9).setCellRenderer(new DateCellRenderer());
            jTable1.getColumnModel().getColumn(10).setCellRenderer(new DateCellRenderer());
            jTable1.setShowHorizontalLines( true );
            jTable1.setRowSelectionAllowed( true );

        }
        catch(Exception e)
        {

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
                    int i=stmt1.executeUpdate("delete from tbl_labourdetails  where empId="+empId);
                    if(i!=0)
                    {
                        dispose();
                        ViewLabourForm();
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
     public void updateJobStatus()
     {
         int response=JOptionPane.showConfirmDialog(null,"Is he Left the job?","Confirm",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
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
                    int i=stmt1.executeUpdate("UPDATE tbl_labourdetails SET Status='LEFT' where empId="+empId);
                    if(i!=0)
                    {
                        dispose();
                        ViewLabourForm();
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
     public void searchByEmpId()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select @i := @i + 1 '"+"SL.NO"+"',empId '"+"ID"+"',empName'"+"NAME"+"',nationality '"+"NATIONALITY"+"',profession '"+"PROFESSION"+"',passportNumber '"+"PASSPORT#"+"',passportExpiry'"+"P.EXPIRY"+"',visaExpiry'"+"ID.EXPIRY"+"',idNumber '"+"ID#"+"',todayDate'"+"DATE"+"',dob'"+"DOB"+"',firstParty '"+"FIRST PARTY"+"' from tbl_labourdetails,(SELECT @i := 0) temp where empId='"+txtEmpIdSearch.getText()+"' order by empId desc");
            jTable1.setModel(DbUtils.resultSetToTableModel(rs));
            jTable1.setAutoCreateRowSorter(true);
            con.close();           
            jTable1.getColumnModel().getColumn(0).setMaxWidth(50);
            jTable1.getColumnModel().getColumn(1).setMaxWidth(50);
            jTable1.getColumnModel().getColumn(2).setMaxWidth(400);
            jTable1.getColumnModel().getColumn(3).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(4).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(5).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(6).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(7).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(8).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(9).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(10).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(11).setMaxWidth(200);
            
            jTable1.getColumnModel().getColumn(6).setCellRenderer(new DateCellRenderer());
            jTable1.getColumnModel().getColumn(7).setCellRenderer(new DateCellRenderer());
            jTable1.getColumnModel().getColumn(9).setCellRenderer(new DateCellRenderer());
            jTable1.getColumnModel().getColumn(10).setCellRenderer(new DateCellRenderer());
            jTable1.setShowHorizontalLines( true );
            jTable1.setRowSelectionAllowed( true );

        }
        catch(Exception e)
        {

        }
    }
     public void searchByPassportNumber()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select @i := @i + 1 '"+"SL.NO"+"',empId '"+"ID"+"',empName'"+"NAME"+"',nationality '"+"NATIONALITY"+"',profession '"+"PROFESSION"+"',passportNumber '"+"PASSPORT#"+"',passportExpiry'"+"P.EXPIRY"+"',visaExpiry'"+"ID.EXPIRY"+"',idNumber '"+"ID#"+"',todayDate'"+"DATE"+"',dob'"+"DOB"+"',firstParty '"+"FIRST PARTY"+"' from tbl_labourdetails,(SELECT @i := 0) temp where passportNumber='"+txtPassportNumberSearch.getText()+"' order by empId desc");
            jTable1.setModel(DbUtils.resultSetToTableModel(rs));
            jTable1.setAutoCreateRowSorter(true);
            con.close();           
            jTable1.getColumnModel().getColumn(0).setMaxWidth(50);
            jTable1.getColumnModel().getColumn(1).setMaxWidth(50);
            jTable1.getColumnModel().getColumn(2).setMaxWidth(400);
            jTable1.getColumnModel().getColumn(3).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(4).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(5).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(6).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(7).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(8).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(9).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(10).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(11).setMaxWidth(200);
            
            jTable1.getColumnModel().getColumn(6).setCellRenderer(new DateCellRenderer());
            jTable1.getColumnModel().getColumn(7).setCellRenderer(new DateCellRenderer());
            jTable1.getColumnModel().getColumn(9).setCellRenderer(new DateCellRenderer());
            jTable1.getColumnModel().getColumn(10).setCellRenderer(new DateCellRenderer());
            jTable1.setShowHorizontalLines( true );
            jTable1.setRowSelectionAllowed( true );

        }
        catch(Exception e)
        {

        }
        
    }
     public void searchByQID()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select @i := @i + 1 '"+"SL.NO"+"',empId '"+"ID"+"',empName'"+"NAME"+"',nationality '"+"NATIONALITY"+"',profession '"+"PROFESSION"+"',passportNumber '"+"PASSPORT#"+"',passportExpiry'"+"P.EXPIRY"+"',visaExpiry'"+"ID.EXPIRY"+"',idNumber '"+"ID#"+"',todayDate'"+"DATE"+"',dob'"+"DOB"+"',firstParty '"+"FIRST PARTY"+"' from tbl_labourdetails,(SELECT @i := 0) temp where idNumber='"+txtQidSearch.getText()+"' order by empId desc");
            jTable1.setModel(DbUtils.resultSetToTableModel(rs));
            jTable1.setAutoCreateRowSorter(true);
            con.close();           
            jTable1.getColumnModel().getColumn(0).setMaxWidth(50);
            jTable1.getColumnModel().getColumn(1).setMaxWidth(50);
            jTable1.getColumnModel().getColumn(2).setMaxWidth(400);
            jTable1.getColumnModel().getColumn(3).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(4).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(5).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(6).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(7).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(8).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(9).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(10).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(11).setMaxWidth(200);
            
            jTable1.getColumnModel().getColumn(6).setCellRenderer(new DateCellRenderer());
            jTable1.getColumnModel().getColumn(7).setCellRenderer(new DateCellRenderer());
            jTable1.getColumnModel().getColumn(9).setCellRenderer(new DateCellRenderer());
            jTable1.getColumnModel().getColumn(10).setCellRenderer(new DateCellRenderer());
            jTable1.setShowHorizontalLines( true );
            jTable1.setRowSelectionAllowed( true );

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

        jtablePopUp = new javax.swing.JPopupMenu();
        menuItemProfile = new javax.swing.JMenuItem();
        menuItemSafteyDetails = new javax.swing.JMenuItem();
        menuItemOpenFolder = new javax.swing.JMenuItem();
        menuItemViewPP = new javax.swing.JMenuItem();
        menuItemViewRP = new javax.swing.JMenuItem();
        menuItemViewID = new javax.swing.JMenuItem();
        menuItemRPRenwalForm = new javax.swing.JMenuItem();
        menuItemStatus = new javax.swing.JMenuItem();
        menuItemDelete = new javax.swing.JMenuItem();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel3 = new javax.swing.JLabel();
        txtEmpName = new javax.swing.JTextField();
        Nationality = new javax.swing.JLabel();
        cmbNationality = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        cmbProfession = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        txtPassportNumber = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtIdNumber = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        cmbfirstParty = new javax.swing.JComboBox();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jDateChooserDate = new com.toedter.calendar.JDateChooser();
        jDateChooserDOB = new com.toedter.calendar.JDateChooser();
        jDateChooserPPExpiry = new com.toedter.calendar.JDateChooser();
        jDateChooserRPExpiry = new com.toedter.calendar.JDateChooser();
        jToolBar1 = new javax.swing.JToolBar();
        btnSave = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnLeftJob = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        radioEmpId = new javax.swing.JRadioButton();
        radioPPnumber = new javax.swing.JRadioButton();
        radioQid = new javax.swing.JRadioButton();
        txtEmpIdSearch = new javax.swing.JTextField();
        txtPassportNumberSearch = new javax.swing.JTextField();
        txtQidSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        btnrefresh1 = new javax.swing.JButton();
        jToolBar2 = new javax.swing.JToolBar();
        btnRefresh = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jToolBar3 = new javax.swing.JToolBar();
        btnView = new javax.swing.JButton();
        btnViewPP = new javax.swing.JButton();
        btnViewRP = new javax.swing.JButton();
        btnViewID = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        txtContactNumbers = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cmbEmployeeType = new javax.swing.JComboBox();

        jtablePopUp.setLabel("PopUp");

        menuItemProfile.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        menuItemProfile.setText("Profile");
        menuItemProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemProfileActionPerformed(evt);
            }
        });
        jtablePopUp.add(menuItemProfile);

        menuItemSafteyDetails.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        menuItemSafteyDetails.setText("Safety Details");
        menuItemSafteyDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemSafteyDetailsActionPerformed(evt);
            }
        });
        jtablePopUp.add(menuItemSafteyDetails);

        menuItemOpenFolder.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        menuItemOpenFolder.setMnemonic('o');
        menuItemOpenFolder.setText("Open Folder");
        menuItemOpenFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemOpenFolderActionPerformed(evt);
            }
        });
        jtablePopUp.add(menuItemOpenFolder);

        menuItemViewPP.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        menuItemViewPP.setText("View PP");
        menuItemViewPP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemViewPPActionPerformed(evt);
            }
        });
        jtablePopUp.add(menuItemViewPP);

        menuItemViewRP.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        menuItemViewRP.setText("View RP");
        menuItemViewRP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemViewRPActionPerformed(evt);
            }
        });
        jtablePopUp.add(menuItemViewRP);

        menuItemViewID.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        menuItemViewID.setText("View ID");
        menuItemViewID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemViewIDActionPerformed(evt);
            }
        });
        jtablePopUp.add(menuItemViewID);

        menuItemRPRenwalForm.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        menuItemRPRenwalForm.setText("RP Renewal Form");
        menuItemRPRenwalForm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemRPRenwalFormActionPerformed(evt);
            }
        });
        jtablePopUp.add(menuItemRPRenwalForm);

        menuItemStatus.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        menuItemStatus.setText("Status Update");
        menuItemStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemStatusActionPerformed(evt);
            }
        });
        jtablePopUp.add(menuItemStatus);

        menuItemDelete.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        menuItemDelete.setForeground(new java.awt.Color(255, 0, 0));
        menuItemDelete.setText("Delete");
        menuItemDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemDeleteActionPerformed(evt);
            }
        });
        jtablePopUp.add(menuItemDelete);

        setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        setClosable(true);
        setIconifiable(true);
        setTitle("Employee Details");
        setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        setFrameIcon(null);
        setOpaque(true);

        jLabel3.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel3.setText("Employee Name");

        txtEmpName.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txtEmpName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtEmpNameKeyPressed(evt);
            }
        });

        Nationality.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        Nationality.setText("Nationality");

        cmbNationality.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel5.setText("Profession");

        cmbProfession.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel7.setText("Passport Number");

        txtPassportNumber.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel8.setText("Passport Expiry");

        jLabel12.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel12.setText("Id Number");

        txtIdNumber.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txtIdNumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdNumberActionPerformed(evt);
            }
        });
        txtIdNumber.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtIdNumberKeyPressed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel11.setText("ID/Visa Expiry");

        jLabel9.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel9.setText("Date Of Birth");

        jLabel13.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel13.setText("Sponser Name");

        cmbfirstParty.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jLabel15.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel15.setText("Joining Date");

        jTable1.setFont(new java.awt.Font("Century Gothic", 0, 10)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable1.setOpaque(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable1KeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jDateChooserDate.setDateFormatString("yyyy-MM-dd");
        jDateChooserDate.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jDateChooserDOB.setDateFormatString("yyyy-MM-dd");
        jDateChooserDOB.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jDateChooserPPExpiry.setDateFormatString("yyyy-MM-dd");
        jDateChooserPPExpiry.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jDateChooserRPExpiry.setDateFormatString("yyyy-MM-dd");
        jDateChooserRPExpiry.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

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

        btnLeftJob.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnLeftJob.setText("Left Job");
        btnLeftJob.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnLeftJob.setFocusable(false);
        btnLeftJob.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnLeftJob.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnLeftJob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLeftJobActionPerformed(evt);
            }
        });
        jToolBar1.add(btnLeftJob);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 0, 12))); // NOI18N

        buttonGroup1.add(radioEmpId);
        radioEmpId.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        radioEmpId.setSelected(true);
        radioEmpId.setText("Emp ID");
        radioEmpId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioEmpIdActionPerformed(evt);
            }
        });

        buttonGroup1.add(radioPPnumber);
        radioPPnumber.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        radioPPnumber.setText("PP #");
        radioPPnumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioPPnumberActionPerformed(evt);
            }
        });

        buttonGroup1.add(radioQid);
        radioQid.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        radioQid.setText("Qid");
        radioQid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioQidActionPerformed(evt);
            }
        });

        txtEmpIdSearch.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        txtPassportNumberSearch.setEnabled(false);
        txtPassportNumberSearch.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        txtQidSearch.setEnabled(false);
        txtQidSearch.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        btnSearch.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnSearch.setMnemonic('s');
        btnSearch.setText("Search");
        btnSearch.setToolTipText("");
        btnSearch.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        btnrefresh1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnrefresh1.setMnemonic('s');
        btnrefresh1.setText("Refresh");
        btnrefresh1.setToolTipText("");
        btnrefresh1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnrefresh1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnrefresh1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnrefresh1ActionPerformed(evt);
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
                        .addComponent(radioQid)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtQidSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(radioPPnumber)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtPassportNumberSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(radioEmpId)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                        .addComponent(txtEmpIdSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnrefresh1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSearch)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radioEmpId)
                    .addComponent(txtEmpIdSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radioPPnumber)
                    .addComponent(txtPassportNumberSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radioQid)
                    .addComponent(txtQidSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSearch)
                    .addComponent(btnrefresh1))
                .addContainerGap(39, Short.MAX_VALUE))
        );

        jToolBar2.setFloatable(false);
        jToolBar2.setRollover(true);

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
        jToolBar2.add(btnRefresh);

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
        jToolBar2.add(btnCancel);

        jToolBar3.setFloatable(false);
        jToolBar3.setRollover(true);

        btnView.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnView.setMnemonic('v');
        btnView.setText("View");
        btnView.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnView.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewActionPerformed(evt);
            }
        });
        jToolBar3.add(btnView);

        btnViewPP.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnViewPP.setMnemonic('P');
        btnViewPP.setText("View PP");
        btnViewPP.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnViewPP.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnViewPP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewPPActionPerformed(evt);
            }
        });
        jToolBar3.add(btnViewPP);

        btnViewRP.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnViewRP.setMnemonic('R');
        btnViewRP.setText("View RP");
        btnViewRP.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnViewRP.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnViewRP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewRPActionPerformed(evt);
            }
        });
        jToolBar3.add(btnViewRP);

        btnViewID.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnViewID.setMnemonic('I');
        btnViewID.setText("View ID");
        btnViewID.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnViewID.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnViewID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewIDActionPerformed(evt);
            }
        });
        jToolBar3.add(btnViewID);

        jLabel14.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel14.setText("Ph#");

        txtContactNumbers.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txtContactNumbers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtContactNumbersActionPerformed(evt);
            }
        });
        txtContactNumbers.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtContactNumbersKeyPressed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel4.setText("Employee Type");

        cmbEmployeeType.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cmbEmployeeType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "--select--", "External", "Internal" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jToolBar3, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(Nationality)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel15))
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cmbNationality, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jDateChooserDOB, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                                            .addComponent(jDateChooserPPExpiry, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel12)
                                            .addComponent(jLabel14))
                                        .addGap(13, 13, 13)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtContactNumbers)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(cmbProfession, 0, 180, Short.MAX_VALUE)
                                                    .addComponent(txtIdNumber))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel7)
                                                    .addComponent(jLabel11))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addComponent(jLabel4)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(cmbEmployeeType, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addComponent(txtPassportNumber)
                                                    .addComponent(jDateChooserRPExpiry, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jDateChooserDate, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel13)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cmbfirstParty, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(txtEmpName, javax.swing.GroupLayout.PREFERRED_SIZE, 534, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jDateChooserDate, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cmbfirstParty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEmpName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbEmployeeType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Nationality, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbNationality, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbProfession, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPassportNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtIdNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jDateChooserPPExpiry, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGap(3, 3, 3)
                                            .addComponent(jDateChooserDOB, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtContactNumbers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jDateChooserRPExpiry, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jToolBar3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 585, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(189, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        
        if(txtEmpName.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "Please Enter The Employee Name", "Warning!!", JOptionPane.WARNING_MESSAGE);
        }
        else
        {           
            insertIntoDb();
        }
    }//GEN-LAST:event_btnSaveActionPerformed
    public void ViewLabourForm()
    {
        LabourDetails  labour=new LabourDetails();
        AnarTrading.desktopPane1.add(labour);
        labour.setVisible(true);
        labour.show();
    }
    public void updateDb()
    {
        
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            PreparedStatement ps=con.prepareStatement("UPDATE tbl_labourdetails SET empName=upper(?),nationality=upper(?),profession=upper(?),passportNumber=upper(?),passportExpiry=?,visaExpiry=?,idNumber=upper(?),todayDate=?,dob=?,firstParty=upper(?),employeeType=upper(?) where empId=?");
            ps.setString(1,txtEmpName.getText());
            ps.setString(2,cmbNationality.getSelectedItem().toString());
            ps.setString(3,cmbProfession.getSelectedItem().toString());
            ps.setString(4,txtPassportNumber.getText());
            ps.setString(5,ppExpiry);
            ps.setString(6,rpExpiry);
            ps.setString(7,txtIdNumber.getText());
            ps.setString(8,todaydate);
            ps.setString(9,dob);
            ps.setString(10,cmbfirstParty.getSelectedItem().toString());
            ps.setString(11,cmbEmployeeType.getSelectedItem().toString());
            ps.setInt(12, empId);
            int i=ps.executeUpdate();
            if(i!=0)
            {
                    dispose();
                    ViewLabourForm();
            }
            con.close();  
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(rootPane, e, "ERROR!!", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void clearFields()
    {
        txtEmpName.setText("");
        cmbNationality.setSelectedItem("");
        cmbProfession.setSelectedItem("");
        txtPassportNumber.setText("");
        txtIdNumber.setText("");
        cmbfirstParty.setSelectedItem("");
    }
    public void insertIntoDb()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            PreparedStatement ps=con.prepareStatement("INSERT INTO tbl_labourdetails(empName,nationality,profession,passportNumber,passportExpiry,visaExpiry,idNumber,todayDate,dob,firstParty,Status,employeeType) VALUES(upper(?),upper(?),upper(?),upper(?),upper(?),upper(?),upper(?),upper(?),upper(?),upper(?),upper(?),upper(?))");           
            ps.setString(1, txtEmpName.getText());
            ps.setString(2, cmbNationality.getSelectedItem().toString());
            ps.setString(3, cmbProfession.getSelectedItem().toString());
            ps.setString(4, txtPassportNumber.getText());
            ps.setString(5, ppExpiry);
            ps.setString(6, rpExpiry);
            ps.setString(7, txtIdNumber.getText());
            ps.setString(8, todaydate);
            ps.setString(9, dob);
            ps.setString(10, cmbfirstParty.getSelectedItem().toString());
            ps.setString(11, "ENTERED TO COUNTRY");
            ps.setString(12, cmbEmployeeType.getSelectedItem().toString());
            int i=ps.executeUpdate();
            if(i!=0)
            {
                    
                    dispose();
                    ViewLabourForm();
            }
            con.close();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(rootPane, e, "ERROR!!", JOptionPane.ERROR_MESSAGE);
            //JOptionPane.showMessageDialog(rootPane,e+"Error SA001");
        }
    }
    
    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        dispose();
        AnarTrading.menuItemLabourDetails.setEnabled(true);
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // TODO add your handling code here:
        dispose();
        ViewLabourForm();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void txtEmpNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmpNameKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmpNameKeyPressed

    private void txtIdNumberKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdNumberKeyPressed
        // TODO add your handling code here:
       
    }//GEN-LAST:event_txtIdNumberKeyPressed

    private void txtIdNumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdNumberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdNumberActionPerformed

    private void menuItemViewPPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemViewPPActionPerformed
        // TODO add your handling code here:
        PassportDocument PD = new PassportDocument(empId);
        AnarTrading.desktopPane1.add(PD);
        PD.setVisible(true);
    }//GEN-LAST:event_menuItemViewPPActionPerformed

    private void menuItemViewRPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemViewRPActionPerformed
        // TODO add your handling code here:
        RPDocument RD = new RPDocument(empId);
        AnarTrading.desktopPane1.add(RD);
        RD.setVisible(true);
    }//GEN-LAST:event_menuItemViewRPActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        if(txtEmpName.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "Please Enter The Employee Name", "Warning!!", JOptionPane.WARNING_MESSAGE);
        }
        else
        {
            updateDb();
        }

    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewActionPerformed
        // TODO add your handling code here:
        AdvancedEmployeeSearch AES = new AdvancedEmployeeSearch();
        AnarTrading.desktopPane1.add(AES);
        AES.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnViewActionPerformed

    private void btnViewPPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewPPActionPerformed
        // TODO add your handling code here:
        PassportDocument PD = new PassportDocument(empId);
        AnarTrading.desktopPane1.add(PD);
        PD.setVisible(true);
    }//GEN-LAST:event_btnViewPPActionPerformed

    private void btnViewRPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewRPActionPerformed
        // TODO add your handling code here:
        RPDocument RD = new RPDocument(empId);
        AnarTrading.desktopPane1.add(RD);
        RD.setVisible(true);
    }//GEN-LAST:event_btnViewRPActionPerformed

    private void btnViewIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewIDActionPerformed
        // TODO add your handling code here:
        ID id = new ID(empId);
        AnarTrading.desktopPane1.add(id);
        id.setVisible(true);
    }//GEN-LAST:event_btnViewIDActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        deleteAction(); 
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
                jtablePopUp.show(evt.getComponent(), evt.getX(),evt.getY());
                screenX = evt.getXOnScreen();
                screenY = evt.getYOnScreen();
            }
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
        // TODO add your handling code here:
        int keyCode = evt.getKeyCode();
        if(keyCode == KeyEvent.VK_ENTER)
        {
            LabourDetails LD = new LabourDetails(empId);
            AnarTrading.desktopPane1.add(LD);
            LD.setVisible(true);
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
                    int i=stmt1.executeUpdate("delete from tbl_labourdetails  where empId="+empId);
                    if(i!=0)
                    {
                        dispose();
                        ViewLabourForm();
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

    private void menuItemViewIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemViewIDActionPerformed
        // TODO add your handling code here:
        ID id = new ID(empId);
        AnarTrading.desktopPane1.add(id);
        id.setVisible(true);
    }//GEN-LAST:event_menuItemViewIDActionPerformed

    private void menuItemRPRenwalFormActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemRPRenwalFormActionPerformed
        // TODO add your handling code here:
        if(cmbfirstParty.getSelectedItem().equals("FIXTURE INTERNATIONAL W.L.L"))
        {
            HashMap para=new HashMap();
            para.put("EmpId",empId);
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\RP_Renewal\\RP_Renewal_Fix.jasper"),para);
            re.setVisible(true);
        }
        else if(cmbfirstParty.getSelectedItem().equals("PALACE CLEANING TRADING & CONTG. EST"))
        {
            HashMap para=new HashMap();
            para.put("EmpId",empId);
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\RP_Renewal\\RP_Renewal_PalaceCleaning.jasper"),para);
            re.setVisible(true);
        }
        else if(cmbfirstParty.getSelectedItem().equals("HABITAT INTERNATIONAL TRADING & CONTRACTING CO.W.L.L"))
        {
            
        }
        else if(cmbfirstParty.getSelectedItem().equals("PALACE INTERNATIONAL CONTRACTING CO. W.L.L"))
        {
            
        }
        else if(cmbfirstParty.getSelectedItem().equals("ARD AL JAZEERA CLEANING"))
        {
            
        }
        else if(cmbfirstParty.getSelectedItem().equals("ANAR TRADING & CONTRACTING W.L.L"))
        {
            HashMap para=new HashMap();
            para.put("EmpId",empId);
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\RP_Renewal\\RP_Renewal_Anar.jasper"),para);
            re.setVisible(true);
        }
    }//GEN-LAST:event_menuItemRPRenwalFormActionPerformed

    private void menuItemDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemDeleteActionPerformed
        // TODO add your handling code here:
        deleteAction();
    }//GEN-LAST:event_menuItemDeleteActionPerformed

    private void menuItemStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemStatusActionPerformed
        // TODO add your handling code here:
        //updateJobStatus();
        Status_Update SU = new Status_Update(empId,txtEmpName.getText(),txtIdNumber.getText());
        AnarTrading.desktopPane1.add(SU);
        SU.setVisible(true);
        
    }//GEN-LAST:event_menuItemStatusActionPerformed

    private void btnLeftJobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLeftJobActionPerformed
        // TODO add your handling code here:
        updateJobStatus();
    }//GEN-LAST:event_btnLeftJobActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        if(txtEmpIdSearch.isEnabled())
        {
            searchByEmpId();
        }
        else if(txtPassportNumberSearch.isEnabled())
        {
            searchByPassportNumber();
        }
        else if (txtQidSearch.isEnabled())
        {
            searchByQID();
        }
    }//GEN-LAST:event_btnSearchActionPerformed

    private void radioEmpIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioEmpIdActionPerformed
        // TODO add your handling code here:
        txtEmpIdSearch.setEnabled(true);
        txtPassportNumberSearch.setEnabled(false);
        txtQidSearch.setEnabled(false);
        
    }//GEN-LAST:event_radioEmpIdActionPerformed

    private void radioPPnumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioPPnumberActionPerformed
        // TODO add your handling code here:
        txtEmpIdSearch.setEnabled(false);
        txtPassportNumberSearch.setEnabled(true);
        txtQidSearch.setEnabled(false);
    }//GEN-LAST:event_radioPPnumberActionPerformed

    private void radioQidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioQidActionPerformed
        // TODO add your handling code here:
        txtEmpIdSearch.setEnabled(false);
        txtPassportNumberSearch.setEnabled(false);
        txtQidSearch.setEnabled(true);
    }//GEN-LAST:event_radioQidActionPerformed

    private void btnrefresh1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnrefresh1ActionPerformed
        // TODO add your handling code here:
                dispose();
        ViewLabourForm();
    }//GEN-LAST:event_btnrefresh1ActionPerformed

    private void menuItemSafteyDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemSafteyDetailsActionPerformed
        // TODO add your handling code here:
         SafetyItems SI=new SafetyItems(empId,txtEmpName.getText(),txtIdNumber.getText());
         AnarTrading.desktopPane1.add(SI);
         SI.setVisible(true); 
    }//GEN-LAST:event_menuItemSafteyDetailsActionPerformed

    private void txtContactNumbersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtContactNumbersActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtContactNumbersActionPerformed

    private void txtContactNumbersKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtContactNumbersKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtContactNumbersKeyPressed

    private void menuItemProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemProfileActionPerformed
        // TODO add your handling code here:
        Profile PP=new Profile(empId);
         AnarTrading.desktopPane1.add(PP);
         PP.setVisible(true);
    }//GEN-LAST:event_menuItemProfileActionPerformed

    private void menuItemOpenFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemOpenFolderActionPerformed
        // TODO add your handling code here:
        if(cmbfirstParty.getSelectedItem().equals("FIXTURE INTERNATIONAL W.L.L"))
        {
            folderPath="D:\\Firms\\Fixture International\\Employees\\"+empId+" "+txtEmpName.getText();
        }
        else if(cmbfirstParty.getSelectedItem().equals("ANAR TRADING & CONTRACTING W.L.L"))
        {
            folderPath="D:\\Firms\\Anar"+empId+" "+txtEmpName.getText();
        }
        Runtime runtime = Runtime.getRuntime();        
        try { 
            runtime.exec("explorer.exe "+folderPath);
        } catch (IOException ex) {
            Logger.getLogger(LabourDetails.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_menuItemOpenFolderActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Nationality;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnLeftJob;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnView;
    private javax.swing.JButton btnViewID;
    private javax.swing.JButton btnViewPP;
    private javax.swing.JButton btnViewRP;
    private javax.swing.JButton btnrefresh1;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cmbEmployeeType;
    private javax.swing.JComboBox cmbNationality;
    private javax.swing.JComboBox cmbProfession;
    private javax.swing.JComboBox cmbfirstParty;
    private com.toedter.calendar.JDateChooser jDateChooserDOB;
    private com.toedter.calendar.JDateChooser jDateChooserDate;
    private com.toedter.calendar.JDateChooser jDateChooserPPExpiry;
    private com.toedter.calendar.JDateChooser jDateChooserRPExpiry;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JToolBar jToolBar3;
    private javax.swing.JPopupMenu jtablePopUp;
    private javax.swing.JMenuItem menuItemDelete;
    private javax.swing.JMenuItem menuItemOpenFolder;
    private javax.swing.JMenuItem menuItemProfile;
    private javax.swing.JMenuItem menuItemRPRenwalForm;
    private javax.swing.JMenuItem menuItemSafteyDetails;
    private javax.swing.JMenuItem menuItemStatus;
    private javax.swing.JMenuItem menuItemViewID;
    private javax.swing.JMenuItem menuItemViewPP;
    private javax.swing.JMenuItem menuItemViewRP;
    private javax.swing.JRadioButton radioEmpId;
    private javax.swing.JRadioButton radioPPnumber;
    private javax.swing.JRadioButton radioQid;
    private javax.swing.JTextField txtContactNumbers;
    private javax.swing.JTextField txtEmpIdSearch;
    private javax.swing.JTextField txtEmpName;
    private javax.swing.JTextField txtIdNumber;
    private javax.swing.JTextField txtPassportNumber;
    private javax.swing.JTextField txtPassportNumberSearch;
    private javax.swing.JTextField txtQidSearch;
    // End of variables declaration//GEN-END:variables

   Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
   Point middle = new Point(0,0);
   public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
   DateFormat defaultDate = new SimpleDateFormat("yyyy-MM-dd");
   String dateString = "",todaydate,dob,ppExpiry,rpExpiry,path,folderPath;
   public int empId,screenX,screenY;
   
}
