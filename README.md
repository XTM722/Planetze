Planetze App Documentation (group 22) 
Welcome to our groups Planetze app documentation! This is your very very peronslzied guide to understand how the app works, how to maintain or expand its functionality, and the key decisions and assumptions we made during development. Whether you're a user, developer, or grader, this document has you covered! Lets begin!!

Instructions on How to Use the Code
To get started with the code, you’ll need to first clone the repository from GitHub. Simply use the following command in your terminal or command line:
git clone https://github.com/XTM722/Planetze.git
Once you’ve cloned the repository, set up Firebase by accessing our project here: Planetze Firebase Console. Replace the “google-services.json” file in the project root with your Firebase configuration file, if needed. Ensure the following Firebase services are enabled:
Authentication
Realtime Database
Open the project in Android Studio. Sync the Gradle files to load dependencies, and use a connected device or emulator (either blue tooth for wifi) to run the app. The app requires permissions for notifications and alarms, which users MUST grant when prompted to enjoy the full functionality. 

A Guide for Developers on Maintaining and Developing the Code
Version Control: Use GitHub for version control. Follow the branch naming convention “feature-<feature-name>’ and always submit pull requests for review by whoever is desganted as scrum master (or most knowledgeable in software design) before merging into the main branch. DO not ever update or commit directly to the main branch. Always make your own branches to work on during the development process!!
Extending Features:
For Habit Suggestions, consider integrating APIs or using user activity data to make recommendations dynamic.
Expand the Eco Hub by adding a CMS (Content Management System) for real-time content updates.
Testing: Write unit tests for new features and use Firebase Test Lab to test the app on multiple Android devices and configurations.
Deployment: Ensure all tests pass before deploying and verify that permissions work seamlessly on devices running Android 13+.

Describing the Overall Structure
The app is built using the Model-View-Presenter design pattern, which ensures separation of concerns and makes the code easier to maintain and scale:
Models:
Handle the data and business logic of the app, such as user accounts, habits, and logged activities (e.g., User, Habitclasses).
Views:
Represent the app’s user interface through XML layouts, including all the visual elements that users interact with (e.g., activity_dashboard.xml, activity_habit_suggestions.xml).
Presenters:
Act as intermediaries between the models and views. Presenters retrieve data from the models, process it if necessary, and update the views accordingly. They also handle user inputs from the views and trigger appropriate model updates.
This MVP structure ensures that each component has a clearly defined role, making it easier to debug, enhance, and maintain the app over time. It also allows developers to work on different parts of the app simultaneously without conflicts.

Description of Key Components
Login & Registration:
Activities: MainActivity, RegistrationActivity, ForgotPasswordActivity.
Features: Email-based login, account creation, and password reset using Firebase Authentication.
Annual Carbon Footprint:
Activities: CalculateScoresActivity, ScoreCompareActivity.
Users answer questions to calculate their carbon footprint, view detailed breakdowns, and compare their emissions to global or national averages.
Eco Tracker:
Activity: EcoTrackerActivity.
Users log daily activities (e.g., transportation, food, energy). Logs are saved in Firebase and displayed through a calendar interface.
Habit Suggestions:
Activity: HabitSuggestionsActivity.
Displays a curated list of eco-friendly habits. Users can search, filter, and set reminders for habits, with notifications managed by ReminderReceiver.java.
Eco Gauge:
Activity: EcoGaugeActivity.
Provides visual breakdowns and trends of emissions over time through graphs.
Eco Hub:
Activity: EcoHubActivity.
A content hub featuring sustainability articles, videos, and tips. Content is locally stored but can be dynamically updated in future versions.

List of Dependencies and Why They’re Needed
libs.appcompat:
Provides backward-compatible versions of Android components, ensuring the app runs smoothly on older Android versions.
libs.material:
Includes Material Design components for building a modern and user-friendly interface.
libs.activity:
Handles activity lifecycle management efficiently, providing improved compatibility and integration with Jetpack libraries.
libs.constraintlayout:
Enables the creation of complex UI layouts with flat hierarchies for better performance and flexibility.
libs.firebase.database:
Used for storing user activity logs and app data in a NoSQL database, ensuring real-time data syncing.
libs.firebase.auth:
Handles user authentication, including account creation, login, and password reset features.
libs.firebase.firestore:
(Optional feature scope) Used for flexible, scalable storage solutions to support Eco Hub content in future updates.
libs.junit:
Provides a framework for unit testing the app’s functionality.
libs.ext.junit:
Extends JUnit for Android-specific test scenarios.
libs.espresso.core:
Facilitates UI testing to ensure that interactions in the app work as intended.
libs.mockito.core:
A mocking framework used in testing to simulate behaviors and validate functionality without relying on real dependencies



Assumptions
General Assumptions:
Users have basic knowledge of Android apps.
Activities logged accurately reflect their real-world actions.
Notifications rely on device settings for permissions and sound (escpailly for reminder notifications!)
Feature-Specific Assumptions:
Recommended Habits: The fixed list of recommended habits is based on insights from environmental research studies and best practices outlined by organizations like the United Nations Environment Programme (UNEP) and the World Resources Institute (WRI). Each of the two habits was selected to target areas with the highest potential for carbon footprint reduction.
The design intentionally avoids real-time dynamic updates to ensure simplicity and a smooth user experience for those who might not log activities regularly or provide detailed emissions data.
This rather static approach assumes that presenting universally impactful habits like "Planting Trees" or "Recycling" encourages behavioral change without overwhelming users with too many options or requiring extensive customization.

Links to GitHub and Jira
GitHub: Planetze Repository
Jira: Project Tasks & Issues

Existing Credentials to Sign In
For testing purposes, you can use the following credentials to log into the app:
Email: testuser@planetze.com
Password: Planetze123
We hope this documentation helps you understand everything about Planetze! Whether you're diving into the code, testing the app, or simply exploring its features, we hope this documataion made your life just a little bit easier!! Happy coding and carbon footprint tracking!



