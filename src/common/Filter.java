package common;

import actor.ActorsAwards;
import entertainment.Genre;
import utils.Utils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Filter {

    private final Integer year;
    private final Genre genre;
    private final List<String> words;
    private final List<ActorsAwards> awards;

    private boolean invalid;

    public Filter(final List<List<String>> input) {

        if (input.get(1) != null
                && input.get(0).size() != 0
                && input.get(1).get(0) != null
                && Utils.stringToGenre(input.get(1).get(0)) == null) {
            invalid = true;
        }
        this.year =
                input.get(0) != null
                        && input.get(0).size() != 0
                        && input.get(0).get(0) != null
                        ? Integer.parseInt(input.get(0).get(0)) : null;

        this.genre =
                input.get(1) != null
                        && input.get(0).size() != 0
                        && input.get(1).get(0) != null
                        ? Utils.stringToGenre(input.get(1).get(0)) : null;

        this.words =
                input.get(2) != null
                        ? input.get(2) : null;

        this.awards =
                input.get(3) == null
                        ? null
                        : input.get(3).stream()
                                .map(Utils::stringToAwards)
                                .collect(Collectors.toList());
    }

    public final Optional<Integer> getYear() {
        return Optional.ofNullable(year);
    }

    public final Optional<Genre> getGenre() {
        return Optional.ofNullable(genre);
    }

    public final Optional<List<String>> getWords() {
        return Optional.ofNullable(words);
    }

    public final Optional<List<ActorsAwards>> getAwards() {
        return Optional.ofNullable(awards);
    }

    public final boolean getInvalid() {
        return invalid;
    }
}
