import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class Music extends Thread {
    private String filePath;
    private boolean loop;

    public Music(String filePath, boolean loop) {
        this.filePath = filePath;
        this.loop = loop;
    }
    @Override
    public void run() {
        try {
            File soundFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            if(loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }else{
                clip.start();
            }
            if(!loop) {
                clip.drain();
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
