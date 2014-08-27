/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.vijay.stock;

import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.Point;
import java.awt.Toolkit;
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
import org.vijay.common.AnarTrading;
import org.vijay.common.AutoCompleteDecorator;
import org.vijay.common.NumberRenderer;
import org.vijay.common.connection;
import org.vijay.employee.LabourDetails;
import org.vijay.invoice.InvoiceEntry;

/**
 *
 * @author MAC
 */
public class SafetyStock extends javax.swing.JInternalFrame {

    /**
     * Creates new form SafetyStock
     */
    public SafetyStock() {
        initComponents();
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setLocation(middle);
        jdateChooserDOD.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("date")) {
                    DateOfDelivery = new SimpleDateFormat("yyyy-MM-dd").format(jdateChooserDOD.getDate());
                }
            }  
        });
        cmbItemFill();
        cmbSizeFill();
        cmbRefFill();
        viewDbStockDetails();
        viewDbCurrentStockDetails();
        jtableSelection();
    }
    public void cmbItemFill()
    {
        try {
            connection c = new connection();
            Connection con = c.conn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT itemName FROM tbl_safetystock order by itemName asc");
            while (rs.next()) {
                cmbItem.addItem(rs.getString(1));
            }
            con.close();
        } catch (SQLException ex) {
            
        }
    }
    public void cmbSizeFill()
    {
        try {
            connection c = new connection();
            Connection con = c.conn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT size FROM tbl_safetystock order by size asc");
            while (rs.next()) {
                cmbSize.addItem(rs.getString(1));
            }
            con.close();
        } catch (SQLException ex) {
            
        }
    }
    public void cmbRefFill()
    {
        try {
            connection c = new connection();
            Connection con = c.conn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT reference FROM tbl_safetystock order by reference asc");
            while (rs.next()) {
                cmbRef.addItem(rs.getString(1));
            }
            con.close();
        } catch (SQLException ex) {
            
        }
    }
    public void insertIntoDb()
    {   
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            PreparedStatement ps=con.prepareStatement("INSERT INTO tbl_safetystock(itemName,size,qty,dateOfDelivery,reference) VALUES(upper(?),upper(?),?,?,?)");           
            ps.setString(1, cmbItem.getSelectedItem().toString());
            ps.setString(2, cmbSize.getSelectedItem().toString());
            ps.setString(3, txtQty.getText());
            ps.setString(4, DateOfDelivery);
            ps.setString(5, cmbRef.getSelectedItem().toString());
            int i=ps.executeUpdate();
            if(i!=0)
            {
                    dispose();
                    ViewInvoiceDetailsForm();
            }
            con.close();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(rootPane,e+"Error SA001");
        }
    }
    public void ViewInvoiceDetailsForm()
    {
        SafetyStock  ss=new SafetyStock();
        AnarTrading.desktopPane1.add(ss);
        ss.setVisible(true);
        ss.show();
    }
    public void viewDbStockDetails()
    {
        query="select @i := @i + 1 '"+"SL.NO"+"',stockId '"+"ID"+"',itemName'"+"ITEM NAME"+"',size '"+"SIZE"+"',qty'"+"QUANTITY"+"',dateOfDelivery '"+"DATE OF DELIVERY"+"',reference'"+"REFERENCE"+"' from tbl_safetystock,(SELECT @i := 0) temp order by stockId Desc";
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery(query);
            tbldeliveryDetails.setModel(DbUtils.resultSetToTableModel(rs));
            con.close();
        }
        catch(Exception e)
        {

        }
    }
    public void viewDbCurrentStockDetails()
    {
        query="select @i := @i + 1 '"+"SL.NO"+"',itemName'"+"ITEM NAME"+"',size '"+"SIZE"+"',sum(qty)'"+"QUANTITY"+"' from tbl_safetystock,(SELECT @i := 0) temp GROUP BY itemName,size";
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery(query);
            tblStock.setModel(DbUtils.resultSetToTableModel(rs));
            con.close();
        }
        catch(Exception e)
        {

        }
    }
public void jtableSelection()
    {
        tbldeliveryDetails.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
            int rowNo=tbldeliveryDetails.getSelectedRow();
            btnSave.setEnabled(false);
            btnUpdate.setEnabled(true);
            btnDelete.setEnabled(true);
            
            stockId=Integer.parseInt(tbldeliveryDetails.getValueAt(rowNo, 1).toString());
            cmbItem.setSelectedItem(tbldeliveryDetails.getValueAt(rowNo,2).toString());
            cmbSize.setSelectedItem(tbldeliveryDetails.getValueAt(rowNo,3).toString());
            txtQty.setText(tbldeliveryDetails.getValueAt(rowNo,4).toString());
            try 
            {
                    jdateChooserDOD.setDate(defaultDate.parse(tbldeliveryDetails.getValueAt(rowNo,5).toString()));
            } 
            catch (ParseException ex) 
            {
                    Logger.getLogger(LabourDetails.class.getName()).log(Level.SEVERE, null, ex);
            } 
            cmbRef.setSelectedItem(tbldeliveryDetails.getValueAt(rowNo,6).toString());
            }
        });
    }
 public void updateDb()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            PreparedStatement ps=con.prepareStatement("UPDATE tbl_safetystock SET itemName=upper(?),size=upper(?),qty=?,dateOfDelivery=?,reference=? where stockId=?");
            ps.setString(1,cmbItem.getSelectedItem().toString());
            ps.setString(2,cmbSize.getSelectedItem().toString());
            ps.setString(3,txtQty.getText());
            ps.setString(4,DateOfDelivery);
            ps.setString(5,cmbRef.getSelectedItem().toString());
            ps.setInt(6, stockId);
            int i=ps.executeUpdate();
            if(i!=0)
            {       
                    dispose();
                    ViewInvoiceDetailsForm();
            }
            con.close();  
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(rootPane, e);
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
        jLabel1 = new javax.swing.JLabel();
        cmbItem = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        cmbSize = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        txtQty = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jdateChooserDOD = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        cmbRef = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbldeliveryDetails = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblStock = new javax.swing.JTable();
        jToolBar1 = new javax.swing.JToolBar();
        btnSave = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        View = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();

        setClosable(true);

        jLabel1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel1.setText("Item Name");

        cmbItem.setEditable(true);
        AutoCompleteDecorator.decorate(cmbItem);
        cmbItem.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        cmbItem.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "--select item name--" }));

        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel2.setText("Size");

        cmbSize.setEditable(true);
        AutoCompleteDecorator.decorate(cmbSize);
        cmbSize.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        cmbSize.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "--select size--" }));

        jLabel3.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel3.setText("Quantity");

        jLabel4.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel4.setText("Date Of Delivary");

        jdateChooserDOD.setDateFormatString("yyy-MM-dd");

        jLabel5.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel5.setText("Ref");

        cmbRef.setEditable(true);
        AutoCompleteDecorator.decorate(cmbRef);
        cmbRef.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "--select ref--" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jdateChooserDOD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(cmbItem, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbSize, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbRef, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(177, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cmbItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(cmbSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4))
                    .addComponent(jdateChooserDOD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(cmbRef, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tbldeliveryDetails.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tbldeliveryDetails);

        tblStock.setEnabled(false);
        tblStock.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblStock);

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnSave.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnSave.setText("Save");
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
        btnDelete.setText("Delete");
        btnDelete.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        jToolBar1.add(btnDelete);

        View.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        View.setText("View");
        View.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        View.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        View.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ViewActionPerformed(evt);
            }
        });
        jToolBar1.add(View);

        btnRefresh.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
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
        btnCancel.setText("Cancel");
        btnCancel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnCancel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        jToolBar1.add(btnCancel);

        jLabel6.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel6.setText("Current Stock");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addComponent(jScrollPane2)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
    
        insertIntoDb();

    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        updateDb();
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
                    int i=stmt1.executeUpdate("delete from tbl_safetystock where stockId="+stockId);
                    dispose();
                    ViewInvoiceDetailsForm();
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

    private void ViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ViewActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_ViewActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // TODO add your handling code here:
        dispose();
        viewDbStockDetails();

    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        dispose();

    }//GEN-LAST:event_btnCancelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton View;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox cmbItem;
    private javax.swing.JComboBox cmbRef;
    private javax.swing.JComboBox cmbSize;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar jToolBar1;
    private com.toedter.calendar.JDateChooser jdateChooserDOD;
    private javax.swing.JTable tblStock;
    private javax.swing.JTable tbldeliveryDetails;
    private javax.swing.JTextField txtQty;
    // End of variables declaration//GEN-END:variables

   Point middle = new Point(0,0);
   public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
   DateFormat defaultDate = new SimpleDateFormat("yyyy-MM-dd");
   String dateString = "",query,DateOfDelivery;
   int stockId;
}
