# Renewable Energy Monitoring System - Testing Guide

## Prerequisites

-   MySQL Server running
-   API Gateway running on **8080**
-   Solar Service running on **8081**
-   Wind Service running on **8082**
-   Battery Service running on **8083**
-   Distribution Service running on **8084**

------------------------------------------------------------------------

## Testing Order

### 1. Register Solar Panels

**POST** `http://localhost:8080/api/v1/solar-panels`

``` json
{
  "panel_name":"Solar Alpha",
  "location":"Chennai",
  "capacity_kw":500,
  "current_generation":300,
  "status":"ACTIVE"
}
```

### 2. Register Wind Turbines

**POST** `http://localhost:8080/api/v1/wind-turbines`

``` json
{
  "turbineName":"Wind Alpha",
  "location":"Coimbatore",
  "capacity":1000,
  "currentGeneration":400,
  "status":"ACTIVE"
}
```

### 3. Register Batteries

**POST** `http://localhost:8080/api/v1/batteries`

``` json
{
  "batteryName":"Battery Alpha",
  "capacity":1000,
  "currentCharge":300,
  "location":"Chennai",
  "status":"ACTIVE"
}
```

Create multiple batteries to test charge/discharge across several
batteries.

------------------------------------------------------------------------

## CRUD Testing

### Solar

-   `GET /api/v1/solar-panels`
-   `GET /api/v1/solar-panels/{id}`
-   `PUT /api/v1/solar-panels/{id}`
-   `DELETE /api/v1/solar-panels/{id}`

### Wind

-   `GET /api/v1/wind-turbines`
-   `GET /api/v1/wind-turbines/{id}`
-   `PUT /api/v1/wind-turbines/{id}`
-   `DELETE /api/v1/wind-turbines/{id}`

### Battery

-   `GET /api/v1/batteries`
-   `GET /api/v1/batteries/{id}`
-   `PUT /api/v1/batteries/{id}`
-   `DELETE /api/v1/batteries/{id}`

------------------------------------------------------------------------

## Generation History

### Solar

**POST** `/api/v1/solar-panels/{id}/generation`

``` json
{
  "generated_units":120
}
```

### Wind

**POST** `/api/v1/wind-turbines/{id}/generation`

``` json
{
  "generatedPower":180
}
```

------------------------------------------------------------------------

## Daily Reports

-   `GET /api/v1/solar-panels/report?date=YYYY-MM-DD`
-   `GET /api/v1/wind-turbines/report?date=YYYY-MM-DD`

------------------------------------------------------------------------

## Fault Detection

-   `GET /api/v1/solar-panels/faults`
-   `GET /api/v1/wind-turbines/faults`

------------------------------------------------------------------------

## Battery Operations

### Charge

**POST** `/api/v1/batteries/{id}/charge`

``` json
{
  "units":150
}
```

### Discharge

**POST** `/api/v1/batteries/{id}/discharge`

``` json
{
  "units":100
}
```

### Battery Status

-   `GET /api/v1/batteries/{id}/percentage`
-   `GET /api/v1/batteries/{id}/status`

------------------------------------------------------------------------

## Distribution Testing

### Endpoint

**POST** `/api/v1/distribution/balance`

### Scenario 1 - Excess Renewable Energy

``` json
{
  "requiredUnits":500
}
```

**Expected Result**

-   Renewable energy satisfies demand.
-   Excess energy stored in batteries.
-   Distribution history saved.

### Scenario 2 - Renewable Energy Insufficient

``` json
{
  "requiredUnits":2500
}
```

**Expected Result**

-   Renewable energy used first.
-   Batteries discharge remaining requirement.
-   Status becomes `BATTERY USED` or `INSUFFICIENT BATTERY`.
-   Distribution history saved.

------------------------------------------------------------------------

## Validation Tests

-   Blank device name
-   Missing required fields
-   Negative capacity
-   Negative generation units
-   Invalid status values

------------------------------------------------------------------------

## Expected Outcomes

-   All CRUD APIs work correctly.
-   Generation history is recorded.
-   Daily reports are accurate.
-   Fault detection identifies active devices with zero generation.
-   Battery charge never exceeds capacity.
-   Battery charge never falls below zero.
-   Distribution prioritizes renewable energy before batteries.
-   Battery stores excess renewable energy.
-   API Gateway routes requests successfully.
-   Distribution history is stored after every balance operation.

------------------------------------------------------------------------

## Project Completion Checklist

-   [x] Solar Service
-   [x] Wind Service
-   [x] Battery Service
-   [x] Distribution Service
-   [x] API Gateway
-   [x] Validation
-   [x] Global Exception Handling
-   [x] Daily Reports
-   [x] Fault Detection
-   [x] RestTemplate Communication