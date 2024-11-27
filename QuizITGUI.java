import javax.swing.*;
import java.awt.*;

public class QuizITGUI {
    // Variabel untuk menyimpan tombol aktif
    private static JButton activeButton = null;

    public static void main(String[] args) {
        // Membuat frame utama
        JFrame frame = new JFrame("Two Panel Layout");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // Panel kiri
        JPanel sidebarpanel = new JPanel();
        sidebarpanel.setBackground(new Color(46, 7, 63)); 
        sidebarpanel.setPreferredSize(new Dimension(200, 0)); 
        sidebarpanel.setLayout(new BoxLayout(sidebarpanel, BoxLayout.Y_AXIS)); 

        // Menambahkan jarak 30 piksel sebelum titleLabel
        sidebarpanel.add(Box.createRigidArea(new Dimension(0, 30))); 

        // Mengubah JLabel menjadi "QuizIT" dan mengatur warnanya menjadi oranye
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
        sidebarpanel.add(Box.createVerticalGlue()); // Menjaga tombol di atas
        sidebarpanel.add(Box.createRigidArea(new Dimension(0, 10))); 
        sidebarpanel.add(createSidebarButton("Logout", "icon_logout.png")); 
        sidebarpanel.add(Box.createRigidArea(new Dimension(0, 30))); 

        // Panel kanan
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout()); 
        mainPanel.setBackground(Color.WHITE); 

        // Panel header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(62, 34, 75)); 
        headerPanel.setPreferredSize(new Dimension(150, 80)); 
        headerPanel.add(new JLabel("Header", SwingConstants.CENTER)); 

        // Panel konten
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE); 
        contentPanel.add(new JLabel("Konten di sini", SwingConstants.CENTER)); 

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
        JButton button = new JButton(text); // Menggunakan JButton
        button.setIcon(new ImageIcon(iconPath)); // Mengatur ikon
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(140, 40)); // Ukuran tombol
        button.setMinimumSize(new Dimension(140, 40)); // Ukuran minimum tombol
        button.setMaximumSize(new Dimension(140, 40)); // Ukuran maksimum tombol
        button.setBackground(new Color(46, 7, 63)); // Warna default transparan
        button.setBorderPainted(true); // Mengaktifkan border
        button.setBorder(BorderFactory.createLineBorder(Color.ORANGE)); // Border oranye untuk semua tombol
        button.setForeground(Color.WHITE); // Ubah warna teks tombol menjadi putih
        button.setBorder(new CustomRoundedBorder(10, Color.ORANGE));


        // Menambahkan ActionListener untuk mengubah warna latar belakang saat diklik
        button.addActionListener(e -> {
            // Reset the previously active button's background
            if (activeButton != null) {
                activeButton.setBackground(new Color(46, 7, 63)); // Kembalikan warna latar belakang tombol aktif menjadi transparan
            }
            activeButton = button; // Set tombol yang baru diklik sebagai tombol aktif
            button.setBackground(Color.ORANGE); // Ubah warna tombol yang baru diklik menjadi oranye
        });

        return button;
    }

    
    
}