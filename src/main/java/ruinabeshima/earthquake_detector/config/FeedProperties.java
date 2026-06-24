/*
    This file creates a configuration class that maps properties in
    application.properties to a strongly typed Java object.

    Record is used instead of the standard object definition because it
    automatically creates methods such as getters / setters.
*/

package ruinabeshima.earthquake_detector.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.feed")
public record FeedProperties(String url,
                             int interval // in milliseconds
) {}
