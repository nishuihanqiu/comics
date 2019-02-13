package com.lls.comics.core.extension;

/************************************
 * SpiPrototypeInterface
 * @author liliangshan
 * @date 2019-02-13
 ************************************/
@Spi(scope = Scope.PROTOTYPE)
public interface SpiPrototypeInterface {

  long printCurrentNumber();

}
