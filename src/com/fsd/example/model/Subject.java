package com.fsd.example.model;

import java.io.Serializable;
import java.util.Set;

public class Subject implements  Comparable, Serializable {

    private static final long serialVersionUID =1l;

    private Long subjectId;
    private String subtitle;
    private int durationInHours;
    private Set<Book> references;

    public Subject(long subjectId, String subtitle, int durationInHours, Set<Book> references) {
        this.subjectId = subjectId;
        this.subtitle = subtitle;
        this.durationInHours = durationInHours;
        this.references = references;
    }

    public long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(long subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public int getDurationInHours() {
        return durationInHours;
    }

    public void setDurationInHours(int durationInHours) {
        this.durationInHours = durationInHours;
    }

    public Set<Book> getReferences() {
        return references;
    }

    public void setReferences(Set<Book> references) {
        this.references = references;
    }

    @Override
    public int compareTo(Object o) {
        return this.subjectId.compareTo(((Subject)o).getSubjectId());
    }
}
