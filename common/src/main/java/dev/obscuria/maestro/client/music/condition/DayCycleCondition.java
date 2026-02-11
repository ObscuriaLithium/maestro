package dev.obscuria.maestro.client.music.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public record DayCycleCondition(
        Optional<Boolean> isDay,
        Optional<Boolean> isNight,
        Optional<Boolean> isSunrise,
        Optional<Boolean> isSunset
) implements MusicCondition {

    public static final Codec<DayCycleCondition> CODEC;

    @Override
    public Codec<DayCycleCondition> codec() {
        return CODEC;
    }

    @Override
    public boolean test(@Nullable Level level, @Nullable Player player) {
        if (level == null) return false;
        if (isDay.isPresent() && isDay(level) != isDay.get()) return false;
        if (isNight.isPresent() && isNight(level) != isNight.get()) return false;
        if (isSunrise.isPresent() && isSunrise(level) != isSunrise.get()) return false;
        if (isSunset.isPresent() && isSunset(level) != isSunset.get()) return false;
        return true;
    }

    private boolean isDay(Level level) {
        return !isNight(level);
    }

    private boolean isNight(Level level) {
        var time = level.getDayTime() % 24000;
        return time >= 13000 && time < 23000;
    }

    private boolean isSunrise(Level level) {
        var time = level.getDayTime() % 24000;
        return time > 23000;
    }

    private boolean isSunset(Level level) {
        var time = level.getDayTime() % 24000;
        return time >= 12000 && time < 13000;
    }

    static {
        CODEC = RecordCodecBuilder.create(codec -> codec.group(
                Codec.BOOL.optionalFieldOf("is_day").forGetter(DayCycleCondition::isDay),
                Codec.BOOL.optionalFieldOf("is_night").forGetter(DayCycleCondition::isNight),
                Codec.BOOL.optionalFieldOf("is_sunrise").forGetter(DayCycleCondition::isSunrise),
                Codec.BOOL.optionalFieldOf("is_sunset").forGetter(DayCycleCondition::isSunrise)
        ).apply(codec, DayCycleCondition::new));
    }
}
