/*
    Enum describing occurences when you reconcile an incoming event against
   what's stored.

    INSERTED: Completely new event
    MATERIAL: Already existing event in database, field that matters changed.
    TRIVIAL: Already existing event in database, nothing material changed.
    UNCHANGED: Already existing, identical (no-op)
 */
package ruinabeshima.earthquake_detector.ingest;

public enum ReconcileOutcome { INSERTED, MATERIAL, TRIVIAL, UNCHANGED }
;
