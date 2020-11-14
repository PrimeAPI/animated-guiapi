package de.primeapi.guiapi.gui.itembuilder;


import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class ItemBuilder {


    //vars
    Material material;
    int amount = 1;
    List<String> lore = new LinkedList<>();
    String name = "";
    boolean lether = false;
    Color color;
    boolean skull = false;
    String skullOwner;
    boolean unbreakeble = false;
    String skullTexture;


    //builder
    public ItemBuilder(Material material) {
        this.material = material;
    }



    public ItemBuilder setUnbreakeble(boolean unbreakeble) {
        this.unbreakeble = unbreakeble;

        return this;
    }

    //methods


    public ItemBuilder setLore(List<String> lore) {
        this.lore = lore;
        return this;
    }

    public ItemBuilder addLore(String s) {
        List<String> lore = this.lore;


        lore.add(s);

        this.lore = lore;
        return this;
    }

    public ItemBuilder setDisplayName(String s) {
        this.name = s;
        return this;
    }

    public ItemBuilder setLeatherColor(Color color) {
        this.lether = true;
        this.color = color;
        return this;
    }

    public ItemBuilder setSkullOwner(String name) {
        this.skull = true;
        this.skullOwner = name;
        return this;
    }

    public ItemBuilder setSkullTexture(String skullTexture) {
        this.skull = true;
        this.skullTexture = skullTexture;
        return this;
    }
    public ItemBuilder setSkullTexture(SkullTexture skullTexture) {
        this.skull = true;
        this.skullTexture = skullTexture.getBase64();
        return this;
    }
    public ItemStack build() {
        ItemStack itemStack;
        itemStack = new ItemStack(this.material, this.amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(this.name);
        itemMeta.setLore(this.lore);
        itemMeta.setUnbreakable(unbreakeble);
        itemStack.setItemMeta(itemMeta);
        if (this.lether) {
            LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemStack.getItemMeta();
            leatherArmorMeta.setColor(this.color);
            itemStack.setItemMeta(leatherArmorMeta);
            return itemStack;
        }

        if (this.skull) {
            SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
            skullMeta.setOwner(this.skullOwner);
            if(skullTexture != null){
                GameProfile profile = new GameProfile(UUID.randomUUID(), "");
                profile.getProperties().put("textures", new Property("textures", skullTexture));
                Field profileField;
                try {
                    profileField = skullMeta.getClass().getDeclaredField("profile");
                    profileField.setAccessible(true);
                    profileField.set(skullMeta, profile);
                } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
                    e.printStackTrace();
                }
            }
            itemStack.setItemMeta(skullMeta);
            return itemStack;
        }
        return itemStack;
    }


}