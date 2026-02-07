package dev.obscuria.maestro.client.music.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.obscuria.maestro.client.MaestroClient;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public record StructureCondition(
        List<ResourceLocation> values
) implements MusicCondition {

    public static final Codec<StructureCondition> CODEC;

    @Override
    public Codec<StructureCondition> codec() {
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
        CODEC = RecordCodecBuilder.create(codec -> codec.group(
                ResourceLocation.CODEC.listOf().fieldOf("values").forGetter(StructureCondition::values)
        ).apply(codec, StructureCondition::new));
    }
}
