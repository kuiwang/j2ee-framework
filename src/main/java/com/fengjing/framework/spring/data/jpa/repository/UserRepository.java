package com.fengjing.framework.spring.data.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.fengjing.framework.springmvc.model.User;

/**
 * User Spring Data Jpa
 * 
 * @author fengjing
 *
 */
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {

  /**
   * �÷���ʵ�����ǲ�ѯcom.fengjing.framework.springmvc.model.User�е�
   * @NamedQuery(name="findUserById",query=" from User a where a.id=?1 ")
   * 
   * @see com.fengjing.framework.springmvc.model.User<br/>
   *      @NamedQuery(name="findUserById",query=" from User a where a.id=?1 ")
   * @param id
   * @return
   */
  User findUserById(int id);

  /**
   * ���ݸ�����sql����޸��û�
   * 
   * @param username
   * @param id
   * @return
   */
  @Modifying
  @Query(value = "update User u set u.username=?1 where u.id=?2 ")
  int modifyById(String username, int id);

  /**
   * ���û���ģ����ѯ
   * 
   * @param username
   * @return
   */
  List<User> findByUsernameLike(String username);

  /**
   * ��ѯidС�ڼ����û�
   * 
   * @param id
   * @return
   */
  List<User> findByIdLessThan(int id);


  /**
   * ����usernameģ����ѯ ����id��������
   * 
   * @param username
   * @return
   */
  List<User> findByUsernameLikeOrderByIdDesc(String username);



  /**
   * ����id��ѯ between and
   * 
   * @param i
   * @param j
   * @return
   */
  List<User> findByIdBetween(int i, int j);



  /**
   * ���ݲ���id��ѯ�û� ע������ķ�����findUserByDeptId ��ȥUser��������Ϊdept�����Խ���ȥdept������ȥ��id
   * ����֮��Ĺ�����ѯ
   * 
   * @param deptid
   * @return
   */
  @Query(value = " from User user where user.dept.id=:deptid")
  List<User> findUserByDeptId(@Param("deptid") int deptid);

}
