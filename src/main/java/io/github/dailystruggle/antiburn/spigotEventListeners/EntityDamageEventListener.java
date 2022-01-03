package io.github.dailystruggle.antiburn.spigotEventListeners;

import io.github.dailystruggle.antiburn.configs.Materials;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageEventListener implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    public void EntityDamageEventHandler(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if(!(entity instanceof Item item)) return;

        EntityDamageEvent.DamageCause damageCause = event.getCause();

        Bukkit.broadcastMessage("cause:" + damageCause.name());
        Bukkit.broadcastMessage("material:" + item.getItemStack().getType().name());

        Bukkit.broadcastMessage("contains damage cause - " + Materials.itemLists.containsKey(damageCause));
        if(!Materials.itemLists.containsKey(damageCause)) return;

        Bukkit.broadcastMessage("contains material - " + Materials.itemLists.get(damageCause).contains(item.getItemStack().getType()));
        if(Materials.itemLists.get(damageCause).contains(item.getItemStack().getType())) event.setCancelled(true);

    }
}
