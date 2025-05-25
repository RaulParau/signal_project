package alerts_week3;

import com.alerts.Alert;
import com.alerts.Week3Logic_StrategyPattern.BloodPressureCriticalAlert;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class BloodPressureCriticalAlertTest {
    private BloodPressureCriticalAlert alertCondition;

    @BeforeEach
    public void setUp(){
        alertCondition = new BloodPressureCriticalAlert();
    }

    @Test
    public void testHighSystolicPressure(){
        PatientRecord systolicRecord = new PatientRecord(1, 190, "SystolicPressure", 100L);
        List<PatientRecord> records = List.of(systolicRecord);

        Alert alert = alertCondition.checkCondition(records);

        Assertions.assertNotNull(alert);
        Assertions.assertEquals("Systolic blood pressure too high", alert.getCondition());

    }

    @Test
    public void testHighDiastolicPressure(){
        PatientRecord diastolicRecord = new PatientRecord(1, 121, "DiastolicPressure", 100L);
        List<PatientRecord> records = List.of(diastolicRecord);

        Alert alert = alertCondition.checkCondition(records);

        Assertions.assertNotNull(alert);
        Assertions.assertEquals("Diastolic blood pressure too high", alert.getCondition());

    }

    @Test
    public void testLowDiastolicPressure(){
        PatientRecord diastolicRecord = new PatientRecord(1, 59, "DiastolicPressure", 100L);
        List<PatientRecord> records = List.of(diastolicRecord);

        Alert alert = alertCondition.checkCondition(records);

        Assertions.assertNotNull(alert);
        Assertions.assertEquals("Diastolic blood pressure too low", alert.getCondition());

    }

    @Test
    public void testLowSystolicPressure(){
        PatientRecord systolicRecord = new PatientRecord(1, 89, "SystolicPressure", 100L);
        List<PatientRecord> records = List.of(systolicRecord);

        Alert alert = alertCondition.checkCondition(records);

        Assertions.assertNotNull(alert);
        Assertions.assertEquals("Systolic blood pressure too low", alert.getCondition());

    }

    @Test
    public void testHealthyBloodPressure(){
        PatientRecord systolicRecord = new PatientRecord(1, 100, "SystolicPressure", 100L);
        PatientRecord diastolicRecord = new PatientRecord(1, 100, "DiastolicPressure", 100L);

        List<PatientRecord> records = Arrays.asList(systolicRecord, diastolicRecord);

        Alert alert = alertCondition.checkCondition(records);

        Assertions.assertNull(alert);

    }
}
