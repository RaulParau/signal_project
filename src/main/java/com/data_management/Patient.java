package com.data_management;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a patient and manages their medical records.
 * This class stores patient-specific data, allowing for the addition and
 * retrieval
 * of medical records based on specified criteria.
 */
public class Patient {
    private int patientId;
    private final List<PatientRecord> patientRecords;

    /**
     * Constructs a new Patient with a specified ID.
     * Initializes an empty list of patient records.
     *
     * @param patientId the unique identifier for the patient
     */
    public Patient(int patientId) {
        this.patientId = patientId;
        //A synchronized list is used to allow for real time data collection
        this.patientRecords = Collections.synchronizedList(new ArrayList<>());
    }

    /**
     * Adds a new record to this patient's list of medical records.
     * The record is created with the specified measurement value, record type, and
     * timestamp.
     *
     * @param measurementValue the measurement value to store in the record
     * @param recordType       the type of record, e.g., "HeartRate",
     *                         "BloodPressure"
     * @param timestamp        the time at which the measurement was taken, in
     *                         milliseconds since UNIX epoch
     */
    public void addRecord(double measurementValue, String recordType, long timestamp) {
        PatientRecord record = new PatientRecord(this.patientId, measurementValue, recordType, timestamp);
        this.patientRecords.add(record);
    }

    /**
     * Retrieves a list of PatientRecord objects for this patient that fall within a
     * specified time range.
     * The method filters records based on the start and end times provided.
     *
     * @param startTime the start of the time range, in milliseconds since UNIX
     *                  epoch
     * @param endTime   the end of the time range, in milliseconds since UNIX epoch
     * @return a list of PatientRecord objects that fall within the specified time
     *         range
     */
    //I had to modify this method in order to create my jar file as it caused compilation errors
    public List<PatientRecord> getRecords(long startTime, long endTime) {
        List<PatientRecord> records = new ArrayList<>();
        synchronized (patientRecords) {  // this block is needed when iterating over a synchronized list
            for (PatientRecord record : patientRecords) {
                if (record.getTimestamp() >= startTime && record.getTimestamp() <= endTime) {
                    records.add(record);
                }
            }
        }
        return records;
    }

    public int getPatientId(){
        return patientId;
    }
}
