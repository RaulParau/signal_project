package com.alerts.Factory;

import com.alerts.Alert;

import static com.alerts.Week3Logic.AlertType.ECG_ABNORMALITY;

public class ECGAlertFactory extends AlertFactory{
    @Override
    public Alert createAlert(String patientId, String condition, long timestamp) {
        return new Alert(patientId, condition, timestamp, ECG_ABNORMALITY);
    }
}
