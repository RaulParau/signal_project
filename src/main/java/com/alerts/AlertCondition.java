package com.alerts;

import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.List;

public interface AlertCondition {
    Alert checkCondition(List<PatientRecord> patientRecord);
}
