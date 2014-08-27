package org.vijay.cheque;


import org.vijay.invoice.*;
import org.vijay.common.CurrentWorkingDirectory;
import org.vijay.invoice.InvoiceDocument;
import org.vijay.common.AnarTrading;
import org.vijay.common.connection;
import org.vijay.common.AutoCompleteDecorator;
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
import java.text.SimpleDateFormat;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.proteanit.sql.DbUtils;
import org.vijay.common.NumberRenderer;
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
public final class AdvancedChequeSearch extends javax.swing.JInternalFrame {

    /**
     * Creates new form AdvancedEmployeeSearch
     */
    public AdvancedChequeSearch() {
        initComponents();
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setLocation(middle);
        cmbBankFill();
        cmbFromFill();
        cmbBearerFill();
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
    public void queryGenerator()
    {
        String query="";
        String totalQuery="";
        String monthYear=todaydate;
        String bank=cmbBank.getSelectedItem().toString();
        String from=cmbFrom.getSelectedItem().toString();
        String bearer=cmbBearer.getSelectedItem().toString();
        
        
        if(chkBank.isSelected()==false && chkFrom.isSelected()==false && chkBearer.isSelected()==false)
        {   
            query="select @i := @i + 1 '"+"SL.NO"+"',chequeId '"+"CHK#ID"+"',bankName'"+"B.NAME"+"',fromName '"+"FROM"+"',bearer'"+"BEARER"+"',chequeNo '"+"CHK#"+"',chequeDate'"+"CHK DATE"+"',amount'"+"AMOUNT"+"',amountInWords '"+"AMT IN WORDS"+"',remarks'"+"REMARKS"+"' from tbl_cheque,(SELECT @i := 0) temp";
            printValue="0000";
            search(query);
        }
        if( chkBank.isSelected()==false && chkFrom.isSelected()==false && chkBearer.isSelected()==true)
        {   
            query="select @i := @i + 1 '"+"SL.NO"+"',chequeId '"+"CHK#ID"+"',bankName'"+"B.NAME"+"',fromName '"+"FROM"+"',bearer'"+"BEARER"+"',chequeNo '"+"CHK#"+"',chequeDate'"+"CHK DATE"+"',amount'"+"AMOUNT"+"',amountInWords '"+"AMT IN WORDS"+"',remarks'"+"REMARKS"+"' from tbl_cheque,(SELECT @i := 0) temp where bearer=?";
            printValue="0001";
            search(query,bearer);
        }
        if(chkBank.isSelected()==false && chkFrom.isSelected()==true && chkBearer.isSelected()==false)
        {   
            query="select @i := @i + 1 '"+"SL.NO"+"',chequeId '"+"CHK#ID"+"',bankName'"+"B.NAME"+"',fromName '"+"FROM"+"',bearer'"+"BEARER"+"',chequeNo '"+"CHK#"+"',chequeDate'"+"CHK DATE"+"',amount'"+"AMOUNT"+"',amountInWords '"+"AMT IN WORDS"+"',remarks'"+"REMARKS"+"' from tbl_cheque,(SELECT @i := 0) temp where fromName=?";
            printValue="0010";
            search(query,from);
        }
        if(chkBank.isSelected()==false && chkFrom.isSelected()==true && chkBearer.isSelected()==true)
        {   
            query="select @i := @i + 1 '"+"SL.NO"+"',chequeId '"+"CHK#ID"+"',bankName'"+"B.NAME"+"',fromName '"+"FROM"+"',bearer'"+"BEARER"+"',chequeNo '"+"CHK#"+"',chequeDate'"+"CHK DATE"+"',amount'"+"AMOUNT"+"',amountInWords '"+"AMT IN WORDS"+"',remarks'"+"REMARKS"+"' from tbl_cheque,(SELECT @i := 0) temp where fromName=? and bearer=?";
            printValue="0011";
            search(query,from,bearer);
        }
        if(chkBank.isSelected()==true && chkFrom.isSelected()==false && chkBearer.isSelected()==false)
        {   
            query="select @i := @i + 1 '"+"SL.NO"+"',chequeId '"+"CHK#ID"+"',bankName'"+"B.NAME"+"',fromName '"+"FROM"+"',bearer'"+"BEARER"+"',chequeNo '"+"CHK#"+"',chequeDate'"+"CHK DATE"+"',amount'"+"AMOUNT"+"',amountInWords '"+"AMT IN WORDS"+"',remarks'"+"REMARKS"+"' from tbl_cheque,(SELECT @i := 0) temp where bankName=?";
            printValue="0100";
            search(query,bank);
        }
        if(chkBank.isSelected()==true && chkFrom.isSelected()==false && chkBearer.isSelected()==true)
        {   
            query="select @i := @i + 1 '"+"SL.NO"+"',chequeId '"+"CHK#ID"+"',bankName'"+"B.NAME"+"',fromName '"+"FROM"+"',bearer'"+"BEARER"+"',chequeNo '"+"CHK#"+"',chequeDate'"+"CHK DATE"+"',amount'"+"AMOUNT"+"',amountInWords '"+"AMT IN WORDS"+"',remarks'"+"REMARKS"+"' from tbl_cheque,(SELECT @i := 0) temp where bankName=? and bearer=?";
            printValue="0101";
            search(query,bank,bearer);
        }
        if(chkBank.isSelected()==true && chkFrom.isSelected()==true && chkBearer.isSelected()==false)
        {   
            query="select @i := @i + 1 '"+"SL.NO"+"',chequeId '"+"CHK#ID"+"',bankName'"+"B.NAME"+"',fromName '"+"FROM"+"',bearer'"+"BEARER"+"',chequeNo '"+"CHK#"+"',chequeDate'"+"CHK DATE"+"',amount'"+"AMOUNT"+"',amountInWords '"+"AMT IN WORDS"+"',remarks'"+"REMARKS"+"' from tbl_cheque,(SELECT @i := 0) temp where bankName=? and fromName=?";
            printValue="0110";
            search(query,bank,from);
        }
        if(chkBank.isSelected()==true && chkFrom.isSelected()==true && chkBearer.isSelected()==true)
        {   
            query="select @i := @i + 1 '"+"SL.NO"+"',chequeId '"+"CHK#ID"+"',bankName'"+"B.NAME"+"',fromName '"+"FROM"+"',bearer'"+"BEARER"+"',chequeNo '"+"CHK#"+"',chequeDate'"+"CHK DATE"+"',amount'"+"AMOUNT"+"',amountInWords '"+"AMT IN WORDS"+"',remarks'"+"REMARKS"+"' from tbl_cheque,(SELECT @i := 0) temp where bankName=? and fromName=? and bearer=?";
            printValue="0111";
            search(query,bank,from,bearer);
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
//                jTable1.getColumnModel().getColumn(1).setMinWidth(0);
//                jTable1.getColumnModel().getColumn(1).setMaxWidth(0);
//                jTable1.getColumnModel().getColumn(1).setWidth(0);
//                jTable1.getColumnModel().getColumn(5).setCellRenderer(NumberRenderer.getDateTimeRenderer());
//            jTable1.getColumnModel().getColumn(6).setCellRenderer(NumberRenderer.getIntegerRenderer());
//            jTable1.getColumnModel().getColumn(10).setCellRenderer(NumberRenderer.getDateTimeRenderer());
//            jTable1.getColumnModel().getColumn(11).setCellRenderer(NumberRenderer.getIntegerRenderer());
//            jTable1.getColumnModel().getColumn(12).setCellRenderer(NumberRenderer.getIntegerRenderer());
//                jTable1.setShowHorizontalLines( true );
//                jTable1.setRowSelectionAllowed( true );

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
//                jTable1.getColumnModel().getColumn(1).setMinWidth(0);
//                jTable1.getColumnModel().getColumn(1).setMaxWidth(0);
//                jTable1.getColumnModel().getColumn(1).setWidth(0);
//                jTable1.getColumnModel().getColumn(5).setCellRenderer(NumberRenderer.getDateTimeRenderer());
//            jTable1.getColumnModel().getColumn(6).setCellRenderer(NumberRenderer.getIntegerRenderer());
//            jTable1.getColumnModel().getColumn(10).setCellRenderer(NumberRenderer.getDateTimeRenderer());
//            jTable1.getColumnModel().getColumn(11).setCellRenderer(NumberRenderer.getIntegerRenderer());
//            jTable1.getColumnModel().getColumn(12).setCellRenderer(NumberRenderer.getIntegerRenderer());
//                jTable1.setShowHorizontalLines( true );
//                jTable1.setRowSelectionAllowed( true );

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
//                jTable1.getColumnModel().getColumn(1).setMinWidth(0);
//                jTable1.getColumnModel().getColumn(1).setMaxWidth(0);
//                jTable1.getColumnModel().getColumn(1).setWidth(0);
//                jTable1.getColumnModel().getColumn(5).setCellRenderer(NumberRenderer.getDateTimeRenderer());
//                jTable1.getColumnModel().getColumn(6).setCellRenderer(NumberRenderer.getIntegerRenderer());
//                jTable1.getColumnModel().getColumn(10).setCellRenderer(NumberRenderer.getDateTimeRenderer());
//                jTable1.getColumnModel().getColumn(11).setCellRenderer(NumberRenderer.getIntegerRenderer());
//                jTable1.getColumnModel().getColumn(12).setCellRenderer(NumberRenderer.getIntegerRenderer());
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
//                jTable1.getColumnModel().getColumn(1).setMinWidth(0);
//                jTable1.getColumnModel().getColumn(1).setMaxWidth(0);
//                jTable1.getColumnModel().getColumn(1).setWidth(0);
//                jTable1.getColumnModel().getColumn(5).setCellRenderer(NumberRenderer.getDateTimeRenderer());
//            jTable1.getColumnModel().getColumn(6).setCellRenderer(NumberRenderer.getIntegerRenderer());
//            jTable1.getColumnModel().getColumn(10).setCellRenderer(NumberRenderer.getDateTimeRenderer());
//            jTable1.getColumnModel().getColumn(11).setCellRenderer(NumberRenderer.getIntegerRenderer());
//            jTable1.getColumnModel().getColumn(12).setCellRenderer(NumberRenderer.getIntegerRenderer());
//                jTable1.setShowHorizontalLines( true );
//                jTable1.setRowSelectionAllowed( true );

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
                jTable1.getColumnModel().getColumn(5).setCellRenderer(NumberRenderer.getDateTimeRenderer());
            jTable1.getColumnModel().getColumn(6).setCellRenderer(NumberRenderer.getIntegerRenderer());
            jTable1.getColumnModel().getColumn(10).setCellRenderer(NumberRenderer.getDateTimeRenderer());
            jTable1.getColumnModel().getColumn(11).setCellRenderer(NumberRenderer.getIntegerRenderer());
            jTable1.getColumnModel().getColumn(12).setCellRenderer(NumberRenderer.getIntegerRenderer());
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
                jTable1.getColumnModel().getColumn(5).setCellRenderer(NumberRenderer.getDateTimeRenderer());
            jTable1.getColumnModel().getColumn(6).setCellRenderer(NumberRenderer.getIntegerRenderer());
            jTable1.getColumnModel().getColumn(10).setCellRenderer(NumberRenderer.getDateTimeRenderer());
            jTable1.getColumnModel().getColumn(11).setCellRenderer(NumberRenderer.getIntegerRenderer());
            jTable1.getColumnModel().getColumn(12).setCellRenderer(NumberRenderer.getIntegerRenderer());
                jTable1.setShowHorizontalLines( true );
                jTable1.setRowSelectionAllowed( true );

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
     public void cmbBankFill() {
        try {
            connection c = new connection();
            Connection con = c.conn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT bankName from tbl_cheque order by bankName asc");
            while (rs.next()) {
                cmbBank.addItem(rs.getString(1));
            }
            con.close();
        } catch (SQLException ex) {
            
        }
    }
     public void cmbFromFill() {
        try {
            connection c = new connection();
            Connection con = c.conn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT fromName from tbl_cheque order by fromName asc");
            while (rs.next()) 
            {
                cmbFrom.addItem(rs.getString(1));
            }
            con.close();
        } catch (SQLException ex) {
            
        }
    }
     public void cmbBearerFill() {
        try {
            connection c = new connection();
            Connection con = c.conn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT bearer from tbl_cheque order by bearer asc");
            while (rs.next()) {
                cmbBearer.addItem(rs.getString(1));
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
        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        chkBank = new javax.swing.JCheckBox();
        cmbBank = new javax.swing.JComboBox();
        chkFrom = new javax.swing.JCheckBox();
        cmbFrom = new javax.swing.JComboBox();
        chkBearer = new javax.swing.JCheckBox();
        cmbBearer = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        txtTotal = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();

        menuItemDocument.setText("Add Document");
        menuItemDocument.setToolTipText("");
        menuItemDocument.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemDocumentActionPerformed(evt);
            }
        });
        jtablePopUp.add(menuItemDocument);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Invoice Received Search");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search Critiria", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 0, 12))); // NOI18N
        jPanel1.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        chkBank.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        chkBank.setText("Bank");
        chkBank.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkBankActionPerformed(evt);
            }
        });

        cmbBank.addItem("--select bank name--");
        cmbBank.setSelectedItem("--select bank name--");
        cmbBank.setEnabled(false);
        cmbBank.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        cmbBank.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbBankItemStateChanged(evt);
            }
        });
        cmbBank.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbBankActionPerformed(evt);
            }
        });

        chkFrom.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        chkFrom.setText("From");
        chkFrom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkFromActionPerformed(evt);
            }
        });

        cmbFrom.addItem("--select from name--");
        cmbFrom.setSelectedItem("--select from name--");
        cmbFrom.setEnabled(false);
        cmbFrom.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        cmbFrom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbFromActionPerformed(evt);
            }
        });

        chkBearer.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        chkBearer.setText("Bearer");
        chkBearer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkBearerActionPerformed(evt);
            }
        });

        cmbBearer.addItem("--select bearer name--");
        cmbBearer.setSelectedItem("--select bearer name--");
        cmbBearer.setEnabled(false);
        cmbBearer.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        cmbBearer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbBearerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chkBank)
                    .addComponent(chkBearer))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cmbBank, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(chkFrom)
                        .addGap(48, 48, 48)
                        .addComponent(cmbFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cmbBearer, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(25, 25, 25))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbBank, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkBank)
                    .addComponent(chkFrom)
                    .addComponent(cmbFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbBearer, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkBearer))
                .addGap(0, 20, Short.MAX_VALUE))
        );

        jTable1.setFont(new java.awt.Font("Century Gothic", 0, 10)); // NOI18N
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
        jTable1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
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

        jMenu1.setText("Graphical Reports");
        jMenu1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenu1.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(775, 775, 775)
                .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 642, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void chkBankActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkBankActionPerformed
        // TODO add your handling code here:
        if(chkBank.isSelected()==false)
        {
            cmbBank.setSelectedItem("--Select Name--");
            cmbBank.setEnabled(false);
        }
        else
        {
            cmbBank.setEnabled(true);
            cmbBank.setEditable(true);
            AutoCompleteDecorator.decorate(cmbBank);
        }
    }//GEN-LAST:event_chkBankActionPerformed

    private void chkBearerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkBearerActionPerformed
        // TODO add your handling code here:
        if(chkBearer.isSelected()==false)
        {
            cmbBearer.setSelectedItem("--Select month--");
            cmbBearer.setEnabled(false);
        }
        else
        {
            cmbBearer.setEnabled(true);
            cmbBearer.setEditable(true);
            AutoCompleteDecorator.decorate(cmbBearer);
        }
    }//GEN-LAST:event_chkBearerActionPerformed

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

    private void cmbBankActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbBankActionPerformed
        // TODO add your handling code here:
        queryGenerator();

    }//GEN-LAST:event_cmbBankActionPerformed

    private void cmbBankItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbBankItemStateChanged
        // TODO add your handling code here:
        queryGenerator();
    }//GEN-LAST:event_cmbBankItemStateChanged

    private void cmbBearerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbBearerActionPerformed
        // TODO add your handling code here:
        queryGenerator();
    }//GEN-LAST:event_cmbBearerActionPerformed

    private void cmbFromActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbFromActionPerformed
        // TODO add your handling code here:
        queryGenerator();
    }//GEN-LAST:event_cmbFromActionPerformed

    private void menuItemDocumentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemDocumentActionPerformed
         // TODO add your handling code here:
        InvoiceReceivedDocument ID = new InvoiceReceivedDocument(invoiceId);
        AnarTrading.desktopPane1.add(ID);
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

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
        // TODO add your handling code here:
        int keyCode = evt.getKeyCode();
        if(keyCode == KeyEvent.VK_ENTER)
        {
            InvoiceReceived  IR=new InvoiceReceived(invoiceId);
            AnarTrading.desktopPane1.add(IR);
            IR.setVisible(true);
            IR.show();
        }
        if(keyCode == KeyEvent.VK_DELETE)
        {
            
        }
    }//GEN-LAST:event_jTable1KeyPressed

    private void jTable1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTable1FocusGained
        // TODO add your handling code here:
        queryGenerator();
    }//GEN-LAST:event_jTable1FocusGained


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox chkBank;
    private javax.swing.JCheckBox chkBearer;
    private javax.swing.JCheckBox chkFrom;
    private javax.swing.JComboBox cmbBank;
    private javax.swing.JComboBox cmbBearer;
    private javax.swing.JComboBox cmbFrom;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JPopupMenu jtablePopUp;
    private javax.swing.JMenuItem menuItemDocument;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
    Point middle = new Point(0,0);
    int invoiceId;
    String printValue,path;
    String todaydate;
}
