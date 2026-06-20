package ruinabeshima.earthquake_detector.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "earthquakes")
public class EarthquakeEvent {

    @Id
    private String id;

    // Double instead of double for nullable values
    private Double magnitude;
    private String place;
    private String status;
    private double latitude;
    private double longitude;
    private double depth;

    @Version
    private long version;

    private Instant occurredAt;
    private Instant sourceUpdatedAt;
    @Column(insertable = false, updatable = false)
    private Instant createdAt;
    @Column(insertable = false, updatable = false)
    private Instant updatedAt;

    // Constructors
    public EarthquakeEvent(){
    }

    // Take in non-nullable fields
    public EarthquakeEvent(String id, String status, double latitude, double longitude, double depth, Instant occurredAt, Instant sourceUpdatedAt){
        this.id = id;
        this.status = status;
        this.latitude = latitude;
        this.longitude = longitude;
        this.depth = depth;
        this.occurredAt = occurredAt;
        this.sourceUpdatedAt = sourceUpdatedAt;
    }

    // Getters and setters
    public String getId(){return id;}
    public void setId(String id){this.id = id;}
    public Double getMagnitude(){return magnitude;}
    public void setMagnitude(Double magnitude){this.magnitude = magnitude;}
    public String getPlace(){return place;}
    public void setPlace(String place){this.place = place;}
    public String getStatus(){return status;}
    public void setStatus(String status){this.status = status;}
    public double getLatitude(){return latitude;}
    public void setLatitude(double latitude){this.latitude = latitude;}
    public double getLongitude(){return longitude;}
    public void setLongitude(double longitude){this.longitude = longitude;}
    public double getDepth(){return depth;}
    public void setDepth(double depth){this.depth = depth;}
    public long getVersion(){return version;}
    public Instant getOccurredAt(){return occurredAt;}
    public void setOccurredAt(Instant occurredAt){this.occurredAt = occurredAt;}
    public Instant getSourceUpdatedAt(){return sourceUpdatedAt;}
    public void setSourceUpdatedAt(Instant sourceUpdatedAt){this.sourceUpdatedAt = sourceUpdatedAt;}
    public Instant getCreatedAt(){return createdAt;}
    public Instant getUpdatedAt(){return updatedAt;}
}
