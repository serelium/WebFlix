import java.util.ArrayList;

public class Movie {

	private int id;
	private String title;
	private int yearOfRelease;
	private ArrayList<Country> productionCountries;
	private Language originLanguage;
	private int length;
	private ArrayList<MovieGenre> genres;
	private MovieRole director;
	private ArrayList<Scriptwriter> scriptwriters;
	private ArrayList<MovieRole> cast;
	private ArrayList<Trailer> trailers;
	private String posterLink;
	private String synopsis;
	
	public Movie(int id,
	 String title,
	 int yearOfRelease,
	 ArrayList<Country> productionCountries,
	 Language originLanguage,
	 int length,
	 ArrayList<MovieGenre> genres,
	 MovieRole director,
	 ArrayList<Scriptwriter> scriptwriters,
	 ArrayList<MovieRole> cast,
	 ArrayList<Trailer> trailers,
	 String posterLink,
	 String synopsis) {
		
		this.id = id;
		this.title = title;
		this.yearOfRelease = yearOfRelease;
		this.productionCountries = productionCountries;
		this.originLanguage = originLanguage;
		this.length = length;
		this.genres = genres;
		this.director = director;
		this.scriptwriters = scriptwriters;
		this.cast = cast;
		this.trailers = trailers;
		this.posterLink = posterLink;
		this.synopsis = synopsis;
	}

	public int getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}

	public int getYearOfRelease() {
		return yearOfRelease;
	}

	public ArrayList<Country> getProductionCountries() {
		return productionCountries;
	}

	public Language getOriginLanguage() {
		return originLanguage;
	}

	public int getLength() {
		return length;
	}

	public ArrayList<MovieGenre> getGenres() {
		return genres;
	}

	public MovieRole getDirector() {
		return director;
	}

	public ArrayList<Scriptwriter> getScriptwriters() {
		return scriptwriters;
	}

	public ArrayList<MovieRole> getCast() {
		return cast;
	}

	public ArrayList<Trailer> getTrailerLinks() {
		return trailers;
	}

	public String getPosterLink() {
		return posterLink;
	}

	public String getSynopsis() {
		return synopsis;
	}
}
