# US03 - As an Editor, I want to add a city in a position XY of the selected map, with a name and a positive number of house blocks.

## 2. Analysis

### 2.1. Relevant Domain Model Excerpt 

![US03-DM](/docs/system-documentation/US03/02.analysis.us03/svg/us03analysis.svg)

### 2.2. Other Remarks

* The name of the city must be unique on the map to avoid conflicts.
* The number of residential blocks should influence the generation of passengers and cargo in the simulator (e.g.: more blocks = higher demand)
* The XY position must be valid within the limits of the map and cannot overlap with other cities or industries.* If the blocks are generated automatically (normal distribution), the system must ensure they are close to the city center.
* The name of the city must be displayed on the map, along with a visual indicator (e.g.: city icon).
* Validations must ensure that:
  * The name does not contain special characters or numbers.
  * The number of blocks is positive.