# US12-As a Product Owner, I want to create a simulator that generates cargoes at current stations, automatically, considering the cities and industries that the railway network serves.



## 1. Requirements Engineering

### 1.1. User Story Description

As a Product Owner, I want to create a simulator that generates cargoes at current stations, automatically, considering the cities and
industries that the railway network serves.

### 1.2. Customer Specifications and Clarifications 

* The user have customization options for cargo generation rules in the edition of the scenario.
 the limit to cargo storage is 24.

### 1.3. Acceptance Criteria

* AC01-This simulator should provide options for start/pause.

### 1.4. Found out Dependencies

US02 - Add industry to the map.

Cargo is based on the industries present on the map, so they must exist.

US03 - Add city to the map.

Cities generate/start consuming certain types of cargo.

US04 - Create scenario for the selected map.

The frequency of cargo generation (fixed or random) and the types of locomotion available come from the scenario definitions.

US05 - Building stations.

Cargo generation only occurs in cities/industries that have stations, so they must have been previously created.

### 1.5 Input and Output Data
input: scenario and map selection, start simulation, scenario confiruraton data, pause simulation. 

 output: simulator readiness confirmation, automatic cargo generation, cargo stored at stations, simulation pause confirmation
### 1.6. System Sequence Diagram (SSD)

![USXXX-SSD](/docs/system-documentation/US12/01.requirements-engineering.us12/svg/us12Diagram.png)

### 1.7 Other Relevant Remarks

n/a