import com.nilo.plaf.nimrod.NimRODLookAndFeel;
import com.nilo.plaf.nimrod.NimRODTheme;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
public class Splash
{
    private JLabel l;
    private JProgressBar jp;
    int formcheck=0;
    JFrame f;
    Toolkit kit = Toolkit.getDefaultToolkit();
    Dimension screenSize = kit.getScreenSize();
    int screenHeight = screenSize.height;
    int screenWidth = screenSize.width;
    public Splash()
    {
        f=new JFrame();
	f.setSize(500,200);
	f.setLayout(null);
	jp = new JProgressBar(0, 100);
	jp.setBounds(0,190,500,6);
	f.setUndecorated(true);
	l=new JLabel();
	l.setBounds(0,0,500,200);
	l.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/spalsh.png")));
	f.add(l);
	f.add(jp);
	f.setVisible(true);
	f.setLocation(screenWidth/3, screenHeight/3);
	for (int i =0; i <= 100; i=i+2)
	{
		jp.setValue(i);
		jp.setBackground(Color.WHITE);
		jp.setForeground(Color.red);
		try
		{
                    Thread.sleep(100);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
        AnarTrading an=new AnarTrading();
        an.setVisible(true);
        f.dispose();
    }
    public static void main(String s[]) throws UnsupportedLookAndFeelException
    {
        try {
            //javax.swing.UIManager.setLookAndFeel(new com.nilo.plaf.nimrod.NimRODLookAndFeel());
            NimRODTheme nt = new NimRODTheme();
            
            //window right side shade
            nt.setPrimary1(new Color(191,191,191));
            //slection color
            nt.setPrimary2(new Color(0,150,179));//94, 94, 202
            //window left side shade
            nt.setPrimary3(new Color(191,191, 191));
            //Border Color for Button,Table Header
            nt.setSecondary1(new Color(155,152,100));//25, 30, 39
            //Border Color for Panel,Table Data
            nt.setSecondary2(new Color(165, 162, 110));
            //BackGround color
            nt.setSecondary3(new Color(210, 218, 221));
            
            nt.setWhite(new Color(208, 203, 150));
            nt.setBlack(new Color(0, 0, 0));
            nt.setOpacity(195);
            nt.setFrameOpacity(180);


            NimRODLookAndFeel NimRODLF = new NimRODLookAndFeel();
            NimRODLF.setCurrentTheme(nt);
            UIManager.setLookAndFeel(NimRODLF);
            Splash sp= new Splash();
        }
        catch(Exception e)
        {
            
        }
    }
}
