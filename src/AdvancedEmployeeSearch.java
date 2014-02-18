
import java.awt.Point;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
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
public class AdvancedEmployeeSearch extends javax.swing.JInternalFrame {

    /**
     * Creates new form AdvancedEmployeeSearch
     */
    public AdvancedEmployeeSearch() {
        initComponents();
        setLocation(middle);
        cmbParentFill();
        cmbChildFill();
        cmbContractFill();
        cmbCurrentSiteFill();
        queryGenerator();
    }
    public void queryGenerator()
    {
        String query="";
        String parent=cmbParent.getSelectedItem().toString();
        String child=cmbChild.getSelectedItem().toString();
        String contract=cmbContract.getSelectedItem().toString();
        String site=cmbCurrentSite.getSelectedItem().toString();
        
        if(chkParent.isSelected()==false && chkChild.isSelected()==false && chkContract.isSelected()==false && chkCurrentSite.isSelected()==false)
        {
            query="select @i := @i + 1 '"+"SL.NO"+"',empId '"+"ID"+"',empName'"+"NAME"+"',nationality '"+"NATIONALITY"+"',profession '"+"PROFESSION"+"',passportNumber '"+"PASSPORT#"+"',passportExpiry'"+"P.EXPIRY"+"',visaExpiry'"+"ID.EXPIRY"+"',idNumber '"+"ID#"+"',todayDate'"+"DATE"+"',dob'"+"DOB"+"',currentSite '"+"SITE"+"',firstParty '"+"FIRST PARTY"+"',secondParty '"+"SECOND PARTY"+"',contractingCompany '"+"CONT.COMAPNY"+"',basicSalary'"+"BASIC"+"' from tbl_labourdetails,(SELECT @i := 0) temp";
            search(query);
        }
        if(chkParent.isSelected()==false && chkChild.isSelected()==false && chkContract.isSelected()==false && chkCurrentSite.isSelected()==true)
        {
            query="select @i := @i + 1 '"+"SL.NO"+"',empId '"+"ID"+"',empName'"+"NAME"+"',nationality '"+"NATIONALITY"+"',profession '"+"PROFESSION"+"',passportNumber '"+"PASSPORT#"+"',passportExpiry'"+"P.EXPIRY"+"',visaExpiry'"+"ID.EXPIRY"+"',idNumber '"+"ID#"+"',todayDate'"+"DATE"+"',dob'"+"DOB"+"',currentSite '"+"SITE"+"',firstParty '"+"FIRST PARTY"+"',secondParty '"+"SECOND PARTY"+"',contractingCompany '"+"CONT.COMAPNY"+"',basicSalary'"+"BASIC"+"' from tbl_labourdetails,(SELECT @i := 0) temp where currentSite=?";
            search(query,site);
        }
        if(chkParent.isSelected()==false && chkChild.isSelected()==false && chkContract.isSelected()==true && chkCurrentSite.isSelected()==false)
        {
            query="select @i := @i + 1 '"+"SL.NO"+"',empId '"+"ID"+"',empName'"+"NAME"+"',nationality '"+"NATIONALITY"+"',profession '"+"PROFESSION"+"',passportNumber '"+"PASSPORT#"+"',passportExpiry'"+"P.EXPIRY"+"',visaExpiry'"+"ID.EXPIRY"+"',idNumber '"+"ID#"+"',todayDate'"+"DATE"+"',dob'"+"DOB"+"',currentSite '"+"SITE"+"',firstParty '"+"FIRST PARTY"+"',secondParty '"+"SECOND PARTY"+"',contractingCompany '"+"CONT.COMAPNY"+"',basicSalary'"+"BASIC"+"' from tbl_labourdetails,(SELECT @i := 0) temp where contractingCompany=?";
            search(query,contract);
        }
        if(chkParent.isSelected()==false && chkChild.isSelected()==false && chkContract.isSelected()==true && chkCurrentSite.isSelected()==true)
        {
            query="select @i := @i + 1 '"+"SL.NO"+"',empId '"+"ID"+"',empName'"+"NAME"+"',nationality '"+"NATIONALITY"+"',profession '"+"PROFESSION"+"',passportNumber '"+"PASSPORT#"+"',passportExpiry'"+"P.EXPIRY"+"',visaExpiry'"+"ID.EXPIRY"+"',idNumber '"+"ID#"+"',todayDate'"+"DATE"+"',dob'"+"DOB"+"',currentSite '"+"SITE"+"',firstParty '"+"FIRST PARTY"+"',secondParty '"+"SECOND PARTY"+"',contractingCompany '"+"CONT.COMAPNY"+"',basicSalary'"+"BASIC"+"' from tbl_labourdetails,(SELECT @i := 0) temp where contractingCompany=? and currentSite=?";
            search(query,contract,site);
        }
        if(chkParent.isSelected()==false && chkChild.isSelected()==true && chkContract.isSelected()==false && chkCurrentSite.isSelected()==false)
        {
            query="select @i := @i + 1 '"+"SL.NO"+"',empId '"+"ID"+"',empName'"+"NAME"+"',nationality '"+"NATIONALITY"+"',profession '"+"PROFESSION"+"',passportNumber '"+"PASSPORT#"+"',passportExpiry'"+"P.EXPIRY"+"',visaExpiry'"+"ID.EXPIRY"+"',idNumber '"+"ID#"+"',todayDate'"+"DATE"+"',dob'"+"DOB"+"',currentSite '"+"SITE"+"',firstParty '"+"FIRST PARTY"+"',secondParty '"+"SECOND PARTY"+"',contractingCompany '"+"CONT.COMAPNY"+"',basicSalary'"+"BASIC"+"' from tbl_labourdetails,(SELECT @i := 0) temp where secondParty=?";
            search(query,child);
        }
        if(chkParent.isSelected()==false && chkChild.isSelected()==true && chkContract.isSelected()==false && chkCurrentSite.isSelected()==true)
        {
            query="select @i := @i + 1 '"+"SL.NO"+"',empId '"+"ID"+"',empName'"+"NAME"+"',nationality '"+"NATIONALITY"+"',profession '"+"PROFESSION"+"',passportNumber '"+"PASSPORT#"+"',passportExpiry'"+"P.EXPIRY"+"',visaExpiry'"+"ID.EXPIRY"+"',idNumber '"+"ID#"+"',todayDate'"+"DATE"+"',dob'"+"DOB"+"',currentSite '"+"SITE"+"',firstParty '"+"FIRST PARTY"+"',secondParty '"+"SECOND PARTY"+"',contractingCompany '"+"CONT.COMAPNY"+"',basicSalary'"+"BASIC"+"' from tbl_labourdetails,(SELECT @i := 0) temp where secondParty=? and currentSite=?";
            search(query,child,site);
        }
        if(chkParent.isSelected()==false && chkChild.isSelected()==true && chkContract.isSelected()==true && chkCurrentSite.isSelected()==false)
        {
            query="select @i := @i + 1 '"+"SL.NO"+"',empId '"+"ID"+"',empName'"+"NAME"+"',nationality '"+"NATIONALITY"+"',profession '"+"PROFESSION"+"',passportNumber '"+"PASSPORT#"+"',passportExpiry'"+"P.EXPIRY"+"',visaExpiry'"+"ID.EXPIRY"+"',idNumber '"+"ID#"+"',todayDate'"+"DATE"+"',dob'"+"DOB"+"',currentSite '"+"SITE"+"',firstParty '"+"FIRST PARTY"+"',secondParty '"+"SECOND PARTY"+"',contractingCompany '"+"CONT.COMAPNY"+"',basicSalary'"+"BASIC"+"' from tbl_labourdetails,(SELECT @i := 0) temp where secondParty=? and contractingCompany=?";
            search(query,child,contract);
        }
        if(chkParent.isSelected()==false && chkChild.isSelected()==true && chkContract.isSelected()==true && chkCurrentSite.isSelected()==true)
        {
            query="select @i := @i + 1 '"+"SL.NO"+"',empId '"+"ID"+"',empName'"+"NAME"+"',nationality '"+"NATIONALITY"+"',profession '"+"PROFESSION"+"',passportNumber '"+"PASSPORT#"+"',passportExpiry'"+"P.EXPIRY"+"',visaExpiry'"+"ID.EXPIRY"+"',idNumber '"+"ID#"+"',todayDate'"+"DATE"+"',dob'"+"DOB"+"',currentSite '"+"SITE"+"',firstParty '"+"FIRST PARTY"+"',secondParty '"+"SECOND PARTY"+"',contractingCompany '"+"CONT.COMAPNY"+"',basicSalary'"+"BASIC"+"' from tbl_labourdetails,(SELECT @i := 0) temp where secondParty=? and contractingCompany=? and currentSite=?";
            search(query,child,contract,site);
        }
        
        if(chkParent.isSelected()==true && chkChild.isSelected()==false && chkContract.isSelected()==false && chkCurrentSite.isSelected()==false)
        {
            query="select @i := @i + 1 '"+"SL.NO"+"',empId '"+"ID"+"',empName'"+"NAME"+"',nationality '"+"NATIONALITY"+"',profession '"+"PROFESSION"+"',passportNumber '"+"PASSPORT#"+"',passportExpiry'"+"P.EXPIRY"+"',visaExpiry'"+"ID.EXPIRY"+"',idNumber '"+"ID#"+"',todayDate'"+"DATE"+"',dob'"+"DOB"+"',currentSite '"+"SITE"+"',firstParty '"+"FIRST PARTY"+"',secondParty '"+"SECOND PARTY"+"',contractingCompany '"+"CONT.COMAPNY"+"',basicSalary'"+"BASIC"+"' from tbl_labourdetails,(SELECT @i := 0) temp where firstParty=?";
            search(query,parent);
        }
        if(chkParent.isSelected()==true && chkChild.isSelected()==false && chkContract.isSelected()==false && chkCurrentSite.isSelected()==true)
        {
            query="select @i := @i + 1 '"+"SL.NO"+"',empId '"+"ID"+"',empName'"+"NAME"+"',nationality '"+"NATIONALITY"+"',profession '"+"PROFESSION"+"',passportNumber '"+"PASSPORT#"+"',passportExpiry'"+"P.EXPIRY"+"',visaExpiry'"+"ID.EXPIRY"+"',idNumber '"+"ID#"+"',todayDate'"+"DATE"+"',dob'"+"DOB"+"',currentSite '"+"SITE"+"',firstParty '"+"FIRST PARTY"+"',secondParty '"+"SECOND PARTY"+"',contractingCompany '"+"CONT.COMAPNY"+"',basicSalary'"+"BASIC"+"' from tbl_labourdetails,(SELECT @i := 0) temp where firstParty=? and currentSite=?";
            search(query,parent,site);
        }
        if(chkParent.isSelected()==true && chkChild.isSelected()==false && chkContract.isSelected()==true && chkCurrentSite.isSelected()==false)
        {
            query="select @i := @i + 1 '"+"SL.NO"+"',empId '"+"ID"+"',empName'"+"NAME"+"',nationality '"+"NATIONALITY"+"',profession '"+"PROFESSION"+"',passportNumber '"+"PASSPORT#"+"',passportExpiry'"+"P.EXPIRY"+"',visaExpiry'"+"ID.EXPIRY"+"',idNumber '"+"ID#"+"',todayDate'"+"DATE"+"',dob'"+"DOB"+"',currentSite '"+"SITE"+"',firstParty '"+"FIRST PARTY"+"',secondParty '"+"SECOND PARTY"+"',contractingCompany '"+"CONT.COMAPNY"+"',basicSalary'"+"BASIC"+"' from tbl_labourdetails,(SELECT @i := 0) temp where firstParty=? and contractingCompany=?";
            search(query,parent,contract);
        }
        if(chkParent.isSelected()==true && chkChild.isSelected()==false && chkContract.isSelected()==true && chkCurrentSite.isSelected()==true)
        {
            query="select @i := @i + 1 '"+"SL.NO"+"',empId '"+"ID"+"',empName'"+"NAME"+"',nationality '"+"NATIONALITY"+"',profession '"+"PROFESSION"+"',passportNumber '"+"PASSPORT#"+"',passportExpiry'"+"P.EXPIRY"+"',visaExpiry'"+"ID.EXPIRY"+"',idNumber '"+"ID#"+"',todayDate'"+"DATE"+"',dob'"+"DOB"+"',currentSite '"+"SITE"+"',firstParty '"+"FIRST PARTY"+"',secondParty '"+"SECOND PARTY"+"',contractingCompany '"+"CONT.COMAPNY"+"',basicSalary'"+"BASIC"+"' from tbl_labourdetails,(SELECT @i := 0) temp where firstParty=? and contractingCompany=? and currentSite=?";
            search(query,parent,contract,site);
        }
        if(chkParent.isSelected()==true && chkChild.isSelected()==true && chkContract.isSelected()==false && chkCurrentSite.isSelected()==false)
        {
            query="select @i := @i + 1 '"+"SL.NO"+"',empId '"+"ID"+"',empName'"+"NAME"+"',nationality '"+"NATIONALITY"+"',profession '"+"PROFESSION"+"',passportNumber '"+"PASSPORT#"+"',passportExpiry'"+"P.EXPIRY"+"',visaExpiry'"+"ID.EXPIRY"+"',idNumber '"+"ID#"+"',todayDate'"+"DATE"+"',dob'"+"DOB"+"',currentSite '"+"SITE"+"',firstParty '"+"FIRST PARTY"+"',secondParty '"+"SECOND PARTY"+"',contractingCompany '"+"CONT.COMAPNY"+"',basicSalary'"+"BASIC"+"' from tbl_labourdetails,(SELECT @i := 0) temp where firstParty=? and secondParty=?";
            search(query,parent,child);
        }
        if(chkParent.isSelected()==true && chkChild.isSelected()==true && chkContract.isSelected()==false && chkCurrentSite.isSelected()==true)
        {
            query="select @i := @i + 1 '"+"SL.NO"+"',empId '"+"ID"+"',empName'"+"NAME"+"',nationality '"+"NATIONALITY"+"',profession '"+"PROFESSION"+"',passportNumber '"+"PASSPORT#"+"',passportExpiry'"+"P.EXPIRY"+"',visaExpiry'"+"ID.EXPIRY"+"',idNumber '"+"ID#"+"',todayDate'"+"DATE"+"',dob'"+"DOB"+"',currentSite '"+"SITE"+"',firstParty '"+"FIRST PARTY"+"',secondParty '"+"SECOND PARTY"+"',contractingCompany '"+"CONT.COMAPNY"+"',basicSalary'"+"BASIC"+"' from tbl_labourdetails,(SELECT @i := 0) temp where firstParty=? and secondParty=? and currentSite=?";
            search(query,parent,child,site);
        }
        if(chkParent.isSelected()==true && chkChild.isSelected()==true && chkContract.isSelected()==true && chkCurrentSite.isSelected()==false)
        {
            query="select @i := @i + 1 '"+"SL.NO"+"',empId '"+"ID"+"',empName'"+"NAME"+"',nationality '"+"NATIONALITY"+"',profession '"+"PROFESSION"+"',passportNumber '"+"PASSPORT#"+"',passportExpiry'"+"P.EXPIRY"+"',visaExpiry'"+"ID.EXPIRY"+"',idNumber '"+"ID#"+"',todayDate'"+"DATE"+"',dob'"+"DOB"+"',currentSite '"+"SITE"+"',firstParty '"+"FIRST PARTY"+"',secondParty '"+"SECOND PARTY"+"',contractingCompany '"+"CONT.COMAPNY"+"',basicSalary'"+"BASIC"+"' from tbl_labourdetails,(SELECT @i := 0) temp where firstParty=? and secondParty=? and contractingCompany=?";
            search(query,parent,child,contract);
        }
        if(chkParent.isSelected()==true && chkChild.isSelected()==true && chkContract.isSelected()==true && chkCurrentSite.isSelected()==true)
        {
            query="select @i := @i + 1 '"+"SL.NO"+"',empId '"+"ID"+"',empName'"+"NAME"+"',nationality '"+"NATIONALITY"+"',profession '"+"PROFESSION"+"',passportNumber '"+"PASSPORT#"+"',passportExpiry'"+"P.EXPIRY"+"',visaExpiry'"+"ID.EXPIRY"+"',idNumber '"+"ID#"+"',todayDate'"+"DATE"+"',dob'"+"DOB"+"',currentSite '"+"SITE"+"',firstParty '"+"FIRST PARTY"+"',secondParty '"+"SECOND PARTY"+"',contractingCompany '"+"CONT.COMAPNY"+"',basicSalary'"+"BASIC"+"' from tbl_labourdetails,(SELECT @i := 0) temp where firstParty=? and secondParty=? and contractingCompany=? and currentSite=?";
            search(query,parent,child,contract,site);
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
     public void cmbParentFill() {
        try {
            connection c = new connection();
            Connection con = c.conn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT firstParty FROM tbl_labourdetails order by firstParty asc");
            while (rs.next()) {
                cmbParent.addItem(rs.getString(1));
            }
            con.close();
        } catch (SQLException ex) {
            
        }
    }
     public void cmbChildFill() {
        try {
            connection c = new connection();
            Connection con = c.conn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT secondParty FROM tbl_labourdetails order by secondParty asc");
            while (rs.next()) 
            {
                cmbChild.addItem(rs.getString(1));
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
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT contractingCompany FROM tbl_labourdetails order by contractingCompany asc");
            while (rs.next()) {
                cmbContract.addItem(rs.getString(1));
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
   

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        chkParent = new javax.swing.JCheckBox();
        cmbParent = new javax.swing.JComboBox();
        chkChild = new javax.swing.JCheckBox();
        cmbChild = new javax.swing.JComboBox();
        chkContract = new javax.swing.JCheckBox();
        chkCurrentSite = new javax.swing.JCheckBox();
        cmbCurrentSite = new javax.swing.JComboBox();
        cmbContract = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        setTitle("Employee Search");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search Critiria", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Gabriola", 0, 18))); // NOI18N

        chkParent.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        chkParent.setText("Parent Company");
        chkParent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkParentActionPerformed(evt);
            }
        });

        cmbParent.addItem("--select name--");
        cmbParent.setSelectedItem("--select name--");
        cmbParent.setEnabled(false);
        cmbParent.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbParentItemStateChanged(evt);
            }
        });
        cmbParent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbParentActionPerformed(evt);
            }
        });

        chkChild.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        chkChild.setText("Child Company");
        chkChild.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkChildActionPerformed(evt);
            }
        });

        cmbChild.addItem("--select name--");
        cmbChild.setSelectedItem("--select name--");
        cmbChild.setEnabled(false);
        cmbChild.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbChildActionPerformed(evt);
            }
        });

        chkContract.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        chkContract.setText("Con.Company");
        chkContract.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkContractActionPerformed(evt);
            }
        });

        chkCurrentSite.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        chkCurrentSite.setText("Current Site");
        chkCurrentSite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkCurrentSiteActionPerformed(evt);
            }
        });

        cmbCurrentSite.addItem("--select site--");
        cmbCurrentSite.setSelectedItem("--select site--");
        cmbCurrentSite.setEnabled(false);
        cmbCurrentSite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbCurrentSiteActionPerformed(evt);
            }
        });

        cmbContract.addItem("--select name--");
        cmbContract.setSelectedItem("--select name--");
        cmbContract.setEnabled(false);
        cmbContract.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbContractActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chkChild)
                    .addComponent(chkParent))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cmbParent, 0, 337, Short.MAX_VALUE)
                    .addComponent(cmbChild, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 134, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chkCurrentSite)
                    .addComponent(chkContract))
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cmbContract, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbCurrentSite, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbChild, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(chkChild)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmbParent, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(chkParent))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cmbContract, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbCurrentSite, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(chkCurrentSite)))
                    .addComponent(chkContract))
                .addGap(0, 22, Short.MAX_VALUE))
        );

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
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 628, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void chkParentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkParentActionPerformed
        // TODO add your handling code here:
        if(chkParent.isSelected()==false)
        {
            cmbParent.setSelectedItem("--Select Name--");
            cmbParent.setEnabled(false);
        }
        else
        {
            cmbParent.setEnabled(true);
            cmbParent.setEditable(true);
            AutoCompleteDecorator.decorate(cmbParent);
        }
    }//GEN-LAST:event_chkParentActionPerformed

    private void chkContractActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkContractActionPerformed
        // TODO add your handling code here:
        if(chkContract.isSelected()==false)
        {
            cmbContract.setSelectedItem("--Select Name--");
            cmbContract.setEnabled(false);
        }
        else
        {
            cmbContract.setEnabled(true);
            cmbContract.setEditable(true);
            AutoCompleteDecorator.decorate(cmbContract);
        }
    }//GEN-LAST:event_chkContractActionPerformed

    private void chkChildActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkChildActionPerformed
        // TODO add your handling code here:
        if(chkChild.isSelected()==false)
        {
            cmbChild.setSelectedItem("--Select Name--");
            cmbChild.setEnabled(false);
        }
        else
        {
            cmbChild.setEnabled(true);
            cmbChild.setEditable(true);
            AutoCompleteDecorator.decorate(cmbChild);
        }
    }//GEN-LAST:event_chkChildActionPerformed

    private void chkCurrentSiteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkCurrentSiteActionPerformed
        // TODO add your handling code here:
        if(chkCurrentSite.isSelected()==false)
        {
            cmbCurrentSite.setSelectedItem("--Select Name--");
            cmbCurrentSite.setEnabled(false);
        }
        else
        {
            cmbCurrentSite.setEnabled(true);
            cmbCurrentSite.setEditable(true);
            AutoCompleteDecorator.decorate(cmbCurrentSite);
        }
    }//GEN-LAST:event_chkCurrentSiteActionPerformed

    private void cmbParentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbParentActionPerformed
        // TODO add your handling code here:
        queryGenerator();

    }//GEN-LAST:event_cmbParentActionPerformed

    private void cmbParentItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbParentItemStateChanged
        // TODO add your handling code here:
        queryGenerator();
    }//GEN-LAST:event_cmbParentItemStateChanged

    private void cmbContractActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbContractActionPerformed
        // TODO add your handling code here:
        queryGenerator();
    }//GEN-LAST:event_cmbContractActionPerformed

    private void cmbChildActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbChildActionPerformed
        // TODO add your handling code here:
        queryGenerator();
    }//GEN-LAST:event_cmbChildActionPerformed

    private void cmbCurrentSiteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCurrentSiteActionPerformed
        // TODO add your handling code here:
        queryGenerator();
    }//GEN-LAST:event_cmbCurrentSiteActionPerformed

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        // TODO add your handling code here:
        dispose();
        AnarTrading.menuItemSearchEmployee.setEnabled(true);
    }//GEN-LAST:event_jLabel1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox chkChild;
    private javax.swing.JCheckBox chkContract;
    private javax.swing.JCheckBox chkCurrentSite;
    private javax.swing.JCheckBox chkParent;
    private javax.swing.JComboBox cmbChild;
    private javax.swing.JComboBox cmbContract;
    private javax.swing.JComboBox cmbCurrentSite;
    private javax.swing.JComboBox cmbParent;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
    Point middle = new Point(100,0);
    String blank=" ";
}
