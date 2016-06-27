package main.datamodel.mapper;

import java.io.Serializable;

import com.iciql.Iciql.IQColumn;
import com.iciql.Iciql.IQIndex;
import com.iciql.Iciql.IQTable;

@IQTable(name="examine")
@IQIndex(name="FK__promoter", value={ "promoter_id" })
public class Examine implements Serializable {

	private static final long serialVersionUID = 1L;

	@IQColumn(primaryKey=true, autoIncrement=true, nullable=false)
	public Integer examine_id;

	@IQColumn(length=100, nullable=false)
	public String examine_name;

	@IQColumn(nullable=false)
	public Integer promoter_id;

	public Examine() {
	}
}