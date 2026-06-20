DROP TABLE IF EXISTS users;

CREATE OR REPLACE FUNCTION update_modified_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = NOW();
RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TABLE Earthquakes (
    id VARCHAR(20) PRIMARY KEY, -- ID is the same as the USGS event ID
    magnitude FLOAT NULL, -- Can be negative for earthquakes that are not felt
    place VARCHAR(255) NULL,

    status VARCHAR(20) CHECK (status IN ('automatic','reviewed','deleted')) NOT NULL,
    latitude FLOAT NOT NULL,
    longitude FLOAT NOT NULL,
    depth FLOAT NOT NULL,

    -- Optimistic Locking
    version BIGINT NOT NULL DEFAULT 0,

    -- When the earthquake occurred
    occurred_at TIMESTAMP WITH TIME ZONE NOT NULL,
    source_updated_at TIMESTAMP WITH TIME ZONE NOT NULL,

    -- When the earthquake was created in the database
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
    );

CREATE TRIGGER update_earthquake_updated_at
    BEFORE UPDATE ON Earthquakes
    FOR EACH ROW
    EXECUTE FUNCTION update_modified_column();