/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vijay.org.visa;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.vijay.common.connection;
import org.vijay.invoice.InvoiceEntry;
import static org.vijay.invoice.InvoiceEntry.dateFormat;

/**
 *
 * @author MAC
 */
public class EntryDateUpdate extends javax.swing.JDialog {

    /**
     * Creates new form EntryDateUpdate
     */
    public EntryDateUpdate(java.awt.Frame parent, boolean modal,int businessVisaId,int screenX,int screenY,String name,String ppNumber) {
        super(parent, modal);
        initComponents();
        setLocation(screenX,screenY);
        this.businessVisaId=businessVisaId;
        btnUpdate.setVisible(false);
        btnDelete.setVisible(false);
        lblApplicatName.setText(name);
        lblPassportNumber.setText(ppNumber);
        lblVisaFor.setEnabled(false);
        rBtn30days.setEnabled(false);
        rBtn60days.setEnabled(false);
        rBtn90days.setEnabled(false);
        jDateEntryDate.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) 
            {
                if (evt.getPropertyName().equals("date")) 
                {
                    entryDate = new SimpleDateFormat("yyyy-MM-dd").format(jDateEntryDate.getDate());
                    Date date1 = null;
                    try 
                    {
                        date1 = defaultDate.parse(entryDate);
                    } 
                    catch (ParseException ex) 
                    {
                        Logger.getLogger(EntryDateUpdate.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date1);

                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    int year = cal.get(Calendar.YEAR);
                    int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                    txtDaysForExpiry.setText(Integer.toString(days-1));
                }
            }  
        });
        jDateExpiryDate.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("date")) {
                    expiryDate = new SimpleDateFormat("yyyy-MM-dd").format(jDateExpiryDate.getDate());
                }
            }  
        });
    }
    private void addDaysToDate(String date, int daysToAdd) throws Exception 
     {
         Date parsedDate = dateFormat.parse(date);
         Calendar now = Calendar.getInstance();
         now.setTime(parsedDate); 
         now.add(Calendar.DAY_OF_MONTH, daysToAdd);
         expiryDate=dateFormat.format(now.getTime());
         jDateExpiryDate.setDate(now.getTime());
      }
    public EntryDateUpdate(java.awt.Frame parent, boolean modal,int businessVisaId,int screenX,int screenY,String name,String ppNumber,int i) {
        super(parent, modal);
        initComponents();
        setLocation(screenX,screenY);
        this.businessVisaId=businessVisaId;
        btnSave.setVisible(false);
        lblApplicatName.setText(name);
        lblPassportNumber.setText(ppNumber);
        jDateEntryDate.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("date")) {
                    entryDate = new SimpleDateFormat("yyyy-MM-dd").format(jDateEntryDate.getDate());
                    Date date1 = null;
                    try 
                    {
                        date1 = defaultDate.parse(entryDate);
                    } 
                    catch (ParseException ex) 
                    {
                        Logger.getLogger(EntryDateUpdate.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date1);

                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    int year = cal.get(Calendar.YEAR);
                    int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                    txtDaysForExpiry.setText(Integer.toString(days-1));
                }
            }  
        });
        jDateExpiryDate.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("date")) {
                    expiryDate = new SimpleDateFormat("yyyy-MM-dd").format(jDateExpiryDate.getDate());
                }
            }  
        });
        viewDbData();
    }
    public void viewDbData()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT entryDate,expiryDate FROM tbl_business_visa_entrydate WHERE businessVisaId="+businessVisaId);
            while(rs.next())
            {
                jDateEntryDate.setDate(rs.getDate("entryDate"));
                jDateExpiryDate.setDate(rs.getDate("expiryDate"));
            }
            
            con.close();
        }
        catch(Exception e)
        {

        }
    }
    public void insertIntoDb()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            PreparedStatement ps=con.prepareStatement("INSERT INTO tbl_business_visa_entrydate(entryDate,businessVisaId,expiryDate) VALUES(?,?,?)");           
            ps.setString(1, entryDate);
            ps.setInt(2, businessVisaId);
            ps.setString(3, expiryDate);
            int i=ps.executeUpdate();
            if(i!=0)
            {
                PreparedStatement ps1=con.prepareStatement("UPDATE tbl_business_visa_appli SET status='Entered to the Country' where businessVisaId=?");
                ps1.setInt(1, businessVisaId);
                int j=ps1.executeUpdate();
                if(j!=0)
                {
                    dispose();
                    //viewBusinessVisaForm();
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
     public void updateDb()
    {
        
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            PreparedStatement ps=con.prepareStatement("UPDATE tbl_business_visa_entrydate SET entryDate=upper(?),expiryDate=? where businessVisaId=?");
            ps.setString(1,entryDate);
            ps.setString(2,expiryDate);
            ps.setInt(3, businessVisaId);
            int i=ps.executeUpdate();
            if(i!=0)
            {
                    dispose();
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel26 = new javax.swing.JLabel();
        jDateEntryDate = new com.toedter.calendar.JDateChooser();
        jLabel27 = new javax.swing.JLabel();
        lblApplicatName = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        lblPassportNumber = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        jDateExpiryDate = new com.toedter.calendar.JDateChooser();
        jLabel29 = new javax.swing.JLabel();
        txtDaysForExpiry = new javax.swing.JTextField();
        VisaExtensionPanel = new javax.swing.JPanel();
        lblVisaFor = new javax.swing.JLabel();
        rBtn30days = new javax.swing.JRadioButton();
        rBtn60days = new javax.swing.JRadioButton();
        rBtn90days = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Entry Date");

        jLabel26.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel26.setText("Entry Date");

        jDateEntryDate.setDateFormatString("yyyy-MM-dd");
        jDateEntryDate.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jDateEntryDate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jDateEntryDateMouseClicked(evt);
            }
        });
        jDateEntryDate.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jDateEntryDateFocusLost(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel27.setText("Name");

        lblApplicatName.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N

        jLabel25.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel25.setText("Passport Number");

        lblPassportNumber.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N

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

        btnUpdate.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnUpdate.setMnemonic('c');
        btnUpdate.setText("Update");
        btnUpdate.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnDelete.setMnemonic('c');
        btnDelete.setText("Delete");
        btnDelete.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel28.setText("Expiry Date");

        jDateExpiryDate.setDateFormatString("yyyy-MM-dd");
        jDateExpiryDate.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jLabel29.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel29.setText("Days For Expiry");

        txtDaysForExpiry.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        txtDaysForExpiry.setMinimumSize(new java.awt.Dimension(6, 25));
        txtDaysForExpiry.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDaysForExpiryFocusLost(evt);
            }
        });
        txtDaysForExpiry.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDaysForExpiryKeyPressed(evt);
            }
        });

        VisaExtensionPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Visa Extension Purpose", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 0, 12))); // NOI18N

        lblVisaFor.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        lblVisaFor.setText("Visa For");

        buttonGroup1.add(rBtn30days);
        rBtn30days.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        rBtn30days.setSelected(true);
        rBtn30days.setText("30 Days");

        buttonGroup1.add(rBtn60days);
        rBtn60days.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        rBtn60days.setText("60 Days");

        buttonGroup1.add(rBtn90days);
        rBtn90days.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        rBtn90days.setText("90 Days");

        javax.swing.GroupLayout VisaExtensionPanelLayout = new javax.swing.GroupLayout(VisaExtensionPanel);
        VisaExtensionPanel.setLayout(VisaExtensionPanelLayout);
        VisaExtensionPanelLayout.setHorizontalGroup(
            VisaExtensionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(VisaExtensionPanelLayout.createSequentialGroup()
                .addComponent(lblVisaFor)
                .addGap(6, 6, 6)
                .addComponent(rBtn30days)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rBtn60days)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rBtn90days)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        VisaExtensionPanelLayout.setVerticalGroup(
            VisaExtensionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, VisaExtensionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(lblVisaFor, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(rBtn30days))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, VisaExtensionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(rBtn60days)
                .addComponent(rBtn90days))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(VisaExtensionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jDateEntryDate, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(btnSave)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnUpdate)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnDelete)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCancel)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDaysForExpiry, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateExpiryDate, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel25)
                            .addComponent(jLabel27))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblApplicatName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblPassportNumber, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(VisaExtensionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblApplicatName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPassportNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtDaysForExpiry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jDateExpiryDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateEntryDate, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(btnCancel)
                    .addComponent(btnUpdate)
                    .addComponent(btnDelete))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed

        insertIntoDb();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        dispose();

    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        updateDb();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void jDateEntryDateFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jDateEntryDateFocusLost


    }//GEN-LAST:event_jDateEntryDateFocusLost

    private void jDateEntryDateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jDateEntryDateMouseClicked
        // TODO add your handling code here:
                
    }//GEN-LAST:event_jDateEntryDateMouseClicked

    private void txtDaysForExpiryFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDaysForExpiryFocusLost
        // TODO add your handling code here:
        try {
             addDaysToDate(entryDate,Integer.parseInt(txtDaysForExpiry.getText()));
        } catch (Exception ex) {
            Logger.getLogger(InvoiceEntry.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_txtDaysForExpiryFocusLost

    private void txtDaysForExpiryKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDaysForExpiryKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtDaysForExpiryKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EntryDateUpdate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EntryDateUpdate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EntryDateUpdate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EntryDateUpdate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel VisaExtensionPanel;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUpdate;
    private javax.swing.ButtonGroup buttonGroup1;
    private com.toedter.calendar.JDateChooser jDateEntryDate;
    private com.toedter.calendar.JDateChooser jDateExpiryDate;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel lblApplicatName;
    private javax.swing.JLabel lblPassportNumber;
    private javax.swing.JLabel lblVisaFor;
    private javax.swing.JRadioButton rBtn30days;
    private javax.swing.JRadioButton rBtn60days;
    private javax.swing.JRadioButton rBtn90days;
    private javax.swing.JTextField txtDaysForExpiry;
    // End of variables declaration//GEN-END:variables
    int businessVisaId;
    public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat defaultDate = new SimpleDateFormat("yyyy-MM-dd");
    String dateString = "",entryDate="",expiryDate="";
}
