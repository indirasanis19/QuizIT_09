import java.awt.*;
import java.io.File;
import java.io.IOException;
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
        contentPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        contentPanel.setLayout(new GridLayout(1, 1));

        // Panel kiri untuk search bar
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setPreferredSize(new Dimension(550, 30));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(new CustomRoundedBorder(10, Color.GRAY));

        // JTextField (search field)
        JTextField searchField = new JTextField();
        searchField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 35));
        searchField.setPreferredSize(new Dimension(500, 30));

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

        // Menambahkan searchPanel ke panel kiri
        JPanel leftContentPanel = new JPanel(new BorderLayout());
        leftContentPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));
        leftContentPanel.setBackground(Color.WHITE);
        leftContentPanel.add(searchPanel, BorderLayout.NORTH);

        // Category Panel
        JPanel categoriesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        categoriesPanel.setBackground(Color.WHITE);
        categoriesPanel.setBorder(BorderFactory.createEmptyBorder(20, 5, 15, 5));

        // Daftar kategori dan ikon
        String[] categories = { "HTML", "CSS", "C++", "PYTHON", "Java", "Javascript" };
        String[] iconPaths = {
                "D:\\PBO\\QuizIT_09\\Image\\HTML 1.png",
                "D:\\PBO\\QuizIT_09\\Image\\css.png",
                "D:\\PBO\\QuizIT_09\\Image\\C++1.png",
                "D:\\PBO\\QuizIT_09\\Image\\Python.png",
                "D:\\PBO\\QuizIT_09\\Image\\java.png",
                "D:\\PBO\\QuizIT_09\\Image\\javascript (2) 1.png"
        };
        Font customFont = null;
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("D:\\PBO\\QuizIT_09\\fonts\\Poppins-Bold.ttf"))
                    .deriveFont(10f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        // Set layout untuk categoriesPanel
        categoriesPanel.setLayout(new BoxLayout(categoriesPanel, BoxLayout.Y_AXIS));

        // Membuat panel untuk tombol kategori
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(Color.WHITE);

        // Membuat tombol untuk setiap kategori
        for (int i = 0; i < categories.length; i++) {
            JButton categoryButton = new JButton(categories[i]);
            categoryButton.setIcon(new ImageIcon(iconPaths[i]));
            categoryButton.setPreferredSize(new Dimension(90, 60));
            categoryButton.setBackground(Color.WHITE);
            categoryButton.setHorizontalTextPosition(SwingConstants.CENTER);
            categoryButton.setVerticalTextPosition(SwingConstants.BOTTOM);

            // Mengatur ukuran font
            categoryButton.setFont(customFont);
            categoryButton.setMargin(new Insets(10, 50, 10, 10));
            categoryButton.setBorder(new CustomRoundedBorder(10, Color.GRAY));
            buttonPanel.add(categoryButton);
        }

        categoriesPanel.add(buttonPanel);

        // Membuat panel untuk label "Recent Activity"
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        labelPanel.setBackground(Color.WHITE);

        JLabel recentActivityLabel = new JLabel("Recent Activity");
        recentActivityLabel.setFont(new Font("Arial", Font.BOLD, 16));
        labelPanel.add(recentActivityLabel);
        categoriesPanel.add(labelPanel);

        // Panel untuk question count and score
        for (int i = 0; i < categories.length; i++) {
            JPanel scorePanel = new JPanel();
            scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.Y_AXIS));
            scorePanel.setPreferredSize(new Dimension(400, 80));

            JPanel iconTextPanel = new JPanel();
            iconTextPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            iconTextPanel.setBackground(Color.WHITE);

            iconTextPanel.add(new JLabel(new ImageIcon(iconPaths[i])));
            JPanel textPanel = new JPanel();
            textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
            textPanel.setBackground(Color.WHITE);
            textPanel.setPreferredSize(new Dimension(150, 30));

            JLabel categoryLabel = new JLabel(categories[i]);
            categoryLabel.setFont(new Font("Arial", Font.BOLD, 14));
            textPanel.add(categoryLabel);

            JLabel questionCountLabel = new JLabel("30 Questions");
            questionCountLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            textPanel.add(questionCountLabel);

            iconTextPanel.add(textPanel);

            JPanel combinedPanel = new JPanel();
            combinedPanel.setLayout(new BorderLayout());
            combinedPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
            combinedPanel.setBackground(Color.WHITE);

            JProgressBar scoreBar = new JProgressBar(0, 30);
            scoreBar.setValue(26);
            scoreBar.setString("26/30");
            scoreBar.setStringPainted(true);
            scoreBar.setBackground(new Color(255, 255, 255));
            scoreBar.setForeground(new Color(46, 7, 63));
            scoreBar.setPreferredSize(new Dimension(400, 30));

            combinedPanel.add(iconTextPanel, BorderLayout.NORTH);
            combinedPanel.add(scoreBar, BorderLayout.SOUTH);

            scorePanel.add(combinedPanel);

            categoriesPanel.add(scorePanel);
        }

        categoriesPanel.revalidate();
        categoriesPanel.repaint();

        leftContentPanel.add(categoriesPanel);

        // panel kanan
        JPanel rightContentPanel = new JPanel();
        rightContentPanel.setBackground(Color.LIGHT_GRAY);
        JPanel rightContentPanelImage = new JPanel();
        rightContentPanel.add(rightContentPanelImage);

        // Menambahkan panel kiri dan kanan ke contentPanel
        contentPanel.add(leftContentPanel);
        contentPanel.add(rightContentPanel);

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