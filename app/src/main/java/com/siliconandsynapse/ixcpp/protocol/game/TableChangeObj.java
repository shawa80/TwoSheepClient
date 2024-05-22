package com.siliconandsynapse.ixcpp.protocol.game;


import java.util.List;

public class TableChangeObj {

    private List<TableChangeObjSeat> seats;

    public TableChangeObj(List<TableChangeObjSeat> seats) {
        this.seats = seats;
    }

    public List<TableChangeObjSeat> getSeats() {
        return seats;
    }

}
