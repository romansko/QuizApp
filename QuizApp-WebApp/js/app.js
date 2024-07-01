
/* Based on https://github.com/jrue/JavaScript-Quiz by Jeremy Rue */


var supportMail = "yourMailAddress@gmail.com";
var quizTitle = "QuizApp";

/*************************************** INTERNAL LOGICS *************************************************/
/**
 * HTML Encoding function for alt tags and attributes to prevent messy
 * data appearing inside tag attributes.
 */
function htmlEncode(value) {
    return $(document.createElement('div')).text(value).html();
}

/**
* This will add the individual choices for each question to the ul#choice-block
*
* @param {choices} array The choices from each question
*/
function addChoices(choices) {
    if (typeof choices !== "undefined" && $.type(choices) == "array") {
        $('#choice-block').empty();
        var j = 0;
        for (var i = 0; i < choices.length; i++) {
            if (typeof choices[i] !== "undefined" && choices[i] != "$" && choices[i] != "") {
                $(document.createElement('li')).addClass('choice choice-box').attr('data-index', j).text(choices[i]).appendTo('#choice-block');
                j++;
            }
        }
    }
}

/**
 * Resets all of the fields to prepare for next question
 */
function nextQuestion() {
    $('#question-Image').css({ zoom: 1, '-moz-transform': 'scale(1)' });
    submt = true;
    $('#explanation').empty();
    $('#question').text(quiz[currentquestion]['question']);
    $('#pager').text(questionText + ' ' + Number(currentquestion + 1) + ' ' + questionTextOf + ' ' + quiz.length);
    if (quiz[currentquestion].hasOwnProperty('Image') && quiz[currentquestion]['Image'] != "") {
        if ($('#question-Image').length == 0) {
            $(document.createElement('img')).addClass('question-Image').attr('id', 'question-Image').attr('src', quiz[currentquestion]['Image']).attr('alt', htmlEncode(quiz[currentquestion]['question'])).insertAfter('#question');
        } else {
            $('#question-Image').attr('src', quiz[currentquestion]['Image']).attr('alt', htmlEncode(quiz[currentquestion]['question']));
        }
        var clicked = false;
        $('#question-Image').click(function () {
            if (clicked) {
                $('#question-Image').css({ zoom: 1, '-moz-transform': 'scale(1)' });
                clicked = false;
            }
            else {
                $('#question-Image').css({ zoom: 5, '-moz-transform': 'scale(3)' });
                clicked = true;
            }
        });
    } else {
        $('#question-Image').remove();
    }
    addChoices(quiz[currentquestion]['choices']);
    setupButtons();
}


/**
 * After a selection is submitted, checks if its the right answer
 *
 * @param {choice} number The li zero-based index of the choice picked
 */
function processQuestion(choice) {
    userQuestion = quiz[currentquestion];
    userChoice = $('.choice').eq(choice).text();
    var correctString = quiz[currentquestion]['correct'];
    if (userChoice == correctString) {
        $('.choice').eq(choice).css({ 'background-color': '#50D943' });
        $('#explanation').html('<strong><font color="darkgreen">' + correctText + '! &#10004;</font><br/></strong> ' + htmlEncode(quiz[currentquestion]['explanation']));
        score++;
    } else {
        allQuestions.push(userQuestion);        // redo mistaken question.
        var correctIndex = quiz[currentquestion]['choices'].indexOf(correctString);
        $('.choice').eq(correctIndex).css({ 'background-color': 'lightgreen' });
        $('.choice').eq(choice).css({ 'background-color': '#D92623' });
        $('#explanation').html('<strong><font color="darkred">' + wrongText + ' &#10008;</font><br/></strong> ' + htmlEncode(quiz[currentquestion]['explanation']));
    }
    currentquestion++;
    $('#submitbutton').html(nextQuestionText + ' &raquo;').on('click', function () {
        if (currentquestion == quiz.length) {
            endQuiz();
        } else {
            $(this).text(checkAnswer).css({ 'color': '#222' }).off('click');
            nextQuestion();
        }
    })
}

/**
 * Sets up the event listeners for each button.
 */
function setupButtons() {
    $('.choice').on('mouseover', function () {
        $(this).css({ 'background-color': '#e1e1e1' });
    });
    $('.choice').on('mouseout', function () {
        $(this).css({ 'background-color': '#fff' });
    })
    $('.choice').on('click', function () {
        picked = $(this).attr('data-index');
        $('.choice').removeAttr('style').off('mouseout mouseover');
        $(this).css({ 'border-color': '#222', 'font-weight': 700, 'background-color': '#c1c1c1' });
        if (submt) {
            submt = false;
            $('#submitbutton').css({ 'color': '#000' }).on('click', function () {
                $('.choice').off('click');
                $(this).off('click');
                processQuestion(picked);
            });
        }
    })
}

/**
 * Quiz ends, display a message.
 */
function endQuiz() {
    $('#question-Image').remove();
    $('#explanation').empty();
    $('#question').empty();
    $('#choice-block').empty();
    $('#submitbutton').remove();
    $('#question').text(doneQuizText1 + score + doneQuizText2 + quiz.length + doneQuizText3 + doneQuizText4);
    var grade = Math.round(score / quiz.length * 100);
    var gradeColor = 'black';
    var cheers = "";
    if (grade < 56) {
        cheers = 'You have failed.';
        gradeColor = 'darkred';
    }
    else if (grade > 90) {
        cheers = 'Wow! Amazing score!';
        gradeColor = 'darkgreen'
    }
    $(document.createElement('gradeH')).css({ 'text-align': 'center', 'font-size': '4em', 'color': gradeColor }).text(grade).insertAfter('#question');
    $(document.createElement('space')).html('<br/>').insertAfter('gradeH');
    $(document.createElement('cheers')).css({ 'color': gradeColor }).text(cheers).insertAfter('space');

}

/**
* Runs the first time and creates all of the elements for the quiz
*/
function init() {
    $("#frame").empty();

    //add title
    $(document.createElement('h1')).text(quizTitle).appendTo('#frame');

    //add pager and questions
    if (typeof quiz !== "undefined" && $.type(quiz) === "array") {
        //add pager
        $(document.createElement('p')).addClass('pager').attr('id', 'pager').text(questionText + ' 1 ' + questionTextOf + ' ' + quiz.length).appendTo('#frame');
        //add first question
        $(document.createElement('h2')).addClass('question').attr('id', 'question').text(quiz[0]['question']).appendTo('#frame');
        //add Image if present
        if (quiz[0].hasOwnProperty('Image') && quiz[0]['Image'] != "") {
            $(document.createElement('img')).addClass('question-Image').attr('id', 'question-Image').attr('src', quiz[0]['Image']).attr('alt', htmlEncode(quiz[0]['question'])).appendTo('#frame');
        }
        $(document.createElement('p')).addClass('explanation').attr('id', 'explanation').html('&nbsp;').appendTo('#frame');

        //questions holder
        $(document.createElement('ul')).attr('id', 'choice-block').appendTo('#frame');

        //add choices
        addChoices(quiz[0]['choices']);

        //add submit button
        $(document.createElement('div')).addClass('choice-box').attr('id', 'submitbutton').text(checkAnswer).css({ 'font-weight': 700, 'color': '#222', 'padding': '30px 0' }).appendTo('#frame');

        setupButtons();
    }
}


/************************************************** FireBase Related *********************************************/
/**
 * A Question
 */
function CQuestion(ch, co, exp, im, que) {
    this.correct = co;
    this.choices = ch;
    this.explanation = exp;
    this.Image = im;
    this.question = que;
}

/**
 * get a random question number between (0, size-1).
 * Represented as string
 */
function getRandomQNum(size) {
    var num = Math.floor(Math.random() * size);
    while (qSet.includes(num))
        num = Math.floor(Math.random() * size);
    return num;
}

/**
* Shuffles array in place. ES6 version
* @param {Array} a items An array containing the items.
*/
function shuffle(a) {
    for (let i = a.length - 1; i > 0; i--) {
        const j = Math.floor(Math.random() * (i + 1));
        [a[i], a[j]] = [a[j], a[i]];
    }
    return a;
}

function loadAllQuestions() {
    document.getElementById("myProgress").style.display = "block";
    document.getElementById("newquiz").style.display = "none";
    document.getElementById("reportMistake").style.display = "none";
    document.getElementById("frame").style.display = "none";
    allQuestions = [];
    allQuestionsSave = [];
    usedQuestions = [];
    rootRef.once('value', function (snapshot) {
        snapshot.forEach(function (childSnapshot) {
            var data = childSnapshot.val();
            var chs = data['choices'];
            var cArr = [chs.A, chs.B, chs.C, chs.D];
            var que = new CQuestion(cArr, data.correct, data.explanation, data.image, data.question);
            allQuestions.push(que);
            allQuestionsSave.push(que);
        });
        $("#qNumText").text(qInDB + allQuestions.length);
        if (allQuestionsSave.length < qInQuiz)
        {
            error(notEnoughQuestionMessage);
            return;
        }
        populateQuiz();
    });
}

/**
 * Display error message and stop animations.
 */
function error(errorMessage)
{
    document.getElementById("myProgress").style.display = "none";
    document.getElementById("newquiz").style.display = "block";
    document.getElementById("reportMistake").style.display = "block";
    document.getElementById("frame").style.display = "block";
    $("#frame").empty();
    $(document.createElement('h1')).html('<font color=darkred>'+errorMessage+'</font>').appendTo('#frame');

}

/**
  * Populate a quiz.
  */
function populateQuiz() {
    currentquestion = 0;
    score = 0;
    submt = true;
    picked = undefined;
    quiz = [];
    shuffle(allQuestions);
    for (var i = 0; i < qInQuiz; ++i) {
        var q = allQuestions.pop();
        if (q == undefined) {       // used all questions
            loadAllQuestions();
            return;
        }
        shuffle(q.choices);
        quiz.push(q);
    }

    document.getElementById("myProgress").style.display = "none";
    document.getElementById("newquiz").style.display = "block";
    document.getElementById("reportMistake").style.display = "block";
    document.getElementById("frame").style.display = "block";
    init();
}

var userChoice, userQuestion;
var askSupport;
function reportMistake() {
    if (userQuestion == undefined) {
        userQuestion = quiz[currentquestion];
    }
    var subject = quizTitle + " - Question Error";
    var body = "";
    body += "Question: " + userQuestion.question + "\n\n";
    body += "Correct Answer: " + userQuestion.correct + "\n\n";
    body += "Answers:\n";
    body += "\t" + userQuestion.choices[0] + "\n";
    body += "\t" + userQuestion.choices[1] + "\n";
    body += "\t" + userQuestion.choices[2] + "\n";
    body += "\t" + userQuestion.choices[3] + "\n\n";
    body += "Explanation: " + userQuestion.explanation + "\n\n";
    body += "User choice: " + userChoice + "\n\n";
    body += "Please Fix this please!!!";
    var mailString = 'mailto:'+supportMail+'?subject=' + subject + '&body=' + encodeURIComponent(body);
    alert(askSupport);
    window.open(mailString);
}

function generateBulk() {
    if (allQuestionsSave == undefined || allQuestionsSave.length == 0) {
        alert(notEnoughQuestionMessage);
        return;
    }
    var bulkQuestionsString = "";
    bulkQuestionsString += "<center><H1>"+quizTitle+"</H1><br/>";
    bulkQuestionsString += "<br/>Correct answer is colored <font color = \"green\">green</font>.";
    bulkQuestionsString += "<br/>Explanation is colored <font color = \"blue\">blue</font>.";
    bulkQuestionsString += "<br/>Total questions:" + allQuestionsSave.length;
    bulkQuestionsString += "<br/><br><button onClick=\"window.print();return false;\">print</button>"
    bulkQuestionsString += "</center><br/><hr/>";
    allQuestionsSave.forEach(function (q) {
        console.log(q);
        bulkQuestionsString += "<br/><b>" + q.question + "</b><br>";
        if (q.Image != undefined && q.Image != "") {
            bulkQuestionsString += "<br/><img src=\"" + q.Image + "\" " + "style=\"width:300px;height:150px;\" /><br/>";
        }
        q.choices.forEach(function (c) {
            if (c && c != "$"){ // if string is not empty nor $.
                if (c == q.correct) {
                    bulkQuestionsString += "<br/><strong><font color = \"green\">" + c + "</strong></font><br/>";
                }
                else bulkQuestionsString += "<br/>" + c + "</br>";
            }
        });
        if (q.explanation && q.explanation != "$") {
            bulkQuestionsString += "<br/><font color=\"blue\"><i>" + q.explanation + "</i></font>";
        }
        bulkQuestionsString += "<hr/>";
    });
    bulkQuestionsString += "<hr/><center>RK Code Solution © 2018-2019</center>";
    localStorage.setItem("bulkString", bulkQuestionsString);
    var win = window.open("bulk.html");
    win.focus();
}

function initQuiz() {
    askSupport = 'Describe the issue';
    questionText = 'Question';
    questionTextOf = 'out of';
    checkAnswer = 'check answer';
    doneQuizText1 = "You answered right ";
    doneQuizText2 = " out of ";
    doneQuizText3 = ".";
    doneQuizText4 = " Your grade is: ";
    nextQuestionText = "next question";
    correctText = 'correct';
    wrongText = 'wrong'
    qInDB = 'Total Questions: ';
    notEnoughQuestionMessage = 'There are not enough questions to fill ' + qInQuiz +  'questions in a quiz.';
    $("#newquiz").text('Load new Quiz');
    $("#reportMistake").text('Report Mistake');
    $("#genBulk").text('All questions view');
    $("#setQuizSize").text('Set number of quesitons in quiz');
    $("#fetchingQ").text('Loading questions from database..');
    document.body.style = "text-align:right;unicode-bidi:bidi-override; direction:rtl;"
    rootRef = fb.database().ref('rootName');
    loadAllQuestions();
}
/************************************* MAIN **********************************************************/

var qInQuiz = 10;       // default
var nextQuestionText, correctText, qInDB;
var checkAnswer, questionText, notEnoughQuestionMessage;
var questionTextOf, doneQuizText1, doneQuizText2, doneQuizText3, doneQuizText4;
var currentquestion = 0, score = 0, submt = true, picked;
var allQuestions = [], allQuestionsSave = [], usedQuestions = [], quiz = [];
var rootRef;


/// ToDo: Replace firebaseConfig with your firebase project config.
// Your web app's Firebase configuration
var firebaseConfig = {
  apiKey: "AIzaSyAdMrcFDpKBsAE_mgMmzeuUcJHi1W4co1o",
  authDomain: "inifibackojj.firebaseapp.com",
  databaseURL: "https://inifibackojj-default-rtdb.firebaseio.com",
  projectId: "inifibackojj",
  storageBucket: "inifibackojj.appspot.com",
  messagingSenderId: "317047502444",
  appId: "1:317047502444:web:7ad6632d1ff977d74dca49",
  measurementId: "G-GP9MSPMPJ1"
};

// Initialize Firebase.
var fb = firebase.initializeApp(firebaseConfig);

window.onload = function () {
    initQuiz();
};

function changeLanguage(language) {
    var element = document.getElementById("url");
    element.value = language;
    element.innerHTML = language;
}

function showDropdown() {
    document.getElementById("myDropdown").classList.toggle("show");
}

/* Menu */
// Close the dropdown if the user clicks outside of it
window.onclick = function(event) {
    if (!event.target.matches('.dropbtn')) {
        var dropdowns = document.getElementsByClassName("dropdown-content");
        var i;
        for (i = 0; i < dropdowns.length; i++) {
            var openDropdown = dropdowns[i];
            if (openDropdown.classList.contains('show')) {
                openDropdown.classList.remove('show');
            }
        }
    }
}

function setQuizSize() {
    var descText = 'Set number of questions in a quiz';
    var errorText = 'Invalid input';
    var inputText = prompt(descText, qInQuiz);
    if (inputText == null) {
        return; // cancelled.
    }
    inputNumber = parseInt(inputText, 10); // radix 10
    if (isNaN(inputNumber) || inputNumber === undefined) {
        alert(errorText);
        return;
    }
    if (inputNumber < 1 || inputNumber > allQuestionsSave.length) {
        alert(errorText);
        return;
    }
    qInQuiz = inputNumber;
    populateQuiz();
}
