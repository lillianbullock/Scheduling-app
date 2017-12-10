package com.example.evans.data;


import android.support.annotation.NonNull;


import com.google.firebase.database.Exclude;

import org.joda.time.LocalDate;


/**
 * Goal class holds the information relevant to a users goal
 * Created by brooke on 10/30/17.
 */

public class Goal implements Comparable {
    private String _id;
    private String _title;
    private String _description;
    private LocalDate _dueDate;
    private LocalDate _startDate;
    private Boolean _repeat;
    private TimePeriod _repeatCycle;
    private Boolean _done;

    public Goal() {
        _id = "";
        _title = "";
        _description = "";
        _dueDate = LocalDate.now();
        _startDate = LocalDate.now();
        setRepeatCycle(null);
        _done = false;
    }

    public Goal(String title, String description, LocalDate dueDate,
         LocalDate startDate, TimePeriod repeatCycle) {
        this._id = "";
        this._title = title;
        this._description = description;
        this._dueDate = dueDate;
        this._startDate = startDate;
        this._done = false;
        setRepeatCycle(repeatCycle);
    }

    public Goal(String title, String description, LocalDate dueDate,
                LocalDate startDate) {
        this._id = "";
        this._title = title;
        this._description = description;
        this._dueDate = dueDate;
        this._startDate = startDate;
        this._done = false;
        _repeatCycle = null;
        _repeat = false;
    }


    @Override
    public boolean equals(Object obj) {

        if (obj == this){
            return true;
        }

        if (obj == null || !(obj instanceof Goal)){
            return false;
        }

        // typecast obj to Goal
        Goal goal = (Goal) obj;

        return this._id.equals(goal._id);
    }

    @Override
    public int hashCode() {
        return _id.hashCode();
    }


    public String getId() { return _id; }
    public void setId(String id) { _id = id;}

    public String getTitle() { return _title; }
    public void setTitle(String title) { this._title = title; }

    public String getDescription() { return _description; }
    public void setDescription(String description) { this._description = description; }

    @Exclude public LocalDate getStartDateObject() { return _startDate;}
    @Exclude public  void setStartDateObject(LocalDate startDate) {_startDate = startDate;}
    public String getStartDate() { return _startDate.toString(); }
    public void setStartDate(String startDateString) { this._startDate = LocalDate.parse(startDateString); }

    @Exclude public LocalDate getDueDateObject() { return _dueDate; }
    @Exclude public  void setDueDateObject(LocalDate dueDate) {_dueDate = dueDate;}
    public String getDueDate() { return _dueDate.toString(); }
    public void setDueDate(String dueDateString) { this._dueDate = LocalDate.parse(dueDateString); }

    public Boolean isRepeat() { return _repeat; }
    public TimePeriod getRepeatCycle() { return _repeatCycle; }
    /**
     * sets the repeat cycle to the passed value and sets _isRepeat based on that
     * @param repeatCycle: TimePeriod that is how often the goal repeats
     */
    public void setRepeatCycle(TimePeriod repeatCycle) {
        _repeatCycle = repeatCycle;
        if (_repeatCycle == null) {
            _repeat = false;
        } else _repeat = true;
    }

    public Boolean isDone() { return _done; }
    public void setDone(Boolean done) {this._done = done; }

    /**
     * Tests to see if it is past the due date of the goal
     * @return whether the due date has passed
     */
    public Boolean isPastDue() {
        LocalDate currentDay = LocalDate.now();
        return _dueDate.isBefore(currentDay);
    }

    @Override
    @Exclude
    public int compareTo(@NonNull Object o) {
        Goal goal1 = (Goal) o;
        if (this._dueDate.isAfter(goal1._dueDate))
            return 1;

        if (this._dueDate.isBefore(goal1._dueDate))
            return -1;

        return 0;
    }
}


