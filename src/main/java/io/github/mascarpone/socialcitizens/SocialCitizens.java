package io.github.mascarpone.socialcitizens;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;

public final class SocialCitizens extends JavaPlugin implements Listener {
	
	private Map<Integer, NPC> social_network;
	
    @Override
    public void onEnable() {
    	// Check if Citizens is present and enabled.
    	if(getServer().getPluginManager().getPlugin("Citizens") == null || getServer().getPluginManager().getPlugin("Citizens").isEnabled() == false) {
    		getLogger().log(Level.SEVERE, "Citizens 2.0 not found or not enabled");
    		getServer().getPluginManager().disablePlugin(this);	
    		return;
    	}	

    	// Register your trait with Citizens.        
    	net.citizensnpcs.api.CitizensAPI.getTraitFactory().registerTrait(net.citizensnpcs.api.trait.TraitInfo.create(SocialNPCTrait.class));	
    	
    	// Initialize the Network
    	this.social_network = new HashMap<Integer, NPC>();
    	
    	// Register the plugin's events
    	// getServer().getPluginManager().registerEvents(this, this);
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    	if (cmd.getName().equalsIgnoreCase("snpc")) { 
    		if (!(sender instanceof Player)) {
    			sender.sendMessage("This command can only be run by a player.");
    		} else {
    			Player player = (Player) sender;
    			if (args.length == 2) {
    				if (args[0].equalsIgnoreCase("create")) {
    					// Spawn a new SocialNPC at the player's location
    					NPCRegistry registry = CitizensAPI.getNPCRegistry();
    					NPC npc = registry.createNPC(EntityType.PLAYER, args[1]);
    					npc.addTrait(new SocialNPCTrait());
    					for (NPC n : registry) {
    						n.getTrait(SocialNPCTrait.class).addLink(npc);
    						npc.getTrait(SocialNPCTrait.class).addLink(n);
    					}
    					npc.spawn(player.getLocation());
    					player.sendMessage(ChatColor.GREEN + "You created " + ChatColor.YELLOW + args[1] );
    				}
    				else if (args[0].equalsIgnoreCase("remove")) {
    					// Destroy the NPC with the given id
    					NPCRegistry registry = CitizensAPI.getNPCRegistry();
    					NPC npc = registry.getById(Integer.parseInt(args[1]));
    					for (NPC n : registry) {
    						n.getTrait(SocialNPCTrait.class).delLink(npc);
    					}
    					String name = npc.getName();
    					npc.destroy();
    					player.sendMessage(ChatColor.YELLOW + name + ChatColor.RED + " has been removed.");
    				}
    			}
    			else {
    				// Give usage
    				sender.sendMessage("usage: /snpc [create <name> | remove <id>]");
    			}
    		}
    		return true;
    	}
    	return false; 
    }
    
}
