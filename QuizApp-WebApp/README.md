# QuizApp - WebApp


An HTML + Javascript trivia application that fetch data (Questions and answers) from Firebase and display them to the user allowing him to play the Trivia.

## Development Setup

#### Connecting the app to FireBase
After downloading this repo, you will want to connect your App to your firebase database:
1. Log into firebase console and choose your project (or create new if you didn't already).
2. Go to "Project Overview" and then click "Add app" and choose web app. Follow google's setup which includes the following steps:
	i. Register your app.
	ii. Add FireBase SDK. Google will give you code which you must replace in the file: `QuizApp-Javascript\js\app.js`. (at the end of the file).
3. That's it. The web app is connected to Firebase.

### App structure
* The Logics are in the file js\app.js
* The styles are presented in the css folder.
* img folder contains images.
* index.html is the main entry point. If used with github pages, the browser will display the app.
* bulk.html is used for representing all the questions.

### Displaying webpage with github pages

1. Upload this javascript project to github.
2. Go to project settings and enable github pages and choose the folder that contains index.html.
3. Github pages will display this web app.

Examples:
* [https://romansko.github.io/HCI/](https://romansko.github.io/HCI/)

*Older versions:*
* [https://romansko.github.io/RegaRegaRega/](https://romansko.github.io/RegaRegaRega/)
* [https://romansko.github.io/Graphica100/](https://romansko.github.io/Graphica100/)




*This Project is based on https://github.com/jrue/JavaScript-Quiz by Jeremy Rue*
