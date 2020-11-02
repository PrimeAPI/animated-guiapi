package de.primeapi.guiapi;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Objects;

public class GUIManager implements Listener {

    public HashMap<ItemStack, IClickAction> items = new HashMap<>();
    public HashMap<Inventory, ICloseAction> inventories = new HashMap<>();
    @Getter
    public static GUIManager instance;


    public GUIManager(Plugin plugin){
        instance = this;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public void registerGUI(GUIBuilder builder) {
        items.putAll(builder.items);
        inventories.put(builder.inventory, builder.closeAction);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (inventories.containsKey(e.getInventory())) {
            e.setCancelled(true);
        }
        if (items.containsKey(e.getCurrentItem())) {
            if (e.getWhoClicked() instanceof Player) {
                e.setCancelled(true);
                items.get(e.getCurrentItem()).onClick((Player) e.getWhoClicked(), e.getCurrentItem());
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (inventories.containsKey(e.getInventory())) {
            if (e.getPlayer() instanceof Player) {
                if (Objects.nonNull(inventories.get(e.getInventory())))
                    inventories.get(e.getInventory()).onClose((Player) e.getPlayer(), e.getInventory());
            }
        }
    }

    //Factory
    public static AnimationConfiguration createDefaultAnimationConfiguration(){
        return new AnimationConfiguration(GUIBuilder.Animation.STAR, 50, Sound.CLICK, Sound.LEVEL_UP);
    }

    public static AnimationConfiguration createDefaultAnimationConfiguration(GUIBuilder.Animation animation){
        switch (animation){
            case LEFT:
            case LEFT_FILLER:
            case STAR:
                return new AnimationConfiguration(animation, 50, Sound.CLICK, Sound.LEVEL_UP);
            case CLOCKWISE:
                return new AnimationConfiguration(animation, 10, Sound.CLICK, Sound.LEVEL_UP);
            default:
                return new AnimationConfiguration(GUIBuilder.Animation.STAR, 50, Sound.CLICK, Sound.LEVEL_UP);
        }
    }

}
