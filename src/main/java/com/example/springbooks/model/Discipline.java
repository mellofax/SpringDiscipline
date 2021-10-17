package com.example.springbooks.model;

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
}
