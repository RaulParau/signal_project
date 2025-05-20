package com.alerts;

import com.alerts.Alert_Types.AlertInterface;
import com.alerts.Week3Logic_StrategyPattern.AlertType;

// Represents an alert
public class Alert implements AlertInterface {
    private String patientId;
    private String condition;
    private long timestamp;
    private AlertType alertType;

    public Alert(String patientId, String condition, long timestamp, AlertType alertType) {
        this.patientId = patientId;
        this.condition = condition;
        this.timestamp = timestamp;
        this.alertType = alertType;
    }

    public AlertType getAlertType() {return alertType; }

    public String getPatientId() {
        return patientId;
    }

    public String getCondition() {
        return condition;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
