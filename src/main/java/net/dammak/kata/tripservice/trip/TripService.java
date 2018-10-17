package net.dammak.kata.tripservice.trip;

import net.dammak.kata.tripservice.exception.UserNotLoggedInException;
import net.dammak.kata.tripservice.user.User;
import net.dammak.kata.tripservice.user.UserSession;

import java.util.ArrayList;
import java.util.List;


public class TripService {

	public List<Trip> getTripsByUser(User user) throws UserNotLoggedInException {
		List<Trip> tripList = new ArrayList<>();
		User loggedUser = getLoggedUser();
		boolean isFriend = false;
		if (loggedUser != null) {
			for (User friend : user.getFriends()) {
				if (friend.equals(loggedUser)) {
					isFriend = true;
					break;
				}
			}
			if (isFriend) {
				tripList = TripDAO.findTripsByUser(user);
			}
			return tripList;
		} else {
			throw new UserNotLoggedInException();
		}
	}

	protected User getLoggedUser() {
		return UserSession.getInstance().getLoggedUser();
	}

}
