package main.datamodel.mapper;

import java.io.Serializable;
import java.sql.Date;

import com.iciql.Iciql.IQColumn;
import com.iciql.Iciql.IQIndex;
import com.iciql.Iciql.IQTable;

@IQTable(name="user")
@IQIndex(name="FK__class", value={ "class_id" })
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@IQColumn(primaryKey=true, length=24, nullable=false)
	public String mail_address;

	@IQColumn(nullable=false, defaultValue="b'0'")
	public Boolean admin_flag;

	@IQColumn(nullable=false)
	public Integer class_id;

	@IQColumn(nullable=false)
	public Date user_birth;

	@IQColumn(length=20, nullable=false)
	public String user_name;

	@IQColumn(length=20, nullable=false)
	public String user_name_kana;

	@IQColumn(length=16, nullable=false)
	public String user_pass;

	public User() {
	}
}