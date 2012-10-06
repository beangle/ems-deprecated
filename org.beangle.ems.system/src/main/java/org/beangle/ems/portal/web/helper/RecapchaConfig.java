/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.portal.web.helper;

/**
 * Config recaptcha publickey and private key
 * 
 * @author chaostone
 * @since 3.0.0
 */
public class RecapchaConfig {
  
  String publickey;

  String privatekey;

  public String getPublickey() {
    return publickey;
  }

  public void setPublickey(String publickey) {
    this.publickey = publickey;
  }

  public String getPrivatekey() {
    return privatekey;
  }

  public void setPrivatekey(String privatekey) {
    this.privatekey = privatekey;
  }

  
}
