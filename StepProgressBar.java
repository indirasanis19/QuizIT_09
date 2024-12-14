import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class StepProgressBar extends JPanel {
    private int stepCount;
    private List<JLabel> stepLabels;
    private List<Color> stepColors;
    private StepClickListener clickListener;

    public StepProgressBar(int stepCount) {
        this.stepCount = stepCount;
        this.stepLabels = new ArrayList<>();
        this.stepColors = new ArrayList<>();
        setLayout(new GridLayout(1, stepCount, 5, 0));

        for (int i = 0; i < stepCount; i++) {
            JLabel stepLabel = new JLabel(String.valueOf(i + 1), SwingConstants.CENTER);
            stepLabel.setOpaque(true);
            stepLabel.setBackground(Color.LIGHT_GRAY);
            stepLabel.setForeground(Color.DARK_GRAY);
            stepLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            stepLabel.setFont(new Font("Arial", Font.BOLD, 14));
            stepLabel.setHorizontalAlignment(SwingConstants.CENTER);
            stepLabel.setPreferredSize(new Dimension(30, 30));
            stepLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (clickListener != null) {
                        clickListener.onStepClicked(Integer.parseInt(stepLabel.getText()) - 1);
                    }
                }
            });
            stepLabels.add(stepLabel);
            stepColors.add(Color.LIGHT_GRAY);
            add(stepLabel);
        }
    }

    public void updateProgress(int step) {
        for (int i = 0; i < stepLabels.size(); i++) {
            stepLabels.get(i).setBackground(i < step ? Color.ORANGE : Color.LIGHT_GRAY);
        }
    }

    public void updateStepColor(int stepIndex, Color color) {
        if (stepIndex >= 0 && stepIndex < stepLabels.size()) {
            stepLabels.get(stepIndex).setBackground(color);
            stepColors.set(stepIndex, color);
        }
    }

    public void highlightCurrentStep(int currentStep) {
        for (int i = 0; i < stepLabels.size(); i++) {
            if (i == currentStep) {
                stepLabels.get(i).setBackground(new Color(255, 204, 0));
            } else if (stepColors.get(i) == Color.LIGHT_GRAY) {
                stepLabels.get(i).setBackground(Color.LIGHT_GRAY);
            } else {
                stepLabels.get(i).setBackground(stepColors.get(i));
            }
        }
    }

    public void resetSteps() {
        for (int i = 0; i < stepLabels.size(); i++) {
            stepLabels.get(i).setBackground(Color.LIGHT_GRAY);
            stepColors.set(i, Color.LIGHT_GRAY);
        }
    }

    public void setStepClickListener(StepClickListener listener) {
        this.clickListener = listener;
    }

    public interface StepClickListener {
        void onStepClicked(int stepIndex);
    }
}
