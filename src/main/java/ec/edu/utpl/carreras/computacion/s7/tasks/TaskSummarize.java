package ec.edu.utpl.carreras.computacion.s7.tasks;

import ec.edu.utpl.carreras.computacion.s7.model.ClimateRecord;
import ec.edu.utpl.carreras.computacion.s7.model.ClimateSummary;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class TaskSummarize implements Callable<ClimateSummary> {
    private final String path2Data;

    public TaskSummarize(String path2Data) {
        this.path2Data = path2Data;
    }

    @Override
    public ClimateSummary call() {
        try {

            var data = getDataAsList(path2Data);

            var tempAvg = data.stream().mapToDouble(ClimateRecord::temp).average().orElse(0.0);
            int currentYear = -1;
            double totalTemp = 0;
            int count = 0;


            for (var record : data) {
                if (record.year() != currentYear) {
                    if (currentYear != -1) {
                        double avgTemp = totalTemp / count;
                        System.out.println("Año: " + currentYear + ", Temperatura Promedio: " + avgTemp);
                    }
                    currentYear = record.year();
                    totalTemp = 0;
                    count = 0;
                }


                totalTemp += record.temp();
                count++;
            }

            if (count > 0) {
                double avgTemp = totalTemp / count;
                System.out.println("Año: " + currentYear + ", Temperatura Promedio: " + avgTemp);
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return null;
    }


    private List<ClimateRecord> getDataAsList(String path2Data) throws IOException {
        List<ClimateRecord> output = new ArrayList<>();
        var csvFormat = CSVFormat
                .RFC4180
                .builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .build();

        try (Reader reader = Files.newBufferedReader(Paths.get(path2Data));
             CSVParser parser = CSVParser.parse(reader, csvFormat)) {

            for (var csvRecord : parser) {

                var temp = Double.parseDouble(csvRecord.get("Temperature (C)"));
                var humidity = Double.parseDouble(csvRecord.get("Humidity"));
                var windSpeed = Double.parseDouble(csvRecord.get("Wind Speed (km/h)"));
                var visibility = Double.parseDouble(csvRecord.get("Visibility (km)"));
                var pressure = Double.parseDouble(csvRecord.get("Pressure (millibars)"));

                output.add(new ClimateRecord(temp, humidity, windSpeed, visibility, pressure));
            }
        }

        return output;
    }
}
