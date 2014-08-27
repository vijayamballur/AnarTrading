/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.vijay.common;

import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class DateCellRenderer extends DefaultTableCellRenderer{
public Component getTableCellRendererComponent(JTable table, Object value, boolean
isSelected, boolean hasFocus, int row, int column){
super.getTableCellRendererComponent( table, value, isSelected, hasFocus, row, column );
if ( value instanceof Date ){
// Use SimpleDateFormat class to get a formatted String from Date object.
String strDate = new SimpleDateFormat("dd-MMM-YYYY").format((Date)value);
// Sorting algorithm will work with model value. So you dont need to worry
// about the renderer's display value.
this.setText( strDate );
}

return this;
}
}
