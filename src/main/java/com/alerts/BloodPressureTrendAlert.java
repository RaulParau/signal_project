package com.alerts;

import com.data_management.PatientRecord;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BloodPressureTrendAlert implements AlertCondition{
    private static final int WINDOW_SIZE = 3;
    private static final double THRESHOLD = 10;
    @Override
    public Alert checkCondition(List<PatientRecord> patientRecord) {
        List<PatientRecord> systolic = patientRecord.stream()
                .filter(r -> r.getRecordType().equals("SystolicPressure"))
                .sorted(Comparator.comparing(PatientRecord::getTimestamp))
                .collect(Collectors.toList());

        List<PatientRecord> diastolic = patientRecord.stream()
                .filter(r -> r.getRecordType().equals("SystolicPressure"))
                .sorted(Comparator.comparing(PatientRecord::getTimestamp))
                .collect(Collectors.toList());

        if(checkTrend(systolic, true)){
            Alert alert = new Alert(Integer.toString(patientRecord.get(0).getPatientId()),
                    "Systolic blood pressure trend increases too quick",
                    patientRecord.get(systolic.size()-1).getTimestamp(),
                    AlertType.BLOOD_PRESSURE_TREND);
        }
        else if(checkTrend(systolic, false)){
            Alert alert = new Alert(Integer.toString(patientRecord.get(0).getPatientId()),
                    "Systolic blood pressure trend decreases too quick",
                    patientRecord.get(systolic.size()-1).getTimestamp(),
                    AlertType.BLOOD_PRESSURE_TREND);
        }

        if(checkTrend(diastolic, true)){
            Alert alert = new Alert(Integer.toString(patientRecord.get(0).getPatientId()),
                    "Diastolic blood pressure trend increases too quick",
                    patientRecord.get(systolic.size()-1).getTimestamp(),
                    AlertType.BLOOD_PRESSURE_TREND);
        }
        else if(checkTrend(diastolic, false)){
            Alert alert = new Alert(Integer.toString(patientRecord.get(0).getPatientId()),
                    "Diastolic blood pressure trend decreases too quick",
                    patientRecord.get(systolic.size()-1).getTimestamp(),
                    AlertType.BLOOD_PRESSURE_TREND);
        }

        return null;
    }

    public boolean checkTrend(List<PatientRecord> patientRecord, boolean increasing){
        for(int i = 0; i < patientRecord.size() - WINDOW_SIZE; i++){
            double a = patientRecord.get(i).getMeasurementValue();
            double b = patientRecord.get(i + 1).getMeasurementValue();
            double c = patientRecord.get(i + 2).getMeasurementValue();

            if(increasing){
                if(b - a > THRESHOLD && c - b > THRESHOLD) return  true;
            } else {
                if(a - b > THRESHOLD && b - c > THRESHOLD) return true;
            }
        }
        return false;
    }
}
