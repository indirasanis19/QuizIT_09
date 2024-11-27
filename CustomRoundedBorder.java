import java.awt.*;
import javax.swing.border.Border;

// Class untuk membuat border dengan sudut melengkung (rounded corner)
public class CustomRoundedBorder implements Border {
    private int radius;
    private Color color;

    // Constructor untuk mengatur radius lengkungan dan warna border
    public CustomRoundedBorder(int radius, Color color) {
        this.radius = radius;
        this.color = color;
    }

    // Method untuk menggambar border dengan sudut melengkung
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(color);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        g2d.dispose();
    }

    // Method untuk mendapatkan ukuran border (insets)
    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(radius / 2, radius / 2, radius / 2, radius / 2);
    }

    // Method untuk mengecek apakah border bersifat opaque (transparan)
    @Override
    public boolean isBorderOpaque() {
        return false;
    }
}