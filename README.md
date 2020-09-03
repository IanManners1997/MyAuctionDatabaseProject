# MyAuction - Phase 2 Submission
Project repository for 1555.

To Run: 

Open the MyAuction.java file. Inside the main() function, update the username and password
field of the call to DriverManager.getConnection() to your oracle username and password.
Then, in a separate terminal, run "sqlplus" and run "@schema.sql", "@trigger.sql", then "@insert.sql" in that 
order. Finally, in the terminal that is not in sqlplus, compile MyAuction.java by doing "javac MyAuction.java"
then run it with "java MyAuction".