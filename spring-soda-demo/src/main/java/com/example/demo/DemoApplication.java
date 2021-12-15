package com.example.demo;

import oracle.soda.OracleCollection;
import oracle.soda.OracleCursor;
import oracle.soda.OracleDatabase;
import oracle.soda.OracleDocument;
import oracle.soda.rdbms.OracleRDBMSClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;
import java.io.File;
import java.sql.Connection;
import java.util.List;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
    @Qualifier("dataSource")
    @Autowired
    private DataSource dataSource;

    @Override
    public void run(String... args) throws Exception {
        Connection connection = dataSource.getConnection();

        System.out.println("################");
        System.out.println(connection.getMetaData().getDatabaseProductVersion() + " " +
            connection.getMetaData().getURL());
        System.out.println("################");

        OracleRDBMSClient cl = new OracleRDBMSClient();
        OracleDatabase db = cl.getDatabase(connection);

        db.admin().dropCollections(true);
        OracleCollection col = db.admin().createCollection("TODOS_SODA");

        for (String json : List.of(
            "{\"name\": \"Code\", \"soda\": true}",
            "{\"name\": \"Slides\", \"status\": \"WORK_IN_PROGRESS\"}")) {
            OracleDocument doc = db.createDocumentFromString(json);
            String k = col.insertAndGet(doc).getKey();
            System.out.println("Inserted: " + col.find().key(k).getOne().getContentAsString());
        }

        OracleDocument f = db.createDocumentFromString("{\"name\" : { \"$startsWith\" : \"C\" }}");
        OracleCursor c = col.find().filter(f).getCursor();

        while (c.hasNext()) {
            OracleDocument resultDoc = c.next();

            System.out.println("Key:     " + resultDoc.getKey());
            System.out.println("Content: " + resultDoc.getContentAsString());
        }

    }

    public static void main(String[] args) {
        System.setProperty("oracle.jdbc.fanEnabled", "false");

        System.setProperty("oracle.net.tns_admin",
            System.getProperty("user.dir") + File.separator + "wallet");

        SpringApplication.run(DemoApplication.class, args);
    }
}
