package week4.Factory_Test;

import static org.junit.jupiter.api.Assertions.*;

import com.alerts.Alert;
import com.alerts.Factory.BloodSaturationCriticalAlertFactory;
import com.alerts.Week3Logic_StrategyPattern.AlertType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BloodSaturationCriticalAlertFactoryTest {

    private BloodSaturationCriticalAlertFactory factory;

    @BeforeEach
    public void setup() {
        factory = new BloodSaturationCriticalAlertFactory();
    }

    @Test
    public void testCreateAlert() {
        String patientId = "456";
        String condition = "Critical oxygen saturation detected";
        long timestamp = 987654321L;

        Alert alert = factory.createAlert(patientId, condition, timestamp);

        assertNotNull(alert);
        assertEquals(patientId, alert.getPatientId());
        assertEquals(condition, alert.getCondition());
        assertEquals(timestamp, alert.getTimestamp());
        assertEquals(AlertType.OXYGEN_SATURATION_LOW, alert.getAlertType());
    }
}
