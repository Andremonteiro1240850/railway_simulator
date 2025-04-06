# USXXX - YYY

_XXX stands for User Story number and YYY for User Story description (e.g. US006 - Create a Task)_

## 1. Requirements Engineering

_In this section, it is suggested to capture the requirement description and specifications as provided by the client as well as any further clarification on it. It is also suggested to capture the requirements acceptance criteria and existing dependencies to other requirements. Finally, identify the involved input and output data and depict an Actor-System interaction in order to fulfill the requirement._

### 1.1. User Story Description

As a Player, I want to upgrade a selected station with a building

### 1.2. Customer Specifications and Clarifications 

There are no automatic updates; When a new building is available, the player can buy it and then the previous building is replaced.\
There are buildings that are mutually exclusive, such as small restaurants and large restaurants.\
There are buildings that, when they come into existence, it is no longer possible to build the previous one, for example, the telegraph was replaced by the telephone. Stations that have a telegraph continue to have the telegraph in operation until a telephone is built to replace the telegraph. From the moment that a telephone exists, it is no longer possible to build a telegraph.\
There cannot be multiple identical buildings (for example, I cannot have 3 grain silos).\
All combinations are possible, as long as the restrictions mentioned above are respected.

### 1.3. Acceptance Criteria

unspecified

### 1.4. Found out Dependencies

Build a station (US05)

### 1.5 Input and Output Data

Input: Select a station, choose a building\
Output: Gives date where the station will be upgraded, Station upgraded

### 1.6. System Sequence Diagram (SSD)

_Insert here a SSD depicting the envisioned Actor-System interactions and throughout which data is inputted and outputted to fulfill the requirement. All interactions must be numbered._

![USXXX-SSD](svg/USXXX-SSD.svg)

### 1.7 Other Relevant Remarks

Stations can be upgraded with, telegraphs, café, custom, post office, hotel, silo and liquid stations.
hotel and café can be small or large
telegraph can be upgraded to a telephone