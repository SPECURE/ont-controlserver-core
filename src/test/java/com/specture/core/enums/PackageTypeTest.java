package com.specture.core.enums;

import com.specture.core.exception.PackageTypeException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static com.specture.core.TestConstants.DEFAULT_PACKAGE_TYPE_STRING;
import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
public class PackageTypeTest {

    @Test(expected = PackageTypeException.class)
    public void whenFromString_getWrongPackageType_ExpectExpectation() {
        PackageType.fromString("wrong");
    }

    @Test
    public void whenFromString_getProperPackageType_ExpectPackageType() {
        PackageType packageType = PackageType.fromString(DEFAULT_PACKAGE_TYPE_STRING);
        assertEquals(PackageType.MOBILE_PREPAID, packageType);
    }
}
