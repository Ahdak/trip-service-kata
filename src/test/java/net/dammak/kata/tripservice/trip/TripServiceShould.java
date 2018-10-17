package net.dammak.kata.tripservice.trip;

import net.dammak.kata.tripservice.exception.UserNotLoggedInException;
import net.dammak.kata.tripservice.user.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TripServiceShould {

    private static final User UN_LOGGED_USER = null;
    private static final User OTHER_USER = new User();

    @Test
    void throw_not_logged_exception_when_user_not_logged() {
        assertThatThrownBy(() -> TestableTripService.ofUnloggedUser().getTripsByUser(OTHER_USER)).isInstanceOf(UserNotLoggedInException.class);
    }

    private static class TestableTripService extends TripService {

        private User loggedUser;

        private TestableTripService(User user) {
            this.loggedUser = user;
        }

        private static TestableTripService ofUnloggedUser() {
            return new TestableTripService(UN_LOGGED_USER);

        }

        @Override
        protected User getLoggedUser() {
            return loggedUser;
        }

    }

	
}
