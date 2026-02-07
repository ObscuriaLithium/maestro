package dev.obscuria.maestro.client.music.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public record HeightmapCondition(
        Optional<Heightmap.Types> above,
        Optional<Heightmap.Types> below
) implements MusicCondition {

    public static final Codec<HeightmapCondition> CODEC;

    @Override
    public Codec<HeightmapCondition> codec() {
        return CODEC;
    }

    @Override
    public boolean test(@Nullable Level level, @Nullable Player player) {
        if (level == null || player == null) return false;
        if (above.isPresent() && !isAbove(level, player, above.get())) return false;
        if (below.isPresent() && !isBelow(level, player, below.get())) return false;
        return true;
    }

    private boolean isAbove(Level level, Player player, Heightmap.Types type) {
        var pos = player.blockPosition();
        var targetPos = level.getHeightmapPos(type, pos);
        return pos.getY() >= targetPos.getY();
    }

    private boolean isBelow(Level level, Player player, Heightmap.Types type) {
        var pos = player.blockPosition();
        var targetPos = level.getHeightmapPos(type, pos);
        return pos.getY() < targetPos.getY();
    }

    static {
        CODEC = RecordCodecBuilder.create(codec -> codec.group(
                Heightmap.Types.CODEC.optionalFieldOf("above").forGetter(HeightmapCondition::above),
                Heightmap.Types.CODEC.optionalFieldOf("below").forGetter(HeightmapCondition::below)
        ).apply(codec, HeightmapCondition::new));
    }
}
