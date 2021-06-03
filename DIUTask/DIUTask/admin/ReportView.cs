using iTextSharp.text;
using iTextSharp.text.pdf;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace DIUTask.admin
{
    public partial class ReportView : Form
    {

        private DataTable ReportData=null;
        private string ReportName;
        private string FileSaveLoc;

        public ReportView(DataTable myDataSet, string reportName)
        {
            InitializeComponent();
            txtShowReportName.Text = reportName;
            this.ReportData = myDataSet;
            this.ReportName = reportName;

            dataGridReportView.DataSource = myDataSet;

        }

        private void ReportView_Load(object sender, EventArgs e)
        {
        }

        private void btnConvertPDF_Click(object sender, EventArgs e)
        {

            folderBrowserDialog.ShowDialog();
            if (folderBrowserDialog.SelectedPath!=String.Empty)
            {
                Document document = new Document(PageSize.LETTER, 0f, 0f, 5f, 0f);
                string filePath = folderBrowserDialog.SelectedPath + "\\" + ReportName + ".pdf";
                PdfWriter.GetInstance(document, new FileStream(filePath, FileMode.Create));
                document.Open();
                PdfPTable table = new PdfPTable(ReportData.Columns.Count);

                PdfPCell cell = new PdfPCell(new Phrase(ReportName));
                cell.Colspan = (ReportData.Columns.Count);
                cell.HorizontalAlignment = Element.ALIGN_CENTER;
                table.AddCell(cell);

                //table.AddCell(ReportData.Columns.Count.ToString());
                for (int i = 0; i < ReportData.Columns.Count; i++)
                {
                    table.AddCell(ReportData.Columns[i].ColumnName.ToString());
                }

                for (int i = 0; i < ReportData.Rows.Count; i++)
                {
                    for (int j = 0; j < ReportData.Columns.Count; j++)
                    {
                        table.AddCell(ReportData.Rows[i][j].ToString());
                    }
                }

                document.Add(table);
                document.Close();

                MessageBox.Show("Data Save In "+filePath);

            }
        }
    }
}
