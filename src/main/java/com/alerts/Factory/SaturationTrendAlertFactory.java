package com.alerts.Factory;

import com.alerts.Alert;

import static com.alerts.Week3Logic_StrategyPattern.AlertType.OXYGEN_SATURATION_RAPID_DROP;

public class SaturationTrendAlertFactory extends AlertFactory{
    @Override
    public Alert createAlert(String patientId, String condition, long timestamp) {
        return new Alert(patientId, condition, timestamp, OXYGEN_SATURATION_RAPID_DROP);
    }
}
