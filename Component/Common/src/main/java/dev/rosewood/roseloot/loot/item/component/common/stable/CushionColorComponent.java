package dev.rosewood.roseloot.loot.item.component.common.stable;

import dev.rosewood.roseloot.loot.context.LootContext;
import dev.rosewood.roseloot.loot.item.component.LootItemComponent;
import io.papermc.paper.datacomponent.DataComponentTypes;
import org.bukkit.DyeColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class CushionColorComponent implements LootItemComponent {

    private final DyeColor dyeColor;

    public CushionColorComponent(ConfigurationSection section) {
        DyeColor dyeColor = null;
        String dyeColorString = section.getString("cushion-color");
        if (dyeColorString != null) {
            try {
                dyeColor = DyeColor.valueOf(dyeColorString.toUpperCase());
            } catch (IllegalArgumentException ignored) { }
        }
        this.dyeColor = dyeColor;
    }

    @Override
    public void apply(ItemStack itemStack, LootContext context) {
        if (this.dyeColor != null)
            itemStack.setData(DataComponentTypes.CUSHION_COLOR, this.dyeColor);
    }

    public static void applyProperties(ItemStack itemStack, StringBuilder stringBuilder) {
        if (!itemStack.isDataOverridden(DataComponentTypes.CUSHION_COLOR))
            return;

        DyeColor dyeColor = itemStack.getData(DataComponentTypes.CUSHION_COLOR);
        stringBuilder.append("cushion-color: ").append(dyeColor.name().toLowerCase()).append('\n');
    }

}
