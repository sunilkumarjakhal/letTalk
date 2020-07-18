package com.demo.login;

import javax.validation.constraints.NotBlank;

public class LoginDataPojo {

  @NotBlank(message="username should not be null")
  private String username;

  @NotBlank(message="password should not be null")
  private String password;

  public String getUsername() {

  return username;

  }

  public void setUsername(String username) {

  this.username = username;

  }

  public String getPassword() {

  return password;

  }

  public void setPassword(String password) {

  this.password = password;

  }

}