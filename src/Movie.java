import java.util.ArrayList;

public class Movie {

	private String title;
	private int yearOfRelease;
	private ArrayList<Country> productionCountries;
	private Language originLanguage;
	private int length;
	private ArrayList<MovieGenre> genres;
	private Artist director;
	private ArrayList<Artist> scriptwritters;
	private ArrayList<Artist> cast;
	private String synopsis;
	
	public Movie(	 String title,
	 int yearOfRelease,
	 ArrayList<Country> productionCountries,
	 Language originLanguage,
	 int length,
	 ArrayList<MovieGenre> genres,
	 Artist director,
	 ArrayList<Artist> scriptwritters,
	 ArrayList<Artist> cast,
	 String synopsis) {
		
		this.title = title;
		this.yearOfRelease = yearOfRelease;
		this.productionCountries = productionCountries;
		this.originLanguage = originLanguage;
		this.length = length;
		this.genres = genres;
		this.director = director;
		this.scriptwritters = scriptwritters;
		this.cast = cast;
		this.synopsis = synopsis;
	}
}
