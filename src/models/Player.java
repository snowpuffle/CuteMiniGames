package models;

public class Player {
	// Player Attributes
	private String name;
	private char token;

	// Default Class Constructor
	public Player(String name, char token) {
		this.name = name;
		this.token = token;
	}

	// Get Player's Name
	public String getName() {
		return name;
	}

	// Get Player's Token: 'X' or 'O'
	public char getToken() {
		return token;
	}
}