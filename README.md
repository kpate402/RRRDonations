# RRRDonations

RRRDonations is a project initial RRR Donations is a project aimed to streamline connecting donors 
and beneficiaries to nonprofit organizations by creating an efficient donation management platform. 
This application is trying to reach the donations and charity domain to better help the less fortunate.
We, Saahi Arumilli, Yibin Li, Kush Patel, Pawel Rozanski, created our prototype based on the original idea
created by Group 20 in Fall 2023 by developers, Fatin, Jennifer, Edgar, and Joshua. Our prototype mainly 
consists of a RRRDonations class containing all frontend elements, DatabaseUtil class which contains all functions 
relating to the database, EmailUtil class which has elements to create our email functionality, Nonprofit, 
Donor, and Beneficiary classes, which store data for our user types. Additionally, information that can be 
contained in the Nonprofit, Donor, and Beneficiary classes, is also stored in our database 

# API Keys

# Api keys needed are googles api key and rapidapi.com's key 
# google’s api key needs to be added at line 130 in RRRDonations.java
# rapidapi's api key is to be added at line 29 in DatabaseUtil.java

# Steps to run the project

# Project is a maven project made in java. It can be run by using commands mvn clean compile exec:java. Make sure 
# You have your own api keys before running the project The prototype has 3 choices at the start for each user type. Beneficiaries have to fill in a survey/login and then can 
# proceed to their profile page. This page shows their information and what charities best match their needs.There is also a wishlist where they can list their current needs and # wants, a update survey button to update the survey, and a navigation function which uses googles static maps API to give users directions to a certain charity. The Donors 
# are also similar to the Beneficiaries where they have a survey/login and then can proceed to their profile page. The difference is that Donors have a donor list instead of a 
# wish list that includes what items they wish to give away and that Donors have the option to volunteer their time. Lastly the Nonprofits can be accessed straight from the 
# home page from a dropdown menu and clicking any nonprofit pulls up all their information and has the navigation function as well. 

# Project development and iterative cycle

# Compared to the original project idea by Group 20, we expanded and added more functionality. Their original idea only had Donors and nonprofits as users. Nonprofits 
# would have associated groups/beneficiaries under them and a wish list that represents the needs of their groups. Donors could pick their favorite wishlists and nonprofits.
# Their original idea served as the base of our project and gave an idea and goal that we felt was worth following. The project's development can be divided into three main 
# releases with each release focusing on a few key elements. The first release was meant to focus on getting our initial application setup. We placed our focus on the donors 
# and beneficiaries user groups by building their initial surveys. Creating an initial profile page for them, building donation and wish lists, and adding specific functionalities for # them. The second release was focused more on the backend and developing the nonprofit side. We were able to automate the creation of nonprofit pages for each of the  # nonprofits in our network. We pulled the nonprofit information from an online API (Charity API) we found on RapidAPI.com. We then stored this information into our SQL 
# database. The data in the database would be used for nonprofit page generation. We also worked on multithreading to stop the slight delay the program was hitting when 
# sending an email. Emails are sent from the donor to a nonprofit and list information about what nonprofit they want to volunteer at and the time of day they are available as 
# well as their contact information. Lastly, we implemented route instructions and maps features by using the Google maps directions API and Google maps static API to 
# provide navigation features for our donors and beneficiaries. The third release was mainly finishing up loose ends and adding finishing touches

# Project development process and review

# The project was created as a group using agile methodology. We used a mix a Scrum and Kanban. We had weekly sprints where we used Jira to help manage our stories and # sprints. Jira also helped display the project using a Kanban board. Every week, all group members would meet once for a 1-2 hour long meeting to communicate ideas and 
# progress in the project. This is also when we examined the week’s work and the work done in the previous week to see if we could make any improvements in our 
# development process. Jira was helpful for this as it showed data on the amount of work done and the difficulty over a period of time. Jira’s visual Kanban board was also 
# helpful with its Work In Progress limits that helped us focus our efforts where they were needed.
