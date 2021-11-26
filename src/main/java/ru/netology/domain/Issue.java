package ru.netology.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Issue implements Comparable<Issue> {
    private int id;
    private boolean isOpen;
    private String author;
    private int whenOpenIssue;
    private Set<String> labels = new HashSet<>();
    private Set<String> assignee = new HashSet<>();

    @Override
    public int compareTo(Issue o) {
        return whenOpenIssue - o.whenOpenIssue;
    }
}
