package alerts;

import com.alerts.Alert;
import com.alerts.Week3Logic.AlertCondition;
import com.alerts.AlertGenerator;
import com.alerts.Week3Logic.AlertType;
import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AlertGeneratorTest {

    private DataStorage mockDataStorage;
    private AlertGenerator alertGenerator;
    private Patient testPatient;

    @BeforeEach
    public void setUp(){
        mockDataStorage = Mockito.mock(DataStorage.class);
        alertGenerator = new AlertGenerator(mockDataStorage);
        testPatient = new Patient(1);
    }

    @Test
    public void testEvaluateDataWithOneTriggeredAlert() {
        List<PatientRecord> mockRecords = Arrays.asList(
                new PatientRecord(1, 99, "Saturation", 100),
                new PatientRecord(1, 120, "SystolicPressure", 200)
        );

        Mockito.when(mockDataStorage.getRecords(Mockito.eq(1), Mockito.anyLong(), Mockito.anyLong()))
                .thenReturn(mockRecords);

        // Add mock AlertCondition
        AlertCondition mockCondition = Mockito.mock(AlertCondition.class);
        Alert mockAlert = new Alert("1", "Saturation too low", 12345, AlertType.BLOOD_PRESSURE_CRITICAL);
        Mockito.when(mockCondition.checkCondition(mockRecords)).thenReturn(mockAlert);
        alertGenerator.registerAlertCondition(mockCondition);

        // Call method under test
        Patient patient = new Patient(1); // Make sure this matches the patientId used above
        alertGenerator.evaluateData(patient);

        // Now assert the triggered alerts
        List<Alert> alerts = alertGenerator.getTriggeredAlerts();

        assertEquals(1, alerts.size(), "Exactly one alert should have been triggered");

        Alert alert = alerts.get(0);
        assertEquals("1", alert.getPatientId());
        assertEquals("Saturation too low", alert.getCondition());
        assertEquals(AlertType.BLOOD_PRESSURE_CRITICAL, alert.getAlertType());
    }


    @Test
    public void testEvaluateDataWithNoTriggeredAlert() {
        List<PatientRecord> mockRecords = Collections.emptyList();

        Mockito.when(mockDataStorage.getRecords(Mockito.eq(1), Mockito.anyLong(), Mockito.anyLong()))
                .thenReturn(mockRecords);

        AlertCondition mockCondition = Mockito.mock(AlertCondition.class);
        Mockito.when(mockCondition.checkCondition(mockRecords)).thenReturn(null);
        alertGenerator.registerAlertCondition(mockCondition);

        alertGenerator.evaluateData(testPatient);

        Mockito.verify(mockCondition).checkCondition(mockRecords);
        assertTrue(alertGenerator.getTriggeredAlerts().isEmpty());
    }
}
