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

    private JLabel questionLabel;
    private JButton optionA, optionB, optionC, optionD;
    private boolean isAnswered = false;
    private StepProgressBar progressBar;
    private Timer timer;
    private int timeLeft = 900;

    private JLabel timerLabel;
    private JPanel startPanel;

    public Quest(String category, Dimension mainFrameSize) {
        this.category = category;
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
                navigateQuest(1);
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
        }
    }

    private void checkAnswer(String selectedOption) {
        String correctOption = questions.get(currentQuestionIndex).getCorrectOption();

        if (selectedOption.equals(correctOption)) {
            Music correct = new Music("music//correct.wav", false);
            correct.start();
            JOptionPane.showMessageDialog(this, "Correct!");
            progressBar.updateStepColor(currentQuestionIndex, Color.GREEN);
        } else {
            Music wrong = new Music("music//wrong.wav", false);
            wrong.start();
            JOptionPane.showMessageDialog(this, "Wrong!");
            progressBar.updateStepColor(currentQuestionIndex, Color.RED);
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
        super.dispose();
    }

    private void navigateQuest(int direction) {
        int newIndex = currentQuestionIndex + direction;
        if (newIndex >= 0 && newIndex < questions.size()) {
            currentQuestionIndex = newIndex;
            displayQuestion(currentQuestionIndex);
            resetTimer(); // Reset timer setiap navigasi
        }
    }

    private void resetTimer() {
        if (timer != null) {
            timer.stop();
        }
        timeLeft = 900;
        timer.start();
    }

    // Format waktu dalam jam:menit:detik
    private String formatTime(int timeInSeconds) {
        int hours = timeInSeconds / 3600;
        int minutes = (timeInSeconds % 3600) / 60;
        int seconds = timeInSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

}
