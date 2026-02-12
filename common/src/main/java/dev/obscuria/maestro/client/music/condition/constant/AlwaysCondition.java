package dev.obscuria.maestro.client.music.condition.constant;

import com.mojang.serialization.MapCodec;
import dev.obscuria.maestro.client.music.condition.MusicCondition;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public record AlwaysCondition() implements MusicCondition {

    public static final MapCodec<AlwaysCondition> CODEC = MapCodec.unit(AlwaysCondition::new);

    @Override
    public MapCodec<AlwaysCondition> codec() {
        return CODEC;
    }

    @Override
    public boolean test(@Nullable Level level, @Nullable Player player) {
        return true;
    }
}
