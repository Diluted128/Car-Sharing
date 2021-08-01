# Car-Sharing
## About
Program simulating renting cars by the carsharing company based on H2 database. More informations about H2 database are [here](https://www.tutorialspoint.com/h2_database/index.htm). The program allows to: 
- Create company and its own cars
- Create a user and rent a car from chosen company.
- Return a car.

## Program structure
In this project was used Data Access Object pattern. More info [here](https://www.tutorialspoint.com/design_pattern/data_access_object_pattern.htm).

![Untitled Diagram](https://user-images.githubusercontent.com/67759414/127780975-c5108695-7f6d-411a-9b7e-0dbe6870011b.png)
'Database operations' interface is Data Access Object Interface.

'Data Access Management' is Data Access Object concrete class. This class is responsible to get data from a database. 

'Company','Customer', 'Car' are Object Value Classes.

'Database Connection' is a separated class to get connection with already created database.
## Database structure
![Untitled Diagram](https://user-images.githubusercontent.com/67759414/127780222-da4d8780-6f5c-4768-9597-ba9e3a437d95.jpg)
## Note
I suggest moving RENTED_CAR_ID column to CAR table if customer could rent more then one car.
