package utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.nio.charset.StandardCharsets;

public class FileUtils {
  private static final Logger logger = LogManager.getLogger(FileUtils.class);

  public static void createDirectory(Path path) {
    try {
      Files.createDirectories(path);
    } catch (IOException e) {
      logger.info("Error creating directory: " + e.getMessage());
    }
  }

  public static void writeToFile(String fileName, String content) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, StandardCharsets.UTF_8))) {
      writer.write(content);
    } catch (IOException e) {
      logger.error("Error writing to file: " + e.getMessage(), e);
    }
  }

  public static void writeToFileAppend(String fileName, String content) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, StandardCharsets.UTF_8, true))) {
      writer.write(content);
      writer.newLine();
    } catch (IOException e) {
      logger.error("Error writing to file: " + e.getMessage(), e);
    }
  }
}
