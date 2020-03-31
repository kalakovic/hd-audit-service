# hd-audit-service
Audit service

Technologies:
Java
Spring Boot
JPA
Hibernate
MySQL

Service provides auditing on database entities that are annoteated with @HDAudit and logs INSERT, UPDATE and DELETE 
actions into table AUDIT_ACTION for all entity properties except those that are annoteated with HDNotAudit. 

It is done using event listeners that offers Envers, a module implementing auditing and versioning of persistent classes.
