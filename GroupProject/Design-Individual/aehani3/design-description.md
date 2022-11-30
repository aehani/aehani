Design Description


Based on the requirements the following design descriptions aids in expressing the UML Class Diagram as self-contained:

1.Initialization/Entry point: 
	I created a Class called MainMenu to show the Initialization of the application, this allows for an entry point for the user to enter current job details, new offers, and compare offers.

2.Current Job:
	Current Job is represented as a class holding different attributes such as simple and complex attributes such as title and location. Location is complex attribute containing two components city and state which is shown as a datatype at the bottom of the diagram. Cost of living is show as a positive integer since it is based on the expantisan price index value. Methods allows for operations such as saving and exiting from the current job information. 

3.Job offers:
	Job offer is shown as a class for inputting information into application by the user based on the jobs that have been offered to the user. The attributes are the same as current Job. Although the methods are unique in perspective to the job offers interface, hence saving the offer details, entering another offer, canceling an offer, and comparing the offer. 

4.Comparison Settings:
	Comparison settings are shown as an associative class since, these represent the comparison settings attributed to the Job comparison class viewed by the user. They rank the attributes to display based on integer scale of 1 representing most value and as the integer increases the ranking decreases in significance. All attributes are set to default value of 0 holding equal value. 

5.Job Comparison
	Is a class that utilizes the association class of comparison settings to display attributes ranked based on current job, and other job offers (required 2 comparison jobs). Methods of rankJob calculates the ranking based on the formula:  AYS + AYB + RS + (RPB * AYS / 100) + (RSUA / 4). 
