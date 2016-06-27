package main.datamodel.mapper;

import java.io.Serializable;
import java.sql.Date;

import com.iciql.Iciql.IQColumn;
import com.iciql.Iciql.IQIndex;
import com.iciql.Iciql.IQTable;

@IQTable(name="history")
@IQIndex(name="FK__examine", value={ "examine_id" })
public class History implements Serializable {

	private static final long serialVersionUID = 1L;

	@IQColumn(primaryKey=true, autoIncrement=true, nullable=false)
	public Integer history_id;

	@IQColumn
	public Date date;

	@IQColumn(nullable=false)
	public Integer examine_id;

	public History() {
	}
}