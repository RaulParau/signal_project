package Week5_test;

import com.data_management.DataStorage;
import com.data_management.PatientRecord;
import com.data_management.PatientWebsocketClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class PatientWebsocketClientTest {
    private PatientWebsocketClient client;
    private DataStorage storage;
    @BeforeEach
    public void setUp() throws URISyntaxException {
        storage = DataStorage.getInstance();
        storage.clear();
        client = new PatientWebsocketClient(new URI("ws://localhost:1011"), storage);
    }

    @Test
    public void testValidMessageParsing(){
        String message = "1, 1744, HeartRate, 90.0";
        client.onMessage(message);

        assertNotNull(storage.getPatient(1), "Patient 1 should exist");

        List<PatientRecord> records = storage.getRecords(1, 0, Long.MAX_VALUE);
        assertEquals(1, records.size(), "There should be one record for patient 1");

        PatientRecord record = records.get(0);
        assertEquals(1, record.getPatientId());
        assertEquals("HeartRate", record.getRecordType());
        assertEquals(90.0, record.getMeasurementValue());
        assertEquals(1744, record.getTimestamp());
    }

    @Test
    public void testInvalidMessageMissingFields() {
        String message = "1, 1744, HeartRate"; // missing measurementValue
        client.onMessage(message);
        assertTrue(storage.getAllPatients().isEmpty(), "Storage should remain empty on invalid input");
    }

    @Test
    public void testInvalidMessageNonNumericPatientId() {
        String message = "abc, 1744, HeartRate, 90.0";
        client.onMessage(message);
        assertTrue(storage.getAllPatients().isEmpty());
    }

    @Test
    public void testEmptyMessage() {
        client.onMessage("");
        assertTrue(storage.getAllPatients().isEmpty());
    }

    @Test
    public void testMultipleMessagesSamePatient() {
        client.onMessage("1, 1744, HeartRate, 90.0");
        client.onMessage("1, 1745, BloodPressure, 120.0");
        List<PatientRecord> records = storage.getRecords(1, 0, Long.MAX_VALUE);
        assertEquals(2, records.size());
    }

    @Test
    public void testMessagesForMultiplePatients() {
        client.onMessage("1, 1744, HeartRate, 90.0");
        client.onMessage("2, 1745, BloodPressure, 130.0");
        assertNotNull(storage.getPatient(1));
        assertNotNull(storage.getPatient(2));
        assertEquals(1, storage.getRecords(1, 0, Long.MAX_VALUE).size());
        assertEquals(1, storage.getRecords(2, 0, Long.MAX_VALUE).size());
    }
}
