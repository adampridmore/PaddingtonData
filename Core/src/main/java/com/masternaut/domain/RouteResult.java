package com.masternaut.domain;

import com.masternaut.CustomerIdentifiable;

import java.util.Date;

public class RouteResult extends CustomerIdentifiable{
    private String assetId;
    private String address;
    private Point point;
    private Date eventDateTime;

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public Date getEventDateTime() {
        return eventDateTime;
    }

    public void setEventDateTime(Date eventDateTime) {
        this.eventDateTime = eventDateTime;
    }
}
