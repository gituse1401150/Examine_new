package main.test.model.login;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

import main.datamodel.objects.User;
import main.model.login.Login;

public class LoginTest {

	Login l;

	public LoginTest() {
		l = new Login();
	}

	@Test
	public void シンプルテスト(){
		User u = l.login("4543@st.asojuku.ac.jp", "password");
		assertThat(u.islogin, is(true));
		System.out.println(u.class_id + ":"+u.class_name+ ":" + u.mail + ":"+ u.name+ ":"+u.name_kana);

	}

	@Test
	public void シンプルテスト_失敗_IDミス(){
		User u = l.login("12345@asojuku.ac.j", "test");
		assertThat(u.islogin, is(false));
	}

	@Test
	public void シンプルテスト_失敗_passミス(){
		User u = l.login("12345@asojuku.ac.jp", "testa");
		assertThat(u.islogin, is(false));
	}

	@Test
	public void シンプルテスト_失敗_両方ミス(){
		User u = l.login("12345@asojuku.ac.j", "testa");
		assertThat(u.islogin, is(false));
	}
}
