package com.qpzm7903;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPOutputStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2021-10-29-20:09
 */
class ObjectTestTest {

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void test_json() throws JsonProcessingException {
        ObjectTest objectTest = new ObjectTest();
        objectTest.setAccount(RandomStringUtils.random(10, true, true));
        objectTest.setAge(RandomStringUtils.random(10, true, true));
        objectTest.setCompany(RandomStringUtils.random(10, true, true));
        objectTest.setPhone(RandomStringUtils.random(10, true, true));
        objectTest.setAddress(RandomStringUtils.random(10, true, true));
        objectTest.setEmail(RandomStringUtils.random(10, true, true));
        objectTest.setName(RandomStringUtils.random(10, true, true));
        String s = objectMapper.writeValueAsString(objectTest);
        System.out.println(s.length());
    }

    @Test
    void test_array() throws IOException {
        List<ObjectTest> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {

            ObjectTest objectTest = new ObjectTest();
            objectTest.setAccount(RandomStringUtils.random(10, true, true));
            objectTest.setAge(RandomStringUtils.random(10, true, true));
            objectTest.setCompany(RandomStringUtils.random(10, true, true));
            objectTest.setPhone(RandomStringUtils.random(10, true, true));
            objectTest.setAddress(RandomStringUtils.random(10, true, true));
            objectTest.setEmail(RandomStringUtils.random(10, true, true));
            objectTest.setName(RandomStringUtils.random(10, true, true));
            list.add(objectTest);
        }
        String s = objectMapper.writeValueAsString(list);
//        System.out.println(s.length());

        FileOutputStream fileOutputStream = new FileOutputStream("test.json");
        byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
        System.out.println(bytes.length);
        fileOutputStream.write(bytes);
        fileOutputStream.close();

        FileOutputStream fileOutputStream1 = new FileOutputStream("test_compress.json");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = null;

        gzip = new GZIPOutputStream(out);
        gzip.write(bytes);
        byte[] b = out.toByteArray();
        System.out.println(b.length);
        fileOutputStream1.write(b);
        fileOutputStream1.close();

    }

}