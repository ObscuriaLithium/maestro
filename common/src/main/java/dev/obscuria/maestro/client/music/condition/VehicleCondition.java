package dev.obscuria.maestro.client.music.condition;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

public record VehicleCondition(
        Set<Identifier> values
) implements MusicCondition {

    public static final MapCodec<VehicleCondition> CODEC;

    @Override
    public MapCodec<VehicleCondition> codec() {
        return CODEC;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean test(@Nullable Level level, @Nullable Player player) {
        if (level == null || player == null) return false;
        @Nullable var vehicle = player.getVehicle();
        if (vehicle == null) return false;
        if (values.isEmpty()) return true;
        return values.contains(vehicle.getType().builtInRegistryHolder().key().identifier());
    }

    static {
        CODEC = RecordCodecBuilder.mapCodec(codec -> codec.group(
                Identifier.CODEC.listOf().xmap(Set::copyOf, List::copyOf).fieldOf("values").forGetter(VehicleCondition::values)
        ).apply(codec, VehicleCondition::new));
    }
}
