package com.test.model;

import org.apache.solr.client.solrj.beans.Field;

import java.util.Date;

/**
 * Document within the Solr Index - copy of the schema.xml
 */
public class CourseDocument {

    @Field("id")
    private String id;
    @Field("course_id")
    private int courseId;
    @Field("course_title")
    private String title;
    @Field("course_description")
    private String description;
    @Field("course_studying_technique")
    private String studyingTechnique;

    @Field("appointment_id")
    private int appointmentId;
    @Field("appointment_title")
    private String appointmentTitle;
    @Field("appointment_start")
    private Date start;
    @Field("appointment_end")
    private Date end;
    @Field("appointment_description")
    private String appointmentDescription;

    @Field("score")
    private Float score; //generated field by Solr - via fl parameter

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId)
    {
        this.courseId = courseId;
        this.id = String.format("%d_%d", courseId, appointmentId);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

     public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStudyingTechnique() {
        return studyingTechnique;
    }

    public void setStudyingTechnique(String studyingTechnique) {
        this.studyingTechnique = studyingTechnique;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int id)
    {
        this.appointmentId = id;
        this.id = String.format("%d_%d", courseId, appointmentId);
    }

    public String getAppointmentTitle() {
        return appointmentTitle;
    }

    public void setAppointmentTitle(String appointmentTitle) {
        this.appointmentTitle = appointmentTitle;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getAppointmentDescription() {
        return appointmentDescription;
    }

    public void setAppointmentDescription(String appointmentDescription) {
        this.appointmentDescription = appointmentDescription;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

     @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CourseDocument that = (CourseDocument) o;

        if (appointmentId != that.appointmentId) return false;
        if (courseId != that.courseId) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = courseId;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + appointmentId;
        return result;
    }
}
