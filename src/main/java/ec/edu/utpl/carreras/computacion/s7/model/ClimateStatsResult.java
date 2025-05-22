package ec.edu.utpl.carreras.computacion.s7.model;

public record ClimateStatsResult(
    double avgTemp,
    double avgHumidity,
    double avgWindSpeed,
    double avgVisibility,
    double avgPressure,
    ClimateRecord minTempRecord,
    ClimateRecord maxTempRecord,
    ClimateRecord minVisibilityRecord,
    ClimateRecord maxVisibilityRecord,
    ClimateRecord minHumidityRecord,
    ClimateRecord maxHumidityRecord,
    ClimateRecord minWindSpeedRecord,
    ClimateRecord maxWindSpeedRecord
) {}