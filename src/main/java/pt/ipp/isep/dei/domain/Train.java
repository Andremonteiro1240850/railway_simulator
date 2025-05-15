package pt.ipp.isep.dei.domain;

import java.util.UUID;

/**
 * Represents a train created from a purchased locomotive.
 */
public class Train {

    private final String id;
    private final Locomotive locomotive;

    public Train(Locomotive locomotive) {
        this.id = UUID.randomUUID().toString();
        this.locomotive = locomotive;
    }

    public String getId() {
        return id;
    }

    public Locomotive getLocomotive() {
        return locomotive;
    }
}
