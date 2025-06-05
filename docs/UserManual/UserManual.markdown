# User Manual
### Railway Network Simulation System
Academic Year 2024/2025  
**Institution:** Instituto Superior de Engenharia do Porto (ISEP)   
**Departments:** Department of Computer Engineering, Department of Organization and Management  
**Authors:** Hugo Azevedo Nº 1240835 / Edgar Couto Nº 1240868 / André Monteiro Nº 12408 / João Silva Nº 12408  
**Supervisors:** Ana Barata, Sandra Luna, Joana Lima, Cidália Neves
**Date:** May 17th, 2025
---

### *Table of Contents*
1. Glossary
2. Introduction
3. System Overview

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
### Description
The system is divided into two main tools:
* **Map and Scenario Editor:** Allows the creation of rectangular maps, placing
cities and industries, and defining simulation constraints.
* **Simulator:** Allows players to manage railway networks, including stations,
lines, trains, routes, and operational simulations.

### Functionalities
* City and industry placement
* Map creation and scenario setup
* Train acquisition and route configuration
* Simulation of train operations and cargo deliveries
* Maintenance and network analysis tools
* Statistical analysis of station performance and KPIs
