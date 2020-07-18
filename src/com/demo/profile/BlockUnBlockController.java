package com.demo.profile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.demo.util.ConstantConfig;
import com.demo.util.MongoDBConnection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

@Controller
public class BlockUnBlockController {

	@RequestMapping(value = "/blockUser", method = RequestMethod.GET)
	public String blockUser(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String queryName) {


		
		if (MongoDBConnection.getDB() == null || MongoDBConnection.getMongoClient() == null) {

			if (MongoDBConnection.makeDBConnection(ConstantConfig.DB_IP, ConstantConfig.DB_PORT)) {
				// System.out..println("DB is again connected");
			} else {

				// System.out..println("DB is not connecting.........");
			}
		}

		HttpSession session = request.getSession();
		String name = (String) session.getAttribute("letsTalk_username");

		if (name != null && name.length() > 0) {
	
			DBCollection table = MongoDBConnection.getDB().getCollection("tbl_block_list_data");

			DBObject updateQuery = new BasicDBObject();
			
			BasicDBObject data = new BasicDBObject();
			data.put("name", queryName);
			
			updateQuery.put("$push", new BasicDBObject("block_list", data));
			//$addToSet
			BasicDBObject query = new BasicDBObject();
			query.put("username", name);
			table.update(query, updateQuery,true , true);
			


		}

		else {


		}
		 return "redirect:" + "/friendProfile?queryName="+queryName;
	

	}
	
	@RequestMapping(value = "/unBlockUser", method = RequestMethod.GET)
	public String unBlockUser(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String queryName) {


		
		if (MongoDBConnection.getDB() == null || MongoDBConnection.getMongoClient() == null) {

			if (MongoDBConnection.makeDBConnection(ConstantConfig.DB_IP, ConstantConfig.DB_PORT)) {
				// System.out..println("DB is again connected");
			} else {

				// System.out..println("DB is not connecting.........");
			}
		}

		HttpSession session = request.getSession();
		String name = (String) session.getAttribute("letsTalk_username");

		if (name != null && name.length() > 0) {
	
			DBCollection table = MongoDBConnection.getDB().getCollection("tbl_block_list_data");

			DBObject updateQuery = new BasicDBObject();
			
			BasicDBObject data = new BasicDBObject();
			data.put("name", queryName);
			
			updateQuery.put("$pull", new BasicDBObject("block_list", data));
			//$addToSet
			BasicDBObject query = new BasicDBObject();
			query.put("username", name);
			table.update(query, updateQuery,true , true);

		}

		else {


		}
		 return "redirect:" + "/friendProfile?queryName="+queryName;

	}

}
