using System;
using System.Diagnostics;
using System.IO;
using System.Text.RegularExpressions;
using System.Windows.Forms;
using QuestionGenerator.Properties;

namespace QuestionGenerator
{

    public partial class QuestionGenerator : Form
    {
        private readonly QuestionHandler _questionHandler;
        public QuestionGenerator()
        {
            InitializeComponent();
            WrapperTB.Visible = false;
            Text = Resources.TITLE;
            _questionHandler = new QuestionHandler();;
        }

        public sealed override string Text
        {
            get => base.Text;
            set => base.Text = value;
        }


        private static void OpenFileLocation(string filePath)
        {
            if (File.Exists(filePath))
            {
                Process.Start("explorer.exe", "/select, " + filePath);
            }
            else
            {
                MessageBox.Show(@"File doesn't exists!", @"Warning", MessageBoxButtons.OK, MessageBoxIcon.Warning);
            }
        }


        /********************************************* INPUT FILE RELATED *****************************************/
        private void openInputFile_Click(object sender, EventArgs e)
        {
            if (openFileDialog.ShowDialog() == DialogResult.OK)
            {
                var filePath = openFileDialog.FileName;
                if (!_questionHandler.ReadQuestionsFromTxt(filePath, out var msg))
                {
                    MessageBox.Show(msg, @"Warning", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                    InputFilePathTextBox.Text = msg;
                }
                else
                {
                    InputFilePathTextBox.Text = filePath;
                    var ending = "";
                    if (IgnoreDupCB.Checked)
                    {
                        var dup = _questionHandler.RemoveDuplicates();
                        ending = "\nIgnored " + dup + " duplicate questions.";
                    }
                    msg = @"Scanned " + _questionHandler.Questions.Count + @" questions." + ending;
                    MessageBox.Show(msg, @"Success", MessageBoxButtons.OK, MessageBoxIcon.Information);
                }
            }
        }

        private void openInputFileLocation_Click(object sender, EventArgs e)
        {
            OpenFileLocation(InputFilePathTextBox.Text);
        }


        /**********************************************************************************************************/


        /********************************************* OUTPUT FILE RELATED *****************************************/
        private void OpenOutputFileLocation_Click(object sender, EventArgs e)
        {
            OpenFileLocation(OutputFilePathTextBox.Text);
        }

        private void SaveInputFile_Click(object sender, EventArgs e)
        {
            var filePath = OutputFilePathTextBox.Text;
            
            if (File.Exists(filePath))
            {
                if (AddWrapperCB.Checked)
                {
                    if (string.IsNullOrEmpty(WrapperTB.Text))
                    {
                        MessageBox.Show(@"Provide wrapper text or disable wrapper.", @"Wrapper error",
                            MessageBoxButtons.OK, MessageBoxIcon.Warning);
                        return;
                    }
                }
                SaveQuestionsAsJson(filePath);
            }
            else
            {
                SaveAsInputFile_Click(sender, e);
            }
        }

        private void SaveAsInputFile_Click(object sender, EventArgs e)
        {
            if (AddWrapperCB.Checked)
            {
                if (string.IsNullOrEmpty(WrapperTB.Text))
                {
                    MessageBox.Show(@"Provide wrapper text or disable wrapper.", @"Wrapper error",
                        MessageBoxButtons.OK, MessageBoxIcon.Warning);
                    return;
                }
            }

            if (saveFileDialog.ShowDialog() == DialogResult.OK)
            {
                SaveQuestionsAsJson(saveFileDialog.FileName);
            }
        }

        private void SaveQuestionsAsJson(string filePath)
        {
            string errMsg;
            var saved = AddWrapperCB.Checked
                ? _questionHandler.WriteQuestionsAsJson(AppendCB.Checked, filePath, out errMsg, WrapperTB.Text)
                : _questionHandler.WriteQuestionsAsJson(AppendCB.Checked, filePath, out errMsg);

            if (saved)
            {
                MessageBox.Show(@"Questions successfully saved.", @"Questions successfully saved", MessageBoxButtons.OK,
                    MessageBoxIcon.Information);
                OutputFilePathTextBox.Text = filePath;
            }
            else
            {
                MessageBox.Show(errMsg, @"Failed saving questions", MessageBoxButtons.OK, MessageBoxIcon.Information);
            }
        }

        private void WrapperTB_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (!char.IsControl(e.KeyChar) && !Regex.IsMatch(e.KeyChar.ToString(), @"[a-zA-Z0-9]"))
            {
                e.Handled = true;
            }
        }

        private void AddWrapperCB_CheckedChanged(object sender, EventArgs e)
        {
            if (!(sender is CheckBox)) return;
            var cb = (CheckBox) sender;
            WrapperTB.Visible = cb.Checked;
        }

        private void AppendCB_CheckedChanged(object sender, EventArgs e)
        {
            if (!(sender is CheckBox)) return;
            var cb = (CheckBox)sender;
            if (cb.Checked)
            {
                AddWrapperCB.Checked = false;
                AddWrapperCB.Enabled = false;
            }
            else
            {
                AddWrapperCB.Enabled = true;
            }
        }

        /**********************************************************************************************************/
    }
}
