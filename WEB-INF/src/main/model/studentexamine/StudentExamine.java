package main.model.studentexamine;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.iciql.Db;

import main.datamodel.objects.AddHistory;
import main.datamodel.objects.StudentExamineHistory;
import main.datamodel.objects.User;
import main.model.IciqlObjectFactory;

public class StudentExamine {
	public boolean insert(User user, AddHistory add) {
		boolean result = false;
		Db db = new IciqlObjectFactory().getIciqlObject();
		String mail_address = user.mail;
		int examine_id = Integer.parseInt(add.examine_id);
		boolean pass_flag = add.pass_flg;
		Date date = Date.valueOf(add.examine_day);

		try {
			main.datamodel.mapper.History history = new main.datamodel.mapper.History();
			List<main.datamodel.mapper.History> hl = db.from(history)
													 .where(history.examine_id).is(examine_id)
													 .and(history.date).is(date)
													 .select();
			if (hl.isEmpty()) {
				result = false;
			} else {
				int history_id = hl.get(0).history_id;
				main.datamodel.mapper.Pass pass = new main.datamodel.mapper.Pass();
				pass.history_id = history_id;
				pass.mail_address = mail_address;
				pass.pass_flag = pass_flag;
				result = db.insert(pass);
			}
		} catch(Exception e) {
			return false;
		}
		return result;
	}

	public List<StudentExamineHistory> select(User user) {
		List<StudentExamineHistory> p_result = new ArrayList<StudentExamineHistory>();
		List<StudentExamineHistory> h_result = new ArrayList<StudentExamineHistory>();
		List<StudentExamineHistory> e_result = new ArrayList<StudentExamineHistory>();
		List<StudentExamineHistory> result = new ArrayList<StudentExamineHistory>();
		Db db = new IciqlObjectFactory().getIciqlObject();
		String mail_address = user.mail;

		try {
			main.datamodel.mapper.Pass pass = new main.datamodel.mapper.Pass();
			List<main.datamodel.mapper.Pass> pl = db.from(pass)
												  .where(pass.mail_address).is(mail_address)
												  .select();
			if (pl.isEmpty()) {

			} else {
				for (main.datamodel.mapper.Pass p : pl) {
					StudentExamineHistory seh = new StudentExamineHistory();
					seh.pass_flg = p.pass_flag;
					seh.history_id = p.history_id.toString();
					p_result.add(seh);
				}

				for (StudentExamineHistory r : p_result) {
					StudentExamineHistory seh = new StudentExamineHistory();
					main.datamodel.mapper.History history = new main.datamodel.mapper.History();
					Integer history_id = Integer.parseInt(r.history_id);
					List<main.datamodel.mapper.History> hl = db.from(history)
															 .where(history.history_id).is(history_id)
															 .select();
					seh.examine_id = hl.get(0).examine_id.toString();
					seh.examine_day = hl.get(0).date.toString();
					seh.history_id = r.history_id;
					seh.pass_flg = r.pass_flg;
					h_result.add(seh);
				}

				for (StudentExamineHistory r : h_result) {
					StudentExamineHistory seh = new StudentExamineHistory();
					Integer examine_id = Integer.parseInt(r.examine_id);
					main.datamodel.mapper.Examine examine = new main.datamodel.mapper.Examine();
					List<main.datamodel.mapper.Examine> el = db.from(examine)
															 .where(examine.examine_id).is(examine_id)
															 .select();
					seh.examine_id = el.get(0).examine_id.toString();
					seh.examine_name = el.get(0).examine_name;
					seh.promoter_id = el.get(0).promoter_id.toString();
					seh.examine_day = r.examine_day;
					seh.history_id = r.history_id;
					seh.pass_flg = r.pass_flg;
					e_result.add(seh);
				}

				for (StudentExamineHistory r : e_result) {
					StudentExamineHistory seh = new StudentExamineHistory();
					Integer promoter_id = Integer.parseInt(r.promoter_id);
					main.datamodel.mapper.Promoter promoter = new main.datamodel.mapper.Promoter();
					List<main.datamodel.mapper.Promoter> prl = db.from(promoter)
															   .where(promoter.promoter_id).is(promoter_id)
															   .select();
					seh.promoter_name = prl.get(0).promoter_name;
					seh.examine_id = r.examine_id;
					seh.examine_name = r.examine_name;
					seh.promoter_id = r.promoter_id;
					seh.examine_day = r.examine_day;
					seh.history_id = r.history_id;
					seh.pass_flg = r.pass_flg;
					result.add(seh);
				}
			}
		} catch(Exception e) {
			return new ArrayList<StudentExamineHistory>();
		}
		return result;
	}

	public boolean delete(User user, StudentExamineHistory seh) {
		boolean result = false;
		Db db = new IciqlObjectFactory().getIciqlObject();
		String mail_address = user.mail;
		int history_id = Integer.parseInt(seh.history_id);

		try {
			main.datamodel.mapper.Pass pass = new main.datamodel.mapper.Pass();
			pass.history_id = history_id;
			pass.mail_address = mail_address;
			result = db.delete(pass);
		} catch (Exception e) {
			return false;
		}

		return result;
	}
}
