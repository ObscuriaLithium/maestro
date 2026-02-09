package dev.obscuria.maestro.client.music.player;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;

public final class MusicSoundInstance extends AbstractTickableSoundInstance {

    private static final float FADE_STEP = 0.01f;

    private final MusicTrack track;
    private boolean fadingOut;

    public MusicSoundInstance(MusicTrack track) {
        super(track.getMusic().getEvent().value(), SoundSource.MUSIC, SoundInstance.createUnseededRandom());
        this.track = track;

        this.attenuation = Attenuation.NONE;
        this.looping = track.cooldownTicks == 0;
        this.relative = true;
        this.volume = 0.1f;

        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    @Override
    public void tick() {
        float target = fadingOut
                || track.getState().suppressed
                ? volume - FADE_STEP
                : volume + FADE_STEP;

        volume = Mth.clamp(target, 0f, 1f);

        if (fadingOut && volume <= 0f) {
            stop();
        }
    }

    public void fadeIn() {
        fadingOut = false;
    }

    public void fadeOut() {
        fadingOut = true;
    }
}
