package com.alerts.Decorator;

import com.alerts.Alert_Types.AlertInterface;

public class AlertDecorator implements AlertInterface {
    private AlertInterface decoratedAlert;

    public AlertDecorator(AlertInterface alert){
        this.decoratedAlert = alert;
    }

    @Override
    public String getPatientId() {
        return decoratedAlert.getPatientId();
    }

    @Override
    public String getCondition() {
        return decoratedAlert.getCondition();
    }

    @Override
    public long getTimestamp() {
        return decoratedAlert.getTimestamp();
    }
}
