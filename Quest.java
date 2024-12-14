import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class Quest extends JFrame {
    private String category;
    private int currentQuestionIndex = 0;
    // private JProgressBar progressBar;
    private List<Question> questions = new ArrayList<>();
    private boolean[] questionAnswered;
    private Music bgm;

    private JLabel questionLabel;
    private JButton optionA, optionB, optionC, optionD;
    private boolean isAnswered = false;
    private StepProgressBar progressBar;
    private Timer timer;
    private int timeLeft = 600;

    private JLabel timerLabel;
    private JPanel startPanel;

    private int score = 0; // Menyimpan skor total

    private Main mainApp; // Reference to Main class
    private String username; // Member variable for username

    // Kelas untuk panel latar belakang
    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String imagePath) {
            backgroundImage = new ImageIcon(imagePath).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public Quest(String category, Dimension mainFrameSize, String username, Main mainApp) {
        this.category = category;
        this.username = username; // Store the username
        this.mainApp = mainApp; // Store the reference
        initialize(mainFrameSize);
        loadQuestions();
        displayQuestion(0);
    }

    private void initialize(Dimension mainFrameSize) {
        setTitle(category + " Quiz");
        setSize(mainFrameSize);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel Opening
        startPanel = new JPanel();
        startPanel.setBackground(Color.WHITE);
        startPanel.setLayout(new BoxLayout(startPanel, BoxLayout.Y_AXIS));

        // Panel Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel categoryLabel = new JLabel(" " + category);
        categoryLabel.setFont(new Font("Arial", Font.BOLD, 30));
        categoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
        categoryLabel.setBorder(BorderFactory.createEmptyBorder(20, 50, 30, 50));
        headerPanel.add(categoryLabel, BorderLayout.NORTH);

        progressBar = new StepProgressBar(15);
        progressBar = new StepProgressBar(15);
        progressBar.setStepClickListener(stepIndex -> {
            currentQuestionIndex = stepIndex;
            displayQuestion(stepIndex);
        });
        headerPanel.add(progressBar, BorderLayout.SOUTH);

        // Panel Pertanyaan
        JPanel questionPanel = new JPanel(new BorderLayout());
        questionPanel.setPreferredSize(new Dimension(0, 150));
        questionPanel.setBackground(new Color(245, 201, 29));
        questionPanel.setBorder(BorderFactory.createEmptyBorder(50, 20, 20, 20));

        questionLabel = new JLabel("", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 24));
        questionLabel.setForeground(Color.WHITE);
        questionPanel.add(questionLabel, BorderLayout.NORTH);

        // Panel Opsi Jawaban
        JPanel answerPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        answerPanel.setBackground(Color.WHITE);
        answerPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
        answerPanel.setPreferredSize(new Dimension(0, 250));

        optionA = new JButton();
        optionB = new JButton();
        optionC = new JButton();
        optionD = new JButton();

        JButton[] buttons = { optionA, optionB, optionC, optionD };

        optionA.setActionCommand("A");
        optionB.setActionCommand("B");
        optionC.setActionCommand("C");
        optionD.setActionCommand("D");

        for (JButton button : buttons) {
            button.setFont(new Font("Arial", Font.PLAIN, 12));
            button.setBackground(Color.LIGHT_GRAY);
            button.setFocusPainted(false);

            button.setText("<html><p style='width: 600px; word-wrap: break-word; text-align:center;'>"
                    + button.getText() + "</p></html>");
            button.addActionListener(e -> {
                if (!isAnswered) { // Hanya proses jika belum dijawab
                    checkAnswer(button.getText().substring(3)); // Action command digunakan untuk jawaban
                    disableButtons(); // Nonaktifkan semua tombol jawaban
                }
            });
        }

        answerPanel.add(optionA);
        answerPanel.add(optionB);
        answerPanel.add(optionC);
        answerPanel.add(optionD);

        // Panel Navigasi
        JPanel navigationPanel = new JPanel(new BorderLayout());
        navigationPanel.setBackground(Color.WHITE);
        navigationPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JButton previousButton = new JButton("◀ Previous");
        JButton nextButton = new JButton("Next ▶");
        // JButton skipButton = new JButton("Skip ➡");
        previousButton.addActionListener(e -> navigateQuestion(-1));
        nextButton.addActionListener(e -> navigateQuestion(1));

        previousButton.setFont(new Font("Arial", Font.BOLD, 14));
        previousButton.setBackground(Color.WHITE);
        previousButton.setFocusPainted(false);

        nextButton.setFont(new Font("Arial", Font.BOLD, 14));
        nextButton.setBackground(new Color(245, 201, 29));
        nextButton.setForeground(Color.WHITE);
        nextButton.setFocusPainted(false);

        // Timer
        timerLabel = new JLabel(formatTime(timeLeft), SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        timerLabel.setForeground(Color.ORANGE);

        // Timer yang berjalan setiap 1 detik
        timer = new Timer(1000, e -> {
            if (timeLeft > 0) {
                timeLeft--;
                SwingUtilities.invokeLater(() -> timerLabel.setText(formatTime(timeLeft)));
            } else {
                ((Timer) e.getSource()).stop();
                JOptionPane.showMessageDialog(this, "Time is up!");
                endQuiz(); // Panggil endQuiz ketika waktu habis
            }
        });
        timer.start();

        // Tambahkan tombol ke panel navigasi
        navigationPanel.add(previousButton, BorderLayout.WEST);
        navigationPanel.add(timerLabel, BorderLayout.CENTER);
        navigationPanel.add(nextButton, BorderLayout.EAST);

        // Tambahkan semua komponen ke frame
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(headerPanel, BorderLayout.NORTH);
        add(questionPanel, BorderLayout.CENTER);
        add(answerPanel, BorderLayout.SOUTH);
        add(navigationPanel, BorderLayout.PAGE_END);
    }

    private void loadQuestions() {
        try (Connection connection = Koneksi.getConnection()) {
            if (connection != null) {
                // Query hanya mengambil soal berdasarkan kategori
                String query = "SELECT * FROM soal WHERE kategori = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, category);
                ResultSet resultSet = statement.executeQuery();

                // Menambahkan soal ke dalam list
                while (resultSet.next()) {
                    String questionText = resultSet.getString("pertanyaan");
                    String optionA = resultSet.getString("pilihan_A");
                    String optionB = resultSet.getString("pilihan_B");
                    String optionC = resultSet.getString("pilihan_C");
                    String optionD = resultSet.getString("pilihan_D");
                    String correctOption = resultSet.getString("jawaban_benar");

                    questions.add(new Question(questionText, optionA, optionB, optionC, optionD, correctOption));
                }
                questionAnswered = new boolean[questions.size()];
                for (int i = 0; i < questionAnswered.length; i++) {
                    questionAnswered[i] = false; // Semua soal awalnya belum dijawab
                }
                progressBar.updateProgress(0);

                System.out.println("Soal berhasil dimuat untuk kategori: " + category);
            } else {
                System.err.println("Koneksi ke database gagal.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void displayQuestion(int index) {
        if (index >= 0 && index < questions.size()) {
            Question question = questions.get(index);
            questionLabel.setText("<html><p style='width: 600px; word-wrap: break-word; text-align:center;'>"
                    + question.getQuestionText() + "</p></html>");
            optionA.setText("A. " + question.getOptionA());
            optionB.setText("B. " + question.getOptionB());
            optionC.setText("C. " + question.getOptionC());
            optionD.setText("D. " + question.getOptionD());
            // progressBar.updateProgress(index + 1);

            progressBar.highlightCurrentStep(index);

            if (questionAnswered[index]) {
                disableButtons();
            } else {
                enableButtons();
            }

            // Reset status jawaban
            isAnswered = false;
        }
    }

    private void disableButtons() {
        optionA.setEnabled(false);
        optionB.setEnabled(false);
        optionC.setEnabled(false);
        optionD.setEnabled(false);
    }

    private void enableButtons() {
        optionA.setEnabled(true);
        optionB.setEnabled(true);
        optionC.setEnabled(true);
        optionD.setEnabled(true);
    }

    private void navigateQuestion(int direction) {
        int newIndex = currentQuestionIndex + direction;
        if (newIndex >= 0 && newIndex < questions.size()) {
            currentQuestionIndex = newIndex;
            displayQuestion(currentQuestionIndex);
        } else {
            // Jika sudah menjawab semua pertanyaan, panggil endQuiz
            endQuiz();
        }
    }

    private void checkAnswer(String selectedOption) {
        String correctOption = questions.get(currentQuestionIndex).getCorrectOption();

        if (selectedOption.equals(correctOption)) {
            Music correct = new Music("music//correct.wav");
            correct.start();
            JOptionPane.showMessageDialog(this, "Correct!");
            progressBar.updateStepColor(currentQuestionIndex, Color.GREEN);
            score++; // Tambah skor untuk jawaban benar
        } else {
            Music wrong = new Music("music//wrong.wav");
            wrong.start();
            JOptionPane.showMessageDialog(this, "Wrong!");
            progressBar.updateStepColor(currentQuestionIndex, Color.RED);
            // Tidak perlu menambah skor untuk jawaban salah
        }

        questionAnswered[currentQuestionIndex] = true;
        isAnswered = true; // Tandai bahwa soal sudah dijawab
        disableButtons(); // Nonaktifkan tombol jawaban
    }

    @Override
    public void dispose() {
        if (timer != null) {
            timer.stop();
        }
        bgm = new Music("Music//bgm.wav");
        bgm.stopMusic();
        super.dispose();
    }

    // private void navigateQuest(int direction) {
    //     int newIndex = currentQuestionIndex + direction;
    //     if (newIndex >= 0 && newIndex < questions.size()) {
    //         currentQuestionIndex = newIndex;
    //         displayQuestion(currentQuestionIndex);
    //         resetTimer(); // Reset timer setiap navigasi
    //     }
    // }

    // private void resetTimer() {
    //     if (timer != null) {
    //         timer.stop();
    //     }
    //     timeLeft = 300;
    //     timer.start();
    // }

    // Format waktu dalam jam:menit:detik
    private String formatTime(int timeInSeconds) {
        int hours = timeInSeconds / 3600;
        int minutes = (timeInSeconds % 3600) / 60;
        int seconds = timeInSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private void endQuiz() {
        // Create dialog
        JDialog dialog = new JDialog(this, "Quiz Complete", true);
        dialog.setSize(600, 400);
        dialog.setLayout(new BorderLayout());

        // Use custom panel with background image
        BackgroundPanel panel = new BackgroundPanel("path/to/your/background/image.jpg");
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Create a panel for score labels
        JPanel scorePanel = new JPanel();
        scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.Y_AXIS)); // Vertical layout
        scorePanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the score panel

        // Label for "YOUR SCORE"
        JLabel titleLabel = new JLabel("YOUR SCORE");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.ORANGE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        scorePanel.add(titleLabel);

        // Label for the actual score
        JLabel scoreLabel = new JLabel(" " + score);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 48)); // Larger font for score
        scoreLabel.setForeground(Color.ORANGE);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        scorePanel.add(scoreLabel);

        // Add score panel to the main panel
        panel.add(Box.createVerticalGlue()); // Add flexible space before the score
        panel.add(scorePanel); // Add score panel
        panel.add(Box.createVerticalStrut(20)); // Add space between score and button

        // Panel for button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Center the button

        // Complete button
        JButton completeButton = new JButton("Complete");
        completeButton.setFont(new Font("Arial", Font.BOLD, 18));
        completeButton.setForeground(Color.WHITE);
        completeButton.setBackground(new Color(46, 7, 63)); // Background color
        completeButton.setFocusPainted(false);
        completeButton.setBorderPainted(true);
        completeButton.setBorder(new CustomRoundedBorder(10, new Color(46, 7, 63))); // Custom border

        completeButton.addActionListener(e -> {
            bgm = new Music("Music//bgm.wav");
            bgm.stopMusic();
            dialog.dispose(); // Close dialog
            this.dispose(); // Close Quest window
            mainApp.showMainScreen(username); // Return to showMainScreen
        });
        
        buttonPanel.add(completeButton);

        // Add button panel to the main panel
        panel.add(buttonPanel); // Add button panel

        // Add background panel to dialog
        dialog.add(panel, BorderLayout.CENTER); // Place panel in the center

        dialog.setLocationRelativeTo(this); // Center the dialog
        dialog.setVisible(true);
    }
}