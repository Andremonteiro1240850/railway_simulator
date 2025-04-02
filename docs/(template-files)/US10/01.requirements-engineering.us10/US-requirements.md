# USXXX - YYY

_XXX stands for User Story number and YYY for User Story description (e.g. US006 - Create a Task)_

## 1. Requirements Engineering

_In this section, it is suggested to capture the requirement description and specifications as provided by the client as well as any further clarification on it. It is also suggested to capture the requirements acceptance criteria and existing dependencies to other requirements. Finally, identify the involved input and output data and depict an Actor-System interaction in order to fulfill the requirement._

### 1.1. User Story Description

As a Player, I want to assign a selected train to a route with
valid stations and the respective list of cargoes to be picked up in each
station


### 1.2. Customer Specifications and Clarifications 

stations has to be valid 

### 1.3. Acceptance Criteria

unspecified

### 1.4. Found out Dependencies

us05(station)

### 1.5 Input and Output Data

Input: train, rout, cargoe
Output. confirm stations, check route, confirm route, confirm cargo

### 1.6. System Sequence Diagram (SSD)

_Insert here a SSD depicting the envisioned Actor-System interactions and throughout which data is inputted and outputted to fulfill the requirement. All interactions must be numbered._

![US10-SSD](us10Diagram.svg)

### 1.7 Other Relevant Remarks

Stations should be valid to move the train to there route
