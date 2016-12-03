package io.github.mascarpone.socialcitizens;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.event.block.Action;

import net.citizensnpcs.api.npc.NPC;

public class SocialNPC {

	// ********************
	// ACTIONS HANDLER NEEDED
	
	// List all the actions that trigger feedbacks for SocialNPCs
	// Will be used in the plugin to call actionFeedback()
	public static List<Action> handledActions;
	
	
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
	
	// Citizens' NPC manipulated
	private NPC npc;
	
	// Out-links in the social graph
	private Map<SocialNPC, SocialLink> social_position;
	
	// Personality traits
	private double social_need;
	private double anti_social_need;
	private double laziness;
	
	
	// ********************
	// CONSTRUCTORS
	
	// Create a SocialNPC using the Citizens2 API and spawn it at the indicated location
	public SocialNPC(   ) {
		// Initialize variables
		this.social_position = new HashMap<SocialNPC, SocialLink>();
		
		// Create an Citizens NPC
		
		// Set the NPC's personality
		
		// Spawn the NPC
	}

	
	// ********************
	// PRIVATE USEFUL FUNCTIONS
	
	// Find the best relationship in the instance's point of view of the social graph
	private SocialNPC getBestfriend() { return new SocialNPC(); }
	
	
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
	public void addLink(NPC npc) {}
	
	// Delete an existing link to another SocialNPC
	public void delLink(NPC npc) {}
	
	// Make a decision based on the utility functions of the class
	// and apply the corresponding action and feedback
	public void utilityChoice(   ) {}
	
	// Find the change in the NPC's status and
	// change the instance's quality of the link with its interlocutor accordingly 
	public void actionFeedback(SocialNPC interlocutor) {}
	
}
