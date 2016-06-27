package main.datamodel.mapper;

import java.io.Serializable;

import com.iciql.Iciql.IQColumn;
import com.iciql.Iciql.IQTable;

@IQTable(name="promoter")
public class Promoter implements Serializable {

	private static final long serialVersionUID = 1L;

	@IQColumn(primaryKey=true, autoIncrement=true, nullable=false)
	public Integer promoter_id;

	@IQColumn(length=10, nullable=false)
	public String promoter_name;

	public Promoter() {
	}
}