import java.util.Date;

public class Rent {

	private MovieCopy movieCopy;
	private Date rentTime;
	
	public Rent(MovieCopy movieCopy, Date rentTime){
		
		this.movieCopy = movieCopy;
		this.rentTime = rentTime;
	}
}
