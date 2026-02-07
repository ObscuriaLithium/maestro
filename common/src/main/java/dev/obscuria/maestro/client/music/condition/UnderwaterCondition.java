package dev.obscuria.maestro.client.music.condition;

import com.mojang.serialization.Codec;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public record UnderwaterCondition() implements MusicCondition {

    public static final Codec<UnderwaterCondition> CODEC = Codec.unit(UnderwaterCondition::new);

    @Override
    public Codec<UnderwaterCondition> codec() {
        return CODEC;
    }

    @Override
    public boolean test(@Nullable Level level, @Nullable Player player) {
        if (level == null || player == null) return false;
        return player.isUnderWater();
    }
}
