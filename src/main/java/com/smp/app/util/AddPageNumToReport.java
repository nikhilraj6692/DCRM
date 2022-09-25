package com.smp.app.util;


import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.IOException;
import java.net.MalformedURLException;


public class AddPageNumToReport extends PdfPageEventHelper {

    public static String footerMagmaImgPath;
    public static String footerCompanyImgPath;
    Font font;
    PdfTemplate t;
    Image total;

    @Override
    public void onOpenDocument(PdfWriter writer, Document document) {
        t = writer.getDirectContent().createTemplate(30, 16);
        try {
            total = Image.getInstance(t);
            total.setRole(PdfName.ARTIFACT);
            font = new Font(FontFamily.TIMES_ROMAN, 11, Font.NORMAL);
        } catch (DocumentException de) {
            throw new ExceptionConverter(de);
        }
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        PdfPTable table = new PdfPTable(1);
        table.setTotalWidth(520);
        table.setLockedWidth(true);
        //table.getDefaultCell().setBorderWidth(0f);
        table.getDefaultCell().setFixedHeight(30);
        table.getDefaultCell().setBorder(Rectangle.TOP);
        table.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);

        PdfPCell pageNumCell = new PdfPCell(new Paragraph("DCRM", font));
        pageNumCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        /*pageNumCell.setBorderWidth(0f);*/
        pageNumCell.setBorder(Rectangle.TOP);
        pageNumCell.setBorderColor(BaseColor.LIGHT_GRAY);
        table.addCell(pageNumCell);
        PdfContentByte canvas = writer.getDirectContent();
        canvas.beginMarkedContentSequence(PdfName.ARTIFACT);
        table.writeSelectedRows(0, -1, 32, 30, canvas);

        Image footerLeftImg = null;
        Image footerRightImg = null;
        try {
            footerLeftImg = Image.getInstance(footerMagmaImgPath);
            footerLeftImg.scaleToFit(40, 30);
            footerLeftImg.setAbsolutePosition(40, 5);
            writer.getDirectContent().addImage(footerLeftImg);

            footerRightImg = Image.getInstance(footerCompanyImgPath);
            footerRightImg.scaleToFit(30, 20);
            footerRightImg.setAbsolutePosition(526, 8);
            writer.getDirectContent().addImage(footerRightImg);
        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        canvas.endMarkedContentSequence();
    }

    @Override
    public void onCloseDocument(PdfWriter writer, Document document) {
        ColumnText.showTextAligned(t, Element.ALIGN_LEFT, new Phrase(String.valueOf(writer.getPageNumber()), font), 2, 4, 0);
    }
}

