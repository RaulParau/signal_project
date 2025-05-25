package week4.Factory_Test;

import static org.junit.jupiter.api.Assertions.*;

import com.alerts.Alert;
import com.alerts.Factory.BloodPressureCriticalAlertFactory;
import com.alerts.Week3Logic_StrategyPattern.AlertType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BloodPressureCriticalAlertFactoryTest {

    private BloodPressureCriticalAlertFactory factory;

    @BeforeEach
    public void setup() {
        factory = new BloodPressureCriticalAlertFactory();
    }

    @Test
    public void testCreateAlert() {
        String patientId = "123";
        String condition = "Critical blood pressure detected";
        long timestamp = 123456789L;

        Alert alert = factory.createAlert(patientId, condition, timestamp);

        assertNotNull(alert);
        assertEquals(patientId, alert.getPatientId());
        assertEquals(condition, alert.getCondition());
        assertEquals(timestamp, alert.getTimestamp());
        assertEquals(AlertType.BLOOD_PRESSURE_CRITICAL, alert.getAlertType());
    }
}
