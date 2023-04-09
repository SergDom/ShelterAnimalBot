package com.javadreamteam.shelteranimalbot.model.photo;

import com.javadreamteam.shelteranimalbot.model.Report;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "report_photo")
public class ReportPhoto extends Photo {
    @ManyToOne
    @JoinColumn(name = "report_id")
    private Report report;

    public ReportPhoto() {
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }
}
