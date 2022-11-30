# Requirements
1. When the app is started, the user is presented with the main menu, which allows the user to (1) enter or edit current job details, (2) enter job offers, (3) adjust the comparison settings, or (4) compare job offers (disabled if no job offers were entered yet).

    **The entry point to the app is represented by the main menu class. Here a user will select an action.**

2. When choosing to enter current job details, a user will:
    1. Be shown a user interface to enter (if it is the first time) or edit all of the details of their current job, which consist of:
        - Title
        - Company
        - Location (entered as city and state)
        - Cost of living in the location (expressed as an index)
        - Yearly salary
        - Yearly bonus
        - Retirement benefits (as percentage matched) (Given as Integer 0-100)
        - Relocation stipend
        - Restricted stock unit award (expressed as a lump sum vested over 4 years)

        **This is a simple CRUD page. I decided to to cast yearlySalary, yearlyBonus, relocationStipend, and restrictedStockUnitAward as Integers because it is unlikely that they will be decimals. The Location and State are both separate classes because a state can have many locations and a location can have may jobs.**

    2. Be able to either save the job details or cancel and exit without saving, returning in both cases to the main menu.

        **The CurrentJobDetails class handles this requirement.**

3. When choosing to enter job offers, a user will:
    1. Be shown a user interface to enter all of the details of the offer, which are the same ones listed above for the current job.
    2. Be able to either save the job offer details or cancel.
    3. Be able to (1) enter another offer, (2) return to the main menu, or (3) compare the offer (if they saved it) with the current job details (if present).

    **The JobOfferDetails class is resposible for all of this requirement.**

4. When adjusting the comparison settings, the user can assign integer weights to:
    - Yearly salary
    - Yearly bonus
    - Retirement benefits
    - Relocation stipend
    - Restricted stock unit award

    **The ComparisonSettings class is provided for this requirement. All attributes are initialized to 7 so they do not unintentionally affect the scoring calculation.**

5. When choosing to compare job offers, a user will:
    1. Be shown a list of job offers, displayed as Title and Company, ranked from best to worst (see below for details), and including the current job (if present), clearly indicated.
    2. Select two jobs to compare and trigger the comparison.
    3. Be shown a table comparing the two jobs, displaying, for each job:
        - Title
        - Company
        - Location
        - Yearly salary adjusted for cost of living
        - Yearly bonus adjusted for cost of living
        - Retirement benefits
        - Relocation stipend
        - Restricted stock unit award
    4. Be offered to perform another comparison or go back to the main menu.

    **The Compare class is provided for this requirement.**

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

    **The setScore function will handle this calculation. It should be ran when a current job or a job offer is saved. When ComparisonSettings are updated the function setAllJobScores function should run the setScore function on all jobs in the system. This will keep the all the job scores up to date.**

7. The user interface must be intuitive and responsive.

    **Since this is an implementation detail it is not represented in this design.**

8. For simplicity, you may assume there is a single system running the app (no communication or saving between devices is necessary).

    **Since this is an implementation detail it is not represented in this design.**
