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
    private Boolean _isRepeat;
    private Boolean _done;
    private TimePeriod _repeatCycle;

    public Goal() {
        _title = "";
        _description = "";
        _dueDate = LocalDate.now();
        _startDate = LocalDate.now();
        _repeatCycle = null;
    }

    public Goal(String title, String description, LocalDate dueDate,
         LocalDate startDate, TimePeriod repeatCycle) {
        this._title = title;
        this._description = description;
        this._dueDate = dueDate;
        this._startDate = startDate;
        this._repeatCycle = repeatCycle;

        if (_repeatCycle == null) {
            _isRepeat = false;
        } else _isRepeat = true;
    }

    public Goal(String title, String description, LocalDate dueDate,
                LocalDate startDate) {
        this._title = title;
        this._description = description;
        this._dueDate = dueDate;
        this._startDate = startDate;

    }

    /**
     * getter functions for each of the member variables
     */
    public String getId() { return _id; }
    public String getTitle() { return _title; }
    public String getDescription() { return _description; }


    public String getDueDate() { return _dueDate.toString(); }
    public String getStartDate() { return _startDate.toString(); }

    @Exclude
    public LocalDate getLocalDateStartDate() { return _startDate;}

    @Exclude
    public LocalDate getLocalDateDueDate() { return _dueDate; }

    public Boolean getIsRepeat() { return _isRepeat; }
    public TimePeriod getRepeatCycle() { return _repeatCycle; }
    public Boolean isDone() { return _done; }



    public Boolean isPastPastDue() {
        LocalDate currentDay = LocalDate.now();
        return _dueDate.isAfter(currentDay);
    }


    /**
     * setter functions for each of the member functions
     */
    public void setId(String id) { _id = id;}
    public void setTitle(String title) { this._title = title; }
    public void setDescription(String description) { this._description = description; }

    public void setDueDate(String dueDateString) { this._dueDate = LocalDate.parse(dueDateString); }
    public void setStartDate(String startDateString) { this._startDate = LocalDate.parse(startDateString); }

    @Exclude
    public  void setStartDateLocalDate(LocalDate startDate) {_startDate = startDate;}

    @Exclude
    public  void setDueDateLocalDate(LocalDate dueDate) {_dueDate = dueDate;}


    public void setDone(Boolean done) {this._done = done; }

    /**
     * sets the repeat cycle to the passed value and sets _isRepeat based on that
     * @param repeatCycle: TimePeriod that is how often the goal repeats
     */
    public void setRepeatCycle(TimePeriod repeatCycle) {
        if (_repeatCycle == null) {
            _isRepeat = false;
        } else _isRepeat = true;
    }

    @Override
    @Exclude
    public int compareTo(@NonNull Object o) {
        Goal goal1 = (Goal) o;
        if(this._dueDate.isAfter(goal1._dueDate))
            return 1;
        else if(this._dueDate.isBefore(goal1._dueDate))
            return -1;
        else
            return 0;
    }
}


