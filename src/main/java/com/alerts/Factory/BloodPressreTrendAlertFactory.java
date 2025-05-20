package com.alerts.Factory;

import com.alerts.Alert;

import static com.alerts.Week3Logic.AlertType.BLOOD_PRESSURE_TREND;

public class BloodPressreTrendAlertFactory extends AlertFactory{
    @Override
    public Alert createAlert(String patientId, String condition, long timestamp) {
        return new Alert(patientId, condition, timestamp, BLOOD_PRESSURE_TREND);
    }
}
