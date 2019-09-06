using System.Linq;
using System.Text.RegularExpressions;

namespace QuestionGenerator
{
    /// <summary>
    /// Question representing class.
    /// </summary>
    public class Question
    {
        public string QuestionText { get; }          // The question itself.
        public string CorrectAnswer { get; }         // Correct answer.
        public string[] Choices { get; }             // Choices.
        public const int NUM_OF_ANS = 4;             // Number of answers.
        public string Image { get; private set; }            // Image (optional).
        public string Explanation { get; private set; }      // Explanation (optional).
  
 
        public Question(string question, string[] choices)
        {
            QuestionText = FixString(question, true);
            Choices = new string[NUM_OF_ANS];
            for (var i = 0; i < Choices.Length; ++i)
            {
                if (choices[i] == null || "$".Equals(choices[i]))
                    Choices[i] = "";
                else Choices[i] = FixString(choices[i]);
            }
            CorrectAnswer = Choices[0];
            Image = "";
            Explanation = "";
        }

        /// <summary>
        /// Prepare a string to valid JSON format.
        /// </summary>
        private static string FixString(string str, bool isKey = false)
        {
            var fixedStr = str == null ? "" : str.Replace("\\", "/").Replace("\"", "\'");
            if (isKey)
            {
                var rgx = new Regex("[^א-תa-zA-Z0-9_: ,()'?-]");
                fixedStr = rgx.Replace(fixedStr, "");
            }
            return fixedStr.Trim();
        }

        public void SetImage(string image)
        {
            Image = FixString(image);
        }

        public void SetExplanation(string exp)
        {
            Explanation = "$".Equals(exp) ? "" : FixString(exp);
        }

        /// <summary>
        /// Check if the Question is in valid format.
        /// </summary>
        public bool IsValid(out string errMsg)
        {
            errMsg = "";
            if (string.IsNullOrEmpty(QuestionText))
            {
                errMsg = "Question text cannot be empty!";
                return false;
            }

            if (Choices.Length != NUM_OF_ANS)
            {
                errMsg = "There are no " + NUM_OF_ANS + " answers!";
                return false;
            }

            if (string.IsNullOrEmpty(CorrectAnswer))
            {
                errMsg = "Correct answer cannot be empty!";
                return false;
            }

            if (!Choices.Contains(CorrectAnswer))
            {
                errMsg = "Answers doesn't match correct answer.";
                return false;
            }

            return true;
        }

        public override string ToString()
        {
            return QuestionText;
        }

        public override bool Equals(object obj)
        {
            if (!(obj is Question))
                return false;
            return ((Question) obj).QuestionText.Equals(QuestionText);
        }

        public override int GetHashCode()
        {
            return (QuestionText != null ? QuestionText.GetHashCode() : 0);
        }
    }
}
