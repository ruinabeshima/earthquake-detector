package ruinabeshima.earthquake_detector.usgs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/*
    JSON -> Java Object

    USGS GeoJSON API sends in a JSON object.
    Jackson translates raw text (JSON) into strongly typed Java code.
    Jackson deserialises it into a Java Object (entity).
    Springboot uses Jackson under the hood automatically.

    This file builds a DTO (Data Transfer Object) class that Jackson maps the
    JSON onto.

    GEOJSON:
    {
        "features": [
        {
            "id": "us7000",
            "properties": {
                "mag": 4.2,
                "place": "10km N of Somewhere",
                "time": 1718900000000,
                "updated": 1718900500000,
                "status": "reviewed"
            },
            "geometry": {
                "coordinates": [-122.5, 38.1, 7.3]
                }
            }
        ]
    }

*/

// The response is one large object with array "features"
@JsonIgnoreProperties(ignoreUnknown = true) // Ignore unknown properties
public record UsgsFeedResponse(List<Feature> features) {

  // Each element in "features" is an object with fields:
  @JsonIgnoreProperties(ignoreUnknown = true)
  public record Feature(String id, Properties properties, Geometry geometry) {}

  @JsonIgnoreProperties(ignoreUnknown = true)
  public record Properties(Double mag, String place, Long time, Long updated,
                           String status) {}

  @JsonIgnoreProperties(ignoreUnknown = true)
  public record Geometry(List<Double> coordinates) {}
}
