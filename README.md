# Library Management System

A Java application for managing a library database with support for books, authors, readers, and sections. The system provides full CRUD operations, audit logging, and persistent data storage.

## Features

- Book management (add, view, update, delete)
- Author management and published works tracking
- Reader management
- Section-based organization of books
- H2 database integration for persistent storage
- Audit logging of all operations in CSV format
- Generic CRUD service layer for consistent data access

## Technologies

- Java
- H2 Embedded Database
- Maven
- CSV (audit logs)

## Getting Started

1. Build the project using Maven
2. Run the main class located at `withDB/proiect.java`
3. Use the menu-driven interface to manage library data
4. All operations are logged in `audit.csv`

## Notes

- The `noDB` folder contains a version of the application without database functionality, provided for reference
- The main implementation uses the H2 database and includes comprehensive audit logging
