# USXXX - YYY

US09 - As a Player, I want to buy a locomotive

## 1. Requirements Engineering

_In this section, it is suggested to capture the requirement description and specifications as provided by the client as well as any further clarification on it. It is also suggested to capture the requirements acceptance criteria and existing dependencies to other requirements. Finally, identify the involved input and output data and depict an Actor-System interaction in order to fulfill the requirement._

### 1.1. User Story Description

As a Player, I want to buy a locomotive

### 1.2. Customer Specifications and Clarifications 

A player can buy the same type of locomotive, but these locomotives  have unique identification (like serial number).
A player can hava an unlimited number of locomotives, just need to have available memory.



### 1.3. Acceptance Criteria

The player should choose the locomotive from a list of available
locomotives for the scenario as well as a current date.

### 1.4. Found out Dependencies

The implementation of this US is only possible if there is already a scenario created with locomotives defined as available and rules on temporal availability.
Therefore, it depends on US 04.

### 1.5 Input and Output Data

Input: player purchase request, selct a locomotiva, purchase confirmation
Output: locomotives avaible,select locomotive information, locomotive id, purchase confirmation message

### 1.6. System Sequence Diagram (SSD)

_Insert here a SSD depicting the envisioned Actor-System interactions and throughout which data is inputted and outputted to fulfill the requirement. All interactions must be numbered._

![US09-SSD](us09Diagram.svg)

### 1.7 Other Relevant Remarks

a train is not a loocomotive ( train is a locomotive and the carriage)
locomotives can be of steam, diesel or electricity
Electric Locomotives need electrified lines
