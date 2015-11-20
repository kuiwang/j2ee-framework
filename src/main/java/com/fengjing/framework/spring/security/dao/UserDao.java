package com.fengjing.framework.spring.security.dao;

import java.util.List;

import com.fengjing.framework.spring.security.model.User;

public interface UserDao {

  User findUserByUsername(String username);

  /**
   * ����userid��ѯ��ɫ
   * 
   * @param userid
   * @return
   */
  List<String> findAuthority(String userid);

}
