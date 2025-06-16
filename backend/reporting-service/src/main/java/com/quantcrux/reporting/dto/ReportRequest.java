package com.quantcrux.reporting.dto;

public class ReportRequest {
    private String reportType;
    private String startDate;
    private String endDate;
    private String format;
    
    // Constructors
    public ReportRequest() {}
    
    // Getters and Setters
    public String getReportType() { return reportType; }
    public void setReportType(String reportType) { this.reportType = reportType; }
    
    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }
    
    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }
    
    public String getFormat() { return format; }
    public void setFormat(String format) { this.format = format; }
}