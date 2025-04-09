package com.cardio_generator.generators;

import java.util.Random;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * This class implements the PatientDataGenerator interface.
 * The class represents a blood saturation data generator, generating random values for the blood saturation of a
 * number of patients while ensuring only small fluctuations. The generated blood saturations are then outputted.
 */
public class BloodSaturationDataGenerator implements PatientDataGenerator {

    /**
     * Random number generator, later used to create random blood saturation levels
     */
    private static final Random random = new Random();

    /**
     * Array storing the blood saturation levels. The index corresponds to the patientID while the value at each index
     * is the blood saturation level.
     */
    private int[] lastSaturationValues;

    /**
     * Creates a BloodSaturationDataGenerator object with a given number of patients.
     * The array of blood saturation objects is populated with values in the range of 95 - 100, giving each patient
     * a random level of blood saturation in a reasonable interval.
     *
     * @param patientCount Number of generated blood saturation levels.
     */
    public BloodSaturationDataGenerator(int patientCount) {
        lastSaturationValues = new int[patientCount + 1];

        // Initialize with baseline saturation values for each patient
        for (int i = 1; i <= patientCount; i++) {
            lastSaturationValues[i] = 95 + random.nextInt(6); // Initializes with a value between 95 and 100
        }
    }

    /**
     * This method generates a new blood saturation level for a given patient and ouptuts the data using a given
     * Output strategy.
     * The method takes the corresponding value from the last saturation values array and adds a variation between
     * -1 and 1 to that value while ensuring a value above 90 and 100 and updates the value in the array to the new
     * value.
     * Then it outputs the created value, with the corresponding patientID, the timestamp, the label "Blood Saturation"
     * and the value that has been generated, using the given output strategy.
     *
     * @param patientId The patient ID of the patient the data is generated for
     * @param outputStrategy Specifies the strategy of outputting the data
     */
    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            // Simulate blood saturation values
            int variation = random.nextInt(3) - 1; // -1, 0, or 1 to simulate small fluctuations
            int newSaturationValue = lastSaturationValues[patientId] + variation;

            // Ensure the saturation stays within a realistic and healthy range
            newSaturationValue = Math.min(Math.max(newSaturationValue, 90), 100);
            lastSaturationValues[patientId] = newSaturationValue;
            outputStrategy.output(patientId, System.currentTimeMillis(), "Saturation",
                    Double.toString(newSaturationValue) + "%");
        } catch (Exception e) {
            System.err.println("An error occurred while generating blood saturation data for patient " + patientId);
            e.printStackTrace(); // This will print the stack trace to help identify where the error occurred.
        }
    }
}
