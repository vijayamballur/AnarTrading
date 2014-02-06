import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Toolkit;
import java.sql.*;
public class Login implements ActionListener
{
    JFrame f;
    JButton btncancel;
    JButton btnlogin;
    JLabel l;
    JPasswordField txtpassword;
    JTextField txtusername;
    String user;
    String pass;
    Toolkit kit = Toolkit.getDefaultToolkit();
    Dimension screenSize = kit.getScreenSize();
    int screenHeight = screenSize.height;
    int screenWidth = screenSize.width;
    Login()
    {
	f=new JFrame("Login Form");
        f.setUndecorated(true);
	f.setSize(500,230);
        f.setLocation(screenWidth/3, screenHeight/3);
        f.setLayout(null);
        l=new JLabel();
        l.setBounds(0,0,500,230);
        l.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/login.jpg")));
        //lblusername = new JLabel("Username");
        txtusername = new JTextField();
        //lblpassword = new JLabel("Password");
        txtpassword = new JPasswordField();
        btnlogin = new JButton("Login");
        btncancel = new JButton("Cancel");

        txtusername=new JTextField();
        txtpassword=new JPasswordField();
        txtpassword.setFont(new Font("Arial", Font.BOLD, 13));
        //lblusername.setBounds(50,20,75,25);
        txtusername.setBounds(125,90,155,25);
        txtusername.setFont(new Font("Arial", Font.BOLD, 13));
        //lblpassword.setBounds(50,50,75,25);
        txtpassword.setBounds(125,120,155,25);
        btnlogin.setBounds(120,170,80,25);
        btncancel.setBounds(215,170,80,25);

        //f.add(lblusername);
        //f.add(lblpassword);
        f.add(txtusername);
        f.add(txtpassword);
        f.add(btnlogin);
        f.add(btncancel);
        f.add(l);
        f.setVisible(true);
        f.setResizable(false);
        btnlogin.addActionListener(this);
        btncancel.addActionListener(this);
    }
    public void actionPerformed(ActionEvent ae)
    {
        if(ae.getSource()==btnlogin)
        {
            txtpassword.selectAll();
            user=null;
            pass=null;
            user=txtusername.getText();
            pass=txtpassword.getSelectedText();
            if(userValidate(user,pass))
            {
                f.dispose();
                //MDI mdi=new MDI();
                //mdi.setVisible(true);
                //Billing_form bf=new Billing_form();


            }
            else
            {
                JOptionPane.showMessageDialog(null,"Incorrect userName or Password");
                txtusername.setText("");
                txtpassword.setText("");
            }
        }
        if(ae.getSource()==btncancel)
        {
            int response=JOptionPane.showConfirmDialog(null,"Do you want to close the window?","Confirm",
            JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
            if(response==JOptionPane.NO_OPTION)
            {
                
            }
            else if(response==JOptionPane.YES_OPTION)
            {
                f.dispose();
            }
            else if(response==JOptionPane.CLOSED_OPTION)
            {

            }
        }
    }
    private boolean userValidate(String user,String password)
    {
        boolean isValidate=false;
        connection c = new connection();
        Connection con=c.conn();
        try
        {
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select * from login");
            while(rs.next())
            {
                String username=rs.getString(1).trim();
                String password1=rs.getString(2).trim();
                if(username.equals(user)&&password1.equals(pass))
                {
                    isValidate=true;
                }
            }
        }
        catch(SQLException ex)
        {
            System.out.println("\n SQL Exception during validation" +ex.toString());
        }
        catch(Exception ex)
        {}
        finally
        {}
        return isValidate;
    }
    public static void main(String args[])
    {
        
    }
}
