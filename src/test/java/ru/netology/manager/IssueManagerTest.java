package ru.netology.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.netology.domain.Issue;
import ru.netology.repository.IssueRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class IssueManagerTest {
    private IssueRepository repository = new IssueRepository();
    private IssueManager manager = new IssueManager(repository);
    private Issue first = new Issue(1, true, "Author1", 1, new HashSet<>(List.of("label1", "label2", "label3")), new HashSet<>(List.of("assignee1", "assignee2", "assignee3")));
    private Issue second = new Issue(2, false, "Author2", 3, new HashSet<>(List.of("label4", "label5", "label6")), new HashSet<>(List.of("assignee7", "assignee8", "assignee9")));
    private Issue third = new Issue(3, true, "Author1", 5, new HashSet<>(List.of("label4", "label5", "label6")), new HashSet<>(List.of("assignee4", "assignee1", "assignee6")));

    @Nested
    public class EmptyManager {

        @Test
        public void shouldReturnEmptyWhenFindOpened() {
            List<Issue> expected = List.of();
            List<Issue> actual = manager.findOpened();
            assertEquals(expected, actual);
        }

        @Test
        public void shouldReturnEmptyWhenFindClosed() {
            List<Issue> expected = List.of();
            List<Issue> actual = manager.findClosed();
            assertEquals(expected, actual);
        }

        @Test
        public void shouldReturnEmptyWhenFilteredByAuthor() {
            Collection<Issue> expected = List.of();
            Collection<Issue> actual = manager.filterByAuthors("Author1");
            assertEquals(expected, actual);
        }

        @Test
        public void shouldReturnEmptyWhenFilteredByLabel() {
            Collection<Issue> expected = List.of();
            Collection<Issue> actual = manager.filterByLabels(new HashSet<>(List.of("label1", "label2", "label3")));
            assertEquals(expected, actual);
        }

        @Test
        public void shouldReturnEmptyWhenFilteredByAssignee() {
            Collection<Issue> expected = List.of();
            Collection<Issue> actual = manager.filterByAssignee(new HashSet<>(List.of("assignee1", "assignee2", "assignee3")));
            assertEquals(expected, actual);
        }

        @Test
        public void shouldReturnEmptyIfNothingToSortByNewest() {
            List<Issue> expected = List.of();
            List<Issue> actual = manager.sortByNewest();
            assertEquals(expected, actual);
        }

        @Test
        public void shouldReturnEmptyIfNothingToSortByOldest() {
            List<Issue> expected = List.of();
            List<Issue> actual = manager.sortByOldest();
            assertEquals(expected, actual);
        }

        @Test
        public void shouldReturnEmptyIfOpened() {
            manager.openIssueById(2);
            List<Issue> expected = List.of();
            List<Issue> actual = manager.findOpened();
            assertEquals(expected, actual);
        }

        @Test
        public void shouldReturnEmptyIfClosed() {
            manager.closeIssueById(1);
            List<Issue> expected = List.of();
            List<Issue> actual = manager.findClosed();
            assertEquals(expected, actual);
        }
    }

    @Nested
    public class SingleIssueManager {

        @Test
        public void shouldFindOpenIfOpened() {
            manager.add(first);
            List<Issue> expected = List.of(first);
            List<Issue> actual = manager.findOpened();
            assertEquals(expected, actual);
        }

        @Test
        public void shouldReturnEmptyIfNotOpened() {
            manager.add(second);
            List<Issue> expected = List.of();
            List<Issue> actual = manager.findOpened();
            assertEquals(expected, actual);
        }

        @Test
        public void shouldFindClosedIfClosed() {
            manager.add(second);
            List<Issue> expected = List.of(second);
            List<Issue> actual = manager.findClosed();
            assertEquals(expected, actual);
        }

        @Test
        public void shouldReturnEmptyIfNotClosed() {
            manager.add(third);
            List<Issue> expected = List.of();
            List<Issue> actual = manager.findClosed();
            assertEquals(expected, actual);
        }

        @Test
        public void shouldFilteredByAuthor() {
            manager.add(first);
            Collection<Issue> expected = List.of(first);
            Collection<Issue> actual = manager.filterByAuthors("Author1");
            assertEquals(expected, actual);
        }

        @Test
        public void shouldReturnEmptyIfNoAuthor() {
            manager.add(first);
            Collection<Issue> expected = List.of();
            Collection<Issue> actual = manager.filterByAuthors("Author2");
            assertEquals(expected, actual);
        }

        @Test
        public void shouldFilteredByLabel() {
            manager.add(first);
            Collection<Issue> expected = List.of(first);
            Collection<Issue> actual = manager.filterByLabels(new HashSet<>(List.of("label1", "label3")));
            assertEquals(expected, actual);
        }

        @Test
        public void shouldReturnEmptyIfNoLabel() {
            manager.add(first);
            Collection<Issue> expected = List.of();
            Collection<Issue> actual = manager.filterByLabels(new HashSet<>(List.of("label10")));
            assertEquals(expected, actual);
        }

        @Test
        public void shouldFilteredByAssignee() {
            manager.add(first);
            Collection<Issue> expected = List.of(first);
            Collection<Issue> actual = manager.filterByAssignee(new HashSet<>(List.of("assignee1", "assignee2", "assignee3")));
            assertEquals(expected, actual);
        }

        @Test
        public void shouldReturnEmptyIfNoAssignee() {
            manager.add(first);
            Collection<Issue> expected = List.of();
            Collection<Issue> actual = manager.filterByAssignee(new HashSet<>(List.of("assignee4", "assignee5", "assignee6")));
            assertEquals(expected, actual);
        }

        @Test
        public void shouldReturnOneIfSortByNewest() {
            manager.add(first);
            List<Issue> expected = List.of(first);
            List<Issue> actual = manager.sortByNewest();
            assertEquals(expected, actual);
        }

        @Test
        public void shouldReturnOneIfSortByOldest() {
            manager.add(first);
            List<Issue> expected = List.of(first);
            List<Issue> actual = manager.sortByOldest();
            assertEquals(expected, actual);
        }

        @Test
        public void shouldOpenIssueById() {
            manager.add(second);
            manager.openIssueById(2);
            List<Issue> expected = List.of(second);
            List<Issue> actual = manager.findOpened();
            assertEquals(expected, actual);
        }

        @Test
        public void shouldDoNothingIfWrongIdWhenOpen() {
            manager.add(first);
            manager.openIssueById(10);
            List<Issue> expected = List.of(first);
            List<Issue> actual = manager.findOpened();
            assertEquals(expected, actual);
        }

        @Test
        public void shouldCloseIssueById() {
            manager.add(third);
            manager.closeIssueById(3);
            List<Issue> expected = List.of(third);
            List<Issue> actual = manager.findClosed();
            assertEquals(expected, actual);
        }

        @Test
        public void shouldDoNothingIfWrongIdWhenClose() {
            manager.add(first);
            manager.closeIssueById(10);
            List<Issue> expected = List.of();
            List<Issue> actual = manager.findClosed();
            assertEquals(expected, actual);
        }

        @Test
        public void shouldDoNothingIfNothingToClose() {
            manager.add(second);
            manager.closeIssueById(2);
            List<Issue> expected = List.of(second);
            List<Issue> actual = manager.findClosed();
            assertEquals(expected, actual);
        }
    }

    @Nested
    public class MultipleIssuesManager {

        @BeforeEach
        public void setUp() {
            repository.addAll(List.of(first, second, third));
        }

        @Test
        public void shouldAddMultipleIssues() {
            List<Issue> expected = List.of(first, second, third);
            List<Issue> actual = repository.findAll();
            assertEquals(expected, actual);
        }

        @Test
        public void shouldSortByNewest() {
            List<Issue> expected = List.of(first, second, third);
            List<Issue> actual = manager.sortByNewest();
            assertEquals(expected, actual);
        }

        @Test
        public void shouldSortByOldest() {
            List<Issue> expected = List.of(third, second, first);
            List<Issue> actual = manager.sortByOldest();
            assertEquals(expected, actual);
        }

        @Test
        public void shouldFindByAuthor() {
            Collection<Issue> expected = List.of(first, third);
            Collection<Issue> actual = manager.filterByAuthors("Author1");
            assertEquals(expected, actual);
        }

        @Test
        public void shouldReturnEmptyIfNoAuthor() {
            Collection<Issue> expected = List.of();
            Collection<Issue> actual = manager.filterByAuthors("Author4");
            assertEquals(expected, actual);
        }

        @Test
        public void shouldFindByLabel() {
            Collection<Issue> expected = List.of(second, third);
            Collection<Issue> actual = manager.filterByLabels(new HashSet<>(List.of("label4")));
            assertEquals(expected, actual);
        }

        @Test
        public void shouldReturnEmptyIfNoLabel() {
            Collection<Issue> expected = List.of();
            Collection<Issue> actual = manager.filterByLabels(new HashSet<>(List.of("label10")));
            assertEquals(expected, actual);
        }

        @Test
        public void shouldFindByAssignee() {
            Collection<Issue> expected = List.of(first, third);
            Collection<Issue> actual = manager.filterByAssignee(new HashSet<>(List.of("assignee1")));
            assertEquals(expected, actual);
        }

        @Test
        public void shouldReturnEmptyWhenFindByAssignee() {
            Collection<Issue> expected = List.of();
            Collection<Issue> actual = manager.filterByAssignee(new HashSet<>(List.of("assignee10")));
            assertEquals(expected, actual);
        }
    }
}