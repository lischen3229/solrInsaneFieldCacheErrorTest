package com.test.helper;

import com.test.model.CourseDocument;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Helper Class to easily create course documents.
 */
public class MockCourseDocument {

    CourseDocument document = new CourseDocument();

    public MockCourseDocument(int id, int appId) {
        document.setId(Integer.toString(id));
        document.setCourseId(id);
        document.setAppointmentId(appId);
    }

    public MockCourseDocument title(String title) {
        document.setTitle(title);
        return this;
    }

    public MockCourseDocument desc(String description) {
        document.setDescription(description);
        return this;
    }

    public MockCourseDocument appTitle(String appTitle) {
        document.setAppointmentTitle(appTitle);
        return this;
    }

    public MockCourseDocument studyTech(String studyTech) {
        document.setStudyingTechnique(studyTech);
        return this;
    }

    /**
     * Required Date format: dd.MM.
     */
    public MockCourseDocument start(String startDateDayAndMonth) {
        document.setStart(getDate(startDateDayAndMonth));
        return this;
    }

    public CourseDocument build() {
        return document;
    }

    private Date getDate(String val) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.");
        try {
            Calendar c = new GregorianCalendar();
            int year = c.get(Calendar.YEAR);

            Date d = formatter.parse(val);
            c.setTime(d);
            c.set(Calendar.YEAR, year + 1); //make sure the date is in the future
            return c.getTime();
        } catch (ParseException ignored) {
        }
        return null;
    }
}
