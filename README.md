Clinic Appointment & Patient Records API

Project Description
This is a simple Spring Boot REST API for managing doctors, patients, appointment slots, appointments, and patient visit records.

Technologies Used
- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- H2 File Database
- Maven
- Postman

Project Folders
controller  : Receives API requests.
service     : Contains business rules.
repository  : Communicates with the database.
entity      : Represents database tables.
dto         : Receives request data.
exception   : Handles errors.

Main Features
- Create doctors and patients.
- Generate 30-minute appointment slots for a doctor.
- View a doctor's schedule for one date.
- Book an appointment.
- Cancel an appointment without deleting it.
- Reschedule an appointment while keeping the old appointment record.
- Record diagnosis and prescription after an appointment.
- View a patient's visit history.

Business Rules
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

Appointment Statuses
BOOKED       : The appointment is active.
CANCELLED    : The appointment was cancelled.
RESCHEDULED  : The appointment was moved to another slot.
COMPLETED    : The visit was recorded successfully.

How to Run the Project
1. Open the clinic-api project in IntelliJ IDEA.
2. Make sure Java 17 is selected as the project SDK.
3. Open ClinicApiApplication.java.
4. Click the green Run button next to the main method.
5. Wait until the console shows:
   Started ClinicApiApplication

Application URL
http://localhost:8080

H2 Database Console
Open this URL while the project is running:
http://localhost:8080/h2-console

Use:
JDBC URL: jdbc:h2:file:./data/clinicdb
User Name: sa
Password: leave empty

API Endpoints

Doctors
POST /api/doctors
GET  /api/doctors
GET  /api/doctors/{doctorId}
POST /api/doctors/{doctorId}/slots
GET  /api/doctors/{doctorId}/schedule?date=YYYY-MM-DD

Patients
POST /api/patients
GET  /api/patients
GET  /api/patients/{patientId}
GET  /api/patients/{patientId}/history

Appointments
POST /api/appointments
GET  /api/appointments/{appointmentId}
POST /api/appointments/{appointmentId}/cancel
POST /api/appointments/{appointmentId}/reschedule
POST /api/appointments/{appointmentId}/visit

HTTP Status Codes
200 OK          : Request completed successfully.
201 Created     : New data was created.
400 Bad Request : Invalid input or invalid date/time.
404 Not Found   : Requested data does not exist.
409 Conflict    : A business rule was broken.

Assumptions
- Each appointment slot is 30 minutes long.
- One doctor can have many appointment slots.
- One patient can have many appointments.
- One appointment can have one visit record.
- Authentication is not included because it was not required.
"# teamwork-Refactoring-Improving-ClinicApiSystem" 
