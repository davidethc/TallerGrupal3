package ec.edu.utpl.carreras.computacion.s7.model;

public record ClimateRecord(
    String formattedDate,
    String summary,
    String precipType,
    double temperature,
    double apparentTemperature,
    double humidity,
    double windSpeed,
    double windBearing,
    double visibility,
    double loudCover,
    double pressure,
    String dailySummary
) {

}