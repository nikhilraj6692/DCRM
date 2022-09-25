package com.smp.app.util;

import com.itextpdf.awt.DefaultFontMapper;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.smp.app.pojo.ReviewerPreviewDetailsResponseTO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.general.DefaultPieDataset;

public class PDFGenerator {

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static String dynamicTitleVersion = "";
    private static final Map<String, Integer> conformityRuleCountMap = new LinkedHashMap<String, Integer>();
    private static final Map<String, Integer> nonConformityRuleCountMap = new LinkedHashMap<String, Integer>();

    private PDFGenerator() {
        throw new IllegalAccessError("Utility class");
    }


    public static void projectPDFReportMApp(HttpServletRequest request, HttpServletResponse response,
        Map<String, List<ReviewerPreviewDetailsResponseTO>> projectRuleMapObject, CommonUtils commonUtils,
        ImageUpload imageUpload, Map<Integer, String> conformityLevelMapObj, String fileToStore,
        Map<Integer, List<String>> circularNameMapObject) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4, -10, -10, 43, 30);
        document.setPageSize(PageSize.A4);
        document.setMargins(-10, -10, 40, 50);
        document.setMarginMirroring(false);
        File folder = new File(fileToStore);
        OutputStream outputStream = new FileOutputStream(folder);
        //PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
        PdfWriter writer = PdfWriter.getInstance(document, outputStream);

        ReviewerPreviewDetailsResponseTO reviewerObject = projectRuleMapObject.values().iterator().next().get(0);

        String footerMagmaImgPath = request.getSession().getServletContext().getRealPath("/media/images/");
        //footerMagmaImgPath = footerMagmaImgPath + "/inner_logo.png";
        footerMagmaImgPath = footerMagmaImgPath + "/footer_left_side_logo.png";
        AddPageNumToReport.footerMagmaImgPath = footerMagmaImgPath;

        AddPageNumToReport.footerCompanyImgPath =
            commonUtils.readUserDefinedMessages("smp.cloud.project_logo.read_image_base_url")
                + reviewerObject.getProjectLogoName();

        writer.setTagged();
        writer.setPageEvent(new AddPageNumToReport());
        document.open();
        BaseFont arialBaseFont = BaseFont.createFont(
            request.getSession().getServletContext().getRealPath("/pdf_font_style/constan.ttf"), BaseFont.WINANSI, true);
        createCoverPage(document, reviewerObject, arialBaseFont, commonUtils, imageUpload, conformityLevelMapObj,
            circularNameMapObject, AddPageNumToReport.footerCompanyImgPath);
        projectDetail(document, projectRuleMapObject, arialBaseFont, commonUtils, imageUpload, conformityLevelMapObj,
            circularNameMapObject, reviewerObject);

        document.newPage();
        writeChartToPDF(generatePieChart("Conformity Status :", conformityRuleCountMap), 480, 300, writer, true);
        writeChartToPDF(generatePieChart("Non-Conformity Rule Details :", nonConformityRuleCountMap), 480, 300, writer,
            false);
        document.close();
    }

    private static void createCoverPage(Document document, ReviewerPreviewDetailsResponseTO previewDetails,
        BaseFont arialBaseFont, CommonUtils commonUtils, ImageUpload imageUpload, Map<Integer, String> conformityLevelMapObj,
        Map<Integer, List<String>> circularNameMapObject, String companyImagePath)
        throws DocumentException, IOException {

        //ReviewerPreviewDetailsResponseTO  previewDetails = projectRuleList.get(0);

        Font headerFontStyle = new Font(arialBaseFont, 17f, Font.BOLD | Font.UNDERLINE);
        headerFontStyle.setColor(new BaseColor(99, 158, 66));

        Font subLabelFontStyle = new Font(arialBaseFont, 13f, Font.NORMAL);
        //subLabelFontStyle.setColor(new BaseColor(99,158,66));
        subLabelFontStyle.setColor(new BaseColor(157, 155, 155));
        Font textContentFontStyle = new Font(arialBaseFont, 13f, Font.NORMAL);
        float fixedbottomPadding = 30f;

        PdfPTable coverPageDetailTbl = new PdfPTable(3);
        coverPageDetailTbl.setWidths(new int[]{1, 4, 1});
        coverPageDetailTbl.getDefaultCell().setBorderWidth(3f);
        coverPageDetailTbl.getDefaultCell().setBackgroundColor(BaseColor.LIGHT_GRAY);

        PdfPCell headerTitleCell = new PdfPCell(new Paragraph("OSH Review Report", headerFontStyle));
        headerTitleCell.setColspan(3);
        headerTitleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerTitleCell.setPaddingTop(17f);
        headerTitleCell.setPaddingBottom(fixedbottomPadding);
        coverPageDetailTbl.addCell(removeCellBorder(headerTitleCell));

        /*Name of the Mine*/
        PdfPCell mineNameLabelCell = new PdfPCell(new Paragraph("Name of the Mine", subLabelFontStyle));
        mineNameLabelCell.setColspan(3);
        mineNameLabelCell.setHorizontalAlignment(Element.ALIGN_CENTER);

        PdfPCell mineNameTextCell = new PdfPCell(new Paragraph(previewDetails.getComapnyName(), textContentFontStyle));
        mineNameTextCell.setColspan(3);
        mineNameTextCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        mineNameTextCell.setPaddingBottom(fixedbottomPadding);

        coverPageDetailTbl.addCell(removeCellBorder(mineNameLabelCell));
        coverPageDetailTbl.addCell(removeCellBorder(mineNameTextCell));

        /*Name of the Project*/
        PdfPCell projectNameLabelCell = new PdfPCell(new Paragraph("Name of the Project", subLabelFontStyle));
        projectNameLabelCell.setColspan(3);
        projectNameLabelCell.setHorizontalAlignment(Element.ALIGN_CENTER);

        PdfPCell projectNameTextCell = new PdfPCell(new Paragraph(previewDetails.getProjectName(), textContentFontStyle));
        projectNameTextCell.setColspan(3);
        projectNameTextCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        //projectNameTextCell.setPaddingBottom(fixedbottomPadding);

        coverPageDetailTbl.addCell(removeCellBorder(projectNameLabelCell));
        coverPageDetailTbl.addCell(removeCellBorder(projectNameTextCell));

        PdfPCell imageCell = new PdfPCell();
        Image reviewerAttachImage = Image.getInstance(companyImagePath);
        reviewerAttachImage.scaleToFit(165f, 110f);
        reviewerAttachImage.setAbsolutePosition(420, 300);
        imageCell.addElement(reviewerAttachImage);
        imageCell.setColspan(3);
        imageCell.setPaddingTop(32f);
        imageCell.setPaddingBottom(32f);
        imageCell.setPaddingLeft(180f);
        imageCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        coverPageDetailTbl.addCell(removeCellBorder(imageCell));




        /*Name of the OSH Reviewer*/
        PdfPCell reviewerLabelCell = new PdfPCell(new Paragraph("Name of the OSH Reviewer", subLabelFontStyle));
        reviewerLabelCell.setColspan(3);
        reviewerLabelCell.setHorizontalAlignment(Element.ALIGN_CENTER);

        PdfPCell reviewerNameCell = new PdfPCell(new Paragraph(previewDetails.getReviewerName(), textContentFontStyle));
        reviewerNameCell.setColspan(3);
        reviewerNameCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        reviewerNameCell.setPaddingBottom(fixedbottomPadding);

        coverPageDetailTbl.addCell(removeCellBorder(reviewerLabelCell));
        coverPageDetailTbl.addCell(removeCellBorder(reviewerNameCell));

        /*Name of the Lead Reviewer*/
        PdfPCell leadReviewerLabelCell = new PdfPCell(new Paragraph("Name of the Lead Reviewer", subLabelFontStyle));
        leadReviewerLabelCell.setColspan(3);
        leadReviewerLabelCell.setHorizontalAlignment(Element.ALIGN_CENTER);

        PdfPCell leadReviewerNameCell = new PdfPCell(new Paragraph("Dr. A.K.Sinha", textContentFontStyle));
        leadReviewerNameCell.setColspan(3);
        leadReviewerNameCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        leadReviewerNameCell.setPaddingBottom(fixedbottomPadding);

        coverPageDetailTbl.addCell(removeCellBorder(leadReviewerLabelCell));
        coverPageDetailTbl.addCell(removeCellBorder(leadReviewerNameCell));

        addEmptyLine(document, 2);
        document.add(coverPageDetailTbl);
        document.newPage();
    }


    public static void projectPDFReport(HttpServletRequest request, HttpServletResponse response,
        List<ReviewerPreviewDetailsResponseTO> projectRuleList, CommonUtils commonUtils, ImageUpload imageUpload,
        Map<Integer, String> conformityLevelMapObj, Map<Integer, List<String>> circularNameMapObject)
        throws DocumentException, IOException {
        Document document = new Document(PageSize.A4, -10, -10, 43, 30);
        document.setPageSize(PageSize.A4);
        document.setMargins(-10, -10, 40, 50);
        document.setMarginMirroring(false);

        PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
        writer.setTagged();
        writer.setPageEvent(new AddPageNumToReport());
        document.open();
        BaseFont arialBaseFont = BaseFont.createFont(
            request.getSession().getServletContext().getRealPath("/pdf_font_style/constan.ttf"), BaseFont.WINANSI, true);
        projectHeaderDetail(document, arialBaseFont);
        // projectDetail(document, projectRuleList, arialBaseFont, commonUtils, imageUpload, conformityLevelMapObj,
        // circularNameMapObject);
        document.close();
    }

    private static void projectHeaderDetail(Document document, BaseFont arialBaseFont) throws DocumentException {
        Font titleFontStyle = new Font(arialBaseFont, 15f, Font.BOLD | Font.UNDERLINE);

        PdfPTable headerDetailTbl = new PdfPTable(2);
        headerDetailTbl.setWidths(new int[]{3, 1});
        PdfPCell reportTitleCell = new PdfPCell(new Paragraph("Review Report", titleFontStyle));
        reportTitleCell.setColspan(2);
        reportTitleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        reportTitleCell.setPaddingTop(17f);

        headerDetailTbl.addCell(removeCellBorder(reportTitleCell));
        document.add(headerDetailTbl);
        addEmptyLine(document, 1);
    }


    private static PdfPCell ruleConformityTblHeaderCell(String headerLabel, BaseFont arialBaseFont) {
        Font headerLabelFontStyle = new Font(arialBaseFont, 12, Font.BOLD);

        PdfPCell pCell = new PdfPCell(new Paragraph(headerLabel, headerLabelFontStyle));
        pCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        pCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        pCell.setPaddingTop(7f);
        pCell.setPaddingBottom(5f);
        return pCell;
    }


    private static void projectDetail(Document document,
        Map<String, List<ReviewerPreviewDetailsResponseTO>> projectRuleMapObject, BaseFont arialBaseFont,
        CommonUtils commonUtils, ImageUpload imageUpload, Map<Integer, String> conformityLevelMapObj,
        Map<Integer, List<String>> circularNameMapObject, ReviewerPreviewDetailsResponseTO firstRuleObj)
        throws DocumentException, IOException {
        Font subTitleFontStyle = new Font(arialBaseFont, 12.5f, Font.BOLD | Font.UNDERLINE);
        Font reportTblHeaderFontStyle = new Font(arialBaseFont, 12.5f, Font.BOLD);

        Font subLabelFontStyle = new Font(arialBaseFont, 12.5f, Font.NORMAL);
        subLabelFontStyle.setColor(new BaseColor(99, 158, 66));
        Font textContentFontStyle = new Font(arialBaseFont, 12.5f, Font.NORMAL);

        conformityRuleCountMap.clear();
        nonConformityRuleCountMap.clear();
        addPieChartDefaultDataSetValue();

        //ReviewerPreviewDetailsResponseTO firstRuleObj = projectRuleList.get(0);

        // -- Rule Conformity Detail--
        PdfPTable ruleConformityDetailTbl = new PdfPTable(3);
        ruleConformityDetailTbl.setWidths(new float[]{1f, 4.8f, 3.1f});

        PdfPCell reportDetailHeadingCell = new PdfPCell(
            new Paragraph("Summary of review report :", reportTblHeaderFontStyle));
        reportDetailHeadingCell.setColspan(3);
        reportDetailHeadingCell.setPaddingBottom(6f);
        reportDetailHeadingCell.setBorderWidth(0f);
        ruleConformityDetailTbl.addCell(reportDetailHeadingCell);

        ruleConformityDetailTbl.addCell(ruleConformityTblHeaderCell("Sl No.", arialBaseFont));
        ruleConformityDetailTbl.addCell(ruleConformityTblHeaderCell("Provision No.", arialBaseFont));
        ruleConformityDetailTbl.addCell(ruleConformityTblHeaderCell("Status", arialBaseFont));

        int projectRuleCount = 0;
        int conformityTblSlNum = 0;

        for (String bookNameKey : projectRuleMapObject.keySet()) {
            List<ReviewerPreviewDetailsResponseTO> projectRuleList = projectRuleMapObject.get(bookNameKey);
            for (ReviewerPreviewDetailsResponseTO projectRuleObj : projectRuleList) {
                //System.out.println(projectRuleObj.getRuleTitle()+"<<<<<>>>>>"+projectRuleObj.getRuleId());

                conformityTblSlNum++;
                if (!projectRuleObj.getReviewerRemark().equalsIgnoreCase("na")) {
                    if (projectRuleCount != 0) {
                        document.newPage();
                    }
                    projectRuleCount++;

                    projectHeaderDetail(document, arialBaseFont);
                    PdfPTable projectDetailTbl = null;
                    projectDetailTbl = new PdfPTable(4);
                    projectDetailTbl.setWidths(new float[]{0.2f, 1.3f, 0.05f, 2.7f});

                    // 01 : Performance Sheet Num
                    PdfPCell sheetNumSeriNumCell = new PdfPCell(new Paragraph("1.", subLabelFontStyle));
                    PdfPCell sheetNumLabelCell = new PdfPCell(new Paragraph("Review Sheet No. ", subLabelFontStyle));

                    String reviewSheetNoDetail =
                        "OSH / " + projectRuleObj.getComapnyName() + " / " + projectRuleObj.getProjectVersionNum() + " / "
                            + projectRuleObj.getBookName() + " / " + projectRuleObj.getRuleSubclauseNum().trim();

                    PdfPCell sheetNumValueCell = new PdfPCell(new Paragraph(reviewSheetNoDetail, textContentFontStyle));
                    projectDetailTbl.addCell(removeCellBorder(sheetNumSeriNumCell));
                    projectDetailTbl.addCell(removeCellBorder(sheetNumLabelCell));
                    projectDetailTbl.addCell(addColonToLabel(subLabelFontStyle));
                    projectDetailTbl.addCell(removeCellBorder(sheetNumValueCell));

                    // 02 :Mine Name
                    PdfPCell mineNameSeriNumCell = new PdfPCell(new Paragraph("2.", subLabelFontStyle));
                    PdfPCell mineNameLabelCell = new PdfPCell(new Paragraph("Name of mine ", subLabelFontStyle));
                    PdfPCell mineNameValueCell = new PdfPCell(
                        new Paragraph(firstRuleObj.getComapnyName(), textContentFontStyle));
                    projectDetailTbl.addCell(removeCellBorder(mineNameSeriNumCell));
                    projectDetailTbl.addCell(removeCellBorder(mineNameLabelCell));
                    projectDetailTbl.addCell(addColonToLabel(subLabelFontStyle));
                    projectDetailTbl.addCell(removeCellBorder(mineNameValueCell));

                    // 03 : Project Name
                    PdfPCell projectNameSeriNumCell = new PdfPCell(new Paragraph("3.", subLabelFontStyle));
                    PdfPCell projectNameLabelCell = new PdfPCell(new Paragraph("Name of the project ", subLabelFontStyle));

                    String projectNameDetail =
                        "OSH / " + projectRuleObj.getComapnyName() + " / " + projectRuleObj.getProjectUniqueId() + " / " + (
                            projectRuleObj.getProjectSubmittedDate().getYear() + 1900) + " / "
                            + projectRuleObj.getProjectVersionNum();
                    PdfPCell projectNameValueCell = new PdfPCell(new Paragraph(projectNameDetail, textContentFontStyle));
                    projectDetailTbl.addCell(removeCellBorder(projectNameSeriNumCell));
                    projectDetailTbl.addCell(removeCellBorder(projectNameLabelCell));
                    projectDetailTbl.addCell(addColonToLabel(subLabelFontStyle));
                    projectDetailTbl.addCell(removeCellBorder(projectNameValueCell));

                    // 04 : Generated On
                    PdfPCell generatedOnSeriNumCell = new PdfPCell(new Paragraph("4.", subLabelFontStyle));
                    PdfPCell generatedOnLabelCell = new PdfPCell(new Paragraph("Generated on ", subLabelFontStyle));
                    PdfPCell generatedOnValueCell = new PdfPCell(
                        new Paragraph(formateDateObj(firstRuleObj.getProjectSubmittedDate()), textContentFontStyle));
                    projectDetailTbl.addCell(removeCellBorder(generatedOnSeriNumCell));
                    projectDetailTbl.addCell(removeCellBorder(generatedOnLabelCell));
                    projectDetailTbl.addCell(addColonToLabel(subLabelFontStyle));
                    projectDetailTbl.addCell(removeCellBorder(generatedOnValueCell));

                    // 05 : Printed On
                    PdfPCell printedOnSeriNumCell = new PdfPCell(new Paragraph("5.", subLabelFontStyle));
                    PdfPCell printedOnLabelCell = new PdfPCell(new Paragraph("Printed on ", subLabelFontStyle));
                    PdfPCell printedOnValueCell = new PdfPCell(
                        new Paragraph(formateDateObj(new Date()), textContentFontStyle));
                    projectDetailTbl.addCell(removeCellBorder(printedOnSeriNumCell));
                    projectDetailTbl.addCell(removeCellBorder(printedOnLabelCell));
                    projectDetailTbl.addCell(addColonToLabel(subLabelFontStyle));
                    projectDetailTbl.addCell(removeCellBorder(printedOnValueCell));

                    // 06 : Management Co-ordinator
                    PdfPCell managementCoOrdinSeriNumCell = new PdfPCell(new Paragraph("6.", subLabelFontStyle));
                    PdfPCell managementCoOrdinLabelCell = new PdfPCell(
                        new Paragraph("Management co-ordinator ", subLabelFontStyle));
                    PdfPCell managementCoOrdinValueCell = new PdfPCell(
                        new Paragraph(firstRuleObj.getManagementName(), textContentFontStyle));
                    projectDetailTbl.addCell(removeCellBorder(managementCoOrdinSeriNumCell));
                    projectDetailTbl.addCell(removeCellBorder(managementCoOrdinLabelCell));
                    projectDetailTbl.addCell(addColonToLabel(subLabelFontStyle));
                    projectDetailTbl.addCell(removeCellBorder(managementCoOrdinValueCell));

                    // 07 : OSH Reviewer
                    PdfPCell oshReviewerSeriNumCell = new PdfPCell(new Paragraph("7.", subLabelFontStyle));
                    PdfPCell oshReviewerLabelCell = new PdfPCell(new Paragraph("OSH Reviewer ", subLabelFontStyle));
                    PdfPCell oshReviewerValueCell = new PdfPCell(
                        new Paragraph(firstRuleObj.getReviewerName(), textContentFontStyle));
                    projectDetailTbl.addCell(removeCellBorder(oshReviewerSeriNumCell));
                    projectDetailTbl.addCell(removeCellBorder(oshReviewerLabelCell));
                    projectDetailTbl.addCell(addColonToLabel(subLabelFontStyle));
                    projectDetailTbl.addCell(removeCellBorder(oshReviewerValueCell));

                    //Rule SubClause Num
                    PdfPCell ruleSubclauseSeriNumCell = new PdfPCell(new Paragraph("8.", subLabelFontStyle));
                    PdfPCell ruleSubclauseLabelCell = new PdfPCell(
                        new Paragraph("Section / Rules / Regulations / Clause/ Subclause No.", subLabelFontStyle));
                    PdfPCell ruleSubclauseValueCell = new PdfPCell(new Paragraph(
                        projectRuleObj.getRuleSubclauseNum().trim() + " of " + projectRuleObj.getBookName().trim(),
                        textContentFontStyle));
                    projectDetailTbl.addCell(removeCellBorder(ruleSubclauseSeriNumCell));
                    projectDetailTbl.addCell(removeCellBorder(ruleSubclauseLabelCell));
                    projectDetailTbl.addCell(addColonToLabel(subLabelFontStyle));
                    projectDetailTbl.addCell(removeCellBorder(ruleSubclauseValueCell));

                    //Title of the Rules/Regulations
                    PdfPCell ruleTitleSeriNumCell = new PdfPCell(new Paragraph("9.", subLabelFontStyle));
                    PdfPCell ruleTitleLabelCell = new PdfPCell(
                        new Paragraph("Title of the Rules/Regulations", subLabelFontStyle));
                    PdfPCell ruleTitleValueCell = new PdfPCell(
                        new Paragraph(projectRuleObj.getRuleTitle(), textContentFontStyle));
                    projectDetailTbl.addCell(removeCellBorder(ruleTitleSeriNumCell));
                    projectDetailTbl.addCell(removeCellBorder(ruleTitleLabelCell));
                    projectDetailTbl.addCell(addColonToLabel(subLabelFontStyle));
                    projectDetailTbl.addCell(removeCellBorder(ruleTitleValueCell));

                    //Responsibility
                    PdfPCell responsibilitySerialNumCell = new PdfPCell(new Paragraph("10.", subLabelFontStyle));
                    PdfPCell responsibilityLabelCell = new PdfPCell(new Paragraph("Responsibility", subLabelFontStyle));
                    PdfPCell responsibilityValueCell = new PdfPCell(
                        new Paragraph(projectRuleObj.getRuleResponsibilty(), textContentFontStyle));
                    projectDetailTbl.addCell(removeCellBorder(responsibilitySerialNumCell));
                    projectDetailTbl.addCell(removeCellBorder(responsibilityLabelCell));
                    projectDetailTbl.addCell(addColonToLabel(subLabelFontStyle));
                    projectDetailTbl.addCell(removeCellBorder(responsibilityValueCell));

                    //Description
                    PdfPCell descriptionSerialNumCell = new PdfPCell(new Paragraph("11.", subLabelFontStyle));
                    PdfPCell descriptionLabelCell = new PdfPCell(new Paragraph("Description", subLabelFontStyle));
                    //PdfPCell descriptionValueCell = new PdfPCell(new Paragraph(formatRuleDescriptionText(projectRuleObj
                    // .getRuleDescription()), textContentFontStyle));
                    PdfPCell descriptionValueCell = createJustifyParagraph(
                        formatRuleDescriptionText(projectRuleObj.getRuleDescription()), textContentFontStyle);
                    projectDetailTbl.addCell(removeCellBorder(descriptionSerialNumCell));
                    projectDetailTbl.addCell(removeCellBorder(descriptionLabelCell));
                    projectDetailTbl.addCell(addColonToLabel(subLabelFontStyle));
                    projectDetailTbl.addCell(removeCellBorder(descriptionValueCell));

                    //Relevant Circulars
                    PdfPCell ruleCircularSerialNumCell = new PdfPCell(new Paragraph("12.", subLabelFontStyle));
                    PdfPCell ruleCircularLabelCell = new PdfPCell(new Paragraph("Related Circulars", subLabelFontStyle));

                    //String relevantCircularMesg = projectRuleObj.getRuleRelevantCircular();
                    String relevantCircularMesg = "";
                    if (circularNameMapObject.containsKey(projectRuleObj.getRuleId())) {
                        for (String circularName : circularNameMapObject.get(projectRuleObj.getRuleId())) {
                            if (relevantCircularMesg.trim().isEmpty()) {
                                relevantCircularMesg = circularName;
                            } else {
                                relevantCircularMesg = relevantCircularMesg + " , " + circularName;
                            }
                        }
                    } else {
                        relevantCircularMesg = "-";
                    }

                    PdfPCell ruleCircularValueCell = createJustifyParagraph(relevantCircularMesg, textContentFontStyle);
                    projectDetailTbl.addCell(removeCellBorder(ruleCircularSerialNumCell));
                    projectDetailTbl.addCell(removeCellBorder(ruleCircularLabelCell));
                    projectDetailTbl.addCell(addColonToLabel(subLabelFontStyle));
                    projectDetailTbl.addCell(removeCellBorder(ruleCircularValueCell));

                    //Management Remarks
                    PdfPCell managRemarkSerialNumCell = new PdfPCell(new Paragraph("13.", subLabelFontStyle));
                    PdfPCell managRemarkLabelCell = new PdfPCell(new Paragraph("Management Remarks", subLabelFontStyle));
                    //PdfPCell managRemarkValueCell = new PdfPCell(new Paragraph(projectRuleObj.getManagementComment(),
                    // textContentFontStyle));
                    PdfPCell managRemarkValueCell = createJustifyParagraph(projectRuleObj.getManagementComment(),
                        textContentFontStyle);
                    projectDetailTbl.addCell(removeCellBorder(managRemarkSerialNumCell));
                    projectDetailTbl.addCell(removeCellBorder(managRemarkLabelCell));
                    projectDetailTbl.addCell(addColonToLabel(subLabelFontStyle));
                    projectDetailTbl.addCell(removeCellBorder(managRemarkValueCell));

                    //---------Reviewer Feedback Comments-----------
                    PdfPCell reviewerFeedbackCell = new PdfPCell(new Paragraph("Reviewer Feedback", subTitleFontStyle));
                    reviewerFeedbackCell.setColspan(4);
                    reviewerFeedbackCell.setPaddingTop(13f);
                    reviewerFeedbackCell.setPaddingBottom(8f);
                    projectDetailTbl.addCell(removeCellBorder(reviewerFeedbackCell));

                    dynamicTitleVersion = "14.";
                    //Status Of The Conformity
                    PdfPCell conformityStatusSerialNumCell = new PdfPCell(
                        new Paragraph(dynamicTitleVersion, subLabelFontStyle));
                    PdfPCell conformityStatusCell = new PdfPCell(new Paragraph("Conformity", subLabelFontStyle));
                    PdfPCell conformityStatusValueCell = null;
                    if (projectRuleObj.getReviewerConformityStatus() == 0) {
                        conformityStatusValueCell = new PdfPCell(new Paragraph("No", textContentFontStyle));
                        conformityRuleCountMap.put("Non-Conformity", conformityRuleCountMap.get("Non-Conformity") + 1);
                    } else {
                        conformityStatusValueCell = new PdfPCell(new Paragraph("Yes", textContentFontStyle));
                        conformityRuleCountMap.put("Conformity", conformityRuleCountMap.get("Conformity") + 1);
                    }
                    projectDetailTbl.addCell(removeCellBorder(conformityStatusSerialNumCell));
                    projectDetailTbl.addCell(removeCellBorder(conformityStatusCell));
                    projectDetailTbl.addCell(addColonToLabel(subLabelFontStyle));
                    projectDetailTbl.addCell(removeCellBorder(conformityStatusValueCell));

                    //Level of Non-conformity :
                    if (projectRuleObj.getReviewerConformityStatus() == 0) {
                        dynamicTitleVersion = nextDynamicVersionNum(dynamicTitleVersion);
                        PdfPCell conformityLevelSerailNumCell = new PdfPCell(
                            new Paragraph(dynamicTitleVersion, subLabelFontStyle));
                        PdfPCell conformityLevelCell = new PdfPCell(
                            new Paragraph("Level of Non-conformity :", subLabelFontStyle));
                        PdfPCell conformityLevelValueCell = new PdfPCell(
                            new Paragraph(conformityLevelMapObj.get(projectRuleObj.getReviewerConformityLevel()),
                                textContentFontStyle));

                        projectDetailTbl.addCell(removeCellBorder(conformityLevelSerailNumCell));
                        projectDetailTbl.addCell(removeCellBorder(conformityLevelCell));
                        projectDetailTbl.addCell(addColonToLabel(subLabelFontStyle));
                        projectDetailTbl.addCell(removeCellBorder(conformityLevelValueCell));

                        nonConformityRuleCountMap.put(conformityLevelMapObj.get(projectRuleObj.getReviewerConformityLevel()),
                            nonConformityRuleCountMap.get(
                                conformityLevelMapObj.get(projectRuleObj.getReviewerConformityLevel())) + 1);
                    }

                    //Attachments
                    List<String> reviewerImageList = imageUpload.getReviewerImageList(projectRuleObj.getEmployeeCommentId());
                    if (reviewerImageList != null && reviewerImageList.size() > 0) {
                        dynamicTitleVersion = nextDynamicVersionNum(dynamicTitleVersion);
                        PdfPCell ruleAttachmentsSerialNumCell = new PdfPCell(
                            new Paragraph(dynamicTitleVersion, subLabelFontStyle));
                        PdfPCell ruleAttachmentsCell = new PdfPCell(new Paragraph("Attachment(s)", subLabelFontStyle));
                        //ruleAttachmentsCell.setColspan(2);
                        projectDetailTbl.addCell(removeCellBorder(ruleAttachmentsSerialNumCell));
                        projectDetailTbl.addCell(removeCellBorder(ruleAttachmentsCell));
                        projectDetailTbl.addCell(addColonToLabel(subLabelFontStyle));
                        projectDetailTbl.addCell(removeCellBorder(new PdfPCell(new Paragraph(""))));
                    }
                    int imgCnt = 0;
                    for (String imageURL : reviewerImageList) {
                        if (imgCnt < 2) {
						/*PdfPCell imageRowDummyCell = new PdfPCell(new Paragraph(""));
						imageRowDummyCell.setColspan(2);
						projectDetailTbl.addCell(removeCellBorder(imageRowDummyCell));*/
                            PdfPCell imageCell = new PdfPCell();
                            Image reviewerAttachImage = Image.getInstance(imageURL);
                            //reviewerAttachImage.setAbsolutePosition(0, 0);
                            //reviewerAttachImage.setAbsolutePosition(480, 810);
                            reviewerAttachImage.scaleToFit(500f, 250f);
                            imageCell.addElement(reviewerAttachImage);
                            imageCell.setColspan(4);
                            imageCell.setPaddingTop(15f);
                            if (imgCnt != 0) {
                                imageCell.setPaddingBottom(20f);
                            }
                            projectDetailTbl.addCell(removeCellBorder(imageCell));
                            imgCnt++;
                        }
                    }

                    if (projectRuleObj.getReviewerRemark() != null && !projectRuleObj.getReviewerRemark().isEmpty()) {
                        dynamicTitleVersion = nextDynamicVersionNum(dynamicTitleVersion);
                        //Reviewer Comment :
                        PdfPCell reviewerRemarkSerailNumCell = new PdfPCell(
                            new Paragraph(dynamicTitleVersion, subLabelFontStyle));
                        PdfPCell reviewerRemarkLabelCell = new PdfPCell(new Paragraph("Remark ", subLabelFontStyle));
                        PdfPCell reviewerRemarkValueCell = createJustifyParagraph(projectRuleObj.getReviewerRemark(),
                            textContentFontStyle);

                        projectDetailTbl.addCell(removeCellBorder(reviewerRemarkSerailNumCell));
                        projectDetailTbl.addCell(removeCellBorder(reviewerRemarkLabelCell));
                        projectDetailTbl.addCell(addColonToLabel(subLabelFontStyle));
                        projectDetailTbl.addCell(removeCellBorder(reviewerRemarkValueCell));
                    }

                    if (projectRuleObj.getLeadReviewerRemark() != null && !projectRuleObj.getLeadReviewerRemark()
                        .isEmpty()) {
                        //******Lead-Reviewer Feedback Comments*********
                        PdfPCell leadReviewerFeedbackCell = new PdfPCell(
                            new Paragraph("Lead-Reviewer Feedback", subTitleFontStyle));
                        leadReviewerFeedbackCell.setColspan(4);
                        leadReviewerFeedbackCell.setPaddingTop(13f);
                        leadReviewerFeedbackCell.setPaddingBottom(8f);

                        dynamicTitleVersion = nextDynamicVersionNum(dynamicTitleVersion);
                        //Reviewer Comment :
                        PdfPCell leadReviewerRemarkSerailNumCell = new PdfPCell(
                            new Paragraph(dynamicTitleVersion, subLabelFontStyle));
                        PdfPCell leadReviewerRemarkLabelCell = new PdfPCell(new Paragraph("Remark ", subLabelFontStyle));
                        PdfPCell leadRevieweRemarkValueCell = createJustifyParagraph(projectRuleObj.getLeadReviewerRemark(),
                            textContentFontStyle);

                        projectDetailTbl.addCell(removeCellBorder(leadReviewerFeedbackCell));

                        projectDetailTbl.addCell(removeCellBorder(leadReviewerRemarkSerailNumCell));
                        projectDetailTbl.addCell(removeCellBorder(leadReviewerRemarkLabelCell));
                        projectDetailTbl.addCell(addColonToLabel(subLabelFontStyle));
                        projectDetailTbl.addCell(removeCellBorder(leadRevieweRemarkValueCell));
                    }

                    document.add(projectDetailTbl);
                }

                // Rule Conformity Detail
                PdfPCell conformityTblSerialNumCell = new PdfPCell(
                    new Paragraph(String.valueOf(conformityTblSlNum), textContentFontStyle));
                PdfPCell conformityTblProvisionCell = new PdfPCell(
                    new Paragraph(projectRuleObj.getRuleSubclauseNum().trim() + " of " + projectRuleObj.getBookName().trim(),
                        textContentFontStyle));
                PdfPCell conformityTblStatusCell = null;
                if (projectRuleObj.getReviewerRemark().equalsIgnoreCase("na")) {
                    conformityTblStatusCell = new PdfPCell(new Paragraph("NA", textContentFontStyle));
                } else if (projectRuleObj.getReviewerConformityStatus() == 0) {
                    conformityTblStatusCell = new PdfPCell(
                        new Paragraph("NC - " + conformityLevelMapObj.get(projectRuleObj.getReviewerConformityLevel()),
                            textContentFontStyle));
                } else {
                    conformityTblStatusCell = new PdfPCell(new Paragraph("Conformity", textContentFontStyle));
                }

                ruleConformityDetailTbl.addCell(conformityTblSerialNumCell);
                ruleConformityDetailTbl.addCell(conformityTblProvisionCell);
                ruleConformityDetailTbl.addCell(conformityTblStatusCell);
            }
        }

        document.newPage();
        document.add(ruleConformityDetailTbl);


    }


    private static String nextDynamicVersionNum(String currentVersionNum) {
        String nextVersionNum = "";

        switch (currentVersionNum) {
            case "12.":
                nextVersionNum = "13.";
                break;
            case "13.":
                nextVersionNum = "14.";
                break;
            case "14.":
                nextVersionNum = "15.";
                break;
            case "15.":
                nextVersionNum = "16.";
                break;
            case "16.":
                nextVersionNum = "17.";
                break;
            case "17.":
                nextVersionNum = "18.";
                break;
        }

        return nextVersionNum;
    }


    private static PdfPCell removeCellBorder(PdfPCell pCell) {
        pCell.setBorderWidth(0f);
        return pCell;
    }

    private static PdfPCell addColonToLabel(Font subLabelFontStyle) {
        PdfPCell colonSymbolCell = new PdfPCell(new Paragraph(":", subLabelFontStyle));
        colonSymbolCell.setBorderWidth(0f);
        colonSymbolCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        return colonSymbolCell;
    }

    private static void addEmptyLine(Document document, int number) throws DocumentException {
        Paragraph emptyPagraph = new Paragraph();
        for (int i = 0; i < number; i++) {
            emptyPagraph.add(new Paragraph(" "));
        }
        document.add(emptyPagraph);
    }

    public static String formateDateObj(Date originalDateObj) {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String targetObject = formatter.format(originalDateObj);
        return targetObject;
    }


    public static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }


    public static void writeChartToPDF(JFreeChart chart, int width, int height, PdfWriter writer, boolean firstGraphStatus) {
        try {

            PdfContentByte contentByte = writer.getDirectContent();
            PdfTemplate template = contentByte.createTemplate(width, height);
            Graphics2D graphics2d = template.createGraphics(width, height, new DefaultFontMapper());
            Rectangle2D rectangle2d = new Rectangle2D.Double(0, 0, width, height);

            chart.draw(graphics2d, rectangle2d);

            graphics2d.dispose();
            if (firstGraphStatus) {
                contentByte.addTemplate(template, 75, 470);
            } else {
                contentByte.addTemplate(template, 75, 115);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static JFreeChart generatePieChart(String graphTitle, Map<String, Integer> targetGraphData) {
        DefaultPieDataset dataSet = new DefaultPieDataset();
        for (String mapKey : targetGraphData.keySet()) {
            dataSet.setValue(mapKey + " (" + targetGraphData.get(mapKey) + ")", targetGraphData.get(mapKey));
        }

        JFreeChart chart = ChartFactory.createPieChart(graphTitle, dataSet, true, true, false);
        chart.getPlot().setBackgroundPaint(Color.WHITE);

        LegendTitle legend = chart.getLegend();
        PiePlot pObj = ((PiePlot) chart.getPlot());
        pObj.setOutlineVisible(false);
        pObj.setLabelGenerator(null);
        //pObj.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}: {1} ({2})", NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance()));
        pObj.setLabelGenerator(new StandardPieSectionLabelGenerator("{2}({1})", NumberFormat.getNumberInstance(),
            NumberFormat.getPercentInstance()));
        pObj.setSimpleLabels(true);
		
		/*pObj.setShadowYOffset(0);
		pObj.setShadowXOffset(0);
		pObj.setLabelOutlinePaint(null);
		pObj.setLabelBackgroundPaint(null);*/

        pObj.setLabelBackgroundPaint(null);
        pObj.setLabelOutlinePaint(null);
        pObj.setLabelShadowPaint(null);
        pObj.setShadowXOffset(0.0D);
        pObj.setShadowYOffset(0.0D);
        legend.setFrame(BlockBorder.NONE);

        pObj.setSectionPaint(0, new Color(253, 103, 128));
        pObj.setSectionPaint(1, new Color(51, 231, 170));
        pObj.setSectionPaint(2, new Color(254, 189, 69));
        pObj.setSectionPaint(3, new Color(49, 163, 251));
		
		
		/*chart.setBorderVisible(false);
	    chart.clearSubtitles();*/
        //chart.getPlot().setShadowYOffset(0);
        //chart.getPlot().setBorderVisible(false);
        //chart.getPlot().setBackgroundPaint(Color.white);
        //JFreeChart chart = ChartFactory.createPieChart3D(graphTitle, dataSet, false, false, false);
        return chart;
    }

    public static void addPieChartDefaultDataSetValue() {
        conformityRuleCountMap.put("Conformity", 0);
        conformityRuleCountMap.put("Non-Conformity", 0);

        nonConformityRuleCountMap.put("Critical", 0);
        nonConformityRuleCountMap.put("Serious", 0);
        nonConformityRuleCountMap.put("Major", 0);
        nonConformityRuleCountMap.put("Minor", 0);
    }

    public static String formatRuleDescriptionText(String ruleDescr) {
        if (ruleDescr != null && !ruleDescr.trim().isEmpty()) {
            return ruleDescr.replaceAll("<br>", "\n").replaceAll("<br/>", "\n").replaceAll("<br />", "\n");
        } else {
            return ruleDescr;
        }
    }


    public static PdfPCell createJustifyParagraph(String paragText, Font textContentFontStyle) {
		/*Paragraph justParagObj = new Paragraph(paragText, textContentFontStyle);
		justParagObj.setAlignment(Element.ALIGN_JUSTIFIED);*/
        Phrase justParagObj = new Phrase(paragText, textContentFontStyle);
        PdfPCell targetCell = new PdfPCell(justParagObj);
        targetCell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
        return targetCell;
    }
}
