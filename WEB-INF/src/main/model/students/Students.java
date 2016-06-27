package main.model.students;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.iciql.Db;

import main.datamodel.mapper.User;
import main.datamodel.objects.ManageUser;
import main.model.IciqlObjectFactory;

public class Students {

	private DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd");

	public boolean addStudent(ManageUser mu){
		if(mu.birthday == null ||
			mu.class_id == 0||
			mu.mail == null||
			mu.name == null||
			mu.name_kana == null||
			mu.password == null){
			return false;
		}
		String reg_id = "^[0-9]{7}[@](st\\.asojuku\\.ac.jp|asojuku\\.ac\\.jp)$";
        if(!Pattern.matches(reg_id, mu.mail)){
            return false;
        }
		String reg_pass = "^[0-9a-zA-Z]{4,16}$";
		if(!Pattern.matches(reg_pass, mu.password)){
			return false;
		}
		User u  = new User();
		u.admin_flag = mu.manage_flg;
		u.class_id = mu.class_id;
		u.mail_address = mu.mail;
		Date d = new Date(0);
		u.user_birth = d.valueOf(mu.birthday);
		u.user_name = mu.name;
		u.user_name_kana = mu.name_kana;
		System.out.println(u.user_name_kana);
		u.user_pass = mu.password;
		Db db = new IciqlObjectFactory().getIciqlObject();
		try {
			db.insert(u);
		} catch (Exception e) {
			//e.printStackTrace();
			return false;
		}
		return true;
	}


	public boolean deleteUser(main.datamodel.objects.User user){
		User u = new User();
		Db db = new IciqlObjectFactory().getIciqlObject();
		List<User> r = db.from(u)
				.where(u.mail_address).is(user.mail)
				.and(u.class_id).is(user.class_id)
				.select();
		try{
			db.delete(r.get(0));
		}catch(Exception e){
			//e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean modifyUser(ManageUser user, main.datamodel.objects.User based){
		if(based.mail == null||
			based.name == null||
			based.name_kana == null){

			return false;
		}
		if(user.birthday == null &&
			user.class_id == 0 &&
			user.mail == null &&
			user.name == null &&
			user.name_kana == null &&
			user.password == null){
			return false;
		}

		String reg_pass = "^[0-9a-zA-Z]{4,16}$";
		if(user.password != null && !Pattern.matches(reg_pass, user.password)){
			return false;
		}

		main.datamodel.mapper.User c = new main.datamodel.mapper.User();
		Db db = new IciqlObjectFactory().getIciqlObject();
		List<main.datamodel.mapper.User> r = db.from(c)
				.where(c.mail_address).is(based.mail)
				.and(c.user_name).is(based.name)
				.select();
		c = r.get(0);

		if(user.class_id != 0){
			c.class_id = user.class_id;
		}

		if(user.name != null){
			c.user_name = user.name;
		}

		if(user.name_kana != null){
			c.user_name_kana = user.name_kana;
		}

		if(user.password != null){
			c.user_pass = user.password;
		}

		try{
			db.update(c);
		}catch(Exception e){
			//e.printStackTrace();
			return false;
		}
		return true;
	}

	public List<main.datamodel.objects.User> getUserClass(int classid){
		Db db = new IciqlObjectFactory().getIciqlObject();
		main.datamodel.mapper.User c = new main.datamodel.mapper.User();
		List<main.datamodel.mapper.User> r = db.from(c)
				.where(c.class_id).is(classid)
				.select();
		List<main.datamodel.objects.User> lu = new ArrayList<main.datamodel.objects.User>();
		for(main.datamodel.mapper.User rs : r){
			main.datamodel.objects.User u1 = new main.datamodel.objects.User();
			u1.name = rs.user_name;
			u1.name_kana = rs.user_name_kana;
			u1.class_id = rs.class_id;
			u1.mail = rs.mail_address;
			u1.manage_flg = rs.admin_flag;
			main.datamodel.mapper.Class c1 = new main.datamodel.mapper.Class();
			List<main.datamodel.mapper.Class> cl = db.from(c1)
												.where(c1.class_id).is(u1.class_id)
												.select();

			u1.class_name = cl.get(0).class_name;
			lu.add(u1);
		}
		return lu;
	}

}
