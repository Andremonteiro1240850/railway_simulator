# US10 - As a Player, I want to assign a selected train to a route withvalid stations and the respective list of cargoes to be picked up in eachstation



## 1. Requirements Engineering

_In this section, it is suggested to capture the requirement description and specifications as provided by the client as well as any further clarification on it. It is also suggested to capture the requirements acceptance criteria and existing dependencies to other requirements. Finally, identify the involved input and output data and depict an Actor-System interaction in order to fulfill the requirement._

### 1.1. User Story Description

As a Player, I want to assign a selected train to a route with
valid stations and the respective list of cargoes to be picked up in each
station


### 1.2. Customer Specifications and Clarifications 
If there is no path between any pair of consective points of the route, the player should be warned, then the player can opt between buy or not a line. **
Multiple cargo types can be assigned to the same train.
A route don´t have a limit of stations that can be assigned.
### 1.3. Acceptance Criteria

unspecified

### 1.4. Found out Dependencies     

US05 – Building stations: to create a route with valid stations, those stations must already exist on the map.

US08 – Building a rail line between stations: there must be  connections between stations for the train to travel along the route.

US09 – Purchasing a locomotive: before assigning a train to a route, the train (locomotive + cargo) must have been purchased.

### 1.5 Input and Output Data

Input: train, rout, cargoe
Output. confirm stations, check route, confirm route, confirm cargo

### 1.6. System Sequence Diagram (SSD)

_Insert here a SSD depicting the envisioned Actor-System interactions and throughout which data is inputted and outputted to fulfill the requirement. All interactions must be numbered._

![US10-SSD](C:\Users\andre\IdeaProjects\sem2-pi-24.25-g045-repo\Sprint1\US10\01.requirements-engineering.us10\us-Diagram\us10Diagram.svg)

### 1.7 Other Relevant Remarks

Stations should be valid to move the train to there route
