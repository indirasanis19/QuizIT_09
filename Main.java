import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;

public class Main {
    private static JButton activeButton = null;
    private JFrame welcomeFrame;
    private JFrame mainFrame;
    private boolean isInMainScreen = true; // Menyimpan status tampilan saat ini
    private String username;
    private int id;
    private ArrayList<PlayerScore> leaderboardData = new ArrayList<>(); // Menyimpan data leaderboard

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().showWelcomeScreen());
    }

    public void showWelcomeScreen() {
        // Frame untuk layar Welcome
        welcomeFrame = new JFrame("QuizIT");
        welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        welcomeFrame.setSize(800, 500);
        welcomeFrame.setLayout(new GridLayout(1, 2));
        welcomeFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Panel kiri
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // Menambahkan komponen dengan CENTER_ALIGNMENT
        JLabel logoLabel = new JLabel("QuizIT", SwingConstants.CENTER);
        logoLabel.setFont(new Font("Arial", Font.BOLD, 36));
        logoLabel.setForeground(Color.ORANGE);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel welcomeLabel = new JLabel("Welcome!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        welcomeLabel.setForeground(Color.GRAY);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel enterNameLabel = new JLabel("Enter Name / Username:", SwingConstants.CENTER);
        enterNameLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        enterNameLabel.setForeground(Color.GRAY);
        enterNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField nameField = new JTextField("");
        nameField.setFont(new Font("Arial", Font.PLAIN, 16));
        nameField.setForeground(Color.GRAY);
        nameField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));
        nameField.setMaximumSize(new Dimension(300, 40));
        nameField.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel enterPassword = new JLabel("Enter Password :", SwingConstants.CENTER);
        enterPassword.setFont(new Font("Arial", Font.PLAIN, 18));
        enterPassword.setForeground(Color.GRAY);
        enterPassword.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPasswordField pwField = new JPasswordField("");
        pwField.setFont(new Font("Arial", Font.PLAIN, 16));
        pwField.setForeground(Color.GRAY);
        pwField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));
        pwField.setMaximumSize(new Dimension(300, 40));
        pwField.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel regis = new JLabel("Don't have an account? register here :", SwingConstants.CENTER);
        regis.setFont(new Font("Arial", Font.PLAIN, 18));
        regis.setForeground(Color.GRAY);
        regis.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton startButton = new JButton("Log in");
        startButton.setFont(new Font("Arial", Font.BOLD, 18));
        startButton.setForeground(Color.WHITE);
        startButton.setBackground(new Color(46, 7, 63));
        startButton.setFocusPainted(false);
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton regBtn = new JButton("Register");
        regBtn.setFont(new Font("Arial", Font.BOLD, 18));
        regBtn.setForeground(Color.WHITE);
        regBtn.setBackground(new Color(46, 7, 63));
        regBtn.setFocusPainted(false);
        regBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Event saat tombol Start diklik
        startButton.addActionListener(e -> {
            String username = nameField.getText().trim();
            char[] passwordChars = pwField.getPassword();
            String password = new String(passwordChars);

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(welcomeFrame,
                        "Username/password cannot be empty",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                if (UserAuth.login(username, password)) { // Periksa hasil login
                    showMainScreen(username);
                    welcomeFrame.dispose();
                } else {
                    //
                }
            }
        });

        regBtn.addActionListener(e -> {
            welcomeFrame.getContentPane().removeAll(); // Menghapus semua komponen yang ada
            showReg(); // Menampilkan layar registrasi
            welcomeFrame.revalidate();
            welcomeFrame.repaint();
        });

        leftPanel.add(Box.createRigidArea(new Dimension(0, 200)));
        leftPanel.add(logoLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        leftPanel.add(welcomeLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        leftPanel.add(enterNameLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        leftPanel.add(nameField);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        leftPanel.add(enterPassword);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        leftPanel.add(pwField);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        leftPanel.add(startButton);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        leftPanel.add(regis);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        leftPanel.add(regBtn);

        // Panel kanan (gambar maskot)
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(new Color(46, 7, 63));
        JLabel mascotLabel = new JLabel(new ImageIcon("Image\\maskot 1.png"));
        mascotLabel.setHorizontalAlignment(SwingConstants.CENTER);
        rightPanel.add(mascotLabel);

        welcomeFrame.add(leftPanel);
        welcomeFrame.add(rightPanel);
        welcomeFrame.setVisible(true);
    }

    public void showReg() {
        welcomeFrame.getContentPane().removeAll(); // Membersihkan frame untuk konten registrasi
        JPanel regPanel = new JPanel();
        regPanel.setLayout(new BoxLayout(regPanel, BoxLayout.Y_AXIS));
        regPanel.setBackground(Color.WHITE);
        regPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JLabel regLabel = new JLabel("Register", SwingConstants.CENTER);
        regLabel.setFont(new Font("Arial", Font.BOLD, 36));
        regLabel.setForeground(Color.ORANGE);
        regLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel enterNameLabel = new JLabel("Enter Name / Username:", SwingConstants.CENTER);
        enterNameLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        enterNameLabel.setForeground(Color.GRAY);
        enterNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField nameField = new JTextField("");
        nameField.setFont(new Font("Arial", Font.PLAIN, 16));
        nameField.setForeground(Color.GRAY);
        nameField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));
        nameField.setMaximumSize(new Dimension(300, 40));
        nameField.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel enterPassword = new JLabel("Enter Password :", SwingConstants.CENTER);
        enterPassword.setFont(new Font("Arial", Font.PLAIN, 18));
        enterPassword.setForeground(Color.GRAY);
        enterPassword.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPasswordField pwField = new JPasswordField();
        pwField.setFont(new Font("Arial", Font.PLAIN, 16));
        pwField.setForeground(Color.GRAY);
        pwField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));
        pwField.setMaximumSize(new Dimension(300, 40));
        pwField.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel confirmPasswordLabel = new JLabel("Confirm Password :", SwingConstants.CENTER);
        confirmPasswordLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        confirmPasswordLabel.setForeground(Color.GRAY);
        confirmPasswordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPasswordField confirmPwField = new JPasswordField();
        confirmPwField.setFont(new Font("Arial", Font.PLAIN, 16));
        confirmPwField.setForeground(Color.GRAY);
        confirmPwField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));
        confirmPwField.setMaximumSize(new Dimension(300, 40));
        confirmPwField.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.BOLD, 18));
        registerButton.setForeground(Color.WHITE);
        registerButton.setBackground(new Color(46, 7, 63));
        registerButton.setFocusPainted(false);
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton backButton = new JButton("Back to Login");
        backButton.setFont(new Font("Arial", Font.BOLD, 18));
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(50, 27, 103));
        backButton.setFocusPainted(false);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Event untuk tombol regist
        registerButton.addActionListener(e -> {
            String username = nameField.getText().trim();
            char[] passwordChars = pwField.getPassword();
            char[] confirmPasswordChars = confirmPwField.getPassword();

            String password = new String(passwordChars); // Mengonversi ke String
            String confirmPassword = new String(confirmPasswordChars);

            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(welcomeFrame,
                        "Username/password cannot be empty",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                UserAuth.register(username, password, confirmPassword); // Panggil metode register sesuai implementasi
                                                                        // Anda
            }

            // Membersihkan data password untuk keamanan
            java.util.Arrays.fill(passwordChars, ' ');
            java.util.Arrays.fill(confirmPasswordChars, ' ');
        });

        // Event untuk tombol back
        backButton.addActionListener(e -> {
            welcomeFrame.getContentPane().removeAll(); // Menghapus semua komponen yang ada
            showWelcomeScreen(); // Kembali ke layar login
            welcomeFrame.revalidate();
            welcomeFrame.repaint();
        });

        regPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        regPanel.add(regLabel);
        regPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        regPanel.add(enterNameLabel);
        regPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        regPanel.add(nameField);
        regPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        regPanel.add(enterPassword);
        regPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        regPanel.add(pwField);
        regPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        regPanel.add(confirmPasswordLabel);
        regPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        regPanel.add(confirmPwField);
        regPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        regPanel.add(registerButton);
        regPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        regPanel.add(backButton);

        welcomeFrame.add(regPanel); // Menambahkan panel ke frame
        welcomeFrame.revalidate();
        welcomeFrame.repaint();
    }

    public void showMainScreen(String username) {
        // Frame untuk layar utama
        mainFrame = new JFrame("QuizIT");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Sidebar
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setBackground(new Color(46, 7, 63));
        sidebarPanel.setPreferredSize(new Dimension(200, 0));
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        sidebarPanel.add(createSidebarButton("Learn", "Image\\home-outline.png"));
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebarPanel.add(createSidebarButton("Profile", "Image\\person-outline.png"));
        sidebarPanel.add(Box.createVerticalGlue());
        sidebarPanel.add(createSidebarButton("Logout", "Image\\Logout.png"));
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(62, 34, 75));
        headerPanel.setPreferredSize(new Dimension(150, 80));
        headerPanel.setLayout(new BorderLayout());

        // Menambahkan titleLabel ke dalam headerPanel
        JLabel titelheader = new JLabel("QuizIT", SwingConstants.CENTER);
        titelheader.setForeground(Color.ORANGE);
        titelheader.setFont(new Font("Arial", Font.BOLD, 34));
        titelheader.setBorder(BorderFactory.createEmptyBorder(10, 50, 0, 10));

        // Menambahkan titleLabel ke headerPanel
        headerPanel.add(titelheader, BorderLayout.WEST);

        JPanel userInfoPanel = new JPanel();
        userInfoPanel.setBackground(new Color(62, 34, 75));
        userInfoPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JPanel nameIdPanel = new JPanel();
        nameIdPanel.setLayout(new BoxLayout(nameIdPanel, BoxLayout.Y_AXIS));
        nameIdPanel.setBackground(new Color(0, 0, 0, 0));
        nameIdPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        JLabel userNameLabel = new JLabel("Welcome, " + username);
        userNameLabel.setForeground(Color.WHITE);
        userNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        userNameLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        JLabel userIdLabel = new JLabel("ID-" + id);
        userIdLabel.setForeground(Color.WHITE);
        userIdLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        nameIdPanel.add(userNameLabel);
        nameIdPanel.add(userIdLabel);

        JLabel userPointsLabel = new JLabel("💎 0");
        userPointsLabel.setForeground(Color.YELLOW);
        userPointsLabel.setBorder(BorderFactory.createEmptyBorder(10, 50, 0, 30));

        userInfoPanel.add(nameIdPanel);
        userInfoPanel.add(userPointsLabel);
        headerPanel.add(userInfoPanel, BorderLayout.EAST);

        // Konten utama
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
        ImageIcon searchIcon = new ImageIcon("Image\\search-outline.png");
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
                "Image\\HTML 1.png",
                "Image\\css.png",
                "Image\\C++1.png",
                "Image\\Python.png",
                "Image\\java.png",
                "Image\\javascript (2) 1.png"
        };
        Font customFont = null;
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("fonts\\Poppins-Bold.ttf"))
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
            final String category = categories[i]; // Create a final variable
            JButton categoryButton = new JButton(category);
            categoryButton.setIcon(new ImageIcon(iconPaths[i]));
            categoryButton.setPreferredSize(new Dimension(90, 60));
            categoryButton.setBackground(Color.WHITE);
            categoryButton.setHorizontalTextPosition(SwingConstants.CENTER);
            categoryButton.setVerticalTextPosition(SwingConstants.BOTTOM);

            // Mengatur ukuran font
            categoryButton.setFont(customFont);
            categoryButton.setMargin(new Insets(10, 50, 10, 10));
            categoryButton.setBorder(new CustomRoundedBorder(10, Color.GRAY));

            // Mengatur ukuran font
            categoryButton.setFont(customFont);
            categoryButton.setMargin(new Insets(10, 50, 10, 10));
            categoryButton.setBorder(new CustomRoundedBorder(10, Color.GRAY));

            buttonPanel.add(categoryButton);

            categoryButton.addActionListener(e -> {
                SwingUtilities.invokeLater(() -> {
                    Music bgm = new Music("music//bgm.wav", false);
                    bgm.start();
                    new Quest(category, mainFrame.getSize()).setVisible(true);
                    mainFrame.dispose();
                });
            });
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

        // Panel kanan untuk card
        JPanel rightContentPanel = new JPanel();
        rightContentPanel.setBackground(Color.WHITE);
        rightContentPanel.setLayout(new BorderLayout());
        rightContentPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 30, 20));

        // Tabel leaderboard (rank, player, score)
        String[] columnNames = { "Rank", "Player", "Score" };
        Object[][] data = getLeaderboardData();

        JTable leaderboardTable = new JTable(data, columnNames);
        leaderboardTable.setFillsViewportHeight(true);
        leaderboardTable.setFont(new Font("Arial", Font.PLAIN, 16));
        leaderboardTable.setForeground(Color.BLACK);
        leaderboardTable.setRowHeight(30);

        // Menambahkan JScrollPane untuk tabel
        JScrollPane scrollPane = new JScrollPane(leaderboardTable);
        rightContentPanel.add(scrollPane, BorderLayout.CENTER);

        // // Mengambil gambar dan mengubah ukurannya
        // ImageIcon originalIcon = new
        // ImageIcon("D:\\PBO\\QuizIT_09\\Image\\maskot2.png");
        // Image image = originalIcon.getImage().getScaledInstance(150, 150,
        // Image.SCALE_SMOOTH); // Mengatur ukuran ke
        // // 150x150 (ubah sesuai
        // // kebutuhan)
        // ImageIcon resizedIcon = new ImageIcon(image);

        // // Menambahkan gambar maskot yang telah diubah ukurannya
        // JLabel mascotLabel = new JLabel(resizedIcon);
        // mascotLabel.setHorizontalAlignment(SwingConstants.CENTER);
        // rightContentPanel.add(mascotLabel, BorderLayout.SOUTH);

        // Membuat panel teks
        JPanel textPanel = new JPanel();
        textPanel.setBackground(Color.ORANGE);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        JLabel cardLabel = new JLabel("Leaderboard");
        cardLabel.setForeground(Color.WHITE);
        cardLabel.setFont(new Font("Arial", Font.BOLD, 24));
        cardLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        // Menambahkan label ke panel teks
        textPanel.add(cardLabel);

        // Membuat cardPanel untuk menampung gambar, teks, dan tombol
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BorderLayout());
        cardPanel.setBackground(Color.WHITE);

        // // Menambahkan gambar dan teks ke panel card
        // cardPanel.add(imageLabel, BorderLayout.CENTER);
        cardPanel.add(textPanel, BorderLayout.NORTH);
        rightContentPanel.add(cardPanel, BorderLayout.NORTH);

        // Menambahkan panel kiri dan kanan ke contentPanel
        contentPanel.add(leftContentPanel);
        contentPanel.add(rightContentPanel);

        mainFrame.add(sidebarPanel, BorderLayout.WEST);
        mainFrame.add(headerPanel, BorderLayout.NORTH);
        mainFrame.add(contentPanel, BorderLayout.CENTER);
        mainFrame.setVisible(true);
    }

    private JButton createSidebarButton(String text, String iconPath) {
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
            if (text.equals("Logout")) {
                int confirmed = JOptionPane.showConfirmDialog(mainFrame, "Are you sure you want to logout?",
                        "Logout Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirmed == JOptionPane.YES_OPTION) {
                    mainFrame.dispose();
                    showWelcomeScreen();
                }
            } else if (text.equals("Profile")) {
                showProfileScreen(username); // Panggil metode untuk menampilkan profil
                isInMainScreen = false; // Update status tampilan
            } else if (text.equals("Learn")) {
                if (!isInMainScreen) {
                    mainFrame.dispose();
                    showMainScreen(username); // Beralih ke showMainScreen jika di showProfileScreen
                    isInMainScreen = true; // Update status tampilan
                }
                // Jika sudah di showMainScreen, tidak perlu melakukan tindakan apa pun
            } else {
                if (activeButton != null) {
                    activeButton.setBackground(new Color(46, 7, 63));
                }
                activeButton = button;
                button.setBackground(Color.ORANGE);
                JOptionPane.showMessageDialog(mainFrame, text + " button clicked!", "Info",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        return button;
    }

    // Kelas untuk menyimpan data pemain dan skor
    public static class PlayerScore {
        private String name;
        private int score;

        public PlayerScore(String name, int score) {
            this.name = name;
            this.score = score;
        }

        public String getName() {
            return name;
        }

        public int getScore() {
            return score;
        }
    }

    private Object[][] getLeaderboardData() {
        // Konversi data leaderboard menjadi array 2D untuk JTable
        Object[][] data = new Object[leaderboardData.size()][3];
        for (int i = 0; i < leaderboardData.size(); i++) {
            PlayerScore player = leaderboardData.get(i);
            data[i][0] = i + 1; // Rank
            data[i][1] = player.getName(); // Player name
            data[i][2] = player.getScore(); // Score
        }
        return data;
    }

    public void showProfileScreen(String username) {
        // Hapus konten sebelumnya
        mainFrame.getContentPane().removeAll();

        // Tambahkan sidebar dan header
        mainFrame.add(createSidebar(), BorderLayout.WEST);
        mainFrame.add(createHeader(username), BorderLayout.NORTH);

        // Panel untuk konten profil
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
        profilePanel.setBackground(Color.WHITE);
        profilePanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JLabel profileLabel = new JLabel("Profile Information", SwingConstants.CENTER);
        profileLabel.setFont(new Font("Arial", Font.BOLD, 50));
        profileLabel.setForeground(Color.ORANGE);

        JLabel usernameLabel = new JLabel("Username: " + username);
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 40));

        JLabel scoreLabel = new JLabel("Score: " + getUserScore(username));
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 40));

        // Tambahkan gambar latar belakang
        ImageIcon backgroundImage = new ImageIcon("D:\\PBO\\QuizIT_09\\Image\\Backgroundprofil.png");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setLayout(new BorderLayout());

        // Tambahkan panel profil ke dalam backgroundLabel
        profilePanel.setOpaque(false);
        backgroundLabel.add(profilePanel, BorderLayout.CENTER);

        profilePanel.add(profileLabel);
        profilePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        profilePanel.add(usernameLabel);
        profilePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        profilePanel.add(scoreLabel);

        // Tambahkan panel profil ke center
        mainFrame.add(backgroundLabel, BorderLayout.CENTER);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private JPanel createSidebar() {
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setBackground(new Color(46, 7, 63));
        sidebarPanel.setPreferredSize(new Dimension(200, 0));
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        sidebarPanel.add(createSidebarButton("Learn", "Image\\home-outline.png"));
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebarPanel.add(createSidebarButton("Profile", "Image\\person-outline.png"));
        sidebarPanel.add(Box.createVerticalGlue());
        sidebarPanel.add(createSidebarButton("Logout", "Image\\Logout.png"));
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        return sidebarPanel;
    }

    private JPanel createHeader(String username) {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(62, 34, 75));
        headerPanel.setPreferredSize(new Dimension(150, 80));
        headerPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("QuizIT", SwingConstants.CENTER);
        titleLabel.setForeground(Color.ORANGE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 34));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 50, 0, 10));

        headerPanel.add(titleLabel, BorderLayout.WEST);

        JPanel userInfoPanel = new JPanel();
        userInfoPanel.setBackground(new Color(62, 34, 75));
        userInfoPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        return headerPanel;
    }

    // Metode untuk mendapatkan skor pengguna (misalnya)
    private int getUserScore(String username) {
        // Logika untuk mendapatkan skor pengguna dari leaderboard atau database
        return 100; // Contoh nilai
    }

}
