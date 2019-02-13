package com.lls.comics.core.extension;

/************************************
 * SpiSingletonInterface
 * @author liliangshan
 * @date 2019-02-13
 ************************************/
@Spi(scope = Scope.SINGLETON)
public interface SpiSingletonInterface {

  long printCurrentNumber();

}
