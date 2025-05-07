package com.alerts;

import com.data_management.PatientRecord;

import java.util.List;

public class SelfTriggeredAlert implements AlertCondition{
    @Override
    public Alert checkCondition(List<PatientRecord> records) {
        for (PatientRecord record : records) {
            if (record.getRecordType().equals("Button was pressed")
                    && record.getMeasurementValue() == 1.0) {

                return new Alert(
                        Integer.toString(record.getPatientId()),
                        "Manual Triggered Alert: Nurse/Patient button pressed",
                        record.getTimestamp(),
                        AlertType.MANUAL_TRIGGER
                );
            }
        }
        return null;
    }
}
