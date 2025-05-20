package com.alerts.Week3Logic_StrategyPattern;


import com.alerts.Alert;
import com.alerts.Factory.AlertFactory;
import com.alerts.Factory.BloodPressureCriticalAlertFactory;
import com.data_management.PatientRecord;

import java.util.List;

/**
 * This class implements the alert that is triggered when the blood pressure reaches a critical value and implements
 * the AlertCondition  interface.
 */

public class BloodPressureCriticalAlert implements AlertCondition {

    /**
     * This method checks if the condition to trigger the alert is matched. The input is a list of PatientRecords. Each
     * of these patient records is evaluated. First the value and measurementValue are retrieved. Then the systolic and
     * diastolic blood pressure are checked and an alert is triggered when the critical value is reached.
     *
     * @param patientRecords is a list of patient records.
     *
     * @return an alert if the conditions are met, null otherwise.
     */

    @Override
    public Alert checkCondition(List<PatientRecord> patientRecords) {
        for (PatientRecord record : patientRecords) {
            double value = record.getMeasurementValue();
            String type = record.getRecordType();

            if ("SystolicPressure".equals(type)) {
                if (value > 180) {
                    AlertFactory factory = new BloodPressureCriticalAlertFactory();
                    return factory.createAlert(Integer.toString(record.getPatientId()),
                            "Systolic blood pressure too high",
                            record.getTimestamp());

                } else if (value < 90) {
                    AlertFactory factory = new BloodPressureCriticalAlertFactory();
                    return factory.createAlert(Integer.toString(record.getPatientId()),
                            "Systolic blood pressure too low",
                            record.getTimestamp());
                }
            } else if ("DiastolicPressure".equals(type)) {
                if (value > 120) {
                    AlertFactory factory = new BloodPressureCriticalAlertFactory();
                    return factory.createAlert(Integer.toString(record.getPatientId()),
                            "Diastolic blood pressure too high",
                            record.getTimestamp());
                } else if (value < 60) {
                    AlertFactory factory = new BloodPressureCriticalAlertFactory();
                    return factory.createAlert(Integer.toString(record.getPatientId()),
                            "Diastolic blood pressure too low",
                            record.getTimestamp());
                }
            }
        }
        return null;
    }
}



