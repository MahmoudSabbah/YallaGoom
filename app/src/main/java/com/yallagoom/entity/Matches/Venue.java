package com.yallagoom.entity.Matches;

/**
 * Created by Mahmoud Sabbah on 3/12/2018.
 */

public class Venue {
    private int venue_id;
    private String venue_name;
    private String venue_capacity;
    private String venue_city_name;
    private String venue_country_name;
    private String venue_map_coordinates;
    private String venue_country_code;

    public int getVenue_id() {
        return venue_id;
    }

    public void setVenue_id(int venue_id) {
        this.venue_id = venue_id;
    }

    public String getVenue_name() {
        return venue_name;
    }

    public void setVenue_name(String venue_name) {
        this.venue_name = venue_name;
    }

    public String getVenue_capacity() {
        return venue_capacity;
    }

    public void setVenue_capacity(String venue_capacity) {
        this.venue_capacity = venue_capacity;
    }

    public String getVenue_city_name() {
        return venue_city_name;
    }

    public void setVenue_city_name(String venue_city_name) {
        this.venue_city_name = venue_city_name;
    }

    public String getVenue_country_name() {
        return venue_country_name;
    }

    public void setVenue_country_name(String venue_country_name) {
        this.venue_country_name = venue_country_name;
    }

    public String getVenue_map_coordinates() {
        return venue_map_coordinates;
    }

    public void setVenue_map_coordinates(String venue_map_coordinates) {
        this.venue_map_coordinates = venue_map_coordinates;
    }

    public String getVenue_country_code() {
        return venue_country_code;
    }

    public void setVenue_country_code(String venue_country_code) {
        this.venue_country_code = venue_country_code;
    }
}
