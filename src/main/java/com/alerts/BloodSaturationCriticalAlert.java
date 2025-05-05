package com.alerts;

import com.data_management.PatientRecord;

import java.util.List;

public class BloodSaturationCriticalAlert implements AlertCondition{
    @Override
    public Alert checkCondition(List<PatientRecord> patientRecord) {
        for (PatientRecord record : patientRecord) {
            double value = record.getMeasurementValue();
            String type = record.getRecordType();

            if ("Saturation".equals(type)) {
                if (value < 92) {
                    return new Alert(
                            Integer.toString(record.getPatientId()),
                            "Saturation too low",
                            record.getTimestamp(),
                            AlertType.OXYGEN_SATURATION_LOW
                    );
                }
            }
        }
        return null;
    }
}
