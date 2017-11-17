package com.example.evans.data;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import org.joda.time.LocalDateTime;


/**
 * Goal class holds the information relevant to a users goal
 * Created by brooke on 10/30/17.
 */

public class Goal implements Comparable {
    private String _title;
    private String _description;
    private LocalDateTime _dueDate;
    private LocalDateTime _startDate;
    private Boolean _isRepeat;
    private TimePeriod _repeatCycle;

    public Goal() {
        _title = "";
        _description = "";
        _dueDate = LocalDateTime.now();
        _startDate = LocalDateTime.now();
        _repeatCycle = null;
    }

    public Goal(String title, String description, LocalDateTime dueDate,
         LocalDateTime startDate, TimePeriod repeatCycle) {
        this._title = title;
        this._description = description;
        this._dueDate = dueDate;
        this._startDate = startDate;
        this._repeatCycle = repeatCycle;

        if (_repeatCycle == null) {
            _isRepeat = false;
        } else _isRepeat = true;
    }

    public Goal(String title, String description, LocalDateTime dueDate,
                LocalDateTime startDate) {
        this._title = title;
        this._description = description;
        this._dueDate = dueDate;
        this._startDate = startDate;

    }

    /**
     * getter functions for each of the member variables
     */
    public String getTitle() { return _title; }
    public String getDescription() { return _description; }
    public LocalDateTime getDueDate() { return _dueDate; }
    public LocalDateTime getStartDate() { return _startDate; }
    public Boolean getIsRepeat() { return _isRepeat; }
    public TimePeriod getRepeatCycle() { return _repeatCycle; }

    /**
     * setter functions for each of the member functions
     */
    public void setTitle(String title) { this._title = title; }
    public void setDescription(String description) { this._description = description; }
    public void setDueDate(LocalDateTime dueDate) { this._dueDate = dueDate; }
    public void setStartDate(LocalDateTime startDate) { this._startDate = startDate; }

    /**
     * sets the repeat cycle to the passed value and sets _isRepeat based on that
     * @param repeatCycle: TimePeriod that is how often the goal repeats
     */
    public void setRepeatCycle(TimePeriod repeatCycle) {
        if (_repeatCycle == null) {
            _isRepeat = false;
        } else _isRepeat = true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
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


