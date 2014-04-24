package org.vijay.employee;


import org.vijay.common.connection;
import org.vijay.common.AutoCompleteDecorator;
import org.vijay.common.AnarTrading;
import java.awt.Dimension;
import static java.awt.Frame.MAXIMIZED_BOTH;
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
public final class LabourDetails extends javax.swing.JInternalFrame {

    /**
     * Creates new form Accession
     */
    public LabourDetails() {
        initComponents();
        cmbProfessionFill();
        cmbNationalityFill();
        cmbCurrentSiteFill();
        cmbfirstPartyFill();
        cmbSecondPartyFill();
        cmbContractCompanyFill();     
        cmbProfession.setEditable(true);
        cmbNationality.setEditable(true);
        cmbCurrentSite.setEditable(true);
        cmbfirstParty.setEditable(true);
        cmbSecondParty.setEditable(true);

        cmbContracting.setEditable(true);

        btnViewRP.setEnabled(false);
        btnViewPP.setEnabled(false);
        btnViewID.setEnabled(false);
        
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
        cmbfirstParty.addItem("");
        cmbSecondParty.addItem("");
        cmbContracting.addItem("");

        
        cmbNationality.setSelectedItem("");
        cmbProfession.setSelectedItem("");
        cmbCurrentSite.setSelectedItem("");
        cmbfirstParty.setSelectedItem("");
        cmbSecondParty.setSelectedItem("");
        cmbContracting.setSelectedItem("");

        
        AutoCompleteDecorator.decorate(cmbNationality);
        AutoCompleteDecorator.decorate(cmbProfession);
        AutoCompleteDecorator.decorate(cmbfirstParty);
        AutoCompleteDecorator.decorate(cmbSecondParty);
        AutoCompleteDecorator.decorate(cmbCurrentSite);

        AutoCompleteDecorator.decorate(cmbContracting);
        
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
    }
    public LabourDetails(int empId) {
        initComponents();
        this.empId=empId;
        cmbProfessionFill();
        cmbNationalityFill();
        cmbCurrentSiteFill();
        cmbfirstPartyFill();
        cmbSecondPartyFill();
        cmbContractCompanyFill();     
        cmbProfession.setEditable(true);
        cmbNationality.setEditable(true);
        cmbCurrentSite.setEditable(true);
        cmbfirstParty.setEditable(true);
        cmbSecondParty.setEditable(true);

        cmbContracting.setEditable(true);

        btnViewRP.setEnabled(true);
        btnViewPP.setEnabled(true);
        btnViewID.setEnabled(true);
        btnSave.setEnabled(false);
        btnView.setEnabled(false);
        
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
        cmbfirstParty.addItem("");
        cmbSecondParty.addItem("");
        cmbContracting.addItem("");

        
        cmbNationality.setSelectedItem("");
        cmbProfession.setSelectedItem("");
        cmbCurrentSite.setSelectedItem("");
        cmbfirstParty.setSelectedItem("");
        cmbSecondParty.setSelectedItem("");
        cmbContracting.setSelectedItem("");

        
        AutoCompleteDecorator.decorate(cmbNationality);
        AutoCompleteDecorator.decorate(cmbProfession);
        AutoCompleteDecorator.decorate(cmbfirstParty);
        AutoCompleteDecorator.decorate(cmbSecondParty);
        AutoCompleteDecorator.decorate(cmbCurrentSite);

        AutoCompleteDecorator.decorate(cmbContracting);
        
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
         getDetailsUsingEmpId();
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
    public void cmbCurrentSiteFill() {
        try {
            connection c = new connection();
            Connection con = c.conn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT currentSite FROM tbl_labourdetails order by currentSite asc");
            while (rs.next()) {
                cmbCurrentSite.addItem(rs.getString(1));
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
    public void cmbSecondPartyFill() {
        try {
            connection c = new connection();
            Connection con = c.conn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT secondParty FROM tbl_labourdetails order by secondParty asc");
            while (rs.next()) {
                cmbSecondParty.addItem(rs.getString(1));
            }
            con.close();
        } catch (SQLException ex) {
            
        }
    }
    public void cmbContractCompanyFill() {
        try {
            connection c = new connection();
            Connection con = c.conn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT contractingCompany FROM tbl_labourdetails order by contractingCompany asc");
            while (rs.next()) {
                cmbContracting.addItem(rs.getString(1));
            }
            con.close();
        } catch (SQLException ex) {
            
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
                txtPassportExpiry.setText(rs.getString("passportExpiry"));
                txtVisaExpiry.setText(rs.getString("visaExpiry"));
                txtIdNumber.setText(rs.getString("idNumber"));
                txtdate.setText(rs.getString("todayDate"));
                txtDOB.setText(rs.getString("dob"));
                cmbCurrentSite.setSelectedItem(rs.getString("currentSite"));
                cmbfirstParty.setSelectedItem(rs.getString("firstParty"));
                cmbSecondParty.setSelectedItem(rs.getString("secondParty"));
                cmbContracting.setSelectedItem(rs.getString("contractingCompany"));
                txtBasicSalary.setText(rs.getString("basicSalary"));
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
        jMenuItemViewDocuments = new javax.swing.JMenuItem();
        jMenuItemTimeSheet = new javax.swing.JMenuItem();
        jPanel3 = new javax.swing.JPanel();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnView = new javax.swing.JButton();
        btnViewPP = new javax.swing.JButton();
        btnViewRP = new javax.swing.JButton();
        btnViewID = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtEmpName = new javax.swing.JTextField();
        Nationality = new javax.swing.JLabel();
        cmbNationality = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        cmbProfession = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        txtPassportNumber = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtPassportExpiry = new javax.swing.JTextField();
        btnPassportExpiry = new net.sourceforge.jcalendarbutton.JCalendarButton();
        jLabel12 = new javax.swing.JLabel();
        txtIdNumber = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtVisaExpiry = new javax.swing.JTextField();
        btnVisaExpiry = new net.sourceforge.jcalendarbutton.JCalendarButton();
        jLabel9 = new javax.swing.JLabel();
        txtDOB = new javax.swing.JTextField();
        btnDOB = new net.sourceforge.jcalendarbutton.JCalendarButton();
        jLabel17 = new javax.swing.JLabel();
        txtBasicSalary = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        cmbCurrentSite = new javax.swing.JComboBox();
        jLabel13 = new javax.swing.JLabel();
        cmbfirstParty = new javax.swing.JComboBox();
        jLabel14 = new javax.swing.JLabel();
        cmbContracting = new javax.swing.JComboBox();
        jLabel16 = new javax.swing.JLabel();
        cmbSecondParty = new javax.swing.JComboBox();
        jLabel15 = new javax.swing.JLabel();
        txtdate = new javax.swing.JTextField();
        btndate = new net.sourceforge.jcalendarbutton.JCalendarButton();

        jtablePopUp.setLabel("PopUp");

        jMenuItemViewDocuments.setText("Add Passport Copy");
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

        setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Employee Details");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/Library.png"))); // NOI18N

        btnSave.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/SAVE.PNG"))); // NOI18N
        btnSave.setMnemonic('s');
        btnSave.setText("Save");
        btnSave.setToolTipText("");
        btnSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnCancel.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/CANCEL.PNG"))); // NOI18N
        btnCancel.setMnemonic('c');
        btnCancel.setText("Cancel");
        btnCancel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnRefresh.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        btnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/clear.png"))); // NOI18N
        btnRefresh.setMnemonic('r');
        btnRefresh.setText("Refresh");
        btnRefresh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        btnUpdate.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/MODIFY.PNG"))); // NOI18N
        btnUpdate.setMnemonic('u');
        btnUpdate.setText("Update");
        btnUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnView.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        btnView.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/view.png"))); // NOI18N
        btnView.setMnemonic('v');
        btnView.setText("View");
        btnView.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewActionPerformed(evt);
            }
        });

        btnViewPP.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        btnViewPP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/view.png"))); // NOI18N
        btnViewPP.setMnemonic('P');
        btnViewPP.setText("View PP");
        btnViewPP.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnViewPP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewPPActionPerformed(evt);
            }
        });

        btnViewRP.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        btnViewRP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/view.png"))); // NOI18N
        btnViewRP.setMnemonic('R');
        btnViewRP.setText("View RP");
        btnViewRP.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnViewRP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewRPActionPerformed(evt);
            }
        });

        btnViewID.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        btnViewID.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/view.png"))); // NOI18N
        btnViewID.setMnemonic('I');
        btnViewID.setText("View ID");
        btnViewID.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnViewID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewIDActionPerformed(evt);
            }
        });

        btnDelete.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/MODIFY.PNG"))); // NOI18N
        btnDelete.setMnemonic('u');
        btnDelete.setText("Delete");
        btnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSave, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCancel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnRefresh, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)))
                    .addComponent(btnView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnViewPP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnViewRP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnViewID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnView, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addComponent(btnViewPP, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnViewRP, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnViewID, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel3.setText("Employee Name");

        txtEmpName.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtEmpName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtEmpNameKeyPressed(evt);
            }
        });

        Nationality.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        Nationality.setText("Nationality");

        cmbNationality.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel5.setText("Profession");

        cmbProfession.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel7.setText("Passport Number");

        txtPassportNumber.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel8.setText("Passport Expiry");

        txtPassportExpiry.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        btnPassportExpiry.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/calendar-icon.png"))); // NOI18N

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

        jLabel11.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel11.setText("ID/Visa Expiry");

        txtVisaExpiry.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        btnVisaExpiry.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/calendar-icon.png"))); // NOI18N

        jLabel9.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel9.setText("Date Of Birth");

        txtDOB.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        btnDOB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/calendar-icon.png"))); // NOI18N

        jLabel17.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel17.setText("Basic Salary");

        txtBasicSalary.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel10.setText("Current Site");

        cmbCurrentSite.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel13.setText("Parent Company");

        cmbfirstParty.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        jLabel14.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel14.setText("Contracting Company");

        cmbContracting.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        jLabel16.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel16.setText("Child Company");

        cmbSecondParty.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        jLabel15.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel15.setText("Date");

        txtdate.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        btndate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/calendar-icon.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(Nationality)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel13)
                    .addComponent(jLabel16)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cmbfirstParty, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbContracting, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cmbNationality, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtPassportExpiry, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                                    .addComponent(txtDOB))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnPassportExpiry, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnDOB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel12)
                            .addComponent(jLabel17))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtBasicSalary)
                            .addComponent(cmbProfession, 0, 180, Short.MAX_VALUE)
                            .addComponent(txtIdNumber))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel11)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtVisaExpiry, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnVisaExpiry, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cmbCurrentSite, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtPassportNumber)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtdate, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btndate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtEmpName)
                    .addComponent(cmbSecondParty, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 200, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btndate, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEmpName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtPassportExpiry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnPassportExpiry, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtIdNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(btnVisaExpiry, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addComponent(txtVisaExpiry, javax.swing.GroupLayout.Alignment.LEADING)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtDOB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtBasicSalary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cmbCurrentSite, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnDOB, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbfirstParty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbContracting, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbSecondParty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 692, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        LabourDetails  labour=new LabourDetails();
        AnarTrading.desktopPane.add(labour);
        labour.setVisible(true);
        labour.show();
    }
    public void updateDb()
    {
        
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            PreparedStatement ps=con.prepareStatement("UPDATE tbl_labourdetails SET empName=upper(?),nationality=upper(?),profession=upper(?),passportNumber=?,passportExpiry=?,visaExpiry=?,idNumber=upper(?),todayDate=?,dob=?,currentSite=upper(?),firstParty=upper(?),secondParty=upper(?),contractingCompany=upper(?),basicSalary=? where empId=?");
            ps.setString(1,txtEmpName.getText());
            ps.setString(2,cmbNationality.getSelectedItem().toString());
            ps.setString(3,cmbProfession.getSelectedItem().toString());
            ps.setString(4,txtPassportNumber.getText());
            ps.setString(5,txtPassportExpiry.getText());
            ps.setString(6,txtVisaExpiry.getText());
            ps.setString(7,txtIdNumber.getText());
            ps.setString(8,txtdate.getText());
            ps.setString(9,txtDOB.getText());
            ps.setString(10,cmbCurrentSite.getSelectedItem().toString());
            ps.setString(11,cmbfirstParty.getSelectedItem().toString());
            ps.setString(12,cmbSecondParty.getSelectedItem().toString());
            ps.setString(13,cmbContracting.getSelectedItem().toString());
            ps.setString(14,txtBasicSalary.getText());
            ps.setInt(15, empId);
            int i=ps.executeUpdate();
            if(i!=0)
            {
                PreparedStatement ps1=con.prepareStatement("INSERT INTO tbl_currentSite(empId,currentSite,fromDate,contractingCompany,basicSalary)SELECT empId,currentSite,todayDate,contractingCompany,basicSalary FROM tbl_labourdetails where empId=?");
                ps1.setInt(1, empId);
                int j=ps1.executeUpdate();
                if(j!=0)
                {
                    dispose();
                }
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
        cmbfirstParty.setSelectedItem("");
        cmbSecondParty.setSelectedItem("");
        
        
    }
    public void insertIntoDb()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            PreparedStatement ps=con.prepareStatement("INSERT INTO tbl_labourdetails(empName,nationality,profession,passportNumber,passportExpiry,visaExpiry,idNumber,todayDate,dob,currentSite,firstParty,secondParty,contractingCompany,basicSalary) VALUES(upper(?),upper(?),upper(?),?,?,?,?,?,?,upper(?),upper(?),upper(?),upper(?),?)");           
            ps.setString(1, txtEmpName.getText());
            ps.setString(2, cmbNationality.getSelectedItem().toString());
            ps.setString(3, cmbProfession.getSelectedItem().toString());
            ps.setString(4, txtPassportNumber.getText());
            ps.setString(5, txtPassportExpiry.getText());
            ps.setString(6, txtVisaExpiry.getText());
            ps.setString(7, txtIdNumber.getText());
            ps.setString(8, txtdate.getText());
            ps.setString(9, txtDOB.getText());
            ps.setString(10, cmbCurrentSite.getSelectedItem().toString());
            ps.setString(11, cmbfirstParty.getSelectedItem().toString());
            ps.setString(12, cmbSecondParty.getSelectedItem().toString());
            ps.setString(13, cmbContracting.getSelectedItem().toString());
            ps.setString(14, txtBasicSalary.getText());
            int i=ps.executeUpdate();
            if(i!=0)
            {
                PreparedStatement ps1=con.prepareStatement("INSERT INTO tbl_currentSite(empId,currentSite,fromDate,contractingCompany,basicSalary)SELECT MAX(empId),currentSite,todayDate,contractingCompany,basicSalary FROM tbl_labourdetails");
                int j=ps1.executeUpdate();
                if(j!=0)
                {
                    JOptionPane.showMessageDialog(null, "New Employee Added", "SUCCESS!!", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    ViewLabourForm();
                }
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

    private void jMenuItemViewDocumentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemViewDocumentsActionPerformed
        // TODO add your handling code here:
        PassportDocument PD = new PassportDocument(empId);
        AnarTrading.desktopPane.add(PD);
        PD.setVisible(true);
        PD.show();
    }//GEN-LAST:event_jMenuItemViewDocumentsActionPerformed

    private void jMenuItemTimeSheetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemTimeSheetActionPerformed
        // TODO add your handling code here:
        TimeSheet TS = new TimeSheet(empId);
        AnarTrading.desktopPane.add(TS);
        TS.setVisible(true);
        TS.show();
    }//GEN-LAST:event_jMenuItemTimeSheetActionPerformed

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
        AnarTrading.desktopPane.add(AES);
        AES.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnViewActionPerformed

    private void btnViewPPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewPPActionPerformed
        // TODO add your handling code here:
        PassportDocument PD = new PassportDocument(empId);
        AnarTrading.desktopPane.add(PD);
        PD.setVisible(true);
    }//GEN-LAST:event_btnViewPPActionPerformed

    private void btnViewRPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewRPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnViewRPActionPerformed

    private void btnViewIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnViewIDActionPerformed

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
                    int i=stmt1.executeUpdate("delete from tbl_labourdetails  where empId="+empId);
                    dispose();
                }
                catch(Exception e)
                {
                    JOptionPane.showMessageDialog(rootPane, e, "ERROR!!", JOptionPane.ERROR_MESSAGE);
                }
            }
            else if(response==JOptionPane.CLOSED_OPTION)
            {

            }
    }//GEN-LAST:event_btnDeleteActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Nationality;
    private javax.swing.JButton btnCancel;
    private net.sourceforge.jcalendarbutton.JCalendarButton btnDOB;
    private javax.swing.JButton btnDelete;
    private net.sourceforge.jcalendarbutton.JCalendarButton btnPassportExpiry;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnView;
    private javax.swing.JButton btnViewID;
    private javax.swing.JButton btnViewPP;
    private javax.swing.JButton btnViewRP;
    private net.sourceforge.jcalendarbutton.JCalendarButton btnVisaExpiry;
    private net.sourceforge.jcalendarbutton.JCalendarButton btndate;
    private javax.swing.JComboBox cmbContracting;
    private javax.swing.JComboBox cmbCurrentSite;
    private javax.swing.JComboBox cmbNationality;
    private javax.swing.JComboBox cmbProfession;
    private javax.swing.JComboBox cmbSecondParty;
    private javax.swing.JComboBox cmbfirstParty;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuItem jMenuItemTimeSheet;
    private javax.swing.JMenuItem jMenuItemViewDocuments;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jtablePopUp;
    private javax.swing.JTextField txtBasicSalary;
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
