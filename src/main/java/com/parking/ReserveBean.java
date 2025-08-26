package com.parking;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@ManagedBean(name = "reserveBean")
@ViewScoped
public class ReserveBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private String space = "1A";
    private String staffId;
    private String staffName;

    private String fromDate;   // yyyy-MM-dd
    private String fromTime;   // HH:mm
    private String toDate;     // yyyy-MM-dd
    private String toTime;     // HH:mm

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("HH:mm");

    public String updateReservation() {
        FacesContext ctx = FacesContext.getCurrentInstance();

        if (isBlank(space) || isBlank(staffId) || isBlank(staffName) ||
            isBlank(fromDate) || isBlank(fromTime) || isBlank(toDate) || isBlank(toTime)) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "All fields are required.", null));
            return null; // stay on page
        }

        LocalDateTime start, end;
        try {
            start = LocalDateTime.of(LocalDate.parse(fromDate, DATE_FMT), LocalTime.parse(fromTime, TIME_FMT));
            end   = LocalDateTime.of(LocalDate.parse(toDate,   DATE_FMT), LocalTime.parse(toTime,   TIME_FMT));
        } catch (DateTimeParseException e) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Invalid date/time.", "Please use the pickers (yyyy-MM-dd, HH:mm)."));
            return null;
        }

        if (!end.isAfter(start)) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "End must be after start.", null));
            return null;
        }

        // TODO: persist/update reservation via your service/DAO here

        // Success banner (shows after the full page render)
        ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Reservation updated Succesfully",
                String.format("Space %s for %s (%s) from %s to %s",
                        space, staffName, staffId, start, end)));

        return null; // return null = stay on the same view (no redirect)
    }

    private boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }

    // getters/setters
    public String getSpace() { return space; }
    public void setSpace(String space) { this.space = space; }
    public String getStaffId() { return staffId; }
    public void setStaffId(String staffId) { this.staffId = staffId; }
    public String getStaffName() { return staffName; }
    public void setStaffName(String staffName) { this.staffName = staffName; }
    public String getFromDate() { return fromDate; }
    public void setFromDate(String fromDate) { this.fromDate = fromDate; }
    public String getFromTime() { return fromTime; }
    public void setFromTime(String fromTime) { this.fromTime = fromTime; }
    public String getToDate() { return toDate; }
    public void setToDate(String toDate) { this.toDate = toDate; }
    public String getToTime() { return toTime; }
    public void setToTime(String toTime) { this.toTime = toTime; }
}
