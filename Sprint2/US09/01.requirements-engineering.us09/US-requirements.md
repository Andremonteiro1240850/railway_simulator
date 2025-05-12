# US09 - As a Player, I want to buy a locomotive


## 1. Requirements Engineering


### 1.1. User Story Description

As a Player, I want to buy a locomotive

### 1.2. Customer Specifications and Clarifications 
**From the client clarifications:**

> A player can buy the same type of locomotive, but these locomotives  have unique identification (like serial number). 

> A player can hava an unlimited number of locomotives, just need to have available memory.



### 1.3. Acceptance Criteria

* **AC1:** The player should choose the locomotive from a list of available
* **AC2:** locomotives for the scenario as well as a current date.

### 1.4. Found out Dependencies

* There is a dependency on "US04 - As an Editor, I want to create a scenario for a selected map" because the implementation of this US is only possible if there is already a scenario created with locomotives defined as available and rules on temporal availability.

### 1.5 Input and Output Data

**Input Data:**

* Typed data:
    * purchase request
  


* Selected data:
    * a locomotiv

**Output Data:**

* locomotives avaible
* locomotive id
* Success of the purchase


### 1.6. System Sequence Diagram (SSD)

_Insert here a SSD depicting the envisioned Actor-System interactions and throughout which data is inputted and outputted to fulfill the requirement. All interactions must be numbered._

![US09-SSD](C:\Users\andre\IdeaProjects\sem2-pi-24.25-g045-repo\Sprint2\US09\01.requirements-engineering.us09\us-Diagram\us09Diagram.svg)

### 1.7 Other Relevant Remarks

* A train is not a loocomotive ( train is a locomotive and the carriage)
* Locomotives can be of steam, diesel or electricity
* Electric Locomotives need electrified lines
