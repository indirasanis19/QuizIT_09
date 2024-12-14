import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class Quest extends JFrame {
    private String category;
    private int currentQuestionIndex = 0;
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
    private int score = 0;
    private Main mainApp;
    private String username;

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
        this.username = username;
        this.mainApp = mainApp;
        this.bgm = new Music("Music//bgm.wav");
        bgm.start();
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

        startPanel = new JPanel();
        startPanel.setBackground(Color.WHITE);
        startPanel.setLayout(new BoxLayout(startPanel, BoxLayout.Y_AXIS));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel categoryLabel = new JLabel(" " + category);
        categoryLabel.setFont(new Font("Arial", Font.BOLD, 30));
        categoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
        categoryLabel.setBorder(BorderFactory.createEmptyBorder(20, 50, 30, 50));
        headerPanel.add(categoryLabel, BorderLayout.NORTH);

        progressBar = new StepProgressBar(15);
        progressBar.setStepClickListener(stepIndex -> {
            currentQuestionIndex = stepIndex;
            displayQuestion(stepIndex);
        });
        headerPanel.add(progressBar, BorderLayout.SOUTH);

        JPanel questionPanel = new JPanel(new BorderLayout());
        questionPanel.setPreferredSize(new Dimension(0, 150));
        questionPanel.setBackground(new Color(245, 201, 29));
        questionPanel.setBorder(BorderFactory.createEmptyBorder(50, 20, 20, 20));

        questionLabel = new JLabel("", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 24));
        questionLabel.setForeground(Color.WHITE);
        questionPanel.add(questionLabel, BorderLayout.NORTH);

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
                if (!isAnswered) {
                    checkAnswer(button.getText().substring(3));
                    disableButtons();
                }
            });
        }

        answerPanel.add(optionA);
        answerPanel.add(optionB);
        answerPanel.add(optionC);
        answerPanel.add(optionD);

        JPanel navigationPanel = new JPanel(new BorderLayout());
        navigationPanel.setBackground(Color.WHITE);
        navigationPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        String previousIconPath = "Image\\pref.png";
        String nextIconPath = "Image\\n" + "ext.png";

        ImageIcon previousIcon = new ImageIcon(previousIconPath);
        ImageIcon nextIcon = new ImageIcon(nextIconPath);

        JButton previousButton = new JButton(previousIcon);
        JButton nextButton = new JButton(nextIcon);
        previousButton.addActionListener(e -> navigateQuestion(-1));
        nextButton.addActionListener(e -> navigateQuestion(1));

        previousButton.setFont(new Font("Arial", Font.BOLD, 14));
        previousButton.setBackground(new Color(245, 201, 29));
        previousButton.setFocusPainted(false);

        nextButton.setFont(new Font("Arial", Font.BOLD, 14));
        nextButton.setBackground(new Color(245, 201, 29));
        nextButton.setForeground(Color.WHITE);
        nextButton.setFocusPainted(false);

        timerLabel = new JLabel(formatTime(timeLeft), SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        timerLabel.setForeground(Color.ORANGE);

        timer = new Timer(1000, e -> {
            if (timeLeft > 0) {
                timeLeft--;
                SwingUtilities.invokeLater(() -> timerLabel.setText(formatTime(timeLeft)));
            } else {
                ((Timer) e.getSource()).stop();
                JOptionPane.showMessageDialog(this, "Time is up!");
                endQuiz();
            }
        });
        timer.start();

        navigationPanel.add(previousButton, BorderLayout.WEST);
        navigationPanel.add(timerLabel, BorderLayout.CENTER);
        navigationPanel.add(nextButton, BorderLayout.EAST);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(headerPanel, BorderLayout.NORTH);
        add(questionPanel, BorderLayout.CENTER);
        add(answerPanel, BorderLayout.SOUTH);
        add(navigationPanel, BorderLayout.PAGE_END);
    }

    private void loadQuestions() {
        try (Connection connection = Koneksi.getConnection()) {
            if (connection != null) {
                String query = "SELECT * FROM soal WHERE kategori = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, category);
                ResultSet resultSet = statement.executeQuery();

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
                    questionAnswered[i] = false;
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

            progressBar.highlightCurrentStep(index);

            if (questionAnswered[index]) {
                disableButtons();
            } else {
                enableButtons();
            }
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
            endQuiz();
        }
    }

    private void checkAnswer(String selectedOption) {
        String correctOption = questions.get(currentQuestionIndex).getCorrectOption();

        if (selectedOption.equals(correctOption)) {
            Music correct = new Music("Music//correct.wav");
            correct.start();
            JOptionPane.showMessageDialog(this, "Correct!");
            progressBar.updateStepColor(currentQuestionIndex, Color.GREEN);
            score++;
        } else {
            Music wrong = new Music("Music//wrong.wav");
            wrong.start();
            JOptionPane.showMessageDialog(this, "Wrong!");
            progressBar.updateStepColor(currentQuestionIndex, Color.RED);
        }

        questionAnswered[currentQuestionIndex] = true;
        isAnswered = true;
        disableButtons();
    }

    @Override
    public void dispose() {
        if (timer != null) {
            timer.stop();
        }
        bgm.stopMusic();
        super.dispose();
    }

    private String formatTime(int timeInSeconds) {
        int hours = timeInSeconds / 3600;
        int minutes = (timeInSeconds % 3600) / 60;
        int seconds = timeInSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private void endQuiz() {
        JDialog dialog = new JDialog(this, "Quiz Complete", true);
        dialog.setSize(600, 400);
        dialog.setLayout(new BorderLayout());

        BackgroundPanel panel = new BackgroundPanel("Image\\skor.png");
        panel.setLayout(new BorderLayout());

        JPanel scorePanel = new JPanel();
        scorePanel.setLayout(new GridBagLayout());
        scorePanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);

        JLabel titleLabel = new JLabel("YOUR SCORE");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        scorePanel.add(titleLabel, gbc);

        JLabel scoreLabel = new JLabel(" " + score);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 80));
        scoreLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        scorePanel.add(scoreLabel, gbc);

        panel.add(scorePanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton completeButton = new JButton("Complete");
        completeButton.setFont(new Font("Arial", Font.BOLD, 18));
        completeButton.setForeground(Color.WHITE);
        completeButton.setBackground(new Color(46, 7, 63));
        completeButton.setFocusPainted(false);
        completeButton.setBorderPainted(true);
        completeButton.setBorder(new CustomRoundedBorder(10, new Color(46, 7, 63)));

        completeButton.addActionListener(e -> {
            updatePlayerScore(username, score);
            Main.updatePlayerStats(username, score, category.toLowerCase());
            bgm.stopMusic();
            this.dispose();
            mainApp.showMainScreen(username);
        });

        buttonPanel.add(completeButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        dialog.add(panel, BorderLayout.CENTER);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        bgm.stopMusic();
    }

    public void updatePlayerScore(String username, int newScore) {
        String query = "UPDATE pemain SET skor_terakhir = ?, skor_tertinggi = GREATEST(skor_tertinggi, ?) WHERE nama = ?";
        try (Connection connection = Koneksi.getConnection();
                PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, newScore);
            stmt.setInt(2, newScore);
            stmt.setString(3, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}