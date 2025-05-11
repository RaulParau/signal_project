package data_management;

import com.alerts.Alert;
import com.alerts.BloodPressureCriticalAlert;
import com.data_management.PatientRecord;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class BloodSaturationCriticalAlertTest {

    private BloodPressureCriticalAlert alertCondition;

    public void setUp(){
        alertCondition = new BloodPressureCriticalAlert();
    }

    public void testCriticalBloodSaturation(){
        PatientRecord saturationRecord = new PatientRecord(1, 90, "Saturation", 100L);
        List<PatientRecord> records = List.of(saturationRecord);

        Alert alert = alertCondition.checkCondition(records);

        assertNotNull(alert);
        assertEquals("Saturation too low", alert.getCondition());

    }

    public void testHealthyBloodSaturation(){
        PatientRecord saturationRecord = new PatientRecord(1, 100, "Saturation", 100L);

        List<PatientRecord> records = List.of(saturationRecord);

        Alert alert = alertCondition.checkCondition(records);

        assertNull(alert);

    }
}
