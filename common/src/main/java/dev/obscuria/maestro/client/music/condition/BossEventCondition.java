package dev.obscuria.maestro.client.music.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.obscuria.maestro.mixin.client.BossHealthOverlayAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public record BossEventCondition(
        boolean value
) implements MusicCondition {

    public static final Codec<BossEventCondition> CODEC;

    @Override
    public Codec<BossEventCondition> codec() {
        return CODEC;
    }

    @Override
    public boolean test(@Nullable Level level, @Nullable Player player) {
        var bossOverlay = Minecraft.getInstance().gui.getBossOverlay();
        return value == !((BossHealthOverlayAccessor) bossOverlay).getEvents().isEmpty();
    }

    static {
        CODEC = RecordCodecBuilder.create(codec -> codec.group(
                Codec.BOOL.optionalFieldOf("value", true).forGetter(BossEventCondition::value)
        ).apply(codec, BossEventCondition::new));
    }
}
