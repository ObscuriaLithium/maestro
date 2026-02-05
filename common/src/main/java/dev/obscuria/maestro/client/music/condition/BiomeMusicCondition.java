package dev.obscuria.maestro.client.music.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Stream;

public record BiomeMusicCondition(
        List<TagKey<Biome>> tags,
        List<ResourceLocation> biomes
) implements MusicCondition {

    public static final Codec<BiomeMusicCondition> CODEC;

    @Override
    public Codec<BiomeMusicCondition> codec() {
        return CODEC;
    }

    @Override
    public boolean test(@Nullable Level level, @Nullable Player player) {
        if (level == null || player == null) return false;
        var biome = level.getBiome(player.blockPosition());
        var key = biome.unwrapKey().orElseThrow();
        return biomes.contains(key.location()) || tags.stream().anyMatch(biome::is);
    }

    private List<ExtraCodecs.TagOrElementLocation> pack() {
        return Stream.concat(
                tags.stream().map(BiomeMusicCondition::packTag),
                biomes.stream().map(BiomeMusicCondition::packBiome)).toList();
    }

    private static ExtraCodecs.TagOrElementLocation packTag(TagKey<Biome> tag) {
        return new ExtraCodecs.TagOrElementLocation(tag.location(), true);
    }

    private static ExtraCodecs.TagOrElementLocation packBiome(ResourceLocation biome) {
        return new ExtraCodecs.TagOrElementLocation(biome, false);
    }

    private static BiomeMusicCondition unpack(List<ExtraCodecs.TagOrElementLocation> packed) {
        final var tags = Lists.<TagKey<Biome>>newArrayList();
        final var biomes = Lists.<ResourceLocation>newArrayList();
        for (var location : packed) {
            if (location.tag()) tags.add(TagKey.create(Registries.BIOME, location.id()));
            else biomes.add(location.id());
        }
        return new BiomeMusicCondition(tags, biomes);
    }

    static {
        CODEC = RecordCodecBuilder.create(codec -> codec.group(
                ExtraCodecs.TAG_OR_ELEMENT_ID.listOf().fieldOf("biomes").forGetter(BiomeMusicCondition::pack)
        ).apply(codec, BiomeMusicCondition::unpack));
    }
}
