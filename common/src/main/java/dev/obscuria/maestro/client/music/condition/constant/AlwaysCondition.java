package dev.obscuria.maestro.client.music.condition.constant;

import com.mojang.serialization.Codec;
import dev.obscuria.maestro.client.music.condition.MusicCondition;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public record AlwaysCondition() implements MusicCondition {

    public static final Codec<AlwaysCondition> CODEC = Codec.unit(AlwaysCondition::new);

    @Override
    public Codec<AlwaysCondition> codec() {
        return CODEC;
    }

    @Override
    public boolean test(@Nullable Level level, @Nullable Player player) {
        return true;
    }
}
