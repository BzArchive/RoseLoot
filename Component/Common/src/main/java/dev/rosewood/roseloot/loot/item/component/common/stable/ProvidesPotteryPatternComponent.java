package dev.rosewood.roseloot.loot.item.component.common.stable;

import dev.rosewood.roseloot.loot.context.LootContext;
import dev.rosewood.roseloot.loot.item.component.LootItemComponent;
import dev.rosewood.roseloot.provider.StringProvider;
import io.papermc.paper.block.pot.PotPatternType;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import net.kyori.adventure.key.Key;
import org.bukkit.Registry;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class ProvidesPotteryPatternComponent implements LootItemComponent {

    private final StringProvider value;

    public ProvidesPotteryPatternComponent(ConfigurationSection section) {
        this.value = StringProvider.fromSection(section, "provides-pottery-pattern", null);
    }

    @Override
    public void apply(ItemStack itemStack, LootContext context) {
        if (this.value != null) {
            String keyValue = this.value.get(context).toLowerCase();
            Key key = Key.key(keyValue);
            Registry<PotPatternType> registry = RegistryAccess.registryAccess().getRegistry(RegistryKey.DECORATED_POT_PATTERN);
            PotPatternType potPatternType = registry.get(key);
            if (potPatternType != null)
                itemStack.setData(DataComponentTypes.PROVIDES_POTTERY_PATTERN, potPatternType);
        }
    }

    public static void applyProperties(ItemStack itemStack, StringBuilder stringBuilder) {
        if (!itemStack.isDataOverridden(DataComponentTypes.PROVIDES_POTTERY_PATTERN))
            return;

        Registry<PotPatternType> registry = RegistryAccess.registryAccess().getRegistry(RegistryKey.DECORATED_POT_PATTERN);
        Key key = registry.getKey(itemStack.getData(DataComponentTypes.PROVIDES_POTTERY_PATTERN));
        if (key != null)
            stringBuilder.append("provides-pottery-pattern: '").append(key.asMinimalString()).append("'\n");
    }

}
