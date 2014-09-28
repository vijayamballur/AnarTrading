/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.vijay.contractEmployee;

import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import org.vijay.common.AutoCompleteDecorator;
import org.vijay.common.CurrentWorkingDirectory;
import org.vijay.common.DateCellRenderer;
import org.vijay.common.connection;
import org.vijay.report.ReportView;

/**
 *
 * @author MAC
 */
public class ConTimeSheetSearch extends javax.swing.JInternalFrame {

    /**
     * Creates new form ConTimeSheetSearch
     */
    public ConTimeSheetSearch() {
        initComponents();
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        cmbSiteNameFill();
        cmbMonthFill();
        cmbContractingComFill();
        queryGenerator();
        CurrentWorkingDirectory CWD=new CurrentWorkingDirectory();
        path=CWD.getpath();
    }
     public void cmbSiteNameFill() {
        try {
            connection c = new connection();
            Connection con = c.conn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT site FROM tbl_conemployee order by site asc");
            while (rs.next()) {
                cmbSiteName.addItem(rs.getString(1));
            }
            con.close();
        } catch (SQLException ex) {
            
        }
    }
      public void cmbMonthFill() {
        try {
            connection c = new connection();
            Connection con = c.conn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT DATE_FORMAT(timesheetDate,'"+"%b %y"+"') FROM tbl_con_timesheet order by DATE_FORMAT(timesheetDate,'"+"%b %y"+"') asc");
            while (rs.next()) {
                cmbMonth.addItem(rs.getString(1));
            }
            con.close();
        } catch (SQLException ex) {
            
        }
    }
      public void cmbContractingComFill() {
        try {
            connection c = new connection();
            Connection con = c.conn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT contractingCompany FROM tbl_conemployee order by contractingCompany asc");
            while (rs.next()) {
                cmbConCompany.addItem(rs.getString(1));
            }
            con.close();
        } catch (SQLException ex) {
            
        }
    }
      public void queryGenerator()
    {
        String query="";
        site=cmbSiteName.getSelectedItem().toString();
        contract=cmbConCompany.getSelectedItem().toString();
        month=cmbMonth.getSelectedItem().toString();
        
        if(chkSite.isSelected()==false && chkConCompany.isSelected()==false && chkMonth.isSelected()==false)
        {
            query="SELECT timeSheetId '"+"Id"+"',tbl_con_timesheet.empId '"+"empId"+"',empName '"+"Name"+"',date1 '"+"1"+"',date2 '"+"2"+"',date3 '"+"3"+"',date4 '"+"4"+"',date5 '"+"5"+"',date6 '"+"6"+"',date7 '"+"7"+"',date8 '"+"8"+"',date9 '"+"9"+"',date10 '"+"10"+"',date11 '"+"11"+"',date12 '"+"12"+"',date13 '"+"13"+"',date14 '"+"14"+"',date15 '"+"15"+"',date16 '"+"16"+"',date17 '"+"17"+"',date18 '"+"18"+"',date19 '"+"19"+"',date20 '"+"20"+"',date21 '"+"21"+"',date22 '"+"22"+"',date23 '"+"23"+"',date24 '"+"24"+"',date25 '"+"25"+"',date26 '"+"26"+"',date27 '"+"27"+"',date28 '"+"28"+"',date29 '"+"29"+"',date30 '"+"30"+"',date31 '"+"31"+"',totalHr THr,countAbs Abs,countLeave Lev,DATE_FORMAT(timesheetDate,'"+"%b %y"+"') MONTH FROM anar.tbl_con_timesheet INNER JOIN anar.tbl_conemployee ON (tbl_con_timesheet.empId = tbl_conemployee.empId) ORDER BY DATE_FORMAT(timesheetDate,'"+"%b %y"+"') desc;";
            printValue="000";
            search(query);
            
        }
        if(chkSite.isSelected()==false && chkConCompany.isSelected()==false && chkMonth.isSelected()==true)
        {
            query="SELECT timeSheetId '"+"Id"+"',tbl_con_timesheet.empId '"+"empId"+"',empName '"+"Name"+"',date1 '"+"1"+"',date2 '"+"2"+"',date3 '"+"3"+"',date4 '"+"4"+"',date5 '"+"5"+"',date6 '"+"6"+"',date7 '"+"7"+"',date8 '"+"8"+"',date9 '"+"9"+"',date10 '"+"10"+"',date11 '"+"11"+"',date12 '"+"12"+"',date13 '"+"13"+"',date14 '"+"14"+"',date15 '"+"15"+"',date16 '"+"16"+"',date17 '"+"17"+"',date18 '"+"18"+"',date19 '"+"19"+"',date20 '"+"20"+"',date21 '"+"21"+"',date22 '"+"22"+"',date23 '"+"23"+"',date24 '"+"24"+"',date25 '"+"25"+"',date26 '"+"26"+"',date27 '"+"27"+"',date28 '"+"28"+"',date29 '"+"29"+"',date30 '"+"30"+"',date31 '"+"31"+"',totalHr THr,countAbs Abs,countLeave Lev,DATE_FORMAT(timesheetDate,'"+"%b %y"+"') MONTH FROM anar.tbl_con_timesheet INNER JOIN anar.tbl_conemployee ON (tbl_con_timesheet.empId = tbl_conemployee.empId) where DATE_FORMAT(timesheetDate,'"+"%b %y"+"')='"+month+"' ORDER BY DATE_FORMAT(timesheetDate,'"+"%b %y"+"') desc;";
            printValue="001";
            search(query);
            
        }
        if(chkSite.isSelected()==false && chkConCompany.isSelected()==true && chkMonth.isSelected()==false)
        {
            query="SELECT timeSheetId '"+"Id"+"',tbl_con_timesheet.empId '"+"empId"+"',empName '"+"Name"+"',date1 '"+"1"+"',date2 '"+"2"+"',date3 '"+"3"+"',date4 '"+"4"+"',date5 '"+"5"+"',date6 '"+"6"+"',date7 '"+"7"+"',date8 '"+"8"+"',date9 '"+"9"+"',date10 '"+"10"+"',date11 '"+"11"+"',date12 '"+"12"+"',date13 '"+"13"+"',date14 '"+"14"+"',date15 '"+"15"+"',date16 '"+"16"+"',date17 '"+"17"+"',date18 '"+"18"+"',date19 '"+"19"+"',date20 '"+"20"+"',date21 '"+"21"+"',date22 '"+"22"+"',date23 '"+"23"+"',date24 '"+"24"+"',date25 '"+"25"+"',date26 '"+"26"+"',date27 '"+"27"+"',date28 '"+"28"+"',date29 '"+"29"+"',date30 '"+"30"+"',date31 '"+"31"+"',totalHr THr,countAbs Abs,countLeave Lev,DATE_FORMAT(timesheetDate,'"+"%b %y"+"') MONTH FROM anar.tbl_con_timesheet INNER JOIN anar.tbl_conemployee ON (tbl_con_timesheet.empId = tbl_conemployee.empId) where contractingCompany='"+contract+"' ORDER BY DATE_FORMAT(timesheetDate,'"+"%b %y"+"') desc;";
            printValue="010";
            search(query);
        }
        if(chkSite.isSelected()==false && chkConCompany.isSelected()==true && chkMonth.isSelected()==true)
        {
            query="SELECT timeSheetId '"+"Id"+"',tbl_con_timesheet.empId '"+"empId"+"',empName '"+"Name"+"',date1 '"+"1"+"',date2 '"+"2"+"',date3 '"+"3"+"',date4 '"+"4"+"',date5 '"+"5"+"',date6 '"+"6"+"',date7 '"+"7"+"',date8 '"+"8"+"',date9 '"+"9"+"',date10 '"+"10"+"',date11 '"+"11"+"',date12 '"+"12"+"',date13 '"+"13"+"',date14 '"+"14"+"',date15 '"+"15"+"',date16 '"+"16"+"',date17 '"+"17"+"',date18 '"+"18"+"',date19 '"+"19"+"',date20 '"+"20"+"',date21 '"+"21"+"',date22 '"+"22"+"',date23 '"+"23"+"',date24 '"+"24"+"',date25 '"+"25"+"',date26 '"+"26"+"',date27 '"+"27"+"',date28 '"+"28"+"',date29 '"+"29"+"',date30 '"+"30"+"',date31 '"+"31"+"',totalHr THr,countAbs Abs,countLeave Lev,DATE_FORMAT(timesheetDate,'"+"%b %y"+"') MONTH FROM anar.tbl_con_timesheet INNER JOIN anar.tbl_conemployee ON (tbl_con_timesheet.empId = tbl_conemployee.empId) where contractingCompany='"+contract+"' and DATE_FORMAT(timesheetDate,'"+"%b %y"+"')='"+month+"' ORDER BY DATE_FORMAT(timesheetDate,'"+"%b %y"+"') desc;";
            printValue="011";
            search(query);
        }
        if(chkSite.isSelected()==true && chkConCompany.isSelected()==false && chkMonth.isSelected()==false)
        {
            query="SELECT timeSheetId '"+"Id"+"',tbl_con_timesheet.empId '"+"empId"+"',empName '"+"Name"+"',date1 '"+"1"+"',date2 '"+"2"+"',date3 '"+"3"+"',date4 '"+"4"+"',date5 '"+"5"+"',date6 '"+"6"+"',date7 '"+"7"+"',date8 '"+"8"+"',date9 '"+"9"+"',date10 '"+"10"+"',date11 '"+"11"+"',date12 '"+"12"+"',date13 '"+"13"+"',date14 '"+"14"+"',date15 '"+"15"+"',date16 '"+"16"+"',date17 '"+"17"+"',date18 '"+"18"+"',date19 '"+"19"+"',date20 '"+"20"+"',date21 '"+"21"+"',date22 '"+"22"+"',date23 '"+"23"+"',date24 '"+"24"+"',date25 '"+"25"+"',date26 '"+"26"+"',date27 '"+"27"+"',date28 '"+"28"+"',date29 '"+"29"+"',date30 '"+"30"+"',date31 '"+"31"+"',totalHr THr,countAbs Abs,countLeave Lev,DATE_FORMAT(timesheetDate,'"+"%b %y"+"') MONTH FROM anar.tbl_con_timesheet INNER JOIN anar.tbl_conemployee ON (tbl_con_timesheet.empId = tbl_conemployee.empId) where site='"+site+"' ORDER BY DATE_FORMAT(timesheetDate,'"+"%b %y"+"') desc;";
            printValue="100";
            search(query);
        }
        if(chkSite.isSelected()==true && chkConCompany.isSelected()==false && chkMonth.isSelected()==true)
        {
            query="SELECT timeSheetId '"+"Id"+"',tbl_con_timesheet.empId '"+"empId"+"',empName '"+"Name"+"',date1 '"+"1"+"',date2 '"+"2"+"',date3 '"+"3"+"',date4 '"+"4"+"',date5 '"+"5"+"',date6 '"+"6"+"',date7 '"+"7"+"',date8 '"+"8"+"',date9 '"+"9"+"',date10 '"+"10"+"',date11 '"+"11"+"',date12 '"+"12"+"',date13 '"+"13"+"',date14 '"+"14"+"',date15 '"+"15"+"',date16 '"+"16"+"',date17 '"+"17"+"',date18 '"+"18"+"',date19 '"+"19"+"',date20 '"+"20"+"',date21 '"+"21"+"',date22 '"+"22"+"',date23 '"+"23"+"',date24 '"+"24"+"',date25 '"+"25"+"',date26 '"+"26"+"',date27 '"+"27"+"',date28 '"+"28"+"',date29 '"+"29"+"',date30 '"+"30"+"',date31 '"+"31"+"',totalHr THr,countAbs Abs,countLeave Lev,DATE_FORMAT(timesheetDate,'"+"%b %y"+"') MONTH FROM anar.tbl_con_timesheet INNER JOIN anar.tbl_conemployee ON (tbl_con_timesheet.empId = tbl_conemployee.empId) where site='"+site+"' and DATE_FORMAT(timesheetDate,'"+"%b %y"+"')='"+month+"' ORDER BY DATE_FORMAT(timesheetDate,'"+"%b %y"+"') desc;";
            printValue="101";
            search(query);
        }
        if(chkSite.isSelected()==true && chkConCompany.isSelected()==true && chkMonth.isSelected()==false)
        {
            query="SELECT timeSheetId '"+"Id"+"',tbl_con_timesheet.empId '"+"empId"+"',empName '"+"Name"+"',date1 '"+"1"+"',date2 '"+"2"+"',date3 '"+"3"+"',date4 '"+"4"+"',date5 '"+"5"+"',date6 '"+"6"+"',date7 '"+"7"+"',date8 '"+"8"+"',date9 '"+"9"+"',date10 '"+"10"+"',date11 '"+"11"+"',date12 '"+"12"+"',date13 '"+"13"+"',date14 '"+"14"+"',date15 '"+"15"+"',date16 '"+"16"+"',date17 '"+"17"+"',date18 '"+"18"+"',date19 '"+"19"+"',date20 '"+"20"+"',date21 '"+"21"+"',date22 '"+"22"+"',date23 '"+"23"+"',date24 '"+"24"+"',date25 '"+"25"+"',date26 '"+"26"+"',date27 '"+"27"+"',date28 '"+"28"+"',date29 '"+"29"+"',date30 '"+"30"+"',date31 '"+"31"+"',totalHr THr,countAbs Abs,countLeave Lev,DATE_FORMAT(timesheetDate,'"+"%b %y"+"') MONTH FROM anar.tbl_con_timesheet INNER JOIN anar.tbl_conemployee ON (tbl_con_timesheet.empId = tbl_conemployee.empId) where site='"+site+"' and contractingCompany='"+contract+"' ORDER BY DATE_FORMAT(timesheetDate,'"+"%b %y"+"') desc;";
            printValue="110";
            search(query);
        }
        if(chkSite.isSelected()==true && chkConCompany.isSelected()==true && chkMonth.isSelected()==true)
        {
            query="SELECT timeSheetId '"+"Id"+"',tbl_con_timesheet.empId '"+"empId"+"',empName '"+"Name"+"',date1 '"+"1"+"',date2 '"+"2"+"',date3 '"+"3"+"',date4 '"+"4"+"',date5 '"+"5"+"',date6 '"+"6"+"',date7 '"+"7"+"',date8 '"+"8"+"',date9 '"+"9"+"',date10 '"+"10"+"',date11 '"+"11"+"',date12 '"+"12"+"',date13 '"+"13"+"',date14 '"+"14"+"',date15 '"+"15"+"',date16 '"+"16"+"',date17 '"+"17"+"',date18 '"+"18"+"',date19 '"+"19"+"',date20 '"+"20"+"',date21 '"+"21"+"',date22 '"+"22"+"',date23 '"+"23"+"',date24 '"+"24"+"',date25 '"+"25"+"',date26 '"+"26"+"',date27 '"+"27"+"',date28 '"+"28"+"',date29 '"+"29"+"',date30 '"+"30"+"',date31 '"+"31"+"',totalHr THr,countAbs Abs,countLeave Lev,DATE_FORMAT(timesheetDate,'"+"%b %y"+"') MONTH FROM anar.tbl_con_timesheet INNER JOIN anar.tbl_conemployee ON (tbl_con_timesheet.empId = tbl_conemployee.empId) where site='"+site+"' and contractingCompany='"+contract+"' and DATE_FORMAT(timesheetDate,'"+"%b %y"+"')='"+month+"' ORDER BY DATE_FORMAT(timesheetDate,'"+"%b %y"+"') desc;";
            printValue="111";
            search(query);
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        chkSite = new javax.swing.JCheckBox();
        cmbSiteName = new javax.swing.JComboBox();
        chkMonth = new javax.swing.JCheckBox();
        chkConCompany = new javax.swing.JCheckBox();
        cmbConCompany = new javax.swing.JComboBox();
        cmbMonth = new javax.swing.JComboBox();
        jToolBar1 = new javax.swing.JToolBar();
        btnPrintTimeSheet = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setClosable(true);
        setTitle("Time Sheet Search");

        chkSite.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        chkSite.setText("Site Name");
        chkSite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkSiteActionPerformed(evt);
            }
        });

        cmbSiteName.addItem("--select name--");
        cmbSiteName.setSelectedItem("--select name--");
        cmbSiteName.setEnabled(false);
        cmbSiteName.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        cmbSiteName.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbSiteNameItemStateChanged(evt);
            }
        });
        cmbSiteName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbSiteNameActionPerformed(evt);
            }
        });

        chkMonth.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        chkMonth.setText("Month");
        chkMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMonthActionPerformed(evt);
            }
        });

        chkConCompany.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        chkConCompany.setText("Contracting Company");
        chkConCompany.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkConCompanyActionPerformed(evt);
            }
        });

        cmbConCompany.addItem("--select name--");
        cmbConCompany.setSelectedItem("--select name--");
        cmbConCompany.setEnabled(false);
        cmbConCompany.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        cmbConCompany.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbConCompanyActionPerformed(evt);
            }
        });

        cmbMonth.addItem("--select month & year--");
        cmbMonth.setSelectedItem("--select month & year--");
        cmbMonth.setEnabled(false);
        cmbMonth.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        cmbMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbMonthActionPerformed(evt);
            }
        });

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnPrintTimeSheet.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnPrintTimeSheet.setText("Print Time Sheet");
        btnPrintTimeSheet.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnPrintTimeSheet.setFocusable(false);
        btnPrintTimeSheet.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPrintTimeSheet.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnPrintTimeSheet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintTimeSheetActionPerformed(evt);
            }
        });
        jToolBar1.add(btnPrintTimeSheet);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chkSite)
                    .addComponent(chkMonth))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cmbSiteName, 0, 370, Short.MAX_VALUE)
                    .addComponent(cmbMonth, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(chkConCompany)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cmbConCompany, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(21, 21, 21))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkSite)
                    .addComponent(cmbSiteName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkConCompany)
                    .addComponent(cmbConCompany, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(chkMonth)
                        .addComponent(cmbMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTable1.setFont(new java.awt.Font("Century Gothic", 0, 9)); // NOI18N
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
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 26, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void chkSiteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkSiteActionPerformed
        // TODO add your handling code here:
        if(chkSite.isSelected()==false)
        {
            cmbSiteName.setSelectedItem("--Select Name--");
            cmbSiteName.setEnabled(false);
        }
        else
        {
            cmbSiteName.setEnabled(true);
            cmbSiteName.setEditable(true);
            AutoCompleteDecorator.decorate(cmbSiteName);
        }
    }//GEN-LAST:event_chkSiteActionPerformed

    private void cmbSiteNameItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbSiteNameItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbSiteNameItemStateChanged

    private void cmbSiteNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSiteNameActionPerformed
        // TODO add your handling code here:
        queryGenerator();
    }//GEN-LAST:event_cmbSiteNameActionPerformed

    private void chkMonthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMonthActionPerformed
        // TODO add your handling code here:
        if(chkMonth.isSelected()==false)
        {
            cmbMonth.setSelectedItem("--select month & year--");
            cmbMonth.setEnabled(false);
        }
        else
        {
            cmbMonth.setEnabled(true);
            cmbMonth.setEditable(true);
            AutoCompleteDecorator.decorate(cmbMonth);
        }
    }//GEN-LAST:event_chkMonthActionPerformed

    private void chkConCompanyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkConCompanyActionPerformed
        // TODO add your handling code here:
        if(chkConCompany.isSelected()==false)
        {
            cmbConCompany.setSelectedItem("--Select Name--");
            cmbConCompany.setEnabled(false);
        }
        else
        {
            cmbConCompany.setEnabled(true);
            cmbConCompany.setEditable(true);
            AutoCompleteDecorator.decorate(cmbConCompany);
        }
    }//GEN-LAST:event_chkConCompanyActionPerformed

    private void cmbConCompanyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbConCompanyActionPerformed
        // TODO add your handling code here:
        queryGenerator();
    }//GEN-LAST:event_cmbConCompanyActionPerformed

    private void cmbMonthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbMonthActionPerformed
        // TODO add your handling code here:
        queryGenerator();
    }//GEN-LAST:event_cmbMonthActionPerformed

    private void btnPrintTimeSheetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintTimeSheetActionPerformed
        // TODO add your handling code here:

        if(printValue.equals("000"))
        {
            HashMap para=new HashMap();
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\TimeSeet\\conTimeSheet.jasper"));
            re.setVisible(true);
        }
        if(printValue.equals("001"))
        {
            HashMap para=new HashMap();
            para.put("month",month);
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\TimeSeet\\conTimeSheet_001.jasper"),para);
            re.setVisible(true);
        }
        if(printValue.equals("010"))
        {
            HashMap para=new HashMap();
            para.put("contract",contract);
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\TimeSeet\\conTimeSheet_010.jasper"),para);
            re.setVisible(true); 
        }
        if(printValue.equals("011"))
        {
            HashMap para=new HashMap();
            para.put("contract",contract);
            para.put("month",month);
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\TimeSeet\\conTimeSheet_011.jasper"),para);
            re.setVisible(true); 
        }
        if(printValue.equals("100"))
        {
            HashMap para=new HashMap();
            para.put("site",site);
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\TimeSeet\\conTimeSheet_100.jasper"),para);
            re.setVisible(true); 
        }
        if(printValue.equals("101"))
        {
            HashMap para=new HashMap();
            para.put("site",site);
            para.put("month",month);
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\TimeSeet\\conTimeSheet_101.jasper"),para);
            re.setVisible(true); 
        }
        if(printValue.equals("110"))
        {
            HashMap para=new HashMap();
            para.put("site",site);
            para.put("contract",contract);
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\TimeSeet\\conTimeSheet_110.jasper"),para);
            re.setVisible(true); 
        }
        if(printValue.equals("111"))
        {
            HashMap para=new HashMap();
            para.put("site",site);
            para.put("contract",contract);
            para.put("month",month);
            ReportView re=new ReportView(path.concat("\\lib\\Reports\\Anar\\TimeSeet\\conTimeSheet_111.jasper"),para);
            re.setVisible(true); 
        }
        
        
    }//GEN-LAST:event_btnPrintTimeSheetActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPrintTimeSheet;
    private javax.swing.JCheckBox chkConCompany;
    private javax.swing.JCheckBox chkMonth;
    private javax.swing.JCheckBox chkSite;
    private javax.swing.JComboBox cmbConCompany;
    private javax.swing.JComboBox cmbMonth;
    private javax.swing.JComboBox cmbSiteName;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
    String printValue="",path,month,contract,site;
}
