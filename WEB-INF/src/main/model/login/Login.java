package main.model.login;

import java.util.List;

import com.iciql.Db;

import main.datamodel.objects.User;
import main.model.IciqlObjectFactory;

public class Login {
	public User login(String id, String pass){
		Db db = new IciqlObjectFactory().getIciqlObject();
		main.datamodel.mapper.User user = new main.datamodel.mapper.User();
		List<main.datamodel.mapper.User> r = db.from(user)
										.where(user.mail_address).is(id)
										.and(user.user_pass).is(pass)
										.select();
		if(r.isEmpty()){
			return new User();
		}else{
			User u = new User();
			main.datamodel.mapper.User uo = r.get(0);
			u.name = uo.user_name;
			u.name_kana = uo.user_name_kana;
			u.class_id = uo.class_id;
			main.datamodel.mapper.Class c = new main.datamodel.mapper.Class();
			List<main.datamodel.mapper.Class> cl = db.from(c)
												.where(c.class_id).is(uo.class_id)
												.select();

			u.class_name = cl.get(0).class_name;
			u.mail = uo.mail_address;
			u.manage_flg = uo.admin_flag;
			u.islogin = true;
			return u;
		}
	}
}
