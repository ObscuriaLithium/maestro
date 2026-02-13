package dev.obscuria.maestro.client.music.condition.logic;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.obscuria.maestro.client.music.condition.MusicCondition;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public record NoneOfCondition(List<MusicCondition> terms) implements MusicCondition {

    public static final MapCodec<NoneOfCondition> CODEC;

    @Override
    public MapCodec<NoneOfCondition> codec() {
        return CODEC;
    }

    @Override
    public boolean test(@Nullable Level level, @Nullable Player player) {
        for (var term : terms) {
            if (!term.test(level, player)) continue;
            return false;
        }
        return true;
    }

    static {
        CODEC = RecordCodecBuilder.mapCodec(codec -> codec.group(
                MusicCondition.CODEC.listOf().fieldOf("terms").forGetter(NoneOfCondition::terms)
        ).apply(codec, NoneOfCondition::new));
    }
}
