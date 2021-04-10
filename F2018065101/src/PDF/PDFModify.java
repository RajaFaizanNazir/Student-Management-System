/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PDF;

/**
 *
 * @author RFaiz
 */
import java.io.FileOutputStream;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import java.util.ArrayList;

/**
 * This class is used to modify an existing pdf file using iText jar.
 *
 * @author javawithease
 */
public class PDFModify {

    public static void write(ArrayList<String> listl, ArrayList<String> listr, int x, int y) {
        try {
            //Create PdfReader instance.
            PdfReader pdfReader
                    = new PdfReader("Status-Template.pdf");
            System.out.println("File Readen");
            //Create PdfStamper instance.
            PdfStamper pdfStamper = new PdfStamper(pdfReader,
                    new FileOutputStream("Status.pdf"));

            //Create BaseFont instance.
            BaseFont baseFont = BaseFont.createFont(
                    BaseFont.TIMES_ROMAN,
                    BaseFont.CP1252, BaseFont.NOT_EMBEDDED);

            //Get the number of pages in pdf.
            int pages = pdfReader.getNumberOfPages();

            //Iterate the pdf through pages.
            for (int i = 1; i <= pages; i++) {
                //Contain the pdf data.
                PdfContentByte pageContentByte
                        = pdfStamper.getOverContent(i);
                pageContentByte.setFontAndSize(baseFont, 9);
                for (int j = 0; j < listl.size(); j++) {
                    pageContentByte.beginText();
                    pageContentByte.setTextMatrix(x, y);
                    pageContentByte.showText(listl.get(j));
                    
                    pageContentByte.beginText();
                    pageContentByte.setTextMatrix(x+100, y);
                    pageContentByte.showText(listr.get(j));
                    y-=12;
                }
                pageContentByte.endText();
            }

            //Close the pdfStamper.
            pdfStamper.close();
            System.out.println("PDF modified successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
