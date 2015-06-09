package org.vijay.contractEmployee;


import org.vijay.employee.*;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.proteanit.sql.DbUtils;
import org.vijay.common.DateCellRenderer;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user
 */
public final class ContractLabourDetails extends javax.swing.JInternalFrame {

    /**
     * Creates new form Accession
     */
    public ContractLabourDetails() {
        initComponents();
        
        btnViewRP.setEnabled(false);
        btnViewPP.setEnabled(false);
        btnViewID.setEnabled(false);
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
        btnDemoblization.setEnabled(false);
        btnChangeSite.setEnabled(false);
        
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        cmbCurrentSiteFill();
        cmbContractCompanyFill();
        viewDbEmployeeDetails();
        jDatejoiningDate.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("date")) {
                    joinigDate = new SimpleDateFormat("yyyy-MM-dd").format(jDatejoiningDate.getDate());
                }
            }  
        });
        jDateppExpiryDate.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("date")) {
                    ppExpiry = new SimpleDateFormat("yyyy-MM-dd").format(jDateppExpiryDate.getDate());
                }
            }  
        });
        jDaterpExpiryDate.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("date")) {
                    rpExpiry = new SimpleDateFormat("yyyy-MM-dd").format(jDaterpExpiryDate.getDate());
                }
            }  
        });
      jtableSelection();
    }
    public ContractLabourDetails(int empId) {
        initComponents();
        setTitle("Contract Employee-Updation/Deletion");
        
        this.empId=empId;
        
        btnViewRP.setEnabled(true);
        btnViewPP.setEnabled(true);
        btnViewID.setEnabled(true);
        btnUpdate.setEnabled(true);
        btnDelete.setEnabled(true);
        btnDemoblization.setEnabled(true);
        btnChangeSite.setEnabled(true);
        btnSave.setEnabled(false);
        
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        cmbCurrentSiteFill();
        cmbContractCompanyFill();
        viewDbEmployeeDetails();
        jDatejoiningDate.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("date")) {
                    joinigDate = new SimpleDateFormat("yyyy-MM-dd").format(jDatejoiningDate.getDate());
                }
            }  
        });
        jDateppExpiryDate.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("date")) {
                    ppExpiry = new SimpleDateFormat("yyyy-MM-dd").format(jDateppExpiryDate.getDate());
                }
            }  
        });
        jDaterpExpiryDate.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("date")) {
                    rpExpiry = new SimpleDateFormat("yyyy-MM-dd").format(jDaterpExpiryDate.getDate());
                }
            }  
        });
      jtableSelection();
      getDetailsUsingEmpId();
    }
    public void getDetailsUsingEmpId()
     {
         try {
            connection c = new connection();
            Connection con = c.conn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT empName,passportNumber,passportExpiry,idNumber,idExpiry,joinDate,site,contractingCompany FROM tbl_conemployee where empId="+empId);
            while (rs.next()) {
                try 
                {
                    txtEmpName.setText(rs.getString("empName"));
                    txtPassportNumber.setText(rs.getString("passportNumber"));
                    jDateppExpiryDate.setDate(dateFormat.parse(rs.getString("passportExpiry")));
                    txtIdNumber.setText(rs.getString("idNumber"));
                    jDaterpExpiryDate.setDate(dateFormat.parse(rs.getString("idExpiry")));
                    jDatejoiningDate.setDate(dateFormat.parse(rs.getString("joinDate")));
                    cmbCurrentSite.setSelectedItem(rs.getString("site"));
                    cmbContracting.setSelectedItem(rs.getString("contractingCompany"));
                    
                } 
                catch (ParseException ex) 
                {
                    Logger.getLogger(LabourDetails.class.getName()).log(Level.SEVERE, null, ex);
                }
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
    public void cmbContractCompanyFill() {
        try {
            connection c = new connection();
            Connection con = c.conn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT contractingCompany FROM tbl_conemployee order by contractingCompany asc");
            while (rs.next()) {
                cmbContracting.addItem(rs.getString(1));
            }
            con.close();
        } catch (SQLException ex) {
            
        }
    }
    public void ViewConLabourForm()
    {
        ContractLabourDetails  CLD=new ContractLabourDetails();
        AnarTrading.desktopPane1.add(CLD);
        CLD.setVisible(true);
        CLD.show();
    }
    public void viewDbEmployeeDetails()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select @i := @i + 1 '"+"SL.NO"+"',empId '"+"ID"+"',empName'"+"NAME"+"',passportNumber '"+"PASSPORT#"+"',passportExpiry'"+"P.EXPIRY"+"',idNumber'"+"ID #"+"',idExpiry '"+"ID.EXIPRY"+"',joinDate'"+"JOIN.DATE"+"',site '"+"SITE"+"',contractingCompany '"+"CONT.COMAPNY"+"' from tbl_conemployee,(SELECT @i := 0) temp where status=0 order by empId desc");
            jTable1.setModel(DbUtils.resultSetToTableModel(rs));
            jTable1.setAutoCreateRowSorter(true);
            con.close();
            

            
            
            jTable1.getColumnModel().getColumn(0).setMaxWidth(50);
            jTable1.getColumnModel().getColumn(1).setMaxWidth(50);
            jTable1.getColumnModel().getColumn(3).setMaxWidth(200);
            jTable1.getColumnModel().getColumn(4).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(5).setMaxWidth(200);
            jTable1.getColumnModel().getColumn(6).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(7).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(8).setMaxWidth(100);
            
            jTable1.getColumnModel().getColumn(4).setCellRenderer(new DateCellRenderer());
            jTable1.getColumnModel().getColumn(6).setCellRenderer(new DateCellRenderer());
            jTable1.getColumnModel().getColumn(7).setCellRenderer(new DateCellRenderer());

            
            jTable1.setShowHorizontalLines( true );
            jTable1.setRowSelectionAllowed( true );

        }
        catch(Exception e)
        {

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
            btnViewPP.setEnabled(true);
            btnViewRP.setEnabled(true);
            btnViewID.setEnabled(true);
            btnDemoblization.setEnabled(true);
            btnChangeSite.setEnabled(true);
            
            empId=Integer.parseInt(jTable1.getValueAt(rowNo,1).toString());
            txtEmpName.setText(jTable1.getValueAt(rowNo,2).toString());
            txtPassportNumber.setText(jTable1.getValueAt(rowNo,3).toString());
            try 
            {
                    jDateppExpiryDate.setDate(defaultDate.parse(jTable1.getValueAt(rowNo,4).toString()));
                    txtIdNumber.setText(jTable1.getValueAt(rowNo,5).toString());
                    jDaterpExpiryDate.setDate(defaultDate.parse(jTable1.getValueAt(rowNo,6).toString()));
                    jDatejoiningDate.setDate(defaultDate.parse(jTable1.getValueAt(rowNo,7).toString()));
                    cmbCurrentSite.setSelectedItem(jTable1.getValueAt(rowNo,8).toString()); 
                    cmbContracting.setSelectedItem(jTable1.getValueAt(rowNo,9).toString());
            } 
            catch (ParseException ex) 
            {
                    Logger.getLogger(LabourDetails.class.getName()).log(Level.SEVERE, null, ex);
            }  
            }
        });
    }
    public void insertIntoDb()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            PreparedStatement ps=con.prepareStatement("INSERT INTO tbl_conemployee(empName,joinDate,passportNumber,idNumber,passportExpiry,idExpiry,site,contractingCompany,status) VALUES(upper(?),?,upper(?),upper(?),?,?,upper(?),upper(?),?)");           
            ps.setString(1, txtEmpName.getText());
            ps.setString(2, joinigDate);
            ps.setString(3, txtPassportNumber.getText());
            ps.setString(4, txtIdNumber.getText());
            ps.setString(5, ppExpiry);
            ps.setString(6, rpExpiry);
            ps.setString(7, cmbCurrentSite.getSelectedItem().toString());
            ps.setString(8, cmbContracting.getSelectedItem().toString());
            ps.setInt(9, 0);
            int i=ps.executeUpdate();
            if(i!=0)
            {
                    dispose();
                    ViewConLabourForm();
            }
            con.close();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(rootPane, e, "ERROR!!", JOptionPane.ERROR_MESSAGE);
            //JOptionPane.showMessageDialog(rootPane,e+"Error SA001");
        }
    }
     public void updateDb()
    {
        
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            PreparedStatement ps=con.prepareStatement("UPDATE tbl_conemployee SET empName=upper(?),passportNumber=?,passportExpiry=upper(?),idNumber=?,idExpiry=?,joinDate=?,site=upper(?),contractingCompany=upper(?) where empId=?");
            ps.setString(1,txtEmpName.getText());
            ps.setString(2,txtPassportNumber.getText());
            ps.setString(3,ppExpiry);
            ps.setString(4,txtIdNumber.getText());
            ps.setString(5,rpExpiry);
            ps.setString(6,joinigDate);
            ps.setString(7,cmbCurrentSite.getSelectedItem().toString());
            ps.setString(8,cmbContracting.getSelectedItem().toString());
            ps.setInt(9, empId);
            int i=ps.executeUpdate();
            if(i!=0)
            {
                    dispose();
                    ViewConLabourForm();
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
                    int i=stmt1.executeUpdate("delete from tbl_conemployee  where empId="+empId);
                    if(i!=0)
                    {
                        dispose();
                        ViewConLabourForm();
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
         

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jtablePopUp = new javax.swing.JPopupMenu();
        menuItemViewPP = new javax.swing.JMenuItem();
        menuItemViewRP = new javax.swing.JMenuItem();
        menuItemViewID = new javax.swing.JMenuItem();
        menuItemPrint = new javax.swing.JMenuItem();
        menuItemDelete = new javax.swing.JMenuItem();
        menuItemDemob = new javax.swing.JMenuItem();
        jLabel3 = new javax.swing.JLabel();
        txtEmpName = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtPassportNumber = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtIdNumber = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        cmbCurrentSite = new javax.swing.JComboBox();
        jLabel14 = new javax.swing.JLabel();
        cmbContracting = new javax.swing.JComboBox();
        jLabel15 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jDatejoiningDate = new com.toedter.calendar.JDateChooser();
        jDateppExpiryDate = new com.toedter.calendar.JDateChooser();
        jDaterpExpiryDate = new com.toedter.calendar.JDateChooser();
        jToolBar1 = new javax.swing.JToolBar();
        btnSave = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jToolBar2 = new javax.swing.JToolBar();
        btnDemoblization = new javax.swing.JButton();
        btnChangeSite = new javax.swing.JButton();
        jToolBar3 = new javax.swing.JToolBar();
        btnTimeSheet = new javax.swing.JButton();
        btnView = new javax.swing.JButton();
        btnViewPP = new javax.swing.JButton();
        btnViewRP = new javax.swing.JButton();
        btnViewID = new javax.swing.JButton();

        jtablePopUp.setLabel("PopUp");

        menuItemViewPP.setText("View PP");
        menuItemViewPP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemViewPPActionPerformed(evt);
            }
        });
        jtablePopUp.add(menuItemViewPP);

        menuItemViewRP.setText("View RP");
        menuItemViewRP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemViewRPActionPerformed(evt);
            }
        });
        jtablePopUp.add(menuItemViewRP);

        menuItemViewID.setText("View ID");
        menuItemViewID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemViewIDActionPerformed(evt);
            }
        });
        jtablePopUp.add(menuItemViewID);

        menuItemPrint.setText("Print");
        menuItemPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemPrintActionPerformed(evt);
            }
        });
        jtablePopUp.add(menuItemPrint);

        menuItemDelete.setText("Delete");
        menuItemDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemDeleteActionPerformed(evt);
            }
        });
        jtablePopUp.add(menuItemDelete);

        menuItemDemob.setText("Demobilize");
        menuItemDemob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemDemobActionPerformed(evt);
            }
        });
        jtablePopUp.add(menuItemDemob);

        setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        setClosable(true);
        setTitle("Contract Employee Details");
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

        jLabel10.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel10.setText("Current Site");

        cmbCurrentSite.setEditable(true);
        AutoCompleteDecorator.decorate(cmbCurrentSite);
        cmbCurrentSite.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cmbCurrentSite.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "--select site--" }));

        jLabel14.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel14.setText("Contracting Company");

        cmbContracting.setEditable(true);
        AutoCompleteDecorator.decorate(cmbContracting);
        cmbContracting.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cmbContracting.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "--select company--" }));

        jLabel15.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel15.setText("Joining Date");

        jLabel1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel1.setText("Last 40 Entries");

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

        jDatejoiningDate.setDateFormatString("yyyy-MM-dd");
        jDatejoiningDate.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jDateppExpiryDate.setDateFormatString("yyyy-MM-dd");
        jDateppExpiryDate.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jDaterpExpiryDate.setDateFormatString("yyyy-MM-dd");
        jDaterpExpiryDate.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

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

        jToolBar2.setFloatable(false);
        jToolBar2.setRollover(true);

        btnDemoblization.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnDemoblization.setText("Demobilize");
        btnDemoblization.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnDemoblization.setFocusable(false);
        btnDemoblization.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnDemoblization.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDemoblization.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDemoblizationActionPerformed(evt);
            }
        });
        jToolBar2.add(btnDemoblization);

        btnChangeSite.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnChangeSite.setText("Change Site");
        btnChangeSite.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnChangeSite.setFocusable(false);
        btnChangeSite.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnChangeSite.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnChangeSite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangeSiteActionPerformed(evt);
            }
        });
        jToolBar2.add(btnChangeSite);

        jToolBar3.setFloatable(false);
        jToolBar3.setRollover(true);

        btnTimeSheet.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnTimeSheet.setMnemonic('v');
        btnTimeSheet.setText("TimeSheet");
        btnTimeSheet.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnTimeSheet.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTimeSheet.setFocusable(false);
        btnTimeSheet.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTimeSheet.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTimeSheet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimeSheetActionPerformed(evt);
            }
        });
        jToolBar3.add(btnTimeSheet);

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel15)
                            .addComponent(jLabel7)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtEmpName)
                            .addComponent(jDatejoiningDate, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtPassportNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jDateppExpiryDate, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE))
                                    .addComponent(cmbCurrentSite, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel12)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtIdNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel11)
                                        .addGap(18, 18, 18)
                                        .addComponent(jDaterpExpiryDate, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(cmbContracting, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jToolBar3, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)
                        .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(320, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jDatejoiningDate, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEmpName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtIdNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtPassportNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jDaterpExpiryDate, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbCurrentSite, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbContracting, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jDateppExpiryDate, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToolBar3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 673, Short.MAX_VALUE)
                .addGap(97, 97, 97))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        
        insertIntoDb();
    }//GEN-LAST:event_btnSaveActionPerformed
    
    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        dispose();
        AnarTrading.menuItemLabourDetails.setEnabled(true);
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // TODO add your handling code here:
        dispose();
        ViewConLabourForm();
       
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
        updateDb();

    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewActionPerformed
        // TODO add your handling code here:
        ConEmployeesSearch CES = new ConEmployeesSearch();
        AnarTrading.desktopPane1.add(CES);
        CES.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnViewActionPerformed

    private void btnViewPPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewPPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnViewPPActionPerformed

    private void btnViewRPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewRPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnViewRPActionPerformed

    private void btnViewIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewIDActionPerformed
        // TODO add your handling code here:
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
            }
        }
    }//GEN-LAST:event_jTable1MouseClicked

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

    private void menuItemViewIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemViewIDActionPerformed
        // TODO add your handling code here:
        ID id = new ID(empId);
        AnarTrading.desktopPane1.add(id);
        id.setVisible(true);
    }//GEN-LAST:event_menuItemViewIDActionPerformed

    private void menuItemPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemPrintActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuItemPrintActionPerformed

    private void menuItemDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemDeleteActionPerformed
        // TODO add your handling code here:
        deleteAction();

    }//GEN-LAST:event_menuItemDeleteActionPerformed

    private void menuItemDemobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemDemobActionPerformed
        // TODO add your handling code here:
        DemobilizationContractEmployee demob=new DemobilizationContractEmployee(null, closable,empId);
        demob.setVisible(true);
        
    }//GEN-LAST:event_menuItemDemobActionPerformed

    private void btnDemoblizationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDemoblizationActionPerformed
        // TODO add your handling code here:
            // TODO add your handling code here:   
        DemobilizationContractEmployee demob=new DemobilizationContractEmployee(null, closable,empId);
        demob.setVisible(true);

    }//GEN-LAST:event_btnDemoblizationActionPerformed

    private void jTable1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTable1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1FocusGained

    private void btnChangeSiteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangeSiteActionPerformed
        // TODO add your handling code here:
        ChangeSiteContractEmployee changeSite=new ChangeSiteContractEmployee(null, closable,empId);
        changeSite.setVisible(true);
    }//GEN-LAST:event_btnChangeSiteActionPerformed

    private void btnTimeSheetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimeSheetActionPerformed
        // TODO add your handling code here:
        ConEmployeeTimeSheet conTimeSheet=new ConEmployeeTimeSheet(null, closable);
        conTimeSheet.setVisible(true);
    }//GEN-LAST:event_btnTimeSheetActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnChangeSite;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnDemoblization;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnTimeSheet;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnView;
    private javax.swing.JButton btnViewID;
    private javax.swing.JButton btnViewPP;
    private javax.swing.JButton btnViewRP;
    private javax.swing.JComboBox cmbContracting;
    private javax.swing.JComboBox cmbCurrentSite;
    private com.toedter.calendar.JDateChooser jDatejoiningDate;
    private com.toedter.calendar.JDateChooser jDateppExpiryDate;
    private com.toedter.calendar.JDateChooser jDaterpExpiryDate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JToolBar jToolBar3;
    private javax.swing.JPopupMenu jtablePopUp;
    private javax.swing.JMenuItem menuItemDelete;
    private javax.swing.JMenuItem menuItemDemob;
    private javax.swing.JMenuItem menuItemPrint;
    private javax.swing.JMenuItem menuItemViewID;
    private javax.swing.JMenuItem menuItemViewPP;
    private javax.swing.JMenuItem menuItemViewRP;
    private javax.swing.JTextField txtEmpName;
    private javax.swing.JTextField txtIdNumber;
    private javax.swing.JTextField txtPassportNumber;
    // End of variables declaration//GEN-END:variables

   Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
   Point middle = new Point(0,0);
   public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
   DateFormat defaultDate = new SimpleDateFormat("yyyy-MM-dd");
   String dateString = "",joinigDate,ppExpiry,rpExpiry;
   public int empId;

}
