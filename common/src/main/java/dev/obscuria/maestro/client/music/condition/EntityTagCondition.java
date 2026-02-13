package dev.obscuria.maestro.client.music.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

public record EntityTagCondition(
        Set<TagKey<EntityType<?>>> values,
        int requiredAmount,
        int searchRadius
) implements MusicCondition {

    public static final MapCodec<EntityTagCondition> CODEC;

    @Override
    public MapCodec<EntityTagCondition> codec() {
        return CODEC;
    }

    @Override
    public boolean test(@Nullable Level level, @Nullable Player player) {
        if (level == null || player == null) return false;
        var totalMatches = 0;
        for (var target : level.getEntities(player, player.getBoundingBox().inflate(searchRadius))) {
            if (!contains(target)) continue;
            totalMatches += 1;
            if (totalMatches < requiredAmount) continue;
            return true;
        }
        return false;
    }

    @SuppressWarnings("deprecation")
    private boolean contains(Entity entity) {
        var holder = entity.getType().builtInRegistryHolder();
        for (var value : values) {
            if (!holder.is(value)) continue;
            return true;
        }
        return false;
    }

    static {
        CODEC = RecordCodecBuilder.mapCodec(codec -> codec.group(
                TagKey.codec(Registries.ENTITY_TYPE).listOf().xmap(Set::copyOf, List::copyOf).fieldOf("values").forGetter(EntityTagCondition::values),
                Codec.INT.optionalFieldOf("required_amount", 1).forGetter(EntityTagCondition::requiredAmount),
                Codec.INT.optionalFieldOf("search_radius", 64).forGetter(EntityTagCondition::searchRadius)
        ).apply(codec, EntityTagCondition::new));
    }
}
