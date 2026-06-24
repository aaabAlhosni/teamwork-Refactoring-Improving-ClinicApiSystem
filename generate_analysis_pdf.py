from reportlab.lib.pagesizes import A4
from reportlab.lib.styles import getSampleStyleSheet, ParagraphStyle
from reportlab.lib.units import cm
from reportlab.lib import colors
from reportlab.platypus import (
    SimpleDocTemplate, Paragraph, Spacer, Table, TableStyle,
    HRFlowable, KeepTogether
)
from reportlab.lib.enums import TA_LEFT, TA_CENTER, TA_JUSTIFY
from reportlab.platypus import PageBreak

OUTPUT = "Analysis.pdf"

doc = SimpleDocTemplate(
    OUTPUT,
    pagesize=A4,
    leftMargin=2.2*cm, rightMargin=2.2*cm,
    topMargin=2.2*cm, bottomMargin=2.2*cm,
    title="Phase 1 – Project Analysis",
    author="Team Analysis Report"
)

W = A4[0] - 4.4*cm  # usable page width

# ── Styles ──────────────────────────────────────────────────────────────────
base = getSampleStyleSheet()

def S(name, parent="Normal", **kw):
    s = ParagraphStyle(name, parent=base[parent], **kw)
    return s

cover_title   = S("CoverTitle",  fontSize=26, leading=32, alignment=TA_CENTER,
                   spaceAfter=6, textColor=colors.black, fontName="Helvetica-Bold")
cover_sub     = S("CoverSub",    fontSize=13, leading=18, alignment=TA_CENTER,
                   spaceAfter=4, textColor=colors.black, fontName="Helvetica")
cover_meta    = S("CoverMeta",   fontSize=10, leading=14, alignment=TA_CENTER,
                   spaceAfter=2, textColor=colors.black, fontName="Helvetica")

h1 = S("H1", fontSize=15, leading=20, spaceBefore=18, spaceAfter=6,
        fontName="Helvetica-Bold", textColor=colors.black,
        borderPadding=(0,0,2,0))
h2 = S("H2", fontSize=12, leading=16, spaceBefore=12, spaceAfter=4,
        fontName="Helvetica-Bold", textColor=colors.black)
h3 = S("H3", fontSize=10, leading=14, spaceBefore=8, spaceAfter=2,
        fontName="Helvetica-BoldOblique", textColor=colors.black)
body = S("Body", fontSize=9.5, leading=14, spaceAfter=4,
         fontName="Helvetica", textColor=colors.black, alignment=TA_JUSTIFY)
body_left = S("BodyLeft", fontSize=9.5, leading=14, spaceAfter=4,
              fontName="Helvetica", textColor=colors.black, alignment=TA_LEFT)
code_style = S("Code", fontSize=8.5, leading=12, spaceAfter=4,
               fontName="Courier", textColor=colors.black,
               backColor=colors.Color(0.93,0.93,0.93),
               borderPadding=4, leftIndent=10)
bullet = S("Bullet", fontSize=9.5, leading=13, spaceAfter=2,
           fontName="Helvetica", textColor=colors.black,
           leftIndent=16, firstLineIndent=-10)

# ── Table helper ─────────────────────────────────────────────────────────────
def make_table(headers, rows, col_widths=None):
    data = [headers] + rows
    if col_widths is None:
        n = len(headers)
        col_widths = [W / n] * n
    t = Table(data, colWidths=col_widths, repeatRows=1)
    style = TableStyle([
        ("BACKGROUND",  (0,0), (-1,0),  colors.black),
        ("TEXTCOLOR",   (0,0), (-1,0),  colors.white),
        ("FONTNAME",    (0,0), (-1,0),  "Helvetica-Bold"),
        ("FONTSIZE",    (0,0), (-1,0),  9),
        ("ALIGN",       (0,0), (-1,0),  "CENTER"),
        ("BOTTOMPADDING",(0,0),(-1,0),  6),
        ("TOPPADDING",  (0,0), (-1,0),  6),
        ("FONTNAME",    (0,1), (-1,-1), "Helvetica"),
        ("FONTSIZE",    (0,1), (-1,-1), 8.5),
        ("ALIGN",       (0,1), (-1,-1), "LEFT"),
        ("VALIGN",      (0,0), (-1,-1), "MIDDLE"),
        ("TOPPADDING",  (0,1), (-1,-1), 4),
        ("BOTTOMPADDING",(0,1),(-1,-1), 4),
        ("LEFTPADDING", (0,0), (-1,-1), 6),
        ("RIGHTPADDING",(0,0), (-1,-1), 6),
        ("ROWBACKGROUNDS",(0,1),(-1,-1),[colors.white, colors.Color(0.95,0.95,0.95)]),
        ("GRID",        (0,0), (-1,-1), 0.4, colors.black),
    ])
    t.setStyle(style)
    return t

def hr(): return HRFlowable(width="100%", thickness=0.6, color=colors.black, spaceAfter=4, spaceBefore=4)
def sp(h=6): return Spacer(1, h)
def p(text, style=body): return Paragraph(text, style)
def b(text): return Paragraph(f"&bull;&nbsp;&nbsp;{text}", bullet)
def code(text):
    escaped = text.replace("&","&amp;").replace("<","&lt;").replace(">","&gt;")
    lines = escaped.split("\n")
    result = []
    for line in lines:
        result.append(Paragraph(line if line.strip() else "&nbsp;", code_style))
    return result

# ── Build content ─────────────────────────────────────────────────────────────
story = []

# ── COVER PAGE ──────────────────────────────────────────────────────────────
story.append(sp(60))
story.append(p("CLINIC APPOINTMENT &amp; PATIENT RECORDS API", cover_title))
story.append(hr())
story.append(sp(8))
story.append(p("PHASE 1 – PROJECT ANALYSIS", cover_sub))
story.append(sp(20))
story.append(p("Software Engineering Graduate Program", cover_meta))
story.append(p("Team: Saif | Mariam | Abdullah | Safaa", cover_meta))
story.append(p("Date: June 2026", cover_meta))
story.append(PageBreak())

# ── SECTION 1: PROJECT UNDERSTANDING ────────────────────────────────────────
story.append(p("1. Project Understanding", h1))
story.append(hr())
story.append(sp(4))

story.append(p("What is this project?", h2))
story.append(p(
    "This is a <b>Clinic Appointment and Patient Records API</b> — a backend system (no user interface) "
    "that a clinic would use to manage doctors, patients, appointment scheduling, and medical visit records. "
    "Think of it as the engine behind a clinic's booking software. A front-end website or mobile app would "
    "send requests to this API, and the API handles all the business rules such as preventing a patient from "
    "booking the same doctor twice on the same day.", body))

story.append(p("Technology Stack", h2))
story.append(make_table(
    ["Component", "Technology", "Purpose"],
    [
        ["Language",     "Java 17",             "Core programming language"],
        ["Framework",    "Spring Boot 3",        "Rapid REST API development"],
        ["Database",     "H2 (file-based)",      "Embedded database — no separate server needed"],
        ["ORM",          "Spring Data JPA / Hibernate", "Java-to-database communication without raw SQL"],
        ["Validation",   "Jakarta Validation",   "Validates request data before processing"],
        ["Build Tool",   "Apache Maven",         "Dependency management and project build"],
    ],
    col_widths=[3.5*cm, 5.5*cm, W - 9*cm]
))
story.append(sp(8))

# ── SECTION 2: EXISTING FEATURES ────────────────────────────────────────────
story.append(p("2. Existing Features", h1))
story.append(hr())
story.append(sp(4))

story.append(p("Doctor Management", h2))
for item in [
    "Add a new doctor (name, specialty, working hours)",
    "Get a list of all doctors",
    "Get details for one specific doctor",
    "Generate available 30-minute appointment time slots for a doctor on a given date",
    "View a doctor's full schedule for a specific date",
]:
    story.append(b(item))

story.append(p("Patient Management", h2))
for item in [
    "Register a new patient (name, date of birth, phone number)",
    "Get a list of all patients",
    "Get details for one specific patient",
    "View a patient's full visit history (all diagnoses and prescriptions)",
]:
    story.append(b(item))

story.append(p("Appointment Management", h2))
for item in [
    "Book an appointment (links a patient to an available time slot)",
    "Cancel a booked appointment (releases the slot for others to use)",
    "Reschedule an appointment (moves it to a different slot)",
    "View details of a specific appointment",
]:
    story.append(b(item))

story.append(p("Visit / Medical Records", h2))
for item in [
    "Record a visit after an appointment is completed (stores diagnosis and prescription)",
]:
    story.append(b(item))
story.append(sp(8))

# ── SECTION 3: BUSINESS WORKFLOW ────────────────────────────────────────────
story.append(p("3. Business Workflow", h1))
story.append(hr())
story.append(sp(4))

story.append(p(
    "The following describes the complete end-to-end flow of the system, "
    "from registering a doctor through to recording a completed patient visit.", body))

story.append(p("End-to-End Process", h2))
steps = [
    ("Step 1", "Register a Doctor", "Doctor is created with a name, specialty, and working hours (e.g., 09:00–17:00)"),
    ("Step 2", "Generate Appointment Slots", "System auto-creates 30-minute slots for a chosen date (09:00, 09:30, 10:00 … 16:30)"),
    ("Step 3", "Register a Patient", "Patient profile created with name, date of birth, and phone number"),
    ("Step 4", "Patient Books an Appointment", "Patient picks an available slot — system enforces no double-booking"),
    ("Step 5", "Appointment Day", "Patient may cancel or reschedule before the appointment time"),
    ("Step 6", "Doctor Records the Visit", "Diagnosis and prescription saved; appointment status becomes COMPLETED"),
    ("Step 7", "Patient History Available", "All past visits are retrievable per patient at any time"),
]
story.append(make_table(
    ["Step", "Action", "Description"],
    steps,
    col_widths=[1.8*cm, 4.8*cm, W - 6.6*cm]
))
story.append(sp(8))

story.append(p("Appointment Status Flow", h2))
for line in [
    "BOOKED  →  (cancelled)    →  CANCELLED",
    "BOOKED  →  (rescheduled)  →  RESCHEDULED  →  new BOOKED",
    "BOOKED  →  (visit saved)  →  COMPLETED",
]:
    story.append(p(line, code_style))
story.append(sp(8))

# ── SECTION 4: ARCHITECTURE REVIEW ──────────────────────────────────────────
story.append(p("4. Project Architecture Review", h1))
story.append(hr())
story.append(sp(4))

story.append(p(
    "The project follows the standard <b>Layered Architecture</b> pattern, which is the industry standard "
    "for Spring Boot REST APIs. Each layer has a single responsibility and only communicates with the layer "
    "directly below it.", body))

story.append(make_table(
    ["Layer", "Files", "Responsibility"],
    [
        ["Controller Layer", "3 controllers", "Receives HTTP requests, returns HTTP responses"],
        ["Service Layer",    "5 services",    "Contains all business rules and logic"],
        ["Repository Layer", "5 repositories","Handles all database read/write operations"],
        ["Database Layer",   "H2 File (5 tables)", "Persists all data between application restarts"],
    ],
    col_widths=[4*cm, 4*cm, W - 8*cm]
))
story.append(sp(6))

story.append(p("Additional Architectural Components", h2))
story.append(make_table(
    ["Component", "Description"],
    [
        ["DTOs (Data Transfer Objects)", "Separate input structures from database entities — prevents over-posting attacks"],
        ["Entities",                     "Java classes that map directly to database tables via JPA annotations"],
        ["Exception Handling",           "Centralized via @RestControllerAdvice — all errors produce consistent JSON responses"],
        ["Enums",                        "AppointmentStatus enum enforces valid status values at the type level"],
    ],
    col_widths=[5.5*cm, W - 5.5*cm]
))
story.append(sp(8))

# ── SECTION 5: DATABASE REVIEW ───────────────────────────────────────────────
story.append(p("5. Database Review", h1))
story.append(hr())
story.append(sp(4))

story.append(p("Tables and Their Purpose", h2))
story.append(make_table(
    ["Table", "Purpose"],
    [
        ["doctors",            "Stores doctor profiles — name, specialty, and working hours"],
        ["patients",           "Stores patient profiles — name, date of birth, phone"],
        ["appointment_slots",  "Time slots generated per doctor per date (30 minutes each)"],
        ["appointments",       "Bookings that link patients to time slots, with status tracking"],
        ["visits",             "Medical records created after a completed appointment"],
    ],
    col_widths=[5*cm, W - 5*cm]
))
story.append(sp(6))

story.append(p("Entity Relationships", h2))
story.append(make_table(
    ["Relationship", "Type", "Description"],
    [
        ["Doctor → Appointment Slots",       "One-to-Many",  "One doctor can have many time slots across dates"],
        ["Patient → Appointments",           "One-to-Many",  "One patient can have many appointments over time"],
        ["Appointment Slot → Appointment",   "One-to-One",   "Each slot can only be booked once"],
        ["Appointment → Visit",              "One-to-One",   "Each appointment produces at most one visit record"],
        ["Appointment → Appointment (self)", "One-to-One",   "Rescheduled appointment links to the new replacement appointment"],
    ],
    col_widths=[5.5*cm, 2.8*cm, W - 8.3*cm]
))
story.append(sp(6))

story.append(p("Database Configuration", h2))
for line in [
    "spring.datasource.url=jdbc:h2:file:./data/clinicdb",
    "spring.datasource.driverClassName=org.h2.Driver",
    "spring.jpa.hibernate.ddl-auto=update",
    "spring.h2.console.enabled=true",
    "spring.h2.console.path=/h2-console",
]:
    story.append(p(line, code_style))
story.append(sp(8))

# ── SECTION 6: CODE STRUCTURE REVIEW ────────────────────────────────────────
story.append(p("6. Code Structure Review", h1))
story.append(hr())
story.append(sp(4))

story.append(p("Package Layout", h2))
story.append(make_table(
    ["Package", "Files", "Description"],
    [
        ["controller/", "3 files", "HTTP entry points — maps URLs to service calls"],
        ["dto/",        "6 files", "Input/request models for each operation"],
        ["entity/",     "5 files + 1 enum", "JPA-mapped database table models"],
        ["exception/",  "4 files", "Custom exceptions and global error handler"],
        ["repository/", "5 files", "Spring Data JPA interfaces for DB access"],
        ["service/",    "5 files", "All business logic — the core of the application"],
    ],
    col_widths=[3.5*cm, 3.5*cm, W - 7*cm]
))
story.append(sp(6))

story.append(p("API Endpoint Summary", h2))
story.append(make_table(
    ["Method", "Endpoint", "Description"],
    [
        ["POST",  "/api/doctors",                                "Create a new doctor"],
        ["GET",   "/api/doctors",                                "List all doctors"],
        ["GET",   "/api/doctors/{id}",                           "Get a specific doctor"],
        ["POST",  "/api/doctors/{id}/slots",                     "Generate time slots for a doctor"],
        ["GET",   "/api/doctors/{id}/schedule?date=YYYY-MM-DD",  "Get doctor's schedule for a date"],
        ["POST",  "/api/patients",                               "Register a new patient"],
        ["GET",   "/api/patients",                               "List all patients"],
        ["GET",   "/api/patients/{id}",                          "Get a specific patient"],
        ["GET",   "/api/patients/{id}/history",                  "Get a patient's visit history"],
        ["POST",  "/api/appointments",                           "Book an appointment"],
        ["GET",   "/api/appointments/{id}",                      "Get appointment details"],
        ["POST",  "/api/appointments/{id}/cancel",               "Cancel an appointment"],
        ["POST",  "/api/appointments/{id}/reschedule",           "Reschedule an appointment"],
        ["POST",  "/api/appointments/{id}/visit",                "Record a visit (diagnosis + prescription)"],
    ],
    col_widths=[1.8*cm, 7.5*cm, W - 9.3*cm]
))
story.append(sp(8))

# ── SECTION 7: STRENGTHS ─────────────────────────────────────────────────────
story.append(p("7. Strengths of Current Implementation", h1))
story.append(hr())
story.append(sp(4))

story.append(make_table(
    ["Strength", "Explanation"],
    [
        ["Clean layered architecture",     "Controller → Service → Repository separation is correctly implemented"],
        ["Custom exception handling",      "GlobalExceptionHandler gives consistent, predictable error responses"],
        ["Business rule validation",       "Double-booking prevention, past-date checks, and working hour validation are present"],
        ["Transactional safety",           "@Transactional is used where multi-step database changes need to succeed together"],
        ["Enum for appointment status",    "AppointmentStatus enum prevents invalid or typo-based status values"],
        ["DTOs for request inputs",        "Input bodies use DTO classes — entities are not directly exposed"],
        ["Automatic slot generation",      "30-minute slots are generated programmatically based on the doctor's working hours"],
        ["Rescheduling history preserved", "Old appointment is linked to the new one — a full audit trail is maintained"],
    ],
    col_widths=[5.5*cm, W - 5.5*cm]
))
story.append(sp(8))

# ── SECTION 8: WEAKNESSES ─────────────────────────────────────────────────────
story.append(p("8. Weaknesses of Current Implementation", h1))
story.append(hr())
story.append(sp(4))

story.append(p(
    "The following issues were identified during the Phase 1 analysis. "
    "Each will be investigated in detail during Phase 2 – Issue Identification.", body))
story.append(sp(4))

story.append(make_table(
    ["#", "Weakness", "Severity"],
    [
        ["1",  "No update or delete operations for doctors or patients",                   "Medium"],
        ["2",  "No authentication or authorization — any caller can use any endpoint",     "High"],
        ["3",  "H2 embedded database is not suitable for production use",                  "Medium"],
        ["4",  "Visit time check has no timezone handling — could produce incorrect results", "Medium"],
        ["5",  "No pagination on list endpoints — could return thousands of records",      "Medium"],
        ["6",  "Entities are returned directly in responses — no response DTOs",           "Medium"],
        ["7",  "Phone number field has no format validation",                              "Low"],
        ["8",  "Some fields missing @NotBlank / @NotNull validation annotations",         "Low"],
        ["9",  "Slot generation has no maximum future date constraint",                    "Low"],
        ["10", "No API documentation (Swagger / OpenAPI) provided",                       "Medium"],
        ["11", "System clock dependency for visit eligibility check is fragile",          "Medium"],
        ["12", "No soft-delete — cancelled records permanently visible in all queries",   "Low"],
        ["13", "Only one trivial test exists — no real unit or integration tests",        "High"],
        ["14", "No search or filter capability on patient or doctor list endpoints",      "Low"],
        ["15", "Doctor specialty is free-text — no standardization or controlled list",   "Low"],
    ],
    col_widths=[0.8*cm, W - 3.3*cm, 2.5*cm]
))
story.append(sp(12))
story.append(hr())
story.append(sp(4))
story.append(p(
    "End of Phase 1 – Project Analysis. "
    "Awaiting team approval to proceed to Phase 2 – Issue Identification.", body_left))

doc.build(story)
print(f"PDF generated: {OUTPUT}")
