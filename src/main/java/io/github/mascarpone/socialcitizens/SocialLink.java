package io.github.mascarpone.socialcitizens;

class SocialLink {

	private int buddiness;
	private int buddiness_upper_limit = 100;
	private int buddiness_lower_limit = -100;
	
	public SocialLink(int buddiness) { this.buddiness = buddiness; }
	
	public int getBuddiness() { return this.buddiness; }
	
	private void checkLimit() {
		if (this.buddiness > this.buddiness_upper_limit) this.buddiness = this.buddiness_upper_limit;
		if (this.buddiness < this.buddiness_lower_limit) this.buddiness = this.buddiness_lower_limit;
	}
	
	public int buddinessUp(int x) { 
		this.buddiness += x; 
		this.checkLimit();
		return this.getBuddiness();
	}
	
	public int buddinessDown(int x) {
		this.buddiness -= x;
		this.checkLimit();
		return this.getBuddiness();
	}
	
}
