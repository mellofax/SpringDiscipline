package com.example.springbooks.model;

import com.example.springbooks.forms.DisciplineForm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Discipline {
    private String name;
    private String teacher;
    private String hours;

    public boolean equal(DisciplineForm o){
        if(this.name.equals(o.getName()) || this.teacher.equals(o.getTeacher()) || this.hours.equals(o.getHours()))
            return true;
        else return false;
    }
}
