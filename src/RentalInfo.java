import java.util.HashMap;

import PackageMovie.Movie;

public class RentalInfo {

// REFACTORIZACION - VEGA CENTENO RODRIGO SEBASTIAN

// Genera un registro de alquiler de películas para un cliente en base a su historial de alquileres
	
  public String statement(Customer customer) {
    HashMap<String, Movie> movies = createMoviesMap();

    double totalAmount = 0;
    int frequentEnterPoints = 0;
    String result = "Rental Record for " + customer.getName() + "\n";

    for (MovieRental r : customer.getRentals()) {
      double thisAmount = calculateAmount(r, movies);
      frequentEnterPoints += calculateFrequentEnterPoints(r, movies);
      result += getRentalFigure(r, movies, thisAmount);
      totalAmount += thisAmount;
    }

    // add footer lines
    result += "Amount owed is " + totalAmount + "\n";
    result += "You earned " + frequentEnterPoints + " frequent points\n";

    return result;
  }
  // metodo creado para agregar 4 peliculas 
  private static final String REGULAR = "regular";
  private HashMap<String, Movie> createMoviesMap() {
    HashMap<String, Movie> movies = new HashMap<String, Movie>();
    movies.put("F001", new Movie("You've Got Mail", REGULAR));
    movies.put("F002", new Movie("Matrix", REGULAR));
    movies.put("F003", new Movie("Cars", "childrens"));
    movies.put("F004", new Movie("Fast & Furious X", "new"));
    return movies;
  }
// Metodo creado para calcular el monto correspondiente a un alquiler de película 
  private double calculateAmount(MovieRental rental, HashMap<String, Movie> movies) {
    Movie movie = movies.get(rental.getMovieId());
    String code = movie.getCode();
    int days = rental.getDays();
    double amount = 0;

    if (code.equals(REGULAR)) {
      amount = 2;
      if (days > 2) {
        amount += (days - 2) * 1.5;
      }
    } else if (code.equals("new")) {
      amount = days * 3;
    } else if (code.equals("childrens")) {
      amount = 1.5;
      if (days > 3) {
        amount += (days - 3) * 1.5;
      }
    }

    return amount;
  }
//  Calcula los puntos de bonificación frecuentes para un alquiler de película
  private int calculateFrequentEnterPoints(MovieRental rental, HashMap<String, Movie> movies) {
    Movie movie = movies.get(rental.getMovieId());
    String code = movie.getCode();
    int days = rental.getDays();
    int points = 1;

    if (code.equals("new") && days > 2) {
      points++;
    }

    return points;
  }

  private String getRentalFigure(MovieRental rental, HashMap<String, Movie> movies, double amount) {
    Movie movie = movies.get(rental.getMovieId());
    String title = movie.getTitle();
    return "\t" + title + "\t" + amount + "\n";
  }
}
