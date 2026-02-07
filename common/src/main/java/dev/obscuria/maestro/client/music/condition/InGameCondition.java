package dev.obscuria.maestro.client.music.condition;

import com.mojang.serialization.Codec;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public record InGameCondition() implements MusicCondition {

    public static final Codec<InGameCondition> CODEC = Codec.unit(InGameCondition::new);

    @Override
    public Codec<InGameCondition> codec() {
        return CODEC;
    }

    @Override
    public boolean test(@Nullable Level level, @Nullable Player player) {
        return level != null && player != null;
    }
}
