package com.example.demo;

import oracle.sql.json.OracleJsonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.File;
import java.sql.Connection;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
    @Autowired
    private TodoRepository repository;

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

        repository.deleteAll();

        repository.save(new Todo("Code", "spring: true"));
        repository.save(new Todo("Slides", "status: WORK_IN_PROGRESS"));

        System.out.println("== FindAll() ==");
        for (Todo todo : repository.findAll()) {
            System.out.println(todo);
        }

        System.out.println("== FindByName() ==");
        for (Todo todo : repository.findByName("Code")) {
            System.out.println(todo);
        }

        System.out.println("== JSON serialize from DB ==");
        jdbcTemplate.query("SELECT JSON_SERIALIZE(t.data) FROM todo t",
            (rs, i) -> rs.getObject(1))
            .forEach(System.out::println);

        System.out.println("== JSON parser in app ==");
        OracleJsonFactory ojf = new OracleJsonFactory();
        jdbcTemplate.query("SELECT t.data FROM todo t",
            (rs, i) -> ojf.createJsonBinaryValue(rs.getBinaryStream(1)))
            .forEach(System.out::println);

        System.out.println("== SQL Query over JSON ==");
        jdbcTemplate.query("SELECT t.data.name, t.data.data FROM todo t",
            (rs, i) -> rs.getString(1) + " => " + rs.getObject(2))
            .forEach(System.out::println);
    }

    public static void main(String[] args) {
        System.setProperty("oracle.jdbc.fanEnabled", "false");

        System.setProperty("oracle.net.tns_admin",
            System.getProperty("user.dir") + File.separator + "wallet");

        SpringApplication.run(DemoApplication.class, args);
    }
}
