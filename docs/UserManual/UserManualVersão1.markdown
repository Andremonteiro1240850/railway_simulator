<h1 align="center" style="font-size:5em;">User Manual</h1>  

![Imagem_Capa](/docs/UserManual/Imagem_UserManual.svg)

### Railway Network Simulation System
**Academic Year 2024/2025**  
**Institution:** Instituto Superior de Engenharia do Porto (ISEP)   
**Departments:** Department of Computer Engineering (DEI)  
**Authors:** Hugo Azevedo Nº 1240835 / Edgar Couto Nº 1240868 / André Monteiro Nº 12408 / João Silva Nº 12408  
**Supervisors:** Sandra Luna   
**Date:** June 15th, 2025
---

### *Table of Contents*
1. Glossary
2. Introduction
3. System Overview
4. System Requirements
5. Features
6. Troubleshooting and Helpdesk.
7. Frequently Asked Questions (FAQs).

---

## 1. Glossary
| Term    | Definition                                                                                                          |
|---------|---------------------------------------------------------------------------------------------------------------------|
| **Acceleration** | The rate at which a train increases its speed.                                                                      |
| **Agile Development** | A software development methodology based on iterative and incremental progress.                                     |
| **Authentication** | The process of verifying user identity before granting access.                                                      |
| **Available Buildings** | A list of station upgrades that are allowed within a given scenario and their availability year.                    |
| **Backlog** | A list of pending tasks, features, or bug fixes in a Scrum project.                                                 |
| **Bakeries**  | A type of transforming industry that converts raw materials into bread and other food products.                     |
| **Board Privacy** | The access level of a Scrum Board (e.g., private, public).                                                          |
| **Budget*      | A financial constraint representing the available funds for constructing and maintaining railway infrastructure.    |
| **Building**                                                                                    | An upgrade that can be added to a station, with specific cost and functional effect.                                |
| **Business Rules**                                                                             | Constraints and guidelines that define how the system should operate.                                               |
| **Cargo/Freight**                                                                              | Goods or commodities transported by trains, such as coal, steel, or textiles.                                       |
| **Carriages/Wagons**                                                                                   | Train cars used for transporting passengers or cargo.                                                               |
| **City**                                                                                       | A location on the map containing houses and industries that generate or consume cargo.                              |
| **City Block**                                                                               | A unit representing residential or commercial buildings in a city.                                                  |
| **Collision Detection**                                                                         | A system to prevent trains from crashing (excluded from this project).                                              |
| **Connectivity Analysis**                                                                      | The process of evaluating how well the railway network is structured.                                               |
| **Cost** | The expense required to build infrastructure, purchase trains, or upgrade stations.                                 |
| **Coverage Report** | A document showing the percentage of code tested with unit tests.                                                   |
| **Customs Office** | A station upgrade that facilitates the handling of international cargo.                                             |
| **Data Persistence** | The storage of data so that it remains available between program executions.                                        |
| **Date** | Defines the start, current, and end year of a scenario.                                                             |
| **Deadline**                                                                                      | The final date by which a task must be completed.                                                                   |
| **Double Track**                                                                                | A railway line with two parallel tracks allowing bidirectional movement.                                            |
| **Editor**                                                                                      | A user who creates maps and scenarios, defining elements like industries, cities, and scenario restrictions.        |
| **FURPS+** | A model defining system quality attributes: Functionality, Usability, Reliability, Performance, and Supportability. |
| **Feature** | A specific functionality of the software.                                                                           |
| **Final Product** | A manufactured good that can be delivered to cities, such as cars or textiles.                                      |
| **Framework**                                                                                    | A structured approach or set of tools used in software development.                                                 |
| **Freight**                                                                                       | Another term for cargo transported by trains.                                                                       |
| **Game Mechanic**                                                                              | The rules and interactions within the railway simulation.                                                           |
| **GitHub Repository**                                                                           | A cloud-based storage system for managing source code and version control.                                          |
| **Graphical Representation**                                                                        | The visual depiction of maps, stations, and train routes.                                                           |
| **Historical Restriction**                                                                         | A limitation based on real-world events affecting the simulation.                                                   |
| **House Block**                                                                               | A segment of a city representing residential areas.                                                                 |
| **I/O**                                                                                          | Input/Output Operations.                                                                                            |
| **Infrastructure**                                                                              | The essential physical structures for the railway network (stations, lines, trains).                                |
| **Inhabitants**                                                                                 | People who live in a House Block.                                                                                   |
| **Input/Output Operations**                                                                   | Processes involving data entry and retrieval in the system.                                                         |
| **Inventory**                                                                                   | A dynamic record of all trains, carriages, cargo, and station upgrades owned by the player.                         |
| **Iteration**                                                                                    | A cycle in agile development where a set of features is implemented and reviewed.                                   |
| **JUnit 5**                                                                                       | A framework for writing and running unit tests in Java.                                                             |
| **JaCoCo**                                                                                        | A tool for measuring code coverage in Java applications.                                                            |
| **Java Applications**                                                                               | Software programs developed using the Java programming language.                                                    |
| **JavaDoc**                                                                                       | Documentation automatically generated from Java source code comments.                                               |
| **Kilometer Cost**                                                                          | The expense associated with constructing a railway line per kilometer.                                              |
| **Line**                                                                                        | A railway connection between two stations.                                                                          |
| **Logbook**                                                                             | A Scrum Board column where teams record project-related notes and decisions.                                        |
| **Mail**                                                                                       | A type of cargo representing postal services delivered between cities.                                              |
| **Maintenance Route**                                                                          | A special route designed to pass once through each railway line to simulate maintenance operations.                 |
| **Map**                                                                                         | The virtual representation where the railway simulation occurs.                                                     |
| **Mixed Industry**                                                                              | An industry that can import/export any type of cargo (e.g., ports).                                                 |
| **Network Performance**                                                                         | An assessment of how efficiently the railway system operates.                                                       |
| **Node**                                                                                          | A key point in the railway network, such as a station or industry.                                                  |
| **Object Serialization**                                                                      | A technique to save and restore program data between executions.                                                    |
| **Operational Radius**                                                                             | The area of influence a station covers for supplying and receiving cargo.                                           |
| **Passenger**                                                                                   | A type of cargo representing people traveling by train.                                                             |
| **Performance Metric**                                                                        | A measurement used to evaluate system efficiency.                                                                   |
| **Player**                                                                                     | A user who plays the game by creating stations, trains, and routes on a selected map/scenario.                      |
| **Port**                                                                                        | A mixed industry facility that can import/export various cargoes.                                                   |
| **Position**                                                                                   | Represents XY coordinates on the map for placing cities, industries, or stations.                                   |
| **Product Owner** | A role in Scrum responsible for defining product requirements and priorities.                                       |
| **Radius**                                                                                        | The radius determines the area around a station within which cities and industries can send or receive cargo.       |
| **Railroad Network** | A system of railway lines, stations, and trains.                                                                    |
| **Railway Connectivity**                                                                    | The extent to which different parts of the railway network are linked.                                              |
| **Route**                                                                                        | A list of stations where the train passes.                                                                          |
| **Route Validity Check**                                                                        | A check to ensure a train can travel between two stations, considering type and electrification.                    |
| **Scenario**                                                                                    | A defined context for the simulation including date, restrictions, and available resources.                         |
| **Scrum**                                                                                       | An agile framework for managing software development projects.                                                      |
| **Scrum Board**                                                                                 | A visual tool for tracking.                                                                                         |
| **Scrum Master**                                                                               | A team member responsible for facilitating the Scrum process.                                                       |
| **Serialization**                                                                               | The process of converting an object into a format that can be stored or transmitted.                                |
| **Simulator**                                                                                   | A program that imitates the behavior of a real system or process.                                                   |
| **Single Track**                                                                             | A railway line with only one track, requiring careful scheduling to avoid congestion.                               |
| **Sprint**                                                                                      | A fixed period in which a Scrum team completes a set of tasks.                                                      |
| **Station**                                                                                     | A mid-tier station where trains stop to pick up and deliver cargo.                                                  |
| **Technical Restriction**                                                                     | Limits which locomotives, buildings, or industries are available in a scenario.                                     |
| **Terminal**                                                                                    | The largest type of station with a 5x5 economic radius.                                                             |
| **Time Restriction**                                                                           | Limits the period in which the simulation can occur.                                                                |
| **Train**                                                                                     | A railway vehicle used to transport passengers or goods.                                                            |
| **Transforming Industry**                                                                     | A facility that processes raw materials into finished products (e.g., automobile factories).                        |
| **User Case Diagram**                                                                   | A UML diagram that visually represents the interactions between users and a system.                                 |
| **User Story**                                                                     | A description of a software feature from the end-user perspective.                                                  |
| **Version Register**                                                                           | A log tracking different versions of the project.                                                                   |
| **Waypoints**                                                                        | Specific locations along a train’s route where it can stop or change direction.                                     |

---

## 2. Introduction
### Purpose & Scope
This comprehensive User Manual serves as an essential guide to help users effectively navigate and utilize the Railway Network Simulation System, a sophisticated software solution developed as part of an Integrative Project during the 2024/25 academic year at ISEP (Instituto Superior de Engenharia do Porto). Designed to emulate the strategic depth and engaging mechanics of classic management games like Railroad Tycoon, the system provides an optimized and immersive simulation experience tailored for both educational and practical applications.
Users can take on the role of a railway network manager, overseeing a dynamic and interconnected system that includes:
* **Cities** – Urban centers with evolving demands and growth patterns. 
* **Industries** – Production hubs that generate and require cargo.
* **Stations** – Critical nodes for loading, unloading, and passenger transit. 
* **Trains** – Customizable fleets with varying speeds, capacities, and efficiency levels. 
* **Cargo Operations** – Logistics management involving resource distribution and supply chain optimization.

Whether for academic exploration, project development, or strategic experimentation, this manual ensures users can fully leverage the system’s capabilities to design, analyze, and refine their railway networks with precision.

### Intended Users
* **Editors** – Users who create maps and define simulation scenarios.
* **Players** – Users who simulate and manage railway operations.

---

## 3. System Overview
### Product Purpose and Functionality
With the Railway Network Simulator, users can create and oversee stations, plan train routes, and model how a railway network would function. By simulating an actual railway system, users will be able to investigate how their choices affect station performance, network connectivity, and profitability.

### Key Features
* **Map Editor:** Design maps with cities, industries, and other elements. 
* **Scenario Editor:** Set restrictions for scenarios, such as technological advancements or historical events. 
* **Station Management:** Build and upgrade stations and manage cargo flows. 
* **Train Management:** Assign locomotives to trains and define their routes. 
* **Simulation Mode:** Simulate the network's operation, monitor performance, and analyze results. 
* **Statistical Tools:** Analyze data on cargo traffic, station performance, and financial outcomes.

### System Architecture
The Railway Network Simulation System is built using Java, leveraging object-oriented programming principles to create a modular and extensible architecture. Key components include:
* **Map and Scenario Editors:** For creating maps and defining simulation parameters.
* **Simulator:** A tool to run simulations, handle train operations, and track cargo.
* **UI:** Built with JavaFX, providing an interactive platform for players to manage the system.
---

## 4. System Requirements
### Hardware Requirements
* **CPU:** Dual-core processor (Intel i3 or equivalent) or better.
* **RAM:** Minimum 4 GB (8 GB recommended).
* **Storage:** At least 500 MB of free disk space for installation and data.
* **Graphics:** Integrated graphics card (or dedicated GPU for high-performance rendering).

### Software Requirements
* **Operating System:** Windows 10 or later, macOS 10.12 or later, or Linux(Ubuntu 18.04 or later).
* **Java Runtime Environment (JRE):** Version 11 or later.
* **Python:** Version 3.8 or later (for additional scripting capabilities).
* **Libraries:** 
  * **JavaFX:** For the graphical user interface.
  * **JUnit 5:** For unit testing.
  * **JaCoCo:** For code coverage analysis.
  * **GraphStream or Graphviz:** For visualizing network topology.
  * **Python Libraries:** Matplotlib, Pandas, NumPy for statistical analysis.

### Installation Instructions
1. Install the Java Development Kit (JDK) version 11 or later.
2. Install JavaFX SDK and configure your IDE to use it.
3. Clone the GitHub repository containing the Railway Network Simulation System source code.
4. Open the project in your preferred Java IDE (e.g., IntelliJ IDEA, Eclipse).
5. Run the project to start the Railway Network Simulation System.
---

## 5. Features
### Map and Scenario Management
* **Map Editor:** A tool that allows users to create maps with dimensions specified by the user and add static elements like cities and industries.
* Features include:
  * Create maps with specific dimensions. 
  * Add cities at specific geographical locations (XY coordinates). 
  * Define industry types (Primary, Transforming, Mixed). 
  * Assign coordinates and locations for cities and industries.


* **Scenario Editor:** A tool to define the operational conditions of the game, including historical, technological, and temporal aspects.
* Features include: 
  * Define time restrictions, such as historical periods or events. 
  * Select available locomotive types and industries. 
  * Set economic or historical changes (e.g., war increases steel demand). 

### Station Management
* **Station Construction:** Allows users to build stations with varying capacities: Depot, Station, and Terminal
* Features: 
    * Build stations anywhere on the map. 
    * Automatically suggest a name for the station based on the nearest city. 
    * Define station type (Depot, Station, or Terminal) with capacity and operational radius. 
    * Example: "Build a Terminal station in Porto."
  

* **Station Upgrades:** Improve the functionality of an existing station by adding various buildings like cafes, telegraphs, and more.
* Features: 
    * Add buildings to existing stations (hotel, telegraph, warehouse, etc.). 
    * Display available cargo and the types of cargo that need to be delivered
    * Example: "Add a telegraph to Lisbon station to improve operations."


### Train and Locomotive Management
* **Locomotive Purchase and Management:** Allows the player to purchase locomotives based on availability and budget.
* Features: 
    * Choose locomotives from different types (steam, diesel, electric). 
    * Assign locomotives to trains and define their routes.

---
## 6. Troubleshooting and Helpdesk
### Common Issues and Solutions
| **Issue #** | **Symptom**                                              | **Possible Cause**                                           | **Solution**                                                                                                                                                         |
|-------------|----------------------------------------------------------|-------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Issue 1** | Application won't start                                  | Missing JavaFX runtime components                           | Ensure that JavaFX is properly installed and configured on your system. You can refer to the installation guide for setup instructions.                             |
| **Issue 2** | Train not moving                                          | Insufficient power or track connectivity                     | Check the locomotive's power rating and ensure that tracks are properly connected. Verify that the locomotive is compatible with the type of track (electrified or not). |
| **Issue 3** | Map not saving                                            | Invalid map name                                            | Make sure the map name does not contain special characters or spaces. Use valid filenames.                                                                            |
| **Issue 4** | Simulation freezing or lagging                           | System resources are insufficient or simulation complexity   | Close unnecessary applications to free up system resources. Consider reducing the number of active trains or stations in the simulation to improve performance.   |
| **Issue 5** | Error when trying to load a saved game                   | File corruption or incompatible version                     | Check if the game was saved in the correct version. If the file is corrupted, try loading an earlier save or restart the simulator.                                  |
| **Issue 6** | Unable to build a station                                | Invalid station location or overlapping elements             | Ensure the location is valid and that no other stations or structures are overlapping. You cannot build on occupied spaces.                                         |

### Helpdesk Contact Information
- **Email**: support@railwaysimulator.com
- **Phone**: 123-456-7890
- **Operating Hours**: Monday - Friday, 9:00h - 17:00h
---

## 7.Frequently Asked Questions (FAQs)
### 1. **How do I start a new game in the Railway Network Simulation System?**
- To start a new game, simply open the simulation tool, create a new map using the Map Editor, and set up a scenario with time, technological, and historical restrictions. Once the map and scenario are set, you can begin building stations, creating routes, and managing trains.

### 2. **Can I modify the map after creating it?**
- Yes, the Map Editor allows you to modify the map even after it has been created. You can add or remove cities, industries, and change the map's size, as long as it adheres to the defined map dimensions.

### 3. **What types of stations can I build?**
- You can build three types of stations: Depots (small capacity), Stations (medium capacity), and Terminals (large capacity). Each station type has a different operational radius and capacity for cargo handling.

### 4. **How do I purchase and assign locomotives to trains?**
- You can purchase locomotives from the available list based on the scenario. After purchasing, go to the Train Management section, select a locomotive, and assign it to a train. Define the route that the train will follow using the available stations.

### 5. **How does the simulation work?**
- The simulation runs in real-time or can be paused. Once started, the trains will begin their routes, loading and unloading cargo at each station. The simulator calculates train arrivals, cargo deliveries, and updates station demand and supply. The simulation can be started, paused, or reset.

### 6. **What happens if a train doesn't have enough fuel or power?**
- If a train runs out of fuel or power, it will not move until it is resupplied. Check the locomotive's fuel and power levels, and ensure the tracks are compatible with the locomotive (electrified or not).

### 7. **Can I play with others?**
- Currently, the system supports single-player mode, where you manage the railway network alone. Multiplayer capabilities may be added in future versions.
