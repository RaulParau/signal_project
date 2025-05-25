package Week5_test;

import com.data_management.DataStorage;
import com.data_management.PatientWebsocketClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ErrorHandlingTest {

        private PatientWebsocketClient client;
        private DataStorage storage;

        @BeforeEach
        public void setup() throws URISyntaxException {
            storage = DataStorage.getInstance();
            client = new PatientWebsocketClient(new URI("ws://localhost:1234"), storage);
        }

        @Test
        public void testMalformedMessage() {
            String malformed = "this,is,not,valid";
            client.onMessage(malformed);
            assertTrue(storage.getAllPatients().isEmpty());
        }

        @Test
        public void testNonNumericMeasurement() {
            String message = "1, 123456789, HeartRate, not_a_number";
            client.onMessage(message);
            assertTrue(storage.getAllPatients().isEmpty());
        }

        @Test
        public void testWebSocketErrorHandling() {
            client.onError(new RuntimeException("Test error"));
        }

        @Test
        public void testWebSocketDisconnection() {
            client.onClose(1006, "Abnormal closure", false);
        }


}
