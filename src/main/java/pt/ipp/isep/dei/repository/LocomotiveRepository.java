package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.Locomotive;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LocomotiveRepository {

        private final List<Locomotive> purchasedLocomotives = new ArrayList<>();

        public void addPurchasedLocomotive(Locomotive loco) {
            purchasedLocomotives.add(loco);
        }

        public List<Locomotive> getPurchasedLocomotives() {
            return purchasedLocomotives;
        }
    }

