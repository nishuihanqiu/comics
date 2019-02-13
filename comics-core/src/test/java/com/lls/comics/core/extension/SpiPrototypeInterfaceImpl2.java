package com.lls.comics.core.extension;

import java.util.concurrent.atomic.AtomicLong;

/************************************
 * SpiPrototypeInterfaceImpl2
 * @author liliangshan
 * @date 2019-02-13
 ************************************/
@SpiMeta(name = "spiPrototypeTest2")
public class SpiPrototypeInterfaceImpl2 implements SpiPrototypeInterface {

  private static AtomicLong counter = new AtomicLong(0);
  private long currentNumber = 0;

  public SpiPrototypeInterfaceImpl2() {
    currentNumber = counter.incrementAndGet();
  }

  @Override
  public long printCurrentNumber() {
    System.out.println("SpiPrototypeInterfaceImpl2" + currentNumber + " print current number.");
    return currentNumber;
  }
}
