using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;

namespace QuestionGenerator
{
    public class QuestionHandler
    {
        public List<Question> Questions { get; private set; }       // List of currently loaded questions.
        public const string SEPARATOR = ";";                        // Question separator.
        public static readonly Encoding UTF8 = new UTF8Encoding(false);

        public QuestionHandler()
        {
            Questions = new List<Question>();
        }

        /// <summary>
        /// Remove duplicates questions from loaded questions.
        /// </summary>
        /// <returns>number of duplicates removed.</returns>
        public int RemoveDuplicates()
        {
            var before = Questions.Count;
            Questions = Questions.Distinct().ToList();
            return before - Questions.Count;
        }

        /// <summary>
        /// Parse questions from TXT file.
        /// </summary>
        /// <param name="filePath"></param>
        /// <param name="errMsg"></param>
        /// <param name="reset">Reset questions</param>
        /// <returns></returns>
        public bool ReadQuestionsFromTxt(string filePath, out string errMsg, bool reset = true)
        {
            errMsg = "";
            if (string.IsNullOrEmpty(filePath))
            {
                errMsg = "Invalid file path!";
                return false;
            }
            if (!filePath.ToLower().EndsWith(".txt"))
            {
                errMsg = "Not a text file!";
                return false;
            }
            if (!File.Exists(filePath))
            {
                errMsg = "File Doesn't exist!";
                return false;
            }

            var reader = new StreamReader(filePath, UTF8);
            var newList = new List<Question>();
            uint lineNumber = 0;
            do
            {
                string line;
                if (!ReadSingleLine(ref reader, ref lineNumber, out line, out errMsg))
                {
                    reader.Close();
                    return false;
                }

                var q = line;
                var choices = new string[Question.NUM_OF_ANS];
                for (var i = 0; i < choices.Length; ++i)
                {
                    if (!ReadSingleLine(ref reader, ref lineNumber, out line, out errMsg))
                    {
                        reader.Close();
                        return false;
                    }
                    choices[i] = line;
                }
                var question = new Question(q, choices);
                if (!ReadSingleLine(ref reader, ref lineNumber, out line, out errMsg))
                {
                    reader.Close();
                    return false;
                }

                if (!line.Equals(SEPARATOR))
                {
                    question.SetExplanation(line);
                    if (!ReadSingleLine(ref reader, ref lineNumber, out line, out errMsg))
                    {
                        reader.Close();
                        return false;
                    }

                    if (!line.Equals(SEPARATOR))
                    {
                        question.SetImage(line);
                        if (!ReadSingleLine(ref reader, ref lineNumber, out line, out errMsg))
                        {
                            reader.Close();
                            return false;
                        }

                        if (!line.Equals(SEPARATOR))
                        {
                            errMsg = "Invalid Question format = line #" + lineNumber;
                            reader.Close();
                            return false;
                        }
                    }
                }
                newList.Add(question);
            } while (!reader.EndOfStream);

            if (reset)
            {
                Questions = newList;
            }
            else
            {
                Questions.AddRange(newList);
            }
            return true;
        }

        private static bool ReadSingleLine(ref StreamReader reader, ref uint lineNum, out string line, out string errMsg)
        {
            if (reader.EndOfStream)
            {
                errMsg = "Unexpected end of stream reached";
                line = "";
                return false;
            }
            line = reader.ReadLine();
            lineNum++;
            if (string.IsNullOrEmpty(line))
            {
                errMsg = "Invalid Question - line #" + lineNum;
                return false;
            }
            errMsg = "";
            return true;
        }


        public bool WriteQuestionsAsJson(bool append, string filePath, out string errMsg, string wrapper = "")
        {
            if (string.IsNullOrEmpty(filePath))
            {
                errMsg = "Invalid file path";
                return false;
            }

            if (!filePath.ToLower().EndsWith(".json"))
                filePath += ".json";

            errMsg = "";
            
            using (var writer = new StreamWriter(filePath, append, UTF8))
            {
                if (string.IsNullOrEmpty(wrapper))
                {
                    writer.Write(", ");
                }
                else
                {
                    writer.WriteLine("{");
                    writer.WriteLine("  \"" + wrapper + "\" : [  ");
                }

                for (var i = 0; i < Questions.Count; ++i)
                {
                    var question = Questions[i];
                    if (!question.IsValid(out var curErrMsg))
                    {
                        errMsg += "\n" + curErrMsg;
                        continue;
                    }
                    writer.WriteLine(" {");
                    writer.WriteLine("    \"choices\" : {");
                    writer.WriteLine(GetJsonString(true, "A", question.Choices[0], "      "));
                    writer.WriteLine(GetJsonString(true, "B", question.Choices[1], "      "));
                    writer.WriteLine(GetJsonString(true, "C", question.Choices[2], "      "));
                    writer.WriteLine(GetJsonString(false, "D", question.Choices[3], "      "));
                    writer.WriteLine("    },");
                    writer.WriteLine(GetJsonString(true, "correct", question.CorrectAnswer, "    "));
                    writer.WriteLine(GetJsonString(true, "explanation", question.Explanation, "    "));
                    writer.WriteLine(GetJsonString(true, "image", question.Image, "    "));
                    writer.WriteLine(GetJsonString(false, "question", question.QuestionText, "    "));
                    writer.Write(i == Questions.Count - 1 ? "  }" : "  },");
                }

                if (!string.IsNullOrEmpty(wrapper))
                {
                    writer.WriteLine("  ]");
                    writer.WriteLine("}");
                }
            }
            return true;
        }

        private string GetJsonString(bool sep, string key, string value, string indent = "")
        {
            var jsonString =  indent + "\"" + key + "\" : \"" + value + "\"";
            if (sep)
                jsonString += ",";
            return jsonString;
        }
    }
}
