package view.utilities;

import java.util.ArrayList;

/**
 * A simple class to store information about the player, which is used to display the PlayerProfilePanel
 * @author flanagdonn
 *
 */
public class PlayerInfo {
	private Avatar avatar;

	private String teamMember1, teamMember2;

	private int happinessValue = 50;

	public PlayerInfo(){
		//set this player's team mates
		teamMember1 = randomTeamMember();
		teamMember2 = randomTeamMember();
		while(teamMember1.equals(teamMember2)){
			teamMember2 = randomTeamMember();
		}
	}

	/**
	 *
	 * @return The String name associated with this player's avatar
	 */
	public String getName() {
		return avatar.toString();
	}

	/**
	 *
	 * @return This player's avatar
	 */
	public Avatar getAvatar() {
		return avatar;
	}

	/**
	 * Creates a random team member, so that the current player doesn't feel alone,
	 * or like they don't have friends, even if they're playing in single-player mode.
	 * @return
	 */
	public String randomTeamMember() {
		ArrayList<String> teamMembers = new ArrayList<String>();
		teamMembers.add("Hercules Morse");
		teamMembers.add("Ronald McDonald");
		teamMembers.add("Bottomley Potts");
		teamMembers.add("Scarface Claw");
		teamMembers.add("Bitzer Maloney");
		teamMembers.add("Schnitzel von Krumm");

		int random = (int) (Math.random()*teamMembers.size());
		return teamMembers.get(random);
	}

	/**
	 *
	 * @return This player's imaginary team mate number 1
	 */
	public String getTeamMember1() {
		return teamMember1;
	}

	/**
	 *
	 * @return This player's imaginary team mate number 2
	 */
	public String getTeamMember2() {
		return teamMember2;
	}

	/**
	 *
	 * @param lvl The happiness level of this player, to be reassigned to the field.
	 */
	public void setHappinessValue(int lvl) {
		happinessValue = lvl;

	}

	/**
	 *
	 * @return This player's happiness level
	 */
	public int getHappinessValue() {
		return happinessValue;
	}

	public void setAvatar(Avatar chosenAvatar) {
		avatar = chosenAvatar;

	}



}
