package com.alerts.Week3Logic;

import com.alerts.Alert;
import com.alerts.Factory.AlertFactory;
import com.alerts.Factory.ECGAlertFactory;
import com.data_management.PatientRecord;

import java.util.LinkedList;
import java.util.List;

/**
 * This class implements the EGC alert, which is triggered when peaks above a threshold happens with respect to the last
 * 10 readings.
 */
public class EGCAlert implements AlertCondition {

    private final int WINDOW_SIZE = 10;
    private final double THRESHOLD = 1.5; //This is just an approximation of what the threshold could be

    /**
     * This method checks if the condition to trigger the alert is matched. A sliding window approach is used, which takes
     * the last 10 readings into account. If a reading is 1.5 times bigger than the average of the last 10 readings, an
     * EGC alert is triggered.
     *
     * @param patientRecord is a list of patient records
     * @return an EGC alert if the conditions are met, null otherwise
     */
    @Override
    public Alert checkCondition(List<PatientRecord> patientRecord) {
        List<Double> window = new LinkedList<>();

        for(PatientRecord record: patientRecord){
            if(record.getRecordType().equals("ECG")){

                double value = record.getMeasurementValue();

                if(window.size() == WINDOW_SIZE){
                    window.remove(0);
                }

                window.add(value);

                double average = window.stream()
                                .mapToDouble(Double::doubleValue)
                                .average()
                                .orElse(0.0);

                if(average > 0 && value > THRESHOLD * average){
                    AlertFactory factory = new ECGAlertFactory();
                    factory.createAlert(Integer.toString(record.getPatientId()),
                            "Abnormal ECG Data",
                            record.getTimestamp());
                }

            }
        }

        return null;
    }
}
