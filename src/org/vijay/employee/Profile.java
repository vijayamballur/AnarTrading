/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.vijay.employee;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import org.vijay.common.connection;
import org.vijay.report.ReportView;
import org.vijay.common.CurrentWorkingDirectory;
import org.vijay.common.DateCellRenderer;

/**
 *
 * @author MAC
 */
public class Profile extends javax.swing.JInternalFrame {

    /**
     * Creates new form PassportDocument
     */
    public Profile() {
        initComponents();
    }
    public Profile(int empId) {
        this.empId=empId;
        initComponents();
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setLocation(middle);
        viewDocuments();
        if(lblPassport.getIcon()==null)
        {
            btnSave.setEnabled(true);
            btnUpdate.setEnabled(false);
        }
        else
        {
            btnSave.setEnabled(false);
            btnUpdate.setEnabled(false);
        }
        viewEmpDetails();
        viewEntryExitDetails();
    }
    public void viewEntryExitDetails()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select @i := @i + 1 '"+"SL.NO"+"',statusId,EmployeeStatus,Purpose,statusDate,Description from tbl_employee_status,(SELECT @i := 0) temp where empId="+empId);
            jTable1.setModel(DbUtils.resultSetToTableModel(rs));
            jTable1.getColumnModel().getColumn(0).setMaxWidth(50);
            jTable1.getColumnModel().getColumn(1).setMaxWidth(50);
            jTable1.getColumnModel().getColumn(4).setCellRenderer(new DateCellRenderer());
            jTable1.setAutoCreateRowSorter(true);
            con.close();


        }
        catch(Exception e)
        {

        }
    }
    public void viewEmpDetails()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {

            PreparedStatement ps=con.prepareStatement("select empId,empName,nationality,profession,passportNumber,passportExpiry,visaExpiry,idNumber,todayDate,dob,firstParty,Status from tbl_labourdetails where empId=?");
            ps.setInt(1, empId);
            ResultSet rs=ps.executeQuery();
            while(rs.next())
            {
                lblEmpId.setText(rs.getString("empId"));
                lblname.setText(rs.getString("empName"));
                lblNationality.setText(rs.getString("nationality"));
                lblProfession.setText(rs.getString("profession"));
                lblPassportNumber.setText(rs.getString("passportNumber"));
                lblPassportExpiry.setText(rs.getString("passportExpiry"));
                lblVisaExpiry.setText(rs.getString("visaExpiry"));
                lblVisaNumber.setText(rs.getString("idNumber"));
                lblJoiningDate.setText(rs.getString("todayDate"));
                lblDateOfBirth.setText(rs.getString("dob"));
                lblSpnoserName.setText(rs.getString("firstParty"));
                lblStatus.setText(rs.getString("Status"));
                
                
            }
            
        }
        catch(Exception e)
        {
            
        }
    }
    public static BufferedImage resize(BufferedImage image, int width, int height) 
    {
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
        Graphics2D g2d = (Graphics2D) bi.createGraphics();
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        g2d.drawImage(image, 0, 0, width, height, null);
        g2d.dispose();
        return bi;
    }
    public void viewDocuments()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {

            PreparedStatement ps=con.prepareStatement("select pic from tbl_profile_pic where empId=?");
            ps.setInt(1, empId);
            ResultSet rs=ps.executeQuery();
            while(rs.next())
            {
                IDByte=rs.getBytes(1);
                ByteArrayInputStream bisPassport = new ByteArrayInputStream(IDByte);
                try 
                { 
                    bfImage=ImageIO.read(bisPassport);
                    resizedImage=resize(bfImage,200,200);
                    imageIcon=new ImageIcon(resizedImage);
                    lblPassport.setIcon(imageIcon);
                    
                } 
                catch(IOException e)
                {
                    
                }
             }
            
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

        jPanel1 = new javax.swing.JPanel();
        lblPassport = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        btnBrowse = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        lblProfession = new javax.swing.JLabel();
        lblVisaNumber = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lblNationality = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        lblDateOfBirth = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel();
        lblPassportExpiry = new javax.swing.JLabel();
        lblVisaExpiry = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblPassportNumber = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblJoiningDate = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblSpnoserName = new javax.swing.JLabel();
        lblname = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        lblEmpId = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setClosable(true);
        setTitle("Profile");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Employee Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 0, 12))); // NOI18N

        lblPassport.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnBrowse.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnBrowse.setText("Browse");
        btnBrowse.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowseActionPerformed(evt);
            }
        });
        jToolBar1.add(btnBrowse);

        btnSave.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnSave.setText("Save");
        btnSave.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        jToolBar1.add(btnSave);

        btnUpdate.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        jToolBar1.add(btnUpdate);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel15.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel15.setText("Profession");

        lblProfession.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        lblProfession.setText("lblProfession");

        lblVisaNumber.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        lblVisaNumber.setText("lblVisa/RPnumber");

        jLabel12.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel12.setText("RP/Visa Number");

        lblNationality.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        lblNationality.setText("lblNationality");

        jLabel17.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel17.setText("DateOfBirth");

        lblDateOfBirth.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        lblDateOfBirth.setText("lblDateOFBirth");

        jLabel16.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel16.setText("Status");

        lblStatus.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        lblStatus.setText("lblStatus");

        lblPassportExpiry.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        lblPassportExpiry.setText("lblPassportExpiry");

        lblVisaExpiry.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        lblVisaExpiry.setText("lblRP/VisaExpiry");

        jLabel8.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel8.setText("Passport #");

        lblPassportNumber.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        lblPassportNumber.setText("lblPassportNumber");

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel1.setText("Joining Date");

        lblJoiningDate.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        lblJoiningDate.setText("lblJoining Date");

        jLabel3.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel3.setText("Sponser Name");

        lblSpnoserName.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        lblSpnoserName.setText("lblSpnoserName");

        lblname.setFont(new java.awt.Font("Consolas", 1, 18)); // NOI18N
        lblname.setText("lblname");

        jLabel10.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel10.setText("Passport Expiry");

        jLabel14.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel14.setText("RP/Visa Expiry");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(jLabel12)
                            .addComponent(jLabel1)
                            .addComponent(jLabel16)
                            .addComponent(jLabel8))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblStatus)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblProfession)
                                    .addComponent(lblJoiningDate)
                                    .addComponent(lblPassportNumber, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                                    .addComponent(lblVisaNumber, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblSpnoserName)
                                    .addComponent(lblDateOfBirth)
                                    .addComponent(lblVisaExpiry)
                                    .addComponent(lblPassportExpiry)))))
                    .addComponent(lblNationality)
                    .addComponent(lblname, javax.swing.GroupLayout.PREFERRED_SIZE, 820, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 20, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(lblname)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNationality)
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblJoiningDate)
                    .addComponent(jLabel3)
                    .addComponent(lblSpnoserName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(lblPassportNumber)
                    .addComponent(lblPassportExpiry)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblVisaNumber)
                    .addComponent(jLabel12)
                    .addComponent(lblVisaExpiry)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(lblProfession)
                    .addComponent(jLabel17)
                    .addComponent(lblDateOfBirth))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(lblStatus))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblEmpId.setFont(new java.awt.Font("Consolas", 1, 18)); // NOI18N
        lblEmpId.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEmpId.setText("lblEmpId");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPassport, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(lblEmpId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblPassport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblEmpId)
                .addGap(13, 13, 13))
        );

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Entry & Exit Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 0, 12))); // NOI18N

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(355, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBrowseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowseActionPerformed
        // TODO add your handling code here:
        fc.showOpenDialog(this);
        fileID=fc.getSelectedFile();
        pathID=fileID.getAbsolutePath();
        try
        {
            finID=new FileInputStream(fileID);
            lenID=(int)fileID.length();
            bfImage=ImageIO.read(fileID);
            resizedImage=resize(bfImage,200,200);
            imageIcon=new ImageIcon(resizedImage);
            lblPassport.setIcon(imageIcon);
            if(imageIcon==null)
            {
                btnUpdate.setEnabled(false);
            }
            else
            {
                btnUpdate.setEnabled(true);
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(rootPane,e);
        }
    }//GEN-LAST:event_btnBrowseActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        if(lblPassport.getIcon()==null)
        {
            JOptionPane.showMessageDialog(rootPane,"Please Browse the RP then press 'Save' button");
            
        }
        else
        {
            try
            {
                connection c=new connection();
                Connection con=c.conn();
                PreparedStatement ps=con.prepareStatement("insert into tbl_profile_pic(empId,pic) values(?,?)");
                ps.setInt(1,empId);
                ps.setBinaryStream(2,finID,lenID);
                int status=ps.executeUpdate();
                if(status>0)
                {
                    btnSave.setEnabled(false);
                }
                else
                {
                    JOptionPane.showMessageDialog(rootPane,"Error");
                }
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(rootPane,e);
            }
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        if(lblPassport.getIcon()==null)
        {
            JOptionPane.showMessageDialog(rootPane,"Please Browse the new RP then press 'Update' button");
            
        }
        else
        {
            try
            {
                connection c=new connection();
                Connection con=c.conn();
                PreparedStatement ps=con.prepareStatement("update tbl_profile_pic set pic=? where empId=?");
                ps.setBinaryStream(1,finID,lenID);
                ps.setInt(2,empId);
                int i=ps.executeUpdate();
                if(i!=0)
                {
                    btnUpdate.setEnabled(false);
                }
                else
                {
                    JOptionPane.showMessageDialog(rootPane,"Error");
                }
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(rootPane,e);
            }
        }
    }//GEN-LAST:event_btnUpdateActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBrowse;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lblDateOfBirth;
    private javax.swing.JLabel lblEmpId;
    private javax.swing.JLabel lblJoiningDate;
    private javax.swing.JLabel lblNationality;
    private javax.swing.JLabel lblPassport;
    private javax.swing.JLabel lblPassportExpiry;
    private javax.swing.JLabel lblPassportNumber;
    private javax.swing.JLabel lblProfession;
    private javax.swing.JLabel lblSpnoserName;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblVisaExpiry;
    private javax.swing.JLabel lblVisaNumber;
    private javax.swing.JLabel lblname;
    // End of variables declaration//GEN-END:variables
    byte[] IDByte;
    BufferedImage bfImage,resizedImage;
    ImageIcon imageIcon;
    int empId,lenID;
    String pathID;
    File fileID;
    FileInputStream finID;
    JFileChooser fc=new JFileChooser(new File("Desktop"));
    Point middle = new Point(0,0);
}
