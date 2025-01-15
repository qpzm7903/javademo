package com.example.demo;

import com.example.demo.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2022-05-12-6:03
 */
@SpringBootTest
@AutoConfigureJsonTesters
public class MyJsonTest {
    public static final String JSON_STRING = "{\n" +
            "  \"id\": \"1\",\n" +
            "  \"name\": \"test\",\n" +
            "  \"author\": \"001\",\n" +
            "  \"description\": \"test desc\",\n" +
            "  \"price\": \"00\"\n" +
            "}";
    @Autowired
    private JacksonTester<Book> json;

    @Test
    void serialize() throws Exception {
        Book books = new Book();
        books.setId("1");
        books.setName("test");
        books.setAuthor("001");
        books.setDescription("test desc");
        books.setPrice("00");
        // Assert against a `.json` file in the same package as the test
        assertThat(this.json.write(books)).isEqualToJson(JSON_STRING);
        // Or use JSON path based assertions
        assertThat(this.json.write(books)).hasJsonPathStringValue("@.id");
        assertThat(this.json.write(books)).extractingJsonPathStringValue("@.id").isEqualTo("1");
    }

    @Test
    void deserialize() throws Exception {

        Book books = new Book();
        books.setId("1");
        books.setName("test");
        books.setAuthor("001");
        books.setDescription("test desc");
        books.setPrice("00");
        assertThat(this.json.parse(JSON_STRING)).isEqualTo(books);
        assertThat(this.json.parseObject(JSON_STRING).getAuthor()).isEqualTo("001");
    }
}
