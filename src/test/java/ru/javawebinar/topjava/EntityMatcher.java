package ru.javawebinar.topjava;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class EntityMatcher<T> {
    private final String[] IGNORED_FIELDS;

    public EntityMatcher(String... ignoredFields) {
        this.IGNORED_FIELDS = ignoredFields;
    }

    public void assertMatch(T actual, T expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, IGNORED_FIELDS);
    }

    public void assertMatch(Iterable<T> actual, T... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public void assertMatch(Iterable<T> actual, Iterable<T> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields(IGNORED_FIELDS).isEqualTo(expected);
    }
}