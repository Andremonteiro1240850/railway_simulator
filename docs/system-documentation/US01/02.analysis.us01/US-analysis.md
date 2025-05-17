# US01 - As an Editor, I want to create a map with a size and a name.

## 2. Analysis

### 2.1. Relevant Domain Model Excerpt 

![US01-DM](/docs/system-documentation/US01/02.analysis.us01/svg/us01Analysis.svg)

### 2.2. Other Remarks

* O nome do mapa deve ser único no sistema para evitar conflitos.
* A interface de criação do mapa deve ser intuitiva, permitindo que o Editor insira facilmente as dimensões e o nome.
* Validações devem ser implementadas para garantir que as dimensões do mapa sejam números inteiros positivos.
* O sistema deve fornecer feedback imediato caso os critérios de aceitação não sejam atendidos (ex: nome inválido ou dimensões não positivas).
* O mapa criado deve ser persistido no sistema para uso futuro no editor de cenários ou no simulador.