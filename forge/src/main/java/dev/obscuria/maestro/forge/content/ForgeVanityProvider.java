package dev.obscuria.maestro.forge.content;

import dev.obscuria.maestro.content.IVanityProvider;
import dev.obscuria.maestro.content.Vanity;
import dev.obscuria.maestro.content.item.VanityItem;
import net.minecraft.world.item.Item;

public final class ForgeVanityProvider implements IVanityProvider {

    @Override
    public VanityItem create(Vanity vanity, Item.Properties properties) {
        return new ForgeVanityItem(vanity, properties);
    }
}
