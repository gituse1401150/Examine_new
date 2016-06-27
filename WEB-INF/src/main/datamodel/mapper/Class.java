package main.datamodel.mapper;

import java.io.Serializable;

import com.iciql.Iciql.IQColumn;
import com.iciql.Iciql.IQTable;

@IQTable(name="class")
public class Class implements Serializable {

	private static final long serialVersionUID = 1L;

	@IQColumn(primaryKey=true, autoIncrement=true, nullable=false)
	public Integer class_id;

	@IQColumn(length=20, nullable=false)
	public String class_name;

	public Class() {
	}
}