package com.framgia.attendance.dao;

import java.util.List;

import org.seasar.dao.annotation.tiger.Arguments;
import org.seasar.dao.annotation.tiger.Query;
import org.seasar.dao.annotation.tiger.S2Dao;
import org.seasar.dao.annotation.tiger.Sql;
import org.seasar.dao.annotation.tiger.SqlFile;

import com.framgia.attendance.entity.UserAccount;

@S2Dao(bean = UserAccount.class)
public interface UserAccountDao {

	public static final String createNewUser_SQL = "insert into user_account "
			+ "(username, hashed_password, user_type, employee_id, default_language)"
			+ " values (" + " /*userAccount.username*/,"
			+ " password(/*userAccount.password*/),"
			+ " /*userAccount.userType*/," + " /*userAccount.employeeId*/,"
			+ " /*userAccount.defaultLanguage*/" + " );";

	public int createNewUser(UserAccount userAccount);

	/**
	 * @param userAccount
	 * @return
	 */
	public int update(UserAccount userAccount);

	/**
	 * 有効なユーザーアカウントを検索する
	 * 
	 * @param username
	 * @param password
	 * @return
	 */

	@Sql("SELECT * FROM user_account u"
			+ " WHERE username=/*username*/ and hashed_password=PASSWORD(/*password*/)")
	@Arguments({ "username", "password" })
	public UserAccount getUserAccount(String username, String password);

	@Sql("SELECT * FROM user_account u INNER JOIN employee e USING(employee_id)"
			+ " WHERE username=/*username*/ and hashed_password=PASSWORD(/*password*/)"
			+ " AND  delete_flag = 0")
	@Arguments({ "username", "password" })
	public UserAccount selectUserAccount(String username, String password);

	@Query("user_id=? and delete_flag = 0 ")
	public UserAccount selectUserAccountByUserId(int userId);

	public List<UserAccount> findAll();

	@Query("delete_flag = 0 ")
	public List<UserAccount> findAllActive();

	@Query("user_id = ?")
	public UserAccount findById(Integer userId);

	@Query("username = ?")
	public UserAccount findByUsername(String username);

	// segment_Type=5 =Resign
	@Sql("SELECT * FROM user_account INNER JOIN employee e USING(employee_id) WHERE delete_flag = 0 AND (e.delete_date is null OR DATE_FORMAT(e.delete_date , '%Y%m') >=  DATE_FORMAT(CURDATE(), '%Y%m')) AND  e.email_address = /*email*/")
	@Arguments("email")
	public UserAccount findByEmail(String email);

	@Sql("update user_account set delete_flag = 1  where user_id in /*userIds*/(1,2,3)")
	@Arguments({ "userIds" })
	public int updateFlagByIds(List<Integer> userIds);

}
