import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class Music extends Thread {
    private String filePath;
    private Clip clip;

    public Music(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void run() {
        try {
            File soundFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
            clip.drain();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void stopMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
        interrupt();
    }
}
