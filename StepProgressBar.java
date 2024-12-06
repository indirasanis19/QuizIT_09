import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StepProgressBar extends JPanel {
    private List<JLabel> stepLabels = new ArrayList<>();

    public StepProgressBar(int totalSteps) {
        setLayout(new GridLayout(1, totalSteps, 5, 0)); // Layout horizontal dengan jarak antar langkah
        setBackground(Color.WHITE);

        for (int i = 1; i <= totalSteps; i++) {
            JLabel stepLabel = new JLabel(String.valueOf(i));
            stepLabel.setFont(new Font("Arial", Font.BOLD, 16));
            stepLabel.setOpaque(true);
            stepLabel.setBackground(Color.LIGHT_GRAY); // Warna default
            stepLabel.setForeground(Color.DARK_GRAY);
            stepLabel.setHorizontalAlignment(SwingConstants.CENTER);
            stepLabel.setPreferredSize(new Dimension(30, 30)); // Ukuran persegi
            stepLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

            // Simpan referensi langkah
            stepLabels.add(stepLabel);
            add(stepLabel);
        }
    }

    public void updateProgress(int currentStep) {
        // Reset warna langkah sebelumnya
        for (int i = 0; i < stepLabels.size(); i++) {
            if (i < currentStep) {
                stepLabels.get(i).setBackground(new Color(255, 247, 212)); 
                stepLabels.get(i).setForeground(Color.BLACK);
            } else {
                stepLabels.get(i).setBackground(Color.LIGHT_GRAY); // Warna default
                stepLabels.get(i).setForeground(Color.DARK_GRAY);
            }
        }
    }

}
