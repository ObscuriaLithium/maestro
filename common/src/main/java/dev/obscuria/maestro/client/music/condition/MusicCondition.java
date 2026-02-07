package dev.obscuria.maestro.client.music.condition;

import com.mojang.serialization.Codec;
import dev.obscuria.fragmentum.registry.BootstrapContext;
import dev.obscuria.maestro.client.registry.MaestroClientRegistries;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public interface MusicCondition {

    Codec<MusicCondition> CODEC = MaestroClientRegistries.MUSIC_CONDITION.byNameCodec().dispatch(MusicCondition::codec, Function.identity());

    Codec<? extends MusicCondition> codec();

    boolean test(@Nullable Level level, @Nullable Player player);

    static void bootstrap(BootstrapContext<Codec<? extends MusicCondition>> context) {
        context.register("all_of", () -> AllOfCondition.CODEC);
        context.register("any_of", () -> AnyOfCondition.CODEC);
        context.register("none_of", () -> NoneOfCondition.CODEC);
        context.register("always", () -> AlwaysCondition.CODEC);
        context.register("never", () -> NeverCondition.CODEC);
        context.register("in_game", () -> InGameCondition.CODEC);
        context.register("creative", () -> CreativeCondition.CODEC);
        context.register("dimension", () -> DimensionCondition.CODEC);
        context.register("biome", () -> BiomeCondition.CODEC);
        context.register("biome_tag", () -> BiomeTagCondition.CODEC);
        context.register("weather", () -> WeatherCondition.CODEC);
        context.register("entity", () -> EntityCondition.CODEC);
        context.register("screen", () -> ScreenCondition.CODEC);
        context.register("structure", () -> StructureCondition.CODEC);
        context.register("heightmap", () -> HeightmapCondition.CODEC);
        context.register("y_level", () -> YLevelCondition.CODEC);
        context.register("underwater", () -> UnderwaterCondition.CODEC);
    }
}
