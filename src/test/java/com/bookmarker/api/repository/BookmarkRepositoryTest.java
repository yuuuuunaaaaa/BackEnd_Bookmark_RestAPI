package com.bookmarker.api.repository;

import com.bookmarker.api.domain.Bookmark;
import com.bookmarker.api.domain.BookmarkRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class BookmarkRepositoryTest {
    @Autowired
    private BookmarkRepository repository;

    @Container
    static PostgreSQLContainer postgresqlContainer = new PostgreSQLContainer("postgres:latest")
            .withDatabaseName("bookmarkTestdb")
            .withUsername("bookmarkTest")
            .withPassword("bookmarkTest");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresqlContainer::getUsername);
        registry.add("spring.datasource.password", postgresqlContainer::getPassword);
    }

    @BeforeAll
    static void beforeAll() {
        postgresqlContainer.start();
    }

    @AfterAll
    static void afterAll() {
        postgresqlContainer.stop();
    }

    @BeforeEach
    void beforeEach() {
        repository.deleteAll();
    }

    @Test
    public void create_select() {
        Bookmark bookmark = new Bookmark("Testing SpringBoot Applications", "https://labs.in/spring-boot-testing", Instant.now());
        Bookmark bookmark1 = new Bookmark("Testing REST APIs using Postman", "https://labs.in/testing-rest-apis-with-postman", Instant.now());

        repository.save(bookmark);
        repository.save(bookmark1);

        List<Bookmark> bookmarkList = new ArrayList<>();
        bookmarkList.addAll(repository.findAll());
        System.out.println(bookmarkList.size());

        Assertions.assertThat(bookmarkList).hasSize(2);
        Assertions.assertThat(bookmarkList.get(0)
                .getTitle())
                .isEqualTo("Testing SpringBoot Applications");

    }
}
