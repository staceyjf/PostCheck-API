# PostCheck

{add test badges here, all projects you build from here on out will have tests, therefore you should have github workflow badges at the top of your repositories: [Github Workflow Badges](https://docs.github.com/en/actions/monitoring-and-troubleshooting-workflows/adding-a-workflow-status-badge)}

## Demo & Snippets

-   Include hosted link
-   Include images of app if CLI or Client App

---

## Requirements / Purpose

-   MVP
-   purpose of project
-   stack used and why

---

## Build Steps

-   how to build / run project
-   use proper code snippets if there are any commands to run

---

## Design Goals / Approach

-   Design goals
-   why did you implement this the way you did?

---

## Features

-   What features does the project have?
-   list them...

---

## Known issues

-   Remaining bugs, things that have been left unfixed
-   Features that are buggy / flimsy

---

## Future Goals

-   What are the immediate features you'd add given more time

---

## Change logs

-   Write a paragraph labelled with the date every day you work on the project to discuss what you've done for the say. Be specific about the changes that have happened for that day.

### 13/02/2022 - {Theme of changes if applicable}

-   Extended the expiry time of JWT tokens on the backend
-   Added users to cohort response payload
-   Centralized API base URL on frontend using the proxy `package.json` property

---

## What did you struggle with?

1. Ownership: Originally was considering dual ownership but subsequently decided on single ownership to reduce complexity
2. Validation: Reviewing which builtin validation I should use for DTO vs implementing customized validation in the service level. I ultimately decided on streamlining the DTO to check for null values and integrated validation into the service level for better custom validation messages 
3. Decided on the relationship between postcode and suburb
    - accounting for edge cases where a suburb has more than one postcode eg Sydney, NSW has 2000 and 2001
    - 

---

## Licensing Details

-   What type of license are you releasing this under?

---

## Further details, related projects, reimplementations

-   Is this project a reimplementation for something you've done in the past? if so explain it and link it here.
-   If it's an API, is there a client app that works with this project? link it
