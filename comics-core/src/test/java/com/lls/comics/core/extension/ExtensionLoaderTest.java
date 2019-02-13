package com.lls.comics.core.extension;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

/************************************
 * ExtensionLoaderTest
 * @author liliangshan
 * @date 2019-02-13
 ************************************/
public class ExtensionLoaderTest extends TestCase {

  @Test
  public void testExtensionLoader() {

    // singleton
    Assert.assertEquals(1, ExtensionLoader.getExtensionLoader(SpiSingletonInterface.class).getExtension("spiSingletonTest").printCurrentNumber());
    Assert.assertEquals(1, ExtensionLoader.getExtensionLoader(SpiSingletonInterface.class).getExtension("spiSingletonTest").printCurrentNumber());

    // prototype
    Assert.assertEquals(1, ExtensionLoader.getExtensionLoader(SpiPrototypeInterface.class).getExtension("spiPrototypeTest").printCurrentNumber());
    Assert.assertEquals(2, ExtensionLoader.getExtensionLoader(SpiPrototypeInterface.class).getExtension("spiPrototypeTest").printCurrentNumber());

    Assert.assertEquals(1, ExtensionLoader.getExtensionLoader(SpiPrototypeInterface.class).getExtensions("").size());
    ExtensionLoader loader = ExtensionLoader.getExtensionLoader(SpiPrototypeInterface.class);
    loader.addExtensionClass(SpiPrototypeInterfaceImpl2.class);

    // 返回所有实现类
    ExtensionLoader.initExtensionLoader(SpiPrototypeInterface.class);
    junit.framework.Assert.assertEquals(1, ExtensionLoader.getExtensionLoader(SpiSingletonInterface.class).getExtensions("").size());
    junit.framework.Assert.assertEquals(2, ExtensionLoader.getExtensionLoader(SpiPrototypeInterface.class).getExtensions("").size());


  }

}
