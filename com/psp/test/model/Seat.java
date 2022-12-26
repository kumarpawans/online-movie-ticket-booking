package com.psp.test.model;

import lombok.Data;

@Data
public class Seat {
    private String theaterId;
    private int row;
    private int column;
    private boolean reserved;

    // constructors, getters, and setters
}
