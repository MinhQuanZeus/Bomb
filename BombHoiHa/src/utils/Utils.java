package utils;

import gui.MainPanel;
import models.ItemMapModel;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * Created by QuanT on 3/9/2017.
 */
public class Utils {

    public static Image loadImageFromRes(String url) {
        try {
            Image image = ImageIO.read(new File("resources/" + url + ".png"));
            return image;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static int getRandom(int max) {
        Random random = new Random();
        int a;
        a = random.nextInt(max);
        return a;
    }

    public static int getRowMatrix(int y) {
        return y / ItemMapModel.SIZE_TILED;
    }

    public static int getColMatrix(int x) {
        return x / ItemMapModel.SIZE_TILED;
    }

    public static void playSound(String audioUrl, boolean repeat) {
        if (!MainPanel.isMute()) {
            File soundFile = new File("resources/Sounds/" + audioUrl);
            try {
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
                AudioFormat baseFormat = audioIn.getFormat();
                AudioFormat decodeFormat = new AudioFormat(
                        AudioFormat.Encoding.PCM_SIGNED,
                        baseFormat.getSampleRate(),
                        16,
                        baseFormat.getChannels(),
                        baseFormat.getChannels() * 2,
                        baseFormat.getSampleRate(),
                        false
                );
                AudioInputStream decodedAudioIn = AudioSystem.getAudioInputStream(decodeFormat, audioIn);
                Clip clip = AudioSystem.getClip();
                clip.open(decodedAudioIn);
                clip.start();
                if (repeat) {
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                } else clip.loop(0);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<String> readFileMap(String url) {
        try {
            java.util.List<String> arrRows = new ArrayList<>();
            File file = new File(url);
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String row = bufferedReader.readLine();
            while (row != null) {
                arrRows.add(row);
                row = bufferedReader.readLine();
            }
            bufferedReader.close();
            return arrRows;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
