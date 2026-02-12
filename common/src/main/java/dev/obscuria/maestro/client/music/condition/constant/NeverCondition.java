package dev.obscuria.maestro.client.music.condition.constant;

import com.mojang.serialization.MapCodec;
import dev.obscuria.maestro.client.music.condition.MusicCondition;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public record NeverCondition() implements MusicCondition {

    public static final MapCodec<NeverCondition> CODEC = MapCodec.unit(NeverCondition::new);

    @Override
    public MapCodec<NeverCondition> codec() {
        return CODEC;
    }

    @Override
    public boolean test(@Nullable Level level, @Nullable Player player) {
        return false;
    }
}
