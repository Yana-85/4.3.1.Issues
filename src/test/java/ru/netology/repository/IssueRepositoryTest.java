package ru.netology.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.netology.domain.Issue;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class IssueRepositoryTest {
    private IssueRepository repository = new IssueRepository();
    private Issue first = new Issue(1, true, "Author1", 1, new HashSet<>(Arrays.asList("label1", "label2", "label3")), new HashSet<>(Arrays.asList("assignee1", "assignee2", "assignee3")));
    private Issue second = new Issue(2, false, "Author2", 3, new HashSet<>(Arrays.asList("label4", "label5", "label6")), new HashSet<>(Arrays.asList("assignee7", "assignee8", "assignee9")));
    private Issue third = new Issue(3, true, "Author3", 5, new HashSet<>(Arrays.asList("label4", "label5", "label6")), new HashSet<>(Arrays.asList("assignee4", "assignee5", "assignee6")));

    @Nested
    public class EmptyRepository {
        @Test
        public void shouldReturnEmptyWhenNoIssues() {
            List<Issue> expected = List.of();
            List<Issue> actual = repository.findAll();
            assertEquals(expected, actual);
        }

        @Test
        public void shouldReturnNullWhenNoId() {
            repository.save(first);
            int idToFind = first.getId() - 1;
            Issue expected = null;
            Issue actual = repository.findById(idToFind);
            assertEquals(expected, actual);
        }
    }

    @Nested
    public class SingleIssueRepository {

        @BeforeEach
        public void setUp() {
            repository.addAll(List.of(first));
        }

        @Test
        public void shouldAddOneIssue() {
            List<Issue> expected = List.of(first);
            List<Issue> actual = repository.findAll();
            assertEquals(expected, actual);
        }

        @Test
        public void shouldFindIssueByIdIfExist() {
            repository.findById(1);
            List<Issue> expected = List.of(first);
            List<Issue> actual = repository.findAll();
            assertEquals(expected, actual);
        }

        @Test
        public void shouldFindIssueByIdIfNotExist() {
            repository.findById(2);
            List<Issue> expected = List.of(first);
            List<Issue> actual = repository.findAll();
            assertEquals(expected, actual);
        }

        @Test
        public void shouldRemoveByIdIfExist() {
            repository.removeById(1);
            List<Issue> expected = List.of();
            List<Issue> actual = repository.findAll();
            assertEquals(expected, actual);
        }

        @Test
        public void shouldRemoveByIdIfNotExist() {
            int removeId = 2;
            assertThrows(NotFoundException.class, () -> repository.removeById(removeId));
        }

        @Test
        public void shouldRemoveAll() {
            repository.removeAll(List.of(first));
            List<Issue> expected = List.of();
            List<Issue> actual = repository.findAll();
            assertEquals(expected, actual);
        }
    }

    @Nested
    public class MultipleIssuesRepository {

        @BeforeEach
        public void setUp() {
            repository.addAll(List.of(first, second, third));
        }

        @Test
        public void shouldAddAllIssue() {
            List<Issue> expected = List.of(first, second, third);
            List<Issue> actual = repository.findAll();
            assertEquals(expected, actual);
        }

        @Test
        public void shouldFindAllIssuesByIdIfExist() {
            repository.findById(1);
            repository.findById(2);
            repository.findById(3);
            List<Issue> expected = List.of(first, second, third);
            List<Issue> actual = repository.findAll();
            assertEquals(expected, actual);
        }

        @Test
        public void shouldFindIssuesByIdIfNotExist() {
            repository.findById(1);
            repository.findById(2);
            repository.findById(5);
            List<Issue> expected = List.of(first, second, third);
            List<Issue> actual = repository.findAll();
            assertEquals(expected, actual);
        }

        @Test
        public void shouldRemoveByIdIfExist() {
            repository.removeById(1);
            List<Issue> expected = List.of(second, third);
            List<Issue> actual = repository.findAll();
            assertEquals(expected, actual);
        }

        @Test
        public void shouldRemoveByIdIfNotExist() {
            int removeId = 4;
            assertThrows(NotFoundException.class, () -> repository.removeById(removeId));
        }
    }
}