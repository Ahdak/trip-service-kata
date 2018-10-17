package net.dammak.kata.tripservice.trip;

import net.dammak.kata.tripservice.exception.UserNotLoggedInException;
import net.dammak.kata.tripservice.user.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TripServiceShould {

    private static final User UN_LOGGED_USER = null;
    private static final User OTHER_USER = new User();
    private static final User LOGGED_USER = new User();
    private static final Trip TRIP_TO_SFAX = new Trip();
    private static final Trip TRIP_TO_TUNIS = new Trip();

    @Test
    void throw_not_logged_exception_when_user_not_logged() {
        assertThatThrownBy(() -> TestableTripService.ofUnloggedUser().getTripsByUser(OTHER_USER)).isInstanceOf(UserNotLoggedInException.class);
    }

    @Test
    void return_no_trip_when_logged_user_is_not_friend_with_user_having_no_friend() {
        var userWithNoFriend = new User();
        userWithNoFriend.addTrip(TRIP_TO_SFAX);
        userWithNoFriend.addTrip(TRIP_TO_TUNIS);
        List<Trip> trips = TestableTripService.ofLoggedUser().getTripsByUser(userWithNoFriend);
        assertThat(trips).isEmpty();
    }

    @Test
    void return_trip_to_tunis_and_trip_to_sfax_when_logged_user_friend_with_user_having_those_trips() {
        var userHavingFriend = new User();
        userHavingFriend.addTrip(TRIP_TO_SFAX);
        userHavingFriend.addTrip(TRIP_TO_TUNIS);
        userHavingFriend.addFriend(LOGGED_USER);
        List<Trip> trips = TestableTripService.ofLoggedUser().getTripsByUser(userHavingFriend);
        assertThat(trips).contains(TRIP_TO_TUNIS, TRIP_TO_SFAX);
    }

    private static class TestableTripService extends TripService {

        private User loggedUser;

        private TestableTripService(User user) {
            this.loggedUser = user;
        }

        private static TestableTripService ofUnloggedUser() {
            return new TestableTripService(UN_LOGGED_USER);
        }

        private static TestableTripService ofLoggedUser() {
            return new TestableTripService(LOGGED_USER);
        }

        @Override
        protected User getLoggedUser() {
            return loggedUser;
        }

        @Override
        protected List<Trip> findTripsByUser(User user) {
            return user.trips();
        }

    }

	
}
