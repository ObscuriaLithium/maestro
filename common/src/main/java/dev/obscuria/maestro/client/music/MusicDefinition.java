package dev.obscuria.maestro.client.music;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.obscuria.maestro.client.music.condition.MusicCondition;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;

public record MusicDefinition(
        int priority,
        MusicLayerEnum layer,
        ResourceLocation soundEvent,
        MusicCondition condition
) implements Comparable<MusicDefinition> {

    public static final Codec<MusicDefinition> CODEC;

    public boolean test() {
        var minecraft = Minecraft.getInstance();
        return condition.test(minecraft.level, minecraft.player);
    }

    public Music assemble() {
        var sound = Holder.direct(SoundEvent.createVariableRangeEvent(soundEvent));
        return new Music(sound, 0, 0, true);
    }

    static {
        CODEC = RecordCodecBuilder.create(codec -> codec.group(
                Codec.INT.fieldOf("priority").forGetter(MusicDefinition::priority),
                MusicLayerEnum.CODEC.fieldOf("layer").forGetter(MusicDefinition::layer),
                ResourceLocation.CODEC.fieldOf("sound_event").forGetter(MusicDefinition::soundEvent),
                MusicCondition.CODEC.fieldOf("condition").forGetter(MusicDefinition::condition)
        ).apply(codec, MusicDefinition::new));
    }

    @Override
    public int compareTo(MusicDefinition other) {
        return Integer.compare(priority, other.priority);
    }
}
