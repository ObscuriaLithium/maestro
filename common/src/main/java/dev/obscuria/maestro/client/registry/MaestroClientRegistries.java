package dev.obscuria.maestro.client.registry;

import com.mojang.serialization.Codec;
import dev.obscuria.fragmentum.registry.BootstrapContext;
import dev.obscuria.fragmentum.registry.DelegatedRegistry;
import dev.obscuria.fragmentum.registry.FragmentumRegistry;
import dev.obscuria.fragmentum.registry.Registrar;
import dev.obscuria.maestro.Maestro;
import dev.obscuria.maestro.client.music.MusicDefinition;
import dev.obscuria.maestro.client.music.condition.MusicCondition;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public interface MaestroClientRegistries {

    Registrar REGISTRAR = FragmentumRegistry.registrar(Maestro.MODID);

    DelegatedRegistry<Codec<? extends MusicCondition>> MUSIC_CONDITION = REGISTRAR.createRegistry(Key.MUSIC_CONDITION);

    interface Resource {

        ResourceRegistry<MusicDefinition> MUSIC_DEFINITION = new ResourceRegistry.Ordered<>("definition");
    }

    interface Key {

        ResourceKey<Registry<Codec<? extends MusicCondition>>> MUSIC_CONDITION = create("music_condition");

        private static <T> ResourceKey<Registry<T>> create(String name) {
            return ResourceKey.createRegistryKey(Maestro.key(name));
        }
    }

    static void init() {

        MusicCondition.bootstrap(BootstrapContext.create(REGISTRAR, Key.MUSIC_CONDITION, Maestro::key));
    }
}
