import java.util.Date;

public class Artist {

	private int id;
	private String name;
	private Date birthDate;
	private String birthPlace;
	private String pictureLink;
	private String biography;
	
	public Artist(int id, String name, Date birthDate, String birthPlace, String pictureLink, String biography) {
		
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
		this.birthPlace = birthPlace;
		this.pictureLink = pictureLink;
		this.biography = biography;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public String getBirthPlace() {
		return birthPlace;
	}

	public String getPictureLink() {
		return pictureLink;
	}

	public String getBiography() {
		return biography;
	}
	
	
}
