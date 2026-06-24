package ruinabeshima.earthquake_detector.usgs;

import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UsgsFeedResponseTest {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  void deserializesFeedJson() {
    String json = """
            {
                    "features": [
                    {
                        "id": "us7000",
                        "properties": {
                            "mag": 4.2,
                            "place": "10km N of Somewhere",
                            "time": 1718900000000,
                            "updated": 1718900500000,
                            "status": "reviewed", 
                            "tsunami": 0 
                        },
                        "geometry": {
                            "coordinates": [-122.5, 38.1, 7.3]
                            }
                        }
                    ]
                }
            """; // Tsunami is an unknown field to prove ignoreUnknown

    UsgsFeedResponse response = objectMapper.readValue(json, UsgsFeedResponse.class);

    assertThat(response.features()).isNotEmpty();
    UsgsFeedResponse.Feature feature = response.features().get(0);
    assertThat(feature.id()).isEqualTo("us7000");
    assertThat(feature.properties().mag()).isEqualTo(4.2);
    assertThat(feature.geometry().coordinates().get(0)).isEqualTo(-122.5);
    assertThat(feature.geometry().coordinates().get(1)).isEqualTo(38.1);
    assertThat(feature.geometry().coordinates().get(2)).isEqualTo(7.3);
  }
}
