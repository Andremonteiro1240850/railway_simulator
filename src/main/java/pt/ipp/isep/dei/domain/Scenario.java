package pt.ipp.isep.dei.domain;
import java.util.List;

    public class Scenario {
        private final List<String> portCargoBehaviors;
        private final List<String> availableLocomotionTypes;
        private final String generationFactor;

        public Scenario(List<String> portCargoBehaviors, List<String> locomotionTypes, String generationFactor) {
            this.portCargoBehaviors = portCargoBehaviors;
            this.availableLocomotionTypes = locomotionTypes;
            this.generationFactor = generationFactor;
        }

        public List<String> getPortCargoBehaviors() {
            return portCargoBehaviors;
        }

        public List<String> getAvailableLocomotionTypes() {
            return availableLocomotionTypes;
        }

        public String getGenerationFactor() {
            return generationFactor;
        }
    }

