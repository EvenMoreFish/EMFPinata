package org.evenmorefish.emfpinata.pinata.config;

import com.oheers.fish.FishUtils;
import com.oheers.fish.items.ItemFactory;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.evenmorefish.emfpinata.api.EntityConfig;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.messagelib.replacer.Replacer;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class EquipmentEntityConfig extends EntityConfig<Map<EquipmentSlot, ItemStack>> {

    public EquipmentEntityConfig(@NonNull Section section) {
        super(section);
    }

    @Override
    public Map<EquipmentSlot, ItemStack> getConfiguredValue() {
        Section equipmentSection = section.getSection("equipment");
        if (equipmentSection == null) {
            return Map.of();
        }
        HashMap<EquipmentSlot, ItemStack> equipmentMap = new HashMap<>();
        for (String key : equipmentSection.getRoutesAsStrings(false)) {
            Section section = equipmentSection.getSection(key);
            if (section == null) {
                continue;
            }
            EquipmentSlot slot = FishUtils.getEnumValue(EquipmentSlot.class, key);
            if (slot == null) {
                continue;
            }
            ItemStack item = ItemFactory.itemFactory(section).createItem();
            if (item.isEmpty()) {
                continue;
            }
            equipmentMap.putIfAbsent(slot, item);
        }
        return equipmentMap;
    }

    @Override
    protected BiConsumer<Entity, Map<EquipmentSlot, ItemStack>> applyToEntity(@Nullable Replacer replacements) {
        return (entity, value) -> {
            if (!(entity instanceof LivingEntity living)) {
                return;
            }
            EntityEquipment equipment = living.getEquipment();
            if (equipment == null) {
                return;
            }
            value.forEach(equipment::setItem);
        };
    }
}
