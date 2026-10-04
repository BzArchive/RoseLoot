package dev.rosewood.roseloot.loot.item.component.common.stable;

import dev.rosewood.roseloot.loot.context.LootContext;
import dev.rosewood.roseloot.loot.item.component.LootItemComponent;
import dev.rosewood.roseloot.provider.NumberProvider;
import dev.rosewood.roseloot.provider.StringProvider;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.MobVisibility;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import io.papermc.paper.registry.set.RegistryKeySet;
import io.papermc.paper.registry.set.RegistrySet;
import io.papermc.paper.registry.tag.Tag;
import io.papermc.paper.registry.tag.TagKey;
import java.util.ArrayList;
import java.util.List;
import net.kyori.adventure.key.Key;
import org.bukkit.Registry;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class MobVisibilityComponent implements LootItemComponent {

    private final StringProvider targetingEntityTypes;
    private final NumberProvider visbility;

    public MobVisibilityComponent(ConfigurationSection section) {
        ConfigurationSection mobVisbilitySection = section.getConfigurationSection("mob-visibility");
        if (mobVisbilitySection != null) {
            this.targetingEntityTypes = StringProvider.fromSection(mobVisbilitySection, "targeting-entity-types", null);
            this.visbility = NumberProvider.fromSection(mobVisbilitySection, "visibility", null);
        } else {
            this.targetingEntityTypes = null;
            this.visbility = null;
        }
    }

    @Override
    public void apply(ItemStack itemStack, LootContext context) {
        if (this.targetingEntityTypes == null)
            return;

        RegistryKeySet<EntityType> registryKeySet;
        List<String> entityStrings = this.targetingEntityTypes.getList(context);
        Registry<EntityType> registry = RegistryAccess.registryAccess().getRegistry(RegistryKey.ENTITY_TYPE);
        if (entityStrings.size() == 1 && entityStrings.getFirst().startsWith("#")) {
            String tag = entityStrings.getFirst().toLowerCase();
            TagKey<EntityType> tagKey = TagKey.create(RegistryKey.ENTITY_TYPE, Key.key(tag.substring(1)));
            registryKeySet = registry.getTag(tagKey);
        } else {
            List<EntityType> entityTypes = new ArrayList<>();
            for (String value : entityStrings) {
                if (value.startsWith("#")) {
                    TagKey<EntityType> tagKey = TagKey.create(RegistryKey.ENTITY_TYPE, Key.key(value.substring(1)));
                    Tag<EntityType> tag = registry.getTag(tagKey);
                    entityTypes.addAll(tag.resolve(registry));
                } else {
                    Key key = Key.key(value.toLowerCase());
                    EntityType entityType = registry.get(key);
                    if (entityType != null)
                        entityTypes.add(entityType);
                }
            }
            registryKeySet = RegistrySet.keySetFromValues(RegistryKey.ENTITY_TYPE, entityTypes);
        }

        float visibility = Math.max(Math.min(this.visbility != null ? this.visbility.getFloat(context) : 1.0f, 0.0f), 10.0f);

        MobVisibility mobVisibility = MobVisibility.mobVisibility(registryKeySet, visibility);
        
        itemStack.setData(DataComponentTypes.MOB_VISIBILITY, mobVisibility);
    }

    public static void applyProperties(ItemStack itemStack, StringBuilder stringBuilder) {
        if (!itemStack.isDataOverridden(DataComponentTypes.MOB_VISIBILITY))
            return;

        MobVisibility mobVisibility = itemStack.getData(DataComponentTypes.MOB_VISIBILITY);
        stringBuilder.append("mob-visibility:\n");

        if (mobVisibility.targetingEntityTypes() instanceof Tag<?> tag) {
            stringBuilder.append("  targeting-entity-types: '#").append(tag.tagKey().key().asMinimalString()).append("'\n");
        } else {
            stringBuilder.append("  targeting-entity-types:\n");
            for (TypedKey<EntityType> key : mobVisibility.targetingEntityTypes().values())
                stringBuilder.append("    - '").append(key.asMinimalString()).append("'\n");
        }

        stringBuilder.append("  visibility: '").append(mobVisibility.visibility()).append("'\n");
    }

} 
