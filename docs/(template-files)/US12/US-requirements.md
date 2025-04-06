# USXXX - YYY

_XXX stands for User Story number and YYY for User Story description (e.g. US006 - Create a Task)_

## 1. Requirements Engineering

_In this section, it is suggested to capture the requirement description and specifications as provided by the client as well as any further clarification on it. It is also suggested to capture the requirements acceptance criteria and existing dependencies to other requirements. Finally, identify the involved input and output data and depict an Actor-System interaction in order to fulfill the requirement._

### 1.1. User Story Description

As a Product Owner, I want to create a simulator that generates cargoes at current stations, automatically, considering the cities and
industries that the railway network serves.

### 1.2. Customer Specifications and Clarifications 

The user have customization options for cargo generation rules in the edition of the scenario.
the limit to cargo storage is 24.

### 1.3. Acceptance Criteria

This simulator should provide options for start/pause.

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

_Insert here a SSD depicting the envisioned Actor-System interactions and throughout which data is inputted and outputted to fulfill the requirement. All interactions must be numbered._

![USXXX-SSD](svg/USXXX-SSD.svg)

### 1.7 Other Relevant Remarks

_Use this section to capture other relevant information that is related with this US such as:  
&nbsp; &nbsp; (i) special requirements;  
&nbsp; &nbsp; (ii) data and/or technology variations;  
&nbsp; &nbsp; (iii) how often this US is held._