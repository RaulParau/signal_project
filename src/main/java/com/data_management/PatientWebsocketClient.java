package com.data_management;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class PatientWebsocketClient extends WebSocketClient {

    private final DataStorage dataStorage;

    public PatientWebsocketClient(URI serverUri, DataStorage dataStorage) {
        super(serverUri);
        this.dataStorage = dataStorage;
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        System.out.println("Connected to websocket");
    }

    @Override
    public void onMessage(String s) {
        try{
            // The message will be split into parts so it can be read.
            // The format of the message will be: patientId, measurementValue, recordType, timestamp
            String[] parts = s.split(",");

            // Basic validation that the message is long enough
            if(parts.length == 4){

                //parse the parts
                int patientId = Integer.parseInt(parts[0].trim());
                long timestamp = Long.parseLong(parts[1].trim());
                String recordType = parts[2].trim();
                double measurementValue = Double.parseDouble(parts[3].trim());

                // add the parsed data to the data storage in the correct order
                dataStorage.addPatientData(patientId, measurementValue, recordType, timestamp);

            }
        } catch (Exception e){
            // Catch parsing exceptions or runtime errors
            System.out.println("Error parsing message: " + s);
            e.printStackTrace();
        }
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        System.out.println("Disconnected from websocket");
    }

    @Override
    public void onError(Exception e) {
        System.out.println("Error connecting to websocket");
        e.printStackTrace(); //print details of the error for debugging.
    }
}
