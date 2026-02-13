package dev.obscuria.maestro.client.music.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

public record EntityCondition(
        Set<Identifier> values,
        int requiredAmount,
        int searchRadius
) implements MusicCondition {

    public static final MapCodec<EntityCondition> CODEC;

    @Override
    public MapCodec<EntityCondition> codec() {
        return CODEC;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean test(@Nullable Level level, @Nullable Player player) {
        if (level == null || player == null) return false;
        var totalMatches = 0;
        for (var target : level.getEntities(player, player.getBoundingBox().inflate(searchRadius))) {
            if (!values.contains(target.getType().builtInRegistryHolder().key().identifier())) continue;
            totalMatches += 1;
            if (totalMatches < requiredAmount) continue;
            return true;
        }
        return false;
    }

    static {
        CODEC = RecordCodecBuilder.mapCodec(codec -> codec.group(
                Identifier.CODEC.listOf().xmap(Set::copyOf, List::copyOf).fieldOf("values").forGetter(EntityCondition::values),
                Codec.INT.optionalFieldOf("required_amount", 1).forGetter(EntityCondition::requiredAmount),
                Codec.INT.optionalFieldOf("search_radius", 64).forGetter(EntityCondition::searchRadius)
        ).apply(codec, EntityCondition::new));
    }
}
