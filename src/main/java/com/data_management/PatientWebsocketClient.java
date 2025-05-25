package com.data_management;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class PatientWebsocketClient extends WebSocketClient {

    private DataStorage dataStorage;

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
            String[] parts = s.split(",");
            int patientId = Integer.parseInt(parts[0].trim());
            long timestamp = Long.parseLong(parts[1].trim());
            String recordType = parts[2].trim();
            double measurementValue =Double.parseDouble(parts[3].trim());

            dataStorage.addPatientData(patientId, measurementValue, recordType, timestamp);
        } catch (Exception e){
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
        e.printStackTrace();
    }
}
