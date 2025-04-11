package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

/**
 * This class implements the OutputStrategy interface and is responsible for sending ouptut data to clients
 *  over a TCP connection. It does this by listening to a specified port for client connections and sends data
 *  back to that client
 */
public class TcpOutputStrategy implements OutputStrategy {

    /**
     * Server socket that listens for client connection.
     */
    private ServerSocket serverSocket;

    /**
     * Server socket of the client.
     */
    private Socket clientSocket;

    /**
     * Writer that is used to send data to the client.
     */
    private PrintWriter out;

    /**
     * Creates a TcpOutputStrategy object that starts the TCP server on a specified port
     *
     * @param port port on that the TCP server is started
     */
    public TcpOutputStrategy(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("TCP Server started on port " + port);

            // Accept clients in a new thread to not block the main thread
            Executors.newSingleThreadExecutor().submit(() -> {
                try {
                    clientSocket = serverSocket.accept();
                    out = new PrintWriter(clientSocket.getOutputStream(), true);
                    System.out.println("Client connected: " + clientSocket.getInetAddress());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends data to the patient that is connected to the server in a comma seperated form.
     * The message that is sent back contains patientId, the timestamp, the label and the data.
     *
     * @param patientId The patients patient ID
     * @param timestamp Moment in time when the data was generated
     * @param label A label of the data, describing the type of data
     * @param data The generated data
     */
    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        if (out != null) {
            String message = String.format("%d,%d,%s,%s", patientId, timestamp, label, data);
            out.println(message);
        }
    }
}
