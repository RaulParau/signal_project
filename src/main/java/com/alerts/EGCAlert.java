package com.alerts;

import com.data_management.PatientRecord;

import java.util.LinkedList;
import java.util.List;

public class EGCAlert implements AlertCondition{

    private final int WINDOW_SIZE = 10;
    private final double THRESHOLD = 1.5;

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
                    return new Alert(
                            Integer.toString(record.getPatientId()),
                            "Abnormal ECG Data",
                            record.getTimestamp(),
                            AlertType.ECG_ABNORMALITY
                    );
                }

            }
        }

        return null;
    }
}
