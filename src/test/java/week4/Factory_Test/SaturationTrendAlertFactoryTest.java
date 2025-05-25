package week4.Factory_Test;

import static org.junit.jupiter.api.Assertions.*;

import com.alerts.Alert;
import com.alerts.Factory.SaturationTrendAlertFactory;
import com.alerts.Week3Logic_StrategyPattern.AlertType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SaturationTrendAlertFactoryTest {

    private SaturationTrendAlertFactory factory;

    @BeforeEach
    public void setup() {
        factory = new SaturationTrendAlertFactory();
    }

    @Test
    public void testCreateAlert() {
        String patientId = "789";
        String condition = "Rapid oxygen saturation drop detected";
        long timestamp = 123456789L;

        Alert alert = factory.createAlert(patientId, condition, timestamp);

        assertNotNull(alert);
        assertEquals(patientId, alert.getPatientId());
        assertEquals(condition, alert.getCondition());
        assertEquals(timestamp, alert.getTimestamp());
        assertEquals(AlertType.OXYGEN_SATURATION_RAPID_DROP, alert.getAlertType());
    }
}
