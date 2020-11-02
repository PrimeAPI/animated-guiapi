package de.primeapi.guiapi;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public interface ICloseAction {

    void onClose(Player p, Inventory inventory);
}
