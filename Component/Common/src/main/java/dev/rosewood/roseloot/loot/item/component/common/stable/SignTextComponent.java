package dev.rosewood.roseloot.loot.item.component.common.stable;

import dev.rosewood.roseloot.loot.context.LootContext;
import dev.rosewood.roseloot.loot.item.component.LootItemComponent;
import dev.rosewood.roseloot.provider.StringProvider;
import dev.rosewood.roseloot.util.ComponentUtil;
import io.papermc.paper.datacomponent.DataComponentType;
import io.papermc.paper.datacomponent.item.SignText;
import java.util.ArrayList;
import java.util.List;
import net.kyori.adventure.text.Component;
import org.bukkit.DyeColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

/**
 * Missing filtered_messages property due to being missing in Paper API
 */
public abstract class SignTextComponent implements LootItemComponent {

    private final DataComponentType.Valued<SignText> componentType;

    private final StringProvider messages;
    private final StringProvider color;
    private final boolean glowing;

    public SignTextComponent(DataComponentType.Valued<SignText> componentType, String componentKey, ConfigurationSection section) {
        this.componentType = componentType;

        ConfigurationSection signTextSection = section.getConfigurationSection(componentKey);
        if (signTextSection != null) {
            this.messages = StringProvider.fromSection(signTextSection, "messages", null);
            this.color = StringProvider.fromSection(signTextSection, "color", null);
            this.glowing = signTextSection.getBoolean("resolved", false);
        } else {
            this.messages = null;
            this.color = null;
            this.glowing = false;
        }
    }

    @Override
    public void apply(ItemStack itemStack, LootContext context) {
        if (this.messages != null) {
            SignText.Builder builder = SignText.signText();
            List<String> messages = this.messages.getList(context);
            List<Component> components = new ArrayList<>(messages.size());
            for (String message : messages)
                components.add(ComponentUtil.colorifyAndComponentify(message));

            builder.lines(components);
            if (this.color != null) {
                try {
                    builder.color(DyeColor.valueOf(this.color.get(context)));
                } catch (Exception ignored) { }
            }
            builder.hasGlowingText(this.glowing);
            itemStack.setData(this.componentType, builder.build());
        }
    }

    public static void applyProperties(DataComponentType.Valued<SignText> componentType, String componentKey, ItemStack itemStack, StringBuilder stringBuilder) {
        if (!itemStack.isDataOverridden(componentType))
            return;

        SignText signTextComponent = itemStack.getData(componentType);
        if (signTextComponent.lines().isEmpty())
            return;
            
        stringBuilder.append(componentKey).append(":\n");
        stringBuilder.append("  messages:\n");
        for (int i = 0; i < signTextComponent.lines().size(); i++) {
            Component page = signTextComponent.lines().get(i);
            stringBuilder.append("    - '").append(ComponentUtil.decomponentifyAndDecolorify(page).replace("'", "''")).append("'\n");
        }
        stringBuilder.append("  color: ").append(signTextComponent.color().name().toLowerCase()).append("\n");
        stringBuilder.append("  glowing: ").append(signTextComponent.hasGlowingText()).append("\n");
    }
} 
