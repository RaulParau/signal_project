package com.alerts.Factory;

import com.alerts.Alert;

import static com.alerts.Week3Logic_StrategyPattern.AlertType.BLOOD_PRESSURE_CRITICAL;

public class BloodPressureCriticalAlertFactory extends AlertFactory{

    @Override
    public Alert createAlert(String patientId, String condition, long timestamp) {
        return new Alert(patientId, condition, timestamp, BLOOD_PRESSURE_CRITICAL);
    }
}
