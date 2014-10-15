/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.vijay.contractEmployee;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
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
import org.vijay.common.AutoCompleteDecorator;
import org.vijay.common.connection;
import org.vijay.employee.LabourDetails;
import static org.vijay.employee.TimeSheet.isDouble;

/**
 *
 * @author MAC
 */
public final class ConEmployeeTimeSheet extends javax.swing.JDialog {

    /**
     * Creates new form ConEmployeeTimeSheet
     * @param parent
     * @param modal
     */
    public ConEmployeeTimeSheet(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        txtTotalHr.setText("0.0");
        defaultValueFills();
        setLocation(0,110);
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        cmbEmpNameFill();
        jDatecurrentMonth.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("date")) {
                    currentMonth = new SimpleDateFormat("yyyy-MM-dd").format(jDatecurrentMonth.getDate());
                }
            }  
        });
        dbDetails();
        jtableSelection();
    }
    public void fillActualDate()
    {
        fillTextFields(txtDate1, txtActualDate1);
        fillTextFields(txtDate2, txtActualDate2);
        fillTextFields(txtDate3, txtActualDate3);
        fillTextFields(txtDate4, txtActualDate4);
        fillTextFields(txtDate5, txtActualDate5);
        fillTextFields(txtDate6, txtActualDate6);
        fillTextFields(txtDate7, txtActualDate7);
        fillTextFields(txtDate8, txtActualDate8);
        fillTextFields(txtDate9, txtActualDate9);
        fillTextFields(txtDate10, txtActualDate10);
        fillTextFields(txtDate11, txtActualDate11);
        fillTextFields(txtDate12, txtActualDate12);
        fillTextFields(txtDate13, txtActualDate13);
        fillTextFields(txtDate14, txtActualDate14);
        fillTextFields(txtDate15, txtActualDate15);
        fillTextFields(txtDate16, txtActualDate16);
        fillTextFields(txtDate17, txtActualDate17);
        fillTextFields(txtDate18, txtActualDate18);
        fillTextFields(txtDate19, txtActualDate19);
        fillTextFields(txtDate20, txtActualDate20);
        fillTextFields(txtDate21, txtActualDate21);
        fillTextFields(txtDate22, txtActualDate22);
        fillTextFields(txtDate23, txtActualDate23);
        fillTextFields(txtDate24, txtActualDate24);
        fillTextFields(txtDate25, txtActualDate25);
        fillTextFields(txtDate26, txtActualDate26);
        fillTextFields(txtDate27, txtActualDate27);
        fillTextFields(txtDate28, txtActualDate28);
        fillTextFields(txtDate29, txtActualDate29);
        fillTextFields(txtDate30, txtActualDate30);
        fillTextFields(txtDate31, txtActualDate31);
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

            
            timeSheetId=Integer.parseInt(jTable1.getValueAt(rowNo,0).toString());
            empId=Integer.parseInt(jTable1.getValueAt(rowNo,1).toString());
            txtDate1.setText(jTable1.getValueAt(rowNo,3).toString());
            txtDate2.setText(jTable1.getValueAt(rowNo,4).toString());
            txtDate3.setText(jTable1.getValueAt(rowNo,5).toString());
            txtDate4.setText(jTable1.getValueAt(rowNo,6).toString());
            txtDate5.setText(jTable1.getValueAt(rowNo,7).toString());
            txtDate6.setText(jTable1.getValueAt(rowNo,8).toString());
            txtDate7.setText(jTable1.getValueAt(rowNo,9).toString());
            txtDate8.setText(jTable1.getValueAt(rowNo,10).toString());
            txtDate9.setText(jTable1.getValueAt(rowNo,11).toString());
            txtDate10.setText(jTable1.getValueAt(rowNo,12).toString());
            txtDate11.setText(jTable1.getValueAt(rowNo,13).toString());
            txtDate12.setText(jTable1.getValueAt(rowNo,14).toString());
            txtDate13.setText(jTable1.getValueAt(rowNo,15).toString());
            txtDate14.setText(jTable1.getValueAt(rowNo,16).toString());
            txtDate15.setText(jTable1.getValueAt(rowNo,17).toString());
            txtDate16.setText(jTable1.getValueAt(rowNo,18).toString());
            txtDate17.setText(jTable1.getValueAt(rowNo,19).toString());
            txtDate18.setText(jTable1.getValueAt(rowNo,20).toString());
            txtDate19.setText(jTable1.getValueAt(rowNo,21).toString());
            txtDate20.setText(jTable1.getValueAt(rowNo,22).toString());
            txtDate21.setText(jTable1.getValueAt(rowNo,23).toString());
            txtDate22.setText(jTable1.getValueAt(rowNo,24).toString());
            txtDate23.setText(jTable1.getValueAt(rowNo,25).toString());
            txtDate24.setText(jTable1.getValueAt(rowNo,26).toString());
            txtDate25.setText(jTable1.getValueAt(rowNo,27).toString());
            txtDate26.setText(jTable1.getValueAt(rowNo,28).toString());
            txtDate27.setText(jTable1.getValueAt(rowNo,29).toString());
            txtDate28.setText(jTable1.getValueAt(rowNo,30).toString());
            txtDate29.setText(jTable1.getValueAt(rowNo,31).toString());
            txtDate30.setText(jTable1.getValueAt(rowNo,32).toString());
            txtDate31.setText(jTable1.getValueAt(rowNo,33).toString());
            try 
            {
                    jDatecurrentMonth.setDate(defaultDate.parse(jTable1.getValueAt(rowNo,37).toString()));
            } 
            catch (ParseException ex) 
            {
                    Logger.getLogger(LabourDetails.class.getName()).log(Level.SEVERE, null, ex);
            }  
            fillActualDate();
            empNameUsingEmpId();
            }
        });
    }
    public void empIdUsingEmpName()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT empId FROM tbl_conemployee WHERE empname='"+cmbEmployeeName.getSelectedItem()+"' and status=0");
            while(rs.next())
            {
                empId=rs.getInt(1);
            }          
        }
        catch(Exception e)
        {

        }
    }
    public void empNameUsingEmpId()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT empName FROM tbl_conemployee WHERE empId="+empId+" and status=0");
            while(rs.next())
            {
                cmbEmployeeName.setSelectedItem(rs.getString("empName"));
            }          
        }
        catch(Exception e)
        {

        }
    }
    public void viewEmployeeDetailsUsingName()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT timeSheetId '"+"Id"+"',tbl_con_timesheet.empId '"+"empId"+"',empName '"+"Name"+"',date1 '"+"1"+"',date2 '"+"2"+"',date3 '"+"3"+"',date4 '"+"4"+"',date5 '"+"5"+"',date6 '"+"6"+"',date7 '"+"7"+"',date8 '"+"8"+"',date9 '"+"9"+"',date10 '"+"10"+"',date11 '"+"11"+"',date12 '"+"12"+"',date13 '"+"13"+"',date14 '"+"14"+"',date15 '"+"15"+"',date16 '"+"16"+"',date17 '"+"17"+"',date18 '"+"18"+"',date19 '"+"19"+"',date20 '"+"20"+"',date21 '"+"21"+"',date22 '"+"22"+"',date23 '"+"23"+"',date24 '"+"24"+"',date25 '"+"25"+"',date26 '"+"26"+"',date27 '"+"27"+"',date28 '"+"28"+"',date29 '"+"29"+"',date30 '"+"30"+"',date31 '"+"31"+"',totalHr THr,countAbs Abs,countLeave Lev,DATE_FORMAT(timesheetDate,'"+"%b %y"+"') MONTH FROM anar.tbl_con_timesheet INNER JOIN anar.tbl_conemployee ON (tbl_con_timesheet.empId = tbl_conemployee.empId) where empname='"+cmbEmployeeName.getSelectedItem()+"' ORDER BY DATE_FORMAT(timesheetDate,'"+"%b %y"+"') desc;");
           
            jTable1.setModel(DbUtils.resultSetToTableModel(rs));
            jTable1.setAutoCreateRowSorter(true);
            con.close();
             
            jTable1.getColumnModel().getColumn(0).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(1).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(3).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(4).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(5).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(6).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(7).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(8).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(9).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(10).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(11).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(12).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(13).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(14).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(15).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(16).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(17).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(18).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(19).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(20).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(21).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(22).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(23).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(24).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(25).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(26).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(27).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(28).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(29).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(30).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(31).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(32).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(33).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(34).setMaxWidth(50);
            jTable1.getColumnModel().getColumn(35).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(36).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(37).setMaxWidth(50);

            
            jTable1.setShowHorizontalLines( true );
            jTable1.setRowSelectionAllowed( true );

        }
        catch(Exception e)
        {

        }
    }
    public void dbDetails()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT timeSheetId '"+"Id"+"',tbl_con_timesheet.empId '"+"empId"+"',empName '"+"Name"+"',date1 '"+"1"+"',date2 '"+"2"+"',date3 '"+"3"+"',date4 '"+"4"+"',date5 '"+"5"+"',date6 '"+"6"+"',date7 '"+"7"+"',date8 '"+"8"+"',date9 '"+"9"+"',date10 '"+"10"+"',date11 '"+"11"+"',date12 '"+"12"+"',date13 '"+"13"+"',date14 '"+"14"+"',date15 '"+"15"+"',date16 '"+"16"+"',date17 '"+"17"+"',date18 '"+"18"+"',date19 '"+"19"+"',date20 '"+"20"+"',date21 '"+"21"+"',date22 '"+"22"+"',date23 '"+"23"+"',date24 '"+"24"+"',date25 '"+"25"+"',date26 '"+"26"+"',date27 '"+"27"+"',date28 '"+"28"+"',date29 '"+"29"+"',date30 '"+"30"+"',date31 '"+"31"+"',totalHr THr,countAbs Abs,countLeave Lev,DATE_FORMAT(timesheetDate,'"+"%b %y"+"') MONTH FROM anar.tbl_con_timesheet INNER JOIN anar.tbl_conemployee ON (tbl_con_timesheet.empId = tbl_conemployee.empId) ORDER BY DATE_FORMAT(timesheetDate,'"+"%b %y"+"')");
            jTable1.setModel(DbUtils.resultSetToTableModel(rs));
            jTable1.setAutoCreateRowSorter(true);
            con.close();
            jTable1.getColumnModel().getColumn(0).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(1).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(3).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(4).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(5).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(6).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(7).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(8).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(9).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(10).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(11).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(12).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(13).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(14).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(15).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(16).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(17).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(18).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(19).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(20).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(21).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(22).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(23).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(24).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(25).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(26).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(27).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(28).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(29).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(30).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(31).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(32).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(33).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(34).setMaxWidth(50);
            jTable1.getColumnModel().getColumn(35).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(36).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(37).setMaxWidth(50); 
            jTable1.setShowHorizontalLines( true );
            jTable1.setRowSelectionAllowed( true );

        }
        catch(Exception e)
        {

        }
    }
    public void defaultValueFills()
    {
        txtActualDate1.setText("0.0");
        txtActualDate2.setText("0.0");
        txtActualDate3.setText("0.0");
        txtActualDate4.setText("0.0");
        txtActualDate5.setText("0.0");
        txtActualDate6.setText("0.0");
        txtActualDate7.setText("0.0");
        txtActualDate8.setText("0.0");
        txtActualDate9.setText("0.0");
        txtActualDate10.setText("0.0");
        txtActualDate11.setText("0.0");
        txtActualDate12.setText("0.0");
        txtActualDate13.setText("0.0");
        txtActualDate14.setText("0.0");
        txtActualDate15.setText("0.0");
        txtActualDate16.setText("0.0");
        txtActualDate17.setText("0.0");
        txtActualDate18.setText("0.0");
        txtActualDate19.setText("0.0");
        txtActualDate20.setText("0.0");
        txtActualDate21.setText("0.0");
        txtActualDate22.setText("0.0");
        txtActualDate23.setText("0.0");
        txtActualDate24.setText("0.0");
        txtActualDate25.setText("0.0");
        txtActualDate26.setText("0.0");
        txtActualDate27.setText("0.0");
        txtActualDate28.setText("0.0");
        txtActualDate29.setText("0.0");
        txtActualDate30.setText("0.0");
        txtActualDate31.setText("0.0");
        
    }
    public void cmbEmpNameFill() {
        try {
            connection c = new connection();
            Connection con = c.conn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT empName FROM tbl_conemployee WHERE STATUS=0 ORDER BY empName");
            while (rs.next()) {
                cmbEmployeeName.addItem(rs.getString(1));
            }
            con.close();
        } catch (SQLException ex) {
            
        }
    }
    public void upperCase(JTextField txtFiled)
    {
        txtFiled.setText(txtFiled.getText().toUpperCase());
    }
    public void textFieldValueCheck(JTextField txtFiled,java.awt.event.KeyEvent evt)
    {
        int charCode= evt.getKeyCode();
            if (((charCode >=KeyEvent.VK_0) && (charCode<=KeyEvent.VK_9))||((charCode >=KeyEvent.VK_NUMPAD0) && (charCode<=KeyEvent.VK_NUMPAD9))|| charCode==KeyEvent.VK_BACK_SPACE || charCode==KeyEvent.VK_DELETE|| charCode==KeyEvent.VK_A||charCode==KeyEvent.VK_H||charCode==KeyEvent.VK_L||charCode==KeyEvent.VK_O||charCode==KeyEvent.VK_F||charCode==KeyEvent.VK_DECIMAL||charCode==KeyEvent.VK_CAPS_LOCK||charCode==KeyEvent.VK_TAB||charCode==KeyEvent.VK_ALT||charCode==KeyEvent.VK_SHIFT)
            {
                
            }
            else
            {
                JOptionPane.showMessageDialog(null,"This Value is not allowed in the field !!","Warning!!!",JOptionPane.ERROR_MESSAGE);
                txtFiled.setText("");
            }
    }
//    public void viewEmployeeDetailsUsingName()
//    {
//        connection c=new connection();
//        Connection con=c.conn();
//        try
//        {
//            Statement stmt=con.createStatement();
//            ResultSet rs=stmt.executeQuery("select empId from tbl_conemployee where empname='"+cmbEmployeeName.getSelectedItem()+"'and status=0");
//            while(rs.next())
//            {
//                empId=rs.getInt(1);
//            }
//            
//        }
//        catch(Exception e)
//        {
//
//        }
//    }
    public void countAbsent()
    {
        txtAbsent.setText(Integer.toString(countACharacter('A',countAbsAndPre())));
    }
    public void countLeave()
    {
        txtLeave.setText(Integer.toString(countACharacter('L',countAbsAndPre())));
    }
    public int countACharacter(char thecharacter, String stringtocountcharactersin) 
    {
        int count = 0;
        for(int i = 0; i < stringtocountcharactersin.length(); i++) 
        {
            if(stringtocountcharactersin.charAt(i) == thecharacter) 
            {
                count++;
            }
        }
        return count;
    }
    public String countAbsAndPre()
    {
       String countAbsAndPre=txtDate1.getText()+
                txtDate2.getText()+
                txtDate3.getText()+
                txtDate4.getText()+
                txtDate5.getText()+
                txtDate6.getText()+
                txtDate7.getText()+
                txtDate8.getText()+
                txtDate9.getText()+
                txtDate10.getText()+
                txtDate11.getText()+
                txtDate12.getText()+
                txtDate13.getText()+
                txtDate14.getText()+
                txtDate15.getText()+
                txtDate16.getText()+
                txtDate17.getText()+
                txtDate18.getText()+
                txtDate19.getText()+
                txtDate20.getText()+
                txtDate21.getText()+
                txtDate22.getText()+
                txtDate23.getText()+
                txtDate24.getText()+
                txtDate25.getText()+
                txtDate26.getText()+
                txtDate27.getText()+
                txtDate28.getText()+
                txtDate29.getText()+
                txtDate30.getText()+
                txtDate31.getText();
                return countAbsAndPre;
    }
    public void fillTextFields(JTextField date,JTextField actualDate)
    {
            if(isDouble(date.getText()))
            {
                if(Double.parseDouble(date.getText())-0>0)
                {
                    actualDate.setText(Double.toString(Double.parseDouble(date.getText())-0));
                }
                else
                {
                    actualDate.setText("0.0");
                }
            }
            else
            {
                
                upperCase(date);
                if(date.getText().equalsIgnoreCase("a"))
                {
                    actualDate.setText("0.0");
                }
                if(date.getText().equalsIgnoreCase("l"))
                {
                    actualDate.setText("0.0");
                }
                if(date.getText().equalsIgnoreCase(""))
                {
                    actualDate.setText("0.0");
                    date.setText("0.0");
                }
                
            }
           totalHrs();
           countAbsent();
           countLeave();
           
        
    }
    public void totalHrs()
    {
        totalHr=
                Double.parseDouble(txtActualDate1.getText())+
                Double.parseDouble(txtActualDate2.getText())+
                Double.parseDouble(txtActualDate3.getText())+
                Double.parseDouble(txtActualDate4.getText())+
                Double.parseDouble(txtActualDate5.getText())+
                Double.parseDouble(txtActualDate6.getText())+
                Double.parseDouble(txtActualDate7.getText())+
                Double.parseDouble(txtActualDate8.getText())+
                Double.parseDouble(txtActualDate9.getText())+
                Double.parseDouble(txtActualDate10.getText())+
                Double.parseDouble(txtActualDate11.getText())+
                Double.parseDouble(txtActualDate12.getText())+
                Double.parseDouble(txtActualDate13.getText())+
                Double.parseDouble(txtActualDate14.getText())+
                Double.parseDouble(txtActualDate15.getText())+
                Double.parseDouble(txtActualDate16.getText())+
                Double.parseDouble(txtActualDate17.getText())+
                Double.parseDouble(txtActualDate18.getText())+
                Double.parseDouble(txtActualDate19.getText())+
                Double.parseDouble(txtActualDate20.getText())+
                Double.parseDouble(txtActualDate21.getText())+
                Double.parseDouble(txtActualDate22.getText())+
                Double.parseDouble(txtActualDate23.getText())+
                Double.parseDouble(txtActualDate24.getText())+
                Double.parseDouble(txtActualDate25.getText())+
                Double.parseDouble(txtActualDate26.getText())+
                Double.parseDouble(txtActualDate27.getText())+
                Double.parseDouble(txtActualDate28.getText())+
                Double.parseDouble(txtActualDate29.getText())+
                Double.parseDouble(txtActualDate30.getText())+
                Double.parseDouble(txtActualDate31.getText());
        txtTotalHr.setText(Double.toString(totalHr));
                
        
    }
    public void insertIntoDb()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            PreparedStatement ps=con.prepareStatement("INSERT INTO tbl_con_timesheet(empId,date1,date2,date3,date4,date5,date6,date7,date8,date9,date10,date11,date12,date13,date14,date15,date16,date17,date18,date19,date20,date21,date22,date23,date24,date25,date26,date27,date28,date29,date30,date31,totalHr,countAbs,countLeave,timesheetDate) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");           
            ps.setInt(1, empId);
            ps.setString(2, txtDate1.getText());
            ps.setString(3, txtDate2.getText());
            ps.setString(4, txtDate3.getText());
            ps.setString(5, txtDate4.getText());
            ps.setString(6, txtDate5.getText());
            ps.setString(7, txtDate6.getText());
            ps.setString(8, txtDate7.getText());
            ps.setString(9, txtDate8.getText());
            ps.setString(10, txtDate9.getText());
            ps.setString(11, txtDate10.getText());
            ps.setString(12, txtDate11.getText());
            ps.setString(13, txtDate12.getText());
            ps.setString(14, txtDate13.getText());
            ps.setString(15, txtDate14.getText());
            ps.setString(16, txtDate15.getText());
            ps.setString(17, txtDate16.getText());
            ps.setString(18, txtDate17.getText());
            ps.setString(19, txtDate18.getText());
            ps.setString(20, txtDate19.getText());
            ps.setString(21, txtDate20.getText());
            ps.setString(22, txtDate21.getText());
            ps.setString(23, txtDate22.getText());
            ps.setString(24, txtDate23.getText());
            ps.setString(25, txtDate24.getText());
            ps.setString(26, txtDate25.getText());
            ps.setString(27, txtDate26.getText());
            ps.setString(28, txtDate27.getText()); 
            ps.setString(29, txtDate28.getText());
            ps.setString(30, txtDate29.getText());
            ps.setString(31, txtDate30.getText());
            ps.setString(32, txtDate31.getText());
            ps.setString(33, txtTotalHr.getText());
            ps.setString(34, txtAbsent.getText());
            ps.setString(35, txtLeave.getText());
            ps.setString(36, currentMonth);
            int i=ps.executeUpdate();
            if(i!=0)
            {
                    dispose();
                    viewConTimesheet();
            }
            con.close();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(rootPane, e, "ERROR!!", JOptionPane.ERROR_MESSAGE);
            //JOptionPane.showMessageDialog(rootPane,e+"Error SA001");
        }
    }
    public void viewConTimesheet()
    {
        ConEmployeeTimeSheet conTimeSheet=new ConEmployeeTimeSheet(null, true);
        conTimeSheet.setVisible(true);
    }
    public void updateDb()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            PreparedStatement ps=con.prepareStatement("UPDATE tbl_con_timesheet SET empId=?,date1=?,date2=?,date3=?,date4=?,date5=?,date6=?,date7=?,date8=?,date9=?,date10=?,date11=?,date12=?,date13=?,date14=?,date15=?,date16=?,date17=?,date18=?,date19=?,date20=?,date21=?,date22=?,date23=?,date24=?,date25=?,date26=?,date27=?,date28=?,date29=?,date30=?,date31=?,totalHr=?,countAbs=?,countLeave=?,timesheetDate=? where timeSheetId=?");
            ps.setInt(1,empId);
            ps.setString(2,txtDate1.getText());
            ps.setString(3,txtDate2.getText());
            ps.setString(4,txtDate3.getText());
            ps.setString(5,txtDate4.getText());
            ps.setString(6,txtDate5.getText());
            ps.setString(7,txtDate6.getText());
            ps.setString(8,txtDate7.getText());
            ps.setString(9,txtDate8.getText());
            ps.setString(10,txtDate9.getText());
            ps.setString(11,txtDate10.getText());
            ps.setString(12,txtDate11.getText());
            ps.setString(13,txtDate12.getText());
            ps.setString(14,txtDate13.getText());
            ps.setString(15,txtDate14.getText());
            ps.setString(16,txtDate15.getText());
            ps.setString(17,txtDate16.getText());
            ps.setString(18,txtDate17.getText());
            ps.setString(19,txtDate18.getText());
            ps.setString(20,txtDate19.getText());
            ps.setString(21,txtDate20.getText());
            ps.setString(22,txtDate21.getText());
            ps.setString(23,txtDate22.getText());
            ps.setString(24,txtDate23.getText());
            ps.setString(25,txtDate24.getText());
            ps.setString(26,txtDate25.getText());
            ps.setString(27,txtDate26.getText());
            ps.setString(28,txtDate27.getText());
            ps.setString(29,txtDate28.getText());
            ps.setString(30,txtDate29.getText());
            ps.setString(31,txtDate30.getText());
            ps.setString(32,txtDate31.getText());
            ps.setString(33,txtTotalHr.getText());
            ps.setString(34,txtAbsent.getText());
            ps.setString(35,txtLeave.getText());
            ps.setString(36,currentMonth);
            ps.setInt(37, timeSheetId);
            int i=ps.executeUpdate();
            if(i!=0)
            {
                    dispose();
                    viewConTimesheet();
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
                    int i=stmt1.executeUpdate("delete from tbl_con_timesheet  where timeSheetId="+timeSheetId);
                    if(i!=0)
                    {
                        dispose();
                        viewConTimesheet();
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

        txtDate1 = new javax.swing.JTextField();
        txtDate2 = new javax.swing.JTextField();
        txtDate3 = new javax.swing.JTextField();
        txtDate4 = new javax.swing.JTextField();
        txtDate5 = new javax.swing.JTextField();
        txtDate6 = new javax.swing.JTextField();
        txtDate7 = new javax.swing.JTextField();
        txtDate8 = new javax.swing.JTextField();
        txtDate9 = new javax.swing.JTextField();
        txtDate10 = new javax.swing.JTextField();
        txtDate11 = new javax.swing.JTextField();
        txtDate12 = new javax.swing.JTextField();
        txtDate13 = new javax.swing.JTextField();
        txtDate14 = new javax.swing.JTextField();
        txtDate15 = new javax.swing.JTextField();
        txtDate16 = new javax.swing.JTextField();
        txtDate17 = new javax.swing.JTextField();
        txtDate18 = new javax.swing.JTextField();
        txtDate19 = new javax.swing.JTextField();
        txtDate20 = new javax.swing.JTextField();
        txtDate21 = new javax.swing.JTextField();
        txtDate22 = new javax.swing.JTextField();
        txtDate23 = new javax.swing.JTextField();
        txtDate24 = new javax.swing.JTextField();
        txtDate25 = new javax.swing.JTextField();
        txtDate26 = new javax.swing.JTextField();
        txtDate27 = new javax.swing.JTextField();
        txtDate28 = new javax.swing.JTextField();
        txtDate29 = new javax.swing.JTextField();
        txtDate30 = new javax.swing.JTextField();
        txtDate31 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jDatecurrentMonth = new com.toedter.calendar.JDateChooser();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        cmbEmployeeName = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jToolBar1 = new javax.swing.JToolBar();
        btnSave = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        txtTotalHr = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        txtAbsent = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        txtLeave = new javax.swing.JTextField();
        txtActualDate1 = new javax.swing.JTextField();
        txtActualDate2 = new javax.swing.JTextField();
        txtActualDate3 = new javax.swing.JTextField();
        txtActualDate4 = new javax.swing.JTextField();
        txtActualDate5 = new javax.swing.JTextField();
        txtActualDate6 = new javax.swing.JTextField();
        txtActualDate7 = new javax.swing.JTextField();
        txtActualDate8 = new javax.swing.JTextField();
        txtActualDate9 = new javax.swing.JTextField();
        txtActualDate10 = new javax.swing.JTextField();
        txtActualDate11 = new javax.swing.JTextField();
        txtActualDate12 = new javax.swing.JTextField();
        txtActualDate13 = new javax.swing.JTextField();
        txtActualDate14 = new javax.swing.JTextField();
        txtActualDate15 = new javax.swing.JTextField();
        txtActualDate16 = new javax.swing.JTextField();
        txtActualDate17 = new javax.swing.JTextField();
        txtActualDate18 = new javax.swing.JTextField();
        txtActualDate19 = new javax.swing.JTextField();
        txtActualDate22 = new javax.swing.JTextField();
        txtActualDate23 = new javax.swing.JTextField();
        txtActualDate24 = new javax.swing.JTextField();
        txtActualDate25 = new javax.swing.JTextField();
        txtActualDate26 = new javax.swing.JTextField();
        txtActualDate27 = new javax.swing.JTextField();
        txtActualDate28 = new javax.swing.JTextField();
        txtActualDate29 = new javax.swing.JTextField();
        txtActualDate30 = new javax.swing.JTextField();
        txtActualDate31 = new javax.swing.JTextField();
        txtActualDate20 = new javax.swing.JTextField();
        txtActualDate21 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Contract Employee Time Sheet");
        setName("txtActualDate"); // NOI18N

        txtDate1.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtDate1.setMinimumSize(new java.awt.Dimension(6, 25));
        txtDate1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDate1FocusLost(evt);
            }
        });
        txtDate1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDate1KeyPressed(evt);
            }
        });

        txtDate2.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtDate2.setMinimumSize(new java.awt.Dimension(6, 25));
        txtDate2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDate2FocusLost(evt);
            }
        });
        txtDate2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDate2KeyPressed(evt);
            }
        });

        txtDate3.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtDate3.setMinimumSize(new java.awt.Dimension(6, 25));
        txtDate3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDate3ActionPerformed(evt);
            }
        });
        txtDate3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDate3FocusLost(evt);
            }
        });
        txtDate3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDate3KeyPressed(evt);
            }
        });

        txtDate4.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtDate4.setMinimumSize(new java.awt.Dimension(6, 25));
        txtDate4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDate4FocusLost(evt);
            }
        });
        txtDate4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDate4KeyPressed(evt);
            }
        });

        txtDate5.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtDate5.setMinimumSize(new java.awt.Dimension(6, 25));
        txtDate5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDate5FocusLost(evt);
            }
        });
        txtDate5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDate5KeyPressed(evt);
            }
        });

        txtDate6.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtDate6.setMinimumSize(new java.awt.Dimension(6, 25));
        txtDate6.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDate6FocusLost(evt);
            }
        });
        txtDate6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDate6KeyPressed(evt);
            }
        });

        txtDate7.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtDate7.setMinimumSize(new java.awt.Dimension(6, 25));
        txtDate7.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDate7FocusLost(evt);
            }
        });
        txtDate7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDate7KeyPressed(evt);
            }
        });

        txtDate8.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtDate8.setMinimumSize(new java.awt.Dimension(6, 25));
        txtDate8.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDate8FocusLost(evt);
            }
        });
        txtDate8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDate8KeyPressed(evt);
            }
        });

        txtDate9.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtDate9.setMinimumSize(new java.awt.Dimension(6, 25));
        txtDate9.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDate9FocusLost(evt);
            }
        });
        txtDate9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDate9KeyPressed(evt);
            }
        });

        txtDate10.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtDate10.setMinimumSize(new java.awt.Dimension(6, 25));
        txtDate10.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDate10FocusLost(evt);
            }
        });
        txtDate10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDate10KeyPressed(evt);
            }
        });

        txtDate11.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtDate11.setMinimumSize(new java.awt.Dimension(6, 25));
        txtDate11.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDate11FocusLost(evt);
            }
        });
        txtDate11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDate11KeyPressed(evt);
            }
        });

        txtDate12.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtDate12.setMinimumSize(new java.awt.Dimension(6, 25));
        txtDate12.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDate12FocusLost(evt);
            }
        });
        txtDate12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDate12KeyPressed(evt);
            }
        });

        txtDate13.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtDate13.setMinimumSize(new java.awt.Dimension(6, 25));
        txtDate13.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDate13FocusLost(evt);
            }
        });
        txtDate13.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDate13KeyPressed(evt);
            }
        });

        txtDate14.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtDate14.setMinimumSize(new java.awt.Dimension(6, 25));
        txtDate14.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDate14FocusLost(evt);
            }
        });
        txtDate14.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDate14KeyPressed(evt);
            }
        });

        txtDate15.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtDate15.setMinimumSize(new java.awt.Dimension(6, 25));
        txtDate15.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDate15FocusLost(evt);
            }
        });
        txtDate15.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDate15KeyPressed(evt);
            }
        });

        txtDate16.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtDate16.setMinimumSize(new java.awt.Dimension(6, 25));
        txtDate16.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDate16FocusLost(evt);
            }
        });
        txtDate16.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDate16KeyPressed(evt);
            }
        });

        txtDate17.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtDate17.setMinimumSize(new java.awt.Dimension(6, 25));
        txtDate17.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDate17FocusLost(evt);
            }
        });
        txtDate17.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDate17KeyPressed(evt);
            }
        });

        txtDate18.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtDate18.setMinimumSize(new java.awt.Dimension(6, 25));
        txtDate18.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDate18FocusLost(evt);
            }
        });
        txtDate18.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDate18KeyPressed(evt);
            }
        });

        txtDate19.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtDate19.setMinimumSize(new java.awt.Dimension(6, 25));
        txtDate19.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDate19FocusLost(evt);
            }
        });
        txtDate19.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDate19KeyPressed(evt);
            }
        });

        txtDate20.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtDate20.setMinimumSize(new java.awt.Dimension(6, 25));
        txtDate20.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDate20FocusLost(evt);
            }
        });
        txtDate20.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDate20KeyPressed(evt);
            }
        });

        txtDate21.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtDate21.setMinimumSize(new java.awt.Dimension(6, 25));
        txtDate21.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDate21FocusLost(evt);
            }
        });
        txtDate21.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDate21KeyPressed(evt);
            }
        });

        txtDate22.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtDate22.setMinimumSize(new java.awt.Dimension(6, 25));
        txtDate22.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDate22FocusLost(evt);
            }
        });
        txtDate22.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDate22KeyPressed(evt);
            }
        });

        txtDate23.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtDate23.setMinimumSize(new java.awt.Dimension(6, 25));
        txtDate23.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDate23FocusLost(evt);
            }
        });
        txtDate23.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDate23KeyPressed(evt);
            }
        });

        txtDate24.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtDate24.setMinimumSize(new java.awt.Dimension(6, 25));
        txtDate24.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDate24FocusLost(evt);
            }
        });
        txtDate24.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDate24KeyPressed(evt);
            }
        });

        txtDate25.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtDate25.setMinimumSize(new java.awt.Dimension(6, 25));
        txtDate25.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDate25FocusLost(evt);
            }
        });
        txtDate25.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDate25KeyPressed(evt);
            }
        });

        txtDate26.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtDate26.setMinimumSize(new java.awt.Dimension(6, 25));
        txtDate26.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDate26FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDate26FocusLost(evt);
            }
        });
        txtDate26.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDate26KeyPressed(evt);
            }
        });

        txtDate27.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtDate27.setMinimumSize(new java.awt.Dimension(6, 25));
        txtDate27.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDate27FocusLost(evt);
            }
        });
        txtDate27.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDate27KeyPressed(evt);
            }
        });

        txtDate28.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtDate28.setMinimumSize(new java.awt.Dimension(6, 25));
        txtDate28.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDate28FocusLost(evt);
            }
        });
        txtDate28.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDate28KeyPressed(evt);
            }
        });

        txtDate29.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtDate29.setMinimumSize(new java.awt.Dimension(6, 25));
        txtDate29.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDate29FocusLost(evt);
            }
        });
        txtDate29.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDate29KeyPressed(evt);
            }
        });

        txtDate30.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtDate30.setMinimumSize(new java.awt.Dimension(6, 25));
        txtDate30.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDate30FocusLost(evt);
            }
        });
        txtDate30.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDate30KeyPressed(evt);
            }
        });

        txtDate31.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtDate31.setMinimumSize(new java.awt.Dimension(6, 25));
        txtDate31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDate31ActionPerformed(evt);
            }
        });
        txtDate31.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDate31FocusLost(evt);
            }
        });
        txtDate31.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDate31KeyPressed(evt);
            }
        });

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("1");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("2");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("3");

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("4");

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("5");

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("6");

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("7");

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("8");

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("9");

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("10");

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("11");

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("12");

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("13");

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("14");

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("15");

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("16");

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("17");

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("18");

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("19");

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("20");

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("21");

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("22");

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("23");

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("24");

        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("25");

        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("26");

        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("27");

        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("28");

        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setText("29");

        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel31.setText("30");

        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setText("31");

        jDatecurrentMonth.setDateFormatString("MMM yyyy");
        jDatecurrentMonth.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jLabel33.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel33.setText("Time Sheet Month");

        jLabel34.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel34.setText("Employee Name");

        cmbEmployeeName.setEditable(true);
        AutoCompleteDecorator.decorate(cmbEmployeeName);
        cmbEmployeeName.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cmbEmployeeName.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "--select name--" }));
        cmbEmployeeName.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbEmployeeNameItemStateChanged(evt);
            }
        });
        cmbEmployeeName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbEmployeeNameActionPerformed(evt);
            }
        });

        jTable1.setFont(new java.awt.Font("Century Gothic", 0, 9)); // NOI18N
        jTable1.getTableHeader().setFont(new Font("Century Gothic", Font.PLAIN,9));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTable1);

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

        jLabel35.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel35.setText("Total Hours");

        txtTotalHr.setEditable(false);
        txtTotalHr.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtTotalHr.setMinimumSize(new java.awt.Dimension(6, 25));
        txtTotalHr.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTotalHrFocusLost(evt);
            }
        });
        txtTotalHr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTotalHrKeyPressed(evt);
            }
        });

        jLabel36.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel36.setText("No.Of.Absent");

        txtAbsent.setEditable(false);
        txtAbsent.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtAbsent.setMinimumSize(new java.awt.Dimension(6, 25));
        txtAbsent.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtAbsentFocusLost(evt);
            }
        });
        txtAbsent.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtAbsentKeyPressed(evt);
            }
        });

        jLabel37.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel37.setText("No.Of.Leave");

        txtLeave.setEditable(false);
        txtLeave.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtLeave.setMinimumSize(new java.awt.Dimension(6, 25));
        txtLeave.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtLeaveFocusLost(evt);
            }
        });
        txtLeave.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtLeaveKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel35)
                .addGap(18, 18, 18)
                .addComponent(txtTotalHr, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtAbsent, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtLeave, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTotalHr, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAbsent, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtLeave, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txtActualDate1.setEditable(false);
        txtActualDate1.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtActualDate1.setMinimumSize(new java.awt.Dimension(6, 25));
        txtActualDate1.setName("txtActualDate1"); // NOI18N
        txtActualDate1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtActualDate1FocusLost(evt);
            }
        });
        txtActualDate1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtActualDate1KeyPressed(evt);
            }
        });

        txtActualDate2.setEditable(false);
        txtActualDate2.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtActualDate2.setMinimumSize(new java.awt.Dimension(6, 25));
        txtActualDate2.setName("txtActualDate"); // NOI18N
        txtActualDate2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtActualDate2FocusLost(evt);
            }
        });
        txtActualDate2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtActualDate2KeyPressed(evt);
            }
        });

        txtActualDate3.setEditable(false);
        txtActualDate3.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtActualDate3.setMinimumSize(new java.awt.Dimension(6, 25));
        txtActualDate3.setName("txtActualDate"); // NOI18N
        txtActualDate3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtActualDate3ActionPerformed(evt);
            }
        });
        txtActualDate3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtActualDate3FocusLost(evt);
            }
        });
        txtActualDate3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtActualDate3KeyPressed(evt);
            }
        });

        txtActualDate4.setEditable(false);
        txtActualDate4.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtActualDate4.setMinimumSize(new java.awt.Dimension(6, 25));
        txtActualDate4.setName("txtActualDate"); // NOI18N
        txtActualDate4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtActualDate4FocusLost(evt);
            }
        });
        txtActualDate4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtActualDate4KeyPressed(evt);
            }
        });

        txtActualDate5.setEditable(false);
        txtActualDate5.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtActualDate5.setMinimumSize(new java.awt.Dimension(6, 25));
        txtActualDate5.setName("txtActualDate"); // NOI18N
        txtActualDate5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtActualDate5FocusLost(evt);
            }
        });
        txtActualDate5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtActualDate5KeyPressed(evt);
            }
        });

        txtActualDate6.setEditable(false);
        txtActualDate6.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtActualDate6.setMinimumSize(new java.awt.Dimension(6, 25));
        txtActualDate6.setName("txtActualDate"); // NOI18N
        txtActualDate6.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtActualDate6FocusLost(evt);
            }
        });
        txtActualDate6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtActualDate6KeyPressed(evt);
            }
        });

        txtActualDate7.setEditable(false);
        txtActualDate7.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtActualDate7.setMinimumSize(new java.awt.Dimension(6, 25));
        txtActualDate7.setName("txtActualDate"); // NOI18N
        txtActualDate7.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtActualDate7FocusLost(evt);
            }
        });
        txtActualDate7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtActualDate7KeyPressed(evt);
            }
        });

        txtActualDate8.setEditable(false);
        txtActualDate8.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtActualDate8.setMinimumSize(new java.awt.Dimension(6, 25));
        txtActualDate8.setName("txtActualDate"); // NOI18N
        txtActualDate8.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtActualDate8FocusLost(evt);
            }
        });
        txtActualDate8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtActualDate8KeyPressed(evt);
            }
        });

        txtActualDate9.setEditable(false);
        txtActualDate9.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtActualDate9.setMinimumSize(new java.awt.Dimension(6, 25));
        txtActualDate9.setName("txtActualDate"); // NOI18N
        txtActualDate9.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtActualDate9FocusLost(evt);
            }
        });
        txtActualDate9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtActualDate9KeyPressed(evt);
            }
        });

        txtActualDate10.setEditable(false);
        txtActualDate10.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtActualDate10.setMinimumSize(new java.awt.Dimension(6, 25));
        txtActualDate10.setName("txtActualDate"); // NOI18N
        txtActualDate10.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtActualDate10FocusLost(evt);
            }
        });
        txtActualDate10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtActualDate10KeyPressed(evt);
            }
        });

        txtActualDate11.setEditable(false);
        txtActualDate11.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtActualDate11.setMinimumSize(new java.awt.Dimension(6, 25));
        txtActualDate11.setName("txtActualDate"); // NOI18N
        txtActualDate11.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtActualDate11FocusLost(evt);
            }
        });
        txtActualDate11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtActualDate11KeyPressed(evt);
            }
        });

        txtActualDate12.setEditable(false);
        txtActualDate12.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtActualDate12.setMinimumSize(new java.awt.Dimension(6, 25));
        txtActualDate12.setName("txtActualDate"); // NOI18N
        txtActualDate12.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtActualDate12FocusLost(evt);
            }
        });
        txtActualDate12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtActualDate12KeyPressed(evt);
            }
        });

        txtActualDate13.setEditable(false);
        txtActualDate13.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtActualDate13.setMinimumSize(new java.awt.Dimension(6, 25));
        txtActualDate13.setName("txtActualDate"); // NOI18N
        txtActualDate13.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtActualDate13FocusLost(evt);
            }
        });
        txtActualDate13.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtActualDate13KeyPressed(evt);
            }
        });

        txtActualDate14.setEditable(false);
        txtActualDate14.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtActualDate14.setMinimumSize(new java.awt.Dimension(6, 25));
        txtActualDate14.setName("txtActualDate"); // NOI18N
        txtActualDate14.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtActualDate14FocusLost(evt);
            }
        });
        txtActualDate14.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtActualDate14KeyPressed(evt);
            }
        });

        txtActualDate15.setEditable(false);
        txtActualDate15.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtActualDate15.setMinimumSize(new java.awt.Dimension(6, 25));
        txtActualDate15.setName("txtActualDate"); // NOI18N
        txtActualDate15.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtActualDate15FocusLost(evt);
            }
        });
        txtActualDate15.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtActualDate15KeyPressed(evt);
            }
        });

        txtActualDate16.setEditable(false);
        txtActualDate16.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtActualDate16.setMinimumSize(new java.awt.Dimension(6, 25));
        txtActualDate16.setName("txtActualDate"); // NOI18N
        txtActualDate16.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtActualDate16FocusLost(evt);
            }
        });
        txtActualDate16.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtActualDate16KeyPressed(evt);
            }
        });

        txtActualDate17.setEditable(false);
        txtActualDate17.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtActualDate17.setMinimumSize(new java.awt.Dimension(6, 25));
        txtActualDate17.setName("txtActualDate"); // NOI18N
        txtActualDate17.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtActualDate17FocusLost(evt);
            }
        });
        txtActualDate17.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtActualDate17KeyPressed(evt);
            }
        });

        txtActualDate18.setEditable(false);
        txtActualDate18.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtActualDate18.setMinimumSize(new java.awt.Dimension(6, 25));
        txtActualDate18.setName("txtActualDate"); // NOI18N
        txtActualDate18.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtActualDate18FocusLost(evt);
            }
        });
        txtActualDate18.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtActualDate18KeyPressed(evt);
            }
        });

        txtActualDate19.setEditable(false);
        txtActualDate19.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtActualDate19.setMinimumSize(new java.awt.Dimension(6, 25));
        txtActualDate19.setName("txtActualDate"); // NOI18N
        txtActualDate19.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtActualDate19FocusLost(evt);
            }
        });
        txtActualDate19.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtActualDate19KeyPressed(evt);
            }
        });

        txtActualDate22.setEditable(false);
        txtActualDate22.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtActualDate22.setMinimumSize(new java.awt.Dimension(6, 25));
        txtActualDate22.setName("txtActualDate"); // NOI18N
        txtActualDate22.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtActualDate22FocusLost(evt);
            }
        });
        txtActualDate22.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtActualDate22KeyPressed(evt);
            }
        });

        txtActualDate23.setEditable(false);
        txtActualDate23.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtActualDate23.setMinimumSize(new java.awt.Dimension(6, 25));
        txtActualDate23.setName("txtActualDate"); // NOI18N
        txtActualDate23.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtActualDate23FocusLost(evt);
            }
        });
        txtActualDate23.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtActualDate23KeyPressed(evt);
            }
        });

        txtActualDate24.setEditable(false);
        txtActualDate24.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtActualDate24.setMinimumSize(new java.awt.Dimension(6, 25));
        txtActualDate24.setName("txtActualDate"); // NOI18N
        txtActualDate24.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtActualDate24FocusLost(evt);
            }
        });
        txtActualDate24.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtActualDate24KeyPressed(evt);
            }
        });

        txtActualDate25.setEditable(false);
        txtActualDate25.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtActualDate25.setMinimumSize(new java.awt.Dimension(6, 25));
        txtActualDate25.setName("txtActualDate"); // NOI18N
        txtActualDate25.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtActualDate25FocusLost(evt);
            }
        });
        txtActualDate25.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtActualDate25KeyPressed(evt);
            }
        });

        txtActualDate26.setEditable(false);
        txtActualDate26.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtActualDate26.setMinimumSize(new java.awt.Dimension(6, 25));
        txtActualDate26.setName("txtActualDate"); // NOI18N
        txtActualDate26.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtActualDate26FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtActualDate26FocusLost(evt);
            }
        });
        txtActualDate26.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtActualDate26KeyPressed(evt);
            }
        });

        txtActualDate27.setEditable(false);
        txtActualDate27.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtActualDate27.setMinimumSize(new java.awt.Dimension(6, 25));
        txtActualDate27.setName("txtActualDate"); // NOI18N
        txtActualDate27.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtActualDate27FocusLost(evt);
            }
        });
        txtActualDate27.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtActualDate27KeyPressed(evt);
            }
        });

        txtActualDate28.setEditable(false);
        txtActualDate28.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtActualDate28.setMinimumSize(new java.awt.Dimension(6, 25));
        txtActualDate28.setName("txtActualDate"); // NOI18N
        txtActualDate28.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtActualDate28FocusLost(evt);
            }
        });
        txtActualDate28.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtActualDate28KeyPressed(evt);
            }
        });

        txtActualDate29.setEditable(false);
        txtActualDate29.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtActualDate29.setMinimumSize(new java.awt.Dimension(6, 25));
        txtActualDate29.setName("txtActualDate"); // NOI18N
        txtActualDate29.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtActualDate29FocusLost(evt);
            }
        });
        txtActualDate29.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtActualDate29KeyPressed(evt);
            }
        });

        txtActualDate30.setEditable(false);
        txtActualDate30.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtActualDate30.setMinimumSize(new java.awt.Dimension(6, 25));
        txtActualDate30.setName("txtActualDate"); // NOI18N
        txtActualDate30.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtActualDate30FocusLost(evt);
            }
        });
        txtActualDate30.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtActualDate30KeyPressed(evt);
            }
        });

        txtActualDate31.setEditable(false);
        txtActualDate31.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtActualDate31.setMinimumSize(new java.awt.Dimension(6, 25));
        txtActualDate31.setName("txtActualDate"); // NOI18N
        txtActualDate31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtActualDate31ActionPerformed(evt);
            }
        });
        txtActualDate31.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtActualDate31FocusLost(evt);
            }
        });
        txtActualDate31.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtActualDate31KeyPressed(evt);
            }
        });

        txtActualDate20.setEditable(false);
        txtActualDate20.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtActualDate20.setMinimumSize(new java.awt.Dimension(6, 25));
        txtActualDate20.setName("txtActualDate"); // NOI18N
        txtActualDate20.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtActualDate20FocusLost(evt);
            }
        });
        txtActualDate20.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtActualDate20KeyPressed(evt);
            }
        });

        txtActualDate21.setEditable(false);
        txtActualDate21.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtActualDate21.setMinimumSize(new java.awt.Dimension(6, 25));
        txtActualDate21.setName("txtActualDate"); // NOI18N
        txtActualDate21.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtActualDate21FocusLost(evt);
            }
        });
        txtActualDate21.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtActualDate21KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel33)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtDate1, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtDate2, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtDate3, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtDate4, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addComponent(jDatecurrentMonth, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtDate5, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtDate6, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtDate7, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtDate8, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtDate9, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtDate10, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtDate11, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtDate12, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtDate13, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtDate14, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtDate15, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtDate16, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtDate17, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtDate18, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtDate19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtDate20, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                            .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtDate21, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                            .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtDate22, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                            .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtDate23, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                            .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(11, 11, 11)
                                        .addComponent(cmbEmployeeName, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(105, 105, 105)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtDate24, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                    .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtDate25, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                    .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtDate26, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                    .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtDate27, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                    .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtDate28, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                    .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtDate29, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                    .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtDate30, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                    .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtDate31, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel32, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 131, Short.MAX_VALUE)
                                .addComponent(txtActualDate1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtActualDate2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtActualDate3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtActualDate4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtActualDate5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtActualDate6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtActualDate7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtActualDate8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtActualDate9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtActualDate10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtActualDate11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtActualDate12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtActualDate13, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtActualDate14, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtActualDate15, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtActualDate16, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtActualDate17, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtActualDate18, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtActualDate19, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtActualDate20, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtActualDate21, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtActualDate22, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtActualDate23, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtActualDate24, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtActualDate25, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtActualDate26, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtActualDate27, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtActualDate28, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtActualDate29, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtActualDate30, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtActualDate31, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jDatecurrentMonth, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbEmployeeName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(3, 3, 3)))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19)
                    .addComponent(jLabel20)
                    .addComponent(jLabel21)
                    .addComponent(jLabel22)
                    .addComponent(jLabel23)
                    .addComponent(jLabel24)
                    .addComponent(jLabel25)
                    .addComponent(jLabel26)
                    .addComponent(jLabel27)
                    .addComponent(jLabel28)
                    .addComponent(jLabel29)
                    .addComponent(jLabel30)
                    .addComponent(jLabel31)
                    .addComponent(jLabel32))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDate1, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                    .addComponent(txtDate2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDate3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDate4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDate5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDate6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDate7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDate8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDate9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDate10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDate11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDate12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDate13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDate14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDate15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDate16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDate17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDate18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDate19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDate20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDate21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDate23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDate24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDate26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDate27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDate28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDate25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDate22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDate29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDate30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDate31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtActualDate1, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                    .addComponent(txtActualDate2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtActualDate3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtActualDate4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtActualDate5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtActualDate6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtActualDate7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtActualDate8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtActualDate9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtActualDate10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtActualDate11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtActualDate12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtActualDate13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtActualDate14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtActualDate15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtActualDate16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtActualDate17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtActualDate18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtActualDate19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtActualDate23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtActualDate24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtActualDate26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtActualDate27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtActualDate28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtActualDate25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtActualDate22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtActualDate29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtActualDate30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtActualDate31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtActualDate20, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                    .addComponent(txtActualDate21, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 609, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtDate1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate1FocusLost
        // TODO add your handling code here:
        fillTextFields(txtDate1, txtActualDate1);
    }//GEN-LAST:event_txtDate1FocusLost

    private void txtDate1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate1KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate1, evt);
    }//GEN-LAST:event_txtDate1KeyPressed

    private void txtDate2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate2FocusLost
        // TODO add your handling code here:
        fillTextFields(txtDate2, txtActualDate2);
    }//GEN-LAST:event_txtDate2FocusLost

    private void txtDate2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate2KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate2, evt);
    }//GEN-LAST:event_txtDate2KeyPressed

    private void txtDate3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDate3ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtDate3ActionPerformed

    private void txtDate3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate3FocusLost
        // TODO add your handling code here:
        fillTextFields(txtDate3, txtActualDate3);
    }//GEN-LAST:event_txtDate3FocusLost

    private void txtDate3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate3KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate3, evt);
    }//GEN-LAST:event_txtDate3KeyPressed

    private void txtDate4FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate4FocusLost
        // TODO add your handling code here:
        fillTextFields(txtDate4, txtActualDate4);
    }//GEN-LAST:event_txtDate4FocusLost

    private void txtDate4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate4KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate4, evt);
    }//GEN-LAST:event_txtDate4KeyPressed

    private void txtDate5FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate5FocusLost
        // TODO add your handling code here:
        fillTextFields(txtDate5, txtActualDate5);
    }//GEN-LAST:event_txtDate5FocusLost

    private void txtDate5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate5KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate5, evt);
    }//GEN-LAST:event_txtDate5KeyPressed

    private void txtDate6FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate6FocusLost
        // TODO add your handling code here:
        fillTextFields(txtDate6, txtActualDate6);
    }//GEN-LAST:event_txtDate6FocusLost

    private void txtDate6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate6KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate6, evt);
    }//GEN-LAST:event_txtDate6KeyPressed

    private void txtDate7FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate7FocusLost
        // TODO add your handling code here:
        fillTextFields(txtDate7, txtActualDate7);
    }//GEN-LAST:event_txtDate7FocusLost

    private void txtDate7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate7KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate7, evt);
    }//GEN-LAST:event_txtDate7KeyPressed

    private void txtDate8FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate8FocusLost
        // TODO add your handling code here:
        fillTextFields(txtDate8, txtActualDate8);
    }//GEN-LAST:event_txtDate8FocusLost

    private void txtDate8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate8KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate8, evt);
    }//GEN-LAST:event_txtDate8KeyPressed

    private void txtDate9FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate9FocusLost
        // TODO add your handling code here:
        fillTextFields(txtDate9, txtActualDate9);
    }//GEN-LAST:event_txtDate9FocusLost

    private void txtDate9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate9KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate9, evt);
    }//GEN-LAST:event_txtDate9KeyPressed

    private void txtDate10FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate10FocusLost
        // TODO add your handling code here:
        fillTextFields(txtDate10, txtActualDate10);
    }//GEN-LAST:event_txtDate10FocusLost

    private void txtDate10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate10KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate10, evt);
    }//GEN-LAST:event_txtDate10KeyPressed

    private void txtDate11FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate11FocusLost
        // TODO add your handling code here:
        fillTextFields(txtDate11, txtActualDate11);
    }//GEN-LAST:event_txtDate11FocusLost

    private void txtDate11KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate11KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate11, evt);
    }//GEN-LAST:event_txtDate11KeyPressed

    private void txtDate12FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate12FocusLost
        // TODO add your handling code here:
        fillTextFields(txtDate12, txtActualDate12);
    }//GEN-LAST:event_txtDate12FocusLost

    private void txtDate12KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate12KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate12, evt);
    }//GEN-LAST:event_txtDate12KeyPressed

    private void txtDate13FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate13FocusLost
        // TODO add your handling code here:
        fillTextFields(txtDate13, txtActualDate13);
    }//GEN-LAST:event_txtDate13FocusLost

    private void txtDate13KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate13KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate13, evt);
    }//GEN-LAST:event_txtDate13KeyPressed

    private void txtDate14FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate14FocusLost
        // TODO add your handling code here:
        fillTextFields(txtDate14, txtActualDate14);
    }//GEN-LAST:event_txtDate14FocusLost

    private void txtDate14KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate14KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate14, evt);
    }//GEN-LAST:event_txtDate14KeyPressed

    private void txtDate15FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate15FocusLost
        // TODO add your handling code here:
        fillTextFields(txtDate15, txtActualDate15);
    }//GEN-LAST:event_txtDate15FocusLost

    private void txtDate15KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate15KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate15, evt);
    }//GEN-LAST:event_txtDate15KeyPressed

    private void txtDate16FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate16FocusLost
        // TODO add your handling code here:
        fillTextFields(txtDate16, txtActualDate16);
    }//GEN-LAST:event_txtDate16FocusLost

    private void txtDate16KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate16KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate16, evt);
    }//GEN-LAST:event_txtDate16KeyPressed

    private void txtDate17FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate17FocusLost
        // TODO add your handling code here:
        fillTextFields(txtDate17, txtActualDate17);
    }//GEN-LAST:event_txtDate17FocusLost

    private void txtDate17KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate17KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate17, evt);
    }//GEN-LAST:event_txtDate17KeyPressed

    private void txtDate18FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate18FocusLost
        // TODO add your handling code here:
        fillTextFields(txtDate18, txtActualDate18);
    }//GEN-LAST:event_txtDate18FocusLost

    private void txtDate18KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate18KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate18, evt);
    }//GEN-LAST:event_txtDate18KeyPressed

    private void txtDate19FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate19FocusLost
        // TODO add your handling code here:
        fillTextFields(txtDate19, txtActualDate19);
    }//GEN-LAST:event_txtDate19FocusLost

    private void txtDate19KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate19KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate19, evt);
    }//GEN-LAST:event_txtDate19KeyPressed

    private void txtDate20FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate20FocusLost
        // TODO add your handling code here:
        fillTextFields(txtDate20, txtActualDate20);
    }//GEN-LAST:event_txtDate20FocusLost

    private void txtDate20KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate20KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate20, evt);
    }//GEN-LAST:event_txtDate20KeyPressed

    private void txtDate21FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate21FocusLost
        // TODO add your handling code here:
         fillTextFields(txtDate21, txtActualDate21);
    }//GEN-LAST:event_txtDate21FocusLost

    private void txtDate21KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate21KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate21, evt);
    }//GEN-LAST:event_txtDate21KeyPressed

    private void txtDate22FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate22FocusLost
        // TODO add your handling code here:
         fillTextFields(txtDate22, txtActualDate22);
    }//GEN-LAST:event_txtDate22FocusLost

    private void txtDate22KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate22KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate22, evt);
    }//GEN-LAST:event_txtDate22KeyPressed

    private void txtDate23FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate23FocusLost
        // TODO add your handling code here:
         fillTextFields(txtDate23, txtActualDate23);
    }//GEN-LAST:event_txtDate23FocusLost

    private void txtDate23KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate23KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate23, evt);
    }//GEN-LAST:event_txtDate23KeyPressed

    private void txtDate24FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate24FocusLost
        // TODO add your handling code here:
         fillTextFields(txtDate24, txtActualDate24);
    }//GEN-LAST:event_txtDate24FocusLost

    private void txtDate24KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate24KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate24, evt);
    }//GEN-LAST:event_txtDate24KeyPressed

    private void txtDate25FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate25FocusLost
        // TODO add your handling code here:
         fillTextFields(txtDate25, txtActualDate25);
    }//GEN-LAST:event_txtDate25FocusLost

    private void txtDate25KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate25KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate25, evt);
    }//GEN-LAST:event_txtDate25KeyPressed

    private void txtDate26FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate26FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDate26FocusGained

    private void txtDate26FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate26FocusLost
        // TODO add your handling code here:
         fillTextFields(txtDate26, txtActualDate26);
    }//GEN-LAST:event_txtDate26FocusLost

    private void txtDate26KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate26KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate26, evt);
    }//GEN-LAST:event_txtDate26KeyPressed

    private void txtDate27FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate27FocusLost
        // TODO add your handling code here:
         fillTextFields(txtDate27, txtActualDate27);
    }//GEN-LAST:event_txtDate27FocusLost

    private void txtDate27KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate27KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate27, evt);
    }//GEN-LAST:event_txtDate27KeyPressed

    private void txtDate28FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate28FocusLost
        // TODO add your handling code here:
         fillTextFields(txtDate28, txtActualDate28);
    }//GEN-LAST:event_txtDate28FocusLost

    private void txtDate28KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate28KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate28, evt);
    }//GEN-LAST:event_txtDate28KeyPressed

    private void txtDate29FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate29FocusLost
        // TODO add your handling code here:
         fillTextFields(txtDate29, txtActualDate29);
    }//GEN-LAST:event_txtDate29FocusLost

    private void txtDate29KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate29KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate29, evt);
    }//GEN-LAST:event_txtDate29KeyPressed

    private void txtDate30FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate30FocusLost
        // TODO add your handling code here:
         fillTextFields(txtDate30, txtActualDate30);
    }//GEN-LAST:event_txtDate30FocusLost

    private void txtDate30KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate30KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate30, evt);
    }//GEN-LAST:event_txtDate30KeyPressed

    private void txtDate31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDate31ActionPerformed
        // TODO add your handling code here:
         
    }//GEN-LAST:event_txtDate31ActionPerformed

    private void txtDate31FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate31FocusLost
        // TODO add your handling code here:
        fillTextFields(txtDate31, txtActualDate31);
    }//GEN-LAST:event_txtDate31FocusLost

    private void txtDate31KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate31KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate31, evt);
    }//GEN-LAST:event_txtDate31KeyPressed

    private void cmbEmployeeNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEmployeeNameActionPerformed
        // TODO add your handling code here:
       //viewEmployeeDetailsUsingName();
        empIdUsingEmpName();
    }//GEN-LAST:event_cmbEmployeeNameActionPerformed

    private void txtTotalHrFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTotalHrFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalHrFocusLost

    private void txtTotalHrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTotalHrKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalHrKeyPressed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if(cmbEmployeeName.getSelectedItem().equals("--select name--") || currentMonth.equals(""))
        {
            JOptionPane.showMessageDialog(rootPane, "Please select the Month and Employee Name");
        }
        else
        {
            insertIntoDb();
        }
        
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        updateDb();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        deleteAction();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // TODO add your handling code here:
        dispose();
        viewConTimesheet();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void txtAbsentFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAbsentFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAbsentFocusLost

    private void txtAbsentKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAbsentKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAbsentKeyPressed

    private void txtLeaveFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLeaveFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLeaveFocusLost

    private void txtLeaveKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLeaveKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLeaveKeyPressed

    private void txtActualDate1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtActualDate1FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate1FocusLost

    private void txtActualDate1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtActualDate1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate1KeyPressed

    private void txtActualDate2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtActualDate2FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate2FocusLost

    private void txtActualDate2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtActualDate2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate2KeyPressed

    private void txtActualDate3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtActualDate3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate3ActionPerformed

    private void txtActualDate3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtActualDate3FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate3FocusLost

    private void txtActualDate3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtActualDate3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate3KeyPressed

    private void txtActualDate4FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtActualDate4FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate4FocusLost

    private void txtActualDate4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtActualDate4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate4KeyPressed

    private void txtActualDate5FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtActualDate5FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate5FocusLost

    private void txtActualDate5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtActualDate5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate5KeyPressed

    private void txtActualDate6FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtActualDate6FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate6FocusLost

    private void txtActualDate6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtActualDate6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate6KeyPressed

    private void txtActualDate7FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtActualDate7FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate7FocusLost

    private void txtActualDate7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtActualDate7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate7KeyPressed

    private void txtActualDate8FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtActualDate8FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate8FocusLost

    private void txtActualDate8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtActualDate8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate8KeyPressed

    private void txtActualDate9FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtActualDate9FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate9FocusLost

    private void txtActualDate9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtActualDate9KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate9KeyPressed

    private void txtActualDate10FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtActualDate10FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate10FocusLost

    private void txtActualDate10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtActualDate10KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate10KeyPressed

    private void txtActualDate11FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtActualDate11FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate11FocusLost

    private void txtActualDate11KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtActualDate11KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate11KeyPressed

    private void txtActualDate12FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtActualDate12FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate12FocusLost

    private void txtActualDate12KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtActualDate12KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate12KeyPressed

    private void txtActualDate13FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtActualDate13FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate13FocusLost

    private void txtActualDate13KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtActualDate13KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate13KeyPressed

    private void txtActualDate14FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtActualDate14FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate14FocusLost

    private void txtActualDate14KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtActualDate14KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate14KeyPressed

    private void txtActualDate15FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtActualDate15FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate15FocusLost

    private void txtActualDate15KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtActualDate15KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate15KeyPressed

    private void txtActualDate16FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtActualDate16FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate16FocusLost

    private void txtActualDate16KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtActualDate16KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate16KeyPressed

    private void txtActualDate17FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtActualDate17FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate17FocusLost

    private void txtActualDate17KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtActualDate17KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate17KeyPressed

    private void txtActualDate18FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtActualDate18FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate18FocusLost

    private void txtActualDate18KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtActualDate18KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate18KeyPressed

    private void txtActualDate19FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtActualDate19FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate19FocusLost

    private void txtActualDate19KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtActualDate19KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate19KeyPressed

    private void txtActualDate20FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtActualDate20FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate20FocusLost

    private void txtActualDate20KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtActualDate20KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate20KeyPressed

    private void txtActualDate22FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtActualDate22FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate22FocusLost

    private void txtActualDate22KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtActualDate22KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate22KeyPressed

    private void txtActualDate23FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtActualDate23FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate23FocusLost

    private void txtActualDate23KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtActualDate23KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate23KeyPressed

    private void txtActualDate24FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtActualDate24FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate24FocusLost

    private void txtActualDate24KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtActualDate24KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate24KeyPressed

    private void txtActualDate25FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtActualDate25FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate25FocusLost

    private void txtActualDate25KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtActualDate25KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate25KeyPressed

    private void txtActualDate26FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtActualDate26FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate26FocusGained

    private void txtActualDate26FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtActualDate26FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate26FocusLost

    private void txtActualDate26KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtActualDate26KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate26KeyPressed

    private void txtActualDate27FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtActualDate27FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate27FocusLost

    private void txtActualDate27KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtActualDate27KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate27KeyPressed

    private void txtActualDate28FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtActualDate28FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate28FocusLost

    private void txtActualDate28KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtActualDate28KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate28KeyPressed

    private void txtActualDate29FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtActualDate29FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate29FocusLost

    private void txtActualDate29KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtActualDate29KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate29KeyPressed

    private void txtActualDate30FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtActualDate30FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate30FocusLost

    private void txtActualDate30KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtActualDate30KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate30KeyPressed

    private void txtActualDate31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtActualDate31ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate31ActionPerformed

    private void txtActualDate31FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtActualDate31FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate31FocusLost

    private void txtActualDate31KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtActualDate31KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate31KeyPressed

    private void txtActualDate21FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtActualDate21FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate21FocusLost

    private void txtActualDate21KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtActualDate21KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualDate21KeyPressed

    private void cmbEmployeeNameItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbEmployeeNameItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbEmployeeNameItemStateChanged

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
            java.util.logging.Logger.getLogger(ConEmployeeTimeSheet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ConEmployeeTimeSheet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ConEmployeeTimeSheet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ConEmployeeTimeSheet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ConEmployeeTimeSheet dialog = new ConEmployeeTimeSheet(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox cmbEmployeeName;
    private com.toedter.calendar.JDateChooser jDatecurrentMonth;
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextField txtAbsent;
    private javax.swing.JTextField txtActualDate1;
    private javax.swing.JTextField txtActualDate10;
    private javax.swing.JTextField txtActualDate11;
    private javax.swing.JTextField txtActualDate12;
    private javax.swing.JTextField txtActualDate13;
    private javax.swing.JTextField txtActualDate14;
    private javax.swing.JTextField txtActualDate15;
    private javax.swing.JTextField txtActualDate16;
    private javax.swing.JTextField txtActualDate17;
    private javax.swing.JTextField txtActualDate18;
    private javax.swing.JTextField txtActualDate19;
    private javax.swing.JTextField txtActualDate2;
    private javax.swing.JTextField txtActualDate20;
    private javax.swing.JTextField txtActualDate21;
    private javax.swing.JTextField txtActualDate22;
    private javax.swing.JTextField txtActualDate23;
    private javax.swing.JTextField txtActualDate24;
    private javax.swing.JTextField txtActualDate25;
    private javax.swing.JTextField txtActualDate26;
    private javax.swing.JTextField txtActualDate27;
    private javax.swing.JTextField txtActualDate28;
    private javax.swing.JTextField txtActualDate29;
    private javax.swing.JTextField txtActualDate3;
    private javax.swing.JTextField txtActualDate30;
    private javax.swing.JTextField txtActualDate31;
    private javax.swing.JTextField txtActualDate4;
    private javax.swing.JTextField txtActualDate5;
    private javax.swing.JTextField txtActualDate6;
    private javax.swing.JTextField txtActualDate7;
    private javax.swing.JTextField txtActualDate8;
    private javax.swing.JTextField txtActualDate9;
    private javax.swing.JTextField txtDate1;
    private javax.swing.JTextField txtDate10;
    private javax.swing.JTextField txtDate11;
    private javax.swing.JTextField txtDate12;
    private javax.swing.JTextField txtDate13;
    private javax.swing.JTextField txtDate14;
    private javax.swing.JTextField txtDate15;
    private javax.swing.JTextField txtDate16;
    private javax.swing.JTextField txtDate17;
    private javax.swing.JTextField txtDate18;
    private javax.swing.JTextField txtDate19;
    private javax.swing.JTextField txtDate2;
    private javax.swing.JTextField txtDate20;
    private javax.swing.JTextField txtDate21;
    private javax.swing.JTextField txtDate22;
    private javax.swing.JTextField txtDate23;
    private javax.swing.JTextField txtDate24;
    private javax.swing.JTextField txtDate25;
    private javax.swing.JTextField txtDate26;
    private javax.swing.JTextField txtDate27;
    private javax.swing.JTextField txtDate28;
    private javax.swing.JTextField txtDate29;
    private javax.swing.JTextField txtDate3;
    private javax.swing.JTextField txtDate30;
    private javax.swing.JTextField txtDate31;
    private javax.swing.JTextField txtDate4;
    private javax.swing.JTextField txtDate5;
    private javax.swing.JTextField txtDate6;
    private javax.swing.JTextField txtDate7;
    private javax.swing.JTextField txtDate8;
    private javax.swing.JTextField txtDate9;
    private javax.swing.JTextField txtLeave;
    private javax.swing.JTextField txtTotalHr;
    // End of variables declaration//GEN-END:variables
   Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
   Point middle = new Point(0,0);
   public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
   DateFormat defaultDate = new SimpleDateFormat("MMM yy");
   String dateString = "",currentMonth="";
   public int empId,timeSheetId;   
   double totalHr=0.0;
}   


