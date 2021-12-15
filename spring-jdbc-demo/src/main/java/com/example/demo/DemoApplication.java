package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
    private static final Logger LOG = LoggerFactory.getLogger(DemoApplication.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        Connection connection = jdbcTemplate.getDataSource().getConnection();
        System.out.println("################");
        System.out.println(connection.getMetaData().getDatabaseProductVersion() + " " +
            connection.getMetaData().getURL());
        System.out.println("################");
        connection.close();

        initSchema();

        List<Object[]> todos = Arrays.asList(
            new Object[]{"Code", "{\"spring\": true}"},
            new Object[]{"Slides", "{\"status\": \"WORK_IN_PROGRESS\"}"}
        );

        jdbcTemplate.batchUpdate("INSERT INTO todos(name, data) VALUES (?, ?)", todos);

        jdbcTemplate.query("SELECT * FROM todos",
            (rs, i) -> new Todo(rs.getLong("id"),
                rs.getString("name"),
                rs.getString("data")))
            .forEach(System.out::println);

        jdbcTemplate.query("SELECT t.data.spring FROM todos t",
            (rs, i) -> rs.getString(1))
            .forEach(System.out::println);
    }

    private void initSchema() {
        URL url = DemoApplication.class.getClassLoader().getResource("com/example/demo/schema.ddl");

        try (Scanner sc = new Scanner(url.openStream())) {
            sc.useDelimiter("#");
            while (sc.hasNext()) {
                String line = sc.next().trim();
                jdbcTemplate.execute(line);
            }
        } catch (IOException e) {
            LOG.error("An error occurred when reading schema DDL from " + url, e);
        }
    }

    public static void main(String[] args) {
        System.setProperty("oracle.jdbc.fanEnabled", "false");

        System.setProperty("oracle.net.tns_admin",
            System.getProperty("user.dir") + File.separator + "wallet");

        SpringApplication.run(DemoApplication.class, args);
    }
}
