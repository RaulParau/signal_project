package week4.Factory_Test;

import com.alerts.Alert;
import com.alerts.Factory.AlertFactory;
import com.alerts.Factory.BloodPressureTrendAlertFactory;
import com.alerts.Week3Logic_StrategyPattern.AlertType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BloodPressureTrendAlertFactoryTest {

    @Test
    void testCreateAlert() {
        AlertFactory factory = new BloodPressureTrendAlertFactory();
        String patientId = "123";
        String condition = "Blood pressure rising trend";
        long timestamp = System.currentTimeMillis();

        Alert alert = factory.createAlert(patientId, condition, timestamp);

        assertNotNull(alert);
        assertEquals(patientId, alert.getPatientId());
        assertEquals(condition, alert.getCondition());
        assertEquals(timestamp, alert.getTimestamp());
        assertEquals(AlertType.BLOOD_PRESSURE_TREND, alert.getAlertType());
    }
}
