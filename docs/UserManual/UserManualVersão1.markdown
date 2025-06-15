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
| **Infrastructure**                                                                              | The essential physical structures for the railway network (stations, lines, trains).                                | | A Scrum Board column where teams record project-related notes and decisions.                                        |
| **Mail**                                                                                       | A type of cargo representing postal services delivered between cities.                                              |
| **Maintenance Route**                                                                          | A special route designed to pass once through each railway line to simulate maintenance operations.                 |
| **Map**                                                                                         | The virtual representation where the railway simulation occurs.                                                     |
| **Mixed Industry**                                                                              | An industry that can import/export any type of cargo (e.g., ports).                                                 |
| **Network Performance**                                                                         | An assessment of how efficiently the railway system operates.                                                       |
| **Node**                                                                                          | A key point in the railway network, such as a station or industry.                                                  |
| **Object Serialization**                                                                      | A technique to save and restore program data between executions.                                                    |
| **Operational Radius**                                                                             | The area of influence a station covers for supplying and receiving cargo.                                           |

---

## 2. Introduction
### Purpose & Scope
This comprehensive User Manual serves as an essential guide to help users effectively navigate and utilize the Railway Network Simulation System, a sophisticated software solution developed as part of an Integrative Project during the 2024/25 academic year at ISEP (Instituto Superior de Engenharia do Porto). Designed to emulate the strategic depth and engaging mechanics of classic management games like Railroad Tycoon, the system provides an optimized and immersive simulation experience tailored for both educational and practical applications.
Users can take on the role of a railway network manager, overseeing a dynamic and interconnected system that includes:
* **Cities** – Urban centers with evolving demands and growth patterns. 
* **Industries** – Production hubs that generate and require cargo.


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


### System Architecture
The Railway Network Simulation System is built using Java, leveraging object-oriented programming principles to create a modular and extensible architecture. Key components include:
* **Map and Scenario Editors:** For creating maps and defining simulation parameters.
* **Simulator:** A tool to run simulations, handle train operations, and track cargo.

---

## 4. System Requirements
### Hardware Requirements
*
* **Storage:** At least 500 MB of free disk space for installation and data.
* **Graphics:** Integrated graphics card (or dedicated GPU for high-performance rendering).

### Software Requirements
* **Operating System:** Windows 10 or later, macOS 10.12 or later, or Linux(Ubuntu 18.04 or later).
* **Java Runtime Environment (JRE):** Version 11 or later.
* **Python:** Version 3.8 or later (for additional scripting capabilities).


### Installation Instructions
1. Install the Java Development Kit (JDK) version 11 or later.
2. Install JavaFX SDK and configure your IDE to use it.


## 5. Features
### Map and Scenario Management
* **Map Editor:** A tool that allows users to create maps with dimensions specified by the user and add static elements like cities and industries.
* Features include:
  * Create maps with specific dimensions. 



* **Scenario Editor:** A tool to define the operational conditions of the game, including historical, technological, and temporal aspects.
* Features include: 
  * Define time restrictions, such as historical periods or events. 
   

### Station Management
* **Station Construction:** Allows users to build stations with varying capacities: Depot, Station, and Terminal
* Features: 
    * Build stations anywhere on the map. 
   
  

* **Station Upgrades:** Improve the functionality of an existing station by adding various buildings like cafes, telegraphs, and more.
* Features: 
    * Add buildings to existing stations (hotel, telegraph, warehouse, etc.). 
    
    

### Train and Locomotive Management
* **Locomotive Purchase and Management:** Allows the player to purchase locomotives based on availability and budget.
* Features: 
    * Choose locomotives from different types (steam, diesel, electric). 
   

---
## 6. Troubleshooting and Helpdesk
### Common Issues and Solutions
| **Issue #** | **Symptom**                                              | **Possible Cause**                                           | **Solution**                                                                                                                                                         |
|-------------|----------------------------------------------------------|-------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Issue 1** | Application won't start                                  | Missing JavaFX runtime components                           | Ensure that JavaFX is properly installed and configured on your system. You can refer to the installation guide for setup instructions.                             |
| **Issue 2** | Train not moving                                          | Insufficient power or track connectivity                     | Check the locomotive's power rating and ensure that tracks are properly connected. Verify that the locomotive is compatible with the type of track (electrified or not). |

### Helpdesk Contact Information
- **Email**: 
- **Phone**: 
- **Operating Hours**: 
---

## 7.Frequently Asked Questions (FAQs)
### 1. **How do I start a new game in the Railway Network Simulation System?**
- To start a new game, simply open the simulation tool, create a new map using the Map Editor, and set up a scenario with time, technological, and historical restrictions. Once the map and scenario are set, you can begin building stations, creating routes, and managing trains.

### 2. **Can I modify the map after creating it?**
- Yes, the Map Editor allows you to modify the map even after it has been created. You can add or remove cities, industries, and change the map's size, as long as it adheres to the defined map dimensions.

