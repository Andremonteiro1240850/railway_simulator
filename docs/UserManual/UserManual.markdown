# User Manual 
#### Railway Network Simulation System <BR> Academic Year 2024/2025 <BR> 
#### **Institution:** Instituto Superior de Engenharia do Porto (ISEP)
#### **Departments:** Department of Computer Engineering, Department of Organization and Management
#### **Authors:** Hugo Azevedo Nº 1240835 / Edgar Couto Nº 1240868 / André Monteiro Nº 12408 / João Silva Nº 12408
#### **Supervisors:** Ana Barata, Sandra Luna, Joana Lima, Cidália Neves
#### **Date:** May 17th, 2025


### *Table of Contents*
1. Glossary
2. Introduction
3. System Overview
4. System Requirements
5. Features
6. Troubleshooting & Helpdesk
7. Frequently Asked Questions (FAQs)
-----------------------
### 1. Glossary
| Term     | Definition |
|:---------|:-----------|
| MVP      | Minimum Viable Product – core version of the software |
| Map      | Rectangular area where simulation elements (cities, industries) are placed |
| Station  | A train stop point (Depot, Station, or Terminal) |
| Route    | A path a train follows through stations |
| KPI      | Key Performance Indicator |
| Scenario | A defined simulation configuration including time, restrictions, etc. |
| TDD      | Test-Driven Development – approach for coding with pre-defined tests |
| SCRUM    | Agile framework used to manage team development |

---
### 2. Introduction
**Purpose & Scope**  
This User Manual is designed to help users navigate the Railway Network Simulation System, which was developed as part of an Integrative Project for the 2024/25 academic year at ISEP. The system offers a optimized simulation experience inspired by Railroad Tycoon, allowing users to oversee various aspects of railway management, including cities, industries, stations, trains, and cargo operations.
   
**Intended Users**
* Editors – Users who create maps and define simulation scenarios.
* Players – Users who simulate and manage railway operations.

---
### 3. System Overview
**Description**  
The system is divided into two main tools:
* Map and Scenario Editor: Allows the creation of rectangular maps, placing cities and industries, and defining simulation constraints.
* Simulator: Allows players to manage railway networks, including stations, lines, trains, routes, and operational simulations.
   
**Functionalities** 
* City and industry placement
* Map creation and scenario setup
* Train acquisition and route configuration
* Simulation of train operations and cargo deliveries 
* Maintenance and network analysis tools 
* Statistical analysis of station performance and KPIs

---
### 4. System Requirements
**Software**
* Java (SE 17 or higher)
* Python (for KPI analysis, with Jupyter Notebook)
* JDK: Required for compilation 
* JUnit 5: For unit testing 
* JaCoCo Plugin: For code coverage reports
 
**Hardware**
* OS: Windows 10+, macOS 12+, or Linux 
* RAM: 8 GB minimum 
* Storage: 1 GB available disk space 
* Processor: Dual-core 2.0 GHz minimum
   
**Dependencies**
* Java libraries for UI and file handling 
* GraphStream/Graphviz for visualization 
* CSV reading tools for data analytics (Python)
---

### 5. Features
**Map Creation (Editor)**  
* Function: Define a new map (name and dimensions). 
* Steps:  
  * 1.Access “Create Map”. 
  * 2.Enter name and size. 
  * 3.Confirm creation. 

**Add Cities & Industries**
* Function: Add city or industry to the map. 
* Steps:
  * 1.Choose type (City/Industry). 
  * 2.Input location (X, Y) and attributes. 
  * 3.Place element on map.

**Scenario Setup**  
* Function: Configure simulation rules. 
* Steps:
  * 1.Select map. 
  * 2.Define technological and time constraints. 
  * 3.Save scenario.

**Station Management**
* Function: Build, upgrade, and list stations.
* Types:
    * Depot 
    * Station 
    * Terminal
* Steps:
  * 1.Choose station type. 
  * 2.Place it near a city or industry. 
  * 3.Upgrade with buildings (e.g., telegraph, hotel).

**Railway Construction**
* Function: Connect stations. 
* Steps:
  * 1.Select stations. 
  * 2.Define track type (single/double, electrified). 
  * 3.Build line.
  
**Train Management**  
* Function: Buy and assign locomotives. 
* Steps:
  * 1.Choose from available locomotives. 
  * 2.Set route and cargos.
  * 3.Monitor operations. 

**Simulation Mode**
* Function: Run simulation (play/pause). 
* Output: Shows journey, cargo delivery, station updates.

**Connectivity & Maintenance Check**
* Tools:
  * Route feasibility analysis for each locomotive type. 
  * Maintenance planner (Eulerian path approach). 
  * Network visualizations. 

**Statistical Analysis (Python)**   
* KPIs:
  * Profitability per station (mean, median, mode, std. dev). 
  * Boxplots of passenger data. 
  * Histograms for cargo. 
  * Pie charts for train, mail, and passenger distribution.

---
### 6. Troubleshooting & Helpdesk

| Issue ID | Symptom                    | Cause                                  | Solution                               |
|----------|----------------------------|----------------------------------------|----------------------------------------|
| #001     | Application fails to start | Missing Java installation              | Reinstall Java JDK                     |
| #002     | Map elements not saving    | Permission error                       | Run application with elevated rights   |
| #003     | Train route not working    | Unconnected stations or invalid route | Re-check route and station connections |
| #004     | KPI report errors          | Missing CSV data or Python modules     | Ensure data file and dependencies exist|

**Helpdesk Contact:**   
Email: support@isep.ipp.pt   
Office Hours: Mon–Fri, 9:00–17:00

---
### 7. Frequently Asked Questions (FAQs)
> *Q1:* Can I run the simulator on macOS?   
> *A1:* Yes, the application supports macOS 12+ with Java installed.
   
> *Q2:* Is multiplayer supported?   
> *A2:* Not currently. This version only supports single-user simulation.
   
> *Q3:* Do I need internet to run the simulation?   
> *A3:* No, the application runs offline.
   
> *Q4:* How are industries defined in a scenario?   
> *A4:** By selecting available industries during scenario configuration.
   
> *Q5:* Where are the saved scenarios stored?   
> *A5:* In the local file directory under /scenarios/.