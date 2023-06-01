package com.ford.cvas.evap.purge.common;


public interface PowerpackDataBin {
    int getCount();

    Integer getId();

    IndependentVariable getIndependentVariable();

    double getMean();

    double getStandardDeviation();

    double getWarningThreshold();

    int getLowerBound();

    int getUpperBound();
}
