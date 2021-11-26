package ru.netology.domain;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class IssueTest {
    private Issue first = new Issue(1, true, "Author1", 1, new HashSet<>(List.of("label1", "label2", "label3")), new HashSet<>(List.of("assignee1", "assignee2", "assignee3")));
    private Issue second = new Issue(2, false, "Author2", 5, new HashSet<>(List.of("label4", "label5", "label6")), new HashSet<>(List.of("assignee7", "assignee8", "assignee9")));
    private Issue third = new Issue(3, true, "Author1", 3, new HashSet<>(List.of("label4", "label5", "label6")), new HashSet<>(List.of("assignee4", "assignee1", "assignee6")));

    @Test
    public void shouldSortByTimeOpened() {
        Issue[] expected = new Issue[]{first, third, second};
        Issue[] actual = new Issue[]{first, second, third};

        Arrays.sort(actual);

        assertArrayEquals(expected, actual);
    }
}
