package com.huewaco.cskh.objects;

import java.util.ArrayList;
import java.util.HashMap;

public class Img2TextObj {
    private String imagepath;
    private String actual_number;
    private int frame_count;
    private String frame_data;
    private ArrayList<ArrayList<String>> frame_shape = new ArrayList<>();//
    private String imagename;
    private int number_count;
    private HashMap<String,ArrayList<String>> number_shapes= new HashMap<>();//
    private String predicted_number;
    private String read_number;
    private String score;

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public String getActual_number() {
        return actual_number;
    }

    public void setActual_number(String actual_number) {
        this.actual_number = actual_number;
    }

    public int getFrame_count() {
        return frame_count;
    }

    public void setFrame_count(int frame_count) {
        this.frame_count = frame_count;
    }

    public String getFrame_data() {
        return frame_data;
    }

    public void setFrame_data(String frame_data) {
        this.frame_data = frame_data;
    }

    public ArrayList<ArrayList<String>> getFrame_shape() {
        return frame_shape;
    }

    public void setFrame_shape(ArrayList<ArrayList<String>> frame_shape) {
        this.frame_shape = frame_shape;
    }

    public String getImagename() {
        return imagename;
    }

    public void setImagename(String imagename) {
        this.imagename = imagename;
    }

    public int getNumber_count() {
        return number_count;
    }

    public void setNumber_count(int number_count) {
        this.number_count = number_count;
    }

    public HashMap<String, ArrayList<String>> getNumber_shapes() {
        return number_shapes;
    }

    public void setNumber_shapes(HashMap<String, ArrayList<String>> number_shapes) {
        this.number_shapes = number_shapes;
    }

    public String getPredicted_number() {
        return predicted_number;
    }

    public void setPredicted_number(String predicted_number) {
        this.predicted_number = predicted_number;
    }

    public String getRead_number() {
        return read_number;
    }

    public void setRead_number(String read_number) {
        this.read_number = read_number;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Img2TextObj{" +
                "actual_number='" + actual_number + '\'' +
                ", frame_count=" + frame_count +
                ", frame_data='" + frame_data + '\'' +
//                ", frame_shape=" + frame_shape +
                ", imagename='" + imagename + '\'' +
                ", number_count=" + number_count +
//                ", number_shapes=" + number_shapes +
                ", predicted_number='" + predicted_number + '\'' +
                ", read_number='" + read_number + '\'' +
                ", score='" + score + '\'' +
                '}';
    }
}
