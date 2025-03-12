package fr.theorigindev.shootcraft.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemBuilder {
    private ItemStack item;
    private ItemMeta meta;

    public ItemBuilder(Material material) {
        this.item = new ItemStack(material);
        this.meta = item.getItemMeta();
    }

    public ItemBuilder setDisplayName(String name) {
        meta.setDisplayName(name);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
        meta.addEnchant(enchantment, level, true);
        return this;
    }

    public ItemStack build() {
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createShootCraftStick() {
        return new ItemBuilder(Material.STICK)
                .setDisplayName("§6Lanceur de Feu")
                .addEnchantment(Enchantment.DURABILITY, 1)
                .build();
    }}
