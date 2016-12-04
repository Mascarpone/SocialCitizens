package io.github.mascarpone.socialcitizens;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.ai.Goal;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.TraitName;

@TraitName("SocialNPCTrait")
public class SocialNPCTrait extends Trait {
		
	// ********************
	// PROTOTYPES
	
	// Action functions interface: orders to make the NPC act in the Minecraft world
	protected interface ActionFunction { public void exec(); }
	
	// Feedback functions interface: reaction to another SocialNPC's action towards the instance
	protected interface FeedbackFunction { public void exec(); }
	
	// Utility functions interface: evaluation of a need
	protected interface UtilityFunction { public double exec(); }
	
	// Solution functions interface: strategy to fulfill a need using action functions
	protected interface SolutionFunction { public void exec(); }
	
	
	// ********************
	// VARIABLES
	
	// Out-links in the social graph
	private Map<NPC, SocialLink> social_position;
	
	// Personality constants
	private int default_buddiness;
	private double social_need;
	private double anti_social_need;
	private double laziness;
	
	
	// ********************
	// CONSTRUCTORS
	
	// Create a SocialNPC using the Citizens2 API and spawn it at the indicated location
	public SocialNPCTrait() {
		super("SocialNPC");
	}

	
	// ********************
	// PRIVATE USEFUL FUNCTIONS
	
	// Find the best relationship in the instance's point of view of the social graph
	// If there is no NPC with a positive buddiness, return null
	private NPC getBestfriend() { 
		NPC best_friend = null;
		int best_buddiness = 0;
		for (Map.Entry<NPC, SocialLink> entry : this.social_position.entrySet()) {
			if (entry.getValue().getBuddiness() > best_buddiness) {
				best_friend = entry.getKey();
				best_buddiness = entry.getValue().getBuddiness();
			}
		}
		return best_friend;
	}
	
	
	// ********************
	// PROTECTED FUNCTIONS
		
	// Action: Give a cookie
	// Action: Hit a player
	// Action: Be idle
	
	// Feedback: Reaction when the instance is being given a cookie
	// Feedback: Reaction when the instance is being hit
	
	// Utility: Evaluate the satisfaction of the instance
	// Utility: Evaluate the need of sociability of the instance
	// Utility: Evaluate the need of non-sociability of the instance
	
	// Solution: Strategy to keep a good satisfaction of the current status
		// --> idle
	// Solution: Strategy to lower the instance's need of sociability
		// --> make a new friend
		// --> strengthen a friendship
	// Solution: Strategy to lower the instance's need of non-sociability
		// --> make a new enemy
		// --> strengthen an animosity
	
	
	// ********************
	// PUBLIC FUNCTIONS
	
	// Add a new link to an unknown NPC
	public void addLink(NPC snpc) {
		if (!this.social_position.containsKey(snpc)) {
			this.social_position.put(snpc, new SocialLink(default_buddiness));
		}
	}
	
	// Delete an existing link to another SocialNPC
	public void delLink(NPC snpc) {
		this.social_position.remove(snpc);
	}
	
	// Find the change in the NPC's status and
	// change the instance's quality of the link with its interlocutor accordingly 
	public void actionFeedback(NPC interlocutor) {}
	
	
	// ********************	
	// TRAIT FUNCTIONS
	
	//Run code when your trait is attached to a NPC. 
    //This is called BEFORE onSpawn, so npc.getBukkitEntity() will return null
    //This would be a good place to load configurable defaults for new NPCs.
	@Override
	public void onAttach() {
		// Initialize the graph vision of the NPC
		this.social_position = new HashMap<NPC, SocialLink>();
		
		// Set parameters for an Social Citizen NPC
		this.npc.setProtected(false);
				
		// Set the NPC's personality
		// TODO: fill the following variables according to the NPC name or randomly for example
		this.default_buddiness = 0;
		this.social_need = 0;
		this.anti_social_need = 0;
		this.laziness = 0;
	}
	
	// Make a decision based on the utility functions of the class
	// and apply the corresponding action and feedback
	// Called every tick
    @Override
    public void run() {}
	
    // Change the social link's quality with the attacker 
    @EventHandler
    public void NPCDamageByEntityEvent(EntityDamageByEntityEvent event) {
    	Bukkit.broadcastMessage(this.npc.getName() + " has been damaged.");
    }
    
}
