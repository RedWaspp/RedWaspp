package com.ford.cvas.evap.purge.common;

public class DefaultPowerpackDataBin  implements PowerpackDataBin{
  @Override
  public int getCount() {
    return 0;
  }

  @Override
  public Integer getId() {
    return null;
  }

  @Override
  public IndependentVariable getIndependentVariable() {
    return null;
  }

  @Override
  public double getMean() {
    return 0;
  }

  @Override
  public double getStandardDeviation() {
    return 0;
  }

  @Override
  public double getWarningThreshold() {
    return 0;
  }

  @Override
  public int getLowerBound() {
    return 0;
  }

  @Override
  public int getUpperBound() {
    return 0;
  }
}
