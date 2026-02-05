package dev.obscuria.maestro.client.music.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public record AllOfCondition(List<MusicCondition> terms) implements MusicCondition {

    public static final Codec<AllOfCondition> CODEC;

    @Override
    public Codec<AllOfCondition> codec() {
        return CODEC;
    }

    @Override
    public boolean test(@Nullable Level level, @Nullable Player player) {
        for (var term : terms) {
            if (term.test(level, player)) continue;
            return false;
        }
        return true;
    }

    static {
        CODEC = RecordCodecBuilder.create(codec -> codec.group(
                MusicCondition.CODEC.listOf().fieldOf("terms").forGetter(AllOfCondition::terms)
        ).apply(codec, AllOfCondition::new));
    }
}
