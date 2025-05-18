# US12 - As a Product Owner, I want to create a simulator that generates cargoes at current stations, automatically, considering the cities and industries that the railway network serves.



## 3. Design

### 3.1. Rationale

**The rationale grounds on the SSD interactions and the identified input/output data.**

| Interaction ID | Question: Which class is responsible for...                                                            | Answer                  | Justification (with GRASP patterns)                                                                 |
|----------------|--------------------------------------------------------------------------------------------------------|--------------------------|------------------------------------------------------------------------------------------------------|
| Step 1         | ... interacting with the Product Owner?                                                                | SimulationUI            | Pure Fabrication: mediates between user and system logic.                                           |
|                | ... coordinating the simulation setup?                                                                 | SimulationController    | Controller: central coordinator for the simulation logic.                                           |
| Step 2         | ... loading the scenario from the selected map?                                                        | ScenarioRepository      | Information Expert: owns the data about scenarios and handles persistence/retrieval.               |
| Step 3         | ... retrieving all stations in the map?                                                                | StationRepository       | Information Expert: encapsulates access to station data.                                            |
| Step 4         | ... checking whether stations are valid for cargo generation?                                          | Station                 | Information Expert: knows its own status and relation to nearby cities/industries.                 |
| Step 5         | ... generating cargo based on scenario and surroundings?                                               | CargoGenerator          | Information Expert: encapsulates the logic for contextual cargo creation.                           |
| Step 6         | ... scheduling periodic generation (fixed or random)?                                                  | SimulationScheduler     | Information Expert: manages time-based execution and scheduling.                                   |
| Step 7         | ... storing cargo at the station (max 24 units)?                                                       | Station                 | Information Expert: owns and manages the stored cargo.                                              |
| Step 8         | ... showing the simulation has started?                                                                | SimulationUI            | Pure Fabrication: responsible for communicating status to the user.                                |
| Step 9         | ... pausing the simulation?                                                                            | SimulationController    | Controller: responsible for simulation control actions.                                             |
| Step 10        | ... showing the simulation has been paused successfully?                                               | SimulationUI            | Pure Fabrication: UI component handles final user feedback.                                         |

### Systematization ##

According to the taken rationale, the conceptual classes promoted to software classes are:


* Station 
* Scenario

Other software classes (i.e. Pure Fabrication) identified:

* SimulationUI 
* SimulationController 
* ScenarioRepository
* StationRepository 
* CargoGenerator 
* SimulationScheduler



## 3.2. Sequence Diagram (SD)


![USXXX-SD](/docs/system-documentation/US12/03.design/svg/US012-SD-full.svg)


### Split Diagrams

The following diagram shows the same sequence of interactions between the classes involved in the realization of this user story, but it is split in partial diagrams to better illustrate the interactions between the classes.


![Sequence Diagram - split](/docs/system-documentation/US12/03.design/svg/US12-split.svg)

**Load Scenario Partial SD**

![Sequence Diagram - Partial - Confirm Locomotive](/docs/system-documentation/US12/03.design/svg/US12-SD-parial-load-Scenario.svg)

**Start Simulation Partial SD**

![Sequence Diagram - Partial - Get Available Locomotive ](/docs/system-documentation/US12/03.design/svg/US12-SD-partial-start-simulation.svg)



**Pause Simulation Partial SD**

![Sequence Diagram - Partial - Get Available Locomotive ](/docs/system-documentation/US12/03.design/svg/US12-SD-partial-pause-simulation.svg)


## 3.3. Class Diagram (CD)

![USXXX-CD](/docs/system-documentation/US12/03.design/svg/US12-CD.svg)