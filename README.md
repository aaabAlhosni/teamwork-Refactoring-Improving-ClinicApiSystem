# Clinic Appointment & Patient Records API

## Project Description

This is a simple Spring Boot REST API for managing doctors, patients, appointment slots, appointments, and patient visit records.

## Technologies Used

* Java 17
* Spring Boot
* Spring Web
* Spring Data JPA
* H2 File Database
* Maven
* Postman

## Project Folders

controller  : Receives API requests.
service     : Contains business rules.
repository  : Communicates with the database.
entity      : Represents database tables.
dto         : Handles request and response data transfer.
exception   : Handles errors.

## Main Features

* Create doctors and patients.
* Generate 30-minute appointment slots for a doctor.
* View a doctor's schedule for one date.
* Book an appointment.
* Cancel an appointment without deleting it.
* Reschedule an appointment while keeping the old appointment record.
* Record diagnosis and prescription after an appointment.
* View a patient's visit history.
* Search doctors by name and specialty.
* Standardize doctor specialties using predefined enum values.
* Validate request data and return clear validation error messages.

## Business Rules

1. The doctor's working start time must be before the working end time.
2. Slots cannot be generated for a past date.
3. Slots can only be generated once for the same doctor and date.
4. One slot cannot be booked by two patients.
5. A patient cannot have two active BOOKED appointments with the same doctor on the same date.
6. Cancelling an appointment does not delete it. Its status becomes CANCELLED.
7. Rescheduling creates a new appointment. The old appointment becomes RESCHEDULED and is linked to the new appointment.
8. A visit can only be recorded for a BOOKED appointment after its end time has passed.
9. A cancelled or future appointment cannot have a visit.
10. One appointment can only have one visit.
11. Doctor specialties must be selected from the predefined Specialty enum values.
12. Invalid request data is rejected with clear validation messages.

## Appointment Statuses

BOOKED       : The appointment is active.
CANCELLED    : The appointment was cancelled.
RESCHEDULED  : The appointment was moved to another slot.
COMPLETED    : The visit was recorded successfully.

## Doctor Specialties

Doctor specialties are standardized using a `Specialty` enum instead of free text.
This prevents inconsistent specialty values and duplicate formats.

Valid specialty values:

* GENERAL_MEDICINE
* CARDIOLOGY
* DERMATOLOGY
* PEDIATRICS
* ORTHOPEDICS
* NEUROLOGY
* DENTISTRY
* TESTING
* INTERNAL_MEDICINE
* FAMILY_MEDICINE
* EMERGENCY_MEDICINE
* ENDOCRINOLOGY
* GASTROENTEROLOGY
* HEMATOLOGY
* ONCOLOGY
* RHEUMATOLOGY
* PULMONOLOGY
* NEPHROLOGY
* UROLOGY
* OPHTHALMOLOGY
* ENT
* PSYCHIATRY
* RADIOLOGY
* ANESTHESIOLOGY
* GENERAL_SURGERY
* PLASTIC_SURGERY
* OBSTETRICS_GYNECOLOGY
* INFECTIOUS_DISEASE
* ALLERGY_IMMUNOLOGY
* PATHOLOGY
* PHYSIOTHERAPY
* NUTRITION
* SPORTS_MEDICINE

Invalid specialty values are rejected.
For example, `"Heart Doctor"` is not accepted because it is not part of the predefined Specialty enum.

## Input Validation

Request DTOs include validation annotations to prevent invalid data from entering the system.

Examples of validations:

* Required fields cannot be null or blank.
* Names must have a valid length.
* Phone numbers must be exactly 8 digits and must start with 9 (e.g. `91234567`).
* Date of birth must be in the past.
* Slot dates cannot be in the past.
* IDs must be greater than 0.
* Doctor specialty must be one of the predefined enum values.

When validation fails, the API returns a clear error response.

Example validation error response:

```json
{
  "message": "Validation failed",
  "errors": {
    "name": "Patient name is required",
    "dateOfBirth": "Date of birth must be in the past",
    "phone": "Phone number must be 8 digits and start with 9"
  }
}
```

Example invalid specialty response:

```json
{
  "message": "Invalid request value",
  "error": "Specialty must be one of: [GENERAL_MEDICINE, CARDIOLOGY, DERMATOLOGY, PEDIATRICS, ORTHOPEDICS, NEUROLOGY, DENTISTRY, TESTING]"
}
```

## How to Run the Project

1. Open the clinic-api project in IntelliJ IDEA.
2. Make sure Java 17 is selected as the project SDK.
3. Open ClinicApiApplication.java.
4. Click the green Run button next to the main method.
5. Wait until the console shows:

```text
Started ClinicApiApplication
```

## Application URL

```text
http://localhost:8080
```

## H2 Database Console

Open this URL while the project is running:

```text
http://localhost:8080/h2-console
```

Use:

```text
JDBC URL: jdbc:h2:file:./data/clinicdb
User Name: sa
Password: leave empty
```

## API Endpoints

### Doctors

```text
POST /api/doctors
GET  /api/doctors
GET  /api/doctors?name={name}
GET  /api/doctors?specialty={specialty}
GET  /api/doctors?name={name}&specialty={specialty}
GET  /api/doctors/{doctorId}
POST /api/doctors/{doctorId}/slots
GET  /api/doctors/{doctorId}/schedule?date=YYYY-MM-DD
```

Doctor search supports optional filters by name and specialty.
If no filters are provided, all doctors are returned page by page.

Create doctor example:

```http
POST /api/doctors
```

Request body:

```json
{
  "name": "Dr. Ali Noor",
  "specialty": "CARDIOLOGY",
  "workingStart": "10:00:00",
  "workingEnd": "13:00:00"
}
```

Search doctors by name:

```http
GET /api/doctors?name=Sara
```

Search doctors by specialty:

```http
GET /api/doctors?specialty=CARDIOLOGY
```

Search doctors by name and specialty:

```http
GET /api/doctors?name=Ali&specialty=CARDIOLOGY
```

### Patients

```text
POST /api/patients
GET  /api/patients
GET  /api/patients/{patientId}
GET  /api/patients/{patientId}/history
```

Create patient example:

```http
POST /api/patients
```

Request body:

```json
{
  "name": "Safa Ahmed",
  "dateOfBirth": "2001-02-05",
  "phone": "91234567"
}
```

### Appointments

```text
POST /api/appointments
GET  /api/appointments/{appointmentId}
POST /api/appointments/{appointmentId}/cancel
POST /api/appointments/{appointmentId}/reschedule
POST /api/appointments/{appointmentId}/visit
```

Book appointment example:

```http
POST /api/appointments
```

Request body:

```json
{
  "patientId": 1,
  "slotId": 1
}
```

Reschedule appointment example:

```http
POST /api/appointments/{appointmentId}/reschedule
```

Request body:

```json
{
  "newSlotId": 2
}
```

Record visit example:

```http
POST /api/appointments/{appointmentId}/visit
```

Request body:

```json
{
  "diagnosis": "Flu",
  "prescription": "Rest and drink fluids"
}
```

## How to Test the API

> Start the app first, then send requests using Postman or any HTTP client at `http://localhost:8080`.
> Follow the order below — create a doctor first, then generate slots, then create a patient, then book an appointment.

---

### 1. Create a Doctor

```http
POST /api/doctors
Content-Type: application/json
```

Request body:

```json
{
  "name": "Dr. Ali Hassan",
  "specialty": "CARDIOLOGY",
  "workingStart": "08:00",
  "workingEnd": "16:00"
}
```

Expected status: `201 Created`

Expected response:

```json
{
  "id": 1,
  "name": "Dr. Ali Hassan",
  "specialty": "CARDIOLOGY",
  "workingStart": "08:00:00",
  "workingEnd": "16:00:00"
}
```

---

### 2. Get All Doctors (Paginated)

```http
GET /api/doctors?page=0&size=5
```

Expected status: `200 OK`

Returns a paginated list of all doctors.

---

### 3. Search Doctors by Name

```http
GET /api/doctors?name=Ali
```

Expected status: `200 OK`

Returns all doctors whose name contains "Ali".

---

### 4. Filter Doctors by Specialty

```http
GET /api/doctors?specialty=CARDIOLOGY
```

Expected status: `200 OK`

Returns all doctors with the CARDIOLOGY specialty.

---

### 5. Search by Name and Specialty

```http
GET /api/doctors?name=Ali&specialty=CARDIOLOGY
```

Expected status: `200 OK`

Returns doctors matching both filters.

---

### 6. Get Doctor by ID

```http
GET /api/doctors/1
```

Expected status: `200 OK`

Expected response:

```json
{
  "id": 1,
  "name": "Dr. Ali Hassan",
  "specialty": "CARDIOLOGY",
  "workingStart": "08:00:00",
  "workingEnd": "16:00:00"
}
```

---

### 7. Generate Appointment Slots for a Doctor

```http
POST /api/doctors/1/slots
Content-Type: application/json
```

Request body:

```json
{
  "date": "2026-07-01"
}
```

Expected status: `201 Created`

Expected response (list of 30-minute slots from 08:00 to 16:00):

```json
[
  {
    "id": 1,
    "doctorId": 1,
    "doctorName": "Dr. Ali Hassan",
    "slotDate": "2026-07-01",
    "startTime": "08:00:00",
    "endTime": "08:30:00",
    "available": true
  },
  {
    "id": 2,
    "doctorId": 1,
    "doctorName": "Dr. Ali Hassan",
    "slotDate": "2026-07-01",
    "startTime": "08:30:00",
    "endTime": "09:00:00",
    "available": true
  }
]
```

---

### 8. View Doctor Schedule for a Date

```http
GET /api/doctors/1/schedule?date=2026-07-01
```

Expected status: `200 OK`

Returns all slots for doctor 1 on that date, showing which are available.

---

### 9. Create a Patient

```http
POST /api/patients
Content-Type: application/json
```

Request body:

```json
{
  "name": "Sara Ahmed",
  "dateOfBirth": "1995-03-15",
  "phone": "91234567"
}
```

Expected status: `201 Created`

Expected response:

```json
{
  "id": 1,
  "name": "Sara Ahmed",
  "dateOfBirth": "1995-03-15",
  "phone": "91234567"
}
```

---

### 10. Get All Patients (Paginated)

```http
GET /api/patients?page=0&size=5
```

Expected status: `200 OK`

Returns a paginated list of all patients.

---

### 11. Get Patient by ID

```http
GET /api/patients/1
```

Expected status: `200 OK`

Expected response:

```json
{
  "id": 1,
  "name": "Sara Ahmed",
  "dateOfBirth": "1995-03-15",
  "phone": "91234567"
}
```

---

### 12. Book an Appointment

```http
POST /api/appointments
Content-Type: application/json
```

Request body:

```json
{
  "patientId": 1,
  "slotId": 1
}
```

Expected status: `201 Created`

Expected response:

```json
{
  "id": 1,
  "patientId": 1,
  "patientName": "Sara Ahmed",
  "slotId": 1,
  "doctorId": 1,
  "doctorName": "Dr. Ali Hassan",
  "status": "BOOKED",
  "rescheduledToId": null
}
```

---

### 13. Get Appointment by ID

```http
GET /api/appointments/1
```

Expected status: `200 OK`

Returns the appointment details including current status.

---

### 14. Cancel an Appointment

```http
POST /api/appointments/1/cancel
```

Expected status: `200 OK`

Expected response:

```json
{
  "id": 1,
  "patientId": 1,
  "patientName": "Sara Ahmed",
  "slotId": 1,
  "doctorId": 1,
  "doctorName": "Dr. Ali Hassan",
  "status": "CANCELLED",
  "rescheduledToId": null
}
```

The appointment is not deleted — its status changes to `CANCELLED`.

---

### 15. Reschedule an Appointment

Book a new appointment first (step 12) to get a BOOKED appointment, then:

```http
POST /api/appointments/1/reschedule
Content-Type: application/json
```

Request body:

```json
{
  "newSlotId": 2
}
```

Expected status: `200 OK`

Expected response (the new appointment):

```json
{
  "id": 2,
  "patientId": 1,
  "patientName": "Sara Ahmed",
  "slotId": 2,
  "doctorId": 1,
  "doctorName": "Dr. Ali Hassan",
  "status": "BOOKED",
  "rescheduledToId": null
}
```

The original appointment (id: 1) status becomes `RESCHEDULED` and links to the new one.

---

### 16. Record a Visit

Only works on a BOOKED appointment after the slot's end time has passed.

```http
POST /api/appointments/1/visit
Content-Type: application/json
```

Request body:

```json
{
  "diagnosis": "Hypertension stage 1",
  "prescription": "Losartan 50mg once daily for 30 days"
}
```

Expected status: `201 Created`

Expected response:

```json
{
  "id": 1,
  "appointmentId": 1,
  "patientId": 1,
  "patientName": "Sara Ahmed",
  "diagnosis": "Hypertension stage 1",
  "prescription": "Losartan 50mg once daily for 30 days"
}
```

---

### 17. View Patient Visit History

```http
GET /api/patients/1/history
```

Expected status: `200 OK`

Expected response:

```json
[
  {
    "id": 1,
    "appointmentId": 1,
    "patientId": 1,
    "patientName": "Sara Ahmed",
    "diagnosis": "Hypertension stage 1",
    "prescription": "Losartan 50mg once daily for 30 days"
  }
]
```

---

### Validation Error Examples

**Invalid doctor name (contains numbers):**

```http
POST /api/doctors
```

```json
{ "name": "Dr4 Ali", "specialty": "CARDIOLOGY", "workingStart": "08:00", "workingEnd": "16:00" }
```

Expected: `400 Bad Request`

```json
{ "message": "Validation failed", "errors": { "name": "Doctor name must contain only letters, spaces, and dots" } }
```

---

**Invalid specialty (not in enum):**

```http
POST /api/doctors
```

```json
{ "name": "Dr. Ali", "specialty": "Heart Doctor", "workingStart": "08:00", "workingEnd": "16:00" }
```

Expected: `400 Bad Request`

```json
{ "message": "Invalid request value", "error": "Specialty must be one of: [GENERAL_MEDICINE, CARDIOLOGY, ...]" }
```

---

**Slot date in the past:**

```http
POST /api/doctors/1/slots
```

```json
{ "date": "2020-01-01" }
```

Expected: `400 Bad Request`

---

**Invalid phone number:**

```http
POST /api/patients
```

```json
{ "name": "Sara Ahmed", "dateOfBirth": "1995-03-15", "phone": "12345678" }
```

Expected: `400 Bad Request`

```json
{ "message": "Validation failed", "errors": { "phone": "Phone number must be 8 digits and start with 9" } }
```

---

**Booking an already-booked slot:**

```http
POST /api/appointments
```

```json
{ "patientId": 2, "slotId": 1 }
```

Expected: `409 Conflict`

---

**Recording a visit on a non-BOOKED appointment:**

```http
POST /api/appointments/1/visit
```

```json
{ "diagnosis": "Flu", "prescription": "Rest" }
```

Expected: `400 Bad Request` (if appointment is CANCELLED or not yet past end time)

## HTTP Status Codes

200 OK          : Request completed successfully.
201 Created     : New data was created.
400 Bad Request : Invalid input or invalid date/time.
404 Not Found   : Requested data does not exist.
409 Conflict    : A business rule was broken.

## Assumptions

* Each appointment slot is 30 minutes long.
* One doctor can have many appointment slots.
* One patient can have many appointments.
* One appointment can have one visit record.
* Authentication is not included because it was not required.
