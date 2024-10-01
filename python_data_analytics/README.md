# Introduction

London Gift Shop, a gift ware retailer, has not seen any revenue growth in the last view years. As
such, they want to use modern data engineering techniques to determine ways of improving upon their
revenue. However, since they lack the required infrastructure to perform this analysis themselves,
they contacted the Jarvis group to utilize their services.

This project is a proof of concept for London Gift Shop, showcasing how data engineering techniques
can be used to perform business analyses. As this is just a proof of concept, it was not possible to
directly incorporate it into their development environment. Instead, the team at London Gift Shop
compiled some sample data into an SQL file for the proof of concept to work with. The SQL file was
loaded into a denormalized PSQL database, which acts as data warehouse for OLAP purposes. The
analytics themselves were performed in a Jupyter notebook, using Python. Specifically, DataFrames
from the Pandas library were used to manipulate the data, while matplotlib was used for plotting.

# Project Architecture
![Architecture Diagram](./.assets/architecture_diagram.png)

London Gift Shop's web application consists of a Microsoft Azure resource group, using Azure's
Content Delivery Network to handle the front-end stack, and Azure's API Management to handle the
back-end. The back end itself is a microservice architecture, using a scalable AKS cluster for the
actual processing, and a single SQL server for OLTP data. It is from this SQL server that the
sample data used in the proof of concept was pulled. As mentioned above, the sample data was stored
in a PSQL database separate from the Azure group, and the data analysis was performed in a Jupyter
notebook.

# Data Analytics and Wrangling
The proof of concept showcasing the data analytics and wrangling can be found [here](./retail_data_analytics_wrangling.ipynb).
Of particular note are the results of the RFM segmentation found at the bottom of the notebook. In
particular, there are three segments which deserve attention. The "Can't Lose" segment contains
customers who previously made large purchases, but have since stopped. In order to regain their
patronage, a targeted campaign should be ran for them, making specific use of their previously
purchased products, while also including new ones similar to them. The "Hibernating" segment is
similar to the "Can't Lose" segment, in that it includes customers who purchased in the past, but
were not major contributors to the revenue. A campaign similar to the one ran for the "Can't Lose"
segment can be ran to entice them into becoming more frequent customers. Finally, the "Champions"
segment contains repeat customers who are responsible for a large portion of the revenue. While not
at as much of a risk of leaving as the "Can't Lose" segment, it would still be prudent to run a
campaign for them to ensure they continue with their repeat patronage.

# Improvements
1. Look into other alternatives to RFM for measuring customer value
2. Find a way to chart all the segment groups
3. Investigate into more ways the different segments can be targeted to increase revenue