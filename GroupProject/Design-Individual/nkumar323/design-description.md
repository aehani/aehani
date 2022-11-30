# Assignment 5 : Software Design
#### nkumar323@gatech.edu | CS6300 | Spring 2022


## 1. Introduction

#### 1.1 Purpose
This document describes the design for a **single-user** job offer comparison app. It also keeps track of the data needed to properly describe architecture and system design and helps facilitate devlopment.

#### 1.2 Assumptions
Below assumptions were made while working on this design:
- The design only depicts the application's underlying logic and data structures, not the GUI classes.
- There is a single system running the app ( no communication or saving between devices is required).
- The MainMenu class represents the "entry point" of the application and controls the applications GUI based on user's action on the menu. It triggers the "createActivity()" and brings up the menu options to the user.
-  All class attributes are accessed using public getter, setter methods and get initiliazed by the contructors which is not shown in the design representation.
-  All class attributes are initialized with `null` unless declared with a non null default value. 
-  We also assume that user is familiar with the menu options and is aware of his task. For Ex- If entering a new job, user selects "Enter Current job details" from the menu shown.
-  Also, we assume that the end user is already employed or has one or more job offers in hand while using the application.
-  Also, there will only be a single instance of MainMenu and application with which the user will interact.
-  There are some read only attributes in Job details that are used to track any existing jobs or for calculating other properties which are derived using other core attributes of the class.

#### 1.3 Constraints
Design constraints are noted in the UML diagram with bracketed annotations. The read only attributes cannot be entred or edited by user. They are derived from teh values entered for a job.

## 2. Requirements
#### 2.1 Main Menu
When the app is started, the user is presented with the main menu which allows the user to select option to
(1) enter or edit current job details, 
(2) enter job offers, 
(3) adjust the comparison settings, or 
(4) compare job offers (disabled if no job offers are stored previously).

To realize this requirement, I have added a `MainMenu` class that has the actions to Add/edit job details/ Adjust
comprison settings. This class is the "entry point" for the application.

The comparison of job offers is enabled only when there are multiple job offers and no current jobs, or there is a job offer and a current job exists.

By clicking on the appropriate menu option the user can change the UI of the application and will be presented with a screen to perform operations as needed. `enterCurrentJobDetails` and `editCurrentJobDetails` methods help add a new job details and edit an existing job detail respectively by navigating user to an UI where these details can be supplied.

#### 2.2 Enter/Edit Current Job Details
##### 2.2.1 User will be shown an interface with follwing details to enter(if first time) or edit (if a job exists)
- Title
- Company
- Location (entered as city and state)
- Cost of living in the location (expressed as an index)
- Yearly salary
- Yearly bonus
- Retirement benefits (as percentage matched) (Given as Integer 0-100)
- Relocation stipend
- Restricted stock unit award (expressed as a lump sum vested over 4 years)

These attribute are shown as part of `JobDetails` class which inherits the Job interface having attributes like 
title, company and location. The abstract methods are implemented by the Job Details class.

To edit an existing job, JobDetails.editJobDetails is invoked which uses the jobId value to edit the existing data.


##### 2.2.2 Save the job details or cancel and exit without saving. 

This is depicted by the 'save' and 'cancel' methods on `JobDetails` class. The actions from each method, shows the user being navigategd to main menu.

#### 2.3 Enter Job Offers

A user is shown an interface to enter all of the details of a offer, which are the same ones listed in sub section 2.2.1 for teh current job. This is represented by the `JobOffer` class inheriting the `JobDetails` class thus resusing it's attributes to create a new job offer instance.

Since, the `JobOffer` class is a child of `JobDetails`, it can reuse the save and cancel methods of the parent class and behave in the same manner and navigate the user to main menu when done.

#### 2.4 Adjusting comparison settings

The user can assign numerical weights to the "yearly salary", "yearly bonus", "Retirement benefits", "Relocation stipend", "Restricted stock unit award". If no weights are assigned all factors are considered equal.

`MainMenu.adjustComparisonSetting()` invokes the adjust comparison settings.
The Adjustment comparison settings are represented by `ComparisonSettings` class in the design and contins the attributes as mentioned above. When there is no weight assignment, the class is initialized with initial values having weight 1 and hence all factors are considered equally valued. 

#### 2.5 Compare Job Offers
User can compare between job offers only when below conditions are met:
1. There is at least one job offer and a current job available in system.
2. There are two or more job offers, if there is no current job details available.

When choosing to compare job offers, the user is shown a table with title and company ranked from best to worst and highlights the current job if present. Once at compare screen, user is shown option to perform new comparison or go back to the main menu. `CompareOffers.newCompare()` starts a fresh comparison.

This functionality is shown in `CompareOffers` class which uses the `JobOffer` and `ComparisonSettings` to make the comparison and rank the jobs from best to worst and displays in a table. Job ranking method is explained in [Job ranking](#41-definitions-and-abbreviations).

#### 2.6 Intuitive User Interface
This is not represented in my design, as it will be handled entirely within the GUI implementation.


## 3 User Interface Design
The user starts with main menu in the application and can move to edit/add job details, compare job offers  and adjust comparison settings based on the action selected from main menu. Also, from any of the UI screen, the user can come back to main menu on clicking "cancel". The screens are not in scope of teh design as it will be handles while implementing GUI.

## 4 Appendices and References


#### 4.1 Definitions and Abbreviations
When ranking jobs, a job’s score is computed as the weighted sum of:

```
AYS + AYB + RS + (RPB * AYS / 100) + (RSUA / 4)

where:
AYS = yearly salary adjusted for cost of living
AYB = yearly bonus adjusted for cost of living
RBP = retirement benefits percentage
RS = relocation stipend
RSUA = restricted stock unit award
```

The rationale for the _RSUA subformula_ is:
- value of a restricted stock unit award vests over 4 years
- average value of the restricted stock unit award per year (RSUA / 4)

For ex-  if the weights are 2 for AYS, 2 for RS, and 1 for all other factors, the score would be:

`2/7 * AYS + 1/7 * AYB + 2/7 * RS + 1/7 * (RPB * AYS / 100) + 1/7 * (RSUA / 4)`
