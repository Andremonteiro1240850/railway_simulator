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
6. Troubleshooting and Helpdesk
7. Frequently Asked Questions (FAQs)

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
This User Manual is the gateway to properly navigate through and utilize the Railway Network Simulation System. This sophisticated software solution, developed in an Integrative Project during the 2024/25 academic year at ISEP (Instituto Superior de Engenharia do Porto), is designed to provide a better and more immersive simulation experience of railway network operation. Modeled on the strategic depth and addictiveness of classic management games like Railroad Tycoon, the system is designed for application in both educational and real-world environments.  
The system allows the user to take on the role of an administrator of a railway network, overseeing a dynamic, interconnected world that includes:  

**Cities** – Metropolises with evolving demands and growth profiles, producing and consuming passengers, mail, and finished goods.  

**Industries** – Production hubs that generate primary resources or transform them into others, also managing cargo demands.  

**Stations** – Major hubs for loading, unloading, and transit of cargo (both goods and passengers), with upgradability.  

**Trains** – Customizable fleets consisting of locomotives and carriages, with diverse speeds, capacities, and levels of efficiency.  

**Cargo Operations** – Logistics management comprising distribution of resources and supply chain optimization along user-defined routes.

This manual allows users to harness the system's capabilities to model, analyze, and optimize their railway networks with accuracy, be it for academic research, project viability, or strategic experimentation.

## Target Users
This manual addresses two primary user roles for the system:  

**Editors** – Users who create maps and define simulation scenarios.  

**Players** – Users who play and simulate railway operations, buying trains or stations and defining routes.

---

### 3. System Overview  
   ### 3.1 Purpose and Intended Use  
   The Railway Network Simulation System is meant to be an easy-to-use tool for anyone curious about how railway networks work. It tries not to be too complicated and breaks down the operation into simpler parts. What you can do with it includes:

- Making your own maps by placing cities, industries, and creating different scenarios, even some that follow historical rules.
- Managing trains, stations, cargo, and railway lines, and tweaking them as the simulation goes on.
- Checking out statistics and reports to see how well your network performs and where things might be improved.

This system is mostly made for learning, testing projects, or just playing around with ideas. It borrows a bit from old railway management games to keep things fun and interesting.

### 3.2 Structure and Functionalities  
The system has two main parts that work together:

**Map and Scenario Editor**  
This is where you set up everything before the simulation starts. You can:

- Make maps using XY coordinates.
- Add cities, decide where they are, how big they are, and what kind of cargo they create or need.
- Set up industries and choose what they produce or use.
- Create scenarios with different time settings, technology limits, and port rules for imports and exports.

**Simulation Tool**  
This is where the action happens. You can:

- Build and upgrade stations, choosing from different types and sizes.
- Lay down railway lines, picking single or double tracks, and adding electrification if you want.
- Buy trains and put together their locomotives and carriages.
- Plan routes, decide where cargo is picked up, and set loading options.
- Start and pause the simulation, watching trains move and events take place.
- Look at stats about traffic, cargo, passengers, and money.
- Check if the network is well connected and find the best routes.
- Save your progress and load it later.

### 3.3 System Modules and Architecture  
The system is made with Java and follows object-oriented programming. The development used an agile SCRUM method. Because it’s modular, it’s easy to add new things or fix problems without changing everything.

Here are the main parts:

- **Core Logic / Business Layer** — This is where the main rules live, like how trains move and how paths are found. It uses simple operations and doesn’t rely on built-in Java libraries for important algorithms.
- **Editor Module** — Manages creating maps and scenarios.
- **Simulation Module** — Runs the simulation, handling trains, cargo, and the economy.
- **Data Persistence Module** — Saves and loads your maps, scenarios, and ongoing games.
- **Statistical Analysis Module (Python)** — A separate tool using Python and Jupyter Notebook to analyze data and create reports.
- **Graphical User Interface (GUI)** — Built with JavaFX 11, this is what you use to interact with the system.
- **Utility and Algorithm Module** — Contains special graph algorithms for checking routes, maintenance, and shortest paths. It’s well documented and explains its complexity.

### 3.4 Structural Overview  
To understand how everything fits together, it helps to use a diagram, like a UML Component or Package Diagram. This would show:

- How the GUI talks to both the Editor and Simulator.
- How the Editor and Simulator depend on the Core Logic and Data Persistence modules.
- How the Statistical Analysis module works separately but gets data from the simulation.

This kind of picture makes it easier to see how the system works and how the parts connect.

---
## 4. System Requirements

Please make sure your environment satisfies the following requirements in order to guarantee the Railway Network Simulation System operates at its best.


### Hardware Requirements

The underlying hardware has an impact on the simulation system's performance. For a seamless user experience, we advise the following specifications:


- **Processor (CPU):**  
  The bare minimum is a dual-core processor (such as an AMD Ryzen 3, Intel Core i3, or comparable).  
  A quad-core processor (Intel Core i5, AMD Ryzen 5, or equivalent) or higher is strongly advised for improved performance, particularly during intricate simulations or map editing.

- **Memory (RAM):**  
  For basic operation, at least 4 GB of RAM is needed.  
  8 GB or more of RAM is highly recommended for optimal performance, especially when handling larger maps or longer simulation times.

- **Storage:**  
  The application's installation files, generated simulation data, and saved game states require at least 500 MB of free disc space.  
  For quicker system responsiveness and load times, an SSD (Solid State Drive) is advised.

- **Graphics:**  
  For typical operation, an integrated graphics card is adequate.  
  A dedicated GPU can make any possible high-performance rendering or intricate UI animations run more smoothly, even though it is not strictly necessary for core functionality.

- **Display:**  
  To ensure that the graphical user interface is displayed correctly, a minimum screen resolution of 1280x720 pixels is advised.


### Software Requirements

For the system to work properly, certain software environments and libraries are required.


- **System of operation:**
  - Windows 10 or higher
  - macOS 10.12 (Sierra) or higher
  - Linux (Ubuntu 18.04 LTS or later, or any other contemporary distribution that supports JavaFX 11)

- **Java Runtime Environment (JRE):**  
  Version 11 or higher. This is important because the application uses JavaFX 11 and was created in Java 11.

- **Python:**  
  At least version 3.8. This is necessary for the Python-based external Statistical Analysis Module, which might use Jupyter Notebooks.

- **Development Environment:**
  - **Java Development Kit (JDK):** Version 11 or later (corresponding to the JRE version)
  - **Integrated Development Environment (IDE):** Any contemporary Java IDE, including Apache NetBeans, Eclipse IDE, and IntelliJ IDEA (Community or Ultimate Edition)

- **Frameworks and Libraries Needed:**
  - **JavaFX SDK:** The Graphical User Interface depends on the JavaFX SDK. Your IDE needs to be set up correctly with the JavaFX SDK 11 (or a compatible later version).
  - **JUnit 5:** Used to execute the project's unit tests.
  - **JaCoCo:** A plug-in code coverage tool for creating coverage reports while a project is being developed.
  - **GraphStream and Graphviz:**  
    These are frequently used for debugging or for visualising network topology during development, even though they are not specifically listed as a runtime dependency in the project description for the finished application.  
    The underlying libraries (such as graphviz-java or an internal implementation) must be accessible if the application has built-in visualisation.  
    *Self-correction:* Based on "Implement algorithms using only primitive operations," it is unlikely that these dependencies will be present in the final product at runtime; however, they are worth mentioning for development/analysis if applicable.
  - **Python Libraries:**
    - Matplotlib (for plotting and visualisation)
    - Pandas (for data manipulation)
    - NumPy (for numerical operations)  
      These are necessary for the Statistical Analysis Module. You should have these installed in your Python environment.


### 4.3 Installation Steps

Please take the following actions to install and launch the Railway Network Simulation System from its source code:


1. **Set up the Java Development Kit (JDK):**
  - Use an OpenJDK distribution (like Adoptium Temurin) or the official Oracle website to download and install JDK version 11 or later.
  - Make sure the environment variables for your system have `JAVA_HOME` set correctly.


2. **Install the JavaFX SDK:**
  - Go to the OpenJFX website and download JavaFX SDK version 11 (or a compatible later version).
  - Save the SDK to a convenient location on your computer by unzipping it.
  - Set up the JavaFX SDK as a library or module dependency for the project in the Java IDE of your choice (IntelliJ IDEA, Eclipse, or NetBeans).  
    Online tutorials for JavaFX setup or the documentation for your IDE contain detailed instructions for IDE configuration.


3. **Install Python and Necessary Libraries:**
  - Go to [python.org](https://www.python.org) and download and install Python 3.8 or later.
  - Launch a command prompt or terminal and use pip to install the required Python libraries:

    ```bash
    pip install matplotlib pandas numpy
    ```

  - **Optional but advised:** If you intend to use the Statistical Analysis Module interactively, install Jupyter Notebook:

    ```bash
    pip install notebook
    ```


4. **Clone the Project Repository:**  
   The Railway Network Simulation System source code can be cloned from its GitHub repository using Git:

   ```bash
   git clone [URL_DO_REPOSITORIO_GITHUB_AQUI]

---

## 5. Features

The main features of the Railway Network Simulation System are described in this section, along with how you, as a user, can interact with its different parts.  
Every feature is explained, along with its main functions and the user role or roles that are involved.  
To improve clarity, we recommend using screenshots where appropriate.


### 5.1.1 Map Editor
**Users:** Editors

- **Goal:**  
  With the help of this strong tool, you can create the game's geographic layout and lay the groundwork for your railway network.

- **Main Features:**
  - **Define Map Dimensions:** You can specify the exact dimensions of your newly created map.
  - **Place Cities:** On your map, add cities at particular XY coordinates that will serve as hubs for the demand for passengers and cargo.
  - **Create Industries:** To produce resources and goods throughout your map, introduce different industry types (Primary, Transforming, and Mixed).
  - **Assign Locations:** Using their geographic coordinates, precisely place cities and industries.



### 5.1.2 Scenario Editor
**Users:** Editors

- **Goal:**  
  After your map is ready, you can add layers of difficulty and realism to your simulation by defining the precise operational conditions and historical background using the Scenario Editor.

- **Key Features:**
  - **Set Time Restrictions:** Choose the historical era or particular occasions that will affect the timeline of your game.
  - **Manage Available Industries and Locomotives:** Decide which industries and locomotive types will be operational or accessible in this specific situation.
  - **Introduce Economic/Historical Changes:** Bring about dynamic changes, like a war influencing the demand for steel or a technological advancement influencing the effectiveness of trains.


### 5.2 Administration of the Station

The core of your railway network are stations, which act as essential hubs for all of your activities.


#### 5.2.1 Building a Station
**Users:** Players

- **Goal:**  
  By strategically placing and constructing various station types across your map, you can adjust to the demands of your network.

- **Essential Skills & Guidelines:**
  - **Establish Stations Anywhere:** You are free to establish stations at any legitimate spot on the map.
  - **Smart Naming Suggestion:** To speed up setup, the system will automatically recommend a name for your new station based on the closest city.
  - **Select the Type of Station:** Choose from three different kinds:
    - **Depot:** Low capacity, perfect for train maintenance or small operations.
    - **Station:** Medium capacity, suitable for both general passenger and cargo traffic.
    - **Terminal:** Large capacity, intended for large hubs and extensive operations.
  - **Define Operational Radius:** The range and throughput of each station type are influenced by its operational radius and capacity.

- **Step-by-step Example — Constructing a Porto Terminal Station:**
  1. **Choose the "Build Station" Tool:** Click the "Build Station" icon after navigating to the Simulator UI's construction panel.
  2. **Select the "Terminal" Type:** Choose "Terminal" from the list of station types.
  3. **Click on the Map:** Click on the preferred spot close to Porto by moving your cursor over the map. The name "Porto Terminal" or something similar will then be automatically suggested by the system.
  4. **Verify Placement:** Examine the recommended information and validate the station's design.

#### 5.2.2 Improvements to the Station
**Users:** Players

- **Goal:**  
  By adding specialised buildings, you can increase the functionality and effectiveness of your current stations.

- **Key Capabilities:**
  - **Add Variety of Buildings:** Upgrade your stations by including buildings such as:
    - Warehouses (for cargo storage)
    - Telegraphs (for operational enhancements)
    - Hotels (for passengers)
  - **Track Cargo Flows:**  
    The station interface will show the kinds of cargo that must be delivered from that location as well as the available cargo that is ready for pickup.

- **Detailed Example — Equipping Lisbon Station with a Telegraph:**
  1. **Choose Lisbon Station:** On your map, click the "Lisbon Station" icon or representation.
  2. **Access the Upgrade Menu:** Find and select the "Upgrade" or "Add Building" button in the station's detail panel.
  3. **Pick "Telegraph":** From the list of buildings that are available, pick "Telegraph."
  4. **Verify Upgrade:** Verify your selection and note how the station's operational statistics have improved.


### 5.3 Management of Trains and Locomotives

Because they guarantee the movement of both passengers and goods, your trains are the backbone of your network.


#### 5.3.1 Purchasing and Managing Locomotives
**Users:** Players

- **Goal:**  
  By acquiring locomotives, which are necessary for assembling trains and transporting goods, this feature enables you to grow your fleet.

- **Essential Skills:**
  - **Choose from a Variety of Types:**  
    Pick locomotives with different performance traits from different fuel types and technological eras, such as electric, diesel, and steam models.
  - **Assign to Trains and Define Routes:**  
    After purchasing these locomotives, you can meticulously define their routes throughout your railway network and assign them to new trains.

---
## 6. Helpdesk and Troubleshooting

Even the most resilient systems occasionally run into a few hiccups!  
This section is intended to assist you in promptly recognising and resolving typical problems that may arise when utilising the Railway Network Simulation System.

Additionally, we've provided our support team's clear contact details in case you need assistance.


### 6.1 Typical Problems and Fixes

This is a brief guide to some common problems and how to solve them.  
If you encounter any difficulties, we advise you to look at this table first.

| **Problem** | **Description** | **Solution** |
|-------------|------------------|--------------|
| **Problem 1** | When the application opens, it either doesn't launch or crashes right away. | JavaFX components or JRE are out-of-date, missing, or misconfigured. Ensure JRE version 11+ is installed and JavaFX SDK 11 is correctly configured. Try restarting your system. |
| **Problem 2** | A train stays in one place even after being given a route. | May be caused by wrong track type, broken track, or underpowered locomotive. Check train weight vs. locomotive power. Ensure correct track type and complete connectivity from origin to destination. |
| **Problem 3** | Error occurs when saving a newly created or edited map. | May be due to write permission issues, overly long file name, or invalid characters. Use only alphanumeric characters and save in a permitted directory. |
| **Problem 4** | Simulation freezes, slows down, or becomes unresponsive. | Simulation may be too complex or system has insufficient RAM/CPU. Close other applications or simplify the simulation. Consider upgrading RAM. |
| **Problem 5** | Error appears when trying to load a saved game. | Save file may be incompatible, corrupted, deleted, or moved. Confirm simulator version compatibility. Try another save file or start a new game. |
| **Problem 6** | Error when placing a new station on the map. | Chosen location may be invalid (e.g., out of bounds or over existing structure). Ensure coordinates are valid and area is clear. |
| **Problem 7** | Unable to log in or receive “Incorrect Credentials” error. | Username or password may be incorrect or incorrectly formatted. Passwords must have at least 3 capital letters, 2 digits, and 7 alphanumeric characters. Check credentials or contact support. |


### 6.2 Support and Help Desk Services

Our committed support staff is available to assist you if you run into a problem that isn't covered above or if the solutions offered don't work.  
Do not hesitate to contact us using the information provided below.

- **Email address:** support@railwaysimulator.com

- **Telephone:** 123-456-7890  
  *(Note: Regular call fees might be applicable.)*

- **Hours of operation:**  
  9:00 AM to 5:00 PM, Monday through Friday  
  *(WEST – Western European Summer Time)*

---

## 7. Frequently Asked Questions (FAQs)

The most frequent queries you may have regarding the Railway Network Simulation System are intended to be answered in this section.  
We've made an effort to keep the responses brief, straightforward, and useful so you can resume constructing your railway empire!

---

**Q.1: How can I launch a new Railway Network Simulation System game?**  
**A.1:** Just open the app! The geography of your network will then be designed using the Map Editor, and the operational conditions and difficulties will be specified using the Scenario Editor. You can start building stations, creating routes, and controlling your trains as soon as your map and scenario are complete.

---

**Q.2: After creating a map, is it possible to make changes to it?**  
**A.2:** Definitely! You can alter your map at any time using the Map Editor. As long as the modifications stay inside the designated borders, you are free to add or remove cities and industries as well as change the map's overall size.

---

**Q.3: What kinds of stations can I construct?**  
**A.3:** You have access to three different types of stations: Terminals (large capacity, built for major hubs), Depots (small capacity, perfect for basic operations or maintenance), and Stations (medium capacity, for general traffic). Each type affects the station's capacity to handle cargo and its operational radius.

---

**Q.4: How can I buy locomotives and put them on trains?**  
**A.4:** Within the constraints of your scenario, locomotives can be bought from the available selection. After obtaining it, proceed to the Train Management area. Here, you can use the stations in your network to precisely define the routes that your newly purchased locomotives will take as they form trains.

---

**Q.5: What is the real mechanism of the simulation?**  
**A.5:** You can pause the simulation at any time, but it usually runs in real-time. Trains will start travelling along their designated routes as soon as they are started, actively loading and unloading passengers and goods at each station. The simulator dynamically updates station demand and supply, handles deliveries, and continuously computes train arrivals. The simulation can be started, paused, or reset at any time by you.

---

**Q.6: What occurs if a train's power or fuel runs out?**  
**A.6:** Until it is restocked, a train that is running low on power or fuel will just stop. Make sure you keep an eye on the power and fuel levels of your locomotive. Additionally, confirm that the track type is appropriate for your locomotive (for example, electrified tracks are necessary for electric trains).

---

**Q.7: Is it possible to play multiplayer with friends?**  
**A.7:** At the moment, the system only allows single-player mode, which allows you to control your railway network on your own. Although this version does not support multiplayer, future updates may take that into account.

---

**Q.8: How can I register for an account or access the application?**  
**A.8:** You will be asked to log in when you first open the application. You can make an account right from the login screen if you don't already have one. Keep in mind that your password needs to contain at least three capital letters, two digits, and seven alphanumeric characters.

---

**Q.9: How can I load a saved game or save my game progress?**  
**A.9:** The in-game menu allows you to save your current simulation progress at any moment. Choose the "Load Game" option from the main menu or the simulator interface, then pick the save file you want to use to load a previously saved game.

---

**Q.10: What is the game's primary objective?**  
**A.10:** The main goal is to strategically manage and expand your railway network while maximising passenger and freight transportation to attain efficiency and profitability. Your ability to satisfy demands, grow your infrastructure, and optimise revenue within the limitations of the selected scenario will be used to gauge your success.

---

**Q.11: Is it possible to conduct sophisticated data analysis on my simulation?**  
**A.11:** Indeed! An external Python-developed Statistical Analysis Module is part of the system. With the help of programs like Matplotlib, Pandas, and NumPy, you can export simulation data and conduct in-depth analysis to gain a thorough understanding of the economics and performance of your network.
