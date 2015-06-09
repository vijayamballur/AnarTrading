/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.vijay.deliverynote;

import org.vijay.invoice.*;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
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
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.proteanit.sql.DbUtils;
import org.vijay.common.AnarTrading;
import org.vijay.common.AutoCompleteDecorator;
import org.vijay.common.DateCellRenderer;
import org.vijay.common.EnglishNumberToWords;
import org.vijay.common.connection;
import static org.vijay.contractEmployee.ContractLabourDetails.dateFormat;
import org.vijay.employee.LabourDetails;


/**
 *
 * @author MAC
 */
public class DeliveryNote extends javax.swing.JInternalFrame {

    /**
     * Creates new form InvoiceNew
     */
    public DeliveryNote() {
        initComponents();
        setSize(Toolkit.getDefaultToolkit().getScreenSize()); 
        viewTempSubTable();
        fillcmbTo();
        jtable2Selection();
        jDateInvoiceDate.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("date")) {
                    invoiceDate = new SimpleDateFormat("yyyy-MM-dd").format(jDateInvoiceDate.getDate());
                }
            }  
        });
        rowCount=jTable2.getRowCount();
        if(rowCount==0)
        {
            
        }
        else
        {
            viewTempMainTable();
        }
    }
    public void fillFeild()
    {
        try {
            connection c = new connection();
            Connection con = c.conn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Address FROM tbl_invoice_parent where name='"+cmbToName.getSelectedItem()+"'");
            while (rs.next()) {
                txtAddress.setText(rs.getString(1));
            }
            con.close();
        } catch (SQLException ex) {
            
        }
    }
    public void viewInvoiceNewForm()
    {
        DeliveryNote IN= new DeliveryNote();
        AnarTrading.desktopPane1.add(IN);
        IN.setVisible(true);
    }
    public void insertIntoMasterTempTable()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            PreparedStatement ps=con.prepareStatement("INSERT INTO tbl_invoice_parent_temp(Name,invNumber,invDate,DeliveryNo,LPONo,Address,grandTotal,amountInWords) VALUES(Upper(?),Upper(?),?,Upper(?),upper(?),upper(?),?,upper(?))");           
            ps.setString(1, cmbToName.getSelectedItem().toString());
            ps.setString(2, txtInvoiceNumber.getText());
            ps.setString(3, invoiceDate);
            ps.setString(4, txtDoNumber.getText());
            ps.setString(5, txtLpoNumber.getText());
            ps.setString(6, txtAddress.getText());
            ps.setString(7, txtGrandTotal.getText());
            ps.setString(8, txtNumberTowords.getText());
            int i=ps.executeUpdate();
            if(i!=0)
            {
                
                    dispose();
                    viewInvoiceNewForm();
            }
            con.close();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(rootPane, e, "Data Insertion ERROR!! Master Temp Table", JOptionPane.ERROR_MESSAGE);
            //JOptionPane.showMessageDialog(rootPane,e+"Error SA001");
        }
    }
    public void insertSubTempTable()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            PreparedStatement ps=con.prepareStatement("INSERT INTO tbl_invoice_sub_temp(description,unitPrice,qty,umo,amount) VALUES(?,?,?,?,?)");           
            ps.setString(1, txtDescription.getText());
            ps.setString(2, txtUnitPrice.getText());
            ps.setString(3, txtQty.getText());
            ps.setString(4, txtUmo.getText());
            ps.setString(5, txtAmount.getText());
            int i=ps.executeUpdate();
            if(i!=0)
            {
                    viewTempSubTable();
                    if(rowCount>0)
                    {
                        updateMainTempTable();
                    }
                    
                    else
                    {
                          insertIntoMasterTempTable();  
                    }
            }
            con.close();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(rootPane, e, "Data Insertion ERROR!!Sub Temp Table", JOptionPane.ERROR_MESSAGE);
            //JOptionPane.showMessageDialog(rootPane,e+"Error SA001");
        }
    }
    public void updateMainTempTable()
    {
        
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            PreparedStatement ps=con.prepareStatement("UPDATE tbl_invoice_parent_temp SET Name=upper(?),invNumber=upper(?),invDate=?,DeliveryNo=Upper(?),LPONo=upper(?),Address=upper(?),grandTotal=?,amountInWords=upper(?)");
            ps.setString(1,cmbToName.getSelectedItem().toString());
            ps.setString(2,txtInvoiceNumber.getText());
            ps.setString(3,invoiceDate);
            ps.setString(4,txtDoNumber.getText());
            ps.setString(5,txtLpoNumber.getText());
            ps.setString(6,txtAddress.getText());
            ps.setString(7,txtGrandTotal.getText());
            ps.setString(8,txtNumberTowords.getText());
            int i=ps.executeUpdate();
            if(i!=0)
            {
                    dispose();
                    viewInvoiceNewForm();
            }
            con.close();  
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(rootPane, e, "ERROR!!", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void viewTempSubTable()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT @i := @i + 1 AS Sl_No,id AS ID,description AS DESCRIPTION,unitPrice UNIT_PRICE,qty AS QTY,umo AS UMO,amount AS AMOUNT FROM tbl_invoice_sub_temp,(SELECT @i := 0) temp");
            jTable2.setModel(DbUtils.resultSetToTableModel(rs));
            jTable2.setAutoCreateRowSorter(true);
            con.close();
            viewGRandTotalTempSubTable();

        }
        catch(Exception e)
        {

        }
    }
    public void fillcmbTo()
    {
        try {
            connection c = new connection();
            Connection con = c.conn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT Name FROM tbl_invoice_parent order by Name asc");
            while (rs.next()) {
                cmbToName.addItem(rs.getString(1));
            }
            con.close();
        } catch (SQLException ex) {
            
        }
    }
            
    public void viewTempMainTable()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT NAME,invNumber,invDate,DeliveryNo,LPONo,Address FROM tbl_invoice_parent_temp");
            while(rs.next())
            {
                try 
                {
                    cmbToName.setSelectedItem(rs.getString("name"));
                    txtInvoiceNumber.setText(rs.getString("invNumber"));
                    jDateInvoiceDate.setDate(dateFormat.parse(rs.getString("invDate")));
                    txtDoNumber.setText(rs.getString("DeliveryNo"));
                    txtLpoNumber.setText(rs.getString("LPONo"));
                    txtAddress.setText(rs.getString("Address"));
                    
                } 
                catch (ParseException ex) 
                {
                    Logger.getLogger(LabourDetails.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            con.close();
            viewGRandTotalTempSubTable();

        }
        catch(Exception e)
        {

        }
    }
    public void viewGRandTotalTempSubTable()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT sum(amount) FROM tbl_invoice_sub_temp");
            while(rs.next())
            {
                txtGrandTotal.setText(rs.getString(1));
            }
            con.close();
            
            String phrase = txtGrandTotal.getText() ;
        if(phrase.contains("."))
        {
            Float num = new Float( phrase ) ;
            int riyal = (int)Math.floor( num ) ;
            int dirham = Integer.parseInt(phrase.substring(phrase.lastIndexOf(".")+1));
            //txtTheSumOfQR.setText(+phrase.lastIndexOf(".")+" ... "+phrase.substring(phrase.lastIndexOf(".")+1));
            String s = EnglishNumberToWords.convert( riyal ) + " and " + EnglishNumberToWords.convert( dirham ) + " Dirham Only" ;
            txtNumberTowords.setText(s);
        }
        else
        {
            Float num = new Float( phrase ) ;
            int riyal = (int)Math.floor( num ) ;
            int dirham = Integer.parseInt(phrase.substring(phrase.lastIndexOf(".")+1));
            //txtTheSumOfQR.setText(+phrase.lastIndexOf(".")+" ... "+phrase.substring(phrase.lastIndexOf(".")+1));
            String s = EnglishNumberToWords.convert( riyal ) + " Only";
            txtNumberTowords.setText(s);
        }

        }
        catch(Exception e)
        {

        }
    }
    public static void trancateTempTable()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            PreparedStatement ps=con.prepareStatement("TRUNCATE TABLE tbl_invoice_sub_temp");           
            int i=ps.executeUpdate();
            if(i!=0)
            {   
            }
            con.close();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e, "ERROR!!", JOptionPane.ERROR_MESSAGE);
            //JOptionPane.showMessageDialog(rootPane,e+"Error SA001");
        }
    }
    public static void trancateMainTempTable()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            PreparedStatement ps=con.prepareStatement("TRUNCATE TABLE tbl_invoice_parent_temp");           
            int i=ps.executeUpdate();
            if(i!=0)
            {   
            }
            con.close();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e, "ERROR!!", JOptionPane.ERROR_MESSAGE);
            //JOptionPane.showMessageDialog(rootPane,e+"Error SA001");
        }
    }
    
    public void amountCalculation()
    {
        unitPrice=Float.parseFloat(txtUnitPrice.getText());
        qty=Integer.parseInt(txtQty.getText());
        txtAmount.setText(Float.toString(unitPrice*qty));
    }
    public void jtable2Selection()
    {
        jTable2.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
            int rowNo=jTable2.getSelectedRow();
            btnViewInvoice.setEnabled(false);
            btnUpdate.setEnabled(true);
            btnDelete.setEnabled(true);
       
            subId=Integer.parseInt(jTable2.getValueAt(rowNo,1).toString());
            txtDescription.setText(jTable2.getValueAt(rowNo,2).toString());
            txtUnitPrice.setText(jTable2.getValueAt(rowNo,3).toString());
            txtQty.setText(jTable2.getValueAt(rowNo,4).toString());
            txtUmo.setText(jTable2.getValueAt(rowNo,5).toString());
            txtAmount.setText(jTable2.getValueAt(rowNo,6).toString());
            }
        });
    }
    public void updateDb()
    {
        
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            PreparedStatement ps=con.prepareStatement("UPDATE tbl_invoice_sub_temp SET description=?,unitPrice=?,qty=?,umo=?,amount=? where id=?");
            ps.setString(1,txtDescription.getText());
            ps.setString(2,txtUnitPrice.getText());
            ps.setString(3,txtQty.getText());
            ps.setString(4,txtUmo.getText());
            ps.setString(5,txtAmount.getText());
            ps.setInt(6, subId);
            int i=ps.executeUpdate();
            if(i!=0)
            {
                    dispose();
                    viewInvoiceNewForm();
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
                    int i=stmt1.executeUpdate("delete from tbl_invoice_sub_temp  where id="+subId);
                    if(i!=0)
                    {
                        dispose();
                        viewInvoiceNewForm();
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
    public boolean InvoiceDetailsValidation()
    {
        if(cmbToName.getSelectedItem().equals("--select Name--"))
        {
            JOptionPane.showMessageDialog(cmbToName, "Please select the 'TO Name'");
            return false;
        }
        else if(txtInvoiceNumber.getText().equals(""))
        {
            JOptionPane.showMessageDialog(txtInvoiceNumber, "Please Enter the Invoice Number from your Template");
            return false;
        }
        else if(invoiceDate.equals(""))
        {
            JOptionPane.showMessageDialog(jDateInvoiceDate, "Please Enter the Invoice Date");
            return false;
        }
        else if(txtAddress.getText().equals(""))
        {
            JOptionPane.showMessageDialog(txtAddress, "Please Enter the Address");
            return false;
        }
        else if(rowCount==0)
        {
            JOptionPane.showMessageDialog(jTable2,"Please Enter the Item Details");
            return false;
        }
        else
        {
          return true; 
        }

    }
    public boolean ItemDetailsValidation()
    {
        if(txtUnitPrice.getText().equals("--select Name--"))
        {
            JOptionPane.showMessageDialog(cmbToName, "Please select the 'TO Name'");
            return false;
        }
        else if(txtInvoiceNumber.getText().equals(""))
        {
            JOptionPane.showMessageDialog(txtInvoiceNumber, "Please Enter the Invoice Number from your Template");
            return false;
        }
        else if(invoiceDate.equals(""))
        {
            JOptionPane.showMessageDialog(jDateInvoiceDate, "Please Enter the Invoice Date");
            return false;
        }
        else if(txtAddress.getText().equals(""))
        {
            JOptionPane.showMessageDialog(txtAddress, "Please Enter the Address");
            return false;
        }
        else if(rowCount==0)
        {
            JOptionPane.showMessageDialog(jTable2,"Please Enter the Item Details");
            return false;
        }
        else
        {
          return true; 
        }

    }
    public void textFieldDecimalValueCheck(JTextField txtFiled,java.awt.event.KeyEvent evt)
    {
        int charCode= evt.getKeyCode();
            if (((charCode >=KeyEvent.VK_0) && (charCode<=KeyEvent.VK_9))||((charCode >=KeyEvent.VK_NUMPAD0) && (charCode<=KeyEvent.VK_NUMPAD9))|| charCode==KeyEvent.VK_BACK_SPACE || charCode==KeyEvent.VK_DELETE|| charCode==KeyEvent.VK_DECIMAL||charCode==KeyEvent.VK_TAB)
            {
                
            }
            else
            {
                JOptionPane.showMessageDialog(null,"This Value is not allowed in the field !!","Warning!!!",JOptionPane.ERROR_MESSAGE);
                txtFiled.setText(""+txtFiled.getText().substring(0, txtFiled.getText().length()- 1));
                txtFiled.requestFocusInWindow();
            }
    }
    public void textFieldIntegerValueCheck(JTextField txtFiled,java.awt.event.KeyEvent evt)
    {
        int charCode= evt.getKeyCode();
            if (((charCode >=KeyEvent.VK_0) && (charCode<=KeyEvent.VK_9))||((charCode >=KeyEvent.VK_NUMPAD0) && (charCode<=KeyEvent.VK_NUMPAD9))|| charCode==KeyEvent.VK_BACK_SPACE || charCode==KeyEvent.VK_DELETE|| charCode==KeyEvent.VK_TAB)
            {
                
            }
            else
            {
                JOptionPane.showMessageDialog(null,"This Value is not allowed in the field !!","Warning!!!",JOptionPane.ERROR_MESSAGE);
                txtFiled.setText(""+txtFiled.getText().substring(0, txtFiled.getText().length()- 1));
                txtFiled.grabFocus();
            }
    }
    public void selectInvoiceMasterId()
    {
        try {
            connection c = new connection();
            Connection con = c.conn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT MAX(invoiceMasterId) FROM tbl_invoice_parent");
            while (rs.next()) {
                invoiceMasterId=rs.getString(1);
            }
            con.close();
        } catch (SQLException ex) {
            
        }
    }
    public void savetoChildTable()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            PreparedStatement ps=con.prepareStatement("INSERT INTO tbl_invoice_child(invoiceMasterId,description,unitPrice,qty,umo,amount) VALUES(?,?,?,?,?,?)");           
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT description,unitPrice,qty,umo,amount FROM tbl_invoice_sub_temp ");
            while(rs.next())
            {
                ps.setString(1,invoiceMasterId);
                ps.setString(2,rs.getString(1));
                ps.setString(3,rs.getString(2));
                ps.setString(4,rs.getString(3));
                ps.setString(5,rs.getString(4));
                ps.setString(6,rs.getString(5));
                int i=ps.executeUpdate();
            }
            con.close();
            dispose();
            AnarTrading.btnInvoiceTemplate.setEnabled(true);
            
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(rootPane, e, "ERROR!!", JOptionPane.ERROR_MESSAGE);
            //JOptionPane.showMessageDialog(rootPane,e+"Error SA001");
        }
    }
    public void saveAllDetails()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            PreparedStatement ps=con.prepareStatement("INSERT INTO tbl_invoice_parent(Name,invNumber,invDate,DeliveryNo,LPONo,Address,grandTotal,amountInWords) SELECT Name,invNumber,invDate,DeliveryNo,LPONo,Address,grandTotal,amountInWords FROM tbl_invoice_parent_temp");
            int i=ps.executeUpdate();
            if(i!=0)
            {
                selectInvoiceMasterId();
                savetoChildTable();
                
            }
            con.close();  
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(rootPane, e, "ERROR!!", JOptionPane.ERROR_MESSAGE);
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
        menuItemPrint = new javax.swing.JMenuItem();
        menuItemDelete = new javax.swing.JMenuItem();
        jPanelMain = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        cmbToName = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAddress = new javax.swing.JTextArea();
        jDateInvoiceDate = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtLpoNumber = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtInvoiceNumber = new javax.swing.JTextField();
        jToolBar3 = new javax.swing.JToolBar();
        txtDoNumber = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jPanelSub = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDescription = new javax.swing.JTextArea();
        jLabel13 = new javax.swing.JLabel();
        txtUnitPrice = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtQty = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtUmo = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtAmount = new javax.swing.JTextField();
        jToolBar1 = new javax.swing.JToolBar();
        btnRefresh = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel19 = new javax.swing.JLabel();
        txtNumberTowords = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtGrandTotal = new javax.swing.JTextField();
        btnViewInvoice = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();

        menuItemPrint.setText("Print");
        jtablePopUp.add(menuItemPrint);

        menuItemDelete.setText("Delete");
        menuItemDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemDeleteActionPerformed(evt);
            }
        });
        jtablePopUp.add(menuItemDelete);

        setTitle("Invoice Template");
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        jPanelMain.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Invoice Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 0, 12))); // NOI18N

        jLabel10.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel10.setText("To Name");

        cmbToName.setEditable(true);
        AutoCompleteDecorator.decorate(cmbToName);
        cmbToName.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cmbToName.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "--select Name--" }));
        cmbToName.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbToNameItemStateChanged(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel11.setText("Address");

        txtAddress.setColumns(20);
        txtAddress.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txtAddress.setRows(5);
        jScrollPane1.setViewportView(txtAddress);

        jDateInvoiceDate.setDateFormatString("yyyy-MM-dd");
        jDateInvoiceDate.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel8.setText("Date");

        jLabel12.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel12.setText("L.P.O No.");

        txtLpoNumber.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txtLpoNumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLpoNumberActionPerformed(evt);
            }
        });
        txtLpoNumber.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtLpoNumberKeyPressed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel18.setText("Inv#");

        txtInvoiceNumber.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txtInvoiceNumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtInvoiceNumberActionPerformed(evt);
            }
        });
        txtInvoiceNumber.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtInvoiceNumberKeyPressed(evt);
            }
        });

        jToolBar3.setFloatable(false);
        jToolBar3.setRollover(true);

        txtDoNumber.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txtDoNumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDoNumberActionPerformed(evt);
            }
        });
        txtDoNumber.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDoNumberKeyPressed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel9.setText("D.O No.");

        javax.swing.GroupLayout jPanelMainLayout = new javax.swing.GroupLayout(jPanelMain);
        jPanelMain.setLayout(jPanelMainLayout);
        jPanelMainLayout.setHorizontalGroup(
            jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanelMainLayout.createSequentialGroup()
                        .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cmbToName, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelMainLayout.createSequentialGroup()
                                .addComponent(txtDoNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel8)
                                .addGap(18, 18, 18)
                                .addComponent(jDateInvoiceDate, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtLpoNumber)
                            .addComponent(txtInvoiceNumber)))
                    .addGroup(jPanelMainLayout.createSequentialGroup()
                        .addComponent(jToolBar3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 492, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelMainLayout.setVerticalGroup(
            jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbToName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtInvoiceNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtDoNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jDateInvoiceDate, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtLpoNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addComponent(jToolBar3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelSub.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Item Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 0, 12))); // NOI18N

        txtDescription.setColumns(20);
        txtDescription.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txtDescription.setRows(5);
        jScrollPane2.setViewportView(txtDescription);

        jLabel13.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel13.setText("Description");

        txtUnitPrice.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txtUnitPrice.setText("0.0");
        txtUnitPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUnitPriceActionPerformed(evt);
            }
        });
        txtUnitPrice.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtUnitPriceFocusLost(evt);
            }
        });
        txtUnitPrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUnitPriceKeyPressed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel14.setText("Unit Price");

        jLabel15.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel15.setText("Qty");

        txtQty.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txtQty.setText("0");
        txtQty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQtyActionPerformed(evt);
            }
        });
        txtQty.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtQtyFocusLost(evt);
            }
        });
        txtQty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtQtyKeyPressed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel16.setText("UMO");

        txtUmo.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txtUmo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUmoActionPerformed(evt);
            }
        });
        txtUmo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUmoKeyPressed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel17.setText("Amount");

        txtAmount.setEditable(false);
        txtAmount.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txtAmount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAmountActionPerformed(evt);
            }
        });
        txtAmount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtAmountKeyPressed(evt);
            }
        });

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnRefresh.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnRefresh.setMnemonic('s');
        btnRefresh.setText("Refresh");
        btnRefresh.setToolTipText("");
        btnRefresh.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnRefresh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRefresh.setFocusable(false);
        btnRefresh.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRefresh.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });
        jToolBar1.add(btnRefresh);

        btnUpdate.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnUpdate.setMnemonic('s');
        btnUpdate.setText("Update");
        btnUpdate.setToolTipText("");
        btnUpdate.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUpdate.setEnabled(false);
        btnUpdate.setFocusable(false);
        btnUpdate.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnUpdate.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        jToolBar1.add(btnUpdate);

        btnDelete.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnDelete.setMnemonic('s');
        btnDelete.setText("Delete");
        btnDelete.setToolTipText("");
        btnDelete.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDelete.setEnabled(false);
        btnDelete.setFocusable(false);
        btnDelete.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDelete.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        jToolBar1.add(btnDelete);

        javax.swing.GroupLayout jPanelSubLayout = new javax.swing.GroupLayout(jPanelSub);
        jPanelSub.setLayout(jPanelSubLayout);
        jPanelSubLayout.setHorizontalGroup(
            jPanelSubLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSubLayout.createSequentialGroup()
                .addGroup(jPanelSubLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelSubLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanelSubLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelSubLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanelSubLayout.createSequentialGroup()
                                .addComponent(txtUnitPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtUmo, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanelSubLayout.setVerticalGroup(
            jPanelSubLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSubLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelSubLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelSubLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUnitPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUmo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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
        });
        jScrollPane4.setViewportView(jTable2);

        jLabel19.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel19.setText("Amount In Words");

        txtNumberTowords.setEditable(false);
        txtNumberTowords.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txtNumberTowords.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumberTowordsActionPerformed(evt);
            }
        });
        txtNumberTowords.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNumberTowordsKeyPressed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel20.setText("Grand Total");

        txtGrandTotal.setEditable(false);
        txtGrandTotal.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txtGrandTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGrandTotalActionPerformed(evt);
            }
        });
        txtGrandTotal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtGrandTotalKeyPressed(evt);
            }
        });

        btnViewInvoice.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnViewInvoice.setMnemonic('s');
        btnViewInvoice.setText("View Invoice");
        btnViewInvoice.setToolTipText("");
        btnViewInvoice.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnViewInvoice.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnViewInvoice.setFocusable(false);
        btnViewInvoice.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnViewInvoice.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnViewInvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewInvoiceActionPerformed(evt);
            }
        });

        btnClose.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnClose.setMnemonic('s');
        btnClose.setText("Close");
        btnClose.setToolTipText("");
        btnClose.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnClose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnClose.setFocusable(false);
        btnClose.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnClose.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        btnSave.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnSave.setMnemonic('s');
        btnSave.setText("Save");
        btnSave.setToolTipText("");
        btnSave.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSave.setFocusable(false);
        btnSave.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSave.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNumberTowords)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtGrandTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanelMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanelSub, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnSave)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnViewInvoice)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnClose)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanelMain, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanelSub, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE))
                .addGap(35, 35, 35)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txtNumberTowords, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(txtGrandTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSave)
                    .addComponent(btnClose)
                    .addComponent(btnViewInvoice))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtDoNumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDoNumberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDoNumberActionPerformed

    private void txtDoNumberKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDoNumberKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtDoNumberKeyPressed

    private void txtLpoNumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLpoNumberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLpoNumberActionPerformed

    private void txtLpoNumberKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLpoNumberKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLpoNumberKeyPressed

    private void txtUnitPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUnitPriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUnitPriceActionPerformed

    private void txtUnitPriceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUnitPriceKeyPressed
        // TODO add your handling code here:
        textFieldDecimalValueCheck(txtUnitPrice, evt);
    }//GEN-LAST:event_txtUnitPriceKeyPressed

    private void txtQtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQtyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtQtyActionPerformed

    private void txtQtyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQtyKeyPressed
        // TODO add your handling code here:
        textFieldIntegerValueCheck(txtQty, evt);
    }//GEN-LAST:event_txtQtyKeyPressed

    private void txtUmoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUmoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUmoActionPerformed

    private void txtUmoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUmoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUmoKeyPressed

    private void txtAmountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAmountActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAmountActionPerformed

    private void txtAmountKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAmountKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAmountKeyPressed

    private void btnViewInvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewInvoiceActionPerformed
      
        InvoiceviewTemplate IVT= new InvoiceviewTemplate();
        AnarTrading.desktopPane1.add(IVT);
        IVT.setVisible(true);
        btnViewInvoice.setEnabled(false);
    }//GEN-LAST:event_btnViewInvoiceActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        updateDb();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        deleteAction();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void txtInvoiceNumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtInvoiceNumberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtInvoiceNumberActionPerformed

    private void txtInvoiceNumberKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtInvoiceNumberKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtInvoiceNumberKeyPressed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
       if(InvoiceDetailsValidation())
       {
           saveAllDetails();
       }
       else
       {
           
       }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // TODO add your handling code here:
                    dispose();
                    viewInvoiceNewForm();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void txtNumberTowordsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumberTowordsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumberTowordsActionPerformed

    private void txtNumberTowordsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumberTowordsKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumberTowordsKeyPressed

    private void txtGrandTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGrandTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGrandTotalActionPerformed

    private void txtGrandTotalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGrandTotalKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGrandTotalKeyPressed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        // TODO add your handling code here:
        trancateTempTable();
        dispose();
        AnarTrading.btnInvoiceTemplate.setEnabled(true);
    }//GEN-LAST:event_btnCloseActionPerformed

    private void txtUnitPriceFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUnitPriceFocusLost
        // TODO add your handling code here:
        amountCalculation();
    }//GEN-LAST:event_txtUnitPriceFocusLost

    private void txtQtyFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtQtyFocusLost
        // TODO add your handling code here:
        amountCalculation();
    }//GEN-LAST:event_txtQtyFocusLost

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
    }//GEN-LAST:event_formInternalFrameOpened

    private void cmbToNameItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbToNameItemStateChanged
        // TODO add your handling code here:
        fillFeild();
    }//GEN-LAST:event_cmbToNameItemStateChanged

    private void menuItemDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemDeleteActionPerformed
        // TODO add your handling code here:
        deleteAction();
    }//GEN-LAST:event_menuItemDeleteActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable2MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnViewInvoice;
    private javax.swing.JComboBox cmbToName;
    private com.toedter.calendar.JDateChooser jDateInvoiceDate;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanelMain;
    private javax.swing.JPanel jPanelSub;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar3;
    private javax.swing.JPopupMenu jtablePopUp;
    private javax.swing.JMenuItem menuItemDelete;
    private javax.swing.JMenuItem menuItemPrint;
    private javax.swing.JTextArea txtAddress;
    private javax.swing.JTextField txtAmount;
    private javax.swing.JTextArea txtDescription;
    private javax.swing.JTextField txtDoNumber;
    private javax.swing.JTextField txtGrandTotal;
    private javax.swing.JTextField txtInvoiceNumber;
    private javax.swing.JTextField txtLpoNumber;
    private javax.swing.JTextField txtNumberTowords;
    private javax.swing.JTextField txtQty;
    private javax.swing.JTextField txtUmo;
    private javax.swing.JTextField txtUnitPrice;
    // End of variables declaration//GEN-END:variables
    float unitPrice;
    int qty,subId,rowCount;
    public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat defaultDate = new SimpleDateFormat("yyyy-MM-dd");
    String dateString = "",invoiceDate="",invoiceMasterId;
}
