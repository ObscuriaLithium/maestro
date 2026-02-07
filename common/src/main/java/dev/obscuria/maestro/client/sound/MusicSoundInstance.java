package dev.obscuria.maestro.client.sound;

import dev.obscuria.maestro.client.music.player.MusicTrack;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;

public final class MusicSoundInstance extends AbstractTickableSoundInstance {

    private MusicTrack track;
    private boolean fadeOut;

    public MusicSoundInstance(MusicTrack track) {
        super(track.getMusic().getEvent().value(), SoundSource.MUSIC, SoundInstance.createUnseededRandom());
        this.track = track;
        this.attenuation = Attenuation.NONE;
        this.looping = true;
        this.relative = true;
        this.volume = 0.1f;
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    @Override
    public void tick() {
        var nextVolume = fadeOut || track.suppressed() ? volume - 0.01f : volume + 0.01f;
        this.volume = Mth.clamp(nextVolume, 0f, 1f);
        if (fadeOut && volume <= 0f) stop();
    }

    public void fadeIn() {
        this.fadeOut = false;
    }

    public void fadeOut() {
        this.fadeOut = true;
    }
}
