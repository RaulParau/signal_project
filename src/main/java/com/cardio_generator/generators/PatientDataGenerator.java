package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;
/**
 * This Interface provides a contract to implement the method "generate"
 *
 * @author RaulParau
 */
public interface PatientDataGenerator {
    /**
     * Generates data for a specific patient and outputs the data in a specified way
     *
     * @param patientId The patient ID of the patient the data is generated for
     * @param outputStrategy Specifies the strategy of outputting the data
     */
    void generate(int patientId, OutputStrategy outputStrategy);
}
