package com.cardio_generator.outputs;

/**
 * Interface for defining the output strategies for generated data
 * Implementations of the output method specify the strategy of the data output (e.g. to the console, file, websocket etc)
 */
public interface OutputStrategy {
    /**
     * Outputs the generated data for a patient
     *
     * @param patientId The patients patient ID
     * @param timestamp Moment when the data point was generated
     * @param label L
     * @param data The generated data
     */
    void output(int patientId, long timestamp, String label, String data);
}
