# Welcome to PostChecker-API

A Spring Boot backend powered by SQLite with a TypeScript React frontend demo.

<div align="center">
  <img src="./React/public/homepage.png" alt="Homepage">
</div>

## Build Steps

1. Clone the repo.
2. Run `npm install` to install the relevant dependencies.
3. Start the Spring Boot backend via `PostcodeApplication.java`.
4. Navigate into `/React` and start the frontend via `npm run dev`.
5. Tests can be run in `/React` with `npm run test`.

## About

In this fictional brief, Aus-Post would like to add authentication to their service (in particular for their creating, updating, and deleting functionalities) that provides postcode and suburb information via an API.

The MVP to deliver on this client brief was:
- Create an API in Java that allows clients to retrieve and add suburb and postcode combinations.
- Implement:
  - An API that allows clients to retrieve suburb information by postcode.
  - An API that allows clients to retrieve a postcode given a suburb name.
- A secured API to add new suburb and postcode combinations.
- Some form of persistence (a database).
- Testing for service layers.

PostChecker-API delivers on this and more - check out below.

Explore the Spring API documentation at: `http://localhost:8080/swagger-ui/index.html` with the search term `/api-docs`.

## Key Features

### Backend

1. **Implementing JWT Auth:** While a service token may have been more appropriate given the nature of the API, I wanted to explore JWT and how to implement it using Spring Security.
2. **CRUD API Endpoints:** Full CRUD endpoints are provided for the `Postcode` entity. Create, Read and Update endpoints are provided for the `Suburbs` and `Users` entities.
3. **API Documentation:** Enhanced use of Swagger to produce more informative documentation on how to use the API.
4. **Many-to-Many Relationship:** As postcodes and suburbs can contain many of the other entity, I implemented a join table to ensure that they were correctly managed within the DB. Given the proposed use of the API, I decided to implement a uni-directional relationship with the `Postcode` entity owning the relationship (associated suburbs were only contained on the postcode side). If there are additional future user requirements, I can look at changing this to a bi-directional relationship to ensure that each can hold associations of the other.

### Frontend

1. **Full CRUD for Postcodes:** Users are able to create, read, update, and delete new postcodes.
2. **Login and Auth:** Users are able to log in to access restricted areas such as creating, updating, and deleting postcodes.
3. **Component Testing:** Components have a range of tests to ensure elements render as expected.

## Key Learning Highlights

1. **Managing Auth:** The journey of understanding how authentication is implemented with Spring Security, including:
   - Understanding how HTTP requests are directed, using the `OncePerRequestFilter` interface, and implementing a filter to ensure proper authentication where needed.
   - Setting up how to generate and read a token with the `java-jwt` library, including how to navigate environment variables using the `@PropertySource` annotation in the configuration class.
   - Using the `UserService` interface when creating my user entity, which is used to store user information in authentication objects.
   - Creating a sign-up endpoint that utilizes `UserDetailsService` and `BCryptPasswordEncoder` to manage users and their password details.
   - Creating a sign-in endpoint that uses the `Authentication` object and additional methods like `UsernamePasswordAuthenticationToken` to authenticate the username and password, and then pass this onto our custom Token service to ensure that the token is valid.
2. **Testing:** It was the first time that I was using SpringTest and JUnit, so it took some time getting used to the different syntax and functionality each offers. I was able to implement JUnit for unit tests on my service modules but could not get the integrated tests to work for my controllers given the implemented auth requirements and how JWTs are created and read.

## To-Dos

1. **Orphaned Suburb Entities:** Review the Postcode delete endpoint and introduce functionality to deal with orphaned suburb entities.
2. **Documentation:** Improve the documentation by adding content objects to my `@APIResponse` annotations for better visibility into what is expected from a successful or valid HTTP request.
3. **Spring Testing:** Continue to investigate how I can use Spring testing and overcome the challenges in providing a valid token (all requests came back with a 401 or 403, which in some ways is positive as it shows that the auth was correctly working).

## Screenshots

| New Form                                 | Update Form                                 | Test                                   |
| ---------------------------------------- | ------------------------------------------- | -------------------------------------- |
| <img src="./React/public/newform.png"  /> | <img src="./React/public/updateform.png"  /> | <img src="./React/public/test.png"  /> |

## Technologies Used

<div align="center">

![Spring Boot](https://img.shields.io/badge/-Spring%20Boot-05122A?style=flat&logo=springboot)
![React Testing Library](https://img.shields.io/badge/-React%20Testing%20Library-05122A?style=flat&logo=testinglibrary)
![Log4j2](https://img.shields.io/badge/-Log4j2-05122A?style=flat&logo=apache)
![OpenAPI](https://img.shields.io/badge/-OpenAPI-05122A?style=flat&logo=openapiinitiative)
![React](https://img.shields.io/badge/-React-05122A?style=flat&logo=react)
![HTML5](https://img.shields.io/badge/-HTML5-05122A?style=flat&logo=html5)
![CSS3](https://img.shields.io/badge/-CSS3-05122A?style=flat&logo=css3)
![JavaScript](https://img.shields.io/badge/-JavaScript-05122A?style=flat&logo=javascript)
![Git](https://img.shields.io/badge/-Git-05122A?style=flat&logo=git)
![GitHub](https://img.shields.io/badge/-GitHub-05122A?style=flat&logo=github)

</div>
