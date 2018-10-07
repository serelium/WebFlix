import java.util.Date;

public class Artist {

	private int id;
	private String name;
	private Date birthDate;
	private String birthPlace;
	private String biography;
	
	public Artist(int id, String name, Date birthDate, String birthPlace, String biography) {
		
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
		this.birthPlace = birthPlace;
		this.biography = biography;
	}
}
