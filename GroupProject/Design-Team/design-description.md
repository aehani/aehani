# CS6300 | Spring | Group Project : Deliverable 1
### Group 054
  * #### Ehani, Ahsan | aehani3 | aehani3@gatech.edu
  * #### Kumar, Niraj | nkumar323 | nkumar323@gatech.edu
  * #### Schmid, Brittnay | bschmid3 | bschmid3@gatech.edu
  * #### Sullivan, Joseph | jsullivan73 | jsullivan73@gatech.edu

## Requirements

1. When the app is started, the user is presented with the main menu, which allows the user to (1) enter or edit current job details, (2) enter job offers, (3) adjust the comparison settings, or (4) compare job offers (disabled if no job offers were entered yet).

---

***To realize this requirement, our design contains an OfferComparisonApp class. This class will be the entry point to the application. The design has a JobDetails class which stores all details of a job. The OfferComparisonApp has many JobDetails. The OfferComparisonApp class can create JobDetails via the createNewJob() method. The JobDetails class provides an editJobDetails() method for editing a job. The JobDetails class contains a boolean, isCurrentJob, which is used to indicate if a job is the current job. The createNewJob() method is able to set this boolean. It is assumed that there is only one current job. All of this satisfies (1) and (2) of the requirement. To satisfy (3) of the requirement, our design contains a ComparisonSettings class which belongs to the OfferComparisonApp class. Like wise the OfferComparisonApp has one ComparisonSettings. The OfferComparisonApp class will be able to set all the comparison settings through the ComparisonSettings class. To satisfy (4) of the requirement, the OfferComparisonApp class has a compareJobOffers() method. This method will compare at least two jobs if two or more jobs are in the system.***

---

2. When choosing to enter current job details, a user will:
    - Be shown a user interface to enter (if it is the first time) or edit all of the details of their current job, which consist of:
        - Title
        - Company
        - Location (entered as city and state)
        - Cost of living in the location (expressed as an index)
        - Yearly salary
        - Yearly bonus
        - Retirement benefits (as percentage matched) (Given as Integer 0-100)
        - Relocation stipend
        - Restricted stock unit award (expressed as a lump sum vested over 4 years)
---

***To accomplish this, our design provides the OfferComparisonApp class with a currentJob variable, which is of the class JobDetails. The OfferComparisonApp class contains the createNewJob() method for creating a new job. In this method, the isCurrentJob boolean can be set along with all the other required variables. The OfferComparisonApp class provides the editJobDetails() method for editing the variables of a job based on the given ID. We have a Location class, which contains the state and the cost of living.***

---

    - Be able to either save the job details or cancel and exit without saving, returning in both cases to the main menu.

---

***A user can choose to save the JobDetails with the OfferComparisonApp.saveJob() method or cancel, which will return the user to the main menu without saving.***

---

3. When choosing to enter job offers, a user will:
    
    - Be shown a user interface to enter all of the details of the offer, which are the same ones listed above for the current job.

---

***A new JobDetails instance will be generated via the OfferComparisonApp.createNewJob(false) method. The JobDetails class contains all of the attributes listed above.***

---
   
    - Be able to either save the job offer details or cancel.

---

***The OfferComparisonApp class provides a `saveJob()` method or the user can just cancel the request and return to the main menu without saving.***

---
    
    - Be able to (1) enter another offer, (2) return to the main menu, or (3) compare the offer (if they saved it) with the current job details (if present).

---

***If a JobDetails instance is saved and the `isCurrentJob` boolean is set to false, the OfferComparisonApp.saveJob() method will provide a trigger to show a menu with these options.***

---


4. When adjusting the comparison settings, the user can assign integer weights to:
    - Yearly salary
    - Yearly bonus
    - Retirement benefits
    - Relocation stipend
    - Restricted stock unit award

---

***We provide the `ComparisonSettings` class which has these variables. The default values for all the variables will be set to 7 so as to not affect the initial weighting of the score calculation of the `JobDetails` class.***

---


5. When choosing to compare job offers, a user will:
    - Be shown a list of job offers, displayed as Title and Company, ranked from best to worst (see below for details), and including the current job (if present), clearly indicated.

---

***We give the `JobDetails` class the `calculateScore` method so the `JobComparisonAnalysis` class can rank all of its `JobDetails` instances. The user interface will be able to use the isCurrentJob boolean of the JobDetails class to highlight the current job.***

---

    - Select two jobs to compare and trigger the comparison.

---

***The OfferComparisonApp class provides the compareJobOffers() method, which generates a `JobComparisonAnalysis`. This class contains the necessary attributes and methods to compare the JobDetails of multiple jobs.***

---

    - Be shown a table comparing the two jobs, displaying, for each job:
        - Title
        - Company
        - Location
        - Yearly salary adjusted for cost of living
        - Yearly bonus adjusted for cost of living
        - Retirement benefits
        - Relocation stipend
        - Restricted stock unit award

---

***These attributes are available to display in the JobDetails class, which are ranked on demand in the JobComparisonAnalysis class.***

---

    - Be offered to perform another comparison or go back to the main menu.

---

***Since this is a UI detail, it is not represented in this design.***

---


6. When ranking jobs, a job’s score is computed as the weighted sum of:

    AYS + AYB + RS + (RPB * AYS / 100) + (RSUA / 4)

    where:

    AYS = yearly salary adjusted for cost of living

    AYB = yearly bonus adjusted for cost of living

    RBP = retirement benefits percentage

    RS = relocation stipend

    RSUA = restricted stock unit award

    The rationale for the RSUA subformula is:

    value of a restricted stock unit award vests over 4 years average value of the restricted stock unit award per year (RSUA / 4)

    For example, if the weights are 2 for the yearly salary, 2 for relocation stipend, and 1 for all other factors, the score would be computed as:

    2/7 * AYS + 1/7 * AYB + 2/7 * RS + 1/7 * (RPB * AYS / 100) + 1/7 * (RSUA / 4)

---

***We provide the `JobDetails` class with the `calculateScore` method. This method will use the above calculation for determining the score.***

---


7. The user interface must be intuitive and responsive.
---

***Since this is UI detail, it is not represented in this design.***

---

8. For simplicity, you may assume there is a single system running the app (no communication or saving between devices is necessary).
---

***Since this is an implementation detail, it is not represented in this design.***

---
