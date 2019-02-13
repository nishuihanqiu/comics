package com.lls.comics.core.extension;

import java.util.concurrent.atomic.AtomicLong;

/************************************
 * SpiPrototypeInterfaceImpl
 * @author liliangshan
 * @date 2019-02-13
 ************************************/
@SpiMeta(name = "spiPrototypeTest")
public class SpiPrototypeInterfaceImpl implements SpiPrototypeInterface {

  private long number = 0;
  private static AtomicLong count = new AtomicLong(0);

  public SpiPrototypeInterfaceImpl() {
    number = count.incrementAndGet();
  }

  @Override
  public long printCurrentNumber() {
    return number;
  }


}
