package week4_Singleton_test;

import com.cardio_generator.HealthDataSimulator;
import com.data_management.DataStorage;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

public class TestIfSingletonWorks {

    @Test
    void testDataStorageSingleton() {
        DataStorage instance1 = DataStorage.getInstance();
        DataStorage instance2 = DataStorage.getInstance();

        assertNotNull(instance1, "There should be an instance of DataStorage");
        assertSame(instance1, instance2, "The instances should be the same");
    }

    @Test
    void testHealthDataSimulatorSingleton() {
        HealthDataSimulator instance1 = HealthDataSimulator.getInstance();
        HealthDataSimulator instance2 = HealthDataSimulator.getInstance();

        assertNotNull(instance1, "There should be an instance of HealthDataSimulator");
        assertSame(instance1, instance2, "The instances should be the same");
    }
}

