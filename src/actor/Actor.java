package actor;

import fileio.ActorInputData;

import java.util.ArrayList;
import java.util.Map;

public final class Actor {

    private final String name;
    private final String careerDescription;
    private final ArrayList<String> filmography;
    private final Map<ActorsAwards, Integer> awards;

    public Actor(final ActorInputData inputData) {
        this.name = inputData.getName();
        this.careerDescription = inputData.getCareerDescription();
        this.filmography = inputData.getFilmography();
        this.awards = inputData.getAwards();
    }

    public String getName() {
        return name;
    }

    public String getCareerDescription() {
        return careerDescription;
    }

    public ArrayList<String> getFilmography() {
        return filmography;
    }

    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    @Override
    public String toString() {
        return name;
    }
}
