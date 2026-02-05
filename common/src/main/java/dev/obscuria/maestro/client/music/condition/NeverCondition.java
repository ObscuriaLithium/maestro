package dev.obscuria.maestro.client.music.condition;

import com.mojang.serialization.Codec;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public record NeverCondition() implements MusicCondition {

    public static final Codec<NeverCondition> CODEC = Codec.unit(NeverCondition::new);

    @Override
    public Codec<NeverCondition> codec() {
        return CODEC;
    }

    @Override
    public boolean test(@Nullable Level level, @Nullable Player player) {
        return false;
    }
}
