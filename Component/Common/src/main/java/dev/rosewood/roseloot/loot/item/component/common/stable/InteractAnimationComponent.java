package dev.rosewood.roseloot.loot.item.component.common.stable;

import io.papermc.paper.datacomponent.DataComponentTypes;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class InteractAnimationComponent extends SwingAnimationComponent {

    public InteractAnimationComponent(ConfigurationSection section) {
        super(DataComponentTypes.ATTACK_ANIMATION, "attack-animation", section);
    }

    public static void applyProperties(ItemStack itemStack, StringBuilder stringBuilder) {
        SwingAnimationComponent.applyProperties(DataComponentTypes.ATTACK_ANIMATION, "attack-animation", itemStack, stringBuilder);
    }

}
