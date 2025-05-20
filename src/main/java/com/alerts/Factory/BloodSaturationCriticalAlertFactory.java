package com.alerts.Factory;

import com.alerts.Alert;

import static com.alerts.Week3Logic.AlertType.OXYGEN_SATURATION_LOW;

public class BloodSaturationCriticalAlertFactory extends AlertFactory{

    @Override
    public Alert createAlert(String patientId, String condition, long timestamp) {
        return new Alert(patientId, condition, timestamp, OXYGEN_SATURATION_LOW);
    }
}