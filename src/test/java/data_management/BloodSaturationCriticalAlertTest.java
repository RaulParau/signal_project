package data_management;

import com.alerts.Alert;
import com.alerts.BloodPressureCriticalAlert;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;


public class BloodSaturationCriticalAlertTest {

    private BloodPressureCriticalAlert alertCondition;

    @BeforeEach
    public void setUp(){
        alertCondition = new BloodPressureCriticalAlert();
    }

   @Test
    public void testCriticalBloodSaturation(){
        PatientRecord saturationRecord = new PatientRecord(1, 90, "Saturation", 100L);
        List<PatientRecord> records = List.of(saturationRecord);

        Alert alert = alertCondition.checkCondition(records);

        Assertions.assertNotNull(alert);
        Assertions.assertEquals("Saturation too low", alert.getCondition());

    }

    @Test
    public void testHealthyBloodSaturation(){
        PatientRecord saturationRecord = new PatientRecord(1, 100, "Saturation", 100L);

        List<PatientRecord> records = List.of(saturationRecord);

        Alert alert = alertCondition.checkCondition(records);

        Assertions.assertNull(alert);

    }
}
