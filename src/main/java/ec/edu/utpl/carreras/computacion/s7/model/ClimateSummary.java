package ec.edu.utpl.carreras.computacion.s7.model;

public record ClimateSummary(
        String formattedDate,
        String summary,
        String precipType,
        double temperature,
        double apparentTemperature,
        double windSpeed,
        int windBearing,
        double visibility,
        double cloudCover,
        double pressure,
        String dailySummary
) {}