# US05 - As a Player, I want to build a station.


## 1. Requirements Engineering

### 1.1. User Story Description

As a Player, I want to build a station
### 1.2. Customer Specifications and Clarifications 

>As the **PLAYER**, we should be able to build a Station, choose type of building and name, and centre if is a station
Position; Station type (Terminal, Station or Depot) and, in the case of the station, the center must be filled in. If the proposed is not accepted an alternative one must be entered.\
Overbuilding in the Editor is possible, the existing building is replaced for the new one;\
Overbuilding in the Simulator is NOT possible, the player gets a warning and games continues.\
 A player can build depots, stations and terminal as far he have available budget and there no overbuilding.


** From the Client Clarifications:**
>**Question:**  If overbuilding occurs, the program stops running and the game ends or simply gives an error message and asks again for the station construction place?<br>
> **Answer:** Overbuilding in the Editor is possible, the existing building is replaced for the new one;

>**Question:**  Should users have options to select different station types (Depot, Station, Terminal) based on the game rules? <br>
>**Answer:** No, a player can build depots, stations and terminal as far he have available budget and there no overbuilding.
### 1.3. Acceptance Criteria

**AC1:** Overbuilding prevention
- In Simulator mode, prevent station construction if location is occupied
- Show appropriate warning message when overbuilding attempted

**AC2:** Station naming
- Allow player to modify proposed name

**AC3:** Position validation
- For Stations: require player to select center position
- For Depots/Terminals: use geometric center automatically

**AC4:** Budget verification
- Verify player has sufficient funds before construction
- Show error if insufficient funds

**AC5:** Type-specific rules enforcement
- Apply correct radius based on station type 
- Enforce correct construction costs 

### 1.4. Found out Dependencies

- **US01**: Requires existing map to build on
- **US04**: Scenario defines available resources/budget
- **US07**: Station list needed for overbuilding verification
- **US12**: Simulator mode affects overbuilding behavior

### 1.5 Input and Output Data

**Input Data:**
- Selected map (from US01)
- Station type (Depot/Station/Terminal)
- Desired coordinates (X,Y)
- For Stations only: center position 
- Player confirmation/modification of auto-generated name
- Current player budget

**Output Data:**
- Success:
 - Updated map with new station
 - Updated player budget
 - Construction confirmation message
- Failure:
 - Overbuilding warning (Simulator mode)
 - Insufficient funds warning
 - Invalid position warning

### 1.6. System Sequence Diagram (SSD)


![US05-SSD](/docs/system-documentation/US01/01.requirements-engineering.us01/svg/us01Diagram.svg)

### 1.7 Other Relevant Remarks

1. Different modes (Editor/Simulator) have different overbuilding policies
2. Station type determines all construction parameters
3. Economic radius affects future cargo generation
4. All construction consumes player budget immediately