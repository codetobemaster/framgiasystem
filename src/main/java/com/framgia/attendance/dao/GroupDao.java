package com.framgia.attendance.dao;

import java.util.List;

import org.seasar.dao.annotation.tiger.Arguments;
import org.seasar.dao.annotation.tiger.S2Dao;
import org.seasar.dao.annotation.tiger.Sql;

import com.framgia.attendance.entity.Group;

@S2Dao(bean = Group.class)
public interface GroupDao {

	@Sql("insert into `group`(group_name, approver_id) values(?, ?)")
	public void insert(String groupName, int approverId);

	@Sql("update `group` set group_name=/*groupName*/, approver_id=/*approverId*/ where group_id=/*groupId*/")
	@Arguments({"groupName", "approverId", "groupId"})
	public void update(String groupName, int approverId, int groupId);
	
	@Sql("select * from `group` where group_id=?")
	public Group getGroupById(Integer groupId);

	@Sql("select group_id from `group` where approver_id=?")
	public List<Integer> listApproverGroups(int approverId);
	

	
	@Sql("select gr.*, CONCAT(emp.last_name_kanji, ' ', emp.first_name_kanji) as approverName from `group` gr inner join employee emp on emp.employee_id = gr.approver_id")
	public  List<Group> selectAll();
	
	@Sql("select * from `group` where approver_id=/*approverId*/")
	public  List<Group> selectByApproverId(int approverId);
	
	@Sql("select gr.*, CONCAT(emp.last_name_kanji, ' ', emp.first_name_kanji) as approverName from `group` gr inner join employee emp on emp.employee_id = gr.approver_id where lower(gr.group_name) like lower(?)")
	public Group selectByName(String groupName);
	
	@Sql("select gr.*, CONCAT(emp.last_name_kanji, ' ', emp.first_name_kanji) as approverName from `group` gr inner join employee emp on emp.employee_id = gr.approver_id where gr.id = ?")
    public Group selectByGroupId(int groupId);
	
	@Sql("delete from `group` where group_id in /*groupIds*/(1,2,3)")
	@Arguments({"groupIds"})
	public int deleteByIds(List<Integer> groupIds);

}
