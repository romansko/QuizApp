namespace QuestionGenerator
{
    partial class QuestionGenerator
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(QuestionGenerator));
            this.InputGroupBox = new System.Windows.Forms.GroupBox();
            this.IgnoreDupCB = new System.Windows.Forms.CheckBox();
            this.OpenInputFile = new System.Windows.Forms.Button();
            this.OpenInputFileLocation = new System.Windows.Forms.Button();
            this.InputFilePathTextBox = new System.Windows.Forms.TextBox();
            this.openFileDialog = new System.Windows.Forms.OpenFileDialog();
            this.OutputGroupBox = new System.Windows.Forms.GroupBox();
            this.AppendCB = new System.Windows.Forms.CheckBox();
            this.WrapperTB = new System.Windows.Forms.TextBox();
            this.SaveAsInputFile = new System.Windows.Forms.Button();
            this.AddWrapperCB = new System.Windows.Forms.CheckBox();
            this.SaveInputFile = new System.Windows.Forms.Button();
            this.OpenOutputFileLocation = new System.Windows.Forms.Button();
            this.OutputFilePathTextBox = new System.Windows.Forms.TextBox();
            this.saveFileDialog = new System.Windows.Forms.SaveFileDialog();
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            this.InputGroupBox.SuspendLayout();
            this.OutputGroupBox.SuspendLayout();
            this.SuspendLayout();
            // 
            // InputGroupBox
            // 
            this.InputGroupBox.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.InputGroupBox.Controls.Add(this.IgnoreDupCB);
            this.InputGroupBox.Controls.Add(this.OpenInputFile);
            this.InputGroupBox.Controls.Add(this.OpenInputFileLocation);
            this.InputGroupBox.Controls.Add(this.InputFilePathTextBox);
            this.InputGroupBox.Location = new System.Drawing.Point(14, 14);
            this.InputGroupBox.Margin = new System.Windows.Forms.Padding(5);
            this.InputGroupBox.Name = "InputGroupBox";
            this.InputGroupBox.Padding = new System.Windows.Forms.Padding(10);
            this.InputGroupBox.Size = new System.Drawing.Size(579, 86);
            this.InputGroupBox.TabIndex = 4;
            this.InputGroupBox.TabStop = false;
            this.InputGroupBox.Text = "Input txt file";
            // 
            // IgnoreDupCB
            // 
            this.IgnoreDupCB.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.IgnoreDupCB.AutoSize = true;
            this.IgnoreDupCB.Location = new System.Drawing.Point(13, 62);
            this.IgnoreDupCB.Name = "IgnoreDupCB";
            this.IgnoreDupCB.Size = new System.Drawing.Size(177, 17);
            this.IgnoreDupCB.TabIndex = 4;
            this.IgnoreDupCB.Text = "Ignore duplicates upon loading. ";
            this.toolTip.SetToolTip(this.IgnoreDupCB, "Ignore duplicate questions when loading new input file.");
            this.IgnoreDupCB.UseVisualStyleBackColor = true;
            // 
            // OpenInputFile
            // 
            this.OpenInputFile.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.OpenInputFile.ForeColor = System.Drawing.SystemColors.WindowText;
            this.OpenInputFile.Location = new System.Drawing.Point(13, 28);
            this.OpenInputFile.Margin = new System.Windows.Forms.Padding(0);
            this.OpenInputFile.Name = "OpenInputFile";
            this.OpenInputFile.Size = new System.Drawing.Size(51, 21);
            this.OpenInputFile.TabIndex = 3;
            this.OpenInputFile.Text = "Load";
            this.OpenInputFile.UseVisualStyleBackColor = true;
            this.OpenInputFile.Click += new System.EventHandler(this.openInputFile_Click);
            // 
            // OpenInputFileLocation
            // 
            this.OpenInputFileLocation.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.OpenInputFileLocation.Location = new System.Drawing.Point(439, 55);
            this.OpenInputFileLocation.Name = "OpenInputFileLocation";
            this.OpenInputFileLocation.Size = new System.Drawing.Size(127, 24);
            this.OpenInputFileLocation.TabIndex = 2;
            this.OpenInputFileLocation.Text = "Open File Location";
            this.OpenInputFileLocation.UseVisualStyleBackColor = true;
            this.OpenInputFileLocation.Click += new System.EventHandler(this.openInputFileLocation_Click);
            // 
            // InputFilePathTextBox
            // 
            this.InputFilePathTextBox.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.InputFilePathTextBox.BackColor = System.Drawing.Color.LightGray;
            this.InputFilePathTextBox.Enabled = false;
            this.InputFilePathTextBox.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.InputFilePathTextBox.Location = new System.Drawing.Point(70, 28);
            this.InputFilePathTextBox.Name = "InputFilePathTextBox";
            this.InputFilePathTextBox.ReadOnly = true;
            this.InputFilePathTextBox.ShortcutsEnabled = false;
            this.InputFilePathTextBox.Size = new System.Drawing.Size(496, 21);
            this.InputFilePathTextBox.TabIndex = 0;
            // 
            // openFileDialog
            // 
            this.openFileDialog.DefaultExt = "txt";
            this.openFileDialog.Filter = "TXT files|*.txt";
            this.openFileDialog.Title = "Open txt questions file";
            // 
            // OutputGroupBox
            // 
            this.OutputGroupBox.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.OutputGroupBox.Controls.Add(this.AppendCB);
            this.OutputGroupBox.Controls.Add(this.WrapperTB);
            this.OutputGroupBox.Controls.Add(this.SaveAsInputFile);
            this.OutputGroupBox.Controls.Add(this.AddWrapperCB);
            this.OutputGroupBox.Controls.Add(this.SaveInputFile);
            this.OutputGroupBox.Controls.Add(this.OpenOutputFileLocation);
            this.OutputGroupBox.Controls.Add(this.OutputFilePathTextBox);
            this.OutputGroupBox.Location = new System.Drawing.Point(14, 110);
            this.OutputGroupBox.Margin = new System.Windows.Forms.Padding(5);
            this.OutputGroupBox.Name = "OutputGroupBox";
            this.OutputGroupBox.Padding = new System.Windows.Forms.Padding(10);
            this.OutputGroupBox.Size = new System.Drawing.Size(579, 120);
            this.OutputGroupBox.TabIndex = 5;
            this.OutputGroupBox.TabStop = false;
            this.OutputGroupBox.Text = "Output json file";
            // 
            // AppendCB
            // 
            this.AppendCB.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.AppendCB.AutoSize = true;
            this.AppendCB.Location = new System.Drawing.Point(108, 55);
            this.AppendCB.Name = "AppendCB";
            this.AppendCB.Size = new System.Drawing.Size(91, 17);
            this.AppendCB.TabIndex = 7;
            this.AppendCB.Text = "Append to file";
            this.toolTip.SetToolTip(this.AppendCB, "Append to a given output file. (Won\'t overwrite).");
            this.AppendCB.UseVisualStyleBackColor = true;
            this.AppendCB.CheckedChanged += new System.EventHandler(this.AppendCB_CheckedChanged);
            // 
            // WrapperTB
            // 
            this.WrapperTB.Location = new System.Drawing.Point(108, 87);
            this.WrapperTB.MaxLength = 32;
            this.WrapperTB.Name = "WrapperTB";
            this.WrapperTB.Size = new System.Drawing.Size(307, 20);
            this.WrapperTB.TabIndex = 6;
            this.WrapperTB.Text = "rootName";
            this.WrapperTB.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.WrapperTB_KeyPress);
            // 
            // SaveAsInputFile
            // 
            this.SaveAsInputFile.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.SaveAsInputFile.ForeColor = System.Drawing.SystemColors.WindowText;
            this.SaveAsInputFile.Location = new System.Drawing.Point(13, 52);
            this.SaveAsInputFile.Margin = new System.Windows.Forms.Padding(0);
            this.SaveAsInputFile.Name = "SaveAsInputFile";
            this.SaveAsInputFile.Size = new System.Drawing.Size(62, 21);
            this.SaveAsInputFile.TabIndex = 5;
            this.SaveAsInputFile.Text = "Save as..";
            this.SaveAsInputFile.UseVisualStyleBackColor = true;
            this.SaveAsInputFile.Click += new System.EventHandler(this.SaveAsInputFile_Click);
            // 
            // AddWrapperCB
            // 
            this.AddWrapperCB.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.AddWrapperCB.AutoSize = true;
            this.AddWrapperCB.Location = new System.Drawing.Point(13, 90);
            this.AddWrapperCB.Name = "AddWrapperCB";
            this.AddWrapperCB.Size = new System.Drawing.Size(89, 17);
            this.AddWrapperCB.TabIndex = 4;
            this.AddWrapperCB.Text = "Add Wrapper";
            this.toolTip.SetToolTip(this.AddWrapperCB, "Add a JSON node that wraps all the questions. Needed for newly created JSON files" +
        ".");
            this.AddWrapperCB.UseVisualStyleBackColor = true;
            this.AddWrapperCB.CheckedChanged += new System.EventHandler(this.AddWrapperCB_CheckedChanged);
            // 
            // SaveInputFile
            // 
            this.SaveInputFile.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.SaveInputFile.ForeColor = System.Drawing.SystemColors.WindowText;
            this.SaveInputFile.Location = new System.Drawing.Point(13, 28);
            this.SaveInputFile.Margin = new System.Windows.Forms.Padding(0);
            this.SaveInputFile.Name = "SaveInputFile";
            this.SaveInputFile.Size = new System.Drawing.Size(51, 21);
            this.SaveInputFile.TabIndex = 3;
            this.SaveInputFile.Text = "Save";
            this.SaveInputFile.UseVisualStyleBackColor = true;
            this.SaveInputFile.Click += new System.EventHandler(this.SaveInputFile_Click);
            // 
            // OpenOutputFileLocation
            // 
            this.OpenOutputFileLocation.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.OpenOutputFileLocation.Location = new System.Drawing.Point(439, 55);
            this.OpenOutputFileLocation.Name = "OpenOutputFileLocation";
            this.OpenOutputFileLocation.Size = new System.Drawing.Size(127, 24);
            this.OpenOutputFileLocation.TabIndex = 2;
            this.OpenOutputFileLocation.Text = "Open File Location";
            this.OpenOutputFileLocation.UseVisualStyleBackColor = true;
            this.OpenOutputFileLocation.Click += new System.EventHandler(this.OpenOutputFileLocation_Click);
            // 
            // OutputFilePathTextBox
            // 
            this.OutputFilePathTextBox.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.OutputFilePathTextBox.BackColor = System.Drawing.Color.LightGray;
            this.OutputFilePathTextBox.Enabled = false;
            this.OutputFilePathTextBox.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.OutputFilePathTextBox.Location = new System.Drawing.Point(70, 28);
            this.OutputFilePathTextBox.Name = "OutputFilePathTextBox";
            this.OutputFilePathTextBox.ReadOnly = true;
            this.OutputFilePathTextBox.ShortcutsEnabled = false;
            this.OutputFilePathTextBox.Size = new System.Drawing.Size(496, 21);
            this.OutputFilePathTextBox.TabIndex = 0;
            // 
            // saveFileDialog
            // 
            this.saveFileDialog.DefaultExt = "json";
            this.saveFileDialog.Filter = "JSON files|*.json";
            this.saveFileDialog.Title = "Save json questions file";
            // 
            // QuestionGenerator
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(607, 244);
            this.Controls.Add(this.OutputGroupBox);
            this.Controls.Add(this.InputGroupBox);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Name = "QuestionGenerator";
            this.Text = "Title";
            this.InputGroupBox.ResumeLayout(false);
            this.InputGroupBox.PerformLayout();
            this.OutputGroupBox.ResumeLayout(false);
            this.OutputGroupBox.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.GroupBox InputGroupBox;
        private System.Windows.Forms.CheckBox IgnoreDupCB;
        private System.Windows.Forms.Button OpenInputFile;
        private System.Windows.Forms.Button OpenInputFileLocation;
        private System.Windows.Forms.TextBox InputFilePathTextBox;
        private System.Windows.Forms.OpenFileDialog openFileDialog;
        private System.Windows.Forms.GroupBox OutputGroupBox;
        private System.Windows.Forms.CheckBox AddWrapperCB;
        private System.Windows.Forms.Button SaveInputFile;
        private System.Windows.Forms.Button OpenOutputFileLocation;
        private System.Windows.Forms.TextBox OutputFilePathTextBox;
        private System.Windows.Forms.Button SaveAsInputFile;
        private System.Windows.Forms.SaveFileDialog saveFileDialog;
        private System.Windows.Forms.TextBox WrapperTB;
        private System.Windows.Forms.CheckBox AppendCB;
        private System.Windows.Forms.ToolTip toolTip;
    }
}

