package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentHashMap;
// THE FILE NAME WAS WRONG

/**
 * The class implements the OutputStrategy interface and writes output data into files
 * It can store data of patients in a file-based storage system and can create new directories
 * if necessary. It can also add data to a file with a specific label.
 */
public class FileOutputStrategy implements OutputStrategy {

    //changed BaseDirectory to baseDirectory
    /**
     * This is the Base Directory where the files are stored.
     */
    private String baseDirectory;

    // file_map to FILE_MAP as it is final
    /**
     * The map maps labels to file paths, ensuring that each data label has a corresponding output file.
     * If the corresponding output label is not available the file is created
     */
    public final ConcurrentHashMap<String, String> FILE_MAP = new ConcurrentHashMap<>();

    /**
     * Constructs a FileOuptutStrategy object with a base directory
     * @param baseDirectory Directory where the output file will be saved
     */
    public FileOutputStrategy(String baseDirectory) {

        this.baseDirectory = baseDirectory;
    }

    /**
     * Method to output data to a file with the given label.
     * If the file does not exist yet, the file is created.
     * @param patientId The patients patient ID
     * @param timestamp Moment in time when the data was generated
     * @param label A label of the data, describing the type of data
     * @param data The generated data
     */
    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        try {
            // Create the directory
            Files.createDirectories(Paths.get(baseDirectory));
        } catch (IOException e) {
            System.err.println("Error creating base directory: " + e.getMessage());
            return;
        }
        // Set the FilePath variable
        // FilePath was changed to filePath
        String filePath = FILE_MAP.computeIfAbsent(label, k -> Paths.get(baseDirectory, label + ".txt").toString());

        // Write the data to the file
        try (PrintWriter out = new PrintWriter(
                Files.newBufferedWriter(Paths.get(filePath), StandardOpenOption.CREATE, StandardOpenOption.APPEND))) {
            out.printf("Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n", patientId, timestamp, label, data);
        } catch (Exception e) {
            System.err.println("Error writing to file " + filePath + ": " + e.getMessage());
        }
    }
}