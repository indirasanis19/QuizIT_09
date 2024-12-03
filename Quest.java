import javax.swing.*;
import java.awt.*;

public class Quest extends JFrame {
    private String category;

    public Quest(String category, Dimension mainFrameSize) {
        this.category = category;
        initialize(mainFrameSize);
    }

    private void initialize(Dimension mainFrameSize) {
        setTitle(category + " Quiz");
        setSize(mainFrameSize); // Sesuaikan ukuran dengan frame utama
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel Progress Bar
        JProgressBar progressBar = new JProgressBar(0, 5);
        progressBar.setValue(1); // Set langkah awal
        progressBar.setStringPainted(true);
        progressBar.setForeground(Color.YELLOW);
        progressBar.setBackground(Color.LIGHT_GRAY);

        // Panel Pertanyaan
        JPanel questionPanel = new JPanel(new BorderLayout());
        questionPanel.setBackground(Color.YELLOW);

        JLabel questionLabel = new JLabel(
                "<html><div style='text-align:center;'>An interface design application that runs in the browser<br>with team-based collaborative design projects</div></html>",
                SwingConstants.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        questionLabel.setForeground(Color.WHITE);
        questionPanel.add(questionLabel, BorderLayout.CENTER);

        // Panel Opsi Jawaban
        JPanel answerPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        answerPanel.setBackground(Color.WHITE);

        JButton optionA = new JButton("A. FIGMA");
        JButton optionB = new JButton("B. ADOBE XD");
        JButton optionC = new JButton("C. INVISION");
        JButton optionD = new JButton("D. SKETCH");

        // Gaya tombol jawaban
        JButton[] buttons = {optionA, optionB, optionC, optionD};
        for (JButton button : buttons) {
            button.setFont(new Font("Arial", Font.BOLD, 16));
            button.setBackground(Color.LIGHT_GRAY);
            button.setFocusPainted(false);
        }

        answerPanel.add(optionA);
        answerPanel.add(optionB);
        answerPanel.add(optionC);
        answerPanel.add(optionD);

        // Panel Navigasi
        JPanel navigationPanel = new JPanel(new BorderLayout());
        navigationPanel.setBackground(Color.WHITE);

        JButton previousButton = new JButton("◀ Previous");
        JButton nextButton = new JButton("Next ▶");
        JButton skipButton = new JButton("Skip ➡");

        // Timer
        JLabel timerLabel = new JLabel("60", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        timerLabel.setForeground(Color.YELLOW);

        // Tambahkan tombol ke panel navigasi
        navigationPanel.add(previousButton, BorderLayout.WEST);
        navigationPanel.add(timerLabel, BorderLayout.CENTER);
        navigationPanel.add(nextButton, BorderLayout.EAST);

        // Tambahkan semua komponen ke frame
        add(progressBar, BorderLayout.NORTH);
        add(questionPanel, BorderLayout.CENTER);
        add(answerPanel, BorderLayout.SOUTH);
        add(navigationPanel, BorderLayout.PAGE_END);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Quest quest = new Quest("Design Tools", new Dimension(800, 600));
            quest.setVisible(true);
        });
    }
}
