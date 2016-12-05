package io.github.mascarpone.socialcitizens;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;

import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.event.NPCDamageByEntityEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.TraitName;

@TraitName("SocialNPCTrait")
public class SocialNPCTrait extends Trait {
		
	// ********************
	// PROTOTYPES
	
	// Utility functions interface: evaluation of a need
	interface UtilityFunction { public double exec(); }
	
	// Solution functions interface: strategy to fulfill a need using action functions
	protected interface SolutionFunction { public boolean exec(); }
	
	
	// ********************
	// VARIABLES
	
	// Association between the utility functions and their solutions
	private Map<UtilityFunction, SolutionFunction> behaviors;
	
	// Out-links in the social graph
	private Map<NPC, SocialLink> social_position;
	
	// Personality constants
	private int default_buddiness;
	private int hurt_sensibility;
	private int present_sensibility;
	private double social_need;
	private double anti_social_need;
	private double laziness;
	
	
	// ********************
	// TRAIT INITIALIZATION
	
	// Create a SocialNPC using the Citizens2 API and spawn it at the indicated location
	public SocialNPCTrait() {
		super("SocialNPC");
	}
	
	//Run code when your trait is attached to a NPC. 
    //This is called BEFORE onSpawn, so npc.getBukkitEntity() will return null
    //This would be a good place to load configurable defaults for new NPCs.
	@Override
	public void onAttach() {
		// Initialize the behavior strategies
		this.behaviors = new HashMap<UtilityFunction, SolutionFunction>();
		this.behaviors.put(new SatisfactionUtility(), new SatisfactionSolution());
		
		// Initialize the graph vision of the NPC
		this.social_position = new HashMap<NPC, SocialLink>();
		
		// Set parameters for an Social Citizen NPC
		this.npc.setProtected(false);
				
		// Set the NPC's personality
		// TODO: fill the following variables according to the NPC name or randomly for example
		this.default_buddiness = 1;
		this.hurt_sensibility = 1;
		this.present_sensibility = 1;
		this.social_need = 0;
		this.anti_social_need = 0;
		this.laziness = 1;
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
	// UTILITIES AND SOLUTIONS
		
	// Utility: Evaluate the satisfaction of the instance
	class SatisfactionUtility implements UtilityFunction { 
		public double exec() {
			return laziness;
		}
	}
	
	// Utility: Evaluate the need of sociability of the instance
	class SocialNeedUtility implements UtilityFunction { 
		public double exec() {
			// TODO implement interesting function HERE
			return 0;
		}
	}
	
	// Utility: Evaluate the need of non-sociability of the instance
	class AnimosityUtility implements UtilityFunction {
		public double exec() {
			// TODO implement interesting function HERE
			return 0;
		}
	}
	
	
	// Solution: Strategy to keep a good satisfaction of the current status
		// --> idle
	class SatisfactionSolution implements SolutionFunction {
		public boolean exec() {
			NPC best_friend = getBestfriend();
			if (best_friend == null) {
				if (npc.getEntity() != null) {
					npc.faceLocation(npc.getEntity().getLocation().add(0, 5, 0));
				}
			}
			else {
				npc.getNavigator().setTarget(best_friend.getEntity(), false);
				npc.faceLocation(best_friend.getEntity().getLocation());
			}
			return true;
		}
	}
	// Solution: Strategy to lower the instance's need of sociability
		// --> make a new friend
		// --> strengthen a friendship
	// TODO
	// Solution: Strategy to lower the instance's need of non-sociability
		// --> make a new enemy
		// --> strengthen an animosity
	// TODO
	
	
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
		
	
	// ********************	
	// MAIN
		
	// Make a decision based on the utility functions of the class
	// and apply the corresponding action and feedback
	// Called every tick
    @Override
    public void run() {
    	// Check if the NPC is currently doing an action
    	// ??? this.npc.getDefaultGoalController().isExecutingGoal()
    	if (true) {
    		// Compute the utility functions
    		Map<Double, SolutionFunction> decision = new HashMap<Double, SolutionFunction>();
    		for (UtilityFunction uf : this.behaviors.keySet()) {
    			decision.put(uf.exec(), behaviors.get(uf));
    		}		
    		//  Sort the needs and apply the according solution function
    		for (SolutionFunction sf : new TreeMap<Double, SolutionFunction>(decision).descendingMap().values()) {
    			if (sf.exec()) break;
    		}
    	}
    }
    
    
	// ********************	
	// EVENT HANDLERS
	
    // Change the social link's quality with the attacker 
    @EventHandler
    public void NPCDamageByEntityEvent(NPCDamageByEntityEvent event) {
    	if (event.getNPC() == this.getNPC()) {
    		if (event.getDamager() instanceof NPC) {
    			NPC attacker = (NPC) event.getDamager();
    			if (this.social_position.containsKey(attacker)) {
    				this.social_position.get(attacker).buddinessDown(this.hurt_sensibility);
    			}
    		}

            Bukkit.broadcastMessage("<" + this.npc.getName() + "> Ouch!");
        }
    }
    
    // Event Handler for right click 
    @EventHandler
    public void NPCRightClickEvent(NPCRightClickEvent event) {
    	if (event.getNPC() == this.getNPC()) {
    		// Check that it has a cookie
    		// Take the cookie
    		// Change social links
    		if (event.getClicker() instanceof NPC) {
    			NPC friend = (NPC) event.getClicker();
    			if (this.social_position.containsKey(friend)) {
    				this.social_position.get(friend).buddinessUp(this.present_sensibility);
    			}
    		}
			Bukkit.broadcastMessage("<" + this.npc.getName() + "> Ty you!");
    	}
    }
    
}
