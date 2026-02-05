package dev.obscuria.maestro.client.music.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public record EntityCondition(
        Holder<EntityType<?>> entity,
        int distance
) implements MusicCondition {

    public static final Codec<EntityCondition> CODEC;

    @Override
    public Codec<EntityCondition> codec() {
        return CODEC;
    }

    @Override
    public boolean test(@Nullable Level level, @Nullable Player player) {
        if (level == null || player == null) return false;
        for (var target : level.getEntities(player, player.getBoundingBox().inflate(distance))) {
            if (!target.getType().equals(entity.value())) continue;
            return true;
        }
        return false;
    }

    static {
        CODEC = RecordCodecBuilder.create(codec -> codec.group(
                BuiltInRegistries.ENTITY_TYPE.holderByNameCodec().fieldOf("entity").forGetter(EntityCondition::entity),
                Codec.INT.optionalFieldOf("distance", 64).forGetter(EntityCondition::distance)
        ).apply(codec, EntityCondition::new));
    }
}
