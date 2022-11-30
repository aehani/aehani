# Assignment 5: Software Design

# Requirements

When the app is started, the user is presented with the main menu, which allows the user to (1) enter or edit current job details, (2) enter job offers, (3) adjust the comparison settings, or (4) compare job offers (disabled if no job offers were entered yet).

	The landing point for a user will be a singleton OfferComparisonClass that will store a list of JobDetails for current and prospective jobs and handle creating and modifying each JobDetails instance within a session. It will also store the ComparisonSettings used to generate a JobComparisonAnalysis based on selected JobDetails.

When choosing to enter current job details, a user will:

Be shown a user interface to enter (if it is the first time) or edit all of the details of their current job, which consist of:

Title

Company

Location (entered as city and state)

Cost of living in the location (expressed as an [index](https://www.expatistan.com/cost-of-living/index/north-america))

Yearly salary

Yearly bonus

Retirement benefits (as percentage matched) (Given as Integer 0-100)

Relocation stipend

Restricted stock unit award (expressed as a lump sum vested over 4 years)

	When the user chooses to enter their current job details via the OfferComparisonClass.createNewJob(true) method, a new JobDetails class will be generated (if currentJob is null) and the user will have the opportunity to enter/modify the details of the current job as listed above.

Be able to either save the job details or cancel and exit without saving, returning in both cases to the main menu.

	The user can choose to save the currentJob (which will call the OfferComparisonClass.saveJob() method that will set the OfferComparisonClass.currentJob attribute) or just return to the main menu.

When choosing to enter job offers, a user will:

Be shown a user interface to enter all of the details of the offer, which are the same ones listed above for the current job.

	When the user chooses to enter a new job offer, the OfferComparisonClass.createNewJob(false) will be called, which will create a new JobDetails instance. The user will be able to set the job details as listed above.

Be able to either save the job offer details or cancel.

	The user can choose to save the new job offer (which will call the OfferComparisonClass.saveJob() method and add the new JobDetails to OfferComparisonClass.jobOffers attribute) or just return to the main menu.

Be able to (1) enter another offer, (2) return to the main menu, or (3) compare the offer (if they saved it) with the current job details (if present).

	If the user chooses to enter another offer, a similar process to initially creating a new job offer will occur. This process will be on a loop until the user chooses to return to the main menu or compare the offer with the current job details.

	If the OfferComparisonApp.currentJob is not null and the user chooses to compare the saved offer with the current job details, the OfferComparisonApp.compareJobOffers() method will be called with the newly-saved job offer and the OfferComparisonApp.currentJob attribute. This will return a JobComparisonAnalysis based on the ComparisonSettings and will present the details and functions associated with that.

	If the user chooses to return to the main menu at any point while the application is running, the main menu view will be returned and is not represented in the UML class diagram since it is strictly UI related.
  

When adjusting the comparison settings, the user can assign integer weights to:

Yearly salary

Yearly bonus

Retirement benefits

Relocation stipend

Restricted stock unit award

	A ComparisonSettings class will be used to store the integer weights associated with the details listed above. A singleton instance of ComparisonSettings will be stored as the OfferComparisonApp.settings attribute.

If no weights are assigned, all factors are considered equal.

	The initial values of the ComparisonSettings attributes will be 1.


When choosing to compare job offers, a user will:

Be shown a list of job offers, displayed as Title and Company, ranked from best to  worst (see below for details), and including the current job (if present), clearly indicated.

	When the user chooses to compare job offers, the  OfferComparisonApp.compareJobOffers() method will be called with all of the existing JobDetails instances and a JobComparisonAnalysis will be returned. In the constructor of JobComparisonAnalysis, the JobComparisonAnalysis.calculateRankings() method will be called, which will rank the given job offers and set the JobComparisonAnalysis.rankedJobs attribute. The JobComparisonAnalysis.rankedJobs attribute will be iterated over in the view to display the Title and Company of each job offer from the JobDetails class.

Select two jobs to compare and trigger the comparison.

	When a user selects 2 jobs to compare, the JobDetails from each selected job will be used from the JobComparisonAnalysis.rankedJobs attribute.

Be shown a table comparing the two jobs, displaying, for each job:

Title

Company

Location

Yearly salary adjusted for cost of living

Yearly bonus adjusted for cost of living

Retirement benefits

Relocation stipend

Restricted stock unit award

	The attributes from JobDetails for the two selected jobs will be used to display the details listed above (including the JobDetails.calculateAYS() and JobDetails.calculateAYB() methods used to determine the yearly salary/bonus adjusted for cost of living).

Be offered to perform another comparison or go back to the main menu.

	The user can deselect and select other job offers for comparison. When 2 offers are selected, the JobDetails for those offers will be displayed, similar to how the initially selected offers were displayed.


When ranking jobs, a job’s score is computed as the weighted sum of:  
  
AYS + AYB + RS + (RPB * AYS / 100) + (RSUA / 4)  
  
where:  
AYS = yearly salary adjusted for cost of living  
AYB = yearly bonus adjusted for cost of living  
RBP = retirement benefits percentage  
RS = relocation stipend  
RSUA = restricted stock unit award

  
The rationale for the RSUA subformula is:

value of a restricted stock unit award vests over 4 years

average value of the restricted stock unit award per year (RSUA / 4)

For example, if the weights are 2 for the yearly salary, 2 for relocation stipend, and 1 for all other factors, the score would be computed as:  
  

2/7 * AYS + 1/7 * AYB + 2/7 * RS + 1/7 * (RPB * AYS / 100) + 1/7 * (RSUA / 4)

	The weighted sum for each JobDetails instance in the JobComparisonAnalysis.rankedJobs attribute will be computed in the JobDetails.calculateScore() method, which will be called by the JobComparisonAnalysis.calculateRankings() method.

  
The user interface must be intuitive and responsive.

	This requirement cannot be directly represented in a UML class diagram, since it is purely UI related.

For simplicity, you may assume there is a single system running the app (no communication or saving between devices is necessary).

	This requirement is not applicable for UML class diagrams, since links to external systems are not usually represented in them.