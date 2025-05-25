package com.cardio_generator;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.cardio_generator.generators.*;

import com.cardio_generator.outputs.ConsoleOutputStrategy;
import com.cardio_generator.outputs.FileOutputStrategy;
import com.cardio_generator.outputs.OutputStrategy;
import com.cardio_generator.outputs.TcpOutputStrategy;
import com.cardio_generator.outputs.WebSocketOutputStrategy;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;

public class HealthDataSimulator {

    private static HealthDataSimulator instance;

    private int patientCount = 50;
    private ScheduledExecutorService scheduler;
    private OutputStrategy outputStrategy = new ConsoleOutputStrategy();
    private final Random random = new Random();

    // Singleton accessor
    public static synchronized HealthDataSimulator getInstance() {
        if (instance == null) {
            instance = new HealthDataSimulator();
        }
        return instance;
    }

    // Private constructor
    private HealthDataSimulator() {
    }

    // Entry point
    public static void main(String[] args) throws IOException {
        HealthDataSimulator simulator = HealthDataSimulator.getInstance();
        simulator.run(args);
    }

    public void run(String[] args) throws IOException {
        parseArguments(args);
        scheduler = Executors.newScheduledThreadPool(patientCount * 4);
        List<Integer> patientIds = initializePatientIds(patientCount);
        Collections.shuffle(patientIds);
        scheduleTasksForPatients(patientIds);
    }

    private void parseArguments(String[] args) throws IOException {
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-h":
                    printHelp();
                    System.exit(0);
                    break;
                case "--patient-count":
                    if (i + 1 < args.length) {
                        try {
                            patientCount = Integer.parseInt(args[++i]);
                        } catch (NumberFormatException e) {
                            System.err.println("Error: Invalid number of patients. Using default value: " + patientCount);
                        }
                    }
                    break;
                case "--output":
                    if (i + 1 < args.length) {
                        String outputArg = args[++i];
                        if (outputArg.equals("console")) {
                            outputStrategy = new ConsoleOutputStrategy();
                        } else if (outputArg.startsWith("file:")) {
                            String baseDirectory = outputArg.substring(5);
                            Path outputPath = Paths.get(baseDirectory);
                            if (!Files.exists(outputPath)) {
                                Files.createDirectories(outputPath);
                            }
                            outputStrategy = new FileOutputStrategy(baseDirectory);
                        } else if (outputArg.startsWith("websocket:")) {
                            try {
                                int port = Integer.parseInt(outputArg.substring(10));
                                outputStrategy = new WebSocketOutputStrategy(port);
                                System.out.println("WebSocket output will be on port: " + port);
                            } catch (NumberFormatException e) {
                                System.err.println("Invalid port for WebSocket output.");
                            }
                        } else if (outputArg.startsWith("tcp:")) {
                            try {
                                int port = Integer.parseInt(outputArg.substring(4));
                                outputStrategy = new TcpOutputStrategy(port);
                                System.out.println("TCP output will be on port: " + port);
                            } catch (NumberFormatException e) {
                                System.err.println("Invalid port for TCP output.");
                            }
                        } else {
                            System.err.println("Unknown output type. Using default (console).");
                        }
                    }
                    break;
                default:
                    System.err.println("Unknown option '" + args[i] + "'");
                    printHelp();
                    System.exit(1);
            }
        }
    }

    private void printHelp() {
        System.out.println("Usage: java HealthDataSimulator [options]");
        System.out.println("Options:");
        System.out.println("  -h                       Show help and exit.");
        System.out.println("  --patient-count <count>  Number of patients (default: 50).");
        System.out.println("  --output <type>          Output method: 'console', 'file:<dir>', 'websocket:<port>', 'tcp:<port>'");
    }

    private List<Integer> initializePatientIds(int count) {
        List<Integer> ids = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            ids.add(i);
        }
        return ids;
    }

    private void scheduleTasksForPatients(List<Integer> patientIds) {
        ECGDataGenerator ecg = new ECGDataGenerator(patientCount);
        BloodSaturationDataGenerator saturation = new BloodSaturationDataGenerator(patientCount);
        BloodPressureDataGenerator pressure = new BloodPressureDataGenerator(patientCount);
        BloodLevelsDataGenerator levels = new BloodLevelsDataGenerator(patientCount);
        SelfTriggeredAlertGenerator selfAlerts = new SelfTriggeredAlertGenerator();
        AlertGenerator alerts = new AlertGenerator(patientCount);

        for (int id : patientIds) {
            scheduleTask(() -> ecg.generate(id, outputStrategy), 1, TimeUnit.SECONDS);
            scheduleTask(() -> saturation.generate(id, outputStrategy), 1, TimeUnit.SECONDS);
            scheduleTask(() -> pressure.generate(id, outputStrategy), 1, TimeUnit.MINUTES);
            scheduleTask(() -> levels.generate(id, outputStrategy), 2, TimeUnit.MINUTES);
            scheduleTask(() -> alerts.generate(id, outputStrategy), 20, TimeUnit.SECONDS);
            scheduleTask(() -> selfAlerts.generate(id, outputStrategy), 1, TimeUnit.SECONDS);
        }
    }

    private void scheduleTask(Runnable task, long period, TimeUnit unit) {
        scheduler.scheduleAtFixedRate(task, random.nextInt(5), period, unit);
    }
}

