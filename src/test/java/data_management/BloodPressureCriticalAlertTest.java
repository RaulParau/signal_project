package data_management;

import com.alerts.Alert;
import com.alerts.BloodPressureCriticalAlert;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class BloodPressureCriticalAlertTest {
    private BloodPressureCriticalAlert alertCondition;

    public void setUp(){
        alertCondition = new BloodPressureCriticalAlert();
    }

    public void testHighSystolicPressure(){
        PatientRecord systolicRecord = new PatientRecord(1, 190, "SystolicPressure", 100L);
        List<PatientRecord> records = List.of(systolicRecord);

        Alert alert = alertCondition.checkCondition(records);

        assertNotNull(alert);
        assertEquals("Systolic blood pressure too high", alert.getCondition());

    }

    public void testHighDiastolicPressure(){
        PatientRecord diastolicRecord = new PatientRecord(1, 121, "DiastolicPressure", 100L);
        List<PatientRecord> records = List.of(diastolicRecord);

        Alert alert = alertCondition.checkCondition(records);

        assertNotNull(alert);
        assertEquals("Diastolic blood pressure too high", alert.getCondition());

    }

    public void testLowDiastolicPressure(){
        PatientRecord diastolicRecord = new PatientRecord(1, 59, "DiastolicPressure", 100L);
        List<PatientRecord> records = List.of(diastolicRecord);

        Alert alert = alertCondition.checkCondition(records);

        assertNotNull(alert);
        assertEquals("Diastolic blood pressure too low", alert.getCondition());

    }

    public void testLowSystolicPressure(){
        PatientRecord systolicRecord = new PatientRecord(1, 89, "SystolicPressure", 100L);
        List<PatientRecord> records = List.of(systolicRecord);

        Alert alert = alertCondition.checkCondition(records);

        assertNotNull(alert);
        assertEquals("Systolic blood pressure too low", alert.getCondition());

    }

    public void testHealthyBloodPressure(){
        PatientRecord systolicRecord = new PatientRecord(1, 100, "SystolicPressure", 100L);
        PatientRecord diastolicRecord = new PatientRecord(1, 100, "DiastolicPressure", 100L);

        List<PatientRecord> records = Arrays.asList(systolicRecord, diastolicRecord);

        Alert alert = alertCondition.checkCondition(records);

        assertNull(alert);

    }
}
