package net.dammak.kata.tripservice.trip;

import net.dammak.kata.tripservice.exception.CollaboratorCallException;
import net.dammak.kata.tripservice.user.User;

import java.util.List;


public class TripDAO {

	public static List<Trip> findTripsByUser(User user) {
		throw new CollaboratorCallException(
				"TripDAO should not be invoked on an unit test.");
	}
	
}