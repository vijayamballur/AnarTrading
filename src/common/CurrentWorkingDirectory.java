package common;


import java.io.File;
import javax.swing.JOptionPane;
import org.vijay.report.ReportView;

 public class CurrentWorkingDirectory 
 {
     public String getpath()
     {
        String path = null;
        String absolutePath=null;
        File directory = new File (".");
        try 
        {
            path=directory.getCanonicalPath();
            absolutePath=path.replace("\\","\\");
        }
        catch(Exception e) 
        {
           System.out.println("Current working directory exception="+e.getMessage());
        }
        return absolutePath;
     }
}