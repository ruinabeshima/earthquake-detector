/*
    This file sets up a globally available, reusable HTTP Client (REST Client).

    @Configuration: Tells Spring Boot during startup to scan the class for
    Bean definitions

    @Bean: Tells Spring to execute the restClient() method, take the returned
   RestClient object, and register it inside the Spring Application Context as a
   managed Singleton (only creates one object of that class).

    Dependency Injection (RestClient.Builder builder): Spring automatically
   injects a pre-configured Builder object into this method. This builder
   already has Spring Boot's default settings attached (like default HTTP
   message converters for JSON parsing).

    builder.build(): Instantiates the thread-safe RestClient instance.
*/

package ruinabeshima.earthquake_detector.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

  @Bean
  public RestClient restClient() {
    return RestClient.builder().build();
  }
}
