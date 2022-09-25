package com.smp.app.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PreviewResponseListTO {

    List<ReviewerPreviewDetailsResponseTO> resultList = new ArrayList<ReviewerPreviewDetailsResponseTO>();
    Map<Integer, List<String>> circularNameMapObject = new HashMap<>();
    List<ManagementPreviewDetailsResponseTO> managementPreviewList = new ArrayList<ManagementPreviewDetailsResponseTO>();
    Map<String, List<ReviewerPreviewDetailsResponseTO>> pdfResultReviewerMapObj = new TreeMap<String,
        List<ReviewerPreviewDetailsResponseTO>>();


    public List<ReviewerPreviewDetailsResponseTO> getResultList() {
        return resultList;
    }

    public void setResultList(List<ReviewerPreviewDetailsResponseTO> resultList) {
        this.resultList = resultList;
    }

    public Map<Integer, List<String>> getCircularNameMapObject() {
        return circularNameMapObject;
    }

    public void setCircularNameMapObject(Map<Integer, List<String>> circularNameMapObject) {
        this.circularNameMapObject = circularNameMapObject;
    }

    public List<ManagementPreviewDetailsResponseTO> getManagementPreviewList() {
        return managementPreviewList;
    }

    public void setManagementPreviewList(List<ManagementPreviewDetailsResponseTO> managementPreviewList) {
        this.managementPreviewList = managementPreviewList;
    }

    public Map<String, List<ReviewerPreviewDetailsResponseTO>> getPdfResultReviewerMapObj() {
        return pdfResultReviewerMapObj;
    }

    public void setPdfResultReviewerMapObj(Map<String, List<ReviewerPreviewDetailsResponseTO>> pdfResultReviewerMapObj) {
        this.pdfResultReviewerMapObj = pdfResultReviewerMapObj;
    }


}
