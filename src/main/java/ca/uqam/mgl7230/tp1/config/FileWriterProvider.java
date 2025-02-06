package ca.uqam.mgl7230.tp1.config;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

public class FileWriterProvider {

    private static final Logger logger = Logger.getLogger(FileWriterProvider.class.getName());
private static final String ISSUE = "Issue when creating file: " ;

    public FileWriter createFile(String fileName) {
        try {
            return new FileWriter(fileName);
        } catch (IOException e) {
            logger.warning(ISSUE + e);
            return null;
        }
    }
}
