package com.panopset.gp;

import java.lang.reflect.InvocationTargetException;
import java.util.StringTokenizer;
import com.panopset.compat.Logop;
import com.panopset.compat.MapProvider;

public class ReflectionInvoker {
  private ReflectionInvoker() {}

  private MapProvider pmapProvider;
  private Object pobject;
  private Class<?> pclazz;
  private String pmethod;
  private String pparms;

  public String exec() {
    StringTokenizer st = new StringTokenizer(pparms, ",");
    Object[] args = new String[st.countTokens()];
    Class<?>[] parmTypes = new Class<?>[st.countTokens()];
    int incr = 0;
    while (st.hasMoreTokens()) {
      String key = st.nextToken();
      String val = key;
      if (pmapProvider != null) {
        String str = pmapProvider.get(key);
        if (str != null) {
          val = str;
        }
      }
      args[incr] = val;
      parmTypes[incr] = String.class;
      incr++;
    }
    Object rtn;
    try {
      rtn = pclazz.getMethod(pmethod, parmTypes).invoke(pobject, args);
    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
        | NoSuchMethodException | SecurityException ex) {
      Logop.error(ex);
      return "";
    }
    if (rtn == null) {
      return "";
    }
    return rtn.toString();
  }

  public static final class Builder {

    private Object bobject;
    private String bclassName;
    private Class<?> bclazz;
    private String bmethod;
    private String bparms;
    private MapProvider bmapProvider;

    public ReflectionInvoker construct() {
      ReflectionInvoker rtn = new ReflectionInvoker();
      rtn.pobject = bobject;
      if (bobject == null) {
        if (bclazz == null) {
          try {
            rtn.pclazz = Class.forName(bclassName);
          } catch (ClassNotFoundException ex) {
            Logop.error(ex);
          }
        } else {
          rtn.pclazz = bclazz;
        }
      } else {
        rtn.pclazz = bobject.getClass();
      }
      rtn.pmethod = bmethod;
      rtn.pparms = bparms;
      rtn.pmapProvider = bmapProvider;
      return rtn;
    }

    /**
     * Optional, if not specified, either className or classMethodAndParms must
     * be specified.
     *
     * @param object
     *          Object method is to be invoked on.
     * @return Builder.
     */
    public Builder object(final Object object) {
      this.bobject = object;
      return this;
    }

    /**
     * Optional, if not specified, either object or classMethodAndParms must be
     * specified.
     *
     * @param className
     *          className
     * @return Builder.
     */
    public Builder className(final String className) {
      this.bclassName = className;
      return this;
    }

    /**
     * If not specified, static methods only may be invoked.
     */
    public Builder clazz(final Class<?> clazz) {
      this.bclazz = clazz;
      return this;
    }

    public Builder method(final String method) {
      this.bmethod = method;
      return this;
    }

    public Builder parms(final String parms) {
      this.bparms = parms;
      return this;
    }

    /**
     * Optional mapProvider. If specified, the mapProvider will be checked for
     * parameter values. If the parameter does not match any of the
     * mapProvider's keys, then the parameter is simply used by itself.
     */
    public Builder mapProvider(final MapProvider mapProvider) {
      this.bmapProvider = mapProvider;
      return this;
    }

    public Builder methodAndParms(final String methodAndParms) {
      int paramsStart = methodAndParms.indexOf("(");
      if (paramsStart < 2) {
        Logop.error(String.format("Format should be function(parms), found: %s", methodAndParms));
      }
      bmethod = methodAndParms.substring(0, paramsStart);
      bparms = methodAndParms.substring(paramsStart + 1,
          methodAndParms.length() - 1);
      return this;
    }

    /**
     * Optional, if not specified either object or className must be specified.
     */
    public Builder classMethodAndParms(final String classMethodAndParms) {
      int methodStart = classMethodAndParms.lastIndexOf(".");
      if (methodStart == -1) {
        return this;
      }
      String className = classMethodAndParms.substring(0, methodStart);
      String methodAndParms = classMethodAndParms.substring(methodStart + 1);
      return className(className).methodAndParms(methodAndParms);
    }
  }
}
