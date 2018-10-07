public class MovieRole {

	private int id;
	private MovieRoleType type;
	private String characterName;
	private Artist artist;
	private Movie movie;
	
	public MovieRole(int id, MovieRoleType type, String characterName, Artist artist, Movie movie){
		
		this.type = type;
		this.characterName = characterName;
		this.artist = artist;
		this.movie = movie;
	}
	
	public enum MovieRoleType {
		
		Director,
		Actor,
		Scriptwritter
	}
}
