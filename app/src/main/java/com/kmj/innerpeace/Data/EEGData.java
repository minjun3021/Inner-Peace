package com.kmj.innerpeace.Data;

import java.util.ArrayList;

public class EEGData {
    public EEGData(ArrayList<Integer> delta, ArrayList<Integer> theta, ArrayList<Integer> lowAlpha, ArrayList<Integer> highAlpha, ArrayList<Integer> lowBeta, ArrayList<Integer> highBeta, ArrayList<Integer> lowGamma, ArrayList<Integer> middleGamma) {
        this.delta = delta;
        this.theta = theta;
        this.lowAlpha = lowAlpha;
        this.highAlpha = highAlpha;
        this.lowBeta = lowBeta;
        this.highBeta = highBeta;
        this.lowGamma = lowGamma;
        this.middleGamma = middleGamma;
    }

    public ArrayList<Integer> delta;
    public ArrayList<Integer> theta;
    public ArrayList<Integer> lowAlpha;
    public ArrayList<Integer> highAlpha;
    public ArrayList<Integer> lowBeta;
    public ArrayList<Integer> highBeta;
    public ArrayList<Integer> lowGamma;
    public ArrayList<Integer> middleGamma;

}
