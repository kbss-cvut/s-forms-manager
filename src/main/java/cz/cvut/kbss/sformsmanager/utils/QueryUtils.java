package cz.cvut.kbss.sformsmanager.utils;

import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Files;

public class QueryUtils {

    public static String getQueryFromFile(String queryFile) throws IOException {
        try {
            return new String(Files.readAllBytes(ResourceUtils.getFile(queryFile).toPath()));
        } catch (IOException e) {
            throw new IOException("Query file could not be found!");
        }
    }

}
