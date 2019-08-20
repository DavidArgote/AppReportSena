package com.davidargote.appreportsena.model;

public class Report {

    private int id;
    private String titleReport;
    private byte[] imageReport;
    private String urlAudio;
    private double latReport;
    private double longReport;


    public Report() {
    }

    public Report(int id, String titleReport, byte[] imageReport, String urlAudio, double latReport, double longReport) {
        this.id = id;
        this.titleReport = titleReport;
        this.imageReport = imageReport;
        this.urlAudio = urlAudio;
        this.latReport = latReport;
        this.longReport = longReport;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitleReport() {
        return titleReport;
    }

    public void setTitleReport(String titleReport) {
        this.titleReport = titleReport;
    }

    public byte[] getImageReport() {
        return imageReport;
    }

    public void setImageReport(byte[] imageReport) {
        this.imageReport = imageReport;
    }

    public String getUrlAudio() {
        return urlAudio;
    }

    public void setUrlAudio(String urlAudio) {
        this.urlAudio = urlAudio;
    }

    public double getLatReport() {
        return latReport;
    }

    public void setLatReport(double latReport) {
        this.latReport = latReport;
    }

    public double getLongReport() {
        return longReport;
    }

    public void setLongReport(double longReport) {
        this.longReport = longReport;
    }
}
