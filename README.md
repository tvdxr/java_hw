================================================================================
** LIBRARY MANAGEMENT SYSTEM **
================================================================================

A Java application for managing a library database with support for books, 
authors, readers, and sections. Features include full CRUD operations, audit 
logging, and data persistence.

================================================================================
** FEATURES **
================================================================================

- Book Management - Add, view, update, and delete books from the collection
- Author Management - Manage author information and their published works
- Reader Management - Track library readers and their information
- Section Organization - Organize books into different library sections
- Database Integration - H2 database for persistent data storage
- Audit Logging - Track all operations in an audit log (CSV format)
- CRUD Operations - Generic service layer for consistent data access patterns

================================================================================
** TECHNOLOGIES **
================================================================================

- Language: Java
- Database: H2 (embedded database)
- Build Tool: Maven
- Data Format: CSV for audit logs

================================================================================
** GETTING STARTED **
================================================================================

1. Build the project with Maven
2. Run the main class from withDB/proiect.java
3. Use the menu-driven interface to manage library data
4. All changes are logged in audit.csv

================================================================================
** NOTES **
================================================================================

The noDB folder contains a version without database functionality for 
reference. The main implementation uses the H2 database for data persistence 
and includes comprehensive audit logging.
