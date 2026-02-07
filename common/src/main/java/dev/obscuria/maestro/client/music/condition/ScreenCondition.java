package dev.obscuria.maestro.client.music.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.*;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.Optional;

public record ScreenCondition(
        Optional<ScreenKind> kind,
        Optional<String> classPath
) implements MusicCondition {

    public static final Codec<ScreenCondition> CODEC;

    @Override
    public Codec<ScreenCondition> codec() {
        return CODEC;
    }

    @Override
    public boolean test(@Nullable Level level, @Nullable Player player) {
        @Nullable var screen = Minecraft.getInstance().screen;
        if (screen == null) return false;
        if (kind.isPresent() && !kind.get().test(screen)) return false;
        if (classPath.isPresent() && !screen.getClass().getName().equals(classPath.get())) return false;
        return true;
    }

    public enum ScreenKind implements StringRepresentable {
        TITLE(TitleScreen.class),
        CREDITS(WinScreen.class),
        INVENTORY(InventoryScreen.class),
        PAUSE(PauseScreen.class);

        public static final Codec<ScreenKind> CODEC = StringRepresentable.fromEnum(ScreenKind::values);

        private final Class<? extends Screen> clazz;

        ScreenKind(Class<? extends Screen> clazz) {
            this.clazz = clazz;
        }

        public boolean test(Screen screen) {
            return screen.getClass().isAssignableFrom(clazz);
        }

        @Override
        public String getSerializedName() {
            return name().toLowerCase(Locale.ROOT);
        }
    }

    static {
        CODEC = RecordCodecBuilder.create(codec -> codec.group(
                ScreenKind.CODEC.optionalFieldOf("kind").forGetter(ScreenCondition::kind),
                Codec.STRING.optionalFieldOf("class_path").forGetter(ScreenCondition::classPath)
        ).apply(codec, ScreenCondition::new));
    }
}
