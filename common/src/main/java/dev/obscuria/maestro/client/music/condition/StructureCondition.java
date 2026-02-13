package dev.obscuria.maestro.client.music.condition;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.obscuria.maestro.client.MaestroClient;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

public record StructureCondition(
        Set<Identifier> values
) implements MusicCondition {

    public static final MapCodec<StructureCondition> CODEC;

    @Override
    public MapCodec<StructureCondition> codec() {
        return CODEC;
    }

    @Override
    public boolean test(@Nullable Level level, @Nullable Player player) {
        if (level == null || player == null) return false;
        for (var structure : MaestroClient.structureList) {
            if (!values.contains(structure)) continue;
            return true;
        }
        return false;
    }

    static {
        CODEC = RecordCodecBuilder.mapCodec(codec -> codec.group(
                Identifier.CODEC.listOf().xmap(Set::copyOf, List::copyOf).fieldOf("values").forGetter(StructureCondition::values)
        ).apply(codec, StructureCondition::new));
    }
}
