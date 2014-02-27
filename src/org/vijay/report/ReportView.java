package org.vijay.report;


import org.vijay.common.connection;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Connection;
import java.util.HashMap;
import javax.swing.JFrame;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;


public class ReportView extends JFrame
{
    Toolkit kit = Toolkit.getDefaultToolkit();
    Dimension screenSize = kit.getScreenSize();
    int screenHeight = screenSize.height;
    int screenWidth = screenSize.width;
    public ReportView(String fileName)
    {
        this(fileName,null);
    }
    public ReportView(String fileName,HashMap para)
    {
        super("Anar ");
        try
        {
            connection c=new connection();
            Connection con=c.conn();
            JasperPrint print=JasperFillManager.fillReport(fileName,para,con);
            JRViewer viwer=new JRViewer(print);
            Container c1=getContentPane();
            c1.add(viwer);
        }
        catch(JRException e)
        {

        }
        setBounds(10,10,screenWidth,screenHeight);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
    }

}
