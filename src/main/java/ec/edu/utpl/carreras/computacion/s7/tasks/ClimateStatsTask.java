package ec.edu.utpl.carreras.computacion.s7.tasks;

import ec.edu.utpl.carreras.computacion.s7.model.StatsAccumulator;
import ec.edu.utpl.carreras.computacion.s7.model.ClimateRecord;
import java.util.*;
import java.util.concurrent.Callable;
import org.apache.commons.csv.*;
import java.io.*;

public class ClimateStatsTask implements Callable<Map<Integer, StatsAccumulator>> {
    private final List<String> lines;

    // Nuevo constructor para particiones de líneas
    public ClimateStatsTask(List<String> lines) {
        this.lines = lines;
    }

    @Override
    public Map<Integer, StatsAccumulator> call() throws Exception {
        Map<Integer, StatsAccumulator> statsByYear = new HashMap<>();
        for (String line : lines) {
            // Divide la línea CSV en campos (ajusta si hay comas dentro de campos)
            String[] fields = line.split(",", -1);

            // Asume el orden de columnas según tu archivo CSV
            String dateStr = fields[0];
            int year = Integer.parseInt(dateStr.substring(0, 4));

            ClimateRecord record = new ClimateRecord(
                fields[0], // Formatted Date
                fields[1], // Summary
                fields[2], // Precip Type
                Double.parseDouble(fields[3]), // Temperature (C)
                Double.parseDouble(fields[4]), // Apparent Temperature (C)
                Double.parseDouble(fields[5]), // Humidity
                Double.parseDouble(fields[6]), // Wind Speed (km/h)
                Double.parseDouble(fields[7]), // Wind Bearing (degrees)
                Double.parseDouble(fields[8]), // Visibility (km)
                Double.parseDouble(fields[9]), // Loud Cover
                Double.parseDouble(fields[10]), // Pressure (millibars)
                fields[11] // Daily Summary
            );

            statsByYear
                .computeIfAbsent(year, y -> new StatsAccumulator())
                .add(record);
        }
        return statsByYear;
    }
}