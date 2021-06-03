
namespace DIUTask.admin
{
    partial class ReportView
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
            this.dataGridReportView = new System.Windows.Forms.DataGridView();
            this.btnConvertPDF = new System.Windows.Forms.Button();
            this.txtShowReportName = new System.Windows.Forms.TextBox();
            this.folderBrowserDialog = new System.Windows.Forms.FolderBrowserDialog();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridReportView)).BeginInit();
            this.SuspendLayout();
            // 
            // dataGridReportView
            // 
            this.dataGridReportView.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridReportView.Location = new System.Drawing.Point(1, 27);
            this.dataGridReportView.Margin = new System.Windows.Forms.Padding(2, 2, 2, 2);
            this.dataGridReportView.Name = "dataGridReportView";
            this.dataGridReportView.RowHeadersWidth = 62;
            this.dataGridReportView.RowTemplate.Height = 28;
            this.dataGridReportView.Size = new System.Drawing.Size(717, 439);
            this.dataGridReportView.TabIndex = 0;
            // 
            // btnConvertPDF
            // 
            this.btnConvertPDF.Location = new System.Drawing.Point(3, 3);
            this.btnConvertPDF.Margin = new System.Windows.Forms.Padding(2, 2, 2, 2);
            this.btnConvertPDF.Name = "btnConvertPDF";
            this.btnConvertPDF.Size = new System.Drawing.Size(164, 20);
            this.btnConvertPDF.TabIndex = 1;
            this.btnConvertPDF.Text = "Download As PDF";
            this.btnConvertPDF.UseVisualStyleBackColor = true;
            this.btnConvertPDF.Click += new System.EventHandler(this.btnConvertPDF_Click);
            // 
            // txtShowReportName
            // 
            this.txtShowReportName.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtShowReportName.Location = new System.Drawing.Point(228, 5);
            this.txtShowReportName.Margin = new System.Windows.Forms.Padding(2, 2, 2, 2);
            this.txtShowReportName.Multiline = true;
            this.txtShowReportName.Name = "txtShowReportName";
            this.txtShowReportName.Size = new System.Drawing.Size(488, 17);
            this.txtShowReportName.TabIndex = 2;
            // 
            // ReportView
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(718, 467);
            this.Controls.Add(this.txtShowReportName);
            this.Controls.Add(this.btnConvertPDF);
            this.Controls.Add(this.dataGridReportView);
            this.Margin = new System.Windows.Forms.Padding(2, 2, 2, 2);
            this.Name = "ReportView";
            this.Text = "ReportView";
            this.Load += new System.EventHandler(this.ReportView_Load);
            ((System.ComponentModel.ISupportInitialize)(this.dataGridReportView)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.DataGridView dataGridReportView;
        private System.Windows.Forms.Button btnConvertPDF;
        private System.Windows.Forms.TextBox txtShowReportName;
        private System.Windows.Forms.FolderBrowserDialog folderBrowserDialog;
    }
}