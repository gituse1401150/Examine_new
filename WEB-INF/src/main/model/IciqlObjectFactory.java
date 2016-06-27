package main.model;

import com.iciql.Db;

public class IciqlObjectFactory {
	public Db getIciqlObject(){
		return Db.open("jdbc:mysql://localhost:3306/examine", "root", "");
	}
}
