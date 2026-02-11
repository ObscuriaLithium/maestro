package dev.obscuria.maestro.client.music.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public record CreativeCondition(
        boolean value
) implements MusicCondition {

    public static final Codec<CreativeCondition> CODEC;

    @Override
    public Codec<CreativeCondition> codec() {
        return CODEC;
    }

    @Override
    public boolean test(@Nullable Level level, @Nullable Player player) {
        if (player == null) return false;
        return value == player.isCreative();
    }

    static {
        CODEC = RecordCodecBuilder.create(codec -> codec.group(
                Codec.BOOL.optionalFieldOf("value", true).forGetter(CreativeCondition::value)
        ).apply(codec, CreativeCondition::new));
    }
}
