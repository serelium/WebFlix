public class MovieRole {

	private MovieRoleType type;
	private String characterName;
	private Artist artist;
	
	public MovieRole(MovieRoleType type, String characterName, Artist artist){
		
		this.type = type;
		this.characterName = characterName;
		this.artist = artist;
	}
	
	public MovieRoleType getType() {
		return type;
	}

	public String getCharacterName() {
		return characterName;
	}

	public Artist getArtist() {
		return artist;
	}

	public enum MovieRoleType {
		
		Director,
		Actor,
		Scriptwritter
	}
}
