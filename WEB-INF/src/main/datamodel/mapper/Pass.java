package main.datamodel.mapper;

import java.io.Serializable;

import com.iciql.Iciql.IQColumn;
import com.iciql.Iciql.IQIndex;
import com.iciql.Iciql.IQTable;

@IQTable(name="pass", primaryKey={ "history_id", "mail_address" })
@IQIndex(name="FK__history", value={ "history_id" })
public class Pass implements Serializable {

	private static final long serialVersionUID = 1L;

	@IQColumn(nullable=false)
	public Integer history_id;

	@IQColumn(length=24, nullable=false)
	public String mail_address;

	@IQColumn(nullable=false, defaultValue="b'0'")
	public Boolean pass_flag;

	public Pass() {
	}
}