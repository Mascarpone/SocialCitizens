package io.github.mascarpone.socialcitizens;

import java.util.Iterator;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;

public final class SocialCitizens extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        // TODO Insert logic to be performed when the plugin is enabled
    	// if CitizensAPI is required *make sure you check the Citizens version in onEnable*
    	getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("onEnable has been invoked!");
    }
    
    @Override
    public void onDisable() {
        // TODO Insert logic to be performed when the plugin is disabled
		getLogger().info("onDisable has been invoked!");
    }
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
    	Player p = event.getPlayer();
    	
    	if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
    		Block clicked = event.getClickedBlock();
    		
    		// Create a clone of the player at his actual position
    		if (clicked.getType() == Material.STONE_BUTTON) {
    		    NPCRegistry registry = CitizensAPI.getNPCRegistry();
    		    NPC npc = registry.createNPC(EntityType.PLAYER, p.getName());
    		    npc.spawn(p.getLocation());
    		}
    	}
    }
    
}
