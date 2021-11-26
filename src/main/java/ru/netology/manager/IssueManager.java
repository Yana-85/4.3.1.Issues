package ru.netology.manager;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.netology.domain.Issue;
import ru.netology.repository.IssueRepository;

import java.util.*;
import java.util.function.Predicate;

@Data
@NoArgsConstructor
public class IssueManager {
    private IssueRepository repository;

    public IssueManager(IssueRepository repository) {
        this.repository = repository;
    }

    public void add(Issue issue) {
        repository.save(issue);
    }

    private List<Issue> filterBy(Predicate<Issue> predicate) {
        List<Issue> result = new ArrayList<>();
        for (Issue issue : repository.findAll()) {
            if (predicate.test(issue)) result.add(issue);
        }
        return result;
    }

    public List<Issue> findOpened() {
        return filterBy(issue -> issue.isOpen());
    }

    public List<Issue> findClosed() {
        return filterBy(issue -> !issue.isOpen());
    }

    public List<Issue> filterByAuthors(String author) {
        Predicate<String> byAuthor = issue -> issue.equals(author);
        List<Issue> issues = new ArrayList<>();
        for (Issue issue : repository.findAll())
            if (byAuthor.test(issue.getAuthor())) {
                issues.add(issue);
            }
        return issues;
    }

    public List<Issue> filterByLabels(Collection<? extends String> labels) {
        Predicate<Set<String>> byLabels = issue -> issue.containsAll(labels);
        List<Issue> issues = new ArrayList<>();
        for (Issue issue : repository.findAll())
            if (byLabels.test(issue.getLabels())) {
                issues.add(issue);
            }
        return issues;
    }

    public List<Issue> filterByAssignee(Collection<? extends String> assignee) {
        Predicate<Set<String>> byAssignee = issue -> issue.containsAll(assignee);
        List<Issue> issues = new ArrayList<>();
        for (Issue issue : repository.findAll())
            if (byAssignee.test(issue.getAssignee())) {
                issues.add(issue);
            }
        return issues;
    }

    public List<Issue> sortByNewest() {
        Comparator byNewest = Comparator.naturalOrder();
        List<Issue> issues = new ArrayList<>(repository.findAll());
        issues.sort(byNewest);
        return issues;
    }

    public List<Issue> sortByOldest() {
        Comparator byOldest = Comparator.reverseOrder();
        List<Issue> issues = new ArrayList<>(repository.findAll());
        issues.sort(byOldest);
        return issues;
    }

    public void openIssueById(int id) {
        for (Issue issue : repository.findAll()) {
            if (issue.getId() == id && !issue.isOpen()) {
                issue.setOpen(true);
            }
        }
    }

    public void closeIssueById(int id) {
        for (Issue issue : repository.findAll()) {
            if (issue.getId() == id && issue.isOpen()) {
                issue.setOpen(false);
            }
        }
    }
}
