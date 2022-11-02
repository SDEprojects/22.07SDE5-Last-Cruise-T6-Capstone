package com.lastcruise.model;
import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundEffect {
    private static FloatControl musicControl;
    private static Clip clip;
    private static float soundFxVolume = (-10.0f);
    private static float tempFxVolume = (-10.0f);
    private int loopCounter = 0;


    public static void runAudio(URL path) {
        try {
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(path);
            clip = AudioSystem.getClip();
            clip.open(inputStream);
            clip.start();
            musicControl =
                (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

            musicControl.setValue(soundFxVolume);
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }
    public void changeMapFx() {
        URL sound = getClass().getResource(AllSounds.ALL_SOUNDS.get("run"));
        runAudio(sound);
    }
    public void pickUpFx() {
        URL sound = getClass().getResource(AllSounds.ALL_SOUNDS.get("pickup"));
        runAudio(sound);
    }
    public void sleepFx() {
        URL sound = getClass().getResource(AllSounds.ALL_SOUNDS.get("sleep"));
        loopCounter++;
        if (loopCounter > 150) {
            runAudio(sound);
            loopCounter = 0;
        }
    }
    public void walkFx() {
        URL sound = getClass().getResource(AllSounds.ALL_SOUNDS.get("footsteps"));
        loopCounter++;
        if (loopCounter > 90) {
//            runAudio(sound);
            loopCounter = 0;
        } else if (loopCounter == 1) {
            runAudio(sound);
        }
    }
    public static void decreaseFxVolume(){
        try {
            soundFxVolume = musicControl.getValue() - 7.0f;
        } catch (IllegalArgumentException | NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    public static void increaseFxVolume(){
        try {
            soundFxVolume = musicControl.getValue() + 7.0f;
        } catch (IllegalArgumentException | NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    public static void muteSoundFx() {
        try {
            tempFxVolume = soundFxVolume;
            soundFxVolume = musicControl.getMinimum();
        } catch (IllegalArgumentException | NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    public static void unMuteSoundFx(){
        try {
            soundFxVolume = tempFxVolume;
        } catch (IllegalArgumentException | NullPointerException ex) {
            ex.printStackTrace();
        }
    }
}