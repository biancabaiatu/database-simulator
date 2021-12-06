package user;

import common.Database;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class UserResource {

    private final Database database;

    private static UserResource instance = null;

    private UserResource() {
        this.database = Database.getInstance();
    }

    /**
     * Gets instance of the UserResource
     * class using the Singleton pattern
     * @return instance of UserResource class
     */
    public static UserResource getInstance() {
        if (instance == null) {
            instance = new UserResource();
        }
        return instance;
    }

    /**
     * Determines a list of n users
     * that have given the most ratings
     * @param n Number of users to be listed
     * @return List of most active users
     */
    public List<User> getMostActiveUsers(final int n, final boolean reversed) {
        Comparator<User> comparator = Comparator
                .comparingInt(User::getNumberOfRatings)
                .thenComparing(User::getUsername);
        if (reversed) {
            comparator = comparator.reversed();
        }

        return database.getUsers().stream()
                .sorted(comparator)
                .filter((user) -> (user.getNumberOfRatings() > 0))
                .limit(n)
                .collect(Collectors.toList());
    }

    /**
     * Searches for an user in the database based on the name
     * @param userName Name to be searched for in the users' list
     * @return The user that was found in the database
     */
    public User getUserByUsername(final String userName) {
        for (User user : database.getUsers()) {
            if (user.getUsername().equals(userName)) {
                return user;
            }
        }
        return null;
    }

}
