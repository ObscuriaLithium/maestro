package dev.obscuria.maestro.client.music.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

public record VehicleCondition(
        Set<ResourceLocation> values
) implements MusicCondition {

    public static final Codec<VehicleCondition> CODEC;

    @Override
    public Codec<VehicleCondition> codec() {
        return CODEC;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean test(@Nullable Level level, @Nullable Player player) {
        if (level == null || player == null) return false;
        @Nullable var vehicle = player.getVehicle();
        if (vehicle == null) return false;
        if (values.isEmpty()) return true;
        return values.contains(vehicle.getType().builtInRegistryHolder().key().location());
    }

    static {
        CODEC = RecordCodecBuilder.create(codec -> codec.group(
                ResourceLocation.CODEC.listOf().xmap(Set::copyOf, List::copyOf).fieldOf("values").forGetter(VehicleCondition::values)
        ).apply(codec, VehicleCondition::new));
    }
}
