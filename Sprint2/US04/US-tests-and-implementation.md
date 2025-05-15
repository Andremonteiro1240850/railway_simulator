# US04 - Create a scenario

## 4. Tests

**Test 1:** Test that the scenario can not be created without a map

	@Test(expected = IllegalArgumentException.class)
        public void ensureMapSelectionIsRequired() {
            CreateScenarioController controller = new CreateScenarioController();
            controller.createScenario(null, portBehavior, locomotionTypes, industryFactors);
        }

**Test 2:** Test Port behavior options

    @Test
    public void testGetPortBehaviorOptions() {
        CreateScenarioController controller = new CreateScenarioController();
        List<String> behaviors = controller.getAvailablePortBehaviors();
        assertFalse(behaviors.isEmpty());
    }

**Test 3:** Test industry generating frequency

    @Test(expected = IllegalArgumentException.class)
        public void ensureIndustryFactorsAreValid() {
            CreateScenarioController controller = new CreateScenarioController();
            controller.createScenario(selectedMap, portBehavior, locomotionTypes, null);
        }

## 5. Construction (Implementation)

### Class CreateScenarioController

    public class CreateScenarioController {
    private MapManager mapManager;
    private PortManager portManager;
    private TransportSystem transportSystem;
    private IndustryManager industryManager;
    private ScenarioManager scenarioManager;

    public List<Map> getAvailableMaps() {
        return mapManager.getAllMaps();
    }

    public List<String> getAvailablePortBehaviors() {
        return portManager.getBehaviorOptions();
    }

    public List<String> getAvailableLocomotionTypes() {
        return transportSystem.requestLocomotionTypes();
    }

    public Map<String, Double> getIndustryGenerationFactors() {
        return industryManager.getGenerationFrequency();
    }

    public Scenario createScenario(Map selectedMap, String portBehavior, 
                                  List<String> locomotionTypes, 
                                  Map<String, Double> industryFactors) {
        return scenarioManager.create(selectedMap, portBehavior, locomotionTypes, industryFactors);
    }
    }

### Class ScenarioManager

    public class ScenarioManager {
    public Scenario create(Map map, String portBehavior, List<String> locomotionTypes, 
                          Map<String, Double> industryFactors) {
        Scenario scenario = new Scenario(map);
        scenario.setPortBehavior(portBehavior);
        scenario.setLocomotionTypes(locomotionTypes);
        scenario.configureIndustries(industryFactors);
        return scenario;
    }
    }




## 6. Integration and Demo

* The scenario creation process have:
    * Map Manager to look for the maps
    * Port Manager to retrieve behavior options
    * Industry Manager to apply generation frequencies

* User interaction:
    * Editor selects a map and defines port behavior, locomotive types
    *

## 7. Observations

n/ahis section, it is suggested to present a critical perspective on the developed work, pointing, for example, to other alternatives and or future related work._