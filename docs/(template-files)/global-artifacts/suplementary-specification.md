# Supplementary Specification (FURPS+)

## Functionality

_Specifies functionalities that:  
&nbsp; &nbsp; (i) are common across several US/UC;  
&nbsp; &nbsp; (ii) are not related to US/UC, namely: Audit, Reporting and Security._

Common functionalities across multiple use cases (US/UC):User authentication and authorization; Logging and monitoring systems Notification services (email, SMS, push notifications); Data validation and error handling mechanisms.

Functionalities not directly related to US/UC: System audit logs for tracking user activity; Role-based access control (RBAC) enforcement; Report generation tools; Encryption and secure data storage mechanisms.
## Usability 

_Evaluates the user interface. It has several subcategories,
among them: error prevention; interface aesthetics and design; help and
documentation; consistency and standards._

- Error Prevention

The document specifies validation rules for business logic, such as name constraints (e.g., city names cannot have special characters or digits) and preventing overbuilding of stations.
It is unclear whether there are real-time UI validation mechanisms (e.g., warnings before errors occur). Implementing inline validation could enhance usability.

- Interface Aesthetics and Design

The document does not explicitly describe the visual aspects of the UI.
The map editor, station details, and trains should be arranged with easy-to-understand layouts. This will ensure that important information, like cargo supply and demand, can be read intuitively.

- Help and Documentation

The application documentation will be in English; however, information pertaining to any in-app help feature has not been given.
Implementation of tooltips, guided instructions, or a help section within the UI will greatly aid user experience.

- Consistency and Standards

The project enforces coding standards, that is, CamelCase, Javadoc, and so on, ensuring uniformity in software development.
UI consistency should also be emphasized, such as maintaining uniform naming conventions, button placements, and interaction patterns across different modules.

## Reliability

_Refers to the integrity, compliance and interoperability of the software. The requirements to be considered are: frequency and severity of failure, possibility of recovery, possibility of prediction, accuracy, average time between failures._

- Frequency and Severity of Failure

The project follows Test-Driven Development (TDD) and requires JUnit 5 testing, which should help minimize software failures.
JaCoCo coverage reports ensure that most of the code is tested, reducing undetected bugs.
However, the document does not mention stress testing or fault tolerance strategies. The severity of failure could vary depending on unhandled exceptions, missing data validations, or improper resource management.

- Possibility of Recovery

Object serialization is used to ensure data persistence, which helps recover application state between runs.
No explicit mention of error recovery mechanisms, such as automatic backup creation, graceful degradation, or rollback mechanisms in case of failure.
The system should implement exception handling with detailed logs to allow users to recover from crashes without significant data loss.

- Possibility of Prediction

No predictive maintenance or failure detection mechanisms are mentioned.
The simulator’s automatic cargo generation could benefit from log-based analysis to detect inconsistencies before they cause failures.
Runtime monitoring (e.g., performance logs, anomaly detection) could help predict and prevent system failures.

- Accuracy

The project involves simulating cargo demand, supply, and transportation, requiring precise calculations.
The document does not specify how data precision is ensured (e.g., rounding mechanisms, floating-point calculations).
Accuracy should be verified with unit tests covering business logic calculations for demand, supply, and transportation times.

- Average Time Between Failures (MTBF)

Since the software is still in development, MTBF is not defined, but using TDD, structured object-oriented design, and automated testing should help improve reliability.
A bug tracking and monitoring system could be implemented to log and analyze system failures over time.
Conducting load and stress testing will help determine the MTBF by simulating heavy usage scenarios.
## Performance

_Evaluates the performance requirements of the software, namely: response time, start-up time, recovery time, memory consumption, CPU usage, load capacity and application availability._

(fill in here )

## Supportability

_The supportability requirements gathers several characteristics, such as:
testability, adaptability, maintainability, compatibility,
configurability, installability, scalability and more._

(fill in here )

## +

### Design Constraints

_Specifies or constraints the system design process. Examples may include: programming languages, software process, mandatory standards/patterns, use of development tools, class library, etc._

(fill in here )

### Implementation Constraints

_Specifies or constraints the code or construction of a system such
such as: mandatory standards/patterns, implementation languages,
database integrity, resource limits, operating system._

(fill in here )

### Interface Constraints

_Specifies or constraints the features inherent to the interaction of the
system being developed with other external systems._

(fill in here )

### Physical Constraints

_Specifies a limitation or physical requirement regarding the hardware used to house the system, as for example: material, shape, size or weight._

(fill in here )