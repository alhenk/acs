acs
===

JAVALAB PROJECT

Attendance control system. The system gets attendance records from a corresponding access control system in the form of a database table. Administrator runs user accounts, list of employees and list of RFID tags. Administrator and supervisor can get monthly or daily group reports of late arriving and leaving before events. Employee can get only individual reports.

DESCRIPTION

User opens a main page of the web application. To enter the service, user needs to have an account in the system database. Only Administrator may create new user account with the given password. User may enter the password in a presence of Administrator.

User signs in with the correct username and password. Then, the system redirects the user to a dashboard page.

 If the user account has supervisor privilege (administrator, supervisor), he or she may get group reports of the office hour violations. The reports can be monthly (for a particular month of the year) or daily (for a particular calendar date). If there is no any violation for a given time interval, the system shows empty table. 

If the user account has no supervisor privilege (employee), he or she may get only individual reports of their own late arriving or leaving before events.

The database has ATTENDANCE table, no one may write or edit it. The table has records made by CERBERUS the access control system for the whole 2013 year. Thus, it makes sense to require the violation reports within 2013 year.

Three main user roles represented by the following accounts:

•	username = admin , role = Administrator, password = 123

•	username = volozh, role = Supervisor, password = 123

•	username = morgan, role = Employee, password = 123

BRANCHES

- hello : initial hello world webapp
- servlet : simple configuration for servlets
- front : simple front controller
- test : junit plus mockito test for servlets
- jstl : jstl API and user tag
- locale: localization, SetLanguage action
- user: sign in action, user object attribute

Branch develop
- dao: db h2, sqlite
- db: h2 dependency, simple table existence check
- tags: fields (password, select, string)

