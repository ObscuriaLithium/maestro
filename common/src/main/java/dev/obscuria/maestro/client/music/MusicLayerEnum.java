package dev.obscuria.maestro.client.music;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;

import java.util.Locale;

public enum MusicLayerEnum implements StringRepresentable {
    ENCOUNTER,
    UNDERSCORE;

    public static final Codec<MusicLayerEnum> CODEC = StringRepresentable.fromEnum(MusicLayerEnum::values);

    @Override
    public String getSerializedName() {
        return name().toLowerCase(Locale.ROOT);
    }
}
