package dev.rosewood.roseloot.loot.item.component.common.stable;

import io.papermc.paper.datacomponent.DataComponentTypes;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class SignTextBackComponent extends SignTextComponent {

    public SignTextBackComponent(ConfigurationSection section) {
        super(DataComponentTypes.SIGN_TEXT_BACK, "sign-text-back", section);
    }

    public static void applyProperties(ItemStack itemStack, StringBuilder stringBuilder) {
        SignTextComponent.applyProperties(DataComponentTypes.SIGN_TEXT_BACK, "sign-text-back", itemStack, stringBuilder);
    }

}
