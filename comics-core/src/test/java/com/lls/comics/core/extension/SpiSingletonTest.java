package com.lls.comics.core.extension;

import java.util.concurrent.atomic.AtomicLong;

/************************************
 * SpiSingletonTest
 * @author liliangshan
 * @date 2019-02-13
 ************************************/
@SpiMeta(name = "spiSingletonTest")
public class SpiSingletonTest implements SpiSingletonInterface {

  private static AtomicLong counter = new AtomicLong(0);
  private long idx = 0;

  public SpiSingletonTest() {
    idx = counter.incrementAndGet();
  }

  @Override
  public long printCurrentNumber() {
    return idx;
  }

}
