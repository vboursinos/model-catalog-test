package ai.turintech.modelcatalog.migrationfilescreator.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    try (BufferedWriter writer =
        new BufferedWriter(new FileWriter(fileName, StandardCharsets.UTF_8))) {
      writer.write(content);
    } catch (IOException e) {
      logger.error("Error writing to file: " + e.getMessage(), e);
    }
  }

  public static void writeToFileAppend(String fileName, String content) {
    try (BufferedWriter writer =
        new BufferedWriter(new FileWriter(fileName, StandardCharsets.UTF_8, true))) {
      writer.write(content);
      writer.newLine();
    } catch (IOException e) {
      logger.error("Error writing to file: " + e.getMessage(), e);
    }
  }

  public static String countFiles(String directoryPath) {
    File directory = new File(directoryPath);
    if (!directory.exists() || !directory.isDirectory()) {
      logger.error("The specified path is not a directory or does not exist.");
      return "-1";
    }

    File[] files = directory.listFiles();
    if (files == null) {
      logger.error("Error occurred while listing files in the directory.");
      return "-1";
    }

    int fileCount = 0;
    for (File file : files) {
      if (file.isFile()) {
        fileCount++;
      }
    }
    String formattedFileCount = String.format("%04d", fileCount + 1);
    return formattedFileCount;
  }

  public static void deleteFile(String filePath) {
    File file = new File(filePath);
    if (file.delete()) {
      logger.info("File deleted successfully");
    } else {
      logger.error("Failed to delete the file");
    }
  }
}
