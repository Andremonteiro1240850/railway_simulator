# US02 - As an Editor, I want to add an industry in a position XY of the selected map.

## 2. Analysis

### 2.1. Relevant Domain Model Excerpt 

![US02-DM](/docs/system-documentation/US02/02.analisys.us02/svg/us02Analysis.svg)

### 2.2. Other Remarks

* The list of available industries must be loaded dynamically based on the selected scenario (e.g., mines, factories, ports).
* The XY position must be valid within the map's boundaries.
* Overlapping industries in cities or other already existing industries must not be allowed. 
* The system must validate if the selected industry is compatible with the scenario (e.g., a port can only be added if the scenario allows it).
*  After addition, the industry must be visually displayed on the map, with an icon corresponding to its type.