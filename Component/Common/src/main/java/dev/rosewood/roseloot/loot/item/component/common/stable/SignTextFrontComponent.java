package dev.rosewood.roseloot.loot.item.component.common.stable;

import io.papermc.paper.datacomponent.DataComponentTypes;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class SignTextFrontComponent extends SignTextComponent {

    public SignTextFrontComponent(ConfigurationSection section) {
        super(DataComponentTypes.SIGN_TEXT_FRONT, "sign-text-front", section);
    }

    public static void applyProperties(ItemStack itemStack, StringBuilder stringBuilder) {
        SignTextComponent.applyProperties(DataComponentTypes.SIGN_TEXT_FRONT, "sign-text-front", itemStack, stringBuilder);
    }

}
