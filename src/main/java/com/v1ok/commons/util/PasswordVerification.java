package com.v1ok.commons.util;

import java.security.MessageDigest;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

public final class PasswordVerification {

  private PasswordVerification() {
  }

  public static String generate(String password) {
    String salt = RandomStringUtils.randomNumeric(16);
    password = md5Hex(password + salt);
    char[] cs = new char[48];

    for (int i = 0; i < 48; i += 3) {
      cs[i] = password.charAt(i / 3 * 2);
      char c = salt.charAt(i / 3);
      cs[i + 1] = c;
      cs[i + 2] = password.charAt(i / 3 * 2 + 1);
    }

    return new String(cs);
  }

  public static boolean verify(String password, String md5) {
    char[] cs1 = new char[32];
    char[] cs2 = new char[16];

    for (int i = 0; i < 48; i += 3) {
      cs1[i / 3 * 2] = md5.charAt(i);
      cs1[i / 3 * 2 + 1] = md5.charAt(i + 2);
      cs2[i / 3] = md5.charAt(i + 1);
    }

    String salt = new String(cs2);
    return md5Hex(password + salt).equals(new String(cs1));
  }

  private static String md5Hex(String src) {
    try {
      MessageDigest md5 = DigestUtils.getMd5Digest();
      byte[] bs = md5.digest(src.getBytes());
      return new String((new Hex()).encode(bs));
    } catch (Exception var3) {
      return null;
    }
  }
}
