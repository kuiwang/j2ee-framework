package com.fengjing.framework.springmvc.exception.handling;

/**
 * �Զ����쳣
 * 
 * @author scott
 *
 */
@SuppressWarnings("serial")
public class GenericException extends RuntimeException {

  private String customMsg;

  public String getCustomMsg() {
    return customMsg;
  }

  public void setCustomMsg(String customMsg) {
    this.customMsg = customMsg;
  }

  public GenericException(String customMsg) {
    this.customMsg = customMsg;
  }
}
