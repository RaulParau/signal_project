package com.alerts.Week3Logic;

import com.alerts.Alert;
import com.data_management.PatientRecord;

import java.util.List;

public interface AlertCondition {
    Alert checkCondition(List<PatientRecord> patientRecord);
}
