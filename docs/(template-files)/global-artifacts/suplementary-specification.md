# Supplementary Specification (FURPS+)

## Functionality

_Specifies functionalities that:  
&nbsp; &nbsp; (i) are common across several US/UC;  
&nbsp; &nbsp; (ii) are not related to US/UC, namely: Audit, Reporting and Security._

Common functionalities across multiple use cases (US/UC): User authentication and authorization; Logging and monitoring systems Notification services (email, SMS, push notifications); Data validation and error handling mechanisms.

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

- Response Time

Because the software simulates a railway network, it must frequently calculate resource availability, train schedules, and cargo movement.
Although real-time performance optimizations (such as caching and efficient data structures) are not mentioned, the Java-based implementation should enable efficient execution.
Processing intricate train schedules or loading large maps may cause delays.

Suggestion for Improvement: Use multithreading and asynchronous processing for tasks that require high performance, such as updating train movements.


- Start-Up Time

If a lot of data needs to be loaded, the project's use of object serialization for data persistence could impact startup time.
Optimizations like lazy loading, which loads only the most important data at startup, are not specifically mentioned.

Progress Recommendation: Reduce startup time by loading only necessary items first and retrieving others as needed.

- Recovery Time

The document mentions that object serialization keeps data safe between runs, but it doesn't explain how quickly data can be restored. If there's a failure, it's unclear if there is a way to bring back the last good state.

Improvement Suggestion: use incremental save states. This means loading smaller parts of data at different times, instead of everything at once, which can speed up recovery.

- Memory Consumption

The software has to store maps, industries, cities, trains, and their states, which can use a lot of memory. Java's automatic garbage collection helps to manage this. However, if objects that are no longer needed are not removed, it may cause memory issues.

Improvement Suggestion: Use tools like VisualVM or JProfiler to monitor memory usage. Also, use techniques like data compression and object pooling to use memory more efficiently.

- CPU Usage 

The simulator is constantly updating train movements, cargo transactions, and network statistics, which can result in high CPU usage. The document doesn't discuss optimizing how tasks are divided among different threads, so CPU-heavy tasks might slow down the main operations.

Improvement Suggestion: use multithreading to distribute tasks. This involves creating separate threads for the user interface, simulation, and database processes. Also, use performance profiling to detect where CPU usage is too high.


- Load Capacity

The document doesn't specify the largest map sizes, or the maximum number of trains or industries the system can handle before performance decreases. Having too many objects can slow the system significantly due to increased calculations.

Improvement Suggestion:
Define maximum supported limits (e.g., number of active trains, industries, and events).
Implement dynamic scaling, such as reducing update frequency for distant or inactive areas of the map.

- Application Availability

There is no specific mention of uptime guarantees or methods to keep the system stable over long periods. If the application is used by multiple users in the future, maintaining high availability will be crucial.


## Supportability

_The supportability requirements gathers several characteristics, such as:
testability, adaptability, maintainability, compatibility,
configurability, installability, scalability and more._

- Testability

This project uses Test-Driven Development (TDD) with JUnit 5 to ensure code quality through unit testing. With JaCoCo, we verify how much of the code is covered by tests. However, there is no mention of integration testing, which is important to see how different parts like the user interface, data storage, and simulations work together.

Improvement Suggestion: Add automated integration tests to ensure different parts of the system interact properly.

- Adaptability

The project follows object-oriented programming (OOP) principles, which should make it easier to modify and extend.
The iterative and incremental development process (SCRUM) allows for changes over time.
However, no mention is made of plugin-based architectures or modular designs, which could further improve adaptability.

Improvement Suggestion:  Build the software with a modular design for easy addition of new parts like train models or industries. - Develop APIs to connect with external extensions or third-party services.

- Maintainability

Using JavaDoc helps make the code clear and easy to understand. Enforcing coding standards like CamelCase simplifies code maintenance. There's no mention of code improvement practices or using static analysis tools.

Improvement Suggestion:  Implement static code analysis tools to identify areas for improvement in the code. - Establish strict version control to efficiently track changes.

- Compatibility

It's unclear if the application works on various operating systems or hardware setups. Since it's built with Java, it potentially runs on multiple platforms, but this has not been confirmed through cross-platform testing.

- Configurability

The application’s options for user configuration and personalization, such as changing UI, performance, or gameplay settings, are not addressed. Users need to have the ability to easily customize these settings for different simulation scenarios.

Improvement Suggestion: Use configuration files like JSON or XML to allow easy changes in settings. - Develop a user-friendly menu to change simulator settings effortlessly.

- Instability

There is no discussion about how the software will be installed or distributed, like whether there will be an installer or options for cloud deployment. Installing Java applications often needs JRE/JDK, which might be challenging for non-technical users.

- Scalability

The project does not mention any performance limits, such as the maximum number of trains, industries, or cities it can handle. For future expansions, including more players or larger maps, the system should be ready to manage increased demands effectively.

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