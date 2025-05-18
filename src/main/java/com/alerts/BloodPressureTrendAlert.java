package com.alerts;

import com.data_management.PatientRecord;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class implements AlertConddition and checks if the condition for an alert regarding a critical Blood presure
 * trend is met.
 */
public class BloodPressureTrendAlert implements AlertCondition{

    /** This determines the window size */
    private static final int WINDOW_SIZE = 3;

    /** this is the threshold for triggering the alert*/
    private static final double THRESHOLD = 10;

    /**
     * This method checks if the conditions to trigger a blood pressure trend critical alert are met. First of all the
     * list of patient records is filtered for records that have data on Systolic or Diastolic blood pressure and the
     * records are appended to a list containing only reports on that data. The helper Method checkTrend is used to
     * determine if the conditions are met.
      *
     * @param patientRecord is a list of patient records.
     * @return an alert if the conditions are met, null otherwise.
     */
    @Override
    public Alert checkCondition(List<PatientRecord> patientRecord) {
        List<PatientRecord> systolic = patientRecord.stream()
                .filter(r -> r.getRecordType().equals("SystolicPressure"))
                .sorted(Comparator.comparing(PatientRecord::getTimestamp))
                .collect(Collectors.toList());

        List<PatientRecord> diastolic = patientRecord.stream()
                .filter(r -> r.getRecordType().equals("DiastolicPressure"))
                .sorted(Comparator.comparing(PatientRecord::getTimestamp))
                .collect(Collectors.toList());

        if(checkTrend(systolic, true)){
            return new Alert(Integer.toString(patientRecord.get(0).getPatientId()),
                    "Systolic blood pressure trend increases too quick",
                    patientRecord.get(systolic.size()-1).getTimestamp(),
                    AlertType.BLOOD_PRESSURE_TREND);
        }
        else if(checkTrend(systolic, false)){
            return new Alert(Integer.toString(patientRecord.get(0).getPatientId()),
                    "Systolic blood pressure trend decreases too quick",
                    patientRecord.get(systolic.size()-1).getTimestamp(),
                    AlertType.BLOOD_PRESSURE_TREND);
        }

        if(checkTrend(diastolic, true)){
            return new Alert(Integer.toString(patientRecord.get(0).getPatientId()),
                    "Diastolic blood pressure trend increases too quick",
                    patientRecord.get(diastolic.size()-1).getTimestamp(),
                    AlertType.BLOOD_PRESSURE_TREND);
        }
        else if(checkTrend(diastolic, false)){
            return new Alert(Integer.toString(patientRecord.get(0).getPatientId()),
                    "Diastolic blood pressure trend decreases too quick",
                    patientRecord.get(diastolic.size()-1).getTimestamp(),
                    AlertType.BLOOD_PRESSURE_TREND);
        }

        return null;
    }

    /**
     * Helper method to check the trend. The method checks the trend for 3 consecutive readings using a sliding window
     * approach. Depending on the value of "increasing" it tests for a decreasing or increasing trend and returns true
     * or false accordingly.
     *
      * @param patientRecord is a list of filtered patient records.
     * @param increasing is a boolean value determining if the method checks for increasing or decreasing trend.
     *
     * @return true if the condition is met, false otherwise.
     */
    public boolean checkTrend(List<PatientRecord> patientRecord, boolean increasing){
        if(patientRecord.size() < WINDOW_SIZE) return false;
        for(int i = 0; i < patientRecord.size() - WINDOW_SIZE + 1; i++){
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
