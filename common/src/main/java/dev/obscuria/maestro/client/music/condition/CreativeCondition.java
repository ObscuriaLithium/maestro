package dev.obscuria.maestro.client.music.condition;

import com.mojang.serialization.Codec;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public record CreativeCondition() implements MusicCondition {

    public static final Codec<CreativeCondition> CODEC = Codec.unit(CreativeCondition::new);

    @Override
    public Codec<CreativeCondition> codec() {
        return CODEC;
    }

    @Override
    public boolean test(@Nullable Level level, @Nullable Player player) {
        if (level == null || player == null) return false;
        return player.isCreative();
    }
}
