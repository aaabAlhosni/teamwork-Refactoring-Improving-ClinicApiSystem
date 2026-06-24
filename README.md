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
dto         : Receives request data.
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
* Phone numbers must contain 8 to 15 digits and may start with `+`.
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
    "phone": "Phone number must contain 8 to 15 digits and may start with +"
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
  "phone": "+96891234567"
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
