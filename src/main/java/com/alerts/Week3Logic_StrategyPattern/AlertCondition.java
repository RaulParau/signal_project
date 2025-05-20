package com.alerts.Week3Logic_StrategyPattern;

import com.alerts.Alert;
import com.data_management.PatientRecord;

import java.util.List;

public interface AlertCondition {
    Alert checkCondition(List<PatientRecord> patientRecord);
}
