package ec.edu.utpl.carreras.computacion.s7;

import ec.edu.utpl.carreras.computacion.s7.model.StatsAccumulator;
import ec.edu.utpl.carreras.computacion.s7.tasks.ClimateStatsTask;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws Exception {
        String path = "/Users/monky02/Desktop/TallerGrupal3/weatherHistory.csv";
        int numThreads = 4; // Número de hilos en el pool

        // Lee todas las líneas del archivo (excepto el encabezado)
        List<String> allLines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String header = br.readLine(); // Salta encabezado
            String line;
            while ((line = br.readLine()) != null) {
                allLines.add(line);
            }
        }

        // Divide las líneas en partes para cada hilo
        int chunkSize = (allLines.size() + numThreads - 1) / numThreads;
        List<List<String>> partitions = new ArrayList<>();
        for (int i = 0; i < allLines.size(); i += chunkSize) {
            partitions.add(allLines.subList(i, Math.min(i + chunkSize, allLines.size())));
        }

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        List<Future<Map<Integer, StatsAccumulator>>> futures = new ArrayList<>();

        // Lanza una tarea por partición
        for (List<String> part : partitions) {
            futures.add(executor.submit(new ClimateStatsTask(part)));
        }

        // Combina los resultados de todos los hilos
        Map<Integer, StatsAccumulator> statsByYear = new HashMap<>();
        for (Future<Map<Integer, StatsAccumulator>> future : futures) {
            Map<Integer, StatsAccumulator> partial = future.get();
            for (var entry : partial.entrySet()) {
                statsByYear.computeIfAbsent(entry.getKey(), y -> new StatsAccumulator())
                    .merge(entry.getValue());
            }
        }

        // Imprime resultados
        for (var entry : statsByYear.entrySet()) { 
            int year = entry.getKey();
            StatsAccumulator stats = entry.getValue();
            System.out.printf("Año: %d | Temp: %.2f | Humedad: %.2f | Viento: %.2f | Visibilidad: %.2f | Presión: %.2f%n",
                year, stats.avgTemp(), stats.avgHumidity(), stats.avgWind(), stats.avgVis(), stats.avgPressure());

            System.out.printf("  Más frío: %s (%.2f°C)%n", stats.minTempRecord().formattedDate(), stats.minTempRecord().temperature());
            System.out.printf("  Más calor: %s (%.2f°C)%n", stats.maxTempRecord().formattedDate(), stats.maxTempRecord().temperature());
            System.out.printf("  Menor visibilidad: %s (%.2f km)%n", stats.minVisRecord().formattedDate(), stats.minVisRecord().visibility());
            System.out.printf("  Mayor visibilidad: %s (%.2f km)%n", stats.maxVisRecord().formattedDate(), stats.maxVisRecord().visibility());
            System.out.printf("  Menor humedad: %s (%.2f)%n", stats.minHumidityRecord().formattedDate(), stats.minHumidityRecord().humidity());
            System.out.printf("  Mayor humedad: %s (%.2f)%n", stats.maxHumidityRecord().formattedDate(), stats.maxHumidityRecord().humidity());
            System.out.printf("  Menor viento: %s (%.2f km/h)%n", stats.minWindRecord().formattedDate(), stats.minWindRecord().windSpeed());
            System.out.printf("  Mayor viento: %s (%.2f km/h)%n", stats.maxWindRecord().formattedDate(), stats.maxWindRecord().windSpeed());
        }

        executor.shutdown();
    }
}
