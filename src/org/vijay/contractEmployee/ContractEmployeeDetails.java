package org.vijay.contractEmployee;


import org.vijay.common.AnarTrading;
import org.vijay.common.connection;
import org.vijay.common.AutoCompleteDecorator;
import org.vijay.employee.TimeSheet;
import java.awt.Dimension;
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
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.proteanit.sql.DbUtils;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user
 */
public final class ContractEmployeeDetails extends javax.swing.JInternalFrame {

    /**
     * Creates new form Accession
     */
    public ContractEmployeeDetails() {
        initComponents();
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        cmbProfessionFill();
        cmbNationalityFill();
        cmbCurrentSiteFill();
        cmbParentFill();
        cmbContractFill();
        cmbProfession.setEditable(true);
        cmbNationality.setEditable(true);
        cmbCurrentSite.setEditable(true);
        cmbParentCompany.setEditable(true);
        cmbConCompany.setEditable(true);
        cmbEmployeeName.setEditable(true);
        
        lblEmployeeName.setEnabled(false);
        cmbEmployeeName.setEnabled(false);

        
        //txtPassportExpiry.setEditable(false);
        //txtVisaExpiry.setEditable(false);
        txtdate.setEditable(false);
        //txtDOB.setEditable(false);
        
        txtdate.setText("1111-11-11");
        txtPassportExpiry.setText("1111-11-11");
        txtVisaExpiry.setText("1111-11-11");
        txtDOB.setText("1111-11-11");
        
        cmbNationality.addItem("");
        cmbProfession.addItem("");
        cmbCurrentSite.addItem("");
        cmbParentCompany.addItem("");
        cmbConCompany.addItem("");
        cmbEmployeeName.addItem("--select employee name--");
        
        cmbNationality.setSelectedItem("");
        cmbProfession.setSelectedItem("");
        cmbCurrentSite.setSelectedItem("");
        cmbParentCompany.setSelectedItem("");
        cmbConCompany.setSelectedItem("");
        cmbEmployeeName.setSelectedItem("--select employee name--");
        
        AutoCompleteDecorator.decorate(cmbNationality);
        AutoCompleteDecorator.decorate(cmbProfession);
        AutoCompleteDecorator.decorate(cmbParentCompany);
        AutoCompleteDecorator.decorate(cmbConCompany);
        AutoCompleteDecorator.decorate(cmbCurrentSite);
        AutoCompleteDecorator.decorate(cmbEmployeeName);
 
        
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
        
        viewDbEmployeeDetails();
        setLocation(middle);
        btnPassportExpiry.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

                @Override
                public void propertyChange(java.beans.PropertyChangeEvent evt) {
                    if (evt.getNewValue() instanceof Date) {
                        passportExpiry((Date) evt.getNewValue());
                    }
                }
            });
        btnVisaExpiry.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

                @Override
                public void propertyChange(java.beans.PropertyChangeEvent evt) {
                    if (evt.getNewValue() instanceof Date) {
                        visaExpiry((Date) evt.getNewValue());
                    }
                }
            });
         btnDOB.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

                @Override
                public void propertyChange(java.beans.PropertyChangeEvent evt) {
                    if (evt.getNewValue() instanceof Date) {
                        dob((Date) evt.getNewValue());
                    }
                }
            });
         btndate.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

                @Override
                public void propertyChange(java.beans.PropertyChangeEvent evt) {
                    if (evt.getNewValue() instanceof Date) {
                        todayDate((Date) evt.getNewValue());
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
            lblShowDocuments.setEnabled(true);
            
            empId=Integer.parseInt(jTable1.getValueAt(rowNo, 0).toString());
            txtEmpName.setText(jTable1.getValueAt(rowNo,1).toString());
            txtdate.setText(jTable1.getValueAt(rowNo,2).toString());
            cmbNationality.setSelectedItem(jTable1.getValueAt(rowNo,3).toString());
            cmbProfession.setSelectedItem(jTable1.getValueAt(rowNo,4).toString());
            txtPassportNumber.setText(jTable1.getValueAt(rowNo,5).toString());
            txtIdNumber.setText(jTable1.getValueAt(rowNo,6).toString());
            txtPassportExpiry.setText(jTable1.getValueAt(rowNo,7).toString());
            txtVisaExpiry.setText(jTable1.getValueAt(rowNo,8).toString()); 
            txtDOB.setText(jTable1.getValueAt(rowNo,9).toString());       
            cmbCurrentSite.setSelectedItem(jTable1.getValueAt(rowNo,10).toString()); 
            cmbParentCompany.setSelectedItem(jTable1.getValueAt(rowNo,11).toString()); 
            cmbConCompany.setSelectedItem(jTable1.getValueAt(rowNo,12).toString()); 
            }
        });
        
    }
    public void cmbProfessionFill() {
        try {
            connection c = new connection();
            Connection con = c.conn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT profession FROM tbl_conemployee order by profession asc");
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
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT nationality FROM tbl_conemployee order by nationality asc");
            while (rs.next()) {
                cmbNationality.addItem(rs.getString(1));
            }
            con.close();
        } catch (SQLException ex) {
            
        }
    }
    public void cmbCurrentSiteFill() {
        try {
            connection c = new connection();
            Connection con = c.conn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT site FROM tbl_conemployee order by site asc");
            while (rs.next()) {
                cmbCurrentSite.addItem(rs.getString(1));
            }
            con.close();
        } catch (SQLException ex) {
            
        }
    }
    public void cmbParentFill() {
        try {
            connection c = new connection();
            Connection con = c.conn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT parentCompany FROM tbl_conemployee order by parentCompany asc");
            while (rs.next()) {
                cmbParentCompany.addItem(rs.getString(1));
            }
            con.close();
        } catch (SQLException ex) {
            
        }
    }
    public void cmbContractFill() {
        try {
            connection c = new connection();
            Connection con = c.conn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT contractingCompany FROM tbl_conemployee order by contractingCompany asc");
            while (rs.next()) {
                cmbConCompany.addItem(rs.getString(1));
            }
            con.close();
        } catch (SQLException ex) {
            
        }
    }
    public void cmbEmployeeNameFill() {
        try {
            connection c = new connection();
            Connection con = c.conn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT empName FROM tbl_conemployee order by empName asc");
            while (rs.next()) {
                cmbEmployeeName.addItem(rs.getString(1));
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
            ResultSet rs=stmt.executeQuery("select empId '"+"ID"+"',empName'"+"NAME"+"',joinDate '"+"DATE"+"',nationality '"+"NATIONALITY"+"',profession '"+"PROFESSION"+"',passportNumber'"+"PASSPORT#"+"',idNumber'"+"ID#"+"',passportExpiry '"+"P.EXPIRY"+"',idExpiry'"+"ID.EXPIRY"+"',dob'"+"DOB"+"',site '"+"SITE"+"',parentCompany '"+"PARENT COMPANY"+"',contractingCompany '"+"CON.COMPANY"+"' from tbl_conemployee order by empId desc");
            jTable1.setModel(DbUtils.resultSetToTableModel(rs));
            con.close();
            jTable1.getColumnModel().getColumn(0).setMinWidth(0);
            jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
            jTable1.getColumnModel().getColumn(0).setWidth(0);
            jTable1.setShowHorizontalLines( true );
            jTable1.setRowSelectionAllowed( true );

        }
        catch(Exception e)
        {

        }
    }
    public void passportExpiry(String dateString)
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
                this.passportExpiry(date);
    }
     public void passportExpiry(Date date)
     {
        if (date != null)
        dateString = dateFormat.format(date);
        txtPassportExpiry.setText(dateString);
        btnPassportExpiry.setTargetDate(date);
     }
     public void dob(String dateString)
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
                this.passportExpiry(date);
    }
     public void dob(Date date)
     {
        if (date != null)
        dateString = dateFormat.format(date);
        txtDOB.setText(dateString);
        btnDOB.setTargetDate(date);
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
                this.passportExpiry(date);
    }
     public void todayDate(Date date)
     {
        if (date != null)
        dateString = dateFormat.format(date);
        txtdate.setText(dateString);
        btndate.setTargetDate(date);
     }
     public void visaExpiry(String dateString)
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
                this.passportExpiry(date);
    }
     public void visaExpiry(Date date)
     {
        if (date != null)
        dateString = dateFormat.format(date);
        txtVisaExpiry.setText(dateString);
        btnVisaExpiry.setTargetDate(date);
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
        jMenuItemViewDocuments = new javax.swing.JMenuItem();
        jMenuItemTimeSheet = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        Nationality = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtPassportNumber = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtPassportExpiry = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtIdNumber = new javax.swing.JTextField();
        txtVisaExpiry = new javax.swing.JTextField();
        cmbNationality = new javax.swing.JComboBox();
        cmbProfession = new javax.swing.JComboBox();
        txtEmpName = new javax.swing.JTextField();
        btnPassportExpiry = new net.sourceforge.jcalendarbutton.JCalendarButton();
        btnVisaExpiry = new net.sourceforge.jcalendarbutton.JCalendarButton();
        txtDOB = new javax.swing.JTextField();
        btnDOB = new net.sourceforge.jcalendarbutton.JCalendarButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtdate = new javax.swing.JTextField();
        btndate = new net.sourceforge.jcalendarbutton.JCalendarButton();
        jLabel15 = new javax.swing.JLabel();
        cmbParentCompany = new javax.swing.JComboBox();
        cmbConCompany = new javax.swing.JComboBox();
        jLabel16 = new javax.swing.JLabel();
        cmbCurrentSite = new javax.swing.JComboBox();
        lblEmployeeName = new javax.swing.JLabel();
        cmbEmployeeName = new javax.swing.JComboBox();
        lblEditEmployeeDetails = new javax.swing.JLabel();
        lblShowDocuments = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        lblSearch = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        btnSave = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();

        jtablePopUp.setLabel("PopUp");

        jMenuItemViewDocuments.setText("View Documents");
        jMenuItemViewDocuments.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemViewDocumentsActionPerformed(evt);
            }
        });
        jtablePopUp.add(jMenuItemViewDocuments);

        jMenuItemTimeSheet.setText("Add Time Sheet");
        jMenuItemTimeSheet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemTimeSheetActionPerformed(evt);
            }
        });
        jtablePopUp.add(jMenuItemTimeSheet);

        setTitle("Contract Employee Details");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/Library.png"))); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Employee Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Verdana", 0, 10))); // NOI18N
        jPanel1.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel3.setText("Employee Name");

        Nationality.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        Nationality.setText("Nationality");

        jLabel5.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel5.setText("Profession");

        jLabel7.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel7.setText("Passport Number");

        txtPassportNumber.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel8.setText("Passport Expiry");

        txtPassportExpiry.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel11.setText("ID/Visa Expiry");

        jLabel12.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel12.setText("Id Number");

        txtIdNumber.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
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

        txtVisaExpiry.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        cmbNationality.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        cmbProfession.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        txtEmpName.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtEmpName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtEmpNameKeyPressed(evt);
            }
        });

        btnPassportExpiry.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/calendar-icon.png"))); // NOI18N

        btnVisaExpiry.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/calendar-icon.png"))); // NOI18N

        txtDOB.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        btnDOB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/calendar-icon.png"))); // NOI18N

        jLabel9.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel9.setText("Date Of Birth");

        jLabel13.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel13.setText("Parent Company");

        txtdate.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        btndate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/calendar-icon.png"))); // NOI18N

        jLabel15.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel15.setText("Date");

        cmbParentCompany.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        cmbConCompany.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        jLabel16.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel16.setText("Con.Company");

        cmbCurrentSite.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        lblEmployeeName.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        lblEmployeeName.setText("Employee Name");

        cmbEmployeeName.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        cmbEmployeeName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbEmployeeNameActionPerformed(evt);
            }
        });

        lblEditEmployeeDetails.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        lblEditEmployeeDetails.setForeground(new java.awt.Color(204, 0, 204));
        lblEditEmployeeDetails.setText("Edit Employee Details>>");
        lblEditEmployeeDetails.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblEditEmployeeDetails.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblEditEmployeeDetailsMouseClicked(evt);
            }
        });

        lblShowDocuments.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        lblShowDocuments.setForeground(new java.awt.Color(204, 0, 204));
        lblShowDocuments.setText("View Documents>>");
        lblShowDocuments.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblShowDocuments.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblShowDocumentsMouseClicked(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel18.setText("Current Site");

        lblSearch.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        lblSearch.setForeground(new java.awt.Color(204, 0, 204));
        lblSearch.setText("Search>>");
        lblSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSearchMouseClicked(evt);
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
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(Nationality)
                            .addComponent(jLabel5)
                            .addComponent(jLabel7)
                            .addComponent(lblEmployeeName))
                        .addGap(82, 82, 82)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblEditEmployeeDetails)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(cmbEmployeeName, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtdate, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btndate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cmbNationality, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbProfession, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtPassportNumber)
                            .addComponent(txtEmpName)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel13)
                            .addComponent(jLabel16))
                        .addGap(84, 84, 84)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbParentCompany, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtDOB, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                                    .addComponent(txtPassportExpiry))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(btnDOB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnPassportExpiry, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel18))
                                .addGap(43, 43, 43)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(txtVisaExpiry, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnVisaExpiry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(cmbCurrentSite, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(txtIdNumber)
                            .addComponent(cmbConCompany, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblShowDocuments)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblSearch)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(lblEditEmployeeDetails)
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbEmployeeName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEmployeeName, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(93, 93, 93)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtEmpName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbNationality, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Nationality, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbProfession, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPassportNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtIdNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtVisaExpiry, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnVisaExpiry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnPassportExpiry, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtPassportExpiry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnDOB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtDOB, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cmbCurrentSite, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbParentCompany, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbConCompany, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblShowDocuments)
                            .addComponent(lblSearch)))
                    .addComponent(btndate, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
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

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Controls", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Verdana", 0, 10))); // NOI18N
        jPanel3.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        btnSave.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/SAVE.PNG"))); // NOI18N
        btnSave.setText("Save");
        btnSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnUpdate.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/MODIFY.PNG"))); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/DELETE.PNG"))); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnCancel.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/CANCEL.PNG"))); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnRefresh.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
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
                .addContainerGap()
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 1, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
                .addContainerGap())
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
        ContractEmployeeDetails  CE=new ContractEmployeeDetails();
        AnarTrading.desktopPane1.add(CE);
        CE.setVisible(true);
        CE.show();
    }
    public void updateDb()
    {
        
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            PreparedStatement ps=con.prepareStatement("UPDATE tbl_conemployee SET empName=upper(?),joinDate=?,nationality=upper(?),profession=upper(?),passportNumber=?,idNumber=?,passportExpiry=?,idExpiry=?,dob=?,site=upper(?),parentCompany=upper(?),contractingCompany=upper(?)where empId=?");
            ps.setString(1,txtEmpName.getText());
            ps.setString(2,txtdate.getText());
            ps.setString(3,cmbNationality.getSelectedItem().toString());
            ps.setString(4,cmbProfession.getSelectedItem().toString());
            ps.setString(5,txtPassportNumber.getText());
            ps.setString(6,txtIdNumber.getText());
            ps.setString(7,txtPassportExpiry.getText());
            ps.setString(8,txtVisaExpiry.getText());
            ps.setString(9,txtDOB.getText());
            ps.setString(10,cmbCurrentSite.getSelectedItem().toString());
            ps.setString(11,cmbParentCompany.getSelectedItem().toString());
            ps.setString(12,cmbConCompany.getSelectedItem().toString());
            ps.setInt(13, empId);
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
            JOptionPane.showMessageDialog(rootPane, e);
        }
    }
    public void clearFields()
    {
        txtdate.setText("1111-11-11");
        txtEmpName.setText("");
        cmbNationality.setSelectedItem("");
        cmbProfession.setSelectedItem("");
        txtPassportNumber.setText("");
        txtIdNumber.setText("");
        txtPassportExpiry.setText("1111-11-11");
        txtVisaExpiry.setText("1111-11-11");
        txtDOB.setText("1111-11-11");
        cmbCurrentSite.setSelectedItem("");
        cmbParentCompany.setSelectedItem("");
        cmbConCompany.setSelectedItem("");
        
        
    }
    public void insertIntoDb()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            PreparedStatement ps=con.prepareStatement("INSERT INTO tbl_conemployee(empName,joinDate,nationality,profession,passportNumber,idNumber,passportExpiry,idExpiry,dob,site,parentCompany,contractingCompany) VALUES(upper(?),?,upper(?),upper(?),?,?,?,?,?,upper(?),upper(?),upper(?))");           
            ps.setString(1, txtEmpName.getText());
            ps.setString(2, txtdate.getText());
            ps.setString(3, cmbNationality.getSelectedItem().toString());
            ps.setString(4, cmbProfession.getSelectedItem().toString());
            ps.setString(5, txtPassportNumber.getText());
            ps.setString(6, txtIdNumber.getText());
            ps.setString(7, txtPassportExpiry.getText());
            ps.setString(8, txtVisaExpiry.getText()); 
            ps.setString(9, txtDOB.getText());
            ps.setString(10, cmbCurrentSite.getSelectedItem().toString());
            ps.setString(11, cmbParentCompany.getSelectedItem().toString());
            ps.setString(12, cmbConCompany.getSelectedItem().toString());
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
            JOptionPane.showMessageDialog(rootPane,e+"Error SA001");
        }
    }
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
                    int i=stmt1.executeUpdate("delete from tbl_conemployee  where empId="+empId);
                    dispose();
                    ViewLabourForm();
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
        AnarTrading.menuItemContractEmployee.setEnabled(true);
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

    private void lblShowDocumentsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblShowDocumentsMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lblShowDocumentsMouseClicked

    private void jMenuItemViewDocumentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemViewDocumentsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItemViewDocumentsActionPerformed

    private void jMenuItemTimeSheetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemTimeSheetActionPerformed
        // TODO add your handling code here:
        TimeSheet TS = new TimeSheet(empId);
        AnarTrading.desktopPane1.add(TS);
        TS.setVisible(true);
        TS.show();
    }//GEN-LAST:event_jMenuItemTimeSheetActionPerformed

    private void lblEditEmployeeDetailsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEditEmployeeDetailsMouseClicked
        // TODO add your handling code here:
        clearFields();
        lblShowDocuments.setEnabled(true);
        lblEmployeeName.setEnabled(true);
        cmbEmployeeName.setEnabled(true);    
        cmbEmployeeNameFill();
        btnSave.setEnabled(false);
        btnUpdate.setEnabled(true);
        btnDelete.setEnabled(true);
        jTable1.setEnabled(false);
    }//GEN-LAST:event_lblEditEmployeeDetailsMouseClicked

    private void cmbEmployeeNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEmployeeNameActionPerformed
        // TODO add your handling code here:
        clearFields();
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            PreparedStatement ps=con.prepareStatement("SELECT * FROM tbl_conemployee WHERE empName=?");
            ps.setString(1, cmbEmployeeName.getSelectedItem().toString());
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                 empId=rs.getInt(1);
                 txtEmpName.setText(rs.getString(2));
                 txtdate.setText(rs.getString(3));
                 cmbNationality.setSelectedItem(rs.getString(4));
                 cmbProfession.setSelectedItem(rs.getString(5));
                 txtPassportNumber.setText(rs.getString(6));
                 txtIdNumber.setText(rs.getString(7));
                 txtPassportExpiry.setText(rs.getString(8));
                 txtVisaExpiry.setText(rs.getString(9));
                 txtDOB.setText(rs.getString(10));
                 cmbCurrentSite.setSelectedItem(rs.getString(11));
                 cmbParentCompany.setSelectedItem(rs.getString(12));
                 cmbConCompany.setSelectedItem(rs.getString(13));        
            }
        }
        catch(Exception e)
        {

        }
    }//GEN-LAST:event_cmbEmployeeNameActionPerformed

    private void lblSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSearchMouseClicked
        // TODO add your handling code here:
        AdvancedContractEmployeeSearch ACE = new AdvancedContractEmployeeSearch();
        AnarTrading.desktopPane1.add(ACE);
        ACE.setVisible(true);
        ACE.show();
    }//GEN-LAST:event_lblSearchMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Nationality;
    private javax.swing.JButton btnCancel;
    private net.sourceforge.jcalendarbutton.JCalendarButton btnDOB;
    private javax.swing.JButton btnDelete;
    private net.sourceforge.jcalendarbutton.JCalendarButton btnPassportExpiry;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUpdate;
    private net.sourceforge.jcalendarbutton.JCalendarButton btnVisaExpiry;
    private net.sourceforge.jcalendarbutton.JCalendarButton btndate;
    private javax.swing.JComboBox cmbConCompany;
    private javax.swing.JComboBox cmbCurrentSite;
    private javax.swing.JComboBox cmbEmployeeName;
    private javax.swing.JComboBox cmbNationality;
    private javax.swing.JComboBox cmbParentCompany;
    private javax.swing.JComboBox cmbProfession;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuItem jMenuItemTimeSheet;
    private javax.swing.JMenuItem jMenuItemViewDocuments;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JPopupMenu jtablePopUp;
    private javax.swing.JLabel lblEditEmployeeDetails;
    private javax.swing.JLabel lblEmployeeName;
    private javax.swing.JLabel lblSearch;
    private javax.swing.JLabel lblShowDocuments;
    private javax.swing.JTextField txtDOB;
    private javax.swing.JTextField txtEmpName;
    private javax.swing.JTextField txtIdNumber;
    private javax.swing.JTextField txtPassportExpiry;
    private javax.swing.JTextField txtPassportNumber;
    private javax.swing.JTextField txtVisaExpiry;
    private javax.swing.JTextField txtdate;
    // End of variables declaration//GEN-END:variables

   Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
   Point middle = new Point(0,0);
   public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
   DateFormat defaultDate = new SimpleDateFormat("yyyy-MM-dd");
   String dateString = "";
   public int empId;
}
