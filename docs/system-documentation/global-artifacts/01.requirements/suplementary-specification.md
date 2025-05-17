# Supplementary Specification (FURPS+)

## Functionality

_Specifies functionalities that:  
&nbsp; &nbsp; (i) are common across several US/UC;  
&nbsp; &nbsp; (ii) are not related to US/UC, namely: Audit, Reporting and Security._

### (ii)
### Security:
All users must log in using a password containing exactly seven alphanumeric characters, including at least three capital letters and two digits.

Access control must be enforced based on user roles (i.e., Player, Editor).

## Usability

_Evaluates the user interface. It has several subcategories,
among them: error prevention; interface aesthetics and design; help and
documentation; consistency and standards._

#### *Localizability:*

The entire application documentation must be written in English.



## Reliability

_Refers to the integrity, compliance and interoperability of the software. The requirements to be considered are: frequency and severity of failure, possibility of recovery, possibility of prediction, accuracy, average time between failures._




## Performance

_Evaluates the performance requirements of the software, namely: response time, start-up time, recovery time, memory consumption, CPU usage, load capacity and application availability._

 

## Supportability

_The supportability requirements gathers several characteristics, such as:
testability, adaptability, maintainability, compatibility,
configurability, installability, scalability and more._

### *Testability:*
The development team needs to implement unit tests for all the methods, except the Input/Output methods.

The JaCoCo plugin must be used to generate code coverage reports.

### *Maintainability:*
The class design should support easy future maintenance and scalability, following the principles of object-oriented programming.

## +

### Design Constraints

_Specifies or constraints the system design process. Examples may include: programming languages, software process, mandatory standards/patterns, use of development tools, class library, etc._

### *Programming Language:*
The Java programming language is required to implement the system.

### *Development Tools:*
JaCoCo needs to be employed to provide calculations and reports of test coverage during development.

### *Mandatory Standards:*
Business rule validation on update or save needs to be implemented.

Best practices of object-oriented analysis, design, and requirement specification have to be applied throughout the project.



### Implementation Constraints

_Specifies or constraints the code or construction of a system such
such as: mandatory standards/patterns, implementation languages,
database integrity, resource limits, operating system._

### *Mandatory Standards/Patterns:*

Follow recognized coding conventions like CamelCase.

JavaDoc must be utilized to produce readable and organized code documentation.

The JUnit 5 framework must be utilized to execute unit tests.

All diagrams or figures created during development must be saved in SVG format.

Development should be in accordance with the Test-Driven Development (TDD) approach.

### *Data Integrity:*
Object serialization must be implemented to preserve data across successive program runs.



### Interface Constraints

_Specifies or constraints the features inherent to the interaction of the
system being developed with other external systems._



### Physical Constraints

_Specifies a limitation or physical requirement regarding the hardware used to house the system, as for example: material, shape, size or weight._

