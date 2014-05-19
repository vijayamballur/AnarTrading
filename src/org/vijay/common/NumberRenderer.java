/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.vijay.common;
import java.text.NumberFormat;

import java.util.Locale;

import javax.swing.SwingConstants;


public class NumberRenderer extends FormatRenderer
{

    public NumberRenderer(NumberFormat formatter)	
    {
        super(formatter);
        setHorizontalAlignment( SwingConstants.RIGHT );
    }	
    public static NumberRenderer getCurrencyRenderer() 	
    {
	return new NumberRenderer( NumberFormat.getCurrencyInstance() );
    }
    public static NumberRenderer getIntegerRenderer()
    {
	return new NumberRenderer( NumberFormat.getNumberInstance());
    }
    public static NumberRenderer getPercentRenderer()
    {	
        return new NumberRenderer( NumberFormat.getPercentInstance() );
    }
}
