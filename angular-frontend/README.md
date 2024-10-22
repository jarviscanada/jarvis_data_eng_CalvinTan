
# Introduction
Developed a front-end web application using Angular. Allows user to view the trading profiles of multiple traders in a list. 
New users can be added and existing traders can have their information updated. 
Trader data and stock quote data are pulled from a mock server running on NodeJS using a HttpClient. 
The program is containerized using Docker.

# Quick Start
build an image by running the included Dockerfile 
```
docker build -t user/angular-trading-app .
```
run docker container
```
docker run -d -it -p 80:80/tcp --name angular-trading-app user/angular-trading-app:latest
```
access app by entering `localhost` into a browser

# Implemenation
The main page is a dashboard listing out a list of traders. 
The user can add, delete, or edit traders through the respective buttons at the top of the dashboard and next to the traders.
Adding a new trader opens a new dialog with a form asking for the required information.
From the dashboard, each trader can be view in a separate account page. 
From this page the user can withdraw or deposit into the traders' account. 
From the navigation bar, a quotes page could also be access with up to date quote information. 

## Architecture
![](./resources/angular%20trading%20app.drawio.png)

# Test
The application was tested manually by testing out features as they are implemented. 
This was done through the use of `ng serve` which allows for changes to be seen in real time

# Deployment
The application is packed into a docker image and available through dockerhub. 
Users can also build the image themselves through the included Dockerfile.

# Improvements
Write at least three things you want to improve 
e.g. 
- Validation of information when adding or editing traders
- Allow for specific quotes to be searched 
- Allow for tables to be filter and sorted by various criteria 