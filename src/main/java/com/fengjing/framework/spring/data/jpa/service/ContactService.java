package com.fengjing.framework.spring.data.jpa.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fengjing.framework.springmvc.model.Contact;

public interface ContactService {

  Contact findById(int id);

  Contact save(Contact contact);

  Contact modifyContact(Contact contact);

  void delete(Contact contact);

  void delete(int id);

  Page<Contact> findAll(Pageable pageable);

  List<Contact> listAll();

  /**
   * ��ѯ����С��150�� �Ұ�Id��������
   * 
   * @param age
   * @param pageable
   * @return
   */
  Page<Contact> findByAgeLessThanEqualOrderByIdDesc(int age, Pageable pageable);

}
