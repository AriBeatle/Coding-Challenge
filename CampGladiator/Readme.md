REST API coding challenge for Camp Gladiator, by Ariel Rodriguez (arielfrodriguez@gmail.com)

Overview
########
Although the requirement was to implement only 2 endpoints, since I was delayed in providing the coding challenge I added the 5 HTTP Methods: GET, POST, PUT, PATCH, DELETE. 
I've also added the following fields to the proposed Trainer model: Zip Code, Certified Trainer and Background.

Pre-requisites
##############
1- Please install Python version 3.7 or above
2- Please ensure you have CURL installed
3- Please install Django along with its REST framework and filters, from a command line:
 pip install django
 pip install djangorestframework
 pip install django_filter
 
Testing the API
###############
This repository already provides an SQLite 3 DB that contains some sample records.
In order to run the server:
1- Go into the Django project directory trainerAPICodeChallenge
2- Execute the following command to start the Django server:
   python ./manage.py runserver
   
Open a console to execute the CURL commmands to exercise each of the following endpoints:
#1 - Create a new trainer 
curl -X POST http://localhost:8000/api/trainers/new -d first_name=Tony -d last_name=Stark -d email=ironman@marvel.com -d phone=682-605-0396 -d zip_code=90210 -d certified=True -d background="Genius. Billionaire, Playboy, Philanthropist"

#2 - Get details for a trainer
curl http://localhost:8000/api/trainers/trainer-id-000001/ 

#3 - Partially update information for a trainer
curl -X PATCH http://localhost:8000/api/trainers/trainer-id-000002/ -H "Content-Type: application/json" -d "{\"phone\":\"682-605-0399\",\"first_name\":\"Peter\",\"last_name\":\"Parker\"}"

#4 - Fully update information for a trainer
curl -X PUT http://localhost:8000/api/trainers/trainer-id-000004/ -d first_name=Stephen -d last_name=Strange -d email=dr.strange@marvel.com -d phone=682-605-0397 -d zip_code=58744 -d certified=False -d background="Master of the Mystic Arts"

#5 - Delete a trainer
curl -X DELETE http://localhost:8000/api/trainers/trainer-id-000004/
