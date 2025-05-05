package com.alerts;

import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.ArrayList;
import java.util.List;

public class BloodPressureCriticalAlert implements AlertCondition {
    @Override
    public Alert checkCondition(List<PatientRecord> patientRecords) {
        for (PatientRecord record : patientRecords) {
            double value = record.getMeasurementValue();
            String type = record.getRecordType();

            if ("SystolicPressure".equals(type)) {
                if (value > 180) {
                    return new Alert(
                            Integer.toString(record.getPatientId()),
                            "Systolic blood pressure too high",
                            record.getTimestamp(),
                            AlertType.BLOOD_PRESSURE_CRITICAL
                    );
                } else if (value < 90) {
                    return new Alert(
                            Integer.toString(record.getPatientId()),
                            "Systolic blood pressure too low",
                            record.getTimestamp(),
                            AlertType.BLOOD_PRESSURE_CRITICAL
                    );
                }
            } else if ("DiastolicPressure".equals(type)) {
                if (value > 120) {
                    return new Alert(
                            Integer.toString(record.getPatientId()),
                            "Diastolic blood pressure too high",
                            record.getTimestamp(),
                            AlertType.BLOOD_PRESSURE_CRITICAL
                    );
                } else if (value < 60) {
                    return new Alert(
                            Integer.toString(record.getPatientId()),
                            "Diastolic blood pressure too low",
                            record.getTimestamp(),
                            AlertType.BLOOD_PRESSURE_CRITICAL
                    );
                }
            }
        }
        return null;
    }
}



