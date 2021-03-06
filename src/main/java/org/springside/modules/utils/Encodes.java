/**
 * Copyright (c) 2005-2012 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package org.springside.modules.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 * ��װ���ָ�ʽ�ı�����빤����.
 * 
 * 1.Commons-Codec�� hex/base64 ���� 2.���Ƶ�base62 ���� 3.Commons-Lang��xml/html escape
 * 4.JDK�ṩ��URLEncoder
 * 
 * @author calvin
 */
public class Encodes {

  private static final String DEFAULT_URL_ENCODING = "UTF-8";

  private static final char[] BASE62 =
      "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

  /**
   * Hex����.
   */
  public static String encodeHex(byte[] input) {
    return Hex.encodeHexString(input);
  }

  /**
   * Hex����.
   */
  public static byte[] decodeHex(String input) {
    try {
      return Hex.decodeHex(input.toCharArray());
    } catch (DecoderException e) {
      throw Exceptions.unchecked(e);
    }
  }

  /**
   * Base64����.
   */
  public static String encodeBase64(byte[] input) {
    return Base64.encodeBase64String(input);
  }

  /**
   * Base64����, URL��ȫ(��Base64�е�URL�Ƿ��ַ�'+'��'/'תΪ'-'��'_', ��RFC3548).
   */
  public static String encodeUrlSafeBase64(byte[] input) {
    return Base64.encodeBase64URLSafeString(input);
  }

  /**
   * Base64����.
   */
  public static byte[] decodeBase64(String input) {
    return Base64.decodeBase64(input);
  }

  /**
   * Base62���롣
   */
  public static String encodeBase62(byte[] input) {
    char[] chars = new char[input.length];
    for (int i = 0; i < input.length; i++) {
      chars[i] = BASE62[((input[i] & 0xFF) % BASE62.length)];
    }
    return new String(chars);
  }

  /**
   * Html ת��.
   */
  public static String escapeHtml(String html) {
    return StringEscapeUtils.escapeHtml4(html);
  }

  /**
   * Html ����.
   */
  public static String unescapeHtml(String htmlEscaped) {
    return StringEscapeUtils.unescapeHtml4(htmlEscaped);
  }

  /**
   * Xml ת��.
   */
  public static String escapeXml(String xml) {
    return StringEscapeUtils.escapeXml(xml);
  }

  /**
   * Xml ����.
   */
  public static String unescapeXml(String xmlEscaped) {
    return StringEscapeUtils.unescapeXml(xmlEscaped);
  }

  /**
   * URL ����, EncodeĬ��ΪUTF-8.
   */
  public static String urlEncode(String part) {
    try {
      return URLEncoder.encode(part, DEFAULT_URL_ENCODING);
    } catch (UnsupportedEncodingException e) {
      throw Exceptions.unchecked(e);
    }
  }

  /**
   * URL ����, EncodeĬ��ΪUTF-8.
   */
  public static String urlDecode(String part) {

    try {
      return URLDecoder.decode(part, DEFAULT_URL_ENCODING);
    } catch (UnsupportedEncodingException e) {
      throw Exceptions.unchecked(e);
    }
  }
}
