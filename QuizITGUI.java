import java.awt.*;
import java.sql.Connection;
import javax.swing.*;

public class QuizITGUI {
    // Variabel untuk menyimpan tombol aktif
    private static JButton activeButton = null;

    public static void main(String[] args) {
        // Membuat frame utama
        JFrame frame = new JFrame("QuizIT");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        Connection connection = Koneksi.getConnection();

        // Panel kiri
        JPanel sidebarpanel = new JPanel();
        sidebarpanel.setBackground(new Color(46, 7, 63)); 
        sidebarpanel.setPreferredSize(new Dimension(200, 0)); 
        sidebarpanel.setLayout(new BoxLayout(sidebarpanel, BoxLayout.Y_AXIS)); 
        sidebarpanel.add(Box.createRigidArea(new Dimension(0, 30))); 

        JLabel titleLabel = new JLabel("QuizIT", SwingConstants.CENTER);
        titleLabel.setForeground(Color.ORANGE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 34)); 
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); 

        sidebarpanel.add(titleLabel);
        sidebarpanel.add(Box.createRigidArea(new Dimension(0, 50)));

        // Menambahkan beberapa komponen ke panel kiri dengan ikon
        sidebarpanel.add(createSidebarButton("Learn", "D:\\PBO\\QuizIT_09\\Image\\home-outline.png")); 
        sidebarpanel.add(Box.createRigidArea(new Dimension(0, 10))); 
        sidebarpanel.add(createSidebarButton("Pages", "icon_pages.png"));
        sidebarpanel.add(Box.createRigidArea(new Dimension(0, 10))); 
        sidebarpanel.add(createSidebarButton("Appearance", "icon_appearance.png"));
        sidebarpanel.add(Box.createRigidArea(new Dimension(0, 10))); 
        sidebarpanel.add(createSidebarButton("Profile", "D:\\PBO\\QuizIT_09\\Image\\person-outline.png"));
        sidebarpanel.add(Box.createVerticalGlue()); 
        sidebarpanel.add(Box.createRigidArea(new Dimension(0, 10))); 
        sidebarpanel.add(createSidebarButton("Logout", "D:\\PBO\\QuizIT_09\\Image\\Logout.png")); 
        sidebarpanel.add(Box.createRigidArea(new Dimension(0, 30))); 

        // Panel kanan
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout()); 
        mainPanel.setBackground(Color.WHITE); 

        // Panel header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(62, 34, 75)); 
        headerPanel.setPreferredSize(new Dimension(150, 80)); 
        headerPanel.setLayout(new BorderLayout());

        // New panel for user info
        JPanel userInfoPanel = new JPanel();
        userInfoPanel.setBackground(new Color(62, 34, 75)); 
        userInfoPanel.setLayout(new FlowLayout(FlowLayout.RIGHT)); 

        // Adding user info components
        ImageIcon profileIcon = new ImageIcon("D:\\\\PBO\\\\QuizIT_09\\\\Image\\\\person-outline.png"); 
        JLabel userProfileLabel = new JLabel(profileIcon);
        userProfileLabel.setPreferredSize(new Dimension(40, 40)); 
        userProfileLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10)); 


        // Create a panel for name and ID
        JPanel nameIdPanel = new JPanel();
        nameIdPanel.setLayout(new BoxLayout(nameIdPanel, BoxLayout.Y_AXIS)); 
        nameIdPanel.setBackground(new Color(0, 0, 0, 0)); 

        // Add user name and ID labels with padding
        JLabel userNameLabel = new JLabel("Elsa");
        userNameLabel.setForeground(Color.WHITE);
        userNameLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10)); 

        JLabel userIdLabel = new JLabel("ID-1809");
        userIdLabel.setForeground(Color.WHITE);
        userIdLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10)); 

        nameIdPanel.add(userNameLabel); 
        nameIdPanel.add(userIdLabel);    

        JLabel userPointsLabel = new JLabel("💎 0");
        userPointsLabel.setForeground(Color.YELLOW); 
        userPointsLabel.setBorder(BorderFactory.createEmptyBorder(10, 50, 0, 30)); 

        userInfoPanel.add(userProfileLabel); 
        userInfoPanel.add(nameIdPanel);      
        userInfoPanel.add(userPointsLabel);   
        headerPanel.add(userInfoPanel, BorderLayout.EAST);

        // Panel konten
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); 
        // Panel untuk search bar
        JPanel searchPanel = new JPanel(new BorderLayout()); 
        searchPanel.setPreferredSize(new Dimension(390, 30)); 
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); 

        // JTextField (search field)
        JTextField searchField = new JTextField();
        searchField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 35)); 
        searchField.setPreferredSize(new Dimension(380, 30)); 

        // Ikon search 
        ImageIcon searchIcon = new ImageIcon("D:\\PBO\\QuizIT_09\\Image\\search-outline.png"); 
        JLabel searchIconLabel = new JLabel(searchIcon); 
        searchIconLabel.setPreferredSize(new Dimension(30, 30)); 
        searchIconLabel.setHorizontalAlignment(SwingConstants.CENTER); 
        searchIconLabel.setBackground(Color.WHITE); 
        searchIconLabel.setOpaque(true);

        // Tambahkan JTextField ke kiri dan ikon ke kanan
        searchPanel.add(searchField, BorderLayout.CENTER); 
        searchPanel.add(searchIconLabel, BorderLayout.EAST); 
        contentPanel.add(searchPanel); 
        // Menambahkan panel header dan konten ke mainPanel
        mainPanel.add(headerPanel, BorderLayout.NORTH); 
        mainPanel.add(contentPanel, BorderLayout.CENTER); 

        // Menambahkan panel ke frame
        frame.add(sidebarpanel, BorderLayout.WEST); 
        frame.add(mainPanel, BorderLayout.CENTER); 

        // Menampilkan frame
        frame.setVisible(true);
    }

    // Metode untuk membuat tombol sidebar
    private static JButton createSidebarButton(String text, String iconPath) {
        JButton button = new JButton(text); 
        button.setIcon(new ImageIcon(iconPath)); 
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(140, 40)); 
        button.setMinimumSize(new Dimension(140, 40)); 
        button.setMaximumSize(new Dimension(140, 40)); 
        button.setBackground(new Color(46, 7, 63)); 
        button.setBorderPainted(true); 
        button.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
        button.setForeground(Color.WHITE); 
        button.setBorder(new CustomRoundedBorder(10, Color.ORANGE));


        // Menambahkan ActionListener untuk mengubah warna latar belakang saat diklik
        button.addActionListener(e -> {
            if (activeButton != null) {
                activeButton.setBackground(new Color(46, 7, 63)); 
            }
            activeButton = button; 
            button.setBackground(Color.ORANGE); 
        });

        return button;
    }
}