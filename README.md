# document-service <br>

### How to install and run <br>

### You can clone this and run like common spring boot application<br>

#### or <br>

### Use docker-compose <br>

- git clone https://github.com/krlld/document-service
- cd document-service
- docker-compose build
- docker-compose up

### To create liming document use http request

- POST http://localhost:8080/liming-document

### With json body like this

- {
  "organizationName": "ОАО \"Шашки\"",
  "districtName": "СТОЛБЦОВСКИЙ Р-Н",
  "regionName": "МИНСКАЯ ОБЛАСТЬ",
  "elementarySectionLimingPlans": [
  {
  "workSectionNumber": 29,
  "elementarySectionNumber": 190,
  "elementarySectionArea": 5.3,
  "elementarySectionPerimeter": 27,
  "pHInKCl": 5.41,
  "pHGroup": 3,
  "granulometricSoilComposition": "Рыхлосупесч.",
  "humusContent": 1.79,
  "caesium": 0.001,
  "strontium": 0.002,
  "CaCO3Dose": 3.5,
  "limeMassDose": 3.7,
  "limeMaterialRequirement": 19.6,
  "limingPeriod": 3,
  "costPerArea": 414.56,
  "totalCost": 2197.15
  },
  {
  "workSectionNumber": 29,
  "elementarySectionNumber": 191,
  "elementarySectionArea": 4.1,
  "elementarySectionPerimeter": 27,
  "pHInKCl": 5,
  "pHGroup": 2,
  "granulometricSoilComposition": "Связнопесч.",
  "humusContent": 1.79,
  "caesium": 0.001,
  "strontium": 0.002,
  "CaCO3Dose": 4,
  "limeMassDose": 4.2,
  "limeMaterialRequirement": 17.2,
  "limingPeriod": 3,
  "costPerArea": 466.05,
  "totalCost": 1910.82
  },
  {
  "workSectionNumber": 29,
  "elementarySectionNumber": 192,
  "elementarySectionArea": 5.7,
  "elementarySectionPerimeter": 27,
  "pHInKCl": 4.79,
  "pHGroup": 2,
  "granulometricSoilComposition": "Связнопесч.",
  "humusContent": 1.79,
  "caesium": 0.001,
  "strontium": 0.002,
  "CaCO3Dose": 4,
  "limeMassDose": 4.2,
  "limeMaterialRequirement": 23.9,
  "limingPeriod": 3,
  "costPerArea": 465.83,
  "totalCost": 2655.24
  }
