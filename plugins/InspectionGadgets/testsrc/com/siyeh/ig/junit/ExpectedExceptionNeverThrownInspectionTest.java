/**
 * (c) 2013 Desert Island BV
 * created: 14 08 2013
 */
package com.siyeh.ig.junit;

import com.intellij.codeInspection.LocalInspectionTool;
import com.siyeh.ig.LightInspectionTestCase;

/**
 * @author Bas Leijdekkers
 */
public class ExpectedExceptionNeverThrownInspectionTest extends LightInspectionTestCase {
  @Override
  protected LocalInspectionTool getInspection() {
    return new ExpectedExceptionNeverThrownInspection();
  }

  @Override
  protected String[] getEnvironmentClasses() {
    return new String[] {"package org.junit; " +
                         "public @interface Test {\n" +
                         "    java.lang.Class<? extends java.lang.Throwable> expected() default org.junit.Test.None.class;" +
                         "}"};
  }

  public void testSimple() {
    doTest("class X {" +
           "    @org.junit.Test(expected=/*Expected 'java.io.IOException' never thrown in body of 'test()'*/java.io.IOException/**/.class)" +
           "    public void test() {}" +
           "}");
  }

  public void testError() {
    doTest("class X {" +
           "    @org.junit.Test(expected = Error.class)" +
           "    public void test() {}" +
           "}");
  }

  public void testRuntimeException() {
    doTest("class X {" +
           "    @org.junit.Test(expected = IllegalArgumentException.class)" +
           "    public void test() {}" +
           "}");
  }
}
