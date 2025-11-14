package de.core.audio;
/**
 * super class for audio implementations
 */
public abstract class Audio {
    private double volume = 1.0;
    public double getVolume() {
        return volume;
    }
    /* methods implemented by sub classes */
    abstract public void play();
    abstract public void stop();
}