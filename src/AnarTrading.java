

//import com.nilo.plaf.nimrod.NimRODLookAndFeel;
//import com.nilo.plaf.nimrod.NimRODTheme;
import com.nilo.plaf.nimrod.NimRODLookAndFeel;
import com.nilo.plaf.nimrod.NimRODTheme;
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author vijay
 */
public class AnarTrading extends javax.swing.JFrame {

    /** Creates new form InventryMdi */
    public AnarTrading() {
        initComponents();
        jToolBar1.setVisible(false);
        setExtendedState(getExtendedState() | MAXIMIZED_BOTH);

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar2 = new javax.swing.JToolBar();
        desktopPane = new javax.swing.JDesktopPane();
        jToolBar1 = new javax.swing.JToolBar();
        btnEmployee = new javax.swing.JButton();
        btnAdvanced = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        menuItemCourse = new javax.swing.JButton();
        btnStudent = new javax.swing.JButton();
        btnExcel = new javax.swing.JButton();
        btnServerBkp = new javax.swing.JButton();
        btnAccount = new javax.swing.JButton();
        btnAccount1 = new javax.swing.JButton();
        btnAccount2 = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuWindow = new javax.swing.JMenu();
        menuFile = new javax.swing.JMenu();
        menuItemExit = new javax.swing.JMenuItem();
        menuCourse = new javax.swing.JMenu();
        menuItemLabourDetails = new javax.swing.JMenuItem();
        menuItemHoliday = new javax.swing.JMenuItem();
        menuItemAdvancePayment = new javax.swing.JMenuItem();
        menuTransaction = new javax.swing.JMenu();
        menuItemAdvancedView = new javax.swing.JMenuItem();
        menuHelp = new javax.swing.JMenu();
        menu = new javax.swing.JMenu();

        jToolBar2.setRollover(true);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        jToolBar1.setBackground(new java.awt.Color(25, 30, 39));
        jToolBar1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jToolBar1.setFloatable(false);
        jToolBar1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jToolBar1.setRollover(true);
        jToolBar1.setToolTipText("Tool Bar");
        jToolBar1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jToolBar1.setMargin(new java.awt.Insets(0, 20, 0, -1));
        jToolBar1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jToolBar1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jToolBar1MouseExited(evt);
            }
        });

        btnEmployee.setBackground(new java.awt.Color(255, 255, 255));
        btnEmployee.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        btnEmployee.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/billingIcon.png"))); // NOI18N
        btnEmployee.setText("Employee");
        btnEmployee.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEmployee.setFocusable(false);
        btnEmployee.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEmployee.setMargin(new java.awt.Insets(2, 5, 0, 5));
        btnEmployee.setMaximumSize(new java.awt.Dimension(90, 93));
        btnEmployee.setMinimumSize(new java.awt.Dimension(71, 93));
        btnEmployee.setPreferredSize(new java.awt.Dimension(71, 93));
        btnEmployee.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/billingIcon1.png"))); // NOI18N
        btnEmployee.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/billingIcon1.png"))); // NOI18N
        btnEmployee.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEmployee.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEmployeeMouseEntered(evt);
            }
        });
        btnEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmployeeActionPerformed(evt);
            }
        });
        jToolBar1.add(btnEmployee);

        btnAdvanced.setBackground(new java.awt.Color(255, 255, 255));
        btnAdvanced.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        btnAdvanced.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/purchaseIcon.png"))); // NOI18N
        btnAdvanced.setText("Adavanced");
        btnAdvanced.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAdvanced.setFocusable(false);
        btnAdvanced.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAdvanced.setMargin(new java.awt.Insets(2, 5, 0, 5));
        btnAdvanced.setMaximumSize(new java.awt.Dimension(90, 93));
        btnAdvanced.setMinimumSize(new java.awt.Dimension(71, 93));
        btnAdvanced.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/purchaseIcon1.png"))); // NOI18N
        btnAdvanced.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/purchaseIcon1.png"))); // NOI18N
        btnAdvanced.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAdvanced.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAdvancedMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAdvancedMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAdvancedMouseExited(evt);
            }
        });
        btnAdvanced.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdvancedActionPerformed(evt);
            }
        });
        jToolBar1.add(btnAdvanced);

        btnSearch.setBackground(new java.awt.Color(255, 255, 255));
        btnSearch.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/SearchIcon.png"))); // NOI18N
        btnSearch.setText("3");
        btnSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSearch.setFocusable(false);
        btnSearch.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSearch.setMargin(new java.awt.Insets(2, 5, 0, 5));
        btnSearch.setMaximumSize(new java.awt.Dimension(90, 93));
        btnSearch.setMinimumSize(new java.awt.Dimension(71, 93));
        btnSearch.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/SearchIcon1.png"))); // NOI18N
        btnSearch.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/SearchIcon1.png"))); // NOI18N
        btnSearch.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSearchMouseEntered(evt);
            }
        });
        jToolBar1.add(btnSearch);

        menuItemCourse.setBackground(new java.awt.Color(255, 255, 255));
        menuItemCourse.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        menuItemCourse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/StockIcon.png"))); // NOI18N
        menuItemCourse.setText("4");
        menuItemCourse.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuItemCourse.setFocusable(false);
        menuItemCourse.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menuItemCourse.setMargin(new java.awt.Insets(2, 5, 0, 5));
        menuItemCourse.setMaximumSize(new java.awt.Dimension(90, 93));
        menuItemCourse.setMinimumSize(new java.awt.Dimension(71, 93));
        menuItemCourse.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/StockIcon1.png"))); // NOI18N
        menuItemCourse.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/StockIcon1.png"))); // NOI18N
        menuItemCourse.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        menuItemCourse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                menuItemCourseMouseEntered(evt);
            }
        });
        menuItemCourse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemCourseActionPerformed(evt);
            }
        });
        jToolBar1.add(menuItemCourse);

        btnStudent.setBackground(new java.awt.Color(255, 255, 255));
        btnStudent.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        btnStudent.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/ServiceIcon.png"))); // NOI18N
        btnStudent.setText("5");
        btnStudent.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnStudent.setFocusable(false);
        btnStudent.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnStudent.setMargin(new java.awt.Insets(2, 5, 0, 5));
        btnStudent.setMaximumSize(new java.awt.Dimension(90, 93));
        btnStudent.setMinimumSize(new java.awt.Dimension(71, 93));
        btnStudent.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/ServiceIcon1.png"))); // NOI18N
        btnStudent.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/ServiceIcon1.png"))); // NOI18N
        btnStudent.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnStudent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnStudentMouseEntered(evt);
            }
        });
        btnStudent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStudentActionPerformed(evt);
            }
        });
        jToolBar1.add(btnStudent);

        btnExcel.setBackground(new java.awt.Color(255, 255, 255));
        btnExcel.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        btnExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/ExcelBkpIcon.png"))); // NOI18N
        btnExcel.setText("6");
        btnExcel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExcel.setFocusable(false);
        btnExcel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnExcel.setMargin(new java.awt.Insets(2, 5, 0, 5));
        btnExcel.setMaximumSize(new java.awt.Dimension(90, 93));
        btnExcel.setMinimumSize(new java.awt.Dimension(71, 93));
        btnExcel.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/ExcelBkpIcon1.png"))); // NOI18N
        btnExcel.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnExcel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnExcelMouseEntered(evt);
            }
        });
        jToolBar1.add(btnExcel);

        btnServerBkp.setBackground(new java.awt.Color(255, 255, 255));
        btnServerBkp.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        btnServerBkp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/serverBkpIcon.png"))); // NOI18N
        btnServerBkp.setText("7");
        btnServerBkp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnServerBkp.setFocusable(false);
        btnServerBkp.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnServerBkp.setMargin(new java.awt.Insets(2, 5, 0, 5));
        btnServerBkp.setMaximumSize(new java.awt.Dimension(90, 93));
        btnServerBkp.setMinimumSize(new java.awt.Dimension(71, 93));
        btnServerBkp.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/serverBkpIcon1.png"))); // NOI18N
        btnServerBkp.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/serverBkpIcon1.png"))); // NOI18N
        btnServerBkp.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnServerBkp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnServerBkpMouseEntered(evt);
            }
        });
        jToolBar1.add(btnServerBkp);

        btnAccount.setBackground(new java.awt.Color(255, 255, 255));
        btnAccount.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        btnAccount.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/AccountIcon.png"))); // NOI18N
        btnAccount.setText("8");
        btnAccount.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAccount.setFocusable(false);
        btnAccount.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAccount.setMargin(new java.awt.Insets(2, 5, 0, 5));
        btnAccount.setMaximumSize(new java.awt.Dimension(90, 93));
        btnAccount.setMinimumSize(new java.awt.Dimension(71, 93));
        btnAccount.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/AccountIcon1.png"))); // NOI18N
        btnAccount.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/AccountIcon1.png"))); // NOI18N
        btnAccount.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAccount.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAccountMouseEntered(evt);
            }
        });
        jToolBar1.add(btnAccount);

        btnAccount1.setBackground(new java.awt.Color(255, 255, 255));
        btnAccount1.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        btnAccount1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/AccountIcon.png"))); // NOI18N
        btnAccount1.setText("9");
        btnAccount1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAccount1.setFocusable(false);
        btnAccount1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAccount1.setMargin(new java.awt.Insets(2, 5, 0, 5));
        btnAccount1.setMaximumSize(new java.awt.Dimension(90, 93));
        btnAccount1.setMinimumSize(new java.awt.Dimension(71, 93));
        btnAccount1.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/AccountIcon1.png"))); // NOI18N
        btnAccount1.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/AccountIcon1.png"))); // NOI18N
        btnAccount1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAccount1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAccount1MouseEntered(evt);
            }
        });
        jToolBar1.add(btnAccount1);

        btnAccount2.setBackground(new java.awt.Color(255, 255, 255));
        btnAccount2.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        btnAccount2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/AccountIcon.png"))); // NOI18N
        btnAccount2.setText("10");
        btnAccount2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAccount2.setFocusable(false);
        btnAccount2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAccount2.setMargin(new java.awt.Insets(2, 5, 0, 5));
        btnAccount2.setMaximumSize(new java.awt.Dimension(90, 93));
        btnAccount2.setMinimumSize(new java.awt.Dimension(71, 93));
        btnAccount2.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/AccountIcon1.png"))); // NOI18N
        btnAccount2.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/AccountIcon1.png"))); // NOI18N
        btnAccount2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAccount2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAccount2MouseEntered(evt);
            }
        });
        jToolBar1.add(btnAccount2);

        desktopPane.add(jToolBar1);
        jToolBar1.setBounds(0, 0, 100, 1000);
        desktopPane.add(jSeparator6);
        jSeparator6.setBounds(160, 280, 0, 2);

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setMinimumSize(new java.awt.Dimension(190, 945));
        jPanel1.setLayout(null);
        desktopPane.add(jPanel1);
        jPanel1.setBounds(75, -10, 190, 950);

        jPanel2.setBackground(new java.awt.Color(153, 153, 153));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 93, Short.MAX_VALUE)
        );

        jMenuBar1.setBackground(new java.awt.Color(102, 102, 102));
        jMenuBar1.setMargin(new java.awt.Insets(0, 0, 0, 50));

        menuWindow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/menuIcons/windows.png"))); // NOI18N
        menuWindow.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuWindowMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                menuWindowMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                menuWindowMouseExited(evt);
            }
        });
        menuWindow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuWindowActionPerformed(evt);
            }
        });
        jMenuBar1.add(menuWindow);

        menuFile.setForeground(new java.awt.Color(255, 255, 255));
        menuFile.setText("File");
        menuFile.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuFile.setFont(new java.awt.Font("Gabriola", 0, 20)); // NOI18N
        menuFile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuFileMouseClicked(evt);
            }
        });

        menuItemExit.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        menuItemExit.setText("Exit");
        menuItemExit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuFile.add(menuItemExit);

        jMenuBar1.add(menuFile);

        menuCourse.setForeground(new java.awt.Color(255, 255, 255));
        menuCourse.setText("Tune-Up");
        menuCourse.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuCourse.setFont(new java.awt.Font("Gabriola", 0, 20)); // NOI18N
        menuCourse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuCourseMouseClicked(evt);
            }
        });
        menuCourse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCourseActionPerformed(evt);
            }
        });

        menuItemLabourDetails.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        menuItemLabourDetails.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        menuItemLabourDetails.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/menuIcons/company.png"))); // NOI18N
        menuItemLabourDetails.setText("Employee");
        menuItemLabourDetails.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuItemLabourDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemLabourDetailsActionPerformed(evt);
            }
        });
        menuCourse.add(menuItemLabourDetails);

        menuItemHoliday.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        menuItemHoliday.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        menuItemHoliday.setText("Holiday");
        menuItemHoliday.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemHolidayActionPerformed(evt);
            }
        });
        menuCourse.add(menuItemHoliday);

        menuItemAdvancePayment.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        menuItemAdvancePayment.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        menuItemAdvancePayment.setText("Advance Payment");
        menuItemAdvancePayment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemAdvancePaymentActionPerformed(evt);
            }
        });
        menuCourse.add(menuItemAdvancePayment);

        jMenuBar1.add(menuCourse);

        menuTransaction.setForeground(new java.awt.Color(255, 255, 255));
        menuTransaction.setText("Transaction");
        menuTransaction.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuTransaction.setFont(new java.awt.Font("Gabriola", 0, 20)); // NOI18N

        menuItemAdvancedView.setFont(new java.awt.Font("Gabriola", 0, 18)); // NOI18N
        menuItemAdvancedView.setText("Advanced View");
        menuItemAdvancedView.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuItemAdvancedView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemAdvancedViewActionPerformed(evt);
            }
        });
        menuTransaction.add(menuItemAdvancedView);

        jMenuBar1.add(menuTransaction);

        menuHelp.setForeground(new java.awt.Color(255, 255, 255));
        menuHelp.setText("Help");
        menuHelp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuHelp.setFont(new java.awt.Font("Gabriola", 0, 20)); // NOI18N
        menuHelp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuHelpMouseClicked(evt);
            }
        });
        menuHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuHelpActionPerformed(evt);
            }
        });
        jMenuBar1.add(menuHelp);

        menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/Exiticon1.png"))); // NOI18N
        menu.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/Exiticon1.png"))); // NOI18N
        menu.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/Exiticon1.png"))); // NOI18N
        menu.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Icons/Exiticon1.png"))); // NOI18N
        menu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuMouseClicked(evt);
            }
        });

        jMenuBar1.add(Box.createHorizontalGlue());

        jMenuBar1.add(menu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 643, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(desktopPane, javax.swing.GroupLayout.PREFERRED_SIZE, 932, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAdvancedMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAdvancedMouseEntered

    }//GEN-LAST:event_btnAdvancedMouseEntered

    private void btnAdvancedMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAdvancedMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAdvancedMouseExited

    private void btnAdvancedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdvancedActionPerformed

        AdvancedView av = new AdvancedView();
        AnarTrading.desktopPane.add(av);
        av.setVisible(true);
        av.show();
        btnAdvanced.setEnabled(false);
        menuItemAdvancedView.setEnabled(false);
    }//GEN-LAST:event_btnAdvancedActionPerformed

    private void btnAdvancedMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAdvancedMouseClicked

    }//GEN-LAST:event_btnAdvancedMouseClicked

    private void btnEmployeeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEmployeeMouseEntered

    }//GEN-LAST:event_btnEmployeeMouseEntered

    private void menuItemCourseMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuItemCourseMouseEntered

    }//GEN-LAST:event_menuItemCourseMouseEntered

    private void btnStudentMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnStudentMouseEntered

        
    }//GEN-LAST:event_btnStudentMouseEntered

    private void btnExcelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcelMouseEntered

    }//GEN-LAST:event_btnExcelMouseEntered

    private void btnServerBkpMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnServerBkpMouseEntered

    }//GEN-LAST:event_btnServerBkpMouseEntered

    private void btnAccountMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAccountMouseEntered

    }//GEN-LAST:event_btnAccountMouseEntered

    private void btnSearchMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSearchMouseEntered

    }//GEN-LAST:event_btnSearchMouseEntered

    private void menuCourseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCourseActionPerformed
        // TODO add your handling code here:
       
    }//GEN-LAST:event_menuCourseActionPerformed

    private void menuCourseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuCourseMouseClicked

    }//GEN-LAST:event_menuCourseMouseClicked

    private void menuFileMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuFileMouseClicked

    }//GEN-LAST:event_menuFileMouseClicked

    private void menuItemAdvancedViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemAdvancedViewActionPerformed

    }//GEN-LAST:event_menuItemAdvancedViewActionPerformed

    private void menuItemLabourDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemLabourDetailsActionPerformed

        LabourDetails labour = new LabourDetails();
        AnarTrading.desktopPane.add(labour);
        labour.setVisible(true);
        labour.show();
        btnEmployee.setEnabled(false);
    }//GEN-LAST:event_menuItemLabourDetailsActionPerformed

    private void menuWindowMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuWindowMouseEntered
        // TODO add your handling code here:
       
    }//GEN-LAST:event_menuWindowMouseEntered

    private void menuWindowMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuWindowMouseExited
        // TODO add your handling code here:
        //jToolBar1.setVisible(false);
    }//GEN-LAST:event_menuWindowMouseExited

    private void jToolBar1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jToolBar1MouseEntered
        // TODO add your handling code here:

    }//GEN-LAST:event_jToolBar1MouseEntered

    private void jToolBar1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jToolBar1MouseExited
        // TODO add your handling code here:

    }//GEN-LAST:event_jToolBar1MouseExited

    private void menuWindowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuWindowActionPerformed
        // TODO add your handling code here:
         
    }//GEN-LAST:event_menuWindowActionPerformed

    private void menuWindowMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuWindowMouseClicked
        // TODO add your handling code here:
        jToolBar1.setVisible(true);
    }//GEN-LAST:event_menuWindowMouseClicked

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here:
       
    }//GEN-LAST:event_formMouseClicked

    private void menuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuMouseClicked
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_menuMouseClicked

    private void menuItemCourseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemCourseActionPerformed
        // TODO add your handling code here:
//        CourseDetails course = new CourseDetails();
//        LibraryMdi.desktopPane.add(course);
//        course.setVisible(true);
//        course.show();
//        menuItemCourse.setEnabled(false);
//        LibraryMdi.menuItemCourse.setEnabled(false);
    }//GEN-LAST:event_menuItemCourseActionPerformed

    private void btnEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmployeeActionPerformed
         //TODO add your handling code here:
        LabourDetails accession = new LabourDetails();
        AnarTrading.desktopPane.add(accession);
        accession.setVisible(true);
        accession.show();
        btnEmployee.setEnabled(false);
        AnarTrading.menuItemLabourDetails.setEnabled(false);
    }//GEN-LAST:event_btnEmployeeActionPerformed

    private void menuHelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuHelpActionPerformed
        // TODO add your handling code here:
//         Help help = new Help();
//         LibraryMdi.desktopPane.add(help);
//         help.setVisible(true);
//         help.show();
//         //btnAccession.setEnabled(false);
//         //LibraryMdi.menuItemAccession.setEnabled(false);
    }//GEN-LAST:event_menuHelpActionPerformed

    private void menuHelpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuHelpMouseClicked
        // TODO add your handling code here:
//        Help help = new Help();
//         LibraryMdi.desktopPane.add(help);
//         help.setVisible(true);
//         help.show();
    }//GEN-LAST:event_menuHelpMouseClicked

    private void btnStudentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStudentActionPerformed
        // TODO add your handling code here:
//        StudentDetails student = new StudentDetails();
//        LibraryMdi.desktopPane.add(student);
//        student.setVisible(true);
//        student.show();
//        btnStudent.setEnabled(false);
//        menuItemStudent.setEnabled(false);
    }//GEN-LAST:event_btnStudentActionPerformed

    private void btnAccount1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAccount1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAccount1MouseEntered

    private void btnAccount2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAccount2MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAccount2MouseEntered

    private void menuItemHolidayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemHolidayActionPerformed
        // TODO add your handling code here:
        HolidayDetails holiday = new HolidayDetails();
        AnarTrading.desktopPane.add(holiday);
        holiday.setVisible(true);
        holiday.show();
        AnarTrading.menuItemHoliday.setEnabled(false);
    }//GEN-LAST:event_menuItemHolidayActionPerformed

    private void menuItemAdvancePaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemAdvancePaymentActionPerformed
        // TODO add your handling code here:
        AdvancePayment advance = new AdvancePayment();
        AnarTrading.desktopPane.add(advance);
        advance.setVisible(true);
        advance.show();
        AnarTrading.menuItemAdvancePayment.setEnabled(false);
    }//GEN-LAST:event_menuItemAdvancePaymentActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
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
        } 
        catch (UnsupportedLookAndFeelException ex) 
        {
            Logger.getLogger(AnarTrading.class.getName()).log(Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new AnarTrading().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAccount;
    private javax.swing.JButton btnAccount1;
    private javax.swing.JButton btnAccount2;
    public static javax.swing.JButton btnAdvanced;
    public static javax.swing.JButton btnEmployee;
    private javax.swing.JButton btnExcel;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnServerBkp;
    public static javax.swing.JButton btnStudent;
    public static javax.swing.JDesktopPane desktopPane;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JMenu menu;
    private javax.swing.JMenu menuCourse;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenu menuHelp;
    public static javax.swing.JMenuItem menuItemAdvancePayment;
    public static javax.swing.JMenuItem menuItemAdvancedView;
    public static javax.swing.JButton menuItemCourse;
    private javax.swing.JMenuItem menuItemExit;
    public static javax.swing.JMenuItem menuItemHoliday;
    public static javax.swing.JMenuItem menuItemLabourDetails;
    private javax.swing.JMenu menuTransaction;
    private javax.swing.JMenu menuWindow;
    // End of variables declaration//GEN-END:variables
    public static int jtableValueCount;
}
