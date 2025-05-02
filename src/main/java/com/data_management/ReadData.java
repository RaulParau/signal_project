package com.data_management;
import java.io.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;

public class ReadData implements DataReader{

    private final ConcurrentHashMap<String, String> fileMap;

    public ReadData(ConcurrentHashMap<String, String> fileMap){
        this.fileMap = fileMap;
    }

    /**
     *
     * This method implements  the DataReader interface. It reads the data from a specified csv file
     * that is found using the fileMap, which maps data labels to csv file and is passed to the object by the
     * FileOutputStrategy object when being created. It reads the data from the file, parses the data and then passes
     * it to a DataStorage object.
     *
     * @param dataStorage Instance of the DataStorage object, represents the storage where data will be stored
     *
     * @throws IOException if there is an error reading the file.
     */
    @Override
    public void readData(DataStorage dataStorage) throws IOException {
        for(String label: fileMap.keySet()){
            String filePath = fileMap.get(label);

            // The file is opened with the specified Path and read by the buffered reader.
            try(BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))){
                String line;
                while((line = reader.readLine()) != null){
                    String[] parts = line.split(",");
                    if(parts.length == 4){

                        int patientId = Integer.parseInt(parts[0].split(":")[1].trim());
                        long timestamp = Long.parseLong(parts[1].split(":")[1].trim());
                        String dataLabel = parts[2].split(":")[1].trim();
                        String data = parts[3].split(":")[1].trim();

                        double measurementValue = Double.parseDouble(data);

                        dataStorage.addPatientData(patientId, measurementValue, dataLabel, timestamp);
                    }
                }
            } catch(IOException e){
                System.err.println("ERROR file " + filePath + ": " + e.getMessage());
            }
        }
    }
}
