# OO Analysis

The construction process of the domain model is based on the client specifications, especially the nouns (for _concepts_) and verbs (for _relations_) used. 

## Rationale to identify domain conceptual classes
To identify domain conceptual classes, start by making a list of candidate conceptual classes inspired by the list of categories suggested in the book "Applying UML and Patterns: An Introduction to Object-Oriented Analysis and Design and Iterative Development". 


### _Conceptual Class Category List_

**Business Transactions**

Order, payment

___

**Transaction Line Items**

Order item

---

**Product/Service related to a Transaction or Transaction Line Item**

Product, service

---

**Transaction Records**

Invoice, receipt

---  

**Roles of People or Organizations**

Seller, supplier, customer

---

**Places**

Store, warehouse

---

**Noteworthy Events**

Shipment, return

---

**Physical Objects**

Package, device

---

**Descriptions of Things**

Product description, service details

---

**Catalogs**

Product catalog

---

**Containers**

Shopping cart, Inventory

---

**Elements of Containers**

Car item, stock item

---

**(Other) Organizations**

Manufacturer, distributor

---

**Other (External/Collaborating) Systems**

Payment gateway, shipping system

---

**Records of finance, work, contracts, legal matters**

Contract, invoice record

---

**Financial Instruments**

Credit card

---

**Documents mentioned/used to perform some work**

Purchase, order, warranty document

---


## Rationale to identify associations between conceptual classes

An association is a relationship between instances of objects that indicates a relevant connection and that is worth of remembering, or it is derivable from the List of Common Associations: 

* **_A_** is physically or logically part of **_B_**
* **_A_** is physically or logically contained in/on **_B_**
* **_A_** is a description for **_B_**
* **_A_** known/logged/recorded/reported/captured in **_B_**
* **_A_** uses or manages or owns **_B_**
* **_A_** is related with a transaction (item) of **_B_**
* etc.


| Concept (A) 		 | Association   	 |     Concept (B) |
|----------------|:---------------:|----------------:|
| Customer  	    |     Places      |           Order |               |                  |             |
| Order 	        |    Contains     |      Order Item |
| Order Item     |    Refers to    |         Product |
| Order          |    Generates    |         Invoice |
| Invoice        |     Records     |         Payment |
| Payment        | is processed by | Payment Manager |
| Seller         |     Manages     |         Product |
| Warehouse      |     Stores      |         Product |
| Order          |  is shipped to  |        Customer |
| Shipment       |    delivers     |           Order |



## Domain Model

**Do NOT forget to identify concept atributes too.**

**Insert the Domain Model diagram in SVG format below.**

![Domain Model](svg/DM.svg)