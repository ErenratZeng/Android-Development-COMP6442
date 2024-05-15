# [G48 - Kangarun] Report

The following is a report template to help your team successfully provide all the details necessary for your report in a structured and organised manner. Please give a straightforward and concise report that best demonstrates your project. Note that a good report will give a better impression of your project to the reviewers.

Note that you should have removed ALL TEMPLATE/INSTRUCTION textes in your submission (like the current sentence), otherwise it hampers the professionality in your documentation.

*Here are some tips to write a good report:*

* `Bullet points` are allowed and strongly encouraged for this report. Try to summarise and list the highlights of your project (rather than give long paragraphs).*

* *Try to create `diagrams` for parts that could greatly benefit from it.*

* *Try to make your report `well structured`, which is easier for the reviewers to capture the necessary information.*

*We give instructions enclosed in square brackets [...] and examples for each sections to demonstrate what are expected for your project report. Note that they only provide part of the skeleton and your description should be more content-rich. Quick references about markdown by [CommonMark](https://commonmark.org/help/)*

## Table of Contents

- [\[G48 - Kangarun\] Report](#g48---kangarun-report)
  - [Table of Contents](#table-of-contents)
  - [Administrative](#administrative)
  - [Team Members and Roles](#team-members-and-roles)
  - [Summary of Individual Contributions](#summary-of-individual-contributions)
  - [Application Description](#application-description)
    - [Application Use Cases and or Examples](#application-use-cases-and-or-examples)
    - [Application UML](#application-uml)
  - [Code Design and Decisions](#code-design-and-decisions)
    - [Data Structures](#data-structures)
    - [Design Patterns](#design-patterns)
    - [Parser](#parser)
    - [Grammar(s)](#grammars)
    - [Tokenizers and Parsers](#tokenizers-and-parsers)
    - [Others](#others)
  - [Implemented Features](#implemented-features)
    - [Basic Features](#basic-features)
    - [Custom Features](#custom-features)
    - [Surprise Features](#surprise-features)
  - [Summary of Known Errors and Bugs](#summary-of-known-errors-and-bugs)
  - [Testing Summary](#testing-summary)
  - [Team Management](#team-management)
    - [Meetings Records](#meetings-records)
    - [Conflict Resolution Protocol](#conflict-resolution-protocol)

## Administrative
- Firebase Repository Link: <insert-link-to-firebase-repository>
   - Confirm: I have already added comp21006442@gmail.com as a Developer to the Firebase project prior to due date.
- Two user accounts for markers' access are usable on the app's APK (do not change the username and password unless there are exceptional circumstances. Note that they are not real e-mail addresses in use):
   - Username: comp2100@anu.edu.au	Password: comp2100
   - Username: comp6442@anu.edu.au	Password: comp6442

## Team Members and Roles
The key area(s) of responsibilities for each member

| UID      |   Name   |                                                                  Role |
|:---------|:--------:|----------------------------------------------------------------------:|
| u7724723 | Qiutong Zeng |      Responsible for Database Management, User profile, Firebase Auth |
| u7611510 | Heng Sun |                                      Responsible for exercise feature |
| u6812566 | Runyao Wang |                    Responsible for Message, Search and Social Network |
| u6508459 | Bingnan Zhao | Responsible for exercise records and firestore and data upload script |
| u7779907 |  Yan Jin  |                                                                [role] |


## Summary of Individual Contributions

Specific details of individual contribution of each member to the project.

Each team member is responsible for writing **their own subsection**.

A generic summary will not be acceptable and may result in a significant lose of marks.

*[Summarise the contributions made by each member to the project, e.g. code implementation, code design, UI design, report writing, etc.]*

*[Code Implementation. Which features did you implement? Which classes or methods was each member involved in? Provide an approximate proportion in pecentage of the contribution of each member to the whole code implementation, e.g. 30%.]*

*you should ALSO provide links to the specified classes and/or functions*
Note that the core criteria of contribution is based on `code contribution` (the technical developing of the App).

*Here is an example: (Note that you should remove the entire section (e.g. "others") if it is not applicable)*

1. **UID1, Name1**  I have 30% contribution, as follows: <br>
  - **Code Contribution in the final App**
    - Feature A1, A2, A3 - class Dummy: [Dummy.java](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java)
    - XYZ Design Pattern -  class AnotherClass: [functionOne()](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java#L22-43), [function2()](the-URL)
    - ... (any other contribution in the code, including UI and data files) ... [Student class](../src/path/to/class/Student.java), ..., etc.*, [LanguageTranslator class](../src/path/to/class/LanguageTranslator.java): function1(), function2(), ... <br><br>

  - **Code and App Design** 
    - [What design patterns, data structures, did the involved member propose?]*
    - [UI Design. Specify what design did the involved member propose? What tools were used for the design?]* <br><br>

  - **Others**: (only if significant and significantly different from an "average contribution") 
    - [Report Writing?] [Slides preparation?]*
    - [You are welcome to provide anything that you consider as a contribution to the project or team.] e.g., APK, setups, firebase* <br><br>
2. **u7724723, Qiutong Zeng**  I have % contribution, as follows: <br>
   - **Code Contribution in the final App**
      - Feature FB-Auth: Use firebase to verify user login and add register info to Firestore - [LoginActivity.java](https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/blob/main/MyApplication/app/src/main/java/com/example/kangarun/activity/LoginActivity.java) & [RegisterActivity.java](https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/blob/main/MyApplication/app/src/main/java/com/example/kangarun/activity/RegisterActivity.java)
      - Feature FB-Persist: Use firebase to persist user profile - [UserProfileActivity.java](https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/blob/main/MyApplication/app/src/main/java/com/example/kangarun/activity/UserProfileActivity.java) & [FriendProfileActivity.java](https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/blob/main/MyApplication/app/src/main/java/com/example/kangarun/activity/FriendProfileActivity.java)
      - Feature Data-Profile: Display personal information and an avatar for each user - [UserProfileActivity.java](https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/blob/main/MyApplication/app/src/main/java/com/example/kangarun/activity/UserProfileActivity.java)-  [FriendProfileActivity.java](https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/blob/main/MyApplication/app/src/main/java/com/example/kangarun/activity/FriendProfileActivity.java)
   - **Code and App Design**
       - Implement media file display and upload through Picasso and imagepicker <br><br>

3. **u7611510, HengSun**  I have % contribution, as follows: <br>
   - **Code Contribution in the final App**
       - Feature Data-GPS: use Google Maps api to display map, locate the movement position in real time, 
       continuously draw poly line according to the user's position in the map,  calculate the movement distance, time, 
       and calories in real time - class MapsActivity: [MapsActivity.java](https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/blob/main/MyApplication/app/src/main/java/com/example/kangarun/activity/MapsActivity.java)
       - Feature FB-Persist: use firebase to persist user exercise record - class MapsActivity: [MapsActivity.java](https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/blob/main/MyApplication/app/src/main/java/com/example/kangarun/activity/MapsActivity.java)
       - Feature Data-graphical: get user exercise record from firebase,
       display exercise record in detail including exercise path img,distance,duration,date - class ExerciseRecordDetailActivity:[ExerciseRecordDetailActivity.java](https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/blob/main/MyApplication/app/src/main/java/com/example/kangarun/activity/ExerciseRecordDetailActivity.java)
       -class ExerciseRecordAdaptor:[ExerciseRecordAdapter.java](https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/blob/main/MyApplication/app/src/main/java/com/example/kangarun/adapter/ExerciseRecordAdapter.java)<br>
   - **Code and App Design**
       - proposed using AVL tree to store the friend relationship data structure, which can reduce the query waiting time<br><br>

4. **u6812566, Runyao Wang**  I have xx% contribution, as follows: <br>
   - **Code Contribution in the final App**
       - Feature P2P-DM: Real-time messaging implementation and relative UI -
         [ChatActivity.java](https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/blob/main/MyApplication/app/src/main/java/com/example/kangarun/activity/ChatActivity.java),
         [ChatAdapter.java](https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/blob/main/MyApplication/app/src/main/java/com/example/kangarun/adapter/ChatAdapter.java),
         [activity_chat.xml](https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/blob/main/MyApplication/app/src/main/res/layout/activity_login.xml)
       - Feature Search: Search user by username with AVL tree-
         [SearchActivity.java](https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/blob/main/MyApplication/app/src/main/java/com/example/kangarun/activity/SearchActivity.java),
         [UserAVLTree.java](https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/blob/main/MyApplication/app/src/main/java/com/example/kangarun/utils/UserAVLTree.java)
       - User adapter to create list to display users in search result and friends list -
         [UserAdapter.java](https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/blob/main/MyApplication/app/src/main/java/com/example/kangarun/adapter/UserAdapter.java), 
       - Chat adapter to display messages in list [ChatAdapter.java](https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/blob/main/MyApplication/app/src/main/java/com/example/kangarun/adapter/ChatAdapter.java)
       - Add/delete friend [FriendProfileActivity.java](https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/blob/main/MyApplication/app/src/main/java/com/example/kangarun/activity/FriendProfileActivity.java)
       - Friend list [FriendListActivity.java](https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/blob/main/MyApplication/app/src/main/java/com/example/kangarun/activity/FriendListActivity.java)
   - **Code and App Design**
       - Use Recycle view and adapter to display list items (user list, messages)<br><br>

5. **u6508459, Bingnan ZHao**  I have xx% contribution, as follows: <br>
   - **Code Contribution in the final App**
       - Feature Exercise Record: Check exercise history in different orders.
         [ExerciseRecordActivity.java](https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/blob/main/MyApplication/app/src/main/java/com/example/kangarun/activity/ExerciseRecordActivity.java)
         [ExerciseRecordAdapter.java](https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/blob/main/MyApplication/app/src/main/java/com/example/kangarun/adapter/ExerciseRecordAdapter.java)
       - Template method for adapters
         [BaseAdapter.java](https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/blob/main/MyApplication/app/src/main/java/com/example/kangarun/adapter/BaseAdapter.java)
         [BlacklistUserAdapter.java](https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/blob/main/MyApplication/app/src/main/java/com/example/kangarun/adapter/BlacklistUserAdapter.java)
         [ChatAdapter.java](https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/blob/main/MyApplication/app/src/main/java/com/example/kangarun/adapter/ChatAdapter.java)
         [ExerciseRecordAdapter.java](https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/blob/main/MyApplication/app/src/main/java/com/example/kangarun/adapter/ExerciseRecordAdapter.java)
         [UserAdapter.java](https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/blob/main/MyApplication/app/src/main/java/com/example/kangarun/adapter/UserAdapter.java)
       - Script to create test messages
         [create_chat.py](https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/blob/main/items/script/create_chat.py)
   - **Code and App Design**
       - Designed template method and proposed the use of singleton
       - UI for maps activity and exercise record activity.

## Application Description
Kangarun is a sports-centric social application designed for tracking your every workout. With our app, you can log your exercise duration, calories burned, distance covered, and even visualize your route through map images. You can add your workout buddies to the app, chat with them, hang out, and exercise together.


### Application Use Cases and or Examples
#### Use Case 1: Casual Jogger
- **User**: Alex, a college student who jogs casually.
- **Goal**: To stay fit and share activities with friends.
- **Actions**:
    1. Alex logs into Kangarun to start a jogging session, using the app to track his route and performance.
    2. After the jog, he reviews the distance covered and calories burned displayed on the app.
    3. He shares his running route and stats on his Kangarun feed, receiving likes and comments from friends.
    4. Alex checks his friends' recent activities on the app to stay motivated and plans joint runs.

#### Use Case 2: Competitive Runner
- **User**: Maria, a competitive runner training for a marathon.
- **Goal**: To optimize training and track performance over time.
- **Actions**:
    1. Maria uses Kangarun's advanced features to monitor detailed stats like pace, elevation, and heart rate.
    2. She sets personal goals in the app and tracks her progress against these targets.
    3. Maria analyzes the comprehensive data collected to adjust her training regimen.
    4. She participates in virtual challenges and competitions through Kangarun to rank her performance against others.

#### Use Case 3: Fitness Trainer
- **User**: John, a fitness trainer.
- **Goal**: To manage his clients' workouts and motivate them.
- **Actions**:
    1. John creates group activities for his clients on Kangarun and monitors their participation.
    2. He reviews each client's activity data to provide personalized feedback and adjustments to their exercise plans.
    3. John uses the app's messaging feature to send motivational messages and workout tips.
    4. He organizes weekly challenges through the app to encourage competition and community among his clients.

#### Use Case 4: Health Enthusiast
- **User**: Linda, who is focused on overall wellness and likes to keep a healthy lifestyle.
- **Goal**: To maintain a balanced exercise routine and connect with like-minded individuals.
- **Actions**:
    1. Linda uses Kangarun to track various types of activities, including running, cycling, and yoga.
    2. She follows her friends and joins public groups within the app to find inspiration and new workout ideas.
    3. Linda shares her own tips and success stories, engaging with others through comments and messages.
    4. She uses the app’s reminder feature to keep a consistent workout schedule.

<hr> 

### Application UML
![Application UML](https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/blob/main/items/java2%20g48%20UML.png) <br>


<hr>

## Code Design and Decisions

This is an important section of your report and should include all technical decisions made. Well-written justifications will increase your marks for both the report as well as for the relevant parts (e.g., data structure). This includes, for example,

- Details about the parser (describe the formal grammar and language used)

- Decisions made (e.g., explain why you chose one or another data structure, why you used a specific data model, etc.)

- Details about the design patterns used (where in the code, justification of the choice, etc)

*Please give clear and concise descriptions for each subsections of this part. It would be better to list all the concrete items for each subsection and give no more than `5` concise, crucial reasons of your design.

<hr>

### Data Structures

*[What data structures did your team utilise? Where and why?]*

Here is a partial (short) example for the subsection `Data Structures`:*

*I used the following data structures in my project:*

1. *AVL Tree*
   * *Objective: used for storing user information for search feature.*
   * *Code Locations: defined in [UserAVLTree.java](https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/blob/main/MyApplication/app/src/main/java/com/example/kangarun/utils/UserAVLTree.java); processed using [SearchActivity.java](https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/blob/main/MyApplication/app/src/main/java/com/example/kangarun/activity/SearchActivity.java).
   * *Reasons:*
      * *It is more efficient than Arraylist for search with a time complexity O(log(n))*
      * *The tree is created upon app start to reduce the number of requests to firebase*

2. ...

3. ...

<hr>

### Design Patterns
*[What design patterns did your team utilise? Where and why?]*

1. *Singleton Pattern*
   * *Objective: used for storing user information for all features that needs user information.*
   * *Code Locations: defined in [Class LoginState, methods getInstance](https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/blob/main/MyApplication/app/src/main/java/com/example/kangarun/LoginState.java) 
   * *Reasons:*
      * used to ensure that a class has only one instance and provides a global point of access to that instance. It is useful to control access to a shared resource or manage global state within an application.

2. *DAO Pattern*
   * *Objective: storing data access object.*
   * *Code Locations: defined in [Class User](https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/blob/main/MyApplication/app/src/main/java/com/example/kangarun/User.java) and [Class Message](https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/blob/main/MyApplication/app/src/main/java/com/example/kangarun/AdminUser.java)
   * *Reasons:*
      * used to separate the business logic from the data persistence logic, promoting better code organization and maintainability. It abstracts the database operations, providing a clean interface for accessing and manipulating data, which enhances code readability and reusability.

3. *Template Pattern*
   * *Objective: used for storing xxxx for xxx feature.*
   * *Code Locations: defined in [Class BaseAdaptor](https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/blob/main/MyApplication/app/src/main/java/com/example/kangarun/adapter/BaseAdapter.java) and [Class ChatAdapter](https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/blob/main/MyApplication/app/src/main/java/com/example/kangarun/adapter/ChatAdapter.java) and [Class ExerciseRecordAdapter](https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/blob/main/MyApplication/app/src/main/java/com/example/kangarun/adapter/ExerciseRecordAdapter.java) and [Class UserAdapter](https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/blob/main/MyApplication/app/src/main/java/com/example/kangarun/adapter/UserAdapter.java)
   * *Reasons:*
      * used to define the skeleton of an algorithm in a superclass but allows subclasses to override specific steps of the algorithm without changing its structure. This promotes code reuse and allows for variation in behavior among subclasses while maintaining a common workflow.


<hr>

### Parser

### <u>Grammar(s)</u>
*[How do you design the grammar? What are the advantages of your designs?]*
*If there are several grammars, list them all under this section and what they relate to.*

Production Rules:

    <query> ::= <field>
    <query> ::= <field><separator><query>
    <field> ::= <username_field> | <email_field> | <gender_field>
    <username_field> ::= "name="<username>
    <email_field> ::= "email="<email>
    <gender_field> ::= "gender="<gender>
    
Terminals:

    <username>, <email> ::= Any string doesn't include = or ;
    <gender> ::= m | f | o
    <separator> ::= ;
*The gender terminals represents male, female and other genders respectively.

### <u>Tokenizers and Parsers</u>

Tokenizer and Parser are used in user search with tokens. The search grammar is stated above.
An simple query example would be:

    name=1;email=1;gender=f
There should be at least one field and the order of fields doesn't matter.

The tokenizer for this grammar splits the input query into tokens using the semicolon ";" as a delimiter. Each token represents a potential field within the query based on the prefix ("name=", "email=", "gender=").

With the tokens generated by the tokenizer, the parser verifies each token against the grammar rules. It checks if each token correctly fits the expected format for the field it represents, and data integrity is therefore ensured.
 
Using tokenized search enriches the possibility and accuracy of current search function. It is designed for who wants to find a specific group of users.

<hr>

### Others

*[What other design decisions have you made which you feel are relevant? Feel free to separate these into their own subheadings.]*

<br>
<hr>

## Implemented Features
*[What features have you implemented? where, how, and why?]* <br>
*List all features you have completed in their separate categories with their featureId. THe features must be one of the basic/custom features, or an approved feature from Voice Four Feature.*

### Basic Features
1. [LogIn]. Description of the feature ... (easy)
   * Code: [Class X, methods Z, Y](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java#L22-43) and Class Y, ...
   * Description of feature: ... <br>
   * Description of your implementation: ... <br>

2. [DataFiles]. Description  ... ... (...)
   * Code to the Data File [users_interaction.json](link-to-file), [search-queries.xml](link-to-file), ...
   * Link to the Firebase repo: ...

3. ...
   <br>
4. ....
    <br>
5. [Search]. Users are able to search other users by username or tokens.
    * Code: [SearchActivity.java](https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/blob/main/MyApplication/app/src/main/java/com/example/kangarun/activity/SearchActivity.java), [UserAVLTree.java](https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/blob/main/MyApplication/app/src/main/java/com/example/kangarun/utils/UserAVLTree.java), [Tokenizer.java](https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/blob/main/MyApplication/app/src/main/java/com/example/kangarun/utils/Tokenizer.java), [Parser.java](https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/blob/main/MyApplication/app/src/main/java/com/example/kangarun/utils/Parser.java)
    * The search function offers users the option to perform either a token-based search, which follows a predefined context-free grammar (CFG) as described above, or a simpler partial username search. 
      If the user's input is not tokenizable, the system will automatically perform a search based on partial username matches.
    * Both search method utilize an AVL tree algorithm. All user data is inserted into an AVL tree when the app starts. Then the search activity determines the appropriate search method based on user query, and invoke corresponding search method in the AVL tree.
   <br>
### Custom Features
Feature Category: Peer to Peer Messaging <br>
1. [P2P-DM]. Users can chat with other users in a real-time direct message. (hard)
   * Code: [ChatActivity.java](https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/blob/main/MyApplication/app/src/main/java/com/example/kangarun/activity/ChatActivity.java),
     [activity_chat.xml](https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/blob/main/MyApplication/app/src/main/res/layout/activity_login.xml), [ChatAdapter.java](https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/blob/main/MyApplication/app/src/main/java/com/example/kangarun/adapter/ChatAdapter.java)
   * It is based on Firestore for persistent and real-time data synchronization, ensuring that messages are consistently updated and displayed. The messages are displayed by a recycle view adapter to handle asynchronous data loading and UI updates to provide a responsive user experience.
   <br>
     <br>
   
Feature Category: Search-related features <br>
2. [Search-Filter]. Sort and filter the search result (easy)
    * Code: [SearchActivity.java](https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/blob/main/MyApplication/app/src/main/java/com/example/kangarun/activity/SearchActivity.java)
    * Sort button to sort by username or email. Filter button for user-selected gender.
    * Based on O(n logn) Collеctions.sort()
   <br><br>

Feature Category: Firebase Integration <br>
3. [FB-Auth] Description of the feature (easy)
   * Code: [Class X, entire file](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java#L22-43) and Class Y, ...
   * [Class B](../src/path/to/class/file.java#L30-85): methods A, B, C, lines of code: 30 to 85
   * Description of your implementation: ... <br>

Feature Category: Greater Data Usage, Handling and Sophistication<br>
4.[Data-GPS] Use GPS information based on location data in your App. (easy)
   * Code: [Class MapsActivity, method getCurrentLocation](https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/blob/main/MyApplication/app/src/main/java/com/example/kangarun/activity/MapsActivity.java#L238-L250)
   * Description of your implementation: Use FusedLocationProviderClient to obtain the user's location information, and according to user current location on the map. <br>
<br>

5.[Data-Graphical] Create a Graphical report viewer to see a report of some useful data from your app. No marks will be awarded if the report is non-graphical. (medium)

   * Code: [Class MapsActivity,methods captureMapSnapshot, uploadMapSnapshotToFirebase](https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/blob/main/MyApplication/app/src/main/java/com/example/kangarun/activity/MapsActivity.java#L332-L370) lines of 332 to 370
   * Description of your implementation: the graphical is generated by captureMapSnapshot method and stored to firebase by uploadMapSnapshotToFirebase function<br><br>

<hr>

### Surprise Features
#### Code smells
* Code smell 1: query firebase every time when need the current logged-in user id<br>
(https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/commit/b3ad484654b3e47cd1f61cf7823f55c876ed115b)
(https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/commit/53262b4b45eae4306f61cb2f934c41727372c747)
(https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/commit/a6a55fd24aa3ed89d328f2eed747fec09b240991)

  - Correction:

    The code has been improved by leveraging a singleton login state to directly access the current user's ID instead of querying Firebase repeatedly. This modification enhances performance by eliminating redundant database calls and enhances code clarity by centralizing user authentication data management.</br>
    (https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/commit/44701b1ae64f375620857c36aa6ff8f988971510)
    (https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/commit/deeb9c917d327177cff5ddb1ce183be0f390b618)
    (https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/commit/9b59babaa79722d0fc546670405666e7d7a990a9)
    (https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/commit/9765590f50a7f8b489bf2066b7f5d52e8941fc2d)
    (https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/commit/e3f3bad97b29a4428455f6d8fcd041ad428c66a8)
    (https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/commit/82411964179f30bad0735de79696db58b74b90d6)
  
  <br>
* Code smell 2: Many adaptors have similar functionality without using the template pattern<br>
(https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/commit/82f7dfe31026057d045af06873274a665eb643b0)
(https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/commit/624c27bb412c5b7d4629a78cc525ec57ef87c2cb)
(https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/commit/cce2b1f2a6dbeb5895107ba895030660ba00f2f3)
(https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/commit/de77f53b1aadd61c1c38bf2a783547997029e824)
  - Correction: <br> The adapters have been refactored to adhere to the template method pattern by introducing a BaseAdapter template class. This pattern abstracts the common functionality shared among adapters while allowing subclasses to implement specific details. Now, each adapter extends the BaseAdapter and overrides methods as needed, promoting code reuse and maintaining a consistent structure across adapters.
    (https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/commit/c6d72c0d1b55c8a4d9b6a42aa4d59f9a6dbe435a)

  <br>
* Code smell 3: The display of data is not restricted to two decimal places, resulting in a lot of screen space being used to display meaningless decimals<br>
(https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/commit/624c27bb412c5b7d4629a78cc525ec57ef87c2cb)


* Code smell 4: AVL tree type issue
    - The AVL tree was using lab code with improper type declaration. It has complicated class inheritance to fit different types of data. It needs to cast every time used. (https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/commit/574b6b8d75e7d88804f15b06669275077451b82b)
    - Correction: <br>
    Create an AVL tree for specific type User. It is significantly easier to invoke and readability improved. (https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/commit/53262b4b45eae4306f61cb2f934c41727372c747)


## Summary of Known Errors and Bugs

*[Where are the known errors and bugs? What consequences might they lead to?]*
*List all the known errors and bugs here. If we find bugs/errors that your team does not know of, it shows that your testing is not thorough.*

*Here is an example:*

1. *Bug 1:*
   - *The length of chat message container is fixed. It may looks weird in landscape mode.*
   - The reason of fixed container width is to eliminate extra spaces with *wrap_content(0dp)* in Recycle View.

2. *Bug 2:*
3. ...

<br> <hr>


## Testing Summary

*[What features have you tested? What is your testing coverage?]*
*Please provide some screenshots of your testing summary, showing the achieved testing coverage. Feel free to provide further details on your tests.*

*Here is an example:*

1. Tests for Search
   - Code: [TokenizerTest Class, entire file](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java) for the [Tokenizer Class, entire file](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java#L22-43)
   - *Number of test cases: ...*
   - *Code coverage: ...*
   - *Types of tests created and descriptions: ...*

2. xxx

...

<br> <hr>


## Team Management

### Meetings Records
* Link to the minutes of your meetings like above. There must be at least 4 team meetings.
  (each commited within 2 days aftre the meeting)
* Your meetings should also have a reasonable date spanning across Week 6 to 11.*


- *[Team Meeting 1](https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/blob/main/items/meeting20240411.md)*
- *[Team Meeting 2](https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/blob/main/items/meeting20240415.md)*
- *[Team Meeting 3](https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/blob/main/items/meeting20240422.md)*
- [Team Meeting 4](https://gitlab.cecs.anu.edu.au/u7724723/gp-24s1/-/blob/main/items/meeting20240429.md)
- ... (Add any descriptions if needed) ...

<hr>

### Conflict Resolution Protocol
*[Write a well defined protocol your team can use to handle conflicts. That is, if your group has problems, what is the procedure for reaching consensus or solving a problem?
(If you choose to make this an external document, link to it here)]*

This shall include an agreed procedure for situations including (but not limited to):
- if a member fails to meet the initial plan and/or deadlines，We will first check whether the assigned task is too complicated or too many assignments, which leads to failure to complete, or other assignments take up too much time. If it is the above situation, we will catch up with the progress, if not, then the failure to complete the task on time will be reflected in the final score
- If someone is sick, we will let her have a good rest first, and at the same time assign her tasks to someone who is not very busy at the present stage. When the illness recovers, the rest person will help others to take part of the work to compensate
- if we have different opinions on a problem, the way we resolve conflicts is by secret ballot. When we have different views on an issue, we use secret ballot to resolve the issue. All opinions are listed, each person has one vote in a secret ballot, the only restriction is that they can not vote for their own choice, and after the vote is completed, according to the majority of the votes.