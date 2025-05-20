package com.alerts.Week3Logic;

import com.alerts.Alert;
import com.data_management.PatientRecord;

import java.util.List;

/**
 * Implements the alert condition that an alert has been triggered by hand.
 */
public class SelfTriggeredAlert implements AlertCondition {

    /**
     * This method implements the logic for a self triggered alert. It filters for records that match the alert condition
     * "Button was pressed" and triggers an alert if the measurement value equals 1.0
     *
     * @param records is a list of all the patient records
     * @return a self triggered alert if the conditions are met, null otherwise
     */
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
