package ruinabeshima.earthquake_detector.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ruinabeshima.earthquake_detector.entity.EarthquakeEvent;

// Interface: List of method names and signatures with no code behind them.
// Spring Data JPA will generate the implementation at runtime based on the method names and signatures.
// JpaRepository provides basic CRUD operations and pagination/sorting capabilities out of the box.
// String: Type of ID field
public interface EarthquakeEventRepository extends JpaRepository<EarthquakeEvent, String> {
}
