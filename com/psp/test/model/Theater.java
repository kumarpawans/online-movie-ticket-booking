package com.psp.test.model;

import lombok.Data;

@Data
public class Theater {
    private String id;
    private String name;
    private String seat;
    private String location;
    private int numRows;
    private int numColumns;
    public Theater() {

    }
    public Theater(String name, String location, String seat, int numRows, int numColumns) {
    }


}
