# USXXX - YYY

_XXX stands for User Story number and YYY for User Story description (e.g. US006 - Create a Task)_

## 3. Design

### 3.1. Rationale

**The rationale grounds on the SSD interactions and the identified input/output data.**

| Interaction ID | Question: Which class is responsible for...                      | Answer                  | Justification (with patterns)                                                                                                                           |
|:---------------|:-----------------------------------------------------------------|:------------------------|:--------------------------------------------------------------------------------------------------------------------------------------------------------|
| Step 1  		     | ... interacting with the Editor?                                 | CreateMapUI             | Pure Fabrication: separates UI responsibility from domain logic.                                                                                        |
| 		             | ... coordinating the flow of the use case?                       | CreateMapController     | Controller: mediates between UI and domain logic.                                                                                                       |
| 		             | ... knowing the authenticated user?                              | UserSession             | Information Expert: maintains session data.                                                                                                             |
| Step 2  		     | ... requesting the name and dimensions of the map from the user? | CreateMapUI             | Information Expert: handles user input.                                                                                                                 |
| Step 3  		     | ... validating the map name (valid file name)?                   | CreateMapController     | Information Expert: Implements the business rule for validating the map name to ensure it adheres to valid file naming conventions.                     |
| Step 4  		     | ... validating the map dimensions (positive integers)?           | CreateMapController     | Information Expert: This class is responsible for applying validation logic on map dimensions, ensuring they are positive integers as per domain rules. |
| Step 5  		     | ... creating the map instance?                                   | Organization            | 	Creator: In the Domain Model, this class is responsible for creating instances of domain entities like the map, aligning with the Creator pattern.     |
|                   | ... knowing the map data (name and dimensions)?                  | Map                     | Information Expert: The Map class owns the data related to the map (its name and dimensions) and manages it internally.                                 |
| Step 6  		     | ... storing the created map?                                     | OrganizationRepository  | Repository: Handles the persistence of the Organization and its associated maps, ensuring data storage and retrieval.                                   |              
| Step 7  		     | ... informing the result of the operation to the UI?             | CreateMapController     | Controller: This class is responsible for relaying the outcome of the operation (success or failure) back to the UI.                                    |
| Step 8  		     | ... displaying a success or error message to the user?           | CreateMapUI             | Information Expert: The UI class is responsible for providing feedback to the user, either showing a success or error message based on the operation result.                                                                                                                                                        |

### Systematization ##

According to the taken rationale, the conceptual classes promoted to software classes are:

* Organization
* Map

Other software classes (i.e. Pure Fabrication) identified:

* CreateMapUI
* CreateMapController
* Repositories
* OrganizationRepository
* ApplicationSession
* UserSession

## 3.2. Sequence Diagram (SD)

_In this section, it is suggested to present an UML dynamic view representing the sequence of interactions between software objects that allows to fulfill the requirements._

![US01-SD](svg/USXXX-SD.svg)

### Split Diagrams
The following diagrams show the same sequence of interactions between the classes involved in the realization of this user story, but split into partial diagrams to better illustrate the interactions between the classes.

They use Interaction Occurrence (a.k.a. Interaction Use) and reflect different logical phases of the interaction.

#### Input & Validation Phase
This diagram shows how the user starts the use case and inputs map data, which is then validated by the controller.
![US01-SD-partial-input-validation](Sprint2/US01/03.design.us01/svg/US01-SD-partial-input-validation.svg)

## 3.3. Class Diagram (CD)

_In this section, it is suggested to present an UML static view representing the main related software classes that are involved in fulfilling the requirements as well as their relations, attributes and methods._\

### Full Diagram
This diagram shows the complete sequence of interactions between the classes involved in the realization of this user story.\
![US01-CD-](Sprint2/US01/03.design.us01/svg/US01-SD-full.svg)
