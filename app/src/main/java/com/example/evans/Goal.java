package com.example.evans;

import java.time.LocalDateTime;

/**
 * Goal class holds the information relavant to a users goal
 * Created by brooke on 10/30/17.
 */

public class Goal {
    private String _title;
    private String _description;
    private LocalDateTime _dueDate;
    private LocalDateTime _startDate;
    private Boolean _isRepeat;
    private TimePeriod _repeatCycle;

    Goal() {
        _title = "";
        _description = "";
        _dueDate = null;
        _startDate = null;
        _isRepeat = false;
        _repeatCycle = null;
    }

    /**
     * getter functions for each of the member variables
     */
    public String getTitle() {
        return _title;
    }
    public String getDescription() {
        return _description;
    }
    public LocalDateTime getDueDate() {
        return _dueDate;
    }
    public LocalDateTime getStartDate() {
        return _startDate;
    }
    public Boolean getIsRepeat() {
        return _isRepeat;
    }
    public TimePeriod getRepeatCycle() {
        return _repeatCycle;
    }

    /**
     * setter functions for each of the member functions
     */
    public void setTitle(String title) {
        this._title = title;
    }
    public void setDescription(String description) {
        this._description = description;
    }
    public void setDueDate(LocalDateTime dueDate) {
        this._dueDate = dueDate;
    }
    public void setStartDate(LocalDateTime startDate) {
        this._startDate = startDate;
    }
    public void setIsRepeat(Boolean isRepeat) {
        this._isRepeat = isRepeat;
    }
    public void setRepeatCycle(TimePeriod repeatCycle) {
        this._repeatCycle = repeatCycle;
        _isRepeat = true;
    }



}

