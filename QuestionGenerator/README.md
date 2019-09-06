
# Question Generator

Our question database inside firebase is represented by a JSON format. We need to provide the questions in specific JSON format.
In order to make it more easier, Question Generator reads simple format and generate a JSON file from it which can be loaded to our firebase project database.

Question Generator is .NET project written in C#. Minimal .NET required: v4. (probably compatible with earlier version but It wasn't tested).

## Project Structure

* QuestionGeneratorSolution folder contains the .NET solution for this project.
* Releases contains the Exe files that you can use to generate Questions in JSON format.
* Example folder contains example of input TXT files and corresponding output JSON files.

## The Format

The input format should be as follows:
```
1 Line Question Text
1 Line Correct answer
1 Line wrong answer
1 Line wrong answer
1 Line wrong answer
1 Line explanation text to be displayed after question is answered (optional)
1 Line picture link to be displayed when question shown (optional)
;
```
The semicolon symbol `;`  is to indicate question closure.

### Format Examples:
```
Question with explanation and picture link.
Correct answer
wrong answer
wrong answer
wrong answer
Explanation
Picture Link
;
Question with explanation and without picture link.
Correct answer
wrong answer
wrong answer
wrong answer
Explanation
;
Question without explanation and with picture link.
Correct answer
wrong answer
wrong answer
wrong answer
$
Picture Link
;
Question without explanation and picture link.
Correct answer
wrong answer
wrong answer
wrong answer
;
Question with less than 4 answers. Can be combined with other formats.
Correct answer
wrong answer
$
$
;
```

Loading these format examples and generating JSON file from it will yield:

![QuestionGenerator screenshot](https://raw.githubusercontent.com/Romansko/QuizApp/master/QuestionGenerator/screenshot.PNG)

```
{
  "rootName" : [  
 {
    "choices" : {
      "A" : "Correct answer",
      "B" : "wrong answer",
      "C" : "wrong answer",
      "D" : "wrong answer"
    },
    "correct" : "Correct answer",
    "explanation" : "Explanation",
    "image" : "Picture Link",
    "question" : "Question with explanation and picture link"
  }, {
    "choices" : {
      "A" : "Correct answer",
      "B" : "wrong answer",
      "C" : "wrong answer",
      "D" : "wrong answer"
    },
    "correct" : "Correct answer",
    "explanation" : "Explanation",
    "image" : "",
    "question" : "Question with explanation and without picture link"
  }, {
    "choices" : {
      "A" : "Correct answer",
      "B" : "wrong answer",
      "C" : "wrong answer",
      "D" : "wrong answer"
    },
    "correct" : "Correct answer",
    "explanation" : "$",
    "image" : "Picture Link",
    "question" : "Question without explanation and with picture link"
  }, {
    "choices" : {
      "A" : "Correct answer",
      "B" : "wrong answer",
      "C" : "wrong answer",
      "D" : "wrong answer"
    },
    "correct" : "Correct answer",
    "explanation" : "",
    "image" : "",
    "question" : "Question without explanation and picture link"
  }, {
    "choices" : {
      "A" : "Correct answer",
      "B" : "wrong answer",
      "C" : "$",
      "D" : "$"
    },
    "correct" : "Correct answer",
    "explanation" : "",
    "image" : "",
    "question" : "Question with less than 4 answers Can be combined with other formats"
  }  ]
}
```


##### *QuestionGenerator was developed with Visual Studio 2017*.
