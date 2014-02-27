package org.vijay.employee;


import org.vijay.common.connection;
import org.vijay.common.AnarTrading;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
public final class TimeSheet extends javax.swing.JInternalFrame {

    /**
     * Creates new form TimeSheet
     */
    public TimeSheet() {
        initComponents();
        SetFriday();
        setLocation(middle);
    }
    
    public TimeSheet(int empId) {
        this.empId=empId;
        initComponents();
        SetFriday();
        findHoliday();
        viewEmpDetails();
        viewDbEmployeeDetails();
        advancedDeduction();
        setLocation(middle);
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
        jtableTimeSheet.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
            cmbMonth.setEnabled(false);
            cmbYear.setEnabled(false);
            btnSave.setEnabled(false);
            btnUpdate.setEnabled(true);
            btnDelete.setEnabled(true);
            txtFoodAddition.setText("0.0");
            txtDues.setText("0.0");
            txtBonus.setText("0.0");
            txtOtherAddition.setText("0.0");
        
            txtAbsentDeduction.setText("0.0");
            txtAdvanceDeduction.setText("0.0");
            txtFoodDeduction.setText("0.0");
            txtOtherDeduction.setText("0.0");
            
            txtTotalAddition.setText("0.0");
            txtTotalDeduction.setText("0.0");
            txtNetAmount.setText("0.0");
            
            int rowNo=jtableTimeSheet.getSelectedRow();
            timeSheetId=Integer.parseInt(jtableTimeSheet.getValueAt(rowNo,0).toString());
            cmbMonth.setSelectedItem(jtableTimeSheet.getValueAt(rowNo,2).toString());
            cmbYear.setSelectedItem(jtableTimeSheet.getValueAt(rowNo,3).toString());
            txtDate1.setText(jtableTimeSheet.getValueAt(rowNo,4).toString());
            txtDate2.setText(jtableTimeSheet.getValueAt(rowNo,5).toString());
            txtDate3.setText(jtableTimeSheet.getValueAt(rowNo,6).toString());
            txtDate4.setText(jtableTimeSheet.getValueAt(rowNo,7).toString());
            txtDate5.setText(jtableTimeSheet.getValueAt(rowNo,8).toString());
            txtDate6.setText(jtableTimeSheet.getValueAt(rowNo,9).toString());
            txtDate7.setText(jtableTimeSheet.getValueAt(rowNo,10).toString());
            txtDate8.setText(jtableTimeSheet.getValueAt(rowNo,11).toString());
            txtDate9.setText(jtableTimeSheet.getValueAt(rowNo,12).toString());
            txtDate10.setText(jtableTimeSheet.getValueAt(rowNo,13).toString());
            txtDate11.setText(jtableTimeSheet.getValueAt(rowNo,14).toString());
            txtDate12.setText(jtableTimeSheet.getValueAt(rowNo,15).toString());
            txtDate13.setText(jtableTimeSheet.getValueAt(rowNo,16).toString());
            txtDate14.setText(jtableTimeSheet.getValueAt(rowNo,17).toString());
            txtDate15.setText(jtableTimeSheet.getValueAt(rowNo,18).toString());
            txtDate16.setText(jtableTimeSheet.getValueAt(rowNo,19).toString());
            txtDate17.setText(jtableTimeSheet.getValueAt(rowNo,20).toString());
            txtDate18.setText(jtableTimeSheet.getValueAt(rowNo,21).toString());
            txtDate19.setText(jtableTimeSheet.getValueAt(rowNo,22).toString());
            txtDate20.setText(jtableTimeSheet.getValueAt(rowNo,23).toString());
            txtDate21.setText(jtableTimeSheet.getValueAt(rowNo,24).toString());
            txtDate22.setText(jtableTimeSheet.getValueAt(rowNo,25).toString());
            txtDate23.setText(jtableTimeSheet.getValueAt(rowNo,26).toString());
            txtDate24.setText(jtableTimeSheet.getValueAt(rowNo,27).toString());
            txtDate25.setText(jtableTimeSheet.getValueAt(rowNo,28).toString());
            txtDate26.setText(jtableTimeSheet.getValueAt(rowNo,29).toString());
            txtDate27.setText(jtableTimeSheet.getValueAt(rowNo,30).toString());
            txtDate28.setText(jtableTimeSheet.getValueAt(rowNo,31).toString());
            txtDate29.setText(jtableTimeSheet.getValueAt(rowNo,32).toString());
            txtDate30.setText(jtableTimeSheet.getValueAt(rowNo,33).toString());
            txtDate31.setText(jtableTimeSheet.getValueAt(rowNo,34).toString());
            txtFoodAddition.setText(jtableTimeSheet.getValueAt(rowNo,35).toString());
            txtDues.setText(jtableTimeSheet.getValueAt(rowNo,36).toString());
            txtOtherAddition.setText(jtableTimeSheet.getValueAt(rowNo,37).toString());
            txtAdvanceDeduction.setText(jtableTimeSheet.getValueAt(rowNo,38).toString());
            txtFoodDeduction.setText(jtableTimeSheet.getValueAt(rowNo,39).toString());
            txtOtherDeduction.setText(jtableTimeSheet.getValueAt(rowNo,40).toString());
            fillEntireTextBox();
            }
        });
        
        
    }
    public void findHoliday()
    {
        
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            PreparedStatement ps=con.prepareStatement("SELECT holiday1,holiday2,holiday3,holiday4,holiday5,holiday6,holiday7,holiday8,holiday9,holiday10,holiday11,holiday12 FROM tbl_holiday where hMonth=? and hYear=?");
            ps.setString(1,cmbMonth.getSelectedItem().toString());
            ps.setString(2,cmbYear.getSelectedItem().toString());
            ResultSet rs=ps.executeQuery();
            while(rs.next())
            {
                if(rs.getString(1).equals("1") || rs.getString(2).equals("1")||rs.getString(3).equals("1")||rs.getString(4).equals("1")||rs.getString(5).equals("1")||rs.getString(6).equals("1")||rs.getString(7).equals("1")||rs.getString(8).equals("1")||rs.getString(9).equals("1")||rs.getString(10).equals("1")||rs.getString(11).equals("1")||rs.getString(12).equals("1"))
                {
                    txtDate1.setBackground(Color.ORANGE);
                }
                if(rs.getString(1).equals("2") || rs.getString(2).equals("2")||rs.getString(3).equals("2")||rs.getString(4).equals("2")||rs.getString(5).equals("2")||rs.getString(6).equals("2")||rs.getString(7).equals("2")||rs.getString(8).equals("2")||rs.getString(9).equals("2")||rs.getString(10).equals("2")||rs.getString(11).equals("2")||rs.getString(12).equals("2"))
                {
                    txtDate2.setBackground(Color.ORANGE);
                }
                if(rs.getString(1).equals("3") || rs.getString(2).equals("3")||rs.getString(3).equals("3")||rs.getString(4).equals("3")||rs.getString(5).equals("3")||rs.getString(6).equals("3")||rs.getString(7).equals("3")||rs.getString(8).equals("3")||rs.getString(9).equals("3")||rs.getString(10).equals("3")||rs.getString(11).equals("3")||rs.getString(12).equals("3"))
                {
                    txtDate3.setBackground(Color.ORANGE);
                }
                if(rs.getString(1).equals("4") || rs.getString(2).equals("4")||rs.getString(3).equals("4")||rs.getString(4).equals("4")||rs.getString(5).equals("4")||rs.getString(6).equals("4")||rs.getString(7).equals("4")||rs.getString(8).equals("4")||rs.getString(9).equals("4")||rs.getString(10).equals("4")||rs.getString(11).equals("4")||rs.getString(12).equals("4"))
                {
                    txtDate4.setBackground(Color.ORANGE);
                }
                if(rs.getString(1).equals("5") || rs.getString(2).equals("5")||rs.getString(3).equals("5")||rs.getString(4).equals("5")||rs.getString(5).equals("5")||rs.getString(6).equals("5")||rs.getString(7).equals("5")||rs.getString(8).equals("5")||rs.getString(9).equals("5")||rs.getString(10).equals("5")||rs.getString(11).equals("5")||rs.getString(12).equals("5"))
                {
                    txtDate5.setBackground(Color.ORANGE);
                }
                if(rs.getString(1).equals("6") || rs.getString(2).equals("6")||rs.getString(3).equals("6")||rs.getString(4).equals("6")||rs.getString(5).equals("6")||rs.getString(6).equals("6")||rs.getString(7).equals("6")||rs.getString(8).equals("6")||rs.getString(9).equals("6")||rs.getString(10).equals("6")||rs.getString(11).equals("6")||rs.getString(12).equals("6"))
                {
                    txtDate6.setBackground(Color.ORANGE);
                }
                if(rs.getString(1).equals("7") || rs.getString(2).equals("7")||rs.getString(3).equals("7")||rs.getString(4).equals("7")||rs.getString(5).equals("7")||rs.getString(6).equals("7")||rs.getString(7).equals("7")||rs.getString(8).equals("7")||rs.getString(9).equals("7")||rs.getString(10).equals("7")||rs.getString(11).equals("7")||rs.getString(12).equals("7"))
                {
                    txtDate7.setBackground(Color.ORANGE);
                }
                if(rs.getString(1).equals("8") || rs.getString(2).equals("8")||rs.getString(3).equals("8")||rs.getString(4).equals("8")||rs.getString(5).equals("8")||rs.getString(6).equals("8")||rs.getString(7).equals("8")||rs.getString(8).equals("8")||rs.getString(9).equals("8")||rs.getString(10).equals("8")||rs.getString(11).equals("8")||rs.getString(12).equals("8"))
                {
                    txtDate8.setBackground(Color.ORANGE);
                }
                if(rs.getString(1).equals("9") || rs.getString(2).equals("9")||rs.getString(3).equals("9")||rs.getString(4).equals("9")||rs.getString(5).equals("9")||rs.getString(6).equals("9")||rs.getString(7).equals("9")||rs.getString(8).equals("9")||rs.getString(9).equals("9")||rs.getString(10).equals("9")||rs.getString(11).equals("9")||rs.getString(12).equals("9"))
                {
                    txtDate9.setBackground(Color.ORANGE);
                }
                if(rs.getString(1).equals("10") || rs.getString(2).equals("10")||rs.getString(3).equals("10")||rs.getString(4).equals("10")||rs.getString(5).equals("10")||rs.getString(6).equals("10")||rs.getString(7).equals("10")||rs.getString(8).equals("10")||rs.getString(9).equals("10")||rs.getString(10).equals("10")||rs.getString(11).equals("10")||rs.getString(12).equals("10"))
                {
                    txtDate10.setBackground(Color.ORANGE);
                }
                if(rs.getString(1).equals("11") || rs.getString(2).equals("11")||rs.getString(3).equals("11")||rs.getString(4).equals("11")||rs.getString(5).equals("11")||rs.getString(6).equals("11")||rs.getString(7).equals("11")||rs.getString(8).equals("11")||rs.getString(9).equals("11")||rs.getString(10).equals("11")||rs.getString(11).equals("11")||rs.getString(12).equals("11"))
                {
                    txtDate11.setBackground(Color.ORANGE);
                }
                if(rs.getString(1).equals("12") || rs.getString(2).equals("12")||rs.getString(3).equals("12")||rs.getString(4).equals("12")||rs.getString(5).equals("12")||rs.getString(6).equals("12")||rs.getString(7).equals("12")||rs.getString(8).equals("12")||rs.getString(9).equals("12")||rs.getString(10).equals("12")||rs.getString(11).equals("12")||rs.getString(12).equals("12"))
                {
                    txtDate12.setBackground(Color.ORANGE);
                }
                if(rs.getString(1).equals("13") || rs.getString(2).equals("13")||rs.getString(3).equals("13")||rs.getString(4).equals("13")||rs.getString(5).equals("13")||rs.getString(6).equals("13")||rs.getString(7).equals("13")||rs.getString(8).equals("13")||rs.getString(9).equals("13")||rs.getString(10).equals("13")||rs.getString(11).equals("13")||rs.getString(12).equals("13"))
                {
                    txtDate13.setBackground(Color.ORANGE);
                }
                if(rs.getString(1).equals("14") || rs.getString(2).equals("14")||rs.getString(3).equals("14")||rs.getString(4).equals("14")||rs.getString(5).equals("14")||rs.getString(6).equals("14")||rs.getString(7).equals("14")||rs.getString(8).equals("14")||rs.getString(9).equals("14")||rs.getString(10).equals("14")||rs.getString(11).equals("14")||rs.getString(12).equals("14"))
                {
                    txtDate14.setBackground(Color.ORANGE);
                }
                if(rs.getString(1).equals("15") || rs.getString(2).equals("15")||rs.getString(3).equals("15")||rs.getString(4).equals("15")||rs.getString(5).equals("15")||rs.getString(6).equals("15")||rs.getString(7).equals("15")||rs.getString(8).equals("15")||rs.getString(9).equals("15")||rs.getString(10).equals("15")||rs.getString(11).equals("15")||rs.getString(12).equals("15"))
                {
                    txtDate15.setBackground(Color.ORANGE);
                }
                if(rs.getString(1).equals("16") || rs.getString(2).equals("16")||rs.getString(3).equals("16")||rs.getString(4).equals("16")||rs.getString(5).equals("16")||rs.getString(6).equals("16")||rs.getString(7).equals("16")||rs.getString(8).equals("16")||rs.getString(9).equals("16")||rs.getString(10).equals("16")||rs.getString(11).equals("16")||rs.getString(12).equals("16"))
                {
                    txtDate16.setBackground(Color.ORANGE);
                }
                if(rs.getString(1).equals("17") || rs.getString(2).equals("17")||rs.getString(3).equals("17")||rs.getString(4).equals("17")||rs.getString(5).equals("17")||rs.getString(6).equals("17")||rs.getString(7).equals("17")||rs.getString(8).equals("17")||rs.getString(9).equals("17")||rs.getString(10).equals("17")||rs.getString(11).equals("17")||rs.getString(12).equals("17"))
                {
                    txtDate17.setBackground(Color.ORANGE);
                }
                if(rs.getString(1).equals("18") || rs.getString(2).equals("18")||rs.getString(3).equals("18")||rs.getString(4).equals("18")||rs.getString(5).equals("18")||rs.getString(6).equals("18")||rs.getString(7).equals("18")||rs.getString(8).equals("18")||rs.getString(9).equals("18")||rs.getString(10).equals("18")||rs.getString(11).equals("18")||rs.getString(12).equals("18"))
                {
                    txtDate18.setBackground(Color.ORANGE);
                }
                if(rs.getString(1).equals("19") || rs.getString(2).equals("19")||rs.getString(3).equals("19")||rs.getString(4).equals("19")||rs.getString(5).equals("19")||rs.getString(6).equals("19")||rs.getString(7).equals("19")||rs.getString(8).equals("19")||rs.getString(9).equals("19")||rs.getString(10).equals("19")||rs.getString(11).equals("19")||rs.getString(12).equals("19"))
                {
                    txtDate19.setBackground(Color.ORANGE);
                }
                if(rs.getString(1).equals("20") || rs.getString(2).equals("20")||rs.getString(3).equals("20")||rs.getString(4).equals("20")||rs.getString(5).equals("20")||rs.getString(6).equals("20")||rs.getString(7).equals("20")||rs.getString(8).equals("20")||rs.getString(9).equals("20")||rs.getString(10).equals("20")||rs.getString(11).equals("20")||rs.getString(12).equals("20"))
                {
                    txtDate20.setBackground(Color.ORANGE);
                }
                if(rs.getString(1).equals("21") || rs.getString(2).equals("21")||rs.getString(3).equals("21")||rs.getString(4).equals("21")||rs.getString(5).equals("21")||rs.getString(6).equals("21")||rs.getString(7).equals("21")||rs.getString(8).equals("21")||rs.getString(9).equals("21")||rs.getString(10).equals("21")||rs.getString(11).equals("21")||rs.getString(12).equals("21"))
                {
                    txtDate21.setBackground(Color.ORANGE);
                }
                if(rs.getString(1).equals("22") || rs.getString(2).equals("22")||rs.getString(3).equals("22")||rs.getString(4).equals("22")||rs.getString(5).equals("22")||rs.getString(6).equals("22")||rs.getString(7).equals("22")||rs.getString(8).equals("22")||rs.getString(9).equals("22")||rs.getString(10).equals("22")||rs.getString(11).equals("22")||rs.getString(12).equals("22"))
                {
                    txtDate22.setBackground(Color.ORANGE);
                }
                if(rs.getString(1).equals("23") || rs.getString(2).equals("23")||rs.getString(3).equals("23")||rs.getString(4).equals("23")||rs.getString(5).equals("23")||rs.getString(6).equals("23")||rs.getString(7).equals("23")||rs.getString(8).equals("23")||rs.getString(9).equals("23")||rs.getString(10).equals("23")||rs.getString(11).equals("23")||rs.getString(12).equals("23"))
                {
                    txtDate23.setBackground(Color.ORANGE);
                }
                if(rs.getString(1).equals("24") || rs.getString(2).equals("24")||rs.getString(3).equals("24")||rs.getString(4).equals("24")||rs.getString(5).equals("24")||rs.getString(6).equals("24")||rs.getString(7).equals("24")||rs.getString(8).equals("24")||rs.getString(9).equals("24")||rs.getString(10).equals("24")||rs.getString(11).equals("24")||rs.getString(12).equals("24"))
                {
                    txtDate24.setBackground(Color.ORANGE);
                }
                if(rs.getString(1).equals("25") || rs.getString(2).equals("25")||rs.getString(3).equals("25")||rs.getString(4).equals("25")||rs.getString(5).equals("25")||rs.getString(6).equals("25")||rs.getString(7).equals("25")||rs.getString(8).equals("25")||rs.getString(9).equals("25")||rs.getString(10).equals("25")||rs.getString(11).equals("25")||rs.getString(12).equals("25"))
                {
                    txtDate25.setBackground(Color.ORANGE);
                }
                if(rs.getString(1).equals("26") || rs.getString(2).equals("26")||rs.getString(3).equals("26")||rs.getString(4).equals("26")||rs.getString(5).equals("26")||rs.getString(6).equals("26")||rs.getString(7).equals("26")||rs.getString(8).equals("26")||rs.getString(9).equals("26")||rs.getString(10).equals("26")||rs.getString(11).equals("26")||rs.getString(12).equals("26"))
                {
                    txtDate26.setBackground(Color.ORANGE);
                }
                if(rs.getString(1).equals("27") || rs.getString(2).equals("27")||rs.getString(3).equals("27")||rs.getString(4).equals("27")||rs.getString(5).equals("27")||rs.getString(6).equals("27")||rs.getString(7).equals("27")||rs.getString(8).equals("27")||rs.getString(9).equals("27")||rs.getString(10).equals("27")||rs.getString(11).equals("27")||rs.getString(12).equals("27"))
                {
                    txtDate27.setBackground(Color.ORANGE);
                }
                if(rs.getString(1).equals("28") || rs.getString(2).equals("28")||rs.getString(3).equals("28")||rs.getString(4).equals("28")||rs.getString(5).equals("28")||rs.getString(6).equals("28")||rs.getString(7).equals("28")||rs.getString(8).equals("28")||rs.getString(9).equals("28")||rs.getString(10).equals("28")||rs.getString(11).equals("28")||rs.getString(12).equals("28"))
                {
                    txtDate28.setBackground(Color.ORANGE);
                }
                if(rs.getString(1).equals("29") || rs.getString(2).equals("29")||rs.getString(3).equals("29")||rs.getString(4).equals("29")||rs.getString(5).equals("29")||rs.getString(6).equals("29")||rs.getString(7).equals("29")||rs.getString(8).equals("29")||rs.getString(9).equals("29")||rs.getString(10).equals("29")||rs.getString(11).equals("29")||rs.getString(12).equals("29"))
                {
                    txtDate29.setBackground(Color.ORANGE);
                }
                if(rs.getString(1).equals("30") || rs.getString(2).equals("30")||rs.getString(3).equals("30")||rs.getString(4).equals("30")||rs.getString(5).equals("30")||rs.getString(6).equals("30")||rs.getString(7).equals("30")||rs.getString(8).equals("30")||rs.getString(9).equals("30")||rs.getString(10).equals("30")||rs.getString(11).equals("30")||rs.getString(12).equals("30"))
                {
                    txtDate30.setBackground(Color.ORANGE);
                }
                if(rs.getString(1).equals("31") || rs.getString(2).equals("31")||rs.getString(3).equals("31")||rs.getString(4).equals("31")||rs.getString(5).equals("31")||rs.getString(6).equals("31")||rs.getString(7).equals("31")||rs.getString(8).equals("31")||rs.getString(9).equals("31")||rs.getString(10).equals("31")||rs.getString(11).equals("31")||rs.getString(12).equals("31"))
                {
                    txtDate31.setBackground(Color.ORANGE);
                }
                
            }
        }
        catch(Exception e)
        {

        }
    }
     public void advancedDeduction() {
        
         try {
            connection c = new connection();
            Connection con = c.conn();
            PreparedStatement ps=con.prepareStatement("SELECT SUM(amount) FROM tbl_advancepayment WHERE empId=? AND pMonth=? AND pYear=?");
            ps.setInt(1, empId);
            ps.setString(2, cmbMonth.getSelectedItem().toString());
            ps.setString(3, cmbYear.getSelectedItem().toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) 
            {
                txtOtherDeduction.setText(Double.toString(rs.getDouble(1)));
            }
            con.close();
        } catch (SQLException ex) {
            
        }
    }
    public void fillTextFields(JTextField date,JTextField ot,JTextField normal,JTextField hot)
    {
        if(date.getBackground()!=Color.YELLOW && date.getBackground()!=Color.orange)
        {
            if(isDouble(date.getText()))
            {
                if(Double.parseDouble(date.getText())-8>0)
                {
                    ot.setText(Double.toString(Double.parseDouble(date.getText())-8));
                }
                else
                {
                    ot.setText("0");
                }
                normal.setText(Double.toString(Math.abs(Double.parseDouble(ot.getText())-Double.parseDouble(date.getText()))));
                hot.setText("0");
            }
            else
            {
                if(date.getText().equalsIgnoreCase("a"))
                {
                    ot.setText("0");
                    normal.setText("0");
                }
                if(date.getText().equalsIgnoreCase("l"))
                {
                    ot.setText("0");
                    normal.setText("0");
                }
                
            }
        }
        else
        {
            if(isDouble(date.getText()))
            {
                 hot.setText(date.getText());
            }
            else
            {
                if(date.getText().equalsIgnoreCase("a"))
                {
                    ot.setText("0");
                    normal.setText("0");
                }
                if(date.getText().equalsIgnoreCase("l"))
                {
                    ot.setText("0");
                    normal.setText("0");
                }
                hot.setText("0");
            }
           
            normal.setText("0");
            ot.setText("0");
        }
        totalNormalHrs();
        totalOTHrs();
        totalHOTHrs();
        countAbsent();
        countLeave();
        ratePerHour();
        salaryCalculation();
        
    }
    public void salaryCalculation()
    {
        double totalAddition=Math.round(Double.parseDouble(txtFoodAddition.getText())+Double.parseDouble(txtDues.getText())+Double.parseDouble(txtBonus.getText())+Double.parseDouble(txtOtherAddition.getText()));
        double totalDeduction=Math.round(Double.parseDouble(txtAbsentDeduction.getText())+Double.parseDouble(txtAdvanceDeduction.getText())+Double.parseDouble(txtFoodDeduction.getText())+Double.parseDouble(txtOtherDeduction.getText()));
        double grossAmount=Double.parseDouble(txtGrossAmount.getText());
        txtTotalAddition.setText(Double.toString(totalAddition));
        txtTotalDeduction.setText(Double.toString(totalDeduction));
        txtNetAmount.setText(Double.toString(Math.round((grossAmount+totalAddition)-totalDeduction)));
    }
    public static boolean isDouble(String s) 
    {
        try 
        { 
            Double.parseDouble(s); 
        } 
        catch(NumberFormatException e) 
        { 
            return false; 
        }
        // only got here if we didn't return false
        return true;
    }
    public void fillEntireTextBox()
    {       
            
            fillTextFields(txtDate1,txtOT1,txtNormalDate1,txtHot1);
            fillTextFields(txtDate2,txtOT2,txtNormalDate2,txtHot2);
            fillTextFields(txtDate3,txtOT3,txtNormalDate3,txtHot3);
            fillTextFields(txtDate4,txtOT4,txtNormalDate4,txtHot4);
            fillTextFields(txtDate5,txtOT5,txtNormalDate5,txtHot5);
            fillTextFields(txtDate6,txtOT6,txtNormalDate6,txtHot6);
            fillTextFields(txtDate7,txtOT7,txtNormalDate7,txtHot7);
            fillTextFields(txtDate8,txtOT8,txtNormalDate8,txtHot8);
            fillTextFields(txtDate9,txtOT9,txtNormalDate9,txtHot9);
            fillTextFields(txtDate10,txtOT10,txtNormalDate10,txtHot10);
            fillTextFields(txtDate11,txtOT11,txtNormalDate11,txtHot11);
            fillTextFields(txtDate12,txtOT12,txtNormalDate12,txtHot12);
            fillTextFields(txtDate12,txtOT12,txtNormalDate12,txtHot12);
            fillTextFields(txtDate13,txtOT13,txtNormalDate13,txtHot13);
            fillTextFields(txtDate14,txtOT14,txtNormalDate14,txtHot14);
            fillTextFields(txtDate15,txtOT15,txtNormalDate15,txtHot15);
            fillTextFields(txtDate16,txtOT16,txtNormalDate16,txtHot16);
            fillTextFields(txtDate17,txtOT17,txtNormalDate17,txtHot17);
            fillTextFields(txtDate18,txtOT18,txtNormalDate18,txtHot18);
            fillTextFields(txtDate19,txtOT19,txtNormalDate19,txtHot19);
            fillTextFields(txtDate20,txtOT20,txtNormalDate20,txtHot20);
            fillTextFields(txtDate21,txtOT21,txtNormalDate21,txtHot21);
            fillTextFields(txtDate22,txtOT22,txtNormalDate22,txtHot22);
            fillTextFields(txtDate22,txtOT22,txtNormalDate22,txtHot22);
            fillTextFields(txtDate23,txtOT23,txtNormalDate23,txtHot23);
            fillTextFields(txtDate24,txtOT24,txtNormalDate24,txtHot24);
            fillTextFields(txtDate25,txtOT25,txtNormalDate25,txtHot25);
            fillTextFields(txtDate26,txtOT26,txtNormalDate26,txtHot26);
            fillTextFields(txtDate27,txtOT27,txtNormalDate27,txtHot27);
            fillTextFields(txtDate28,txtOT28,txtNormalDate28,txtHot28);
            fillTextFields(txtDate29,txtOT29,txtNormalDate29,txtHot29);
            fillTextFields(txtDate30,txtOT30,txtNormalDate30,txtHot30);
            fillTextFields(txtDate31,txtOT31,txtNormalDate31,txtHot31);
    }
    public void totalNormalHrs()
    {

        double totalNormal=
                Double.parseDouble(txtNormalDate1.getText())+
                Double.parseDouble(txtNormalDate2.getText())+
                Double.parseDouble(txtNormalDate3.getText())+
                Double.parseDouble(txtNormalDate4.getText())+
                Double.parseDouble(txtNormalDate5.getText())+
                Double.parseDouble(txtNormalDate6.getText())+
                Double.parseDouble(txtNormalDate7.getText())+
                Double.parseDouble(txtNormalDate8.getText())+
                Double.parseDouble(txtNormalDate9.getText())+
                Double.parseDouble(txtNormalDate10.getText())+
                Double.parseDouble(txtNormalDate11.getText())+
                Double.parseDouble(txtNormalDate12.getText())+
                Double.parseDouble(txtNormalDate13.getText())+
                Double.parseDouble(txtNormalDate14.getText())+
                Double.parseDouble(txtNormalDate15.getText())+
                Double.parseDouble(txtNormalDate16.getText())+
                Double.parseDouble(txtNormalDate17.getText())+
                Double.parseDouble(txtNormalDate18.getText())+
                Double.parseDouble(txtNormalDate19.getText())+
                Double.parseDouble(txtNormalDate20.getText())+
                Double.parseDouble(txtNormalDate21.getText())+
                Double.parseDouble(txtNormalDate22.getText())+
                Double.parseDouble(txtNormalDate23.getText())+
                Double.parseDouble(txtNormalDate24.getText())+
                Double.parseDouble(txtNormalDate25.getText())+
                Double.parseDouble(txtNormalDate26.getText())+
                Double.parseDouble(txtNormalDate27.getText())+
                Double.parseDouble(txtNormalDate28.getText())+
                Double.parseDouble(txtNormalDate29.getText())+
                Double.parseDouble(txtNormalDate30.getText())+
                Double.parseDouble(txtNormalDate31.getText());
        txtTotalNormal.setText(Double.toString(totalNormal));
        
    }
    public void totalOTHrs()
    {
        totalOT=
                Double.parseDouble(txtOT1.getText())+
                Double.parseDouble(txtOT2.getText())+
                Double.parseDouble(txtOT3.getText())+
                Double.parseDouble(txtOT4.getText())+
                Double.parseDouble(txtOT5.getText())+
                Double.parseDouble(txtOT6.getText())+
                Double.parseDouble(txtOT7.getText())+
                Double.parseDouble(txtOT8.getText())+
                Double.parseDouble(txtOT9.getText())+
                Double.parseDouble(txtOT10.getText())+
                Double.parseDouble(txtOT11.getText())+
                Double.parseDouble(txtOT12.getText())+
                Double.parseDouble(txtOT13.getText())+
                Double.parseDouble(txtOT14.getText())+
                Double.parseDouble(txtOT15.getText())+
                Double.parseDouble(txtOT16.getText())+
                Double.parseDouble(txtOT17.getText())+
                Double.parseDouble(txtOT18.getText())+
                Double.parseDouble(txtOT19.getText())+
                Double.parseDouble(txtOT20.getText())+
                Double.parseDouble(txtOT21.getText())+
                Double.parseDouble(txtOT22.getText())+
                Double.parseDouble(txtOT23.getText())+
                Double.parseDouble(txtOT24.getText())+
                Double.parseDouble(txtOT25.getText())+
                Double.parseDouble(txtOT26.getText())+
                Double.parseDouble(txtOT27.getText())+
                Double.parseDouble(txtOT28.getText())+
                Double.parseDouble(txtOT29.getText())+
                Double.parseDouble(txtOT30.getText())+
                Double.parseDouble(txtOT31.getText());
        txtTotalOT.setText(Double.toString(totalOT));
                
        
    }
    public void totalHOTHrs()
    {
        totalHOT=
                Double.parseDouble(txtHot1.getText())+
                Double.parseDouble(txtHot2.getText())+
                Double.parseDouble(txtHot3.getText())+
                Double.parseDouble(txtHot4.getText())+
                Double.parseDouble(txtHot5.getText())+
                Double.parseDouble(txtHot6.getText())+
                Double.parseDouble(txtHot7.getText())+
                Double.parseDouble(txtHot8.getText())+
                Double.parseDouble(txtHot9.getText())+
                Double.parseDouble(txtHot10.getText())+
                Double.parseDouble(txtHot11.getText())+
                Double.parseDouble(txtHot12.getText())+
                Double.parseDouble(txtHot13.getText())+
                Double.parseDouble(txtHot14.getText())+
                Double.parseDouble(txtHot15.getText())+
                Double.parseDouble(txtHot16.getText())+
                Double.parseDouble(txtHot17.getText())+
                Double.parseDouble(txtHot18.getText())+
                Double.parseDouble(txtHot19.getText())+
                Double.parseDouble(txtHot20.getText())+
                Double.parseDouble(txtHot21.getText())+
                Double.parseDouble(txtHot22.getText())+
                Double.parseDouble(txtHot23.getText())+
                Double.parseDouble(txtHot24.getText())+
                Double.parseDouble(txtHot25.getText())+
                Double.parseDouble(txtHot26.getText())+
                Double.parseDouble(txtHot27.getText())+
                Double.parseDouble(txtHot28.getText())+
                Double.parseDouble(txtHot29.getText())+
                Double.parseDouble(txtHot30.getText())+
                Double.parseDouble(txtHot31.getText());
        txtTotalHot.setText(Double.toString(totalHOT));
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
    public void countAbsent()
    {
        txtCountAbsent.setText(Integer.toString(countACharacter('A',countAbsAndPre())));
    }
    public void countLeave()
    {
        txtCountLeave.setText(Integer.toString(countACharacter('L',countAbsAndPre())));
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
    public void ratePerHour()
    {
        Calendar calendar = Calendar.getInstance();
        int year =this.year;
        int month =this.month;
        int date = 1;
        calendar.set(year, month, date);
        int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        
        double normalRateperHour=round(Double.parseDouble(txtBasic.getText())/days/8,2);
        double otRatePerHour=round((Double.parseDouble(txtBasic.getText())/days/8)*1.25,2);
        double hotRatePerHour=round((Double.parseDouble(txtBasic.getText())/days/8)*1.5,2);
        
        double normalGrossAmount=round(Double.parseDouble(txtBasic.getText()),2);
        double otGrossAmount=round(totalOT*otRatePerHour,2);
        double hotGrossamount=round(totalHOT*hotRatePerHour,2);
        double grossAmount=round(normalGrossAmount+otGrossAmount+hotGrossamount,2);
        double absentDeduction=round((Double.parseDouble(txtBasic.getText()))/days*Integer.parseInt(txtCountAbsent.getText()),2);
        
        txtNormalRatePerHour.setText(Double.toString(normalRateperHour));
        txtOtRatePerHour.setText(Double.toString(otRatePerHour));
        txtHotRatePerHour.setText(Double.toString(hotRatePerHour));
        
        txtGrossAmountNormal.setText(Double.toString(normalGrossAmount));
        txtGrossAmountOT.setText(Double.toString(otGrossAmount));
        txtGrossAmountHOT.setText(Double.toString(hotGrossamount));
        
        txtGrossAmount.setText(Double.toString(grossAmount));
        
        
        txtAbsentDeduction.setText(Double.toString(absentDeduction));
        
        if(Integer.parseInt(txtCountAbsent.getText())==0 && Integer.parseInt(txtCountLeave.getText())<=3)
        {
            txtBonus.setText("50.0");
        }  
        else
        {
            txtBonus.setText("0.0");   
        }
    }
    public static double round(double value, int places) 
    {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    public void setBackColorYellow(JTextField date,JTextField normal,JTextField ot,JTextField hot)
    {
        date.setBackground(Color.YELLOW);
        normal.setBackground(Color.YELLOW);
        ot.setBackground(Color.YELLOW);
        hot.setBackground(Color.YELLOW);
    }
    public void setBackColorDefault()
    {
        txtDate1.setBackground(new Color(142,222,252));
        txtDate2.setBackground(new Color(142,222,252));
        txtDate3.setBackground(new Color(142,222,252));
        txtDate4.setBackground(new Color(142,222,252));
        txtDate5.setBackground(new Color(142,222,252));
        txtDate6.setBackground(new Color(142,222,252));
        txtDate7.setBackground(new Color(142,222,252));
        txtDate8.setBackground(new Color(142,222,252));
        txtDate9.setBackground(new Color(142,222,252));
        txtDate10.setBackground(new Color(142,222,252));
        txtDate11.setBackground(new Color(142,222,252));
        txtDate12.setBackground(new Color(142,222,252));
        txtDate13.setBackground(new Color(142,222,252));
        txtDate14.setBackground(new Color(142,222,252));
        txtDate15.setBackground(new Color(142,222,252));
        txtDate16.setBackground(new Color(142,222,252));  
        txtDate17.setBackground(new Color(142,222,252)); 
        txtDate18.setBackground(new Color(142,222,252)); 
        txtDate19.setBackground(new Color(142,222,252)); 
        txtDate20.setBackground(new Color(142,222,252)); 
        txtDate21.setBackground(new Color(142,222,252)); 
        txtDate22.setBackground(new Color(142,222,252)); 
        txtDate23.setBackground(new Color(142,222,252)); 
        txtDate24.setBackground(new Color(142,222,252)); 
        txtDate25.setBackground(new Color(142,222,252)); 
        txtDate26.setBackground(new Color(142,222,252)); 
        txtDate27.setBackground(new Color(142,222,252)); 
        txtDate28.setBackground(new Color(142,222,252)); 
        txtDate29.setBackground(new Color(142,222,252)); 
        txtDate30.setBackground(new Color(142,222,252)); 
        txtDate31.setBackground(new Color(142,222,252));
        txtNormalDate1.setBackground(new Color(142,222,252));
        txtNormalDate2.setBackground(new Color(142,222,252));
        txtNormalDate3.setBackground(new Color(142,222,252));
        txtNormalDate4.setBackground(new Color(142,222,252));
        txtNormalDate5.setBackground(new Color(142,222,252));
        txtNormalDate6.setBackground(new Color(142,222,252));
        txtNormalDate7.setBackground(new Color(142,222,252));
        txtNormalDate8.setBackground(new Color(142,222,252));
        txtNormalDate9.setBackground(new Color(142,222,252));
        txtNormalDate10.setBackground(new Color(142,222,252));
        txtNormalDate11.setBackground(new Color(142,222,252));
        txtNormalDate12.setBackground(new Color(142,222,252));
        txtNormalDate13.setBackground(new Color(142,222,252));
        txtNormalDate14.setBackground(new Color(142,222,252));
        txtNormalDate15.setBackground(new Color(142,222,252));
        txtNormalDate16.setBackground(new Color(142,222,252));
        txtNormalDate17.setBackground(new Color(142,222,252));
        txtNormalDate18.setBackground(new Color(142,222,252));
        txtNormalDate19.setBackground(new Color(142,222,252));
        txtNormalDate20.setBackground(new Color(142,222,252));
        txtNormalDate21.setBackground(new Color(142,222,252));
        txtNormalDate22.setBackground(new Color(142,222,252));
        txtNormalDate23.setBackground(new Color(142,222,252));
        txtNormalDate24.setBackground(new Color(142,222,252));
        txtNormalDate25.setBackground(new Color(142,222,252));
        txtNormalDate26.setBackground(new Color(142,222,252));
        txtNormalDate27.setBackground(new Color(142,222,252));
        txtNormalDate28.setBackground(new Color(142,222,252));
        txtNormalDate29.setBackground(new Color(142,222,252));
        txtNormalDate30.setBackground(new Color(142,222,252));
        txtNormalDate31.setBackground(new Color(142,222,252));
        txtOT1.setBackground(new Color(142,222,252));
        txtOT2.setBackground(new Color(142,222,252));
        txtOT3.setBackground(new Color(142,222,252));
        txtOT4.setBackground(new Color(142,222,252));
        txtOT5.setBackground(new Color(142,222,252));
        txtOT6.setBackground(new Color(142,222,252));
        txtOT7.setBackground(new Color(142,222,252));
        txtOT8.setBackground(new Color(142,222,252));
        txtOT9.setBackground(new Color(142,222,252));
        txtOT10.setBackground(new Color(142,222,252));
        txtOT11.setBackground(new Color(142,222,252));
        txtOT12.setBackground(new Color(142,222,252));
        txtOT13.setBackground(new Color(142,222,252));
        txtOT14.setBackground(new Color(142,222,252));
        txtOT15.setBackground(new Color(142,222,252));
        txtOT16.setBackground(new Color(142,222,252));
        txtOT17.setBackground(new Color(142,222,252));
        txtOT18.setBackground(new Color(142,222,252));
        txtOT19.setBackground(new Color(142,222,252));
        txtOT20.setBackground(new Color(142,222,252));
        txtOT21.setBackground(new Color(142,222,252));
        txtOT22.setBackground(new Color(142,222,252));
        txtOT23.setBackground(new Color(142,222,252));
        txtOT24.setBackground(new Color(142,222,252));
        txtOT25.setBackground(new Color(142,222,252));
        txtOT26.setBackground(new Color(142,222,252));
        txtOT27.setBackground(new Color(142,222,252));
        txtOT28.setBackground(new Color(142,222,252));
        txtOT29.setBackground(new Color(142,222,252));
        txtOT30.setBackground(new Color(142,222,252));
        txtOT31.setBackground(new Color(142,222,252));
        
        txtDate1.setText("");
        txtDate2.setText("");
        txtDate3.setText("");
        txtDate4.setText("");
        txtDate5.setText("");
        txtDate6.setText("");
        txtDate7.setText("");
        txtDate8.setText("");
        txtDate9.setText("");
        txtDate10.setText("");
        txtDate11.setText("");
        txtDate12.setText("");
        txtDate13.setText("");
        txtDate14.setText("");
        txtDate15.setText("");
        txtDate16.setText("");
        txtDate17.setText("");
        txtDate18.setText("");
        txtDate19.setText("");
        txtDate20.setText("");
        txtDate21.setText("");
        txtDate22.setText("");
        txtDate23.setText("");
        txtDate24.setText("");
        txtDate25.setText("");
        txtDate26.setText("");
        txtDate27.setText("");
        txtDate28.setText("");
        txtDate29.setText("");
        txtDate30.setText("");
        txtDate31.setText("");
        
        txtNormalDate1.setText("0");
        txtNormalDate2.setText("0");
        txtNormalDate3.setText("0");
        txtNormalDate4.setText("0");
        txtNormalDate5.setText("0");
        txtNormalDate6.setText("0");
        txtNormalDate7.setText("0");
        txtNormalDate8.setText("0");
        txtNormalDate9.setText("0");
        txtNormalDate10.setText("0");
        txtNormalDate11.setText("0");
        txtNormalDate12.setText("0");
        txtNormalDate13.setText("0");
        txtNormalDate14.setText("0");
        txtNormalDate15.setText("0");
        txtNormalDate16.setText("0");
        txtNormalDate17.setText("0");
        txtNormalDate18.setText("0");
        txtNormalDate19.setText("0");
        txtNormalDate20.setText("0");
        txtNormalDate21.setText("0");
        txtNormalDate22.setText("0");
        txtNormalDate23.setText("0");
        txtNormalDate24.setText("0");
        txtNormalDate25.setText("0");
        txtNormalDate26.setText("0");
        txtNormalDate27.setText("0");
        txtNormalDate28.setText("0");
        txtNormalDate29.setText("0");
        txtNormalDate30.setText("0");
        txtNormalDate31.setText("0");
        
        txtOT1.setText("0");
        txtOT2.setText("0");
        txtOT3.setText("0");
        txtOT4.setText("0");
        txtOT5.setText("0");
        txtOT6.setText("0");
        txtOT7.setText("0");
        txtOT8.setText("0");
        txtOT9.setText("0");
        txtOT10.setText("0");
        txtOT11.setText("0");
        txtOT12.setText("0");
        txtOT13.setText("0");
        txtOT14.setText("0");
        txtOT15.setText("0");
        txtOT16.setText("0");
        txtOT17.setText("0");
        txtOT18.setText("0");
        txtOT19.setText("0");
        txtOT20.setText("0");
        txtOT21.setText("0");
        txtOT22.setText("0");
        txtOT23.setText("0");
        txtOT24.setText("0");
        txtOT25.setText("0");
        txtOT26.setText("0");
        txtOT27.setText("0");
        txtOT28.setText("0");
        txtOT29.setText("0");
        txtOT30.setText("0");
        txtOT31.setText("0");
        txtHot1.setText("0");
        txtHot2.setText("0");
        txtHot3.setText("0");
        txtHot4.setText("0");
        txtHot5.setText("0");
        txtHot6.setText("0");
        txtHot7.setText("0");
        txtHot8.setText("0");
        txtHot9.setText("0");
        txtHot10.setText("0");
        txtHot11.setText("0");
        txtHot12.setText("0");
        txtHot13.setText("0");
        txtHot14.setText("0");
        txtHot15.setText("0");
        txtHot16.setText("0");
        txtHot17.setText("0");
        txtHot18.setText("0");
        txtHot19.setText("0");
        txtHot20.setText("0");
        txtHot21.setText("0");
        txtHot22.setText("0");
        txtHot23.setText("0");
        txtHot24.setText("0");
        txtHot25.setText("0");
        txtHot26.setText("0");
        txtHot27.setText("0");
        txtHot28.setText("0");
        txtHot29.setText("0");
        txtHot30.setText("0");
        txtHot31.setText("0");
        
        txtCountAbsent.setText("0");
        txtCountLeave.setText("0");
        
        txtTotalNormal.setText("0.0");
        txtTotalOT.setText("0.0");
        txtTotalHot.setText("0.0");
        
        txtNormalRatePerHour.setText("0.0");
        txtOtRatePerHour.setText("0.0");
        txtHotRatePerHour.setText("0.0");
        
        txtGrossAmountNormal.setText("0.0");
        txtGrossAmountOT.setText("0.0");
        txtGrossAmountHOT.setText("0.0");
        
        txtGrossAmount.setText("0.0");
        
        txtFoodAddition.setText("0.0");
        txtDues.setText("0.0");
        txtBonus.setText("0.0");
        txtOtherAddition.setText("0.0");
        
        txtAbsentDeduction.setText("0.0");
        txtAdvanceDeduction.setText("0.0");
        txtFoodDeduction.setText("0.0");
        txtOtherDeduction.setText("0.0");
        txtTotalAddition.setText("0.0");
        txtTotalDeduction.setText("0.0");
        txtNetAmount.setText("0.0");
        
    }
     public void viewDbEmployeeDetails()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            int i=0;
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT timeSheetId,empId,timeSheetMonth '"+"Month"+"',timeSheetYear '"+"Year"+"',f1'"+"1"+"',f2'"+"2"+"',f3'"+"3"+"',f4'"+"4"+"',f5'"+"5"+"',f6'"+"6"+"',f7'"+"7"+"',f8'"+"8"+"',f9'"+"9"+"',f10'"+"10"+"',f11'"+"11"+"',f12'"+"12"+"',f13'"+"13"+"',f14'"+"14"+"',f15'"+"15"+"',f16'"+"16"+"',f17'"+"17"+"',f18'"+"18"+"',f19'"+"19"+"',f20'"+"20"+"',f21'"+"21"+"',f22'"+"22"+"',f23'"+"23"+"',f24'"+"24"+"',f25'"+"25"+"',f26'"+"26"+"',f27'"+"27"+"',f28'"+"28"+"',f29'"+"29"+"',f30'"+"30"+"',f31'"+"31"+"',foodAddition,dueAddition,otherAddition,advanceDeduction,foodDeduction,otherDeduction FROM tbl_timesheet where empId="+empId);
            jtableTimeSheet.setModel(DbUtils.resultSetToTableModel(rs));
            con.close();
            jtableTimeSheet.getColumnModel().getColumn(0).setMinWidth(0);
            jtableTimeSheet.getColumnModel().getColumn(0).setMaxWidth(0);
            jtableTimeSheet.getColumnModel().getColumn(1).setMinWidth(0);
            jtableTimeSheet.getColumnModel().getColumn(1).setMaxWidth(0);
            jtableTimeSheet.getColumnModel().getColumn(2).setMinWidth(100);
            jtableTimeSheet.getColumnModel().getColumn(2).setMaxWidth(100);
            jtableTimeSheet.getColumnModel().getColumn(3).setMinWidth(100);
            jtableTimeSheet.getColumnModel().getColumn(3).setMaxWidth(100);
            jtableTimeSheet.getColumnModel().getColumn(35).setMinWidth(0);
            jtableTimeSheet.getColumnModel().getColumn(35).setMaxWidth(0);
            jtableTimeSheet.getColumnModel().getColumn(36).setMinWidth(0);
            jtableTimeSheet.getColumnModel().getColumn(36).setMaxWidth(0);
            jtableTimeSheet.getColumnModel().getColumn(37).setMinWidth(0);
            jtableTimeSheet.getColumnModel().getColumn(37).setMaxWidth(0);
            jtableTimeSheet.getColumnModel().getColumn(38).setMinWidth(0);
            jtableTimeSheet.getColumnModel().getColumn(38).setMaxWidth(0);
            jtableTimeSheet.getColumnModel().getColumn(39).setMinWidth(0);
            jtableTimeSheet.getColumnModel().getColumn(39).setMaxWidth(0);
            jtableTimeSheet.getColumnModel().getColumn(40).setMinWidth(0);
            jtableTimeSheet.getColumnModel().getColumn(40).setMaxWidth(0);

        }
        catch(Exception e)
        {

        }
    }
    public void textFieldValueCheck(JTextField txtFiled,java.awt.event.KeyEvent evt)
    {
        int charCode= evt.getKeyCode();
            if (((charCode >=KeyEvent.VK_0) && (charCode<=KeyEvent.VK_9))||((charCode >=KeyEvent.VK_NUMPAD0) && (charCode<=KeyEvent.VK_NUMPAD9))|| charCode==KeyEvent.VK_BACK_SPACE || charCode==KeyEvent.VK_DELETE|| charCode==KeyEvent.VK_A||charCode==KeyEvent.VK_H||charCode==KeyEvent.VK_L||charCode==KeyEvent.VK_O||charCode==KeyEvent.VK_F||charCode==KeyEvent.VK_DECIMAL||charCode==KeyEvent.VK_CAPS_LOCK||charCode==KeyEvent.VK_TAB||charCode==KeyEvent.VK_ALT)
            {

            }
            else
            {
                JOptionPane.showMessageDialog(null,"This Value is not allowed in the field !!","Warning!!!",JOptionPane.ERROR_MESSAGE);
                txtFiled.setText("");
            }
    }
    public void viewEmpDetails()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            int i=0;
            PreparedStatement ps=con.prepareStatement("SELECT empId,empName,nationality,profession,passportNumber,DATE_FORMAT(passportExpiry ,'%d/%b/%Y'),DATE_FORMAT(visaExpiry,'%d/%b/%Y'),idNumber,basicSalary FROM tbl_labourdetails where empId=?");
            ps.setInt(1,empId);
            ResultSet rs=ps.executeQuery();
            while(rs.next())
            {
                lblEmpName.setText(rs.getString(2));
                txtBasic.setText(rs.getString(9));
            }
            
        }
        catch(Exception e)
        {

        }
    }
    public void SetFriday()
    {
        setBackColorDefault();
         year=Integer.parseInt(cmbYear.getSelectedItem().toString());
         month=cmbMonth.getSelectedIndex();
         Calendar cal = new GregorianCalendar(year, month, 1);
        do 
        {
            int day = cal.get(Calendar.DAY_OF_WEEK);
            if (day == Calendar.FRIDAY) 
            {
                if((cal.get(Calendar.DAY_OF_MONTH))==1)
                {
                    setBackColorYellow(txtDate1,txtNormalDate1,txtOT1,txtHot1);
                }
                if((cal.get(Calendar.DAY_OF_MONTH))==2)
                {
                    setBackColorYellow(txtDate2,txtNormalDate2,txtOT2,txtHot2);
                }
                if((cal.get(Calendar.DAY_OF_MONTH))==3)
                {
                    setBackColorYellow(txtDate3,txtNormalDate3,txtOT3,txtHot3);
                }
                if((cal.get(Calendar.DAY_OF_MONTH))==4)
                {
                    setBackColorYellow(txtDate4,txtNormalDate4,txtOT4,txtHot4);
                }
                if((cal.get(Calendar.DAY_OF_MONTH))==5)
                {
                    setBackColorYellow(txtDate5,txtNormalDate5,txtOT5,txtHot5);
                }
                if((cal.get(Calendar.DAY_OF_MONTH))==6)
                {
                    setBackColorYellow(txtDate6,txtNormalDate6,txtOT6,txtHot6);
                }
                if((cal.get(Calendar.DAY_OF_MONTH))==7)
                {
                    setBackColorYellow(txtDate7,txtNormalDate7,txtOT7,txtHot7);
                }
                if((cal.get(Calendar.DAY_OF_MONTH))==8)
                {
                    setBackColorYellow(txtDate8,txtNormalDate8,txtOT8,txtHot8);
                }
                if((cal.get(Calendar.DAY_OF_MONTH))==9)
                {
                    setBackColorYellow(txtDate9,txtNormalDate9,txtOT9,txtHot9);
                }
                if((cal.get(Calendar.DAY_OF_MONTH))==10)
                {
                   setBackColorYellow(txtDate10,txtNormalDate10,txtOT10,txtHot10);
                }
                if((cal.get(Calendar.DAY_OF_MONTH))==11)
                {
                   setBackColorYellow(txtDate11,txtNormalDate11,txtOT11,txtHot11);
                }
                if((cal.get(Calendar.DAY_OF_MONTH))==12)
                {
                    setBackColorYellow(txtDate12,txtNormalDate12,txtOT12,txtHot12);
                }
                if((cal.get(Calendar.DAY_OF_MONTH))==13)
                {
                    setBackColorYellow(txtDate13,txtNormalDate13,txtOT13,txtHot13);
                }
                if((cal.get(Calendar.DAY_OF_MONTH))==14)
                {
                    setBackColorYellow(txtDate14,txtNormalDate14,txtOT14,txtHot14);
                }
                if((cal.get(Calendar.DAY_OF_MONTH))==15)
                {
                    setBackColorYellow(txtDate15,txtNormalDate15,txtOT15,txtHot15);
                }
                if((cal.get(Calendar.DAY_OF_MONTH))==16)
                {
                    setBackColorYellow(txtDate16,txtNormalDate16,txtOT16,txtHot16);
                }
                if((cal.get(Calendar.DAY_OF_MONTH))==17)
                {
                    setBackColorYellow(txtDate17,txtNormalDate17,txtOT17,txtHot17);
                }
                if((cal.get(Calendar.DAY_OF_MONTH))==18)
                {
                    setBackColorYellow(txtDate18,txtNormalDate18,txtOT18,txtHot18);
                }
                if((cal.get(Calendar.DAY_OF_MONTH))==19)
                {
                    setBackColorYellow(txtDate19,txtNormalDate19,txtOT19,txtHot19);
                }
                if((cal.get(Calendar.DAY_OF_MONTH))==20)
                {
                   setBackColorYellow(txtDate20,txtNormalDate20,txtOT20,txtHot20);
                }
                if((cal.get(Calendar.DAY_OF_MONTH))==21)
                {
                    setBackColorYellow(txtDate21,txtNormalDate21,txtOT21,txtHot21);
                }
                if((cal.get(Calendar.DAY_OF_MONTH))==22)
                {
                    setBackColorYellow(txtDate22,txtNormalDate22,txtOT22,txtHot22);
                }
                if((cal.get(Calendar.DAY_OF_MONTH))==23)
                {
                    setBackColorYellow(txtDate23,txtNormalDate23,txtOT24,txtHot24);
                }
                if((cal.get(Calendar.DAY_OF_MONTH))==24)
                {
                   setBackColorYellow(txtDate24,txtNormalDate24,txtOT24,txtHot24);
                }
                if((cal.get(Calendar.DAY_OF_MONTH))==25)
                {
                   setBackColorYellow(txtDate25,txtNormalDate25,txtOT25,txtHot25);
                }
                if((cal.get(Calendar.DAY_OF_MONTH))==26)
                {
                     setBackColorYellow(txtDate26,txtNormalDate26,txtOT26,txtHot26);
                }
                if((cal.get(Calendar.DAY_OF_MONTH))==27)
                {
                    setBackColorYellow(txtDate27,txtNormalDate27,txtOT27,txtHot27);       
                }
                if((cal.get(Calendar.DAY_OF_MONTH))==28)
                {
                   setBackColorYellow(txtDate28,txtNormalDate28,txtOT28,txtHot28);
                }
                if((cal.get(Calendar.DAY_OF_MONTH))==29)
                {
                   setBackColorYellow(txtDate29,txtNormalDate29,txtOT29,txtHot29);
                }
                if((cal.get(Calendar.DAY_OF_MONTH))==30)
                {
                  setBackColorYellow(txtDate30,txtNormalDate30,txtOT30,txtHot30);
                }
                if((cal.get(Calendar.DAY_OF_MONTH))==31)
                {
                   setBackColorYellow(txtDate31,txtNormalDate31,txtOT31,txtHot31);
                }  
            }
            cal.add(Calendar.DAY_OF_YEAR, 1);
        }  while (cal.get(Calendar.MONTH) == month);
    }
     public void insertIntoDb()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            PreparedStatement ps=con.prepareStatement("INSERT INTO tbl_timesheet(empId,timeSheetMonth,timeSheetYear,f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f11,f12,f13,f14,f15,f16,f17,f18,f19,f20,f21,f22,f23,f24,f25,f26,f27,f28,f29,f30,f31,totalNormal,toalOT,totalHOT,normalRatePerHour,otRatePerHour,hotRatePerHour,grossAmountNormal,grossAmountOT,grossAmountHOT,countLeave,countAbsent,grossAmount,foodAddition,dueAddition,bonusAddition,otherAddition,absentDeduction,advanceDeduction,foodDeduction,otherDeduction) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            ps.setInt(1,empId);
            ps.setString(2,cmbMonth.getSelectedItem().toString());
            ps.setInt(3,Integer.parseInt(cmbYear.getSelectedItem().toString()));
            ps.setString(4,txtDate1.getText());
            ps.setString(5,txtDate2.getText());
            ps.setString(6,txtDate3.getText());
            ps.setString(7,txtDate4.getText());
            ps.setString(8,txtDate5.getText());
            ps.setString(9,txtDate6.getText());
            ps.setString(10,txtDate7.getText());
            ps.setString(11,txtDate8.getText());
            ps.setString(12,txtDate9.getText());
            ps.setString(13,txtDate10.getText());
            ps.setString(14,txtDate11.getText());
            ps.setString(15,txtDate12.getText());
            ps.setString(16,txtDate13.getText());
            ps.setString(17,txtDate14.getText());
            ps.setString(18,txtDate15.getText());
            ps.setString(19,txtDate16.getText());
            ps.setString(20,txtDate17.getText());
            ps.setString(21,txtDate18.getText());
            ps.setString(22,txtDate19.getText());
            ps.setString(23,txtDate20.getText());
            ps.setString(24,txtDate21.getText());
            ps.setString(25,txtDate22.getText());
            ps.setString(26,txtDate23.getText());
            ps.setString(27,txtDate24.getText());
            ps.setString(28,txtDate25.getText());
            ps.setString(29,txtDate26.getText());
            ps.setString(30,txtDate27.getText());
            ps.setString(31,txtDate28.getText());
            ps.setString(32,txtDate29.getText());
            ps.setString(33,txtDate30.getText());
            ps.setString(34,txtDate31.getText());
            ps.setDouble(35,Double.parseDouble(txtTotalNormal.getText()));
            ps.setDouble(36,Double.parseDouble(txtTotalOT.getText()));
            ps.setDouble(37,Double.parseDouble(txtTotalHot.getText()));
            
            ps.setDouble(38,Double.parseDouble(txtNormalRatePerHour.getText()));
            ps.setDouble(39,Double.parseDouble(txtOtRatePerHour.getText()));
            ps.setDouble(40,Double.parseDouble(txtHotRatePerHour.getText()));
            ps.setDouble(41,Double.parseDouble(txtGrossAmountNormal.getText()));
            ps.setDouble(42,Double.parseDouble(txtGrossAmountOT.getText()));
            ps.setDouble(43,Double.parseDouble(txtGrossAmountHOT.getText()));
            ps.setInt(44,Integer.parseInt(txtCountLeave.getText()));
            ps.setInt(45,Integer.parseInt(txtCountAbsent.getText()));
            ps.setDouble(46,Double.parseDouble(txtGrossAmount.getText()));
            ps.setDouble(47,Double.parseDouble(txtFoodAddition.getText()));
            ps.setDouble(48,Double.parseDouble(txtDues.getText()));
            ps.setDouble(49,Double.parseDouble(txtBonus.getText()));
            ps.setDouble(50,Double.parseDouble(txtOtherAddition.getText()));
            ps.setDouble(51,Double.parseDouble(txtAbsentDeduction.getText()));
            ps.setDouble(52,Double.parseDouble(txtAdvanceDeduction.getText()));
            ps.setDouble(53,Double.parseDouble(txtFoodDeduction.getText()));
            ps.setDouble(54,Double.parseDouble(txtOtherDeduction.getText()));
            ps.executeUpdate();
            con.close();
            dispose();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(rootPane,e+"Error SA001");
            System.out.println(e);
        }
    }
     public void updateDb()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            PreparedStatement ps=con.prepareStatement("update tbl_timesheet set timeSheetMonth=?,timeSheetYear=?,f1=?,f2=?,f3=?,f4=?,f5=?,f6=?,f7=?,f8=?,f9=?,f10=?,f11=?,f12=?,f13=?,f14=?,f15=?,f16=?,f17=?,f18=?,f19=?,f20=?,f21=?,f22=?,f23=?,f24=?,f25=?,f26=?,f27=?,f28=?,f29=?,f30=?,f31=?,totalNormal=?,toalOT=?,totalHOT=?,normalRatePerHour=?,otRatePerHour=?,hotRatePerHour=?,grossAmountNormal=?,grossAmountOT=?,grossAmountHOT=?,countLeave=?,countAbsent=?,grossAmount=?,foodAddition=?,dueAddition=?,bonusAddition=?,otherAddition=?,absentDeduction=?,advanceDeduction=?,foodDeduction=?,otherDeduction=? where timeSheetId=?");
            ps.setString(1,cmbMonth.getSelectedItem().toString());
            ps.setInt(2,Integer.parseInt(cmbYear.getSelectedItem().toString()));
            ps.setString(3,txtDate1.getText());
            ps.setString(4,txtDate2.getText());
            ps.setString(5,txtDate3.getText());
            ps.setString(6,txtDate4.getText());
            ps.setString(7,txtDate5.getText());
            ps.setString(8,txtDate6.getText());
            ps.setString(9,txtDate7.getText());
            ps.setString(10,txtDate8.getText());
            ps.setString(11,txtDate9.getText());
            ps.setString(12,txtDate10.getText());
            ps.setString(13,txtDate11.getText());
            ps.setString(14,txtDate12.getText());
            ps.setString(15,txtDate13.getText());
            ps.setString(16,txtDate14.getText());
            ps.setString(17,txtDate15.getText());
            ps.setString(18,txtDate16.getText());
            ps.setString(19,txtDate17.getText());
            ps.setString(20,txtDate18.getText());
            ps.setString(21,txtDate19.getText());
            ps.setString(22,txtDate20.getText());
            ps.setString(23,txtDate21.getText());
            ps.setString(24,txtDate22.getText());
            ps.setString(25,txtDate23.getText());
            ps.setString(26,txtDate24.getText());
            ps.setString(27,txtDate25.getText());
            ps.setString(28,txtDate26.getText());
            ps.setString(29,txtDate27.getText());
            ps.setString(30,txtDate28.getText());
            ps.setString(31,txtDate29.getText());
            ps.setString(32,txtDate30.getText());
            ps.setString(33,txtDate31.getText());
            ps.setDouble(34,Double.parseDouble(txtTotalNormal.getText()));
            ps.setDouble(35,Double.parseDouble(txtTotalOT.getText()));
            ps.setDouble(36,Double.parseDouble(txtTotalHot.getText()));
            ps.setDouble(37,Double.parseDouble(txtNormalRatePerHour.getText()));
            ps.setDouble(38,Double.parseDouble(txtOtRatePerHour.getText()));
            ps.setDouble(39,Double.parseDouble(txtHotRatePerHour.getText()));
            ps.setDouble(40,Double.parseDouble(txtGrossAmountNormal.getText()));
            ps.setDouble(41,Double.parseDouble(txtGrossAmountOT.getText()));
            ps.setDouble(42,Double.parseDouble(txtGrossAmountHOT.getText()));
            ps.setInt(43,Integer.parseInt(txtCountLeave.getText()));
            ps.setInt(44,Integer.parseInt(txtCountAbsent.getText()));
            ps.setDouble(45,Double.parseDouble(txtGrossAmount.getText()));
            ps.setDouble(46,Double.parseDouble(txtFoodAddition.getText()));
            ps.setDouble(47,Double.parseDouble(txtDues.getText()));
            ps.setDouble(48,Double.parseDouble(txtBonus.getText()));
            ps.setDouble(49,Double.parseDouble(txtOtherAddition.getText()));
            ps.setDouble(50,Double.parseDouble(txtAbsentDeduction.getText()));
            ps.setDouble(51,Double.parseDouble(txtAdvanceDeduction.getText()));
            ps.setDouble(52,Double.parseDouble(txtFoodDeduction.getText()));
            ps.setDouble(53,Double.parseDouble(txtOtherDeduction.getText()));
            ps.setInt(54, timeSheetId);
            int i=ps.executeUpdate();
            if(i!=0)
            {
               dispose();
               TimeSheet TS = new TimeSheet(empId);
               AnarTrading.desktopPane.add(TS);
               TS.setVisible(true);
               TS.show();
            } 
            con.close();
            
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(rootPane,e+"Error SA001");
            System.out.println(e);
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

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblEmpName = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        cmbYear = new javax.swing.JComboBox();
        cmbMonth = new javax.swing.JComboBox();
        jLabel33 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtableTimeSheet = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        btnSave = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
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
        txtDate22 = new javax.swing.JTextField();
        txtDate23 = new javax.swing.JTextField();
        txtDate24 = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        txtDate25 = new javax.swing.JTextField();
        txtDate26 = new javax.swing.JTextField();
        txtDate27 = new javax.swing.JTextField();
        txtDate28 = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        txtDate29 = new javax.swing.JTextField();
        txtDate31 = new javax.swing.JTextField();
        txtDate30 = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        txtOT1 = new javax.swing.JTextField();
        txtOT2 = new javax.swing.JTextField();
        txtOT3 = new javax.swing.JTextField();
        txtOT4 = new javax.swing.JTextField();
        txtOT5 = new javax.swing.JTextField();
        txtOT6 = new javax.swing.JTextField();
        txtOT7 = new javax.swing.JTextField();
        txtOT8 = new javax.swing.JTextField();
        txtOT9 = new javax.swing.JTextField();
        txtOT10 = new javax.swing.JTextField();
        txtOT11 = new javax.swing.JTextField();
        txtOT12 = new javax.swing.JTextField();
        txtOT13 = new javax.swing.JTextField();
        txtOT14 = new javax.swing.JTextField();
        txtOT15 = new javax.swing.JTextField();
        txtOT16 = new javax.swing.JTextField();
        txtOT17 = new javax.swing.JTextField();
        txtOT18 = new javax.swing.JTextField();
        txtOT19 = new javax.swing.JTextField();
        txtOT20 = new javax.swing.JTextField();
        txtOT21 = new javax.swing.JTextField();
        txtOT22 = new javax.swing.JTextField();
        txtOT23 = new javax.swing.JTextField();
        txtOT24 = new javax.swing.JTextField();
        txtOT25 = new javax.swing.JTextField();
        txtOT26 = new javax.swing.JTextField();
        txtOT27 = new javax.swing.JTextField();
        txtOT28 = new javax.swing.JTextField();
        txtOT29 = new javax.swing.JTextField();
        txtOT30 = new javax.swing.JTextField();
        txtOT31 = new javax.swing.JTextField();
        txtNormalDate1 = new javax.swing.JTextField();
        txtNormalDate2 = new javax.swing.JTextField();
        txtNormalDate3 = new javax.swing.JTextField();
        txtNormalDate4 = new javax.swing.JTextField();
        txtNormalDate5 = new javax.swing.JTextField();
        txtNormalDate6 = new javax.swing.JTextField();
        txtNormalDate7 = new javax.swing.JTextField();
        txtNormalDate8 = new javax.swing.JTextField();
        txtNormalDate9 = new javax.swing.JTextField();
        txtNormalDate10 = new javax.swing.JTextField();
        txtNormalDate11 = new javax.swing.JTextField();
        txtNormalDate12 = new javax.swing.JTextField();
        txtNormalDate13 = new javax.swing.JTextField();
        txtNormalDate14 = new javax.swing.JTextField();
        txtNormalDate15 = new javax.swing.JTextField();
        txtNormalDate16 = new javax.swing.JTextField();
        txtNormalDate17 = new javax.swing.JTextField();
        txtNormalDate18 = new javax.swing.JTextField();
        txtNormalDate19 = new javax.swing.JTextField();
        txtNormalDate20 = new javax.swing.JTextField();
        txtNormalDate21 = new javax.swing.JTextField();
        txtNormalDate22 = new javax.swing.JTextField();
        txtNormalDate23 = new javax.swing.JTextField();
        txtNormalDate24 = new javax.swing.JTextField();
        txtNormalDate25 = new javax.swing.JTextField();
        txtNormalDate26 = new javax.swing.JTextField();
        txtNormalDate27 = new javax.swing.JTextField();
        txtNormalDate28 = new javax.swing.JTextField();
        txtNormalDate29 = new javax.swing.JTextField();
        txtNormalDate30 = new javax.swing.JTextField();
        txtNormalDate31 = new javax.swing.JTextField();
        txtHot1 = new javax.swing.JTextField();
        txtHot2 = new javax.swing.JTextField();
        txtHot3 = new javax.swing.JTextField();
        txtHot4 = new javax.swing.JTextField();
        txtHot5 = new javax.swing.JTextField();
        txtHot6 = new javax.swing.JTextField();
        txtHot7 = new javax.swing.JTextField();
        txtHot8 = new javax.swing.JTextField();
        txtHot9 = new javax.swing.JTextField();
        txtHot10 = new javax.swing.JTextField();
        txtHot11 = new javax.swing.JTextField();
        txtHot12 = new javax.swing.JTextField();
        txtHot13 = new javax.swing.JTextField();
        txtHot14 = new javax.swing.JTextField();
        txtHot15 = new javax.swing.JTextField();
        txtHot16 = new javax.swing.JTextField();
        txtHot17 = new javax.swing.JTextField();
        txtHot18 = new javax.swing.JTextField();
        txtHot19 = new javax.swing.JTextField();
        txtHot20 = new javax.swing.JTextField();
        txtHot21 = new javax.swing.JTextField();
        txtHot22 = new javax.swing.JTextField();
        txtHot23 = new javax.swing.JTextField();
        txtHot24 = new javax.swing.JTextField();
        txtHot25 = new javax.swing.JTextField();
        txtHot26 = new javax.swing.JTextField();
        txtHot27 = new javax.swing.JTextField();
        txtHot28 = new javax.swing.JTextField();
        txtHot29 = new javax.swing.JTextField();
        txtHot30 = new javax.swing.JTextField();
        txtHot31 = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        txtTotalNormal = new javax.swing.JTextField();
        txtTotalOT = new javax.swing.JTextField();
        txtTotalHot = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        txtCountAbsent = new javax.swing.JTextField();
        txtCountLeave = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        txtNormalRatePerHour = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        txtOtRatePerHour = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        txtHotRatePerHour = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        txtGrossAmountNormal = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        txtGrossAmountOT = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        txtGrossAmountHOT = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        txtBasic = new javax.swing.JTextField();
        jLabel53 = new javax.swing.JLabel();
        txtGrossAmount = new javax.swing.JTextField();
        jLabel54 = new javax.swing.JLabel();
        txtFoodAddition = new javax.swing.JTextField();
        jLabel55 = new javax.swing.JLabel();
        txtDues = new javax.swing.JTextField();
        Bonus = new javax.swing.JLabel();
        txtBonus = new javax.swing.JTextField();
        Bonus1 = new javax.swing.JLabel();
        txtOtherAddition = new javax.swing.JTextField();
        jLabel56 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        Bonus4 = new javax.swing.JLabel();
        Bonus5 = new javax.swing.JLabel();
        txtAbsentDeduction = new javax.swing.JTextField();
        txtAdvanceDeduction = new javax.swing.JTextField();
        txtFoodDeduction = new javax.swing.JTextField();
        txtOtherDeduction = new javax.swing.JTextField();
        jLabel57 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel60 = new javax.swing.JLabel();
        txtTotalAddition = new javax.swing.JTextField();
        jLabel61 = new javax.swing.JLabel();
        txtTotalDeduction = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        txtNetAmount = new javax.swing.JTextField();

        setForeground(new java.awt.Color(153, 153, 255));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Employee Details"));

        jLabel1.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        jLabel1.setText("Employee Name");

        lblEmpName.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        lblEmpName.setText("Name");

        jLabel34.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        jLabel34.setText("Year");

        cmbYear.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2014", "2015", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2021", "2022" }));
        cmbYear.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbYearItemStateChanged(evt);
            }
        });
        cmbYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbYearActionPerformed(evt);
            }
        });

        cmbMonth.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        cmbMonth.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));
        cmbMonth.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbMonthItemStateChanged(evt);
            }
        });
        cmbMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbMonthActionPerformed(evt);
            }
        });

        jLabel33.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        jLabel33.setText("Month");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(23, 23, 23)
                .addComponent(lblEmpName, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(360, 360, 360)
                .addComponent(jLabel33)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cmbMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel34)
                .addGap(80, 80, 80)
                .addComponent(cmbYear, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(lblEmpName, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmbMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel33))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel34)
                        .addComponent(cmbYear, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Time Sheet", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Gabriola", 0, 18))); // NOI18N

        jtableTimeSheet.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jtableTimeSheet);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Controls", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Gabriola", 0, 18))); // NOI18N

        btnSave.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/SAVE.PNG"))); // NOI18N
        btnSave.setText("Save");
        btnSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnUpdate.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/MODIFY.PNG"))); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/DELETE.PNG"))); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnCancel.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/CANCEL.PNG"))); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnRefresh.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
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
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRefresh)
                    .addComponent(btnDelete)
                    .addComponent(btnCancel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Time Sheet Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Gabriola", 0, 18))); // NOI18N
        jPanel1.setToolTipText("");

        txtDate1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
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

        txtDate2.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
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

        txtDate3.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
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

        txtDate4.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
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

        txtDate5.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
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

        txtDate6.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
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

        txtDate7.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
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

        txtDate8.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
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

        txtDate9.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
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

        txtDate10.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
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

        txtDate11.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
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

        txtDate12.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
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

        txtDate13.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
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

        txtDate14.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
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

        txtDate15.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
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

        txtDate16.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
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

        txtDate17.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
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

        txtDate18.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
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

        txtDate19.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
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

        txtDate20.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
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

        txtDate21.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
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

        jLabel2.setText("1");

        jLabel3.setText("2");

        jLabel4.setText("3");

        jLabel5.setText("4");

        jLabel6.setText("5");

        jLabel7.setText("6");

        jLabel8.setText("7");

        jLabel9.setText("8");

        jLabel10.setText("9");

        jLabel11.setText("10");

        jLabel12.setText("11");

        jLabel13.setText("12");

        jLabel14.setText("13");

        jLabel15.setText("14");

        jLabel16.setText("15");

        jLabel17.setText("16");

        jLabel18.setText("17");

        jLabel19.setText("18");

        jLabel20.setText("19");

        jLabel21.setText("20");

        jLabel22.setText("21");

        txtDate22.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
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

        txtDate23.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
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

        txtDate24.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
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

        jLabel23.setText("22");

        jLabel24.setText("23");

        jLabel25.setText("24");

        txtDate25.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
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

        txtDate26.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
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

        txtDate27.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
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

        txtDate28.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
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

        jLabel26.setText("25");

        jLabel27.setText("26");

        jLabel28.setText("27");

        jLabel29.setText("28");

        jLabel30.setText("29");

        jLabel31.setText("30");

        txtDate29.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
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

        txtDate31.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
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

        txtDate30.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
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

        jLabel32.setText("31");

        txtOT1.setEditable(false);
        txtOT1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtOT1.setMinimumSize(new java.awt.Dimension(6, 25));
        txtOT1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtOT1KeyPressed(evt);
            }
        });

        txtOT2.setEditable(false);
        txtOT2.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtOT2.setMinimumSize(new java.awt.Dimension(6, 25));
        txtOT2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtOT2KeyPressed(evt);
            }
        });

        txtOT3.setEditable(false);
        txtOT3.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtOT3.setMinimumSize(new java.awt.Dimension(6, 25));
        txtOT3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtOT3KeyPressed(evt);
            }
        });

        txtOT4.setEditable(false);
        txtOT4.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtOT4.setMinimumSize(new java.awt.Dimension(6, 25));

        txtOT5.setEditable(false);
        txtOT5.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtOT5.setMinimumSize(new java.awt.Dimension(6, 25));

        txtOT6.setEditable(false);
        txtOT6.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtOT6.setMinimumSize(new java.awt.Dimension(6, 25));

        txtOT7.setEditable(false);
        txtOT7.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtOT7.setMinimumSize(new java.awt.Dimension(6, 25));

        txtOT8.setEditable(false);
        txtOT8.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtOT8.setMinimumSize(new java.awt.Dimension(6, 25));

        txtOT9.setEditable(false);
        txtOT9.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtOT9.setMinimumSize(new java.awt.Dimension(6, 25));

        txtOT10.setEditable(false);
        txtOT10.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtOT10.setMinimumSize(new java.awt.Dimension(6, 25));

        txtOT11.setEditable(false);
        txtOT11.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtOT11.setMinimumSize(new java.awt.Dimension(6, 25));

        txtOT12.setEditable(false);
        txtOT12.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtOT12.setMinimumSize(new java.awt.Dimension(6, 25));

        txtOT13.setEditable(false);
        txtOT13.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtOT13.setMinimumSize(new java.awt.Dimension(6, 25));

        txtOT14.setEditable(false);
        txtOT14.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtOT14.setMinimumSize(new java.awt.Dimension(6, 25));

        txtOT15.setEditable(false);
        txtOT15.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtOT15.setMinimumSize(new java.awt.Dimension(6, 25));

        txtOT16.setEditable(false);
        txtOT16.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtOT16.setMinimumSize(new java.awt.Dimension(6, 25));

        txtOT17.setEditable(false);
        txtOT17.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtOT17.setMinimumSize(new java.awt.Dimension(6, 25));

        txtOT18.setEditable(false);
        txtOT18.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtOT18.setMinimumSize(new java.awt.Dimension(6, 25));

        txtOT19.setEditable(false);
        txtOT19.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtOT19.setMinimumSize(new java.awt.Dimension(6, 25));

        txtOT20.setEditable(false);
        txtOT20.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtOT20.setMinimumSize(new java.awt.Dimension(6, 25));

        txtOT21.setEditable(false);
        txtOT21.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtOT21.setMinimumSize(new java.awt.Dimension(6, 25));

        txtOT22.setEditable(false);
        txtOT22.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtOT22.setMinimumSize(new java.awt.Dimension(6, 25));

        txtOT23.setEditable(false);
        txtOT23.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtOT23.setMinimumSize(new java.awt.Dimension(6, 25));

        txtOT24.setEditable(false);
        txtOT24.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtOT24.setMinimumSize(new java.awt.Dimension(6, 25));

        txtOT25.setEditable(false);
        txtOT25.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtOT25.setMinimumSize(new java.awt.Dimension(6, 25));

        txtOT26.setEditable(false);
        txtOT26.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtOT26.setMinimumSize(new java.awt.Dimension(6, 25));

        txtOT27.setEditable(false);
        txtOT27.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtOT27.setMinimumSize(new java.awt.Dimension(6, 25));

        txtOT28.setEditable(false);
        txtOT28.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtOT28.setMinimumSize(new java.awt.Dimension(6, 25));

        txtOT29.setEditable(false);
        txtOT29.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtOT29.setMinimumSize(new java.awt.Dimension(6, 25));

        txtOT30.setEditable(false);
        txtOT30.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtOT30.setMinimumSize(new java.awt.Dimension(6, 25));

        txtOT31.setEditable(false);
        txtOT31.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtOT31.setMinimumSize(new java.awt.Dimension(6, 25));

        txtNormalDate1.setEditable(false);
        txtNormalDate1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtNormalDate1.setMinimumSize(new java.awt.Dimension(6, 25));
        txtNormalDate1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNormalDate1KeyPressed(evt);
            }
        });

        txtNormalDate2.setEditable(false);
        txtNormalDate2.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtNormalDate2.setMinimumSize(new java.awt.Dimension(6, 25));
        txtNormalDate2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNormalDate2KeyPressed(evt);
            }
        });

        txtNormalDate3.setEditable(false);
        txtNormalDate3.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtNormalDate3.setMinimumSize(new java.awt.Dimension(6, 25));
        txtNormalDate3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNormalDate3KeyPressed(evt);
            }
        });

        txtNormalDate4.setEditable(false);
        txtNormalDate4.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtNormalDate4.setMinimumSize(new java.awt.Dimension(6, 25));

        txtNormalDate5.setEditable(false);
        txtNormalDate5.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtNormalDate5.setMinimumSize(new java.awt.Dimension(6, 25));

        txtNormalDate6.setEditable(false);
        txtNormalDate6.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtNormalDate6.setMinimumSize(new java.awt.Dimension(6, 25));

        txtNormalDate7.setEditable(false);
        txtNormalDate7.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtNormalDate7.setMinimumSize(new java.awt.Dimension(6, 25));

        txtNormalDate8.setEditable(false);
        txtNormalDate8.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtNormalDate8.setMinimumSize(new java.awt.Dimension(6, 25));

        txtNormalDate9.setEditable(false);
        txtNormalDate9.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtNormalDate9.setMinimumSize(new java.awt.Dimension(6, 25));

        txtNormalDate10.setEditable(false);
        txtNormalDate10.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtNormalDate10.setMinimumSize(new java.awt.Dimension(6, 25));

        txtNormalDate11.setEditable(false);
        txtNormalDate11.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtNormalDate11.setMinimumSize(new java.awt.Dimension(6, 25));

        txtNormalDate12.setEditable(false);
        txtNormalDate12.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtNormalDate12.setMinimumSize(new java.awt.Dimension(6, 25));

        txtNormalDate13.setEditable(false);
        txtNormalDate13.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtNormalDate13.setMinimumSize(new java.awt.Dimension(6, 25));

        txtNormalDate14.setEditable(false);
        txtNormalDate14.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtNormalDate14.setMinimumSize(new java.awt.Dimension(6, 25));

        txtNormalDate15.setEditable(false);
        txtNormalDate15.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtNormalDate15.setMinimumSize(new java.awt.Dimension(6, 25));

        txtNormalDate16.setEditable(false);
        txtNormalDate16.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtNormalDate16.setMinimumSize(new java.awt.Dimension(6, 25));

        txtNormalDate17.setEditable(false);
        txtNormalDate17.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtNormalDate17.setMinimumSize(new java.awt.Dimension(6, 25));

        txtNormalDate18.setEditable(false);
        txtNormalDate18.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtNormalDate18.setMinimumSize(new java.awt.Dimension(6, 25));

        txtNormalDate19.setEditable(false);
        txtNormalDate19.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtNormalDate19.setMinimumSize(new java.awt.Dimension(6, 25));

        txtNormalDate20.setEditable(false);
        txtNormalDate20.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtNormalDate20.setMinimumSize(new java.awt.Dimension(6, 25));

        txtNormalDate21.setEditable(false);
        txtNormalDate21.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtNormalDate21.setMinimumSize(new java.awt.Dimension(6, 25));

        txtNormalDate22.setEditable(false);
        txtNormalDate22.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtNormalDate22.setMinimumSize(new java.awt.Dimension(6, 25));

        txtNormalDate23.setEditable(false);
        txtNormalDate23.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtNormalDate23.setMinimumSize(new java.awt.Dimension(6, 25));

        txtNormalDate24.setEditable(false);
        txtNormalDate24.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtNormalDate24.setMinimumSize(new java.awt.Dimension(6, 25));

        txtNormalDate25.setEditable(false);
        txtNormalDate25.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtNormalDate25.setMinimumSize(new java.awt.Dimension(6, 25));

        txtNormalDate26.setEditable(false);
        txtNormalDate26.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtNormalDate26.setMinimumSize(new java.awt.Dimension(6, 25));

        txtNormalDate27.setEditable(false);
        txtNormalDate27.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtNormalDate27.setMinimumSize(new java.awt.Dimension(6, 25));

        txtNormalDate28.setEditable(false);
        txtNormalDate28.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtNormalDate28.setMinimumSize(new java.awt.Dimension(6, 25));

        txtNormalDate29.setEditable(false);
        txtNormalDate29.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtNormalDate29.setMinimumSize(new java.awt.Dimension(6, 25));

        txtNormalDate30.setEditable(false);
        txtNormalDate30.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtNormalDate30.setMinimumSize(new java.awt.Dimension(6, 25));

        txtNormalDate31.setEditable(false);
        txtNormalDate31.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtNormalDate31.setMinimumSize(new java.awt.Dimension(6, 25));

        txtHot1.setEditable(false);
        txtHot1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtHot1.setMinimumSize(new java.awt.Dimension(6, 25));
        txtHot1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtHot1KeyPressed(evt);
            }
        });

        txtHot2.setEditable(false);
        txtHot2.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtHot2.setMinimumSize(new java.awt.Dimension(6, 25));
        txtHot2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtHot2KeyPressed(evt);
            }
        });

        txtHot3.setEditable(false);
        txtHot3.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtHot3.setMinimumSize(new java.awt.Dimension(6, 25));
        txtHot3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtHot3KeyPressed(evt);
            }
        });

        txtHot4.setEditable(false);
        txtHot4.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtHot4.setMinimumSize(new java.awt.Dimension(6, 25));

        txtHot5.setEditable(false);
        txtHot5.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtHot5.setMinimumSize(new java.awt.Dimension(6, 25));

        txtHot6.setEditable(false);
        txtHot6.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtHot6.setMinimumSize(new java.awt.Dimension(6, 25));

        txtHot7.setEditable(false);
        txtHot7.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtHot7.setMinimumSize(new java.awt.Dimension(6, 25));

        txtHot8.setEditable(false);
        txtHot8.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtHot8.setMinimumSize(new java.awt.Dimension(6, 25));

        txtHot9.setEditable(false);
        txtHot9.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtHot9.setMinimumSize(new java.awt.Dimension(6, 25));

        txtHot10.setEditable(false);
        txtHot10.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtHot10.setMinimumSize(new java.awt.Dimension(6, 25));

        txtHot11.setEditable(false);
        txtHot11.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtHot11.setMinimumSize(new java.awt.Dimension(6, 25));

        txtHot12.setEditable(false);
        txtHot12.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtHot12.setMinimumSize(new java.awt.Dimension(6, 25));

        txtHot13.setEditable(false);
        txtHot13.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtHot13.setMinimumSize(new java.awt.Dimension(6, 25));

        txtHot14.setEditable(false);
        txtHot14.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtHot14.setMinimumSize(new java.awt.Dimension(6, 25));

        txtHot15.setEditable(false);
        txtHot15.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtHot15.setMinimumSize(new java.awt.Dimension(6, 25));

        txtHot16.setEditable(false);
        txtHot16.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtHot16.setMinimumSize(new java.awt.Dimension(6, 25));

        txtHot17.setEditable(false);
        txtHot17.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtHot17.setMinimumSize(new java.awt.Dimension(6, 25));

        txtHot18.setEditable(false);
        txtHot18.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtHot18.setMinimumSize(new java.awt.Dimension(6, 25));

        txtHot19.setEditable(false);
        txtHot19.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtHot19.setMinimumSize(new java.awt.Dimension(6, 25));

        txtHot20.setEditable(false);
        txtHot20.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtHot20.setMinimumSize(new java.awt.Dimension(6, 25));

        txtHot21.setEditable(false);
        txtHot21.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtHot21.setMinimumSize(new java.awt.Dimension(6, 25));

        txtHot22.setEditable(false);
        txtHot22.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtHot22.setMinimumSize(new java.awt.Dimension(6, 25));

        txtHot23.setEditable(false);
        txtHot23.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtHot23.setMinimumSize(new java.awt.Dimension(6, 25));

        txtHot24.setEditable(false);
        txtHot24.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtHot24.setMinimumSize(new java.awt.Dimension(6, 25));

        txtHot25.setEditable(false);
        txtHot25.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtHot25.setMinimumSize(new java.awt.Dimension(6, 25));

        txtHot26.setEditable(false);
        txtHot26.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtHot26.setMinimumSize(new java.awt.Dimension(6, 25));

        txtHot27.setEditable(false);
        txtHot27.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtHot27.setMinimumSize(new java.awt.Dimension(6, 25));

        txtHot28.setEditable(false);
        txtHot28.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtHot28.setMinimumSize(new java.awt.Dimension(6, 25));

        txtHot29.setEditable(false);
        txtHot29.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtHot29.setMinimumSize(new java.awt.Dimension(6, 25));

        txtHot30.setEditable(false);
        txtHot30.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtHot30.setMinimumSize(new java.awt.Dimension(6, 25));

        txtHot31.setEditable(false);
        txtHot31.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtHot31.setMinimumSize(new java.awt.Dimension(6, 25));

        jLabel37.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        jLabel37.setText("OT.Hrs");

        jLabel38.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        jLabel38.setText("HOT.hrs");

        jLabel39.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        jLabel39.setText("N.Hrs");

        jLabel40.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        jLabel40.setText("Hrs.");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel39)
                            .addComponent(jLabel37)
                            .addComponent(jLabel40))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel38)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtOT1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtOT2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtOT3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtOT4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtOT5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtOT6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtOT7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtOT8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtOT9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtOT10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtOT11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtOT12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtOT13, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtOT14, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtOT15, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtOT16, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtOT17, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtOT18, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtOT19, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtOT20, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtOT21, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtOT22, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtOT23, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtOT24, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtOT25, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtOT26, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtOT27, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtOT28, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtOT29, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtOT30, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtOT31, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtNormalDate1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDate1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtDate2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDate3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDate4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDate5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDate6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDate7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDate8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDate9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDate10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDate11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDate12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDate13, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDate14, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDate15, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDate16, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDate17, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDate18, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDate19, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDate20, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDate21, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDate22, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDate23, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDate24, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDate25, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDate26, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDate27, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDate28, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDate29, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDate30, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDate31, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtNormalDate2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNormalDate3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNormalDate4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNormalDate5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNormalDate6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNormalDate7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNormalDate8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNormalDate9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNormalDate10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNormalDate11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNormalDate12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNormalDate13, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNormalDate14, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNormalDate15, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNormalDate16, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNormalDate17, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNormalDate18, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNormalDate19, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNormalDate20, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNormalDate21, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNormalDate22, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNormalDate23, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNormalDate24, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNormalDate25, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNormalDate26, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNormalDate27, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNormalDate28, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNormalDate29, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNormalDate30, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNormalDate31, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtHot1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHot2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHot3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHot4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHot5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHot6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHot7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHot8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHot9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHot10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHot11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHot12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHot13, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHot14, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHot15, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHot16, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHot17, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHot18, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHot19, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHot20, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHot21, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHot22, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHot23, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHot24, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHot25, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHot26, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHot27, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHot28, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHot29, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHot30, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHot31, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDate1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                    .addComponent(txtDate31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel40))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNormalDate2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNormalDate3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNormalDate4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNormalDate5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNormalDate6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNormalDate7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNormalDate8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNormalDate9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNormalDate10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNormalDate11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNormalDate12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNormalDate13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNormalDate14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNormalDate15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNormalDate16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNormalDate17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNormalDate18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNormalDate19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNormalDate20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNormalDate21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNormalDate23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNormalDate24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNormalDate22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNormalDate26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNormalDate27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNormalDate28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNormalDate25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNormalDate29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNormalDate30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNormalDate31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNormalDate1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel39))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtOT1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtOT2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtOT3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtOT4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtOT5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtOT6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtOT7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtOT8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtOT9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtOT10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtOT11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtOT12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtOT13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtOT14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtOT15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtOT16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtOT17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtOT18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtOT19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtOT20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtOT21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtOT23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtOT24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtOT22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtOT26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtOT27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtOT28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtOT25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtOT29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtOT30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtOT31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel37))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtHot1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtHot2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(31, 31, 31))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtHot3, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                        .addComponent(txtHot4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtHot5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtHot6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtHot7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtHot8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtHot9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtHot10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtHot11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtHot12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtHot13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtHot14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtHot15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtHot16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtHot17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtHot18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtHot19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtHot20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtHot21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtHot23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtHot24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtHot22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtHot26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtHot27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtHot28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtHot25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtHot29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtHot30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtHot31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel38))))
        );

        txtDate1.getAccessibleContext().setAccessibleDescription("");
        txtDate2.getAccessibleContext().setAccessibleDescription("");
        txtDate3.getAccessibleContext().setAccessibleDescription("");
        txtDate4.getAccessibleContext().setAccessibleDescription("");
        txtDate5.getAccessibleContext().setAccessibleDescription("");
        txtDate6.getAccessibleContext().setAccessibleDescription("");
        txtDate7.getAccessibleContext().setAccessibleDescription("");
        jLabel3.getAccessibleContext().setAccessibleName("");
        jLabel4.getAccessibleContext().setAccessibleName("");
        jLabel5.getAccessibleContext().setAccessibleName("");
        jLabel6.getAccessibleContext().setAccessibleName("");
        jLabel7.getAccessibleContext().setAccessibleName("");
        jLabel8.getAccessibleContext().setAccessibleName("");
        jLabel9.getAccessibleContext().setAccessibleName("");
        jLabel10.getAccessibleContext().setAccessibleName("");
        jLabel11.getAccessibleContext().setAccessibleName("");
        jLabel12.getAccessibleContext().setAccessibleName("");
        jLabel23.getAccessibleContext().setAccessibleName("");
        jLabel24.getAccessibleContext().setAccessibleName("30");
        jLabel25.getAccessibleContext().setAccessibleName("30");
        jLabel26.getAccessibleContext().setAccessibleName("30");
        jLabel27.getAccessibleContext().setAccessibleName("30");
        jLabel28.getAccessibleContext().setAccessibleName("30");
        jLabel29.getAccessibleContext().setAccessibleName("30");

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Calculations", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Gabriola", 0, 18))); // NOI18N

        jLabel41.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        jLabel41.setText("Total OT Hrs.");

        jLabel42.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        jLabel42.setText("Total Normal Hrs.");

        jLabel43.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        jLabel43.setText("Total HOT Hrs.");

        txtTotalNormal.setEditable(false);

        txtTotalOT.setEditable(false);

        txtTotalHot.setEditable(false);

        jLabel44.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        jLabel44.setText("Total No.Leave");

        jLabel45.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        jLabel45.setText("Total No.Absent");

        txtCountAbsent.setEditable(false);

        txtCountLeave.setEditable(false);

        jLabel46.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        jLabel46.setText("Normal Rate/Hr");

        txtNormalRatePerHour.setEditable(false);

        jLabel47.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        jLabel47.setText("OT Rate/Hr");

        txtOtRatePerHour.setEditable(false);

        jLabel48.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        jLabel48.setText("HOT Rate/Hr");

        txtHotRatePerHour.setEditable(false);

        jLabel49.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        jLabel49.setText("Gross Amount Normal");

        txtGrossAmountNormal.setEditable(false);

        jLabel50.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        jLabel50.setText("Gross Amount OT");

        txtGrossAmountOT.setEditable(false);

        jLabel51.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        jLabel51.setText("Gross Amount HOT");

        txtGrossAmountHOT.setEditable(false);

        jLabel52.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        jLabel52.setText("Basic Salary");

        txtBasic.setEditable(false);

        jLabel53.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        jLabel53.setText("Gross Amount ");

        txtGrossAmount.setEditable(false);

        jLabel54.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        jLabel54.setText("Food");

        txtFoodAddition.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFoodAdditionFocusLost(evt);
            }
        });

        jLabel55.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        jLabel55.setText("Dues");

        txtDues.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDuesFocusLost(evt);
            }
        });

        Bonus.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        Bonus.setText("Bonus");

        txtBonus.setEditable(false);

        Bonus1.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        Bonus1.setText("Other");

        txtOtherAddition.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtOtherAdditionFocusLost(evt);
            }
        });

        jLabel56.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        jLabel56.setText("Absent");

        jLabel58.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        jLabel58.setText("Advance");

        Bonus4.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        Bonus4.setText("Food");

        Bonus5.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        Bonus5.setText("Other");

        txtAbsentDeduction.setEditable(false);

        txtAdvanceDeduction.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtAdvanceDeductionFocusLost(evt);
            }
        });

        txtFoodDeduction.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFoodDeductionFocusLost(evt);
            }
        });

        txtOtherDeduction.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtOtherDeductionFocusLost(evt);
            }
        });

        jLabel57.setFont(new java.awt.Font("Gabriola", 1, 24)); // NOI18N
        jLabel57.setText("Additions");

        jLabel59.setFont(new java.awt.Font("Gabriola", 1, 24)); // NOI18N
        jLabel59.setText("Deductions");

        jLabel60.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        jLabel60.setText("Total Addition");

        txtTotalAddition.setEditable(false);

        jLabel61.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        jLabel61.setText("Total Deduction");

        txtTotalDeduction.setEditable(false);

        jLabel35.setFont(new java.awt.Font("Gabriola", 1, 24)); // NOI18N
        jLabel35.setText("NET AMOUNT");

        txtNetAmount.setBackground(new java.awt.Color(102, 204, 255));
        txtNetAmount.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel54)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtFoodAddition, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel55)
                                .addComponent(Bonus)
                                .addComponent(Bonus1))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtDues, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtBonus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtOtherAddition, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel42)
                                .addComponent(jLabel41)
                                .addComponent(jLabel43))
                            .addGap(4, 4, 4)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtTotalNormal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
                                .addComponent(txtTotalHot)
                                .addComponent(txtTotalOT, javax.swing.GroupLayout.Alignment.TRAILING))))
                    .addComponent(jLabel57))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel46)
                            .addComponent(jLabel47)
                            .addComponent(jLabel48)
                            .addComponent(jLabel58)
                            .addComponent(jLabel56)
                            .addComponent(Bonus4)
                            .addComponent(Bonus5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtOtRatePerHour, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
                                    .addComponent(txtNormalRatePerHour, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
                                    .addComponent(txtHotRatePerHour))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel50)
                                    .addComponent(jLabel49)
                                    .addComponent(jLabel51))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(txtGrossAmountNormal, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel44))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtGrossAmountOT, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtGrossAmountHOT, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel52)
                                            .addComponent(jLabel45))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCountAbsent, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCountLeave, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtBasic, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(348, 348, 348))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtAbsentDeduction, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtAdvanceDeduction, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtFoodDeduction, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtOtherDeduction, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(28, 28, 28)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel60)
                                    .addComponent(jLabel61)
                                    .addComponent(jLabel53))
                                .addGap(48, 48, 48)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtGrossAmount)
                                    .addComponent(txtTotalAddition, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
                                    .addComponent(txtTotalDeduction, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel35)
                                .addGap(18, 18, 18)
                                .addComponent(txtNetAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel59)
                        .addGap(28, 28, 28))))
            .addComponent(jSeparator2)
            .addComponent(jSeparator1)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtGrossAmountNormal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCountLeave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCountAbsent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTotalNormal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNormalRatePerHour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTotalOT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtOtRatePerHour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtGrossAmountOT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(6, 6, 6)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtTotalHot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtHotRatePerHour, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel51, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtGrossAmountHOT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(txtBasic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(24, 24, 24)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtFoodAddition, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtDues)
                                    .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtBonus)
                                    .addComponent(Bonus, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Bonus4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtOtherAddition)
                                    .addComponent(Bonus1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Bonus5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtAbsentDeduction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtGrossAmount)
                                    .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtAdvanceDeduction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTotalAddition, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtFoodDeduction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel61, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTotalDeduction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtOtherDeduction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNetAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel35))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbMonthItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbMonthItemStateChanged
        // TODO add your handling code here:
          SetFriday();
          findHoliday();
          advancedDeduction();
          
          //fillEntireTextBox();
    }//GEN-LAST:event_cmbMonthItemStateChanged

    private void cmbMonthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbMonthActionPerformed
        // TODO add your handling code here:
        //SetFriday();
        //fillEntireTextBox();
    }//GEN-LAST:event_cmbMonthActionPerformed

    private void cmbYearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbYearActionPerformed
        // TODO add your handling code here:
        //SetFriday();
        //fillEntireTextBox();
    }//GEN-LAST:event_cmbYearActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        
        insertIntoDb();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        updateDb();
        
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        
        
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // TODO add your handling code here:
        dispose();
        TimeSheet TS = new TimeSheet(empId);
        AnarTrading.desktopPane.add(TS);
        TS.setVisible(true);
        TS.show();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void txtDate1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate1KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate1, evt);
    }//GEN-LAST:event_txtDate1KeyPressed

    private void txtDate2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate2KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate2, evt);
    }//GEN-LAST:event_txtDate2KeyPressed

    private void txtDate3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate3KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate3, evt);
    }//GEN-LAST:event_txtDate3KeyPressed

    private void txtOT1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtOT1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOT1KeyPressed

    private void txtOT2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtOT2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOT2KeyPressed

    private void txtOT3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtOT3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOT3KeyPressed

    private void txtDate1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate1FocusLost
        // TODO add your handling code here:
        fillTextFields(txtDate1,txtOT1,txtNormalDate1,txtHot1);
    }//GEN-LAST:event_txtDate1FocusLost

    private void txtDate31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDate31ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDate31ActionPerformed

    private void txtNormalDate1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNormalDate1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNormalDate1KeyPressed

    private void txtNormalDate2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNormalDate2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNormalDate2KeyPressed

    private void txtNormalDate3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNormalDate3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNormalDate3KeyPressed

    private void txtDate2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate2FocusLost
        // TODO add your handling code here:
        fillTextFields(txtDate2,txtOT2,txtNormalDate2,txtHot2);
    }//GEN-LAST:event_txtDate2FocusLost

    private void txtDate3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDate3ActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtDate3ActionPerformed

    private void txtDate3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate3FocusLost
        // TODO add your handling code here:
        fillTextFields(txtDate3,txtOT3,txtNormalDate3,txtHot3);
    }//GEN-LAST:event_txtDate3FocusLost

    private void txtDate4FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate4FocusLost
        // TODO add your handling code here:
        fillTextFields(txtDate4,txtOT4,txtNormalDate4,txtHot4);
    }//GEN-LAST:event_txtDate4FocusLost

    private void txtDate5FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate5FocusLost
        // TODO add your handling code here:
        fillTextFields(txtDate5,txtOT5,txtNormalDate5,txtHot5);
    }//GEN-LAST:event_txtDate5FocusLost

    private void txtHot1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHot1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHot1KeyPressed

    private void txtHot2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHot2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHot2KeyPressed

    private void txtHot3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHot3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHot3KeyPressed

    private void txtDate6FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate6FocusLost
        // TODO add your handling code here:
        fillTextFields(txtDate6,txtOT6,txtNormalDate6,txtHot6);
        totalNormalHrs();
        totalOTHrs();
        totalHOTHrs();
    }//GEN-LAST:event_txtDate6FocusLost

    private void txtDate7FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate7FocusLost
        // TODO add your handling code here:
        fillTextFields(txtDate7,txtOT7,txtNormalDate7,txtHot7);
    }//GEN-LAST:event_txtDate7FocusLost

    private void txtDate8FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate8FocusLost
        // TODO add your handling code here:
        fillTextFields(txtDate8,txtOT8,txtNormalDate8,txtHot8);
    }//GEN-LAST:event_txtDate8FocusLost

    private void txtDate9FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate9FocusLost
        // TODO add your handling code here:
        fillTextFields(txtDate9,txtOT9,txtNormalDate9,txtHot9);
    }//GEN-LAST:event_txtDate9FocusLost

    private void txtDate10FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate10FocusLost
        // TODO add your handling code here:
        fillTextFields(txtDate10,txtOT10,txtNormalDate10,txtHot10);
    }//GEN-LAST:event_txtDate10FocusLost

    private void txtDate11FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate11FocusLost
        // TODO add your handling code here:
        fillTextFields(txtDate11,txtOT11,txtNormalDate11,txtHot11);
    }//GEN-LAST:event_txtDate11FocusLost

    private void txtDate12FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate12FocusLost
        // TODO add your handling code here:
        fillTextFields(txtDate12,txtOT12,txtNormalDate12,txtHot12);
    }//GEN-LAST:event_txtDate12FocusLost

    private void txtDate13FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate13FocusLost
        // TODO add your handling code here:
        fillTextFields(txtDate13,txtOT13,txtNormalDate13,txtHot13);
    }//GEN-LAST:event_txtDate13FocusLost

    private void txtDate14FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate14FocusLost
        // TODO add your handling code here:
        fillTextFields(txtDate14,txtOT14,txtNormalDate14,txtHot14);
    }//GEN-LAST:event_txtDate14FocusLost

    private void txtDate15FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate15FocusLost
        // TODO add your handling code here:
        fillTextFields(txtDate15,txtOT15,txtNormalDate15,txtHot15);
    }//GEN-LAST:event_txtDate15FocusLost

    private void txtDate16FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate16FocusLost
        // TODO add your handling code here:
        fillTextFields(txtDate16,txtOT16,txtNormalDate16,txtHot16);
    }//GEN-LAST:event_txtDate16FocusLost

    private void txtDate17FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate17FocusLost
        // TODO add your handling code here:
        fillTextFields(txtDate17,txtOT17,txtNormalDate17,txtHot17);
    }//GEN-LAST:event_txtDate17FocusLost

    private void txtDate18FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate18FocusLost
        // TODO add your handling code here:
        fillTextFields(txtDate18,txtOT18,txtNormalDate18,txtHot18);
    }//GEN-LAST:event_txtDate18FocusLost

    private void txtDate19FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate19FocusLost
        // TODO add your handling code here:
        fillTextFields(txtDate19,txtOT19,txtNormalDate19,txtHot19);
    }//GEN-LAST:event_txtDate19FocusLost

    private void txtDate20FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate20FocusLost
        // TODO add your handling code here:
        fillTextFields(txtDate20,txtOT20,txtNormalDate20,txtHot20);
    }//GEN-LAST:event_txtDate20FocusLost

    private void txtDate21FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate21FocusLost
        // TODO add your handling code here:
        fillTextFields(txtDate21,txtOT21,txtNormalDate21,txtHot21);
    }//GEN-LAST:event_txtDate21FocusLost

    private void txtDate22FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate22FocusLost
        // TODO add your handling code here:
        fillTextFields(txtDate22,txtOT22,txtNormalDate22,txtHot22);
    }//GEN-LAST:event_txtDate22FocusLost

    private void txtDate23FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate23FocusLost
        // TODO add your handling code here:
        fillTextFields(txtDate23,txtOT23,txtNormalDate23,txtHot23);
    }//GEN-LAST:event_txtDate23FocusLost

    private void txtDate24FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate24FocusLost
        // TODO add your handling code here:
        fillTextFields(txtDate24,txtOT24,txtNormalDate24,txtHot24);
    }//GEN-LAST:event_txtDate24FocusLost

    private void txtDate25FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate25FocusLost
        // TODO add your handling code here:
        fillTextFields(txtDate25,txtOT25,txtNormalDate25,txtHot25);
    }//GEN-LAST:event_txtDate25FocusLost

    private void txtDate26FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate26FocusLost
        // TODO add your handling code here:
        fillTextFields(txtDate26,txtOT26,txtNormalDate26,txtHot26);
    }//GEN-LAST:event_txtDate26FocusLost

    private void txtDate26FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate26FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDate26FocusGained

    private void txtDate27FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate27FocusLost
        // TODO add your handling code here:
        fillTextFields(txtDate27,txtOT27,txtNormalDate27,txtHot27);
    }//GEN-LAST:event_txtDate27FocusLost

    private void txtDate28FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate28FocusLost
        // TODO add your handling code here:
        fillTextFields(txtDate28,txtOT28,txtNormalDate28,txtHot28);
    }//GEN-LAST:event_txtDate28FocusLost

    private void txtDate29FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate29FocusLost
        // TODO add your handling code here:
        fillTextFields(txtDate29,txtOT29,txtNormalDate29,txtHot29);
    }//GEN-LAST:event_txtDate29FocusLost

    private void txtDate30FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate30FocusLost
        // TODO add your handling code here:
        fillTextFields(txtDate30,txtOT30,txtNormalDate30,txtHot30);
    }//GEN-LAST:event_txtDate30FocusLost

    private void txtDate31FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDate31FocusLost
        // TODO add your handling code here:
        fillTextFields(txtDate31,txtOT31,txtNormalDate31,txtHot31);
    }//GEN-LAST:event_txtDate31FocusLost

    private void cmbYearItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbYearItemStateChanged
        // TODO add your handling code here:
          SetFriday();
          findHoliday();
          advancedDeduction();
          //fillEntireTextBox();
    }//GEN-LAST:event_cmbYearItemStateChanged

    private void txtFoodAdditionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFoodAdditionFocusLost
        // TODO add your handling code here:
        salaryCalculation();
    }//GEN-LAST:event_txtFoodAdditionFocusLost

    private void txtDuesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDuesFocusLost
        // TODO add your handling code here:
        salaryCalculation();
    }//GEN-LAST:event_txtDuesFocusLost

    private void txtOtherAdditionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtOtherAdditionFocusLost
        // TODO add your handling code here:
        salaryCalculation();
    }//GEN-LAST:event_txtOtherAdditionFocusLost

    private void txtAdvanceDeductionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAdvanceDeductionFocusLost
        // TODO add your handling code here:
        salaryCalculation();
    }//GEN-LAST:event_txtAdvanceDeductionFocusLost

    private void txtFoodDeductionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFoodDeductionFocusLost
        // TODO add your handling code here:
        salaryCalculation();
    }//GEN-LAST:event_txtFoodDeductionFocusLost

    private void txtOtherDeductionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtOtherDeductionFocusLost
        // TODO add your handling code here:
        salaryCalculation();
    }//GEN-LAST:event_txtOtherDeductionFocusLost

    private void txtDate4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate4KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate4, evt);
    }//GEN-LAST:event_txtDate4KeyPressed

    private void txtDate5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate5KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate5, evt);
    }//GEN-LAST:event_txtDate5KeyPressed

    private void txtDate6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate6KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate6, evt);
    }//GEN-LAST:event_txtDate6KeyPressed

    private void txtDate7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate7KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate7, evt);
    }//GEN-LAST:event_txtDate7KeyPressed

    private void txtDate8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate8KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate8, evt);
    }//GEN-LAST:event_txtDate8KeyPressed

    private void txtDate9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate9KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate9, evt);
    }//GEN-LAST:event_txtDate9KeyPressed

    private void txtDate10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate10KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate10, evt);
    }//GEN-LAST:event_txtDate10KeyPressed

    private void txtDate11KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate11KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate11, evt);
    }//GEN-LAST:event_txtDate11KeyPressed

    private void txtDate12KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate12KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate12, evt);
    }//GEN-LAST:event_txtDate12KeyPressed

    private void txtDate13KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate13KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate13, evt);
    }//GEN-LAST:event_txtDate13KeyPressed

    private void txtDate14KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate14KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate14, evt);
    }//GEN-LAST:event_txtDate14KeyPressed

    private void txtDate15KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate15KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate15, evt);
    }//GEN-LAST:event_txtDate15KeyPressed

    private void txtDate16KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate16KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate16, evt);
    }//GEN-LAST:event_txtDate16KeyPressed

    private void txtDate17KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate17KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate17, evt);
    }//GEN-LAST:event_txtDate17KeyPressed

    private void txtDate18KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate18KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate18, evt);
    }//GEN-LAST:event_txtDate18KeyPressed

    private void txtDate19KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate19KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate19, evt);
    }//GEN-LAST:event_txtDate19KeyPressed

    private void txtDate20KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate20KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate20, evt);
    }//GEN-LAST:event_txtDate20KeyPressed

    private void txtDate21KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate21KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate21, evt);
    }//GEN-LAST:event_txtDate21KeyPressed

    private void txtDate22KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate22KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate22, evt);
    }//GEN-LAST:event_txtDate22KeyPressed

    private void txtDate23KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate23KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate23, evt);
    }//GEN-LAST:event_txtDate23KeyPressed

    private void txtDate24KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate24KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate24, evt);
    }//GEN-LAST:event_txtDate24KeyPressed

    private void txtDate25KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate25KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate25, evt);
    }//GEN-LAST:event_txtDate25KeyPressed

    private void txtDate26KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate26KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate26, evt);
    }//GEN-LAST:event_txtDate26KeyPressed

    private void txtDate27KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate27KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate27, evt);
    }//GEN-LAST:event_txtDate27KeyPressed

    private void txtDate28KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate28KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate28, evt);
    }//GEN-LAST:event_txtDate28KeyPressed

    private void txtDate29KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate29KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate29, evt);
    }//GEN-LAST:event_txtDate29KeyPressed

    private void txtDate30KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate30KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate30, evt);
    }//GEN-LAST:event_txtDate30KeyPressed

    private void txtDate31KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate31KeyPressed
        // TODO add your handling code here:
        textFieldValueCheck(txtDate31, evt);
    }//GEN-LAST:event_txtDate31KeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Bonus;
    private javax.swing.JLabel Bonus1;
    private javax.swing.JLabel Bonus4;
    private javax.swing.JLabel Bonus5;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox cmbMonth;
    private javax.swing.JComboBox cmbYear;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable jtableTimeSheet;
    private javax.swing.JLabel lblEmpName;
    private javax.swing.JTextField txtAbsentDeduction;
    private javax.swing.JTextField txtAdvanceDeduction;
    private javax.swing.JTextField txtBasic;
    private javax.swing.JTextField txtBonus;
    private javax.swing.JTextField txtCountAbsent;
    private javax.swing.JTextField txtCountLeave;
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
    private javax.swing.JTextField txtDues;
    private javax.swing.JTextField txtFoodAddition;
    private javax.swing.JTextField txtFoodDeduction;
    private javax.swing.JTextField txtGrossAmount;
    private javax.swing.JTextField txtGrossAmountHOT;
    private javax.swing.JTextField txtGrossAmountNormal;
    private javax.swing.JTextField txtGrossAmountOT;
    private javax.swing.JTextField txtHot1;
    private javax.swing.JTextField txtHot10;
    private javax.swing.JTextField txtHot11;
    private javax.swing.JTextField txtHot12;
    private javax.swing.JTextField txtHot13;
    private javax.swing.JTextField txtHot14;
    private javax.swing.JTextField txtHot15;
    private javax.swing.JTextField txtHot16;
    private javax.swing.JTextField txtHot17;
    private javax.swing.JTextField txtHot18;
    private javax.swing.JTextField txtHot19;
    private javax.swing.JTextField txtHot2;
    private javax.swing.JTextField txtHot20;
    private javax.swing.JTextField txtHot21;
    private javax.swing.JTextField txtHot22;
    private javax.swing.JTextField txtHot23;
    private javax.swing.JTextField txtHot24;
    private javax.swing.JTextField txtHot25;
    private javax.swing.JTextField txtHot26;
    private javax.swing.JTextField txtHot27;
    private javax.swing.JTextField txtHot28;
    private javax.swing.JTextField txtHot29;
    private javax.swing.JTextField txtHot3;
    private javax.swing.JTextField txtHot30;
    private javax.swing.JTextField txtHot31;
    private javax.swing.JTextField txtHot4;
    private javax.swing.JTextField txtHot5;
    private javax.swing.JTextField txtHot6;
    private javax.swing.JTextField txtHot7;
    private javax.swing.JTextField txtHot8;
    private javax.swing.JTextField txtHot9;
    private javax.swing.JTextField txtHotRatePerHour;
    private javax.swing.JTextField txtNetAmount;
    private javax.swing.JTextField txtNormalDate1;
    private javax.swing.JTextField txtNormalDate10;
    private javax.swing.JTextField txtNormalDate11;
    private javax.swing.JTextField txtNormalDate12;
    private javax.swing.JTextField txtNormalDate13;
    private javax.swing.JTextField txtNormalDate14;
    private javax.swing.JTextField txtNormalDate15;
    private javax.swing.JTextField txtNormalDate16;
    private javax.swing.JTextField txtNormalDate17;
    private javax.swing.JTextField txtNormalDate18;
    private javax.swing.JTextField txtNormalDate19;
    private javax.swing.JTextField txtNormalDate2;
    private javax.swing.JTextField txtNormalDate20;
    private javax.swing.JTextField txtNormalDate21;
    private javax.swing.JTextField txtNormalDate22;
    private javax.swing.JTextField txtNormalDate23;
    private javax.swing.JTextField txtNormalDate24;
    private javax.swing.JTextField txtNormalDate25;
    private javax.swing.JTextField txtNormalDate26;
    private javax.swing.JTextField txtNormalDate27;
    private javax.swing.JTextField txtNormalDate28;
    private javax.swing.JTextField txtNormalDate29;
    private javax.swing.JTextField txtNormalDate3;
    private javax.swing.JTextField txtNormalDate30;
    private javax.swing.JTextField txtNormalDate31;
    private javax.swing.JTextField txtNormalDate4;
    private javax.swing.JTextField txtNormalDate5;
    private javax.swing.JTextField txtNormalDate6;
    private javax.swing.JTextField txtNormalDate7;
    private javax.swing.JTextField txtNormalDate8;
    private javax.swing.JTextField txtNormalDate9;
    private javax.swing.JTextField txtNormalRatePerHour;
    private javax.swing.JTextField txtOT1;
    private javax.swing.JTextField txtOT10;
    private javax.swing.JTextField txtOT11;
    private javax.swing.JTextField txtOT12;
    private javax.swing.JTextField txtOT13;
    private javax.swing.JTextField txtOT14;
    private javax.swing.JTextField txtOT15;
    private javax.swing.JTextField txtOT16;
    private javax.swing.JTextField txtOT17;
    private javax.swing.JTextField txtOT18;
    private javax.swing.JTextField txtOT19;
    private javax.swing.JTextField txtOT2;
    private javax.swing.JTextField txtOT20;
    private javax.swing.JTextField txtOT21;
    private javax.swing.JTextField txtOT22;
    private javax.swing.JTextField txtOT23;
    private javax.swing.JTextField txtOT24;
    private javax.swing.JTextField txtOT25;
    private javax.swing.JTextField txtOT26;
    private javax.swing.JTextField txtOT27;
    private javax.swing.JTextField txtOT28;
    private javax.swing.JTextField txtOT29;
    private javax.swing.JTextField txtOT3;
    private javax.swing.JTextField txtOT30;
    private javax.swing.JTextField txtOT31;
    private javax.swing.JTextField txtOT4;
    private javax.swing.JTextField txtOT5;
    private javax.swing.JTextField txtOT6;
    private javax.swing.JTextField txtOT7;
    private javax.swing.JTextField txtOT8;
    private javax.swing.JTextField txtOT9;
    private javax.swing.JTextField txtOtRatePerHour;
    private javax.swing.JTextField txtOtherAddition;
    private javax.swing.JTextField txtOtherDeduction;
    private javax.swing.JTextField txtTotalAddition;
    private javax.swing.JTextField txtTotalDeduction;
    private javax.swing.JTextField txtTotalHot;
    private javax.swing.JTextField txtTotalNormal;
    private javax.swing.JTextField txtTotalOT;
    // End of variables declaration//GEN-END:variables
    Point middle = new Point(80,0);
    int year,month;
    int empId,countLeave=0,countAbsent=0,timeSheetId;
    double totalHOT,totalOT;
}    
    
   

