# OO Analysis

The construction process of the domain model is based on the client specifications, especially the nouns (for _concepts_) and verbs (for _relations_) used. 

## Rationale to identify domain conceptual classes
To identify domain conceptual classes, start by making a list of candidate conceptual classes inspired by the list of categories suggested in the book "Applying UML and Patterns: An Introduction to Object-Oriented Analysis and Design and Iterative Development". 


### _Conceptual Class Category List_

**Business Transactions**  
RouteAssignment   
CargoTransaction   
StationUpgrade   
TrainPurchase 

___

**Transaction Line Items**  
CargoLoadItem

---

**Product/Service related to a Transaction or Transaction Line Item**  
Cargo (passengers, mail, coal, iron ore, steel, cars, etc.)  
Railway Service (train service between stations)
---

**Transaction Records**  
MaintenanceReport  
SimulationLog

---  

**Roles of People or Organizations**

Player  
Editor

---

**Places**  
Map  
City   
Industry  
Station  
Port 

---

**Noteworthy Events**  
Shipment  
HistoricalEvent

---

**Physical Objects**  
Locomotive   
Carriage   
Train   
RailwayLine  
Building 

---

**Descriptions of Things**  
Scenario (defines time, technological, historical restrictions)  
LocomotiveSpecs (power, acceleration, fuel cost, etc.)  
IndustrySpecs (resource generation rates, transformations)

---

**Catalogs**  
LocomotiveCatalog   
IndustryCatalog   
CargoTypeCatalog

---

**Containers**  
RailwayNetwork (collection of stations and lines)  
TrainFleet (collection of available trains)

---

**Elements of Containers**  
StationList → contains instances of Station  
TrainFleet → contains Train, each composed of Locomotive and Carriage  
CargoManifest → list of cargo types loaded on a train

---

**(Other) Organizations**  
RailwayCompany  
IndustryOperator

---

**Other (External/Collaborating) Systems**  
ScenarioLoader (used to import predefined scenarios)  
DataExporter (CSV output or save/load scenarios)

---

**Records of finance, work, contracts, legal matters**  
ProfitReport   
RevenueLog   
SimulationEventLog 

---

**Financial Instruments**  
Budget   
CostEstimation 
---

**Documents mentioned/used to perform some work**  
PurchaseRecord   
MaintenanceRequest   
UpgradeRequest 

---


## Rationale to identify associations between conceptual classes

An association is a relationship between instances of objects that indicates a relevant connection and that is worth of remembering, or it is derivable from the List of Common Associations: 

* **_A_** is physically or logically part of **_B_**
* **_A_** is physically or logically contained in/on **_B_**
* **_A_** is a description for **_B_**
* **_A_** known/logged/recorded/reported/captured in **_B_**
* **_A_** uses or manages or owns **_B_**
* **_A_** is related with a transaction (item) of **_B_**
* etc.


| Concept (A) 		  | Association   	 |          Concept (B) |
|-----------------|:---------------:|---------------------:|
| Player  	       |     Creates     |        Map, Scenario |  
| Editor	         |     Defines     |             Scenario |
| Player          |     Builds      | Station, RailwayLine |
| Player          |    Purchases    |           Locomotive |
| Train           |    Contains     | Locomotive, Carriage |
| RouteAssignment |     Assigns     |       Train to Route |
| Station         |     Serves      |       City, Industry |
| RailwayLine     |    Connects     |              Station |
| Train           |     Follow      |                Route |
| Scenario        |     Defines     |      HistoricalEvent |
| Scenario        |    Restricts    |           Locomotive |
| Station         |     stores      |                Cargo |
| StationUpgrades |    upgrades     |              Station |
|                 |                 |                      |
|                 |                 |                      |
|                 |                 |                      |
|                 |                 |                      |
|                 |                 |                      |
|                 |                 |                      |
|                 |                 |                      |
|                 |                 |                      |
|                 |                 |                      |
|                 |                 |                      |



## Domain Model

**Do NOT forget to identify concept atributes too.**

**Insert the Domain Model diagram in SVG format below.**

![Domain Model](svg/DM.svg)