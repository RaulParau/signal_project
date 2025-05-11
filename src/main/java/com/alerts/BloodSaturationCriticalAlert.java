package com.alerts;

import com.data_management.PatientRecord;

import java.util.List;
/**
 * This class implements AlertConddition and checks if the condition for an alert regarding a critical Blood Saturation
 * is met.
 */
public class BloodSaturationCriticalAlert implements AlertCondition{

    /**
     * This method checks if the condition for a low blood pressure alert is met. If the blood saturation drops under a
     * threshold of 92, the method returns a blood saturation critical alert.
     *
     * @param patientRecord is a list of patient records
     *
     * @return a blood pressure critical alert if the condition is met, null otherwise.
     */
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
