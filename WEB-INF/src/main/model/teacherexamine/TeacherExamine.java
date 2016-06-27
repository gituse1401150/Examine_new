package main.model.teacherexamine;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.iciql.Db;

import main.datamodel.objects.ExamineHistoryTeacher;
import main.datamodel.objects.GetExamineHistoryTeacher;
import main.model.IciqlObjectFactory;

public class TeacherExamine {
	public List<ExamineHistoryTeacher> examine_select(GetExamineHistoryTeacher geht) {
		List<ExamineHistoryTeacher> p_result = new ArrayList<ExamineHistoryTeacher>();
		List<ExamineHistoryTeacher> u_result = new ArrayList<ExamineHistoryTeacher>();
		List<ExamineHistoryTeacher> c_result = new ArrayList<ExamineHistoryTeacher>();
		List<ExamineHistoryTeacher> result = new ArrayList<ExamineHistoryTeacher>();
		Db db = new IciqlObjectFactory().getIciqlObject();
		int promoter_id = Integer.parseInt(geht.promoter_id);
		int examine_id = Integer.parseInt(geht.examine_id);
		Date date = Date.valueOf(geht.examine_day);
		boolean pass_flag = geht.pass_flg;

		try {
			main.datamodel.mapper.History history = new main.datamodel.mapper.History();
			List<main.datamodel.mapper.History> h = db.from(history)
													.where(history.examine_id).is(examine_id)
													.and(history.date).is(date)
													.select();
			if (h.isEmpty()) {

			} else {
				int history_id = h.get(0).history_id;
				main.datamodel.mapper.Pass pass = new main.datamodel.mapper.Pass();
				List<main.datamodel.mapper.Pass> pl = db.from(pass)
													  .where(pass.history_id).is(history_id)
													  .and(pass.pass_flag).is(pass_flag)
													  .select();
				if (pl.isEmpty()) {

				} else {
					for (main.datamodel.mapper.Pass r : pl) {
						ExamineHistoryTeacher eht = new ExamineHistoryTeacher();
						eht.mail = r.mail_address;
						p_result.add(eht);
					}

					for (ExamineHistoryTeacher r : p_result) {
						ExamineHistoryTeacher eht = new ExamineHistoryTeacher();
						main.datamodel.mapper.User user = new main.datamodel.mapper.User();
						List<main.datamodel.mapper.User> ul = db.from(user)
															  .where(user.mail_address).is(r.mail)
															  .select();
						eht.mail = r.mail;
						eht.class_id = ul.get(0).class_id.toString();
						eht.name = ul.get(0).user_name;
						u_result.add(eht);
					}

					for (ExamineHistoryTeacher r : u_result) {
						ExamineHistoryTeacher eht = new ExamineHistoryTeacher();
						main.datamodel.mapper.Class c = new main.datamodel.mapper.Class();
						List<main.datamodel.mapper.Class> cl = db.from(c)
															   .where(c.class_id).is(Integer.parseInt(r.class_id))
															   .select();
						eht.mail = r.mail;
						eht.class_id = r.class_id;
						eht.name = r.name;
						eht.class_name = cl.get(0).class_name;
						c_result.add(eht);
					}

					main.datamodel.mapper.Examine examine = new main.datamodel.mapper.Examine();
					List<main.datamodel.mapper.Examine> el = db.from(examine)
															 .where(examine.examine_id).is(examine_id)
															 .select();
					String examine_name = el.get(0).examine_name;

					main.datamodel.mapper.Promoter promoter = new main.datamodel.mapper.Promoter();
					List<main.datamodel.mapper.Promoter> prl = db.from(promoter)
															   .where(promoter.promoter_id).is(promoter_id)
															   .select();
					String promoter_name = prl.get(0).promoter_name;

					for (ExamineHistoryTeacher r : c_result) {
						ExamineHistoryTeacher eht = new ExamineHistoryTeacher();
						eht.mail = r.mail;
						eht.class_id = r.class_id;
						eht.class_name = r.class_name;
						eht.examine_id = String.valueOf(examine_id);
						eht.examine_name = examine_name;
						eht.name = r.name;
						eht.promoter_id = String.valueOf(promoter_id);
						eht.promoter_name = promoter_name;
						result.add(eht);
					}
				}
			}
		} catch (Exception e) {
			return new ArrayList<ExamineHistoryTeacher>();
		}

		return result;
	}

	public List<ExamineHistoryTeacher> student_select(String mail_address) {
		List<ExamineHistoryTeacher> h_result = new ArrayList<ExamineHistoryTeacher>();
		List<ExamineHistoryTeacher> e_result = new ArrayList<ExamineHistoryTeacher>();
		List<ExamineHistoryTeacher> p_result = new ArrayList<ExamineHistoryTeacher>();
		List<ExamineHistoryTeacher> result = new ArrayList<ExamineHistoryTeacher>();
		Db db = new IciqlObjectFactory().getIciqlObject();

		try {
			main.datamodel.mapper.Pass pass = new main.datamodel.mapper.Pass();
			List<main.datamodel.mapper.Pass> pl = db.from(pass)
												  .where(pass.mail_address).is(mail_address)
												  .and(pass.pass_flag).is(true)
												  .select();
			for (main.datamodel.mapper.Pass r : pl) {
				ExamineHistoryTeacher eht = new ExamineHistoryTeacher();
				int history_id = r.history_id;
				main.datamodel.mapper.History history = new main.datamodel.mapper.History();
				List<main.datamodel.mapper.History> hl = db.from(history)
														 .where(history.history_id).is(history_id)
														 .select();
				eht.examine_id = hl.get(0).examine_id.toString();
				h_result.add(eht);
			}

			for (ExamineHistoryTeacher r : h_result) {
				ExamineHistoryTeacher eht = new ExamineHistoryTeacher();
				int examine_id = Integer.parseInt(r.examine_id);
				main.datamodel.mapper.Examine examine = new main.datamodel.mapper.Examine();
				List<main.datamodel.mapper.Examine> el = db.from(examine)
														 .where(examine.examine_id).is(examine_id)
														 .select();
				eht.examine_id = r.examine_id;
				eht.examine_name = el.get(0).examine_name;
				eht.promoter_id = el.get(0).promoter_id.toString();
				e_result.add(eht);
			}

			for (ExamineHistoryTeacher r : e_result) {
				ExamineHistoryTeacher eht = new ExamineHistoryTeacher();
				int promoter_id = Integer.parseInt(r.promoter_id);
				main.datamodel.mapper.Promoter promoter = new main.datamodel.mapper.Promoter();
				List<main.datamodel.mapper.Promoter> prl = db.from(promoter)
														   .where(promoter.promoter_id).is(promoter_id)
														   .select();
				eht.examine_id = r.examine_id;
				eht.examine_name = r.examine_name;
				eht.promoter_id = r.promoter_id;
				eht.promoter_name = prl.get(0).promoter_name;
				p_result.add(eht);
			}

			main.datamodel.mapper.User user = new main.datamodel.mapper.User();
			List<main.datamodel.mapper.User> ul = db.from(user)
												  .where(user.mail_address).is(mail_address)
												  .select();
			int class_id = ul.get(0).class_id;
			String name = ul.get(0).user_name;

			main.datamodel.mapper.Class c = new main.datamodel.mapper.Class();
			List<main.datamodel.mapper.Class> cl = db.from(c)
												   .where(c.class_id).is(class_id)
												   .select();
			String class_name = cl.get(0).class_name;

			for (ExamineHistoryTeacher r : p_result) {
				ExamineHistoryTeacher eht = new ExamineHistoryTeacher();
				eht.class_id = String.valueOf(class_id);
				eht.class_name = class_name;
				eht.mail = mail_address;
				eht.name = name;
				eht.examine_id = r.examine_id;
				eht.examine_name = r.examine_name;
				eht.promoter_id = r.promoter_id;
				eht.promoter_name = r.promoter_name;
				result.add(eht);
			}
		} catch (Exception e) {
			return new ArrayList<ExamineHistoryTeacher>();
		}

		return result;
	}

}
