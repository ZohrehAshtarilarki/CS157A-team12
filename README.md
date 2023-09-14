# Campus Event Management System

## Project Description

The **Campus Event Management System** is a comprehensive platform designed to streamline the organization and management of events on a college campus. This README provides an overview of the project, its key features, database design, technologies used, and instructions for getting started.

## Key Features

### Event Creation and Scheduling
- Users (students, faculty, and staff) can easily create events with detailed information such as date, time, location, and event type.

### Event Registration
- Attendees can register for events they wish to attend, allowing organizers to estimate attendance and plan logistics accordingly.

### Event Promotion
- Event organizers can promote their events by sharing them on social media platforms or through email notifications to potential attendees.

### Ticketing and Check-In
- For events with limited seating or entry fees, the system manages ticketing and provides a smooth check-in process for registered attendees.

### Event Feedback and Reviews
- Attendees can provide valuable feedback and ratings for events they've attended, helping improve the quality of future events.

### Event Analytics
- Administrators can access analytics and reports on event attendance, demographics, and feedback to make data-driven decisions about campus activities.

### Calendar Integration
- Events are seamlessly integrated into a campus-wide calendar, allowing users to view upcoming events and plan their schedules conveniently.

### Role-Based Access
- Different user roles, including event organizers, attendees, and administrators, have specific permissions and access levels within the system.

## Database Design

Here is an overview of the database design:

- **Users**: Stores information about registered users.
- **Events**: Contains details about all campus events.
- **Registrations**: Tracks event registrations.
- **Reviews**: Stores feedback and ratings for events.
- **Event Types**: Stores predefined event types.

The database is managed using **MySQL Workbench**, with well-defined relationships between tables to ensure data integrity and efficient retrieval.

## Technologies Used

The **Campus Event Management System** utilizes the following technologies:

- **Database Management System (DBMS)**: MySQL Workbench
- **Front-end**: HTML, CSS, JavaScript
- **Back-end**: Java
- **Authentication and Authorization**: Implements user authentication and role-based access control.

## Getting Started

To run the Campus Event Management System locally, follow these steps:

1. Clone this repository to your local machine.
2. Set up a MySQL database using the provided SQL scripts in the `database-scripts` directory.
3. Configure the database connection in the application's configuration files (e.g., `db-config.xml`).
4. Deploy the application using a Java web server (e.g., Apache Tomcat).
5. Access the system through a web browser and start using it.

For detailed installation and configuration instructions, please refer to the project's documentation.

## Contributors

- Zohreh Ashtarilarki
- Long Nguyen

## License

This project is licensed under the [MIT License](LICENSE).

Feel free to contribute, report issues, or provide feedback to enhance the **Campus Event Management System**. We hope this system simplifies event management on your college campus. Enjoy using it!
