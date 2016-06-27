package main.test.model.studnet;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import main.datamodel.objects.ManageUser;
import main.datamodel.objects.User;
import main.model.login.Login;
import main.model.students.Students;

public class StudentsTest {

	private String id = "4567459@st.asojuku.ac.jp";
	private String pass = "password";

	@Test
	public void test() {
		//fail("まだ実装されていません");
		ManageUser mu = new ManageUser();
		mu.birthday = "1999-5-12";
		mu.class_id = 1;
		mu.mail = this.id;
		mu.manage_flg = false;
		mu.name = "name null";
		mu.name_kana = "塗る";
		mu.password = this.pass;
		Students s = new Students();
		assertThat(s.addStudent(mu), is(true));
	}

	//@Test
	/*public void 削除テスト(){
		String id = "43@st.asojuku.ac.jp";
		String pass = "password";
		ManageUser mu = new ManageUser();
		mu.birthday = "1999-5-12";
		mu.class_id = 1;
		mu.mail = id;
		mu.manage_flg = false;
		mu.name = "name null";
		mu.name_kana = "塗る";
		mu.password = pass;
		Students s = new Students();
		s.addStudent(mu);
		Login l = new Login();
		User u = l.login(id, pass);
		assertThat(s.deleteUser(u), is(true));
	}
	*/

	@Test
	public void 変更テスト(){
		String ps = "consoals111";
		Login l = new Login();
		User u = l.login("12345@asojuku.ac.jp", "test");
		System.out.println(u.islogin);
		ManageUser mu = new ManageUser();
		mu.name = ps;
		Students st = new Students();
		st.modifyUser(mu, u);
		User u1 = l.login("12345@asojuku.ac.jp", "test");
		assertThat(u1.name, is(ps));
	}

	@Test
	public void クラス一覧取得テスト(){
		int classId = 4;
		Students st = new Students();
		List<User> u = st.getUserClass(classId);
		for(User u1 : u){
			System.out.println(u1.class_id + ":"+u1.class_name+ ":" + u1.mail + ":"+ u1.name+ ":"+u1.name_kana);
		}
		assertThat(u.isEmpty(), is(false));
	}

}
