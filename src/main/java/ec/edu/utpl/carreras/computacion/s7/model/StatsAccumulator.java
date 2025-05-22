package ec.edu.utpl.carreras.computacion.s7.model;

public class StatsAccumulator {
    double sumTemp = 0, sumHumidity = 0, sumWind = 0, sumVis = 0, sumPressure = 0;
    int count = 0;

    ClimateRecord minTempRecord = null, maxTempRecord = null;
    ClimateRecord minVisRecord = null, maxVisRecord = null;
    ClimateRecord minHumidityRecord = null, maxHumidityRecord = null;
    ClimateRecord minWindRecord = null, maxWindRecord = null;

    public void add(ClimateRecord record) {
        sumTemp += record.temperature();
        sumHumidity += record.humidity();
        sumWind += record.windSpeed();
        sumVis += record.visibility();
        sumPressure += record.pressure();
        count++;

        if (minTempRecord == null || record.temperature() < minTempRecord.temperature()) minTempRecord = record;
        if (maxTempRecord == null || record.temperature() > maxTempRecord.temperature()) maxTempRecord = record;
        if (minVisRecord == null || record.visibility() < minVisRecord.visibility()) minVisRecord = record;
        if (maxVisRecord == null || record.visibility() > maxVisRecord.visibility()) maxVisRecord = record;
        if (minHumidityRecord == null || record.humidity() < minHumidityRecord.humidity()) minHumidityRecord = record;
        if (maxHumidityRecord == null || record.humidity() > maxHumidityRecord.humidity()) maxHumidityRecord = record;
        if (minWindRecord == null || record.windSpeed() < minWindRecord.windSpeed()) minWindRecord = record;
        if (maxWindRecord == null || record.windSpeed() > maxWindRecord.windSpeed()) maxWindRecord = record;
    }

    public void merge(StatsAccumulator other) {
        this.sumTemp += other.sumTemp;
        this.sumHumidity += other.sumHumidity;
        this.sumWind += other.sumWind;
        this.sumVis += other.sumVis;
        this.sumPressure += other.sumPressure;
        this.count += other.count;

        if (this.minTempRecord == null || 
            (other.minTempRecord != null && other.minTempRecord.temperature() < this.minTempRecord.temperature())) {
            this.minTempRecord = other.minTempRecord;
        }
        if (this.maxTempRecord == null || 
            (other.maxTempRecord != null && other.maxTempRecord.temperature() > this.maxTempRecord.temperature())) {
            this.maxTempRecord = other.maxTempRecord;
        }
        if (this.minVisRecord == null || 
            (other.minVisRecord != null && other.minVisRecord.visibility() < this.minVisRecord.visibility())) {
            this.minVisRecord = other.minVisRecord;
        }
        if (this.maxVisRecord == null || 
            (other.maxVisRecord != null && other.maxVisRecord.visibility() > this.maxVisRecord.visibility())) {
            this.maxVisRecord = other.maxVisRecord;
        }
        if (this.minHumidityRecord == null || 
            (other.minHumidityRecord != null && other.minHumidityRecord.humidity() < this.minHumidityRecord.humidity())) {
            this.minHumidityRecord = other.minHumidityRecord;
        }
        if (this.maxHumidityRecord == null || 
            (other.maxHumidityRecord != null && other.maxHumidityRecord.humidity() > this.maxHumidityRecord.humidity())) {
            this.maxHumidityRecord = other.maxHumidityRecord;
        }
        if (this.minWindRecord == null || 
            (other.minWindRecord != null && other.minWindRecord.windSpeed() < this.minWindRecord.windSpeed())) {
            this.minWindRecord = other.minWindRecord;
        }
        if (this.maxWindRecord == null || 
            (other.maxWindRecord != null && other.maxWindRecord.windSpeed() > this.maxWindRecord.windSpeed())) {
            this.maxWindRecord = other.maxWindRecord;
        }
    }

    public double avgTemp() { return sumTemp / count; }
    public double avgHumidity() { return sumHumidity / count; }
    public double avgWind() { return sumWind / count; }
    public double avgVis() { return sumVis / count; }
    public double avgPressure() { return sumPressure / count; }

    public ClimateRecord minTempRecord() { return minTempRecord; }
    public ClimateRecord maxTempRecord() { return maxTempRecord; }
    public ClimateRecord minVisRecord() { return minVisRecord; }
    public ClimateRecord maxVisRecord() { return maxVisRecord; }
    public ClimateRecord minHumidityRecord() { return minHumidityRecord; }
    public ClimateRecord maxHumidityRecord() { return maxHumidityRecord; }
    public ClimateRecord minWindRecord() { return minWindRecord; }
    public ClimateRecord maxWindRecord() { return maxWindRecord; }
}