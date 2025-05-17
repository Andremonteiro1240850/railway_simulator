package pt.ipp.isep.dei.repository;


 import java.util.ArrayList;
import java.util.List;

    public class TypeStationRepository {
        private final List<String> stationTypes;
        private final List<String> stationRegions; // Apenas para estações do tipo "station"

        public TypeStationRepository() {
            this.stationTypes = new ArrayList<>();
            this.stationRegions = new ArrayList<>();
            initializeStationTypes();
            initializeStationRegions();
        }

        private void initializeStationTypes() {
            stationTypes.add("depot");
            stationTypes.add("terminal");
            stationTypes.add("station");
        }

        private void initializeStationRegions() {
            stationRegions.add("NE");
            stationRegions.add("NW");
            stationRegions.add("SE");
            stationRegions.add("SW");
        }

        // Métodos para obter os tipos de estações e regiões
        public List<String> getAllStationTypes() {
            return new ArrayList<>(stationTypes);
        }

        public List<String> getAllStationRegions() {
            return new ArrayList<>(stationRegions);
        }

        // Verifica se um tipo de estação é válido
        public boolean isValidStationType(String type) {
            return stationTypes.contains(type.toLowerCase());
        }

        // Verifica se uma região é válida (apenas para estações do tipo "station")
        public boolean isValidStationRegion(String region) {
            return stationRegions.contains(region.toUpperCase());
        }
    }

